/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.product;

import com.shop.cereshop.business.page.canvas.CanvasProduct;
import com.shop.cereshop.business.page.canvas.CanvasProductParam;
import com.shop.cereshop.business.page.canvas.CanvasShop;
import com.shop.cereshop.business.page.member.ProductMember;
import com.shop.cereshop.business.page.product.ShopProduct;
import com.shop.cereshop.business.param.product.ProductGetAllParam;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    ShopProduct getById(@Param("productId") Long productId);

    List<CereShopProduct> getAllProducts(ProductGetAllParam param);

    CereShopProduct checkName(@Param("shopId") Long shopId,@Param("classifyId") Long classifyId,@Param("productName") String productName);

    List<CanvasProduct> getProducts(CanvasProductParam param);

    List<CanvasShop> getShops(CanvasProductParam param);

    Long checkActivity(@Param("productId") Long productId);

    Long checkGroupWork(@Param("productId") Long productId);

    Long checkSeckill(@Param("productId") Long productId);

    Long checkDiscount(@Param("productId") Long productId);

    List<Long> findAllZeroStockNumber();

    void updateBatchShelveState(@Param("ids") List<Long> ids,@Param("time") String time);

    List<CereShopProduct> selectAll();

    List<ProductMember> getProductMembers(@Param("productId") Long productId);
}
