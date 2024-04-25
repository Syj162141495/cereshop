/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.finance;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 财务数据返回实体类
 */
@Data
public class Finance {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商家名称
     */
    private String shopName;

    /**
     * 商家编码
     */
    private String shopCode;

    /**
     * 营收总额
     */
    private BigDecimal revenue;

    /**
     * 冻结中金额
     */
    private BigDecimal frozen;

    /**
     * 已提现金额
     */
    private BigDecimal haveWithdrawable;

    /**
     * 已退款金额
     */
    private BigDecimal refund;
}
