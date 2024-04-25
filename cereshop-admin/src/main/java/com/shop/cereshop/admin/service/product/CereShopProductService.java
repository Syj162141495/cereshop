/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product;

import com.shop.cereshop.admin.page.product.ProductDetail;
import com.shop.cereshop.admin.page.product.ProductExamineParam;
import com.shop.cereshop.admin.page.product.ProductUpdateParam;
import com.shop.cereshop.admin.param.product.CanvasAdminProductParam;
import com.shop.cereshop.admin.param.product.ProductGetAllParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopProductService {
    Page getProducts(CanvasAdminProductParam param) throws CoBusinessException,Exception;

    Page getShops(CanvasAdminProductParam param) throws CoBusinessException;

    Page getAll(ProductGetAllParam param) throws CoBusinessException;

    ProductDetail getById(Long productId) throws CoBusinessException;

    void examine(ProductExamineParam param, CerePlatformUser user) throws CoBusinessException;

    void setFictitious(ProductUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void forced(ProductUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void unShelveByShopId(Long shopId, CerePlatformUser user) throws CoBusinessException;

    void triggerCacheUpdate(Long productId);

    void updateActivityProductStock(Long productId, Integer activityType, Integer activityStatus,
                                    Integer activityStock, Integer totalStock, long millSeconds);
}
