/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.shop.Shop;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.param.shop.ShopUpdateLogoParam;
import com.shop.cereshop.business.service.shop.*;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.*;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 店铺
 */
@RestController
@RequestMapping("shop")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopController")
@Api(value = "店铺模块", tags = "店铺模块")
public class ShopController {

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    @Autowired
    private CereShopReturnService cereShopReturnService;

    @Autowired
    private CereShopPersonalService cereShopPersonalService;

    @Autowired
    private CereShopIndividualBusinessesService cereShopIndividualBusinessesService;

    @Autowired
    private CereShopOtherOrganizationsService cereShopOtherOrganizationsService;

    @Autowired
    private CereShopEnterpriseService cereShopEnterpriseService;

    /**
     * 店铺信息查询
     * @param cerePlatformShop
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "店铺信息查询")
    public Result<Shop> getById(@RequestBody ShopParam cerePlatformShop) throws CoBusinessException{
        cerePlatformShop.setShopId(ContextUtil.getShopId());
        Shop shop=cerePlatformShopService.getById(cerePlatformShop.getShopId());
        return new Result(shop);
    }

    /**
     * 修改店铺信息
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改店铺信息")
    @NoRepeatWebLog
    public Result updateLogo(@RequestBody ShopUpdateLogoParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cerePlatformShopService.update(param,user);
        return new Result(user.getUsername(),"修改店铺信息", GsonUtil.objectToGson(param));
    }

    /**
     * 修改退货地址信息
     * @param cereShopReturn
     * @return
     */
    @RequestMapping(value = "updateReturn",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改退货地址信息")
    @NoRepeatWebLog
    public Result updateReturn(@RequestBody CereShopReturn cereShopReturn, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopReturn.setShopId(ContextUtil.getShopId());
        cereShopReturnService.updateReturn(cereShopReturn,user);
        return new Result(user.getUsername(),"修改退货地址信息", GsonUtil.objectToGson(cereShopReturn));
    }

    /**
     * 店铺认证（个人）
     * @param personal
     * @return
     */
    @RequestMapping(value = "personal",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "店铺认证（个人）")
    @NoRepeatWebLog
    public Result personal(@RequestBody CereShopPersonal personal, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        personal.setShopId(ContextUtil.getShopId());
        cereShopPersonalService.personal(personal,user);
        return new Result(user.getUsername(),"店铺认证（个人）", GsonUtil.objectToGson(personal));
    }

    /**
     * 店铺认证（个体工商户）
     * @param individualBusinesses
     * @return
     */
    @RequestMapping(value = "individual",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "店铺认证（个体工商户）")
    @NoRepeatWebLog
    public Result individual(@RequestBody CereShopIndividualBusinesses individualBusinesses, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        individualBusinesses.setShopId(ContextUtil.getShopId());
        cereShopIndividualBusinessesService.individual(individualBusinesses,user);
        return new Result(user.getUsername(),"店铺认证（个体工商户）", GsonUtil.objectToGson(individualBusinesses));
    }

    /**
     * 店铺认证（企业）
     * @param enterprise
     * @return
     */
    @RequestMapping(value = "enterprise",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "店铺认证（企业）")
    @NoRepeatWebLog
    public Result enterprise(@RequestBody CereShopEnterprise enterprise, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        enterprise.setShopId(ContextUtil.getShopId());
        cereShopEnterpriseService.enterprise(enterprise,user);
        return new Result(user.getUsername(),"店铺认证（企业）", GsonUtil.objectToGson(enterprise));
    }

    /**
     * 店铺认证（其他组织）
     * @param otherOrganizations
     * @return
     */
    @RequestMapping(value = "organizations",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "店铺认证（其他组织）")
    @NoRepeatWebLog
    public Result organizations(@RequestBody CereShopOtherOrganizations otherOrganizations, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        otherOrganizations.setShopId(ContextUtil.getShopId());
        cereShopOtherOrganizationsService.organizations(otherOrganizations,user);
        return new Result(user.getUsername(),"店铺认证（其他组织）", GsonUtil.objectToGson(otherOrganizations));
    }

    /**
     * 修改店铺认证（个人）
     * @param personal
     * @return
     */
    @RequestMapping(value = "updatePersonal",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改店铺认证（个人）")
    @NoRepeatWebLog
    public Result updatePersonal(@RequestBody CereShopPersonal personal, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        personal.setShopId(ContextUtil.getShopId());
        cereShopPersonalService.updatePersonal(personal,user);
        return new Result(user.getUsername(),"修改店铺认证（个人）", GsonUtil.objectToGson(personal));
    }

    /**
     * 修改店铺认证（个体工商户）
     * @param individualBusinesses
     * @return
     */
    @RequestMapping(value = "updateIndividual",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改店铺认证（个体工商户）")
    @NoRepeatWebLog
    public Result updateIndividual(@RequestBody CereShopIndividualBusinesses individualBusinesses, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        individualBusinesses.setShopId(ContextUtil.getShopId());
        cereShopIndividualBusinessesService.updateIndividual(individualBusinesses,user);
        return new Result(user.getUsername(),"修改店铺认证（个体工商户）", GsonUtil.objectToGson(individualBusinesses));
    }

    /**
     * 修改店铺认证（企业）
     * @param enterprise
     * @return
     */
    @RequestMapping(value = "updateEnterprise",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改店铺认证（企业）")
    @NoRepeatWebLog
    public Result updateEnterprise(@RequestBody CereShopEnterprise enterprise, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        enterprise.setShopId(ContextUtil.getShopId());
        cereShopEnterpriseService.updateEnterprise(enterprise,user);
        return new Result(user.getUsername(),"修改店铺认证（企业）", GsonUtil.objectToGson(enterprise));
    }

    /**
     * 修改店铺认证（其他组织）
     * @param otherOrganizations
     * @return
     */
    @RequestMapping(value = "updateOrganizations",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改店铺认证（其他组织）")
    @NoRepeatWebLog
    public Result updateOrganizations(@RequestBody CereShopOtherOrganizations otherOrganizations, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        otherOrganizations.setShopId(ContextUtil.getShopId());
        cereShopOtherOrganizationsService.updateOrganizations(otherOrganizations,user);
        return new Result(user.getUsername(),"修改店铺认证（其他组织）", GsonUtil.objectToGson(otherOrganizations));
    }
}
