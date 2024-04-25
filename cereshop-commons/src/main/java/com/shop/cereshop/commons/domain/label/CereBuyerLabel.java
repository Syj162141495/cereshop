/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.label;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_label 客户关联标签信息实体类
 * @author 
 */
@Data
public class CereBuyerLabel implements Serializable {
    /**
     * 关联客户id
     */
    private Long buyerUserId;

    /**
     * 关联平台标签id
     */
    private Long buyerLabelId;

    private static final long serialVersionUID = 1L;

}
