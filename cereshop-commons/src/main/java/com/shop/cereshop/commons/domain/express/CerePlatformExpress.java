/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.express;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_express 物流查询策略实体
 * @author 
 */
@Data
public class CerePlatformExpress implements Serializable {
    /**
     * 操作人用户id   初始化为空
     */
    private Long platformUserId;

    /**
     * 对接第三方快递类型 1-快递100 2-快递鸟
     */
    private Integer expressType;

    /**
     * 更新时间
     */
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
