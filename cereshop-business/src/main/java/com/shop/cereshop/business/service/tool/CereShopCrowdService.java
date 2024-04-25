/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.tool.ShopCrowdDetail;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopCrowdService {
    void save(ShopCrowdSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(ShopCrowdUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(ShopCrowdGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopCrowdDetail getById(Long shopCrowdId) throws CoBusinessException;

    Page getAll(ShopCrowdGetAllParam param) throws CoBusinessException;

    List<BuyerUser> getUsers(Long shopCrowdId) throws CoBusinessException;

    String findUserIds(Long shopCrowdId);

    Page selectCrowd(ShopCrowdGetAllParam param) throws CoBusinessException;

    List<CereShopCrowd> findAll();

    List<String> findUserByShopBuyCondition(CrowdCondition condition);

    List<String> findNoBuy(CrowdCondition condition);

    List<String> findUserByShopOrderCondition(CrowdCondition condition);

    List<String> findNoOrder(CrowdCondition condition);

    List<String> findUserByShopAddCondition(CrowdCondition condition);

    List<String> findUserByShopVisitCondition(CrowdCondition condition);

    List<String> findNoVisit(CrowdCondition condition);

    List<String> findUserByCountCondition(CrowdCondition condition);

    List<String> findUserByPriceCondition(CrowdCondition condition);

    List<String> findUserByLabelCondition(CrowdCondition condition);

    void updateBatch(List<CereShopCrowd> updates) throws CoBusinessException;
}
