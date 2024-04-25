/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.product.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luciad.imageio.webp.WebPReadParam;
import com.shop.cereshop.app.dao.platformtool.CerePlatformDiscountDAO;
import com.shop.cereshop.app.dao.platformtool.CerePlatformSeckillDAO;
import com.shop.cereshop.app.dao.product.CereShopProductDAO;
import com.shop.cereshop.app.page.canvas.CanvasProduct;
import com.shop.cereshop.app.page.index.Product;
import com.shop.cereshop.app.page.order.ShopDistributor;
import com.shop.cereshop.app.page.product.*;
import com.shop.cereshop.app.param.canvas.CanvasAppProductParam;
import com.shop.cereshop.app.param.index.SearchParam;
import com.shop.cereshop.app.param.product.PageProductParam;
import com.shop.cereshop.app.param.product.ProductParam;
import com.shop.cereshop.app.param.product.ShareParam;
import com.shop.cereshop.app.param.product.SkuParam;
import com.shop.cereshop.app.redis.service.api.StringRedisService;
import com.shop.cereshop.app.service.activity.CerePlatformActivityService;
import com.shop.cereshop.app.service.business.CereBusinessBuyerUserService;
import com.shop.cereshop.app.service.buyer.CereBuyerCommentLikeService;
import com.shop.cereshop.app.service.buyer.CereBuyerReceiveService;
import com.shop.cereshop.app.service.collect.CereBuyerCollectService;
import com.shop.cereshop.app.service.collect.CereBuyerFootprintService;
import com.shop.cereshop.app.service.coupon.CereShopCouponService;
import com.shop.cereshop.app.service.discount.CereShopDiscountDetailService;
import com.shop.cereshop.app.service.discount.CereShopDiscountService;
import com.shop.cereshop.app.service.distributor.CereShopDistributorService;
import com.shop.cereshop.app.service.groupwork.CereShopGroupWorkService;
import com.shop.cereshop.app.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.app.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.app.service.price.CereShopPriceService;
import com.shop.cereshop.app.service.product.CereCommentWordService;
import com.shop.cereshop.app.service.product.CereProductMemberService;
import com.shop.cereshop.app.service.product.CereProductSkuService;
import com.shop.cereshop.app.service.product.CereShopProductService;
import com.shop.cereshop.app.service.scene.CereShopSceneService;
import com.shop.cereshop.app.service.seckill.CereShopSeckillDetailService;
import com.shop.cereshop.app.service.seckill.CereShopSeckillService;
import com.shop.cereshop.app.service.shop.CerePlatformShopservice;
import com.shop.cereshop.app.service.shop.CereShopCommentService;
import com.shop.cereshop.app.service.shop.CereShopConversionService;
import com.shop.cereshop.app.service.shop.CereShopVisitService;
import com.shop.cereshop.app.service.stock.CereStockService;
import com.shop.cereshop.app.utils.AlipayUtil;
import com.shop.cereshop.app.utils.WechatUtil;
import com.shop.cereshop.commons.cache.constants.CacheKeyConstants;
import com.shop.cereshop.commons.cache.product.ProductBo;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.ParamEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.business.CereBusinessBuyerUser;
import com.shop.cereshop.commons.domain.buyer.CereBuyerCommentLike;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.collect.CereBuyerCollect;
import com.shop.cereshop.commons.domain.collect.CereBuyerFootprint;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.distributor.CereShopDistributor;
import com.shop.cereshop.commons.domain.product.*;
import com.shop.cereshop.commons.domain.shop.CereShopConversion;
import com.shop.cereshop.commons.domain.shop.CereShopVisit;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.upload.strategy.FileStrategy;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.QRCodeUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CereShopProductServiceImpl implements CereShopProductService {

    @Autowired
    private CereShopProductDAO cereShopProductDAO;

    @Autowired
    private CerePlatformActivityService cerePlatformActivityService;

    @Autowired
    private CerePlatformShopservice cerePlatformShopservice;

    @Autowired
    private CereBuyerReceiveService cereBuyerReceiveService;

    @Autowired
    private CereShopCommentService cereShopCommentService;

    @Autowired
    private CereShopDistributorService cereShopDistributorService;

    @Autowired
    private CereShopVisitService cereShopVisitService;

    @Autowired
    private CereProductSkuService cereProductSkuService;

    @Autowired
    private CereShopConversionService cereShopConversionService;

    @Autowired
    private CereCommentWordService cereCommentWordService;

    @Autowired
    private CereBuyerCollectService cereBuyerCollectService;

    @Autowired
    private CereBuyerFootprintService cereBuyerFootprintService;

    @Autowired
    private CereShopGroupWorkService cereShopGroupWorkService;

    @Autowired
    private CereShopSeckillService cereShopSeckillService;

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    @Autowired
    private CereBuyerCommentLikeService cereBuyerCommentLikeService;

    @Autowired
    private CereShopCouponService cereShopCouponService;

    @Autowired
    private CereBusinessBuyerUserService cereBusinessBuyerUserService;

    @Autowired
    private FileStrategy fileStrategy;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Autowired
    private CereShopSceneService cereShopSceneService;

    @Autowired
    private CereProductMemberService cereProductMemberService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CerePlatformSeckillDAO cerePlatformSeckillDAO;

    @Autowired
    private CerePlatformDiscountDAO cerePlatformDiscountDAO;

    @Autowired
    private CereShopSeckillDetailService cereShopSeckillDetailService;

    @Autowired
    private CereShopDiscountDetailService cereShopDiscountDetailService;

    @Autowired
    private CereStockService cereStockService;

    @Autowired
    private CereShopPriceService cereShopPriceService;

    /**
     * 商品详情前缀
     */
    @Value("${goods_url}")
    private String goodsUrl;

    /**
     * 商品缓存时间
     */
    @Value("${productCacheTime}")
    private long productCacheTime;

    @Override
    public CereShopProduct selectByPrimaryKey(Long id) {
        return cereShopProductDAO.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public ProductDetail getById(ProductParam param, CereBuyerUser user) throws CoBusinessException,Exception {
        ProductDetail detail = null;
        String time = TimeUtils.yyMMddHHmmss();
        if(user!=null){
            //新增访问记录
            CereShopVisit visit=new CereShopVisit();
            visit.setBuyerUserId(user.getBuyerUserId());
            visit.setShopId(param.getShopId());
            visit.setVisitTime(time);
            visit.setTerminal(param.getTerminal());
            visit.setSystem(param.getSystem());
            visit.setCity(param.getCity());
            cereShopVisitService.insert(visit);
            //新增转化数据
            CereShopConversion cereShopConversion=new CereShopConversion();
            cereShopConversion.setShopId(param.getShopId());
            cereShopConversion.setCreateTime(time);
            cereShopConversion.setType(ParamEnum.CONVERSION_VISIT.getCode());
            cereShopConversionService.insert(cereShopConversion);
            //新增足迹
            CereBuyerFootprint cereBuyerFootprint=new CereBuyerFootprint();
            cereBuyerFootprint.setBuyerUserId(user.getBuyerUserId());
            cereBuyerFootprint.setProductId(param.getProductId());
            cereBuyerFootprint.setCreateTime(time);
            cereBuyerFootprint.setShopId(param.getShopId());
            cereBuyerFootprint.setSkuId(param.getSkuId());
            cereBuyerFootprint.setSelected(IntegerEnum.NO.getCode());
            cereBuyerFootprintService.insert(cereBuyerFootprint);
            //新增商家和客户的绑定关系
            CereBusinessBuyerUser bbu = new CereBusinessBuyerUser();
            bbu.setShopId(param.getShopId());
            bbu.setBuyerUserId(user.getBuyerUserId());
            cereBusinessBuyerUserService.addBusinessBuyerUser(bbu);
        }
        //查询商品详情数据
        detail=cereShopProductDAO.findBySkuId(param.getSkuId(),param.getShopId());

        /*ProductBo productBo = fetchProductCache(param.getShopId(), param.getProductId(), param.getSkuId(), user);
        if (productBo != null) {
            detail = new ProductDetail();
            BeanUtils.copyProperties(productBo, detail);
        }*/
        if(detail!=null){
            //查询正常商品数据逻辑
            detail = assembleActivityInfo(detail, user, param);
            //查询商品图片
            detail.setImages(cereShopProductDAO.findImages(param.getProductId()));
            //查询最新收货地址数据
            if(user!=null){
                detail.setReceive(cereBuyerReceiveService.findlatelyReceiveByUserId(user.getBuyerUserId()));
                //查询该商品收藏id
                CereBuyerCollect collect=cereBuyerCollectService.findByUserProduct(user.getBuyerUserId(),detail.getProductId());
                if(collect!=null){
                    detail.setCollectId(collect.getCollectId());
                    detail.setIfCollect(collect.getState());
                }
            }

            //查询该商品所有规格名
            List<SkuName> names=cereProductSkuService.findSkuNames(param.getProductId());
            if(!EmptyUtils.isEmpty(names)){
                //查询该商品所有规格值数据
                List<SkuValue> list=cereProductSkuService.findValuesByProductId(param.getProductId());
                if(!EmptyUtils.isEmpty(list)){
                    names.forEach(name -> {
                        if(!EmptyUtils.isEmpty(name.getNameCode())){
                            List<SkuValue> values=new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                if(i<0){
                                    i=0;
                                }
                                if(list.get(i).getSkuName().equals(name.getSkuName())){
                                    values.add(list.get(i));
                                    list.remove(i);
                                    i--;
                                }
                            }
                            name.setValues(values);
                        }
                    });
                }
            }
            detail.setNames(names);

            //查询商品评论信息
            List<BuyerComment> comments=cereShopCommentService.findByProductId(param.getProductId());
            //封装评论图片数组数据
            if(!EmptyUtils.isEmpty(comments)){
                comments.forEach(buyerComment -> {
                    buyerComment.setImages(EmptyUtils.getImages(buyerComment.getImage()));
                    buyerComment.setAddImages(EmptyUtils.getImages(buyerComment.getAddImage()));
                    buyerComment.setValues(EmptyUtils.getImages(buyerComment.getValue()));
                    //设置是否点赞
                    if(user!=null){
                        CereBuyerCommentLike cereBuyerCommentLike=cereBuyerCommentLikeService.findByUserIdAndCommentId(user.getBuyerUserId(),buyerComment.getCommentId());
                        if(cereBuyerCommentLike!=null){
                            buyerComment.setIfLike(cereBuyerCommentLike.getIfLike());
                        }
                    }
                });
                //查询该商品评论关联关键词统计数据
                List<CommentWord> words=cereCommentWordService.findByProductId(param.getProductId());
                detail.setWords(words);
            }
            detail.setComments(comments);
            //查询店铺商品总类数
            detail.setClassifyNumber(cereShopProductDAO.findClassifyNumber(param.getShopId()).size());
            //定义优惠券拼接信息
            String couponSplicing="";
            //查询平台优惠券
            List<ProductCoupon> platformCoupons = setPlatformCoupons(user, detail);

            //然后查询店铺优惠券
            List<ProductCoupon> shopCoupons = setShopCoupons(user, detail);

            if(!EmptyUtils.isEmpty(platformCoupons)){
                //设置优惠券内容
                platformCoupons.forEach(tool -> {
                    if (IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(tool.getDiscountMode())) {
                        tool.setContent("满"+tool.getFullMoney().stripTrailingZeros().toPlainString()+
                                "减"+tool.getReduceMoney().stripTrailingZeros().toPlainString()+"元");
                    } else {
                        tool.setContent(tool.getReduceMoney().stripTrailingZeros().toPlainString()+"折券");
                    }
                });
                couponSplicing=platformCoupons.stream().map(ProductCoupon::getContent).collect(Collectors.joining(";"));
            }
            if(!EmptyUtils.isEmpty(shopCoupons)){
                shopCoupons.forEach(tool -> {
                    if(IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(tool.getCouponType())){
                        tool.setContent("满"+tool.getFullMoney().stripTrailingZeros().toPlainString()+
                                "减"+tool.getReduceMoney().stripTrailingZeros().toPlainString()+"元");
                    }else {
                        tool.setContent(tool.getReduceMoney().stripTrailingZeros().toPlainString()+"折券");
                    }
                });
                couponSplicing+=shopCoupons.stream().map(ProductCoupon::getContent).collect(Collectors.joining(";"));
            }
            detail.setCouponSplicing(couponSplicing);
            //查询同类商品
            List<Product> similarProducts = cereShopProductDAO.findSimilarProducts(detail.getClassifyId());
            detail.setSimilarProducts(similarProducts);
        }
        return detail;
    }

    /**
     * 组装商品活动信息
     * @param detail
     * @param user
     * @param param
     * @return
     * @throws Exception
     */
    private ProductDetail assembleActivityInfo(ProductDetail detail, CereBuyerUser user, ProductParam param) throws Exception {
        long start = System.currentTimeMillis();

        //拼团
        Long shopGroupWorkId = detail.getShopGroupWorkId();
        //查询拼团商品数据
        detail= cereShopGroupWorkService.setActivityInfo(shopGroupWorkId,user,param,detail);
        log.info("queryGroupWork use time {} mills", System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        //平台秒杀id
        Long platformSeckillId = detail.getPlatformSeckillId();
        if(platformSeckillId != null && platformSeckillId > 0){
            //查询平台秒杀商品数据
            detail = cerePlatformSeckillService.setActivityInfo(platformSeckillId, user, detail);
            log.info("queryPlatformSeckill use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //平台折扣id
        Long platformDiscountId = detail.getPlatformDiscountId();
        if(platformDiscountId != null && platformDiscountId > 0){
            //查询平台限时折扣商品数据
            detail = cerePlatformDiscountService.setActivityInfo(platformDiscountId, user, detail);
            log.info("queryPlatformDiscount use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //商家秒杀
        Long shopSeckillId = detail.getShopSeckillId();
        //查询秒杀商品数据
        detail = cereShopSeckillService.setActivityInfo(shopSeckillId, user,param,detail);
        log.info("queryShopSeckill use time {} mills", System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        //商家折扣
        Long shopDiscountId = detail.getShopDiscountId();
        //查询限时折扣商品数据
        detail = cereShopDiscountService.setActivityInfo(shopDiscountId, user,param,detail);
        log.info("queryShopDiscount use time {} mills", System.currentTimeMillis() - start);
        start = System.currentTimeMillis();

        // 定价捆绑 - 商品详情页暂时没用到
        Long priceId = cereShopPriceService.selectPriceByProductId(detail.getProductId());
        if (priceId != null) {
            detail.setActivityType(IntegerEnum.ACTIVITY_TYPE_PRICE.getCode());
            detail.setActivityName(IntegerEnum.ACTIVITY_TYPE_PRICE.getName());
            detail.setPriceId(priceId);
        }

        // 组合捆绑 - 商品详情页暂时没用到

        //场景营销
        if (detail.getActivityType() == null || detail.getActivityType() == 0) {
            detail = cereShopSceneService.setActivityInfo(user, detail);
            log.info("queryShopScene use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //其它活动可能已经设置了该参数，所以这里做一下null判断
        if (detail.getMap() == null) {
            //查询该商品所有组合规格数据封装到map
            List<Sku> skus=cereProductSkuService.findSimpleSkuByProductId(param.getProductId());
            log.info("findSimpleSkuByProductId use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
            if(!EmptyUtils.isEmpty(skus)){
                Map<String, Sku> map = new HashMap<>();
                String image=detail.getImage();
                skus.forEach(sku -> {
                    if (EmptyUtils.isEmpty(sku.getImage())) {
                        sku.setImage(image);
                    }
                    //查询规格所有规格值拼接字符串
                    if(EmptyUtils.isEmpty(sku.getValueCodes())){
                        sku.setValueCodes("单款项");
                    }
                    map.put(sku.getValueCodes(), sku);
                });
                detail.setMap(map);
            }
        }

        //会员价 需要保证先查好 sku的相关信息
        if (detail.getActivityType() == null || detail.getActivityType() == 0) {
            detail = cereProductMemberService.setActivityInfo(user, detail);
            log.info("queryProductMember use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //total和surplusNumber兜底逻辑
        if (detail.getTotal() == null || detail.getSurplusNumber() == null) {
            ProductStockInfo stockInfo = cereProductSkuService.findProductStockInfo(detail.getProductId());
            log.info("queryProductStockInfo use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
            if (stockInfo != null) {
                if (detail.getTotal() == null) {
                    detail.setTotal(stockInfo.getTotal());
                }
                if (detail.getSurplusNumber() == null) {
                    detail.setSurplusNumber(stockInfo.getStockNumber());
                }
            }
        }

        return detail;
    }

    /**
     * 组装商品活动信息
     * @param detail
     * @param user
     * @param param
     * @return
     * @throws Exception
     */
    private ProductDetail assembleActivityInfoForCache(ProductDetail detail, CereBuyerUser user, ProductParam param) throws Exception {
        long start = System.currentTimeMillis();

        //拼团
        Long shopGroupWorkId = detail.getShopGroupWorkId();
        //查询拼团商品数据
        if (shopGroupWorkId != null && shopGroupWorkId > 0) {
            detail= cereShopGroupWorkService.setActivityInfo(shopGroupWorkId,user,param,detail);
            log.info("queryGroupWork use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //平台秒杀id
        Long platformSeckillId = detail.getPlatformSeckillId();
        if(platformSeckillId != null && platformSeckillId > 0){
            //查询平台秒杀商品数据
            detail = cerePlatformSeckillService.setActivityInfo(platformSeckillId, user, detail);
            log.info("queryPlatformSeckill use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //平台折扣id
        Long platformDiscountId = detail.getPlatformDiscountId();
        if(platformDiscountId != null && platformDiscountId > 0){
            //查询平台限时折扣商品数据
            detail = cerePlatformDiscountService.setActivityInfo(platformDiscountId, user, detail);
            log.info("queryPlatformDiscount use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //商家秒杀
        Long shopSeckillId = detail.getShopSeckillId();
        if (shopSeckillId != null && shopSeckillId > 0) {
            //查询秒杀商品数据
            detail = cereShopSeckillService.setActivityInfo(shopSeckillId, user,param,detail);
            log.info("queryShopSeckill use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //商家折扣
        Long shopDiscountId = detail.getShopDiscountId();
        if (shopDiscountId != null && shopDiscountId > 0) {
            //查询限时折扣商品数据
            detail = cereShopDiscountService.setActivityInfo(shopDiscountId, user,param,detail);
            log.info("queryShopDiscount use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //场景营销
        if (detail.getActivityType() == null || detail.getActivityType() == 0) {
            detail = cereShopSceneService.setActivityInfo(user, detail);
            log.info("queryShopScene use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //其它活动可能已经设置了该参数，所以这里做一下null判断
        /*if (detail.getMap() == null) {
            //查询该商品所有组合规格数据封装到map
            List<Sku> skus=cereProductSkuService.findSimpleSkuByProductId(param.getProductId());
            log.info("findSimpleSkuByProductId use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
            if(!EmptyUtils.isEmpty(skus)){
                Map<String, Sku> map = new HashMap<>();
                String image=detail.getImage();
                skus.forEach(sku -> {
                    if (EmptyUtils.isEmpty(sku.getImage())) {
                        sku.setImage(image);
                    }
                    //查询规格所有规格值拼接字符串
                    if(EmptyUtils.isEmpty(sku.getValueCodes())){
                        sku.setValueCodes("单款项");
                    }
                    map.put(sku.getValueCodes(), sku);
                });
                detail.setMap(map);
            }
        }*/

        //会员价 需要保证先查好 sku的相关信息
        if (detail.getActivityType() == null || detail.getActivityType() == 0) {
            detail = cereProductMemberService.setActivityInfo(user, detail);
            log.info("queryProductMember use time {} mills", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }

        //total和surplusNumber兜底逻辑
        if (detail.getTotal() == null || detail.getSurplusNumber() == null) {
            ProductStockInfo stockInfo = cereProductSkuService.findProductStockInfo(detail.getProductId());
            log.info("queryProductStockInfo use time {} mills", System.currentTimeMillis() - start);
            if (stockInfo != null) {
                if (detail.getTotal() == null) {
                    detail.setTotal(stockInfo.getTotal());
                }
                if (detail.getSurplusNumber() == null) {
                    detail.setSurplusNumber(stockInfo.getStockNumber());
                }
            }
        }

        return detail;
    }

    /**
     * 设置平台优惠券信息
     * @return
     */
    private List<ProductCoupon> setPlatformCoupons(CereBuyerUser user, ProductDetail detail) {
        List<ProductCoupon> platformCoupons = null;
        // 有参加 拼团、平台秒杀、平台折扣、商家秒杀，商家折扣 其中之后，设置了不允许优惠券叠加
        if (detail.getIfAdd() != null && IntegerEnum.NO.getCode().equals(detail.getIfAdd())) {
            return platformCoupons;
        }
        if(user!=null){
            //过滤已使用和已过期的优惠券
            platformCoupons=cerePlatformActivityService.findCouponByProductIdAndUserId(user.getBuyerUserId(),detail.getProductId());
            platformCoupons=platformCoupons.stream()
                    .filter(productCoupon -> IntegerEnum.COUPON_NOT_HAVE.getCode().equals(productCoupon.getState())
                            ||IntegerEnum.COUPON_HAVE.getCode().equals(productCoupon.getState()))
                    .peek(productCoupon -> {
                        if(!EmptyUtils.isEmpty(productCoupon.getFrequency())){
                            //如果有限制领取次数
                            if(productCoupon.getFrequency().equals(productCoupon.getCount())){
                                //如果限制领取次数=用户已领取优惠券总数,修改店铺优惠券状态为已领取
                                productCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
                            }
                        }else {
                            //如果无限制领取
                            if(productCoupon.getStockNumber()>0){
                                //未领完,修改状态为未领取
                                productCoupon.setState(IntegerEnum.COUPON_NOT_HAVE.getCode());
                            }
                        }
                    }).collect(Collectors.toList());
        }else {
            platformCoupons=cerePlatformActivityService.findCouponByProductId(detail.getProductId());
        }
        detail.setMarkTools(platformCoupons);
        //设置活动图片数组
        if(!EmptyUtils.isEmpty(platformCoupons)){
            detail.setCouponImages(platformCoupons.stream().map(ProductCoupon::getImage).distinct().collect(Collectors.toList()));
        }
        return platformCoupons;
    }

    /**
     * 设置商家优惠券
     * @param user
     * @param detail
     * @return
     */
    private List<ProductCoupon> setShopCoupons(CereBuyerUser user, ProductDetail detail) {
        List<ProductCoupon> shopCoupons = null;
        // 有参加 拼团、平台秒杀、平台折扣、商家秒杀，商家折扣 其中之后，设置了不允许优惠券叠加
        if (detail.getIfAdd() != null && IntegerEnum.NO.getCode().equals(detail.getIfAdd())) {
            return shopCoupons;
        }
        if(user!=null){
            shopCoupons=cereShopCouponService.findByProductIdAndUserId(user.getBuyerUserId(),detail.getProductId());
            if(!EmptyUtils.isEmpty(shopCoupons)){
                //设置优惠券状态
                shopCoupons.forEach(shopCoupon -> {
                    if(!EmptyUtils.isEmpty(shopCoupon.getFrequency())){
                        //如果有限制领取次数
                        if(shopCoupon.getFrequency().equals(shopCoupon.getCount())){
                            //如果限制领取次数=用户已领取优惠券总数,修改店铺优惠券状态为已领取
                            shopCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
                        }
                    }else {
                        //无限制领取次数,修改状态为未领取
                        shopCoupon.setState(IntegerEnum.COUPON_NOT_HAVE.getCode());
                    }
                });
            }
        }else {
            shopCoupons=cereShopCouponService.findByProductId(detail.getProductId());
        }
        detail.setShopMarkTools(shopCoupons);
        return shopCoupons;
    }

    @Override
    public ProductSkus getProducts(SkuParam param) throws CoBusinessException,Exception {
        //查询该商品所有规格名
        List<SkuName> names=cereProductSkuService.findSkuNames(param.getProductId());
        if(!EmptyUtils.isEmpty(names)){
            if(!EmptyUtils.isEmpty(names.get(0).getSkuName())){
                //多款式
                names.forEach(name -> name.setValues(cereProductSkuService.findSkuValues(name.getSkuName(),param.getProductId())));
            }else {
                //单款式
                names.get(0).setValues(cereProductSkuService.findOneValues(param.getSkuId()));
            }
        }
        //查询当前商品第一条规格数据
        ProductSkus productSkus=null;
        if(!EmptyUtils.isEmpty(param.getSkuId())){
            productSkus=cereProductSkuService.findSku(param.getSkuId());
        }else {
            productSkus=cereProductSkuService.findFirstSku(param.getProductId());
        }
        if(productSkus!=null){
            productSkus.setNames(names);
            //查询活动数据
            SkuTool skuTool=cereProductSkuService.findTool(param.getProductId());
            if(skuTool!=null){
                if(IntegerEnum.COUPON_STATE_START.getCode().equals(skuTool.getState())){
                    skuTool.setIfEnable(IntegerEnum.NO.getCode());
                }else {
                    if(IntegerEnum.ENABLE_START.getCode().equals(skuTool.getIfEnable())
                            &&!EmptyUtils.isEmpty(skuTool.getEnableTime())){
                        //如果开启活动预热,计算预热几小时前的时间
                        String enableTime=TimeUtils.headDate(skuTool.getStartTime(),skuTool.getEnableTime());
                        //判断当前时间是否处于预热时间和活动开始时间之间
                        if(TimeUtils.isBelong(enableTime,skuTool.getStartTime())){
                            //如果处于,需要展示预热信息
                            skuTool.setIfEnable(IntegerEnum.YES.getCode());
                        }
                    }
                }
                productSkus.setSkuTool(skuTool);
            }
        }
        //查询该商品所有组合规格数据封装到map
        List<Sku> skus=cereProductSkuService.findSkuByProductId(param.getProductId());
        if(!EmptyUtils.isEmpty(skus)){
            Map<String,Sku> map=new HashMap<>();
            ProductSkus finalProductSkus = productSkus;
            //定义是否活动数据
            boolean flag=false;
            skus.forEach(sku -> {

                if(EmptyUtils.isEmpty(sku.getImage())){
                    sku.setImage(finalProductSkus.getImage());
                }
                //查询规格所有规格值拼接字符串
                String key = cereProductSkuService.findValueBySkuId(sku.getSkuId());
                if(EmptyUtils.isEmpty(key)){
                    key="单款项";
                }
                map.put(key,sku);
            });
            productSkus.setMap(map);
        }
        return productSkus;
    }

    @Override
    public List<Product> getSearchProducts(SearchParam param) throws CoBusinessException {
        return cereShopProductDAO.getSearchProducts(param);
    }

    @Override
    public ShareProduct share(ShareParam shareParam, CereBuyerUser user) throws CoBusinessException,Exception {
        //查询商品数据
        ShareProduct shareProduct=cereShopProductDAO.findShareProduct(shareParam.getSkuId());
        if(shareProduct!=null){
            shareProduct.setName(user.getWechatName());
            shareProduct.setHeadImage(user.getHeadImage());
            //生成小程序码
//            RestTemplate rest = new RestTemplate();
//            InputStream inputStream = null;
//            AppletTokenResult appletTokenResult = QRCodeUtil.getAppletToken("");
//            if(appletTokenResult == null || StringUtil.isEmpty(appletTokenResult.getAccess_token())){
////                throw new CommonException("小程序token获取失败！");
//            }
//            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="
//                    +appletTokenResult.getAccess_token();
//            Map<String, Object> param= new HashMap<String, Object>();
//            String urlString = URLEncoder.encode("=", "GBK");
//            //查询分销员邀请码
//            String invitationCode = cereShopDistributorService.findInvitationCode(shareParam.getDistributorId());
//            param.put("scene", shareParam.getShopId()+"-"+
//                    shareParam.getDistributorId()+"-"+invitationCode);
//            param.put("page", "https://shopapi.zkthink.com/#/pages/goodsModule/goodsDetails?id='"+shareProduct.getProductId()
//                    +"'&salesId='"+shareParam.getDistributorId()+"'");
//            param.put("width", 430);
//            param.put("auto_color", false);
//            Map<String,Object> line_color = new HashMap<>();
//            line_color.put("r", 0);
//            line_color.put("g", 0);
//            line_color.put("b", 0);
//            param.put("line_color", line_color);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> requestEntity = new HttpEntity<String>(new Gson().toJson(param), headers);
//            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST,
//                    requestEntity, byte[].class, new Object[0]);
//            byte[] result = entity.getBody();
//            inputStream = new ByteArrayInputStream(result);
//            byte[] bytes = QRCodeUtil.toByteArray(inputStream);
//            String qrcode = uploadService.uploadByte("商品分享", bytes);
//            shareProduct.setShareImage(qrcode);
        }
        return shareProduct;
    }

    @Override
    public Page getCanvasProducts(CanvasAppProductParam param) throws CoBusinessException,Exception {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<CanvasProduct> list=null;
        if(IntegerEnum.CANVAS_ACTIVITY_GROUP_WORK.getCode().equals(param.getType())){
            list=cereShopProductDAO.getGroupWorkProducts(param);
        }else if(IntegerEnum.CANVAS_ACTIVITY_SECKILL.getCode().equals(param.getType())){
            list=cereShopProductDAO.getSeckillProducts(param);
        }else if(IntegerEnum.CANVAS_ACTIVITY_DISCOUNT.getCode().equals(param.getType())){
            list=cereShopProductDAO.getDiscountProducts(param);
        }else {
            list=cereShopProductDAO.getCanvasProducts(param);
        }
        if(!EmptyUtils.isEmpty(list)){
            for (CanvasProduct product : list) {
                ProductBo productBo = fetchProductCache(product.getShopId(), product.getProductId(), product.getSkuId(), null, true);
                if (productBo != null) {
                    product.setPrice(productBo.getPrice());
                    product.setOriginalPrice(productBo.getOriginalPrice());
                    product.setActivityType(productBo.getActivityType());
                    product.setIfEnable(productBo.getIfEnable());
                    product.setTime(productBo.getTime());
                    product.setStockNumber(productBo.getSurplusNumber());
                }
                /*if(IntegerEnum.COUPON_STATE_START.getCode().equals(product.getState())){
                    //活动进行中
                    product.setIfEnable(IntegerEnum.NO.getCode());
                    //设置秒杀活动倒计时
                    product.setTime(TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),product.getEndTime()));
                } else if(IntegerEnum.COUPON_STATE_READY.getCode().equals(product.getState())){
                    if(IntegerEnum.ENABLE_START.getCode().equals(product.getIfEnable())
                            &&!EmptyUtils.isEmpty(product.getEnableTime())){
                        //如果开启活动预热,计算预热几小时前的时间
                        String enableTime= TimeUtils.headDate(product.getStartTime(),product.getEnableTime());
                        //判断当前时间是否处于预热时间和活动开始时间之间
                        if(TimeUtils.isBelong(enableTime,product.getStartTime())){
                            //如果处于,需要展示预热信息
                            product.setIfEnable(IntegerEnum.YES.getCode());
                        }
                    }
                }*/
            }
        }
        PageInfo<CanvasProduct> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<Classify> getClassify() throws CoBusinessException {
        //查询所有一级类目
        List<Classify> list=cereShopProductDAO.findAllClassify();
        //查询所有子节点类目
        List<Classify> childs=cereShopProductDAO.findChildsClassify();
        if(!EmptyUtils.isEmpty(list)){
            Map<String,Integer> map=new HashMap<>();
            list.forEach((classify -> {
                setChildsIndex(classify,childs,map);
            }));
        }
        return list;
    }

    @Override
    public CereShopDistributor check(Long shopId, CereBuyerUser user) throws CoBusinessException {
        return cereShopDistributorService.check(shopId,user.getBuyerUserId());
    }

    @Override
    public void deleteAllSearch(CereBuyerUser user) throws CoBusinessException {
        if(user!=null){
            cereShopProductDAO.deleteAllSearch(user.getBuyerUserId());
        }
    }

    @Override
    public List<String> findImages(Long productId) {
        return cereShopProductDAO.findImages(productId);
    }

    @Override
    public List<String> findSkuImages(Long productId) {
        return cereShopProductDAO.findSkuImages(productId);
    }

    @Override
    public List<Product> findSimilarProducts(Long classifyId) {
        return cereShopProductDAO.findSimilarProducts(classifyId);
    }

    @Override
    public List<Long> findClassifyNumber(Long shopId) {
        return cereShopProductDAO.findClassifyNumber(shopId);
    }

    @Override
    public Integer findPayNumber(Long skuId) {
        return cereShopProductDAO.findPayNumber(skuId);
    }

    @Override
    public String getSharePic(ProductParam param, CereBuyerUser user) throws CoBusinessException {
        if (user == null) {
            throw new CoBusinessException(CoReturnFormat.USER_NOT_LOGIN);
        }
        try {
            ProductDetail detail = getById(param,user);
            int env = param.getTerminal();
            String itemPicUrl = CollectionUtils.isNotEmpty(detail.getImages())?detail.getImages().get(0):"";
            String shareHeadUrl = user.getHeadImage();
            if (StringUtils.isBlank(shareHeadUrl)) {
                shareHeadUrl = StringEnum.DEFAULT_HEAD_IMAGE.getCode();
            }
            String shareName = user.getName();
            if (StringUtils.isBlank(shareName)) {
                shareName = user.getWechatName();
            }
            String itemName = detail.getProductName();
            Double itemPrice = detail.getPrice().doubleValue();
            ShopDistributor distributor = cereShopDistributorService.findByPhone(user.getPhone(),param.getShopId());
            Long distributorId = null;
            if (distributor != null) {
                distributorId = distributor.getDistributorId();
            }
            String itemUrlSuffix = "?shopId="+param.getShopId()+"&productId="+param.getProductId()+"&skuId="+param.getSkuId()+"&salesId="+distributorId;
            String accessToken = WechatUtil.getAccessToken();
            return genPoster(env,itemPicUrl,shareHeadUrl,shareName,itemName,itemPrice,itemUrlSuffix,accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page getRandomSortProduct(PageProductParam param) {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Product> list=cereShopProductDAO.getRandomSortProduct(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(a -> {
                //查询付款人数
                a.setUsers(cerePlatformShopservice.findPayUsers(a.getProductId()));
                //设置活动信息
                ProductBo productBo = fetchProductCache(a.getShopId(), a.getProductId(), a.getSkuId(), null, false);
                if (productBo != null) {
                    a.setActivityType(productBo.getActivityType());
                    a.setOriginalPrice(productBo.getOriginalPrice());
                    a.setPrice(productBo.getPrice());
                    a.setTotal(productBo.getTotal());
                }
            });
        }
        PageInfo<Product> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    /**
     * 查询skuId对应的缓存
     * @param shopId
     * @param productId
     * @param skuId
     * @return
     */
    @Override
    public ProductBo fetchProductCache(Long shopId, Long productId, Long skuId, CereBuyerUser user, boolean needRealtimeStock) {
        if (productId == null || productId <= 0) {
            return null;
        }
        log.info("fetchProductCache shopId = {}, productId = {}, skuId = {}, needRealtimeStock = {}", shopId, productId, skuId, needRealtimeStock);
        long start = System.currentTimeMillis();
        String cacheKey = CacheKeyConstants.SKU_CACHE_PREFIX + "_" + productId.toString() + "_" + skuId;
        String productStr = (String)stringRedisService.get(cacheKey);
        if (StringUtils.isNotBlank(productStr)) {
            ProductBo productBo = JSON.parseObject(productStr, ProductBo.class);
            //缓存没有超过配置的时间，才可以返回，否则需要重新查询
            if (System.currentTimeMillis() - productBo.getLastUpdateTimestamp() <= productCacheTime) {
                if (needRealtimeStock) {
                    assembleStock(productBo,skuId);
                }
                log.info("fetchProductCache byCache productId = {}, skuId = {}, use time {} mills", productId, skuId, System.currentTimeMillis() - start);
                return productBo;
            }
        }

        ProductParam param = new ProductParam();
        param.setShopId(shopId);
        param.setProductId(productId);
        param.setSkuId(skuId);
        ProductDetail detail = cereShopProductDAO.findBySkuId(param.getSkuId(),param.getShopId());
        if (detail == null) {
            return null;
        }
        try {
            detail = assembleActivityInfoForCache(detail, user, param);
            ProductBo productBo = new ProductBo();
            BeanUtils.copyProperties(detail, productBo);
            productBo.setLastUpdateTimestamp(System.currentTimeMillis());
            stringRedisService.set(cacheKey, JSON.toJSONString(productBo));
            log.info("fetchProductCache byDb productId = {}, skuId = {}, use time {} mills", productId, skuId, System.currentTimeMillis() - start);
            return productBo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 库存变化频繁，需要重新查数据库
     * @param productBo
     */
    private void assembleStock(ProductBo productBo, Long skuId) {
        if (productBo.getMap() != null) {
            boolean useSameStock = false;
            Integer total = 0;
            Integer stockNumber = 0;
            productBo.setTotal(null);
            productBo.setSurplusNumber(null);
            Long productId = productBo.getProductId();
            for (Sku sku:productBo.getMap().values()) {
                if (useSameStock) {
                    sku.setTotal(total);
                    sku.setStockNumber(stockNumber);
                    continue;
                }
                Integer activityType = sku.getActivityType();

                if (IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode().equals(activityType)
                    || IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode().equals(activityType)) {

                    ProductStockInfo activityProductStock = cereStockService.getActivityProductStock(activityType, productId);

                    if (activityProductStock != null) {
                        total = activityProductStock.getTotal();
                        stockNumber = activityProductStock.getStockNumber();
                        sku.setTotal(total);
                        sku.setStockNumber(stockNumber);
                        productBo.setTotal(total);
                        productBo.setSurplusNumber(stockNumber);
                        useSameStock = true;
                    } else {
                        ProductStockInfo stockInfo = null;
                        if (IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode().equals(activityType)) {
                            stockInfo = cerePlatformSeckillDAO.selectProductStockInfo(sku.getPlatformSeckillId(), productBo.getProductId());
                        } else {
                            stockInfo = cerePlatformDiscountDAO.selectProductStockInfo(sku.getPlatformDiscountId(), productBo.getProductId());
                        }
                        if (stockInfo != null) {
                            total = stockInfo.getTotal();
                            stockNumber = stockInfo.getStockNumber();
                            sku.setTotal(total);
                            sku.setStockNumber(stockNumber);
                            productBo.setTotal(total);
                            productBo.setSurplusNumber(stockNumber);
                            useSameStock = true;
                            //更新缓存
                            cereStockService.updateActivityProductStock(activityType, productId, total, stockNumber);
                        }
                    }
                } else if (IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode().equals(activityType)
                    || IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode().equals(activityType)) {

                    ProductStockInfo activitySkuStock = cereStockService.getActivitySkuStock(activityType, sku.getSkuId());

                    if (activitySkuStock != null) {
                        sku.setTotal(activitySkuStock.getTotal());
                        sku.setStockNumber(activitySkuStock.getStockNumber());
                        if (sku.getSkuId().equals(skuId)) {
                            productBo.setTotal(sku.getTotal());
                            productBo.setSurplusNumber(sku.getStockNumber());
                        }
                    } else {
                        ProductStockInfo stockInfo = null;
                        if (IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode().equals(activityType)) {
                            stockInfo = cereShopSeckillDetailService.selectSkuStockInfo(sku.getShopSeckillId(), sku.getSkuId());
                        } else {
                            stockInfo = cereShopDiscountDetailService.selectSkuStockInfo(sku.getShopDiscountId(), sku.getSkuId());
                        }
                        if (stockInfo != null && stockInfo.getTotal() != null && stockInfo.getStockNumber() != null) {
                            sku.setTotal(stockInfo.getTotal());
                            sku.setStockNumber(stockInfo.getStockNumber());
                            if (sku.getSkuId().equals(skuId)) {
                                productBo.setTotal(sku.getTotal());
                                productBo.setSurplusNumber(sku.getStockNumber());
                            }
                            //更新缓存
                            cereStockService.updateActivitySkuStock(activityType, skuId, stockInfo.getTotal(), stockInfo.getStockNumber());
                        }
                    }
                }
            }
            //上面的for循环，没有查到相应的库存信息
            if (productBo.getTotal() == null || productBo.getSurplusNumber() == null) {
                boolean hasNull = false;
                Integer sumTotal = 0;
                Integer sumStockNumber = 0;
                for (Sku sku:productBo.getMap().values()) {
                    ProductStockInfo skuStockInfo = cereStockService.getSkuStockInfo(sku.getSkuId());
                    if (skuStockInfo != null) {
                        sku.setTotal(skuStockInfo.getTotal());
                        sku.setStockNumber(skuStockInfo.getStockNumber());
                        sumTotal += sku.getTotal();
                        sumStockNumber += sku.getStockNumber();
                    } else {
                        hasNull = true;
                        break;
                    }
                }
                //缓存查不到，就到数据库去查
                if (hasNull) {
                    sumTotal = 0;
                    sumStockNumber = 0;
                    List<Sku> skuList = cereProductSkuService.findSimpleSkuByProductId(productBo.getProductId());
                    Map<Long,Sku> skuMap = new HashMap<>();

                    for (Sku sku:skuList) {
                        skuMap.put(sku.getSkuId(), sku);
                        sumTotal += sku.getTotal();
                        sumStockNumber += sku.getStockNumber();
                    }
                    for (Sku sku:productBo.getMap().values()) {
                        Sku querySku = skuMap.get(sku.getSkuId());
                        if (querySku != null) {
                            sku.setTotal(querySku.getTotal());
                            sku.setStockNumber(querySku.getStockNumber());
                            //更新缓存
                            cereStockService.updateSkuStock(sku.getSkuId(), querySku.getTotal(), querySku.getStockNumber());
                        }
                    }
                }

                productBo.setTotal(sumTotal);
                productBo.setSurplusNumber(sumStockNumber);
            }
        }
    }

    private Classify setChildsIndex(Classify parent, List<Classify> all, Map<String,Integer> map) throws ArrayIndexOutOfBoundsException{
        if(!parent.getParentId().equals(0l)){
            //回调进来的,设置回调执行次数+1
            map.put("callback",map.get("callback")+1);
        }else {
            //如果是根节点进来,初始化回调执行次数
            map.put("callback",0);
        }
        List<Classify> childs=new ArrayList<>();
        if(!EmptyUtils.isEmpty(all)){
            for (int i = 0; i < all.size(); i++) {
                if(all.size()>0){
                    if(i<0){
                        i=0;
                    }
                    Classify classify = all.get(i);
                    //设置按钮菜单权限
                    if(parent.getId().equals(classify.getParentId())){
                        all.remove(i);
                        i--;
                        //执行回调
                        Classify itemPermission = setChildsIndex(classify, all,map);
                        childs.add(itemPermission);
                        //判断当前是否回到最高级菜单节点
                        if(parent.getParentId().equals(0l)){
                            //如果是,计算索引值=当前索引值-（回调执行次数-1）
                            i=i-(map.get("callback")-1);
                            //初始化回调执行次数
                            map.put("callback",0);
                        }
                    }
                }
            }
            parent.setChilds(childs);
        }
        return parent;
    }

    /**
     * 生成分享海报
     * @param env 1-APP 2-微信小程序 3-H5 4-支付宝小程序
     * @param itemPicUrl
     * @param shareHeadUrl
     * @param shareName
     * @param itemName
     * @param itemPrice
     * @param itemUrlSuffix
     * @param accessToken
     */
    private String genPoster(int env, String itemPicUrl, String shareHeadUrl,
                             String shareName, String itemName, Double itemPrice,
                             String itemUrlSuffix, String accessToken) throws Exception {
        int width = 640;
        int height = 1052;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();

        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.OPAQUE);
        g2d.dispose();
        g2d = image.createGraphics();

        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, width, height);

        g2d.setColor(new Color(182,249,225));
        g2d.setStroke(new BasicStroke(1));
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        BufferedImage proImg = readImgInner(itemPicUrl);
        g2d.drawImage(proImg.getScaledInstance(600, 600, Image.SCALE_SMOOTH), 20, 0, null);

        BufferedImage shareHeadImg = readImgInner(shareHeadUrl);

        int baseHeight = 610;

        //xxx分享了一个商品
        g2d.setPaint(Color.GRAY);
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.PLAIN, 27));

        g2d.drawImage(shareHeadImg.getScaledInstance(120, 120, Image.SCALE_SMOOTH), 50, baseHeight, null);
        g2d.drawString(shareName + "分享了一个商品", 210, baseHeight + 60);

        //商品名称
        g2d.setPaint(Color.BLACK);
        final Font logoFont = new Font("Alibaba PuHuiTi R", Font.PLAIN, 21);
        g2d.setFont(logoFont);
        FontMetrics fm2 = g2d.getFontMetrics(g2d.getFont());
        // itemName = "潮流及时就算这么来的然后席卷了整个欧洲";
        int textWidth2 = fm2.stringWidth(itemName);
        while(textWidth2 > 260 && itemName.length() > 2) {
            itemName = itemName.substring(0,itemName.length()-1);
            textWidth2 = fm2.stringWidth(itemName);
        }

        //填写售价
        g2d.setPaint(Color.RED);
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.BOLD, 30));
        g2d.drawString(itemName, 50, baseHeight + 190);
        g2d.drawString("￥" + itemPrice.toString(), 50 + textWidth2 + 90, baseHeight + 190);

        //画分割线
        g2d.setPaint(new Color(194, 194, 194));
        g2d.drawLine(10, baseHeight + 250, 630, baseHeight + 250);

        //生成底部文案 和 二维码
        g2d.setFont(new Font("Alibaba PuHuiTi R", Font.PLAIN, 17));
        BufferedImage qrCode = null;
        if (env == 1 || env == 2) {
            g2d.drawString("长按识别小程序", 30, baseHeight + 336);
            qrCode = WechatUtil.getWxacode(accessToken, "pages_category_page1/goodsModule/goodsDetails" + itemUrlSuffix);
        } else if (env == 3) {
            g2d.drawString("扫描二维码购买商品", 30, baseHeight + 336);
            qrCode = QRCodeUtil.createImage(goodsUrl + itemUrlSuffix, null, true);
        } else if (env == 4) {
            g2d.drawString("长按识别小程序", 30, baseHeight + 336);
            String qrCodeUrl = AlipayUtil.generateQrCode("pages_category_page1/goodsModule/goodsDetails", itemUrlSuffix.substring(1));
            qrCode = ImageIO.read(new URL(qrCodeUrl));
            qrCode = qrCode.getSubimage(102, 134, 1336, 1336);
        }
        if (qrCode != null) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.drawImage(qrCode.getScaledInstance(160, 160, Image.SCALE_SMOOTH), 448, baseHeight + 266, null);
        }

        g2d.dispose();

        String fileName = UUID.randomUUID().toString() + ".png";
        InputStream inputStream = bufferedImageToInputStream(image);
        ByteArrayOutputStream baos = cloneInputStream(inputStream);
        return fileStrategy.uploadStream(fileName, IOUtils.toByteArray(new ByteArrayInputStream(baos.toByteArray())), new ByteArrayInputStream(baos.toByteArray()), inputStream.available());
    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage readImgInner(String imageUrl) throws Exception {
        if (StringUtil.isNotBlank(imageUrl)) {
            if (imageUrl.endsWith(".webp")) {
                // Obtain a WebP ImageReader instance
                ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

                // Configure decoding parameters
                WebPReadParam readParam = new WebPReadParam();
                readParam.setBypassFiltering(true);

                // Configure the input on the ImageReader
                reader.setInput(ImageIO.createImageInputStream(new URL(imageUrl).openStream()));

                // Decode the image
                return reader.read(0, readParam);
            } else {
                return ImageIO.read(new URL(imageUrl));
            }
        }
        return null;
    }

    /**
     * 将BufferedImage转换为InputStream
     * @param image
     * @return
     */
    public InputStream bufferedImageToInputStream(BufferedImage image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
