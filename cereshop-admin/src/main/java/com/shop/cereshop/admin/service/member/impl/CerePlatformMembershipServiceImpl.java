/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.member.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.member.CerePlatformMembershipDAO;
import com.shop.cereshop.admin.param.member.MembershipGetByIdParam;
import com.shop.cereshop.admin.param.member.MembershipSaveParam;
import com.shop.cereshop.admin.param.member.MembershipUpdateParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.member.CerePlatformMembershipService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.domain.member.CerePlatformMembership;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CerePlatformMembershipServiceImpl implements CerePlatformMembershipService {

    @Autowired
    private CerePlatformMembershipDAO cerePlatformMembershipDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(MembershipSaveParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformMembership membership=new CerePlatformMembership();
        membership.setMemberName(param.getMemberName());
        membership.setMemberIcon(param.getMemberIcon());
        membership.setMemberReason(param.getMemberReason());
        membership.setCreateTime(time);
        cerePlatformMembershipDAO.insert(membership);
        //新增日志
        cerePlatformLogService.addLog(user,"会员管理","平台端操作","添加会员权益",String.valueOf(membership.getMemberId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(MembershipUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformMembership membership=new CerePlatformMembership();
        membership.setMemberId(param.getMemberId());
        membership.setMemberName(param.getMemberName());
        membership.setMemberIcon(param.getMemberIcon());
        membership.setMemberReason(param.getMemberReason());
        membership.setUpdateTime(time);
        cerePlatformMembershipDAO.updateByPrimaryKeySelective(membership);
        //新增日志
        cerePlatformLogService.addLog(user,"会员管理","平台端操作","编辑会员权益",String.valueOf(membership.getMemberId()),time);
    }

    @Override
    public CerePlatformMembership getById(MembershipGetByIdParam param) throws CoBusinessException {
        return cerePlatformMembershipDAO.selectByPrimaryKey(param.getMemberId());
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(MembershipGetByIdParam param, CerePlatformUser user) throws CoBusinessException {
        //校验该会员权益是否关联会员等级
        CerePlatformMembership membership=cerePlatformMembershipDAO.check(param.getMemberId());
        if(membership!=null){
            throw new CoBusinessException(CoReturnFormat.MEMBER_SHIP_NOT_DELETE);
        }
        cerePlatformMembershipDAO.deleteByPrimaryKey(param.getMemberId());
        //新增日志
        cerePlatformLogService.addLog(user,"会员管理","平台端操作","删除会员权益",String.valueOf(param.getMemberId()),TimeUtils.yyMMddHHmmss());
    }

    @Override
    public Page getAll(PageParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CerePlatformMembership> list=cerePlatformMembershipDAO.getAll();
        PageInfo<CerePlatformMembership> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
