/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.product;

import com.shop.cereshop.commons.domain.product.Classify;
import com.shop.cereshop.business.param.product.ProductGetClassifyParam;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface CereProductClassifyService {
    List<CereProductClassify> getClassifySelect(ProductGetClassifyParam param) throws CoBusinessException;

    CereProductClassify findByHierarchy(String hierarchy);

    List<Classify> getClassify() throws CoBusinessException;

    List<CereProductClassify> selectAll();
}
