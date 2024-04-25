/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.compose;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.business.page.compose.ShopCompose;
import com.shop.cereshop.business.page.compose.ShopComposeDetail;
import com.shop.cereshop.business.page.product.ShopProduct;
import com.shop.cereshop.business.param.compose.ComposeGetAllParam;
import com.shop.cereshop.business.param.compose.ComposeSaveParam;
import com.shop.cereshop.business.param.compose.ComposeUpdateParam;
import com.shop.cereshop.business.param.product.ProductGetAllParam;
import com.shop.cereshop.commons.domain.permission.CerePlatformPermission;
import com.shop.cereshop.commons.domain.tool.CereComposeProduct;
import com.shop.cereshop.commons.domain.tool.CereShopCompose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CereShopComposeDAO extends BaseMapper<CerePlatformPermission> {
    int deleteByPrimaryKey(Long composeId);

    int insert(CereShopCompose record);

    int insertSelective(CereShopCompose record);

    CereShopCompose selectByPrimaryKey(Long composeId);

    int updateByPrimaryKeySelective(CereShopCompose record);

    int updateByPrimaryKey(CereShopCompose record);

    List<CereShopCompose> checkTime(ComposeSaveParam param);

    List<CereShopCompose> checkUpdateTime(ComposeUpdateParam param);

    ShopComposeDetail getById(@Param("composeId") Long composeId);

    List<ShopCompose> getAll(ComposeGetAllParam param);

    CereShopCompose checkName(@Param("composeName") String composeName,@Param("composeId") Long composeId);

    List<Long> checkShopCompose(@Param("ids") List<Long> ids,@Param("startTime") String startTime,
                                @Param("endTime") String endTime,@Param("shopId") Long shopId,@Param("composeId") Long composeId);

    List<ShopProduct> selectProduct(ProductGetAllParam param);

    List<BigDecimal> findProductPrice(@Param("list") List<CereComposeProduct> list);
}
