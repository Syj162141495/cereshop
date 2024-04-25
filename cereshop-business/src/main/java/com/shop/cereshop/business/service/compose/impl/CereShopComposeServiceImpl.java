/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.compose.impl;

import cn.hutool.extra.emoji.EmojiUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.compose.CereShopComposeDAO;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.business.page.compose.ShopCompose;
import com.shop.cereshop.business.page.compose.ShopComposeDetail;
import com.shop.cereshop.business.page.product.ShopProduct;
import com.shop.cereshop.business.param.compose.*;
import com.shop.cereshop.business.param.product.ProductGetAllParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.compose.CereComposeProductService;
import com.shop.cereshop.business.service.compose.CereShopComposeService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.product.CereProductMemberService;
import com.shop.cereshop.business.service.product.CereProductSkuService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.CereShopDiscountService;
import com.shop.cereshop.business.service.tool.CereShopSeckillService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.product.CereProductMember;
import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CereShopComposeServiceImpl implements CereShopComposeService {

    @Autowired
    private CereShopComposeDAO cereShopComposeDAO;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereComposeProductService cereComposeProductService;

    @Autowired
    private CereShopSeckillService cereShopSeckillService;

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Autowired
    private CereProductMemberService cereProductMemberService;

    @Autowired
    private CereProductSkuService cereProductSkuService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ComposeSaveParam param, CerePlatformBusiness user) throws CoBusinessException, Exception {
        //校验组合名称
        CereShopCompose compose=cereShopComposeDAO.checkName(param.getComposeName(),null);
        if(compose!=null){
            throw new CoBusinessException(CoReturnFormat.COMPOSE_NAME_ALREADY);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopCompose cereShopCompose=new CereShopCompose();
        cereShopCompose.setShopId(param.getShopId());
        cereShopCompose.setComposeName(param.getComposeName());
        cereShopCompose.setComposeType(param.getComposeType());
        cereShopCompose.setPromote(param.getPromote());
        cereShopCompose.setStartTime(param.getStartTime());
        cereShopCompose.setEndTime(param.getEndTime());
        cereShopCompose.setCreateTime(time);
        //校验捆绑商品数量
        if(!EmptyUtils.isEmpty(param.getComposeProducts())){
            if(param.getComposeProducts().size()<2||param.getComposeProducts().size()>5){
                throw new CoBusinessException(CoReturnFormat.COMPOSE_PRODUCT_NUMBER);
            }
        }
        List<Long> productIs = param.getComposeProducts().stream().map(CereComposeProduct::getProductId).collect(Collectors.toList());
        //查询当前时间范围内这些商品中是否存在商家限时折扣活动中
        List<Long> ids=cereShopSeckillService.checkOtherDiscount(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在商家拼团活动中
        ids=cereShopSeckillService.checkOtherWork(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在商家秒杀活动中
        ids=cereShopDiscountService.checkOtherSeckill(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
        ids=cerePlatformDiscountService.checkPlatformDiscount(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在平台秒杀活动中
        ids = cerePlatformSeckillService.checkPlatformSeckill(productIs, cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(), param.getShopId());
        if (!EmptyUtils.isEmpty(ids)) {
            throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在组合捆绑活动中
        /*ids = cereShopComposeDAO.checkShopCompose(productIs, cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(), param.getShopId(),null);
        if (!EmptyUtils.isEmpty(ids)) {
            throw new CoBusinessException(CoReturnFormat.SHOP_COMPOSE_PRODUCT_REPEAT);
        }*/

        if(IntegerEnum.SCENE_TIME_FIXED.getCode().equals(param.getComposeType())){
            //固定减价,计算商品最低价格
            if(!EmptyUtils.isEmpty(param.getComposeProducts())){
                List<BigDecimal> decimals=cereShopComposeDAO.findProductPrice(param.getComposeProducts());
                if(!EmptyUtils.isEmpty(decimals)){
                    BigDecimal decimal=BigDecimal.ZERO;
                    for (BigDecimal min : decimals) {
                        decimal=decimal.add(min);
                    }
                    if(param.getPromote().compareTo(decimal) > 0){
                        throw new CoBusinessException(CoReturnFormat.PRICE_PRICE_ERROR);
                    }
                }
            }
        }

        //默认状态已停用
        cereShopCompose.setState(IntegerEnum.SCEME_STATE_STOP.getCode());
        //校验时间是否有交叉
//        List<CereShopCompose> list=cereShopComposeDAO.checkTime(param);
//        if(!EmptyUtils.isEmpty(list)){
//            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
//        }
        //如果当前时间小于开始时间,状态未开始
        if(TimeUtils.compareTo(param.getStartTime(),time)){
            cereShopCompose.setState(IntegerEnum.SHOP_COMPOSE_STATE_NOT.getCode());
            cereShopComposeDAO.insert(cereShopCompose);
            //新增延时任务
            stringRedisService.set(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId(),1,TimeUtils.getCountDownByTime(time,param.getStartTime()));
            cereRedisKeyServcice.add(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId(),param.getStartTime());
        }
        //如果当前时间在起始时间范围内,状态进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cereShopCompose.setState(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode());
            cereShopComposeDAO.insert(cereShopCompose);
            //新增延时任务
            stringRedisService.set(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId(),1,TimeUtils.getCountDownByTime(time,param.getEndTime()));
            cereRedisKeyServcice.add(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId(),param.getEndTime());
        }
        if(!EmptyUtils.isEmpty(param.getComposeProducts())){
            param.getComposeProducts().forEach(a -> a.setComposeId(cereShopCompose.getComposeId()));
            //批量插入商品数据
            cereComposeProductService.insertBatch(param.getComposeProducts());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"组合捆绑管理","商户端操作","添加组合捆绑",cereShopCompose.getComposeId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ComposeUpdateParam param, CerePlatformBusiness user) throws CoBusinessException, Exception {
        //校验组合名称
        CereShopCompose compose=cereShopComposeDAO.checkName(param.getComposeName(),param.getComposeId());
        if(compose!=null){
            throw new CoBusinessException(CoReturnFormat.COMPOSE_NAME_ALREADY);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopCompose cereShopCompose=new CereShopCompose();
        cereShopCompose.setComposeId(param.getComposeId());
        cereShopCompose.setShopId(param.getShopId());
        cereShopCompose.setComposeName(param.getComposeName());
        cereShopCompose.setComposeType(param.getComposeType());
        cereShopCompose.setPromote(param.getPromote());
        cereShopCompose.setStartTime(param.getStartTime());
        cereShopCompose.setEndTime(param.getEndTime());
        cereShopCompose.setUpdateTime(time);
        //校验捆绑商品数量
        if(!EmptyUtils.isEmpty(param.getComposeProducts())){
            if(param.getComposeProducts().size()<2||param.getComposeProducts().size()>5){
                throw new CoBusinessException(CoReturnFormat.COMPOSE_PRODUCT_NUMBER);
            }
        }
        List<Long> productIs = param.getComposeProducts().stream().map(CereComposeProduct::getProductId).collect(Collectors.toList());
        //查询当前时间范围内这些商品中是否存在商家限时折扣活动中
        List<Long> ids=cereShopSeckillService.checkOtherDiscount(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在商家拼团活动中
        ids=cereShopSeckillService.checkOtherWork(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在商家秒杀活动中
        ids=cereShopDiscountService.checkOtherSeckill(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
        ids=cerePlatformDiscountService.checkPlatformDiscount(productIs,cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(),param.getShopId());
        if(!EmptyUtils.isEmpty(ids)){
            throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
        }
        //查询当前时间范围内这些商品中是否存在平台秒杀活动中
        ids = cerePlatformSeckillService.checkPlatformSeckill(productIs, cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(), param.getShopId());
        if (!EmptyUtils.isEmpty(ids)) {
            throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
        }

        //查询当前时间范围内这些商品中是否存在组合捆绑活动中
        /*ids = cereShopComposeDAO.checkShopCompose(productIs, cereShopCompose.getStartTime(),
                cereShopCompose.getEndTime(), param.getShopId(),param.getComposeId());
        if (!EmptyUtils.isEmpty(ids)) {
            throw new CoBusinessException(CoReturnFormat.SHOP_COMPOSE_PRODUCT_REPEAT);
        }*/
        //校验时间是否有交叉
//        List<CereShopCompose> list=cereShopComposeDAO.checkUpdateTime(param);
//        if(!EmptyUtils.isEmpty(list)){
//            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
//        }
        //清空延时任务
        stringRedisService.delete(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId());
        cereRedisKeyServcice.deleteByKey(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId());
        stringRedisService.delete(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId());
        cereRedisKeyServcice.deleteByKey(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId());
        //如果当前时间小于开始时间,状态未开始
        if(TimeUtils.compareTo(param.getStartTime(),time)){
            cereShopCompose.setState(IntegerEnum.SHOP_COMPOSE_STATE_NOT.getCode());
            cereShopComposeDAO.updateByPrimaryKeySelective(cereShopCompose);
            //新增延时任务
            stringRedisService.set(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId(),1,TimeUtils.getCountDownByTime(time,param.getStartTime()));
            cereRedisKeyServcice.add(StringEnum.COMPOSE_START.getCode()+"-"+cereShopCompose.getComposeId(),param.getStartTime());
        }
        //如果当前时间在起始时间范围内,状态进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cereShopCompose.setState(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode());
            cereShopComposeDAO.updateByPrimaryKeySelective(cereShopCompose);
            //新增延时任务
            stringRedisService.set(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId(),1,TimeUtils.getCountDownByTime(time,param.getEndTime()));
            cereRedisKeyServcice.add(StringEnum.COMPOSE_END.getCode()+"-"+cereShopCompose.getComposeId(),param.getEndTime());
        }
        //清空商品数据
        cereComposeProductService.deleteByComposeId(cereShopCompose.getComposeId());
        if(!EmptyUtils.isEmpty(param.getComposeProducts())){
            param.getComposeProducts().forEach(a -> a.setComposeId(cereShopCompose.getComposeId()));
            //批量插入商品数据
            cereComposeProductService.insertBatch(param.getComposeProducts());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"组合捆绑管理","商户端操作","添加组合捆绑",cereShopCompose.getComposeId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ComposeGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        //清空延时任务
        stringRedisService.delete(StringEnum.COMPOSE_START.getCode()+"-"+param.getComposeId());
        cereRedisKeyServcice.deleteByKey(StringEnum.COMPOSE_START.getCode()+"-"+param.getComposeId());
        stringRedisService.delete(StringEnum.COMPOSE_END.getCode()+"-"+param.getComposeId());
        cereRedisKeyServcice.deleteByKey(StringEnum.COMPOSE_END.getCode()+"-"+param.getComposeId());
        String time = TimeUtils.yyMMddHHmmss();
        cereShopComposeDAO.deleteByPrimaryKey(param.getComposeId());
        //清空商品数据
        cereComposeProductService.deleteByComposeId(param.getComposeId());
        //新增日志
        cerePlatformLogService.addLog(user,"组合捆绑管理","商户端操作","删除组合捆绑",param.getComposeId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void start(ComposeStartParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopCompose cereShopCompose=new CereShopCompose();
        cereShopCompose.setComposeId(param.getComposeId());
        cereShopCompose.setState(param.getState());
        cereShopCompose.setUpdateTime(time);
        String describe="";
        if(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode().equals(param.getState())){
            //启用
            describe="启用组合捆绑";
        }else {
            //停用
            describe="终止组合捆绑";
        }
        cereShopComposeDAO.updateByPrimaryKeySelective(cereShopCompose);
        //新增日志
        cerePlatformLogService.addLog(user,"组合捆绑管理","商户端操作",describe,param.getComposeId(),time);
    }

    @Override
    public ShopComposeDetail getById(Long composeId) throws CoBusinessException {
        ShopComposeDetail detail=cereShopComposeDAO.getById(composeId);
        if(detail!=null){
            //查询商品明细数据
            detail.setComposeProducts(cereComposeProductService.findProducts(composeId));
        }
        return detail;
    }

    @Override
    public Page getAll(ComposeGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopCompose> list=cereShopComposeDAO.getAll(param);
        PageInfo<ShopCompose> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CereShopCompose findById(Long composeId) {
        return cereShopComposeDAO.selectByPrimaryKey(composeId);
    }

    @Override
    public void updateState(CereShopCompose cereShopCompose) throws CoBusinessException {
        cereShopComposeDAO.updateByPrimaryKeySelective(cereShopCompose);
    }

    @Override
    public Page selectProduct(ProductGetAllParam param) throws CoBusinessException {
        //定义最低会员价map
        Map<Long, BigDecimal> minMap=new HashMap<>();
        //定义最高会员价map
        Map<Long,BigDecimal> maxMap=new HashMap<>();
        //查询所有商品会员价格最低值数据
        List<CereProductMember> minMembers=cereProductMemberService.findAllMin();
        if(!EmptyUtils.isEmpty(minMembers)){
            minMap=minMembers.stream().collect(Collectors.toMap(CereProductMember::getProductId,CereProductMember::getPrice));
        }
        //查询所有商品会员价格最高值数据
        List<CereProductMember> maxMembers=cereProductMemberService.findAllMax();
        if(!EmptyUtils.isEmpty(maxMembers)){
            maxMap=maxMembers.stream().collect(Collectors.toMap(CereProductMember::getProductId,CereProductMember::getPrice));
        }
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopProduct> list=cereShopComposeDAO.selectProduct(param);
        if(!EmptyUtils.isEmpty(list)){
            Map<Long, BigDecimal> finalMinMap = minMap;
            Map<Long, BigDecimal> finalMaxMap = maxMap;
            list.forEach((shopProduct -> {
                if(!EmptyUtils.isEmpty(shopProduct.getSkuImage())){
                    //规格配图了,取规格图片展示
                    shopProduct.setProductImage(shopProduct.getSkuImage());
                }
                //查询规格商品销量
                Integer total=cereProductSkuService.findVolumeByProductId(shopProduct.getProductId());
                shopProduct.setVolume(total);
                if(!EmptyUtils.isEmpty(finalMinMap)&&!EmptyUtils.isEmpty(finalMinMap.get(shopProduct.getProductId()))){
                    //设置会员价格区间
                    shopProduct.setMemberSection("￥"+finalMinMap.get(shopProduct.getProductId())+"~￥"+
                            finalMaxMap.get(shopProduct.getProductId()));
                }
            }));
        }
        PageInfo<ShopProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

}
