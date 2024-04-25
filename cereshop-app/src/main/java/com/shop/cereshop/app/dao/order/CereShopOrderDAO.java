/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.app.page.order.OrderCountDTO;
import com.shop.cereshop.app.page.order.OrderDetail;
import com.shop.cereshop.app.page.order.Orders;
import com.shop.cereshop.app.page.settlement.SettlementShop;
import com.shop.cereshop.app.param.order.OrderGetAllParam;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import com.shop.cereshop.commons.domain.order.CereOrderParent;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopOrderDAO extends BaseMapper<CereShopOrder> {
    int deleteByPrimaryKey(Long orderId);

    int insert(CereShopOrder record);

    int insertSelective(CereShopOrder record);

    CereShopOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(CereShopOrder record);

    int updateByPrimaryKey(CereShopOrder record);

    CereBuyerReceive findUserReceive(@Param("buyerUserId") Long buyerUserId);

    SettlementShop findSettlementShop(@Param("shopId") Long shopId);

    String findSkuValues(@Param("skuId") Long skuId);

    CereShopOrder findById(@Param("orderId") Long orderId);

    CereOrderParent findByParentFormid(@Param("orderFormId") String orderFormId);

    List<CereShopOrder> findByParentId(@Param("parentId") Long parentId);

    List<CereShopOrder> findByFormid(@Param("orderFormId") String orderFormId);

    List<Orders> getAll(OrderGetAllParam param);

    OrderCountDTO selectOrderCount(Long buyerUserId);

    OrderDetail getById(@Param("orderId") Long orderId);

    List<Long> findShopId(@Param("orderId") Long orderId);

    List<CereOrderAfter> findAfterByOrderId(@Param("orderId") Long orderId);

    void deleteAfterProducts(@Param("list") List<CereOrderAfter> list);

    void deleteAfterProductSkus(@Param("list") List<CereOrderAfter> list);

    void deleteOrderProductSkus(@Param("orderId") Long orderId);

    void deleteAll(@Param("orderId") Long orderId);

    void updateBatchStock(@Param("skus") List<CereProductSku> skus);

    List<CereShopOrder> findByOrderId(@Param("orderId") Long orderId);

    List<CereShopOrder> checkPayParent(@Param("orderId") Long orderId);

    List<CereShopOrder> checkPayOrder(@Param("orderId") Long orderId);

    List<Long> findAfterSkuIdsByOrderId(@Param("orderId") Long orderId);

    CereShopOrder checkState(@Param("orderId") Long orderId,@Param("state") Integer state);

    CereShopOrder checkStateFinishAndCancel(@Param("orderId") Long orderId);

    CereShopOrder checkCancelState(@Param("orderId") Long orderId);

    CerePayLog findPayLog(@Param("orderFormid") String orderFormid);

    CereShopOrder findByOrderFormid(@Param("orderFormid") String orderFormId);

    List<CereShopOrder> findByIds(@Param("ids") List<Long> ids);

    int findOrderNumber(@Param("orderId") Long orderId);

    void updateBatch(@Param("list") List<CereShopOrder> list,@Param("state") Integer state,@Param("time") String time);

    void updateBuyerData(@Param("buyerUserId") Long buyerUserId,@Param("id") Long id);
}
