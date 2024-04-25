/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.business.param.recruit.ShopRecruitParam;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.domain.shop.CereShopRecruit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopRecruitDAO extends BaseMapper<CereShopRecruit> {
    int insert(CereShopRecruit record);

    int insertSelective(CereShopRecruit record);

    void update(CereShopRecruit recruit);

    ShopRecruitParam getByShopId(@Param("shopId") Long shopId);

    List<CereShopDistributor> findStayDistributor(@Param("shopId") Long shopId);
}
