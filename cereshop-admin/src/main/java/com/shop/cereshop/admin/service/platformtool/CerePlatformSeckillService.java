/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool;

import com.shop.cereshop.admin.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.admin.page.seckill.SeckillData;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformSeckillService {
    void save(SeckillSaveParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void update(SeckillUpdateParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void delete(Long seckillId, CerePlatformUser user) throws CoBusinessException;

    void stop(Long seckillId, CerePlatformUser user) throws CoBusinessException;

    CerePlatformSeckill getById(Long seckillId) throws CoBusinessException;

    SeckillData getData(SeckillGetByIdParam param) throws CoBusinessException;

    Page getAll(SeckillGetAllParam param) throws CoBusinessException;

    Page getShops(SeckillShopParam param) throws CoBusinessException;

    Page getProducts(SeckillGetProductsParam param) throws CoBusinessException;

    List<ShopDataDetail> findExcel(SeckillGetByIdParam param) throws CoBusinessException;

    void updateSeckill(CerePlatformSeckill cerePlatformSeckill) throws CoBusinessException;

    List<CereActivitySign> findBySeckill(Long seckIllId);

    List<CereActivitySign> findErrorSign(Long seckIllId);

    List<CanvasPlatformSeckill> getPlatformSeckills(RenovationParam param) throws CoBusinessException;

    List<Long> findProductIdList(Long seckIllId);
}
