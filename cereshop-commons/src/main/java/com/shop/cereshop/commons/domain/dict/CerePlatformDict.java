/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_dict 数据字典实体类
 * @author 
 */
@Data
@ApiModel(value = "CerePlatformDict", description = "数据字典实体类")
public class CerePlatformDict implements Serializable {

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
