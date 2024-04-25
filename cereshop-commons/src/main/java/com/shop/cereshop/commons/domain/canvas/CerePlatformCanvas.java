/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.canvas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_canvas 画布信息实体
 * @author
 */
@Data
@ApiModel("画布信息实体")
public class CerePlatformCanvas implements Serializable {
    /**
     * 画布id
     */
    @ApiModelProperty("画布id")
    private Long canvasId;

    /**
     * 终端 1-小程序 2-H5 3-APP 4-PC
     */
    @ApiModelProperty("终端 1-小程序 2-H5 3-APP 4-PC")
    private Integer terminal;

    /**
     * 画布json数据
     */
    @ApiModelProperty("画布json数据")
    private String json;

    /**
     * 类型 1-系统相关页面 2-自定义页面 3-店铺装修
     */
    @ApiModelProperty("类型 1-系统相关页面 2-自定义页面 3-店铺装修")
    private Integer type;

    /**
     * 页面名称
     */
    @ApiModelProperty("页面名称")
    private String name;

    /**
     * 店铺id
     */
    @ApiModelProperty("店铺id")
    private Long shopId;

    /**
     * 自定也页面id
     */
    @ApiModelProperty("自定义页面id")
    private Long customId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
