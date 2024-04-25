/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.platformtool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.platformtool.CerePlatformDiscountDAO;
import com.shop.cereshop.admin.page.canvas.CanvasPlatformDiscount;
import com.shop.cereshop.admin.page.canvas.ToolProduct;
import com.shop.cereshop.admin.page.discount.Discount;
import com.shop.cereshop.admin.page.discount.DiscountData;
import com.shop.cereshop.admin.page.discount.DiscountShop;
import com.shop.cereshop.admin.page.discount.DiscountShopProduct;
import com.shop.cereshop.admin.page.seckill.Seckill;
import com.shop.cereshop.admin.page.seckill.SeckillData;
import com.shop.cereshop.admin.page.seckill.SeckillShop;
import com.shop.cereshop.admin.page.seckill.SeckillShopProduct;
import com.shop.cereshop.admin.param.canvas.RenovationParam;
import com.shop.cereshop.admin.param.discount.*;
import com.shop.cereshop.admin.param.seckill.*;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.admin.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.ShopDataDetail;
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
import java.util.List;

@Service
public class CerePlatformDiscountServiceImpl implements CerePlatformDiscountService {

    @Autowired
    private CerePlatformDiscountDAO cerePlatformDiscountDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private StringRedisService stringRedisService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(DiscountSaveParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        //如果当前时间大于活动结束时间,活动状态为活动已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getEndTime())){
            throw new CoBusinessException(CoReturnFormat.NOW_DATE_LESS_END_TIME);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformDiscount cerePlatformDiscount=new CerePlatformDiscount();
        cerePlatformDiscount.setDiscountName(param.getDiscountName());
        cerePlatformDiscount.setRemark(param.getRemark());
        cerePlatformDiscount.setSignStartTime(param.getSignStartTime());
        cerePlatformDiscount.setSignEndTime(param.getSignEndTime());
        cerePlatformDiscount.setStartTime(param.getStartTime());
        cerePlatformDiscount.setEndTime(param.getEndTime());
        cerePlatformDiscount.setIfBond(param.getIfBond());
        cerePlatformDiscount.setBondMoney(param.getBondMoney());
        cerePlatformDiscount.setDiscount(param.getDiscount());
        cerePlatformDiscount.setIfLimit(param.getIfLimit());
        cerePlatformDiscount.setLimitNumber(param.getLimitNumber());
        cerePlatformDiscount.setIfAdd(param.getIfAdd());
        cerePlatformDiscount.setCreateTime(time);
        cerePlatformDiscount.setUpdateTime(time);
        //校验活动开始时间必须大于报名结束时间
        if(!TimeUtils.compareTo(param.getStartTime(),param.getSignEndTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果报名开始时间大于当前时间,活动状态为报名未开始
        if(TimeUtils.compareTo(param.getSignStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_NOT_START.getCode());
        }
        //如果当前时间在报名起始时间范围内,活动状态为报名进行中
        if(TimeUtils.isBelong(param.getSignStartTime(),param.getSignEndTime())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_SIGN_ON.getCode());
        }
        //如果当前时间在报名结束时间和活动开始时间之间,活动状态为活动待开始
        if(TimeUtils.isBelong(param.getSignEndTime(),param.getStartTime())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_STAY_START.getCode());
        }
        //如果当前时间在活动起始时间范围内,活动状态为活动进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_START.getCode());
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CerePlatformDiscount> discounts=cerePlatformDiscountDAO.checkTime(param);
        if(!EmptyUtils.isEmpty(discounts)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        cerePlatformDiscountDAO.insert(cerePlatformDiscount);
        //新增redis任务
        if(cerePlatformDiscount.getState().equals(IntegerEnum.ACTIVITY_NOT_START.getCode())){
            //过期时间为报名开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getSignStartTime());
        }else if(cerePlatformDiscount.getState().equals(IntegerEnum.ACTIVITY_SIGN_ON.getCode())){
            //过期时间为报名结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getSignEndTime());
        }else if(cerePlatformDiscount.getState().equals(IntegerEnum.ACTIVITY_STAY_START.getCode())){
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getStartTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getStartTime());
        }else if(cerePlatformDiscount.getState().equals(IntegerEnum.ACTIVITY_START.getCode())){
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getEndTime()));
            //新增redis过期key记录
            cereRedisKeyServcice.add(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getEndTime());
        }
        //新增日志
        cerePlatformLogService.addLog(user,"平台限时折扣活动管理","平台端操作","添加平台限时折扣活动",String.valueOf(cerePlatformDiscount.getDiscountId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(DiscountUpdateParam param, CerePlatformUser user) throws CoBusinessException, Exception {
        //如果当前时间大于活动结束时间,活动状态为活动已结束
        if(TimeUtils.compareTo(TimeUtils.yyMMddHHmmss(),param.getEndTime())){
            throw new CoBusinessException(CoReturnFormat.NOW_DATE_LESS_END_TIME);
        }
        //校验时间交叉,通过开始时间和结束时间查询是否在其他未结束的活动开始结束时间范围内
        List<CerePlatformDiscount> seckills=cerePlatformDiscountDAO.checkUpdateTime(param);
        if(!EmptyUtils.isEmpty(seckills)){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_CROSS);
        }
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformDiscount cerePlatformDiscount=new CerePlatformDiscount();
        cerePlatformDiscount.setDiscountId(param.getDiscountId());
        cerePlatformDiscount.setDiscountName(param.getDiscountName());
        cerePlatformDiscount.setRemark(param.getRemark());
        cerePlatformDiscount.setSignStartTime(param.getSignStartTime());
        cerePlatformDiscount.setSignEndTime(param.getSignEndTime());
        cerePlatformDiscount.setStartTime(param.getStartTime());
        cerePlatformDiscount.setEndTime(param.getEndTime());
        cerePlatformDiscount.setIfBond(param.getIfBond());
        cerePlatformDiscount.setBondMoney(param.getBondMoney());
        cerePlatformDiscount.setDiscount(param.getDiscount());
        cerePlatformDiscount.setIfLimit(param.getIfLimit());
        cerePlatformDiscount.setLimitNumber(param.getLimitNumber());
        cerePlatformDiscount.setIfAdd(param.getIfAdd());
        cerePlatformDiscount.setUpdateTime(time);
        //校验活动开始时间必须大于报名结束时间
        if(!TimeUtils.compareTo(param.getStartTime(),param.getSignEndTime())){
            throw new CoBusinessException(CoReturnFormat.ACTIVITY_TIME_ERROR);
        }
        //如果报名开始时间大于当前时间,活动状态为报名未开始
        if(TimeUtils.compareTo(param.getSignStartTime(),TimeUtils.yyMMddHHmmss())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_NOT_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+param.getDiscountId());
            //过期时间为报名开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getSignStartTime());
        }
        //如果当前时间在报名起始时间范围内,活动状态为报名进行中
        if(TimeUtils.isBelong(param.getSignStartTime(),param.getSignEndTime())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_SIGN_ON.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+param.getDiscountId());
            //过期时间为报名结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getSignEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getSignEndTime());
        }
        //如果当前时间在报名结束时间和活动开始时间之间,活动状态为活动待开始
        if(TimeUtils.isBelong(param.getSignEndTime(),param.getStartTime())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_STAY_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+param.getDiscountId());
            //过期时间为活动开始时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getStartTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getStartTime());
        }
        //如果当前时间在活动起始时间范围内,活动状态为活动进行中
        if(TimeUtils.isBelong(param.getStartTime(),param.getEndTime())){
            cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_START.getCode());
            //清除redis任务
            stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+param.getDiscountId());
            //过期时间为活动结束时间-当前时间
            stringRedisService.set(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+cerePlatformDiscount.getDiscountId(),1,
                    TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),param.getEndTime()));
            //更新redis过期key记录
            cereRedisKeyServcice.updateByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+cerePlatformDiscount.getDiscountId(),param.getEndTime());
        }
        cerePlatformDiscountDAO.updateByPrimaryKeySelective(cerePlatformDiscount);
        //新增日志
        cerePlatformLogService.addLog(user,"平台限时折扣活动管理","平台端操作","修改平台限时折扣活动",String.valueOf(cerePlatformDiscount.getDiscountId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(Long discountId, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+discountId);
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+discountId);
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+discountId);
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+discountId);
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+discountId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+discountId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+discountId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+discountId);
        //删除活动数据
        cerePlatformDiscountDAO.deleteByPrimaryKey(discountId);
        //新增日志
        cerePlatformLogService.addLog(user,"平台限时折扣活动管理","平台端操作","删除平台限时折扣活动",String.valueOf(discountId),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void stop(Long discountId, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //清空redis任务
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+discountId);
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+discountId);
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+discountId);
        stringRedisService.delete(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+discountId);
        //清除redis延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_SIGN_IN.getCode()+"-"+discountId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_STAY.getCode()+"-"+discountId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_IN.getCode()+"-"+discountId);
        cereRedisKeyServcice.deleteByKey(StringEnum.PLATFORM_DISCOUNT_ACTIVITY_END.getCode()+"-"+discountId);
        //更新活动状态为活动已结束
        CerePlatformDiscount cerePlatformDiscount=new CerePlatformDiscount();
        cerePlatformDiscount.setDiscountId(discountId);
        cerePlatformDiscount.setState(IntegerEnum.ACTIVITY_END.getCode());
        cerePlatformDiscount.setUpdateTime(time);
        cerePlatformDiscountDAO.updateByPrimaryKeySelective(cerePlatformDiscount);
        //新增日志
        cerePlatformLogService.addLog(user,"平台限时折扣活动管理","平台端操作","停止平台限时折扣活动",String.valueOf(discountId),time);
    }

    @Override
    public CerePlatformDiscount getById(Long discountId) throws CoBusinessException {
        return cerePlatformDiscountDAO.selectByPrimaryKey(discountId);
    }

    @Override
    public DiscountData getData(DiscountGetByIdParam param) throws CoBusinessException {
        Long discountId = param.getDiscountId();
        DiscountData data=new DiscountData();
        //设置参与商家数
        data.setShops(cerePlatformDiscountDAO.findShops(discountId));
        //设置参与商品数
        data.setProducts(cerePlatformDiscountDAO.findProducts(discountId,null));
        //设置活动成交金额
        data.setTotal(cerePlatformDiscountDAO.findTotal(discountId,null));
        //设置支付买家数
        data.setPays(cerePlatformDiscountDAO.findPays(discountId));
        //设置支付转化率

        //设置支付订单数
        data.setOrders(cerePlatformDiscountDAO.findOrders(discountId,null));
        //设置浏览量
        data.setVisit(cerePlatformDiscountDAO.findVisit(discountId,null));
        //设置商家数据明细
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopDataDetail> shopDatas = cerePlatformDiscountDAO.findShopDatas(discountId);
        if(!EmptyUtils.isEmpty(shopDatas)){
            shopDatas.forEach(shopDataDetail -> {
                //设置参与商品数
                shopDataDetail.setProducts(cerePlatformDiscountDAO.findProducts(discountId,shopDataDetail.getShopId()));
                //设置访客数
                shopDataDetail.setVisit(cerePlatformDiscountDAO.findVisit(discountId,shopDataDetail.getShopId()));
                //设置提交订单数
                shopDataDetail.setOrders(cerePlatformDiscountDAO.findSubmit(discountId,shopDataDetail.getShopId()));
                //设置成交笔数
                shopDataDetail.setFinish(cerePlatformDiscountDAO.findOrders(discountId,shopDataDetail.getShopId()));
                //设置成交总额
                shopDataDetail.setTotal(cerePlatformDiscountDAO.findTotal(discountId,shopDataDetail.getShopId()));
            });
        }
        PageInfo<ShopDataDetail> pageInfo=new PageInfo<>(shopDatas);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setShopDataDetails(page);
        return data;
    }

    @Override
    public Page getAll(DiscountGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Discount> list=cerePlatformDiscountDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(discount -> {
                //设置参与商家数
                discount.setShops(cerePlatformDiscountDAO.findShops(discount.getDiscountId()));
                //设置商品数
                discount.setProducts(cerePlatformDiscountDAO.findProducts(discount.getDiscountId(),null));
            });
        }
        PageInfo<Discount> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getShops(DiscountShopParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<DiscountShop> list=cerePlatformDiscountDAO.getShops(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(discount -> {
                //设置审核次数
                discount.setExamines(cerePlatformDiscountDAO.findExamines(discount.getDiscountId(), discount.getShopId()));
            });
        }
        PageInfo<DiscountShop> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getProducts(DiscountGetProductsParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<DiscountShopProduct> list=cerePlatformDiscountDAO.getProducts(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(product -> {
                //设置折扣
                BigDecimal discount=product.getDiscount().divide(BigDecimal.TEN,2,BigDecimal.ROUND_HALF_UP);
                //设置折扣价区间
                product.setSectionPrice("￥"+ product.getMinPrice().multiply(discount).setScale(2,BigDecimal.ROUND_HALF_UP)+"~￥"+
                        product.getMaxPrice().multiply(discount).setScale(2,BigDecimal.ROUND_HALF_UP));
                //设置售价区间
                product.setOriginalPrice("￥"+product.getMinPrice()+"~￥"+product.getMaxPrice());
            });
        }
        PageInfo<DiscountShopProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<ShopDataDetail> findExcel(DiscountGetByIdParam param) throws CoBusinessException {
        List<ShopDataDetail> shopDatas = cerePlatformDiscountDAO.findShopDatas(param.getDiscountId());
        if(!EmptyUtils.isEmpty(shopDatas)) {
            shopDatas.forEach(shopDataDetail -> {
                //设置参与商品数
                shopDataDetail.setProducts(cerePlatformDiscountDAO.findProducts(param.getDiscountId(), shopDataDetail.getShopId()));
                //设置访客数
                shopDataDetail.setVisit(cerePlatformDiscountDAO.findVisit(param.getDiscountId(), shopDataDetail.getShopId()));
                //设置提交订单数
                shopDataDetail.setOrders(cerePlatformDiscountDAO.findSubmit(param.getDiscountId(), shopDataDetail.getShopId()));
                //设置成交笔数
                shopDataDetail.setFinish(cerePlatformDiscountDAO.findOrders(param.getDiscountId(), shopDataDetail.getShopId()));
                //设置成交总额
                shopDataDetail.setTotal(cerePlatformDiscountDAO.findTotal(param.getDiscountId(), shopDataDetail.getShopId()));
            });
        }
        return shopDatas;
    }

    @Override
    public void updateDiscount(CerePlatformDiscount cerePlatformDiscount) throws CoBusinessException {
        cerePlatformDiscountDAO.updateByPrimaryKeySelective(cerePlatformDiscount);
    }

    @Override
    public List<CereActivitySign> findErrorSign(Long discountId) {
        return cerePlatformDiscountDAO.findErrorSign(discountId);
    }

    @Override
    public List<CereActivitySign> findByDiscount(Long discountId) {
        return cerePlatformDiscountDAO.findByDiscount(discountId);
    }

    @Override
    public List<CanvasPlatformDiscount> getMinDiscount(RenovationParam param) throws CoBusinessException {
        List<CanvasPlatformDiscount> list=cerePlatformDiscountDAO.getMinDiscount(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(detail -> {
                //查询商品明细
                List<ToolProduct> distinctProducts = cerePlatformDiscountDAO.findDistinctProducts(detail.getDiscountId());
                if(!EmptyUtils.isEmpty(distinctProducts)){
                    distinctProducts.forEach(product -> {
                        //计算折扣价
                        BigDecimal discount=product.getDiscount().divide(BigDecimal.TEN,2,BigDecimal.ROUND_HALF_UP);
                        product.setPrice(product.getPrice().multiply(discount).setScale(2,BigDecimal.ROUND_HALF_UP));
                    });
                }
                detail.setProducts(distinctProducts);
            });
        }
        return list;
    }

    @Override
    public List<Long> findProductIdList(Long discountId) {
        return cerePlatformDiscountDAO.findProductIdList(discountId);
    }
}
