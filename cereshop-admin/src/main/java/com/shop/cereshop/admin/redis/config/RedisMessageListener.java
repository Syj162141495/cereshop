/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.redis.config;

import com.shop.cereshop.admin.redis.listener.RedisListener;
import com.shop.cereshop.admin.service.activity.CereActivitySignService;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityService;
import com.shop.cereshop.admin.service.activity.CereSignProductService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.admin.service.product.CereShopProductService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.admin.service.user.CerePlatformUserService;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisMessageListener {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private CerePlatformUserService cerePlatformUserService;

	@Autowired
	private CerePlatformActivityService cerePlatformActivityService;

	@Autowired
	private CereRedisKeyServcice cereRedisKeyServcice;

	@Autowired
	private CereActivitySignService cereActivitySignService;

	@Autowired
	private CerePlatformSeckillService cerePlatformSeckillService;

	@Autowired
	private CerePlatformDiscountService cerePlatformDiscountService;

	@Autowired
	private CerePlatformPoliteService cerePlatformPoliteService;

	@Autowired
	private CereShopProductService cereShopProductService;

	@Autowired
	private CereSignProductService cereSignProductService;

	//@Autowired
	//private WxPayService wxPayService;

	@Value("${spring.redis.database}")
	private String database;

	@Bean
	public RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory redisConnectionFactory){
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(redisConnectionFactory);
		// 若是监听所有DB，则注释 下面代码
		RedisListener redisListener = new RedisListener(listenerContainer,redisTemplate,
				cerePlatformUserService,cerePlatformActivityService,
				cereRedisKeyServcice,cereActivitySignService,
				cerePlatformSeckillService,cerePlatformDiscountService,
				cerePlatformPoliteService,cereShopProductService,
				cereSignProductService);
		//指定监听DB为0的库
		listenerContainer.addMessageListener(redisListener, new PatternTopic("__keyevent@"+database+"__:expired"));
		return listenerContainer;
	}
}
