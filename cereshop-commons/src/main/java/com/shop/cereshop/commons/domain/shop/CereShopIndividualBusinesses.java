/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * cere_shop_individual_businesses 店铺个体工商户认证信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopIndividualBusinesses", description = "店铺个体工商户认证信息实体类")
public class CereShopIndividualBusinesses implements Serializable {
    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    @NotBlank(message = "商户名称不能为空")
    private String subjectName;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value = "统一社会信用代码")
    @NotBlank(message = "统一社会信用代码不能为空")
    private String subjectCode;

    /**
     * 地址  省-市-区
     */
    @ApiModelProperty(value = "地址  省-市-区")
    @NotBlank(message = "地址不能为空")
    private String subjectRegion;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String subjectAdress;

    /**
     * 营业期限开始时间
     */
    @ApiModelProperty(value = "营业期限开始时间")
    @NotBlank(message = "营业期限开始时间不能为空")
    private String subjectStartTime;

    /**
     * 营业期限结束时间
     */
    @ApiModelProperty(value = "营业期限结束时间")
    @NotBlank(message = "营业期限结束时间不能为空")
    private String subjectEndTime;

    /**
     * 营业执照图片地址（多个以逗号隔开）
     */
    @ApiModelProperty(value = "营业执照图片地址（多个以逗号隔开）")
    @NotBlank(message = "营业执照图片不能为空")
    private String subjectLicense;

    /**
     * 经营者姓名
     */
    @ApiModelProperty(value = "经营者姓名")
    @NotBlank(message = "经营者姓名不能为空")
    private String subjectOperator;

    /**
     * 证件类型 （取数据字典）
     */
    @ApiModelProperty(value = "证件类型 （取数据字典）")
    @NotNull(message = "证件类型不能为空")
    private Long subjectCardType;

    /**
     * 经营者身份证号码
     */
    @ApiModelProperty(value = "经营者身份证号码")
    @NotBlank(message = "经营者身份证号码不能为空")
    private String subjectIdCard;

    /**
     * 证件有效开始时间
     */
    @ApiModelProperty(value = "证件有效开始时间")
    @NotBlank(message = "证件有效开始时间不能为空")
    private String subjectCardStartTime;

    /**
     * 证件有效结束时间
     */
    @ApiModelProperty(value = "证件有效结束时间")
    @NotBlank(message = "证件有效结束时间不能为空")
    private String subjectCardEndTime;

    /**
     * 身份证正面照
     */
    @ApiModelProperty(value = "身份证正面照")
    @NotBlank(message = "身份证正面照不能为空")
    private String subjectCardPositive;

    /**
     * 身份证反面照
     */
    @ApiModelProperty(value = "身份证反面照")
    @NotBlank(message = "身份证反面照不能为空")
    private String subjectCardSide;

    /**
     * 管理员手机号
     */
    @ApiModelProperty(value = "管理员手机号")
    private String administratorsPhone;

    /**
     * 管理员邮箱
     */
    @ApiModelProperty(value = "管理员邮箱")
    private String administratorsEmail;

    /**
     * 账户类型 1-对公 2-对私
     */
    @ApiModelProperty(value = "账户类型 1-对公 2-对私")
    private Integer accountType;

    /**
     * 开户银行（取数据字典）
     */
    @ApiModelProperty(value = "开户银行（取数据字典）")
    private Long accountOpenBank;

    /**
     * 开户银行地区   省-市
     */
    @ApiModelProperty(value = "开户银行地区   省-市")
    private String accountBankRegion;

    /**
     * 银行账户
     */
    @ApiModelProperty(value = "银行账户")
    private String accountBankCard;

    /**
     * 商户简称
     */
    @ApiModelProperty(value = "商户简称")
    private String shopAbbreviation;

    /**
     * 客服电话
     */
    @ApiModelProperty(value = "客服电话")
    private String servicePhone;

    /**
     * 提供服务（取数据字典）
     */
    @ApiModelProperty(value = "提供服务（取数据字典）")
    private Long serviceProviding;

    /**
     * 店铺首页截图
     */
    @ApiModelProperty(value = "店铺首页截图")
    private String shopIndexImage;

    /**
     * 店铺管理后台截图
     */
    @ApiModelProperty(value = "店铺管理后台截图")
    private String shopBackstageImage;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;
}
