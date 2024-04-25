/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_platfrom_message 短信账号配置信息实体
 * @author 
 */
@Data
@ApiModel(value = "CerePlatfromMessage", description = "短信账号配置信息实体")
public class CerePlatfromMessage implements Serializable {
    /**
     * 短信id
     */
    @ApiModelProperty(value = "短信id")
    private Long messageId;

    /**
     * 第三方短信名称
     */
    @ApiModelProperty(value = "第三方短信名称")
    private String messageProject;

    /**
     * 短信模板名称
     */
    @ApiModelProperty(value = "短信模板名称")
    private String messageTemplate;

    /**
     * 模板编码
     */
    @ApiModelProperty(value = "模板编码")
    private String templateCode;

    /**
     * 模板签名
     */
    @ApiModelProperty(value = "模板签名")
    private String templateSign;

    /**
     * 模板描述
     */
    @ApiModelProperty(value = "模板描述")
    private String templateDescribe;

    /**
     * 是否启用 1-是 0-否
     */
    @ApiModelProperty(value = "是否启用 1-是 0-否")
    private Integer state;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;

}
