/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.dict;

import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformDictDAO extends BaseMapper<CerePlatformDict> {
    int deleteByPrimaryKey(Long dictId);

    int insert(CerePlatformDict record);

    int insertSelective(CerePlatformDict record);

    CerePlatformDict selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(CerePlatformDict record);

    int updateByPrimaryKey(CerePlatformDict record);

    Integer findExpress();

    List<CerePlatformDict> findExpressSelect(@Param("id") Long id);

    List<CerePlatformDict> getSelect(@Param("dictName") String dictName);
}
