/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除消息请求
 */
@Data
@ApiModel(value = "NoticeIdParam", description = "删除消息请求")
public class NoticeIdParam {

    /**
     * 消息id
     */
    @ApiModelProperty(value = "消息id")
    private Long noticeId;
}
