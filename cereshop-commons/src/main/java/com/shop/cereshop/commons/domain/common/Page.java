/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    /**
     * 返回数据总数
     */
    private long total;

    /**
     * 封装数据对象
     */
    private List<T> list;

    public Page(List<T> list, long total) {
        this.list=list;
        this.total=total;
    }
}
