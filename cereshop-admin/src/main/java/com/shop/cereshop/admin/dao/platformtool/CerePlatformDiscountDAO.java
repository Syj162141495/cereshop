/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.platformtool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.admin.page.canvas.ToolProduct;
import com.shop.cereshop.admin.page.discount.Discount;
import com.shop.cereshop.admin.page.discount.DiscountShop;
import com.shop.cereshop.admin.page.discount.DiscountShopProduct;
import com.shop.cereshop.admin.page.seckill.Seckill;
import com.shop.cereshop.admin.page.seckill.SeckillShop;
import com.shop.cereshop.admin.page.seckill.SeckillShopProduct;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.param.discount.*;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CerePlatformDiscountDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long discountId);

    int insert(CerePlatformDiscount record);

    int insertSelective(CerePlatformDiscount record);

    CerePlatformDiscount selectByPrimaryKey(Long discountId);

    int updateByPrimaryKeySelective(CerePlatformDiscount record);

    int updateByPrimaryKey(CerePlatformDiscount record);

    List<CerePlatformDiscount> checkTime(DiscountSaveParam param);

    List<CerePlatformDiscount> checkUpdateTime(DiscountUpdateParam param);

    Integer findShops(@Param("discountId") Long discountId);

    Integer findProducts(@Param("discountId") Long discountId,@Param("shopId") Long shopId);

    BigDecimal findTotal(@Param("discountId") Long discountId, @Param("shopId") Long shopId);

    Integer findPays(@Param("discountId") Long discountId);

    Integer findVisit (@Param("discountId") Long discountId,@Param("shopId") Long shopId);

    List<ShopDataDetail> findShopDatas(@Param("discountId") Long discountId);

    Integer findOrders(@Param("discountId") Long discountId,@Param("shopId") Long shopId);

    Integer findSubmit(@Param("discountId") Long discountId,@Param("shopId") Long shopId);

    List<Discount> getAll(DiscountGetAllParam param);

    List<DiscountShop> getShops(DiscountShopParam param);

    Integer findExamines(@Param("discountId") Long discountId,@Param("shopId") Long shopId);

    List<DiscountShopProduct> getProducts(DiscountGetProductsParam param);

    List<CereActivitySign> findErrorSign(@Param("discountId") Long discountId);

    List<CereActivitySign> findByDiscount(@Param("discountId") Long discountId);

    List<CanvasPlatformDiscount> getMinDiscount(RenovationParam param);

    List<ToolProduct> findDistinctProducts(@Param("discountId") Long discountId);

    List<Long> findProductIdList(Long discountId);
}
