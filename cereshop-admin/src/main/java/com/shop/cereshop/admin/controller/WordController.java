/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.word.WordParam;
import com.shop.cereshop.admin.service.word.CerePlatformWordService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.domain.word.CerePlatformWord;
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

/**
 * 关键词
 */
@RestController
@RequestMapping("word")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "WordController")
@Api(value = "关键词", tags = "关键词")
public class WordController {

    @Autowired
    private CerePlatformWordService cerePlatformWordService;

    /**
     * 添加关键词
     * @param word
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加关键词")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated CerePlatformWord word, HttpServletRequest request) throws CoBusinessException{
        //校验关键词
        CerePlatformWord cerePlatformWord=cerePlatformWordService.checkWord(word.getKeyWord(),null);
        if(cerePlatformWord!=null){
            return new Result(CoReturnFormat.KEY_WORD_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.save(word,user);
        return new Result(user.getUsername(),"添加关键词", GsonUtil.objectToGson(word));
    }

    /**
     * 修改关键词
     * @param word
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改关键词")
    @NoRepeatWebLog
    public Result update(@RequestBody CerePlatformWord word, HttpServletRequest request) throws CoBusinessException{
        //校验关键词
        CerePlatformWord cerePlatformWord=cerePlatformWordService.checkWord(word.getKeyWord(),word.getWordId());
        if(cerePlatformWord!=null){
            return new Result(CoReturnFormat.KEY_WORD_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.update(word,user);
        return new Result(user.getUsername(),"修改关键词", GsonUtil.objectToGson(word));
    }

    /**
     * 删除关键词
     * @param word
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除关键词")
    @NoRepeatWebLog
    public Result delete(@RequestBody CerePlatformWord word, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.delete(word,user);
        return new Result(user.getUsername(),"删除关键词", GsonUtil.objectToGson(word));
    }

    /**
     * 关键词修改查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "关键词修改查询")
    public Result<CerePlatformDict> getById(@RequestBody CerePlatformWord word) throws CoBusinessException{
        CerePlatformWord cerePlatformWord=cerePlatformWordService.getById(word.getWordId());
        return new Result(cerePlatformWord);
    }

    /**
     * 关键词管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "关键词管理查询")
    public Result<Page<CerePlatformWord>> getAll(@RequestBody WordParam param) throws CoBusinessException{
        Page page =cerePlatformWordService.getAll(param);
        return new Result(page);
    }

    /**
     * 启停用
     * @return
     */
    @RequestMapping(value = "start",method = RequestMethod.POST)
    @ApiOperation(value = "启停用")
    @NoRepeatWebLog
    public Result start(@RequestBody CerePlatformWord word, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformWordService.start(word.getState());
        return new Result(user.getUsername(),"启停用", GsonUtil.objectToGson(word));
    }
}
