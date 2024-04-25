/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.label;

import com.shop.cereshop.business.page.shop.LabelSource;
import com.shop.cereshop.business.param.label.LabelGetSourceParam;
import com.shop.cereshop.commons.domain.label.CereLabelSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereLabelSourceDAO extends BaseMapper<CereLabelSource> {
    int insert(CereLabelSource record);

    int insertSelective(CereLabelSource record);

    List<CereLabelSource> findByLabelId(@Param("labelId") Long labelId);

    void deleteByLabelId(@Param("labelId") Long labelId);

    void insertBatch(@Param("list") List<CereLabelSource> list);

    List<LabelSource> getAllByLabel(LabelGetSourceParam param);

    void deleteByImageAndLabelId(@Param("labelId") Long labelId,@Param("image") String image);

    List<CereLabelSource> findByLabelIdAndType(@Param("labelId") Long labelId,@Param("labelType") Integer labelType);

    void update(CereLabelSource labelSource);
}
