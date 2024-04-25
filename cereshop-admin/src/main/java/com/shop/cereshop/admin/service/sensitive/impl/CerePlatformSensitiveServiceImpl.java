/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.sensitive.impl;

import com.shop.cereshop.admin.dao.sensitive.CerePlatformSensitiveDAO;
import com.shop.cereshop.admin.param.sensitive.SensitiveSaveParam;
import com.shop.cereshop.admin.param.sensitive.SensitiveUpdateParam;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.sensitive.CerePlatformSensitiveService;
import com.shop.cereshop.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
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
public class CerePlatformSensitiveServiceImpl implements CerePlatformSensitiveService {

    @Autowired
    private CerePlatformSensitiveDAO cerePlatformSensitiveDAO;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(SensitiveSaveParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        String describe="";
        CerePlatformSensitive sensitive=new CerePlatformSensitive();
        sensitive.setState(param.getState());
        sensitive.setHandleMeasures(param.getHandleMeasures());
        sensitive.setSensitiveWord(param.getSensitiveWord());
        if(!EmptyUtils.isEmpty(param.getId())){
            //修改
            describe="修改敏感词设置";
            sensitive.setId(param.getId());
            cerePlatformSensitiveDAO.update(sensitive);
        }else {
            //新增
            describe="添加敏感词设置";
            cerePlatformSensitiveDAO.insert(sensitive);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"敏感词管理","平台端操作",describe,String.valueOf(sensitive.getId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(SensitiveUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        CerePlatformSensitive sensitive=new CerePlatformSensitive();
        sensitive.setId(param.getId());
        sensitive.setState(param.getState());
        sensitive.setHandleMeasures(param.getHandleMeasures());
        sensitive.setSensitiveWord(param.getSensitiveWord());
        cerePlatformSensitiveDAO.update(sensitive);
        //新增日志
        cerePlatformLogService.addLog(user,"敏感词管理","平台端操作","修改敏感词设置",String.valueOf(sensitive.getId()),time);
    }

    @Override
    public List<CerePlatformSensitive> get() throws CoBusinessException {
        return cerePlatformSensitiveDAO.get();
    }
}
