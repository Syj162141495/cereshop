/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.activity;

import com.shop.cereshop.admin.page.activity.*;
import com.shop.cereshop.admin.page.canvas.CanvasCoupon;
import com.shop.cereshop.admin.page.canvas.CanvasCouponDetail;
import com.shop.cereshop.admin.page.canvas.ShopGroupWorkUDetail;
import com.shop.cereshop.admin.page.canvas.ToolProduct;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.param.activity.*;
import com.shop.cereshop.admin.param.canvas.CanvasCouponParam;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.domain.shop.ShopDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CerePlatformActivityDAO extends BaseMapper<CerePlatformActivity> {
    int deleteByPrimaryKey(Long activityId);

    int insert(CerePlatformActivity record);

    int insertSelective(CerePlatformActivity record);

    CerePlatformActivity selectByPrimaryKey(Long activityId);

    int updateByPrimaryKeySelective(CerePlatformActivity record);

    int updateByPrimaryKey(CerePlatformActivity record);

    ActivityDetail getById(@Param("activityId") Long activityId);

    List<Activity> getAll(ActivityGetAllParam param);

    Integer findProductNumber(@Param("activityId") Long activityId);

    List<ShopActivity> getShops(ActivityGetShopsParam param);

    Integer findExamine(@Param("shopId") Long shopId,@Param("activityId") Long activityId);

    List<ShopProduct> getProducts(ActivityGetProductsParam param);

    List<SignExamineLog> getExamines(ActivityGetExaminesParam param);

    List<ActivityBond> getAdminBonds(ActivityGetAdminBondsParam param);

    BigDecimal getBondTotal();

    CerePlatformActivity findById(@Param("activityId") Long activityId);

    ActivityData findActivityData(CerePlatformActivity activity);

    Integer findBusiness(CerePlatformActivity activity);

    Integer findProducts(CerePlatformActivity activity);

    List<Proportion> findCities(CerePlatformActivity activity);

    List<Proportion> findCityPeople(CerePlatformActivity activity);

    Proportion findNews(CerePlatformActivity activity);

    Proportion findOlds(CerePlatformActivity activity);

    Proportion findAPP(CerePlatformActivity activity);

    Proportion findApplets(CerePlatformActivity activity);

    Proportion findAdroid(CerePlatformActivity activity);

    Proportion findIos(CerePlatformActivity activity);

    BigDecimal findTrendMoney(@Param("activityId") Long activityId,@Param("time") String time);

    Integer findTrendTotal(@Param("activityId") Long activityId,@Param("time") String time);

    List<ShopRanking> findShopRankings(CerePlatformActivity activity);

    List<CereProductClassify> findClassifies();

    BigDecimal findTotalByClassify(@Param("classifyId") Long classifyId,@Param("activityId") Long activityId,
                                  @Param("activityStartTime") String activityStartTime,@Param("activityEndTime") String activityEndTime);

    List<ProductRanking> findProductRankings(CerePlatformActivity activity);

    List<ShopDetail> findShops(ActivityGetDatasParam param);

    Integer findShopProducts(@Param("shopId") Long shopId,@Param("activityId") Long activityId,
                          @Param("activityStartTime") String activityStartTime,@Param("activityEndTime") String activityEndTime);

    Integer findShopPersons(@Param("shopId") Long shopId,@Param("activityId") Long activityId,
                         @Param("activityStartTime") String activityStartTime,@Param("activityEndTime") String activityEndTime);

    Integer findShopOrders(@Param("shopId") Long shopId,@Param("activityId") Long activityId,
                            @Param("activityStartTime") String activityStartTime,@Param("activityEndTime") String activityEndTime);

    Integer findShopCustomers(@Param("shopId") Long shopId,@Param("activityId") Long activityId,
                           @Param("activityStartTime") String activityStartTime,@Param("activityEndTime") String activityEndTime);

    BigDecimal findShopTotal(@Param("shopId") Long shopId,@Param("activityId") Long activityId,
                              @Param("activityStartTime") String activityStartTime,@Param("activityEndTime") String activityEndTime);

    List<CerePlatformActivity> checkTime(ActivitySaveParam param);

    Integer findIfBondBySignId(@Param("signId") Long signId);

    CereActivitySign checkSignError(CereActivitySign activitySign);

    CereActivitySign findSign(@Param("signId") String signId);

    CerePayLog findBondPayLog(@Param("formid") String formid);

    List<Long> findSignIdsByShopIdAndActivityId(@Param("shopId") Long shopId,@Param("activityId") Long activityId,@Param("signType") int signType);

    List<CereActivitySign> findByActivity(@Param("activityId") Long activityId);

    List<CereActivitySign> findErrorSign(@Param("activityId") Long activityId);

    Integer findIfBond(@Param("activityId") Long activityId);

    void updateSignStateError(@Param("list") List<CereActivitySign> list);

    List<CanvasCoupon> getCoupons(CanvasCouponParam param);

    List<CanvasCouponDetail> findDetai(@Param("activityId") Long activityId);

    List<CerePlatformActivity> checkUpdateTime(ActivityUpdateParam param);

    Integer findSeckillIfBondBySignId(@Param("signId") Long signId);

    Integer findDiscountIfBondBySignId(@Param("signId") Long signId);

    List<ShopGroupWorkUDetail> getGroupWorks(RenovationParam param);

    List<ToolProduct> findDistinctProducts(@Param("shopGroupWorkId") Long shopGroupWorkId);

    List<ToolProduct> getGroupWorkProducts(RenovationParam param);
}
