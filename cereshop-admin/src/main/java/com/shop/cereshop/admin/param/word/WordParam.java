/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.word;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 关键词请求
 */
@Data
@ApiModel(value = "WordParam", description = "关键词请求")
public class WordParam extends PageParam {

    /**
     * 搜索字段
     */
    private String search;
}
