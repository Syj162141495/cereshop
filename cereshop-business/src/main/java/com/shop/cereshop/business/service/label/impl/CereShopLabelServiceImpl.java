/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.label.impl;

import com.shop.cereshop.business.dao.label.CereShopLabelDAO;
import com.shop.cereshop.business.page.label.ShopLabel;
import com.shop.cereshop.business.param.label.LabelDeleteParam;
import com.shop.cereshop.business.param.label.LabelGetAllParam;
import com.shop.cereshop.business.param.label.LabelSaveParam;
import com.shop.cereshop.business.param.label.LabelUpdateParam;
import com.shop.cereshop.business.service.label.CereLabelSourceService;
import com.shop.cereshop.business.service.label.CereShopLabelService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.label.CereLabelSource;
import com.shop.cereshop.commons.domain.label.CereShopLabel;
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
public class CereShopLabelServiceImpl implements CereShopLabelService {

    @Autowired
    private CereShopLabelDAO cereShopLabelDAO;

    @Autowired
    private CereLabelSourceService cereLabelSourceService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(LabelSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopLabel cereShopLabel=new CereShopLabel();
        cereShopLabel.setShopId(param.getShopId());
        cereShopLabel.setLabelName(param.getLabelName());
        cereShopLabel.setCreateTime(time);
        cereShopLabelDAO.insert(cereShopLabel);
        //新增日志
        cerePlatformLogService.addLog(user,"店铺标签管理","商户端操作","添加店铺标签",cereShopLabel.getLabelId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(LabelUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopLabel cereShopLabel=new CereShopLabel();
        cereShopLabel.setLabelId(param.getLabelId());
        cereShopLabel.setLabelName(param.getLabelName());
        cereShopLabel.setUpdateTime(time);
        cereShopLabelDAO.updateByPrimaryKeySelective(cereShopLabel);
        //新增日志
        cerePlatformLogService.addLog(user,"店铺标签管理","商户端操作","修改店铺标签",cereShopLabel.getLabelId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(LabelDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //删除标签
        cereShopLabelDAO.deleteByPrimaryKey(param.getLabelId());
        //查询该标签绑定的素材数据
        List<CereLabelSource> list= cereLabelSourceService.findByLabelId(param.getLabelId());
        if(!EmptyUtils.isEmpty(list)){
            //查询店铺所属未分组标签
            CereShopLabel cereShopLabel=cereShopLabelDAO.findByShopIdNotGroup(param.getShopId());
            if(cereShopLabel==null){
                //新增一个未分组标签
                cereShopLabel=new CereShopLabel();
                cereShopLabel.setShopId(param.getShopId());
                cereShopLabel.setLabelName("未分组");
                cereShopLabel.setCreateTime(time);
                cereShopLabelDAO.insert(cereShopLabel);
            }
            //删除当前标签绑定素材
            cereLabelSourceService.deleteByLabelId(param.getLabelId());
            //把当前删除标签所属图片数据挂到未分组标签
            for (CereLabelSource cereLabelSource:list) {
                if(cereLabelSource.getImage().contains("jpg")||cereLabelSource.getImage().contains("png")){
                    cereLabelSource.setLabelType(IntegerEnum.SOURCE_IMAGE.getCode());
                }else {
                    cereLabelSource.setLabelType(IntegerEnum.SOURCE_REDIO.getCode());
                }
                cereLabelSource.setLabelId(cereShopLabel.getLabelId());
            }
            cereLabelSourceService.insertBatch(list);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"店铺标签管理","商户端操作","删除店铺标签",param.getLabelId(),time);
    }

    @Override
    public CereShopLabel getById(Long labelId) throws CoBusinessException {
        return cereShopLabelDAO.getById(labelId);
    }

    @Override
    public List<ShopLabel> getAll(LabelGetAllParam param) throws CoBusinessException {
        List<ShopLabel> list = cereShopLabelDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            //查询标签下面的素材
            list.forEach(label -> label.setSources(cereLabelSourceService.findByLabelIdAndType(label.getLabelId(),param.getLabelType())));
        }
        return list;
    }

    @Override
    public CereShopLabel findByShopIdNotGroup(Long shopId) {
        return cereShopLabelDAO.findByShopIdNotGroup(shopId);
    }

    @Override
    public void insert(CereShopLabel cereShopLabel) throws CoBusinessException {
        cereShopLabelDAO.insert(cereShopLabel);
    }
}
