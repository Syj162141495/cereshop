/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;


import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.product.ProductClassify;
import com.shop.cereshop.admin.param.product.ClassifyDeleteParam;
import com.shop.cereshop.admin.param.product.ClassifyGetAllParam;
import com.shop.cereshop.admin.param.product.ClassifyGetByIdParam;
import com.shop.cereshop.admin.param.product.ClassifyLevelParam;
import com.shop.cereshop.admin.service.product.CereProductClassifyService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品类别
 */
@RestController
@RequestMapping("classify")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ClassifyController")
@Api(value = "商品类别模块", tags = "商品类别模块")
public class ClassifyController {

    @Autowired
    private CereProductClassifyService cereProductClassifyService;

    /**
     * 添加商品类别
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加商品类别")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated ClassifyLevelParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductClassifyService.save(param,user);
        return new Result(user.getUsername(),"添加商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 修改商品类别
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改商品类别")
    @NoRepeatWebLog
    public Result update(@RequestBody ClassifyLevelParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductClassifyService.update(param,user);
        return new Result(user.getUsername(),"修改商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 删除商品类别
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除商品类别")
    @NoRepeatWebLog
    public Result delete(@RequestBody ClassifyDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereProductClassifyService.delete(param,user);
        return new Result(user.getUsername(),"删除商品类别", GsonUtil.objectToGson(param));
    }

    /**
     * 商品类别编辑查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "商品类别编辑查询")
    public Result<ProductClassify> getById(@RequestBody ClassifyGetByIdParam param) throws CoBusinessException{
        ProductClassify classify=cereProductClassifyService.getById(param.getOneClassifyId());
        return new Result(classify);
    }

    /**
     * 商品类别管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "商品类别管理查询")
    public Result<Page<CereProductClassify>> getAll(@RequestBody ClassifyGetAllParam param) throws CoBusinessException{
        Page page=cereProductClassifyService.getAll(param);
        return new Result(page);
    }

    /**
     * 查询分类层级
     * @return
     */
    @RequestMapping(value = "getClassify",method = RequestMethod.POST)
    @ApiOperation(value = "查询分类层级")
    public Result<Classify> getClassify() throws CoBusinessException{
        List<Classify> list=cereProductClassifyService.getClassify();
        return new Result(list);
    }
}
