/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product;

import com.shop.cereshop.business.page.canvas.CanvasProductParam;
import com.shop.cereshop.business.page.member.ProductMember;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.page.product.ShopProduct;
import com.shop.cereshop.business.param.product.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface CereShopProductService {
    void save(ProductSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(ProductUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(ProductDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopProduct getById(Long productId) throws CoBusinessException;

    void start(ProductUpDownParam param, CerePlatformBusiness user) throws CoBusinessException;

    Page getAll(ProductGetAllParam param) throws CoBusinessException;

    void importProduct(Workbook wb) throws CoBusinessException,Exception;

    CereShopProduct checkName(Long shopId, Long classifyId, String productName);

    Page getProducts(CanvasProductParam param) throws CoBusinessException;

    Page getShops(CanvasProductParam param) throws CoBusinessException;

    List<Long> findAllZeroStockNumber();

    void updateBatchShelveState(List<Long> ids, String time) throws CoBusinessException;

    List<CereShopProduct> selectAll();

    List<ProductMember> getProductMembers(Long productId) throws CoBusinessException;

    void setProductMember(ProductMemberParam param) throws CoBusinessException;

    void triggerCacheUpdate(Long productId);

    void updateActivitySkuStock(Long skuId, Integer activityType, Integer activityStatus,
                                Integer activityStock, Integer totalStock, long millSeconds);
}
