/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.city;

import com.shop.cereshop.business.page.city.City;
import com.shop.cereshop.business.param.logistics.LogistCityParam;
import com.shop.cereshop.commons.domain.city.CereCityManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CereCityManageDAO extends BaseMapper<CereCityManage> {
    int deleteByPrimaryKey(Long cityId);

    int insert(CereCityManage record);

    int insertSelective(CereCityManage record);

    CereCityManage selectByPrimaryKey(Long cityId);

    int updateByPrimaryKeySelective(CereCityManage record);

    int updateByPrimaryKey(CereCityManage record);

    List<City> getCitySelect(LogistCityParam param);
}
