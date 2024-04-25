/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.canvas.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.canvas.CerePlatformCanvasCustomDAO;
import com.shop.cereshop.admin.dao.canvas.CerePlatformCanvasDAO;
import com.shop.cereshop.admin.param.canvas.CanvasPageParam;
import com.shop.cereshop.admin.service.canvas.CerePlatformCanvasCustomService;
import com.shop.cereshop.admin.service.canvas.CerePlatformCanvasService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvas;
import com.shop.cereshop.commons.domain.canvas.CerePlatformCanvasCustom;
import com.shop.cereshop.commons.domain.common.Page;
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
public class CerePlatformCanvasCustomServiceImpl implements CerePlatformCanvasCustomService {

    @Autowired
    private CerePlatformCanvasCustomDAO cerePlatformCanvasCustomDAO;

    @Autowired
    private CerePlatformCanvasDAO cerePlatformCanvasDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user) throws CoBusinessException {
        String describe="";
        String time= TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(canvasCustom.getId())){
            describe="更新自定义页面";
            canvasCustom.setUpdateTime(time);
            //更新自定义页面
            cerePlatformCanvasCustomDAO.updateByPrimaryKeySelective(canvasCustom);
        }else {
            describe="新增自定义页面";
            canvasCustom.setCreateTime(time);
            canvasCustom.setUpdateTime(time);
            //新增自定义页面
            cerePlatformCanvasCustomDAO.insertSelective(canvasCustom);
            //4种终端都新增对应的画布
            for (int i=1;i<=4;i++) {
                CerePlatformCanvas canvas = new CerePlatformCanvas();
                canvas.setCreateTime(time);
                canvas.setUpdateTime(time);
                canvas.setJson("[]");
                canvas.setName("");
                canvas.setShopId(0L);
                canvas.setTerminal(i);
                canvas.setType(2);
                canvas.setCustomId(canvasCustom.getId());
                cerePlatformCanvasDAO.insertSelective(canvas);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"画布管理","平台端操作",describe,String.valueOf(canvasCustom.getId()), TimeUtils.yyMMddHHmmss());
    }

    @Override
    public void delCanvasCustom(CerePlatformCanvasCustom canvasCustom, CerePlatformUser user) throws CoBusinessException{
        cerePlatformCanvasCustomDAO.deleteByPrimaryKey(canvasCustom.getId());
        //删除自定义页面
        cerePlatformCanvasDAO.deleteByCustomId(canvasCustom.getId());
        //新增日志
        cerePlatformLogService.addLog(user,"画布管理","平台端操作","删除自定义页面",String.valueOf(canvasCustom.getId()), TimeUtils.yyMMddHHmmss());

    }

    @Override
    public Page selectCanvasCustomList(CanvasPageParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<CerePlatformCanvasCustom> list = cerePlatformCanvasCustomDAO.selectCanvasCustomList(param);
        PageInfo<CerePlatformCanvasCustom> pageInfo = new PageInfo<>(list);
        Page page = new Page(pageInfo.getList(), pageInfo.getTotal());
        return page;
    }
}
