/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.business.page.index.HotSellProduct;
import com.shop.cereshop.business.page.shop.Shop;
import com.shop.cereshop.commons.domain.business.CereBusinessShop;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CerePlatformShopDAO extends BaseMapper<CerePlatformShop> {
    int deleteByPrimaryKey(Long shopId);

    int insert(CerePlatformShop record);

    int insertSelective(CerePlatformShop record);

    CerePlatformShop selectByPrimaryKey(Long shopId);

    int updateByPrimaryKeySelective(CerePlatformShop record);

    int updateByPrimaryKey(CerePlatformShop record);

    CerePlatformShop findByShopName(@Param("shopName") String shopName);

    Shop findShop(@Param("shopId") Long shopId);

    void updateState(CerePlatformShop cerePlatformShop);

    Integer getTotal(@Param("shopId") Long shopId);

    Integer getStayOrders(@Param("shopId") Long shopId);

    Integer getStayAfters(@Param("shopId") Long shopId);

    BigDecimal getMoney(@Param("shopId") Long shopId);

    Integer getVisit(@Param("shopId") Long shopId,@Param("time") String time);

    List<HotSellProduct> getHotProducts(@Param("shopId") Long shopId,@Param("condition") Integer condition);

    CerePlatformShop check(@Param("shopPhone") String shopPhone);

    CerePlatformShop findByPhone(@Param("shopPhone") String shopPhone);

    CerePlatformShop checkShopIdByName(@Param("shopName") String shopName,@Param("shopId") Long shopId);

    CerePlatformShop checkShopIdByPhone(@Param("shopPhone") String shopPhone,@Param("shopId") Long shopId);

    BigDecimal findVisits(@Param("shopId") Long shopId,@Param("condition") Integer condition,@Param("type") Integer type);

    Integer findPayUsers(@Param("productId") Long productId);

    List<CereBusinessShop> findIds();

    Long findShopRoleId(@Param("id") Long id);

    List<CerePlatformShop> selectAll();
}
