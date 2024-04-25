/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.after;

import com.shop.cereshop.business.page.order.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 售后单返回数据实体类
 */
@Data
@ApiModel(value = "After", description = "售后单返回数据实体类")
public class After {

    /**
     * 售后单id
     */
    @ApiModelProperty(value = "售后单id")
    private Long afterId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 售后单号
     */
    @ApiModelProperty(value = "售后单号")
    private String afterFormid;

    /**
     * 图片凭证地址
     */
    private String image;

    /**
     * 图片凭证地址数组
     */
    @ApiModelProperty(value = "图片凭证地址数组")
    private List<String> images;

    /**
     * 协商历史
     */
    @ApiModelProperty(value = "协商历史")
    private List<AfterHistory> afterHistory;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderFormid;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer state;

    /**
     * 售后类型  1-仅退款  2-退货退款
     */
    @ApiModelProperty(value = "售后类型  1-仅退款  2-退货退款")
    private Integer afterType;

    /**
     * 退款金额
     */
    @ApiModelProperty(value = "退款金额")
    private BigDecimal price;

    /**
     * 物理费用
     */
    @ApiModelProperty(value = "物理费用")
    private BigDecimal logisticsPrice;

    /**
     * 买家账户
     */
    @ApiModelProperty(value = "买家账户")
    private String customerName;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private String createTime;

    /**
     * 最近处理时间
     */
    @ApiModelProperty(value = "最近处理时间")
    private String updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 买家说明
     */
    @ApiModelProperty(value = "买家说明")
    private String explain;

    /**
     * 卖家留言
     */
    @ApiModelProperty(value = "卖家留言")
    private String reason;

    /**
     * 快递公司
     */
    @ApiModelProperty(value = "快递公司")
    private String express;

    /**
     * 快递单号
     */
    @ApiModelProperty(value = "快递单号")
    private String deliverFormid;

    /**
     * 商品信息
     */
    @ApiModelProperty(value = "商品信息")
    private List<Product> products;

    /**
     * 售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过
     */
    @ApiModelProperty(value = "售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过")
    private Integer afterState;

    /**
     * 售后状态名称
     */
    @ApiModelProperty(value = "售后状态名称")
    private String afterStateName;
}
