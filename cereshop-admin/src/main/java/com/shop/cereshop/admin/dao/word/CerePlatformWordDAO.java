/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.word;

import com.shop.cereshop.admin.param.word.WordParam;
import com.shop.cereshop.commons.domain.word.CerePlatformWord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface CerePlatformWordDAO extends BaseMapper<CerePlatformWord> {
    int deleteByPrimaryKey(Long wordId);

    int insert(CerePlatformWord record);

    int insertSelective(CerePlatformWord record);

    CerePlatformWord selectByPrimaryKey(Long wordId);

    int updateByPrimaryKeySelective(CerePlatformWord record);

    int updateByPrimaryKey(CerePlatformWord record);

    CerePlatformWord checkWord(@Param("keyWord") String keyWord,@Param("wordId") Long wordId);

    List<CerePlatformWord> getAll(WordParam param);

    void updateState(@Param("state") Integer state);
}
