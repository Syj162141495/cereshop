/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product.impl;

import com.shop.cereshop.admin.dao.product.CereProductProviderClassifyDAO;
import com.shop.cereshop.admin.page.product.ProductProviderClassify;
import com.shop.cereshop.admin.param.product.ClassDeleteParam;
import com.shop.cereshop.admin.param.product.ProductProviderClassifyParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.product.CereProductProviderClassifyService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.LongEnum;
import com.shop.cereshop.commons.domain.product.CereProductProviderClassify;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
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
import java.util.List;

@Service
public class CereProductProviderClassifyServiceImpl implements CereProductProviderClassifyService {

    @Autowired
    private CereProductProviderClassifyDAO cereProductProviderClassifyDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void add(ProductProviderClassifyParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //定义批量更新层级结构字段数组
        List<CereProductProviderClassify> updates = new ArrayList<>();
        if (!EmptyUtils.isEmpty(param.getClassifies())) {
            //遍历一级类别
            for (ProductProviderClassify classify : param.getClassifies()) {
                //新增一级类别数据
                addOneClassify(classify, time, updates);
            }
            if (!EmptyUtils.isEmpty(updates)) {
                //批量更新分类层级结构
                cereProductProviderClassifyDAO.updateBatchLevelHierarchy(updates);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "服务商类别", "平台端操作", "添加服务商类别", null, time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ProductProviderClassifyParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //定义批量更新层级结构字段数组
        List<CereProductProviderClassify> updates = new ArrayList<>();
        if (!EmptyUtils.isEmpty(param.getClassifies())) {
            for (ProductProviderClassify classify : param.getClassifies()) {
                CereProductProviderClassify cereCustomerClassify = cereProductProviderClassifyDAO.getById(classify.getClassifyId());
                Long pid = cereCustomerClassify.getClassifyPid();
                //如果不存在父节点，说明它就是顶级节点
                if (pid == 0) {
                    addOneClassify(classify, time, updates);
                }
                //否则，找到父节点，从父节点开始启动
                else {
                    CereProductProviderClassify pClassify = cereProductProviderClassifyDAO.getById(pid);
                    ProductProviderClassify parentClassify = new ProductProviderClassify();
                    parentClassify.setClassifyId(pClassify.getClassifyId());
                    parentClassify.setClassifyName(pClassify.getClassifyName());
                    parentClassify.setDescription(pClassify.getDescription());
                    parentClassify.setSort(pClassify.getSort());
                    // 实际上能够进入这个代码片段的param.getClassifies()的长度为一，只有一个顶级分类
                    parentClassify.setChildren(param.getClassifies());
                    addOneClassify(parentClassify, time, updates);
                }
            }
            if (!EmptyUtils.isEmpty(updates)) {
                //批量更新分类层级结构
                cereProductProviderClassifyDAO.updateBatchLevelHierarchy(updates);
            }
            if (!EmptyUtils.isEmpty(param.getDeleteIds())) {
                //删除分类
                cereProductProviderClassifyDAO.deleteByIds(param.getDeleteIds());
            }
        } else {
            // 提交了一个空的树形结构，有可能是删除一个顶级类
            if (!EmptyUtils.isEmpty(param.getDeleteIds())) {
                //删除分类
                cereProductProviderClassifyDAO.deleteByIds(param.getDeleteIds());
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user, "服务商类别", "平台端操作", "修改服务商类别", null, time);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(ClassDeleteParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        // 需要被删除的最顶级类型
        CereProductProviderClassify classify = cereProductProviderClassifyDAO.getById(param.getClassifyId());
        // 如果存在，则依次检查次级类型
        if (classify != null) {
            String[] split = classify.getClassifyLevelHierarchy().split("/");
            int level = split.length - 1;
            if (level == 3) {
                // 三级
                Long third_level_id = Long.parseLong(split[3]);
                // 校验该类别是否存在服务商
                List<CereShopProduct> list = cereProductProviderClassifyDAO.checkProduct(third_level_id);
                if (!EmptyUtils.isEmpty(list)) {
                    throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
                }
                cereProductProviderClassifyDAO.deleteByPrimaryKey(third_level_id);
            } else if (level == 2) {
                // 二级
                Long second_level_id = Long.parseLong(split[2]);
                // 先检查所有三级是否被使用
                List<ProductProviderClassify> third_level_ids = cereProductProviderClassifyDAO.findByPid(second_level_id);
                for (ProductProviderClassify third_item : third_level_ids) {
                    Long child_id = third_item.getClassifyId();
                    // 校验该类别是否存在服务商
                    List<CereShopProduct> list = cereProductProviderClassifyDAO.checkProduct(child_id);
                    if (!EmptyUtils.isEmpty(list)) {
                        throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
                    }
                }
                // 均为使用后，依次删除二三级类别
                for (ProductProviderClassify third_item : third_level_ids) {
                    cereProductProviderClassifyDAO.deleteByPrimaryKey(third_item.getClassifyId());
                }
                cereProductProviderClassifyDAO.deleteByPrimaryKey(second_level_id);
            } else if (level == 1) {
                // 一级
                Long first_level_id = Long.parseLong(split[1]);
                List<ProductProviderClassify> second_level_ids = cereProductProviderClassifyDAO.findByPid(first_level_id);
                for (ProductProviderClassify second_item : second_level_ids) {
                    List<ProductProviderClassify> third_level_ids = cereProductProviderClassifyDAO.findByPid(second_item.getClassifyId());
                    for (ProductProviderClassify third_item : third_level_ids) {
                        Long child_id = third_item.getClassifyId();
                        // 校验该类别是否存在服务商
                        List<CereShopProduct> list = cereProductProviderClassifyDAO.checkProduct(child_id);
                        if (!EmptyUtils.isEmpty(list)) {
                            throw new CoBusinessException(CoReturnFormat.CLASSIFY_BOND_PRODUCT);
                        }
                    }
                }
                for (ProductProviderClassify second_item : second_level_ids) {
                    List<ProductProviderClassify> third_level_ids = cereProductProviderClassifyDAO.findByPid(second_item.getClassifyId());
                    for (ProductProviderClassify third_item : third_level_ids) {
                        cereProductProviderClassifyDAO.deleteByPrimaryKey(third_item.getClassifyId());
                    }
                    cereProductProviderClassifyDAO.deleteByPrimaryKey(second_item.getClassifyId());
                }
                cereProductProviderClassifyDAO.deleteByPrimaryKey(first_level_id);
            }
            cerePlatformLogService.addLog(user, "服务商类别", "平台端操作", "删除服务商类别", String.valueOf(param.getClassifyId()), time);
        }
    }

    @Override
    public ProductProviderClassify getById(Long classifyId) throws CoBusinessException {
        ProductProviderClassify result = new ProductProviderClassify();
        //查询一级类别数据
        CereProductProviderClassify classify = cereProductProviderClassifyDAO.getById(classifyId);
        if (classify != null) {
            result.setClassifyId(classify.getClassifyId());
            result.setClassifyName(classify.getClassifyName());
            result.setClassifyLevel(classify.getClassifyLevel());
            result.setDescription(classify.getDescription());
            result.setSort(classify.getSort());
            //查询所有二级分类
            List<ProductProviderClassify> classifies = cereProductProviderClassifyDAO.findByPid(classify.getClassifyId());
            if (!EmptyUtils.isEmpty(classifies)) {
                //查询所有三级分类
                classifies.forEach(a -> {
                    a.setChildren(cereProductProviderClassifyDAO.findByPid(a.getClassifyId()));
                });
                result.setChildren(classifies);
            }
        }
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public List<ProductProviderClassify> getByPid(Long classifyPID) throws CoBusinessException {
        return cereProductProviderClassifyDAO.findByPid(classifyPID);
    }

    private void addOneClassify(ProductProviderClassify classify, String time, List<CereProductProviderClassify> updates) throws CoBusinessException {
        if (EmptyUtils.isEmpty(classify.getClassifyName())) {
            throw new CoBusinessException(CoReturnFormat.CLASSIFY_NAME_NULL);
        }
        // 初始化数据
        CereProductProviderClassify cereCustomerClassify = new CereProductProviderClassify();
        cereCustomerClassify.setClassifyId(classify.getClassifyId());
        cereCustomerClassify.setClassifyPid(LongEnum.ROOT.getCode());
        cereCustomerClassify.setClassifyName(classify.getClassifyName());
        cereCustomerClassify.setClassifyLevel(IntegerEnum.CLASSIFY_LEVEL_ONE.getCode());
        cereCustomerClassify.setClassifyHierarchy("-" + classify.getClassifyName());
        cereCustomerClassify.setDescription(classify.getDescription());
        if (classify.getSort() != null) {
            cereCustomerClassify.setSort(classify.getSort());
        } else {
            List<ProductProviderClassify> clist = cereProductProviderClassifyDAO.findByPid(0L);
            int sortNum = clist.stream()
                    .mapToInt(ProductProviderClassify::getSort)
                    .max()
                    .orElse(0);
            cereCustomerClassify.setSort(sortNum + 10);
        }

        if (!EmptyUtils.isEmpty(classify.getClassifyId())) {
            //更新一级类别
            cereCustomerClassify.setUpdateTime(time);
            cereCustomerClassify.setClassifyLevelHierarchy("/" + cereCustomerClassify.getClassifyId());
            cereProductProviderClassifyDAO.updateByPrimaryKeySelective(cereCustomerClassify);
        } else {
            //新增一级类别
            cereCustomerClassify.setCreateTime(time);
            cereProductProviderClassifyDAO.insert(cereCustomerClassify);
            cereCustomerClassify.setClassifyLevelHierarchy("/" + cereCustomerClassify.getClassifyId());
            updates.add(cereCustomerClassify);
        }
        if (!EmptyUtils.isEmpty(classify.getChildren())) {
            //新增子级类别
            for (ProductProviderClassify customerClassify : classify.getChildren()) {
                addChildClassify(cereCustomerClassify,
                        updates, time, customerClassify, IntegerEnum.CLASSIFY_LEVEL_TWO.getCode());
            }
        }
    }


    private void addChildClassify(CereProductProviderClassify parent, List<CereProductProviderClassify> updates,
                                  String time, ProductProviderClassify child, Integer level) throws CoBusinessException {
        if (EmptyUtils.isEmpty(child.getClassifyName())) {
            throw new CoBusinessException(CoReturnFormat.CLASSIFY_NAME_NULL);
        }
        // 初始化数据
        CereProductProviderClassify cereCustomerClassify = new CereProductProviderClassify();
        cereCustomerClassify.setClassifyId(child.getClassifyId());
        cereCustomerClassify.setClassifyPid(parent.getClassifyId());
        cereCustomerClassify.setClassifyName(child.getClassifyName());
        cereCustomerClassify.setClassifyLevel(level);
        cereCustomerClassify.setClassifyHierarchy(parent.getClassifyHierarchy() + "-" + child.getClassifyName());
        cereCustomerClassify.setDescription(child.getDescription());
        System.out.println(child);
        System.out.println(child.getSort());
        if (child.getSort() != null) {
            cereCustomerClassify.setSort(child.getSort());
        } else {
            List<ProductProviderClassify> clist = cereProductProviderClassifyDAO.findByPid(parent.getClassifyId());
            int sortNum = clist.stream()
                    .mapToInt(ProductProviderClassify::getSort)
                    .max()
                    .orElse(0);
            cereCustomerClassify.setSort(sortNum + 10);
        }
        System.out.println(cereCustomerClassify);

        if (!EmptyUtils.isEmpty(child.getClassifyId())) {
            //更新子级类别
            cereCustomerClassify.setUpdateTime(time);
            cereCustomerClassify.setClassifyLevelHierarchy(parent.getClassifyLevelHierarchy() + "/" + cereCustomerClassify.getClassifyId());
            cereProductProviderClassifyDAO.updateByPrimaryKeySelective(cereCustomerClassify);
        } else {
            //新增子级类别
            cereCustomerClassify.setCreateTime(time);
            cereProductProviderClassifyDAO.insert(cereCustomerClassify);
            cereCustomerClassify.setClassifyLevelHierarchy(parent.getClassifyLevelHierarchy() + "/" + cereCustomerClassify.getClassifyId());
            updates.add(cereCustomerClassify);
        }

        // 递归
        if (!EmptyUtils.isEmpty(child.getChildren())) {
            //新增子级类别
            for (ProductProviderClassify classify : child.getChildren()) {
                addChildClassify(cereCustomerClassify, updates, time, classify, IntegerEnum.CLASSIFY_LEVEL_THREE.getCode());
            }
        }
    }
}

