/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.product;

import java.util.List;

public interface CereProductImageService {
    List<String> findByProductId(Long productId);
}
