/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.city;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_city_manage 城市管理实体类
 * @author 
 */
@Data
@ApiModel(value = "CereCityManage", description = "城市管理实体类")
public class CereCityManage implements Serializable {
    /**
     * 城市id
     */
    @ApiModelProperty(value = "城市id")
    private Long cityId;

    /**
     * 上级节点id
     */
    @ApiModelProperty(value = "上级节点id")
    private Long cityPid;

    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    private String cityName;

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
