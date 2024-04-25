/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop;

import com.shop.cereshop.business.page.group.GroupDetail;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.group.*;
import com.shop.cereshop.business.param.product.ProductGetGroupParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.CereShopGroup;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopGroupService {
    List<CereShopGroup> getGroupSelect(ProductGetGroupParam param) throws CoBusinessException;

    void save(ShopGroupSaveParam group, CerePlatformBusiness user) throws CoBusinessException;

    void update(ShopGroupUpdateParam group, CerePlatformBusiness user) throws CoBusinessException;

    void delete(GroupDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    GroupDetail getById(Long shopGroupId) throws CoBusinessException;

    Page getAll(GroupGetAllParam param) throws CoBusinessException;

    Page getProducts(GroupProductParam param) throws CoBusinessException;

    List<Product> getProductIds(ShopGroupSaveParam group) throws CoBusinessException;
}
