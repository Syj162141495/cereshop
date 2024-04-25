/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.sensitive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_sensitive 敏感词库实体类
 * @author 
 */
@Data
@ApiModel(value = "CerePlatformSensitive", description = "敏感词库实体类")
public class CerePlatformSensitive implements Serializable {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 敏感词库
     */
    @ApiModelProperty(value = "敏感词库")
    private String sensitiveWord;

    /**
     * 是否开启 1-是 0-否
     */
    @ApiModelProperty(value = "是否开启 1-是 0-否")
    private Integer state;

    /**
     * 处理措施  1-禁止发布 2-需审核
     */
    @ApiModelProperty(value = "处理措施  1-禁止发布 2-需审核")
    private Integer handleMeasures;

    private static final long serialVersionUID = 1L;

}
