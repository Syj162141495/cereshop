/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.collage.CereCollageOrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereCollageOrderDetailDAO extends BaseMapper<CereCollageOrderDetail> {
    int deleteByPrimaryKey(Long id);

    int insert(CereCollageOrderDetail record);

    int insertSelective(CereCollageOrderDetail record);

    CereCollageOrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CereCollageOrderDetail record);

    int updateByPrimaryKey(CereCollageOrderDetail record);

    void updateInvalid(@Param("ids") List<Long> ids);
}
