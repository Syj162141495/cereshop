/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.distributor.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.distributor.CereShopDistributorDAO;
import com.shop.cereshop.business.page.distribution.ShopDistributor;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.param.shopDistributor.*;
import com.shop.cereshop.business.service.distributor.CereShopDistributorService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereShopDistributorServiceImpl implements CereShopDistributorService {

    @Autowired
    private CereShopDistributorDAO cereShopDistributorDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopDistributorSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //校验分销员手机号是否已添加
        CereShopDistributor cereShopDistributor=cereShopDistributorDAO.checkPhone(param.getDistributorPhone(),null,param.getShopId());
        if(cereShopDistributor!=null&&IntegerEnum.NO.getCode().equals(cereShopDistributor.getIfLiquidation())){
            throw new CoBusinessException(CoReturnFormat.DISTRIBUTOR_PHONE_ALREADY_ADD);
        }
        //校验客户是否有效
        CereBuyerUser buyerUser=cereShopDistributorDAO.checkBuyerUser(param.getDistributorPhone());
        if(buyerUser==null){
            throw new CoBusinessException(CoReturnFormat.USER_NOT_BUYER);
        }else {
            if(IntegerEnum.NO.getCode().equals(buyerUser.getState())){
                throw new CoBusinessException(CoReturnFormat.USER_ALREADY_STOP);
            }
            if(IntegerEnum.YES.getCode().equals(buyerUser.getIfBlack())){
                throw new CoBusinessException(CoReturnFormat.USER_ALREADY_BLACK);
            }
        }
        if(cereShopDistributor!=null){
            //更新
            cereShopDistributor.setInvitees(param.getInvitees());
            cereShopDistributor.setDistributorName(param.getDistributorName());
            cereShopDistributor.setIfLiquidation(IntegerEnum.NO.getCode());
            cereShopDistributor.setUpdateTime(time);
            cereShopDistributorDAO.updateByPrimaryKeySelective(cereShopDistributor);
        }else {
            //新增分销员
            cereShopDistributor=new CereShopDistributor();
            cereShopDistributor.setShopId(param.getShopId());
            cereShopDistributor.setCreateTime(time);
            cereShopDistributor.setInvitees(param.getInvitees());
            cereShopDistributor.setDistributorLevelId(param.getDistributorLevelId());
            cereShopDistributor.setDistributorName(param.getDistributorName());
            cereShopDistributor.setDistributorPhone(param.getDistributorPhone());
            cereShopDistributor.setState(IntegerEnum.DISTRIBUTOR_SUCCESS.getCode());
            cereShopDistributor.setIfLiquidation(IntegerEnum.NO.getCode());
            //生成10位随机数邀请码
            cereShopDistributor.setInvitationCode(RandomStringUtil.getRandomCode(10,0));
            //默认未清退
            cereShopDistributor.setIfLiquidation(IntegerEnum.NO.getCode());
            cereShopDistributorDAO.insert(cereShopDistributor);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"分销员管理","商户端操作","添加分销员",cereShopDistributor.getDistributorId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopDistributorUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //编辑分销员
        CereShopDistributor cereShopDistributor=cereShopDistributorDAO.checkPhone(param.getDistributorPhone(),
                param.getDistributorId(),param.getShopId());
        if(cereShopDistributor!=null){
            throw new CoBusinessException(CoReturnFormat.DISTRIBUTOR_PHONE_ALREADY_ADD);
        }
        if(param.getDistributorId().equals(param.getInvitees())){
            throw new CoBusinessException(CoReturnFormat.DISTRIBUTOR_NOT_SELF);
        }
        //校验客户是否有效
        CereBuyerUser buyerUser=cereShopDistributorDAO.checkBuyerUser(param.getDistributorPhone());
        if(buyerUser==null){
            throw new CoBusinessException(CoReturnFormat.USER_NOT_BUYER);
        }else {
            if(IntegerEnum.NO.getCode().equals(buyerUser.getState())){
                throw new CoBusinessException(CoReturnFormat.USER_ALREADY_STOP);
            }
            if(IntegerEnum.YES.getCode().equals(buyerUser.getIfBlack())){
                throw new CoBusinessException(CoReturnFormat.USER_ALREADY_BLACK);
            }
        }
        cereShopDistributor=new CereShopDistributor();
        cereShopDistributor.setUpdateTime(time);
        cereShopDistributor.setDistributorId(param.getDistributorId());
        cereShopDistributor.setInvitees(param.getInvitees());
        cereShopDistributor.setDistributorLevelId(param.getDistributorLevelId());
        cereShopDistributor.setDistributorName(param.getDistributorName());
        cereShopDistributor.setDistributorPhone(param.getDistributorPhone());
        cereShopDistributorDAO.updateByPrimaryKeySelective(cereShopDistributor);
        //新增日志
        cerePlatformLogService.addLog(user,"分销员管理","商户端操作","修改分销员",param.getDistributorId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ShopDistributorDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        //修改分销员清退状态
        String time=TimeUtils.yyMMddHHmmss();
        CereShopDistributor cereShopDistributor=new CereShopDistributor();
        cereShopDistributor.setDistributorId(param.getDistributorId());
        cereShopDistributor.setUpdateTime(time);
        cereShopDistributor.setIfLiquidation(IntegerEnum.YES.getCode());
        cereShopDistributorDAO.updateByPrimaryKeySelective(cereShopDistributor);
        //查询下级分销员
        CereShopDistributor distributor=cereShopDistributorDAO.findSubordinate(param.getDistributorId());
        if(distributor!=null){
            //解除绑定关系
            distributor.setInvitees(null);
            cereShopDistributorDAO.updateByPrimaryKeySelective(distributor);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"分销员管理","商户端操作","清退分销员",param.getDistributorId(),time);
    }

    @Override
    public Page getAll(ShopDistributorGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopDistributor> list=cereShopDistributorDAO.getAll(param);
        PageInfo<ShopDistributor> pageInfo=new PageInfo<>(list);
        if(!EmptyUtils.isEmpty(list)){
            list=list.stream()
                    //查询累计客户数
                    .peek(a->a.setTotal(cereShopDistributorDAO.findTotal(a.getDistributorId())))
                    //查询累计下级数
                    .peek(a->a.setSubordinate(cereShopDistributorDAO.findSubordinateTotal(a.getDistributorId())))
                    //查询累计佣金
                    .peek(a->a.setMoney(cereShopDistributorDAO.findMoney(a.getDistributorId())))
                    .collect(Collectors.toList());
            pageInfo.setList(list);
        }
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CereShopDistributor> getAllInvitees(ShopParam param) throws CoBusinessException {
        return cereShopDistributorDAO.getAllInvitees(param);
    }

    @Override
    public Page getStayExamineAll(ShopDistributorGetStayParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopDistributor> list=cereShopDistributorDAO.getStayExamineAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.stream()
                    //查询累计下单数
                    .peek(a->a.setOrderTotal(cereShopDistributorDAO.findOrderTotal(a.getDistributorId())))
                    //查询累计消费金额
                    .peek(a->a.setOrderMoney(cereShopDistributorDAO.findOrderMoney(a.getDistributorId())));
        }
        PageInfo<ShopDistributor> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(ShopDistributorHandleParam param, CerePlatformBusiness user) throws CoBusinessException {
        //修改审核状态
        String time=TimeUtils.yyMMddHHmmss();
        //查询分销员数据
        CereShopDistributor cereShopDistributor=cereShopDistributorDAO.selectByPrimaryKey(param.getDistributorId());
        if(cereShopDistributor!=null){
            cereShopDistributor.setState(param.getState());
            cereShopDistributor.setUpdateTime(time);
            String describe="";
            if(IntegerEnum.DISTRIBUTOR_SUCCESS.getCode().equals(param.getState())){
                describe="同意分销员申请";
                cereShopDistributor.setJoinTime(time);
            }else {
                describe="拒绝分销员申请";
            }
            cereShopDistributorDAO.updateByPrimaryKeySelective(cereShopDistributor);
            //新增日志
            cerePlatformLogService.addLog(user,"分销员管理","商户端操作",describe,param.getDistributorId(),time);
        }
    }

    @Override
    public ShopDistributor getById(Long distributorId) throws CoBusinessException {
        return cereShopDistributorDAO.getById(distributorId);
    }

    @Override
    public CereShopDistributor findByShopIdAndUserId(Long shopId, Long buyerUserId) {
        return cereShopDistributorDAO.findByShopIdAndUserId(shopId,buyerUserId);
    }

    @Override
    public void updateReliveBindByShopId(Long shopId, String time) {
        cereShopDistributorDAO.updateReliveBindByShopId(shopId,time);
    }

    @Override
    public void updateBatch(List<CereShopDistributor> list) throws CoBusinessException {
        cereShopDistributorDAO.updateBatch(list);
    }

}
