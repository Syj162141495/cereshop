/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.redis.service.api.impl;

import com.shop.cereshop.app.redis.service.api.UserRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserRedisServiceImpl implements UserRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /** 用户过期时间设置为 12小时 */
    private static final Long USER_EXPIRE_TIME = 12 * 60 * 60L;

    /** 用户id -> token 对应关系的redis key */
    private static final String USER_TOKEN_PREFIX = "user_token.";

    @Override
    public void saveUser(String token, Long buyerUserId) {
        Object oldTokenKey = redisTemplate.opsForValue().get(USER_TOKEN_PREFIX + buyerUserId);
        if (oldTokenKey != null) {
            redisTemplate.delete(oldTokenKey);
        }
        redisTemplate.opsForValue().set(token, buyerUserId, USER_EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(USER_TOKEN_PREFIX + buyerUserId, token);
    }

    @Override
    public Long getBuyerUserIdByToken(String token) {
        if (StringUtils.isNotBlank(token)) {
            Object obj = redisTemplate.opsForValue().get(token);
            if (obj != null) {
                return Long.valueOf(obj.toString());
            }
        }
        return null;
    }

    @Override
    public void refreshToken(String token) {
        redisTemplate.expire(token, USER_EXPIRE_TIME, TimeUnit.SECONDS);
    }
}
