/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_log 平台系统日志实体类
 * @author 
 */
@Data
public class CerePlatformLog implements Serializable {

    /**
     * 日志id
     */
    @ApiModelProperty(value = "日志id")
    private Long logId;

    /**
     * 关联用户id
     */
    @ApiModelProperty(value = "关联用户id")
    private Long platformUserId;

    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String module;

    /**
     * 操作对应工程
     */
    @ApiModelProperty(value = "操作对应工程")
    private String operation;

    /**
     * 操作功能描述
     */
    @ApiModelProperty(value = "操作功能描述")
    private String operationDescribtion;

    /**
     * 操作功能对应唯一标识id
     */
    @ApiModelProperty(value = "操作功能对应唯一标识id")
    private String only;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private String createTime;

    private static final long serialVersionUID = 1L;

}
