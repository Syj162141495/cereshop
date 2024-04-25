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
 * cere_shop_personal 店铺个人认证信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopPersonal", description = "店铺个人认证信息实体类")
public class CereShopPersonal implements Serializable {
    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 个人姓名
     */
    @ApiModelProperty(value = "个人姓名")
    private String personalName;

    /**
     * 证件类型 （取数据字典）
     */
    @ApiModelProperty(value = "证件类型 （取数据字典）")
    @NotNull(message = "证件类型不能为空")
    private Long personalCardType;

    /**
     * 法人身份证号码
     */
    @ApiModelProperty(value = "法人身份证号码")
    @NotBlank(message = "法人身份证号码不能为空")
    private String personalIdCard;

    /**
     * 证件有效开始时间
     */
    @ApiModelProperty(value = "证件有效开始时间")
    @NotBlank(message = "证件有效开始时间不能为空")
    private String personalCardStartTime;

    /**
     * 证件有效结束时间
     */
    @ApiModelProperty(value = "证件有效结束时间")
    @NotBlank(message = "证件有效结束时间不能为空")
    private String personalCardEndTime;

    /**
     * 法人身份证正面照
     */
    @ApiModelProperty(value = "法人身份证正面照")
    @NotBlank(message = "法人身份证正面照不能为空")
    private String personalCardPositive;

    /**
     * 法人身份证反面照
     */
    @ApiModelProperty(value = "法人身份证反面照")
    @NotBlank(message = "法人身份证反面照不能为空")
    private String personalCardSide;

    /**
     * 手持证件照
     */
    @ApiModelProperty(value = "手持证件照")
    @NotBlank(message = "手持证件照不能为空")
    private String personalCardHand;

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
    @NotBlank(message = "客服电话不能为空")
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
