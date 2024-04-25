/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.label.ShopUserLabel;
import com.shop.cereshop.business.param.label.LabelGetByIdParam;
import com.shop.cereshop.business.param.label.UserLabelGetAllParam;
import com.shop.cereshop.business.service.label.CereShopUserLabelService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.CereShopUserLabel;
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
 * 标签管理
 */
@RestController
@RequestMapping("user_label")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopUserLabelController")
@Api(value = "标签管理模块", tags = "标签管理模块")
public class ShopUserLabelController {

    @Autowired
    private CereShopUserLabelService cereShopUserLabelService;

    /**
     * 添加标签
     * @param label
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加标签")
    @NoRepeatWebLog
    public Result save(@RequestBody CereShopUserLabel label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        label.setShopId(ContextUtil.getShopId());
        cereShopUserLabelService.save(label,user);
        return new Result(user.getUsername(),"添加标签", GsonUtil.objectToGson(label));
    }

    /**
     * 修改标签
     * @param label
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改标签")
    @NoRepeatWebLog
    public Result update(@RequestBody CereShopUserLabel label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        label.setShopId(ContextUtil.getShopId());
        cereShopUserLabelService.update(label,user);
        return new Result(user.getUsername(),"修改标签", GsonUtil.objectToGson(label));
    }

    /**
     * 删除标签
     * @param label
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除标签")
    @NoRepeatWebLog
    public Result delete(@RequestBody LabelGetByIdParam label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopUserLabelService.delete(label.getIds(),user);
        return new Result(user.getUsername(),"删除标签", GsonUtil.objectToGson(label));
    }

    /**
     * 标签编辑查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "标签编辑查询")
    public Result<CereShopUserLabel> getById(@RequestBody LabelGetByIdParam param) throws CoBusinessException{
        CereShopUserLabel label=cereShopUserLabelService.getById(param.getLabelId());
        return new Result(label);
    }

    /**
     * 标签管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "标签管理查询")
    public Result<Page<ShopUserLabel>> getAll(@RequestBody UserLabelGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopUserLabelService.getAll(param);
        return new Result(page);
    }
}
