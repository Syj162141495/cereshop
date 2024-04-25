/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.tool.ShopCrowd;
import com.shop.cereshop.business.param.tool.CrowdCondition;
import com.shop.cereshop.business.param.tool.ShopCrowdGetAllParam;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopCrowdDAO extends BaseMapper<CereShopCrowd> {
    int deleteByPrimaryKey(Long shopCrowdId);

    int insert(CereShopCrowd record);

    int insertSelective(CereShopCrowd record);

    CereShopCrowd selectByPrimaryKey(Long shopCrowdId);

    int updateByPrimaryKeySelective(CereShopCrowd record);

    int updateByPrimaryKey(CereShopCrowd record);

    List<String> findNoBuy(CrowdCondition condition);

    List<String> findNoOrder(CrowdCondition condition);

    List<String> findNoVisit(CrowdCondition condition);

    CereShopCrowd checkName(@Param("crowdName") String crowdName,@Param("shopCrowdId") Long shopCrowdId);

    CereShopCrowd getById(@Param("shopCrowdId") Long shopCrowdId);

    List<ShopCrowd> getAll(ShopCrowdGetAllParam param);

    String findUserIds(@Param("shopCrowdId") Long shopCrowdId);

    List<BuyerUser> getUsers(@Param("ids") List<String> ids);

    List<ShopCrowd> selectCrowd(ShopCrowdGetAllParam param);

    void deleteByIds(@Param("ids") List<Long> ids);

    List<String> findUserByShopBuyCondition(CrowdCondition condition);

    List<String> findUserByShopOrderCondition(CrowdCondition condition);

    List<String> findUserByShopAddCondition(CrowdCondition condition);

    List<String> findUserByShopVisitCondition(CrowdCondition condition);

    List<String> findUserByCountCondition(CrowdCondition condition);

    List<String> findUserByPriceCondition(CrowdCondition condition);

    List<String> findUserByLabelCondition(CrowdCondition condition);

    List<CereShopCrowd> findAll();

    void updateBatch(@Param("list") List<CereShopCrowd> list);
}
