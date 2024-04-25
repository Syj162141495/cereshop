/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.page.shop.CommentDetail;
import com.shop.cereshop.admin.page.shop.ShopComment;
import com.shop.cereshop.admin.param.comment.CommentDeleteParam;
import com.shop.cereshop.admin.param.comment.CommentGetAllParam;
import com.shop.cereshop.admin.param.comment.CommentGetByIdParam;
import com.shop.cereshop.admin.param.comment.CommentUpdateParam;
import com.shop.cereshop.admin.service.shop.CereShopCommentService;
import com.shop.cereshop.commons.domain.common.Page;
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

/**
 * 评论管理
 */
@RestController
@RequestMapping("comment")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "CommentController")
@Api(value = "评论管理模块", tags = "评论管理模块")
public class CommentController {

    @Autowired
    private CereShopCommentService cereShopCommentService;

    /**
     * 评论管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "评论管理查询")
    public Result<Page<ShopComment>> getAll(@RequestBody CommentGetAllParam param) throws CoBusinessException{
        Page page=cereShopCommentService.getAll(param);
        return new Result(page);
    }

    /**
     * 评论详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "评论详情查询")
    public Result<CommentDetail> getById(@RequestBody CommentGetByIdParam param) throws CoBusinessException,Exception{
        CommentDetail detail=cereShopCommentService.getById(param.getCommentId());
        return new Result(detail);
    }

    /**
     * 修改评论
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改评论")
    @NoRepeatWebLog
    public Result update(@RequestBody CommentUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCommentService.update(param,user);
        return new Result(user.getUsername(),"修改评论", GsonUtil.objectToGson(param));
    }

    /**
     * 允许展示评论
     * @param param
     * @return
     */
    @RequestMapping(value = "allow",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "允许展示评论")
    @NoRepeatWebLog
    public Result allow(@RequestBody CommentUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCommentService.allow(param,user);
        return new Result(user.getUsername(),"允许展示评论", GsonUtil.objectToGson(param));
    }

    /**
     * 删除评论
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除评论")
    @NoRepeatWebLog
    public Result delete(@RequestBody CommentDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cereShopCommentService.delete(param,user);
        return new Result(user.getUsername(),"删除评论", GsonUtil.objectToGson(param));
    }
}
