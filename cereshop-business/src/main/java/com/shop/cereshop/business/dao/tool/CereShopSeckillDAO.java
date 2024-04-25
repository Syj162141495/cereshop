/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.CouponProduct;
import com.shop.cereshop.business.page.tool.ShopSeckill;
import com.shop.cereshop.business.page.tool.ShopSeckillDetail;
import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.ShopSeckillGetAllParam;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopSeckillDAO extends BaseMapper<CereShopSeckill> {
    int deleteByPrimaryKey(Long shopSeckillId);

    int insert(CereShopSeckill record);

    int insertSelective(CereShopSeckill record);

    CereShopSeckill selectByPrimaryKey(Long shopSeckillId);

    int updateByPrimaryKeySelective(CereShopSeckill record);

    int updateByPrimaryKey(CereShopSeckill record);

    ShopSeckillDetail getById(@Param("shopSeckillId") Long shopSeckillId);

    List<ShopSeckill> getAll(ShopSeckillGetAllParam param);

    List<ToolProduct> getProducts(@Param("shopId") Long shopId);

    List<CouponProduct> findDataProducts(@Param("shopSeckillId") Long shopSeckillId,@Param("shopId") Long shopId);

    String findSeckillName(@Param("shopSeckillId") Long shopSeckillId);

    int findVisit(@Param("shopSeckillId") Long shopSeckillId);

    Integer findPays(@Param("shopSeckillId") Long shopSeckillId,@Param("shopId") Long shopId);

    BigDecimal findTotal(@Param("shopSeckillId") Long shopSeckillId,@Param("shopId") Long shopId);

    List<CereShopSeckill> checkTime(CereShopSeckill cereShopSeckill);

    List<Long> checkOtherWork(@Param("ids") List<Long> ids, @Param("startTime") String startTime,
                                 @Param("endTime") String endTime, @Param("shopId") Long shopId);

    List<Long> checkOtherDiscount(@Param("ids") List<Long> ids,@Param("startTime") String startTime,
                                  @Param("endTime") String endTime,@Param("shopId") Long shopId);

    void updateState(CereShopSeckill cereShopSeckill);

    List<ShopSeckillDetail> getSeckills(RenovationParam param);

    List<CereShopSeckill> checkUpdateTime(CereShopSeckill cereShopSeckill);

    void updateSeckillEndState(@Param("list") List<CereShopSeckill> list,@Param("time") String time);
}
