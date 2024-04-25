/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.product;

import com.shop.cereshop.business.page.product.Sku;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.product.DeleteSkuParam;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    void insertBatch(@Param("list") List<CereProductSku> list);

    void deleteByIds(@Param("ids") List<Long> ids);

    void deleteByProductId(@Param("productId") Long productId);

    List<Sku> findByProductId(@Param("productId") Long productId);

    Integer findVolumeByProductId(@Param("productId") Long productId);

    int findStockNumber(@Param("skuId") Long skuId);

    List<ToolProduct> getToolSkus(@Param("productId") Long productId);

    List<CereProductSku> findStockNumberByOrderId(@Param("orderId") Long orderId);

    int findStockNumberBySkuId(@Param("skuId") Long skuId);

    void updateBatchSkus(@Param("list") List<CereProductSku> list);
}
