/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.distributor;

import com.shop.cereshop.business.param.ship.ShipBindParam;
import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereDistributorBuyerDAO extends BaseMapper<CereDistributorBuyer> {
    int insert(CereDistributorBuyer record);

    int insertSelective(CereDistributorBuyer record);

    void update(CereDistributorBuyer buyer);

    void delete(CereDistributorBuyer buyer);

    CereDistributorBuyer findBuyer(CereDistributorBuyer buyer);

    CereDistributorBuyer findByUserIdAndDistributorId(ShipBindParam param);
}
