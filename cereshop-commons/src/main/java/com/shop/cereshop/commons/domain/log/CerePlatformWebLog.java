/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.log;

import com.shop.cereshop.commons.constant.IntegerEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platform_web_log 请求记录实体
 * @author 
 */
@Data
public class CerePlatformWebLog implements Serializable {
    /**
     * 请求记录id
     */
    @ApiModelProperty(value = "请求记录id")
    private Long webLogId;

    /**
     * 请求用户名
     */
    @ApiModelProperty(value = "请求用户名")
    private String name;

    /**
     * 请求接口地址
     */
    @ApiModelProperty(value = "请求接口地址")
    private String url;

    /**
     * 请求接口参数
     */
    @ApiModelProperty(value = "请求接口参数")
    private String params;

    /**
     * 请求服务类型    1-平台端 2-商家端 3-C端
     */
    @ApiModelProperty(value = "请求服务类型    1-平台端 2-商家端 3-C端")
    private Integer type;

    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String ip;

    /**
     * IP来源
     */
    @ApiModelProperty(value = "IP来源")
    private String ipSource;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String describe;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    private String browserName;

    /**
     * 耗时（毫秒）
     */
    @ApiModelProperty(value = "耗时（毫秒）")
    private Integer time;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    private static final long serialVersionUID = 1L;
}
