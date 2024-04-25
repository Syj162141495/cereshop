/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.product;

import com.shop.cereshop.business.param.product.DeleteSkuParam;
import com.shop.cereshop.business.param.product.SkuNameParam;
import com.shop.cereshop.business.param.product.SkuNameValueParam;
import com.shop.cereshop.business.param.product.SkuValueParam;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.product.CereSkuName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereSkuNameDAO extends BaseMapper<CereSkuName> {
    int deleteByPrimaryKey(@Param("skuId") Long skuId);

    int insert(CereSkuName cereSkuName);

    int insertSelective(CereSkuName record);

    int updateByPrimaryKeySelective(CereSkuName record);

    int updateByPrimaryKey(CereSkuName record);

    void insertBatch(@Param("list") List<CereSkuName> list);

    void deleteByIds(@Param("ids") List<Long> ids);

    void deleteByProductId(@Param("productId") Long productId);

    List<SkuNameValueParam> findBySkuId(@Param("skuId") Long skuId);

    List<SkuNameParam> findNameBySkuId(@Param("skuId") Long skuId);

    List<SkuValueParam> findByNameAndSkuId(@Param("skuId") Long skuId, @Param("skuName") String skuName);

    CereSkuName findValueBySkuId(@Param("skuId") Long skuId);

    List<SkuNameParam> findNameByProductId(@Param("productId") Long productId);

    List<SkuValueParam> findByName(@Param("skuName") String skuName,@Param("productId") Long productId);

    CereSkuName findValueByProductId(@Param("productId") Long productId);

    List<Long> findDeleteSkuIds(@Param("list") List<DeleteSkuParam> list,@Param("productId") Long productId);
}
