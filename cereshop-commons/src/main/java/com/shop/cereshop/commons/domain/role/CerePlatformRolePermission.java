/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.role;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_role_permission 平台账户角色关联权限实体类
 * @author 
 */
@Data
public class CerePlatformRolePermission implements Serializable {
    /**
     * 关联角色id
     */
    private Long roleId;

    /**
     * 关联权限id
     */
    private Long permissionId;

    private static final long serialVersionUID = 1L;

}
