/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.redis.service.api;

public interface UserRedisService {

    /** 保存用户id到redis中 */
    void saveUser(String token, Long buyerUserId);

    /** 根据token获取用户id */
    Long getBuyerUserIdByToken(String token);

    void refreshToken(String token);

}
