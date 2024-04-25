/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.cereshop.admin.page.product.CanvasProduct;
import com.shop.cereshop.admin.page.product.CanvasShop;
import com.shop.cereshop.admin.page.product.ProductDetail;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.param.product.CanvasAdminProductParam;
import com.shop.cereshop.admin.param.product.ProductGetAllParam;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopProductDAO extends BaseMapper<CereShopProduct> {
    int deleteByPrimaryKey(Long productId);

    int insert(CereShopProduct record);

    int insertSelective(CereShopProduct record);

    CereShopProduct selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(CereShopProduct record);

    int updateByPrimaryKeyWithBLOBs(CereShopProduct record);

    int updateByPrimaryKey(CereShopProduct record);

    List<CanvasProduct> getProducts(CanvasAdminProductParam param);

    List<CanvasShop> getShops(CanvasAdminProductParam param);

    List<CanvasProduct> getGroupWorkProducts(CanvasAdminProductParam param);

    List<CanvasProduct> getSeckillProducts(CanvasAdminProductParam param);

    List<CanvasProduct> getDiscountProducts(CanvasAdminProductParam param);

    List<ShopProduct> getAll(ProductGetAllParam param);

    ProductDetail getById(@Param("productId") Long productId);

    int unShelveByShopId(Long shopId);

    List<Long> findProudctIdListByShopId(Long shopId);
}
