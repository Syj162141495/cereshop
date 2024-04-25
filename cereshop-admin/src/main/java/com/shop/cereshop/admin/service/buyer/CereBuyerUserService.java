/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.buyer;

import com.shop.cereshop.admin.page.buyer.BuyerUserDetail;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.buyer.*;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereBuyerUserService {
    Page getAll(BuyerGetAllParam param) throws CoBusinessException;

    BuyerUserDetail getById(BuyerGetByIdParam param) throws CoBusinessException;

    List<CerePlatformLabel> getLabels(BuyerGetLabelsParam param) throws CoBusinessException;

    void saveUserLabel(BuyerSaveUserLabelParam param, CerePlatformUser user) throws CoBusinessException;

    void blacklist(BuyerBlackListParam param, CerePlatformUser user) throws CoBusinessException;

    List<CerePlatformLabel> getUserLabels(BuyerGetLabelsParam param) throws CoBusinessException;
}
