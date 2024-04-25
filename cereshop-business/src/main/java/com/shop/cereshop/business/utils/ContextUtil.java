/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.utils;

import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.stereotype.Component;

@Component
public class ContextUtil {

    static ThreadLocal<Long> shopTL = new ThreadLocal<>();

    public static void setShopId(Long shopId){
        shopTL.set(shopId);
    }

    public static void clearShopId(){
        shopTL.set(null);
    }

    public static Long getShopId() throws CoBusinessException {
        Long shopId = shopTL.get();
        if (shopId == null) {
            throw new CoBusinessException(CoReturnFormat.USER_NOT_LOGIN);
        }
        return shopTL.get();
    }

}
