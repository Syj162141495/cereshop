/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.member;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_member_level 会员等级实体
 * @author 
 */
@Data
public class CerePlatformMemberLevel implements Serializable {
    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 等级名称
     */
    private String memberLevelName;

    /**
     * 等级图标地址
     */
    private String memberLevelIcon;

    /**
     * 等级背景图地址
     */
    private String memberLevelBackground;

    /**
     * 成长值
     */
    private Integer growth;

    /**
     * 会员权益id(多个以逗号隔开)
     */
    private String memberIds;

    /**
     * 会员等级说明
     */
    private String memberLevelReason;

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
