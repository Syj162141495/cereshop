package com.shop.cereshop.admin.dao.serviceRecommendation;

import com.shop.cereshop.commons.domain.serviceRecommendation.CereServiceRecommendationProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Mapper
public interface CereServiceRecommendationProductDAO {

    int insert(CereServiceRecommendationProduct serviceRecommendationProduct);

    int deleteByServiceRecommendationId(Long serviceRecommendationId);

    int deleteByServiceRecommendationIdAndProductId(Long serviceRecommendationId, Long productId);

    List<CereServiceRecommendationProduct> selectByServiceRecommendationId(Long serviceRecommendationId);
}
