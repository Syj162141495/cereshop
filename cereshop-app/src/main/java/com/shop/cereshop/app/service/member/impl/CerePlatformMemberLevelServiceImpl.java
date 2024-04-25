/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.member.impl;

import com.shop.cereshop.app.dao.member.CerePlatformMemberLevelDAO;
import com.shop.cereshop.app.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CerePlatformMemberLevelServiceImpl implements CerePlatformMemberLevelService {

    @Autowired
    private CerePlatformMemberLevelDAO cerePlatformMemberLevelDAO;

    @Override
    public CerePlatformMemberLevel selectByMemberLevelId(Long memberLevelId) {
        return cerePlatformMemberLevelDAO.selectByPrimaryKey(memberLevelId);
    }

    @Override
    public CerePlatformMemberLevel selectNextLevel(Long memberLevelId) {
        return cerePlatformMemberLevelDAO.selectNextLevel(memberLevelId);
    }

    @Override
    public CerePlatformMemberLevel selectFirstLevel() {
        return cerePlatformMemberLevelDAO.selectFirstLevel();
    }


}
