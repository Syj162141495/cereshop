/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.after;

import com.shop.cereshop.admin.page.after.*;
import com.shop.cereshop.admin.param.after.AfterGetAllParam;
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

    AfterDetail getById(@Param("afterId") Long afterId);

    List<AfterProduct> findProducts(@Param("afterId") Long afterId);

    List<AfterHistory> findHistories(@Param("afterId") Long afterId);

    AfterHistory findAdminHistory(@Param("afterId") Long afterId);

    AfterBuyer getBuyer(@Param("buyerUserId") Long buyerUserId);

    Integer findSuccessAfter(@Param("buyerUserId") Long buyerUserId);

    CereOrderAfter findByOrderId(@Param("orderId") Long orderId);

    Integer findExpress();

    AfterRefund findOrderPay(@Param("afterId") Long afterId);

    List<CereOrderProduct> findOtherProductsByAfterId(@Param("orderId") Long orderId,@Param("afterId") Long afterId);

    List<CereProductSku> findAfterSkus(@Param("afterId") Long afterId);

    CereOrderAfter findById(@Param("afterId") Long afterId);
}
