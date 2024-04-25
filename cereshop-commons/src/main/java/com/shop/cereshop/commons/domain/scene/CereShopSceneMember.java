/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.scene;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * cere_shop_scene_member 商家场景营销规则信息实体
 * @author 
 */
@Data
public class CereShopSceneMember implements Serializable {
    /**
     * 场景营销id
     */
    private Long sceneId;

    /**
     * 会员等级id
     */
    private Long memberLevelId;

    /**
     * 是否包邮 1-是 0-否
     */
    private Integer ifFreeShipping;

    /**
     * 折扣
     */
    private BigDecimal discount;

    private static final long serialVersionUID = 1L;

}
