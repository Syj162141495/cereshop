/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.product;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取商品列表请求
 */
@Data
@ApiModel(value = "ProductGetAllParam", description = "获取商品列表请求")
public class ProductGetAllParam extends PageParam {

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称")
    private String shopName;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String productId;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品类型")
    private String productType;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String productName;

    /**
     * 商品状态 0-已下架 1-已上架 2-待审核 3-审核失败
     */
    @ApiModelProperty(value = "商品状态 0-已下架 1-已上架 2-待审核 3-审核失败")
    private Integer shelveState;

    /**
     * 创建时间数组
     */
    @ApiModelProperty(value = "下单时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }

    /**
     * 创建开始时间
     */
    @ApiModelProperty(value = "发送开始时间")
    private String startTime;

    /**
     * 创建结束时间
     */
    @ApiModelProperty(value = "发送结束时间")
    private String endTime;
}
