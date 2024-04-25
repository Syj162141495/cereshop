/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.buyer.BuyerUser;
import com.shop.cereshop.admin.page.buyer.BuyerUserDetail;
import com.shop.cereshop.admin.param.buyer.*;
import com.shop.cereshop.admin.service.buyer.CereBuyerUserService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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
 * 客户管理
 */
@RestController
@RequestMapping("buyer")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "BuyerUserController")
@Api(value = "客户管理模块", tags = "客户管理模块")
public class BuyerUserController {

    @Autowired
    private CereBuyerUserService cereBuyerUserService;

    /**
     * 客户管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "客户管理查询")
    public Result<Page<BuyerUser>> getAll(@RequestBody BuyerGetAllParam param) throws CoBusinessException{
        Page page=cereBuyerUserService.getAll(param);
        return new Result(page);
    }

    /**
     * 客户详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "客户详情查询")
    public Result<BuyerUserDetail> getById(@RequestBody BuyerGetByIdParam param) throws CoBusinessException{
        BuyerUserDetail detail=cereBuyerUserService.getById(param);
        return new Result(detail);
    }

    /**
     * 客户绑定标签查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getUserLabels",method = RequestMethod.POST)
    @ApiOperation(value = "客户绑定标签查询")
    public Result<List<CerePlatformLabel>> getUserLabels(@RequestBody BuyerGetLabelsParam param) throws CoBusinessException{
        List<CerePlatformLabel> list=cereBuyerUserService.getUserLabels(param);
        return new Result(list);
    }

    /**
     * 标签查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getLabels",method = RequestMethod.POST)
    @ApiOperation(value = "标签查询")
    public Result<List<CerePlatformLabel>> getLabels(@RequestBody BuyerGetLabelsParam param) throws CoBusinessException{
        List<CerePlatformLabel> list=cereBuyerUserService.getLabels(param);
        return new Result(list);
    }

    /**
     * 贴标签
     * @param param
     * @return
     */
    @RequestMapping(value = "saveUserLabel",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "贴标签")
    @NoRepeatWebLog
    public Result saveUserLabel(@RequestBody BuyerSaveUserLabelParam param,
                                HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerUserService.saveUserLabel(param,user);
        return new Result(user.getUsername(),"贴标签", GsonUtil.objectToGson(param));
    }

    /**
     * 加入或取消黑名单
     * @param param
     * @return
     */
    @RequestMapping(value = "blacklist",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "加入和取消黑名单")
    @NoRepeatWebLog
    public Result blacklist(@RequestBody BuyerBlackListParam param,
                            HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereBuyerUserService.blacklist(param,user);
        return new Result(user.getUsername(),"加入或取消黑名单", GsonUtil.objectToGson(param));
    }
}
