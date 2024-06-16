package com.shop.cereshop.admin.service.serviceRecommendation.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.product.CereShopProductDAO;
import com.shop.cereshop.admin.dao.serviceRecommendation.CereServiceRecommendationDAO;
import com.shop.cereshop.admin.dao.serviceRecommendation.CereServiceRecommendationProductDAO;
import com.shop.cereshop.admin.page.buyer.BuyerUser;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.page.serviceRecommendation.ServiceRecommendation;
import com.shop.cereshop.admin.param.product.ProductGetAllParam;
import com.shop.cereshop.admin.param.serviceRecommendation.ServiceRecommendationParam;
import com.shop.cereshop.admin.service.product.CereShopProductService;
import com.shop.cereshop.admin.service.serviceRecommendation.CereServiceRecommendationService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.serviceRecommendation.CereServiceRecommendation;
import com.shop.cereshop.commons.domain.serviceRecommendation.CereServiceRecommendationProduct;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Service
public class CereServiceRecommendationServiceImpl implements CereServiceRecommendationService {

    @Autowired
    private CereServiceRecommendationDAO cereServiceRecommendationDAO;

    @Autowired
    private CereServiceRecommendationProductDAO cereServiceRecommendationProductDAO;

    @Autowired
    private CereShopProductDAO cereShopProductDAO;

    /**
     * 新增服务推荐
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public int insertServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam) {
        serviceRecommendationParam
                .setServiceRecommendationProductIds(serviceRecommendationParam.getServiceRecommendationProductIds()
                        .stream().distinct().collect(Collectors.toList()));
        CereServiceRecommendation cereServiceRecommendation = transferServiceRecommendationParamToServiceRecommendationParam(serviceRecommendationParam);
        // 主表推荐
        cereServiceRecommendation.setServiceRecommendationTime(TimeUtils.yyMMddHHmmss());
        int count = cereServiceRecommendationDAO.insert(cereServiceRecommendation);
        for (Long productId : serviceRecommendationParam.getServiceRecommendationProductIds()) {
            // 子表插入
            CereServiceRecommendationProduct cereServiceRecommendationProduct = new CereServiceRecommendationProduct();
            cereServiceRecommendationProduct.setServiceRecommendationId(cereServiceRecommendation.getServiceRecommendationId());
            cereServiceRecommendationProduct.setProductId(productId);
            count += cereServiceRecommendationProductDAO.insert(cereServiceRecommendationProduct);
        }
        return count;
    }

    /**
     * 更新服务推荐
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public int updateServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam) {
        serviceRecommendationParam
                .setServiceRecommendationProductIds(serviceRecommendationParam.getServiceRecommendationProductIds()
                        .stream().distinct().collect(Collectors.toList()));
        CereServiceRecommendation cereServiceRecommendation = transferServiceRecommendationParamToServiceRecommendationParam(serviceRecommendationParam);
        // 更新主表
        int count = cereServiceRecommendationDAO.updateByServiceRecommendationId(cereServiceRecommendation);
        // 更新子表
        count += cereServiceRecommendationProductDAO.deleteByServiceRecommendationId(cereServiceRecommendation.getServiceRecommendationId());
        for (Long productId : serviceRecommendationParam.getServiceRecommendationProductIds()) {
            CereServiceRecommendationProduct cereServiceRecommendationProduct = new CereServiceRecommendationProduct();
            cereServiceRecommendationProduct.setServiceRecommendationId(cereServiceRecommendation.getServiceRecommendationId());
            cereServiceRecommendationProduct.setProductId(productId);
            count += cereServiceRecommendationProductDAO.insert(cereServiceRecommendationProduct);
        }
        return count;
    }

    /**
     * 删除服务推荐
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public int deleteServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam) {
        // 删除子表记录
        int count = cereServiceRecommendationProductDAO.deleteByServiceRecommendationId(serviceRecommendationParam.getServiceRecommendationId());
        // 删除主表记录
        count += cereServiceRecommendationDAO.deleteByServiceRecommendationId(serviceRecommendationParam.getServiceRecommendationId());
        return count;
    }

    public List<ShopProduct> getShopProducts(ProductGetAllParam productGetAllParam) {
        return cereShopProductDAO.getAll(productGetAllParam);
    }

    /**
     * 获取分页数据
     */
    @Override
    public Page<ServiceRecommendation> selectServiceRecommendation(ServiceRecommendationParam serviceRecommendationParam) {
         // 查询服务推荐记录
        List<CereServiceRecommendation> cereServiceRecommendationList = cereServiceRecommendationDAO.getAllServiceRecommendations();
        List<ServiceRecommendation> serviceRecommendationList = new ArrayList<>();
        for (int i = (serviceRecommendationParam.getPageNumber() - 1) * serviceRecommendationParam.getPageSize();
             i < cereServiceRecommendationList.size() && i < serviceRecommendationParam.getPageNumber() * serviceRecommendationParam.getPageSize(); i++) {
            // 查询服务推荐对应的商品
            CereServiceRecommendation cereServiceRecommendation = cereServiceRecommendationList.get(i);
            List<CereServiceRecommendationProduct> cereServiceRecommendationProductList
                    = cereServiceRecommendationProductDAO.selectByServiceRecommendationId(cereServiceRecommendation.getServiceRecommendationId());
            ServiceRecommendation serviceRecommendation = new ServiceRecommendation(cereServiceRecommendation);
            for (CereServiceRecommendationProduct cereServiceRecommendationProduct : cereServiceRecommendationProductList) {
                serviceRecommendation.getServiceRecommendationProductIds().add(cereServiceRecommendationProduct.getProductId());
            }
            serviceRecommendationList.add(serviceRecommendation);
        }
        // PageInfo<ServiceRecommendation> pageInfo = new PageInfo<>(serviceRecommendationList);
        return new Page<>(serviceRecommendationList, cereServiceRecommendationList.size());
    }

    @Override
    public List<BuyerUser> getAllBuyer() {
        return cereServiceRecommendationDAO.getAllBuyer();
    }

    private CereServiceRecommendation transferServiceRecommendationParamToServiceRecommendationParam(ServiceRecommendationParam serviceRecommendationParam) {
        CereServiceRecommendation cereServiceRecommendation = new CereServiceRecommendation();
        cereServiceRecommendation.setServiceRecommendationId(serviceRecommendationParam.getServiceRecommendationId());
        cereServiceRecommendation.setServiceRecommendationName(serviceRecommendationParam.getServiceRecommendationName());
        cereServiceRecommendation.setServiceRecommendationBuyerId(serviceRecommendationParam.getServiceRecommendationBuyerId());
        cereServiceRecommendation.setServiceRecommendationAdoption(serviceRecommendationParam.getServiceRecommendationAdoption());
        cereServiceRecommendation.setServiceRecommendationTime(serviceRecommendationParam.getServiceRecommendationTime());
        cereServiceRecommendation.setServiceRecommendationType(serviceRecommendationParam.getServiceRecommendationProductIds().size() > 1 ? "套餐" : "单项");
        return cereServiceRecommendation;
    }
}
