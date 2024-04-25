/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.redis.listener;

import cn.hutool.core.date.DateUtil;
import com.shop.cereshop.admin.dao.order.CereShopOrderDAO;
import com.shop.cereshop.admin.pay.PayFactory;
import com.shop.cereshop.admin.service.activity.CereActivitySignService;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityService;
import com.shop.cereshop.admin.service.activity.CereSignProductService;
import com.shop.cereshop.admin.service.order.CereOrderReconciliationService;
import com.shop.cereshop.admin.service.pay.CerePayLogService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.admin.service.product.CereShopProductService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.admin.service.user.CerePlatformUserService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.constant.WxPayEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.activity.CereSignProduct;
import com.shop.cereshop.commons.domain.order.CereOrderReconciliation;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisListener extends KeyExpirationEventMessageListener {

    private RedisTemplate<String, Object> redisTemplate;

    private CerePlatformUserService cerePlatformUserService;

    private CerePlatformActivityService cerePlatformActivityService;

    private CereRedisKeyServcice cereRedisKeyServcice;

    private CereActivitySignService cereActivitySignService;

    private CerePlatformSeckillService cerePlatformSeckillService;

    private CerePlatformDiscountService cerePlatformDiscountService;

    private CerePlatformPoliteService cerePlatformPoliteService;

    private CereShopProductService cereShopProductService;

    private CereSignProductService cereSignProductService;

    @Value("${bond_payment_mode}")
    private Integer bondPaymentMode;


    public RedisListener(RedisMessageListenerContainer listenerContainer,
                         RedisTemplate redisTemplate, CerePlatformUserService cerePlatformUserService,
                         CerePlatformActivityService cerePlatformActivityService,
                         CereRedisKeyServcice cereRedisKeyServcice,
                         CereActivitySignService cereActivitySignService,
                         CerePlatformSeckillService cerePlatformSeckillService,
                         CerePlatformDiscountService cerePlatformDiscountService,
                         CerePlatformPoliteService cerePlatformPoliteService,
                         CereShopProductService cereShopProductService,
                         CereSignProductService cereSignProductService) {
        super(listenerContainer);
        this.redisTemplate=redisTemplate;
        this.cerePlatformUserService=cerePlatformUserService;
        this.cerePlatformActivityService=cerePlatformActivityService;
        this.cereRedisKeyServcice=cereRedisKeyServcice;
        this.cereActivitySignService=cereActivitySignService;
        this.cerePlatformSeckillService=cerePlatformSeckillService;
        this.cerePlatformDiscountService=cerePlatformDiscountService;
        this.cerePlatformPoliteService=cerePlatformPoliteService;
        this.cereShopProductService=cereShopProductService;
        this.cereSignProductService=cereSignProductService;
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
                    if(StringEnum.SEVEN_DAY_CHANGE_TOKEN.getCode().equals(operation)){
                        //替换用户token
                        changeUserToken(split[1],time);
                    }else if(StringEnum.SHOP_SIGN_IN.getCode().equals(operation)){
                        //修改当前活动状态为报名进行中
                        updateActivityState(split[1],time, IntegerEnum.ACTIVITY_SIGN_ON.getCode());
                    }else if(StringEnum.SHOP_ACTIVITY_STAY.getCode().equals(operation)){
                        //修改当前活动状态为活动待开始
                        updateActivityState(split[1],time, IntegerEnum.ACTIVITY_STAY_START.getCode());
                    }else if(StringEnum.SHOP_ACTIVITY_IN.getCode().equals(operation)){
                        //修改当前活动状态为活动进行中
                        updateActivityState(split[1],time, IntegerEnum.ACTIVITY_START.getCode());
                    }else if(StringEnum.SHOP_ACTIVITY_END.getCode().equals(operation)){
                        //修改当前活动状态为活动已结束
                        updateActivityState(split[1],time, IntegerEnum.ACTIVITY_END.getCode());
                    }else if(StringEnum.THREE_DAY_REFUND_BOND.getCode().equals(operation)){
                        //报名失败3天后自动退款
                        signError(split[1],split[2]);
                    }else if(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode().equals(operation)){
                        //活动结束15天后自动退款至商家微信
                        signRefund(split[1],split[2]);
                    }else if(StringEnum.PLATFORM_SECKILL_SIGN_IN.getCode().equals(operation)){
                        //修改当前秒杀活动状态为报名进行中
                        updateSeckillState(split[1],time, IntegerEnum.ACTIVITY_SIGN_ON.getCode());
                    }else if(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode().equals(operation)){
                        //修改当前秒杀活动状态为活动待开始
                        updateSeckillState(split[1],time, IntegerEnum.ACTIVITY_STAY_START.getCode());
                    }else if(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode().equals(operation)){
                        //修改当前秒杀活动状态为活动进行中
                        updateSeckillState(split[1],time, IntegerEnum.ACTIVITY_START.getCode());
                    }else if(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode().equals(operation)){
                        //修改当前秒杀活动状态为活动已结束
                        updateSeckillState(split[1],time, IntegerEnum.ACTIVITY_END.getCode());
                    }else if(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode().equals(operation)){
                        //修改当前限时折扣活动状态为报名进行中
                        updateDiscountState(split[1],time, IntegerEnum.ACTIVITY_SIGN_ON.getCode());
                    }else if(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode().equals(operation)){
                        //修改当前限时折扣活动状态为活动待开始
                        updateDiscountState(split[1],time, IntegerEnum.ACTIVITY_STAY_START.getCode());
                    }else if(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode().equals(operation)){
                        //修改当前限时折扣活动状态为活动进行中
                        updateDiscountState(split[1],time, IntegerEnum.ACTIVITY_START.getCode());
                    }else if(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode().equals(operation)){
                        //修改当前限时折扣活动状态为活动已结束
                        updateDiscountState(split[1],time, IntegerEnum.ACTIVITY_END.getCode());
                    }else if(StringEnum.POLITE_START.getCode().equals(operation)){
                        //修改平台支付有礼活动状态为进行中
                        updatePoliteState(split[1],time, IntegerEnum.POLITE_ON.getCode());
                    }else if(StringEnum.POLITE_END.getCode().equals(operation)){
                        //修改平台支付有礼活动状态为已结束
                        updatePoliteState(split[1],time, IntegerEnum.POLITE_END.getCode());
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

    private void signRefund(String signId,String signType) throws CoBusinessException,Exception{
        int type=Integer.parseInt(signType);
        //根据报名id查询报名数据
        CereActivitySign activitySign=cerePlatformActivityService.findSign(signId);
        if(activitySign!=null){
            String orderFormid="";
            if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(signType)){
                //平台优惠券
                orderFormid="YHQBZJ-"+ activitySign.getShopId() + "-" + activitySign.getActivityId();
            }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(signType)){
                //平台秒杀
                orderFormid="MSBZJ-"+ activitySign.getShopId() + "-" + activitySign.getActivityId();
            }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(signType)){
                //平台限时折扣
                orderFormid="XSZKBZJ-"+ activitySign.getShopId() + "-" + activitySign.getActivityId();
            }
            //查询保证金支付流水,自动退保证金
            CerePayLog bondPayLog = cerePlatformActivityService.findBondPayLog(orderFormid);
            if(bondPayLog!=null) {
                //退款保证金
                refundBond(bondPayLog,type);
            }
        }
    }

    private void signError(String signId,String signType) throws CoBusinessException,Exception{
        int type=Integer.parseInt(signType);
        //根据报名id查询报名数据
        CereActivitySign activitySign=cerePlatformActivityService.findSign(signId);
        if(activitySign!=null){
            //根据店铺id和活动id查询除了当前报名数据以外是否有其他报名未失败的数据
            CereActivitySign checkSignError=cerePlatformActivityService.checkSignError(activitySign);
            if(checkSignError==null){
                String orderFormId="";
                if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(signType)){
                    //平台优惠券
                    orderFormId="YHQBZJ-"+ activitySign.getShopId() + "-" + activitySign.getActivityId();
                }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(signType)){
                    //平台秒杀
                    orderFormId="MSBZJ-"+ activitySign.getShopId() + "-" + activitySign.getActivityId();
                }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(signType)){
                    //平台限时折扣
                    orderFormId="XSZKBZJ-"+ activitySign.getShopId() + "-" + activitySign.getActivityId();
                }
                //查询保证金支付流水,自动退保证金
                CerePayLog bondPayLog = cerePlatformActivityService.findBondPayLog(orderFormId);
                if(bondPayLog!=null){
                    //退款保证金
                    refundBond(bondPayLog,type);
                    //查询所有报名数据,清空延时任务
                    List<Long> ids=cerePlatformActivityService.findSignIdsByShopIdAndActivityId(activitySign.getShopId(),activitySign.getActivityId(),type);
                    if(!EmptyUtils.isEmpty(ids)){
                        for (Long id:ids) {
                            redisTemplate.delete(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+id+"-"+type);
                            //同步删除redis延时任务记录
                            cereRedisKeyServcice.deleteByKey(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+id+"-"+type);
                        }
                    }
                }
            }
        }
    }

    private void updateActivityState(String id, String time, Integer state) throws CoBusinessException,Exception{
        Long activityId = Long.parseLong(id);
        //查询活动数据
        CerePlatformActivity cerePlatformActivity=cerePlatformActivityService.findById(activityId);
        cerePlatformActivity.setActivityId(activityId);
        cerePlatformActivity.setUpdateTime(time);
        cerePlatformActivity.setState(state);
        cerePlatformActivityService.updateActivity(cerePlatformActivity);
        if(IntegerEnum.ACTIVITY_SIGN_ON.getCode().equals(state)){
            //如果当前是修改状态为报名进行中,新增延时任务修改状态为活动待开始
            //过期时间为报名结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformActivity.getSignEndTime()), TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+cerePlatformActivity.getActivityId(),cerePlatformActivity.getSignEndTime());
        }else if(IntegerEnum.ACTIVITY_STAY_START.getCode().equals(state)){
            //如果当前是修改状态为活动待开始,新增延时任务修改状态为活动进行中
            //过期时间为活动开始时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformActivity.getActivityStartTime()),TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),cerePlatformActivity.getActivityStartTime());
        }else if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
            //如果当前是修改状态为活动进行中,新增延时任务修改状态为活动已结束
            //过期时间为活动结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformActivity.getActivityEndTime()),TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+cerePlatformActivity.getActivityId(),cerePlatformActivity.getActivityEndTime());
        }
        //查询活动是否需要保证金
        Integer ifBond=cerePlatformActivityService.findIfBond(activityId);
        if(IntegerEnum.ACTIVITY_END.getCode().equals(state)&&IntegerEnum.YES.getCode().equals(ifBond)){
            //如果活动已结束
            //新增延时任务15天后所有参与该活动的商家自动退还保证金
            List<CereActivitySign> list=cerePlatformActivityService.findByActivity(activityId);
            if(!EmptyUtils.isEmpty(list)){
                for (CereActivitySign activitySign:list) {
                    redisTemplate.opsForValue().set(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+activitySign.getSignId()+"-"+"1",1,
                            TimeUtils.getCountDownByTime(time,TimeUtils.getMoreDayAfter(time,15)),TimeUnit.MILLISECONDS);
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+activitySign.getSignId()+"-"+"1",
                            TimeUtils.getMoreDayAfter(time,15));
                }
            }
        }else if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
            //如果活动刚开始,未审核或者审核失败都视为报名失败,自动退保证金
            List<CereActivitySign> signs=cerePlatformActivityService.findErrorSign(activityId);
            if(!EmptyUtils.isEmpty(signs)){
                //更新报名状态为报名失败
                cerePlatformActivityService.updateSignStateError(signs);
                if(IntegerEnum.YES.getCode().equals(ifBond)){
                    //退还保证金
                    for (CereActivitySign sign:signs) {
                        //查询保证金支付流水,自动退保证金
                        CerePayLog bondPayLog = cerePlatformActivityService.findBondPayLog("YHQBZJ-" + sign.getShopId() + "-" + sign.getActivityId());
                        if(bondPayLog!=null){
                            //退款保证金
                            refundBond(bondPayLog,IntegerEnum.ACTIVITY_COUPON.getCode());
                        }
                    }
                }
            }
        }
    }

    private void updateSeckillState(String id, String time, Integer state) throws CoBusinessException,Exception{
        Long seckIllId = Long.parseLong(id);
        //查询活动数据
        CerePlatformSeckill cerePlatformSeckill=cerePlatformSeckillService.getById(seckIllId);
        cerePlatformSeckill.setSeckillId(seckIllId);
        cerePlatformSeckill.setUpdateTime(time);
        cerePlatformSeckill.setState(state);
        cerePlatformSeckillService.updateSeckill(cerePlatformSeckill);
        if(IntegerEnum.ACTIVITY_SIGN_ON.getCode().equals(state)){
            //如果当前是修改状态为报名进行中,新增延时任务修改状态为活动待开始
            //过期时间为报名结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformSeckill.getSignEndTime()), TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_ACTIVITY_STAY.getCode()+"-"+cerePlatformSeckill.getSeckillId(),cerePlatformSeckill.getSignEndTime());
        }else if(IntegerEnum.ACTIVITY_STAY_START.getCode().equals(state)){
            //如果当前是修改状态为活动待开始,新增延时任务修改状态为活动进行中
            //过期时间为活动开始时间-当前时间
            long countdownTime = TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformSeckill.getStartTime());
            if (countdownTime < 1L) {
                countdownTime = 1L;
            }
            redisTemplate.opsForValue().set(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    countdownTime,TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_ACTIVITY_IN.getCode()+"-"+cerePlatformSeckill.getSeckillId(),cerePlatformSeckill.getStartTime());
        }else if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
            //如果当前是修改状态为活动进行中,新增延时任务修改状态为活动已结束
            //过期时间为活动结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+cerePlatformSeckill.getSeckillId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformSeckill.getEndTime()),TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_SECKILL_ACTIVITY_END.getCode()+"-"+cerePlatformSeckill.getSeckillId(),cerePlatformSeckill.getEndTime());
        }
        //查询活动是否需要保证金
        boolean signError = false;
        if(IntegerEnum.ACTIVITY_END.getCode().equals(state)&&IntegerEnum.YES.getCode().equals(cerePlatformSeckill.getIfBond())){
            //如果活动已结束
            //新增延时任务15天后所有参与该活动的商家自动退还保证金
            List<CereActivitySign> list=cerePlatformSeckillService.findBySeckill(seckIllId);
            if(!EmptyUtils.isEmpty(list)){
                for (CereActivitySign activitySign:list) {
                    redisTemplate.opsForValue().set(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+activitySign.getSignId()+"-"+"2",1,
                            TimeUtils.getCountDownByTime(time,TimeUtils.getMoreDayAfter(time,15)),TimeUnit.MILLISECONDS);
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+activitySign.getSignId()+"-"+"2",
                            TimeUtils.getMoreDayAfter(time,15));
                }
            }
        }else if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
            //如果活动刚开始,未审核或者审核失败都视为报名失败,自动退保证金
            List<CereActivitySign> signs=cerePlatformSeckillService.findErrorSign(seckIllId);
            if(!EmptyUtils.isEmpty(signs)){
                signError = true;
                //更新报名状态为报名失败
                cerePlatformActivityService.updateSignStateError(signs);
                if(IntegerEnum.YES.getCode().equals(cerePlatformSeckill.getIfBond())){
                    //退还保证金
                    for (CereActivitySign sign:signs) {
                        //查询保证金支付流水,自动退保证金
                        CerePayLog bondPayLog = cerePlatformActivityService.findBondPayLog("MSBZJ-" + sign.getShopId() + "-" + sign.getActivityId());
                        if(bondPayLog!=null){
                            //退款保证金
                            refundBond(bondPayLog,IntegerEnum.ACTIVITY_SECKILL.getCode());
                        }
                    }
                }
            }
        }
        List<Long> productIdList = cerePlatformSeckillService.findProductIdList(seckIllId);
        for (Long productId:productIdList) {
            cereShopProductService.triggerCacheUpdate(productId);
        }
        if (state.equals(IntegerEnum.ACTIVITY_START.getCode()) && !signError
                || state.equals(IntegerEnum.ACTIVITY_END.getCode())) {
            LocalDateTime endTime = DateUtil.parseLocalDateTime(cerePlatformSeckill.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            long millSeconds = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();
            if (millSeconds < 0) {
                millSeconds = 0;
            }
            List<CereSignProduct> signProductList = cereSignProductService.selectSignProductList(IntegerEnum.ACTIVITY_SECKILL.getCode(), seckIllId);
            for (CereSignProduct signProduct:signProductList) {
                cereShopProductService.updateActivityProductStock(signProduct.getProductId(), IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode(),
                        state, signProduct.getNumber(), signProduct.getTotal(), millSeconds);
            }
        }
    }

    private void updatePoliteState(String id, String time, Integer state) throws CoBusinessException,Exception{
        Long politeId=Long.parseLong(id);
        //查询活动数据
        CerePlatformPolite cerePlatformPolite=cerePlatformPoliteService.findById(politeId);
        if(cerePlatformPolite!=null){
            cerePlatformPolite.setState(state);
            cerePlatformPolite.setUpdateTime(time);
            cerePlatformPoliteService.updatePolite(cerePlatformPolite);
        }
        if(IntegerEnum.POLITE_ON.getCode().equals(state)){
            //如果当前是修改状态为进行中,新增延时任务修改状态为已结束
            //过期时间为报名结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformPolite.getEndTime()), TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId(),cerePlatformPolite.getEndTime());
        }
    }

    private void updateDiscountState(String id, String time, Integer state) throws CoBusinessException,Exception{
        Long discountId = Long.parseLong(id);
        //查询活动数据
        CerePlatformDiscount cerePlatformDiscount=cerePlatformDiscountService.getById(discountId);
        cerePlatformDiscount.setDiscountId(discountId);
        cerePlatformDiscount.setUpdateTime(time);
        cerePlatformDiscount.setState(state);
        cerePlatformDiscountService.updateDiscount(cerePlatformDiscount);
        if(IntegerEnum.ACTIVITY_SIGN_ON.getCode().equals(state)){
            //如果当前是修改状态为报名进行中,新增延时任务修改状态为活动待开始
            //过期时间为报名结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformDiscount.getSignEndTime()), TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+cerePlatformDiscount.getDiscountId(),cerePlatformDiscount.getSignEndTime());
        }else if(IntegerEnum.ACTIVITY_STAY_START.getCode().equals(state)){
            //如果当前是修改状态为活动待开始,新增延时任务修改状态为活动进行中
            //过期时间为活动开始时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformDiscount.getStartTime()),TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),cerePlatformDiscount.getStartTime());
        }else if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
            //如果当前是修改状态为活动进行中,新增延时任务修改状态为活动已结束
            //过期时间为活动结束时间-当前时间
            redisTemplate.opsForValue().set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cerePlatformDiscount.getEndTime()),TimeUnit.MILLISECONDS);
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+cerePlatformDiscount.getDiscountId(),cerePlatformDiscount.getEndTime());
        }
        //查询活动是否需要保证金
        boolean signError = false;
        if(IntegerEnum.ACTIVITY_END.getCode().equals(state)&&IntegerEnum.YES.getCode().equals(cerePlatformDiscount.getIfBond())){
            //如果活动已结束
            //新增延时任务15天后所有参与该活动的商家自动退还保证金
            List<CereActivitySign> list=cerePlatformDiscountService.findByDiscount(discountId);
            if(!EmptyUtils.isEmpty(list)){
                for (CereActivitySign activitySign:list) {
                    redisTemplate.opsForValue().set(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+activitySign.getSignId()+"-"+"3",1,
                            TimeUtils.getCountDownByTime(time,TimeUtils.getMoreDayAfter(time,15)),TimeUnit.MILLISECONDS);
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+activitySign.getSignId()+"-"+"3",
                            TimeUtils.getMoreDayAfter(time,15));
                }
            }
        }else if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
            //如果活动刚开始,未审核或者审核失败都视为报名失败,自动退保证金
            List<CereActivitySign> signs=cerePlatformDiscountService.findErrorSign(discountId);
            if(!EmptyUtils.isEmpty(signs)){
                signError = true;
                //更新报名状态为报名失败
                cerePlatformActivityService.updateSignStateError(signs);
                if(IntegerEnum.YES.getCode().equals(cerePlatformDiscount.getIfBond())){
                    //退还保证金
                    for (CereActivitySign sign:signs) {
                        //查询保证金支付流水,自动退保证金
                        CerePayLog bondPayLog = cerePlatformActivityService.findBondPayLog("XSZKBZJ-" + sign.getShopId() + "-" + sign.getActivityId());
                        if(bondPayLog!=null){
                            //退款保证金
                            refundBond(bondPayLog,IntegerEnum.ACTIVITY_DISCOUNT.getCode());
                        }
                    }
                }
            }
        }
        List<Long> productIdList = cerePlatformDiscountService.findProductIdList(discountId);
        for (Long productId:productIdList) {
            cereShopProductService.triggerCacheUpdate(productId);
        }
        if (state.equals(IntegerEnum.ACTIVITY_START.getCode()) && !signError
                || state.equals(IntegerEnum.ACTIVITY_END.getCode())) {
            LocalDateTime endTime = DateUtil.parseLocalDateTime(cerePlatformDiscount.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            long millSeconds = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();
            if (millSeconds < 0) {
                millSeconds = 0;
            }
            List<CereSignProduct> signProductList = cereSignProductService.selectSignProductList(IntegerEnum.ACTIVITY_DISCOUNT.getCode(), discountId);
            for (CereSignProduct signProduct:signProductList) {
                cereShopProductService.updateActivityProductStock(signProduct.getProductId(), IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode(),
                        state, signProduct.getNumber(), signProduct.getTotal(), millSeconds);
            }
        }
    }

    private void changeUserToken(String platformUserId, String time) throws CoBusinessException {
        CerePlatformUser cerePlatformUser=new CerePlatformUser();
        cerePlatformUser.setPlatformUserId(Long.parseLong(platformUserId));
        cerePlatformUser.setUpdateTime(time);
        cerePlatformUser.setToken(RandomStringUtil.getRandomCode(128,0));
        cerePlatformUserService.updateToken(cerePlatformUser);
    }

    /**
     * 调用退款保证金请求，并且判断如果是支付宝的退款，同步处理
     * @param bondPayLog
     * @throws Exception
     */
    private void refundBond(CerePayLog bondPayLog,int signType) throws Exception {
        Map<String, String> resultMap = PayFactory.getPayService(bondPaymentMode).refundBond(bondPayLog.getTransactionId(),bondPayLog.getOutRefundNo(),bondPayLog.getTotalFee(),bondPayLog.getTotalFee());
        if(!EmptyUtils.isEmpty(resultMap)){
            if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                    resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                //退款成功 支付宝的退款是立即退款的，需要同步处理
                if (IntegerEnum.ORDER_PAY_ALI.getCode().equals(bondPayLog.getPaymentMode())) {
                    String orderNo = resultMap.get("out_trade_no");
                    //截取订单编号
                    String[] split = orderNo.split("-");
                    cereActivitySignService.refundBond(split,resultMap.get("transaction_id"),orderNo,signType);
                }
            }
        }
    }

}
