/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product.impl;

import com.shop.cereshop.business.dao.product.CereProductClassifyDAO;
import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.business.param.product.ProductGetClassifyParam;
import com.shop.cereshop.business.service.product.CereProductClassifyService;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
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

    @Override
    public List<CereProductClassify> getClassifySelect(ProductGetClassifyParam param) throws CoBusinessException {
        return cereProductClassifyDAO.getClassifySelect(param.getClassifyPid());
    }

    @Override
    public CereProductClassify findByHierarchy(String hierarchy) {
        return cereProductClassifyDAO.findByHierarchy(hierarchy);
    }

    @Override
    public List<Classify> getClassify() throws CoBusinessException {
        //查询所有一级类目
        List<Classify> list=cereProductClassifyDAO.findAll();
        //查询所有子节点类目
        List<Classify> childs=cereProductClassifyDAO.findChilds();
        if(!EmptyUtils.isEmpty(list)){
            Map<String,Integer> map=new HashMap<>();
            list.forEach((classify -> {
                setChildsIndex(classify,childs,map);
            }));
        }
        return list;
    }

    @Override
    public List<CereProductClassify> selectAll() {
        return cereProductClassifyDAO.selectAll();
    }

    private Classify setChildsIndex(Classify parent, List<Classify> all, Map<String,Integer> map) throws ArrayIndexOutOfBoundsException{
        if(!parent.getParentId().equals(0l)){
            //回调进来的,设置回调执行次数+1
            map.put("callback",map.get("callback")+1);
        }else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback",0);
        }
        List<Classify> childs=new ArrayList<>();
        if(!EmptyUtils.isEmpty(all)){
            for (int i = 0; i < all.size(); i++) {
                if(all.size()>0){
                    if(i<0){
                        i=0;
                    }
                    Classify classify = all.get(i);
                    //设置按钮菜单权限
                    if(parent.getId().equals(classify.getParentId())){
                        all.remove(i);
                        i--;
                        //执行回调
                        Classify itemPermission = setChildsIndex(classify, all,map);
                        childs.add(itemPermission);
                        //判断当前是否回到最高级菜单节点
                        if(parent.getParentId().equals(0l)){
                            //如果是,计算索引值=当前索引值-（回调执行次数-1）
                            i=i-(map.get("callback")-1);
                            //初始化回调执行次数
                            map.put("callback",0);
                        }
                    }
                }
            }
            parent.setChilds(childs);
        }
        return parent;
    }
}
