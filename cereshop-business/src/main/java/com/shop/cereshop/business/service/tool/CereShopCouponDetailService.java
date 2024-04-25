/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopCouponDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopCouponDetailService {
    void insertBatch(List<CereShopCouponDetail> collect) throws CoBusinessException;

    void deleteById(Long shopCouponId) throws CoBusinessException;

    List<ToolProduct> findProducts(Long shopCouponId);
}
