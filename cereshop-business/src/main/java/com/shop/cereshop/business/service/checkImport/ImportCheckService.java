/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.checkImport;

import com.shop.cereshop.business.param.product.ProductImport;
import com.shop.cereshop.business.param.product.ProductSaveParam;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

public interface ImportCheckService {
    List<ProductSaveParam> checkProduct(List<ProductImport> list) throws CoBusinessException;
}
