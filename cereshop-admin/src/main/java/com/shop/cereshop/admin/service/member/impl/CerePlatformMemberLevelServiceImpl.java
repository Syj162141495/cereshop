/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.member.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.member.CerePlatformMemberLevelDAO;
import com.shop.cereshop.admin.page.member.MemberLevel;
import com.shop.cereshop.admin.param.member.MemberLevelSaveParam;
import com.shop.cereshop.admin.param.member.MemberLevelUpdateParam;
import com.shop.cereshop.admin.param.member.MemberLevelgetByIdParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ConnectionReuseStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CerePlatformMemberLevelServiceImpl implements CerePlatformMemberLevelService {

    @Autowired
    private CerePlatformMemberLevelDAO cerePlatformMemberLevelDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(MemberLevelSaveParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        if (cerePlatformMemberLevelDAO.countMemberLevel(StringUtils.trim(param.getMemberLevelName()), param.getGrowth(), null) > 0) {
            throw new CoBusinessException(CoReturnFormat.EXIST_MEMBER_LEVEL);
        }
        if (param.getGrowth() != 0 && cerePlatformMemberLevelDAO.countByGrowth(0) == 0) {
            throw new CoBusinessException(CoReturnFormat.NEED_ZERO_GROWTH);
        }
        CerePlatformMemberLevel memberLevel=new CerePlatformMemberLevel();
        memberLevel.setMemberLevelName(param.getMemberLevelName());
        memberLevel.setMemberLevelIcon(param.getMemberLevelIcon());
        memberLevel.setMemberLevelBackground(param.getMemberLevelBackground());
        memberLevel.setMemberIds(EmptyUtils.getImage(param.getIds()));
        memberLevel.setGrowth(param.getGrowth());
        memberLevel.setMemberLevelReason(param.getMemberLevelReason());
        memberLevel.setCreateTime(time);
        cerePlatformMemberLevelDAO.insert(memberLevel);
        //新增日志
        cerePlatformLogService.addLog(user,"会员管理","平台端操作","添加会员等级",String.valueOf(memberLevel.getMemberLevelId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(MemberLevelUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        if (cerePlatformMemberLevelDAO.countMemberLevel(StringUtils.trim(param.getMemberLevelName()), param.getGrowth(), param.getMemberLevelId()) > 0) {
            throw new CoBusinessException(CoReturnFormat.EXIST_MEMBER_LEVEL);
        }
        CerePlatformMemberLevel oldLevel = cerePlatformMemberLevelDAO.selectByPrimaryKey(param.getMemberLevelId());
        if (oldLevel == null) {
            throw new CoBusinessException(CoReturnFormat.PARAM_INVALID);
        }
        if (oldLevel.getGrowth() == 0 && param.getGrowth() != 0) {
            throw new CoBusinessException(CoReturnFormat.NEED_ZERO_GROWTH);
        }
        CerePlatformMemberLevel memberLevel=new CerePlatformMemberLevel();
        memberLevel.setMemberLevelId(param.getMemberLevelId());
        memberLevel.setMemberLevelName(param.getMemberLevelName());
        memberLevel.setMemberLevelIcon(param.getMemberLevelIcon());
        memberLevel.setMemberLevelBackground(param.getMemberLevelBackground());
        memberLevel.setMemberIds(EmptyUtils.getImage(param.getIds()));
        memberLevel.setGrowth(param.getGrowth());
        memberLevel.setMemberLevelReason(param.getMemberLevelReason());
        memberLevel.setUpdateTime(time);
        cerePlatformMemberLevelDAO.updateByPrimaryKeySelective(memberLevel);
        //新增日志
        cerePlatformLogService.addLog(user,"会员管理","平台端操作","编辑会员等级",String.valueOf(memberLevel.getMemberLevelId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(MemberLevelgetByIdParam param, CerePlatformUser user) throws CoBusinessException {
        //校验该会员等级是否关联待开始和进行中状态的场景营销活动
        CerePlatformMemberLevel memberLevel=cerePlatformMemberLevelDAO.check(param.getMemberLevelId());
        if(memberLevel!=null){
            throw new CoBusinessException(CoReturnFormat.MEMBER_LEVEL_NOT_DELETE);
        }
        //查询当前是否还有其他会员等级数据
        List<Long> ids=cerePlatformMemberLevelDAO.findOther(param.getMemberLevelId());
        if(EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.MEMBER_LEVEL_MUST_ONE);
        }
        CerePlatformMemberLevel oldLevel = cerePlatformMemberLevelDAO.selectByPrimaryKey(param.getMemberLevelId());
        if (oldLevel != null && oldLevel.getGrowth() == 0) {
            throw new CoBusinessException(CoReturnFormat.NEED_ZERO_GROWTH);
        }
        cerePlatformMemberLevelDAO.deleteByPrimaryKey(param.getMemberLevelId());
        //新增日志
        cerePlatformLogService.addLog(user,"会员管理","平台端操作","删除会员等级",String.valueOf(param.getMemberLevelId()),TimeUtils.yyMMddHHmmss());
    }

    @Override
    public MemberLevel getById(MemberLevelgetByIdParam param) throws CoBusinessException {
        MemberLevel memberLevel=cerePlatformMemberLevelDAO.getById(param.getMemberLevelId());
        if(memberLevel!=null){
            memberLevel.setIds(EmptyUtils.getImages(memberLevel.getMemberIds()));
        }
        return memberLevel;
    }

    @Override
    public Page getAll(PageParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<MemberLevel> list=cerePlatformMemberLevelDAO.getAll();
        PageInfo<MemberLevel> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
