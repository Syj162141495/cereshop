/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.buyer;

import com.shop.cereshop.admin.page.buyer.BuyerComment;
import com.shop.cereshop.admin.page.buyer.BuyerOrder;
import com.shop.cereshop.admin.page.buyer.BuyerUser;
import com.shop.cereshop.admin.page.buyer.BuyerUserDetail;
import com.shop.cereshop.admin.param.buyer.BuyerGetAllParam;
import com.shop.cereshop.admin.param.buyer.BuyerGetByIdParam;
import com.shop.cereshop.admin.param.buyer.BuyerGetLabelsParam;
import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereBuyerUserDAO extends BaseMapper<CereBuyerUser> {
    int deleteByPrimaryKey(Long buyerUserId);

    int insert(CereBuyerUser record);

    int insertSelective(CereBuyerUser record);

    CereBuyerUser selectByPrimaryKey(Long buyerUserId);

    int updateByPrimaryKeySelective(CereBuyerUser record);

    int updateByPrimaryKey(CereBuyerUser record);

    List<BuyerUser> getAll(BuyerGetAllParam param);

    BuyerUserDetail getById(@Param("buyerUserId") Long buyerUserId);

    List<String> findLabels(@Param("buyerUserId") Long buyerUserId);

    Integer findOrders(@Param("buyerUserId") Long buyerUserId);

    Integer findPays(@Param("buyerUserId") Long buyerUserId);

    Integer findProducts(@Param("buyerUserId") Long buyerUserId);

    BigDecimal findPrice(@Param("buyerUserId") Long buyerUserId);

    Integer findAfters(@Param("buyerUserId") Long buyerUserId);

    Integer findAfterOrders(@Param("buyerUserId") Long buyerUserId);

    Integer findSuccessAfters(@Param("buyerUserId") Long buyerUserId);

    List<BuyerOrder> findOrderList(BuyerGetByIdParam param);

    List<BuyerComment> findComments(@Param("buyerUserId") Long buyerUserId);

    List<CereBuyerReceive> findReceives(@Param("buyerUserId") Long buyerUserId);

    List<CerePlatformLabel> getLabels(BuyerGetLabelsParam param);

    List<CerePlatformLabel> getUserLabels(BuyerGetLabelsParam param);
}
