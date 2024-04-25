/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.shop.CereShopWithdrawalDAO;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalGetAllParam;
import com.shop.cereshop.admin.param.withdrawal.WithdrawalHandleParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.shop.CereShopWithdrawalService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.shop.CereShopWithdrawal;
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
public class CereShopWithdrawalServiceImpl implements CereShopWithdrawalService {

    @Autowired
    private CereShopWithdrawalDAO cereShopWithdrawalDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public Page getAll(WithdrawalGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CereShopWithdrawal> list=cereShopWithdrawalDAO.getAll(param);
        PageInfo<CereShopWithdrawal> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CereShopWithdrawal getById(Long withdrawalId) throws CoBusinessException {
        return cereShopWithdrawalDAO.getById(withdrawalId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(WithdrawalHandleParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopWithdrawal cereShopWithdrawal=new CereShopWithdrawal();
        cereShopWithdrawal.setWithdrawalId(param.getWithdrawalId());
        cereShopWithdrawal.setHandleTime(time);
        cereShopWithdrawal.setState(IntegerEnum.WITHDRAWAL_ALREADY.getCode());
        cereShopWithdrawalDAO.update(cereShopWithdrawal);
        //新增日志
        cerePlatformLogService.addLog(user,"提现申请管理","平台端操作","处理提现申请",String.valueOf(param.getWithdrawalId()),time);
    }
}
