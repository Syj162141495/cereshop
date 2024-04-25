/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新字典请求
 */
@Data
@ApiModel(value = "DictUpdateParam", description = "更新字典请求")
public class DictUpdateParam {

    /**
     * 字典id
     */
    @ApiModelProperty(value = "字典id")
    private Long dictId;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private Long dictPid;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典描述
     */
    @ApiModelProperty(value = "字典描述")
    private String dictDescribe;
}
