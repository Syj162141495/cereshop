/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.notice.impl;

import com.shop.cereshop.business.dao.notice.CereNoticeDAO;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.service.buyer.CereBuyerShopCouponService;
import com.shop.cereshop.business.service.notice.CereNoticeService;
import com.shop.cereshop.business.service.tool.CereShopCrowdService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereNoticeServiceImpl implements CereNoticeService {

    @Autowired
    private CereNoticeDAO cereNoticeDAO;

    @Autowired
    private CereShopCrowdService cereShopCrowdService;

    @Autowired
    private CereBuyerShopCouponService cereBuyerShopCouponService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void addOperate(Long shopCrowdId, List<OperateCoupon> coupons,Long shopId,Long shopOperateId) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //查询人群关联客户id
        String userIds=cereShopCrowdService.findUserIds(shopCrowdId);
        if(!EmptyUtils.isEmpty(userIds)){
            //转为id数组
            List<String> ids = EmptyUtils.getImages(userIds);
            //计算优惠券数量
            if(!EmptyUtils.isEmpty(coupons)){
                int count=coupons.stream().mapToInt(OperateCoupon::getNumber).sum();
                //封装客户对应消息数据
                List<CereNotice> list = ids.stream().map(id -> {
                    CereNotice cereNotice = new CereNotice();
                    cereNotice.setBuyerUserId(Long.parseLong(id));
                    cereNotice.setCreateTime(time);
                    cereNotice.setShopId(shopId);
                    cereNotice.setReceive(3);
                    cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
                    cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_SYSTEM.getCode());
                    cereNotice.setNoticeContent("您获得" + count + "张优惠券,赶紧使用吧~");
                    cereNotice.setCreateTime(time);
                    return cereNotice;
                }).collect(Collectors.toList());
                //批量插入消息数据
                List<CereBuyerShopCoupon> buyerShopCoupons=new ArrayList<>();
                cereNoticeDAO.insertBatch(list);
                for (OperateCoupon coupon : coupons) {
                    //封装客户优惠券数据
                    for (String id : ids) {
                        for (Integer i = 0; i < coupon.getNumber(); i++) {
                            CereBuyerShopCoupon cereBuyerShopCoupon=new CereBuyerShopCoupon();
                            cereBuyerShopCoupon.setBuyerUserId(Long.parseLong(id));
                            cereBuyerShopCoupon.setShopCouponId(coupon.getShopCouponId());
                            cereBuyerShopCoupon.setCreateTime(time);
                            cereBuyerShopCoupon.setCouponName(coupon.getCouponName());
                            cereBuyerShopCoupon.setCouponType(coupon.getCouponType());
                            cereBuyerShopCoupon.setEndTime(coupon.getEffectiveEnd());
                            cereBuyerShopCoupon.setStartTime(coupon.getEffectiveStart());
                            cereBuyerShopCoupon.setFullMoney(coupon.getThreshold());
                            cereBuyerShopCoupon.setReduceMoney(coupon.getCouponContent());
                            cereBuyerShopCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
                            cereBuyerShopCoupon.setShopOperateId(shopOperateId);
                            buyerShopCoupons.add(cereBuyerShopCoupon);
                        }
                    }
                }
                //批量插入客户关联商家优惠券数据
                cereBuyerShopCouponService.insertBatch(buyerShopCoupons);
            }
        }
    }

    @Override
    public void insert(CereNotice cereNotice) throws CoBusinessException {
        cereNoticeDAO.insert(cereNotice);
    }
}
