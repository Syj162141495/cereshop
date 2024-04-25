/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.canvas;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.param.canvas.CanvasPageParam;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvasCustom;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CerePlatformCanvasCustomService {

    /**
     * 新增或更新自定义页面
     * @param canvasCustom
     * @param user
     * @throws CoBusinessException
     */
    void saveCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user) throws CoBusinessException;

    /**
     * 删除自定义页面
     * @param canvasCustom
     * @param user
     * @throws CoBusinessException
     */
    void delCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user) throws CoBusinessException;

    /**
     * 分页查询自定义页面列表
     * @param param
     * @return
     */
    Page selectCanvasCustomList(CanvasPageParam param);
}
