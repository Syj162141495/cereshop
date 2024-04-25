/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.label.ShopLabel;
import com.shop.cereshop.business.page.shop.LabelSource;
import com.shop.cereshop.business.param.label.*;
import com.shop.cereshop.business.service.label.CereLabelSourceService;
import com.shop.cereshop.business.service.label.CereShopLabelService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.label.CereShopLabel;
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
import java.util.List;

/**
 * 素材
 */
@RestController
@RequestMapping("shop_label")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ShopLabelController")
@Api(value = "素材模块", tags = "素材模块")
public class ShopLabelController {

    @Autowired
    private CereShopLabelService cereShopLabelService;

    @Autowired
    private CereLabelSourceService cereLabelSourceService;

    /**
     * 添加标签
     * @param label
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加标签")
    @NoRepeatWebLog
    public Result save(@RequestBody LabelSaveParam label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        label.setShopId(ContextUtil.getShopId());
        cereShopLabelService.save(label,user);
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
    public Result update(@RequestBody LabelUpdateParam label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        label.setShopId(ContextUtil.getShopId());
        cereShopLabelService.update(label,user);
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
    public Result delete(@RequestBody LabelDeleteParam label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        label.setShopId(ContextUtil.getShopId());
        cereShopLabelService.delete(label,user);
        return new Result(user.getUsername(),"删除标签", GsonUtil.objectToGson(label));
    }

    /**
     * 标签编辑查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "标签编辑查询")
    public Result<CereShopLabel> getById(@RequestBody LabelGetByIdParam param) throws CoBusinessException{
        CereShopLabel cereShopLabel=cereShopLabelService.getById(param.getLabelId());
        return new Result(cereShopLabel);
    }

    /**
     * 标签管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "标签管理查询")
    public Result<List<ShopLabel>> getAll(@RequestBody LabelGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<ShopLabel> list=cereShopLabelService.getAll(param);
        return new Result(list);
    }

    /**
     * 上传素材
     * @param param
     * @return
     */
    @RequestMapping(value = "saveSource",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "上传素材")
    @NoRepeatWebLog
    public Result saveSource(@RequestBody LabelSaveSourceParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereLabelSourceService.saveSource(param,user);
        return new Result(user.getUsername(),"上传素材", GsonUtil.objectToGson(param));
    }

    /**
     * 更新素材绑定标签
     * @param param
     * @return
     */
    @RequestMapping(value = "updateSource",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "更新素材绑定标签")
    @NoRepeatWebLog
    public Result updateSource(@RequestBody LabelSaveSourceParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereLabelSourceService.updateSource(param,user);
        return new Result(user.getUsername(),"更新素材绑定标签", GsonUtil.objectToGson(param));
    }

    /**
     * 素材查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAllByLabel",method = RequestMethod.POST)
    @ApiOperation(value = "素材查询")
    public Result<List<LabelSource>> getAllByLabel(@RequestBody LabelGetSourceParam param) throws CoBusinessException{
        List<LabelSource> list=cereLabelSourceService.getAllByLabel(param);
        return new Result(list);
    }

    /**
     * 素材图片删除
     * @param param
     * @return
     */
    @RequestMapping(value = "deleteSource",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "素材图片删除")
    @NoRepeatWebLog
    public Result deleteSource(@RequestBody LabelDeleteSourceParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(ContextUtil.getShopId());
        cereLabelSourceService.deleteSource(param,user);
        return new Result(user.getUsername(),"素材图片删除", GsonUtil.objectToGson(param));
    }
}
