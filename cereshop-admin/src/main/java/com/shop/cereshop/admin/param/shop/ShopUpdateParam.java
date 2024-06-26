/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新商家请求
 */
@Data
@ApiModel(value = "ShopUpdateParam", description = "更新商家请求")
public class ShopUpdateParam {

    /**
     * 商家用户id
     */
    @ApiModelProperty(value = "商家用户id")
    private Long businessUserId;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

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
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String shopAdress;

    @ApiModelProperty(value = "机构地址")
    private String address;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "地区")
    private String area;

    @ApiModelProperty(value = "服务商大类")
    private String providersMajor;

    @ApiModelProperty(value = "关联分类id")
    private Long classifyId;

    @ApiModelProperty(value = "服务商小类")
    private String providersSubclass;

    @ApiModelProperty(value = "医疗联合")
    private String medicalcollaboration;

    @ApiModelProperty(value = "服务类型")
    private String serviceClassify;

    @ApiModelProperty(value = "坐标X")
    private String coordinateX;

    @ApiModelProperty(value = "坐标Y")
    private String coordinateY;

    @ApiModelProperty(value = "机构类型")
    private String institutionalClassify;

    @ApiModelProperty(value = "机构等级")
    private String institutionalGrade;

    @ApiModelProperty(value = "信用代码")
    private String reditCode;

    @ApiModelProperty(value = "机构简介")
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
