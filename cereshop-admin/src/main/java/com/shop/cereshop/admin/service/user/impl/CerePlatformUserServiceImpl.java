/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.user.CerePlatformUserDAO;
import com.shop.cereshop.admin.page.role.PlatformUserRole;
import com.shop.cereshop.admin.page.user.PlatformUser;
import com.shop.cereshop.admin.param.user.*;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.user.CerePlatformUserRoleService;
import com.shop.cereshop.admin.service.user.CerePlatformUserService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.domain.user.CerePlatformUserRole;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.EncryptUtil;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CerePlatformUserServiceImpl implements CerePlatformUserService {

    @Autowired
    private CerePlatformUserDAO cerePlatformUserDAO;

    @Autowired
    private CerePlatformUserRoleService cerePlatformUserRoleService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    public CerePlatformUser findByUserName(String userName) {
        return cerePlatformUserDAO.findByUserName(userName);
    }

    @Override
    public CerePlatformUser findByPhone(String phone) {
        return cerePlatformUserDAO.findByPhone(phone);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(UserSaveParam userParam, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //校验用户名或者手机号是否已注册
        CerePlatformUser platformUser=cerePlatformUserDAO.checkUserNameOrPhone(userParam.getUsername(),userParam.getPhone(),null);
        if(platformUser!=null){
            throw new CoBusinessException(CoReturnFormat.USERNAME_OR_PHONE_HAVE);
        }
        CerePlatformUser cerePlatformUser=new CerePlatformUser();
        cerePlatformUser.setCreateTime(time);
        cerePlatformUser.setUsername(userParam.getUsername());
        cerePlatformUser.setEmail(userParam.getEmail());
        cerePlatformUser.setName(userParam.getName());
        cerePlatformUser.setPhone(userParam.getPhone());
        String password="";
        if(EmptyUtils.isEmpty(userParam.getPassword())){
            //设置默认密码123456
            password= EncryptUtil.encrypt("123456");
        }else {
            password= EncryptUtil.encrypt(userParam.getPassword());
        }
        cerePlatformUser.setPassword(password);
        cerePlatformUser.setSex(userParam.getSex());
        cerePlatformUser.setState(userParam.getState());
        cerePlatformUser.setToken(RandomStringUtil.getRandomCode(128,0));
        cerePlatformUserDAO.insert(cerePlatformUser);
        if(!EmptyUtils.isEmpty(userParam.getRoleIds())){
            //新增用户角色数据
            for (Long id:userParam.getRoleIds()) {
                CerePlatformUserRole cerePlatformUserRole=new CerePlatformUserRole();
                cerePlatformUserRole.setPlatformUserId(cerePlatformUser.getPlatformUserId());
                cerePlatformUserRole.setRoleId(id);
                cerePlatformUserRoleService.insert(cerePlatformUserRole);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"用户管理","平台端操作","添加用户",String.valueOf(cerePlatformUser.getPlatformUserId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(UserUpdateParam userParam, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //校验用户名或者手机号是否已注册
        CerePlatformUser platformUser=cerePlatformUserDAO.checkUserNameOrPhone(userParam.getUsername(),userParam.getPhone(),userParam.getPlatformUserId());
        if(platformUser!=null){
            throw new CoBusinessException(CoReturnFormat.USERNAME_OR_PHONE_HAVE);
        }
        CerePlatformUser cerePlatformUser=new CerePlatformUser();
        cerePlatformUser.setPlatformUserId(userParam.getPlatformUserId());
        cerePlatformUser.setUpdateTime(time);
        cerePlatformUser.setUsername(userParam.getUsername());
        cerePlatformUser.setEmail(userParam.getEmail());
        cerePlatformUser.setName(userParam.getName());
        cerePlatformUser.setPhone(userParam.getPhone());
        cerePlatformUser.setSex(userParam.getSex());
        cerePlatformUser.setState(userParam.getState());
        if(!EmptyUtils.isEmpty(userParam.getPassword())){
            //更新密码
            cerePlatformUser.setPassword(EncryptUtil.encrypt(userParam.getPassword()));
        }
        cerePlatformUserDAO.updateByPrimaryKeySelective(cerePlatformUser);
        //清空关联角色数据
        cerePlatformUserRoleService.deleteByUserId(cerePlatformUser.getPlatformUserId());
        if(!EmptyUtils.isEmpty(userParam.getRoleIds())){
            //新增用户角色数据
            for (Long id:userParam.getRoleIds()) {
                CerePlatformUserRole cerePlatformUserRole=new CerePlatformUserRole();
                cerePlatformUserRole.setPlatformUserId(cerePlatformUser.getPlatformUserId());
                cerePlatformUserRole.setRoleId(id);
                cerePlatformUserRoleService.insert(cerePlatformUserRole);
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"用户管理","平台端操作","修改用户",String.valueOf(cerePlatformUser.getPlatformUserId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(UserDeleteParam userParam, CerePlatformUser user) throws CoBusinessException{
        String time =TimeUtils.yyMMddHHmmss();
        cerePlatformUserDAO.deleteByPrimaryKey(userParam.getPlatformUserId());
        //清空关联角色数据
        cerePlatformUserRoleService.deleteByUserId(userParam.getPlatformUserId());
        //新增日志
        cerePlatformLogService.addLog(user,"用户管理","平台端操作","删除用户",String.valueOf(userParam.getPlatformUserId()),time);
    }

    @Override
    public PlatformUser getById(Long platformUserId) throws CoBusinessException,Exception {
        PlatformUser page = cerePlatformUserDAO.getById(platformUserId);
        if(page!=null){
            //解密密码
            page.setPassword(EncryptUtil.decrypt(page.getPassword()));
            //查询关联角色数据
            List<PlatformUserRole> list= cerePlatformUserRoleService.findRolesByUserId(platformUserId);
            if(!EmptyUtils.isEmpty(list)){
                List<Long> ids = list.stream()
                        .map(a -> {
                            return a.getRoleId();
                        }).collect(Collectors.toList());
                page.setIds(ids);
            }
            page.setRoles(list);
        }
        return page;
    }

    @Override
    public Page getAll(UserGetAllParam userParam) throws CoBusinessException {
        PageHelper.startPage(userParam.getPage(),userParam.getPageSize());
        List<PlatformUser> list=cerePlatformUserDAO.getAll(userParam);
        PageInfo<PlatformUser> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public CerePlatformUser findByToken(String token) {
        return cerePlatformUserDAO.findByToken(token);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void forgetPassword(UserForgetPasswordParam user, CerePlatformUser cerePlatformUser) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        cerePlatformUser.setUpdateTime(time);
        cerePlatformUser.setPassword(EncryptUtil.encrypt(user.getNewPassword()));
        cerePlatformUserDAO.updateByPrimaryKeySelective(cerePlatformUser);
        //新增日志
        cerePlatformLogService.addLog(cerePlatformUser,"系统用户管理","平台端操作","忘记密码",
                String.valueOf(cerePlatformUser.getPlatformUserId()),time);
    }

    @Override
    public void updateToken(CerePlatformUser cerePlatformUser) throws CoBusinessException {
        cerePlatformUserDAO.updateToken(cerePlatformUser);
    }

    @Override
    public void updatePassword(UserForgetPasswordParam passwordParam, CerePlatformUser user) throws CoBusinessException{
        if (user != null) {
            PlatformUser platformUser = cerePlatformUserDAO.getById(user.getPlatformUserId());
            if (platformUser != null) {
                String oldPassword = EncryptUtil.encrypt(passwordParam.getPassword());
                if (!Objects.equals(oldPassword, platformUser.getPassword())) {
                    throw new CoBusinessException(CoReturnFormat.OLD_PASSWORD_ERROR);
                }
                String time=TimeUtils.yyMMddHHmmss();
                user.setUpdateTime(time);
                user.setPassword(EncryptUtil.encrypt(passwordParam.getNewPassword()));
                cerePlatformUserDAO.updateByPrimaryKeySelective(user);
                //新增日志
                cerePlatformLogService.addLog(user,"系统用户管理","平台端操作","修改密码",
                        String.valueOf(user.getPlatformUserId()),time);
            }
        }
    }
}
