/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopDiscountDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopDiscountDetailDAO extends BaseMapper<CereShopDiscountDetail> {
    int insert(CereShopDiscountDetail record);

    int insertSelective(CereShopDiscountDetail record);

    void insertBatch(@Param("list") List<CereShopDiscountDetail> list);

    void deleteById(@Param("shopDiscountId") Long shopDiscountId);

    List<ToolProduct> findProducts(@Param("shopDiscountId") Long shopDiscountId);

    List<ToolProduct> findDistinctProducts(@Param("shopDiscountId") Long shopDiscountId);

    List<CereShopDiscountDetail> findBySkuId(@Param("skuId") Long skuId,@Param("shopId") Long shopId);

    void updateBatchDiscountPrice(@Param("list") List<CereShopDiscountDetail> list);

    List<CereShopDiscountDetail> findNumberDetails(@Param("orderId") Long orderId,@Param("shopDiscountId") Long shopDiscountId);

    void updateBatch(@Param("list") List<CereShopDiscountDetail> list);

    List<Long> findProductIdList(Long shopDiscountId);

    List<CereShopDiscountDetail> findDetailList(Long shopDiscountId);
}
