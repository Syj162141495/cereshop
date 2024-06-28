package com.shop.cereshop.admin.page.index;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "OrderInfo", description = "首页订单信息实体")
public class OrderInfo {

    private String orderId;

    private String productName;

    private BigDecimal price;

    private String state;

    private String createTime;

}
