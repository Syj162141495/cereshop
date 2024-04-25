/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.shop.cereshop.business.dao.tool.CereShopOperateDetailDAO;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.service.tool.CereShopOperateDetailService;
import com.shop.cereshop.commons.domain.tool.CereShopOperateDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereShopOperateDetailServiceImpl implements CereShopOperateDetailService {

    @Autowired
    private CereShopOperateDetailDAO cereShopOperateDetailDAO;

    @Override
    public void insertBatch(List<CereShopOperateDetail> list) throws CoBusinessException {
        cereShopOperateDetailDAO.insertBatch(list);
    }

    @Override
    public List<OperateCoupon> findCoupons(Long shopOperateId) {
        return cereShopOperateDetailDAO.findCoupons(shopOperateId);
    }
}
