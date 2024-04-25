/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.shop.CommentDetail;
import com.shop.cereshop.admin.param.comment.CommentDeleteParam;
import com.shop.cereshop.admin.param.comment.CommentGetAllParam;
import com.shop.cereshop.admin.param.comment.CommentUpdateParam;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CereShopCommentService {
    Page getAll(CommentGetAllParam param) throws CoBusinessException;

    CommentDetail getById(Long commentId) throws CoBusinessException,Exception;

    void update(CommentUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(CommentDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    void allow(CommentUpdateParam param, CerePlatformUser user) throws CoBusinessException;
}
