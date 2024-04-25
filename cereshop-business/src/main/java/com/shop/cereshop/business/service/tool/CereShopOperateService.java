/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.operate.OperateData;
import com.shop.cereshop.business.page.tool.ShopOperateDetail;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopOperateService {
    void saveOperate(ShopOperateSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    Page selectCoupon(OperateCouponParam param) throws CoBusinessException,Exception;

    List<CereShopOperate> findAll();

    CereShopCrowd findCrowd(Long shopCrowdId);

    List<String> findUserByCondition(CrowdCondition condition);

    List<String> findNoBuy(CrowdCondition condition);

    List<String> findNoOrder(CrowdCondition condition);

    List<String> findNoVisit(CrowdCondition condition);

    List<OperateCoupon> findCouponsById(Long shopOperateId);

    void insertBatchNotice(List<CereNotice> list) throws CoBusinessException;

    void insertBatchBuyerCoupon(List<CereBuyerShopCoupon> buyerShopCoupons) throws CoBusinessException;

    void update(ShopOperateUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void updateState(CereShopOperate cereShopOperate) throws CoBusinessException;

    CereShopOperate findById(Long shopOperateId);

    void delete(List<Long> ids, CerePlatformBusiness user) throws CoBusinessException;

    ShopOperateDetail getById(Long shopOperateId) throws CoBusinessException;

    Page getAll(ShopOperateGetAllParam param) throws CoBusinessException;

    List<OperateData> getDatas(Long shopOperateId) throws CoBusinessException,Exception;

    List<OperateCoupon> findCouponDetails(Long shopOperateId);

    List<Long> findAlreadyCoupon(List<Long> ids,Long shopOperateId);

    List<CereShopOperate> findShopOperate();

    void updateOperateEndState(List<CereShopOperate> operates, String time) throws CoBusinessException;
}
