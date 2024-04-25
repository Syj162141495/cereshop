/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.dict;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取字典子节点请求
 */
@Data
@ApiModel(value = "DictGetChildsParam", description = "获取字典子节点请求")
public class DictGetChildsParam extends PageParam {

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private Long dictPid;
}
