/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.user;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_user_role 平台账户关联角色实体类
 * @author 
 */
@Data
public class CerePlatformUserRole implements Serializable {
    /**
     * 平台账户编号
     */
    private Long platformUserId;

    /**
     * 关联角色表id
     */
    private Long roleId;

    private static final long serialVersionUID = 1L;

}
