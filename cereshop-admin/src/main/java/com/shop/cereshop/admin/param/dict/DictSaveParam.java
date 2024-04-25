/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加字典请求
 */
@Data
@ApiModel(value = "DictSaveParam", description = "添加字典请求")
public class DictSaveParam {

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private Long dictPid;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述")
    private String dictDescribe;
}
