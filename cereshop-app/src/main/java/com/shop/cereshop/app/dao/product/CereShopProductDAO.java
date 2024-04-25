/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.dao.product;

import com.shop.cereshop.app.page.canvas.CanvasProduct;
import com.shop.cereshop.app.page.index.Product;
import com.shop.cereshop.app.page.product.ProductDetail;
import com.shop.cereshop.app.page.product.ShareProduct;
import com.shop.cereshop.app.param.canvas.CanvasAppProductParam;
import com.shop.cereshop.app.param.canvas.CanvasProductParam;
import com.shop.cereshop.app.param.index.SearchParam;
import com.shop.cereshop.app.param.product.PageProductParam;
import com.shop.cereshop.commons.domain.product.CereShopProduct;
import com.shop.cereshop.commons.domain.product.Classify;
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

    ProductDetail findBySkuId(@Param("skuId") Long skuId,@Param("shopId") Long shopId);

    List<String> findImages(@Param("productId") Long productId);

    List<String> findSkuImages(@Param("productId") Long productId);

    List<Long> findClassifyNumber(@Param("shopId") Long shopId);

    Integer findPayNumber(@Param("skuId") Long skuId);

    List<Product> getSearchProducts(SearchParam param);

    ShareProduct findShareProduct(@Param("skuId") Long skuId);

    List<CanvasProduct> getCanvasProducts(CanvasAppProductParam param);

    List<Classify> findAllClassify();

    List<Classify> findChildsClassify();

    List<Product> findSimilarProducts(@Param("classifyId") Long classifyId);

    void deleteAllSearch(@Param("buyerUserId") Long buyerUserId);

    List<CanvasProduct> getGroupWorkProducts(CanvasAppProductParam param);

    List<CanvasProduct> getSeckillProducts(CanvasAppProductParam param);

    List<CanvasProduct> getDiscountProducts(CanvasAppProductParam param);

    List<Product> getRandomSortProduct(PageProductParam param);

    List<Product> findRandom4ShopProducts(Long shopId);
}
