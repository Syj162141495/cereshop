/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.notice;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_notice 客户已读公告、站内信消息关系实体
 * @author 
 */
@Data
public class CereBuyerNotice implements Serializable {
    /**
     * 消息id
     */
    private Long noticeId;

    /**
     * 客户id
     */
    private Long buyerUserId;

}
