/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.notice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.notice.CereNoticeDAO;
import com.shop.cereshop.admin.param.notice.NoticeGetAllParam;
import com.shop.cereshop.admin.param.notice.NoticeIdParam;
import com.shop.cereshop.admin.param.notice.NoticeParam;
import com.shop.cereshop.admin.service.notice.CereNoticeService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CereNoticeServiceImpl implements CereNoticeService {

    @Autowired
    private CereNoticeDAO cereNoticeDAO;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(NoticeParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereNotice cereNotice=new CereNotice();
        cereNotice.setCreateTime(time);
        cereNotice.setNoticeTitle(param.getNoticeTitle());
        cereNotice.setNoticeContent(param.getNoticeContent());
        cereNotice.setNoticeType(param.getNoticeType());
        cereNotice.setReceive(param.getReceive());
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeDAO.insert(cereNotice);
    }

    @Override
    public void delete(NoticeIdParam param, CerePlatformUser user) throws CoBusinessException {
        cereNoticeDAO.deleteByPrimaryKey(param.getNoticeId());
    }

    @Override
    public CereNotice getById(Long noticeId) throws CoBusinessException {
        return cereNoticeDAO.selectByPrimaryKey(noticeId);
    }

    @Override
    public Page getAll(NoticeGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CereNotice> list=cereNoticeDAO.getAll(param);
        PageInfo<CereNotice> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CereNotice> getNotices() throws CoBusinessException {
        return cereNoticeDAO.getNotices();
    }
}
