/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor.impl;

import com.shop.cereshop.business.dao.distributor.CereDistributorBuyerDAO;
import com.shop.cereshop.business.param.ship.ShipBindParam;
import com.shop.cereshop.business.param.ship.ShipDeleteParam;
import com.shop.cereshop.business.param.ship.ShipRelieveParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.distributor.CereDistributorBuyerService;
import com.shop.cereshop.business.service.distributor.CereShopDistributorService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.shop.CereShopRelationshipService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.distributor.CereDistributorBuyer;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.domain.redis.CereRedisKey;
import com.shop.cereshop.commons.domain.shop.CereShopRelationship;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;

@Service
public class CereDistributorBuyerServiceImpl implements CereDistributorBuyerService {

    @Autowired
    private CereDistributorBuyerDAO cereDistributorBuyerDAO;

    @Autowired
    private CereShopRelationshipService cereShopRelationshipService;

    @Autowired
    private CereShopDistributorService cereShopDistributorService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void bind(ShipBindParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //查询店铺关系设置
        CereShopRelationship relationship=cereShopRelationshipService.getById(param.getShopId());
        if(relationship!=null){
            if(IntegerEnum.NO.getCode().equals(relationship.getIfDistributionRelationship())){
                //如果不允许分销员之间建立客户关系,查询当前绑定客户是否为分销员
                CereShopDistributor distributor=cereShopDistributorService.findByShopIdAndUserId(param.getShopId(),param.getBuyerUserId());
                if(distributor!=null){
                    throw new CoBusinessException(CoReturnFormat.DISTRIBUTO_NOT_RELATIONSHIP);
                }
            }
            //当前客户是否绑定其他分销员
            CereDistributorBuyer cereDistributorBuyer=cereDistributorBuyerDAO.findByUserIdAndDistributorId(param);
            if(cereDistributorBuyer!=null){
                //如果绑定了,查询该分销员所属店铺关系设置数据
                CereShopRelationship cereShopRelationship=cereShopRelationshipService.getById(cereDistributorBuyer.getShopId());
                //定义是否可以抢客
                boolean flag=true;
                if(cereShopRelationship!=null){
                    //如果有绑定
                    if(IntegerEnum.CAN_GRAB_USER.getCode().equals(relationship.getIfRobbing())){
                        //随时可抢
                    }else if(IntegerEnum.NOT_GRAB_USER.getCode().equals(relationship.getIfRobbing())){
                        //不允许抢客
                        flag=false;
                    }else {
                        //保护期几天内不允许抢
                        if(!EmptyUtils.isEmpty(relationship.getRobbingDay())){
                            //计算关系绑定时间到当前时间有效天数
                            int day=TimeUtils.differentDaysByMillisecond(cereDistributorBuyer.getUpdateTime(),time);
                            if(day<=relationship.getRobbingDay()){
                                //如果时间没有超出了保护期天数,不允许抢客
                                flag=false;
                            }
                        }
                    }
                }
                if(flag){
                    //解除之前绑定关系
                    cereDistributorBuyer.setIfBind(IntegerEnum.NO.getCode());
                    cereDistributorBuyer.setUpdateTime(time);
                    cereDistributorBuyerDAO.update(cereDistributorBuyer);
                }else {
                    throw new CoBusinessException(CoReturnFormat.NOT_ROBBING);
                }
            }
        }
        CereDistributorBuyer buyer=new CereDistributorBuyer();
        buyer.setBuyerUserId(param.getBuyerUserId());
        buyer.setShopId(param.getShopId());
        buyer.setDistributorId(param.getDistributorId());
        //查询之前是否绑定过
        CereDistributorBuyer cereDistributorBuyer = cereDistributorBuyerDAO.findBuyer(buyer);
        if(cereDistributorBuyer==null){
            //添加客户绑定分销员
            buyer.setUpdateTime(time);
            buyer.setIfBind(IntegerEnum.YES.getCode());
            cereDistributorBuyerDAO.insert(buyer);
            cereDistributorBuyer = buyer;
        }else {
            //更新绑定状态
            cereDistributorBuyer.setUpdateTime(time);
            cereDistributorBuyer.setIfBind(IntegerEnum.YES.getCode());
            cereDistributorBuyerDAO.update(cereDistributorBuyer);
        }
        if(relationship!=null){
            if(!EmptyUtils.isEmpty(relationship.getValidityDay())){
                //如果设置了有效天数,在有效天数达到之后解除绑定关系
                stringRedisService.set(StringEnum.SHOP_VALIDITY_DAT.getCode()+"-"+cereDistributorBuyer.getDistributorId()+
                                "-"+cereDistributorBuyer.getBuyerUserId(),1,
                        relationship.getValidityDay()*24*60*60*1000);
                //更新redis延时任务记录
                cereRedisKeyServcice.updateByKey(StringEnum.SHOP_VALIDITY_DAT.getCode()+"-"+cereDistributorBuyer.getDistributorId()+
                                "-"+cereDistributorBuyer.getBuyerUserId(),
                        TimeUtils.getMoreDayAfter(TimeUtils.yyMMddHHmmss(),relationship.getValidityDay()));
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void relieveBind(ShipRelieveParam param, CerePlatformBusiness user) throws CoBusinessException {
        CereDistributorBuyer buyer=new CereDistributorBuyer();
        buyer.setBuyerUserId(param.getBuyerUserId());
        buyer.setShopId(param.getShopId());
        buyer.setDistributorId(param.getDistributorId());
        //解除绑定关系
        String time= TimeUtils.yyMMddHHmmss();
        buyer.setUpdateTime(time);
        cereDistributorBuyerDAO.update(buyer);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ShipDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        if(!EmptyUtils.isEmpty(param.getDistributorId())){
            CereDistributorBuyer buyer=new CereDistributorBuyer();
            buyer.setBuyerUserId(param.getBuyerUserId());
            buyer.setShopId(param.getShopId());
            buyer.setDistributorId(param.getDistributorId());
            //删除绑定关系数据
            cereDistributorBuyerDAO.delete(buyer);
        }
    }
}
