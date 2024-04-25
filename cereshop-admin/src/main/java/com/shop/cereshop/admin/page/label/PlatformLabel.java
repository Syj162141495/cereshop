/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.label;

import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import lombok.Data;
import java.util.List;

/**
 * 标签返回数据实体类
 */
@Data
public class PlatformLabel extends CerePlatformLabel {

    /**
     * 条件
     */
    private List<String> conditions;

    /**
     * 客户数
     */
    private Integer users;
}
