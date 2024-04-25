/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.commons.cache.product.ProductBo;
import com.shop.cereshop.app.dao.product.CereProductClassifyDAO;
import com.shop.cereshop.app.page.classify.Classify;
import com.shop.cereshop.app.page.index.Product;
import com.shop.cereshop.app.page.index.ProductClassify;
import com.shop.cereshop.app.param.classify.ClassifyParam;
import com.shop.cereshop.app.param.classify.ClassifyProductParam;
import com.shop.cereshop.app.service.product.CereProductClassifyService;
import com.shop.cereshop.app.service.product.CereShopProductService;
import com.shop.cereshop.app.service.shop.CerePlatformShopservice;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CereProductClassifyServiceImpl implements CereProductClassifyService {

    @Autowired
    private CereProductClassifyDAO cereProductClassifyDAO;

    @Autowired
    private CerePlatformShopservice cerePlatformShopservice;

    @Autowired
    private CereShopProductService cereShopProductService;

    @Override
    public List<ProductClassify> findAll() {
        return cereProductClassifyDAO.findAll();
    }

    @Override
    public List<Classify> getFirstClassify(ClassifyParam param) throws CoBusinessException {
        List<Classify> classifies=null;
        if(EmptyUtils.isEmpty(param.getClassifyId())){
            //查询所有一级类目
            classifies = cereProductClassifyDAO.getFirstClassify(null);
        }else {
            //查询所有二级类目
            classifies = cereProductClassifyDAO.getFirstClassify(param.getClassifyId());
            //查询所有三级类目
            List<Classify> childs=cereProductClassifyDAO.findThreeClassify();
            //封装三级类目到对应二级类目子级
            if(!EmptyUtils.isEmpty(classifies)){
                classifies.forEach(a -> {
                    List<Classify> classifyList=new ArrayList<>();
                    if(!EmptyUtils.isEmpty(childs)){
                        childs.forEach(classify -> {
                            if(a.getClassifyId().equals(classify.getClassifyPid())){
                                classifyList.add(classify);
                            }
                        });
                        a.setChilds(classifyList);
                    }
                });
            }
        }
        return classifies;
    }

    @Override
    public Page getClaasifyProducts(ClassifyProductParam param, CereBuyerUser user) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        if (user != null) {
            param.setBuyerUserId(user.getBuyerUserId());
        }
        List<Product> list=cereProductClassifyDAO.getClaasifyProducts(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(a -> {
                //查询付款人数
                a.setUsers(cerePlatformShopservice.findPayUsers(a.getProductId()));
                //设置活动信息
                ProductBo productBo = cereShopProductService.fetchProductCache(a.getShopId(), a.getProductId(), a.getSkuId(), user, true);
                if (productBo != null) {
                    a.setActivityType(productBo.getActivityType());
                    a.setOriginalPrice(productBo.getOriginalPrice());
                    a.setPrice(productBo.getPrice());
                    a.setTotal(productBo.getTotal());
                }
            });
        }
        PageInfo<Product> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }
}
