/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.timing;

import com.shop.cereshop.admin.service.label.CerePlatformLabelService;
import com.shop.cereshop.commons.domain.label.CereBuyerLabel;
import com.shop.cereshop.commons.domain.label.CerePlatformLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 客户标签定时任务处理
 */
public class LabelInitializationRunnable implements Runnable{

    private CerePlatformLabelService cerePlatformLabelService;

    public LabelInitializationRunnable(CerePlatformLabelService cerePlatformLabelService){
        this.cerePlatformLabelService=cerePlatformLabelService;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void run() {
        try {
            //查询所有标签
            List<CerePlatformLabel> list=cerePlatformLabelService.findAll();
            if(!EmptyUtils.isEmpty(list)){
                list.stream()
                        //过滤手动标签
                        .filter(a -> !a.getLabelType().equals(1))
                        //判断条件贴标签
                        .peek(a -> label(a));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void label(CerePlatformLabel label){
        List<CereBuyerLabel> list=new ArrayList<>();
        if(label.getMeetConditions().equals(1)){
            //如果是满足任意一个条件,即可贴标签
            if(label.getLastConsumptionTime().equals(1)){
                //如果选中最后消费时间
                if(!EmptyUtils.isEmpty(label.getConsumptionDay())){
                    //如果设置了最近几天条件,轮循所有客户,设置满足在最近几天内下单的客户
                    list=setDayBuyers(label.getConsumptionDay(),list);
                }else {
                    //如果设置了时间范围,设置满足下单时间在设置范围内的客户
                    list=setRangeDayBuyers(label,list);
                }
            }
            if(label.getConsumptionFrequency().equals(1)){
                //如果选中累计消费次数,轮循所有客户,设置满足累计消费次数在当前范围内
                list=setFrequencyBuyes(label,list);
            }
            if(label.getConsumptionMoney().equals(1)){
                //如果选中累计消费金额,轮循所有客户,设置满足累计金额在当前范围内
                list=setMoneyBuyers(label,list);
            }
        }else {
            //必选满足所有条件,才可贴标签
            list=setAllBuyers(label,list);
        }
        if(!EmptyUtils.isEmpty(list)){
            List<CereBuyerLabel> collect = list.stream()
                    //去除重复客户id数据
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(a -> a.getBuyerUserId()))), ArrayList::new));
            //批量绑定客户标签
            cerePlatformLabelService.insertBatchBuyerLabel(collect);
        }
    }

    private List<CereBuyerLabel> setAllBuyers(CerePlatformLabel label, List<CereBuyerLabel> list) {
        List<Long> ids=cerePlatformLabelService.findAllBuyers(label);
        if(!EmptyUtils.isEmpty(ids)){
            ids.forEach(id -> {
                CereBuyerLabel cereBuyerLabel=new CereBuyerLabel();
                cereBuyerLabel.setBuyerUserId(id);
                list.add(cereBuyerLabel);
            });
        }
        return list;
    }

    private List<CereBuyerLabel> setMoneyBuyers(CerePlatformLabel label, List<CereBuyerLabel> list) {
        List<Long> ids=cerePlatformLabelService.findMoneyBuyers(label);
        if(!EmptyUtils.isEmpty(ids)){
            ids.forEach(id -> {
                CereBuyerLabel cereBuyerLabel=new CereBuyerLabel();
                cereBuyerLabel.setBuyerUserId(id);
                list.add(cereBuyerLabel);
            });
        }
        return list;
    }

    private List<CereBuyerLabel> setFrequencyBuyes(CerePlatformLabel label, List<CereBuyerLabel> list) {
        List<Long> ids=cerePlatformLabelService.findFrequencyBuyes(label);
        if(!EmptyUtils.isEmpty(ids)){
            ids.forEach(id -> {
                CereBuyerLabel cereBuyerLabel=new CereBuyerLabel();
                cereBuyerLabel.setBuyerUserId(id);
                list.add(cereBuyerLabel);
            });
        }
        return list;
    }

    private List<CereBuyerLabel> setRangeDayBuyers(CerePlatformLabel label,List<CereBuyerLabel> list) {
        //查询所有满足最近消费时间在几天范围内的客户
        List<Long> ids=cerePlatformLabelService.findRangeDayBuyers(label);
        if(!EmptyUtils.isEmpty(ids)){
            ids.forEach(id -> {
                CereBuyerLabel cereBuyerLabel=new CereBuyerLabel();
                cereBuyerLabel.setBuyerUserId(id);
                list.add(cereBuyerLabel);
            });
        }
        return list;
    }

    private List<CereBuyerLabel> setDayBuyers(Integer consumptionDay,List<CereBuyerLabel> list) {
        //查询所有满足最近消费时间在几天范围内的客户
        List<Long> ids=cerePlatformLabelService.findAllByDay(consumptionDay);
        if(!EmptyUtils.isEmpty(ids)){
            ids.forEach(id -> {
                CereBuyerLabel cereBuyerLabel=new CereBuyerLabel();
                cereBuyerLabel.setBuyerUserId(id);
                list.add(cereBuyerLabel);
            });
        }
        return list;
    }
}
