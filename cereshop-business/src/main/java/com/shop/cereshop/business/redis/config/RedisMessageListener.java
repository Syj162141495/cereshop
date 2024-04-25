/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.redis.config;

import com.shop.cereshop.business.redis.listener.RedisListener;
import com.shop.cereshop.business.service.activity.CereActivitySignService;
import com.shop.cereshop.business.service.business.CerePlatformBusinessService;
import com.shop.cereshop.business.service.compose.CereShopComposeService;
import com.shop.cereshop.business.service.notice.CereNoticeService;
import com.shop.cereshop.business.service.price.CereShopPriceService;
import com.shop.cereshop.business.service.product.CereShopProductService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.business.service.tool.*;
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
	private CerePlatformBusinessService cerePlatformBusinessService;

	@Autowired
	private CereRedisKeyServcice cereRedisKeyServcice;

	@Autowired
	private CereNoticeService cereNoticeService;

	@Autowired
	private CereShopGroupWorkService cereShopGroupWorkService;

	@Autowired
	private CereShopGroupWorkDetailService cereShopGroupWorkDetailService;

	@Autowired
	private CereShopProductService cereShopProductService;

	@Autowired
	private CereShopSeckillService cereShopSeckillService;

	@Autowired
	private CereShopSeckillDetailService cereShopSeckillDetailService;

	@Autowired
	private CereShopDiscountService cereShopDiscountService;

	@Autowired
	private CereShopDiscountDetailService cereShopDiscountDetailService;

	@Autowired
	private CereShopCouponService cereShopCouponService;

	@Autowired
	private CereShopOperateService cereShopOperateService;

	@Autowired
	private CereActivitySignService cereActivitySignService;

	@Autowired
	private CereShopSceneService cereShopSceneService;

	@Autowired
	private CereShopComposeService cereShopComposeService;

	@Autowired
	private CereShopPriceService cereShopPriceService;

	@Value("${spring.redis.database}")
	private String database;

	@Bean
	public RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory redisConnectionFactory){
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(redisConnectionFactory);
		// 若是监听所有DB，则注释 下面代码
		RedisListener redisListener = new RedisListener(listenerContainer,redisTemplate,cerePlatformBusinessService
				,cereRedisKeyServcice,cereNoticeService,cereShopGroupWorkService
				,cereShopGroupWorkDetailService,cereShopProductService
				,cereShopSeckillService,cereShopSeckillDetailService,
				cereShopDiscountService,cereShopDiscountDetailService,
				cereShopCouponService,cereShopOperateService,cereActivitySignService,cereShopSceneService,
				cereShopComposeService,cereShopPriceService);
		//指定监听DB为0的库
		listenerContainer.addMessageListener(redisListener, new PatternTopic("__keyevent@"+database+"__:expired"));
		return listenerContainer;
	}
}
