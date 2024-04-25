/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.label.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.label.CerePlatformLabelDAO;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.label.*;
import com.shop.cereshop.admin.service.label.CereBuyerLabelService;
import com.shop.cereshop.admin.service.label.CerePlatformLabelService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.domain.label.PlatformLabel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CerePlatformLabelServiceImpl implements CerePlatformLabelService {

    @Autowired
    private CerePlatformLabelDAO cerePlatformLabelDAO;

    @Autowired
    private CereBuyerLabelService cereBuyerLabelService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public Page getAll(LabelGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<PlatformLabel> list= cerePlatformLabelDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.stream()
                    //过滤标签类型为手动标签的
                    .filter(platformLabel -> !platformLabel.getLabelType().equals("1"))
                    //设置条件描述
                    .peek(platformLabel -> {
                        List<String> conditions=new ArrayList<>();
                        if(IntegerEnum.YES.getCode().equals(platformLabel.getLastConsumptionTime())){
                            //如果选中最后消费时间
                            if(!EmptyUtils.isEmpty(platformLabel.getConsumptionDay())){
                                //如果选择的最近几天
                                conditions.add("最后消费时间在"+platformLabel.getConsumptionDay()+"天内");
                            }else {
                                //如果选择的最后消费时间段
                                conditions.add("最后消费时间在"+platformLabel.getConsumptionStartTime()+"到"+platformLabel.getConsumptionEndTime()+"范围内");
                            }
                        }
                        if(IntegerEnum.YES.getCode().equals(platformLabel.getConsumptionFrequency())){
                            //如果选中累计消费次数
                            if(!EmptyUtils.isEmpty(platformLabel.getFrequencyEnd())){
                                //如果有截止次数
                                conditions.add("累计消费次数在"+platformLabel.getFrequencyStart()+"-"+platformLabel.getFrequencyEnd()+"次");
                            }else {
                                conditions.add("累计消费次数在"+platformLabel.getFrequencyStart()+"次以上");
                            }
                        }
                        if(IntegerEnum.YES.getCode().equals(platformLabel.getConsumptionMoney())){
                            if(!EmptyUtils.isEmpty(platformLabel.getMoneyEnd())){
                                //如果有截止金额
                                conditions.add("累计消费金额在"+platformLabel.getMoneyStart()+"-"+platformLabel.getMoneyEnd()+"元");
                            }else {
                                conditions.add("累计消费金额在"+platformLabel.getMoneyStart()+"元以上");
                            }
                        }
                        platformLabel.setConditions(conditions);
                    })
                    .collect(Collectors.toList());
        }
        PageInfo<PlatformLabel> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public PlatformLabel getById(Long buyerLabelId) throws CoBusinessException {
        PlatformLabel label = cerePlatformLabelDAO.getById(buyerLabelId);
        //设置条件
        if(label!=null){
            List<Integer> consumptions=new ArrayList<>();
            if(IntegerEnum.YES.getCode().equals(label.getLastConsumptionTime())){
                consumptions.add(1);
            }
            if(IntegerEnum.YES.getCode().equals(label.getConsumptionFrequency())){
                consumptions.add(2);
            }
            if(IntegerEnum.YES.getCode().equals(label.getConsumptionMoney())){
                consumptions.add(3);
            }
            label.setConsumptions(consumptions);
        }
        return label;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(LabelSaveParam label, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        label.setCreateTime(time);
        cerePlatformLabelDAO.insert(label);
        //新增日志
        cerePlatformLogService.addLog(user,"客户标签管理","平台端操作","添加客户标签",String.valueOf(label.getBuyerLabelId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(LabelUpdateParam label, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        label.setUpdateTime(time);
        cerePlatformLabelDAO.updateByPrimaryKeySelective(label);
        //新增日志
        cerePlatformLogService.addLog(user,"客户标签管理","平台端操作","修改客户标签",String.valueOf(label.getBuyerLabelId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(LabelDeleteParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(param.getIds())){
            //删除标签数据
            cerePlatformLabelDAO.deleteByIds(param.getIds());
            //删除标签绑定客户数据
            cereBuyerLabelService.deleteLabelUser(param.getIds());
            //新增日志
            cerePlatformLogService.addLog(user,"客户标签管理","平台端操作","删除客户标签",null,time);
        }
    }

    @Override
    public List<PlatformLabel> findExcel(LabelExcelParam param) throws CoBusinessException {
        if(!EmptyUtils.isEmpty(param.getIds())){
            List<PlatformLabel> list= cerePlatformLabelDAO.getAllByIds(param.getIds());
            if(!EmptyUtils.isEmpty(list)){
                list.stream()
                        //过滤标签类型为手动标签的
                        .filter(platformLabel -> !platformLabel.getLabelType().equals("1"))
                        //设置条件描述
                        .peek(platformLabel -> {
                            List<String> conditions=new ArrayList<>();
                            if(IntegerEnum.YES.getCode().equals(platformLabel.getLastConsumptionTime())){
                                //如果选中最后消费时间
                                if(!EmptyUtils.isEmpty(platformLabel.getConsumptionDay())){
                                    //如果选择的最近几天
                                    conditions.add("最后消费时间在"+platformLabel.getConsumptionDay()+"天内");
                                }else {
                                    //如果选择的最后消费时间段
                                    conditions.add("最后消费时间在"+platformLabel.getConsumptionStartTime()+"到"+platformLabel.getConsumptionEndTime()+"范围内");
                                }
                            }
                            if(IntegerEnum.YES.getCode().equals(platformLabel.getConsumptionFrequency())){
                                //如果选中累计消费次数
                                if(!EmptyUtils.isEmpty(platformLabel.getFrequencyEnd())){
                                    //如果有截止次数
                                    conditions.add("累计消费次数在"+platformLabel.getFrequencyStart()+"-"+platformLabel.getFrequencyEnd()+"次");
                                }else {
                                    conditions.add("累计消费次数在"+platformLabel.getFrequencyStart()+"次以上");
                                }
                            }
                            if(IntegerEnum.YES.getCode().equals(platformLabel.getConsumptionMoney())){
                                if(!EmptyUtils.isEmpty(platformLabel.getMoneyEnd())){
                                    //如果有截止金额
                                    conditions.add("累计消费金额在"+platformLabel.getFrequencyStart()+"-"+platformLabel.getFrequencyEnd()+"元");
                                }else {
                                    conditions.add("累计消费金额在"+platformLabel.getMoneyStart()+"元以上");
                                }
                            }
                            platformLabel.setConditions(conditions);
                        })
                        .collect(Collectors.toList());
                return list;
            }
        }
        return null;
    }

    @Override
    public List<CerePlatformLabel> findAll() {
        return cerePlatformLabelDAO.findAll();
    }

    @Override
    public List<Long> findAllByDay(Integer consumptionDay) {
        return cerePlatformLabelDAO.findAllByDay(consumptionDay);
    }

    @Override
    public List<Long> findRangeDayBuyers(CerePlatformLabel label) {
        return cerePlatformLabelDAO.findRangeDayBuyers(label);
    }

    @Override
    public List<Long> findFrequencyBuyes(CerePlatformLabel label) {
        return cerePlatformLabelDAO.findFrequencyBuyes(label);
    }

    @Override
    public List<Long> findMoneyBuyers(CerePlatformLabel label) {
        return cerePlatformLabelDAO.findMoneyBuyers(label);
    }

    @Override
    public void insertBatchBuyerLabel(List<CereBuyerLabel> collect) {
        cereBuyerLabelService.insertBatch(collect);
    }

    @Override
    public List<Long> findAllBuyers(CerePlatformLabel label) {
        return cerePlatformLabelDAO.findAllBuyers(label);
    }
}
