/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.price.ShopPrice;
import com.shop.cereshop.business.page.price.ShopPriceDetail;
import com.shop.cereshop.business.param.price.PriceGetAllParam;
import com.shop.cereshop.business.param.price.PriceSaveParam;
import com.shop.cereshop.business.param.price.PriceUpdateParam;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import com.shop.cereshop.commons.domain.tool.CereShopPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopPriceDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long priceId);

    int insert(CereShopPrice record);

    int insertSelective(CereShopPrice record);

    CereShopPrice selectByPrimaryKey(Long priceId);

    int updateByPrimaryKeySelective(CereShopPrice record);

    int updateByPrimaryKey(CereShopPrice record);

    List<CereShopCompose> checkTime(PriceSaveParam param);

    List<CereShopCompose> checkUpdateTime(PriceUpdateParam param);

    ShopPriceDetail getById(@Param("priceId") Long priceId);

    List<ShopPrice> getAll(PriceGetAllParam param);

    List<ShopPriceDetail> getPrices(RenovationParam param);

    List<BigDecimal> findProductPrice(@Param("list") List<CerePriceProduct> list);
}
