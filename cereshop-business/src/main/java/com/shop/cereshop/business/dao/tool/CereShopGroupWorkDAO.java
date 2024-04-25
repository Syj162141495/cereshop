/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.*;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.ShopGroupWorkGetAllParam;
import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWorkDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopGroupWorkDAO extends BaseMapper<CereShopGroupWork> {
    int deleteByPrimaryKey(Long shopGroupWorkId);

    int insert(CereShopGroupWork record);

    int insertSelective(CereShopGroupWork record);

    CereShopGroupWork selectByPrimaryKey(Long shopGroupWorkId);

    int updateByPrimaryKeySelective(CereShopGroupWork record);

    int updateByPrimaryKey(CereShopGroupWork record);

    ShopGroupWorkUDetail getById(@Param("shopGroupWorkId") Long shopGroupWorkId);

    List<ToolProduct> getProducts(@Param("shopId") Long shopId);

    List<ShopGroupWork> getAll(ShopGroupWorkGetAllParam param);

    List<CouponProduct> findDataProducts(@Param("shopGroupWorkId") Long shopGroupWorkId,@Param("shopId") Long shopId);

    String findGroupName(@Param("shopGroupWorkId") Long shopGroupWorkId);

    Integer findTotal(@Param("shopGroupWorkId") Long shopGroupWorkId);

    BigDecimal findMony(@Param("shopGroupWorkId") Long shopGroupWorkId,@Param("shopId") Long shopId);

    List<CereShopGroupWork> checkTime(CereShopGroupWork cereShopGroupWork);

    void updateState(CereShopGroupWork cereShopGroupWork);

    List<Long> checkOtherSeckill(@Param("details") List<CereShopGroupWorkDetail> details,@Param("startTime") String startTime,
                                 @Param("endTime") String endTime,@Param("shopId") Long shopId);

    List<Long> checkOtherDiscount(@Param("details") List<CereShopGroupWorkDetail> details,@Param("startTime") String startTime,
                                  @Param("endTime") String endTime,@Param("shopId") Long shopId);

    List<ShopGroupWorkUDetail> getGroupWorks(RenovationParam param);

    List<CereShopGroupWork> checkUpdateTime(CereShopGroupWork cereShopGroupWork);

    List<CereCollageOrder> findCollageOrder(@Param("shopGroupWorkId") Long shopGroupWorkId);

    List<CereShopGroupWork> findShopWorks();

    void updateWorkEndState(@Param("works") List<CereShopGroupWork> works,@Param("time") String time);
}
