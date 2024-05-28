/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.product.ProductProviderClassify;
import com.shop.cereshop.commons.domain.product.CereProductProviderClassify;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereProductProviderClassifyDAO extends BaseMapper<CereProductProviderClassify> {

    CereProductProviderClassify selectByPrimaryKey(Long classifyId);

    int deleteByPrimaryKey(Long classifyId);

    int insert(CereProductProviderClassify record);

    int insertSelective(CereProductProviderClassify record);

    int updateByPrimaryKeySelective(CereProductProviderClassify record);

    int updateByPrimaryKey(CereProductProviderClassify record);

    CereProductProviderClassify getById(@Param("classifyId") Long classifyId);

    List<CereShopProduct> checkProduct(@Param("classifyId") Long classifyId);

    List<ProductProviderClassify> findByPid(@Param("classifyId") Long classifyId);

    void updateBatchLevelHierarchy(@Param("list") List<CereProductProviderClassify> list);

    void deleteByIds(@Param("ids") List<Long> ids);
}
