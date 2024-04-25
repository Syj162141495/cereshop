/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.dao.business;

import com.shop.cereshop.business.page.shop.PlatformBusiness;
import com.shop.cereshop.business.page.user.Business;
import com.shop.cereshop.business.param.user.BusinessGetAllUser;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CerePlatformBusinessDAO extends BaseMapper<CerePlatformBusiness> {
    int deleteByPrimaryKey(Long businessUserId);

    int insert(CerePlatformBusiness record);

    int insertSelective(CerePlatformBusiness record);

    CerePlatformBusiness selectByPrimaryKey(Long businessUserId);

    int updateByPrimaryKeySelective(CerePlatformBusiness record);

    int updateByPrimaryKey(CerePlatformBusiness record);

    PlatformBusiness findByUserName(@Param("username") String username);

    CerePlatformBusiness findByToken(@Param("token") String token);

    void updateToken(CerePlatformBusiness cerePlatformBusiness);

    List<CerePlatformBusiness> getAll(BusinessGetAllUser param);

    CerePlatformBusiness checkUserName(@Param("username") String username,@Param("businessUserId") Long businessUserId);

    Business getById(@Param("businessUserId") Long businessUserId);
}
