/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.collage;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_collage_order 拼单信息实体
 * @author 
 */
@Data
public class CereCollageOrder implements Serializable {
    /**
     * 拼团单id
     */
    private Long collageId;

    /**
     * 拼团活动id
     */
    private Long shopGroupWorkId;

    /**
     * 拼单状态，0-待成团，1-拼团成功，2-拼团失败
     */
    private Integer state;

    /**
     * 拼团名称
     */
    private String collageName;

    /**
     * 发起人用户ID
     */
    private Long buyerUserId;

    /**
     * 剩余人数
     */
    private Integer surplusNumber;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;
}
