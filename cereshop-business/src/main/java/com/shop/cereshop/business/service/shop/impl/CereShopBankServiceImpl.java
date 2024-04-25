/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CereShopBankDAO;
import com.shop.cereshop.business.page.shop.ShopBankDetail;
import com.shop.cereshop.business.param.bank.BankDeleteParam;
import com.shop.cereshop.business.param.bank.BankSaveParam;
import com.shop.cereshop.business.param.bank.BankUpdateParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.shop.CereShopBankService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.domain.shop.CereShopBank;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CereShopBankServiceImpl implements CereShopBankService {

    @Autowired
    private CereShopBankDAO cereShopBankDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(BankSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopBank cereShopBank=new CereShopBank();
        cereShopBank.setShopId(param.getShopId());
        cereShopBank.setCardName(param.getCardName());
        cereShopBank.setCardNumber(param.getCardNumber());
        cereShopBank.setBank(param.getBank());
        cereShopBankDAO.insert(cereShopBank);
        //新增日志
        cerePlatformLogService.addLog(user,"商家账户","商户端操作","绑定账户",param.getShopId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(BankUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopBank cereShopBank=new CereShopBank();
        cereShopBank.setShopId(param.getShopId());
        cereShopBank.setCardName(param.getCardName());
        cereShopBank.setCardNumber(param.getCardNumber());
        cereShopBank.setBank(param.getBank());
        cereShopBankDAO.update(cereShopBank);
        //新增日志
        cerePlatformLogService.addLog(user,"商家账户","商户端操作","更换账户",param.getShopId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(BankDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopBank cereShopBank=new CereShopBank();
        cereShopBank.setShopId(param.getShopId());
        cereShopBankDAO.delete(cereShopBank);
        //新增日志
        cerePlatformLogService.addLog(user,"商家账户","商户端操作","解除绑定",param.getShopId(),time);
    }

    @Override
    public ShopBankDetail getById(Long shopId) throws CoBusinessException {
        return cereShopBankDAO.getById(shopId);
    }

    @Override
    public CerePlatformShop findByPhone(Long shopId, String phone) {
        return cereShopBankDAO.findByPhone(shopId,phone);
    }
}
