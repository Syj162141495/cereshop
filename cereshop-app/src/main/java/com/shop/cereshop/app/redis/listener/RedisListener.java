/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.redis.listener;

import com.shop.cereshop.app.service.activity.CereBuyerCouponService;
import com.shop.cereshop.app.service.after.CereOrderAfterService;
import com.shop.cereshop.app.service.buyer.CereBuyerShopCouponService;
import com.shop.cereshop.app.service.discount.CereShopDiscountDetailService;
import com.shop.cereshop.app.service.discount.CereShopDiscountService;
import com.shop.cereshop.app.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.app.service.distributor.CereDistributorBuyerService;
import com.shop.cereshop.app.service.groupwork.CereCollageOrderDetailService;
import com.shop.cereshop.app.service.groupwork.CereCollageOrderService;
import com.shop.cereshop.app.service.notice.CereNoticeService;
import com.shop.cereshop.app.service.order.CereShopOrderService;
import com.shop.cereshop.app.service.product.CereProductSkuService;
import com.shop.cereshop.app.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.app.service.seckill.CereShopSeckillDetailService;
import com.shop.cereshop.app.service.seckill.CereShopSeckillService;
import com.shop.cereshop.app.service.stock.CereStockService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereBuyerCoupon;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopDiscountDetail;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
public class RedisListener extends KeyExpirationEventMessageListener {

    private RedisTemplate<String, Object> redisTemplate;

    private CereRedisKeyServcice cereRedisKeyServcice;

    private CereShopOrderService cereShopOrderService;

    private CereBuyerCouponService cereBuyerCouponService;

    private CereOrderAfterService cereOrderAfterService;

    private CereDistributorBuyerService cereDistributorBuyerService;

    private CereCollageOrderService cereCollageOrderService;

    private CereCollageOrderDetailService cereCollageOrderDetailService;

    private CereNoticeService cereNoticeService;

    private CereBuyerShopCouponService cereBuyerShopCouponService;

    private CereShopSeckillService cereShopSeckillService;

    private CereShopSeckillDetailService cereShopSeckillDetailService;

    private CereShopDiscountService cereShopDiscountService;

    private CereShopDiscountDetailService cereShopDiscountDetailService;

    private CereProductSkuService cereProductSkuService;

    private CereDistributionOrderService cereDistributionOrderService;

    private CereStockService cereStockService;

