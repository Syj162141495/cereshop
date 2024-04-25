/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.activity.CerePlatformActivityDAO;
import com.shop.cereshop.business.page.canvas.CanvasCoupon;
import com.shop.cereshop.business.param.canvas.CanvasCouponParam;
import com.shop.cereshop.business.service.activity.CerePlatformActivityService;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformActivityServiceImpl implements CerePlatformActivityService {

    @Autowired
    private CerePlatformActivityDAO cerePlatformActivityDAO;

    @Override
    public Page getCoupons(CanvasCouponParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CanvasCoupon> list=cerePlatformActivityDAO.getCoupons(param);
        PageInfo<CanvasCoupon> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CerePlatformActivity> findPlatformCoupon() {
        return cerePlatformActivityDAO.findPlatformCoupon();
    }

    @Override
    public void updateActivityEndState(List<CerePlatformActivity> activities, String time) {
        cerePlatformActivityDAO.updateActivityEndState(activities,time);
    }
}
