/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.order;

import com.shop.cereshop.admin.page.order.ShopOrder;
import com.shop.cereshop.admin.param.notice.NoticeGetAllParam;
import com.shop.cereshop.admin.param.order.OrderGetAllParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopOrderService {
    void updateAfterState(CereShopOrder cereShopOrder) throws CoBusinessException;

    void insert(CereShopOrder order) throws CoBusinessException;

    void updateState(CereShopOrder cereShopOrder) throws CoBusinessException;

    void updateBatchStock(List<CereProductSku> skus) throws CoBusinessException;

    Page getAll(OrderGetAllParam param) throws CoBusinessException;

    ShopOrder getById(Long orderId) throws CoBusinessException;
}
