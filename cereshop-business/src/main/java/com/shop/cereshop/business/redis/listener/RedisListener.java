/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.redis.listener;

import cn.hutool.core.date.DateUtil;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.service.activity.CereActivitySignService;
import com.shop.cereshop.business.service.business.CerePlatformBusinessService;
import com.shop.cereshop.business.service.compose.CereShopComposeService;
import com.shop.cereshop.business.service.notice.CereNoticeService;
import com.shop.cereshop.business.service.price.CereShopPriceService;
import com.shop.cereshop.business.service.product.CereShopProductService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.business.service.tool.*;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.scene.CereShopScene;
import com.shop.cereshop.commons.domain.tool.*;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisListener extends KeyExpirationEventMessageListener {

    private RedisTemplate<String, Object> redisTemplate;

    private CerePlatformBusinessService cerePlatformBusinessService;

    private CereRedisKeyServcice cereRedisKeyServcice;

    private CereNoticeService cereNoticeService;

    private CereShopGroupWorkService cereShopGroupWorkService;

    private CereShopGroupWorkDetailService cereShopGroupWorkDetailService;

    private CereShopProductService cereShopProductService;

    private CereShopSeckillService cereShopSeckillService;

    private CereShopSeckillDetailService cereShopSeckillDetailService;

    private CereShopDiscountService cereShopDiscountService;

    private CereShopDiscountDetailService cereShopDiscountDetailService;

    private CereShopCouponService cereShopCouponService;

    private CereShopOperateService cereShopOperateService;

    private CereActivitySignService cereActivitySignService;

    private CereShopSceneService cereShopSceneService;

    private CereShopComposeService cereShopComposeService;

    private CereShopPriceService cereShopPriceService;

    public RedisListener(RedisMessageListenerContainer listenerContainer,
                         RedisTemplate redisTemplate, CerePlatformBusinessService cerePlatformBusinessService
                         , CereRedisKeyServcice cereRedisKeyServcice, CereNoticeService cereNoticeService
                         , CereShopGroupWorkService cereShopGroupWorkService, CereShopGroupWorkDetailService cereShopGroupWorkDetailService
                         , CereShopProductService cereShopProductService, CereShopSeckillService cereShopSeckillService
                         , CereShopSeckillDetailService cereShopSeckillDetailService, CereShopDiscountService cereShopDiscountService
                         , CereShopDiscountDetailService cereShopDiscountDetailService, CereShopCouponService cereShopCouponService
                         , CereShopOperateService cereShopOperateService, CereActivitySignService cereActivitySignService,
                         CereShopSceneService cereShopSceneService,CereShopComposeService cereShopComposeService,
                         CereShopPriceService cereShopPriceService) {
        super(listenerContainer);
        this.redisTemplate=redisTemplate;
        this.cerePlatformBusinessService=cerePlatformBusinessService;
        this.cereRedisKeyServcice=cereRedisKeyServcice;
        this.cereNoticeService=cereNoticeService;
        this.cereShopGroupWorkService=cereShopGroupWorkService;
        this.cereShopGroupWorkDetailService=cereShopGroupWorkDetailService;
        this.cereShopProductService=cereShopProductService;
        this.cereShopSeckillService=cereShopSeckillService;
        this.cereShopSeckillDetailService=cereShopSeckillDetailService;
        this.cereShopDiscountService=cereShopDiscountService;
        this.cereShopDiscountDetailService=cereShopDiscountDetailService;
        this.cereShopCouponService=cereShopCouponService;
        this.cereShopOperateService=cereShopOperateService;
        this.cereActivitySignService=cereActivitySignService;
        this.cereShopSceneService=cereShopSceneService;
        this.cereShopComposeService=cereShopComposeService;
        this.cereShopPriceService=cereShopPriceService;
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
                    if(StringEnum.SEVEN_DAY_CHANGE_BUSINESS_TOKEN.getCode().equals(operation)){
                        //替换用户token
                        changeBusinessToken(split[1],time);
                    }else if(StringEnum.NOTICE_OPERATE.getCode().equals(operation)){
                        //手动计划运营计划定时发送消息通知
                        addOperate(split);
                    }else if(StringEnum.NOTICE_OPERATE_AUTOMATIC.getCode().equals(operation)){
                        //自动计划运营计划定时发送消息通知
                        addAutomaticOperate(split);
                    }else if(StringEnum.OPERATE_STATE_STAY.getCode().equals(operation)){
                        //修改运营计划状态为进行中
                        updateOperateState(split[1],IntegerEnum.COUPON_STATE_START.getCode());
                    }else if(StringEnum.OPERATE_STATE_STOP.getCode().equals(operation)){
                        //修改运营计划状态为已结束
                        updateOperateState(split[1],IntegerEnum.COUPON_STATE_END.getCode());
                    }else if(StringEnum.SHOP_COUPON_IN.getCode().equals(operation)){
                        //修改店铺优惠券状态为进行中
                        updateShopCouponState(split[1], IntegerEnum.COUPON_STATE_START.getCode(),time);
                    }else if(StringEnum.SHOP_COUPON_END.getCode().equals(operation)){
                        //修改店铺优惠券状态为已结束
                        updateShopCouponState(split[1], IntegerEnum.COUPON_STATE_END.getCode(),time);
                    }else if(StringEnum.SHOP_GROUP_WORK_IN.getCode().equals(operation)){
                        //修改拼团活动状态为进行中
                        updateShopGroupWorkState(split[1],IntegerEnum.COUPON_STATE_START.getCode(),time);
                    }else if(StringEnum.SHOP_GROUP_WORK_END.getCode().equals(operation)){
                        //修改拼团活动状态为已结束
                        updateShopGroupWorkState(split[1],IntegerEnum.COUPON_STATE_END.getCode(),time);
                    }else if(StringEnum.SHOP_SECKILL_IN.getCode().equals(operation)){
                        //修改秒杀活动状态为进行中
                        updateShopSeckillState(split[1],IntegerEnum.COUPON_STATE_START.getCode(),time);
                    }else if(StringEnum.SHOP_SECKILL_END.getCode().equals(operation)){
                        //修改秒杀活动状态为已结束
                        updateShopSeckillState(split[1],IntegerEnum.COUPON_STATE_END.getCode(),time);
                    }else if(StringEnum.SHOP_DISCOUNT_IN.getCode().equals(operation)){
                        //修改限时折扣活动状态为进行中
                        updateShopDiscountState(split[1],IntegerEnum.COUPON_STATE_START.getCode(),time);
                    }else if(StringEnum.SHOP_DISCOUNT_END.getCode().equals(operation)){
                        //修改限时折扣活动状态为已结束
                        updateShopDiscountState(split[1],IntegerEnum.COUPON_STATE_END.getCode(),time);
                    }else if(StringEnum.SIGN_NOT_PAY_DELETE.getCode().equals(operation)){
                        //报名未支付,删除报名数据
                        deleteSign(split[1]);
                    }else if(StringEnum.SCENE_START.getCode().equals(operation)){
                        //修改场景营销活动状态为进行中
                        updateSceneState(split[1],IntegerEnum.SCEME_STATE_START.getCode(),time);
                    }else if(StringEnum.SCENE_END.getCode().equals(operation)){
                        //修改场景营销活动状态为已停止
                        updateSceneState(split[1],IntegerEnum.SCEME_STATE_STOP.getCode(),time);
                    }else if(StringEnum.COMPOSE_START.getCode().equals(operation)){
                        //修改组合捆绑活动状态为进行中
                        updateComposeState(split[1],IntegerEnum.SHOP_COMPOSE_STATE_START.getCode(),time);
                    }else if(StringEnum.COMPOSE_END.getCode().equals(operation)){
                        //修改组合捆绑活动状态为已停止
                        updateComposeState(split[1],IntegerEnum.SHOP_COMPOSE_STATE_END.getCode(),time);
                    }else if(StringEnum.PRICE_START.getCode().equals(operation)){
                        //修改定价捆绑活动状态为进行中
                        updatePriceState(split[1],IntegerEnum.SHOP_COMPOSE_STATE_START.getCode(),time);
                    }else if(StringEnum.PRICE_END.getCode().equals(operation)){
                        //修改定价捆绑活动状态为已停止
                        updatePriceState(split[1],IntegerEnum.SHOP_COMPOSE_STATE_END.getCode(),time);
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

    private void updateSceneState(String id, Integer state, String time) throws CoBusinessException,Exception{
        Long sceneId = Long.parseLong(id);
        //查询场景营销活动数据
        CereShopScene cereShopScene=cereShopSceneService.findById(sceneId);
        if(cereShopScene!=null){
            cereShopScene.setState(state);
            cereShopScene.setUpdateTime(time);
            //更新场景营销状态
            cereShopSceneService.updateState(cereShopScene);
            if(IntegerEnum.SCEME_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopScene.getEndTime()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId(),cereShopScene.getEndTime());
            }
        }
    }

    private void updateComposeState(String id, Integer state, String time) throws CoBusinessException,Exception{
        Long composeId = Long.parseLong(id);
        //查询组合捆绑数据
        CereShopCompose cereShopCompose=cereShopComposeService.findById(composeId);
        if(cereShopCompose!=null){
            cereShopCompose.setState(state);
            cereShopCompose.setUpdateTime(time);
            //更新组合捆绑状态
            cereShopComposeService.updateState(cereShopCompose);
            if(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopCompose.getEndTime()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId(),cereShopCompose.getEndTime());
            }
        }
    }

    private void updatePriceState(String id, Integer state, String time) throws CoBusinessException,Exception{
        Long priceId = Long.parseLong(id);
        //查询定价捆绑数据
        CereShopPrice cereShopPrice=cereShopPriceService.findById(priceId);
        if(cereShopPrice!=null){
            cereShopPrice.setState(state);
            cereShopPrice.setUpdateTime(time);
            //更新定价捆绑状态
            cereShopPriceService.updateState(cereShopPrice);
            if(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopPrice.getEndTime()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId(),cereShopPrice.getEndTime());
            }
        }
    }

    private void deleteSign(String id) throws CoBusinessException{
        long signId = Long.parseLong(id);
        //删除操作日志
        cereActivitySignService.deleteLogBySignId(signId);
        //根据报名id删除报名数据和明细数据
        cereActivitySignService.deleteById(signId);
        cereActivitySignService.deleteDetailsBySignId(signId);
    }

    private void updateOperateState(String shopOperateId, Integer state) throws CoBusinessException,Exception{
        String time =TimeUtils.yyMMddHHmmss();
        CereShopOperate cereShopOperate=cereShopOperateService.findById(Long.parseLong(shopOperateId));
        if(cereShopOperate!=null){
            cereShopOperate.setState(state);
            cereShopOperate.setUpdateTime(time);
            cereShopOperateService.updateState(cereShopOperate);
            if(IntegerEnum.COUPON_STATE_START.getCode().equals(state)){
                //如果当前是修改状态为进行中,新增延时任务修改状态为已结束
                //新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+shopOperateId,1,
                        TimeUtils.getCountDownByTime(time,cereShopOperate.getPlanEnd()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+shopOperateId,cereShopOperate.getPlanEnd());
            }
        }
    }

    private void updateShopGroupWorkState(String id, Integer state,String time) throws CoBusinessException,Exception{
        Long shopGroupWorkId = Long.parseLong(id);
        //查询拼团活动数据
        CereShopGroupWork cereShopGroupWork=cereShopGroupWorkService.findById(shopGroupWorkId);
        if(cereShopGroupWork!=null){
            cereShopGroupWork.setState(state);
            cereShopGroupWork.setUpdateTime(time);
            //更新拼团活动状态
            cereShopGroupWorkService.updateState(cereShopGroupWork);
            if(IntegerEnum.COUPON_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopGroupWork.getEndTime()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.SHOP_GROUP_WORK_END.getCode()+"-"+cereShopGroupWork.getShopGroupWorkId(),cereShopGroupWork.getEndTime());
            }
            //查出对应商品，更新缓存
            List<Long> productIdList = cereShopGroupWorkDetailService.findProductIdList(shopGroupWorkId);
            for (Long productId:productIdList) {
                cereShopProductService.triggerCacheUpdate(productId);
            }
        }
    }

    private void updateShopSeckillState(String id, Integer state,String time) throws CoBusinessException,Exception{
        Long shopSeckillId = Long.parseLong(id);
        //查询秒杀活动数据
        CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(shopSeckillId);
        if(cereShopSeckill!=null){
            cereShopSeckill.setState(state);
            cereShopSeckill.setUpdateTime(time);
            //更新秒杀活动状态
            cereShopSeckillService.updateState(cereShopSeckill);
            if(IntegerEnum.COUPON_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopSeckill.getEffectiveEnd()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.SHOP_SECKILL_END.getCode()+"-"+cereShopSeckill.getShopSeckillId(),cereShopSeckill.getEffectiveEnd());
            }
            List<Long> productIdList = cereShopSeckillDetailService.findProductIdList(shopSeckillId);
            for (Long productId:productIdList) {
                cereShopProductService.triggerCacheUpdate(productId);
            }
            //更新sku活动库存
            if (IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())) {
                List<CereShopSeckillDetail> seckillDetailList = cereShopSeckillDetailService.findDetailList(shopSeckillId);
                LocalDateTime endTime = DateUtil.parseLocalDateTime(cereShopSeckill.getEffectiveEnd(), "yyyy-MM-dd HH:mm:ss");
                long millSeconds = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();
                if (millSeconds < 0) {
                    millSeconds = 0;
                }
                for (CereShopSeckillDetail detail:seckillDetailList) {
                    cereShopProductService.updateActivitySkuStock(detail.getSkuId(), IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode(),
                            state, detail.getNumber(), detail.getTotal(), millSeconds);
                }
            }
        }
    }

    private void updateShopDiscountState(String id, Integer state, String time) throws CoBusinessException,Exception{
        Long shopDiscountId = Long.parseLong(id);
        //查询限时折扣活动数据
        CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(shopDiscountId);
        if(cereShopDiscount!=null){
            cereShopDiscount.setState(state);
            cereShopDiscount.setUpdateTime(time);
            //更新限时折扣活动状态
            cereShopDiscountService.updateState(cereShopDiscount);
            if(IntegerEnum.COUPON_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopDiscount.getEndTime()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.SHOP_DISCOUNT_END.getCode()+"-"+cereShopDiscount.getShopDiscountId(),cereShopDiscount.getEndTime());
            }
            List<Long> productIdList = cereShopDiscountDetailService.findProductIdList(shopDiscountId);
            for (Long productId:productIdList) {
                cereShopProductService.triggerCacheUpdate(productId);
            }
            //更新sku活动库存
            if (IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())) {
                List<CereShopDiscountDetail> discountDetailList = cereShopDiscountDetailService.findDetailList(shopDiscountId);
                LocalDateTime endTime = DateUtil.parseLocalDateTime(cereShopDiscount.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                long millSeconds = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();
                if (millSeconds < 0) {
                    millSeconds = 0;
                }
                for (CereShopDiscountDetail detail:discountDetailList) {
                    cereShopProductService.updateActivitySkuStock(detail.getSkuId(), IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode(),
                            state, detail.getNumber(), detail.getTotal(), millSeconds);
                }
            }
        }
    }

    private void updateShopCouponState(String id, Integer state,String time) throws CoBusinessException,Exception{
        Long shopCouponId = Long.parseLong(id);
        //查询店铺优惠券数据
        CereShopCoupon cereShopCoupon=cereShopCouponService.findById(shopCouponId);
        if(cereShopCoupon!=null){
            cereShopCoupon.setState(state);
            cereShopCoupon.setUpdateTime(time);
            //更新店铺优惠券状态
            cereShopCouponService.updateState(cereShopCoupon);
            if(IntegerEnum.COUPON_STATE_START.getCode().equals(state)){
                //如果当前修改状态为进行中,修改之后新增延时任务修改状态为已结束
                redisTemplate.opsForValue().set(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId(),1,
                        TimeUtils.getCountDownByTime(time,cereShopCoupon.getEffectiveEnd()), TimeUnit.MILLISECONDS);
                //新增延时记录
                cereRedisKeyServcice.add(StringEnum.SHOP_COUPON_END.getCode()+"-"+cereShopCoupon.getShopCouponId(),cereShopCoupon.getEffectiveEnd());
            }else {
                //如果当前修改状态为已结束,需要同步修改客户领取该优惠券状态为已过期
                cereShopCouponService.updateBuyerCouponState(cereShopCoupon.getShopCouponId());
            }
        }
    }

    private void addOperate(String[] split) throws CoBusinessException{
        Long shopOperateId=Long.parseLong(split[1]);
        Long shopCrowdId=Long.parseLong(split[2]);
        Long shopId=Long.parseLong(split[3]);
        //查询运营计划优惠券数据
        List<OperateCoupon> coupons=cereShopOperateService.findCouponDetails(shopOperateId);
        //性钙运营计划状态为已结束
        CereShopOperate cereShopOperate=new CereShopOperate();
        cereShopOperate.setUpdateTime(TimeUtils.yyMMddHHmmss());
        cereShopOperate.setState(IntegerEnum.COUPON_STATE_END.getCode());
        cereShopOperate.setShopOperateId(shopOperateId);
        cereShopOperateService.updateState(cereShopOperate);
        cereNoticeService.addOperate(shopCrowdId,coupons,shopId,shopOperateId);
    }

    private void addAutomaticOperate(String[] split) throws CoBusinessException{
        Long shopOperateId=Long.parseLong(split[1]);
        Long shopCrowdId=Long.parseLong(split[2]);
        Long shopId=Long.parseLong(split[3]);
        //查询运营计划优惠券数据
        List<OperateCoupon> coupons=cereShopOperateService.findCouponDetails(shopOperateId);
        cereNoticeService.addOperate(shopCrowdId,coupons,shopId,shopOperateId);
    }

    private void changeBusinessToken(String businessUserId, String time) throws CoBusinessException{
        CerePlatformBusiness cerePlatformBusiness=new CerePlatformBusiness();
        cerePlatformBusiness.setBusinessUserId(Long.parseLong(businessUserId));
        cerePlatformBusiness.setUpdateTime(time);
        cerePlatformBusiness.setToken(RandomStringUtil.getRandomCode(128,0));
        cerePlatformBusinessService.updateToken(cerePlatformBusiness);
    }
}
