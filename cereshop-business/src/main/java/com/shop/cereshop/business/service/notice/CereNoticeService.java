/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.notice;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereNoticeService {
    void addOperate(Long shopCrowdId, List<OperateCoupon> coupons,Long shopId,Long shopOperateId) throws CoBusinessException;

    void insert(CereNotice cereNotice) throws CoBusinessException;
}
