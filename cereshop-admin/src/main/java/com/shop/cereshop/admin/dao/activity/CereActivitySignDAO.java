/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.activity;

import com.shop.cereshop.admin.page.finance.ShopBond;
import com.shop.cereshop.admin.param.finance.BondParam;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereActivitySignDAO extends BaseMapper<CereActivitySign> {
    int deleteByPrimaryKey(Long signId);

    int insert(CereActivitySign record);

    int insertSelective(CereActivitySign record);

    CereActivitySign selectByPrimaryKey(Long signId);

    int updateByPrimaryKeySelective(CereActivitySign record);

    int updateByPrimaryKey(CereActivitySign record);

    CereActivitySign findBySignId(@Param("signId") Long signId);

    List<ShopBond> getAllBond(BondParam param);

    String findTransactionId(@Param("formid") String formid);

    BigDecimal findBondTotal();

    Integer findActivityState(@Param("activityId") Long activityId);
}
