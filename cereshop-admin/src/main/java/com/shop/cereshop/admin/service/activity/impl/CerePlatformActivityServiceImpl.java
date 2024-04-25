/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.activity.CerePlatformActivityDAO;
import com.shop.cereshop.admin.page.activity.*;
import com.shop.cereshop.admin.page.canvas.CanvasCoupon;
import com.shop.cereshop.admin.page.canvas.ShopGroupWorkUDetail;
import com.shop.cereshop.admin.page.canvas.ToolProduct;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.param.activity.*;
import com.shop.cereshop.admin.param.canvas.CanvasCouponParam;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.activity.CereActivitySignService;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityDetailService;
import com.shop.cereshop.admin.service.activity.CerePlatformActivityService;
import com.shop.cereshop.admin.service.activity.CereSignProductService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformPoliteActivityService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformPoliteActivity;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.domain.shop.ShopDetail;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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
public class CerePlatformActivityServiceImpl implements CerePlatformActivityService {

    @Autowired
    private CerePlatformActivityDAO cerePlatformActivityDAO;

    @Autowired
    private CerePlatformActivityDetailService cerePlatformActivityDetailService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereActivitySignService cereActivitySignService;

    @Autowired
    private CereSignProductService cereSignProductService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CerePlatformPoliteActivityService cerePlatformPoliteActivityService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ActivitySaveParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //新增营销活动数据
        CerePlatformActivity cerePlatformActivity=new CerePlatformActivity();
        cerePlatformActivity.setCreateTime(time);
        cerePlatformActivity.setUpdateTime(time);
        cerePlatformActivity.setActivityName(param.getActivityName());
        cerePlatformActivity.setActivityIntroduce(param.getActivityIntroduce());
        cerePlatformActivity.setSignStartTime(param.getSignStartTime());
        cerePlatformActivity.setSignEndTime(param.getSignEndTime());
        cerePlatformActivity.setActivityStartTime(param.getActivityStartTime());
        cerePlatformActivity.setActivityEndTime(param.getActivityEndTime());
        cerePlatformActivity.setIfBond(param.getIfBond());
        cerePlatformActivity.setBondMoney(param.getBondMoney());
        cerePlatformActivity.setThreshold(param.getThreshold());
        cerePlatformActivity.setDiscountMode(param.getDiscountMode());
        cerePlatformActivity.setCouponContent(param.getCouponContent());
        cerePlatformActivity.setNumber(param.getNumber());
        cerePlatformActivity.setStockNumber(param.getNumber());
        cerePlatformActivity.setReceiveType(param.getReceiveType());
        cerePlatformActivity.setFrequency(param.getFrequency());
        cerePlatformActivity.setImage(param.getImage());
        //校验活动开始时间必须大于等于报名结束时间
        if(!TimeUtils.compareTo(param.getActivityStartTime(),param.getSignEndTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果报名开始时间大于等于当前时间,活动状态为报名未开始
        if(TimeUtils.compareTo(param.getSignStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_NOT_START.getCode());
        }
        //如果当前时间在报名起始时间范围内,活动状态为报名进行中
        if(TimeUtils.isBelong(param.getSignStartTime(),param.getSignEndTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_SIGN_ON.getCode());
        }
        //如果当前时间在报名结束时间和活动开始时间之间,活动状态为活动待开始
        if(TimeUtils.isBelong(param.getSignEndTime(),param.getActivityStartTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_STAY_START.getCode());
        }
        //如果当前时间在活动起始时间范围内,活动状态为活动进行中
        if(TimeUtils.isBelong(param.getActivityStartTime(),param.getActivityEndTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_START.getCode());
        }
        //如果当前时间大于活动结束时间,活动状态为活动已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getActivityEndTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_END.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CerePlatformActivity> activities=cerePlatformActivityDAO.checkTime(param);
        if(!EmptyUtils.isEmpty(activities)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        cerePlatformActivityDAO.insert(cerePlatformActivity);
        //新增redis任务
        if(cerePlatformActivity.getState().equals(IntegerEnum.ACTIVITY_NOT_START.getCode())){
            //过期时间为报名开始时间-当前时间
            stringRedisService.set(StringEnum.SHOP_SIGN_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_SIGN_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getSignStartTime());
        }else if(cerePlatformActivity.getState().equals(IntegerEnum.ACTIVITY_SIGN_ON.getCode())){
            //过期时间为报名结束时间-当前时间
            stringRedisService.set(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getSignEndTime());
        }else if(cerePlatformActivity.getState().equals(IntegerEnum.ACTIVITY_STAY_START.getCode())){
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getActivityStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getActivityStartTime());
        }else if(cerePlatformActivity.getState().equals(IntegerEnum.ACTIVITY_START.getCode())){
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getActivityEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getActivityEndTime());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"平台优惠券活动管理","平台端操作","添加平台优惠券活动",String.valueOf(cerePlatformActivity.getActivityId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ActivityUpdateParam param, CerePlatformUser user) throws CoBusinessException, Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CerePlatformActivity> activities=cerePlatformActivityDAO.checkUpdateTime(param);
        if(!EmptyUtils.isEmpty(activities)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        //新增营销活动数据
        CerePlatformActivity cerePlatformActivity=new CerePlatformActivity();
        cerePlatformActivity.setActivityId(param.getActivityId());
        cerePlatformActivity.setUpdateTime(time);
        cerePlatformActivity.setActivityName(param.getActivityName());
        cerePlatformActivity.setActivityIntroduce(param.getActivityIntroduce());
        cerePlatformActivity.setSignStartTime(param.getSignStartTime());
        cerePlatformActivity.setSignEndTime(param.getSignEndTime());
        cerePlatformActivity.setActivityStartTime(param.getActivityStartTime());
        cerePlatformActivity.setActivityEndTime(param.getActivityEndTime());
        cerePlatformActivity.setIfBond(param.getIfBond());
        cerePlatformActivity.setBondMoney(param.getBondMoney());
        cerePlatformActivity.setThreshold(param.getThreshold());
        cerePlatformActivity.setDiscountMode(param.getDiscountMode());
        cerePlatformActivity.setCouponContent(param.getCouponContent());
        cerePlatformActivity.setNumber(param.getNumber());
        cerePlatformActivity.setReceiveType(param.getReceiveType());
        cerePlatformActivity.setFrequency(param.getFrequency());
        cerePlatformActivity.setImage(param.getImage());
        //校验活动开始时间必须大于报名结束时间
        if(!TimeUtils.compareTo(param.getActivityStartTime(),param.getSignEndTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果报名开始时间大于当前时间,活动状态为报名未开始
        if(TimeUtils.compareTo(param.getSignStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_NOT_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.SHOP_SIGN_IN.getCode()+"-"+param.getActivityId());
            //过期时间为报名开始时间-当前时间
            stringRedisService.set(StringEnum.SHOP_SIGN_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.SHOP_SIGN_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getSignStartTime());
        }
        //如果当前时间在报名起始时间范围内,活动状态为报名进行中
        if(TimeUtils.isBelong(param.getSignStartTime(),param.getSignEndTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_SIGN_ON.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+param.getActivityId());
            //过期时间为报名结束时间-当前时间
            stringRedisService.set(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getSignEndTime());
        }
        //如果当前时间在报名结束时间和活动开始时间之间,活动状态为活动待开始
        if(TimeUtils.isBelong(param.getSignEndTime(),param.getActivityStartTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_STAY_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+param.getActivityId());
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getActivityStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getActivityStartTime());
        }
        //如果当前时间在活动起始时间范围内,活动状态为活动进行中
        if(TimeUtils.isBelong(param.getActivityStartTime(),param.getActivityEndTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+param.getActivityId());
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+cerePlatformActivity.getActivityId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getActivityEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+cerePlatformActivity.getActivityId(),param.getActivityEndTime());
        }
        //如果当前时间大于活动结束时间,活动状态为活动已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getActivityEndTime())){
            cerePlatformActivity.setState(IntegerEnum.ACTIVITY_END.getCode());
        }
        cerePlatformActivityDAO.updateByPrimaryKeySelective(cerePlatformActivity);
        //查询是否有关联还未开始或者进行中的支付有礼活动
        List<CerePlatformPoliteActivity> list=cerePlatformPoliteActivityService.findByActivityId(cerePlatformActivity.getActivityId());
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(activity -> {
                activity.setActivityName(cerePlatformActivity.getActivityName());
                activity.setActivityType(cerePlatformActivity.getDiscountMode());
                activity.setThreshold(cerePlatformActivity.getThreshold());
                activity.setCouponContent(cerePlatformActivity.getCouponContent());
            });
            //批量更新支付有礼活动优惠券数据
            cerePlatformPoliteActivityService.updateBatch(list);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"平台优惠券活动管理","平台端操作","修改平台优惠券活动",String.valueOf(cerePlatformActivity.getActivityId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ActivityDelteParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.SHOP_SIGN_IN.getCode()+"-"+param.getActivityId());
        stringRedisService.delete(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+param.getActivityId());
        stringRedisService.delete(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+param.getActivityId());
        stringRedisService.delete(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+param.getActivityId());
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_SIGN_IN.getCode()+"-"+param.getActivityId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+param.getActivityId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+param.getActivityId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+param.getActivityId());
        //删除活动数据
        cerePlatformActivityDAO.deleteByPrimaryKey(param.getActivityId());
        //新增日志
        cerePlatformLogService.addLog(user,"平台优惠券活动管理","平台端操作","删除平台优惠券活动",String.valueOf(param.getActivityId()),time);
    }

