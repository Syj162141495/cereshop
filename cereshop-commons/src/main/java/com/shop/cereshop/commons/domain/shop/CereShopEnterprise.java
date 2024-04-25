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
 * cere_shop_enterprise 店铺企业认证信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopEnterprise", description = "店铺企业认证信息实体类")
public class CereShopEnterprise implements Serializable {
    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "关联店铺id")
    private Long shopId;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    @NotBlank(message = "企业名称不能为空")
    private String enterpriseName;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value = "统一社会信用代码")
    @NotBlank(message = "统一社会信用代码不能为空")
    private String enterpriseCode;

    /**
     * 地址  省-市-区
     */
    @ApiModelProperty(value = "地址  省-市-区")
    @NotBlank(message = "地址不能为空")
    private String enterpriseRegion;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String enterpriseAdress;

    /**
     * 营业期限开始时间
     */
    @ApiModelProperty(value = "营业期限开始时间")
    @NotBlank(message = "营业期限开始时间不能为空")
    private String enterpriseStartTime;

    /**
     * 营业期限结束时间
     */
    @ApiModelProperty(value = "营业期限结束时间")
    @NotBlank(message = "营业期限结束时间不能为空")
    private String enterpriseEndTime;

    /**
     * 营业执照图片地址（多个以逗号隔开）
     */
    @ApiModelProperty(value = "营业执照图片地址（多个以逗号隔开）")
    @NotBlank(message = "营业执照图片不能为空")
    private String enterpriseLicense;

    /**
     * 法人姓名
     */
    @ApiModelProperty(value = "法人姓名")
    @NotBlank(message = "法人姓名不能为空")
    private String enterpriseOperator;

    /**
     * 证件类型 （取数据字典）
     */
    @ApiModelProperty(value = "证件类型 （取数据字典）")
    @NotNull(message = "证件类型不能为空")
    private Long enterpriseCardType;

    /**
     * 法人身份证号码
     */
    @ApiModelProperty(value = "法人身份证号码")
    @NotBlank(message = "法人身份证号码不能为空")
    private String enterpriseIdCard;

    /**
     * 证件有效开始时间
     */
    @ApiModelProperty(value = "证件有效开始时间")
    @NotBlank(message = "证件有效开始时间不能为空")
    private String enterpriseCardStartTime;

    /**
     * 证件有效结束时间
     */
    @ApiModelProperty(value = "证件有效结束时间")
    @NotBlank(message = "证件有效结束时间不能为空")
    private String enterpriseCardEndTime;

    /**
     * 法人身份证正面照
     */
    @ApiModelProperty(value = "法人身份证正面照")
    @NotBlank(message = "法人身份证正面照不能为空")
    private String enterpriseCardPositive;

    /**
     * 法人身份证反面照
     */
    @ApiModelProperty(value = "法人身份证反面照")
    @NotBlank(message = "法人身份证反面照不能为空")
    private String enterpriseCardSide;

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
     * 开户银行（取数据字典）
     */
    @ApiModelProperty(value = "开户银行（取数据字典）")
    private Long accountOpenBank;

    /**
     * 开户银行名称
     */
    @ApiModelProperty(value = "开户银行名称")
    private String accountBankName;

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
