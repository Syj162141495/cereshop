/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.shop.impl;

import com.shop.cereshop.business.dao.shop.CereShopBannerDAO;
import com.shop.cereshop.business.page.shop.ShopBanner;
import com.shop.cereshop.business.param.banner.BannerSaveParam;
import com.shop.cereshop.business.param.banner.BannerUpdateParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.shop.CereShopBannerService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CereShopBanner;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CereShopBannerServiceImpl implements CereShopBannerService {

    @Autowired
    private CereShopBannerDAO cereShopBannerDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(BannerSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopBanner cereShopBanner=new CereShopBanner();
        cereShopBanner.setShopId(param.getShopId());
        cereShopBanner.setBannerStyle(param.getBannerStyle());
        cereShopBanner.setBannerImage(EmptyUtils.getImage(param.getImages()));
        cereShopBanner.setBannerUrl(param.getBannerUrl());
        cereShopBanner.setState(IntegerEnum.YES.getCode());
        cereShopBanner.setCreateTime(time);
        cereShopBannerDAO.insert(cereShopBanner);
        //新增日志
        cerePlatformLogService.addLog(user,"banner配置管理","商户端操作","添加banner",cereShopBanner.getBannerId(),time);
    }

    @Override
    public void update(BannerUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CereShopBanner cereShopBanner=new CereShopBanner();
        cereShopBanner.setBannerId(param.getBannerId());
        cereShopBanner.setBannerStyle(param.getBannerStyle());
        cereShopBanner.setBannerImage(EmptyUtils.getImage(param.getImages()));
        cereShopBanner.setBannerUrl(param.getBannerUrl());
        cereShopBanner.setUpdateTime(time);
        cereShopBannerDAO.updateByPrimaryKeySelective(cereShopBanner);
        //新增日志
        cerePlatformLogService.addLog(user,"banner配置管理","商户端操作","修改banner",param.getBannerId(),time);
    }

    @Override
    public ShopBanner getById(Long bannerId) throws CoBusinessException {
        ShopBanner banner = cereShopBannerDAO.getById(bannerId);
        if(banner!=null){
            if(!EmptyUtils.isEmpty(banner.getBannerImage())){
                String[] split = banner.getBannerImage().split(",");
                banner.setImages(Arrays.asList(split));
            }
        }
        return banner;
    }
}
