package com.shop.cereshop.commons.domain.serviceRecommendation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Data
public class CereServiceRecommendationProduct implements Serializable {
    /**
     * 服务推荐子表 id
     */
    private Long serviceRecommendationProductId;

    /**
     * 服务推荐 id
     */
    private Long serviceRecommendationId;

    /**
     * 产品 id
     */
    private Long productId;
}
