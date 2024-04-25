/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ShopGroupWorkData;
import com.shop.cereshop.business.page.tool.ShopGroupWorkUDetail;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopGroupWorkService {
    void save(ShopGroupWorkSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ShopGroupWorkUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(Long shopGroupWorkId, CerePlatformBusiness user) throws CoBusinessException;

    void stop(Long shopGroupWorkId, CerePlatformBusiness user) throws CoBusinessException,Exception;

    ShopGroupWorkUDetail getById(Long shopGroupWorkId) throws CoBusinessException;

    Page getProducts(ToolProductParam param) throws CoBusinessException;

    Page getAll(ShopGroupWorkGetAllParam param) throws CoBusinessException;

    ShopGroupWorkData getData(ShopGroupWorkGetByIdParam param, Long shopId) throws CoBusinessException;

    CereShopGroupWork findById(Long shopGroupWorkId);

    void updateState(CereShopGroupWork cereShopGroupWork) throws CoBusinessException;

    List<ShopGroupWorkUDetail> getGroupWorks(RenovationParam param) throws CoBusinessException;

    List<CereShopGroupWork> findShopWorks();

    void updateWorkEndState(List<CereShopGroupWork> works, String time) throws CoBusinessException;

}
