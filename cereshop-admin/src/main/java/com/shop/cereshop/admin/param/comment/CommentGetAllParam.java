/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.comment;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取评论列表请求
 */
@Data
@ApiModel(value = "CommentGetAllParam", description = "获取评论列表请求")
public class CommentGetAllParam extends PageParam {

    /**
     * 是否敏感词审核  1-是 0-否
     */
    @ApiModelProperty(value = "是否敏感词审核  1-是 0-否")
    private Integer ifSensitive;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String shopName;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String shopCode;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String productId;

    /**
     * 关键词
     */
    @ApiModelProperty(value = "关键词")
    private String keyword;
}
