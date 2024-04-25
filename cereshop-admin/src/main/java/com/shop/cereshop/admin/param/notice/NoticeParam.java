/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 消息推送请求
 */
@Data
@ApiModel(value = "NoticeParam", description = "消息推送请求")
public class NoticeParam {

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
     * 接收用户类型 1-全部用户 2-商家 4-普通用户
     */
    @ApiModelProperty(value = "接收用户类型 1-全部用户 2-商家 3-普通用户")
    private Integer receive;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String noticeContent;
}
