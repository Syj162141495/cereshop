/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.mark;

import com.shop.cereshop.commons.domain.after.CereAfterProductAttribute;
import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.order.CereOrderProductAttribute;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MarkDataDao {
    List<CereBuyerUser> findUsers();

    CereBuyerReceive findReceive(@Param("buyerUserId") Long buyerUserId);

    List<CereProductSku> findProducts();

    CereOrderProductAttribute findBySkuId(@Param("skuId") Long skuId);

    List<CereShopOrder> findOrders();

    List<CereOrderProduct> findAfterProducts(@Param("orderId") Long orderId);

    CereAfterProductAttribute findAfterAttribute(@Param("orderProductId") Long orderProductId);

    List<CereShopDistributor> findDistributors();

    CereDistributorBuyer findByUserIdAndDis(@Param("distributorId") Long distributorId,@Param("buyerUserId") Long buyerUserId);
}
