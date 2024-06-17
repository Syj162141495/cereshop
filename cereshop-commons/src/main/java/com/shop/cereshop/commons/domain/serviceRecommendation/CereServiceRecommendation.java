package com.shop.cereshop.commons.domain.serviceRecommendation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Data
public class CereServiceRecommendation implements Serializable {
    /**
     * 服务推荐编号
     */
    private Long serviceRecommendationId;

    /**
     * 服务推荐名称
     */
    private String serviceRecommendationName;

    /**
     * 服务推荐类型
     */
    private String serviceRecommendationType;

    /**
     * 服务推荐时间
     */
    private String serviceRecommendationTime;

    /**
     * 服务推荐客户
     */
    private Long serviceRecommendationBuyerId;

    /**
     * 服务推荐状态
     */
    private String serviceRecommendationAdoption;
}