    @Override
    public ActivityDetail getById(Long activityId) throws CoBusinessException {
        //查询活动数据
        ActivityDetail activity=cerePlatformActivityDAO.getById(activityId);
        return activity;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void end(ActivityEndParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.SHOP_SIGN_IN.getCode()+"-"+param.getActivityId());
        stringRedisService.delete(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+param.getActivityId());
        stringRedisService.delete(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+param.getActivityId());
        stringRedisService.delete(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+param.getActivityId());
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_SIGN_IN.getCode()+"-"+param.getActivityId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_ACTIVITY_STAY.getCode()+"-"+param.getActivityId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_ACTIVITY_IN.getCode()+"-"+param.getActivityId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_ACTIVITY_END.getCode()+"-"+param.getActivityId());
        //更新活动状态为活动已结束
        CerePlatformActivity activity=new CerePlatformActivity();
        activity.setActivityId(param.getActivityId());
        activity.setState(IntegerEnum.ACTIVITY_END.getCode());
        activity.setUpdateTime(time);
        cerePlatformActivityDAO.updateByPrimaryKeySelective(activity);
        //新增日志
        cerePlatformLogService.addLog(user,"平台优惠券活动管理","平台端操作","结束平台优惠券活动",String.valueOf(param.getActivityId()),time);
    }

    @Override
    public Page getAll(ActivityGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Activity> list=cerePlatformActivityDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(activity -> {
                //查询商品数量
                activity.setProductNumber(cerePlatformActivityDAO.findProductNumber(activity.getActivityId()));
            });
        }
        PageInfo<Activity> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getShops(ActivityGetShopsParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopActivity> list=cerePlatformActivityDAO.getShops(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(shopActivity -> {
                //查询审核次数
                shopActivity.setExamine(cerePlatformActivityDAO.findExamine(shopActivity.getShopId(),shopActivity.getActivityId()));
            });
        }
        PageInfo<ShopActivity> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getProducts(ActivityGetProductsParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopProduct> list=cerePlatformActivityDAO.getProducts(param);
        PageInfo<ShopProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<SignExamineLog> getExamines(ActivityGetExaminesParam param) throws CoBusinessException {
        String[] split = param.getOnly().split("-");
        if(!EmptyUtils.isEmpty(split)){
            if(split.length>2){
                List<SignExamineLog> list=cerePlatformActivityDAO.getExamines(param);
                if(!EmptyUtils.isEmpty(list)){
                    list.forEach(log -> {
                        //查询审核备注
                        CereActivitySign sign = cereActivitySignService.findBySignId(Long.parseLong(split[2]));
                        if(sign!=null){
                            log.setRemark(sign.getRemark());
                        }
                    });
                    return list;
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public BondCount getAdminBonds(ActivityGetAdminBondsParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ActivityBond> list=cerePlatformActivityDAO.getAdminBonds(param);
        PageInfo<ActivityBond> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        BondCount count=new BondCount();
        count.setPage(page);
        //查询总保证金
        count.setTotal(cerePlatformActivityDAO.getBondTotal());
        return count;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void examine(ActivityExamineParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        //审核商家报名
        String time =TimeUtils.yyMMddHHmmss();
        //查询报名数据
        CereActivitySign cereActivitySign=cereActivitySignService.findBySignId(param.getSignId());
        if(cereActivitySign!=null){
            String describe="";
            if(IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode().equals(param.getState())){
                if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(cereActivitySign.getSignType())){
                    //平台优惠券
                    describe="同意平台优惠券申请";
                }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(cereActivitySign.getSignType())){
                    //平台秒杀
                    describe="同意平台秒杀申请";
                }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(cereActivitySign.getSignType())){
                    //平台限时折扣
                    describe="同意平台限时折扣申请";
                }
            }else {
                if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(cereActivitySign.getSignType())){
                    //平台优惠券
                    describe="拒绝平台优惠券申请";
                }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(cereActivitySign.getSignType())){
                    //平台秒杀
                    describe="拒绝平台秒杀申请";
                }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(cereActivitySign.getSignType())){
                    //平台限时折扣
                    describe="拒绝平台限时折扣申请";
                }
                Integer ifBond=null;
                //查询活动是否需要保证金
                if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(cereActivitySign.getSignType())){
                    //平台优惠券
                    ifBond=cerePlatformActivityDAO.findIfBondBySignId(param.getSignId());
                }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(cereActivitySign.getSignType())){
                    //平台秒杀
                    ifBond=cerePlatformActivityDAO.findSeckillIfBondBySignId(param.getSignId());
                }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(cereActivitySign.getSignType())){
                    //平台限时折扣
                    ifBond=cerePlatformActivityDAO.findDiscountIfBondBySignId(param.getSignId());
                }
                if(IntegerEnum.YES.getCode().equals(ifBond)){
                    //需要保证金
                    //新增延时任务3天后自动退保证金
                    stringRedisService.set(StringEnum.THREE_DAY_REFUND_BOND+"-"+param.getSignId()+"-"+cereActivitySign.getSignType(),1,
                            TimeUtils.getCountDownByTime(time,TimeUtils.getMoreDayAfter(time,3)));
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.THREE_DAY_REFUND_BOND+"-"+param.getSignId()+"-"+cereActivitySign.getSignType(),
                            TimeUtils.getMoreDayAfter(time,3));
                }
            }
            cereActivitySign.setSignId(param.getSignId());
            cereActivitySign.setState(param.getState());
            cereActivitySign.setRemark(param.getRemark());
            cereActivitySignService.update(cereActivitySign);
            //新增日志
            cerePlatformLogService.addLog(user,"营销活动管理","平台端操作",describe,
                    cereActivitySign.getShopId()+"-"+cereActivitySign.getActivityId()+"-"+cereActivitySign.getSignId(),time);
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void liquidation(ActivityLiquidationParam param, CerePlatformUser user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        //清退商家,清空商品数据
        cereSignProductService.deleteById(param.getSignId());
        //新增日志
        cerePlatformLogService.addLog(user,"营销活动管理","平台端操作","清退商家",String.valueOf(param.getSignId()),time);
    }

    @Override
    public ActivityData getActivitys(ActivityGetDatasParam param) throws CoBusinessException {
        //查询活动开始结束时间
        CerePlatformActivity activity=cerePlatformActivityDAO.findById(param.getActivityId());
        if(activity!=null){
            //查询统计数据
            ActivityData data=cerePlatformActivityDAO.findActivityData(activity);
            if(data!=null){
                //查询参与商家数
                data.setBusiness(cerePlatformActivityDAO.findBusiness(activity));
                //查询参与商品总数
                data.setProducts(cerePlatformActivityDAO.findProducts(activity));
                //查询分布情况
                List<Proportion> cities=cerePlatformActivityDAO.findCities(activity);
                if(!EmptyUtils.isEmpty(cities)){
                    cities.forEach(city -> {
                        //截取省份
                        if(city != null && city.getName() != null && city.getName().contains("-")){
                            city.setName(city.getName().split("-")[0]);
                        }
                    });
                }
                data.setCities(cities);
                //查询前五省份访问占比
                data=setCityPeople(activity,data);
                //查询新老访客占比
                data=setNewOlds(activity,data);
                //查询终端访客占比
                data=setSystems(activity,data);
                //查询系统访客占比
                data=setTerminals(activity,data);
                //查询趋势图
                data=setTrends(activity,data);
                //查询商家成交排行榜
                data.setShopRankings(cerePlatformActivityDAO.findShopRankings(activity));
                //查询畅销商品排行榜
                data.setProductRankings(cerePlatformActivityDAO.findProductRankings(activity));
                //查询销售类别分布
                data=setClassify(activity,data);
                //查询商家数据明细
                PageHelper.startPage(param.getPage(),param.getPageSize());
                List<ShopDetail> list=cerePlatformActivityDAO.findShops(param);
                data=setShopDetails(list,activity,data);
                return data;
            }
        }
        return null;
    }

    @Override
    public List<ShopDetail> findExcel(ActivityGetDatasParam param) throws CoBusinessException{
        //查询活动开始结束时间
        CerePlatformActivity activity=cerePlatformActivityDAO.findById(param.getActivityId());
        List<ShopDetail> shops = cerePlatformActivityDAO.findShops(param);
        setShopDetails(shops,activity,null);
        return shops;
    }

    private ActivityData setShopDetails(List<ShopDetail> list, CerePlatformActivity activity, ActivityData data) {
        //遍历店铺设置明细数据
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(a -> {
                //查询参与商品数
                a.setProducts(cerePlatformActivityDAO.findShopProducts(a.getShopId(),activity.getActivityId(),
                        activity.getActivityStartTime(),activity.getActivityEndTime()));
                //查询访客数
                a.setPersons(cerePlatformActivityDAO.findShopPersons(a.getShopId(),activity.getActivityId(),
                        activity.getActivityStartTime(),activity.getActivityEndTime()));
                //查询订单数
                a.setOrders(cerePlatformActivityDAO.findShopOrders(a.getShopId(),activity.getActivityId(),
                        activity.getActivityStartTime(),activity.getActivityEndTime()));
                //查询成交客户数
                a.setCustomers(cerePlatformActivityDAO.findShopCustomers(a.getShopId(),activity.getActivityId(),
                        activity.getActivityStartTime(),activity.getActivityEndTime()));
                //查询成交总额
                a.setTotal(cerePlatformActivityDAO.findShopTotal(a.getShopId(),activity.getActivityId(),
                        activity.getActivityStartTime(),activity.getActivityEndTime()));
                //查询客单价(成交总额/订单数)
                if(!EmptyUtils.isEmptyBigdecimal(a.getTotal())&&!EmptyUtils.isEmpty(a.getOrders())){
                    a.setPrice(a.getTotal().divide(new BigDecimal(a.getOrders()),2,BigDecimal.ROUND_HALF_UP));
                }
            });
            PageInfo<ShopDetail> pageInfo=new PageInfo<>(list);
            Page<ShopDetail> page=new Page<>(pageInfo.getList(),pageInfo.getTotal());
            if(data!=null){
                data.setPage(page);
            }
        }
        return data;
    }

    private ActivityData setClassify(CerePlatformActivity activity, ActivityData data) {
        //查询所有一级分类
        List<CereProductClassify> classifies=cerePlatformActivityDAO.findClassifies();
        if(!EmptyUtils.isEmpty(classifies)){
            BigDecimal total=new BigDecimal(0);
            BigDecimal hundred=new BigDecimal(100);
            //查询分类所属商品销售额
            List<ActivityClassify> collect = classifies.stream()
                    .map(a -> {
                        ActivityClassify activityClassify = new ActivityClassify();
                        activityClassify.setClassifyId(a.getClassifyId());
                        activityClassify.setName(a.getClassifyName());
                        return activityClassify;
                    })
                    //设置销售额
                    .peek(a -> a.setValue(cerePlatformActivityDAO.findTotalByClassify(a.getClassifyId(),
                            activity.getActivityId(), activity.getActivityStartTime(), activity.getActivityEndTime())))
                    .collect(Collectors.toList());
            if(!EmptyUtils.isEmpty(collect)){
                for (ActivityClassify activityClassify :collect) {
                    total=total.add(activityClassify.getValue());
                }
                data.setClassifies(collect);
            }
        }
        return data;
    }

    private ActivityData setTrends(CerePlatformActivity activity, ActivityData data) {
        //获取活动时间内的每日时间
        List<String> dates = TimeUtils.findDaysStr(activity.getActivityStartTime(), activity.getActivityEndTime());
        List<BigDecimal> moneys=new ArrayList<>();
        List<Integer> totals=new ArrayList<>();
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            dates.forEach(date -> {
                //查询当天销售额
                moneys.add(cerePlatformActivityDAO.findTrendMoney(activity.getActivityId(), date));
                //查询当天访问量
                totals.add(cerePlatformActivityDAO.findTrendTotal(activity.getActivityId(), date));
            });
        }
        Trend trend=new Trend();
        trend.setTimes(dates);
        trend.setMoneys(moneys);
        trend.setTotals(totals);
        data.setTrend(trend);
        return data;
    }

    private ActivityData setTerminals(CerePlatformActivity activity, ActivityData data) {
        BigDecimal count=new BigDecimal(0);
        BigDecimal hundred=new BigDecimal(100);
        List<Proportion> terminals=new ArrayList<>();
        //查询操作系统Android访问数据
        Proportion android =cerePlatformActivityDAO.findAdroid(activity);
        //查询操作系统IOS访问数据
        Proportion ios=cerePlatformActivityDAO.findIos(activity);
        count=count.add(android.getValue()).add(ios.getValue());
        if(!EmptyUtils.isEmptyBigdecimal(android.getValue())&&!EmptyUtils.isEmptyBigdecimal(count)){
            android.setValue(android.getValue().divide(count,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
        }
        if(!EmptyUtils.isEmptyBigdecimal(ios.getValue())&&!EmptyUtils.isEmptyBigdecimal(count)){
            ios.setValue(ios.getValue().divide(count,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
        }
        terminals.add(android);
        terminals.add(ios);
        data.setTerminals(terminals);
        return data;
    }

    private ActivityData setSystems(CerePlatformActivity activity, ActivityData data) {
        BigDecimal count=new BigDecimal(0);
        BigDecimal hundred=new BigDecimal(100);
        List<Proportion> systems=new ArrayList<>();
        //查询终端APP访问数据
        Proportion app=cerePlatformActivityDAO.findAPP(activity);
        //查询终端小程序访问数据
        Proportion applets=cerePlatformActivityDAO.findApplets(activity);
        count=count.add(app.getValue()).add(applets.getValue());
        if(!EmptyUtils.isEmptyBigdecimal(app.getValue())&&!EmptyUtils.isEmptyBigdecimal(count)){
            app.setValue(app.getValue().divide(count,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
        }
        if(!EmptyUtils.isEmptyBigdecimal(applets.getValue())&&!EmptyUtils.isEmptyBigdecimal(count)){
            applets.setValue(applets.getValue().divide(count,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
        }
        systems.add(app);
        systems.add(applets);
        data.setTerminals(systems);
        return data;
    }

    private ActivityData setNewOlds(CerePlatformActivity activity, ActivityData data) {
        BigDecimal count=new BigDecimal(0);
        BigDecimal hundred=new BigDecimal(100);
        List<Proportion> newOlds=new ArrayList<>();
        //查询新客户访问数据
        Proportion news=cerePlatformActivityDAO.findNews(activity);
        //查询老客户访问数据
        Proportion olds=cerePlatformActivityDAO.findOlds(activity);
        count=count.add(news.getValue()).add(olds.getValue());
        if(!EmptyUtils.isEmptyBigdecimal(news.getValue())&&!EmptyUtils.isEmptyBigdecimal(count)){
            news.setValue(news.getValue().divide(count,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
        }
        if(!EmptyUtils.isEmptyBigdecimal(olds.getValue())&&!EmptyUtils.isEmptyBigdecimal(count)){
            olds.setValue(olds.getValue().divide(count,2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
        }
        newOlds.add(news);
        newOlds.add(olds);
        data.setNewOlds(newOlds);
        return data;
    }

    private ActivityData setCityPeople(CerePlatformActivity activity, ActivityData data) {
        BigDecimal count=new BigDecimal(0);
        BigDecimal hundred=new BigDecimal(100);
        //查询前五省份占比数据
        List<Proportion> cities=cerePlatformActivityDAO.findCityPeople(activity);
        if(!EmptyUtils.isEmpty(cities)){
            for (Proportion proportion:cities) {
                count=count.add(proportion.getValue());
            }
            //遍历计算占比
            BigDecimal finalCount = count;
            cities.stream()
                    .peek(a -> {
                        a.getValue().divide(finalCount,2,BigDecimal.ROUND_HALF_UP).multiply(hundred);
                    });
            data.setCityPeople(cities);
        }
        return data;
    }

//    @Override
//    public ActivityData getTest(ActivityGetDatasParam param) throws CoBusinessException {
//        ActivityData data=new ActivityData();
//        data.setUsers(10);
//        data.setMoney(new BigDecimal(5213));
//        data.setBusiness(23);
//        data.setOrders(32);
//        data.setClassifyMoney(new BigDecimal(231));
//        data.setProducts(132);
//        List<Proportion> cities=new ArrayList<>();
//        cities.add(new Proportion("湖北省",new BigDecimal(3)));
//        cities.add(new Proportion("北京市",new BigDecimal(13)));
//        cities.add(new Proportion("天津市",new BigDecimal(33)));
//        cities.add(new Proportion("广东省",new BigDecimal(43)));
//        cities.add(new Proportion("江苏省",new BigDecimal(53)));
//        data.setCities(cities);
//        List<Proportion> cityPeople=new ArrayList<>();
//        cityPeople.add(new Proportion("湖北省-武汉市-武昌区",new BigDecimal(13)));
//        cityPeople.add(new Proportion("北京-北京市-东城区",new BigDecimal(5)));
//        cityPeople.add(new Proportion("天津-天津市-和平区",new BigDecimal(12)));
//        cityPeople.add(new Proportion("广东省-东莞市-东莞区",new BigDecimal(40)));
//        cityPeople.add(new Proportion("江苏省-苏州市-苏州区",new BigDecimal(30)));
//        data.setCityPeople(cityPeople);
//        List<Proportion> newOlds=new ArrayList<>();
//        newOlds.add(new Proportion("新客户",new BigDecimal(30)));
//        newOlds.add(new Proportion("老客户",new BigDecimal(70)));
//        data.setNewOlds(newOlds);
//        List<Proportion> terminals=new ArrayList<>();
//        terminals.add(new Proportion("APP",new BigDecimal(30)));
//        terminals.add(new Proportion("小程序",new BigDecimal(70)));
//        data.setTerminals(terminals);
//        List<Proportion> systems=new ArrayList<>();
//        systems.add(new Proportion("Android",new BigDecimal(30)));
//        systems.add(new Proportion("IOS",new BigDecimal(70)));
//        data.setSystems(systems);
//        Trend trend=new Trend();
//        List<String> times=new ArrayList<>();
//        times.add("2020-11-01");
//        times.add("2020-11-02");
//        times.add("2020-11-03");
//        times.add("2020-11-04");
//        times.add("2020-11-05");
//        times.add("2020-11-06");
//        times.add("2020-11-07");
//        times.add("2020-11-08");
//        times.add("2020-11-09");
//        times.add("2020-11-10");
//        List<BigDecimal> moneys=new ArrayList<>();
//        moneys.add(new BigDecimal(100));
//        moneys.add(new BigDecimal(123));
//        moneys.add(new BigDecimal(421));
//        moneys.add(new BigDecimal(1231));
//        moneys.add(new BigDecimal(100));
//        moneys.add(new BigDecimal(123));
//        moneys.add(new BigDecimal(323));
//        moneys.add(new BigDecimal(123));
//        moneys.add(new BigDecimal(123));
//        moneys.add(new BigDecimal(100));
//        List<Integer> totals=new ArrayList<>();
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        totals.add(20);
//        trend.setTimes(times);
//        trend.setMoneys(moneys);
//        trend.setTotals(totals);
//        data.setTrend(trend);
//        List<ShopRanking> shopRankings=new ArrayList<>();
//        shopRankings.add(new ShopRanking("小李子牛肉店",new BigDecimal(531)));
//        shopRankings.add(new ShopRanking("老王店铺",new BigDecimal(3213)));
//        shopRankings.add(new ShopRanking("小王店铺",new BigDecimal(5431)));
//        shopRankings.add(new ShopRanking("阿萨德",new BigDecimal(543)));
//        shopRankings.add(new ShopRanking("暗粉色的感受",new BigDecimal(123)));
//        shopRankings.add(new ShopRanking("撒打算",new BigDecimal(4231)));
//        shopRankings.add(new ShopRanking("奥术大师多",new BigDecimal(12313)));
//        shopRankings.add(new ShopRanking("奥术大师",new BigDecimal(321)));
//        shopRankings.add(new ShopRanking("大风歌",new BigDecimal(321)));
//        shopRankings.add(new ShopRanking("梵蒂冈的",new BigDecimal(424)));
//        data.setShopRankings(shopRankings);
//        List<ProductRanking> productRankings=new ArrayList<>();
//        productRankings.add(new ProductRanking("牛肉丸",30));
//        productRankings.add(new ProductRanking("牛腱子",42));
//        productRankings.add(new ProductRanking("雪花肥牛",534));
//        productRankings.add(new ProductRanking("毛肚",234));
//        productRankings.add(new ProductRanking("鸡块",12));
//        productRankings.add(new ProductRanking("苹果手机",323));
//        productRankings.add(new ProductRanking("笔记本",123));
//        productRankings.add(new ProductRanking("书桌",4223));
//        productRankings.add(new ProductRanking("玩具车",31));
//        productRankings.add(new ProductRanking("电动车",422));
//        data.setProductRankings(productRankings);
//        List<ActivityClassify> classifies=new ArrayList<>();
//        classifies.add(new ActivityClassify("肉类",new BigDecimal(23123)));
//        classifies.add(new ActivityClassify("蔬菜类",new BigDecimal(321)));
//        classifies.add(new ActivityClassify("小零食",new BigDecimal(1235)));
//        classifies.add(new ActivityClassify("电器",new BigDecimal(5533)));
//        data.setClassifies(classifies);
//        List<ShopDetail> list=new ArrayList<>();
//        list.add(new ShopDetail("胜多负少","SJ1338",123,23,42,23,new BigDecimal(231),new BigDecimal(421)));
//        list.add(new ShopDetail("打发个梵蒂冈","SJ3229",123,23,42,23,new BigDecimal(231),new BigDecimal(421)));
//        list.add(new ShopDetail("阿达","SJ1311",123,23,42,23,new BigDecimal(231),new BigDecimal(421)));
//        list.add(new ShopDetail("碍事法师打发","SJ1332",123,23,42,23,new BigDecimal(231),new BigDecimal(421)));
//        PageInfo<ShopDetail> pageInfo=new PageInfo<>(list);
//        Page<ShopDetail> page=new Page<>(pageInfo.getList(),pageInfo.getTotal());
//        data.setPage(page);
//        return data;
//    }

    @Override
    public void updateActivity(CerePlatformActivity cerePlatformActivity) throws CoBusinessException {
        cerePlatformActivityDAO.updateByPrimaryKeySelective(cerePlatformActivity);
    }

    @Override
    public CereActivitySign checkSignError(CereActivitySign activitySign) {
        return cerePlatformActivityDAO.checkSignError(activitySign);
    }

    @Override
    public CereActivitySign findSign(String signId) {
        return cerePlatformActivityDAO.findSign(signId);
    }

    @Override
    public CerePayLog findBondPayLog(String formid) {
        return cerePlatformActivityDAO.findBondPayLog(formid);
    }

    @Override
    public List<Long> findSignIdsByShopIdAndActivityId(Long shopId, Long activityId,int signType) {
        return cerePlatformActivityDAO.findSignIdsByShopIdAndActivityId(shopId,activityId,signType);
    }

    @Override
    public List<CereActivitySign> findByActivity(Long activityId) {
        return cerePlatformActivityDAO.findByActivity(activityId);
    }

    @Override
    public List<CereActivitySign> findErrorSign(Long activityId) {
        return cerePlatformActivityDAO.findErrorSign(activityId);
    }

    @Override
    public Integer findIfBond(Long activityId) {
        return cerePlatformActivityDAO.findIfBond(activityId);
    }

    @Override
    public void updateSignStateError(List<CereActivitySign> signs) {
        cerePlatformActivityDAO.updateSignStateError(signs);
    }

    @Override
    public Page getCoupons(CanvasCouponParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CanvasCoupon> list=cerePlatformActivityDAO.getCoupons(param);
        if(!EmptyUtils.isEmpty(list)){
            //设置内容
            list.forEach(canvasCoupon -> {
                canvasCoupon.setContent("满"+canvasCoupon.getFullMoney().stripTrailingZeros().toPlainString()+"减"
                +canvasCoupon.getReduceMoney().stripTrailingZeros().toPlainString()+"元");
            });
        }
        PageInfo<CanvasCoupon> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CerePlatformActivity findById(Long activityId) {
        return cerePlatformActivityDAO.findById(activityId);
    }

    @Override
    public List<ShopGroupWorkUDetail> getGroupWorks(RenovationParam param) {
        List<ShopGroupWorkUDetail> list=cerePlatformActivityDAO.getGroupWorks(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(detail -> {
                //查询商品明细
                detail.setProducts(cerePlatformActivityDAO.findDistinctProducts(detail.getShopGroupWorkId()));
            });
        }
        return list;
    }

    @Override
    public List<ToolProduct> getGroupWorkProducts(RenovationParam param) throws CoBusinessException {
        return cerePlatformActivityDAO.getGroupWorkProducts(param);
    }
}
