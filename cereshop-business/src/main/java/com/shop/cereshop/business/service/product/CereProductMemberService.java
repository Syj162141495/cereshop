/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product;

import com.shop.cereshop.business.page.member.MemberPrice;
import com.shop.cereshop.business.page.member.ProductMember;
import com.shop.cereshop.commons.domain.product.CereProductMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CereProductMemberService {

    List<CereProductMember> findAllMin();

    List<CereProductMember> findAllMax();

    List<MemberPrice> getProductMembers(Long skuId);

    void deleteByProductId(Long productId);

    void insertBatch(List<CereProductMember> members);
}
