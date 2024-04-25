/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.canvas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.param.canvas.CanvasPageParam;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvasCustom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CerePlatformCanvasCustomDAO extends BaseMapper<CerePlatformCanvasCustom> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CerePlatformCanvasCustom record);

    CerePlatformCanvasCustom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CerePlatformCanvasCustom record);

    List<CerePlatformCanvasCustom> selectCanvasCustomList(CanvasPageParam param);
}
