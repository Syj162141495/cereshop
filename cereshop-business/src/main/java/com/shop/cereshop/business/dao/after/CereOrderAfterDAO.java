/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.after;

import com.shop.cereshop.business.page.after.After;
import com.shop.cereshop.business.page.after.AfterHistory;
import com.shop.cereshop.business.page.after.Refund;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.page.order.SkuDetail;
import com.shop.cereshop.business.param.after.AfterGetAllParam;
import com.shop.cereshop.business.param.after.AfterIdParam;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereOrderAfterDAO extends BaseMapper<CereOrderAfter> {
    int deleteByPrimaryKey(Long afterId);

    int insert(CereOrderAfter record);

    int insertSelective(CereOrderAfter record);

    CereOrderAfter selectByPrimaryKey(Long afterId);

    int updateByPrimaryKeySelective(CereOrderAfter record);

    int updateByPrimaryKey(CereOrderAfter record);

    List<After> getAll(AfterGetAllParam param);

    After getById(@Param("afterId") Long afterId);

    List<Product> findProducts(@Param("afterId") Long afterId);

    List<CereOrderProduct> findOtherProductsByAfterId(@Param("orderId") Long orderId,@Param("afterId") Long afterId);

    List<CereOrderAfter> findAfterSuccessProduct(AfterIdParam param);

    Refund findOrderPay(@Param("afterId") Long afterId);

    List<SkuDetail> findSkuAttribute(@Param("afterProductId") Long afterProductId);

    List<CereProductSku> findAfterSkus(@Param("afterId") Long afterId);

    CereOrderAfter findById(@Param("afterId") Long afterId);

    List<AfterHistory> findHistories(@Param("afterId") Long afterId);

    Long findByOrderId(@Param("orderId") Long orderId);
}
