/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.notice;

import com.shop.cereshop.commons.domain.common.PageParam;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 历史消息查询请求
 */
@Data
@ApiModel(value = "NoticeGetAllParam", description = "历史消息查询请求")
public class NoticeGetAllParam extends PageParam {

    /**
     * 消息标题
     */
    @ApiModelProperty(value = "消息标题")
    private String noticeTitle;

    /**
     * 消息类型 1-系统消息（系统自动发送） 2-公告（后台录入）3-站内信
     */
    @ApiModelProperty(value = "消息类型 1-系统消息（系统自动发送） 2-公告（后台录入）3-站内信")
    private Integer noticeType;

    /**
     * 发送时间数组
     */
    @ApiModelProperty(value = "发送时间数组")
    private List<String> dates;

    public void setDates(List<String> dates) {
        if(!EmptyUtils.isEmpty(dates)&&dates.size()>1){
            this.startTime=dates.get(0);
            this.endTime=dates.get(1);
        }
    }

    /**
     * 发送开始时间
     */
    @ApiModelProperty(value = "发送开始时间")
    private String startTime;

    /**
     * 发送结束时间
     */
    @ApiModelProperty(value = "发送结束时间")
    private String endTime;
}
