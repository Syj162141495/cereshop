/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.param.tool;

import com.shop.cereshop.commons.domain.tool.CereShopSeckillDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * cere_shop_seckill 新增店铺秒杀活动
 * @author
 */
@Data
@ApiModel(value = "ShopSeckillSaveParam", description = "新增店铺秒杀活动请求")
public class ShopSeckillSaveParam implements Serializable {
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id",required = true)
    private Long shopId;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称",required = true)
    @NotBlank(message = "活动名称不能为空")
    private String seckillName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间",required = true)
    @NotBlank(message = "开始时间不能为空")
    private String effectiveStart;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间",required = true)
    @NotBlank(message = "结束时间不能为空")
    private String effectiveEnd;

    /**
     * 商品限购 1-不限购 2-限购
     */
    @ApiModelProperty(value = "商品限购 1-不限购 2-限购",required = true)
    @NotNull(message = "商品限购不能为空")
    private Integer ifLimit;

    /**
     * 限购几件/人
     */
    @ApiModelProperty(value = "限购几件/人")
    private Integer limitNumber;

    /**
     * 是否限量 1-是 0-否
     */
    @ApiModelProperty(value = "是否限量 1-是 0-否",required = true)
    @NotNull(message = "是否限量不能为空")
    private Integer ifNumber;

    /**
     * 限量数量
     */
    @ApiModelProperty(value = "限量数量")
    private Integer number;

    /**
     * 活动预热   1-停用  2-启用
     */
    @ApiModelProperty(value = "活动预热   1-停用  2-启用",required = true)
    @NotNull(message = "活动预热不能为空")
    private Integer ifEnable;

    /**
     * 预热几小时前
     */
    @ApiModelProperty(value = "预热几小时前")
    private Integer enableTime;

    /**
     * 优惠券是否叠加 1-是 0-否
     */
    @ApiModelProperty(value = "优惠券是否叠加 1-是 0-否",required = true)
    @NotNull(message = "优惠券是否叠加不能为空")
    private Integer ifAdd;

    /**
     * 商品明细数据
     */
    @ApiModelProperty(value = "商品明细数据",required = true)
    @Valid
    private List<CereShopSeckillDetail> details;

    private static final long serialVersionUID = 1L;

}
