/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.http.server.HttpServerResponse;
import com.shop.cereshop.business.message.service.aliyun.AliyunMessageService;
import com.shop.cereshop.business.message.service.miaoxin.MiaoxinMessageService;
import com.shop.cereshop.business.page.permission.MenuButton;
import com.shop.cereshop.business.page.shop.PlatformBusiness;
import com.shop.cereshop.business.param.business.BusinessForgetParam;
import com.shop.cereshop.business.param.business.BusinessGetCodeParam;
import com.shop.cereshop.business.param.business.BusinessLoginParam;
import com.shop.cereshop.business.param.permission.UserBuildParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.business.CerePlatformBusinessService;
import com.shop.cereshop.business.service.message.CereMessageLogService;
import com.shop.cereshop.business.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户登录
 */
@RestController
@RequestMapping("business")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BusinessController")
@Api(value = "用户登录模块", tags = "用户登录模块")
public class BusinessController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    private CerePlatformBusinessService cerePlatformBusinessService;

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

    @Autowired
    private CerePlatformPermissionService cerePlatformPermissionService;

    /**
     * 商家用户登录
     * @param param 封装json对象
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ApiOperation(value = "商家用户登录")
    public Result<PlatformBusiness> login(@RequestBody BusinessLoginParam param) throws CoBusinessException,Exception{
        PlatformBusiness user=null;
        if(!EmptyUtils.isEmpty(param.getCode())){
            if(!param.getCode().equals("9999")){
                //手机号登录,校验验证码
                String code = (String) stringRedisService.get(param.getUsername());
                if(!param.getCode().equals(code)){
                    return new Result(CoReturnFormat.CODE_ERROR);
                }
            }
            //根据手机号查询用户信息
            user=cerePlatformBusinessService.findByUserName(param.getUsername());
        }else {
            //根据账号查询token
            user=cerePlatformBusinessService.findByUserName(param.getUsername());
            if(user!=null){
                //校验密码
                if(!param.getPassword().equals(EncryptUtil.decrypt(user.getPassword()))){
                    return new Result(CoReturnFormat.USER_OR_PASSWD_ERROR);
                }
            }
        }
        if(user==null){
            return new Result(CoReturnFormat.USER_UNREGISTER);
        }else {
            if(IntegerEnum.NO.getCode().equals(user.getState())){
                return new Result(CoReturnFormat.USER_TYPE_STOP);
            }
            if(IntegerEnum.UNTREATED.getCode().equals(user.getCheckState())){
                return new Result(CoReturnFormat.SHOP_CHECK_STAY);
            }
            if(IntegerEnum.REFUSE.getCode().equals(user.getCheckState())){
                return new Result(CoReturnFormat.SHOP_CHECK_STOP);
            }
        }
        request.setAttribute("user",user);
        if(!EmptyUtils.isEmpty(param.getRememberMe())){
            //清楚延时任务和记录
            stringRedisService.delete(StringEnum.SEVEN_DAY_CHANGE_BUSINESS_TOKEN.getCode()+"-"+
                    user.getBusinessUserId());
            cereRedisKeyServcice.deleteByKey(StringEnum.SEVEN_DAY_CHANGE_BUSINESS_TOKEN.getCode()+"-"+
                    user.getBusinessUserId());
            if(param.getRememberMe()){
                //7日后更新该用户token数据
                stringRedisService.set(StringEnum.SEVEN_DAY_CHANGE_BUSINESS_TOKEN.getCode()+"-"+
                        user.getBusinessUserId(),1,7*24*60*60*1000);
                //新增redis延时任务记录
                cereRedisKeyServcice.add(StringEnum.SEVEN_DAY_CHANGE_BUSINESS_TOKEN.getCode()+"-"+
                        user.getBusinessUserId(), TimeUtils.getMoreDayAfter(TimeUtils.yyMMddHHmmss(),7));
            }
        }
        user.setPassword(null);
        return new Result(user);
    }

    /**
     * 登录权限查询
     * @return
     */
    @RequestMapping(value = "build",method = RequestMethod.POST)
    @ApiOperation(value = "登录权限查询")
    public Result<List<MenuButton>> build(@RequestBody UserBuildParam user) throws CoBusinessException{
        user.setShopId(ContextUtil.getShopId());
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
    public Result forgetPassword(@RequestBody BusinessForgetParam user) throws CoBusinessException{
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
        CerePlatformBusiness cerePlatformBusiness=cerePlatformBusinessService.findByUserName(user.getUsername());
        if(cerePlatformBusiness==null){
            return new Result(CoReturnFormat.USER_UNREGISTER);
        }
        cerePlatformBusinessService.forgetPassword(user,cerePlatformBusiness);
        return new Result();
    }


    /**
     * 获取短信验证码
     * @param user
     * @return
     */
    @RequestMapping(value = "getCode",method = RequestMethod.POST)
    @ApiOperation(value = "获取短信验证码")
    public Result getCode(@RequestBody BusinessGetCodeParam user) throws CoBusinessException,Exception{
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
     * 更新密码
     *
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ApiOperation(value = "更新密码")
    public Result updatePassword(@RequestBody BusinessForgetParam passwordParam, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cerePlatformBusinessService.updatePassword(passwordParam, user);
        return new Result();
    }

    /**
     * 更新头像
     *
     * @return
     */
    @RequestMapping(value = "updateAvatar", method = RequestMethod.POST)
    @ApiOperation(value = "更新头像")
    public Result updateAvatar(@RequestBody CerePlatformBusiness platformBusiness, HttpServletRequest request) throws CoBusinessException {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        if (user != null) {
            user.setAvatar(platformBusiness.getAvatar());
            cerePlatformBusinessService.updateAvatar(user);
        }
        return new Result();
    }
}
