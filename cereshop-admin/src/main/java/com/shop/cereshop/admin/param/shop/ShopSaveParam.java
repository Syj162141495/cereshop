/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加商家请求
 */
@Data
@ApiModel(value = "ShopSaveParam", description = "添加服务商请求")
public class ShopSaveParam {

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "服务商名称")
    @NotBlank(message = "服务商称不能为空")
    private String shopName;

    /**
     * 店铺账号
     */
    @ApiModelProperty(value = "服务商账号")
    @NotBlank(message = "服务商账号不能为空")
    private String shopPhone;

    /**
     * 店铺密码
     */
    @ApiModelProperty(value = "服务商密码")
    @NotBlank(message = "服务商密码不能为空")
    private String shopPassword;

    /**
     * 店铺负责人
     */
    @ApiModelProperty(value = "服务商联系人")
    @NotBlank(message = "服务商联系人不能为空")
    private String chargePersonName;

    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "联系人电话")
    @NotBlank(message = "联系人电话不能为空")
    private String chargePersonPhone;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
//    @NotBlank(message = "店铺地址不能为空")
    private String shopAdress;

    @ApiModelProperty(value = "机构地址")
    @NotBlank(message = "机构地址不能为空")
    private String address;

    @ApiModelProperty(value = "城市")
    @NotBlank(message = "城市不能为空")
    private String city;

    @ApiModelProperty(value = "地区")
    @NotBlank(message = "地区不能为空")
    private String area;

    @ApiModelProperty(value = "服务类型")
    @NotBlank(message = "服务类型不能为空")
    private String serviceClassify;

    @ApiModelProperty(value = "坐标X")
    @NotBlank(message = "坐标X不能为空")
    private String coordinateX;

    @ApiModelProperty(value = "坐标Y")
    @NotBlank(message = "坐标Y不能为空")
    private String coordinateY;

    @ApiModelProperty(value = "机构类型")
    @NotBlank(message = "机构类型不能为空")
    private String institutionalClassify;

    @ApiModelProperty(value = "机构等级")
    @NotBlank(message = "机构等级不能为空")
    private String institutionalGrade;

    @ApiModelProperty(value = "信用代码")
    @NotBlank(message = "信用代码不能为空")
    private String reditCode;

    @ApiModelProperty(value = "机构简介")
    @NotBlank(message = "机构简介不能为空")
    private String introduction;

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
}
