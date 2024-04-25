/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.commons.domain.tool.CereShopOperateDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopOperateDetailService {
    void insertBatch(List<CereShopOperateDetail> list) throws CoBusinessException;

    List<OperateCoupon> findCoupons(Long shopOperateId);
}
