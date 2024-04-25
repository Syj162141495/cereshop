/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.canvas;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.param.canvas.CanvasPageParam;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CerePlatformCanvasDAO extends BaseMapper<CerePlatformCanvas> {
    int deleteByPrimaryKey(Long canvasId);

    int insertSelective(CerePlatformCanvas record);

    CerePlatformCanvas selectByPrimaryKey(Long canvasId);

    int updateByPrimaryKeySelective(CerePlatformCanvas record);

    CerePlatformCanvas getCanvas(CerePlatformCanvas canvas);

    List<CerePlatformCanvas> selectCanvasList(CanvasPageParam param);

    void deleteByCustomId(Long id);

    CerePlatformCanvas checkCanvas(CerePlatformCanvas canvas);
}
