/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.tool;

import com.shop.cereshop.business.page.tool.ToolProduct;
import com.shop.cereshop.commons.domain.tool.CereShopGroupWorkDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopGroupWorkDetailDAO extends BaseMapper<CereShopGroupWorkDetail> {
    int insert(CereShopGroupWorkDetail record);

    int insertSelective(CereShopGroupWorkDetail record);

    void insertBatch(@Param("list") List<CereShopGroupWorkDetail> list);

    void deleteById(@Param("shopGroupWorkId") Long shopGroupWorkId);

    List<ToolProduct> findProducts(@Param("shopGroupWorkId") Long shopGroupWorkId);

    List<ToolProduct> findDistinctProducts(@Param("shopGroupWorkId") Long shopGroupWorkId);

    List<Long> findProductIdList(Long shopGroupWorkId);
}
