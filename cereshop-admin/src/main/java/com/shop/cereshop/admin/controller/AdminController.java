/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.message.service.aliyun.AliyunMessageService;
import com.shop.cereshop.admin.message.service.miaoxin.MiaoxinMessageService;
import com.shop.cereshop.admin.page.permission.MenuButton;
import com.shop.cereshop.admin.param.user.UserBuildParam;
import com.shop.cereshop.admin.param.user.UserForgetPasswordParam;
import com.shop.cereshop.admin.param.user.UserGetCodeParam;
import com.shop.cereshop.admin.param.user.UserLoginParam;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.message.CereMessageLogService;
import com.shop.cereshop.admin.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.admin.service.shop.CereShopCheckService;
import com.shop.cereshop.admin.service.user.CerePlatformUserService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.EncryptUtil;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 用户登录
 */
@RestController
@RequestMapping("admin")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "AdminController")
@Api(value = "用户登录模块", tags = "用户登录模块")
public class AdminController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private CerePlatformUserService cerePlatformUserService;

    @Autowired
    private CerePlatformPermissionService cerePlatformPermissionService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private AliyunMessageService aliyunMessageService;

    @Autowired
    private MiaoxinMessageService miaoxinMessageService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CereMessageLogService cereMessageLogService;

    /**
     * 平台账户登录
     * @param param 封装json对象
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ApiOperation(value = "平台账户登录")
    public Result<CerePlatformUser> login(@RequestBody UserLoginParam param) throws CoBusinessException,Exception{
        CerePlatformUser user=null;
        if(!EmptyUtils.isEmpty(param.getCode())){
            if(!param.getCode().equals("9999")){
                //手机号登录,校验验证码
                String code = (String) stringRedisService.get(param.getUsername());
                if(!param.getCode().equals(code)){
                    return new Result(CoReturnFormat.CODE_ERROR);
                }
            }
            //根据手机号查询账户信息
            user=cerePlatformUserService.findByPhone(param.getUsername());
        }else {
            //根据账号查询token
            user=cerePlatformUserService.findByUserName(param.getUsername());
            if(user!=null){
                //校验密码
                if(!param.getPassword().equals(EncryptUtil.decrypt(user.getPassword()))){
                    return new Result(CoReturnFormat.USER_OR_PASSWD_ERROR);
                }
            }
        }
        if(user==null){
            return new Result(CoReturnFormat.USER_UNREGISTER);
        }
        if(IntegerEnum.NO.getCode().equals(user.getState())){
            return new Result(CoReturnFormat.USER_TYPE_STOP);
        }
        request.setAttribute("user",user);
        if(!EmptyUtils.isEmpty(param.getRememberMe())){
            //清楚延时任务和记录
            stringRedisService.delete(StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode()+"-"+
                    user.getPlatformUserId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode()+"-"+
                    user.getPlatformUserId());
            if(param.getRememberMe()){
                //7日后更新该用户token数据
                stringRedisService.set(StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode()+"-"+
                        user.getPlatformUserId(),1,7*24*60*60*1000);
                //新增redis延时任务记录
                cereRedisKeyServcice.add(StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode()+"-"+
                        user.getPlatformUserId(), TimeUtils.getMoreDayAfter(TimeUtils.yyMMddHHmmss(),7));
            }
        }
        user.setPassword(null);
        return new Result(user);
    }


//    @GetMapping("bigReq")
//    public Result bigReqList() {
//        List<String> result = new ArrayList<>(2048);
//        for (int i = 0; i < 204800; i++) {
//            result.add(UUID.randomUUID().toString());
//        }
//        return new Result(result,CoReturnFormat.SUCCESS);
//    }

    /**
     * 登录权限查询
     * @return
     */
    @RequestMapping(value = "build",method = RequestMethod.POST)
    @ApiOperation(value = "登录权限查询")
    public Result<List<MenuButton>> build(@RequestBody UserBuildParam user) throws CoBusinessException{
        List<MenuButton> list =cerePlatformPermissionService.getAllByUserId(user);
        return new Result(list);
    }

    /**
     * 忘记密码
     * @return
     */
    @RequestMapping(value = "forgetPassword",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "忘记密码")
    public Result forgetPassword(@RequestBody UserForgetPasswordParam user) throws CoBusinessException{
        //校验验证码
        if(!user.getCode().equals("9999")){
            //手机号登录,校验验证码
            String code = (String) stringRedisService.get(user.getUsername());
            if(!user.getCode().equals(code)){
                return new Result(CoReturnFormat.CODE_ERROR);
            }
        }
        //校验2次密码是否一致
        if(!user.getPassword().equals(user.getNewPassword())){
            return new Result(CoReturnFormat.PASSWORD_NOT_AGREEN);
        }
        //校验手机号是否注册
        CerePlatformUser cerePlatformUser=cerePlatformUserService.findByPhone(user.getUsername());
        if(cerePlatformUser==null){
            return new Result(CoReturnFormat.USER_UNREGISTER);
        }
        cerePlatformUserService.forgetPassword(user,cerePlatformUser);
        return new Result();
    }


    /**
     * 获取短信验证码
     * @param user
     * @return
     */
    @RequestMapping(value = "getCode",method = RequestMethod.POST)
    @ApiOperation(value = "获取短信验证码")
    public Result getCode(@RequestBody UserGetCodeParam user) throws CoBusinessException,Exception{
        //获取验证码
        String code = RandomStringUtil.getRandom();
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        //验证码存到redis中(5分钟失效)
        stringRedisService.set(user.getPhone(),code,300000);
        //发送短信给用户
        try {
            miaoxinMessageService.sendNotice(user.getPhone(), map);
        } catch (Exception e) {
            log.error("getCode fail: phone = {}", user.getPhone(), e);
            throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
        }
        return new Result();
    }

    /**
     * 一键初始化redis延时任务
     * @return
     */
    @RequestMapping(value = "initializationRedis",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "一键初始化redis延时任务")
    public Result initializationRedis() throws Exception{
        cereRedisKeyServcice.initializationRedis();
        return new Result();
    }

    @Autowired
    private CereShopCheckService cereShopCheckService;

    /**
     * 初始化店铺角色权限数据
     * @return
     */
    @RequestMapping(value = "initializationShopPermission",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "初始化店铺角色权限数据")
    public Result initializationShopPermission() throws CoBusinessException{
        cereShopCheckService.setInitPermission(2l,TimeUtils.yyMMddHHmmss());
        return new Result();
    }
}
