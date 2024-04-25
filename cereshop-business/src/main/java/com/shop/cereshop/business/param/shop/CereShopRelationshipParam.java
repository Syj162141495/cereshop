/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.shop;

import com.shop.cereshop.commons.domain.common.PageParam;
import lombok.Data;

/**
 * 关系设置接收参数实体类
 */
@Data
public class CereShopRelationshipParam extends PageParam {

    /**
     * 客户昵称
     */
    private String name;

    /**
     * 客户手机号
     */
    private String phone;

    /**
     * 分销员昵称
     */
    private String distributorName;

    /**
     * 绑定状态 是否绑定 1-是 0-否
     */
    private Integer ifBind;
}
