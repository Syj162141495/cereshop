/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.scene.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.scene.CereShopSceneDAO;
import com.shop.cereshop.business.page.scene.SceneMember;
import com.shop.cereshop.business.page.scene.ShopScene;
import com.shop.cereshop.business.page.scene.ShopSceneDetail;
import com.shop.cereshop.business.page.tool.ShopCoupon;
import com.shop.cereshop.business.param.scene.SceneGetAllParam;
import com.shop.cereshop.business.param.scene.SceneGetByIdParam;
import com.shop.cereshop.business.param.scene.SceneSaveParam;
import com.shop.cereshop.business.param.scene.SceneUpdateParam;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.scene.CereShopSceneMemberCouponService;
import com.shop.cereshop.business.service.scene.CereShopSceneMemberService;
import com.shop.cereshop.business.service.scene.CereShopSceneService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.scene.CereShopScene;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMember;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMemberCoupon;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CereShopSceneServiceImpl implements CereShopSceneService {

    @Autowired
    private CereShopSceneDAO cereShopSceneDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereShopSceneMemberService cereShopSceneMemberService;

    @Autowired
    private CereShopSceneMemberCouponService cereShopSceneMemberCouponService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CerePlatformMemberLevelService cerePlatformMemberLevelService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(SceneSaveParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        //查询当前是否存在已启用状态的会员日营销和生日营销
        CereShopScene scene=cereShopSceneDAO.checkScene(null,param.getShopId());
        if(scene!=null){
            throw new CoBusinessException(CoReturnFormat.SCENE_ALREADY_MESSAGE);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CereShopScene cereShopScene=new CereShopScene();
        cereShopScene.setSceneName(param.getSceneName());
        cereShopScene.setShopId(param.getShopId());
        cereShopScene.setSceneRule(param.getSceneRule());
        cereShopScene.setSceneType(param.getSceneType());
        cereShopScene.setSceneTimeType(param.getSceneTimeType());
        cereShopScene.setStartTime(param.getStartTime());
        cereShopScene.setEndTime(param.getEndTime());
        cereShopScene.setSceneTime(param.getSceneTime());
        cereShopScene.setCreateTime(time);
        cereShopScene.setUpdateTime(time);
        //默认状态进行中
        cereShopScene.setState(IntegerEnum.SCEME_STATE_START.getCode());
        if(IntegerEnum.SCENE_TYPE_FESTIVAL.getCode().equals(param.getSceneType())){
            //如果是节日营销,校验起始时间是否存在交叉
            List<CereShopScene> list=cereShopSceneDAO.checkTime(param);
            if(!EmptyUtils.isEmpty(list)){
                throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
            }
            //如果当前时间小于开始时间,状态未开始
            if(TimeUtils.compareTo(param.getStartTime(),time)){
                cereShopScene.setState(IntegerEnum.SCEME_STATE_NOT.getCode());

            }
            //如果当前时间在起始时间范围内,状态进行中
            if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
                cereShopScene.setState(IntegerEnum.SCEME_STATE_START.getCode());
            }else {
                //状态已停止
                cereShopScene.setState(IntegerEnum.SCEME_STATE_STOP.getCode());
            }
            cereShopSceneDAO.insert(cereShopScene);
        }else {
            if(scene==null){
                //如果没有,状态为进行中
                cereShopScene.setState(IntegerEnum.SCEME_STATE_START.getCode());
            }
            cereShopSceneDAO.insert(cereShopScene);
        }
        if(IntegerEnum.SCENE_TYPE_FESTIVAL.getCode().equals(param.getSceneType())){
            if(TimeUtils.compareTo(param.getStartTime(),time)){
                //新增延时任务修改状态为进行中
                stringRedisService.set(StringEnum.SCENE_START.getCode()+"-"+cereShopScene.getSceneId(),1,TimeUtils.getCountDownByTime(time,param.getStartTime()));
                cereRedisKeyServcice.add(StringEnum.SCENE_START.getCode()+"-"+cereShopScene.getSceneId(),param.getStartTime());
            }
            if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
                //新增延时任务修改状态为已结束
                stringRedisService.set(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId(),1,TimeUtils.getCountDownByTime(time,param.getEndTime()));
                cereRedisKeyServcice.add(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId(),param.getEndTime());
            }
        }
        //新增会员等级数据
        if(!EmptyUtils.isEmpty(param.getMemberParams())){
            List<CereShopSceneMemberCoupon> coupons=new ArrayList<>();
            List<CereShopSceneMember> collect=null;
            if(IntegerEnum.SCEME_RULE_ALL.getCode().equals(param.getSceneRule())){
                //查询所有会员数据
                List<CerePlatformMemberLevel> memberLevels=cerePlatformMemberLevelService.findAll();
                if(!EmptyUtils.isEmpty(memberLevels)&&!EmptyUtils.isEmpty(param.getMemberParams())){
                        collect=memberLevels.stream().map(a -> {
                        CereShopSceneMember cereShopSceneMember = new CereShopSceneMember();
                        cereShopSceneMember.setSceneId(cereShopScene.getSceneId());
                        cereShopSceneMember.setMemberLevelId(a.getMemberLevelId());
                        cereShopSceneMember.setDiscount(param.getMemberParams().get(0).getDiscount());
                        cereShopSceneMember.setIfFreeShipping(param.getMemberParams().get(0).getIfFreeShipping());
                        if(!EmptyUtils.isEmpty(param.getMemberParams().get(0).getIds())){
                            param.getMemberParams().get(0).getIds().forEach(id -> {
                                CereShopSceneMemberCoupon cereShopSceneMemberCoupon=new CereShopSceneMemberCoupon();
                                cereShopSceneMemberCoupon.setSceneId(cereShopScene.getSceneId());
                                cereShopSceneMemberCoupon.setMemberLevelId(a.getMemberLevelId());
                                cereShopSceneMemberCoupon.setCouponId(id);
                                coupons.add(cereShopSceneMemberCoupon);
                            });
                        }
                        return cereShopSceneMember;
                    }).collect(Collectors.toList());
                }
            }else {
                    collect= param.getMemberParams().stream().map(a -> {
                    CereShopSceneMember cereShopSceneMember = new CereShopSceneMember();
                    cereShopSceneMember.setSceneId(cereShopScene.getSceneId());
                    cereShopSceneMember.setMemberLevelId(a.getMemberLevelId());
                    cereShopSceneMember.setDiscount(a.getDiscount());
                    cereShopSceneMember.setIfFreeShipping(a.getIfFreeShipping());
                    if(!EmptyUtils.isEmpty(a.getIds())){
                        a.getIds().forEach(id -> {
                            CereShopSceneMemberCoupon cereShopSceneMemberCoupon=new CereShopSceneMemberCoupon();
                            cereShopSceneMemberCoupon.setSceneId(cereShopScene.getSceneId());
                            cereShopSceneMemberCoupon.setMemberLevelId(a.getMemberLevelId());
                            cereShopSceneMemberCoupon.setCouponId(id);
                            coupons.add(cereShopSceneMemberCoupon);
                        });
                    }
                    return cereShopSceneMember;
                }).collect(Collectors.toList());
            }
            if(!EmptyUtils.isEmpty(collect)){
                //批量插入会员等级数据
                cereShopSceneMemberService.insertBatch(collect);
            }
            if(!EmptyUtils.isEmpty(coupons)){
                //批量插入会员等级优惠券数据
                cereShopSceneMemberCouponService.insertBatch(coupons);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"场景营销管理","商户端操作","添加场景营销",cereShopScene.getSceneId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(SceneUpdateParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        CereShopScene cereShopScene=new CereShopScene();
        cereShopScene.setSceneId(param.getSceneId());
        cereShopScene.setShopId(param.getShopId());
        cereShopScene.setSceneName(param.getSceneName());
        cereShopScene.setSceneRule(param.getSceneRule());
        cereShopScene.setSceneType(param.getSceneType());
        cereShopScene.setSceneTimeType(param.getSceneTimeType());
        cereShopScene.setStartTime(param.getStartTime());
        cereShopScene.setEndTime(param.getEndTime());
        cereShopScene.setSceneTime(param.getSceneTime());
        cereShopScene.setUpdateTime(time);
        //默认状态已停止
        cereShopScene.setState(IntegerEnum.SCEME_STATE_STOP.getCode());
        //清空延时任务
        stringRedisService.delete(StringEnum.SCENE_START.getCode()+"-"+cereShopScene.getSceneId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SCENE_START.getCode()+"-"+cereShopScene.getSceneId());
        stringRedisService.delete(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId());
        if(IntegerEnum.SCENE_TYPE_FESTIVAL.getCode().equals(param.getSceneType())){
            //如果是节日营销,校验起始时间是否存在交叉
            List<CereShopScene> list=cereShopSceneDAO.checkUpdateTime(param);
            if(!EmptyUtils.isEmpty(list)){
                throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
            }
            //如果当前时间小于开始时间,状态未开始
            if(TimeUtils.compareTo(param.getStartTime(),time)){
                cereShopScene.setState(IntegerEnum.SCEME_STATE_NOT.getCode());
            }
            //如果当前时间在起始时间范围内,状态进行中
            if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
                cereShopScene.setState(IntegerEnum.SCEME_STATE_START.getCode());
            }else {
                //状态已停止
                cereShopScene.setState(IntegerEnum.SCEME_STATE_STOP.getCode());
            }
            cereShopSceneDAO.updateByPrimaryKeySelective(cereShopScene);
        }else {
            //查询当前是否存在已启用状态的会员日营销和生日营销
            CereShopScene scene=cereShopSceneDAO.checkScene(cereShopScene.getSceneId(),param.getShopId());
            if(scene==null){
                //如果没有,状态为进行中
                cereShopScene.setState(IntegerEnum.SCEME_STATE_START.getCode());
            }
            cereShopSceneDAO.updateByPrimaryKeySelective(cereShopScene);
        }
        if(IntegerEnum.SCENE_TYPE_FESTIVAL.getCode().equals(param.getSceneType())){
            if(TimeUtils.compareTo(param.getStartTime(),time)){
                //新增延时任务修改状态为进行中
                stringRedisService.set(StringEnum.SCENE_START.getCode()+"-"+cereShopScene.getSceneId(),1,TimeUtils.getCountDownByTime(time,param.getStartTime()));
                cereRedisKeyServcice.add(StringEnum.SCENE_START.getCode()+"-"+cereShopScene.getSceneId(),param.getStartTime());
            }
            if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
                //新增延时任务修改状态为已结束
                stringRedisService.set(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId(),1,TimeUtils.getCountDownByTime(time,param.getEndTime()));
                cereRedisKeyServcice.add(StringEnum.SCENE_END.getCode()+"-"+cereShopScene.getSceneId(),param.getEndTime());
            }
        }
        //清空会员等级数据
        cereShopSceneMemberService.deleteBySceneId(cereShopScene.getSceneId());
        cereShopSceneMemberCouponService.deleteBySceneId(cereShopScene.getSceneId());
        //新增会员等级数据
        if(!EmptyUtils.isEmpty(param.getMemberParams())){
            List<CereShopSceneMemberCoupon> coupons=new ArrayList<>();
            List<CereShopSceneMember> collect=null;
            if(IntegerEnum.SCEME_RULE_ALL.getCode().equals(param.getSceneRule())){
                //查询所有会员数据
                List<CerePlatformMemberLevel> memberLevels=cerePlatformMemberLevelService.findAll();
                if(!EmptyUtils.isEmpty(memberLevels)&&!EmptyUtils.isEmpty(param.getMemberParams())){
                    collect=memberLevels.stream().map(a -> {
                        CereShopSceneMember cereShopSceneMember = new CereShopSceneMember();
                        cereShopSceneMember.setSceneId(cereShopScene.getSceneId());
                        cereShopSceneMember.setMemberLevelId(a.getMemberLevelId());
                        cereShopSceneMember.setDiscount(param.getMemberParams().get(0).getDiscount());
                        cereShopSceneMember.setIfFreeShipping(param.getMemberParams().get(0).getIfFreeShipping());
                        if(!EmptyUtils.isEmpty(param.getMemberParams().get(0).getIds())){
                            param.getMemberParams().get(0).getIds().forEach(id -> {
                                CereShopSceneMemberCoupon cereShopSceneMemberCoupon=new CereShopSceneMemberCoupon();
                                cereShopSceneMemberCoupon.setSceneId(cereShopScene.getSceneId());
                                cereShopSceneMemberCoupon.setMemberLevelId(a.getMemberLevelId());
                                cereShopSceneMemberCoupon.setCouponId(id);
                                coupons.add(cereShopSceneMemberCoupon);
                            });
                        }
                        return cereShopSceneMember;
                    }).collect(Collectors.toList());
                }
            }else {
                collect= param.getMemberParams().stream().map(a -> {
                    CereShopSceneMember cereShopSceneMember = new CereShopSceneMember();
                    cereShopSceneMember.setSceneId(cereShopScene.getSceneId());
                    cereShopSceneMember.setMemberLevelId(a.getMemberLevelId());
                    cereShopSceneMember.setDiscount(a.getDiscount());
                    cereShopSceneMember.setIfFreeShipping(a.getIfFreeShipping());
                    if(!EmptyUtils.isEmpty(a.getIds())){
                        a.getIds().forEach(id -> {
                            CereShopSceneMemberCoupon cereShopSceneMemberCoupon=new CereShopSceneMemberCoupon();
                            cereShopSceneMemberCoupon.setSceneId(cereShopScene.getSceneId());
                            cereShopSceneMemberCoupon.setMemberLevelId(a.getMemberLevelId());
                            cereShopSceneMemberCoupon.setCouponId(id);
                            coupons.add(cereShopSceneMemberCoupon);
                        });
                    }
                    return cereShopSceneMember;
                }).collect(Collectors.toList());
            }
            if(!EmptyUtils.isEmpty(collect)){
                //批量插入会员等级数据
                cereShopSceneMemberService.insertBatch(collect);
            }
            if(!EmptyUtils.isEmpty(coupons)){
                //批量插入会员等级优惠券数据
                cereShopSceneMemberCouponService.insertBatch(coupons);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"场景营销管理","商户端操作","编辑场景营销",cereShopScene.getSceneId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(SceneGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //清空延时任务
        stringRedisService.delete(StringEnum.SCENE_START.getCode()+"-"+param.getSceneId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SCENE_START.getCode()+"-"+param.getSceneId());
        stringRedisService.delete(StringEnum.SCENE_END.getCode()+"-"+param.getSceneId());
        cereRedisKeyServcice.deleteByKey(StringEnum.SCENE_END.getCode()+"-"+param.getSceneId());
        cereShopSceneDAO.deleteByPrimaryKey(param.getSceneId());
        //新增日志
        cerePlatformLogService.addLog(user,"场景营销管理","商户端操作","删除场景营销",param.getSceneId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void start(SceneGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time =TimeUtils.yyMMddHHmmss();
        //查询当前是否存在已启用状态的会员日营销和生日营销
        CereShopScene scene=cereShopSceneDAO.checkScene(param.getSceneId(),param.getShopId());
        if(scene!=null){
            throw new CoBusinessException(CoReturnFormat.HAVE_OTHER_SCENE);
        }
        CereShopScene cereShopScene=cereShopSceneDAO.selectByPrimaryKey(param.getSceneId());
        if(cereShopScene!=null){
            //校验是否过期
            if(TimeUtils.compareTo(time,cereShopScene.getEndTime())){
                throw new CoBusinessException(CoReturnFormat.SCENE_NOT_START);
            }
            cereShopScene.setSceneId(param.getSceneId());
            cereShopScene.setState(IntegerEnum.SCEME_STATE_START.getCode());
            cereShopScene.setUpdateTime(time);
            cereShopSceneDAO.updateByPrimaryKeySelective(cereShopScene);
            //新增日志
            cerePlatformLogService.addLog(user,"场景营销管理","商户端操作","启动场景营销",param.getSceneId(),time);
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(SceneGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        CereShopScene cereShopScene=new CereShopScene();
        cereShopScene.setSceneId(param.getSceneId());
        cereShopScene.setState(IntegerEnum.SCEME_STATE_STOP.getCode());
        cereShopScene.setUpdateTime(time);
        cereShopSceneDAO.updateByPrimaryKeySelective(cereShopScene);
        //新增日志
        cerePlatformLogService.addLog(user,"场景营销管理","商户端操作","停止场景营销",param.getSceneId(),time);
    }

    @Override
    public ShopSceneDetail getById(Long sceneId) throws CoBusinessException {
        ShopSceneDetail detail=cereShopSceneDAO.getById(sceneId);
        if(detail!=null){
            //查询会员等级数据
            List<SceneMember> members=cereShopSceneMemberService.findBySceneId(sceneId);
            detail.setMembers(members);
            if(!EmptyUtils.isEmpty(members)){
                members.forEach(member -> {
                    //设置优惠券明细
                    List<ShopCoupon> coupons = cereShopSceneMemberCouponService.findCoupons(member.getMemberLevelId(), sceneId);
                    if(!EmptyUtils.isEmpty(coupons)){
                        List<Long> ids = coupons.stream().map(ShopCoupon::getShopCouponId).collect(Collectors.toList());
                        member.setIds(ids);
                        member.setCoupons(coupons);
                    }
                });
            }
        }
        return detail;
    }

    @Override
    public Page getAll(SceneGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopScene> list=cereShopSceneDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(scene -> {
                if(IntegerEnum.SCENE_TYPE_MEMBER.getCode().equals(scene.getSceneType())){
                    //如果是会员日营销
                    String[] split = scene.getSceneTime().split("-");
                    if(IntegerEnum.SCENE_TIME_MONTH.getCode().equals(scene.getSceneTimeType())){
                        if(split.length>1){
                            //每月
                            scene.setTime("每月"+split[0]+"日-"+split[1]+"日");
                        }
                    }else if(IntegerEnum.SCENE_TIME_WEEK.getCode().equals(scene.getSceneTimeType())){
                        //每周
                        String time="每";
                        for (int i = 0; i < split.length; i++) {
                            if(i==0&&!split[i].equals("0")){
                                time+="周一、";
                            }
                            if(i==1&&!split[i].equals("0")){
                                time+="周二、";
                            }
                            if(i==2&&!split[i].equals("0")){
                                time+="周三、";
                            }
                            if(i==3&&!split[i].equals("0")){
                                time+="周四、";
                            }
                            if(i==4&&!split[i].equals("0")){
                                time+="周五、";
                            }
                            if(i==5&&!split[i].equals("0")){
                                time+="周六、";
                            }
                            if(i==6&&!split[i].equals("0")){
                                time+="周天";
                            }
                        }
                        scene.setTime(time);
                    }else {
                        //每日
                        scene.setTime(scene.getSceneTime());
                    }
                }else if(IntegerEnum.SCENE_TYPE_BIRTHDAY.getCode().equals(scene.getSceneType())){
                    //如果是生日营销
                    if(IntegerEnum.SCENE_TIME_BIRTHDAY.getCode().equals(scene.getSceneTimeType())){
                        scene.setTime("用户生日当天");
                    }else if(IntegerEnum.SCENE_TIME_BIRTHDAY_WEEK.getCode().equals(scene.getSceneTimeType())){
                        scene.setTime("用户生日当周");
                    }if(IntegerEnum.SCENE_TIME_BIRTHDAY_MONTH.getCode().equals(scene.getSceneTimeType())){
                        scene.setTime("用户生日当月");
                    }
                }
            });
        }
        PageInfo<ShopScene> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CereShopScene findById(Long sceneId) {
        return cereShopSceneDAO.selectByPrimaryKey(sceneId);
    }

    @Override
    public void updateState(CereShopScene cereShopScene) throws CoBusinessException {
        cereShopSceneDAO.updateByPrimaryKeySelective(cereShopScene);
    }

    @Override
    public List<CerePlatformMemberLevel> getMemberLevels() throws CoBusinessException {
        return cereShopSceneDAO.getMemberLevels();
    }

    @Override
    public List<CereShopScene> findAll() {
        return cereShopSceneDAO.findAll();
    }
}
