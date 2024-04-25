/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.business.param.shop.CereShopIndividualBusinessesParam;
import com.shop.cereshop.commons.domain.shop.CereShopIndividualBusinesses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CereShopIndividualBusinessesDAO extends BaseMapper<CereShopIndividualBusinesses> {
    int insert(CereShopIndividualBusinesses record);

    int insertSelective(CereShopIndividualBusinesses record);

    void update(CereShopIndividualBusinesses individualBusinesses);

    CereShopIndividualBusinesses findByShopId(@Param("shopId") Long shopId);

    void insertParam(CereShopIndividualBusinessesParam param);

    void updateParam(CereShopIndividualBusinessesParam param);

}
