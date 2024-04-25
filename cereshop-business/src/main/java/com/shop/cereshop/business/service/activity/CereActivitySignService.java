/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity;

import com.shop.cereshop.business.page.activity.ActivityData;
import com.shop.cereshop.business.page.activity.ActivitySignDetail;
import com.shop.cereshop.business.page.activity.BondState;
import com.shop.cereshop.business.page.finance.BondCount;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.activity.ActivitiSignGetAllParam;
import com.shop.cereshop.business.param.activity.ActivityGetDataParam;
import com.shop.cereshop.business.param.activity.ActivitySignGetProductParam;
import com.shop.cereshop.business.param.activity.ActivitySignSaveParam;
import com.shop.cereshop.business.param.finance.BondParam;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.CereShopGroup;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereActivitySignService {
    Page getAll(ActivitiSignGetAllParam param) throws CoBusinessException,Exception;

    BondState save(ActivitySignSaveParam param, CerePlatformBusiness user,String ip) throws CoBusinessException,Exception;

    List<Product> getById(Long signId) throws CoBusinessException;

    Page getProducts(ActivitySignGetProductParam param) throws CoBusinessException;

    List<CereShopGroup> getGroups(Long shopId) throws CoBusinessException;

    void handleBondPayLog(String[] split, String transaction_id, String orderNo, Integer paymentMode) throws Exception;

    BondState checkPay(ActivitySignSaveParam param) throws CoBusinessException;

    void refundBond(String[] split, String transaction_id, String orderNo) throws CoBusinessException;

    CereActivitySign findBySignId(Long signId);

    Integer findActitivyState(Long activityId);

    BondCount getAllBond(BondParam param) throws CoBusinessException;

    void deleteById(Long signId) throws CoBusinessException;

    void deleteDetailsBySignId(Long signId) throws CoBusinessException;

    void deleteLogBySignId(Long signId) throws CoBusinessException;

    ActivitySignDetail getActivitySignDetail(Long activityId, CerePlatformBusiness user);

    ActivityData getDatas(ActivityGetDataParam param) throws CoBusinessException;
}
