/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.label;

import com.shop.cereshop.business.page.label.ShopUserLabel;
import com.shop.cereshop.business.param.label.UserLabelGetAllParam;
import com.shop.cereshop.commons.domain.label.CereShopUserLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopUserLabelDAO extends BaseMapper<CereShopUserLabel> {
    int deleteByPrimaryKey(Long labelId);

    int insert(CereShopUserLabel record);

    int insertSelective(CereShopUserLabel record);

    CereShopUserLabel selectByPrimaryKey(Long labelId);

    int updateByPrimaryKeySelective(CereShopUserLabel record);

    int updateByPrimaryKey(CereShopUserLabel record);

    void deleteBuyer(@Param("labelId") Long labelId);

    CereShopUserLabel getById(@Param("labelId") Long labelId);

    List<ShopUserLabel> getAll(UserLabelGetAllParam param);

    List<CereShopUserLabel> getLabels(@Param("shopId") Long shopId);
}
