/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.label;

import com.shop.cereshop.commons.domain.label.CereBuyerShopLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereBuyerShopLabelDAO extends BaseMapper<CereBuyerShopLabel> {
    int insert(CereBuyerShopLabel record);

    int insertSelective(CereBuyerShopLabel record);

    void insertBatch(@Param("list") List<CereBuyerShopLabel> list);

    void deleteById(@Param("buyerUserId") Long buyerUserId);

    List<Long> findByUserId(@Param("buyerUserId") Long buyerUserId);
}
