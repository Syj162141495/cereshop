/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.platformtool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.admin.page.canvas.ToolProduct;
import com.shop.cereshop.admin.page.seckill.Seckill;
import com.shop.cereshop.admin.page.seckill.SeckillShop;
import com.shop.cereshop.admin.page.seckill.SeckillShopProduct;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CerePlatformSeckillDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long seckillId);

    int insert(CerePlatformSeckill record);

    int insertSelective(CerePlatformSeckill record);

    CerePlatformSeckill selectByPrimaryKey(Long seckillId);

    int updateByPrimaryKeySelective(CerePlatformSeckill record);

    int updateByPrimaryKey(CerePlatformSeckill record);

    List<CerePlatformSeckill> checkTime(SeckillSaveParam param);

    List<CerePlatformSeckill> checkUpdateTime(SeckillUpdateParam param);

    Integer findShops(@Param("seckillId") Long seckillId);

    Integer findProducts(@Param("seckillId") Long seckillId,@Param("shopId") Long shopId);

    BigDecimal findTotal(@Param("seckillId") Long seckillId,@Param("shopId") Long shopId);

    Integer findPays(@Param("seckillId") Long seckillId);

    Integer findVisit (@Param("seckillId") Long seckillId,@Param("shopId") Long shopId);

    List<ShopDataDetail> findShopDatas(@Param("seckillId") Long seckillId);

    Integer findOrders(@Param("seckillId") Long seckillId,@Param("shopId") Long shopId);

    Integer findSubmit(@Param("seckillId") Long seckillId,@Param("shopId") Long shopId);

    List<Seckill> getAll(SeckillGetAllParam param);

    List<SeckillShop> getShops(SeckillShopParam param);

    Integer findExamines(@Param("seckillId") Long seckillId,@Param("shopId") Long shopId);

    List<SeckillShopProduct> getProducts(SeckillGetProductsParam param);

    //Integer findIfBond(@Param("seckIllId") Long seckIllId);

    List<CereActivitySign> findBySeckill(@Param("seckIllId") Long seckIllId);

    List<CereActivitySign> findErrorSign(@Param("seckIllId") Long seckIllId);

    List<CanvasPlatformSeckill> getPlatformSeckills(RenovationParam param);

    List<ToolProduct> findSeckillProduct(@Param("seckillId") Long seckillId);

    List<Long> findProductIdList(Long seckIllId);
}
