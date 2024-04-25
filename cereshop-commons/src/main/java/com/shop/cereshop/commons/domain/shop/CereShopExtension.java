/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_shop_extension 店铺推广设置信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereShopExtension", description = "店铺推广设置信息实体类")
public class CereShopExtension implements Serializable {
    /**
     * 推广设置id
     */
    @ApiModelProperty(value = "推广设置id 有值就是更新,null就是新增")
    private Long extensionId;

    /**
     * 推广标题
     */
    @ApiModelProperty(value = "推广标题")
    private String title;

    /**
     * 关联店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 店铺logo是否展示在二维码 1-是 0-否
     */
    @ApiModelProperty(value = "店铺logo是否展示在二维码 1-是 0-否")
    private Integer ifLogo;

    /**
     * 是否展示头像昵称 1-是 0-否
     */
    @ApiModelProperty(value = "是否展示头像昵称 1-是 0-否")
    private Integer ifHead;

    /**
     * 推广语
     */
    @ApiModelProperty(value = "推广语")
    private String extensionReason;

    /**
     * 背景图片地址 875x1275像素
     */
    @ApiModelProperty(value = "背景图片地址 875x1275像素")
    private String image;

    /**
     * 海报图片地址
     */
    @ApiModelProperty(value = "海报图片地址")
    private String poster;

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
