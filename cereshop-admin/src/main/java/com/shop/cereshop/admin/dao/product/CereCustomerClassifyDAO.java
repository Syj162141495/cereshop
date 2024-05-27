/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.product.CustomerClassify;
import com.shop.cereshop.commons.domain.product.CereCustomerClassify;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import com.shop.cereshop.commons.domain.product.Classify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereCustomerClassifyDAO extends BaseMapper<CereCustomerClassify> {

    CereCustomerClassify selectByPrimaryKey(Long classifyId);

    int deleteByPrimaryKey(Long classifyId);

    int insert(CereCustomerClassify record);

    int insertSelective(CereCustomerClassify record);

    int updateByPrimaryKeySelective(CereCustomerClassify record);

    int updateByPrimaryKey(CereCustomerClassify record);

    CereCustomerClassify getById(@Param("classifyId") Long classifyId);

    List<CereShopProduct> checkProduct(@Param("classifyId") Long classifyId);

    List<CustomerClassify> findByPid(@Param("classifyId") Long classifyId);

    void updateBatchLevelHierarchy(@Param("list") List<CereCustomerClassify> list);

    void deleteByIds(@Param("ids") List<Long> ids);
}
