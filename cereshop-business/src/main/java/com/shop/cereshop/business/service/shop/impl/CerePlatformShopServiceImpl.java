/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CerePlatformShopDAO;
import com.shop.cereshop.business.page.index.HotSellProduct;
import com.shop.cereshop.business.page.index.Index;
import com.shop.cereshop.business.page.index.VisitWeek;
import com.shop.cereshop.business.page.shop.Shop;
import com.shop.cereshop.business.page.shop.ShopConversion;
import com.shop.cereshop.business.param.index.IndexParam;
import com.shop.cereshop.business.param.shop.ShopUpdateLogoParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.shop.*;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.ParamEnum;
import com.shop.cereshop.commons.domain.business.CereBusinessShop;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.domain.shop.CereShopReturn;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CerePlatformShopServiceImpl implements CerePlatformShopService {

    @Autowired
    private CerePlatformShopDAO cerePlatformShopDAO;

    @Autowired
    private CereShopReturnService cereShopReturnService;

    @Autowired
    private CereShopPersonalService cereShopPersonalService;

    @Autowired
    private CereShopIndividualBusinessesService cereShopIndividualBusinessesService;

    @Autowired
    private CereShopEnterpriseService cereShopEnterpriseService;

    @Autowired
    private CereShopOtherOrganizationsService cereShopOtherOrganizationsService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public CerePlatformShop selectByPrimaryKey(Long shopId) {
        return cerePlatformShopDAO.selectByPrimaryKey(shopId);
    }

    @Override
    public CerePlatformShop findByShopName(String shopName) {
        return cerePlatformShopDAO.findByShopName(shopName);
    }

    @Override
    public Shop getById(Long shopId) throws CoBusinessException {
        //查询店铺信息
        Shop shop=cerePlatformShopDAO.findShop(shopId);
        if(shop!=null){
            //查询店铺退货地址信息
            CereShopReturn cereShopReturn=cereShopReturnService.findByShopId(shopId);
            shop.setShopReturn(cereShopReturn);
            //查询认证数据
            if(IntegerEnum.getByName("主体类型-个人").equals(shop.getAuthenType())){
                //个人认证
                shop.setPersonal(cereShopPersonalService.findByShopId(shopId));
            }else if(IntegerEnum.getByName("主体类型-个体工商户").equals(shop.getAuthenType())){
                //个体工商户户认证
                shop.setIndividualBusinesses(cereShopIndividualBusinessesService.findByShopId(shopId));
            }else if(IntegerEnum.getByName("主体类型-企业").equals(shop.getAuthenType())){
                //企业认证
                shop.setEnterprise(cereShopEnterpriseService.findByShopId(shopId));
            }else if(IntegerEnum.getByName("主体类型-其他组织").equals(shop.getAuthenType())){
                //其他组织认证
                shop.setOtherOrganizations(cereShopOtherOrganizationsService.findByShopId(shopId));
            }
        }
        return shop;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopUpdateLogoParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop=new CerePlatformShop();
        cerePlatformShop.setShopId(param.getShopId());
        cerePlatformShop.setShopLogo(param.getShopLogo());
        cerePlatformShop.setShopName(param.getShopName());
        cerePlatformShop.setShopBrief(param.getShopBrief());
        cerePlatformShop.setShopAdress(param.getShopAdress());
        cerePlatformShop.setShopPhone(param.getShopPhone());
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShopDAO.updateByPrimaryKeySelective(cerePlatformShop);
        if(!EmptyUtils.isEmpty(param.getShopReturn())){
            //查询是否有退货地址信息
            CereShopReturn shopReturn=cereShopReturnService.findByShopId(param.getShopId());
            if(shopReturn!=null){
                //更新退货地址
                shopReturn.setReturnAdress(param.getShopReturn().getReturnAdress());
                shopReturn.setReturnPerson(param.getShopReturn().getReturnPerson());
                shopReturn.setReturnPhone(param.getShopReturn().getReturnPhone());
                cereShopReturnService.update(shopReturn);
            }else {
                param.getShopReturn().setShopId(param.getShopId());
                cereShopReturnService.insert(param.getShopReturn());
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"店铺管理","商户端操作","修改店铺logo",cerePlatformShop.getShopId(),time);
    }

    @Override
    public void updateState(CerePlatformShop cerePlatformShop) throws CoBusinessException {
        cerePlatformShopDAO.updateState(cerePlatformShop);
    }

    @Override
    public Index index(IndexParam param) throws CoBusinessException {
        Index index=new Index();
        //查询今日店铺访问人数
        index.setTotal(cerePlatformShopDAO.getTotal(param.getShopId()));
        //查询今日待处理订单数
        index.setStayOrders(cerePlatformShopDAO.getStayOrders(param.getShopId()));
        //查询今日待处理售后订单数
        index.setStayAfters(cerePlatformShopDAO.getStayAfters(param.getShopId()));
        //查询今日成交金额
        index.setMoney(cerePlatformShopDAO.getMoney(param.getShopId()));
        //根据时间条件获取对应统计时间数据
        List<String> weeks = getVisitWeeks(param.getCondition());
        //查询月度访问人数数据
        VisitWeek visitWeek=new VisitWeek();
        List<Integer> total = weeks.stream()
                .map(time -> {
                    //查询周时间对应的访问人数
                    return cerePlatformShopDAO.getVisit(param.getShopId(),time);
                })
                .collect(Collectors.toList());
        if(!EmptyUtils.isEmpty(total)){
            //设置总访问人数
            index.setCount(total.stream().mapToInt(Integer::intValue).sum());
        }
        visitWeek.setTotal(total);
        visitWeek.setTime(weeks);
        index.setVisitWeek(visitWeek);
        //查询订单转化数据
        ShopConversion shopConversion=new ShopConversion();
        //初始化文字描述
        List<String> names=new ArrayList<>();
        List<BigDecimal> rates=new ArrayList<>();
        //查询访问人数
        BigDecimal visits = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_VISIT.getCode());
        //查询加购人数
        BigDecimal carts = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_CART.getCode());
        //查询结算人数
        BigDecimal checks = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_CHECK.getCode());
        //查询调取支付人数
        BigDecimal pays = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_PAY.getCode());
        //查询支付成功人数
        BigDecimal success = cerePlatformShopDAO.findVisits(param.getShopId(), param.getCondition(), ParamEnum.CONVERSION_PAY_SUCCESS.getCode());
        //计算转化率
        BigDecimal hundred=new BigDecimal(100);
        if(!EmptyUtils.isEmptyBigdecimal(visits)){
            names.add("访问");
            rates.add(hundred);
            if(!EmptyUtils.isEmptyBigdecimal(carts)){
                names.add("加购");
                rates.add(carts.divide(visits,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(0,BigDecimal.ROUND_HALF_UP));
            }
            if(!EmptyUtils.isEmptyBigdecimal(checks)){
                names.add("结算");
                rates.add(checks.divide(visits,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(0,BigDecimal.ROUND_HALF_UP));
            }
            if(!EmptyUtils.isEmptyBigdecimal(pays)){
                names.add("调取支付");
                rates.add(pays.divide(visits,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(0,BigDecimal.ROUND_HALF_UP));
            }
            if(!EmptyUtils.isEmptyBigdecimal(success)){
                names.add("支付成功");
                rates.add(success.divide(visits,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(0,BigDecimal.ROUND_HALF_UP));
            }
            shopConversion.setRates(rates);
        }
        shopConversion.setNames(names);
        index.setConversion(shopConversion);
        //计算总的转化数
        BigDecimal rate=carts.add(checks).add(pays).add(success);
        //计算总转化率=（加购人数+结账人数+调用支付人数+支付成功人数）/访问人数
        if(!EmptyUtils.isEmptyBigdecimal(rate)&&!EmptyUtils.isEmptyBigdecimal(visits)){
            index.setRate(rate.divide(visits,2,BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(0,BigDecimal.ROUND_HALF_UP));
        }
        //根据时间条件查询热卖商品数据(10条)
        List<HotSellProduct> products=cerePlatformShopDAO.getHotProducts(param.getShopId(),param.getCondition());
        index.setHotSellProducts(products);
        return index;
    }

    @Override
    public Index indexTest(IndexParam param) throws CoBusinessException {
        Index index=new Index();
        //查询今日店铺访问人数
        index.setTotal(12);
        //查询今日待处理订单数
        index.setStayOrders(123);
        //查询今日待处理售后订单数
        index.setStayAfters(321);
        //查询今日成交金额
        index.setMoney(new BigDecimal(54665));
        //根据时间条件获取对应统计时间数据
        List<String> weeks = getVisitWeeks(param.getCondition());
        List<Integer> total=new ArrayList<>();
        //查询月度访问人数数据
        if(!EmptyUtils.isEmpty(weeks)){
            weeks.forEach(a -> {
                total.add(20);
            });
        }
        VisitWeek visitWeek=new VisitWeek();
        visitWeek.setTime(weeks);
        visitWeek.setTotal(total);
        index.setVisitWeek(visitWeek);
        //查询订单转化数据
        ShopConversion shopConversion=new ShopConversion();
        List<String> names=new ArrayList<>();
        names.add("访问");
        names.add("加购");
        names.add("结算");
        names.add("调取支付");
        names.add("支付成功");
        List<BigDecimal> rates=new ArrayList<>();
        rates.add(new BigDecimal(100));
        rates.add(new BigDecimal(5));
        rates.add(new BigDecimal(12));
        rates.add(new BigDecimal(12));
        rates.add(new BigDecimal(32));
        shopConversion.setNames(names);
        shopConversion.setRates(rates);
        //计算转化率
        index.setConversion(shopConversion);
        //根据时间条件查询热卖商品数据(10条)
        List<HotSellProduct> products=new ArrayList<>();
        products.add(new HotSellProduct("牛油果",23));
        products.add(new HotSellProduct("牛肉粒",23));
        products.add(new HotSellProduct("坚果",23));
        products.add(new HotSellProduct("iphone12",23));
        products.add(new HotSellProduct("小苏打饼干",23));
        products.add(new HotSellProduct("薯片",23));
        products.add(new HotSellProduct("辣条",23));
        products.add(new HotSellProduct("肥宅水",23));
        products.add(new HotSellProduct("电饭煲",23));
        products.add(new HotSellProduct("笔记本电脑",23));
        index.setHotSellProducts(products);
        index.setCount(156);
        index.setRate(new BigDecimal(23));
        return index;
    }

    @Override
    public Integer findPayUsers(Long productId) {
        return cerePlatformShopDAO.findPayUsers(productId);
    }

    @Override
    public List<CereBusinessShop> findIds() {
        return cerePlatformShopDAO.findIds();
    }

    @Override
    public Long findShopRoleId(Long id) {
        return cerePlatformShopDAO.findShopRoleId(id);
    }

    @Override
    public List<CerePlatformShop> selectAll() {
        return cerePlatformShopDAO.selectAll();
    }

    @Override
    public CerePlatformShop check(String shopPhone) throws CoBusinessException{
        return cerePlatformShopDAO.check(shopPhone);
    }

    @Override
    public void insert(CerePlatformShop cerePlatformShop) throws CoBusinessException {
        cerePlatformShopDAO.insert(cerePlatformShop);
    }

    @Override
    public CerePlatformShop findByPhone(String shopPhone) {
        return cerePlatformShopDAO.findByPhone(shopPhone);
    }

    @Override
    public void update(CerePlatformShop cerePlatformShop) throws CoBusinessException {
        cerePlatformShopDAO.updateByPrimaryKeySelective(cerePlatformShop);
    }

    @Override
    public CerePlatformShop checkShopIdByName(String shopName, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByName(shopName,shopId);
    }

    @Override
    public CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByPhone(shopPhone,shopId);
    }

    private List<String> getVisitWeeks(Integer condition) {
        List<String> weeks=new ArrayList<>();
        if(IntegerEnum.CONDITION_TODAY.getCode().equals(condition)){
            //封装今日时间
            weeks.add(TimeUtils.today());
        }else if(IntegerEnum.CONDITION_YESTERDAY.getCode().equals(condition)){
            //封装昨日时间
            weeks.add(TimeUtils.yesterday());
        }else if(IntegerEnum.CONDITION_WEEK.getCode().equals(condition)){
            //封装近7天时间
            weeks=TimeUtils.getBeforeSevenDay();
        }else if(IntegerEnum.CONDITION_THIRD.getCode().equals(condition)){
            //封装当月每周时间数据
            //获取当月第一天
            String first = TimeUtils.getMonthFirst();
            //获取当月最后一天
            String last = TimeUtils.getMonthLast();
            weeks.add(first);
            weeks.add(first.substring(0,8)+"07");
            weeks.add(first.substring(0,8)+"14");
            weeks.add(first.substring(0,8)+"21");
            weeks.add(last);
        }
        return weeks;
    }
}
