/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.comment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新评论请求
 */
@Data
@ApiModel(value = "CommentUpdateParam", description = "更新评论请求")
public class CommentUpdateParam {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Long commentId;

    /**
     * 是否隐藏 1-是 0-否
     */
    @ApiModelProperty(value = "是否隐藏 1-是 0-否")
    private Integer state;
}
