/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.param.check.CheckShopParam;
import com.shop.cereshop.business.param.shop.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.business.CerePlatformBusinessService;
import com.shop.cereshop.business.service.shop.*;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 入驻
 */
@RestController
@RequestMapping("check")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopCheckController")
@Api(value = "入驻模块", tags = "入驻模块")
public class ShopCheckController {

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    @Autowired
    private CereShopPersonalService cereShopPersonalService;

    @Autowired
    private CereShopIndividualBusinessesService cereShopIndividualBusinessesService;

    @Autowired
    private CereShopOtherOrganizationsService cereShopOtherOrganizationsService;

    @Autowired
    private CereShopEnterpriseService cereShopEnterpriseService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CerePlatformBusinessService cerePlatformBusinessService;

    /**
     * 入驻申请（个人）
     * @param param
     * @return
     */
    @RequestMapping(value = "personalCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "入驻申请（个人）")
    @NoRepeatWebLog
    public Result personalCheck(@RequestBody @Validated CereShopPersonalParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.findByShopName(param.getShopName());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.findByPhone(param.getShopPhone());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        CerePlatformBusiness business=cerePlatformBusinessService.checkUserName(param.getShopPhone(),null);
        if(business!=null){
            return new Result(CoReturnFormat.PHONE_ALREADY_BIND_SHOP);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopPersonalService.personalCheck(param,user);
        return new Result(user.getUsername(),"入驻申请（个人）", GsonUtil.objectToGson(param));
    }

    /**
     * 入驻申请（个体工商户）
     * @param param
     * @return
     */
    @RequestMapping(value = "individualCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "入驻申请（个体工商户）")
    @NoRepeatWebLog
    public Result individualCheck(@RequestBody CereShopIndividualBusinessesParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.findByShopName(param.getShopName());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.findByPhone(param.getShopPhone());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        CerePlatformBusiness business=cerePlatformBusinessService.checkUserName(param.getShopPhone(),null);
        if(business!=null){
            return new Result(CoReturnFormat.PHONE_ALREADY_BIND_SHOP);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopIndividualBusinessesService.individualCheck(param,user);
        return new Result(user.getUsername(),"入驻申请（个体工商户）", GsonUtil.objectToGson(param));
    }

    /**
     * 入驻申请（企业）
     * @param param
     * @return
     */
    @RequestMapping(value = "enterpriseCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "入驻申请（企业）")
    @NoRepeatWebLog
    public Result enterpriseCheck(@RequestBody CereShopEnterpriseParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.findByShopName(param.getShopName());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.findByPhone(param.getShopPhone());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        CerePlatformBusiness business=cerePlatformBusinessService.checkUserName(param.getShopPhone(),null);
        if(business!=null){
            return new Result(CoReturnFormat.PHONE_ALREADY_BIND_SHOP);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopEnterpriseService.enterpriseCheck(param,user);
        return new Result(user.getUsername(),"入驻申请（企业）", GsonUtil.objectToGson(param));
    }

    /**
     * 入驻申请（其他组织）
     * @param param
     * @return
     */
    @RequestMapping(value = "organizationsCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "入驻申请（其他组织）")
    @NoRepeatWebLog
    public Result organizationsCheck(@RequestBody CereShopOtherOrganizationsParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.findByShopName(param.getShopName());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.findByPhone(param.getShopPhone());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        CerePlatformBusiness business=cerePlatformBusinessService.checkUserName(param.getShopPhone(),null);
        if(business!=null){
            return new Result(CoReturnFormat.PHONE_ALREADY_BIND_SHOP);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopOtherOrganizationsService.organizationsCheck(param,user);
        return new Result(user.getUsername(),"入驻申请（其他组织）", GsonUtil.objectToGson(param));
    }

    /**
     * 修改入驻申请（个人）
     * @param param
     * @return
     */
    @RequestMapping(value = "updatePersonalCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改入驻申请（个人）")
    @NoRepeatWebLog
    public Result updatePersonalCheck(@RequestBody CereShopPersonalParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.checkShopIdByName(param.getShopName(),param.getShopId());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.checkShopIdByPhone(param.getShopPhone(),param.getShopId());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopPersonalService.updatePersonalCheck(param,user);
        return new Result(user.getUsername(),"修改入驻申请（个人）", GsonUtil.objectToGson(param));
    }

    /**
     * 修改入驻申请（个体工商户）
     * @param param
     * @return
     */
    @RequestMapping(value = "updateIndividualCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改入驻申请（个体工商户）")
    @NoRepeatWebLog
    public Result updateIndividualCheck(@RequestBody CereShopIndividualBusinessesParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.checkShopIdByName(param.getShopName(),param.getShopId());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.checkShopIdByPhone(param.getShopPhone(),param.getShopId());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopIndividualBusinessesService.updateIndividualCheck(param,user);
        return new Result(user.getUsername(),"修改入驻申请（个体工商户）", GsonUtil.objectToGson(param));
    }

    /**
     * 修改入驻申请（企业）
     * @param param
     * @return
     */
    @RequestMapping(value = "updateEnterpriseCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改入驻申请（企业）")
    @NoRepeatWebLog
    public Result updateEnterpriseCheck(@RequestBody CereShopEnterpriseParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.checkShopIdByName(param.getShopName(),param.getShopId());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.checkShopIdByPhone(param.getShopPhone(),param.getShopId());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopEnterpriseService.updateEnterpriseCheck(param,user);
        return new Result(user.getUsername(),"修改入驻申请（企业）", GsonUtil.objectToGson(param));
    }

    /**
     * 修改入驻申请（其他组织）
     * @param param
     * @return
     */
    @RequestMapping(value = "updateOrganizationsCheck",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改入驻申请（其他组织）")
    @NoRepeatWebLog
    public Result updateOrganizationsCheck(@RequestBody CereShopOtherOrganizationsParam param, HttpServletRequest request) throws CoBusinessException{
        //校验商家名称是否存在
        CerePlatformShop shop=cerePlatformShopService.checkShopIdByName(param.getShopName(),param.getShopId());
        if(shop!=null){
            return new Result(CoReturnFormat.SHOP_NAME_ALREADY);
        }
        //校验手机号是否已注册
        CerePlatformShop platformShop=cerePlatformShopService.checkShopIdByPhone(param.getShopPhone(),param.getShopId());
        if(platformShop!=null){
            return new Result(CoReturnFormat.SHOP_PHONE_ALREADY);
        }
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopOtherOrganizationsService.updateOrganizationsCheck(param,user);
        return new Result(user.getUsername(),"修改入驻申请（其他组织）", GsonUtil.objectToGson(param));
    }

    /**
     * 查询是否申请
     * @param param
     * @return
     */
    @RequestMapping(value = "check",method = RequestMethod.POST)
    @ApiOperation(value = "查询是否申请")
    public Result<CerePlatformShop> check(@RequestBody CheckShopParam param) throws CoBusinessException{
        if(!param.getCode().equals("9999")){
            //手机号登录,校验验证码
            String code = (String) stringRedisService.get(param.getShopPhone());
            if(!param.getCode().equals(code)){
                return new Result(CoReturnFormat.CODE_ERROR);
            }
        }
        CerePlatformShop shop=cerePlatformShopService.check(param.getShopPhone());
        return new Result(shop);
    }

    /**
     * 查询申请信息
     * @param
     * @return
     */
    @GetMapping("getCheckInfo")
    @ApiOperation(value = "查询申请信息")
    public Result getCheckInfo(ShopParam shopParam) throws CoBusinessException {
        shopParam.setShopId(ContextUtil.getShopId());
        CerePlatformShop shop = cerePlatformShopService.selectByPrimaryKey(shopParam.getShopId());
        if (shop == null) {
            return new Result(CoReturnFormat.PARAM_INVALID);
        }
        Integer authenType = shop.getAuthenType();
        if (IntegerEnum.PERSONAL.getCode().equals(authenType)) {
            CereShopPersonalParam param = new CereShopPersonalParam();
            BeanUtils.copyProperties(cereShopPersonalService.findByShopId(shop.getShopId()), param);
            param.setShopName(shop.getShopName());
            param.setShopPhone(shop.getShopPhone());
            param.setChargePersonName(shop.getChargePersonName());
            param.setChargePersonPhone(shop.getChargePersonPhone());
            param.setShopAdressProvince(shop.getShopAdressProvince());
            param.setShopAdressCity(shop.getShopAdressCity());
            param.setShopAdressDetail(shop.getShopAdressDetail());
            param.setAuthenType(authenType);
            return new Result(param);
        } else if (IntegerEnum.INDIVIDUAL.getCode().equals(authenType)) {
            CereShopIndividualBusinessesParam param = new CereShopIndividualBusinessesParam();
            BeanUtils.copyProperties(cereShopIndividualBusinessesService.findByShopId(shop.getShopId()), param);
            param.setShopName(shop.getShopName());
            param.setShopPhone(shop.getShopPhone());
            param.setChargePersonName(shop.getChargePersonName());
            param.setChargePersonPhone(shop.getChargePersonPhone());
            param.setShopAdressProvince(shop.getShopAdressProvince());
            param.setShopAdressCity(shop.getShopAdressCity());
            param.setShopAdressDetail(shop.getShopAdressDetail());
            param.setAuthenType(authenType);
            return new Result(param);
        } else if (IntegerEnum.ENTERPRICE.getCode().equals(authenType)) {
            CereShopEnterpriseParam param = new CereShopEnterpriseParam();
            BeanUtils.copyProperties(cereShopEnterpriseService.findByShopId(shop.getShopId()), param);
            param.setShopName(shop.getShopName());
            param.setShopPhone(shop.getShopPhone());
            param.setChargePersonName(shop.getChargePersonName());
            param.setChargePersonPhone(shop.getChargePersonPhone());
            param.setShopAdressProvince(shop.getShopAdressProvince());
            param.setShopAdressCity(shop.getShopAdressCity());
            param.setShopAdressDetail(shop.getShopAdressDetail());
            param.setAuthenType(authenType);
            return new Result(param);
        } else {
            CereShopOtherOrganizationsParam param = new CereShopOtherOrganizationsParam();
            BeanUtils.copyProperties(cereShopOtherOrganizationsService.findByShopId(shop.getShopId()), param);
            param.setShopName(shop.getShopName());
            param.setShopPhone(shop.getShopPhone());
            param.setChargePersonName(shop.getChargePersonName());
            param.setChargePersonPhone(shop.getChargePersonPhone());
            param.setShopAdressProvince(shop.getShopAdressProvince());
            param.setShopAdressCity(shop.getShopAdressCity());
            param.setShopAdressDetail(shop.getShopAdressDetail());
            param.setAuthenType(authenType);
            return new Result(param);
        }
    }
}
