/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.city;

import com.shop.cereshop.business.page.city.City;
import com.shop.cereshop.business.param.logistics.LogistCityParam;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereCityManageService {
    List<City> getCitySelect(LogistCityParam param) throws CoBusinessException;
}
