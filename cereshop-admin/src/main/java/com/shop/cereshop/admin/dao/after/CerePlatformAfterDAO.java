/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.after;

import com.shop.cereshop.commons.domain.after.CerePlatformAfter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CerePlatformAfterDAO extends BaseMapper<CerePlatformAfter> {
    int insert(CerePlatformAfter record);

    int insertSelective(CerePlatformAfter record);

    void update(CerePlatformAfter cerePlatformAfter);

}
