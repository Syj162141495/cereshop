/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.app.dao.activity.CerePlatformActivityDAO;
import com.shop.cereshop.app.page.canvas.CanvasCoupon;
import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.app.page.tool.ToolProduct;
import com.shop.cereshop.app.param.canvas.CanvasCouponParam;
import com.shop.cereshop.app.param.canvas.RenovationParam;
import com.shop.cereshop.app.service.activity.CerePlatformActivityService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CerePlatformActivityServiceImpl implements CerePlatformActivityService {

    @Autowired
    private CerePlatformActivityDAO cerePlatformActivityDAO;

    @Override
    public Page getCoupons(CanvasCouponParam param, CereBuyerUser user) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CanvasCoupon> list=null;
        if(user!=null){
            param.setBuyerUserId(user.getBuyerUserId());
            list=cerePlatformActivityDAO.getUserCoupons(param);
            if(!EmptyUtils.isEmpty(list)){
                //过滤已使用和已过期的优惠券
                list=list.stream()
                        .filter(productCoupon -> IntegerEnum.COUPON_NOT_HAVE.getCode().equals(productCoupon.getState())
                                ||IntegerEnum.COUPON_HAVE.getCode().equals(productCoupon.getState()))
                        .collect(Collectors.toList());
            }
        }else {
            list=cerePlatformActivityDAO.getCoupons(param);
        }
        if(!EmptyUtils.isEmpty(list)){
            //设置内容
            list.forEach(canvasCoupon -> {
                if (IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(canvasCoupon.getDiscountMode())) {
                    canvasCoupon.setContent("满"+canvasCoupon.getFullMoney().stripTrailingZeros().toPlainString()+"减"
                            +canvasCoupon.getReduceMoney().stripTrailingZeros().toPlainString()+"元");
                } else {
                    canvasCoupon.setContent(canvasCoupon.getReduceMoney().stripTrailingZeros().toPlainString()+"折券");
                }
            });
        }
        PageInfo<CanvasCoupon> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<ProductCoupon> findCouponByProductId(Long productId) {
        return cerePlatformActivityDAO.findCouponByProductId(productId);
    }

    @Override
    public List<ProductCoupon> findCouponByProductIdAndUserId(Long buyerUserId, Long productId) {
        return cerePlatformActivityDAO.findCouponByProductIdAndUserId(buyerUserId,productId);
    }

    @Override
    public CerePlatformActivity findById(Long couponId) {
        return cerePlatformActivityDAO.selectByPrimaryKey(couponId);
    }

    @Override
    public void updateByPrimaryKeySelective(CerePlatformActivity cerePlatformActivity) throws CoBusinessException {
        cerePlatformActivityDAO.updateByPrimaryKeySelective(cerePlatformActivity);
    }

    @Override
    public List<CerePlatformActivity> findOnGoingCouponByBatchId(List<Long> couponIdList) {
        return cerePlatformActivityDAO.findOnGoingCouponByBatchId(couponIdList);
    }

    @Override
    public List<ToolProduct> getGroupWorkProducts(RenovationParam param) {
        return cerePlatformActivityDAO.getGroupWorkProducts(param);
    }
}
