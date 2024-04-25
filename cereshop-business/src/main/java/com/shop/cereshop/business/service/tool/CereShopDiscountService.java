/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ShopDiscountData;
import com.shop.cereshop.business.page.tool.ShopDiscountDetail;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopDiscountService {
    void save(ShopDiscountSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ShopDiscountUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(Long shopDiscountId, CerePlatformBusiness user) throws CoBusinessException;

    void stop(Long shopDiscountId, CerePlatformBusiness user) throws CoBusinessException;

    ShopDiscountDetail getById(Long shopDiscountId) throws CoBusinessException;

    Page getAll(ShopDiscountGetAllParam param) throws CoBusinessException;

    Page getProducts(ToolProductParam param) throws CoBusinessException;

    ShopDiscountData getData(ShopDiscountGetByIdParam param, Long shopId) throws CoBusinessException;

    CereShopDiscount findById(Long shopDiscountId);

    void updateState(CereShopDiscount cereShopDiscount) throws CoBusinessException;

    List<ShopDiscountDetail> getDiscounts(RenovationParam param) throws CoBusinessException;

    List<Long> checkOtherSeckill(List<Long> productIs, String startTime, String endTime, Long shopId);

    List<CereShopDiscount> findShopDiscount();

    void updateDiscountEndState(List<CereShopDiscount> discounts, String time) throws CoBusinessException;
}
