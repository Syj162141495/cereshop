/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_redis_key  redis的过期键信息实体
 * @author 
 */
@Data
public class CereRedisKey implements Serializable {
    /**
     * redis的延时任务key
     */
    private String redisKey;

    /**
     * 结束时间（到这个时间就需要业务处理）
     */
    private String endTime;

    private static final long serialVersionUID = 1L;

}
