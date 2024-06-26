/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.express;

import com.shop.cereshop.commons.domain.express.KuaiDiNiaoCode;
import com.shop.cereshop.commons.exception.CoBusinessException;

import java.util.List;

/**
 * 快递鸟接口
 * @author wangsong
 * @mail 1720696548@qq.com
 * @date 2020/9/16 0016 10:26
 * @version 1.0.0
 */
public interface KuaiDiNiaoService {

    List<Object> findTraces(String code,String formid) throws CoBusinessException,Exception;

    List<KuaiDiNiaoCode> findCode(String formid) throws CoBusinessException,Exception;

}
