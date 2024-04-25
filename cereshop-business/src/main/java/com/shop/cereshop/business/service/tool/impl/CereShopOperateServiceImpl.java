/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.tool.CereShopOperateDAO;
import com.shop.cereshop.business.page.operate.OperateCoupon;
import com.shop.cereshop.business.page.operate.OperateData;
import com.shop.cereshop.business.page.operate.ShopOperate;
import com.shop.cereshop.business.page.tool.ShopOperateDetail;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.notice.CereNoticeService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.CereShopCouponService;
import com.shop.cereshop.business.service.tool.CereShopOperateDetailService;
import com.shop.cereshop.business.service.tool.CereShopOperateService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
import com.shop.cereshop.commons.domain.tool.CereShopOperate;
import com.shop.cereshop.commons.domain.tool.CereShopOperateDetail;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereShopOperateServiceImpl implements CereShopOperateService {

    @Autowired
    private CereShopOperateDAO cereShopOperateDAO;

    @Autowired
    private CereShopOperateDetailService cereShopOperateDetailService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereNoticeService cereNoticeService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CereShopCouponService cereShopCouponService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void saveOperate(ShopOperateSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopOperate cereShopOperate=new CereShopOperate();
        cereShopOperate.setShopCrowdId(param.getShopCrowdId());
        cereShopOperate.setCreateTime(time);
        cereShopOperate.setShopId(param.getShopId());
        cereShopOperate.setPlanMode(param.getPlanMode());
        cereShopOperate.setManualTime(param.getManualTime());
        cereShopOperate.setOperateName(param.getOperateName());
        cereShopOperate.setPlanStart(param.getPlanStart());
        cereShopOperate.setPlanEnd(param.getPlanEnd());
        //设置状态
        if(IntegerEnum.OPERATE_PLAN_MODE_MANUAL.getCode().equals(param.getPlanMode())){
            if(EmptyUtils.isEmpty(param.getManualTime())){
                cereShopOperate.setManualTime(time);
                //手动计划,立即执行,状态为已结束
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_END.getCode());
                //如果是立即执行,新增营销通知消息数据
                cereNoticeService.addOperate(param.getShopCrowdId(),param.getCoupons(),param.getShopId(),cereShopOperate.getShopOperateId());
            }else{
                //手动计划定时执行,状态为进行中
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_START.getCode());
            }
        }else {
            //如果是自动长期计划,判断当前时间是否大于开始时间
            if(TimeUtils.compareTo(time,param.getPlanStart())){
                //如果大于,状态为进行中
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_START.getCode());
            }else {
                //如果小于,状态为未开始
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_READY.getCode());
            }
        }
        //插入运营计划数据
        cereShopOperateDAO.insert(cereShopOperate);
        if(IntegerEnum.OPERATE_PLAN_MODE_MANUAL.getCode().equals(param.getPlanMode())){
            if(EmptyUtils.isEmpty(param.getManualTime())){
                //如果是立即执行,新增营销通知消息数据
                cereNoticeService.addOperate(param.getShopCrowdId(),param.getCoupons(),param.getShopId(),cereShopOperate.getShopOperateId());
            }else{
                //定时执行,新增redis延时任务发送消息通知(在定时时间执行)
                stringRedisService.set(StringEnum.NOTICE_OPERATE.getCode()+"-"+cereShopOperate.getShopOperateId()
                        +"-"+param.getShopCrowdId()+"-"+param.getShopId(),param.getCoupons(),TimeUtils.getCountDownByTime(time,param.getManualTime()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.NOTICE_OPERATE.getCode()+"-"+cereShopOperate.getShopOperateId()
                        +"-"+param.getShopCrowdId()+"-"+param.getShopId(),param.getManualTime());
                //判断当前时间是否大于定时时间
                if(!TimeUtils.compareTo(time,param.getManualTime())){
                    //如果小于,新增延时任务修改状态为已结束
                    stringRedisService.set(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                            ,1,TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                    //新增redis延时记录
                    cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                            ,param.getPlanStart());
                }
            }
        }else {
            //如果是自动长期计划,判断当前时间是否大于开始时间
            if(TimeUtils.compareTo(time,param.getPlanStart())){
                //如果大于,当前执行一次推送消息通知
                cereNoticeService.addOperate(param.getShopCrowdId(),param.getCoupons(),param.getShopId(),cereShopOperate.getShopOperateId());
                //新增延时任务修改状态为已结束
                stringRedisService.set(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,1,TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,param.getPlanStart());
            }else {
                //如果小于,新增延时任务在开始时间执行推送消息通知
                stringRedisService.set(StringEnum.NOTICE_OPERATE_AUTOMATIC.getCode()+"-"+cereShopOperate.getShopOperateId()
                        +"-"+param.getShopCrowdId()+"-"+param.getShopId(),param.getCoupons(),TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.NOTICE_OPERATE_AUTOMATIC.getCode()+"-"+cereShopOperate.getShopOperateId()
                        +"-"+param.getShopCrowdId()+"-"+param.getShopId(),param.getPlanStart());
                //新增延时任务修改状态为进行中
                stringRedisService.set(StringEnum.OPERATE_STATE_STAY.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,1,TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STAY.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,param.getPlanStart());
            }
        }
        //新增优惠券明细数据
        if(!EmptyUtils.isEmpty(param.getCoupons())){
            List<CereShopOperateDetail> list = param.getCoupons().stream().map(coupon -> {
                CereShopOperateDetail detail = new CereShopOperateDetail();
                detail.setNumber(coupon.getNumber());
                detail.setShopCouponId(coupon.getShopCouponId());
                detail.setShopOperateId(cereShopOperate.getShopOperateId());
                return detail;
            }).collect(Collectors.toList());
            cereShopOperateDetailService.insertBatch(list);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"运营计划管理","商家端操作","新增运营计划",cereShopOperate.getShopOperateId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopOperateUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        //删除相关延时任务
        stringRedisService.delete(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+param.getShopOperateId());
        cereRedisKeyServcice.deleteByKey(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+param.getShopOperateId());
        stringRedisService.delete(StringEnum.OPERATE_STATE_STAY.getCode()+"-"+param.getShopOperateId());
        cereRedisKeyServcice.deleteByKey(StringEnum.OPERATE_STATE_STAY.getCode()+"-"+param.getShopOperateId());
        stringRedisService.delete(StringEnum.NOTICE_OPERATE.getCode()+"-"+param.getShopOperateId());
        cereRedisKeyServcice.deleteByKey(StringEnum.NOTICE_OPERATE.getCode()+"-"+param.getShopOperateId());
        String time = TimeUtils.yyMMddHHmmss();
        CereShopOperate cereShopOperate=new CereShopOperate();
        cereShopOperate.setShopOperateId(param.getShopOperateId());
        cereShopOperate.setShopCrowdId(param.getShopCrowdId());
        cereShopOperate.setCreateTime(time);
        cereShopOperate.setShopId(param.getShopId());
        cereShopOperate.setPlanMode(param.getPlanMode());
        cereShopOperate.setManualTime(param.getManualTime());
        cereShopOperate.setOperateName(param.getOperateName());
        cereShopOperate.setPlanStart(param.getPlanStart());
        cereShopOperate.setPlanEnd(param.getPlanEnd());
        //设置状态
        if(IntegerEnum.OPERATE_PLAN_MODE_MANUAL.getCode().equals(param.getPlanMode())){
            if(EmptyUtils.isEmpty(param.getManualTime())){
                //设置定时时间为当天
                cereShopOperate.setManualTime(time);
                //手动计划,立即执行,状态为已结束
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_END.getCode());
                //如果是立即执行,新增营销通知消息数据
                cereNoticeService.addOperate(param.getShopCrowdId(),param.getCoupons(),param.getShopId(),cereShopOperate.getShopOperateId());
            }else{
                //手动计划定时执行,状态为进行中
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_START.getCode());
                //定时执行,新增redis延时任务发送消息通知(在定时时间执行)
                stringRedisService.set(StringEnum.NOTICE_OPERATE.getCode()+"-"+cereShopOperate.getShopOperateId()
                        +"-"+param.getShopCrowdId()+"-"+param.getShopId(),param.getCoupons(),TimeUtils.getCountDownByTime(time,param.getManualTime()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.NOTICE_OPERATE.getCode()+"-"+cereShopOperate.getShopOperateId()
                        +"-"+param.getShopCrowdId()+"-"+param.getShopId(),param.getManualTime());
                //判断当前时间是否大于定时时间
                if(!TimeUtils.compareTo(time,param.getManualTime())){
                    //如果小于,新增延时任务修改状态为已结束
                    stringRedisService.set(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                            ,1,TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                    //新增redis延时记录
                    cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                            ,param.getPlanStart());
                }
            }
        }else {
            //如果是自动长期计划,判断当前时间是否大于开始时间
            if(TimeUtils.compareTo(time,param.getPlanStart())){
                //如果大于,状态为进行中
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_START.getCode());
                //新增延时任务修改状态为已结束
                stringRedisService.set(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,1,TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STOP.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,param.getPlanStart());
            }else {
                //如果小于,状态为未开始
                cereShopOperate.setState(IntegerEnum.COUPON_STATE_READY.getCode());
                //新增延时任务修改状态为进行中
                stringRedisService.set(StringEnum.OPERATE_STATE_STAY.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,1,TimeUtils.getCountDownByTime(time,param.getPlanStart()));
                //新增redis延时记录
                cereRedisKeyServcice.add(StringEnum.OPERATE_STATE_STAY.getCode()+"-"+cereShopOperate.getShopOperateId()
                        ,param.getPlanStart());
            }
        }
        //更新运营计划数据
        cereShopOperateDAO.updateByPrimaryKeySelective(cereShopOperate);
        //新增优惠券明细数据
        if(!EmptyUtils.isEmpty(param.getCoupons())){
            List<CereShopOperateDetail> list = param.getCoupons().stream().map(coupon -> {
                CereShopOperateDetail detail = new CereShopOperateDetail();
                detail.setNumber(coupon.getNumber());
                detail.setShopCouponId(coupon.getShopCouponId());
                detail.setShopOperateId(cereShopOperate.getShopOperateId());
                return detail;
            }).collect(Collectors.toList());
            cereShopOperateDetailService.insertBatch(list);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"运营计划管理","商家端操作","修改运营计划",cereShopOperate.getShopOperateId(),time);
    }

    @Override
    public void updateState(CereShopOperate cereShopOperate) throws CoBusinessException {
        cereShopOperateDAO.updateByPrimaryKeySelective(cereShopOperate);
    }

    @Override
    public CereShopOperate findById(Long shopOperateId) {
        return cereShopOperateDAO.selectByPrimaryKey(shopOperateId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(List<Long> ids, CerePlatformBusiness user) throws CoBusinessException {
        if(!EmptyUtils.isEmpty(ids)){
            for (Long id : ids) {
                cereShopOperateDAO.deleteByPrimaryKey(id);
                //新增日志
                cerePlatformLogService.addLog(user,"运营计划管理","商家端操作","删除运营计划",id,TimeUtils.yyMMddHHmmss());
            }
        }
    }

    @Override
    public ShopOperateDetail getById(Long shopOperateId) throws CoBusinessException {
        ShopOperateDetail detail = cereShopOperateDAO.getById(shopOperateId);
        if(detail!=null){
            if(detail.getManualTime().equals(detail.getCreateTime())){
                detail.setIfImplement(IntegerEnum.YES.getCode());
            }else {
                detail.setIfImplement(IntegerEnum.NO.getCode());
            }
            //查询优惠券明细数据
            List<OperateCoupon> list = cereShopOperateDetailService.findCoupons(shopOperateId);
            if(!EmptyUtils.isEmpty(list)){
                list.forEach(operateCoupon -> {
                    if(!EmptyUtils.isEmpty(operateCoupon.getThreshold())){
                        operateCoupon.setContent("满"+operateCoupon.getThreshold().stripTrailingZeros().toPlainString()
                                +"减"+operateCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                    }else {
                        operateCoupon.setContent("无门槛,减"+operateCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                    }
                });
            }
            detail.setCoupons(list);
        }
        return detail;
    }

    @Override
    public Page getAll(ShopOperateGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopOperate> list=cereShopOperateDAO.getAll(param);
        PageInfo<ShopOperate> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<OperateData> getDatas(Long shopOperateId) throws CoBusinessException,Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //查询运营计划数据
        CereShopOperate cereShopOperate=cereShopOperateDAO.selectByPrimaryKey(shopOperateId);
        if(cereShopOperate!=null){
            List<OperateData> datas=null;
            //查询运营计划所属日期数组
            if(IntegerEnum.OPERATE_PLAN_MODE_MANUAL.getCode().equals(cereShopOperate.getPlanMode())){
                //如果是定时任务计划,只去当天时间
                datas=new ArrayList<>();
                OperateData operateData=new OperateData();
                operateData.setTime(sdf.format(sdf.parse(cereShopOperate.getManualTime())));
                datas.add(operateData);
            }else {
                //如果是自动长期计划
                List<String> times=TimeUtils.findDaysStr(cereShopOperate.getPlanStart(),cereShopOperate.getPlanEnd());
                if(!EmptyUtils.isEmpty(times)){
                    datas = times.stream().map(time -> {
                        OperateData operateData = new OperateData();
                        operateData.setTime(time);
                        return operateData;
                    }).collect(Collectors.toList());
                }
            }
            if(!EmptyUtils.isEmpty(datas)){
                datas.forEach(data -> {
                    //查询发券人数
                    data.setPerson(cereShopOperateDAO.findCouponUsers(data.getTime(),shopOperateId));
                    //查询下单人数
                    data.setUsers(cereShopOperateDAO.findUsers(data.getTime(),shopOperateId));
                    //查询下单笔数
                    data.setOrders(cereShopOperateDAO.findOrders(data.getTime(),shopOperateId));
                    //查询下单金额
                    data.setTotal(cereShopOperateDAO.findTotal(data.getTime(),shopOperateId));
                    //查询支付人数
                    data.setPays(cereShopOperateDAO.findPays(data.getTime(),shopOperateId));
                    //查询支付订单数
                    data.setPayOrders(cereShopOperateDAO.findPayOrders(data.getTime(),shopOperateId));
                });
                return datas;
            }
        }
        return null;
    }

    @Override
    public List<OperateCoupon> findCouponDetails(Long shopOperateId) {
        return cereShopOperateDetailService.findCoupons(shopOperateId);
    }

    @Override
    public List<Long> findAlreadyCoupon(List<Long> ids,Long shopOperateId) {
        return cereShopOperateDAO.findAlreadyCoupon(ids,shopOperateId);
    }

    @Override
    public List<CereShopOperate> findShopOperate() {
        return cereShopOperateDAO.findShopOperate();
    }

    @Override
    public void updateOperateEndState(List<CereShopOperate> operates, String time) throws CoBusinessException {
        cereShopOperateDAO.updateOperateEndState(operates,time);
    }

    @Override
    public Page selectCoupon(OperateCouponParam param) throws CoBusinessException,Exception {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<OperateCoupon> list=cereShopCouponService.selectCoupon(param);
        //过滤有效期不在结束时间之后的优惠券
        List<OperateCoupon> resulds=new ArrayList<>();
        for (OperateCoupon operateCoupon : list) {
            if(IntegerEnum.TIME_TYPE_CHANGE.getCode().equals(operateCoupon.getTimeType())
            ||TimeUtils.compareTo(operateCoupon.getEffectiveEnd(),param.getEndTime())){
                resulds.add(operateCoupon);
            }
        }
        if(!EmptyUtils.isEmpty(resulds)){
            resulds.forEach(operateCoupon -> {
                if(!EmptyUtils.isEmpty(operateCoupon.getThreshold())){
                    operateCoupon.setContent("满"+operateCoupon.getThreshold().stripTrailingZeros().toPlainString()
                    +"减"+operateCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                }else {
                    operateCoupon.setContent("无门槛,减"+operateCoupon.getCouponContent().stripTrailingZeros().toPlainString()+"元");
                }
            });
        }
        PageInfo<OperateCoupon> pageInfo=new PageInfo<>(resulds);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CereShopOperate> findAll() {
        return cereShopOperateDAO.findAll();
    }

    @Override
    public CereShopCrowd findCrowd(Long shopCrowdId) {
        return cereShopOperateDAO.findCrowd(shopCrowdId);
    }

    @Override
    public List<String> findUserByCondition(CrowdCondition condition) {
        return cereShopOperateDAO.findUserByCondition(condition);
    }

    @Override
    public List<String> findNoBuy(CrowdCondition condition) {
        return cereShopOperateDAO.findNoBuy(condition);
    }

    @Override
    public List<String> findNoOrder(CrowdCondition condition) {
        return cereShopOperateDAO.findNoOrder(condition);
    }

    @Override
    public List<String> findNoVisit(CrowdCondition condition) {
        return cereShopOperateDAO.findNoVisit(condition);
    }

    @Override
    public List<OperateCoupon> findCouponsById(Long shopOperateId) {
        return cereShopOperateDAO.findCouponsById(shopOperateId);
    }

    @Override
    public void insertBatchNotice(List<CereNotice> list) throws CoBusinessException {
        cereShopOperateDAO.insertBatchNotice(list);
    }

    @Override
    public void insertBatchBuyerCoupon(List<CereBuyerShopCoupon> list) throws CoBusinessException {
        cereShopOperateDAO.insertBatchBuyerCoupon(list);
    }


}
