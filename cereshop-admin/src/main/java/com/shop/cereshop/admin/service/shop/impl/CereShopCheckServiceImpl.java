/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.shop.CereShopCheckDAO;
import com.shop.cereshop.admin.page.permission.MenuButton;
import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.shopcheck.CheckDeleteParam;
import com.shop.cereshop.admin.param.shopcheck.CheckGetAllParam;
import com.shop.cereshop.admin.param.shopcheck.CheckHandleParam;
import com.shop.cereshop.admin.service.business.CereBusinessUserRoleService;
import com.shop.cereshop.admin.service.dict.CerePlatformDictService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.admin.service.role.CerePlatformRolePermissionService;
import com.shop.cereshop.admin.service.role.CerePlatformRoleService;
import com.shop.cereshop.admin.service.shop.*;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CereBusinessUserRole;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
import com.shop.cereshop.commons.domain.shop.*;
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
public class CereShopCheckServiceImpl implements CereShopCheckService {

    /**
     * 全局菜单计量id
     */
    private Long id;

    @Autowired
    private CereShopCheckDAO cereShopCheckDAO;

    @Autowired
    private CerePlatformShopService cerePlatformShopService;

    @Autowired
    private CereShopPersonalService cereShopPersonalService;

    @Autowired
    private CereShopIndividualBusinessesService cereShopIndividualBusinessesService;

    @Autowired
    private CereShopEnterpriseService cereShopEnterpriseService;

    @Autowired
    private CereShopOtherOrganizationsService cereShopOtherOrganizationsService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    @Autowired
    private CerePlatformPermissionService cerePlatformPermissionService;

    @Autowired
    private CerePlatformRoleService cerePlatformRoleService;

    @Autowired
    private CereBusinessUserRoleService cereBusinessUserRoleService;

    @Autowired
    private CerePlatformRolePermissionService cerePlatformRolePermissionService;

