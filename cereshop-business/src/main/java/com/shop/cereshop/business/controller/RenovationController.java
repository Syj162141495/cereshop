/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.business.page.canvas.CanvasPlatformSeckill;
import com.shop.cereshop.business.page.tool.ShopDiscountDetail;
import com.shop.cereshop.business.page.tool.ShopGroupWorkUDetail;
import com.shop.cereshop.business.page.tool.ShopSeckillDetail;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.tool.CereShopDiscountService;
import com.shop.cereshop.business.service.tool.CereShopGroupWorkService;
import com.shop.cereshop.business.service.tool.CereShopSeckillService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 店铺装修
 */
@RestController
@RequestMapping("renovation")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "RenovationController")
@Api(value = "店铺装修", tags = "店铺装修")
public class RenovationController {

    @Autowired
    private CereShopGroupWorkService cereShopGroupWorkService;

    @Autowired
    private CereShopSeckillService cereShopSeckillService;

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    /**
     * 选择拼团活动查询
     * @return
     */
    @GetMapping("getGroupWorks")
    @ApiOperation(value = "选择拼团活动查询")
    public Result<List<ShopGroupWorkUDetail>> getGroupWorks(RenovationParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<ShopGroupWorkUDetail> list=cereShopGroupWorkService.getGroupWorks(param);
        return new Result(list);
    }

    /**
     * 选择秒杀活动查询
     * @return
     */
    @GetMapping("getSeckills")
    @ApiOperation(value = "选择秒杀活动查询")
    public Result<List<ShopSeckillDetail>> getSeckills(RenovationParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<ShopSeckillDetail> list=cereShopSeckillService.getSeckills(param);
        return new Result(list);
    }

    /**
     * 选择限时折扣活动查询
     * @return
     */
    @GetMapping("getDiscounts")
    @ApiOperation(value = "选择限时折扣活动查询")
    public Result<List<ShopDiscountDetail>> getDiscounts(RenovationParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<ShopDiscountDetail> list=cereShopDiscountService.getDiscounts(param);
        return new Result(list);
    }

    /**
     * 选择平台端限时折扣活动查询
     * @return
     */
    @GetMapping("getMinDiscount")
    @ApiOperation(value = "选择平台端限时折扣活动查询")
    public Result<CanvasPlatformDiscount> getMinDiscount() throws CoBusinessException{
        CanvasPlatformDiscount discount=cerePlatformDiscountService.getMinDiscount();
        return new Result(discount);
    }

    /**
     * 选择平台端秒杀活动查询
     * @return
     */
    @GetMapping("getPlatformSeckills")
    @ApiOperation(value = "选择平台端限时折扣活动查询")
    public Result<List<CanvasPlatformSeckill>> getPlatformSeckills(RenovationParam param) throws CoBusinessException{
        List<CanvasPlatformSeckill> list=cerePlatformSeckillService.getPlatformSeckills(param);
        return new Result(list);
    }
}

