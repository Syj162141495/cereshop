/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.platformtool.impl;

import com.shop.cereshop.business.dao.platformtool.CerePlatformPoliteActivityDAO;
import com.shop.cereshop.business.service.platformtool.CerePlatformPoliteActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CerePlatformPoliteActivityServiceImpl implements CerePlatformPoliteActivityService {

    @Autowired
    private CerePlatformPoliteActivityDAO cerePlatformPoliteActivityDAO;
}
