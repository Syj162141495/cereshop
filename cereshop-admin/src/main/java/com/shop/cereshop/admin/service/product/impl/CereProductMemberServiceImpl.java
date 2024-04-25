/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.product.CereProductMemberDAO;
import com.shop.cereshop.admin.page.product.MemberProduct;
import com.shop.cereshop.admin.param.product.CanvasAdminProductParam;
import com.shop.cereshop.admin.service.product.CereProductMemberService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CereProductMemberServiceImpl implements CereProductMemberService {

    @Autowired
    private CereProductMemberDAO cereProductMemberDAO;

    @Override
    public Page getMemberProducts(CanvasAdminProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<MemberProduct> list=cereProductMemberDAO.getMemberProducts(param);
        if(!EmptyUtils.isEmpty(list)){
            BigDecimal decimal=new BigDecimal(100);
            list.forEach(product -> {
                //设置会员价格
                if(IntegerEnum.MEMBER_PRODUCT_MODE_DISCOUNT.getCode().equals(product.getMode())){
                    //如果是折扣，计算会员价
                    BigDecimal discount=product.getDiscount().divide(decimal,2,BigDecimal.ROUND_HALF_UP);
                    product.setPrice(product.getPrice().multiply(discount).setScale(2,BigDecimal.ROUND_HALF_UP));
                }else {
                    product.setPrice(product.getDiscount());
                }
            });
        }
        PageInfo<MemberProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
