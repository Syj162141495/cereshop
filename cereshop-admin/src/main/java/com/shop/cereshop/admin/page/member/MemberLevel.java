/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.page.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 会员等级返回数据
 */
@Data
@ApiModel(value = "MemberLevel", description = "会员等级返回数据")
public class MemberLevel {

    /**
     * 会员等级id
     */
    @ApiModelProperty(value = "会员等级id")
    private Long memberLevelId;

    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称")
    private String memberLevelName;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String memberLevelIcon;

    /**
     * 背景图
     */
    @ApiModelProperty(value = "背景图")
    private String memberLevelBackground;

    /**
     * 成长值
     */
    @ApiModelProperty(value = "成长值")
    private Integer growth;

    /**
     * 会员权益id数组
     */
    @ApiModelProperty(value = "会员权益id数组")
    private List<String> ids;

    /**
     * 等级说明
     */
    @ApiModelProperty(value = "等级说明")
    private String memberLevelReason;

    /**
     * 等级权益字符串
     */
    @ApiModelProperty(value = "等级权益字符串")
    private String memberIds;

}