    public RedisListener(RedisMessageListenerContainer listenerContainer,
                         RedisTemplate redisTemplate, CereRedisKeyServcice cereRedisKeyServcice,
                         CereShopOrderService cereShopOrderService, CereBuyerCouponService cereBuyerCouponService,
                         CereOrderAfterService cereOrderAfterService, CereDistributorBuyerService cereDistributorBuyerService,
                         CereCollageOrderService cereCollageOrderService, CereCollageOrderDetailService cereCollageOrderDetailService,
                         CereNoticeService cereNoticeService, CereBuyerShopCouponService cereBuyerShopCouponService,
                         CereShopSeckillService cereShopSeckillService, CereShopSeckillDetailService cereShopSeckillDetailService,
                         CereShopDiscountService cereShopDiscountService, CereShopDiscountDetailService cereShopDiscountDetailService,
                         CereProductSkuService cereProductSkuService, CereDistributionOrderService cereDistributionOrderService,
                         CereStockService cereStockService) {
        super(listenerContainer);
        this.redisTemplate=redisTemplate;
        this.cereRedisKeyServcice=cereRedisKeyServcice;
        this.cereShopOrderService=cereShopOrderService;
        this.cereBuyerCouponService=cereBuyerCouponService;
        this.cereOrderAfterService=cereOrderAfterService;
        this.cereDistributorBuyerService=cereDistributorBuyerService;
        this.cereCollageOrderService=cereCollageOrderService;
        this.cereCollageOrderDetailService=cereCollageOrderDetailService;
        this.cereNoticeService=cereNoticeService;
        this.cereBuyerShopCouponService=cereBuyerShopCouponService;
        this.cereShopSeckillService=cereShopSeckillService;
        this.cereShopSeckillDetailService=cereShopSeckillDetailService;
        this.cereShopDiscountService=cereShopDiscountService;
        this.cereShopDiscountDetailService=cereShopDiscountDetailService;
        this.cereProductSkuService=cereProductSkuService;
        this.cereDistributionOrderService=cereDistributionOrderService;
        this.cereStockService=cereStockService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        log.info("RedisListener key={}", expiredKey);
        String time= TimeUtils.yyMMddHHmmss();
        try {
            if(!EmptyUtils.isEmpty(expiredKey)){
                if(expiredKey.contains("-")){
                    String[] split = expiredKey.split("-");
                    String operation=split[0];
                    if(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode().equals(operation)){
                        //修改订单状态为已关闭
                        updateOrderClose(split[1],time);
                    }else if(StringEnum.ORDER_CONFIRM_DILEVERY.getCode().equals(operation)){
                        //修改订单状态为已完成
                        updateOrderState(split[1],time,IntegerEnum.ORDER_FINISH.getCode());
                    }else if(StringEnum.COUPON_OVERDUE.getCode().equals(operation)){
                        //修改用户已领取平台优惠券状态为已过期
                        updateBuyerCoupon(split,time,IntegerEnum.COUPON_OVERDUE.getCode());
                    }else if(StringEnum.SHOP_COUPON_OVERDUE.getCode().equals(operation)){
                        //修改用户已领取店铺优惠券状态为已过期
                        updateShopBuyerCoupon(split[1],time,IntegerEnum.COUPON_OVERDUE.getCode());
                    }else if(StringEnum.AFTER_CANCEL.getCode().equals(operation)){
                        //售后单三天后未处理自动关闭
                        updateAfterCancel(split[1],time,IntegerEnum.AFTER_CANCEL.getCode());
                    }else if(StringEnum.SHOP_VALIDITY_DAT.getCode().equals(operation)){
                        //当前店铺所有分销员绑定客户解除绑定关系
                        relieveBind(split,time);
                    }else if(StringEnum.COLLAGE_ERROR.getCode().equals(operation)){
                        //拼团失败处理
                        collageError(split[1],time);
                    }
                    //删除失效的key
                    redisTemplate.delete(expiredKey);
                    //删除redis延时任务记录
                    cereRedisKeyServcice.deleteByKey(expiredKey);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public <T> T getAndSet(final String key, T value){
        T oldValue=null;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            oldValue =(T) operations.getAndSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldValue;
    }

    private void updateShopBuyerCoupon(String id, String time, Integer state) throws CoBusinessException{
        CereBuyerShopCoupon cereBuyerShopCoupon=new CereBuyerShopCoupon();
        cereBuyerShopCoupon.setId(Long.parseLong(id));
        cereBuyerShopCoupon.setState(state);
        cereBuyerShopCoupon.setUpdateTime(time);
        cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
    }

    private void collageError(String id, String time) throws CoBusinessException,Exception{
        Long collageId=Long.parseLong(id);
        //修改拼单状态为拼团失败
        CereCollageOrder cereCollageOrder=new CereCollageOrder();
        cereCollageOrder.setCollageId(collageId);
        cereCollageOrder.setUpdateTime(time);
        cereCollageOrder.setState(IntegerEnum.COLLAGE_STATE_ERROR.getCode());
        cereCollageOrderService.update(cereCollageOrder);
        //查询参与该拼单的订单数据
        List<Long> ids=cereCollageOrderService.findOrderIds(collageId);
        //修改拼单明细订单改为已失效,并且订单自动取消
        if(!EmptyUtils.isEmpty(ids)){
            cereCollageOrderDetailService.updateInvalid(ids);
            //批量取消订单
            cereShopOrderService.cancelBatch(ids);
        }
    }

    private void relieveBind(String[] split, String time) throws CoBusinessException{
        Long distributorId=Long.parseLong(split[1]);
        Long buyerUserId=Long.parseLong(split[2]);
        CereDistributorBuyer distributorBuyer=new CereDistributorBuyer();
        distributorBuyer.setUpdateTime(time);
        distributorBuyer.setDistributorId(distributorId);
        distributorBuyer.setBuyerUserId(buyerUserId);
        distributorBuyer.setIfBind(IntegerEnum.NO.getCode());
        cereDistributorBuyerService.update(distributorBuyer);
    }

    private void updateAfterCancel(String afterId, String time, Integer state) throws CoBusinessException{
        //修改售后单状态为已关闭
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(Long.parseLong(afterId));
        cereOrderAfter.setAfterState(state);
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterService.update(cereOrderAfter);
    }

    private void updateBuyerCoupon(String[] split, String time, Integer state) throws CoBusinessException{
        Long buyerUserId=Long.parseLong(split[1]);
        Long activityId=Long.parseLong(split[2]);
        BigDecimal fullMoney=new BigDecimal(split[3]).setScale(2,BigDecimal.ROUND_HALF_UP);
        //更新用户优惠券状态为已过期
        CereBuyerCoupon cereBuyerCoupon=new CereBuyerCoupon();
        cereBuyerCoupon.setBuyerUserId(buyerUserId);
        cereBuyerCoupon.setActivityId(activityId);
        cereBuyerCoupon.setFullMoney(fullMoney);
        cereBuyerCoupon.setState(state);
        cereBuyerCoupon.setUpdateTime(time);
        cereBuyerCouponService.updateState(cereBuyerCoupon);
    }

    private void updateOrderState(String id, String time, Integer state) throws CoBusinessException{
        //更新订单状态为已完成
        CereShopOrder order=cereShopOrderService.findById(Long.parseLong(id));
        order.setState(state);
        order.setUpdateTime(time);
        order.setReceiveTime(time);
        cereShopOrderService.update(order);
        //新增订单已完成消息
        CereNotice cereNotice=new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_FINISH.getCode());
        cereNotice.setNoticeContent("订单"+order.getOrderFormid()+"已确认收货，期待您分享商品评价和购物心得");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
        //修改分销记录为已结算
        if (IntegerEnum.ORDER_FINISH.getCode().equals(state)) {
            cereDistributionOrderService.settleOrder(Long.parseLong(id));
        }
    }

    private void updateOrderClose(String id, String time) throws CoBusinessException{
        Long orderId=Long.parseLong(id);
        //更新订单状态为已关闭
        CereShopOrder order=cereShopOrderService.findById(orderId);
        order.setState(IntegerEnum.ORDER_STOP.getCode());
        order.setUpdateTime(time);
        cereShopOrderService.update(order);
        if(!EmptyUtils.isEmpty(order.getCouponId())){
            //修改平台优惠券状态为已领取
            CereBuyerCoupon cereBuyerCoupon=new CereBuyerCoupon();
            cereBuyerCoupon.setBuyerUserId(order.getBuyerUserId());
            cereBuyerCoupon.setCouponId(order.getCouponId());
            cereBuyerCoupon.setUpdateTime(time);
            cereBuyerCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
            cereBuyerCouponService.updateByUserIdAndCouponId(cereBuyerCoupon);
        }
        if(!EmptyUtils.isEmpty(order.getId())){
            //修改店铺优惠券状态为已领取
            CereBuyerShopCoupon cereBuyerShopCoupon=new CereBuyerShopCoupon();
            cereBuyerShopCoupon.setId(order.getId());
            cereBuyerShopCoupon.setUpdateTime(time);
            cereBuyerShopCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
            cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
        }
        // 库存回流
        cereStockService.rollbackStockByOrderId(orderId);
        /*List<CereProductSku> productSkus=cereProductSkuService.findStockNumberByOrderId(orderId);
        if(!EmptyUtils.isEmpty(productSkus)){
            productSkus.forEach(sku -> {
                int stockNumber = (int) redisTemplate.opsForValue().get(String.valueOf(sku.getSkuId()));
                redisTemplate.opsForValue().set(String.valueOf(sku.getSkuId()),stockNumber+sku.getStockNumber());
            });
        }
        if(!EmptyUtils.isEmpty(productSkus)){
            //批量更新库存数据
            cereShopOrderService.updateBatchStock(productSkus);
        }*/
        /*if(!EmptyUtils.isEmpty(order.getShopSeckillId())){
            //查询秒杀活动数据
            CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(order.getShopSeckillId());
            if(IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())){
                //如果限量,需要更新限量库存数据
                List<CereShopSeckillDetail> seckillDetails=cereShopSeckillDetailService.findNumberDetails(order.getOrderId(),cereShopSeckill.getShopSeckillId());
                if(!EmptyUtils.isEmpty(seckillDetails)){
                    //更新redis限量库存
                    seckillDetails.forEach(detail -> {
                        redisTemplate.opsForValue().set("秒杀活动商品仅剩件数"+detail.getShopSeckillId() + detail.getSkuId(),detail.getNumber());
                    });
                    cereShopSeckillDetailService.updateBatch(seckillDetails);
                }
            }
        }
        if(!EmptyUtils.isEmpty(order.getShopDiscountId())){
            //查询秒杀活动数据
            CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(order.getShopDiscountId());
            if(IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())){
                //如果限量,需要更新限量库存数据
                List<CereShopDiscountDetail> discountDetails=cereShopDiscountDetailService.findNumberDetails(order.getOrderId(),cereShopDiscount.getShopDiscountId());
                if(!EmptyUtils.isEmpty(discountDetails)){
                    //更新redis限量库存
                    discountDetails.forEach(detail -> {
                        redisTemplate.opsForValue().set("限时折扣活动商品仅剩件数"+detail.getShopDiscountId() + detail.getSkuId(),detail.getNumber());
                    });
                    cereShopDiscountDetailService.updateBatch(discountDetails);
                }
            }
        }*/
        //新增订单关闭消息
        CereNotice cereNotice=new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_CANCEL.getCode());
        cereNotice.setNoticeContent("您未在规定时间完成付款，订单"+order.getOrderFormid()+"已关闭,点击查看详情");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }
}
