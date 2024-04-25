/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.timing;


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
     * 开启客户标签自动绑定定时任务
     */
    public ScheduledFuture<?> startExtensionInitialization(String corn, LabelInitializationRunnable labelInitializationRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(labelInitializationRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启客户标签自动绑定定时任务！！！！");
        return future;
    }

    /**
     * 开启分销员等级自动升级定时任务
     */
    public ScheduledFuture<?> startAutomaticUpgradeInitialization(String corn, DistributionLevelInitializationRunnable distributionLevelInitializationRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(distributionLevelInitializationRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启分销员等级自动升级定时任务！！！！");
        return future;
    }

    /**
     * 开启商家合同有效期截止自动禁用定时任务
     */
    public ScheduledFuture<?> startStopShopInitialization(String corn, ShopInitializationRunnable shopInitializationRunnable){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        ScheduledFuture<?> future=threadPoolTaskScheduler.schedule(shopInitializationRunnable, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(corn).nextExecutionTime(triggerContext);
            }
        });
        System.out.println("成功开启商家合同有效期截止自动禁用定时任务！！！！");
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
