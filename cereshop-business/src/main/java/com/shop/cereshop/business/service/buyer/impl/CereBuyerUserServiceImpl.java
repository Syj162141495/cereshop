/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.buyer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.business.CereBusinessBuyerUserDAO;
import com.shop.cereshop.business.dao.buyer.CereBuyerUserDAO;
import com.shop.cereshop.business.page.buyer.BuyerUser;
import com.shop.cereshop.business.page.buyer.BuyerUserDetail;
import com.shop.cereshop.business.param.buyer.BuyerUserGetAllParam;
import com.shop.cereshop.business.param.buyer.UserSaveParam;
import com.shop.cereshop.business.param.buyer.UserUpdateParam;
import com.shop.cereshop.business.service.buyer.CereBuyerUserService;
import com.shop.cereshop.business.service.label.CereBuyerShopLabelService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CereBusinessBuyerUser;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.business.CereShopIdBuyerUserIdTimeDTO;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.CereBuyerShopLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.EncryptUtil;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CereBuyerUserServiceImpl implements CereBuyerUserService {

    @Autowired
    private CereBuyerUserDAO cereBuyerUserDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereBuyerShopLabelService cereBuyerShopLabelService;

    @Autowired
    private CereBusinessBuyerUserDAO cereBusinessBuyerUserDAO;

    @Override
    public Page getAll(BuyerUserGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<BuyerUser> list=cereBuyerUserDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(buyerUser -> {
                if(!EmptyUtils.isEmpty(buyerUser.getIds())){
                    List<Long> labelIds=new ArrayList<>();
                    String[] split = buyerUser.getIds().split(",");
                    for (String id:split) {
                        labelIds.add(Long.parseLong(id));
                    }
                    buyerUser.setLabelIds(labelIds);
                }
            });
        }
        PageInfo<BuyerUser> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(UserSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //校验手机号是否存在
        CereBuyerUser cereBuyerUser=cereBuyerUserDAO.checkPhone(param.getPhone());
        if(cereBuyerUser!=null){
            throw new CoBusinessException(CoReturnFormat.PHONE_ALREADY);
        }
        cereBuyerUser=new CereBuyerUser();
        cereBuyerUser.setCreateTime(time);
        cereBuyerUser.setBirthday(param.getBirthday());
        cereBuyerUser.setName("MJ"+RandomStringUtil.getRandomCode(4,0));
        cereBuyerUser.setWechatName(param.getName());
        cereBuyerUser.setSex(param.getSex());
        cereBuyerUser.setPhone(param.getPhone());
        cereBuyerUser.setState(IntegerEnum.YES.getCode());
        cereBuyerUser.setIfBlack(IntegerEnum.NO.getCode());
        cereBuyerUser.setRemark(param.getRemark());
        cereBuyerUser.setPassword(EncryptUtil.encrypt("123456"));
        cereBuyerUser.setToken(RandomStringUtil.getRandomCode(32,0));
        cereBuyerUser.setHeadImage(StringEnum.DEFAULT_HEAD_IMAGE.getCode());
        cereBuyerUserDAO.insert(cereBuyerUser);
        Long buyerUserId=cereBuyerUser.getBuyerUserId();

        //添加商家客户数据
        CereBusinessBuyerUser bbu = new CereBusinessBuyerUser();
        bbu.setShopId(user.getShopId());
        bbu.setBuyerUserId(buyerUserId);
        bbu.setCreateTime(time);
        cereBusinessBuyerUserDAO.insertOrUpdate(bbu);

        if(!EmptyUtils.isEmpty(param.getIds())){
            //添加客户关联标签数据
            List<CereBuyerShopLabel> collect = param.getIds().stream().map(id -> {
                CereBuyerShopLabel cereBuyerShopLabel = new CereBuyerShopLabel();
                cereBuyerShopLabel.setLabelId(id);
                cereBuyerShopLabel.setBuyerUserId(buyerUserId);
                return cereBuyerShopLabel;
            }).collect(Collectors.toList());
            if(!EmptyUtils.isEmpty(collect)){
                cereBuyerShopLabelService.insertBatch(collect);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"客户管理","商户端操作","添加客户",buyerUserId,time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(UserUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CereBuyerUser cereBuyerUser=new CereBuyerUser();
        cereBuyerUser.setBuyerUserId(param.getBuyerUserId());
        cereBuyerUser.setCreateTime(time);
        cereBuyerUser.setBirthday(param.getBirthday());
        cereBuyerUser.setName("MJ"+RandomStringUtil.getRandomCode(4,0));
        cereBuyerUser.setWechatName(param.getName());
        cereBuyerUser.setSex(param.getSex());
        cereBuyerUser.setState(IntegerEnum.YES.getCode());
        cereBuyerUser.setIfBlack(IntegerEnum.NO.getCode());
        cereBuyerUser.setRemark(param.getRemark());
        cereBuyerUser.setPassword(EncryptUtil.encrypt("123456"));
        cereBuyerUser.setToken(RandomStringUtil.getRandomCode(32,0));
        //更新客户
        cereBuyerUserDAO.updateByPrimaryKeySelective(cereBuyerUser);
        Long buyerUserId=cereBuyerUser.getBuyerUserId();
        //清空客户关联标签数据
        cereBuyerShopLabelService.deleteById(buyerUserId);
        if(!EmptyUtils.isEmpty(param.getIds())){
            //添加客户关联标签数据
            List<CereBuyerShopLabel> collect = param.getIds().stream().map(id -> {
                CereBuyerShopLabel cereBuyerShopLabel = new CereBuyerShopLabel();
                cereBuyerShopLabel.setLabelId(id);
                cereBuyerShopLabel.setBuyerUserId(buyerUserId);
                return cereBuyerShopLabel;
            }).collect(Collectors.toList());
            if(!EmptyUtils.isEmpty(collect)){
                cereBuyerShopLabelService.insertBatch(collect);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"客户管理","商户端操作","修改客户",buyerUserId,time);
    }

    @Override
    public BuyerUserDetail getById(Long shopId, Long buyerUserId) throws CoBusinessException {
        BuyerUserDetail detail = cereBuyerUserDAO.getById(shopId, buyerUserId);
        //查询客户关联标签
        if(detail!=null){
            detail.setIds(cereBuyerShopLabelService.findByUserId(buyerUserId));
        }
        return detail;
    }

    @Override
    public void initBusinessBuyerUser() {
        List<CereShopIdBuyerUserIdTimeDTO> visitRecordList = cereBuyerUserDAO.findVisitRecord();
        List<CereShopIdBuyerUserIdTimeDTO> consumeRecordList = cereBuyerUserDAO.findConsumeRecord();
        Map<String, String> map = consumeRecordList.stream().collect(Collectors.groupingBy(dto -> dto.getShopId() + "-" + dto.getBuyerUserId(),
                Collectors.mapping(CereShopIdBuyerUserIdTimeDTO::getTime, Collectors.joining())));
        int lastLogIndex = 0;
        int size = visitRecordList.size();
        int index = 0;
        for (CereShopIdBuyerUserIdTimeDTO record : visitRecordList) {
            CereBusinessBuyerUser bbu = new CereBusinessBuyerUser();
            bbu.setShopId(record.getShopId());
            bbu.setBuyerUserId(record.getBuyerUserId());
            bbu.setCreateTime(record.getTime());
            bbu.setUpdateTime(map.get(record.getShopId()+"-"+record.getBuyerUserId()));
            cereBusinessBuyerUserDAO.insertOrUpdate(bbu);
            index ++;
            int percent = (int)(index / 1.0 / size * 100);
            if (percent != lastLogIndex) {
                log.info("init progress {}%", percent);
                lastLogIndex = percent;
            }
        }
    }

    @Override
    public void updateGrowth(Long buyerUserId, int growth) {
        cereBuyerUserDAO.updateGrowth(buyerUserId, growth);
    }
}
