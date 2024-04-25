/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.param.shopcheck;

import com.shop.cereshop.commons.utils.TimeUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 处理入驻申请请求
 */
@Data
@ApiModel(value = "CheckHandleParam", description = "处理入驻申请请求")
public class CheckHandleParam {

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    /**
     * 生效日期  null-即时生效 有值-指定日期生效
     */
    @ApiModelProperty(value = "生效日期  null-即时生效 有值-指定日期生效")
    private String effectiveDate;

    public void setEffectiveDate(String effectiveDate) {
        if("null".equals(effectiveDate)){
            this.effectiveDate= TimeUtils.today();
        }else {
            this.effectiveDate=effectiveDate;
        }
    }

    /**
     * 生效时限（年）
     */
    @ApiModelProperty(value = "生效时限（年）")
    private Integer effectiveYear;

    /**
     * 入驻处理 1-同意入驻 0-拒绝入驻
     */
    @ApiModelProperty(value = "入驻处理 1-同意入驻 0-拒绝入驻")
    private Integer checkHandle;

    /**
     * 处理原因
     */
    @ApiModelProperty(value = "处理原因")
    private String reason;
}
