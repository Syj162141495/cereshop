/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除短信配置请求
 */
@Data
@ApiModel(value = "MessageDeleteParam", description = "删除短信配置请求")
public class MessageDeleteParam {

    /**
     * 短信id
     */
    @ApiModelProperty(value = "短信id")
    private Long messageId;
}
