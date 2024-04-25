/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.product;

import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereProductClassifyDAO extends BaseMapper<CereProductClassify> {
    int deleteByPrimaryKey(Long classifyId);

    int insert(CereProductClassify record);

    int insertSelective(CereProductClassify record);

    CereProductClassify selectByPrimaryKey(Long classifyId);

    int updateByPrimaryKeySelective(CereProductClassify record);

    int updateByPrimaryKey(CereProductClassify record);

    List<CereProductClassify> getClassifySelect(@Param("classifyPid") Long classifyPid);

    CereProductClassify findByHierarchy(@Param("hierarchy") String hierarchy);

    List<Classify> findAll();

    List<Classify> findChilds();

    List<CereProductClassify> selectAll();
}
