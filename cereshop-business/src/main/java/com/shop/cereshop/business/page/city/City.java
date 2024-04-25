/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.page.city;

import com.shop.cereshop.commons.domain.city.CereCityManage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 城市返回数据实体类
 */
@Data
@ApiModel(value = "City", description = "城市返回数据实体类")
public class City extends CereCityManage {

    /**
     * 子级数据
     */
    @ApiModelProperty(value = "子级数据")
    private List<City> childs;
}
