/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.shop.cereshop.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.cereshop.commons.domain.shop.CereShopWithdrawal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopWithdrawalDAO extends BaseMapper<CereShopWithdrawal> {
    int insert(CereShopWithdrawal record);

    int insertSelective(CereShopWithdrawal record);

    List<CereShopWithdrawal> getAll(WithdrawalGetAllParam param);

    CereShopWithdrawal getById(@Param("withdrawalId") Long withdrawalId);

    void update(CereShopWithdrawal cereShopWithdrawal);

}
