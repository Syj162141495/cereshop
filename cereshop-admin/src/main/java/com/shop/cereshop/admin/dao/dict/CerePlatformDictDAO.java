/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.dict;

import com.shop.cereshop.admin.param.dict.DictGetAllParam;
import com.shop.cereshop.admin.param.dict.DictGetChildsParam;
import com.shop.cereshop.admin.param.dict.DictUpdateParam;
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

    CerePlatformDict getById(@Param("dictId") Long dictId);

    List<CerePlatformDict> getAll(DictGetAllParam param);

    List<CerePlatformDict> getChilds(DictGetChildsParam param);

    List<CerePlatformDict> getSelect(@Param("dictName") String dictName);

    CerePlatformDict checkName(@Param("dictName") String dictName);

    CerePlatformDict checkNameAndId(DictUpdateParam param);

    String findByCompany(@Param("express") String express,@Param("dictPid") Long dictPid);

    String findNameById(@Param("dictId") Long dictId);

    void deleteByPid(@Param("dictId") Long dictId);
}
