/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.member;

import com.shop.cereshop.admin.page.member.MemberLevel;
import com.shop.cereshop.admin.param.member.MemberLevelSaveParam;
import com.shop.cereshop.admin.param.member.MemberLevelUpdateParam;
import com.shop.cereshop.admin.param.member.MemberLevelgetByIdParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformMemberLevelService {
    void save(MemberLevelSaveParam param, CerePlatformUser user) throws CoBusinessException;

    void update(MemberLevelUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(MemberLevelgetByIdParam param, CerePlatformUser user) throws CoBusinessException;

    MemberLevel getById(MemberLevelgetByIdParam param) throws CoBusinessException;

    Page getAll(PageParam param) throws CoBusinessException;
}
