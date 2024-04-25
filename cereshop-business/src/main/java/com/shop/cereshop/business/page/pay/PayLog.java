/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.pay;

import com.shop.cereshop.commons.domain.pay.CerePayLog;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 支付流水退款数据
 */
@Data
@ApiModel(value = "PayLog", description = "支付流水退款数据")
public class PayLog extends CerePayLog {

    /**
     * 订单id
     */
    private Long orderId;
}
