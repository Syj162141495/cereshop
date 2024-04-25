/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.city.impl;

import com.shop.cereshop.business.dao.city.CereCityManageDAO;
import com.shop.cereshop.business.page.city.City;
import com.shop.cereshop.business.param.logistics.LogistCityParam;
import com.shop.cereshop.business.service.city.CereCityManageService;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CereCityManageServiceImpl implements CereCityManageService {

    @Autowired
    private CereCityManageDAO cereCityManageDAO;

    @Override
    public List<City> getCitySelect(LogistCityParam param) throws CoBusinessException {
        return cereCityManageDAO.getCitySelect(param);
    }
}
