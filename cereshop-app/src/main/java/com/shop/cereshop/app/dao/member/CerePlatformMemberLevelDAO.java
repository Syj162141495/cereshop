/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.dao.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CerePlatformMemberLevelDAO extends BaseMapper<CerePlatformMemberLevel> {
    int deleteByPrimaryKey(Long memberLevelId);

    int insert(CerePlatformMemberLevel record);

    int insertSelective(CerePlatformMemberLevel record);

    CerePlatformMemberLevel selectByPrimaryKey(Long memberLevelId);

    int updateByPrimaryKeySelective(CerePlatformMemberLevel record);

    int updateByPrimaryKey(CerePlatformMemberLevel record);

    CerePlatformMemberLevel selectNextLevel(Long memberLevelId);

    CerePlatformMemberLevel selectFirstLevel();
}
