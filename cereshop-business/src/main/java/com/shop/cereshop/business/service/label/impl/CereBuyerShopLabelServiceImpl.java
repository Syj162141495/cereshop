/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label.impl;

import com.shop.cereshop.business.dao.label.CereBuyerShopLabelDAO;
import com.shop.cereshop.business.param.buyer.BuyerUserSaveParam;
import com.shop.cereshop.business.service.label.CereBuyerShopLabelService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.label.CereBuyerShopLabel;
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
public class CereBuyerShopLabelServiceImpl implements CereBuyerShopLabelService {

    @Autowired
    private CereBuyerShopLabelDAO cereBuyerShopLabelDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveLabel(BuyerUserSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //清空标签
        cereBuyerShopLabelDAO.deleteById(param.getBuyerUserId());
        if(!EmptyUtils.isEmpty(param.getLabelIds())){
            for (Long labelId : param.getLabelIds()) {
                CereBuyerShopLabel cereBuyerShopLabel=new CereBuyerShopLabel();
                cereBuyerShopLabel.setBuyerUserId(param.getBuyerUserId());
                cereBuyerShopLabel.setLabelId(labelId);
                cereBuyerShopLabelDAO.insert(cereBuyerShopLabel);
            }
            //新增日志
            cerePlatformLogService.addLog(user,"客户管理","商家端操作","给客户加标签",param.getBuyerUserId(),time);
        }
    }

    @Override
    public void insertBatch(List<CereBuyerShopLabel> list) throws CoBusinessException {
        cereBuyerShopLabelDAO.insertBatch(list);
    }

    @Override
    public void deleteById(Long buyerUserId) throws CoBusinessException {
        cereBuyerShopLabelDAO.deleteById(buyerUserId);
    }

    @Override
    public List<Long> findByUserId(Long buyerUserId) {
        return cereBuyerShopLabelDAO.findByUserId(buyerUserId);
    }
}
