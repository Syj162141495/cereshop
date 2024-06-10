package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.dao.product.CereShopProductDAO;
import com.shop.cereshop.admin.page.product.ShopProduct;
import com.shop.cereshop.admin.page.serviceRecommendation.ServiceRecommendation;
import com.shop.cereshop.admin.param.product.ProductGetAllParam;
import com.shop.cereshop.admin.param.serviceRecommendation.ServiceRecommendationParam;
import com.shop.cereshop.admin.service.product.CereShopProductService;
import com.shop.cereshop.admin.service.serviceRecommendation.CereServiceRecommendationService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.product.CereProductClassify;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@RestController
@RequestMapping("serviceRecommendation")
@Slf4j(topic = "ServiceRecommendationController")
@Api(value = "服务推荐模块", tags = "服务推荐模块")
public class ServiceRecommendationController {

    @Autowired
    private CereServiceRecommendationService cereServiceRecommendationService;

    @RequestMapping(value = "insertServiceRecommendation", method = RequestMethod.POST)
    public Result<Object> insertServiceRecommendation(@RequestBody ServiceRecommendationParam serviceRecommendationParam,
                                                      HttpServletRequest request) throws Exception{
        cereServiceRecommendationService.insertServiceRecommendation(serviceRecommendationParam);
        return new Result<>(request.toString(), "新增服务推荐", GsonUtil.objectToGson(serviceRecommendationParam));
    }

    @RequestMapping(value = "updateServiceRecommendation", method = RequestMethod.POST)
    public Result<Object> updateServiceRecommendation(@RequestBody ServiceRecommendationParam serviceRecommendationParam,
                                                                 HttpServletRequest request) throws Exception {
        cereServiceRecommendationService.updateServiceRecommendation(serviceRecommendationParam);
        return new Result<>(request.toString(), "修改服务推荐", GsonUtil.objectToGson(serviceRecommendationParam));
    }

    @RequestMapping(value = "deleteServiceRecommendation", method = RequestMethod.DELETE)
    public Result<Object> deleteServiceRecommendation(@RequestBody ServiceRecommendationParam serviceRecommendationParam,
                                                      HttpServletRequest request) throws Exception {
        cereServiceRecommendationService.deleteServiceRecommendation(serviceRecommendationParam);
        return new Result<>(request.toString(), "删除服务推荐", GsonUtil.objectToGson(serviceRecommendationParam));
    }

    @RequestMapping(value = "serviceRecommendationProducts", method = RequestMethod.POST)
    public Result<List<ShopProduct>> serviceRecommendationProducts(@RequestBody ProductGetAllParam productGetAllParam) throws CoBusinessException {
        return new Result<>(cereServiceRecommendationService.getShopProducts(productGetAllParam));
    }

    @RequestMapping(value = "selectServiceRecommendation", method = RequestMethod.POST)
    public Result<Page<ServiceRecommendation>> selectServiceRecommendation(@RequestBody ServiceRecommendationParam serviceRecommendationParam) {
        Page<ServiceRecommendation> page = cereServiceRecommendationService.selectServiceRecommendation(serviceRecommendationParam);
        return new Result<>(page);
    }

}
