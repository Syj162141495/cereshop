/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.business.param.shop.CereShopOtherOrganizationsParam;
import com.shop.cereshop.commons.domain.shop.CereShopOtherOrganizations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CereShopOtherOrganizationsDAO extends BaseMapper<CereShopOtherOrganizations> {
    int insert(CereShopOtherOrganizations record);

    int insertSelective(CereShopOtherOrganizations record);

    void update(CereShopOtherOrganizations otherOrganizations);

    CereShopOtherOrganizations findByShopId(@Param("shopId") Long shopId);

    void insertParam(CereShopOtherOrganizationsParam param);

    void updateParam(CereShopOtherOrganizationsParam param);
}
