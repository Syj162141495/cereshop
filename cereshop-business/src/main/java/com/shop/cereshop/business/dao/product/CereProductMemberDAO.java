/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.member.MemberPrice;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.product.CereProductMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereProductMemberDAO extends BaseMapper<CerePlatformPermission> {
    int insert(CereProductMember record);

    int insertSelective(CereProductMember record);

    List<CereProductMember> findAllMin();

    List<CereProductMember> findAllMax();

    List<MemberPrice> getProductMembers(@Param("skuId") Long skuId);

    void deleteByProductId(@Param("productId") Long productId);

    void insertBatch(@Param("list") List<CereProductMember> list);
}
