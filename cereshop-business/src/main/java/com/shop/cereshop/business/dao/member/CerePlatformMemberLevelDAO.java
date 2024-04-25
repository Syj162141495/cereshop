/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CerePlatformMemberLevelDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long memberLevelId);

    int insert(CerePlatformMemberLevel record);

    int insertSelective(CerePlatformMemberLevel record);

    CerePlatformMemberLevel selectByPrimaryKey(Long memberLevelId);

    int updateByPrimaryKeySelective(CerePlatformMemberLevel record);

    int updateByPrimaryKey(CerePlatformMemberLevel record);

    List<CerePlatformMemberLevel> findAll();
}
