package com.shop.cereshop.admin.page.serviceRecommendation;

import com.shop.cereshop.commons.domain.serviceRecommendation.CereServiceRecommendation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Data
@ApiModel(value = "服务推荐返回数据实体类")
public class ServiceRecommendation implements Serializable {

    @ApiModelProperty(value = "服务推荐id")
    private Long serviceRecommendationId;

    @ApiModelProperty(value = "服务推荐名称")
    private String serviceRecommendationName;

    @ApiModelProperty(value = "服务推荐客户")
    private Long serviceRecommendationBuyerId;

    @ApiModelProperty(value = "服务推荐时间")
    private String serviceRecommendationTime;

    @ApiModelProperty(value = "服务推荐类型")
    private String serviceRecommendationType;

    @ApiModelProperty(value = "服务推荐状态")
    private String serviceRecommendationAdoption;

    @ApiModelProperty(value = "服务推荐商品 id 列表")
    private List<Long> serviceRecommendationProductIds;

    public ServiceRecommendation(CereServiceRecommendation cereServiceRecommendation) {
        this.serviceRecommendationId = cereServiceRecommendation.getServiceRecommendationId();
        this.serviceRecommendationName = cereServiceRecommendation.getServiceRecommendationName();
        this.serviceRecommendationBuyerId = cereServiceRecommendation.getServiceRecommendationBuyerId();
        this.serviceRecommendationTime = cereServiceRecommendation.getServiceRecommendationTime();
        this.serviceRecommendationType = cereServiceRecommendation.getServiceRecommendationType();
        this.serviceRecommendationAdoption = cereServiceRecommendation.getServiceRecommendationAdoption();
        this.serviceRecommendationProductIds = new ArrayList<>();
    }
}
