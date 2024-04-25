/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product.impl;

import com.shop.cereshop.business.dao.product.CereProductMemberDAO;
import com.shop.cereshop.business.page.member.MemberPrice;
import com.shop.cereshop.business.service.product.CereProductMemberService;
import com.shop.cereshop.commons.domain.product.CereProductMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereProductMemberServiceImpl implements CereProductMemberService {

    @Autowired
    private CereProductMemberDAO cereProductMemberDAO;

    @Override
    public List<CereProductMember> findAllMin() {
        return cereProductMemberDAO.findAllMin();
    }

    @Override
    public List<CereProductMember> findAllMax() {
        return cereProductMemberDAO.findAllMax();
    }

    @Override
    public List<MemberPrice> getProductMembers(Long skuId) {
        return cereProductMemberDAO.getProductMembers(skuId);
    }

    @Override
    public void deleteByProductId(Long productId) {
        cereProductMemberDAO.deleteByProductId(productId);
    }

    @Override
    public void insertBatch(List<CereProductMember> members) {
        cereProductMemberDAO.insertBatch(members);
    }
}
