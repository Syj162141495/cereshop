/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.member;

import com.shop.cereshop.admin.param.member.MembershipGetByIdParam;
import com.shop.cereshop.admin.param.member.MembershipSaveParam;
import com.shop.cereshop.admin.param.member.MembershipUpdateParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.member.CerePlatformMembership;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformMembershipService {
    void save(MembershipSaveParam param, CerePlatformUser user) throws CoBusinessException;

    void update(MembershipUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    CerePlatformMembership getById(MembershipGetByIdParam param) throws CoBusinessException;

    void delete(MembershipGetByIdParam param, CerePlatformUser user) throws CoBusinessException;

    Page getAll(PageParam param) throws CoBusinessException;
}
