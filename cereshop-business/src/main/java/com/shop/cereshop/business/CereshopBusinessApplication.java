/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business;

import com.shop.cereshop.business.service.common.CommonService;
import com.shop.cereshop.business.service.product.CereShopProductService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.business.service.tool.CereShopCrowdService;
import com.shop.cereshop.business.service.tool.CereShopOperateService;
import com.shop.cereshop.business.timing.*;
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
@ComponentScan(value = "com.shop.cereshop.business")
@ComponentScan(value = "com.shop.cereshop.commons.config")
public class CereshopBusinessApplication extends SpringBootServletInitializer {

    @Autowired
    private static CereShopOperateService cereShopOperateService;

    @Autowired
    private static CereShopProductService cereShopProductService;

    @Autowired
    private static CereShopCrowdService cereShopCrowdService;

    @Autowired
    private static CereShopSceneService cereShopSceneService;

    @Autowired
    private static CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private static CommonService commonService;

    @Autowired
    public void setCereShopCrowdService(CereShopCrowdService cereShopCrowdService) {
        CereshopBusinessApplication.cereShopCrowdService = cereShopCrowdService;
    }

    @Autowired
    public void setCereShopProductService(CereShopProductService cereShopProductService) {
        CereshopBusinessApplication.cereShopProductService = cereShopProductService;
    }

    @Autowired
    public void setCereShopOperateService(CereShopOperateService cereShopOperateService) {
        CereshopBusinessApplication.cereShopOperateService = cereShopOperateService;
    }

    @Autowired
    public void setCereShopSceneService(CereShopSceneService cereShopSceneService) {
        CereshopBusinessApplication.cereShopSceneService = cereShopSceneService;
    }

    @Autowired
    public void setCereRedisKeyServcice(CereRedisKeyServcice cereRedisKeyServcice) {
        CereshopBusinessApplication.cereRedisKeyServcice = cereRedisKeyServcice;
    }

    @Autowired
    public void setCommonService(CommonService commonService) {
        CereshopBusinessApplication.commonService = commonService;
    }

    /**
     * 需要把web项目打成war包部署到外部tomcat运行时需要改变启动方式
     */
    @Override  //重写configure方法，把springboot的入口给它
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CereshopBusinessApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CereshopBusinessApplication.class, args);
        //开启运营计划定时任务
        TimingTask timingTask=new TimingTask();
        //运营计划定时任务筛选出满足人群的新增客户并发送消息通知和发放优惠券
        ScheduledFuture<?> operateFuture = timingTask.startExtensionInitialization("0 0 0 * * ?",
                new OperateInitializationRunnable(cereShopOperateService));
        //定时任务轮训所有规格库存为0的商品改为已下架状态
        ScheduledFuture<?> productDown = timingTask.startProductDownInitialization("0 0 0 * * ?",
                new ProductDownRunnable(cereShopProductService));
        //定时任务轮训所有人群,重新筛选当前最新满足条件的客户数据
        ScheduledFuture<?> crowdPerson = timingTask.startUpdateCrowdPersonInitialization("0 0 0 * * ?",
                new UpdateCrowdPersonRunnable(cereShopCrowdService));
        //开启营销活动状态补偿修改定时任务
        ScheduledFuture<?> tool = timingTask.startToolUpdateInitialization("0 0 0 * * ?",
                new ToolRunnable(commonService));
//        //定时任务轮训所有场景营销活动,更新状态(无用)
//        ScheduledFuture<?> scene = timingTask.startUpdateCrowdPersonInitialization("0 0 0 * * ?",
//                new UpdateCrowdPersonRunnable(cereShopCrowdService));
        try {
            //初始化Redis延时任务
            cereRedisKeyServcice.initializationRedis();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("------   business-server启动成功    ------\n");
        }
    }

}
