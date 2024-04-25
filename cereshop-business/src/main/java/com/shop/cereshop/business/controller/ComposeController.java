/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.business.page.compose.ShopCompose;
import com.shop.cereshop.business.page.compose.ShopComposeDetail;
import com.shop.cereshop.business.page.product.ShopProduct;
import com.shop.cereshop.business.page.scene.ShopScene;
import com.shop.cereshop.business.page.scene.ShopSceneDetail;
import com.shop.cereshop.business.param.compose.*;
import com.shop.cereshop.business.param.product.ProductGetAllParam;
import com.shop.cereshop.business.param.scene.SceneGetAllParam;
import com.shop.cereshop.business.param.scene.SceneGetByIdParam;
import com.shop.cereshop.business.param.scene.SceneSaveParam;
import com.shop.cereshop.business.service.compose.CereShopComposeService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 组合捆绑销售
 */
@RestController
@RequestMapping("compose")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "SceneController")
@Api(value = "组合捆绑销售", tags = "组合捆绑销售")
public class ComposeController {

    @Autowired
    private CereShopComposeService cereShopComposeService;

    /**
     * 添加组合捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加组合捆绑")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated ComposeSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopComposeService.save(param,user);
        return new Result(user.getUsername(),"添加组合捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 编辑组合捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "编辑组合捆绑")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated ComposeUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopComposeService.update(param,user);
        return new Result(user.getUsername(),"编辑组合捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 删除组合捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除组合捆绑")
    @NoRepeatWebLog
    public Result delete(@RequestBody ComposeGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopComposeService.delete(param,user);
        return new Result(user.getUsername(),"删除组合捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 启停用组合捆绑
     * @param param
     * @return
     */
    @RequestMapping(value = "start",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "启停用组合捆绑")
    @NoRepeatWebLog
    public Result start(@RequestBody ComposeStartParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopComposeService.start(param,user);
        return new Result(user.getUsername(),"启停用组合捆绑", GsonUtil.objectToGson(param));
    }

    /**
     * 组合捆绑详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "组合捆绑详情查询")
    public Result<ShopComposeDetail> getById(@RequestBody ComposeGetByIdParam param) throws CoBusinessException{
        ShopComposeDetail detail=cereShopComposeService.getById(param.getComposeId());
        return new Result(detail);
    }

    /**
     * 组合捆绑列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "组合捆绑列表查询")
    public Result<Page<ShopCompose>> getAll(@RequestBody ComposeGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopComposeService.getAll(param);
        return new Result(page);
    }

    /**
     * 选择商品查询
     * @param param
     * @return
     */
    @RequestMapping(value = "selectProduct",method = RequestMethod.POST)
    @ApiOperation(value = "选择商品查询")
    public Result<Page<ShopProduct>> selectProduct(@RequestBody ProductGetAllParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereShopComposeService.selectProduct(param);
        return new Result(page);
    }
}
