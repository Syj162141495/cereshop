/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.dict;

import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.param.dict.*;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface CerePlatformDictService {
    void save(DictSaveParam param, CerePlatformUser user) throws CoBusinessException;

    void update(DictUpdateParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(DictDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    CerePlatformDict getById(Long dictId) throws CoBusinessException;

    Page getAll(DictGetAllParam param) throws CoBusinessException;

    Page getChilds(DictGetChildsParam param) throws CoBusinessException;

    List<CerePlatformDict> getSelect(String dictName) throws CoBusinessException;

    CerePlatformDict checkName(String dictName);

    CerePlatformDict checkNameAndId(DictUpdateParam param);

    void importExpressCodes(Workbook wb) throws CoBusinessException,Exception;

    String findByCompany(String express,Long dictPid);

    String findNameById(Long cardType);
}
