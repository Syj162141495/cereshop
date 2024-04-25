/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.dao.order;

import com.shop.cereshop.commons.domain.order.CereOrderReconciliation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CereOrderReconciliationDAO extends BaseMapper<CereOrderReconciliation> {
    int deleteByPrimaryKey(Long reconciliationId);

    int insert(CereOrderReconciliation record);

    int insertSelective(CereOrderReconciliation record);

    CereOrderReconciliation selectByPrimaryKey(Long reconciliationId);

    int updateByPrimaryKeySelective(CereOrderReconciliation record);

    int updateByPrimaryKey(CereOrderReconciliation record);

    void updateByOrderId(CereOrderReconciliation reconciliation);
}
