/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.notice;

import com.shop.cereshop.admin.param.notice.NoticeGetAllParam;
import com.shop.cereshop.admin.param.notice.NoticeIdParam;
import com.shop.cereshop.admin.param.notice.NoticeParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereNoticeService {
    void save(NoticeParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(NoticeIdParam param, CerePlatformUser user) throws CoBusinessException;

    CereNotice getById(Long noticeId) throws CoBusinessException;

    Page getAll(NoticeGetAllParam param) throws CoBusinessException;

    List<CereNotice> getNotices() throws CoBusinessException;
}
