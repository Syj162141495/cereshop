/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.dao.notice;

import com.shop.cereshop.admin.param.notice.NoticeGetAllParam;
import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CereNoticeDAO extends BaseMapper<CereNotice> {
    int deleteByPrimaryKey(Long noticeId);

    int insert(CereNotice record);

    int insertSelective(CereNotice record);

    CereNotice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(CereNotice record);

    int updateByPrimaryKey(CereNotice record);

    List<CereNotice> getAll(NoticeGetAllParam param);

    List<CereNotice> getNotices();
}
