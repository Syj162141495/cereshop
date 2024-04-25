/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.buyer;

import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.buyer.BuyerUserDetail;
import com.shop.cereshop.business.param.buyer.BuyerUserGetAllParam;
import com.shop.cereshop.commons.domain.business.CereBusinessBuyerUser;
import com.shop.cereshop.commons.domain.business.CereShopIdBuyerUserIdTimeDTO;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereBuyerUserDAO extends BaseMapper<CereBuyerUser> {
    int deleteByPrimaryKey(Long buyerUserId);

    int insert(CereBuyerUser record);

    int insertSelective(CereBuyerUser record);

    CereBuyerUser selectByPrimaryKey(Long buyerUserId);

    int updateByPrimaryKeySelective(CereBuyerUser record);

    int updateByPrimaryKey(CereBuyerUser record);

    List<BuyerUser> getAll(BuyerUserGetAllParam param);

    CereBuyerUser checkPhone(@Param("phone") String phone);

    BuyerUserDetail getById(@Param("shopId") Long shopId, @Param("buyerUserId") Long buyerUserId);

    /** 查询所有访问了商家或下过单的用户id */
    List<CereShopIdBuyerUserIdTimeDTO> findVisitRecord();

    /** 查询所有商家记录的店铺id */
    List<CereShopIdBuyerUserIdTimeDTO> findConsumeRecord();

    void updateGrowth(@Param("buyerUserId") Long buyerUserId, @Param("growth") int growth);
}
