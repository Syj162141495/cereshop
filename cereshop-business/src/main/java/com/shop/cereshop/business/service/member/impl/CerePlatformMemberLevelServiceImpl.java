/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.member.impl;

import com.shop.cereshop.business.dao.member.CerePlatformMemberLevelDAO;
import com.shop.cereshop.business.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePlatformMemberLevelServiceImpl implements CerePlatformMemberLevelService {

    @Autowired
    private CerePlatformMemberLevelDAO cerePlatformMemberLevelDAO;

    @Override
    public List<CerePlatformMemberLevel> findAll() {
        return cerePlatformMemberLevelDAO.findAll();
    }
}
