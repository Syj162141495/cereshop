/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_comment_like 客户评论点赞信息实体
 * @author 
 */
@Data
public class CereBuyerCommentLike implements Serializable {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 点赞客户id
     */
    private Long buyerUserId;

    /**
     * 是否点赞 1-是 0-否
     */
    private Integer ifLike;

    private static final long serialVersionUID = 1L;

}
