/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.redis.config;

import com.shop.cereshop.app.redis.listener.RedisListener;
import com.shop.cereshop.app.service.activity.CereBuyerCouponService;
import com.shop.cereshop.app.service.after.CereOrderAfterService;
import com.shop.cereshop.app.service.buyer.CereBuyerShopCouponService;
import com.shop.cereshop.app.service.discount.CereShopDiscountDetailService;
import com.shop.cereshop.app.service.discount.CereShopDiscountService;
import com.shop.cereshop.app.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.app.service.distributor.CereDistributorBuyerService;
import com.shop.cereshop.app.service.groupwork.CereCollageOrderDetailService;
import com.shop.cereshop.app.service.groupwork.CereCollageOrderService;
import com.shop.cereshop.app.service.notice.CereNoticeService;
import com.shop.cereshop.app.service.order.CereOrderProductService;
import com.shop.cereshop.app.service.order.CereShopOrderService;
import com.shop.cereshop.app.service.product.CereProductSkuService;
import com.shop.cereshop.app.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.app.service.seckill.CereShopSeckillDetailService;
import com.shop.cereshop.app.service.seckill.CereShopSeckillService;
import com.shop.cereshop.app.service.stock.CereStockService;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.tool.CereShopDiscount;
import com.shop.cereshop.commons.domain.tool.CereShopSeckill;
import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisMessageListener {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private CereRedisKeyServcice cereRedisKeyServcice;

	@Autowired
	private CereShopOrderService cereShopOrderService;

	@Autowired
	private CereBuyerCouponService cereBuyerCouponService;

	@Autowired
	private CereOrderAfterService cereOrderAfterService;

	@Autowired
	private CereDistributorBuyerService cereDistributorBuyerService;

	@Autowired
	private CereCollageOrderService cereCollageOrderService;

	@Autowired
	private CereCollageOrderDetailService cereCollageOrderDetailService;

	@Autowired
	private CereNoticeService cereNoticeService;

	@Autowired
	private CereBuyerShopCouponService cereBuyerShopCouponService;

	@Autowired
	private CereShopSeckillService cereShopSeckillService;

	@Autowired
	private CereShopSeckillDetailService cereShopSeckillDetailService;

	@Autowired
	private CereShopDiscountService cereShopDiscountService;

	@Autowired
	private CereShopDiscountDetailService cereShopDiscountDetailService;

	@Autowired
	private CereProductSkuService cereProductSkuService;

	@Autowired
	private CereDistributionOrderService cereDistributionOrderService;

	@Autowired
	private CereStockService cereStockService;

	@Value("${spring.redis.database}")
	private String database;

	@Bean
	public RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory redisConnectionFactory){
		RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
		listenerContainer.setConnectionFactory(redisConnectionFactory);
		// 若是监听所有DB，则注释 下面代码
		RedisListener redisListener = new RedisListener(listenerContainer,redisTemplate,
				cereRedisKeyServcice,cereShopOrderService,cereBuyerCouponService,cereOrderAfterService,
				cereDistributorBuyerService,cereCollageOrderService,cereCollageOrderDetailService,
				cereNoticeService,cereBuyerShopCouponService,
				cereShopSeckillService,cereShopSeckillDetailService,
				cereShopDiscountService,cereShopDiscountDetailService,
				cereProductSkuService,cereDistributionOrderService,
				cereStockService);
		//指定监听DB为0的库
		listenerContainer.addMessageListener(redisListener, new PatternTopic("__keyevent@"+database+"__:expired"));
		return listenerContainer;
	}
}
