/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.express;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 快递100物流轨迹实体封装
 */
@ApiModel("快递轨迹对象")
@Data
public class ShippingDTO {

	@ApiModelProperty("快递公司编码")
	private String shipperCode;

	@ApiModelProperty("快递单号")
	private String logisticCode;

	@ApiModelProperty("快递轨迹列表")
	private List<ShippingTrace> traces;

}

