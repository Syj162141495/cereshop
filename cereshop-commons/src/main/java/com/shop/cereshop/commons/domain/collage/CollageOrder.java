/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.collage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拼单数据
 */
@Data
@ApiModel(value = "CollageOrder", description = "拼单数据")
public class CollageOrder implements Serializable {

    private static final long serialVersionUID = -929653124649214935L;
    /**
     * 拼单id
     */
    @ApiModelProperty(value = "拼单id")
    private Long collageId;

    /**
     * 发起人名称
     */
    @ApiModelProperty(value = "发起人名称")
    private String name;

    /**
     * 发起人头像
     */
    @ApiModelProperty(value = "发起人头像")
    private String headImage;

    /**
     * 剩余拼团人数
     */
    @ApiModelProperty(value = "剩余拼团人数")
    private Integer person;

    /**
     * 成团倒计时
     */
    @ApiModelProperty(value = "成团倒计时")
    private long time;

    /**
     * 发起拼单时间
     */
    @ApiModelProperty(value = "发起拼单时间")
    private String createTime;
}
