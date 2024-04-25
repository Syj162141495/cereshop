/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.init.impl;

import com.shop.cereshop.business.service.init.InitPermissionService;
import com.shop.cereshop.business.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.business.service.role.CerePlatformRolePermissionService;
import com.shop.cereshop.business.service.shop.CerePlatformShopService;
import com.shop.cereshop.commons.domain.business.CereBusinessShop;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
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
public class InitPermissionServiceImpl implements InitPermissionService {

    @Autowired
    private CerePlatformPermissionService cerePlatformPermissionService;

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    @Autowired
    private CerePlatformRolePermissionService cerePlatformRolePermissionService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void initPermission() throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //查询当前最大排序值
        int sort=cerePlatformPermissionService.findMaxSort();
        //查询所有店铺id数据
        List<CereBusinessShop> ids=cerePlatformShopService.findIds();
        if(!EmptyUtils.isEmpty(ids)){
            for (CereBusinessShop businessShop : ids) {
                //根据店铺id查询店铺角色id
                Long roleId=cerePlatformShopService.findShopRoleId(businessShop.getShopId());
                if(!EmptyUtils.isEmpty(roleId)){
                    //校验是否已存在营销活动菜单
                    Long permissionId=cerePlatformRolePermissionService.check(roleId);
                    if(!EmptyUtils.isLongEmpty(permissionId)){
                        continue;
                    }
                    List<CerePlatformRolePermission> list=new ArrayList<>();
                    //封装营销活动菜单数据
                    CerePlatformPermission permission1=new CerePlatformPermission();
                    permission1.setPermissionPid(0l);
                    permission1.setPermissionName("营销活动");
                    permission1.setPermission("/marketing");
                    permission1.setIcon("el-icon-stopwatch");
                    permission1.setResourceType("catalog");
                    permission1.setProject(businessShop.getShopId());
                    permission1.setSort(++sort);
                    permission1.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission1);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole1=new CerePlatformRolePermission();
                    cereBusinessUserRole1.setRoleId(roleId);
                    cereBusinessUserRole1.setPermissionId(permission1.getPermissionId());
                    list.add(cereBusinessUserRole1);
                    //封装优惠券管理菜单数据
                    CerePlatformPermission permission2=new CerePlatformPermission();
                    permission2.setPermissionPid(permission1.getPermissionId());
                    permission2.setPermissionName("优惠券管理");
                    permission2.setPermission("/marketing/coupon");
                    permission2.setIcon("el-icon-s-goods");
                    permission2.setResourceType("menu");
                    permission2.setProject(businessShop.getShopId());
                    permission2.setSort(++sort);
                    permission2.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission2);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole2=new CerePlatformRolePermission();
                    cereBusinessUserRole2.setRoleId(roleId);
                    cereBusinessUserRole2.setPermissionId(permission2.getPermissionId());
                    list.add(cereBusinessUserRole2);
                    //封装拼团菜单数据
                    CerePlatformPermission permission3=new CerePlatformPermission();
                    permission3.setPermissionPid(permission1.getPermissionId());
                    permission3.setPermissionName("拼团");
                    permission3.setPermission("/marketing/groupBuy");
                    permission3.setIcon("el-icon-suitcase-1");
                    permission3.setResourceType("menu");
                    permission3.setProject(businessShop.getShopId());
                    permission3.setSort(++sort);
                    permission3.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission3);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole3=new CerePlatformRolePermission();
                    cereBusinessUserRole3.setRoleId(roleId);
                    cereBusinessUserRole3.setPermissionId(permission3.getPermissionId());
                    list.add(cereBusinessUserRole3);
                    //封装秒杀菜单数据
                    CerePlatformPermission permission4=new CerePlatformPermission();
                    permission4.setPermissionPid(permission1.getPermissionId());
                    permission4.setPermissionName("秒杀");
                    permission4.setPermission("/marketing/spike");
                    permission4.setIcon("el-icon-alarm-clock");
                    permission4.setResourceType("menu");
                    permission4.setProject(businessShop.getShopId());
                    permission4.setSort(++sort);
                    permission4.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission4);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole4=new CerePlatformRolePermission();
                    cereBusinessUserRole4.setRoleId(roleId);
                    cereBusinessUserRole4.setPermissionId(permission4.getPermissionId());
                    list.add(cereBusinessUserRole4);
                    //封装限时折扣菜单数据
                    CerePlatformPermission permission5=new CerePlatformPermission();
                    permission5.setPermissionPid(permission1.getPermissionId());
                    permission5.setPermissionName("限时折扣");
                    permission5.setPermission("/marketing/discount");
                    permission5.setIcon("el-icon-alarm-clock");
                    permission5.setResourceType("menu");
                    permission5.setProject(businessShop.getShopId());
                    permission5.setSort(++sort);
                    permission5.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission5);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole5=new CerePlatformRolePermission();
                    cereBusinessUserRole5.setRoleId(roleId);
                    cereBusinessUserRole5.setPermissionId(permission5.getPermissionId());
                    list.add(cereBusinessUserRole5);
                    //封装客户管理菜单数据
                    CerePlatformPermission permission6=new CerePlatformPermission();
                    permission6.setPermissionPid(0l);
                    permission6.setPermissionName("客户管理");
                    permission6.setPermission("/customer");
                    permission6.setIcon("el-icon-user");
                    permission6.setResourceType("catalog");
                    permission6.setProject(businessShop.getShopId());
                    permission6.setSort(++sort);
                    permission6.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission6);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole6=new CerePlatformRolePermission();
                    cereBusinessUserRole6.setRoleId(roleId);
                    cereBusinessUserRole6.setPermissionId(permission6.getPermissionId());
                    list.add(cereBusinessUserRole6);
                    //封装客户列表菜单数据
                    CerePlatformPermission permission7=new CerePlatformPermission();
                    permission7.setPermissionPid(permission6.getPermissionId());
                    permission7.setPermissionName("客户列表");
                    permission7.setPermission("/customer/customerList");
                    permission7.setIcon("el-icon-user");
                    permission7.setResourceType("menu");
                    permission7.setProject(businessShop.getShopId());
                    permission7.setSort(++sort);
                    permission7.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission7);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole7=new CerePlatformRolePermission();
                    cereBusinessUserRole7.setRoleId(roleId);
                    cereBusinessUserRole7.setPermissionId(permission7.getPermissionId());
                    list.add(cereBusinessUserRole6);
                    //封装标签列表菜单数据
                    CerePlatformPermission permission8=new CerePlatformPermission();
                    permission8.setPermissionPid(permission6.getPermissionId());
                    permission8.setPermissionName("标签列表");
                    permission8.setPermission("/customer/tagList");
                    permission8.setIcon("el-icon-collection-tag");
                    permission8.setResourceType("menu");
                    permission8.setProject(businessShop.getShopId());
                    permission8.setSort(++sort);
                    permission8.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission8);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole8=new CerePlatformRolePermission();
                    cereBusinessUserRole8.setRoleId(roleId);
                    cereBusinessUserRole8.setPermissionId(permission8.getPermissionId());
                    list.add(cereBusinessUserRole8);
                    //封装客户分群菜单数据
                    CerePlatformPermission permission9=new CerePlatformPermission();
                    permission9.setPermissionPid(permission6.getPermissionId());
                    permission9.setPermissionName("客户分群");
                    permission9.setPermission("/customer/clusteringList");
                    permission9.setIcon("el-icon-connection");
                    permission9.setResourceType("menu");
                    permission9.setProject(businessShop.getShopId());
                    permission9.setSort(++sort);
                    permission9.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission9);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole9=new CerePlatformRolePermission();
                    cereBusinessUserRole9.setRoleId(roleId);
                    cereBusinessUserRole9.setPermissionId(permission9.getPermissionId());
                    list.add(cereBusinessUserRole9);
                    //封装运营计划菜单数据
                    CerePlatformPermission permission10=new CerePlatformPermission();
                    permission10.setPermissionPid(permission6.getPermissionId());
                    permission10.setPermissionName("运营计划");
                    permission10.setPermission("/customer/operate");
                    permission10.setIcon("el-icon-time");
                    permission10.setResourceType("menu");
                    permission10.setProject(businessShop.getShopId());
                    permission10.setSort(++sort);
                    permission10.setCreateTime(time);
                    cerePlatformPermissionService.insert(permission10);
                    //封装用户关联角色表数据
                    CerePlatformRolePermission cereBusinessUserRole10=new CerePlatformRolePermission();
                    cereBusinessUserRole10.setRoleId(roleId);
                    cereBusinessUserRole10.setPermissionId(permission10.getPermissionId());
                    list.add(cereBusinessUserRole10);
                    //插入关联数据
                    cerePlatformRolePermissionService.insertBatch(list);
                }
            }
        }
    }
}
