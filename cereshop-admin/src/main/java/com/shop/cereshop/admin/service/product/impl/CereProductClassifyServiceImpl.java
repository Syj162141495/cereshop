/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.product.CereProductClassifyDAO;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.LongEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.product.ProductClassify;
import com.shop.cereshop.admin.param.product.ClassifyDeleteParam;
import com.shop.cereshop.admin.param.product.ClassifyGetAllParam;
import com.shop.cereshop.admin.param.product.ClassifyLevelParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.product.CereProductClassifyService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.image.Image;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CereProductClassifyServiceImpl implements CereProductClassifyService {

    @Autowired
    private CereProductClassifyDAO cereProductClassifyDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ClassifyLevelParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //定义批量更新层级结构字段数组
        List<CereProductClassify> updates=new ArrayList<>();
        if(!EmptyUtils.isEmpty(param.getClassifies())){
            //遍历一级类别
            for (ProductClassify classify:param.getClassifies()) {
                //新增一级类别数据
                addOneClassify(classify,time,updates);
            }
            if(!EmptyUtils.isEmpty(updates)){
                //批量更新分类层级结构
                cereProductClassifyDAO.updateBatchLevelHierarchy(updates);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"商品类别","平台端操作","添加商品类别",null,time);
    }

    private void addOneClassify(ProductClassify classify, String time,List<CereProductClassify> updates) throws CoBusinessException{
        if(EmptyUtils.isEmpty(classify.getCategoryName())){
            throw new CoBusinessException(CoReturnFormat.CLASSIFY_NAME_NULL);
        }
        CereProductClassify cereProductClassify=new CereProductClassify();
        cereProductClassify.setClassifyId(classify.getId());
        cereProductClassify.setClassifyName(classify.getCategoryName());
        cereProductClassify.setClassifyPid(LongEnum.ROOT.getCode());
        cereProductClassify.setClassifyImage(classify.getCategoryImg());
        cereProductClassify.setClassifyLevel(IntegerEnum.CLASSIFY_LEVEL_ONE.getCode());
        cereProductClassify.setLink(classify.getLink());
        cereProductClassify.setClassifyHierarchy("-"+classify.getCategoryName());
        if(!EmptyUtils.isEmpty(classify.getId())){
            //更新一级类别
            cereProductClassify.setUpdateTime(time);
            cereProductClassify.setClassifyLevelHierarchy("/"+cereProductClassify.getClassifyId());
            cereProductClassifyDAO.updateByPrimaryKeySelective(cereProductClassify);
        }else {
            //新增一级类别
            cereProductClassify.setCreateTime(time);
            cereProductClassifyDAO.insert(cereProductClassify);
            cereProductClassify.setClassifyLevelHierarchy("/"+cereProductClassify.getClassifyId());
            updates.add(cereProductClassify);
        }
        if(!EmptyUtils.isEmpty(classify.getChilds())){
            //新增子级类别
            for (ProductClassify productClassify:classify.getChilds()) {
                addChildClaasify(cereProductClassify,
                        updates,time,productClassify,IntegerEnum.CLASSIFY_LEVEL_TWO.getCode());
            }
        }
    }

    private void addChildClaasify(CereProductClassify parent, List<CereProductClassify> updates,
                                  String time,ProductClassify child,Integer level) throws CoBusinessException{
        if(EmptyUtils.isEmpty(child.getCategoryName())){
            throw new CoBusinessException(CoReturnFormat.CLASSIFY_NAME_NULL);
        }
        CereProductClassify productClassify=new CereProductClassify();
        productClassify.setClassifyId(child.getId());
        productClassify.setClassifyName(child.getCategoryName());
        productClassify.setClassifyPid(parent.getClassifyId());
        productClassify.setClassifyLevel(level);
        productClassify.setLink(child.getLink());
        productClassify.setClassifyImage(child.getCategoryImg());
        productClassify.setClassifyHierarchy(parent.getClassifyHierarchy()+"-"+child.getCategoryName());
        if(!EmptyUtils.isEmpty(child.getId())){
            //更新子级类别
            productClassify.setUpdateTime(time);
            productClassify.setClassifyLevelHierarchy(parent.getClassifyLevelHierarchy()+"/"+productClassify.getClassifyId());
            cereProductClassifyDAO.updateByPrimaryKeySelective(productClassify);
        }else {
            //新增子级类别
            productClassify.setCreateTime(time);
            cereProductClassifyDAO.insert(productClassify);
            productClassify.setClassifyLevelHierarchy(parent.getClassifyLevelHierarchy()+"/"+productClassify.getClassifyId());
            updates.add(productClassify);
        }
        if(!EmptyUtils.isEmpty(child.getChilds())){
            //新增子级类别
            for (ProductClassify classify1:child.getChilds()) {
                addChildClaasify(productClassify,updates,time,classify1,IntegerEnum.CLASSIFY_LEVEL_THREE.getCode());
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ClassifyLevelParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        //定义批量更新层级结构字段数组
        List<CereProductClassify> updates=new ArrayList<>();
        if(!EmptyUtils.isEmpty(param.getClassifies())){
            for (ProductClassify classify:param.getClassifies()) {
                addOneClassify(classify,time,updates);
            }
            if(!EmptyUtils.isEmpty(updates)){
                //批量更新分类层级结构
                cereProductClassifyDAO.updateBatchLevelHierarchy(updates);
            }
            if(!EmptyUtils.isEmpty(param.getDeleteIds())){
                //删除分类
                cereProductClassifyDAO.deleteByIds(param.getDeleteIds());
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"商品类别","平台端操作","修改商品类别",null,time);
    }

    @Override
    public ProductClassify getById(Long classifyId) throws CoBusinessException {
        ProductClassify result=new ProductClassify();
        //查询一级类别数据
        CereProductClassify classify=cereProductClassifyDAO.getById(classifyId);
        if(classify!=null){
            result.setId(classify.getClassifyId());
            result.setCategoryName(classify.getClassifyName());
            result.setLink(classify.getLink());
            result.setDepth(classify.getClassifyLevel());
            result.setCategoryImg(classify.getClassifyImage());
            List<Image> images=new ArrayList<>();
            Image image=new Image();
            image.setImgPath(classify.getClassifyImage());
            images.add(image);
            result.setCategoryImgArray(images);
            //查询所有二级分类
            List<ProductClassify> classifies=cereProductClassifyDAO.findByPid(classify.getClassifyId());
            if(!EmptyUtils.isEmpty(classifies)){
                //查询所有三级分类
                classifies.forEach(a -> {
                    a.setChilds(cereProductClassifyDAO.findByPid(a.getId()));
                    List<Image> childImages=new ArrayList<>();
                    Image child=new Image();
                    child.setImgPath(a.getCategoryImg());
                    childImages.add(child);
                    a.setCategoryImgArray(childImages);
                });
                result.setChilds(classifies);
            }
        }
        return result;
    }

    @Override
    public Page getAll(ClassifyGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CereProductClassify> list=cereProductClassifyDAO.getAll();
        PageInfo<CereProductClassify> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ClassifyDeleteParam param, CerePlatformUser user) throws CoBusinessException {
        //校验一级类别是否存在商品
        List<CereShopProduct> list = cereProductClassifyDAO.checkProduct(param.getOneClassifyId());
        if(!EmptyUtils.isEmpty(list)){
            throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
        }
        String time =TimeUtils.yyMMddHHmmss();
        //查询一级类别层级
        CereProductClassify classify=cereProductClassifyDAO.getById(param.getOneClassifyId());
        if(classify!=null){
            //删除一级类别
            cereProductClassifyDAO.deleteByPrimaryKey(param.getOneClassifyId());
            String[] split = classify.getClassifyLevelHierarchy().split("/");
            if(!EmptyUtils.isEmpty(split)){
                if(split.length==3){
                    //只包含二级类别
                    list=cereProductClassifyDAO.checkProduct(Long.parseLong(split[split.length-1]));
                    if(!EmptyUtils.isEmpty(list)){
                        throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
                    }
                    //删除二级类别
                    cereProductClassifyDAO.deleteByPrimaryKey(Long.parseLong(split[split.length-1]));
                }else if(split.length==4){
                    //包含二级、三级类别
                    //校验当前二级类别是否有绑定商品数据
                    list=cereProductClassifyDAO.checkProduct(Long.parseLong(split[split.length-2]));
                    if(!EmptyUtils.isEmpty(list)){
                        throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
                    }
                    //删除二级类别
                    cereProductClassifyDAO.deleteByPrimaryKey(Long.parseLong(split[split.length-2]));
                    //校验当前三级类别是否有绑定商品数据
                    list=cereProductClassifyDAO.checkProduct(Long.parseLong(split[split.length-1]));
                    if(!EmptyUtils.isEmpty(list)){
                        throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
                    }
                    //删除三级类别
                    cereProductClassifyDAO.deleteByPrimaryKey(Long.parseLong(split[split.length-1]));
                }
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"商品类别","平台端操作","删除商品类别",String.valueOf(param.getOneClassifyId()),time);
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
