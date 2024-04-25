/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.platformtool;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
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

    List<CanvasPlatformSeckill> getPlatformSeckills(RenovationParam param);

    List<Long> checkPlatformSeckill(@Param("ids") List<Long> ids,@Param("startTime") String startTime,
                                    @Param("endTime") String endTime,@Param("shopId") Long shopId);

    List<CerePlatformSeckill> findPlatformSeckill();

    List<CereShopSeckill> findShopSeckill();

    void updatePlatformSeckillEndState(@Param("list") List<CerePlatformSeckill> list,@Param("time") String time);

    BigDecimal findMinProductPrice(@Param("ids") List<Long> ids);
}
