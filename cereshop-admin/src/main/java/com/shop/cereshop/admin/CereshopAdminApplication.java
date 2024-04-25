/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.service.distributor.CereShopDistributionLevelService;
import com.shop.cereshop.admin.service.label.CerePlatformLabelService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.admin.service.shop.CerePlatformShopService;
import com.shop.cereshop.admin.timing.DistributionLevelInitializationRunnable;
import com.shop.cereshop.admin.timing.LabelInitializationRunnable;
import com.shop.cereshop.admin.timing.ShopInitializationRunnable;
import com.shop.cereshop.admin.timing.TimingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ScheduledFuture;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableAsync
@ComponentScan(value = "com.shop.cereshop.commons.utils")
@ComponentScan(value = "com.shop.cereshop.admin")
@ComponentScan(value = "com.shop.cereshop.commons.config")
public class CereshopAdminApplication extends SpringBootServletInitializer {

    @Autowired
    private static CerePlatformLabelService cerePlatformLabelService;
    @Autowired
    private static CereShopDistributionLevelService cereShopDistributionLevelService;
    @Autowired
    private static CerePlatformShopService cerePlatformShopService;
    @Autowired
    private static CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    public void setCerePlatformShopService(CerePlatformShopService cerePlatformShopService) {
        CereshopAdminApplication.cerePlatformShopService = cerePlatformShopService;
    }

    @Autowired
    public void setCerePlatformLabelService(CerePlatformLabelService cerePlatformLabelService){
        CereshopAdminApplication.cerePlatformLabelService=cerePlatformLabelService;
    }

    @Autowired
    public void setCereShopDistributionLevelService(CereShopDistributionLevelService cereShopDistributionLevelService){
        CereshopAdminApplication.cereShopDistributionLevelService=cereShopDistributionLevelService;
    }

    @Autowired
    public void setCereRedisKeyServcice(CereRedisKeyServcice cereRedisKeyServcice) {
        CereshopAdminApplication.cereRedisKeyServcice = cereRedisKeyServcice;
    }

    /**
     * 需要把web项目打成war包部署到外部tomcat运行时需要改变启动方式
     */
    @Override  //重写configure方法，把springboot的入口给它
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CereshopAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CereshopAdminApplication.class, args);
        //开启自动绑定标签定时任务
        TimingTask timingTask=new TimingTask();
        ScheduledFuture<?> labelFuture = timingTask.startExtensionInitialization("0 0 0 * * ?",
                new LabelInitializationRunnable(cerePlatformLabelService));
        //开启分销员等级自动升级定时任务
        ScheduledFuture<?> LevelFutrue = timingTask.startAutomaticUpgradeInitialization("0 0 0 * * ?",
                new DistributionLevelInitializationRunnable(cereShopDistributionLevelService));
        //开启商家合同有效期截止自动禁用定时任务
        ScheduledFuture<?> shopFutrue = timingTask.startStopShopInitialization("0 0 0 * * ?",
                new ShopInitializationRunnable(cerePlatformShopService));
        try {
            //初始化Redis延时任务
            cereRedisKeyServcice.initializationRedis();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("------   admin-server启动成功    ------\n");
        }
    }
}
