/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity;

import com.shop.cereshop.admin.page.activity.ActivityData;
import com.shop.cereshop.admin.page.activity.ActivityDetail;
import com.shop.cereshop.admin.page.activity.BondCount;
import com.shop.cereshop.admin.page.activity.SignExamineLog;
import com.shop.cereshop.admin.page.canvas.ShopGroupWorkUDetail;
import com.shop.cereshop.admin.page.canvas.ToolProduct;
import com.shop.cereshop.admin.param.canvas.CanvasCouponParam;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.activity.*;
import com.shop.cereshop.commons.domain.log.CerePlatformLog;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.shop.ShopDetail;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformActivityService {
    void save(ActivitySaveParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void update(ActivityUpdateParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void delete(ActivityDelteParam param, CerePlatformUser user) throws CoBusinessException;

    ActivityDetail getById(Long activityId) throws CoBusinessException;

    void end(ActivityEndParam param, CerePlatformUser user) throws CoBusinessException;

    Page getAll(ActivityGetAllParam param) throws CoBusinessException;

    Page getShops(ActivityGetShopsParam param) throws CoBusinessException;

    Page getProducts(ActivityGetProductsParam param) throws CoBusinessException;

    List<SignExamineLog> getExamines(ActivityGetExaminesParam param) throws CoBusinessException;

    BondCount getAdminBonds(ActivityGetAdminBondsParam param) throws CoBusinessException;

    void examine(ActivityExamineParam param, CerePlatformUser user) throws CoBusinessException,Exception;

    void liquidation(ActivityLiquidationParam param, CerePlatformUser user) throws CoBusinessException;

    ActivityData getActivitys(ActivityGetDatasParam param) throws CoBusinessException;

//    ActivityData getTest(ActivityGetDatasParam param) throws CoBusinessException;

    List<ShopDetail> findExcel(ActivityGetDatasParam param) throws CoBusinessException;

    void updateActivity(CerePlatformActivity cerePlatformActivity) throws CoBusinessException;

    CereActivitySign checkSignError(CereActivitySign activitySign);

    CereActivitySign findSign(String signId);

    CerePayLog findBondPayLog(String formid);

    List<Long> findSignIdsByShopIdAndActivityId(Long shopId, Long activityId,int signType);

    List<CereActivitySign> findByActivity(Long activity);

    List<CereActivitySign> findErrorSign(Long parseLong);

    Integer findIfBond(Long activityId);

    void updateSignStateError(List<CereActivitySign> signs);

    Page getCoupons(CanvasCouponParam param) throws CoBusinessException;

    CerePlatformActivity findById(Long activityId);

    List<ShopGroupWorkUDetail> getGroupWorks(RenovationParam param);

    List<ToolProduct> getGroupWorkProducts(RenovationParam param) throws CoBusinessException;
}
