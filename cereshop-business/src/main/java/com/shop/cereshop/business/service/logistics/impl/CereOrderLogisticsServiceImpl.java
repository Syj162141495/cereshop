/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.logistics.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.logistics.CereOrderLogisticsDAO;
import com.shop.cereshop.business.page.logistics.Charge;
import com.shop.cereshop.business.page.logistics.Logistics;
import com.shop.cereshop.business.param.logistics.LogistDeleteParam;
import com.shop.cereshop.business.param.logistics.LogistSaveParam;
import com.shop.cereshop.business.param.logistics.LogistUpdateParam;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.logistics.CereLogisticsChargeService;
import com.shop.cereshop.business.service.logistics.CereOrderLogisticsService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import com.shop.cereshop.commons.domain.logistics.CereOrderLogistics;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereOrderLogisticsServiceImpl implements CereOrderLogisticsService {

    @Autowired
    private CereOrderLogisticsDAO cereOrderLogisticsDAO;

    @Autowired
    private CereLogisticsChargeService cereLogisticsChargeService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(LogistSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereOrderLogistics cereOrderLogistics=new CereOrderLogistics();
        cereOrderLogistics.setLogisticsName(param.getLogisticsName());
        cereOrderLogistics.setChargeType(param.getChargeType());
        cereOrderLogistics.setCreateTime(time);
        cereOrderLogistics.setShopId(param.getShopId());
        //新增物流方案
        cereOrderLogisticsDAO.insert(cereOrderLogistics);
        //新增计费明细数据
        if(!EmptyUtils.isEmpty(param.getCharges())){
            List<CereLogisticsCharge> collect = param.getCharges().stream()
                    .map(a -> {
                        CereLogisticsCharge charge = new CereLogisticsCharge();
                        charge.setLogisticsId(cereOrderLogistics.getLogisticsId());
                        charge.setRegion(EmptyUtils.getFenString(a.getRegions()));
                        charge.setWeight(a.getWeight());
                        charge.setPrice(a.getPrice());
                        charge.setSecondPrice(a.getSecondPrice());
                        charge.setSecondWeight(a.getSecondWeight());
                        return charge;
                    }).collect(Collectors.toList());
            cereLogisticsChargeService.insertBatch(collect);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"物流方案管理","商户端操作","添加物流方案",cereOrderLogistics.getLogisticsId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(LogistUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        CereOrderLogistics cereOrderLogistics=new CereOrderLogistics();
        cereOrderLogistics.setLogisticsId(param.getLogisticsId());
        cereOrderLogistics.setLogisticsName(param.getLogisticsName());
        cereOrderLogistics.setChargeType(param.getChargeType());
        cereOrderLogistics.setUpdateTime(time);
        cereOrderLogistics.setShopId(param.getShopId());
        cereOrderLogisticsDAO.updateByPrimaryKeySelective(cereOrderLogistics);
        //清空计费明细
        cereLogisticsChargeService.deleteByLogisticsId(param.getLogisticsId());
        //新增计费明细
        if(!EmptyUtils.isEmpty(param.getCharges())){
            List<CereLogisticsCharge> collect = param.getCharges().stream()
                    .map(a -> {
                        CereLogisticsCharge charge = new CereLogisticsCharge();
                        charge.setLogisticsId(cereOrderLogistics.getLogisticsId());
                        charge.setRegion(EmptyUtils.getFenString(a.getRegions()));
                        charge.setWeight(a.getWeight());
                        charge.setPrice(a.getPrice());
                        charge.setSecondPrice(a.getSecondPrice());
                        charge.setSecondWeight(a.getSecondWeight());
                        return charge;
                    }).collect(Collectors.toList());
            cereLogisticsChargeService.insertBatch(collect);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"物流方案管理","商户端操作","修改物流方案",param.getLogisticsId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(LogistDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cereOrderLogisticsDAO.deleteByPrimaryKey(param.getLogisticsId());
        //清空计费明细
        cereLogisticsChargeService.deleteByLogisticsId(param.getLogisticsId());
        //新增日志
        cerePlatformLogService.addLog(user,"物流方案管理","商户端操作","删除物流方案",param.getLogisticsId(),time);
    }

    @Override
    public Logistics getById(Long logisticsId) throws CoBusinessException {
        //查询物流方案信息
        Logistics logistics=cereOrderLogisticsDAO.getById(logisticsId);
        if(logistics!=null){
            //查询计费明细数据
            List<Charge> list=cereLogisticsChargeService.findByLogisticsId(logisticsId);
            if(!EmptyUtils.isEmpty(list)){
                //封装城市数组
                list.forEach(a -> a.setRegions(EmptyUtils.getFenStrings(a.getRegion())));
            }
            logistics.setCharges(list);
        }
        return logistics;
    }

    @Override
    public Page getAll(ShopParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Logistics> list=cereOrderLogisticsDAO.getAll(param);
        PageInfo<Logistics> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
