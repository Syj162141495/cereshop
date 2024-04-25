/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum LongEnum {

    ROOT("根节点",0l),
    ZERO("0",0l),
    PLATFORM("平台端权限或角色",1l),
    EXPRESS_100("快递100",7l),
    EXPRESS_NIAO("快递鸟",8l);

    String name;
    Long code;


    private static Map<String, LongEnum> valueMap = new HashMap<>();

    static {
        for(LongEnum gender : LongEnum.values()) {
            valueMap.put(gender.name, gender);
        }
    }

    LongEnum(String name,Long code) {
        this.code = code;
        this.name=name;
    }

    public static Long getByName(String name) {
        LongEnum result = valueMap.get(name);
        if(result == null) {
            throw new IllegalArgumentException("No element matches " + name);
        }
        return result.code;
    }
}
