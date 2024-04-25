/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.member.CerePlatformMembership;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformMembershipDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long memberId);

    int insert(CerePlatformMembership record);

    int insertSelective(CerePlatformMembership record);

    CerePlatformMembership selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(CerePlatformMembership record);

    int updateByPrimaryKey(CerePlatformMembership record);

    CerePlatformMembership check(@Param("memberId") Long memberId);

    List<CerePlatformMembership> getAll();
}
