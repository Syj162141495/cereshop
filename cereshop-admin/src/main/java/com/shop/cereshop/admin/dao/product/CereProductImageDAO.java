/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.product;

import com.shop.cereshop.commons.domain.product.CereProductImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereProductImageDAO extends BaseMapper<CereProductImage> {
    int insert(CereProductImage record);

    int insertSelective(CereProductImage record);

    List<String> findByProductId(@Param("productId") Long productId);
}
