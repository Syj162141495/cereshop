/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.word;

import com.shop.cereshop.admin.param.word.WordParam;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.domain.word.CerePlatformWord;
import com.shop.cereshop.commons.exception.CoBusinessException;

public interface CerePlatformWordService {
    CerePlatformWord checkWord(String keyWord,Long wordId);

    void save(CerePlatformWord word, CerePlatformUser user) throws CoBusinessException;

    void update(CerePlatformWord word, CerePlatformUser user) throws CoBusinessException;

    void delete(CerePlatformWord word, CerePlatformUser user) throws CoBusinessException;

    CerePlatformWord getById(Long wordId) throws CoBusinessException;

    Page getAll(WordParam param) throws CoBusinessException;

    void start(Integer state) throws CoBusinessException;
}
