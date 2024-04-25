/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.business;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_business_user_role 商家用户关联角色实体类
 * @author 
 */
@Data
public class CereBusinessUserRole implements Serializable {
    /**
     * 商家用户id
     */
    private Long businessUserId;

    /**
     * 关联角色表id
     */
    private Long roleId;

    private static final long serialVersionUID = 1L;

}
