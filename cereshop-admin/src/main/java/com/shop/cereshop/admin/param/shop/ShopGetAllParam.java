/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shop;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取商家列表请求
 */
@Data
@ApiModel(value = "ShopGetAllParam", description = "获取商家列表请求")
public class ShopGetAllParam extends PageParam {

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "服务商名称")
    private String shopName;

    /**
     * 店铺编码
     */
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "地区")
    private String area;

    @ApiModelProperty(value = "服务类型")
    private String serviceClassify;

    @ApiModelProperty(value = "机构类型")
    private String institutionalClassify;

    @ApiModelProperty(value = "机构等级")
    private String institutionalGrade;

    @ApiModelProperty(value = "机构地址")
    private String address;

    @ApiModelProperty(value = "信用代码")
    private String reditCode;
    /**
     * 店铺负责人
     */
    @ApiModelProperty(value = "联系人")
    private String chargePersonName;

    @ApiModelProperty(value = "联系电话")
    private String chargePersonPhone;

    @ApiModelProperty(value = "坐标X")
    private String coordinateX;

    @ApiModelProperty(value = "坐标Y")
    private String coordinateY;

    @ApiModelProperty(value = "机构简介")
    private String introduction;

    /**
     * 合同状态 1-有效 0-无效
     */
    @ApiModelProperty(value = "合同状态 1-有效 0-无效")
    private Integer contractState;
}
