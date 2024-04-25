/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.order.impl;

import com.shop.cereshop.business.dao.order.CereOrderDileverDAO;
import com.shop.cereshop.business.param.after.AfterIdParam;
import com.shop.cereshop.business.param.order.OrderDileveryParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.after.CereAfterDileverService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.notice.CereNoticeService;
import com.shop.cereshop.business.service.order.CereOrderDileverService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.after.CereAfterDilever;
import com.shop.cereshop.commons.domain.after.CereOrderDilever;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CereOrderDileverServiceImpl implements CereOrderDileverService {

    @Autowired
    private CereOrderDileverDAO cereOrderDileverDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Autowired
    private CereAfterDileverService cereAfterDileverService;

    @Autowired
    private CereNoticeService cereNoticeService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void dilevery(OrderDileveryParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        CereOrderDilever cereOrderDilever=new CereOrderDilever();
        cereOrderDilever.setOrderId(param.getOrderId());
        cereOrderDilever.setExpress(param.getExpress());
        cereOrderDilever.setDeliverFormid(param.getDeliverFormid());
        cereOrderDilever.setCreateTime(time);
        cereOrderDileverDAO.insert(cereOrderDilever);
        //更新订单状态为待收货
        CereShopOrder cereShopOrder=cereShopOrderService.findById(param.getOrderId());
        cereShopOrder.setOrderId(param.getOrderId());
        cereShopOrder.setState(IntegerEnum.ORDER_HAVE_DILEVERY.getCode());
        cereShopOrderService.updateState(cereShopOrder);
        //新增日志
        cerePlatformLogService.addLog(user,"订单管理","商户端操作","发货",cereOrderDilever.getOrderId(),time);
        //新增自动15天确认收货定时任务
        //stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),1,15*24*60*60*1000);
        //cereRedisKeyServcice.add(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),TimeUtils.getMoreDayAfter(time,15));

        //TODO 暂时改为1小时后结算
        stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),1,60*60*1000);
        cereRedisKeyServcice.add(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),TimeUtils.getMoreHourAfter(time, 1));
        log.info("add ORDER_CONFIRM_DILEVERY Redis Message key = {}", StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());

        //新增订单已完成消息
        CereNotice cereNotice=new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(cereShopOrder.getBuyerUserId());
        cereNotice.setShopId(cereShopOrder.getShopId());
        cereNotice.setReceive(3);
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_DELIVERY.getCode());
        cereNotice.setNoticeContent("您购买的"+cereShopOrder.getOrderFormid()+"商家已发货，点击查看物流详情");
        cereNotice.setOnly(cereShopOrder.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundDilevery(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        CereAfterDilever cereAfterDilever=new CereAfterDilever();
        cereAfterDilever.setCreateTime(time);
        cereAfterDilever.setExpress(param.getExpress());
        cereAfterDilever.setDeliverFormid(param.getDeliverFormid());
        cereAfterDilever.setOrderId(param.getOrderId());
        cereAfterDilever.setAfterId(param.getAfterId());
        cereAfterDileverService.insert(cereAfterDilever);
        //更新订单状态为待收货
//        CereShopOrder cereShopOrder=new CereShopOrder();
//        cereShopOrder.setOrderId(param.getOrderId());
//        cereShopOrder.setState(IntegerEnum.ORDER_HAVE_DILEVERY.getCode());
//        cereShopOrderService.updateState(cereShopOrder);
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","仅退款发货",param.getAfterId(),time);
        //新增自动15天确认收货定时任务
//        stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),1,15*24*60*60*1000);
//        cereRedisKeyServcice.add(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),TimeUtils.getMoreDayAfter(time,15));
    }
}
