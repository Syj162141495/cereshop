package com.shop.cereshop.admin.param.serviceRecommendation;

import com.shop.cereshop.commons.domain.common.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 * @description 服务推荐参数
 * 用于新增或者更新服务推荐
 */
@Data
@ApiModel(value = "ServiceRecommendationParam", description = "新增或更新服务推荐")
public class ServiceRecommendationParam {

    @ApiModelProperty(value = "服务推荐序号")
    private Long serviceRecommendationId;

    @ApiModelProperty(value = "服务推荐名称")
    private String serviceRecommendationName;

    @ApiModelProperty(value = "服务推荐客户")
    private Long serviceRecommendationBuyerId;

    @ApiModelProperty(value = "服务推荐服务数量")
    private String serviceRecommendationServicesCount;

    @ApiModelProperty(value = "服务推荐产品")
    private List<Long> serviceRecommendationProductIds;

    @ApiModelProperty(value = "服务推荐时间")
    private String serviceRecommendationTime;

    @ApiModelProperty(value = "服务推荐类型")
    private String serviceRecommendationType;

    @ApiModelProperty(value = "服务推荐状态")
    private String serviceRecommendationAdoption;

    @ApiModelProperty(value = "当前页码")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize;
}
