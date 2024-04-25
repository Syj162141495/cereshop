/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.scene.ShopScene;
import com.shop.cereshop.business.page.scene.ShopSceneDetail;
import com.shop.cereshop.business.param.scene.SceneGetAllParam;
import com.shop.cereshop.business.param.scene.SceneGetByIdParam;
import com.shop.cereshop.business.param.scene.SceneSaveParam;
import com.shop.cereshop.business.param.scene.SceneUpdateParam;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
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
 * 场景营销
 */
@RestController
@RequestMapping("scene")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "SceneController")
@Api(value = "场景营销", tags = "场景营销")
public class SceneController {

    @Autowired
    private CereShopSceneService cereShopSceneService;

    /**
     * 添加场景营销
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加场景营销")
    @NoRepeatWebLog
    public Result insert(@RequestBody @Validated SceneSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopSceneService.save(param,user);
        return new Result(user.getUsername(),"添加场景营销", GsonUtil.objectToGson(param));
    }

    /**
     * 编辑场景营销
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "编辑场景营销")
    @NoRepeatWebLog
    public Result update(@RequestBody @Validated SceneUpdateParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopSceneService.update(param,user);
        return new Result(user.getUsername(),"编辑场景营销", GsonUtil.objectToGson(param));
    }

    /**
     * 删除场景营销
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除场景营销")
    @NoRepeatWebLog
    public Result delete(@RequestBody SceneGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        cereShopSceneService.delete(param,user);
        return new Result(user.getUsername(),"删除场景营销", GsonUtil.objectToGson(param));
    }

    /**
     * 启动场景营销
     * @param param
     * @return
     */
    @RequestMapping(value = "start",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "启动场景营销")
    @NoRepeatWebLog
    public Result start(@RequestBody SceneGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopSceneService.start(param,user);
        return new Result(user.getUsername(),"启动场景营销", GsonUtil.objectToGson(param));
    }

    /**
     * 停止场景营销
     * @param param
     * @return
     */
    @RequestMapping(value = "stop",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "停止场景营销")
    @NoRepeatWebLog
    public Result stop(@RequestBody SceneGetByIdParam param, HttpServletRequest request) throws CoBusinessException,Exception {
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        cereShopSceneService.stop(param,user);
        return new Result(user.getUsername(),"停止场景营销", GsonUtil.objectToGson(param));
    }

    /**
     * 场景营销详情查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "场景营销详情查询")
    public Result<CerePlatformSeckill> getById(@RequestBody SceneGetByIdParam param) throws CoBusinessException{
        ShopSceneDetail detail=cereShopSceneService.getById(param.getSceneId());
        return new Result(detail);
    }

    /**
     * 查询会员等级数据
     * @return
     */
    @RequestMapping(value = "getMemberLevels",method = RequestMethod.POST)
    @ApiOperation(value = "查询会员等级数据")
    public Result<List<CerePlatformMemberLevel>> getMemberLevels() throws CoBusinessException{
        List<CerePlatformMemberLevel> list=cereShopSceneService.getMemberLevels();
        return new Result(list);
    }

    /**
     * 场景营销列表查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "场景营销列表查询")
    public Result<Page<ShopScene>> getAll(@RequestBody SceneGetAllParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page =cereShopSceneService.getAll(param);
        return new Result(page);
    }
}
