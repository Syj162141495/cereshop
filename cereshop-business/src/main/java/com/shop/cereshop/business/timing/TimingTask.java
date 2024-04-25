/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.timing;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "TimingTask")
public class TimingTask {

    /**
     * 开启运营计划定时任务筛选出满足人群的新增客户并发送消息通知和发放优惠券
     */
    public ScheduledFuture<?> startExtensionInitialization(String corn, OperateInitializationRunnable operateInitializationRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(operateInitializationRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启运营计划发送消息通知定时任务！！！！");
        return future;
    }

    /**
     * 开启定时任务轮训所有规格库存为0的商品改为已下架状态
     */
    public ScheduledFuture<?> startProductDownInitialization(String corn, ProductDownRunnable productDownRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(productDownRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启时任务轮训所有规格库存为0的商品改为已下架状态！！！！");
        return future;
    }

    /**
     * 开启定时任务轮训所有人群,重新筛选当前最新满足条件的客户数据
     */
    public ScheduledFuture<?> startUpdateCrowdPersonInitialization(String corn, UpdateCrowdPersonRunnable updateCrowdPersonRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(updateCrowdPersonRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启定时任务轮训所有人群,重新筛选当前最新满足条件的客户数据！！！！");
        return future;
    }

    /**
     * 开启营销活动状态补偿修改定时任务
     */
    public ScheduledFuture<?> startToolUpdateInitialization(String corn, ToolRunnable toolRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(toolRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启营销活动状态补偿修改定时任务！！！！");
        return future;
    }

    /**
     * 关闭定时任务
     */
    public static void stop(ScheduledFuture<?> future){
        if(future!=null){
            future.cancel(true);
            System.out.println("成功关闭定时任务！！！！！");
        }
    }

}
