/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool;

import com.shop.cereshop.admin.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.admin.page.discount.DiscountData;
import com.shop.cereshop.admin.page.seckill.SeckillData;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.param.discount.*;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformDiscountService {

    void save(DiscountSaveParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void update(DiscountUpdateParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void delete(Long discountId, CerePlatformUser user) throws CoBusinessException;

    void stop(Long discountId, CerePlatformUser user) throws CoBusinessException;

    CerePlatformDiscount getById(Long discountId) throws CoBusinessException;

    DiscountData getData(DiscountGetByIdParam param) throws CoBusinessException;

    Page getAll(DiscountGetAllParam param) throws CoBusinessException;

    Page getShops(DiscountShopParam param) throws CoBusinessException;

    Page getProducts(DiscountGetProductsParam param) throws CoBusinessException;

    List<ShopDataDetail> findExcel(DiscountGetByIdParam param) throws CoBusinessException;

    void updateDiscount(CerePlatformDiscount cerePlatformDiscount) throws CoBusinessException;

    List<CereActivitySign> findErrorSign(Long discountId);

    List<CereActivitySign> findByDiscount(Long discountId);

    List<CanvasPlatformDiscount> getMinDiscount(RenovationParam param) throws CoBusinessException;

    List<Long> findProductIdList(Long discountId);
}
