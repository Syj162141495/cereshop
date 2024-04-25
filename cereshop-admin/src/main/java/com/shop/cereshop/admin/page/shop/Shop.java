/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 店铺信息返回数据实体类
 */
@Data
@ApiModel(value = "Shop", description = "店铺信息返回数据实体类")
public class Shop {

    /**
     * 处理id
     */
    @ApiModelProperty(value = "处理id ")
    private Long checkId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 入驻处理状态  0-未处理 1-通过 2-拒绝
     */
    @ApiModelProperty(value = "入驻处理状态  0-未处理 1-通过 2-拒绝")
    private Long checkState;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String shopAdress;

    /**
     * 店铺手机号
     */
    @ApiModelProperty(value = "店铺手机号")
    private String shopPhone;

    /**
     * 主体类型 1-个人 2-个体工商户 3-企业 4-其他组织
     */
    @ApiModelProperty(value = "主体类型 1-个人 2-个体工商户 3-企业 4-其他组织")
    private Integer authenType;

    /**
     * 店铺负责人
     */
    @ApiModelProperty(value = "店铺负责人")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "负责人电话")
    private String chargePersonPhone;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private String createTime;

    /**
     * 客服电话
     */
    @ApiModelProperty(value = "客服电话")
    private String servicePhone;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 证件类型
     */
    private Long cardType;

    /**
     * 证件类型名称
     */
    @ApiModelProperty(value = "证件类型名称")
    private String cardTypeName;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    /**
     * 身份证有效期
     */
    @ApiModelProperty(value = "身份证有效期")
    private String cardTime;

    /**
     * 身份证正面照
     */
    @ApiModelProperty(value = "身份证正面照")
    private String cardPositive;

    /**
     * 身份证反面照
     */
    @ApiModelProperty(value = "身份证反面照")
    private String cardSide;

    /**
     * 手持证件照
     */
    @ApiModelProperty(value = "手持证件照")
    private String cardHand;

    /**
     * 生效日期
     */
    @ApiModelProperty(value = "生效日期")
    private String effectiveDate;

    /**
     * 生效时限（年）
     */
    @ApiModelProperty(value = "生效时限（年）")
    private Integer effectiveYear;

    /**
     * 主体信息-商户名称
     */
    @ApiModelProperty(value = "主体信息-商户名称")
    private String subjectName;

    /**
     * 主体信息-统一社会信用代码
     */
    @ApiModelProperty(value = "主体信息-统一社会信用代码")
    private String subjectCode;

    /**
     * 主体信息-地址
     */
    @ApiModelProperty(value = "主体信息-地址")
    private String subjectAdress;

    /**
     * 主体信息-详细地址
     */
    @ApiModelProperty(value = "主体信息-详细地址")
    private String adress;

    /**
     * 主体信息-营业期限开始时间
     */
    @ApiModelProperty(value = "主体信息-营业期限开始时间")
    private String subjectStartTime;

    /**
     * 主体信息-营业期限结束时间
     */
    @ApiModelProperty(value = "主体信息-营业期限结束时间")
    private String subjectEndTime;

    /**
     * 主体信息-营业执照图片地址
     */
    @ApiModelProperty(value = "主体信息-营业执照图片地址")
    private List<String> subjectLicense;
}
