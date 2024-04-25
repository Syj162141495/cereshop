/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.tool.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.tool.CereShopCrowdDAO;
import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.tool.ShopCrowd;
import com.shop.cereshop.business.page.tool.ShopCrowdDetail;
import com.shop.cereshop.business.param.tool.*;
import com.shop.cereshop.business.service.common.CommonService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.tool.CereShopCrowdService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereShopCrowd;
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
public class CereShopCrowdServiceImpl implements CereShopCrowdService {

    @Autowired
    private CereShopCrowdDAO cereShopCrowdDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CommonService commonService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopCrowdSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //校验名称
        CereShopCrowd cereShopCrowd=cereShopCrowdDAO.checkName(param.getCrowdName(),null);
        if(cereShopCrowd!=null){
            throw new CoBusinessException(CoReturnFormat.CROWD_NAME_ALREADY);
        }
        cereShopCrowd=new CereShopCrowd();
        cereShopCrowd.setCreateTime(time);
        cereShopCrowd.setShopId(user.getShopId());
        cereShopCrowd.setCrowdName(param.getCrowdName());
        //查询满足条件的客户数量
        List<String> ids=null;
        if(!EmptyUtils.isEmpty(param.getConditions())){
            for (CrowdCondition condition : param.getConditions()) {
                //查询满足条件的客户id数组
                condition.setIds(ids);
                condition.setShopId(user.getShopId());
                switch (condition.getType()){
                    case 1 :
                        cereShopCrowd.setShopBuyYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopBuyCondition(condition);
                        break;
                    case 2 :
                        cereShopCrowd.setShopBuyNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopBuyCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无购买的客户id
                            ids=cereShopCrowdDAO.findNoBuy(condition);
                        }
                        break;
                    case 3 :
                        cereShopCrowd.setShopOrderYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopOrderCondition(condition);
                        break;
                    case 4 :
                        cereShopCrowd.setShopOrderNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopOrderCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无下单的客户id
                            ids=cereShopCrowdDAO.findNoOrder(condition);
                        }
                        break;
                    case 5 :
                        cereShopCrowd.setShopAddYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopAddCondition(condition);
                        break;
                    case 6 :
                        cereShopCrowd.setShopAddNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopAddCondition(condition);
                        break;
                    case 7 :
                        cereShopCrowd.setShopVisitYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopVisitCondition(condition);
                        break;
                    case 8 :
                        cereShopCrowd.setShopVisitNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopVisitCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无访问的客户id
                            ids=cereShopCrowdDAO.findNoVisit(condition);
                        }
                        break;
                    case 9 :
                        cereShopCrowd.setEffectiveBuy(condition.getCalculation());
                        cereShopCrowd.setEffectiveBuyCount(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByCountCondition(condition);
                        break;
                    case 10 :
                        cereShopCrowd.setEffectivePrice(condition.getCalculation());
                        cereShopCrowd.setEffectivePriceCount(condition.getNumber());
                        ids=cereShopCrowdDAO.findUserByPriceCondition(condition);
                        break;
                    case 11 :
                        ids=cereShopCrowdDAO.findUserByLabelCondition(condition);
                        if(!EmptyUtils.isEmpty(condition.getLabelIds())){
                            cereShopCrowd.setLabelId(EmptyUtils.getString(condition.getLabelIds()));
                        }
                        break;
                    default:
                        break;
                }
                if(EmptyUtils.isEmpty(ids)){
                    ids=null;
                }
            }
            if(!EmptyUtils.isEmpty(ids)){
                //去除重复id
                ids=ids.stream().distinct().collect(Collectors.toList());
                cereShopCrowd.setUsers(ids.size());
                cereShopCrowd.setIds(EmptyUtils.getImage(ids));
            }
            //插入人群数据
            cereShopCrowdDAO.insert(cereShopCrowd);
            //新增日志
            cerePlatformLogService.addLog(user,"客户人群","商家端操作","添加人群",cereShopCrowd.getShopCrowdId(),time);
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopCrowdUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //校验名称
        CereShopCrowd cereShopCrowd=cereShopCrowdDAO.checkName(param.getCrowdName(),param.getShopCrowdId());
        if(cereShopCrowd!=null){
            throw new CoBusinessException(CoReturnFormat.CROWD_NAME_ALREADY);
        }
        cereShopCrowd=new CereShopCrowd();
        cereShopCrowd.setShopCrowdId(param.getShopCrowdId());
        cereShopCrowd.setCreateTime(time);
        cereShopCrowd.setShopId(user.getShopId());
        cereShopCrowd.setCrowdName(param.getCrowdName());
        //查询满足条件的客户数量
        List<String> ids=null;
        if(!EmptyUtils.isEmpty(param.getConditions())){
            for (CrowdCondition condition : param.getConditions()) {
                //查询满足条件的客户id数组
                condition.setShopId(user.getShopId());
                condition.setIds(ids);
                switch (condition.getType()){
                    case 1 :
                        cereShopCrowd.setShopBuyYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopBuyCondition(condition);
                        break;
                    case 2 :
                        cereShopCrowd.setShopBuyNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopBuyCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无购买的客户id
                            ids=cereShopCrowdDAO.findNoBuy(condition);
                        }
                        break;
                    case 3 :
                        cereShopCrowd.setShopOrderYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopOrderCondition(condition);
                        break;
                    case 4 :
                        cereShopCrowd.setShopOrderNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopOrderCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无下单的客户id
                            ids=cereShopCrowdDAO.findNoOrder(condition);
                        }
                        break;
                    case 5 :
                        cereShopCrowd.setShopAddYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopAddCondition(condition);
                        break;
                    case 6 :
                        cereShopCrowd.setShopAddNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopAddCondition(condition);
                        break;
                    case 7 :
                        cereShopCrowd.setShopVisitYes(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopVisitCondition(condition);
                        break;
                    case 8 :
                        cereShopCrowd.setShopVisitNo(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByShopVisitCondition(condition);
                        if(!EmptyUtils.isEmpty(ids)){
                            //过滤店铺无访问的客户id
                            ids=cereShopCrowdDAO.findNoVisit(condition);
                        }
                        break;
                    case 9 :
                        cereShopCrowd.setEffectiveBuy(condition.getCalculation());
                        cereShopCrowd.setEffectiveBuyCount(Integer.parseInt(condition.getNumber().stripTrailingZeros().toPlainString()));
                        ids=cereShopCrowdDAO.findUserByCountCondition(condition);
                        break;
                    case 10 :
                        cereShopCrowd.setEffectivePrice(condition.getCalculation());
                        cereShopCrowd.setEffectivePriceCount(condition.getNumber());
                        ids=cereShopCrowdDAO.findUserByPriceCondition(condition);
                        break;
                    case 11 :
                        if(!EmptyUtils.isEmpty(condition.getLabelIds())){
                            cereShopCrowd.setLabelId(EmptyUtils.getString(condition.getLabelIds()));
                            ids=cereShopCrowdDAO.findUserByLabelCondition(condition);
                        }
                        break;
                    default:
                        break;
                }
                if(EmptyUtils.isEmpty(ids)){
                    ids=null;
                }
            }
            if(!EmptyUtils.isEmpty(ids)){
                //去除重复id
                ids=ids.stream().distinct().collect(Collectors.toList());
                cereShopCrowd.setUsers(ids.size());
                cereShopCrowd.setIds(EmptyUtils.getImage(ids));
            }else {
                cereShopCrowd.setUsers(0);
            }
            //更新人群数据
            cereShopCrowdDAO.updateByPrimaryKeySelective(cereShopCrowd);
            //新增日志
            cerePlatformLogService.addLog(user,"客户人群","商家端操作","修改人群",cereShopCrowd.getShopCrowdId(),time);
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ShopCrowdGetByIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cereShopCrowdDAO.deleteByIds(param.getIds());
        //新增日志
        cerePlatformLogService.addLog(user,"客户人群","商家端操作","删除人群",param.getShopCrowdId(),time);
    }

    @Override
    public ShopCrowdDetail getById(Long shopCrowdId) throws CoBusinessException {
        CereShopCrowd cereShopCrowd=cereShopCrowdDAO.getById(shopCrowdId);
        if(cereShopCrowd!=null){
            ShopCrowdDetail shopCrowdDetail=new ShopCrowdDetail();
            shopCrowdDetail.setShopCrowdId(cereShopCrowd.getShopCrowdId());
            shopCrowdDetail.setShopId(cereShopCrowd.getShopId());
            shopCrowdDetail.setCrowdName(cereShopCrowd.getCrowdName());
            //设置筛选条件数组
            List<CrowdCondition> conditions=new ArrayList<>();
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopBuyYes())){
                //如果店铺有购买存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(1);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopBuyYes()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopBuyNo())){
                //如果店铺无购买存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(2);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopBuyNo()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopOrderYes())){
                //如果店铺有下单存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(3);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopOrderYes()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopOrderNo())){
                //如果店铺无下单存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(4);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopOrderNo()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopAddYes())){
                //如果店铺有加购存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(5);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopAddYes()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopAddNo())){
                //如果店铺无加购存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(6);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopAddNo()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopVisitYes())){
                //如果店铺有访问存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(7);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopVisitYes()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getShopVisitNo())){
                //如果店铺无访问存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(8);
                condition.setNumber(new BigDecimal(cereShopCrowd.getShopVisitNo()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getEffectiveBuyCount())){
                //如果店铺有效购买次数存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(9);
                condition.setCalculation(cereShopCrowd.getEffectiveBuy());
                condition.setNumber(new BigDecimal(cereShopCrowd.getEffectiveBuyCount()));
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getEffectivePriceCount())){
                //如果店铺有效购买金额存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(10);
                condition.setCalculation(cereShopCrowd.getEffectivePrice());
                condition.setNumber(cereShopCrowd.getEffectivePriceCount());
                conditions.add(condition);
            }
            if(!EmptyUtils.isEmpty(cereShopCrowd.getLabelId())){
                //如果标签id存在数据
                CrowdCondition condition=new CrowdCondition();
                condition.setType(11);
                String[] split = cereShopCrowd.getLabelId().split(",");
                List<Long> labelIds=new ArrayList<>();
                if(!EmptyUtils.isEmpty(split)){
                    for (String s : split) {
                        labelIds.add(Long.parseLong(s));
                    }
                }
                condition.setLabelIds(labelIds);
                conditions.add(condition);
            }
            shopCrowdDetail.setConditions(conditions);
            return shopCrowdDetail;
        }
        return null;
    }

    @Override
    public Page getAll(ShopCrowdGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopCrowd> list=cereShopCrowdDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            //设置人群定义
            list.forEach(crowd -> {
                String content="";
                if(!EmptyUtils.isEmpty(crowd.getShopBuyYes())){
                    //如果店铺有购买存在数据
                    content+="近"+crowd.getShopBuyYes()+"天有购买、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopBuyNo())){
                    //如果店铺无购买存在数据
                    content+="近"+crowd.getShopBuyNo()+"天无购买、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopOrderYes())){
                    //如果店铺有下单存在数据
                    content+="近"+crowd.getShopOrderYes()+"天有下单、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopOrderNo())){
                    //如果店铺无下单存在数据
                    content+="近"+crowd.getShopOrderNo()+"天无下单、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopAddYes())){
                    //如果店铺有加购存在数据
                    content+="近"+crowd.getShopAddYes()+"天有加购、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopAddNo())){
                    //如果店铺无加购存在数据
                    content+="近"+crowd.getShopAddNo()+"天无加购、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopVisitYes())){
                    //如果店铺有访问存在数据
                    content+="近"+crowd.getShopVisitYes()+"天有访问、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopVisitNo())){
                    //如果店铺无访问存在数据
                    content+="近"+crowd.getShopVisitNo()+"天无访问、";
                }
                if(!EmptyUtils.isEmpty(crowd.getEffectiveBuyCount())){
                    //如果店铺有效购买次数存在数据
                    if(IntegerEnum.GROUP_CONDITION_GREATER.getCode().equals(crowd.getEffectiveBuy())){
                        //运算符为大于
                        content+="累计购买次数大于"+crowd.getEffectiveBuyCount();
                    }else if(IntegerEnum.GROUP_CONDITION_EQUAL.getCode().equals(crowd.getEffectiveBuy())){
                        //运算符为等于
                        content+="累计购买次数等于"+crowd.getEffectiveBuyCount();
                    }else {
                        //运算符为小于
                        content+="累计购买次数小于"+crowd.getEffectiveBuyCount();
                    }
                }
                if(!EmptyUtils.isEmpty(crowd.getEffectivePriceCount())){
                    //如果店铺有效购买金额存在数据
                    if(IntegerEnum.GROUP_CONDITION_GREATER.getCode().equals(crowd.getEffectivePrice())){
                        //运算符为大于
                        content+="累计消费金额大于"+crowd.getEffectivePriceCount();
                    }else if(IntegerEnum.GROUP_CONDITION_EQUAL.getCode().equals(crowd.getEffectivePrice())){
                        //运算符为等于
                        content+="累计消费金额等于"+crowd.getEffectivePriceCount();
                    }else {
                        //运算符为小于
                        content+="累计消费金额小于"+crowd.getEffectivePriceCount();
                    }
                }
                if(!EmptyUtils.isEmpty(crowd.getLabelName())){
                    //如果存在标签数据
                    content+="满足任意一个标签："+crowd.getLabelName();
                }
                crowd.setContent(content);
            });
        }
        PageInfo<ShopCrowd> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<BuyerUser> getUsers(Long shopCrowdId) throws CoBusinessException {
        //查询人群关联客户id数据
        String userIds=cereShopCrowdDAO.findUserIds(shopCrowdId);
        if(!EmptyUtils.isEmpty(userIds)){
            //转为id数组
            List<String> ids = EmptyUtils.getImages(userIds);
            //查询客户数据
            List<BuyerUser> list=cereShopCrowdDAO.getUsers(ids);
            return list;
        }
        return null;
    }

    @Override
    public String findUserIds(Long shopCrowdId) {
        return cereShopCrowdDAO.findUserIds(shopCrowdId);
    }

    @Override
    public Page selectCrowd(ShopCrowdGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopCrowd> list=cereShopCrowdDAO.selectCrowd(param);
        if(!EmptyUtils.isEmpty(list)){
            //设置人群定义
            list.forEach(crowd -> {
                String content="";
                if(!EmptyUtils.isEmpty(crowd.getShopBuyYes())){
                    //如果店铺有购买存在数据
                    content+="近"+crowd.getShopBuyYes()+"天有购买、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopBuyNo())){
                    //如果店铺无购买存在数据
                    content+="近"+crowd.getShopBuyNo()+"天无购买、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopOrderYes())){
                    //如果店铺有下单存在数据
                    content+="近"+crowd.getShopOrderYes()+"天有下单、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopOrderNo())){
                    //如果店铺无下单存在数据
                    content+="近"+crowd.getShopOrderNo()+"天无下单、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopAddYes())){
                    //如果店铺有加购存在数据
                    content+="近"+crowd.getShopAddYes()+"天有加购、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopAddNo())){
                    //如果店铺无加购存在数据
                    content+="近"+crowd.getShopAddNo()+"天无加购、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopVisitYes())){
                    //如果店铺有访问存在数据
                    content+="近"+crowd.getShopVisitYes()+"天有访问、";
                }
                if(!EmptyUtils.isEmpty(crowd.getShopVisitNo())){
                    //如果店铺无访问存在数据
                    content+="近"+crowd.getShopVisitNo()+"天无访问、";
                }
                if(!EmptyUtils.isEmpty(crowd.getEffectiveBuyCount())){
                    //如果店铺有效购买次数存在数据
                    if(IntegerEnum.GROUP_CONDITION_GREATER.getCode().equals(crowd.getEffectiveBuy())){
                        //运算符为大于
                        content+="累计购买次数大于"+crowd.getEffectiveBuyCount();
                    }else if(IntegerEnum.GROUP_CONDITION_EQUAL.getCode().equals(crowd.getEffectiveBuy())){
                        //运算符为等于
                        content+="累计购买次数等于"+crowd.getEffectiveBuyCount();
                    }else {
                        //运算符为小于
                        content+="累计购买次数小于"+crowd.getEffectiveBuyCount();
                    }
                }
                if(!EmptyUtils.isEmpty(crowd.getEffectivePriceCount())){
                    //如果店铺有效购买金额存在数据
                    if(IntegerEnum.GROUP_CONDITION_GREATER.getCode().equals(crowd.getEffectivePrice())){
                        //运算符为大于
                        content+="累计消费金额大于"+crowd.getEffectivePriceCount();
                    }else if(IntegerEnum.GROUP_CONDITION_EQUAL.getCode().equals(crowd.getEffectivePrice())){
                        //运算符为等于
                        content+="累计消费金额等于"+crowd.getEffectivePriceCount();
                    }else {
                        //运算符为小于
                        content+="累计消费金额小于"+crowd.getEffectivePriceCount();
                    }
                }
                if(!EmptyUtils.isEmpty(crowd.getLabelName())){
                    //如果存在标签数据
                    content+="满足任意一个标签："+crowd.getLabelName();
                }
            });
        }
        PageInfo<ShopCrowd> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CereShopCrowd> findAll() {
        return cereShopCrowdDAO.findAll();
    }

    @Override
    public List<String> findUserByShopBuyCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByShopBuyCondition(condition);
    }

    @Override
    public List<String> findNoBuy(CrowdCondition condition) {
        return cereShopCrowdDAO.findNoBuy(condition);
    }

    @Override
    public List<String> findUserByShopOrderCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByShopOrderCondition(condition);
    }

    @Override
    public List<String> findNoOrder(CrowdCondition condition) {
        return cereShopCrowdDAO.findNoOrder(condition);
    }

    @Override
    public List<String> findUserByShopAddCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByShopAddCondition(condition);
    }

    @Override
    public List<String> findUserByShopVisitCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByShopVisitCondition(condition);
    }

    @Override
    public List<String> findNoVisit(CrowdCondition condition) {
        return cereShopCrowdDAO.findNoVisit(condition);
    }

    @Override
    public List<String> findUserByCountCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByCountCondition(condition);
    }

    @Override
    public List<String> findUserByPriceCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByPriceCondition(condition);
    }

    @Override
    public List<String> findUserByLabelCondition(CrowdCondition condition) {
        return cereShopCrowdDAO.findUserByLabelCondition(condition);
    }

    @Override
    public void updateBatch(List<CereShopCrowd> updates) throws CoBusinessException {
        cereShopCrowdDAO.updateBatch(updates);
    }
}
