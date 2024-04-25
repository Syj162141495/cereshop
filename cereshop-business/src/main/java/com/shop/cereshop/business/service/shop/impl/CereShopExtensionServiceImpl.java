/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CereShopExtensionDAO;
import com.shop.cereshop.business.param.shop.ShopParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.shop.CereShopExtensionService;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopExtension;
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
public class CereShopExtensionServiceImpl implements CereShopExtensionService {

    @Autowired
    private CereShopExtensionDAO cereShopExtensionDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(CereShopExtension shopExtension, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        String describe="";
        if(!EmptyUtils.isEmpty(shopExtension.getExtensionId())){
            describe="修改推广设置";
            //更新推广设置
            shopExtension.setUpdateTime(time);
            cereShopExtensionDAO.updateByPrimaryKeySelective(shopExtension);
        }else {
            describe="添加推广设置";
            //新增推广设置
            shopExtension.setCreateTime(time);
            cereShopExtensionDAO.insert(shopExtension);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"店铺推广设置","商户端操作",describe,shopExtension.getExtensionId(),time);
    }

    @Override
    public List<CereShopExtension> getAll(ShopParam param) throws CoBusinessException {
        return cereShopExtensionDAO.getAll(param);
    }
}
