/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.operate.ShopOperate;
import com.shop.cereshop.business.page.tool.ShopOperateDetail;
import com.shop.cereshop.business.param.tool.CrowdCondition;
import com.shop.cereshop.business.param.tool.ShopOperateGetAllParam;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopOperateDAO extends BaseMapper<CereShopOperate> {
    int deleteByPrimaryKey(Long shopOperateId);

    int insert(CereShopOperate record);

    int insertSelective(CereShopOperate record);

    CereShopOperate selectByPrimaryKey(Long shopOperateId);

    int updateByPrimaryKeySelective(CereShopOperate record);

    int updateByPrimaryKey(CereShopOperate record);

    List<CereShopOperate> findAll();

    CereShopCrowd findCrowd(@Param("shopCrowdId") Long shopCrowdId);

    List<String> findUserByCondition(CrowdCondition condition);

    List<String> findNoBuy(CrowdCondition condition);

    List<String> findNoOrder(CrowdCondition condition);

    List<String> findNoVisit(CrowdCondition condition);

    List<OperateCoupon> findCouponsById(@Param("shopOperateId") Long shopOperateId);

    void insertBatchNotice(@Param("list") List<CereNotice> list);

    void insertBatchBuyerCoupon(@Param("list") List<CereBuyerShopCoupon> list);

    ShopOperateDetail getById(@Param("shopOperateId") Long shopOperateId);

    List<ShopOperate> getAll(ShopOperateGetAllParam param);

    Integer findCouponUsers(@Param("time") String time,@Param("shopOperateId") Long shopOperateId);

    Integer findUsers(@Param("time") String time,@Param("shopOperateId") Long shopOperateId);

    Integer findOrders(@Param("time") String time,@Param("shopOperateId") Long shopOperateId);

    BigDecimal findTotal(@Param("time") String time, @Param("shopOperateId") Long shopOperateId);

    Integer findPays(@Param("time") String time,@Param("shopOperateId") Long shopOperateId);

    Integer findPayOrders(@Param("time") String time,@Param("shopOperateId") Long shopOperateId);

    List<CereShopOperate> checkState(@Param("ids") List<Long> ids);

    List<Long> findAlreadyCoupon(@Param("ids") List<Long> ids,@Param("shopOperateId") Long shopOperateId);

    List<CereShopOperate> findShopOperate();

    void updateOperateEndState(@Param("list") List<CereShopOperate> list,@Param("time") String time);
}
