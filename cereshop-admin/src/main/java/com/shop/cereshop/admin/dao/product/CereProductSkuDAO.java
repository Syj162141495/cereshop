/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.product;

import com.shop.cereshop.admin.page.product.Sku;
import com.shop.cereshop.admin.page.product.SkuNameParam;
import com.shop.cereshop.admin.page.product.SkuNameValueParam;
import com.shop.cereshop.admin.page.product.SkuValueParam;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.product.CereSkuName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereProductSkuDAO extends BaseMapper<CereProductSku> {
    int deleteByPrimaryKey(Long skuId);

    int insert(CereProductSku record);

    int insertSelective(CereProductSku record);

    CereProductSku selectByPrimaryKey(Long skuId);

    int updateByPrimaryKeySelective(CereProductSku record);

    int updateByPrimaryKey(CereProductSku record);

    int findStockNumber(@Param("skuId") Long skuId);

    List<Sku> findByProductId(@Param("productId") Long productId);

    List<SkuNameParam> findNameByProductId(@Param("productId") Long productId);

    List<SkuValueParam> findByName(@Param("skuName") String skuName,@Param("productId") Long productId);

    CereSkuName findValueByProductId(@Param("productId") Long productId);

    List<SkuNameValueParam> findBySkuId(@Param("skuId") Long skuId);
}
