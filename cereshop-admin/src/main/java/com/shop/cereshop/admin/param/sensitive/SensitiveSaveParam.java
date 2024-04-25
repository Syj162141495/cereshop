/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.sensitive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加敏感词设置
 */
@Data
@ApiModel(value = "SensitiveSaveParam", description = "添加敏感词设置")
public class SensitiveSaveParam {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 敏感词库
     */
    @ApiModelProperty(value = "敏感词库")
    @NotBlank(message = "敏感词库不能为空")
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
}
