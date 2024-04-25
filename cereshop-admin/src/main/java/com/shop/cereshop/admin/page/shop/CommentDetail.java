/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 评论详情返回数据
 */
@Data
@ApiModel(value = "CommentDetail", description = "评论详情返回数据")
public class CommentDetail {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Long commentId;

    /**
     * 图片地址（多个以逗号隔开）
     */
    @ApiModelProperty(value = "图片地址（多个以逗号隔开）")
    private String image;

    /**
     * 追加图片
     */
    @ApiModelProperty(value = "追加图片")
    private String addImage;

    /**
     * 评论
     */
    @ApiModelProperty(value = "评论")
    private String comment;

    /**
     * 追加评论
     */
    @ApiModelProperty(value = "追加评论")
    private String addComment;

    /**
     * 图片数组
     */
    @ApiModelProperty(value = "图片数组")
    private List<String> images;

    /**
     * 追加图片数组
     */
    @ApiModelProperty(value = "追加图片数组")
    private List<String> addImages;

    /**
     * 敏感词
     */
    @ApiModelProperty(value = "敏感词")
    private String sensitiveWord;

    /**
     * 追评时间差
     */
    @ApiModelProperty(value = "追评时间差")
    private Integer time;

    /**
     * 评论时间
     */
    private String createTime;

    /**
     * 追评时间
     */
    private String addTime;
}
