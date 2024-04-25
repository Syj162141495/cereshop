/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.platformtool.CerePlatformPoliteDAO;
import com.shop.cereshop.admin.page.polite.*;
import com.shop.cereshop.admin.param.polite.PoliteGetAllParam;
import com.shop.cereshop.admin.param.polite.PoliteGetByIdParam;
import com.shop.cereshop.admin.param.polite.PoliteSaveParam;
import com.shop.cereshop.admin.param.polite.PoliteUpdateParam;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteActivityService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPolite;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Service
public class CerePlatformPoliteServiceImpl implements CerePlatformPoliteService {

    @Autowired
    private CerePlatformPoliteDAO cerePlatformPoliteDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CerePlatformPoliteActivityService cerePlatformPoliteActivityService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(PoliteSaveParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformPolite cerePlatformPolite=new CerePlatformPolite();
        cerePlatformPolite.setPoliteName(param.getPoliteName());
        cerePlatformPolite.setRemark(param.getRemark());
        cerePlatformPolite.setStartTime(param.getStartTime());
        cerePlatformPolite.setEndTime(param.getEndTime());
        cerePlatformPolite.setBuyerMode(param.getBuyerMode());
        cerePlatformPolite.setBuyer(param.getBuyer());
        cerePlatformPolite.setGrowth(param.getGrowth());
        cerePlatformPolite.setCreateTime(time);
        //校验活动结束时间必须大于活动开始时间
        if(!TimeUtils.compareTo(param.getEndTime(),param.getStartTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果活动开始时间大于当前时间,活动状态为未开始
        if(TimeUtils.compareTo(param.getStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformPolite.setState(IntegerEnum.POLITE_NOT_START.getCode());
        }
        //如果当前时间在活动起始时间范围内,活动状态为进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cerePlatformPolite.setState(IntegerEnum.POLITE_ON.getCode());
        }
        //如果当前时间大于活动结束时间,活动状态为已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getEndTime())){
            throw new CoBusinessException(CoReturnFormat.NOW_DATE_LESS_END_TIME);
        }
        //校验时间是否有交叉
        List<CerePlatformPolite> list=cerePlatformPoliteDAO.checkTime(param);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        cerePlatformPoliteDAO.insert(cerePlatformPolite);
        //新增redis任务
        if(cerePlatformPolite.getState().equals(IntegerEnum.POLITE_NOT_START.getCode())){
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.POLITE_START.getCode()+"-"+cerePlatformPolite.getPoliteId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.POLITE_START.getCode()+"-"+cerePlatformPolite.getPoliteId(),param.getStartTime());
        }else if(cerePlatformPolite.getState().equals(IntegerEnum.POLITE_ON.getCode())){
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId(),param.getEndTime());
        }
        if(!EmptyUtils.isEmpty(param.getDetails())){
            List<CerePlatformPoliteActivity> details = param.getDetails();
            details.forEach(detail -> {detail.setPoliteId(cerePlatformPolite.getPoliteId());});
            //批量插入优惠券数据
            cerePlatformPoliteActivityService.insertBatch(details);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"平台支付有礼活动管理","平台端操作","添加平台支付有礼活动",String.valueOf(cerePlatformPolite.getPoliteId()),time);
    }

    @Override
    public CerePlatformPolite findById(Long politeId) {
        return cerePlatformPoliteDAO.selectByPrimaryKey(politeId);
    }

    @Override
    public void updatePolite(CerePlatformPolite cerePlatformPolite) throws CoBusinessException {
        cerePlatformPoliteDAO.updateByPrimaryKeySelective(cerePlatformPolite);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(PoliteUpdateParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        //如果当前时间大于活动结束时间,活动状态为已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getEndTime())){
            throw new CoBusinessException(CoReturnFormat.NOW_DATE_LESS_END_TIME);
        }
        //校验时间是否有交叉
        List<CerePlatformPolite> list=cerePlatformPoliteDAO.checkUpdateTime(param);
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformPolite cerePlatformPolite=new CerePlatformPolite();
        cerePlatformPolite.setPoliteId(param.getPoliteId());
        cerePlatformPolite.setPoliteName(param.getPoliteName());
        cerePlatformPolite.setRemark(param.getRemark());
        cerePlatformPolite.setStartTime(param.getStartTime());
        cerePlatformPolite.setEndTime(param.getEndTime());
        cerePlatformPolite.setBuyerMode(param.getBuyerMode());
        cerePlatformPolite.setBuyer(param.getBuyer());
        cerePlatformPolite.setGrowth(param.getGrowth());
        cerePlatformPolite.setUpdateTime(time);
        //校验活动结束时间必须大于活动开始时间
        if(!TimeUtils.compareTo(param.getEndTime(),param.getStartTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果活动开始时间大于当前时间,状态为未开始
        if(TimeUtils.compareTo(cerePlatformPolite.getStartTime(),time)){
            cerePlatformPolite.setState(IntegerEnum.POLITE_NOT_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.POLITE_START.getCode()+"-"+cerePlatformPolite.getPoliteId());
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.POLITE_START.getCode()+"-"+cerePlatformPolite.getPoliteId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.POLITE_START.getCode()+"-"+cerePlatformPolite.getPoliteId(),param.getStartTime());
        }
        //如果当前时间在活动起始范围内,状态为进行中
        if(TimeUtils.isBelong(cerePlatformPolite.getStartTime(),cerePlatformPolite.getEndTime())){
            cerePlatformPolite.setState(IntegerEnum.POLITE_ON.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId());
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.POLITE_END.getCode()+"-"+cerePlatformPolite.getPoliteId(),param.getEndTime());

        }
        cerePlatformPoliteDAO.updateByPrimaryKeySelective(cerePlatformPolite);
        //清空优惠券
        cerePlatformPoliteActivityService.deleteByPoliteId(cerePlatformPolite.getPoliteId());
        if(!EmptyUtils.isEmpty(param.getDetails())){
            List<CerePlatformPoliteActivity> details = param.getDetails();
            details.forEach(detail -> {detail.setPoliteId(cerePlatformPolite.getPoliteId());});
            //批量插入优惠券数据
            cerePlatformPoliteActivityService.insertBatch(details);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"平台支付有礼活动管理","平台端操作","添加平台支付有礼活动",String.valueOf(cerePlatformPolite.getPoliteId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(PoliteGetByIdParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.POLITE_START.getCode()+"-"+param.getPoliteId());
        stringRedisService.delete(StringEnum.POLITE_END.getCode()+"-"+param.getPoliteId());
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.POLITE_START.getCode()+"-"+param.getPoliteId());
        cereRedisKeyServcice.deleteByKey(StringEnum.POLITE_END.getCode()+"-"+param.getPoliteId());
        //删除活动数据
        cerePlatformPoliteDAO.deleteByPrimaryKey(param.getPoliteId());
        //新增日志
        cerePlatformLogService.addLog(user,"平台支付有礼活动管理","平台端操作","删除平台支付有礼活动",String.valueOf(param.getPoliteId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(PoliteGetByIdParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.POLITE_START.getCode()+"-"+param.getPoliteId());
        stringRedisService.delete(StringEnum.POLITE_END.getCode()+"-"+param.getPoliteId());
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.POLITE_START.getCode()+"-"+param.getPoliteId());
        cereRedisKeyServcice.deleteByKey(StringEnum.POLITE_END.getCode()+"-"+param.getPoliteId());
        //更新活动状态为活动已结束
        CerePlatformPolite cerePlatformPolite=new CerePlatformPolite();
        cerePlatformPolite.setPoliteId(param.getPoliteId());
        cerePlatformPolite.setState(IntegerEnum.POLITE_END.getCode());
        cerePlatformPolite.setUpdateTime(time);
        cerePlatformPoliteDAO.updateByPrimaryKeySelective(cerePlatformPolite);
        //新增日志
        cerePlatformLogService.addLog(user,"平台支付有礼活动管理","平台端操作","停止平台支付有礼活动",String.valueOf(param.getPoliteId()),time);
    }

    @Override
    public PoliteDetail getById(Long politeId) throws CoBusinessException {
        PoliteDetail detail = cerePlatformPoliteDAO.getById(politeId);
        if(detail!=null){
            //查询优惠券明细
            detail.setDetails(cerePlatformPoliteActivityService.findByPoliteId(politeId));
        }
        return detail;
    }

    @Override
    public PoliteData getData(PoliteGetByIdParam param) throws CoBusinessException {
        Long politeId = param.getPoliteId();
        PoliteData data=new PoliteData();
        //查询活动优惠券数量
        int number=cerePlatformPoliteDAO.findActivityNumber(politeId);
        //设置发放优惠券数量
        data.setNumber(cerePlatformPoliteDAO.findNumber(politeId)*number);
        //设置领取礼品笔数
        data.setReceives(cerePlatformPoliteDAO.findNumber(politeId));
        //设置领取礼品人数
        data.setUsers(cerePlatformPoliteDAO.findUsers(politeId));
        //查询活动成长值
        CerePlatformPolite polite=cerePlatformPoliteDAO.selectByPrimaryKey(politeId);
        if(polite!=null){
            //设置成长值发放
            data.setGrowth(data.getReceives()*polite.getGrowth());
        }
        //查询领取明细
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<PoliteDataDetail> dataDetails = cerePlatformPoliteDAO.findDataDetails(polite.getPoliteId());
        if(!EmptyUtils.isEmpty(dataDetails)){
            dataDetails.forEach(detail -> {
                detail.setNumber(detail.getCount()*number);
                detail.setGrowth(detail.getCount()*polite.getGrowth());
            });
        }
        PageInfo<PoliteDataDetail> pageInfo=new PageInfo<>(dataDetails);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setDetails(page);
        return data;
    }

    @Override
    public Page getAll(PoliteGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Polite> list=cerePlatformPoliteDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(polite -> {
                String remark="";
                if(IntegerEnum.POLITE_BUYER_MONEY.getCode().equals(polite.getBuyerMode())){
                    //拼接成长值
                    remark+="消费满"+polite.getBuyer()+"元";
                    if(!EmptyUtils.isEmpty(polite.getGrowth())){
                        remark+=polite.getGrowth()+"点会员成长值";
                    }
                }else {
                    //拼接优惠券
                    remark+="消费满"+polite.getBuyer()+"件";
                    //查询优惠券明细
                    List<PoliteActivityDetail> activities=cerePlatformPoliteActivityService.findByPoliteId(polite.getPoliteId());
                    if(!EmptyUtils.isEmpty(activities)){
                        remark+="赠";
                        for (int i = 0; i < activities.size(); i++) {
                            if(i==0||i==activities.size()-1){
                                remark+=activities.get(i).getCouponContent()+"元优惠券";
                            }else {
                                remark+="、"+activities.get(i).getCouponContent()+"元优惠券";
                            }
                        }
                    }
                }
                polite.setRemark(remark);
            });
        }
        PageInfo<Polite> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getAllActivity(PageParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<PoliteActivity> list=cerePlatformPoliteDAO.getAllActivity();
        PageInfo<PoliteActivity> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
