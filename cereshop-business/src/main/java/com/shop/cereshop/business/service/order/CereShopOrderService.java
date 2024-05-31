/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.order;

import com.shop.cereshop.business.page.finance.Bank;
import com.shop.cereshop.business.page.finance.FinanceCount;
import com.shop.cereshop.business.page.finance.WithdrawalDetail;
import com.shop.cereshop.business.page.order.ShopOrder;
import com.shop.cereshop.business.param.finance.FinanceCountParam;
import com.shop.cereshop.business.param.finance.FinanceDetailParam;
import com.shop.cereshop.business.param.finance.FinanceWithdrawalParam;
import com.shop.cereshop.business.param.order.OrderGetAllParam;
import com.shop.cereshop.business.param.order.OrderGetByIdParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.math.BigDecimal;
import java.util.List;

public interface CereShopOrderService {
    Page getAll(OrderGetAllParam param) throws CoBusinessException;

    ShopOrder getById(OrderGetByIdParam param) throws CoBusinessException;

    void updateState(CereShopOrder cereShopOrder) throws CoBusinessException;

    FinanceCount getFinanceCount(FinanceCountParam param) throws CoBusinessException;

    Bank getBank(Long shopId) throws CoBusinessException;

    List<WithdrawalDetail> getWithdrawalDetails(FinanceWithdrawalParam param) throws CoBusinessException;

    Page getDetails(FinanceDetailParam param) throws CoBusinessException;

    void updateBatchStock(List<CereProductSku> skus) throws CoBusinessException;

    BigDecimal getWithdrawableStayMoney(Long shopId);

    BigDecimal getAllWithdrawableMoney(Long shopId);

    BigDecimal getWithdrawableMoney(Long shopId);

    CereShopOrder findById(Long orderId);

    void cancelBatch(List<Long> ids) throws CoBusinessException,Exception;

    void handleRefundWxLog(String orderFormid, String transactionId, String outTradeNo) throws Exception;

    List<CereShopOrder> findBySeckillId(Long shopSeckillId);

    void updateBatchCancelOrder(List<CereShopOrder> list) throws CoBusinessException;

    List<CereShopOrder> findByDiscountId(Long shopDiscountId);
}
