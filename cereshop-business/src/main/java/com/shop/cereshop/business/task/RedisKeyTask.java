/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.task;

import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.commons.domain.redis.CereRedisKey;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import lombok.Data;

import java.util.List;

/**
 * redis延时任务线程类
 */
@Data
public class RedisKeyTask implements Runnable{

    public RedisKeyTask(List<CereRedisKey> keyList, StringRedisService stringRedisService) {
        this.keyList = keyList;
        this.stringRedisService=stringRedisService;
    }

    /**
     * 延时数据
     */
    private List<CereRedisKey> keyList;

    /**
     * redis执行
     */
    private StringRedisService stringRedisService;

    @Override
    public void run() {
        try {
            if(!EmptyUtils.isEmpty(keyList)){
                for (CereRedisKey cereRedisKey:keyList) {
                    if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),cereRedisKey.getEndTime())){
                        //如果当前时间在截至时间之后,需要重新设置延时任务,10秒后立即执行
                        stringRedisService.set(cereRedisKey.getRedisKey(),1,10000);
                    }else {
                        //设置延时任务
                        stringRedisService.set(cereRedisKey.getRedisKey(),1,
                                TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),cereRedisKey.getEndTime()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
