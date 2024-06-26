/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.express;

import lombok.Data;

import java.util.List;

/**
 * @Author: api.kuaidi100.com
 * @Date: 2020-07-14 16:07
 */
@Data
public class QueryTrackResp {
    /**
     * 消息体，请忽略
     */
    private String message;
    /**
     * 快递单号
     */
    private String nu;
    /**
     * 是否签收标记
     */
    private String ischeck;
    /**
     * 	快递公司编码,一律用小写字母
     */
    private String com;
    /**
     * 通讯状态
     */
    private String status;
    /**
     * 轨迹详情数组
     */
    private List<QueryTrackData> data;
    /**
     * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投，10待清关，11清关中，12已清关，13清关异常，14拒签 等13个状态
     */
    private String state;
    /**
     * 快递单明细状态标记
     */
    private String condition;

    /**
     * 查不到轨迹或者其他问题返回码
     */
    private String returnCode;
    /**
     * 查不到轨迹或者其他问题返回结果
     */
    private boolean result;
}
