/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.domain.buyer;

import com.shop.cereshop.commons.constant.IntegerEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * cere_buyer_receive 客户收货地址信息实体类
 * @author 
 */
@Data
@ApiModel(value = "CereBuyerReceive", description = "客户收货地址信息实体类")
public class CereBuyerReceive implements Serializable {

    /**
     * 收货地址id
     */
    @ApiModelProperty(value = "收货地址id")
    private Long receiveId;

    /**
     * 关联客户id
     */
    @ApiModelProperty(value = "关联客户id")
    private Long buyerUserId;

    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String receiveName;

    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "收货人电话")
    private String receivePhone;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String receiveAdress;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 地址标签
     */
    @ApiModelProperty(value = "地址标签")
    private String label;

    /**
     * 是否默认 1-是 0-否
     */
    @ApiModelProperty(value = "是否默认 1-是 0-否")
    private Integer ifDefault;

    public void setIfDefault(Integer ifDefault) {
        this.ifDefault=ifDefault;
        if(IntegerEnum.YES.getCode().equals(ifDefault)){
            this.defult=true;
        }else {
            this.defult=false;
        }
    }

    /**
     * 是否默认
     */
    private boolean defult;

    public void setDefult(boolean defult) {
        if(defult){
            this.ifDefault=1;
        }else {
            this.ifDefault=0;
        }
    }

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
