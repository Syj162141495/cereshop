/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopDiscountDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopDiscountDetailService {
    void insertBatch(List<CereShopDiscountDetail> details) throws CoBusinessException;

    void deleteById(Long shopDiscountId) throws CoBusinessException;

    List<ToolProduct> findProducts(Long shopDiscountId);

    List<ToolProduct> findDistinctProducts(Long shopDiscountId);

    List<CereShopDiscountDetail> findBySkuId(Long skuId, Long shopId);

    void updateBatchDiscountPrice(List<CereShopDiscountDetail> details) throws CoBusinessException;

    List<CereShopDiscountDetail> findNumberDetails(Long orderId, Long shopDiscountId);

    void updateBatch(List<CereShopDiscountDetail> discountDetails) throws CoBusinessException;

    List<Long> findProductIdList(Long shopDiscountId);

    List<CereShopDiscountDetail> findDetailList(Long shopDiscountId);
}
