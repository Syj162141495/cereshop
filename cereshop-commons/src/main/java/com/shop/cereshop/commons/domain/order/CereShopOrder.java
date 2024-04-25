/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.order;

import com.shop.cereshop.commons.utils.EmptyUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_order 订单信息实体类
 * @author
 */
@Data
public class CereShopOrder implements Serializable {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 关联父订单id
     */
    private Long parentId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 订单ID
     */
    private String orderFormid;

    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 平台优惠券id
     */
    private Long couponId;

    public void setCouponId(Long couponId) {
        if(!EmptyUtils.isLongEmpty(couponId)){
            this.couponId = couponId;
        }else {
            this.couponId = null;
        }
    }

    /**
     * 客户关联商家优惠券数据主键id
     */
    private Long id;

    public void setId(Long id) {
        if(!EmptyUtils.isLongEmpty(id)){
            this.id = id;
        }else {
            this.id=null;
        }
    }

    /**
     * 店铺秒杀活动id
     */
    private Long shopSeckillId;

    /**
     * 店铺拼团活动id
     */
    private Long shopGroupWorkId;

    /**
     * 店铺限时折扣活动id
     */
    private Long shopDiscountId;

    /**
     * 运营计划id
     */
    private Long shopOperateId;

    /**
     * 订单总价
     */
    private BigDecimal orderPrice;

    /**
     * 物流费用
     */
    private BigDecimal logisticsPrice;

    /**
     * 优惠金额
     */
    private BigDecimal discountPrice;

    /**
     * 支付金额
     */
    private BigDecimal price;

    /**
     * 订单状态
     */
    private Integer state;

    /**
     * 是否支付 1-是 0-否
     */
    private Integer paymentState;

    /**
     * 支付方式 1-微信支付 2-支付宝支付
     */
    private Integer paymentMode;

    /**
     * 支付时间
     */
    private String paymentTime;

    /**
     * 下单人姓名
     */
    private String customerName;

    /**
     * 下单人手机号
     */
    private String customerPhone;

    /**
     * 收货人姓名
     */
    private String receiveName;

    /**
     * 收货人手机号
     */
    private String receivePhone;

    /**
     * 收货地址
     */
    private String receiveAdress;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 成交时间
     */
    private String receiveTime;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 售后状态 0-无售后 1-售后中 2-售后成功 3-售后关闭
     */
    private Integer afterState;

    /**
     * 物流方案
     */
    private Long logisticsId;

    /**
     * 关联分销员id
     */
    private Long distributorId;

    /**
     * 直接分销佣金
     */
    private BigDecimal directDistributorMoney;

    /**
     * 间接分销佣金
     */
    private BigDecimal indirectDistributorMoney;

    /**
     * 平台秒杀活动id
     */
    private Long seckillId;

    /**
     * 平台限时折扣活动id
     */
    private Long discountId;

    /**
     * 平台支付有礼活动id
     */
    private Long politeId;

    /**
     * 商家场景营销活动id
     */
    private Long sceneId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
