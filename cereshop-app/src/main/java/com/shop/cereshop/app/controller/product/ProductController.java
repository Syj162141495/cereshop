/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.controller.product;

import com.shop.cereshop.app.annotation.NoRepeatWebLog;
import com.shop.cereshop.app.page.index.Product;
import com.shop.cereshop.app.page.product.ProductDetail;
import com.shop.cereshop.app.page.product.ProductSkus;
import com.shop.cereshop.app.param.price.ShopPricePageParam;
import com.shop.cereshop.app.param.product.PageProductParam;
import com.shop.cereshop.app.param.product.ProductParam;
import com.shop.cereshop.app.param.product.SkuParam;
import com.shop.cereshop.app.service.buyer.CereBuyerUserService;
import com.shop.cereshop.app.service.compose.CereShopComposeService;
import com.shop.cereshop.app.service.price.CereShopPriceService;
import com.shop.cereshop.app.service.product.CereShopProductService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.tool.CereComposeDetail;
import com.shop.cereshop.commons.domain.tool.CereComposeMergeInfo;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EmptyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品模块
 */
@RestController
@RequestMapping("product")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ProductController")
@Api(value = "商品模块", tags = "商品模块")
public class ProductController {

    @Autowired
    private CereShopProductService cereShopProductService;

    @Autowired
    private CereBuyerUserService cereBuyerUserService;

    @Autowired
    private CereShopComposeService cereShopComposeService;

    @Autowired
    private CereShopPriceService cereShopPriceService;

    /**
     * 商品详情查询
     * @param param
     * @return
     */
    @GetMapping("getById")
    @ApiOperation(value = "商品详情查询")
    public Result<ProductDetail> getById(ProductParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        String token = request.getHeader("Authorization");
        CereBuyerUser user=null;
        if(!EmptyUtils.isEmpty(token)){
            //根据token查询用户信息
            user=cereBuyerUserService.findByToken(token);
        }
        ProductDetail detail=cereShopProductService.getById(param,user);
        return new Result(detail, CoReturnFormat.SUCCESS);
    }

    /**
     * 删除搜索记录
     * @return
     */
    @RequestMapping(value = "deleteAllSearch", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ApiOperation(value = "删除搜索记录")
    @NoRepeatWebLog
    public Result deleteAllSearch(HttpServletRequest request) throws CoBusinessException{
        String token = request.getHeader("Authorization");
        CereBuyerUser user=null;
        String name="";
        if(!EmptyUtils.isEmpty(token)){
            //根据token查询用户信息
            user=cereBuyerUserService.findByToken(token);
            name=user.getWechatName();
        }
        cereShopProductService.deleteAllSearch(user);

        return new Result(CoReturnFormat.SUCCESS,name,"删除搜索记录", "");
    }

    /**
     * 选择商品查询
     * @param param
     * @return
     */
    @GetMapping("getProducts")
    @ApiOperation(value = "选择商品查询")
    public Result<ProductSkus> getProducts(SkuParam param) throws CoBusinessException,Exception{
        ProductSkus products=cereShopProductService.getProducts(param);
        return new Result(products,CoReturnFormat.SUCCESS);
    }

    /**
     * 获取分享图片
     * @param param
     * @return
     */
    @GetMapping("getSharePic")
    @ApiOperation(value = "获取分享图片")
    public Result<String> getSharePic(ProductParam param, HttpServletRequest request) throws CoBusinessException {
        String token = request.getHeader("Authorization");
        CereBuyerUser user=null;
        if(!EmptyUtils.isEmpty(token)){
            //根据token查询用户信息
            user=cereBuyerUserService.findByToken(token);
        }
        String sharePicUrl = cereShopProductService.getSharePic(param,user);
        Result<String> result = new Result();
        result.setCode(CoReturnFormat.SUCCESS);
        result.setData(sharePicUrl);
        return result;
    }

    /**
     * 查询随机排序的商品
     * @param param
     * @return
     */
    @GetMapping("getRandomSortProduct")
    @ApiOperation(value = "查询随机排序的商品")
    public Result<Page<Product>> getRandomSortProduct(PageProductParam param) throws CoBusinessException {
        Page page = cereShopProductService.getRandomSortProduct(param);
        return new Result(page, CoReturnFormat.SUCCESS);
    }

    @GetMapping("selectCompose")
    @ApiOperation(value = "查询组合套餐")
    public Result<List<CereComposeDetail>> selectCompose(ProductParam param) throws CoBusinessException {
        List<CereComposeDetail> list = cereShopComposeService.selectComposeByProductId(param.getProductId());
        return new Result(list, CoReturnFormat.SUCCESS);
    }

    @GetMapping("selectComposeByShopId")
    @ApiOperation(value = "根据店铺id查询组合捆绑套餐")
    public Result<List<CereComposeMergeInfo>> selectComposeByShopId(ProductParam param) throws CoBusinessException {
        List<CereComposeMergeInfo> list = cereShopComposeService.selectComposeByShopId(param.getShopId());
        return new Result(list, CoReturnFormat.SUCCESS);
    }

    @GetMapping("selectProductListByPriceId")
    @ApiOperation(value = "查询定价捆绑套餐")
    public Result<Page<Product>> selectProductListByPriceId(ShopPricePageParam param) throws CoBusinessException {
        Page<Product> list = cereShopPriceService.selectProductListByPriceId(param);
        return new Result(list, CoReturnFormat.SUCCESS);
    }

}
