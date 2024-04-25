/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.pay;

import com.shop.cereshop.admin.pay.alipay.service.AliPayService;
import com.shop.cereshop.admin.pay.weixin.service.WxPayService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.SpringUtil;

public class PayFactory {

    public static PayService getPayService(Integer paymentMode) throws CoBusinessException {
        if (paymentMode != null) {
            if (paymentMode.equals(IntegerEnum.ORDER_PAY_WX.getCode())) {
                return SpringUtil.getBean(WxPayService.class);
            } else if (paymentMode.equals(IntegerEnum.ORDER_PAY_ALI.getCode())) {
                return SpringUtil.getBean(AliPayService.class);
            }
        }
        throw new CoBusinessException(CoReturnFormat.SYS_ERROR);
    }

}
