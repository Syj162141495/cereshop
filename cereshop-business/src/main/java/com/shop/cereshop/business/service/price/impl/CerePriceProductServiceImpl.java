/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.price.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.price.CerePriceProductDAO;
import com.shop.cereshop.business.page.canvas.CanvasProduct;
import com.shop.cereshop.business.page.canvas.CanvasProductParam;
import com.shop.cereshop.business.page.compose.ComposeProduct;
import com.shop.cereshop.business.service.price.CerePriceProductService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CerePriceProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CerePriceProductServiceImpl implements CerePriceProductService {

    @Autowired
    private CerePriceProductDAO cerePriceProductDAO;

    @Override
    public void insertBatch(List<CerePriceProduct> priceProducts) throws CoBusinessException {
        cerePriceProductDAO.insertBatch(priceProducts);
    }

    @Override
    public void deleteByPriceId(Long priceId) throws CoBusinessException {
        cerePriceProductDAO.deleteByPriceId(priceId);
    }

    @Override
    public List<ComposeProduct> findProducts(Long priceId) {
        return cerePriceProductDAO.findProducts(priceId);
    }

    @Override
    public Page getPriceProducts(CanvasProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CanvasProduct> list=cerePriceProductDAO.getPriceProducts(param);
        PageInfo<CanvasProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
