/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.permission;

import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 同步菜单的参数
 */
@Data
@ApiModel(value = "同步菜单的参数")
public class SyncMenuParam {

    @ApiModelProperty("同步范围 true-同步所有商家 false-同步指定商家")
    private boolean syncAll;

    @ApiModelProperty("是否删除菜单")
    private boolean delMenu;

    @ApiModelProperty("指定商家账号")
    private String syncBusinessUsername;

    @ApiModelProperty("需要同步的菜单列表")
    private List<CerePlatformPermission> syncMenu;

}
