package com.shop.cereshop.admin.service.serviceRecommendation;

import com.shop.cereshop.admin.page.buyer.BuyerUser;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.page.serviceRecommendation.ServiceRecommendation;
import com.shop.cereshop.admin.param.product.ProductGetAllParam;
import com.shop.cereshop.admin.param.serviceRecommendation.ServiceRecommendationParam;
import com.shop.cereshop.commons.domain.common.Page;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
public interface CereServiceRecommendationService {

    int insertServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam);

    int updateServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam);

    int deleteServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam);

    List<ShopProduct> getShopProducts(ProductGetAllParam productGetAllParam);

    Page<ServiceRecommendation> selectServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam);

    List<BuyerUser> getAllBuyer();
}