    @Override
    public Page getAll(CheckGetAllParam param) throws CoBusinessException {
        List<Shop> list=null;
        PageHelper.startPage(param.getPage(),param.getPageSize());
        if(IntegerEnum.UNTREATED.getCode().equals(param.getCheckState())){
            //查询未处理入驻数据
            list= cereShopCheckDAO.getStayAll(param);
        }else {
            //查询已处理入驻数据
            list= cereShopCheckDAO.getAll(param);
        }
        PageInfo<Shop> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Shop getById(Long shopId) throws CoBusinessException {
        //查询店铺信息
        Shop shop=cerePlatformShopService.findShop(shopId);
        if(shop!=null){
            //查询认证数据
            if(IntegerEnum.PERSONAL.getCode().equals(shop.getAuthenType())){
                //个人认证
                CereShopPersonal personal = cereShopPersonalService.findByShopId(shopId);
                if(personal!=null){
                    shop.setServicePhone(personal.getServicePhone());
                    shop.setName(personal.getPersonalName());
                    shop.setCardType(personal.getPersonalCardType());
                    shop.setIdCard(personal.getPersonalIdCard());
                    shop.setCardTime(personal.getPersonalCardStartTime()+"-"+personal.getPersonalCardEndTime());
                    shop.setCardPositive(personal.getPersonalCardPositive());
                    shop.setCardSide(personal.getPersonalCardSide());
                    shop.setCardHand(personal.getPersonalCardHand());
                }
            }else if(IntegerEnum.INDIVIDUAL.getCode().equals(shop.getAuthenType())){
                //个体工商户户认证
                CereShopIndividualBusinesses businesses = cereShopIndividualBusinessesService.findByShopId(shopId);
                if(businesses!=null){
                    shop.setServicePhone(businesses.getServicePhone());
                    shop.setName(businesses.getSubjectOperator());
                    shop.setCardType(businesses.getSubjectCardType());
                    shop.setIdCard(businesses.getSubjectIdCard());
                    shop.setCardTime(businesses.getSubjectCardStartTime()+"-"+businesses.getSubjectCardEndTime());
                    shop.setCardPositive(businesses.getSubjectCardPositive());
                    shop.setCardSide(businesses.getSubjectCardSide());
                    shop.setSubjectName(businesses.getSubjectName());
                    shop.setSubjectCode(businesses.getSubjectCode());
                    shop.setSubjectAdress(businesses.getSubjectRegion());
                    shop.setAdress(businesses.getSubjectAdress());
                    shop.setSubjectStartTime(businesses.getSubjectStartTime());
                    shop.setSubjectEndTime(businesses.getSubjectEndTime());
                    shop.setSubjectLicense(EmptyUtils.getImages(businesses.getSubjectLicense()));
                }
            }else if(IntegerEnum.ENTERPRICE.getCode().equals(shop.getAuthenType())){
                //企业认证
                CereShopEnterprise enterprise = cereShopEnterpriseService.findByShopId(shopId);
                if(enterprise!=null){
                    shop.setServicePhone(enterprise.getServicePhone());
                    shop.setName(enterprise.getEnterpriseOperator());
                    shop.setCardType(enterprise.getEnterpriseCardType());
                    shop.setIdCard(enterprise.getEnterpriseIdCard());
                    shop.setCardTime(enterprise.getEnterpriseCardStartTime()+"-"+enterprise.getEnterpriseCardEndTime());
                    shop.setCardPositive(enterprise.getEnterpriseCardPositive());
                    shop.setCardSide(enterprise.getEnterpriseCardSide());
                    shop.setSubjectName(enterprise.getEnterpriseName());
                    shop.setSubjectCode(enterprise.getEnterpriseCode());
                    shop.setSubjectAdress(enterprise.getEnterpriseRegion());
                    shop.setAdress(enterprise.getEnterpriseAdress());
                    shop.setSubjectStartTime(enterprise.getEnterpriseStartTime());
                    shop.setSubjectEndTime(enterprise.getEnterpriseEndTime());
                    shop.setSubjectLicense(EmptyUtils.getImages(enterprise.getEnterpriseLicense()));
                }
            }else if(IntegerEnum.ORGANIZATION.getCode().equals(shop.getAuthenType())){
                //其他组织认证
                CereShopOtherOrganizations otherOrganizations = cereShopOtherOrganizationsService.findByShopId(shopId);
                if(otherOrganizations!=null){
                    shop.setServicePhone(otherOrganizations.getServicePhone());
                    shop.setName(otherOrganizations.getOtherOperator());
                    shop.setCardType(otherOrganizations.getOtherCardType());
                    shop.setIdCard(otherOrganizations.getOtherIdCard());
                    shop.setCardTime(otherOrganizations.getOtherCardStartTime()+"-"+otherOrganizations.getOtherCardEndTime());
                    shop.setCardPositive(otherOrganizations.getOtherCardPositive());
                    shop.setCardSide(otherOrganizations.getOtherCardSide());
                    shop.setSubjectName(otherOrganizations.getOtherName());
                    shop.setSubjectCode(otherOrganizations.getOtherCode());
                    shop.setSubjectAdress(otherOrganizations.getOtherRegion());
                    shop.setAdress(otherOrganizations.getOtherAdress());
                    shop.setSubjectStartTime(otherOrganizations.getOtherStartTime());
                    shop.setSubjectEndTime(otherOrganizations.getOtherEndTime());
                    shop.setSubjectLicense(EmptyUtils.getImages(otherOrganizations.getOtherLicense()));
                }
            }
            //查询证件类型名称
            shop.setCardTypeName(cerePlatformDictService.findNameById(shop.getCardType()));
        }
        return shop;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(CheckHandleParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //校验生效日期
        if(!EmptyUtils.isEmpty(param.getEffectiveDate())&&TimeUtils.compareTo(time,param.getEffectiveDate()+" 23:59:59")){
            //如果当前时间大于生效日期,提示不能选择以往日期,入驻时间直接过期
            throw new CoBusinessException(CoReturnFormat.SHOP_CHECK_TIME_OVERDUE);
        }
        //新增入驻申请处理数据
        CereShopCheck cereShopCheck=new CereShopCheck();
        cereShopCheck.setShopId(param.getShopId());
        cereShopCheck.setCheckHandle(param.getCheckHandle());
        cereShopCheck.setReason(param.getReason());
        cereShopCheck.setCreateTime(time);
        cereShopCheckDAO.insert(cereShopCheck);
        String describe="";
        CerePlatformShop cerePlatformShop=new CerePlatformShop();
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShop.setShopId(param.getShopId());
        cerePlatformShop.setEffectiveDate(param.getEffectiveDate());
        cerePlatformShop.setEffectiveYear(param.getEffectiveYear());
        if(IntegerEnum.SHOP_CHECK_SUCCESS.getCode().equals(param.getCheckHandle())){
            describe="同意入驻";
            //同意入驻申请
            //更新店铺生效日期数据
            cerePlatformShop.setCheckState(IntegerEnum.ADOPT.getCode());
            //初始化商家端角色、菜单权限数据
            setPermission(param.getShopId(),time);
            //发送短信
        }else {
            describe="拒绝入驻";
            //拒绝入驻
            //更新店铺审核状态
            cerePlatformShop.setCheckState(IntegerEnum.REFUSE.getCode());
            //发送短信
        }
        cerePlatformShopService.updateDate(cerePlatformShop);
        //新增日志
        cerePlatformLogService.addLog(user,"入驻申请管理","平台端操作",describe,String.valueOf(cereShopCheck.getShopId()),time);
    }

    private void setPermission(Long shopId,String time) throws CoBusinessException{
        //新增一个管理员角色数据
        CerePlatformRole role=new CerePlatformRole();
        role.setProject(shopId);
        role.setCreateTime(time);
        role.setRoleName("管理员");
        cerePlatformRoleService.insert(role);
        //查询店铺账号id
        Long businessId=cerePlatformShopService.findBusinessId(shopId);
        //新增店铺关联角色数据
        CereBusinessUserRole cereBusinessUserRole=new CereBusinessUserRole();
        cereBusinessUserRole.setBusinessUserId(businessId);
        cereBusinessUserRole.setRoleId(role.getRoleId());
        cereBusinessUserRoleService.insert(cereBusinessUserRole);
        //查询权限层级数据
        List<MenuButton> list=cerePlatformPermissionService.findAllPermissionShop();
        //查询当前最大排序值
        int sort=cerePlatformPermissionService.getMaxSort();
        //定义新增的权限数据集合
        List<CerePlatformPermission> permissions=new ArrayList<>();
        //定义新增的角色关联权限数据集合
        List<CerePlatformRolePermission> rolePermissions=new ArrayList<>();
        //查询最大菜单权限id+10
        id=cerePlatformPermissionService.getMaxId();
        //插入权限数据
        addPermision(list,shopId,time,sort,role.getRoleId(),permissions,rolePermissions);
        if(!EmptyUtils.isEmpty(permissions)){
            //批量插入菜单权限数据
            cerePlatformPermissionService.insertBatch(permissions);
        }
        if(!EmptyUtils.isEmpty(rolePermissions)){
            //批量插入角色关联菜单权限数据
            cerePlatformRolePermissionService.insertBatch(rolePermissions);
        }
    }

    public void setInitPermission(Long shopId,String time) throws CoBusinessException{
        //新增一个管理员角色数据
        CerePlatformRole role=new CerePlatformRole();
        role.setProject(shopId);
        role.setCreateTime(time);
        role.setRoleName("管理员");
        cerePlatformRoleService.insert(role);
        //查询店铺账号id
        Long businessId=cerePlatformShopService.findBusinessId(shopId);
        //新增店铺关联角色数据
        CereBusinessUserRole cereBusinessUserRole=new CereBusinessUserRole();
        cereBusinessUserRole.setBusinessUserId(businessId);
        cereBusinessUserRole.setRoleId(role.getRoleId());
        cereBusinessUserRoleService.insert(cereBusinessUserRole);
        //查询权限层级数据
        List<MenuButton> list=cerePlatformPermissionService.findAllPermissionShop();
        //查询当前最大排序值
        int sort=cerePlatformPermissionService.getMaxSort();
        //定义新增的权限数据集合
        List<CerePlatformPermission> permissions=new ArrayList<>();
        //定义新增的角色关联权限数据集合
        List<CerePlatformRolePermission> rolePermissions=new ArrayList<>();
        //查询最大权限id+10
        id=cerePlatformPermissionService.getMaxId();
        //插入权限数据
        addPermision(list,shopId,time,sort,role.getRoleId(),permissions,rolePermissions);
        if(!EmptyUtils.isEmpty(permissions)){
            //批量插入菜单权限数据
            cerePlatformPermissionService.insertBatch(permissions);
        }
        if(!EmptyUtils.isEmpty(rolePermissions)){
            //批量插入角色关联菜单权限数据
            cerePlatformRolePermissionService.insertBatch(rolePermissions);
        }
    }

    private void addPermision(List<MenuButton> list, Long shopId, String time,int sort,
                              Long roleId,List<CerePlatformPermission> permissions,
                              List<CerePlatformRolePermission> rolePermissions) throws CoBusinessException {
        for (int i = 0; i < list.size(); i++) {
            id++;
            Long parentId=id;
            MenuButton menuButton = list.get(i);
            //新增父级权限数据
            CerePlatformPermission permission=new CerePlatformPermission();
            permission.setPermissionId(parentId);
            permission.setPermissionPid(menuButton.getPermissionPid());
            permission.setPermissionName(menuButton.getPermissionName());
            permission.setIcon(menuButton.getIcon());
            permission.setPermission(menuButton.getPath());
            permission.setResourceType(menuButton.getResourceType());
            permission.setProject(shopId);
            permission.setCreateTime(time);
            sort+=1;
            permission.setSort(sort);
            permissions.add(permission);
            //新增角色关联权限数据
            CerePlatformRolePermission cerePlatformRolePermission=new CerePlatformRolePermission();
            cerePlatformRolePermission.setPermissionId(permission.getPermissionId());
            cerePlatformRolePermission.setRoleId(roleId);
            rolePermissions.add(cerePlatformRolePermission);
            if(!EmptyUtils.isEmpty(menuButton.getButtons())){
                //设置自己按钮数据
                for (int j = 0; j < menuButton.getButtons().size(); j++) {
                    id++;
                    MenuButton button = menuButton.getButtons().get(j);
                    CerePlatformPermission buttonPermission=new CerePlatformPermission();
                    buttonPermission.setPermissionId(id);
                    buttonPermission.setPermissionPid(parentId);
                    buttonPermission.setPermissionName(button.getPermissionName());
                    buttonPermission.setIcon(button.getIcon());
                    buttonPermission.setPermission(button.getPath());
                    buttonPermission.setResourceType(button.getResourceType());
                    buttonPermission.setProject(shopId);
                    buttonPermission.setCreateTime(time);
                    sort+=1;
                    buttonPermission.setSort(sort);
                    permissions.add(buttonPermission);
                    //新增角色关联权限数据
                    CerePlatformRolePermission cerePlatformRolePermission1=new CerePlatformRolePermission();
                    cerePlatformRolePermission1.setPermissionId(buttonPermission.getPermissionId());
                    cerePlatformRolePermission1.setRoleId(roleId);
                    rolePermissions.add(cerePlatformRolePermission1);
                }
            }
            if(!EmptyUtils.isEmpty(menuButton.getChildren())){
                setTwoPermission(permission.getPermissionId(),shopId,time,sort,roleId,menuButton.getChildren(),permissions,rolePermissions);
            }
        }
    }

    private void setTwoPermission(Long permissionId, Long shopId, String time,
                                  int sort, Long roleId,List<MenuButton> list,
                                  List<CerePlatformPermission> permissions,
                                  List<CerePlatformRolePermission> rolePermissions) throws CoBusinessException{
        for (int i = 0; i < list.size(); i++) {
            id++;
            Long parentId=id;
            MenuButton menuButton = list.get(i);
            //新增父级权限数据
            CerePlatformPermission permission=new CerePlatformPermission();
            permission.setPermissionId(parentId);
            permission.setPermissionPid(permissionId);
            permission.setPermissionName(menuButton.getPermissionName());
            permission.setIcon(menuButton.getIcon());
            permission.setPermission(menuButton.getPath());
            permission.setResourceType(menuButton.getResourceType());
            permission.setProject(shopId);
            permission.setCreateTime(time);
            sort+=1;
            permission.setSort(sort);
            permissions.add(permission);
            //新增角色关联权限数据
            CerePlatformRolePermission cerePlatformRolePermission=new CerePlatformRolePermission();
            cerePlatformRolePermission.setPermissionId(permission.getPermissionId());
            cerePlatformRolePermission.setRoleId(roleId);
            rolePermissions.add(cerePlatformRolePermission);
            if(!EmptyUtils.isEmpty(menuButton.getButtons())){
                //设置自己按钮数据
                for (int j = 0; j < menuButton.getButtons().size(); j++) {
                    id++;
                    MenuButton button = menuButton.getButtons().get(j);
                    CerePlatformPermission buttonPermission=new CerePlatformPermission();
                    buttonPermission.setPermissionId(id);
                    buttonPermission.setPermissionPid(parentId);
                    buttonPermission.setPermissionName(button.getPermissionName());
                    buttonPermission.setIcon(button.getIcon());
                    buttonPermission.setPermission(button.getPath());
                    buttonPermission.setResourceType(button.getResourceType());
                    buttonPermission.setProject(shopId);
                    buttonPermission.setCreateTime(time);
                    sort+=1;
                    buttonPermission.setSort(sort);
                    permissions.add(buttonPermission);
                    //新增角色关联权限数据
                    CerePlatformRolePermission cerePlatformRolePermission1=new CerePlatformRolePermission();
                    cerePlatformRolePermission1.setPermissionId(buttonPermission.getPermissionId());
                    cerePlatformRolePermission1.setRoleId(roleId);
                    rolePermissions.add(cerePlatformRolePermission1);
                }
            }
            if(!EmptyUtils.isEmpty(menuButton.getChildren())){
                setTwoPermission(permission.getPermissionId(),shopId,time,sort,roleId,menuButton.getChildren(),permissions,rolePermissions);
            }
        }
    }

    @Override
    public void delete(CheckDeleteParam param, CerePlatformUser user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cereShopCheckDAO.deleteById(param.getCheckId());
        //新增日志
        cerePlatformLogService.addLog(user,"入驻申请管理","平台端操作","删除入驻处理记录",String.valueOf(param.getCheckId()),time);
    }
}
