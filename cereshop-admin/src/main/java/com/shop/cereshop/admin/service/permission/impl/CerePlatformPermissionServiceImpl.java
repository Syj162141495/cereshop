/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.permission.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.permission.CerePlatformPermissionDAO;
import com.shop.cereshop.admin.page.permission.MenuButton;
import com.shop.cereshop.admin.page.permission.Meta;
import com.shop.cereshop.admin.page.permission.Permission;
import com.shop.cereshop.admin.page.permission.RolePermission;
import com.shop.cereshop.admin.param.permission.PermissionDeleteParam;
import com.shop.cereshop.admin.param.permission.PermissionGetAllParam;
import com.shop.cereshop.admin.param.permission.PermissionSaveParam;
import com.shop.cereshop.admin.param.permission.PermissionUpdateParam;
import com.shop.cereshop.admin.param.role.RoleDistributionParam;
import com.shop.cereshop.admin.param.user.UserBuildParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.permission.CerePlatformPermissionService;
import com.shop.cereshop.admin.service.role.CerePlatformRolePermissionService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.LongEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
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
import java.util.stream.Collectors;

@Service
public class CerePlatformPermissionServiceImpl implements CerePlatformPermissionService {

    @Autowired
    private CerePlatformPermissionDAO cerePlatformPermissionDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CerePlatformRolePermissionService cerePlatformRolePermissionService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(PermissionSaveParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformPermission permission=new CerePlatformPermission();
        permission.setPermissionPid(param.getPermissionPid());
        permission.setPermission(param.getPermission());
        permission.setPermissionName(param.getPermissionName());
        permission.setPermissionUri(param.getPermissionUri());
        permission.setDescribe(param.getDescribe());
        permission.setIcon(param.getIcon());
        permission.setResourceType(param.getResourceType());
        permission.setSort(param.getSort());
        if(EmptyUtils.isEmpty(permission.getPermissionPid())){
            //如果没有父节点id,新增根节点
            permission.setPermissionPid(LongEnum.getByName("根节点"));
        }
        permission.setCreateTime(time);
        permission.setProject(LongEnum.PLATFORM.getCode());

        cerePlatformPermissionDAO.insert(permission);
        //新增日志
        cerePlatformLogService.addLog(user,"权限管理","平台端操作","添加权限",String.valueOf(permission.getPermissionId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(PermissionUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformPermission permission=new CerePlatformPermission();
        permission.setPermissionPid(param.getPermissionPid());
        permission.setPermission(param.getPermission());
        permission.setPermissionName(param.getPermissionName());
        permission.setPermissionUri(param.getPermissionUri());
        permission.setDescribe(param.getDescribe());
        permission.setIcon(param.getIcon());
        permission.setResourceType(param.getResourceType());
        permission.setSort(param.getSort());
        permission.setPermissionId(param.getPermissionId());
        permission.setUpdateTime(time);
        cerePlatformPermissionDAO.updateByPrimaryKeySelective(permission);
        //新增日志
        cerePlatformLogService.addLog(user,"权限管理","平台端操作","修改权限",String.valueOf(permission.getPermissionId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(PermissionDeleteParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        cerePlatformPermissionDAO.deleteByPrimaryKey(param.getPermissionId());
        //删除角色关联菜单数据
        cerePlatformRolePermissionService.deleteByPermissionId(param.getPermissionId());
        //新增日志
        cerePlatformLogService.addLog(user,"权限管理","平台端操作","删除权限",String.valueOf(param.getPermissionId()),time);
    }

    @Override
    public CerePlatformPermission getById(Long permissionId) throws CoBusinessException {
        return cerePlatformPermissionDAO.getById(permissionId);
    }

    @Override
    public Page getAll(PermissionGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        //查询所有根节点菜单权限
        List<Permission> list=cerePlatformPermissionDAO.findAllByResourceType(StringEnum.CATALOG.getCode(),param.getPermissionName());
        //查询所有子节点菜单和按钮权限
        List<Permission> childs=cerePlatformPermissionDAO.findChilds(param.getPermissionName());
        if(!EmptyUtils.isEmpty(list)){
            Map<String,Integer> map=new HashMap<>();
            list.forEach((permission -> {
                setMenuChildsIndex(permission,childs,map,null);
            }));
        }
        PageInfo<Permission> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public RolePermission getRolePermission(RoleDistributionParam param) throws CoBusinessException{
        RolePermission rolePermission=new RolePermission();
        //查询所有根节点菜单权限
        List<Permission> list=cerePlatformPermissionDAO.findAllByResourceType(StringEnum.CATALOG.getCode(),null);
        //查询所有子节点菜单权限
        List<Permission> childs=cerePlatformPermissionDAO.findChilds(null);
        if(!EmptyUtils.isEmpty(list)){
            Map<String,Integer> map=new HashMap<>();
            list.forEach((permission -> {
                setMenuChildsIndex(permission,childs,map,null);
            }));
        }
        rolePermission.setPermissions(list);
        //查询该角色已添加的菜单权限id
        rolePermission.setIds(cerePlatformPermissionDAO.findRolePermissionIds(param.getRoleId()));
        return rolePermission;
    }

    @Override
    public List<MenuButton> getAllByUserId(UserBuildParam user) throws CoBusinessException {
        //根据用户id查询所有根节点菜单权限
        List<MenuButton> list=cerePlatformPermissionDAO.findAllCatalogByUserIdAndResourceType(user.getPlatformUserId(),StringEnum.CATALOG.getCode());
        //根据用户id查询所有子节点菜单权限
        List<MenuButton> childs=cerePlatformPermissionDAO.findAllCatalogByUserIdAndResourceType(user.getPlatformUserId(),StringEnum.MENU.getCode());
        //查询所有按钮权限
        List<MenuButton> buttons=cerePlatformPermissionDAO.findAllCatalogByUserIdAndResourceType(user.getPlatformUserId(),StringEnum.BUTTON.getCode());
        if(!EmptyUtils.isEmpty(list)){
            Map<String,Integer> map=new HashMap<>();
            list.forEach((permission -> {
                Meta meta=new Meta();
                meta.setTitle(permission.getPermissionName());
                meta.setIcon(permission.getIcon());
                permission.setMeta(meta);
                setUserMenuChildsIndex(permission,childs,map,buttons);
            }));
        }
        return list;
    }

    @Override
    public List<MenuButton> findAllPermissionShop(){
        //根据用户id查询所有根节点菜单权限
        List<MenuButton> list=cerePlatformPermissionDAO.findAllCatalogByResourceType(StringEnum.CATALOG.getCode());
        //根据用户id查询所有子节点菜单权限
        List<MenuButton> childs=cerePlatformPermissionDAO.findAllCatalogByResourceType(StringEnum.MENU.getCode());
        //查询所有按钮权限
        List<MenuButton> buttons=cerePlatformPermissionDAO.findAllCatalogByResourceType(StringEnum.BUTTON.getCode());
        if(!EmptyUtils.isEmpty(list)){
            Map<String,Integer> map=new HashMap<>();
            list.forEach((permission -> {
                Meta meta=new Meta();
                meta.setTitle(permission.getPermissionName());
                meta.setIcon(permission.getIcon());
                permission.setMeta(meta);
                setUserMenuChildsIndex(permission,childs,map,buttons);
            }));
        }
        return list;
    }

    @Override
    public void insert(CerePlatformPermission permission) throws CoBusinessException {
        cerePlatformPermissionDAO.insert(permission);
    }

    @Override
    public Long getMaxId() {
        return cerePlatformPermissionDAO.getMaxId();
    }

    @Override
    public void insertBatch(List<CerePlatformPermission> permissions) throws CoBusinessException {
        cerePlatformPermissionDAO.insertBatch(permissions);
    }

    @Override
    public CerePlatformPermission findBySort(Integer sort,Long id) {
        return cerePlatformPermissionDAO.findBySort(sort,id);
    }

    @Override
    public Integer getMaxSort() throws CoBusinessException {
        return cerePlatformPermissionDAO.getMaxSort();
    }

    private MenuButton setUserMenuChildsIndex(MenuButton parent, List<MenuButton> all,
                                              Map<String,Integer> map,List<MenuButton> buttons) throws ArrayIndexOutOfBoundsException{
        if(!parent.getResourceType().equals(StringEnum.CATALOG.getCode())){
            //回调进来的,设置回调执行次数+1
            map.put("callback",map.get("callback")+1);
        }else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback",0);
        }
        List<MenuButton> childs=new ArrayList<>();
        if(!EmptyUtils.isEmpty(all)){
            for (int i = 0; i < all.size(); i++) {
                if(i<0){
                    i=0;
                }
                MenuButton sysPermission = all.get(i);
                if(!EmptyUtils.isEmpty(buttons)){
                    //封装按钮数据
                    List<MenuButton> collect = buttons.stream()
                            //过滤不是当前菜单的子级按钮
                            .filter(button -> button.getPermissionPid().equals(sysPermission.getPermissionId()))
                            .collect(Collectors.toList());
                    sysPermission.setButtons(collect);
                }
                if(parent.getPermissionId().equals(sysPermission.getPermissionPid())){
                    all.remove(i);
                    i--;
                    //执行回调
                    MenuButton itemPermission = setUserMenuChildsIndex(sysPermission, all,map,buttons);
                    Meta meta=new Meta();
                    meta.setTitle(itemPermission.getPermissionName());
                    meta.setIcon(itemPermission.getIcon());
                    itemPermission.setMeta(meta);
                    childs.add(itemPermission);
                    //判断当前是否回到最高级菜单节点
                    if(parent.getResourceType().equals(StringEnum.CATALOG.getCode())){
                        //如果是,计算索引值=当前索引值-（回调执行次数-1）
                        i=i-(map.get("callback")-1);
                        //初始化回调执行次数
                        map.put("callback",0);
                    }
                }
            }
            parent.setChildren(childs);
        }
        return parent;
    }

    private Permission setMenuChildsIndex(Permission parent, List<Permission> all, Map<String,Integer> map,List<Permission> buttons) throws ArrayIndexOutOfBoundsException{
        if(!parent.getResourceType().equals(StringEnum.CATALOG.getCode())){
            //回调进来的,设置回调执行次数+1
            map.put("callback",map.get("callback")+1);
        }else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback",0);
        }
        List<Permission> childs=new ArrayList<>();
        if(!EmptyUtils.isEmpty(all)){
            for (int i = 0; i < all.size(); i++) {
                if(i<0){
                    i=0;
                }
                Permission sysPermission = all.get(i);
                if(parent.getPermissionId().equals(sysPermission.getPermissionPid())){
                    all.remove(i);
                    i--;
                    //执行回调
                    Permission itemPermission = setMenuChildsIndex(sysPermission, all,map,buttons);
                    childs.add(itemPermission);
                    //判断当前是否回到最高级菜单节点
                    if(parent.getResourceType().equals(StringEnum.CATALOG.getCode())){
                        //如果是,计算索引值=当前索引值-（回调执行次数-1）
                        i=i-(map.get("callback")-1);
                        //初始化回调执行次数
                        map.put("callback",0);
                    }
                }
            }
            parent.setChilds(childs);
        }
        return parent;
    }
}
