/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.order;

import com.shop.cereshop.admin.page.order.ShopOrder;
import com.shop.cereshop.admin.page.product.Product;
import com.shop.cereshop.admin.page.product.SkuDetail;
import com.shop.cereshop.admin.param.order.OrderGetAllParam;
import com.shop.cereshop.admin.param.order.OrderGetByIdParam;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopOrderDAO extends BaseMapper<CereShopOrder> {
    int deleteByPrimaryKey(@Param("orderId")Long orderId);

    int insert(CereShopOrder record);

    int insertSelective(CereShopOrder record);

    CereShopOrder selectByPrimaryKey(@Param("orderId")Long orderId);

    int updateByPrimaryKeySelective(CereShopOrder record);

    int updateByPrimaryKey(CereShopOrder record);

    void updateAfterState(CereShopOrder cereShopOrder);

    void updateState(CereShopOrder cereShopOrder);

    void updateBatchStock(@Param("skus") List<CereProductSku> skus);

    List<ShopOrder> getAll(OrderGetAllParam param);

    ShopOrder getById(OrderGetByIdParam param);

    Integer getOrderTotals(@Param("buyerUserId") Long buyerUserId);

    List<Product> getProducts(@Param("orderId") Long orderId);

    List<SkuDetail> findSkuAttribute(@Param("orderProductId") Long orderProductId);

}
