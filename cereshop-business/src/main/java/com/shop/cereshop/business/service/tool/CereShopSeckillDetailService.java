/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopSeckillDetailService {
    void insertBatch(List<CereShopSeckillDetail> details) throws CoBusinessException;

    void deleteById(Long shopSeckillId) throws CoBusinessException;

    List<ToolProduct> findProducts(Long shopSeckillId);

    List<ToolProduct> findDistinctProducts(Long shopSeckillId);

    List<CereShopSeckillDetail> findBySkuId(Long skuId, Long shopId);

    void updateBatchSeckillPrice(List<CereShopSeckillDetail> details) throws CoBusinessException;

    List<CereShopSeckillDetail> findNumberDetails(Long orderId, Long shopSeckillId);

    void updateBatch(List<CereShopSeckillDetail> seckillDetails) throws CoBusinessException;

    List<Long> findProductIdList(Long shopSeckillId);

    List<CereShopSeckillDetail> findDetailList(Long shopSeckillId);
}
