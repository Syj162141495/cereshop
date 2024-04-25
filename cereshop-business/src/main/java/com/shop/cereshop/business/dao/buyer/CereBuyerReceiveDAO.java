/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.buyer;

import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereBuyerReceiveDAO extends BaseMapper<CereBuyerReceive> {
    int deleteByPrimaryKey(Long receiveId);

    int insert(CereBuyerReceive record);

    int insertSelective(CereBuyerReceive record);

    CereBuyerReceive selectByPrimaryKey(Long receiveId);

    int updateByPrimaryKeySelective(CereBuyerReceive record);

    int updateByPrimaryKey(CereBuyerReceive record);
}
