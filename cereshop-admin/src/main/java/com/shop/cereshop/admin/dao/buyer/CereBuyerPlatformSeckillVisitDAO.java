/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.buyer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.commons.domain.buyer.CereBuyerPlatformSeckillVisit;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereBuyerPlatformSeckillVisitDAO extends BaseMapper<CereBuyerUser> {
    int insert(CereBuyerPlatformSeckillVisit record);

    int insertSelective(CereBuyerPlatformSeckillVisit record);
}
