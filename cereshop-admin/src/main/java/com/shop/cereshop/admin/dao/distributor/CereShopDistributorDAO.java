/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.distributor;

import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereShopDistributorDAO extends BaseMapper<CereShopDistributor> {
    int deleteByPrimaryKey(Long distributorId);

    int insert(CereShopDistributor record);

    int insertSelective(CereShopDistributor record);

    CereShopDistributor selectByPrimaryKey(Long distributorId);

    int updateByPrimaryKeySelective(CereShopDistributor record);

    int updateByPrimaryKey(CereShopDistributor record);
}
