package com.shop.cereshop.admin.dao.serviceRecommendation;

import com.shop.cereshop.admin.page.buyer.BuyerUser;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.page.serviceRecommendation.ServiceRecommendation;
import com.shop.cereshop.admin.param.serviceRecommendation.ServiceRecommendationParam;
import com.shop.cereshop.commons.domain.serviceRecommendation.CereServiceRecommendation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Mapper
public interface CereServiceRecommendationDAO {

    int insert(CereServiceRecommendation serviceRecommendation);

    int updateByServiceRecommendationId(CereServiceRecommendation serviceRecommendation);

    int deleteByServiceRecommendationId(Long serviceRecommendationId);

    List<CereServiceRecommendation> getAllServiceRecommendations(ServiceRecommendationParam serviceRecommendationParam);

    List<BuyerUser> getAllBuyer();
}
