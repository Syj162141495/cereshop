/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.word.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.word.CerePlatformWordDAO;
import com.shop.cereshop.admin.param.word.WordParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.word.CerePlatformWordService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.log.CerePlatformLog;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.domain.word.CerePlatformWord;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CerePlatformWordServiceImpl implements CerePlatformWordService {

    @Autowired
    private CerePlatformWordDAO cerePlatformWordDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public CerePlatformWord checkWord(String keyWord,Long wordId) {
        return cerePlatformWordDAO.checkWord(keyWord,wordId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(CerePlatformWord word, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        word.setCreateTime(time);
        cerePlatformWordDAO.insert(word);
        //新增日志
        cerePlatformLogService.addLog(user,"评论管理","平台端操作","添加关键词",String.valueOf(word.getWordId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(CerePlatformWord word, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        word.setUpdateTime(time);
        cerePlatformWordDAO.updateByPrimaryKeySelective(word);
        //新增日志
        cerePlatformLogService.addLog(user,"评论管理","平台端操作","修改关键词",String.valueOf(word.getWordId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(CerePlatformWord word, CerePlatformUser user) throws CoBusinessException {
        cerePlatformWordDAO.deleteByPrimaryKey(word.getWordId());
        //新增日志
        cerePlatformLogService.addLog(user,"评论管理","平台端操作","删除关键词",String.valueOf(word.getWordId()),TimeUtils.yyMMddHHmmss());
    }

    @Override
    public CerePlatformWord getById(Long wordId) throws CoBusinessException {
        return cerePlatformWordDAO.selectByPrimaryKey(wordId);
    }

    @Override
    public Page getAll(WordParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CerePlatformWord> list=cerePlatformWordDAO.getAll(param);
        PageInfo<CerePlatformWord> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void start(Integer state) throws CoBusinessException {
        cerePlatformWordDAO.updateState(state);
    }
}
