/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.member;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员签到表
 */
@Data
public class CereMemberSigninRecord implements Serializable {

    /**
     * 签到id
     */
    private Long signinId;

    /**
     * 客户id
     */
    private Long buyerUserId;

    /**
     * 轮询id,第一天签到则为1，第2天签到则为2，到7之后，从1重新开始
     */
    private Integer termId;

    /**
     * 奖励的成长值
     */
    private Integer growth;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
