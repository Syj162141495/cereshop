/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.canvas;

import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CerePlatformCanvasDAO extends BaseMapper<CerePlatformCanvas> {
    int deleteByPrimaryKey(Long canvasId);

    int insert(CerePlatformCanvas record);

    int insertSelective(CerePlatformCanvas record);

    CerePlatformCanvas selectByPrimaryKey(Long canvasId);

    int updateByPrimaryKeySelective(CerePlatformCanvas record);

    int updateByPrimaryKey(CerePlatformCanvas record);

    CerePlatformCanvas getCanvas(CerePlatformCanvas canvas);

    CerePlatformCanvas checkCanvas(CerePlatformCanvas canvas);
}
