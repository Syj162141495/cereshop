/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.message;

import lombok.Data;

import java.io.Serializable;

/**
 * cere_message_log 短信发送记录信息实体
 * @author 
 */
@Data
public class CereMessageLog implements Serializable {
    /**
     * 关联短信id
     */
    private Long messageId;

    /**
     * 短信模板编号
     */
    private String template;

    /**
     * 接收者
     */
    private String phone;

    /**
     * 主体
     */
    private String subject;

    /**
     * 发送内容
     */
    private String message;

    /**
     * 执行状态 1-等待执行 2-执行成功 3-执行失败
     */
    private Integer state;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

}
