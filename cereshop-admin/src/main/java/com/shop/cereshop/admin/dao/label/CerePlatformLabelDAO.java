/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.label;

import com.shop.cereshop.admin.param.label.LabelGetAllParam;
import com.shop.cereshop.admin.param.label.LabelSaveParam;
import com.shop.cereshop.admin.param.label.LabelUpdateParam;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.domain.label.PlatformLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformLabelDAO extends BaseMapper<LabelSaveParam> {
    int deleteByPrimaryKey(Long buyerLabelId);

    int insert(LabelSaveParam record);

    int insertSelective(CerePlatformLabel record);

    CerePlatformLabel selectByPrimaryKey(Long buyerLabelId);

    int updateByPrimaryKeySelective(LabelUpdateParam record);

    int updateByPrimaryKey(CerePlatformLabel record);

    List<PlatformLabel> getAll(LabelGetAllParam param);

    PlatformLabel getById(@Param("buyerLabelId") Long buyerLabelId);

    void deleteByIds(@Param("ids") List<Long> ids);

    List<CerePlatformLabel> findAll();

    List<Long> findAllByDay(@Param("consumptionDay") Integer consumptionDay);

    List<Long> findRangeDayBuyers(CerePlatformLabel label);

    List<Long> findFrequencyBuyes(CerePlatformLabel label);

    List<Long> findMoneyBuyers(CerePlatformLabel label);

    List<Long> findAllBuyers(CerePlatformLabel label);

    List<PlatformLabel> getAllByIds(@Param("ids") List<Long> ids);
}
