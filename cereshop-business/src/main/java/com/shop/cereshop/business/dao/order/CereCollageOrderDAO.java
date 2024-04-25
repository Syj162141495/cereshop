/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.order;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereCollageOrderDAO extends BaseMapper<CereCollageOrder> {
    int deleteByPrimaryKey(Long collageId);

    int insert(CereCollageOrder record);

    int insertSelective(CereCollageOrder record);

    CereCollageOrder selectByPrimaryKey(Long collageId);

    int updateByPrimaryKeySelective(CereCollageOrder record);

    int updateByPrimaryKey(CereCollageOrder record);

    List<Long> findOrderIds(@Param("collageId") Long collageId);
}
