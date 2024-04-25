/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.canvas.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.canvas.CerePlatformCanvasDAO;
import com.shop.cereshop.admin.page.canvas.CanvasCoupon;
import com.shop.cereshop.admin.param.canvas.CanvasPageParam;
import com.shop.cereshop.admin.service.canvas.CerePlatformCanvasService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvasCustom;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.log.CerePlatformLog;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CerePlatformCanvasServiceImpl implements CerePlatformCanvasService {

    @Autowired
    private CerePlatformCanvasDAO cerePlatformCanvasDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveCanvas(CerePlatformCanvas canvas, CerePlatformUser user) throws CoBusinessException {
        String describe="";
        String time= TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(canvas.getCanvasId())){
            describe="更新画布";
            canvas.setUpdateTime(time);
            //更新画布
            cerePlatformCanvasDAO.updateByPrimaryKeySelective(canvas);
        }else {
            //校验是否已存在数据
            CerePlatformCanvas cerePlatformCanvas=cerePlatformCanvasDAO.checkCanvas(canvas);
            if(cerePlatformCanvas!=null){
                throw new CoBusinessException(CoReturnFormat.CANVAS_ALREADY);
            }
            describe="新增画布";
            canvas.setCreateTime(time);
            canvas.setUpdateTime(time);
            //新增画布
            cerePlatformCanvasDAO.insertSelective(canvas);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"画布管理","平台端操作",describe,String.valueOf(canvas.getCanvasId()), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public CerePlatformCanvas getCanvas(CerePlatformCanvas canvas) throws CoBusinessException {
        if (canvas.getType() == null) {
            //默认查询系统页面
            canvas.setType(1);
        }
        return cerePlatformCanvasDAO.getCanvas(canvas);
    }

    @Override
    public void delCanvas(CerePlatformCanvas canvas, CerePlatformUser user) throws CoBusinessException{
        cerePlatformCanvasDAO.deleteByPrimaryKey(canvas.getCanvasId());
        //新增日志
        cerePlatformLogService.addLog(user,"画布管理","平台端操作","删除画布",String.valueOf(canvas.getCanvasId()), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public Page selectCanvasList(CanvasPageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CerePlatformCanvas> list = cerePlatformCanvasDAO.selectCanvasList(param);
        PageInfo<CerePlatformCanvas> pageInfo = new PageInfo<>(list);
        Page page = new Page(pageInfo.getList(), pageInfo.getTotal());
        return page;
    }
}
