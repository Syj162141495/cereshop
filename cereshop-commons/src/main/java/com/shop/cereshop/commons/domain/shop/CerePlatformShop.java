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
import java.io.Serializable;

/**
 * cere_platform_shop 商家信息实体类
 * @author
 */
@Data
@ApiModel(value = "CerePlatformShop", description = "商家信息实体类")
public class CerePlatformShop implements Serializable {

    /**
     * 商家id
     */
    @ApiModelProperty(value = "商家id")
    private Long shopId;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String shopCode;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String shopBrief;

    /**
     * 店铺账号
     */
    @ApiModelProperty(value = "店铺账号")
    private String shopPhone;

    /**
     * 店铺密码
     */
    @ApiModelProperty(value = "店铺密码")
    private String shopPassword;

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
     * 店铺地址省份
     */
    @ApiModelProperty(value = "店铺地址省份")
    private String shopAdressProvince;

    /**
     * 店铺地址城市
     */
    @ApiModelProperty(value = "店铺地址城市")
    private String shopAdressCity;

    /**
     * 店铺地址-详细地址
     */
    @ApiModelProperty(value = "店铺地址-详细地址")
    private String shopAdressDetail;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String shopAdress;

    /**
     * 店铺logo
     */
    @ApiModelProperty(value = "店铺logo")
    private String shopLogo;

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
     * 合同状态 1-有效 0-无效
     */
    @ApiModelProperty(value = "合同状态 1-有效 0-无效")
    private Integer contractState;

    /**
     * 认证状态 1-未认证 2-认证中 3-已认证
     */
    @ApiModelProperty(value = "认证状态 1-未认证 2-认证中 3-已认证")
    private Integer authenticationState;

    /**
     * 入驻处理状态  0-未处理 1-通过 2-拒绝
     */
    @ApiModelProperty(value = "入驻处理状态  0-未处理 1-通过 2-拒绝")
    private Integer checkState;

    /**
     * 是否启用 1-是 0-否
     */
    @ApiModelProperty(value = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 主体类型
     */
    @ApiModelProperty(value = "主体类型")
    private Integer authenType;

    /**
     * 微信收款码
     */
    @ApiModelProperty(value = "微信收款码")
    private String wxImage;

    /**
     * 支付宝收款码
     */
    @ApiModelProperty(value = "支付宝收款码")
    private String ailpayImage;

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
