/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.product;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_comment_word 关键词关联评论信息实体
 * @author 
 */
@Data
public class CereCommentWord implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 关键词id
     */
    private Long wordId;

    /**
     * 关键词
     */
    private String keyWord;

    private static final long serialVersionUID = 1L;

}
