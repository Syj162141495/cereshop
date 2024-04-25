/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor;

import com.shop.cereshop.business.page.distribution.ShopDistributor;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.param.shopDistributor.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopDistributorService {
    void save(ShopDistributorSaveParam param, CerePlatformBusiness user) throws CoBusinessException;

    void update(ShopDistributorUpdateParam param, CerePlatformBusiness user) throws CoBusinessException;

    void delete(ShopDistributorDeleteParam param, CerePlatformBusiness user) throws CoBusinessException;

    Page getAll(ShopDistributorGetAllParam param) throws CoBusinessException;

    List<CereShopDistributor> getAllInvitees(ShopParam param) throws CoBusinessException;

    Page getStayExamineAll(ShopDistributorGetStayParam param) throws CoBusinessException;

    void handle(ShopDistributorHandleParam param, CerePlatformBusiness user) throws CoBusinessException;

    ShopDistributor getById(Long distributorId) throws CoBusinessException;

    CereShopDistributor findByShopIdAndUserId(Long shopId, Long buyerUserId);

    void updateReliveBindByShopId(Long shopId, String time);

    void updateBatch(List<CereShopDistributor> list) throws CoBusinessException;
}
