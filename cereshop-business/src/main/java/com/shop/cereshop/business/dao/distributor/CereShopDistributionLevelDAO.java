/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.distributor;

import com.shop.cereshop.business.page.distribution.DistributoLevel;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributionLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopDistributionLevelDAO extends BaseMapper<CereShopDistributionLevel> {
    int deleteByPrimaryKey(Long distributorLevelId);

    int insert(CereShopDistributionLevel record);

    int insertSelective(CereShopDistributionLevel record);

    CereShopDistributionLevel selectByPrimaryKey(Long distributorLevelId);

    int updateByPrimaryKeySelective(CereShopDistributionLevel record);

    int updateByPrimaryKey(CereShopDistributionLevel record);

    List<CereShopDistributionLevel> getAllLevel(@Param("shopId") Long shopId);

    DistributoLevel getById(@Param("distributorLevelId") Long distributorLevelId);

    List<CereShopDistributionLevel> getAll(@Param("shopId") Long shopId);

    @Deprecated
    CereShopDistributionLevel findSuperior(CereShopDistributionLevel level);

    void updateIfSelf(@Param("shopId") Long shopId, @Param("ifSelf") Integer ifSelf,@Param("time") String time);

    /** 根据分销员等级查询 */
    CereShopDistributionLevel getByLevelNum(@Param("shopId") Long shopId, @Param("levelNum") Integer levelNum);

    /** 查询比当前等级大一级的分销员等级 */
    CereShopDistributionLevel getUpperLevel(@Param("shopId") Long shopId, @Param("levelNum") Integer levelNum);

    /** 查询比当前等级小一级的分销员等级 */
    CereShopDistributionLevel getLowerLevel(@Param("shopId") Long shopId, @Param("levelNum") Integer levelNum);
}
