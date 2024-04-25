/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.dict.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.dict.CerePlatformDictDAO;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.dict.*;
import com.shop.cereshop.admin.param.express.ExpressImort;
import com.shop.cereshop.admin.service.dict.CerePlatformDictService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.poi.ImportExeclUtil;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CerePlatformDictServiceImpl implements CerePlatformDictService {

    @Autowired
    private CerePlatformDictDAO cerePlatformDictDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(DictSaveParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformDict dict=new CerePlatformDict();
        dict.setDictPid(param.getDictPid());
        dict.setDictName(param.getDictName());
        dict.setDictDescribe(param.getDictDescribe());
        if(EmptyUtils.isEmpty(dict.getDictPid())){
            //如果父节点为空,新增根节点,父节点为0
            dict.setDictPid(0l);
        }
        dict.setCreateTime(time);
        cerePlatformDictDAO.insert(dict);
        //新增日志
        cerePlatformLogService.addLog(user,"数据字典管理","平台端操作","添加数据字典",String.valueOf(dict.getDictId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(DictUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformDict dict=new CerePlatformDict();
        dict.setDictPid(param.getDictPid());
        dict.setDictName(param.getDictName());
        dict.setDictDescribe(param.getDictDescribe());
        dict.setDictId(param.getDictId());
        dict.setUpdateTime(time);
        cerePlatformDictDAO.updateByPrimaryKeySelective(dict);
        //新增日志
        cerePlatformLogService.addLog(user,"数据字典管理","平台端操作","修改数据字典",String.valueOf(dict.getDictId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(DictDeleteParam param, CerePlatformUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        cerePlatformDictDAO.deleteByPrimaryKey(param.getDictId());
        //删除所有子节点数据
        cerePlatformDictDAO.deleteByPid(param.getDictId());
        //新增日志
        cerePlatformLogService.addLog(user,"数据字典管理","平台端操作","删除数据字典",String.valueOf(param.getDictId()),time);
    }

    @Override
    public CerePlatformDict getById(Long dictId) throws CoBusinessException {
        return cerePlatformDictDAO.getById(dictId);
    }

    @Override
    public Page getAll(DictGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CerePlatformDict> list=cerePlatformDictDAO.getAll(param);
        PageInfo<CerePlatformDict> pageInfo=new PageInfo<>(list);
        Page page =new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Page getChilds(DictGetChildsParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CerePlatformDict> list=cerePlatformDictDAO.getChilds(param);
        PageInfo<CerePlatformDict> pageInfo=new PageInfo<>(list);
        Page page =new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CerePlatformDict> getSelect(String dictName) throws CoBusinessException {
        return cerePlatformDictDAO.getSelect(dictName);
    }

    @Override
    public CerePlatformDict checkName(String dictName) {
        return cerePlatformDictDAO.checkName(dictName);
    }

    @Override
    public CerePlatformDict checkNameAndId(DictUpdateParam param) {
        return cerePlatformDictDAO.checkNameAndId(param);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void importExpressCodes(Workbook wb) throws CoBusinessException,Exception {
        List<ExpressImort> list = ImportExeclUtil.readDateListT(wb, new ExpressImort());
        String time= TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                CerePlatformDict dict=new CerePlatformDict();
                dict.setDictPid(8l);
                dict.setDictName(list.get(i).getCompany());
                dict.setDictDescribe(list.get(i).getCode());
                dict.setCreateTime(time);
                cerePlatformDictDAO.insert(dict);
            }
        }
    }

    @Override
    public String findByCompany(String express,Long dictPid) {
        return cerePlatformDictDAO.findByCompany(express,dictPid);
    }

    @Override
    public String findNameById(Long dictId) {
        return cerePlatformDictDAO.findNameById(dictId);
    }
}
