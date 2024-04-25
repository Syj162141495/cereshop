/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.price.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.price.CereShopPriceDAO;
import com.shop.cereshop.business.page.compose.ShopCompose;
import com.shop.cereshop.business.page.compose.ShopComposeDetail;
import com.shop.cereshop.business.page.price.ShopPrice;
import com.shop.cereshop.business.page.price.ShopPriceDetail;
import com.shop.cereshop.business.param.compose.ComposeGetAllParam;
import com.shop.cereshop.business.param.compose.ComposeGetByIdParam;
import com.shop.cereshop.business.param.compose.ComposeStartParam;
import com.shop.cereshop.business.param.price.*;
import com.shop.cereshop.business.param.renovation.RenovationParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.price.CerePriceProductService;
import com.shop.cereshop.business.service.price.CerePriceRuleService;
import com.shop.cereshop.business.service.price.CereShopPriceService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import com.shop.cereshop.commons.domain.tool.CerePriceRule;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import com.shop.cereshop.commons.domain.tool.CereShopPrice;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CereShopPriceServiceImpl implements CereShopPriceService {

    @Autowired
    private CereShopPriceDAO cereShopPriceDAO;

    @Autowired
    private CerePriceProductService cerePriceProductService;

    @Autowired
    private CerePriceRuleService cerePriceRuleService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(PriceSaveParam param, CerePlatformBusiness user) throws CoBusinessException, Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopPrice cereShopPrice=new CereShopPrice();
        cereShopPrice.setShopId(param.getShopId());
        cereShopPrice.setComposeName(param.getComposeName());
        cereShopPrice.setStartTime(param.getStartTime());
        cereShopPrice.setEndTime(param.getEndTime());
        cereShopPrice.setCreateTime(time);
        //默认状态已停用
        cereShopPrice.setState(IntegerEnum.SCEME_STATE_STOP.getCode());
        //校验时间是否有交叉
        List<CereShopCompose> list=cereShopPriceDAO.checkTime(param);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //查询商品最低价格总和
//        BigDecimal min=new BigDecimal(0);
//        if(!EmptyUtils.isEmpty(param.getPriceProducts())){
//            List<BigDecimal> decimals=cereShopPriceDAO.findProductPrice(param.getPriceProducts());
//            if(!EmptyUtils.isEmpty(decimals)&&!EmptyUtils.isEmpty(param.getPriceRules())){
//                //校验减价是否超出限制
//                CerePriceRule cerePriceRule = param.getPriceRules().get(0);
//                Integer number = cerePriceRule.getNumber();
//                for (int i = 0; i < number; i++) {
//                    min=min.add(decimals.get(i));
//                }
//                if(cerePriceRule.getPrice().compareTo(min)==1){
//                    throw new CoBusinessException(CoReturnFormat.PRICE_PRICE_ERROR);
//                }
//            }
//        }
        //比较规则第一条
        //如果当前时间小于开始时间,状态未开始
        if(TimeUtils.compareTo(param.getStartTime(),time)){
            cereShopPrice.setState(IntegerEnum.SHOP_COMPOSE_STATE_NOT.getCode());
            cereShopPriceDAO.insert(cereShopPrice);
            //新增延时任务
            stringRedisService.set(StringEnum.PRICE_START.getCode()+"-"+cereShopPrice.getPriceId(),1,TimeUtils.getCountDownByTime(time,param.getStartTime()));
            cereRedisKeyServcice.add(StringEnum.PRICE_START.getCode()+"-"+cereShopPrice.getPriceId(),param.getStartTime());
        }
        //如果当前时间在起始时间范围内,状态进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cereShopPrice.setState(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode());
            cereShopPriceDAO.insert(cereShopPrice);
            //新增延时任务
            stringRedisService.set(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId(),1,TimeUtils.getCountDownByTime(time,param.getEndTime()));
            cereRedisKeyServcice.add(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId(),param.getEndTime());
        }
        if(!EmptyUtils.isEmpty(param.getPriceProducts())){
            param.getPriceProducts().forEach(a -> a.setPriceId(cereShopPrice.getPriceId()));
            //批量插入商品数据
            cerePriceProductService.insertBatch(param.getPriceProducts());
        }
        if(!EmptyUtils.isEmpty(param.getPriceRules())){
            param.getPriceRules().forEach(a -> a.setPriceId(cereShopPrice.getPriceId()));
            //批量插入商品数据
            cerePriceRuleService.insertBatch(param.getPriceRules());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"定价捆绑管理","商户端操作","添加定价捆绑",cereShopPrice.getPriceId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(PriceUpdateParam param, CerePlatformBusiness user) throws CoBusinessException, Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopPrice cereShopPrice=new CereShopPrice();
        cereShopPrice.setPriceId(param.getPriceId());
        cereShopPrice.setShopId(param.getShopId());
        cereShopPrice.setComposeName(param.getComposeName());
        cereShopPrice.setStartTime(param.getStartTime());
        cereShopPrice.setEndTime(param.getEndTime());
        cereShopPrice.setUpdateTime(time);
        //校验时间是否有交叉
        List<CereShopCompose> list=cereShopPriceDAO.checkUpdateTime(param);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //查询商品最低价格总和
        BigDecimal min=new BigDecimal(0);
        if(!EmptyUtils.isEmpty(param.getPriceProducts())){
            List<BigDecimal> decimals=cereShopPriceDAO.findProductPrice(param.getPriceProducts());
            if(!EmptyUtils.isEmpty(decimals)&&!EmptyUtils.isEmpty(param.getPriceRules())){
                //校验减价是否超出限制
                CerePriceRule cerePriceRule = param.getPriceRules().get(0);
                Integer number = cerePriceRule.getNumber();
                for (int i = 0; i < number; i++) {
                    min=min.add(decimals.get(i));
                }
                if(min.compareTo(cerePriceRule.getPrice())==1){
                    throw new CoBusinessException(CoReturnFormat.PRICE_PRICE_ERROR);
                }
            }
        }
        //清空延时任务
        stringRedisService.delete(StringEnum.PRICE_START.getCode()+"-"+cereShopPrice.getPriceId());
        cereRedisKeyServcice.deleteByKey(StringEnum.PRICE_START.getCode()+"-"+cereShopPrice.getPriceId());
        stringRedisService.delete(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId());
        cereRedisKeyServcice.deleteByKey(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId());
        //如果当前时间小于开始时间,状态未开始
        if(TimeUtils.compareTo(param.getStartTime(),time)){
            cereShopPrice.setState(IntegerEnum.SHOP_COMPOSE_STATE_NOT.getCode());
            cereShopPriceDAO.updateByPrimaryKeySelective(cereShopPrice);
            //新增延时任务
            stringRedisService.set(StringEnum.PRICE_START.getCode()+"-"+cereShopPrice.getPriceId(),1,TimeUtils.getCountDownByTime(time,param.getStartTime()));
            cereRedisKeyServcice.add(StringEnum.PRICE_START.getCode()+"-"+cereShopPrice.getPriceId(),param.getStartTime());
        }
        //如果当前时间在起始时间范围内,状态进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cereShopPrice.setState(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode());
            cereShopPriceDAO.updateByPrimaryKeySelective(cereShopPrice);
            //新增延时任务
            stringRedisService.set(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId(),1,TimeUtils.getCountDownByTime(time,param.getEndTime()));
            cereRedisKeyServcice.add(StringEnum.PRICE_END.getCode()+"-"+cereShopPrice.getPriceId(),param.getEndTime());
        }
        //清空商品数据和规则数据
        cerePriceProductService.deleteByPriceId(cereShopPrice.getPriceId());
        cerePriceRuleService.deleteByPriceId(cereShopPrice.getPriceId());
        if(!EmptyUtils.isEmpty(param.getPriceProducts())){
            param.getPriceProducts().forEach(a -> a.setPriceId(cereShopPrice.getPriceId()));
            //批量插入商品数据
            cerePriceProductService.insertBatch(param.getPriceProducts());
        }
        if(!EmptyUtils.isEmpty(param.getPriceRules())){
            param.getPriceRules().forEach(a -> a.setPriceId(cereShopPrice.getPriceId()));
            //批量插入商品数据
            cerePriceRuleService.insertBatch(param.getPriceRules());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"定价捆绑管理","商户端操作","编辑定价捆绑",cereShopPrice.getPriceId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(PriceGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        //清空延时任务
        stringRedisService.delete(StringEnum.PRICE_START.getCode()+"-"+param.getPriceId());
        cereRedisKeyServcice.deleteByKey(StringEnum.PRICE_START.getCode()+"-"+param.getPriceId());
        stringRedisService.delete(StringEnum.PRICE_END.getCode()+"-"+param.getPriceId());
        cereRedisKeyServcice.deleteByKey(StringEnum.PRICE_END.getCode()+"-"+param.getPriceId());
        String time = TimeUtils.yyMMddHHmmss();
        cereShopPriceDAO.deleteByPrimaryKey(param.getPriceId());
        //清空商品数据
        cerePriceProductService.deleteByPriceId(param.getPriceId());
        //清空规则数据
        cerePriceRuleService.deleteByPriceId(param.getPriceId());
        //新增日志
        cerePlatformLogService.addLog(user,"定价捆绑管理","商户端操作","删除定价捆绑",param.getPriceId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void start(PriceStartParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopPrice cereShopPrice=new CereShopPrice();
        cereShopPrice.setPriceId(param.getPriceId());
        cereShopPrice.setState(param.getState());
        cereShopPrice.setUpdateTime(time);
        String describe="";
        if(IntegerEnum.SHOP_COMPOSE_STATE_START.getCode().equals(param.getState())){
            //启用
            describe="启用组合捆绑";
        }else {
            //停用
            describe="终止组合捆绑";
        }
        cereShopPriceDAO.updateByPrimaryKeySelective(cereShopPrice);
        //新增日志
        cerePlatformLogService.addLog(user,"定价捆绑管理","商户端操作",describe,param.getPriceId(),time);
    }

    @Override
    public ShopPriceDetail getById(Long priceId) throws CoBusinessException {
        ShopPriceDetail detail=cereShopPriceDAO.getById(priceId);
        if(detail!=null){
            //查询商品明细数据
            detail.setComposeProducts(cerePriceProductService.findProducts(priceId));
            //查询规则数据
            detail.setRules(cerePriceRuleService.findRules(priceId));
        }
        return detail;
    }

    @Override
    public Page getAll(PriceGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopPrice> list=cereShopPriceDAO.getAll(param);
        PageInfo<ShopPrice> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CereShopPrice findById(Long priceId) {
        return cereShopPriceDAO.selectByPrimaryKey(priceId);
    }

    @Override
    public void updateState(CereShopPrice cereShopPrice) throws CoBusinessException {
        cereShopPriceDAO.updateByPrimaryKeySelective(cereShopPrice);
    }

    @Override
    public List<ShopPriceDetail> getPrices(RenovationParam param) throws CoBusinessException {
        List<ShopPriceDetail> list=cereShopPriceDAO.getPrices(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(detail -> {
                //查询定价商品数据
                detail.setComposeProducts(cerePriceProductService.findProducts(detail.getPriceId()));
                //查询定价规则数据
                detail.setRules(cerePriceRuleService.findRules(detail.getPriceId()));
            });
        }
        return list;
    }
}
