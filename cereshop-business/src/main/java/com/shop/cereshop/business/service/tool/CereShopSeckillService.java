/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool;

import com.shop.cereshop.business.page.tool.ShopSeckillData;
import com.shop.cereshop.business.page.tool.ShopSeckillDetail;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWork;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereShopSeckillService {
    void save(ShopSeckillSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void update(ShopSeckillUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception;

    void delete(Long shopSeckillId, CerePlatformBusiness user) throws CoBusinessException;

    void stop(Long shopSeckillId, CerePlatformBusiness user) throws CoBusinessException;

    ShopSeckillDetail getById(Long shopSeckillId) throws CoBusinessException;

    Page getAll(ShopSeckillGetAllParam param) throws CoBusinessException;

    Page getProducts(ToolProductParam param) throws CoBusinessException;

    ShopSeckillData getData(ShopSeckillGetByIdParam param, Long shopId) throws CoBusinessException;

    CereShopSeckill findById(Long shopSeckillId);

    void updateState(CereShopSeckill cereShopSeckill) throws CoBusinessException;

    List<ShopSeckillDetail> getSeckills(RenovationParam param) throws CoBusinessException;

    List<Long> checkOtherDiscount(List<Long> productIs, String startTime, String endTime, Long shopId);

    List<Long> checkOtherWork(List<Long> productIs, String startTime, String endTime, Long shopId);

    void updateSeckillEndState(List<CereShopSeckill> seckills, String time) throws CoBusinessException;
}
