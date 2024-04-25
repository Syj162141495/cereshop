/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.express;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 快递100物流轨迹明细实体
 */
@ApiModel("快递轨迹")
@Data
public class ShippingTrace implements Serializable {


	@ApiModelProperty("到达站点")
	private String acceptStation;

	@ApiModelProperty("到达时间")
	private String acceptTime;

}
