/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.label.CereShopUserLabelDAO;
import com.shop.cereshop.business.page.label.ShopUserLabel;
import com.shop.cereshop.business.param.label.UserLabelGetAllParam;
import com.shop.cereshop.business.service.label.CereShopUserLabelService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.CereShopUserLabel;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CereShopUserLabelServiceImpl implements CereShopUserLabelService {

    @Autowired
    private CereShopUserLabelDAO cereShopUserLabelDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(CereShopUserLabel label, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        label.setCreateTime(time);
        //新增标签
        cereShopUserLabelDAO.insert(label);
        //新增日志
        cerePlatformLogService.addLog(user,"标签管理","商家端操作","新增标签",label.getLabelId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(CereShopUserLabel label, CerePlatformBusiness user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        label.setUpdateTime(time);
        //更新标签
        cereShopUserLabelDAO.updateByPrimaryKeySelective(label);
        //新增日志
        cerePlatformLogService.addLog(user,"标签管理","商家端操作","修改标签",label.getLabelId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(List<Long> ids, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(ids)){
            for (Long labelId:ids) {
                //删除标签
                cereShopUserLabelDAO.deleteByPrimaryKey(labelId);
                //删除客户关联标签数据
                cereShopUserLabelDAO.deleteBuyer(labelId);
                //新增日志
                cerePlatformLogService.addLog(user,"标签管理","商家端操作","删除标签",labelId,time);
            }
        }
    }

    @Override
    public CereShopUserLabel getById(Long labelId) throws CoBusinessException {
        return cereShopUserLabelDAO.getById(labelId);
    }

    @Override
    public Page getAll(UserLabelGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopUserLabel> list=cereShopUserLabelDAO.getAll(param);
        PageInfo<ShopUserLabel> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CereShopUserLabel> getLabels(Long shopId) throws CoBusinessException {
        return cereShopUserLabelDAO.getLabels(shopId);
    }
}
