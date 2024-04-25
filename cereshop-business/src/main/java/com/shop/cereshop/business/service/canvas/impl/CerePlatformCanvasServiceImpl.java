/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.canvas.impl;

import com.shop.cereshop.business.dao.canvas.CerePlatformCanvasDAO;
import com.shop.cereshop.business.service.canvas.CerePlatformCanvasService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
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

@Service
public class CerePlatformCanvasServiceImpl implements CerePlatformCanvasService {

    @Autowired
    private CerePlatformCanvasDAO cerePlatformCanvasDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveCanvas(CerePlatformCanvas canvas, CerePlatformBusiness user) throws CoBusinessException {
        String describe="";
        String time= TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(canvas.getCanvasId())){
            describe="更新画布";
            canvas.setUpdateTime(time);
            //更新画布
            cerePlatformCanvasDAO.updateByPrimaryKeySelective(canvas);
        }else {
            //校验是否已存在画布
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
        cerePlatformLogService.addLog(user,"画布管理","商家端操作",describe,canvas.getCanvasId(), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public CerePlatformCanvas getCanvas(CerePlatformCanvas canvas) throws CoBusinessException {
        if (canvas.getType() == null) {
            //默认查询系统页面
            canvas.setType(1);
        }
        return cerePlatformCanvasDAO.getCanvas(canvas);
    }
}
