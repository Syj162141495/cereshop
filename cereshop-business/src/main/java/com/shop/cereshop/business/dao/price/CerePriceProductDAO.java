/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.canvas.CanvasProduct;
import com.shop.cereshop.business.page.canvas.CanvasProductParam;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePriceProductDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CerePriceProduct record);

    int insertSelective(CerePriceProduct record);

    void insertBatch(@Param("list") List<CerePriceProduct> list);

    void deleteByPriceId(@Param("priceId") Long priceId);

    List<ComposeProduct> findProducts(@Param("priceId") Long priceId);

    List<CanvasProduct> getPriceProducts(CanvasProductParam param);
}
