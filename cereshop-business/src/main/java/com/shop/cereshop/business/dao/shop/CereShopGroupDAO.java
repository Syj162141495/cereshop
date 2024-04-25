/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.shop;

import com.shop.cereshop.business.page.group.Group;
import com.shop.cereshop.business.page.group.GroupDetail;
import com.shop.cereshop.business.page.group.GroupProduct;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.group.GroupCondition;
import com.shop.cereshop.business.param.group.GroupGetAllParam;
import com.shop.cereshop.business.param.group.GroupProductParam;
import com.shop.cereshop.commons.domain.shop.CereShopGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopGroupDAO extends BaseMapper<CereShopGroup> {
    int deleteByPrimaryKey(@Param("shopGroupId") Long shopGroupId);

    int insert(CereShopGroup record);

    int insertSelective(CereShopGroup record);

    CereShopGroup selectByPrimaryKey(Long shopGroupId);

    int updateByPrimaryKeySelective(CereShopGroup record);

    int updateByPrimaryKey(CereShopGroup record);

    List<CereShopGroup> getGroupSelect(@Param("shopId") Long shopId);

    void updateGroup(@Param("shopGroupId") Long shopGroupId);

    GroupDetail getById(@Param("shopGroupId") Long shopGroupId);

    List<Group> getAll(GroupGetAllParam param);

    List<GroupProduct> getProducts(GroupProductParam param);

    void addProduct(@Param("ids") List<Long> ids,@Param("shopGroupId") Long shopGroupId);

    void deleteProduct(@Param("shopGroupId") Long shopGroupId);

    List<Long> findProductIds(GroupCondition condition);

    List<GroupProduct> findProducts(@Param("shopGroupId") Long shopGroupId);

    List<Product> findProductByIds(@Param("ids") List<Long> ids);
}
