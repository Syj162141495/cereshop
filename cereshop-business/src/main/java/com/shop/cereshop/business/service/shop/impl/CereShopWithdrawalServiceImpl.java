/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CereShopWithdrawalDAO;
import com.shop.cereshop.business.param.finance.FinanceSaveWithdralwalParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.shop.CereShopWithdrawalService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.shop.CereShopBank;
import com.shop.cereshop.commons.domain.shop.CereShopWithdrawal;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CereShopWithdrawalServiceImpl implements CereShopWithdrawalService {

    @Autowired
    private CereShopWithdrawalDAO cereShopWithdrawalDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(FinanceSaveWithdralwalParam param, CerePlatformBusiness user) throws CoBusinessException {
        //查询提现中金额
        BigDecimal stayMoney = cereShopOrderService.getWithdrawableStayMoney(param.getShopId());
        //查询总的可提现金额
        BigDecimal withdrawableMoney = cereShopOrderService.getAllWithdrawableMoney(param.getShopId());
        //查询已提现的金额
        BigDecimal money=cereShopOrderService.getWithdrawableMoney(param.getShopId());
        //计算可提现金额=总的提现金额-提现中金额-已提现金额
        BigDecimal total = withdrawableMoney.subtract(stayMoney).subtract(money);
        if(param.getWithdrawalMoney().compareTo(total)==1){
            throw new CoBusinessException(CoReturnFormat.BALANCE_NOT_ENOUGH);
        }
        String time = TimeUtils.yyMMddHHmmss();
        //查询当前商家绑定账户信息
        CereShopBank cereShopBank=cereShopWithdrawalDAO.findBank(param.getShopId());
        if(cereShopBank!=null){
            CereShopWithdrawal withdrawal=new CereShopWithdrawal();
            withdrawal.setShopId(param.getShopId());
            withdrawal.setShopId(param.getShopId());
            withdrawal.setShopCode(param.getShopCode());
            withdrawal.setShopName(param.getShopName());
            withdrawal.setBankCard(param.getBankCard());
            withdrawal.setBankName(param.getBankName());
            withdrawal.setCollectionName(cereShopBank.getCardName());
            withdrawal.setWithdrawalMoney(param.getWithdrawalMoney());
            withdrawal.setApplyTime(time);
            withdrawal.setState(IntegerEnum.WITHDRAWAL_STAY.getCode());
            cereShopWithdrawalDAO.insert(withdrawal);
            //新增日志
            cerePlatformLogService.addLog(user,"财务管理","商户端操作","添加提现申请",withdrawal.getWithdrawalId(),time);
        }
    }
}
