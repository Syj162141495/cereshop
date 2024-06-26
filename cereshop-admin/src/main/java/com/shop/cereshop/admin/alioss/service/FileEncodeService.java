/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.alioss.service;

public interface FileEncodeService {

    /**
     * 指定文件编码
     */
    public void encodeFile(int maxKeys, String keyPrefix, String nextMarker) throws Exception;
}
