/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWorkDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopGroupWorkDetailService {
    void insertBatch(List<CereShopGroupWorkDetail> details) throws CoBusinessException;

    void deleteById(Long shopGroupWorkId) throws CoBusinessException;

    List<ToolProduct> findProducts(Long shopGroupWorkId);

    List<ToolProduct> findDistinctProducts(Long shopGroupWorkId);

    List<Long> findProductIdList(Long shopGroupWorkId);
}
