/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.buyer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.buyer.CereBuyerWithdrawalDAO;
import com.shop.cereshop.admin.page.buyer.BuyerWithdrawal;
import com.shop.cereshop.admin.param.withdrawal.BuyerWithdrawalGetAllParam;
import com.shop.cereshop.admin.param.withdrawal.BuyerWithdrawalGetByIdParam;
import com.shop.cereshop.admin.service.buyer.CereBuyerWithdrawalService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.buyer.CereBuyerWithdrawal;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.log.CerePlatformLog;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CereBuyerWithdrawalServiceImpl implements CereBuyerWithdrawalService {

    @Autowired
    private CereBuyerWithdrawalDAO cereBuyerWithdrawalDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public Page getAll(BuyerWithdrawalGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<BuyerWithdrawal> list=cereBuyerWithdrawalDAO.getAll(param);
        PageInfo<BuyerWithdrawal> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public BuyerWithdrawal getById(Long withdrawalId) throws CoBusinessException {
        return cereBuyerWithdrawalDAO.getById(withdrawalId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(BuyerWithdrawalGetByIdParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        String describe="";
        CereBuyerWithdrawal cereBuyerWithdrawal=new CereBuyerWithdrawal();
        cereBuyerWithdrawal.setWithdrawalId(param.getWithdrawalId());
        if(IntegerEnum.WITHDRAWAL_SUCCESS.getCode().equals(param.getState())){
            describe="同意用户提现申请";
            cereBuyerWithdrawal.setState(IntegerEnum.WITHDRAWAL_SUCCESS.getCode());
        }else {
            describe="拒绝用户提现申请";
            cereBuyerWithdrawal.setState(IntegerEnum.WITHDRAWAL_ERROR.getCode());
        }
        cereBuyerWithdrawal.setHandleTime(time);
        cereBuyerWithdrawalDAO.updateByPrimaryKeySelective(cereBuyerWithdrawal);
        //新增日志
        cerePlatformLogService.addLog(user,"用户提现申请","平台端操作",describe,String.valueOf(param.getWithdrawalId()),time);
    }
}
