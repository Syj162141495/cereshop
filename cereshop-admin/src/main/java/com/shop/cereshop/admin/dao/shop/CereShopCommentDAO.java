/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.shop;

import com.shop.cereshop.admin.page.shop.CommentDetail;
import com.shop.cereshop.admin.page.shop.ShopComment;
import com.shop.cereshop.admin.param.comment.CommentGetAllParam;
import com.shop.cereshop.commons.domain.shop.CereShopComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CereShopCommentDAO extends BaseMapper<CereShopComment> {
    int insert(CereShopComment record);

    int insertSelective(CereShopComment record);

    List<ShopComment> getAll(CommentGetAllParam param);

    CommentDetail getById(@Param("commentId") Long commentId);

    void update(CereShopComment comment);

    void deleteById(@Param("commentId") Long commentId);

    String findSensitiveWord();
}
