/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopSeckillDetailDAO extends BaseMapper<CereShopSeckillDetail> {
    int insert(CereShopSeckillDetail record);

    int insertSelective(CereShopSeckillDetail record);

    void insertBatch(@Param("list") List<CereShopSeckillDetail> list);

    void deleteById(@Param("shopSeckillId") Long shopSeckillId);

    List<ToolProduct> findProducts(@Param("shopSeckillId") Long shopSeckillId);

    List<ToolProduct> findDistinctProducts(@Param("shopSeckillId") Long shopSeckillId);

    List<CereShopSeckillDetail> findBySkuId(@Param("skuId") Long skuId,@Param("shopId") Long shopId);

    void updateBatchSeckillPrice(@Param("list") List<CereShopSeckillDetail> list);

    List<CereShopSeckillDetail> findNumberDetails(@Param("orderId") Long orderId,@Param("shopSeckillId") Long shopSeckillId);

    void updateBatch(@Param("list") List<CereShopSeckillDetail> list);

    List<Long> findProductIdList(Long shopSeckillId);

    List<CereShopSeckillDetail> findDetailList(Long shopSeckillId);
}
