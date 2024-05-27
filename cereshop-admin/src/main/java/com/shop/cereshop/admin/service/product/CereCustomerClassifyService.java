/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product;

import com.shop.cereshop.admin.page.product.CustomerClassify;
import com.shop.cereshop.admin.param.product.*;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereCustomerClassifyService {
    void add(CustomerClassifyParam param, CerePlatformUser user) throws CoBusinessException;

    void update(CustomerClassifyParam param, CerePlatformUser user) throws CoBusinessException;

    void delete(ClassDeleteParam param, CerePlatformUser user) throws CoBusinessException;

    CustomerClassify getById(Long classifyId) throws CoBusinessException;

    List<CustomerClassify> getByPid(Long classifyPID) throws CoBusinessException;
}
