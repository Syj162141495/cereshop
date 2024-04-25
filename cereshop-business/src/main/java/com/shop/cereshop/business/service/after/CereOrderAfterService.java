/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.after;

import com.shop.cereshop.business.page.after.After;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.business.param.after.AfterGetAllParam;
import com.shop.cereshop.business.param.after.AfterIdParam;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereOrderAfterService {
    Page getAll(AfterGetAllParam param) throws CoBusinessException;

    After getById(Long afterId) throws CoBusinessException;

    void refundSuccess(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void refundRefuse(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void success(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    void refuse(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void confirmAndRefund(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void damaging(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    void handleRefundSuc(String s, String transaction_id, String orderNo, Integer paymentMode) throws CoBusinessException;

    void refundError(String orderFormid) throws CoBusinessException;
}
