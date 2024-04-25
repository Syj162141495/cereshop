/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity;

import com.shop.cereshop.admin.page.finance.BondCount;
import com.shop.cereshop.admin.param.finance.BondParam;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereActivitySignService {
    void update(CereActivitySign cereActivitySign) throws CoBusinessException;

    CereActivitySign findBySignId(Long signId);

    BondCount getAllBond(BondParam param) throws CoBusinessException;

    void refundBond(String[] split, String transaction_id, String orderNo,int signType) throws CoBusinessException;
}
