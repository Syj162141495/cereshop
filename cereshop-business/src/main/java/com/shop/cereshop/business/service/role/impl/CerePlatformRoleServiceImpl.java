/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.role.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.role.CerePlatformRoleDAO;
import com.shop.cereshop.business.param.permission.RoleDistributionParam;
import com.shop.cereshop.business.param.role.RoleDeleteParam;
import com.shop.cereshop.business.param.role.RoleGetAllParam;
import com.shop.cereshop.business.param.role.RoleSaveParam;
import com.shop.cereshop.business.param.role.RoleUpdateParam;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.role.CerePlatformRolePermissionService;
import com.shop.cereshop.business.service.role.CerePlatformRoleService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.role.CerePlatformRole;
import com.shop.cereshop.commons.domain.role.CerePlatformRolePermission;
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
public class CerePlatformRoleServiceImpl implements CerePlatformRoleService {

    @Autowired
    private CerePlatformRoleDAO cerePlatformRoleDAO;

    @Autowired
    private CerePlatformRolePermissionService cerePlatformRolePermissionService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(RoleSaveParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformRole role=new CerePlatformRole();
        role.setRoleName(param.getRoleName());
        role.setRoleDescribe(param.getRoleDescribe());
        role.setProject(param.getShopId());
        role.setCreateTime(time);
        cerePlatformRoleDAO.insert(role);
        //新增日志
        cerePlatformLogService.addLog(user,"角色管理","平台端操作","添加角色",role.getRoleId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(RoleUpdateParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformRole role=new CerePlatformRole();
        role.setRoleName(param.getRoleName());
        role.setRoleDescribe(param.getRoleDescribe());
        role.setRoleId(param.getRoleId());
        role.setUpdateTime(time);
        cerePlatformRoleDAO.updateByPrimaryKeySelective(role);
        //新增日志
        cerePlatformLogService.addLog(user,"角色管理","平台端操作","修改角色",role.getRoleId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(RoleDeleteParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        cerePlatformRoleDAO.deleteByPrimaryKey(param.getRoleId());
        //清空关联权限数据
        cerePlatformRolePermissionService.deleteByRoleId(param.getRoleId());
        //新增日志
        cerePlatformLogService.addLog(user,"角色管理","平台端操作","删除角色",param.getRoleId(),time);
    }

    @Override
    public CerePlatformRole getById(Long roleId) throws CoBusinessException {
        return cerePlatformRoleDAO.getById(roleId);
    }

    @Override
    public Page getAll(RoleGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CerePlatformRole> list=cerePlatformRoleDAO.getAll(param);
        PageInfo<CerePlatformRole> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void distribution(RoleDistributionParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        //根据角色id清空权限数据
        cerePlatformRolePermissionService.deleteByRoleId(param.getRoleId());
        //新增权限数据
        if(!EmptyUtils.isEmpty(param.getPermissionIds())){
            for (Long id:param.getPermissionIds()) {
                CerePlatformRolePermission cerePlatformRolePermission=new CerePlatformRolePermission();
                cerePlatformRolePermission.setRoleId(param.getRoleId());
                cerePlatformRolePermission.setPermissionId(id);
                cerePlatformRolePermissionService.insert(cerePlatformRolePermission);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"角色管理","平台端操作","分配权限",param.getRoleId(),time);
    }
}
