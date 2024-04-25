/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.order.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shop.cereshop.app.alioss.service.UploadService;
import com.shop.cereshop.app.dao.order.CereShopOrderDAO;
import com.shop.cereshop.app.dao.platformtool.CereBuyerPoliteRecordDAO;
import com.shop.cereshop.app.domain.activity.ActivityData;
import com.shop.cereshop.app.page.cart.CartSku;
import com.shop.cereshop.app.page.order.*;
import com.shop.cereshop.app.page.product.ProductCoupon;
import com.shop.cereshop.app.page.product.ProductStockInfo;
import com.shop.cereshop.app.page.settlement.Distribution;
import com.shop.cereshop.app.page.settlement.Settlement;
import com.shop.cereshop.app.page.settlement.SettlementShop;
import com.shop.cereshop.app.param.comment.CommentSaveParam;
import com.shop.cereshop.app.param.compose.CereShopComposeDTO;
import com.shop.cereshop.app.param.coupon.CouponParam;
import com.shop.cereshop.app.param.discount.ShopPlatformDiscount;
import com.shop.cereshop.app.param.order.*;
import com.shop.cereshop.app.param.price.PriceCombineParam;
import com.shop.cereshop.app.param.seckill.ShopBusinessDiscount;
import com.shop.cereshop.app.param.seckill.ShopBusinessSeckill;
import com.shop.cereshop.app.param.seckill.ShopPlatformSeckill;
import com.shop.cereshop.app.param.settlement.ProductSku;
import com.shop.cereshop.app.param.settlement.SettlementParam;
import com.shop.cereshop.app.param.settlement.ShopProductParam;
import com.shop.cereshop.app.pay.PayFactory;
import com.shop.cereshop.app.pay.PayService;
import com.shop.cereshop.app.pay.weixin.service.WxPayService;
import com.shop.cereshop.app.redis.service.api.StringRedisService;
import com.shop.cereshop.app.service.activity.CereBuyerCouponService;
import com.shop.cereshop.app.service.after.CereAfterDileverService;
import com.shop.cereshop.app.service.after.CereOrderAfterService;
import com.shop.cereshop.app.service.business.CereBusinessBuyerUserService;
import com.shop.cereshop.app.service.buyer.CereBuyerReceiveService;
import com.shop.cereshop.app.service.buyer.CereBuyerShopCouponService;
import com.shop.cereshop.app.service.buyer.CereBuyerUserService;
import com.shop.cereshop.app.service.cart.CereShopCartService;
import com.shop.cereshop.app.service.compose.CereShopComposeService;
import com.shop.cereshop.app.service.dict.CerePlatformDictService;
import com.shop.cereshop.app.service.discount.CereShopDiscountDetailService;
import com.shop.cereshop.app.service.discount.CereShopDiscountService;
import com.shop.cereshop.app.service.distributor.CereDistributionOrderService;
import com.shop.cereshop.app.service.distributor.CereDistributorBuyerService;
import com.shop.cereshop.app.service.distributor.CereShopDistributorService;
import com.shop.cereshop.app.service.express.CerePlatformExpressService;
import com.shop.cereshop.app.service.express.KuaiDi100Service;
import com.shop.cereshop.app.service.express.KuaiDiNiaoService;
import com.shop.cereshop.app.service.groupwork.CereCollageOrderDetailService;
import com.shop.cereshop.app.service.groupwork.CereCollageOrderService;
import com.shop.cereshop.app.service.groupwork.CereShopGroupWorkService;
import com.shop.cereshop.app.service.log.CerePlatformLogService;
import com.shop.cereshop.app.service.logistics.CereOrderLogisticsService;
import com.shop.cereshop.app.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.app.service.notice.CereNoticeService;
import com.shop.cereshop.app.service.order.*;
import com.shop.cereshop.app.service.pay.CerePayLogService;
import com.shop.cereshop.app.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.app.service.platformtool.CerePlatformPoliteActivityService;
import com.shop.cereshop.app.service.platformtool.CerePlatformPoliteService;
import com.shop.cereshop.app.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.app.service.price.CerePriceProductService;
import com.shop.cereshop.app.service.price.CereShopPriceService;
import com.shop.cereshop.app.service.product.CereCommentWordService;
import com.shop.cereshop.app.service.product.CereProductMemberService;
import com.shop.cereshop.app.service.product.CereProductSkuService;
import com.shop.cereshop.app.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.app.service.scene.CereShopSceneMemberService;
import com.shop.cereshop.app.service.scene.CereShopSceneService;
import com.shop.cereshop.app.service.seckill.CereShopSeckillDetailService;
import com.shop.cereshop.app.service.seckill.CereShopSeckillService;
import com.shop.cereshop.app.service.sensitive.CerePlatformSensitiveService;
import com.shop.cereshop.app.service.shop.CereShopCommentService;
import com.shop.cereshop.app.service.shop.CereShopConversionService;
import com.shop.cereshop.app.service.stock.CereStockService;
import com.shop.cereshop.commons.config.AlipayConfig;
import com.shop.cereshop.commons.constant.*;
import com.shop.cereshop.commons.domain.activity.CereBuyerCoupon;
import com.shop.cereshop.commons.domain.after.CereAfterDilever;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.business.CereBusinessBuyerUser;
import com.shop.cereshop.commons.domain.buyer.CereBuyerReceive;
import com.shop.cereshop.commons.domain.buyer.CereBuyerShopCoupon;
import com.shop.cereshop.commons.domain.buyer.CereBuyerUser;
import com.shop.cereshop.commons.domain.collage.CereCollageOrder;
import com.shop.cereshop.commons.domain.collage.CereCollageOrderDetail;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.distributor.CereDistributionOrder;
import com.shop.cereshop.commons.domain.express.ShippingTrace;
import com.shop.cereshop.commons.domain.logistics.CereLogisticsCharge;
import com.shop.cereshop.commons.domain.logistics.CereOrderLogistics;
import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;
import com.shop.cereshop.commons.domain.notice.CereNotice;
import com.shop.cereshop.commons.domain.order.CereOrderParent;
import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.order.CereOrderReconciliation;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.platformtool.*;
import com.shop.cereshop.commons.domain.product.CereCommentWord;
import com.shop.cereshop.commons.domain.product.CereProductMember;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.scene.CereShopScene;
import com.shop.cereshop.commons.domain.scene.CereShopSceneMember;
import com.shop.cereshop.commons.domain.sensitive.CerePlatformSensitive;
import com.shop.cereshop.commons.domain.shop.CereShopComment;
import com.shop.cereshop.commons.domain.shop.CereShopConversion;
import com.shop.cereshop.commons.domain.tool.*;
import com.shop.cereshop.commons.domain.word.CerePlatformWord;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage;

@Slf4j
@Service
public class CereShopOrderServiceImpl implements CereShopOrderService {

    @Autowired
    private CereShopOrderDAO cereShopOrderDAO;

    @Autowired
    private CereBuyerPoliteRecordDAO cereBuyerPoliteRecordDAO;

    @Autowired
    private CereOrderLogisticsService cereOrderLogisticsService;

    @Autowired
    private CereProductSkuService cereProductSkuService;

    @Autowired
    private CereOrderParentService cereOrderParentService;

    @Autowired
    private CereDistributorBuyerService cereDistributorBuyerService;

    @Autowired
    private CereShopDistributorService cereShopDistributorService;

    @Autowired
    private CereOrderProductService cereOrderProductService;

    @Autowired
    private CereOrderProductAttributeService cereOrderProductAttributeService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereDistributionOrderService cereDistributionOrderService;

    @Autowired
    private CereOrderReconciliationService cereOrderReconciliationService;

    @Autowired
    private CerePayLogService cerePayLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CereShopCommentService cereShopCommentService;

    @Autowired
    private CerePlatformSensitiveService cerePlatformSensitiveService;

    @Autowired
    private CereShopConversionService cereShopConversionService;

    @Autowired
    private CereCommentWordService cereCommentWordService;

    @Autowired
    private CereBuyerCouponService cereBuyerCouponService;

    @Autowired
    private CereShopCartService cereShopCartService;

    @Autowired
    private CereAfterDileverService cereAfterDileverService;

    @Autowired
    private CereBuyerReceiveService cereBuyerReceiveService;

    @Autowired
    private CerePlatformExpressService cerePlatformExpressService;

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    @Autowired
    private KuaiDi100Service kuaiDi100Service;

    @Autowired
    private KuaiDiNiaoService kuaiDiNiaoService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CereOrderAfterService cereOrderAfterService;

    @Autowired
    private CereBuyerShopCouponService cereBuyerShopCouponService;

    @Autowired
    private CereShopGroupWorkService cereShopGroupWorkService;

    @Autowired
    private CereCollageOrderService cereCollageOrderService;

    @Autowired
    private CereCollageOrderDetailService cereCollageOrderDetailService;

    @Autowired
    private CereShopSeckillService cereShopSeckillService;

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    @Autowired
    private CereShopSeckillDetailService cereShopSeckillDetailService;

    @Autowired
    private CereShopDiscountDetailService cereShopDiscountDetailService;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private CereNoticeService cereNoticeService;

    @Autowired
    private CereBusinessBuyerUserService cereBusinessBuyerUserService;

    @Autowired
    private CereShopPriceService cereShopPriceService;

    @Autowired
    private CerePriceProductService cerePriceProductService;

    @Autowired
    private CereShopComposeService cereShopComposeService;

    @Autowired
    private CerePlatformPoliteService cerePlatformPoliteService;

    @Autowired
    private CereBuyerUserService cereBuyerUserService;

    @Autowired
    private CerePlatformMemberLevelService cerePlatformMemberLevelService;

    @Autowired
    private CerePlatformPoliteActivityService cerePlatformPoliteActivityService;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Autowired
    private CereShopSceneService cereShopSceneService;

    @Autowired
    private CereShopSceneMemberService cereShopSceneMemberService;

    @Autowired
    private CereProductMemberService cereProductMemberService;

    @Autowired
    private CereStockService cereStockService;

    @Override
    public Settlement getSettlement(SettlementParam param, CereBuyerUser user) throws CoBusinessException {
        Settlement settlement=new Settlement();
        if(EmptyUtils.isEmpty(param.getReceiveId())){
            //查询默认地址
            settlement.setReceive(cereBuyerReceiveService.findlatelyReceiveByUserId(user.getBuyerUserId()));
        }else {
            settlement.setReceive(cereBuyerReceiveService.findById(param.getReceiveId()));
        }
        List<ProductCoupon> coupons=new ArrayList<>();
        /*if(ParamEnum.BUY_NOW.getCode().equals(param.getType())){
            //立即购买结算查询
            buyNow(settlement,param,user,coupons);
        } else {*/
            //购物车结算查询
            shopCart(settlement,param,user,coupons);
        /*}*/
        List<ProductCoupon> collect=null;
        if(!EmptyUtils.isEmpty(coupons)){
            //去除重复活动和对应金额优惠券
            collect= coupons.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(
                            () -> new TreeSet<>(Comparator.comparing(data -> data.getDistinct()))), ArrayList::new)
            );
            settlement.setCoupons(collect);
        }
        settlement.setHuabeiChargeType(AlipayConfig.HUABEI_CHARGE_TYPE);
        settlement.setHuabeiFeerateList(AlipayConfig.HUABEI_FEERATE_LIST);
        return settlement;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public PayUrl submit(OrderParam param, CereBuyerUser user,String ip) throws CoBusinessException,Exception {
        PayUrl payUrl=new PayUrl();
        String time = TimeUtils.yyMMddHHmmss();
        if (param.getPaymentMode() == null) {
            param.setPaymentMode(1);
        }
        if(EmptyUtils.isEmpty(param.getType())
            || IntegerEnum.SECKILL_SUBMIT.getCode().equals(param.getType())
            || IntegerEnum.DISCOUNT_SUBMIT.getCode().equals(param.getType())){
            //正常下单
            normalOrder(param,user,ip,payUrl,time);
        }else if(IntegerEnum.GROUP_WORK_LAUNCH.getCode().equals(param.getType())){
            //发起拼团
            launchOrder(param,user,ip,payUrl,time);
        }else if(IntegerEnum.GROUP_WORK_JOIN.getCode().equals(param.getType())){
            //参与拼团
            joinOrder(param,user,ip,payUrl,time);
        } /*else if(IntegerEnum.SECKILL_SUBMIT.getCode().equals(param.getType())){
            //秒杀活动下单
            seckillOrder(param,user,ip,payUrl,time);
        } else if(IntegerEnum.DISCOUNT_SUBMIT.getCode().equals(param.getType())){
            //限时折扣活动下单
            discountOrder(param,user,ip,payUrl,time);
        }*/
        return payUrl;
    }
/*
    private void discountOrder(OrderParam param, CereBuyerUser user, String ip, PayUrl payUrl, String time) throws CoBusinessException,Exception{
        CereBuyerCoupon cereBuyerCoupon=null;
        CereBuyerShopCoupon cereBuyerShopCoupon=null;
        //查询秒杀活动数据
        CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(param.getShopDiscountId());
        if(cereShopDiscount!=null){
            //定义map封装商品购买数量
            Map<Long, ProductSku> numberMap=new HashMap<>();
            param.getShops().forEach(shop -> {
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    shop.getSkus().forEach(sku -> {
                        numberMap.put(sku.getSkuId(),sku);
                    });
                }
            });
            Map<Long, CartSku> map=new HashMap<>();
            if(!EmptyUtils.isEmpty(param.getShops())){
                AtomicBoolean flag= new AtomicBoolean(false);
                //定义运费
                BigDecimal logisticPrice=new BigDecimal(0);
                //定义批量更新限量数据
                List<CereShopDiscountDetail> discountDetails=new ArrayList<>();
                for (OrderProductParam shop : param.getShops()) {
                    if(!EmptyUtils.isEmpty(shop.getDistribution())){
                        //计算总得运费
                        logisticPrice=logisticPrice.add(shop.getDistribution().getDistributionPrice());
                    }
                    if(!EmptyUtils.isEmpty(shop.getSkus())){
                        //查询当前店铺所有购买商品的库存数据
                        List<CartSku> productSkus=cereProductSkuService.findDiscountStockNumberBySkus(shop.getSkus(),cereShopDiscount.getShopDiscountId());
                        if(!EmptyUtils.isEmpty(productSkus)){
                            for (CartSku sku : productSkus) {
                                //校验限量和限购
                                checkDiscountActivity(cereShopDiscount,numberMap.get(sku.getSkuId()).getNumber(),sku,discountDetails);
                                //设置购买数量
                                sku.setNumber(numberMap.get(sku.getSkuId()).getNumber());
                                //设置选中状态
                                sku.setSelected(numberMap.get(sku.getSkuId()).getSelected());
                                //设置店铺id
                                sku.setShopId(shop.getShopId());
                                map.put(sku.getSkuId(),sku);
                            }
                        }
                        shop.getSkus().forEach(sku -> {
                            if(IntegerEnum.NO.getCode().equals(map.get(sku.getSkuId()).getIfOversold())){
                                //如果不允许超卖,校验redis中商品库存
                                int stockNumber=0;
                                *//*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                                }else {
                                    stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                }*//*
                                stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                if(sku.getNumber()>stockNumber){
                                    flag.set(true);
                                }
                            }
                        });
                    }
                }
                if(flag.get()){
                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                }
                //计算订单总金额
                BigDecimal orderPrice=setOrderPrice(map,numberMap);
                //查询平台优惠券数据
                cereBuyerCoupon=cereBuyerCouponService.findByCouponId(param.getCouponId(),user.getBuyerUserId());
                //定义店铺优惠券map
                Map<Long,CereBuyerShopCoupon> discountMap=new HashMap<>();
                //设置优惠金额
                param.setDiscountPrice(setDiscountPrice(cereBuyerCoupon,param,map,numberMap,discountMap));
                //计算订单实付金额=订单总金额-优惠+运费
                if(!EmptyUtils.isEmpty(logisticPrice)){
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()).add(logisticPrice));
                }else {
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()));
                }
                //新增父订单数据
                CereOrderParent parent=new CereOrderParent();
                parent.setCreateTime(time);
                parent.setLogisticsPrice(logisticPrice);
                //订单总价
                parent.setOrderPrice(orderPrice);
                parent.setPrice(param.getPrice());
                parent.setParentFormid(RandomStringUtil.getRandomCode(15,0));
                cereOrderParentService.insert(parent);
                payUrl.setMoney(parent.getPrice());
                payUrl.setOrderId(parent.getParentId());
                if(EmptyUtils.isEmptyBigdecimal(payUrl.getMoney())){
                    //如果金额为0,提示
                    throw new CoBusinessException(CoReturnFormat.PAY_MONEY_NOT_ZERO);
                }
                //生成支付二维码
                generatePayUrl(param, user.getWechatOpenId(), parent.getParentFormid(), payUrl.getMoney(), ip, payUrl);

                //遍历店铺数据,新增订单
                addDiscountOrder(parent.getParentId(),param,user,time,map,cereBuyerCoupon,discountMap,discountDetails);
            }
        }
    }*/
/*
    private void seckillOrder(OrderParam param, CereBuyerUser user, String ip, PayUrl payUrl, String time) throws CoBusinessException,Exception{
        CereBuyerCoupon cereBuyerCoupon=null;
        CereBuyerShopCoupon cereBuyerShopCoupon=null;
        //查询秒杀活动数据
        CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(param.getShopSeckillId());
        if(cereShopSeckill!=null){
            //定义map封装商品购买数量
            Map<Long, ProductSku> numberMap=new HashMap<>();
            param.getShops().forEach(shop -> {
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    shop.getSkus().forEach(sku -> {
                        numberMap.put(sku.getSkuId(),sku);
                    });
                }
            });
            Map<Long, CartSku> map=new HashMap<>();
            if(!EmptyUtils.isEmpty(param.getShops())){
                AtomicBoolean flag= new AtomicBoolean(false);
                //定义运费
                BigDecimal logisticPrice=new BigDecimal(0);
                //定义批量更新限量库存数据
                List<CereShopSeckillDetail> seckillDetails=new ArrayList<>();
                for (OrderProductParam shop : param.getShops()) {
                    if(!EmptyUtils.isEmpty(shop.getDistribution())){
                        //计算总得运费
                        logisticPrice=logisticPrice.add(shop.getDistribution().getDistributionPrice());
                    }
                    if(!EmptyUtils.isEmpty(shop.getSkus())){
                        //查询当前店铺所有购买商品的库存数据
                        List<CartSku> productSkus=cereProductSkuService.findSeckillStockNumberBySkus(shop.getSkus(),cereShopSeckill.getShopSeckillId());
                        if(!EmptyUtils.isEmpty(productSkus)){
                            for (CartSku sku : productSkus) {
                                //校验是否限量和限购
                                checkSeckillActivity(cereShopSeckill,numberMap.get(sku.getSkuId()).getNumber(),sku,seckillDetails);
                                //设置购买数量
                                sku.setNumber(numberMap.get(sku.getSkuId()).getNumber());
                                //设置选中状态
                                sku.setSelected(numberMap.get(sku.getSkuId()).getSelected());
                                //设置店铺id
                                sku.setShopId(shop.getShopId());
                                map.put(sku.getSkuId(),sku);
                            }
                        }
                        shop.getSkus().forEach(sku -> {
                            if(IntegerEnum.NO.getCode().equals(map.get(sku.getSkuId()).getIfOversold())){
                                //如果不允许超卖,校验redis中商品库存
                                int stockNumber=0;
                                *//*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                                }else {
                                    stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                }*//*
                                stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                if(sku.getNumber()>stockNumber){
                                    flag.set(true);
                                }
                            }
                        });
                    }
                }
                if(flag.get()){
                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                }
                //计算订单总金额
                BigDecimal orderPrice=setOrderPrice(map,numberMap);
                //查询平台优惠券数据
                cereBuyerCoupon=cereBuyerCouponService.findByCouponId(param.getCouponId(),user.getBuyerUserId());
                //定义店铺优惠券map
                Map<Long,CereBuyerShopCoupon> discountMap=new HashMap<>();
                //设置优惠金额
                param.setDiscountPrice(setDiscountPrice(cereBuyerCoupon,param,map,numberMap,discountMap));
                //计算订单实付金额=订单总金额-优惠+运费
                if(!EmptyUtils.isEmpty(logisticPrice)){
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()).add(logisticPrice));
                }else {
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()));
                }
                //新增父订单数据
                CereOrderParent parent=new CereOrderParent();
                parent.setCreateTime(time);
                parent.setLogisticsPrice(logisticPrice);
                //订单总价
                parent.setOrderPrice(orderPrice);
                parent.setPrice(param.getPrice());
                parent.setParentFormid(RandomStringUtil.getRandomCode(15,0));
                cereOrderParentService.insert(parent);
                payUrl.setMoney(parent.getPrice());
                payUrl.setOrderId(parent.getParentId());
                if(EmptyUtils.isEmptyBigdecimal(payUrl.getMoney())){
                    //如果金额为0,提示
                    throw new CoBusinessException(CoReturnFormat.PAY_MONEY_NOT_ZERO);
                }
                //生成支付二维码
                generatePayUrl(param, user.getWechatOpenId(), parent.getParentFormid(), payUrl.getMoney(), ip, payUrl);

                //遍历店铺数据,新增订单
                addSeckillOrder(parent.getParentId(),param,user,time,map,cereBuyerCoupon,discountMap,seckillDetails);
            }
        }
    }*/

    private void joinOrder(OrderParam param, CereBuyerUser user, String ip, PayUrl payUrl, String time) throws CoBusinessException,Exception{
        CereBuyerCoupon cereBuyerCoupon=null;
        CereBuyerShopCoupon cereBuyerShopCoupon=null;
        //发起拼团提交订单业务处理
        //查询拼团活动数据
        CereShopGroupWork cereShopGroupWork=cereShopGroupWorkService.findById(param.getShopGroupWorkId());
        if(cereShopGroupWork!=null){
            //定义map封装商品购买数量
            Map<Long, ProductSku> numberMap=new HashMap<>();
            param.getShops().forEach(shop -> {
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    shop.getSkus().forEach(sku -> {
                        numberMap.put(sku.getSkuId(),sku);
                    });
                }
            });
            Map<Long, CartSku> map=new HashMap<>();
            if(!EmptyUtils.isEmpty(param.getShops())){
                AtomicBoolean flag= new AtomicBoolean(false);
                BigDecimal logisticPrice=new BigDecimal(0);
                for (OrderProductParam shop : param.getShops()) {
                    if(!EmptyUtils.isEmpty(shop.getDistribution())){
                        logisticPrice=logisticPrice.add(shop.getDistribution().getDistributionPrice());
                    }
                    if(!EmptyUtils.isEmpty(shop.getSkus())){
                        //查询当前店铺所有购买商品的库存数据
                        List<CartSku> productSkus=cereProductSkuService.findGroupWorkStockNumberBySkus(shop.getSkus(),cereShopGroupWork.getShopGroupWorkId());
                        if(!EmptyUtils.isEmpty(productSkus)){
                            productSkus.forEach(sku -> {
                                //设置购买数量
                                sku.setNumber(numberMap.get(sku.getSkuId()).getNumber());
                                //设置选中状态
                                sku.setSelected(numberMap.get(sku.getSkuId()).getSelected());
                                //设置店铺id
                                sku.setShopId(shop.getShopId());
                                map.put(sku.getSkuId(),sku);
                            });
                        }
                        shop.getSkus().forEach(sku -> {
                            if(IntegerEnum.NO.getCode().equals(map.get(sku.getSkuId()).getIfOversold())){
                                //如果不允许超卖,校验redis中商品库存
                                int stockNumber=0;
                                /*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                                }else {
                                    stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                }*/
                                stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                if(sku.getNumber()>stockNumber){
                                    flag.set(true);
                                }
                            }
                        });
                    }
                }
                if(flag.get()){
                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                }
                //计算订单总金额
                BigDecimal orderPrice=setOrderPrice(map,numberMap);
                //查询平台优惠券数据
                cereBuyerCoupon=cereBuyerCouponService.findByCouponId(param.getCouponId(),user.getBuyerUserId());
                //定义店铺优惠券map
                Map<Long,CereBuyerShopCoupon> discountMap=new HashMap<>();
                //设置优惠金额
                param.setDiscountPrice(setDiscountPrice(cereBuyerCoupon,param,map,numberMap,discountMap));
                //计算订单实付金额=订单总金额-优惠+运费
                if(!EmptyUtils.isEmpty(logisticPrice)){
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()).add(logisticPrice));
                }else {
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()));
                }
                //新增父订单数据
                CereOrderParent parent=new CereOrderParent();
                parent.setCreateTime(time);
                parent.setLogisticsPrice(logisticPrice);
                parent.setOrderPrice(orderPrice);
                parent.setPrice(param.getPrice());
                parent.setParentFormid(RandomStringUtil.getRandomCode(15,0));
                cereOrderParentService.insert(parent);
                payUrl.setMoney(parent.getPrice());
                payUrl.setOrderId(parent.getParentId());
                if(EmptyUtils.isEmptyBigdecimal(payUrl.getMoney())){
                    //如果金额为0,提示
                    throw new CoBusinessException(CoReturnFormat.PAY_MONEY_NOT_ZERO);
                }
                //生成支付二维码
                generatePayUrl(param, user.getWechatOpenId(), parent.getParentFormid(), parent.getOrderPrice(), ip, payUrl);

                //遍历店铺数据,新增订单
                addlaunchOrder(parent.getParentId(),param,user,time,map,cereBuyerCoupon,discountMap,cereShopGroupWork,payUrl);
            }
        }
    }

    private void launchOrder(OrderParam param, CereBuyerUser user, String ip, PayUrl payUrl, String time) throws CoBusinessException,Exception{
        CereBuyerCoupon cereBuyerCoupon=null;
        CereBuyerShopCoupon cereBuyerShopCoupon=null;
        //发起拼团提交订单业务处理
        //查询拼团活动数据
        CereShopGroupWork cereShopGroupWork=cereShopGroupWorkService.findById(param.getShopGroupWorkId());
        if(cereShopGroupWork!=null){
            //定义map封装商品购买数量
            Map<Long, ProductSku> numberMap=new HashMap<>();
            param.getShops().forEach(shop -> {
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    shop.getSkus().forEach(sku -> {
                        numberMap.put(sku.getSkuId(),sku);
                    });
                }
            });
            Map<Long, CartSku> map=new HashMap<>();
            if(!EmptyUtils.isEmpty(param.getShops())){
                AtomicBoolean flag= new AtomicBoolean(false);
                //定义运费
                BigDecimal logisticPrice=new BigDecimal(0);
                for (OrderProductParam shop : param.getShops()) {
                    if(!EmptyUtils.isEmpty(shop.getDistribution())){
                        //计算总得运费
                        logisticPrice=logisticPrice.add(shop.getDistribution().getDistributionPrice());
                    }
                    if(!EmptyUtils.isEmpty(shop.getSkus())){
                        //查询当前店铺所有购买商品的库存数据
                        List<CartSku> productSkus=cereProductSkuService.findGroupWorkStockNumberBySkus(shop.getSkus(),cereShopGroupWork.getShopGroupWorkId());
                        if(!EmptyUtils.isEmpty(productSkus)){
                            productSkus.forEach(sku -> {
                                //设置购买数量
                                sku.setNumber(numberMap.get(sku.getSkuId()).getNumber());
                                //设置选中状态
                                sku.setSelected(numberMap.get(sku.getSkuId()).getSelected());
                                //设置店铺id
                                sku.setShopId(shop.getShopId());
                                map.put(sku.getSkuId(),sku);
                            });
                        }
                        shop.getSkus().forEach(sku -> {
                            if(IntegerEnum.NO.getCode().equals(map.get(sku.getSkuId()).getIfOversold())){
                                //如果不允许超卖,校验redis中商品库存
                                int stockNumber=0;
                                /*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                                }else {
                                    stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                }*/
                                stockNumber=map.get(sku.getSkuId()).getStockNumber();
                                if(sku.getNumber()>stockNumber){
                                    flag.set(true);
                                }
                            }
                        });
                    }
                }
                if(flag.get()){
                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                }
                //计算订单总金额
                BigDecimal orderPrice=setOrderPrice(map,numberMap);
                //查询平台优惠券数据
                cereBuyerCoupon=cereBuyerCouponService.findByCouponId(param.getCouponId(),user.getBuyerUserId());
                //定义店铺优惠券map
                Map<Long,CereBuyerShopCoupon> discountMap=new HashMap<>();
                //设置优惠金额
                param.setDiscountPrice(setDiscountPrice(cereBuyerCoupon,param,map,numberMap,discountMap));
                //计算订单实付金额=订单总金额-优惠+运费
                if(!EmptyUtils.isEmpty(logisticPrice)){
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()).add(logisticPrice));
                }else {
                    param.setPrice(orderPrice.subtract(param.getDiscountPrice()));
                }
                //新增父订单数据
                CereOrderParent parent=new CereOrderParent();
                parent.setCreateTime(time);
                parent.setLogisticsPrice(logisticPrice);
                //订单总价
                parent.setOrderPrice(orderPrice);
                parent.setPrice(param.getPrice());
                parent.setParentFormid(RandomStringUtil.getRandomCode(15,0));
                cereOrderParentService.insert(parent);
                payUrl.setMoney(parent.getPrice());
                payUrl.setOrderId(parent.getParentId());
                if(EmptyUtils.isEmptyBigdecimal(payUrl.getMoney())){
                    //如果金额为0,提示
                    throw new CoBusinessException(CoReturnFormat.PAY_MONEY_NOT_ZERO);
                }
                //生成支付二维码
                generatePayUrl(param, user.getWechatOpenId(), parent.getParentFormid(), payUrl.getMoney(), ip, payUrl);

                //遍历店铺数据,新增订单
                addlaunchOrder(parent.getParentId(),param,user,time,map,cereBuyerCoupon,discountMap,cereShopGroupWork,payUrl);
            }
        }
    }

    private void addlaunchOrder(Long parentId, OrderParam param, CereBuyerUser user, String time, Map<Long, CartSku> map,
                                CereBuyerCoupon buyerCoupon, Map<Long,CereBuyerShopCoupon> discountMap,
                                CereShopGroupWork cereShopGroupWork,PayUrl payUrl) throws CoBusinessException,Exception{
        //遍历店铺
        List<CartSku> carts=new ArrayList<>();
        List<CartSku> skus=null;
        if(!EmptyUtils.isEmpty(map)){
            //拿到所有商品数据
            skus=map.keySet().stream().map(key -> map.get(key)).collect(Collectors.toList());
        }
        //查询所有规格属性数据
        List<OrderProductAttribute> attributes=cereOrderProductAttributeService.findBySkus(skus);
        //查询收货地址数据
        CereBuyerReceive receive=cereBuyerReceiveService.findById(param.getReceiveId());
        if(receive!=null){
            for (OrderProductParam shop : param.getShops()) {
                //封装子订单数据
                CereShopOrder order=new CereShopOrder();
                order.setShopId(shop.getShopId());
                order.setParentId(parentId);
                order.setBuyerUserId(user.getBuyerUserId());
                order.setCreateTime(time);
                order.setUpdateTime(time);
                order.setCouponId(param.getCouponId());
                order.setReceiveName(receive.getReceiveName());
                order.setReceivePhone(receive.getReceivePhone());
                order.setReceiveAdress(receive.getReceiveAdress());
                order.setAddress(receive.getAddress());
                order.setCustomerName(user.getWechatName());
                order.setCustomerPhone(user.getPhone());
                order.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                order.setLogisticsId(shop.getDistribution().getLogisticsId());
                order.setState(IntegerEnum.ORDER_STAY_PAY.getCode());
                order.setOrderFormid(RandomStringUtil.getRandomCode(15,0));
                //定义订单优惠券金额（平台优惠金额/店铺总数+店铺优惠金额）
                BigDecimal discountPrice=new BigDecimal(0);
                if(buyerCoupon!=null){
                    discountPrice=buyerCoupon.getReduceMoney().divide(
                            new BigDecimal(param.getShops().size()),2,BigDecimal.ROUND_HALF_UP);
                    //更新平台优惠券状态为已使用
                    buyerCoupon.setUpdateTime(time);
                    buyerCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                    cereBuyerCouponService.updateState(buyerCoupon);
                    //删除redis延时任务
                    stringRedisService.delete(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                    //删除redis记录数据
                    cereRedisKeyServcice.deleteByKey(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                }
                //计算订单优惠金额
                if(!EmptyUtils.isEmpty(shop.getId())&&!EmptyUtils.isEmpty(discountMap)){
                    order.setId(shop.getId());
                    if(!EmptyUtils.isEmpty(discountMap.get(shop.getId()))){
                        CereBuyerShopCoupon cereBuyerShopCoupon = discountMap.get(shop.getId());
                        //更新店铺优惠券状态为已使用
                        cereBuyerShopCoupon.setUpdateTime(time);
                        cereBuyerShopCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                        cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
                        //删除redis延时任务
                        stringRedisService.delete(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        order.setShopOperateId(cereBuyerShopCoupon.getShopOperateId());
                        if(!EmptyUtils.isEmptyBigdecimal(discountPrice)){
                            discountPrice=discountPrice.add(cereBuyerShopCoupon.getReduceMoney());
                        }else {
                            discountPrice=cereBuyerShopCoupon.getReduceMoney();
                        }
                    }
                }
                order.setDiscountPrice(discountPrice);
                if(!EmptyUtils.isEmpty(skus)){
                    //定义店铺商品总金额字段
                    final BigDecimal[] total = {new BigDecimal(0)};
                    //设置规格数量
                    skus.forEach(sku -> {
                        if(sku.getShopId().equals(shop.getShopId())){
                            total[0] = total[0].add(sku.getPrice().multiply(
                                    new BigDecimal(sku.getNumber())).setScale(2,BigDecimal.ROUND_HALF_UP));
                        }
                    });
                    //订单总金额=店铺商品总金额+运费
                    order.setOrderPrice(total[0].add(order.getLogisticsPrice()));
                    //订单支付金额=订单总金额-优惠
                    order.setPrice(order.getOrderPrice().subtract(order.getDiscountPrice()));
                    order.setPaymentState(IntegerEnum.ORDER_PAY_STAY.getCode());
                    order.setPaymentMode(param.getPaymentMode());
                    order.setRemark(shop.getRemark());
                    order.setBuyerUserId(user.getBuyerUserId());
                    order.setShopGroupWorkId(param.getShopGroupWorkId());
                    //插入订单数据
                    cereShopOrderDAO.insert(order);
                    //新增订单商品数据
                    addOrderProduct(skus,order.getOrderId(),shop.getShopId(),map,carts,attributes);
                    Long collageId=param.getCollageId();
                    if(EmptyUtils.isEmpty(param.getCollageId())){
                        //生成拼单数据
                        CereCollageOrder cereCollageOrder=new CereCollageOrder();
                        cereCollageOrder.setBuyerUserId(user.getBuyerUserId());
                        cereCollageOrder.setCreateTime(time);
                        cereCollageOrder.setShopGroupWorkId(param.getShopGroupWorkId());
                        cereCollageOrder.setSurplusNumber(cereShopGroupWork.getPerson());
                        cereCollageOrder.setState(IntegerEnum.COLLAGE_STATE_STAY.getCode());
                        cereCollageOrderService.insert(cereCollageOrder);
                        collageId=cereCollageOrder.getCollageId();
                        param.setCollageId(cereCollageOrder.getCollageId());
                        //设置该拼单截止时间
                        String end=TimeUtils.getMoreHourAfter(time,cereShopGroupWork.getEffectiveTime());
                        //新增redis延时任务处理拼团失败
                        stringRedisService.set(StringEnum.COLLAGE_ERROR.getCode()+"-"+cereCollageOrder.getCollageId(),1,
                                TimeUtils.getCountDownByTime(time,end));
                        //新增延时记录
                        cereRedisKeyServcice.add(StringEnum.COLLAGE_ERROR.getCode()+"-"+cereCollageOrder.getCollageId(),end);
                    }
                    //生成拼单明细数据
                    CereCollageOrderDetail detail=new CereCollageOrderDetail();
                    detail.setCollageId(collageId);
                    detail.setOrderId(order.getOrderId());
                    detail.setState(IntegerEnum.YES.getCode());
                    cereCollageOrderDetailService.insert(detail);
                    payUrl.setCollageId(collageId);
                }
            }
            //批量更新库存数量
            //cereProductSkuService.updateBatch(skus);
            /*for (CartSku sku:skus) {
                cereStockService.reduceStock(sku.getActivityType(), sku.getProductId(), sku.getSkuId(), sku.getNumber());
            }*/
            //插入规格属性数据
            if(!EmptyUtils.isEmpty(attributes)){
                cereOrderProductAttributeService.insertBatch(attributes);
            }
        }
    }

    /*
    private void addSeckillOrder(Long parentId, OrderParam param, CereBuyerUser user, String time, Map<Long, CartSku> map,
                                 CereBuyerCoupon buyerCoupon,Map<Long,CereBuyerShopCoupon> discountMap,
                                 List<CereShopSeckillDetail> seckillDetails) throws CoBusinessException,Exception{
        //遍历店铺
        List<CartSku> carts=new ArrayList<>();
        List<CartSku> skus=null;
        if(!EmptyUtils.isEmpty(map)){
            //拿到所有商品数据
            skus=map.keySet().stream().map(key -> map.get(key)).collect(Collectors.toList());
        }
        //查询所有规格属性数据
        List<OrderProductAttribute> attributes=cereOrderProductAttributeService.findBySkus(skus);
        //查询收货地址数据
        CereBuyerReceive receive=cereBuyerReceiveService.findById(param.getReceiveId());
        if(receive!=null){
            for (OrderProductParam shop : param.getShops()) {
                //封装子订单数据
                CereShopOrder order=new CereShopOrder();
                order.setShopId(shop.getShopId());
                order.setParentId(parentId);
                order.setBuyerUserId(user.getBuyerUserId());
                order.setCreateTime(time);
                order.setUpdateTime(time);
                order.setShopSeckillId(param.getShopSeckillId());
                order.setCouponId(param.getCouponId());
                order.setReceiveName(receive.getReceiveName());
                order.setReceivePhone(receive.getReceivePhone());
                order.setReceiveAdress(receive.getReceiveAdress());
                order.setAddress(receive.getAddress());
                order.setCustomerName(user.getWechatName());
                order.setCustomerPhone(user.getPhone());
                order.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                order.setLogisticsId(shop.getDistribution().getLogisticsId());
                order.setState(IntegerEnum.ORDER_STAY_PAY.getCode());
                order.setOrderFormid(RandomStringUtil.getRandomCode(15,0));
                //定义订单优惠券金额（平台优惠金额/店铺总数+店铺优惠金额）
                BigDecimal discountPrice=new BigDecimal(0);
                if(buyerCoupon!=null){
                    discountPrice=buyerCoupon.getReduceMoney().divide(
                            new BigDecimal(param.getShops().size()),2,BigDecimal.ROUND_HALF_UP);
                    //更新平台优惠券状态为已使用
                    buyerCoupon.setUpdateTime(time);
                    buyerCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                    cereBuyerCouponService.updateState(buyerCoupon);
                    //删除redis延时任务
                    stringRedisService.delete(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                    //删除redis记录数据
                    cereRedisKeyServcice.deleteByKey(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                }
                //计算订单优惠金额
                if(!EmptyUtils.isEmpty(shop.getId())&&!EmptyUtils.isEmpty(discountMap)){
                    order.setId(shop.getId());
                    if(!EmptyUtils.isEmpty(discountMap.get(shop.getId()))){
                        CereBuyerShopCoupon cereBuyerShopCoupon = discountMap.get(shop.getId());
                        //更新店铺优惠券状态为已使用
                        cereBuyerShopCoupon.setUpdateTime(time);
                        cereBuyerShopCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                        cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
                        //删除redis延时任务
                        stringRedisService.delete(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        order.setShopOperateId(cereBuyerShopCoupon.getShopOperateId());
                        if(!EmptyUtils.isEmptyBigdecimal(discountPrice)){
                            discountPrice=discountPrice.add(cereBuyerShopCoupon.getReduceMoney());
                        }else {
                            discountPrice=cereBuyerShopCoupon.getReduceMoney();
                        }
                    }
                }
                order.setDiscountPrice(discountPrice);
                if(!EmptyUtils.isEmpty(skus)){
                    //定义店铺商品总金额字段
                    final BigDecimal[] total = {new BigDecimal(0)};
                    //设置规格数量
                    skus.forEach(sku -> {
                        if(sku.getShopId().equals(shop.getShopId())){
                            total[0] = total[0].add(sku.getPrice().multiply(
                                    new BigDecimal(sku.getNumber())).setScale(2,BigDecimal.ROUND_HALF_UP));
                        }
                    });
                    //订单总金额=店铺商品总金额+运费
                    order.setOrderPrice(total[0].add(order.getLogisticsPrice()));
                    //订单支付金额=订单总金额-优惠
                    order.setPrice(order.getOrderPrice().subtract(order.getDiscountPrice()));
                    order.setPaymentState(IntegerEnum.ORDER_PAY_STAY.getCode());
                    order.setPaymentMode(param.getPaymentMode());
                    order.setRemark(shop.getRemark());
                    order.setBuyerUserId(user.getBuyerUserId());
                    order.setShopGroupWorkId(param.getShopGroupWorkId());
                    //插入订单数据
                    cereShopOrderDAO.insert(order);
                    //新增订单商品数据
                    addOrderProduct(skus,order.getOrderId(),shop.getShopId(),map,carts,attributes);
                    //设置30分钟延时自动取消订单,并且释放库存
                    stringRedisService.set(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId(),param.getShops(),30*60*1000);
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId(),TimeUtils.getMinuteAfter(30));
                }
            }
            //批量更新库存数量
            //cereProductSkuService.updateBatch(skus);
            if(!EmptyUtils.isEmpty(seckillDetails)){
                //设置缓存中限量库存数据
                seckillDetails.forEach(detail -> {
                    stringRedisService.set("秒杀活动商品仅剩件数" + detail.getShopSeckillId() + detail.getSkuId(), detail.getNumber());
                });
                //批量更新限量库存数量
                cereShopSeckillDetailService.updateBatch(seckillDetails);
            }
            //插入规格属性数据
            if(!EmptyUtils.isEmpty(attributes)){
                cereOrderProductAttributeService.insertBatch(attributes);
            }
        }
    }*/

    /*
    private void addDiscountOrder(Long parentId, OrderParam param, CereBuyerUser user, String time, Map<Long, CartSku> map,
                                  CereBuyerCoupon buyerCoupon, Map<Long,CereBuyerShopCoupon> discountMap,
                                  List<CereShopDiscountDetail> discountDetails) throws CoBusinessException,Exception{
        //遍历店铺
        List<CartSku> carts=new ArrayList<>();
        List<CartSku> skus=null;
        if(!EmptyUtils.isEmpty(map)){
            //拿到所有商品数据
            skus=map.keySet().stream().map(key -> map.get(key)).collect(Collectors.toList());
        }
        //查询所有规格属性数据
        List<OrderProductAttribute> attributes=cereOrderProductAttributeService.findBySkus(skus);
        //查询收货地址数据
        CereBuyerReceive receive=cereBuyerReceiveService.findById(param.getReceiveId());
        if(receive!=null){
            for (OrderProductParam shop : param.getShops()) {
                //封装子订单数据
                CereShopOrder order=new CereShopOrder();
                order.setShopId(shop.getShopId());
                order.setParentId(parentId);
                order.setBuyerUserId(user.getBuyerUserId());
                order.setCreateTime(time);
                order.setUpdateTime(time);
                order.setShopDiscountId(param.getShopDiscountId());
                order.setCouponId(param.getCouponId());
                order.setReceiveName(receive.getReceiveName());
                order.setReceivePhone(receive.getReceivePhone());
                order.setReceiveAdress(receive.getReceiveAdress());
                order.setAddress(receive.getAddress());
                order.setCustomerName(user.getWechatName());
                order.setCustomerPhone(user.getPhone());
                order.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                order.setLogisticsId(shop.getDistribution().getLogisticsId());
                order.setState(IntegerEnum.ORDER_STAY_PAY.getCode());
                order.setOrderFormid(RandomStringUtil.getRandomCode(15,0));
                //定义订单优惠券金额（平台优惠金额/店铺总数+店铺优惠金额）
                BigDecimal discountPrice=new BigDecimal(0);
                if(buyerCoupon!=null){
                    discountPrice=buyerCoupon.getReduceMoney().divide(
                            new BigDecimal(param.getShops().size()),2,BigDecimal.ROUND_HALF_UP);
                    //更新平台优惠券状态为已使用
                    buyerCoupon.setUpdateTime(time);
                    buyerCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                    cereBuyerCouponService.updateState(buyerCoupon);
                    //删除redis延时任务
                    stringRedisService.delete(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                    //删除redis记录数据
                    cereRedisKeyServcice.deleteByKey(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                }
                //计算订单优惠金额
                if(!EmptyUtils.isEmpty(shop.getId())&&!EmptyUtils.isEmpty(discountMap)){
                    order.setId(shop.getId());
                    if(!EmptyUtils.isEmpty(discountMap.get(shop.getId()))){
                        CereBuyerShopCoupon cereBuyerShopCoupon = discountMap.get(shop.getId());
                        //更新店铺优惠券状态为已使用
                        cereBuyerShopCoupon.setUpdateTime(time);
                        cereBuyerShopCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                        cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
                        //删除redis延时任务
                        stringRedisService.delete(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        order.setShopOperateId(cereBuyerShopCoupon.getShopOperateId());
                        if(!EmptyUtils.isEmptyBigdecimal(discountPrice)){
                            discountPrice=discountPrice.add(cereBuyerShopCoupon.getReduceMoney());
                        }else {
                            discountPrice=cereBuyerShopCoupon.getReduceMoney();
                        }
                    }
                }
                order.setDiscountPrice(discountPrice);
                if(!EmptyUtils.isEmpty(skus)){
                    //定义店铺商品总金额字段
                    final BigDecimal[] total = {new BigDecimal(0)};
                    //设置规格数量
                    skus.forEach(sku -> {
                        if(sku.getShopId().equals(shop.getShopId())){
                            total[0] = total[0].add(sku.getPrice().multiply(
                                    new BigDecimal(sku.getNumber())).setScale(2,BigDecimal.ROUND_HALF_UP));
                        }
                    });
                    //订单总金额=店铺商品总金额+运费
                    order.setOrderPrice(total[0].add(order.getLogisticsPrice()));
                    //订单支付金额=订单总金额-优惠
                    order.setPrice(order.getOrderPrice().subtract(order.getDiscountPrice()));
                    order.setPaymentState(IntegerEnum.ORDER_PAY_STAY.getCode());
                    order.setPaymentMode(param.getPaymentMode());
                    order.setRemark(shop.getRemark());
                    order.setBuyerUserId(user.getBuyerUserId());
                    order.setShopGroupWorkId(param.getShopGroupWorkId());
                    //插入订单数据
                    cereShopOrderDAO.insert(order);
                    //新增订单商品数据
                    addOrderProduct(skus,order.getOrderId(),shop.getShopId(),map,carts,attributes);
                    //设置30分钟延时自动取消订单,并且释放库存
                    stringRedisService.set(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId(),param.getShops(),30*60*1000);
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId(),TimeUtils.getMinuteAfter(30));
                }
            }
            //批量更新库存数量
            //cereProductSkuService.updateBatch(skus);
            if(!EmptyUtils.isEmpty(discountDetails)){
                //设置缓存中限量库存数据
                discountDetails.forEach(detail -> {
                    stringRedisService.set("限时折扣活动商品仅剩件数" + detail.getShopDiscountId() + detail.getSkuId(), detail.getNumber());
                });
                //批量更新限量库存数据
                cereShopDiscountDetailService.updateBatch(discountDetails);
            }
            //插入规格属性数据
            if(!EmptyUtils.isEmpty(attributes)){
                cereOrderProductAttributeService.insertBatch(attributes);
            }
        }
    }
     */

    private void normalOrder(OrderParam param, CereBuyerUser user, String ip,PayUrl payUrl,String time) throws CoBusinessException,Exception{
        Map<Long, CartSku> map=new HashMap<>();
        if(!EmptyUtils.isEmpty(param.getShops())){
            //定义map封装商品购买数量
            Map<Long, ProductSku> numberMap=new HashMap<>();
            param.getShops().forEach(shop -> {
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    shop.getSkus().forEach(sku -> {
                        numberMap.put(sku.getSkuId(),sku);
                    });
                }
            });
            //校验商品库存
            AtomicBoolean flag= new AtomicBoolean(false);
            //定义运费
            BigDecimal logisticPrice=new BigDecimal(0);
            List<Long> shopIdList = param.getShops().stream().map(OrderProductParam::getShopId).collect(Collectors.toList());

            // 平台秒杀
            List<ShopPlatformSeckill> platformSeckillList = cerePlatformSeckillService.selectPlatformSeckillsByShopIdList(shopIdList);
            Map<Long, List<ShopPlatformSeckill>> platformSeckillMap = platformSeckillList.stream().collect(Collectors.groupingBy(ShopPlatformSeckill::getSeckillId));

            // 平台折扣
            List<ShopPlatformDiscount> platformDiscountList = cerePlatformDiscountService.selectPlatformDiscountByShopIdList(shopIdList);
            Map<Long, List<ShopPlatformDiscount>> platformDiscountMap = platformDiscountList.stream().collect(Collectors.groupingBy(ShopPlatformDiscount::getDiscountId));

            // 商家秒杀
            List<ShopBusinessSeckill> seckillList = cereShopSeckillService.selectByShopIdList(shopIdList);
            Map<Long, List<ShopBusinessSeckill>> skuIdSeckillMap = seckillList.stream().collect(Collectors.groupingBy(ShopBusinessSeckill::getSkuId));

            // 商家折扣
            List<ShopBusinessDiscount> discountList = cereShopDiscountService.selectByShopIdList(shopIdList);
            Map<Long, List<ShopBusinessDiscount>> skuIdDiscountMap = discountList.stream().collect(Collectors.groupingBy(ShopBusinessDiscount::getSkuId));

            // 定价捆绑
            Map<Long, Map<Long, List<CerePriceRule>>> priceMap = cereShopPriceService.selectPriceMap(shopIdList);

            // 组合捆绑
            List<CereShopComposeDTO> composeDTOList = cereShopComposeService.selectByShopIdList(shopIdList);
            Map<Long, List<CereShopComposeDTO>> composeMap = composeDTOList.stream().collect(Collectors.groupingBy(CereShopComposeDTO::getComposeId));

            Map<Long, BigDecimal> shopIdDiscountPriceMap = new HashMap<>();

            for (OrderProductParam shop : param.getShops()) {
                shopIdDiscountPriceMap.put(shop.getShopId(), BigDecimal.ZERO);

                shopIdList.add(shop.getShopId());
                if(!EmptyUtils.isEmpty(shop.getDistribution())){
                    //计算总得运费
                    logisticPrice=logisticPrice.add(shop.getDistribution().getDistributionPrice());
                }
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    //查询当前店铺所有购买商品的库存数据
                    List<CartSku> productSkus=cereProductSkuService.findStockNumberBySkus(shop.getSkus());
                    if(!EmptyUtils.isEmpty(productSkus)){
                        productSkus.forEach(sku -> {
                            //设置购买数量
                            sku.setNumber(numberMap.get(sku.getSkuId()).getNumber());
                            //设置选中状态
                            sku.setSelected(numberMap.get(sku.getSkuId()).getSelected());
                            //设置店铺id
                            sku.setShopId(shop.getShopId());
                            map.put(sku.getSkuId(),sku);
                        });
                    }
                    //修正库存
                    for (ProductSku sku : shop.getSkus()) {
                        CartSku cartSku = map.get(sku.getSkuId());
                        Long productId = cartSku.getProductId();
                        if (sku.getPlatformSeckillId() != null && sku.getPlatformSeckillId() > 0) {
                            ProductStockInfo stockInfo = cereStockService.getActivityProductStock(IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode(), productId);
                            if (stockInfo != null) {
                                cartSku.setStockNumber(stockInfo.getStockNumber());
                                cartSku.setActivityType(IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode());
                                cartSku.setActivityId(sku.getPlatformSeckillId());
                            }
                        } else if (sku.getPlatformDiscountId() != null && sku.getPlatformDiscountId() > 0) {
                            ProductStockInfo stockInfo = cereStockService.getActivityProductStock(IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode(), productId);
                            if (stockInfo != null) {
                                cartSku.setStockNumber(stockInfo.getStockNumber());
                                cartSku.setActivityType(IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode());
                                cartSku.setActivityId(sku.getPlatformDiscountId());
                            }
                        } else if (sku.getShopSeckillId() != null && sku.getShopSeckillId() > 0) {
                            ProductStockInfo stockInfo = cereStockService.getActivitySkuStock(IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode(), sku.getSkuId());
                            if (stockInfo != null) {
                                cartSku.setStockNumber(stockInfo.getStockNumber());
                                cartSku.setActivityType(IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode());
                                cartSku.setActivityId(sku.getShopSeckillId());
                            }
                        } else if (sku.getShopDiscountId() != null && sku.getShopDiscountId() > 0) {
                            ProductStockInfo stockInfo = cereStockService.getActivitySkuStock(IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode(), sku.getSkuId());
                            if (stockInfo != null) {
                                cartSku.setStockNumber(stockInfo.getStockNumber());
                                cartSku.setActivityType(IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode());
                                cartSku.setActivityId(sku.getShopDiscountId());
                            }
                        }
                    }
                    shop.getSkus().forEach(sku -> {
                        if(IntegerEnum.NO.getCode().equals(map.get(sku.getSkuId()).getIfOversold())){
                            //如果不允许超卖,校验redis中商品库存
                            int stockNumber=0;
                            /*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                            }else {
                                stockNumber=map.get(sku.getSkuId()).getStockNumber();
                            }*/
                            stockNumber=map.get(sku.getSkuId()).getStockNumber();
                            if(sku.getNumber()>stockNumber){
                                flag.set(true);
                            }
                        }
                    });

                    //应用平台秒杀id
                    //applyPlatformSeckill();
                    List<ProductSku> skuList = shop.getSkus().stream().filter(sku -> sku.getPlatformSeckillId() != null && sku.getPlatformSeckillId() > 0).collect(Collectors.toList());
                    Map<Long, List<ProductSku>> skuMap = skuList.stream().collect(Collectors.groupingBy(ProductSku::getPlatformSeckillId));
                    BigDecimal tmpDiscountPrice = BigDecimal.ZERO;
                    for (Long id:skuMap.keySet()) {
                        List<ShopPlatformSeckill> secList = platformSeckillMap.get(id);
                        if (CollectionUtils.isNotEmpty(secList)) {
                            ShopPlatformSeckill sec = secList.get(0);
                            //long num = skuMap.get(id).stream().mapToLong(ProductSku::getNumber).sum();
                            for (ProductSku sku:skuMap.get(id)) {
                                CartSku cartSku = map.get(sku.getSkuId());
                                cartSku.setPrice(cartSku.getPrice().subtract(sec.getSeckillMoney()));
                                if (cartSku.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                                    cartSku.setPrice(BigDecimal.ZERO);
                                }
                            }
                            //tmpDiscountPrice = sec.getSeckillMoney().multiply(new BigDecimal(num));
                            //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                        }
                    }

                    //应用平台折扣id
                    //applyPlatformDiscount();
                    skuList = shop.getSkus().stream().filter(sku -> sku.getPlatformDiscountId() != null && sku.getPlatformDiscountId() > 0).collect(Collectors.toList());
                    skuMap = skuList.stream().collect(Collectors.groupingBy(ProductSku::getPlatformDiscountId));
                    tmpDiscountPrice = BigDecimal.ZERO;
                    for (Long id:skuMap.keySet()) {
                        List<ShopPlatformDiscount> disList = platformDiscountMap.get(id);
                        if (CollectionUtils.isNotEmpty(disList)) {
                            ShopPlatformDiscount discount = disList.get(0);

                            List<ProductSku> disSkuList = skuMap.get(id);
                            tmpDiscountPrice = BigDecimal.ZERO;
                            for (ProductSku disSku:disSkuList) {
                                CartSku cartSku = map.get(disSku.getSkuId());
                                cartSku.setPrice(cartSku.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                                /*tmpDiscountPrice = tmpDiscountPrice.add(map.get(disSku.getSkuId()).getPrice().multiply(new BigDecimal(disSku.getNumber()))
                                        .multiply(BigDecimal.TEN.subtract(discount.getDiscount())).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));*/
                            }
                            //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                        }
                    }

                    //应用商家秒杀id
                    //applyShopSeckill();
                    skuList = shop.getSkus().stream().filter(sku -> sku.getShopSeckillId() != null && sku.getShopSeckillId() > 0).collect(Collectors.toList());
                    tmpDiscountPrice = BigDecimal.ZERO;
                    for (ProductSku tmpSku:skuList) {
                        List<ShopBusinessSeckill> list = skuIdSeckillMap.get(tmpSku.getSkuId());
                        if (CollectionUtils.isNotEmpty(list)) {
                            ShopBusinessSeckill seckill = list.get(0);
                            CartSku cartSku = map.get(tmpSku.getSkuId());
                            cartSku.setPrice(seckill.getSeckillPrice());
                            //tmpDiscountPrice = map.get(tmpSku.getSkuId()).getPrice().subtract(seckill.getSeckillPrice()).multiply(new BigDecimal(tmpSku.getNumber()));
                            //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                        }
                    }

                    //应用商家折扣id
                    //applyShopDiscount();
                    skuList = shop.getSkus().stream().filter(sku -> sku.getShopDiscountId() != null && sku.getShopDiscountId() > 0).collect(Collectors.toList());
                    tmpDiscountPrice = BigDecimal.ZERO;
                    for (ProductSku tmpSku:skuList) {
                        List<ShopBusinessDiscount> list = skuIdDiscountMap.get(tmpSku.getSkuId());
                        if (CollectionUtils.isNotEmpty(list)) {
                            ShopBusinessDiscount dis = list.get(0);
                            CartSku cartSku = map.get(tmpSku.getSkuId());
                            cartSku.setPrice(cartSku.getPrice().multiply(dis.getDiscount()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                            //tmpDiscountPrice = map.get(tmpSku.getSkuId()).getPrice().subtract(dis.getPrice()).multiply(new BigDecimal(tmpSku.getNumber()));
                            //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                        }
                    }

                    //应用定价捆绑
                    //applyPriceCombine();
                    skuList = shop.getSkus().stream().filter(sku -> sku.getPriceId() != null && sku.getPriceId() > 0).collect(Collectors.toList());
                    skuMap = skuList.stream().collect(Collectors.groupingBy(ProductSku::getPriceId));
                    tmpDiscountPrice = BigDecimal.ZERO;
                    Map<Long, List<CerePriceRule>> shopPriceMap = priceMap.get(shop.getShopId());
                    for (Long id:skuMap.keySet()) {
                        List<CerePriceRule> disList = shopPriceMap.get(id);
                        if (CollectionUtils.isNotEmpty(disList)) {
                            List<ProductSku> tmpSkuList = skuMap.get(id);
                            BigDecimal originalPrice = BigDecimal.ZERO;
                            for (ProductSku tmpSku:tmpSkuList) {
                                originalPrice = originalPrice.add(new BigDecimal(tmpSku.getNumber()).multiply(map.get(tmpSku.getSkuId()).getPrice()));
                            }
                            int num = tmpSkuList.stream().mapToInt(ProductSku::getNumber).sum();
                            tmpSkuList.sort((o1,o2) -> o2.getNumber().compareTo(o2.getNumber()));

                            for (CerePriceRule rule:disList) {
                                if (num >= rule.getNumber()) {
                                    int multiple = num / rule.getNumber();
                                    //tmpDiscountPrice = originalPrice.subtract(new BigDecimal(multiple).multiply(rule.getPrice()));
                                    BigDecimal totalPrice = new BigDecimal(multiple).multiply(rule.getPrice());
                                    int remainNum = num - multiple * rule.getNumber();
                                    if (remainNum != 0) {
                                        CartSku minNumberSku = map.get(tmpSkuList.get(0).getSkuId());
                                        totalPrice = totalPrice.add(new BigDecimal(remainNum).multiply(minNumberSku.getPrice()));
                                    }
                                    BigDecimal eachPrice = totalPrice.divide(new BigDecimal(num), 2, BigDecimal.ROUND_HALF_DOWN);
                                    for (ProductSku tmpSku:tmpSkuList) {
                                        map.get(tmpSku.getSkuId()).setPrice(eachPrice);
                                    }
                                    tmpDiscountPrice = totalPrice.subtract(eachPrice.multiply(new BigDecimal(num))).negate();
                                    shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                                    break;
                                }
                            }
                            //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                        }
                    }

                    //应用组合捆绑
                    //applyComposeCombine();
                    skuList = shop.getSkus().stream().filter(sku -> sku.getComposeId() != null && sku.getComposeId() > 0).collect(Collectors.toList());
                    skuMap = skuList.stream().collect(Collectors.groupingBy(ProductSku::getComposeId));
                    for (Long id:skuMap.keySet()) {
                        List<CereShopComposeDTO> list = composeMap.get(id);
                        List<ProductSku> tmpSkuList = skuMap.get(id);
                        BigDecimal originalPrice = BigDecimal.ZERO;
                        for (ProductSku tmpSku:tmpSkuList) {
                            originalPrice = originalPrice.add(new BigDecimal(tmpSku.getNumber()).multiply(map.get(tmpSku.getSkuId()).getPrice()));
                        }

                        int totalNum = tmpSkuList.stream().mapToInt(ProductSku::getNumber).sum();
                        int minNum = tmpSkuList.stream().mapToInt(ProductSku::getNumber).min().getAsInt();
                        CereShopComposeDTO composeDTO = list.get(0);
                        if (composeDTO.getComposeType() == 1) {
                            tmpDiscountPrice = originalPrice.subtract(composeDTO.getPromote().multiply(new BigDecimal(minNum)));
                        } else if (composeDTO.getComposeType() == 2) {
                            tmpDiscountPrice = composeDTO.getPromote().multiply(new BigDecimal(minNum));
                        } else {
                            BigDecimal discountPrice = BigDecimal.ZERO;
                            for(ProductSku sku:tmpSkuList) {
                                discountPrice = discountPrice.add(map.get(sku.getSkuId()).getPrice().multiply(new BigDecimal(minNum)).multiply(BigDecimal.TEN.subtract(composeDTO.getPromote())).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                            }
                            tmpDiscountPrice = discountPrice;
                        }
                        BigDecimal eachPrice = originalPrice.subtract(tmpDiscountPrice).divide(new BigDecimal(totalNum), 2, BigDecimal.ROUND_HALF_UP);
                        for (ProductSku tmpSku:tmpSkuList) {
                            map.get(tmpSku.getSkuId()).setPrice(eachPrice);
                        }
                        tmpDiscountPrice = originalPrice.subtract(eachPrice.multiply(new BigDecimal(totalNum))).negate();

                        shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));
                    }

                    //应用场景营销
                    //applySceneMarket();
                    skuList = shop.getSkus().stream().filter(sku -> sku.getSceneId() != null && sku.getSceneId() > 0).collect(Collectors.toList());
                    //tmpDiscountPrice = BigDecimal.ZERO;
                    CereShopSceneMember sceneMember = null;
                    for (ProductSku sku:skuList) {
                        if (sceneMember == null) {
                            sceneMember = cereShopSceneMemberService.selectSceneMemberList(sku.getSceneId(), user.getMemberLevelId());
                        }
                        if (sceneMember != null) {
                            BigDecimal skuPrice = map.get(sku.getSkuId()).getPrice();
                            skuPrice = skuPrice.multiply(sceneMember.getDiscount()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP);
                            map.get(sku.getSkuId()).setPrice(skuPrice);
                            //tmpDiscountPrice = tmpDiscountPrice.add(map.get(sku.getSkuId()).getPrice().multiply(new BigDecimal(sku.getNumber())).multiply(BigDecimal.TEN.subtract(sceneMember.getDiscount())).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));

                    //应用会员价
                    //applyMember();
                    skuList = shop.getSkus().stream().filter(ProductSku::isUseMember).collect(Collectors.toList());
                    //tmpDiscountPrice = BigDecimal.ZERO;
                    for (ProductSku sku:skuList) {
                        CereProductMember productMember = cereProductMemberService.selectProductMember(user.getMemberLevelId(), map.get(sku.getSkuId()).getProductId(), sku.getSkuId());
                        if (productMember != null) {
                            //BigDecimal num = new BigDecimal(sku.getNumber());
                            if (IntegerEnum.MEMBER_PRODUCT_MODE_PRICE.getCode().equals(productMember.getMode())) {
                                map.get(sku.getSkuId()).setPrice(productMember.getPrice());
                                //sku的价格必须 > 会员价
                                /*if (map.get(sku.getSkuId()).getPrice().compareTo(productMember.getPrice()) > 0) {
                                    tmpDiscountPrice = tmpDiscountPrice.add(map.get(sku.getSkuId()).getPrice().subtract(productMember.getPrice()).multiply(num));
                                }*/
                            } else {
                                BigDecimal skuPrice = map.get(sku.getSkuId()).getPrice();
                                skuPrice = skuPrice.multiply(productMember.getPrice()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP);
                                map.get(sku.getSkuId()).setPrice(skuPrice);
                                // 打折默认折扣都是正常的 价格 x 数量 x (10-折扣) / 10 就是优惠价
                                //tmpDiscountPrice = tmpDiscountPrice.add(map.get(sku.getSkuId()).getPrice().multiply(num).multiply(BigDecimal.TEN.subtract(productMember.getPrice())).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    //shopIdDiscountPriceMap.put(shop.getShopId(), shopIdDiscountPriceMap.get(shop.getShopId()).add(tmpDiscountPrice));

                    //应用普通价
                    //applyNormal();
                }
            }
            if(flag.get()){
                throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
            }
            BigDecimal orderPrice=setOrderPrice(map,numberMap);
            for (BigDecimal value:shopIdDiscountPriceMap.values()) {
                orderPrice = orderPrice.subtract(value);
            }
            //查询平台优惠券数据
            CereBuyerCoupon buyerCoupon=cereBuyerCouponService.findByCouponId(param.getCouponId(),user.getBuyerUserId());
            //定义店铺优惠金额map
            Map<Long,CereBuyerShopCoupon> discountMap=new HashMap<>();
            //计算优惠金额
            param.setDiscountPrice(setDiscountPrice(buyerCoupon,param,map,numberMap,discountMap));
            //计算订单实付金额=订单总金额-优惠+运费
            if(!EmptyUtils.isEmpty(logisticPrice)){
                param.setPrice(orderPrice.subtract(param.getDiscountPrice()).add(logisticPrice));
            }else {
                param.setPrice(orderPrice.subtract(param.getDiscountPrice()));
            }
            //新增父订单数据
            CereOrderParent parent=new CereOrderParent();
            parent.setCreateTime(time);
            parent.setLogisticsPrice(logisticPrice);
            //订单总价
            parent.setOrderPrice(orderPrice);
            parent.setPrice(param.getPrice());
            parent.setParentFormid(RandomStringUtil.getRandomCode(15,0));
            cereOrderParentService.insert(parent);
            payUrl.setMoney(param.getPrice());
            payUrl.setOrderId(parent.getParentId());
            if(EmptyUtils.isEmptyBigdecimal(payUrl.getMoney())){
                //如果金额为0,提示
                throw new CoBusinessException(CoReturnFormat.PAY_MONEY_NOT_ZERO);
            }
            //生成支付二维码
            generatePayUrl(param, user.getWechatOpenId(), parent.getParentFormid(), payUrl.getMoney(), ip, payUrl);

            //遍历店铺数据,新增订单
            addOrder(parent.getParentId(),param,user,time,map,buyerCoupon,discountMap);
        }
    }

    private BigDecimal setOrderPrice(Map<Long, CartSku> map, Map<Long, ProductSku> numberMap) {
        BigDecimal total=new BigDecimal(0);
        for (Long skuId : map.keySet()) {
            total=total.add(map.get(skuId).getPrice().multiply(new BigDecimal(numberMap.get(skuId).getNumber())));
        }
        return total;
    }

    /**
     * 先修改sku的价格 为组合后的价格
     * @param map
     * @param numberMap
     * @param priceCombineParam
     * @return
     */
    private BigDecimal setOrderPriceExt(Map<Long, CartSku> map, Map<Long, ProductSku> numberMap, PriceCombineParam priceCombineParam, CereBuyerUser user) {
        //这个价格代表，享受定价捆绑的sku的价格之和，例如：某个sku买5件，享受的是任选3件定价N元，则该值为sku的原价 * 3
        BigDecimal useToDiscountPrice = BigDecimal.ZERO;
        //享受的捆绑的定价 * 享受的次数 例如: 某个sku买10件，享受的是任选3件定价10元，则可享受3次，总共 3 * 10 = 30
        BigDecimal finalPrice = BigDecimal.ZERO;
        if (priceCombineParam != null && MapUtils.isNotEmpty(priceCombineParam.getPriceMap()) && MapUtils.isNotEmpty(priceCombineParam.getPriceProductIdListMap())) {
            Map<Long,Map<Long,List<CerePriceRule>>> priceRuleMap = priceCombineParam.getPriceMap();
            Map<Long,List<Long>> priceIdProductIdListMap = priceCombineParam.getPriceProductIdListMap();

            List<CartSku> skuList = new ArrayList<>();
            for (Long skuId : map.keySet()) {
                skuList.add(map.get(skuId));
            }
            skuList.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
            Map<Long,List<CartSku>> shopIdSkuMap = skuList.stream().collect(Collectors.groupingBy(CartSku::getShopId));
            for (Long shopId:shopIdSkuMap.keySet()) {
                List<CartSku> shopCartSkuList = shopIdSkuMap.get(shopId);
                Map<Long,List<CerePriceRule>> priceIdRuleListMap = priceRuleMap.get(shopId);
                for (Long priceId:priceIdRuleListMap.keySet()) {
                    List<Long> productIdList = priceIdProductIdListMap.get(priceId);
                    if (CollectionUtils.isEmpty(productIdList)) {
                        continue;
                    }
                    //该活动对应的等级列表，这个列表已经按照任选X件的 X做了降序排列
                    List<CerePriceRule> ruleList = priceIdRuleListMap.get(priceId);
                    for (CerePriceRule priceRule:ruleList) {
                        Integer number = priceRule.getNumber();
                        BigDecimal price = priceRule.getPrice();
                        //先筛选在活动指定商品范围中的sku
                        List<CartSku> validSkuList = new ArrayList<>();
                        Integer validBuyNum = 0;
                        for (CartSku sku:shopCartSkuList) {
                            if (productIdList.contains(sku.getProductId())) {
                                validSkuList.add(sku);
                                validBuyNum += sku.getNumber();
                            }
                        }

                        if (validBuyNum >= number) {
                            //可以优惠multiple次
                            int multiple = validBuyNum / number;
                            int remainNum = number * multiple;
                            Iterator<CartSku> ite = validSkuList.iterator();
                            while(ite.hasNext()) {
                                CartSku sku = ite.next();
                                if (remainNum >= sku.getNumber()) {
                                    useToDiscountPrice = useToDiscountPrice.add(new BigDecimal(sku.getNumber()).multiply(sku.getPrice()));
                                    remainNum = remainNum - sku.getNumber();
                                } else {
                                    useToDiscountPrice = useToDiscountPrice.add(new BigDecimal(remainNum).multiply(sku.getPrice()));
                                    remainNum = 0;
                                }
                                ite.remove();
                                if (remainNum == 0) {
                                    break;
                                }
                            }
                            finalPrice = new BigDecimal(multiple).multiply(price);
                            break;
                        }
                    }
                }
            }
        }
        BigDecimal total=new BigDecimal(0);
        for (Long skuId : map.keySet()) {
            total=total.add(map.get(skuId).getPrice().multiply(new BigDecimal(numberMap.get(skuId).getNumber())));
        }
        return total.subtract(useToDiscountPrice.subtract(finalPrice));
    }

    private BigDecimal setDiscountPrice(CereBuyerCoupon buyerCoupon, OrderParam param,Map<Long,
            CartSku> map,Map<Long,ProductSku> numberMap,Map<Long,CereBuyerShopCoupon> discountMap) {
        //定义满足优惠券商品价格总计
        BigDecimal total;
        BigDecimal decimal=new BigDecimal(0);
        if(buyerCoupon!=null){
            //加上平台优惠券
            decimal=decimal.add(buyerCoupon.getReduceMoney());
        }
        //查询所有店铺优惠券数据
        List<CereBuyerShopCoupon> list=cereBuyerShopCouponService.findByIds(param.getShops());
        //计算店铺优惠券金额总和
        if(!EmptyUtils.isEmpty(list)){
            for (CereBuyerShopCoupon cereBuyerShopCoupon : list) {
                total=new BigDecimal(0);
                if(IntegerEnum.COUPON_TYPE_DISCOUNT.getCode().equals(cereBuyerShopCoupon.getCouponType())){
                    //如果是折扣券,查询改折扣券关联商品id
                    List<Long> ids=cereBuyerShopCouponService.findProductIds(cereBuyerShopCoupon.getShopCouponId());
                    if(!EmptyUtils.isEmpty(param.getShops())){
                        //遍历购买商品
                        if(!EmptyUtils.isEmpty(map)){
                            for (Long key : map.keySet()) {
                                if(ids.contains(map.get(key).getProductId())){
                                    //如果商品满足优惠券,叠加总计
                                    total=total.add(map.get(key).getPrice().multiply(new BigDecimal(numberMap.get(key).getNumber())));
                                }
                            }
                            //计算折扣价格=总计-总计*折扣
                            BigDecimal price=cereBuyerShopCoupon.getReduceMoney().divide(new BigDecimal(10),2,BigDecimal.ROUND_HALF_UP);
                            BigDecimal discount=total.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP);
                            cereBuyerShopCoupon.setReduceMoney(total.subtract(discount));
                            discountMap.put(cereBuyerShopCoupon.getId(),cereBuyerShopCoupon);
                            decimal=decimal.add(total.subtract(discount));
                        }
                    }
                }else {
                    //如果是满减券直接叠加
                    discountMap.put(cereBuyerShopCoupon.getId(),cereBuyerShopCoupon);
                    decimal=decimal.add(cereBuyerShopCoupon.getReduceMoney());
                }
            }
        }
        return decimal;
    }

    /**
     * 根据不同支付类型生成不同的支付url
     */
    private void generatePayUrl(OrderParam orderParam, String openId, String parentFormId, BigDecimal orderPrice, String ip, PayUrl payUrl) throws Exception {
        Integer paymentMode = orderParam.getPaymentMode();
        Integer subPaymentMode = orderParam.getSubPaymentMode();
        if (paymentMode != null && paymentMode.equals(IntegerEnum.ORDER_PAY_WX.getCode())
                && subPaymentMode != null && subPaymentMode.equals(IntegerEnum.ORDER_SUB_PAYMENT_MODE_H5.getCode())) {
            // h5支付 前端重新调用统一下单
        } else if (paymentMode != null && paymentMode.equals(IntegerEnum.ORDER_PAY_WX.getCode())
                && subPaymentMode != null && subPaymentMode.equals(IntegerEnum.ORDER_SUB_PAYMENT_MODE_XCX.getCode())) {
            // 小程序支付 前端重新调用统一下单
        } else if (paymentMode != null && Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(paymentMode)) {
            // 支付宝支付
        } else {
            //生成收款码
            String formid = parentFormId +"-"+ RandomStringUtil.getRandomCode(3, 0) + "XCX";
            String code_url = wxPayService.getOrderCollectionCode(formid, orderPrice,
                    ip, WxPayEnum.PAY_TYPE_NATIVE.getCode());
            if (!EmptyUtils.isEmpty(code_url)) {
                //生成收款二维码图片
                Map<EncodeHintType, Object> hints = new HashMap<>();
                // 设置纠错等级
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                // 编码类型
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, 400, 400, hints);
                MatrixToImageConfig config = new MatrixToImageConfig();
                BufferedImage image = toBufferedImage(bitMatrix, config);
                //上传图片到OSS
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(image, "png", out);
                byte[] bytes = out.toByteArray();
                String url = uploadService.uploadByte(formid + ".png", bytes);
                payUrl.setUrl(url);
            }
        }
    }

    @Override
    public OrderPay gotoPay(PayParam param, CereBuyerUser user,String ip) throws CoBusinessException,Exception {
//        if(EmptyUtils.isEmpty(user.getWechatOpenId())){
//            throw new CoBusinessException(CoReturnFormat.OPENID_IS_NULL);
//        }
        if (param.getPaymentMode() == null) {
            param.setPaymentMode(1);
        }
        if(!EmptyUtils.isLongEmpty(param.getCollageId())){
            //定义加锁key
            String key="拼团订单"+param.getCollageId();
            //加锁
            RLock redissonLock = redisson.getLock(key);
            redissonLock.lock();
            //查询拼单剩余拼团人数
            int person=cereCollageOrderService.findSurplusNumber(param.getCollageId());
            //如果是拼团单支付,查询redis缓存中是否存在该拼单数据
            Object obj = stringRedisService.get(String.valueOf(param.getCollageId()));
            if(EmptyUtils.isEmpty(obj)){
                //如果没有,说明是发起拼团单第一个支付,放行并将拼单数据放到缓存
                stringRedisService.set(String.valueOf(param.getCollageId()),person);
            }else{
                //如果有,说明是参与拼团支付
                int number= (int) stringRedisService.get(String.valueOf(param.getCollageId()));
                if(number<=0){
                    //如果剩余拼团人数小于等于0,拼单已成团,无法支付了
                    throw new CoBusinessException(CoReturnFormat.COLLAGE_ALREADY_SUCCESS,redissonLock);
                }else {
                    //如果大于0,还未成团,修改缓存中剩余拼团人数
                    stringRedisService.set(String.valueOf(param.getCollageId()),number-1);
                }
            }
            //解锁
            redissonLock.unlock();
        }
        List<Long> shopIds=null;
        String orderFormId="";
        BigDecimal price=null;
        if(ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())){
            //父订单提交,查询父订单编号
            CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
            orderFormId=parent.getParentFormid();
            price=parent.getPrice();
            //根据父订单查询该订单下所有的店铺id
            shopIds=cereOrderParentService.findShopIds(param.getOrderId());
        }else {
            //子订单提交,查询子订单编号
            CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
            orderFormId=order.getOrderFormid();
            price=order.getPrice();
            //根据子订单查询该订单店铺id
            shopIds=cereShopOrderDAO.findShopId(param.getOrderId());
        }
        System.out.println("支付订单号为："+orderFormId);
        //调用支付接口
        PayService payService = PayFactory.getPayService(param.getPaymentMode());
        String openId = user.getWechatOpenId();
        if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(param.getPaymentMode())) {
            openId = user.getAliUserId();
        }
        Map<String, String> map = payService.gotoPay(orderFormId, price, openId, ip,ParamEnum.PAY_XCX.getCode(), param.getHuabeiPeriod());
        System.out.println("支付返回结果集"+map.toString());
        ObjectMapper mapper=new ObjectMapper();
        OrderPay pay = mapper.readValue(mapper.writeValueAsString(map), OrderPay.class);
        if(!EmptyUtils.isEmpty(shopIds)){
            List<CereShopConversion> conversions=new ArrayList<>();
            shopIds.forEach(id -> {
                //新增转化数据
                CereShopConversion cereShopConversion=new CereShopConversion();
                cereShopConversion.setShopId(id);
                cereShopConversion.setCreateTime(TimeUtils.yyMMddHHmmss());
                cereShopConversion.setType(ParamEnum.CONVERSION_PAY.getCode());
                conversions.add(cereShopConversion);
            });
            //批量插入转化数据
            cereShopConversionService.insertBatch(conversions);
        }
        return pay;
    }

    @Override
    public OrderPay gotoAppPay(PayParam param, CereBuyerUser user,String ip) throws CoBusinessException,Exception {
        if(EmptyUtils.isEmpty(user.getWechatOpenId())){
            throw new CoBusinessException(CoReturnFormat.OPENID_IS_NULL);
        }
        if(!EmptyUtils.isLongEmpty(param.getCollageId())){
            //定义加锁key
            String key="拼团订单"+param.getCollageId();
            //加锁
            RLock redissonLock = redisson.getLock(key);
            redissonLock.lock();
            //查询拼单剩余拼团人数
            int person=cereCollageOrderService.findSurplusNumber(param.getCollageId());
            //如果是拼团单支付,查询redis缓存中是否存在该拼单数据
            Object obj = stringRedisService.get(String.valueOf(param.getCollageId()));
            if(EmptyUtils.isEmpty(obj)){
                //如果没有,说明是发起拼团单第一个支付,放行并将拼单数据放到缓存
                stringRedisService.set(String.valueOf(param.getCollageId()),person);
            }else{
                //如果有,说明是参与拼团支付
                int number= (int) stringRedisService.get(String.valueOf(param.getCollageId()));
                if(number<=0){
                    //如果剩余拼团人数小于等于0,拼单已成团,无法支付了
                    throw new CoBusinessException(CoReturnFormat.COLLAGE_ALREADY_SUCCESS,redissonLock);
                }else {
                    //如果大于0,还未成团,修改缓存中剩余拼团人数
                    stringRedisService.set(String.valueOf(param.getCollageId()),number-1);
                }
            }
            //解锁
            redissonLock.unlock();
        }
        List<Long> shopIds=null;
        String orderFormId="";
        BigDecimal price=null;
        if(ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())){
            //父订单提交,查询父订单编号
            CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
            orderFormId=parent.getParentFormid();
            price=parent.getPrice();
            //根据父订单查询该订单下所有的店铺id
            shopIds=cereOrderParentService.findShopIds(param.getOrderId());
        }else {
            //子订单提交,查询子订单编号
            CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
            orderFormId=order.getOrderFormid();
            price=order.getPrice();
            //根据子订单查询该订单店铺id
            shopIds=cereShopOrderDAO.findShopId(param.getOrderId());
        }
        //调用微信支付接口
        Map<String, String> map = wxPayService.gotoPay(orderFormId, price, user.getWechatOpenId(), ip,ParamEnum.PAY_APP.getCode(), param.getHuabeiPeriod());
        ObjectMapper mapper=new ObjectMapper();
        OrderPay pay = mapper.readValue(mapper.writeValueAsString(map), OrderPay.class);
        if(!EmptyUtils.isEmpty(shopIds)){
            List<CereShopConversion> conversions=new ArrayList<>();
            shopIds.forEach(id -> {
                //新增转化数据
                CereShopConversion cereShopConversion=new CereShopConversion();
                cereShopConversion.setShopId(id);
                cereShopConversion.setCreateTime(TimeUtils.yyMMddHHmmss());
                cereShopConversion.setType(ParamEnum.CONVERSION_PAY.getCode());
                conversions.add(cereShopConversion);
            });
            //批量插入转化数据
            cereShopConversionService.insertBatch(conversions);
        }
        return pay;
    }

    @Override
    public OrderPay gotoH5Pay(PayParam param, CereBuyerUser user, String ip) throws CoBusinessException,Exception {
        List<Long> shopIds=null;
        String orderFormId="";
        BigDecimal price=null;
        if(ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())){
            //父订单提交,查询父订单编号
            CereOrderParent parent = cereOrderParentService.findById(param.getOrderId());
            orderFormId=parent.getParentFormid();
            price=parent.getPrice();
            //根据父订单查询该订单下所有的店铺id
            shopIds=cereOrderParentService.findShopIds(param.getOrderId());
        }else {
            //子订单提交,查询子订单编号
            CereShopOrder order = cereShopOrderDAO.findById(param.getOrderId());
            orderFormId=order.getOrderFormid();
            price=order.getPrice();
            //根据子订单查询该订单店铺id
            shopIds=cereShopOrderDAO.findShopId(param.getOrderId());
        }
        //调用微信支付接口
        Map<String, String> map = wxPayService.gotoPay(orderFormId, price, user.getWechatOpenId(), ip, ParamEnum.PAY_H5.getCode(), param.getHuabeiPeriod());
        ObjectMapper mapper=new ObjectMapper();
        OrderPay pay = mapper.readValue(mapper.writeValueAsString(map), OrderPay.class);
        if(!EmptyUtils.isEmpty(shopIds)){
            List<CereShopConversion> conversions=new ArrayList<>();
            shopIds.forEach(id -> {
                //新增转化数据
                CereShopConversion cereShopConversion=new CereShopConversion();
                cereShopConversion.setShopId(id);
                cereShopConversion.setCreateTime(TimeUtils.yyMMddHHmmss());
                cereShopConversion.setType(ParamEnum.CONVERSION_PAY.getCode());
                conversions.add(cereShopConversion);
            });
            //批量插入转化数据
            cereShopConversionService.insertBatch(conversions);
        }
        return pay;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleWxLog(String orderFormId, String transactionId, String orderNo) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        List<CereShopOrder> list=null;
        //查询是否为父订单编号
        CereOrderParent parent=cereShopOrderDAO.findByParentFormid(orderFormId);
        if(parent!=null){
            //查询所有子订单数据
            list=cereShopOrderDAO.findByParentId(parent.getParentId());
        }else {
            //子订单支付回调
            list=cereShopOrderDAO.findByFormid(orderFormId);
        }
        //支付业务处理
        if(!EmptyUtils.isEmpty(list)){
            List<CereDistributionOrder> distributionOrders=new ArrayList<>();
            List<CereShopConversion> conversions=new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;
            for (CereShopOrder order:list) {
                total = total.add(order.getPrice());
                if(!EmptyUtils.isEmpty(order.getShopGroupWorkId())){
                    //拼单支付业务处理
                    handleGroupWork(order,transactionId,orderNo,time);
                }else if(!EmptyUtils.isEmpty(order.getShopSeckillId())){
                    //秒杀活动支付业务处理
                    handleSeckill(order,transactionId,orderNo,time);
                }else if(!EmptyUtils.isEmpty(order.getShopDiscountId())){
                    //限时折扣活动支付业务处理
                    handleDiscount(order,transactionId,orderNo,time);
                }else {
                    //正常下单业务处理
                    handleOrder(order,transactionId,orderNo,time,distributionOrders,conversions);
                }
                CereBusinessBuyerUser bbu = new CereBusinessBuyerUser();
                bbu.setShopId(order.getShopId());
                bbu.setBuyerUserId(order.getBuyerUserId());
                bbu.setUpdateTime(time);
                cereBusinessBuyerUserService.refreshUpdateTime(bbu);
            }
            //支付金额四舍五入
            total = total.setScale(0, BigDecimal.ROUND_UP);
            //增加成长值
            cereBuyerUserService.updateGrowth(list.get(0).getBuyerUserId(), total.intValue());

            if(!EmptyUtils.isEmpty(distributionOrders)){
                //批量插入分销订单数据
                cereDistributionOrderService.insertBatch(distributionOrders);
            }
            if(!EmptyUtils.isEmpty(conversions)){
                //批量插入转化数据
                cereShopConversionService.insertBatch(conversions);
            }
        }
    }

    private void handleSeckill(CereShopOrder order, String transactionId, String orderNo, String time) throws Exception{
        //查询秒杀活动数据
        CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(order.getShopSeckillId());
        if(cereShopSeckill!=null){
            //修改订单支付状态和订单状态
            order.setUpdateTime(time);
            order.setPaymentState(IntegerEnum.ORDER_PAY_SUCCESS.getCode());
            order.setPaymentTime(time);
            order.setState(IntegerEnum.ORDER_STAY_DILEVERY.getCode());
            cereShopOrderDAO.updateByPrimaryKeySelective(order);
            //新增订单对账数据
            addReconciliation(order,time);
            //新增支付流水数据
            addPayLog(order,time,orderNo,transactionId);
            //删除自动关闭订单延时任务
            stringRedisService.delete(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId());
            //删除延时任务记录
            cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId());
            // 以下逻辑已经修改为下单减少库存
            /*if(IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())){
                //如果限量,查询活动商品剩余件数
                CereShopSeckillDetail detail=cereShopSeckillDetailService.findSkuDetail(order.getShopSeckillId(),order.getOrderId());
                if(detail!=null){
                    //查询订单商品购买数量
                    int number=cereShopOrderDAO.findOrderNumber(order.getOrderId());
                    //扣除订单购买数量
                    detail.setNumber(detail.getNumber()-number);
                    cereShopSeckillDetailService.updateNumber(detail);
                }
            }*/
            //新增订单支付成功消息
            CereNotice cereNotice=new CereNotice();
            cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
            cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
            cereNotice.setBuyerUserId(order.getBuyerUserId());
            cereNotice.setShopId(order.getShopId());
            cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_PAY_SUCCESS.getCode());
            cereNotice.setNoticeContent("您购买的"+order.getOrderFormid()+"已支付成功，请耐心等待商家发货");
            cereNotice.setOnly(order.getOrderId());
            cereNotice.setCreateTime(time);
            cereNotice.setIfRead(IntegerEnum.NO.getCode());
            cereNoticeService.insert(cereNotice);
        }
    }

    private void handleDiscount(CereShopOrder order, String transactionId, String orderNo, String time) throws Exception{
        //查询限时折扣活动数据
        CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(order.getShopDiscountId());
        if(cereShopDiscount!=null){
            //修改订单支付状态和订单状态
            order.setUpdateTime(time);
            order.setPaymentState(IntegerEnum.ORDER_PAY_SUCCESS.getCode());
            order.setPaymentTime(time);
            order.setState(IntegerEnum.ORDER_STAY_DILEVERY.getCode());
            cereShopOrderDAO.updateByPrimaryKeySelective(order);
            //新增订单对账数据
            addReconciliation(order,time);
            //新增支付流水数据
            addPayLog(order,time,orderNo,transactionId);
            //删除自动关闭订单延时任务
            stringRedisService.delete(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId());
            //删除延时任务记录
            cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId());
            //以下逻辑改为下单时减库存
            /*if(IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())){
                //如果限量,查询活动商品剩余件数
                CereShopDiscountDetail detail=cereShopDiscountDetailService.findSkuDetail(order.getShopDiscountId(),order.getOrderId());
                if(detail!=null){
                    //查询订单商品购买数量
                    int number=cereShopOrderDAO.findOrderNumber(order.getOrderId());
                    //扣除订单购买数量
                    detail.setNumber(detail.getNumber()-number);
                    cereShopDiscountDetailService.updateNumber(detail);
                }
            }*/
            //新增订单支付成功消息
            CereNotice cereNotice=new CereNotice();
            cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
            cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
            cereNotice.setBuyerUserId(order.getBuyerUserId());
            cereNotice.setShopId(order.getShopId());
            cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_PAY_SUCCESS.getCode());
            cereNotice.setNoticeContent("您购买的"+order.getOrderFormid()+"已支付成功，请耐心等待商家发货");
            cereNotice.setOnly(order.getOrderId());
            cereNotice.setCreateTime(time);
            cereNotice.setIfRead(IntegerEnum.NO.getCode());
            cereNoticeService.insert(cereNotice);
        }
    }

    private void handleOrder(CereShopOrder order, String transactionId, String orderNo, String time,
                             List<CereDistributionOrder> distributionOrders,List<CereShopConversion> conversions) throws Exception{
        //修改订单支付状态和订单状态
        order.setUpdateTime(time);
        order.setPaymentState(IntegerEnum.ORDER_PAY_SUCCESS.getCode());
        order.setPaymentTime(time);
        order.setState(IntegerEnum.ORDER_STAY_DILEVERY.getCode());
        //校验当前客户是否为分销员
        ShopDistributor  distributor=null;
        if(!EmptyUtils.isEmpty(order.getCustomerPhone())){
            distributor =cereShopDistributorService.findByPhone(order.getCustomerPhone(),order.getShopId());
        }
        if(distributor!=null){
            if(IntegerEnum.YES.getCode().equals(distributor.getIfSelf())){
                //当前客户就是分销员,并且开启了自购分佣,新增分销订单数据
                addDistributor(distributor,order,time,distributionOrders);
            }
        }else {
            //如果客户不是分销员,判断是否绑定其他分销员,且必须为shopId对应的分销员
            distributor=cereDistributorBuyerService.findByUserId(order.getBuyerUserId(), order.getShopId());
            if(distributor!=null){
                //如果绑定了,新增分销订单数据
                addDistributor(distributor,order,time,distributionOrders);
            }
        }
        cereShopOrderDAO.updateByPrimaryKeySelective(order);
        //新增订单对账数据
        addReconciliation(order,time);
        //新增支付流水数据
        addPayLog(order,time,orderNo,transactionId);
        //新增转化数据
        CereShopConversion cereShopConversion=new CereShopConversion();
        cereShopConversion.setType(ParamEnum.CONVERSION_PAY_SUCCESS.getCode());
        cereShopConversion.setShopId(order.getShopId());
        cereShopConversion.setCreateTime(time);
        conversions.add(cereShopConversion);
        //删除自动关闭订单延时任务
        stringRedisService.delete(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId());
        //删除延时任务记录
        cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId());
        //新增订单支付成功消息
        CereNotice cereNotice=new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_PAY_SUCCESS.getCode());
        cereNotice.setNoticeContent("您购买的"+order.getOrderFormid()+"已支付成功，请耐心等待商家发货");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }

    private void handleGroupWork(CereShopOrder order, String transactionId, String orderNo,String time) throws Exception{
        //根据订单id和拼团活动id查询拼单数据
        CereCollageOrder cereCollageOrder=cereCollageOrderService.findByOrder(order);
        if(cereCollageOrder!=null){
            //修改订单支付状态和订单状态
            order.setUpdateTime(time);
            order.setPaymentState(IntegerEnum.ORDER_PAY_SUCCESS.getCode());
            order.setPaymentTime(time);
            //新增订单对账数据
            addReconciliation(order,time);
            //新增支付流水数据
            addPayLog(order,time,orderNo,transactionId);
            if(cereCollageOrder.getSurplusNumber()>1){
                //待成团业务处理,拼单剩余拼团人数-1
                cereCollageOrder.setSurplusNumber(cereCollageOrder.getSurplusNumber()-1);
                cereCollageOrder.setUpdateTime(time);
                cereCollageOrderService.update(cereCollageOrder);
                //修改订单状态为待成团
                order.setState(IntegerEnum.ORDER_STAY_COLLAGE.getCode());
            }else {
                //拼团成功业务处理,状态改为拼团成功
                cereCollageOrder.setUpdateTime(time);
                cereCollageOrder.setState(IntegerEnum.COLLAGE_STATE_SUCCESS.getCode());
                cereCollageOrder.setSurplusNumber(0);
                cereCollageOrderService.update(cereCollageOrder);
                //查询所有参与拼团的未支付的订单数据,当前订单除外,修改为失效订单
                List<Long> ids=cereCollageOrderService.findNotPayCollageOrderIds(cereCollageOrder.getCollageId(),order.getOrderId());
                if(!EmptyUtils.isEmpty(ids)){
                    cereCollageOrderDetailService.updateInvalid(ids);
                    //取消所有的失效订单
                    cancelBatch(ids);
                }
                //查询所有参与拼团支付过的订单数据,当前订单除外,修改为待发货
                List<CereShopOrder> orders=cereCollageOrderService.findPayCollageOrderIds(cereCollageOrder.getCollageId(),order.getOrderId());
                if(!EmptyUtils.isEmpty(orders)){
                    //批量修改订单状态
                    cereShopOrderDAO.updateBatch(orders,IntegerEnum.ORDER_STAY_DILEVERY.getCode(),time);
                    //封装支付成功消息
                    List<CereNotice> notices = orders.stream().map(cereShopOrder -> {
                        CereNotice cereNotice = new CereNotice();
                        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
                        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
                        cereNotice.setBuyerUserId(cereShopOrder.getBuyerUserId());
                        cereNotice.setShopId(cereShopOrder.getShopId());
                        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_PAY_SUCCESS.getCode());
                        cereNotice.setNoticeContent("您购买的" + cereShopOrder.getOrderFormid() + "已支付成功，请耐心等待商家发货");
                        cereNotice.setOnly(cereShopOrder.getOrderId());
                        cereNotice.setIfRead(IntegerEnum.NO.getCode());
                        cereNotice.setCreateTime(time);
                        return cereNotice;
                    }).collect(Collectors.toList());
                    //批量插入消息
                    cereNoticeService.insertBatch(notices);
                }
                //删除拼团失败延时任务
                stringRedisService.delete(StringEnum.COLLAGE_ERROR.getCode()+"-"+cereCollageOrder.getCollageId());
                cereRedisKeyServcice.deleteByKey(StringEnum.COLLAGE_ERROR.getCode()+"-"+cereCollageOrder.getCollageId());
                //修改订单状态为待发货
                order.setState(IntegerEnum.ORDER_STAY_DILEVERY.getCode());
                //新增订单支付成功消息
                CereNotice cereNotice=new CereNotice();
                cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
                cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
                cereNotice.setBuyerUserId(order.getBuyerUserId());
                cereNotice.setShopId(order.getShopId());
                cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_PAY_SUCCESS.getCode());
                cereNotice.setNoticeContent("您购买的"+order.getOrderFormid()+"已支付成功，请耐心等待商家发货");
                cereNotice.setOnly(order.getOrderId());
                cereNotice.setCreateTime(time);
                cereNotice.setIfRead(IntegerEnum.NO.getCode());
                cereNoticeService.insert(cereNotice);
            }
            cereShopOrderDAO.updateByPrimaryKeySelective(order);
        }
    }


    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleRefundWxLog(String orderFormId, String transaction_id, String orderNo) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单数据
        CereShopOrder order=cereShopOrderDAO.findByOrderFormid(orderFormId);
        if(order!=null){
            //新增对账单数据
            CereOrderReconciliation reconciliation=new CereOrderReconciliation();
            reconciliation.setOrderId(order.getOrderId());
            reconciliation.setMoney(order.getPrice());
            reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
            reconciliation.setType(IntegerEnum.RELATIONSHIP_ONCOME.getCode());
            reconciliation.setCreateTime(time);
            cereOrderReconciliationService.insert(reconciliation);
            //查询支付流水
            CerePayLog cerePayLog=cereShopOrderDAO.findPayLog(order.getOrderFormid());
            //插入退款流水
            CerePayLog payLog=new CerePayLog();
            payLog.setCreateTime(time);
            payLog.setOrderFormid(order.getOrderFormid());
            payLog.setOutTradeNo(orderNo);
            payLog.setTransactionId(transaction_id);
            payLog.setPaymentMode(order.getPaymentMode());
            payLog.setShopId(order.getShopId());
            payLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
            payLog.setTotalFee(order.getPrice());
            payLog.setUserId(String.valueOf(order.getBuyerUserId()));
            payLog.setOutRefundNo(cerePayLog.getOutRefundNo());
            payLog.setRemark(order.getCustomerName()+"取消订单退款"+order.getPrice()+"元,退款单号为："+cerePayLog.getOutRefundNo()
                    +",退款时间为"+time);
            cerePayLogService.insert(payLog);

            //更新订单状态为已取消
            order.setUpdateTime(time);
            order.setState(IntegerEnum.ORDER_STOP.getCode());
            order.setPrice(new BigDecimal(0));
            order.setPaymentState(IntegerEnum.NO.getCode());
            cereShopOrderDAO.updateByPrimaryKeySelective(order);
        }
    }

    @Override
    public SettlementShop findSettlementShop(Long shopId) {
        return cereShopOrderDAO.findSettlementShop(shopId);
    }

    public void handleRefundTestWxLog(String orderFormId, String transaction_id, String orderNo) throws Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单数据
        CereShopOrder order=cereShopOrderDAO.findByOrderFormid(orderFormId);
        if(order!=null){
            //更新订单状态为已取消
            order.setUpdateTime(time);
            order.setState(IntegerEnum.ORDER_STOP.getCode());
            order.setPrice(new BigDecimal(0));
            order.setPaymentState(IntegerEnum.NO.getCode());
            cereShopOrderDAO.updateByPrimaryKeySelective(order);
            //新增对账单数据
            CereOrderReconciliation reconciliation=new CereOrderReconciliation();
            reconciliation.setOrderId(order.getOrderId());
            reconciliation.setMoney(order.getPrice());
            reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
            reconciliation.setType(IntegerEnum.RELATIONSHIP_ONCOME.getCode());
            reconciliation.setCreateTime(time);
            cereOrderReconciliationService.insert(reconciliation);
            //查询支付流水
            CerePayLog cerePayLog=cereShopOrderDAO.findPayLog(orderFormId);
            //插入退款流水
            CerePayLog payLog=new CerePayLog();
            payLog.setCreateTime(time);
            payLog.setOrderFormid(order.getOrderFormid());
            payLog.setOutTradeNo(orderNo);
            payLog.setOutRefundNo(cerePayLog.getOutRefundNo());
            payLog.setTransactionId(transaction_id);
            payLog.setPaymentMode(IntegerEnum.ORDER_PAY_WX.getCode());
            payLog.setShopId(order.getShopId());
            payLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
            payLog.setTotalFee(order.getPrice());
            payLog.setUserId(String.valueOf(order.getBuyerUserId()));
            payLog.setRemark(order.getCustomerName()+"取消订单退款"+order.getPrice()+"元,退款单号为："+cerePayLog.getOutRefundNo()
                    +",退款时间为"+time);
            cerePayLogService.insert(payLog);
        }
    }

    @Override
    public Page getAll(OrderGetAllParam param,CereBuyerUser user) throws CoBusinessException,Exception{
        param.setBuyerUserId(user.getBuyerUserId());
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Orders> list=cereShopOrderDAO.getAll(param);
        if(!EmptyUtils.isEmpty(list)){
            for (Orders orders : list) {
                //计算自动关闭订单倒计时
                orders.setTime(30*60*1000-TimeUtils.getCountDownByTime(orders.getCreateTime(),TimeUtils.yyMMddHHmmss()));
                //查询订单商品明细数据
                List<CartSku> skus=cereOrderProductService.findOrderProductSku(orders.getOrderId());
                if(!EmptyUtils.isEmpty(skus)){
                    //设置规格值数组
                    skus.forEach(sku -> sku.setValues(EmptyUtils.getImages(sku.getValue())));
                }
                //查询该订单已评价商品数量
                int comment=cereShopCommentService.findProductNumber(orders.getOrderId());
                if(skus.size()==comment){
                    //该订单商品全部已评价
                    orders.setIfNotComment(IntegerEnum.NO.getCode());
                }else {
                    orders.setIfNotComment(IntegerEnum.YES.getCode());
                }
                orders.setSkus(skus);
            }
        }
        PageInfo<Orders> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public OrderDetail getById(OrderGetByIdParam param) throws CoBusinessException,Exception {
        Long orderId=param.getOrderId();
        /*if(!EmptyUtils.isLongEmpty(param.getNoticeId())){
            //更新已读状态
            CereNotice cereNotice=new CereNotice();
            cereNotice.setIfRead(IntegerEnum.YES.getCode());
            cereNotice.setNoticeId(param.getNoticeId());
            cereNoticeService.update(cereNotice);
        }*/
        OrderDetail detail=cereShopOrderDAO.getById(orderId);
        if(detail!=null){
            //查询订单商品明细
            List<CartSku> skus=cereOrderProductService.findOrderProductSku(detail.getOrderId());
            if(!EmptyUtils.isEmpty(skus)){
                //设置规格值数组
                skus.forEach(sku -> sku.setValues(EmptyUtils.getImages(sku.getValue())));
            }
            detail.setSkus(skus);
            //计算自动关闭订单倒计时
            detail.setTime(30*60*1000-TimeUtils.getCountDownByTime(detail.getCreateTime(),TimeUtils.yyMMddHHmmss()));
            if(!EmptyUtils.isEmpty(detail.getShopGroupWorkId())){
                //查询拼团信息数据
                CollageDetail collageDetail=cereCollageOrderService.findDetail(detail.getShopGroupWorkId(),detail.getOrderId());
                if(collageDetail!=null){
                    //计算成团有效时间=拼单的发起时间延时拼团活动设置的有效时长后的时间
                    String time = TimeUtils.getMoreHourAfter(collageDetail.getCreateTime(),collageDetail.getEffectiveTime());
                    //设置成团倒计时时间
                    collageDetail.setTime(TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),time));
                    detail.setTime(collageDetail.getTime());
                    //查询参与拼团人员信息
                    collageDetail.setPersonList(cereCollageOrderService.findPersons(collageDetail.getCollageId()));
                    detail.setCollageDetail(collageDetail);
                }
            }
        }
        return detail;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void confirm(OrderGetByIdParam param, CereBuyerUser user) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //校验当前订单状态是否为待收货
        CereShopOrder cereShopOrder=cereShopOrderDAO.checkState(param.getOrderId(),IntegerEnum.ORDER_HAVE_DILEVERY.getCode());
        if(cereShopOrder==null){
            throw new CoBusinessException(CoReturnFormat.ORDER_NOT_TAKE);
        }
        //修改订单状态为已完成
        CereShopOrder order=cereShopOrderDAO.selectByPrimaryKey(param.getOrderId());
        order.setOrderId(param.getOrderId());
        order.setUpdateTime(time);
        order.setState(IntegerEnum.ORDER_FINISH.getCode());
        order.setReceiveTime(time);
        cereShopOrderDAO.updateByPrimaryKeySelective(order);
        //删除自动确认收货延时任务
        stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        //修改订单对账单状态为解冻
        CereOrderReconciliation reconciliation=new CereOrderReconciliation();
        reconciliation.setOrderId(param.getOrderId());
        reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
        reconciliation.setUpdateTime(time);
        cereOrderReconciliationService.updateByOrderId(reconciliation);
        //新增订单已完成消息
        CereNotice cereNotice=new CereNotice();
        cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
        cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
        cereNotice.setBuyerUserId(order.getBuyerUserId());
        cereNotice.setShopId(order.getShopId());
        cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_FINISH.getCode());
        cereNotice.setNoticeContent("订单"+order.getOrderFormid()+"已确认收货，期待您分享商品评价和购物心得");
        cereNotice.setOnly(order.getOrderId());
        cereNotice.setCreateTime(time);
        cereNotice.setIfRead(IntegerEnum.NO.getCode());
        cereNoticeService.insert(cereNotice);
    }

    @Override
    public void update(CereShopOrder cereShopOrder) throws CoBusinessException {
        cereShopOrderDAO.updateByPrimaryKeySelective(cereShopOrder);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void cancel(OrderGetByIdParam param, CereBuyerUser user) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //校验当前订单状态是否为待付款或者待发货或者待成团
        CereShopOrder cereShopOrder=cereShopOrderDAO.checkCancelState(param.getOrderId());
        if(cereShopOrder==null){
            throw new CoBusinessException(CoReturnFormat.ORDER_NOT_TAKE);
        }
        //修改订单状态为已取消
        CereShopOrder order=new CereShopOrder();
        order.setOrderId(param.getOrderId());
        order.setUpdateTime(time);
        order.setState(IntegerEnum.ORDER_STOP.getCode());
        cereShopOrderDAO.updateByPrimaryKeySelective(order);
        if(!EmptyUtils.isEmpty(cereShopOrder.getCouponId())){
            //修改平台优惠券状态为已领取
            CereBuyerCoupon cereBuyerCoupon=new CereBuyerCoupon();
            cereBuyerCoupon.setBuyerUserId(user.getBuyerUserId());
            cereBuyerCoupon.setCouponId(cereShopOrder.getCouponId());
            cereBuyerCoupon.setUpdateTime(time);
            cereBuyerCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
            cereBuyerCouponService.updateByUserIdAndCouponId(cereBuyerCoupon);
        }
        if(!EmptyUtils.isEmpty(cereShopOrder.getId())){
            //修改店铺优惠券状态为已领取
            CereBuyerShopCoupon cereBuyerShopCoupon=new CereBuyerShopCoupon();
            cereBuyerShopCoupon.setId(cereShopOrder.getId());
            cereBuyerShopCoupon.setUpdateTime(time);
            cereBuyerShopCoupon.setState(IntegerEnum.COUPON_HAVE.getCode());
            cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
        }
        if(!EmptyUtils.isLongEmpty(cereShopOrder.getShopGroupWorkId())){
            //查询当前订单是否为开团用户
            CereCollageOrder cereCollageOrder=cereCollageOrderService.findByUserId(user.getBuyerUserId(),order.getOrderId());
            if(cereCollageOrder!=null){
                //修改拼单状态为失败
                cereCollageOrder.setUpdateTime(time);
                cereCollageOrder.setState(IntegerEnum.COLLAGE_STATE_ERROR.getCode());
                cereCollageOrderService.update(cereCollageOrder);
                //查询参与该拼单的订单数据
                List<Long> ids=cereCollageOrderService.findOrderIds(cereCollageOrder.getCollageId());
                //修改拼单明细订单改为已失效,并且订单自动取消
                if(!EmptyUtils.isEmpty(ids)){
                    cereCollageOrderDetailService.updateInvalid(ids);
                    //过滤当前订单id
                    ids=ids.stream().filter(id -> !id.equals(cereShopOrder.getOrderId())).collect(Collectors.toList());
                    if(!EmptyUtils.isEmpty(ids)){
                        //批量取消订单
                        cancelBatch(ids);
                    }
                }
            }
        }
        /*if(!EmptyUtils.isEmpty(cereShopOrder.getShopSeckillId())){
            //查询秒杀活动数据
            CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(cereShopOrder.getShopSeckillId());
            if(IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())){
                //如果限量,需要更新限量库存数据
                List<CereShopSeckillDetail> seckillDetails=cereShopSeckillDetailService.findNumberDetails(cereShopOrder.getOrderId(),cereShopSeckill.getShopSeckillId());
                if(!EmptyUtils.isEmpty(seckillDetails)){
                    //更新redis限量库存
                    seckillDetails.forEach(detail -> {
                        stringRedisService.set("秒杀活动商品仅剩件数"+detail.getShopSeckillId() + detail.getSkuId(),detail.getNumber());
                    });
                    cereShopSeckillDetailService.updateBatch(seckillDetails);
                }
            }
        }
        if(!EmptyUtils.isEmpty(cereShopOrder.getShopDiscountId())){
            //查询秒杀活动数据
            CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(cereShopOrder.getShopDiscountId());
            if(IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())){
                //如果限量,需要更新限量库存数据
                List<CereShopDiscountDetail> discountDetails=cereShopDiscountDetailService.findNumberDetails(cereShopOrder.getOrderId(),cereShopDiscount.getShopDiscountId());
                if(!EmptyUtils.isEmpty(discountDetails)){
                    //更新redis限量库存
                    discountDetails.forEach(detail -> {
                        stringRedisService.set("限时折扣活动商品仅剩件数"+detail.getShopDiscountId() + detail.getSkuId(),detail.getNumber());
                    });
                    cereShopDiscountDetailService.updateBatch(discountDetails);
                }
            }
        }*/
        //更新库存数据
        /*List<CereProductSku> productSkus=cereProductSkuService.findStockNumberByOrderId(param.getOrderId());
        if(!EmptyUtils.isEmpty(productSkus)){
            //更新商品库存数量
            productSkus.forEach(sku -> {
                int stockNumber=cereProductSkuService.findStockNumberBySkuId(sku.getSkuId());
                *//*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                }*//*
                sku.setStockNumber(stockNumber+sku.getStockNumber());
                //更新redis商品库存
                //stringRedisService.set(String.valueOf(sku.getSkuId()),sku.getStockNumber());
            });
            cereProductSkuService.updateBatchSkus(productSkus);
        }*/

        //拿到商品库存数据
        List<CereOrderProduct> opList = cereOrderProductService.findByOrderIds(Collections.singletonList(order.getId()));
        for (CereOrderProduct op:opList) {
            cereStockService.rollbackStock(op.getActivityType(), op.getActivityId(), op.getProductId(), op.getSkuId(), op.getNumber());
        }

        //删除自动确认收货延时任务
        stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        if(IntegerEnum.ORDER_STAY_DILEVERY.getCode().equals(cereShopOrder.getState())
                ||IntegerEnum.YES.getCode().equals(cereShopOrder.getPaymentState())){
            //如果是待发货或者待成团（且付款）取消,需要退款,查询支付流水数据
            CerePayLog refund=cereShopOrderDAO.findPayLog(cereShopOrder.getOrderFormid());
            if(refund!=null){
                //微信支付没通,暂时直接处理业务
//                handleRefundTestWxLog(order.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo());
                /*Map<String, String> resultMap = wxPayService.orderRefund(refund.getTransactionId(), refund.getOutRefundNo(), refund.getTotalFee(), cereShopOrder.getPrice());
                if(!EmptyUtils.isEmpty(resultMap)){
                    if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                            resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                        //退款成功
                    }else if(resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())){
                        //退款失败,商户余额不足
                        throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                    }
                }*/
                PayService payService = PayFactory.getPayService(refund.getPaymentMode());
                Map<String, String> resultMap = payService.orderRefund(refund.getTransactionId(), refund.getOutRefundNo(), refund.getTotalFee(), cereShopOrder.getPrice());
                if(!EmptyUtils.isEmpty(resultMap)){
                    if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                            resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                        //退款成功 支付宝的退款是立即退款的，需要同步处理
                        if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                                IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(order.getPaymentMode())) {
                            //内部调用不会执行事务，所以通过springUtil获取service对象
                            CereShopOrderService cereShopOrderService = SpringUtil.getBean(CereShopOrderService.class);
                            cereShopOrderService.handleRefundWxLog(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo());
                        }
                    }else if(resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())){
                        //退款失败,商户余额不足
                        throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void cancelBatch(List<Long> ids) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单信息
        List<CereShopOrder> orders=cereShopOrderDAO.findByIds(ids);
        for (CereShopOrder order : orders) {
            //修改订单状态为已取消
            order.setUpdateTime(time);
            order.setState(IntegerEnum.ORDER_STOP.getCode());
            cereShopOrderDAO.updateByPrimaryKeySelective(order);
            /*if(!EmptyUtils.isEmpty(order.getShopSeckillId())){
                //查询秒杀活动数据
                CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(order.getShopSeckillId());
                if(IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())){
                    //如果限量,需要更新限量库存数据
                    List<CereShopSeckillDetail> seckillDetails=cereShopSeckillDetailService.findNumberDetails(order.getOrderId(),cereShopSeckill.getShopSeckillId());
                    if(!EmptyUtils.isEmpty(seckillDetails)){
                        //更新redis限量库存
                        seckillDetails.forEach(detail -> {
                            stringRedisService.set("秒杀活动商品仅剩件数"+detail.getShopSeckillId() + detail.getSkuId(),detail.getNumber());
                        });
                        cereShopSeckillDetailService.updateBatch(seckillDetails);
                    }
                }
            }*/
            /*if(!EmptyUtils.isEmpty(order.getShopDiscountId())){
                //查询秒杀活动数据
                CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(order.getShopDiscountId());
                if(IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())){
                    //如果限量,需要更新限量库存数据
                    List<CereShopDiscountDetail> discountDetails=cereShopDiscountDetailService.findNumberDetails(order.getOrderId(),cereShopDiscount.getShopDiscountId());
                    if(!EmptyUtils.isEmpty(discountDetails)){
                        //更新redis限量库存
                        discountDetails.forEach(detail -> {
                            stringRedisService.set("限时折扣活动商品仅剩件数"+detail.getShopDiscountId() + detail.getSkuId(),detail.getNumber());
                        });
                        cereShopDiscountDetailService.updateBatch(discountDetails);
                    }
                }
            }*/
            //更新库存数据
            /*List<CereProductSku> productSkus=cereProductSkuService.findStockNumberByOrderId(order.getOrderId());
            if(!EmptyUtils.isEmpty(productSkus)){
                //更新库存数量
                productSkus.forEach(sku -> {
                    int stockNumber=cereProductSkuService.findStockNumberBySkuId(sku.getSkuId());
                    *//*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                        stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                    }*//*
                    sku.setStockNumber(stockNumber+sku.getStockNumber());
                    //更新redis商品库存
                    //stringRedisService.set(String.valueOf(sku.getSkuId()),sku.getStockNumber());
                });
                cereProductSkuService.updateBatchSkus(productSkus);
            }*/

            // 订单库存回流
            cereStockService.rollbackStockByOrderId(order.getOrderId());

            if(IntegerEnum.YES.getCode().equals(order.getPaymentState())){
                //如果付款了,需要退款,查询支付流水数据
                CerePayLog refund=cereShopOrderDAO.findPayLog(order.getOrderFormid());
                if(refund!=null){
                    //微信支付没通,暂时直接处理业务
//                    handleRefundTestWxLog(order.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo());
                    PayService payService = PayFactory.getPayService(refund.getPaymentMode());
                    Map<String, String> resultMap = payService.orderRefund(refund.getTransactionId(), refund.getOutRefundNo(), refund.getTotalFee(), refund.getTotalFee());
                    if(!EmptyUtils.isEmpty(resultMap)){
                        if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                                resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                            //退款成功 支付宝的退款是立即退款的，需要同步处理
                            if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                                    IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(order.getPaymentMode())) {
                                //内部调用不会执行事务，所以通过springUtil获取service对象
                                CereShopOrderService cereShopOrderService = SpringUtil.getBean(CereShopOrderService.class);
                                cereShopOrderService.handleRefundWxLog(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo());
                            }
                        }else if(resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())){
                            //退款失败,商户余额不足
                            throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                        }
                    }
                }
            }
            //新增订单关闭消息
            CereNotice cereNotice=new CereNotice();
            cereNotice.setNoticeType(IntegerEnum.NOTICE_TYPE_SYSTEM.getCode());
            cereNotice.setJump(IntegerEnum.NOTICE_JUMP_ORDER.getCode());
            cereNotice.setBuyerUserId(order.getBuyerUserId());
            cereNotice.setShopId(order.getShopId());
            cereNotice.setNoticeTitle(StringEnum.NOTICE_TITLE_ORDER_CANCEL.getCode());
            cereNotice.setNoticeContent("您未在规定时间完成付款，订单"+order.getOrderFormid()+"已关闭,点击查看详情");
            cereNotice.setOnly(order.getOrderId());
            cereNotice.setCreateTime(time);
            cereNotice.setIfRead(IntegerEnum.NO.getCode());
            cereNoticeService.insert(cereNotice);
        }
    }

    @Override
    public CereShopOrder findById(Long orderId) {
        return cereShopOrderDAO.selectByPrimaryKey(orderId);
    }

    @Override
    public void updateBuyerData(Long buyerUserId, Long id) throws CoBusinessException {
        cereShopOrderDAO.updateBuyerData(buyerUserId,id);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void addComment(CommentSaveParam commentParam, CereBuyerUser user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(commentParam.getParams())){
            //查询敏感词设置
            CerePlatformSensitive sensitive=cerePlatformSensitiveService.findSensitive();
            for (OrderCommentParam param:commentParam.getParams()) {
                CereShopComment oldComment = cereShopCommentService.findComment(user.getBuyerUserId(), param.getOrderId(), param.getSkuId());
                if (oldComment != null) {
                    throw new CoBusinessException(CoReturnFormat.ALREADY_COMMENTED);
                }
                //查询店铺数据
                CereShopComment cereShopComment=cereShopCommentService.findShop(param.getProductId());
                if(cereShopComment!=null){
                    cereShopComment.setBuyerUserId(user.getBuyerUserId());
                    cereShopComment.setCreateTime(time);
                    cereShopComment.setUpdateTime(time);
                    cereShopComment.setComment(param.getComment());
                    cereShopComment.setImage(param.getImage());
                    cereShopComment.setState(IntegerEnum.NO.getCode());
                    cereShopComment.setIfSensitive(IntegerEnum.NO.getCode());
                    cereShopComment.setOrderId(param.getOrderId());
                    cereShopComment.setSkuId(param.getSkuId());
                    cereShopComment.setStar(param.getStar());
                    cereShopComment.setDes(param.getDes());
                    cereShopComment.setDelivery(param.getDelivery());
                    cereShopComment.setAttitude(param.getAttitude());
                    cereShopComment.setImpression(param.getImpression());
                    if(sensitive!=null&&IntegerEnum.YES.getCode().equals(sensitive.getState())){
                        //校验是否存在敏感词
                        String[] split = sensitive.getSensitiveWord().split(",");
                        for (String word:split) {
                            if(param.getComment().contains(word)){
                                //如果存在敏感词
                                if(IntegerEnum.SENSITIVE_STOP.getCode().equals(sensitive.getHandleMeasures())){
                                    throw new CoBusinessException(CoReturnFormat.SENSITIVE_ERROR);
                                }else {
                                    //设置评论敏感词状态为需审核
                                    cereShopComment.setIfSensitive(IntegerEnum.YES.getCode());
                                    cereShopComment.setState(IntegerEnum.YES.getCode());
                                }
                            }
                        }
                    }
                    cereShopCommentService.insert(cereShopComment);
                    //查询是否匹配关键词
                    List<CerePlatformWord> words=cereShopCommentService.findWords();
                    if(!EmptyUtils.isEmpty(words)){
                        List<CereCommentWord> collect = words.stream()
                                .map(word -> {
                                    if (cereShopComment.getComment().contains(word.getKeyWord())) {
                                        CereCommentWord cereCommentWord = new CereCommentWord();
                                        cereCommentWord.setCommentId(cereShopComment.getCommentId());
                                        cereCommentWord.setProductId(cereShopComment.getProductId());
                                        cereCommentWord.setKeyWord(word.getKeyWord());
                                        cereCommentWord.setWordId(word.getWordId());
                                        return cereCommentWord;
                                    }
                                    return null;
                                }).filter(Objects::nonNull).collect(Collectors.toList());
                        if(!EmptyUtils.isEmpty(collect)){
                            //批量插入关键词关联评论数据
                            cereCommentWordService.insertBatch(collect);
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void addToComment(CommentSaveParam saveParam, CereBuyerUser user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        if(!EmptyUtils.isEmpty(saveParam.getParams())){
            //查询敏感词设置
            CerePlatformSensitive sensitive=cerePlatformSensitiveService.findSensitive();
            for (OrderCommentParam param:saveParam.getParams()) {
                //追加评论
                CereShopComment cereShopComment=new CereShopComment();
                cereShopComment.setCommentId(param.getCommentId());
                cereShopComment.setAddComment(param.getComment());
                cereShopComment.setAddImage(param.getImage());
                cereShopComment.setAddTime(time);
                cereShopComment.setUpdateTime(time);
                //查询敏感词设置
                if(sensitive!=null&&IntegerEnum.YES.getCode().equals(sensitive.getState())){
                    //校验是否存在敏感词
                    String[] split = sensitive.getSensitiveWord().split(",");
                    for (String word:split) {
                        if(param.getComment().contains(word)){
                            //如果存在敏感词
                            if(IntegerEnum.SENSITIVE_STOP.getCode().equals(sensitive.getHandleMeasures())){
                                throw new CoBusinessException(CoReturnFormat.SENSITIVE_ERROR);
                            }else {
                                //设置评论敏感词状态为需审核
                                cereShopComment.setIfSensitive(IntegerEnum.YES.getCode());
                                cereShopComment.setState(IntegerEnum.YES.getCode());
                            }
                        }
                    }
                }
                cereShopCommentService.update(cereShopComment);
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void delete(OrderGetByIdParam param, CereBuyerUser user) throws CoBusinessException {
        //校验当前订单状态是否为已完成或者已取消
        CereShopOrder cereShopOrder=cereShopOrderDAO.checkStateFinishAndCancel(param.getOrderId());
        if(cereShopOrder==null){
            throw new CoBusinessException(CoReturnFormat.ORDER_NOT_TAKE);
        }
        //查询是否有售后数据
        List<CereOrderAfter> afters=cereShopOrderDAO.findAfterByOrderId(param.getOrderId());
        if(!EmptyUtils.isEmpty(afters)){
            //删除售后商品属性数据
            cereShopOrderDAO.deleteAfterProductSkus(afters);
            //删除售后商品数据
            cereShopOrderDAO.deleteAfterProducts(afters);
        }
        //查询订单关联的所有消息id
        List<Long> noticeIds=cereNoticeService.findIdsByOrderId(param.getOrderId());
        if(!EmptyUtils.isEmpty(noticeIds)){
            //删除消息数据
            cereNoticeService.deleteByIds(noticeIds);
            //删除客户关联消息数据
            cereNoticeService.deleteBuyerNotice(noticeIds,user.getBuyerUserId());
        }
        //删除订单商品属性数据
        cereShopOrderDAO.deleteOrderProductSkus(param.getOrderId());
        //删除订单关联所有数据
        cereShopOrderDAO.deleteAll(param.getOrderId());
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void returnExpress(CereAfterDilever dilever, CereBuyerUser user) throws CoBusinessException {
        String time=TimeUtils.yyMMddHHmmss();
        dilever.setCreateTime(time);
        cereAfterDileverService.insert(dilever);
        //修改售后单状态为退货中
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(dilever.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_RETURN_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterService.update(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","客户端操作","填写退货物流信息",dilever.getAfterId(),time);
    }

    @Override
    public List<Dilevery> getDilevery(DileveryParam param) throws CoBusinessException,Exception {
        List<Dilevery> list=new ArrayList<>();
        //查询物流查询策略
        Integer express = cerePlatformExpressService.findExpress();
        String code="";
        if(IntegerEnum.EXPRESS_100.getCode().equals(express)){
            //查询快递公司编码
            code=cerePlatformDictService.findByCompany(param.getExpress(), LongEnum.EXPRESS_100.getCode());
            //通过快递100查询物流轨迹
            List<ShippingTrace> traces = kuaiDi100Service.findTraces(code, param.getDeliverFormid());
            if(!EmptyUtils.isEmpty(traces)){
                list = traces.stream()
                        .map(a -> {
                            Dilevery dilevery = new Dilevery();
                            dilevery.setReason(a.getAcceptStation());
                            dilevery.setTime(a.getAcceptTime());
                            return dilevery;
                        }).collect(Collectors.toList());
            }
        }else if(IntegerEnum.EXPRESS_NIAO.getCode().equals(express)){
            //查询快递公司编码
            code=cerePlatformDictService.findByCompany(param.getExpress(),LongEnum.EXPRESS_NIAO.getCode());
            //通过快递鸟查询物流轨迹
            List<Object> traces = kuaiDiNiaoService.findTraces(code, param.getDeliverFormid());
        }
        return list;
    }

    @Override
    public void updateBatchStock(List<CereProductSku> skus) throws CoBusinessException {
        cereShopOrderDAO.updateBatchStock(skus);
    }

    private void addPayLog(CereShopOrder order, String time, String orderNo, String transactionId) throws Exception{
        CerePayLog payLog=new CerePayLog();
        payLog.setCreateTime(time);
        payLog.setOrderFormid(order.getOrderFormid());
        payLog.setOutTradeNo(orderNo);
        //设置退款单号
        if(orderNo.contains("XCX")){
            //小程序支付
            payLog.setOutRefundNo("XCXTK"+ TimeUtils.todayTime()+order.getOrderFormid()+order.getBuyerUserId());
        }else if(orderNo.contains("APP")){
            //APP支付
            payLog.setOutRefundNo("APPTK"+ TimeUtils.todayTime()+order.getOrderFormid()+order.getBuyerUserId());
        }
        payLog.setTransactionId(transactionId);
        payLog.setPaymentMode(order.getPaymentMode());
        payLog.setShopId(order.getShopId());
        payLog.setState(StringEnum.PAY_LOG_PAY.getCode());
        payLog.setTotalFee(order.getPrice());
        payLog.setUserId(String.valueOf(order.getBuyerUserId()));
        String payTypeStr = PayUtil.getPayTypeStr(order.getPaymentMode());
        payLog.setRemark(order.getCustomerName()+"使用" + payTypeStr + "支付了"+order.getPrice()+"元,支付订单号为："+order.getOrderFormid()
                +",支付时间为"+time);
        cerePayLogService.insert(payLog);
    }

    private void addReconciliation(CereShopOrder order, String time) throws Exception{
        CereOrderReconciliation reconciliation=new CereOrderReconciliation();
        reconciliation.setCreateTime(time);
        reconciliation.setMoney(order.getPrice());
        reconciliation.setOrderId(order.getOrderId());
        reconciliation.setState(IntegerEnum.RELATIONSHIP_FROZEN.getCode());
        reconciliation.setType(IntegerEnum.RELATIONSHIP_INCOME.getCode());
        cereOrderReconciliationService.insert(reconciliation);
    }

    private void addDistributor(ShopDistributor distributor, CereShopOrder order,String time,List<CereDistributionOrder> distributionOrders) throws Exception{
        BigDecimal hundred = new BigDecimal(100);
        if(!EmptyUtils.isEmpty(distributor.getDirectProportion())){
            //计算直接分佣佣金=订单金额*直接分佣比例
            BigDecimal divide = new BigDecimal(distributor.getDirectProportion()).divide(hundred,2,BigDecimal.ROUND_HALF_UP);
            order.setDirectDistributorMoney(order.getPrice().multiply(divide).setScale(2,BigDecimal.ROUND_HALF_UP));
            //新增直接分销订单
            CereDistributionOrder distributionOrder=new CereDistributionOrder();
            distributionOrder.setCommission(order.getDirectDistributorMoney());
            distributionOrder.setDistributorId(distributor.getDistributorId());
            distributionOrder.setDistributorName(distributor.getDistributorName());
            distributionOrder.setDistributorPhone(distributor.getDistributorPhone());
            distributionOrder.setOrderFormid(order.getOrderFormid());
            distributionOrder.setOrderId(order.getOrderId());
            distributionOrder.setPrice(order.getPrice());
            distributionOrder.setState(IntegerEnum.NO.getCode());
            distributionOrder.setType(IntegerEnum.DIRECT_TYPE.getCode());
            distributionOrder.setTransactionTime(time);
            distributionOrders.add(distributionOrder);
        }
        if(!EmptyUtils.isEmpty(distributor.getInvitees())&&!EmptyUtils.isEmpty(distributor.getIndirectProportion())){
            //计算间接分佣佣金=订单金额*间接分佣比例
            BigDecimal divide = new BigDecimal(distributor.getIndirectProportion()).divide(hundred,2,BigDecimal.ROUND_HALF_UP);
            order.setIndirectDistributorMoney(order.getPrice().multiply(divide).setScale(2,BigDecimal.ROUND_HALF_UP));
            //新增间接分佣订单
            CereDistributionOrder distributionOrder=new CereDistributionOrder();
            distributionOrder.setCommission(order.getIndirectDistributorMoney());
            distributionOrder.setDistributorId(distributor.getInvitees());
            distributionOrder.setDistributorName(distributor.getInviteesName());
            distributionOrder.setDistributorPhone(distributor.getInviteesPhone());
            distributionOrder.setOrderFormid(order.getOrderFormid());
            distributionOrder.setOrderId(order.getOrderId());
            distributionOrder.setPrice(order.getPrice());
            distributionOrder.setState(IntegerEnum.NO.getCode());
            distributionOrder.setType(IntegerEnum.IN_DIRECT_TYPE.getCode());
            distributionOrder.setTransactionTime(time);
            distributionOrders.add(distributionOrder);
        }
    }

    private void addOrder(Long parentId, OrderParam param, CereBuyerUser user,String time,
                          Map<Long, CartSku> map,CereBuyerCoupon buyerCoupon,Map<Long,CereBuyerShopCoupon> discountMap) throws CoBusinessException,Exception{
        //遍历店铺
        List<CartSku> carts=new ArrayList<>();
        List<CartSku> skus=null;
        if(!EmptyUtils.isEmpty(map)){
            //拿到所有商品数据
            skus=map.keySet().stream().map(map::get).collect(Collectors.toList());
        }
        //查询所有规格属性数据
        List<OrderProductAttribute> attributes=cereOrderProductAttributeService.findBySkus(skus);
        //查询收货地址数据
        CereBuyerReceive receive=cereBuyerReceiveService.findById(param.getReceiveId());
        if(receive!=null){
            for (OrderProductParam shop : param.getShops()) {
                //封装子订单数据
                CereShopOrder order=new CereShopOrder();
                order.setShopId(shop.getShopId());
                order.setParentId(parentId);
                order.setBuyerUserId(user.getBuyerUserId());
                order.setCreateTime(time);
                order.setUpdateTime(time);
                order.setCouponId(param.getCouponId());
                order.setReceiveName(receive.getReceiveName());
                order.setReceivePhone(receive.getReceivePhone());
                order.setReceiveAdress(receive.getReceiveAdress());
                order.setAddress(receive.getAddress());
                order.setCustomerName(user.getWechatName());
                order.setCustomerPhone(user.getPhone());
                order.setLogisticsPrice(shop.getDistribution().getDistributionPrice());
                order.setLogisticsId(shop.getDistribution().getLogisticsId());
                order.setState(IntegerEnum.ORDER_STAY_PAY.getCode());
                order.setOrderFormid(RandomStringUtil.getRandomCode(15,0));
                Long seckillId = shop.getSkus().stream().filter(obj -> obj.getPlatformSeckillId() != null).findAny().map(ProductSku::getPlatformSeckillId).orElse(null);
                Long discountId = shop.getSkus().stream().filter(obj -> obj.getPlatformDiscountId() != null).findAny().map(ProductSku::getPlatformDiscountId).orElse(null);
                order.setSeckillId(seckillId);
                order.setDiscountId(discountId);
                //定义订单优惠券金额（平台优惠金额/店铺总数+店铺优惠金额）
                BigDecimal discountPrice=new BigDecimal(0);
                if(buyerCoupon!=null){
                    discountPrice=buyerCoupon.getReduceMoney().divide(
                            new BigDecimal(param.getShops().size()),2,BigDecimal.ROUND_HALF_UP);
                    //更新平台优惠券状态为已使用
                    buyerCoupon.setUpdateTime(time);
                    buyerCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                    cereBuyerCouponService.updateState(buyerCoupon);
                    //删除redis延时任务
                    stringRedisService.delete(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                    //删除redis记录数据
                    cereRedisKeyServcice.deleteByKey(StringEnum.COUPON_OVERDUE.getCode()+"-"+buyerCoupon.getBuyerUserId()
                            +"-"+buyerCoupon.getActivityId()+"-"+buyerCoupon.getFullMoney());
                }
                //计算订单优惠金额
                if(!EmptyUtils.isEmpty(shop.getId())&&!EmptyUtils.isEmpty(discountMap)){
                    order.setId(shop.getId());
                    if(!EmptyUtils.isEmpty(discountMap.get(shop.getId()))){
                        CereBuyerShopCoupon cereBuyerShopCoupon = discountMap.get(shop.getId());
                        //更新店铺优惠券状态为已使用
                        cereBuyerShopCoupon.setUpdateTime(time);
                        cereBuyerShopCoupon.setState(IntegerEnum.COUPON_USE.getCode());
                        cereBuyerShopCouponService.updateState(cereBuyerShopCoupon);
                        //删除redis延时任务
                        stringRedisService.delete(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        cereRedisKeyServcice.deleteByKey(StringEnum.SHOP_COUPON_OVERDUE.getCode()+"-"+cereBuyerShopCoupon.getId());
                        order.setShopOperateId(cereBuyerShopCoupon.getShopOperateId());
                        if(!EmptyUtils.isEmptyBigdecimal(discountPrice)){
                            discountPrice=discountPrice.add(cereBuyerShopCoupon.getReduceMoney());
                        }else {
                            discountPrice=cereBuyerShopCoupon.getReduceMoney();
                        }
                    }
                }
                order.setDiscountPrice(discountPrice);
                if(!EmptyUtils.isEmpty(skus)){
                    //定义店铺商品总金额字段
                    final BigDecimal[] total = {new BigDecimal(0)};
                    //设置规格数量
                    skus.forEach(sku -> {
                        if(sku.getShopId().equals(shop.getShopId())){
                            total[0] = total[0].add(sku.getPrice().multiply(
                                    new BigDecimal(sku.getNumber())).setScale(2,BigDecimal.ROUND_HALF_UP));
                        }
                    });
                    //订单总金额=店铺商品总金额+运费
                    order.setOrderPrice(total[0].add(order.getLogisticsPrice()));
                    //订单支付金额=订单总金额-优惠
                    order.setPrice(order.getOrderPrice().subtract(order.getDiscountPrice()));
                    order.setPaymentState(IntegerEnum.ORDER_PAY_STAY.getCode());
                    order.setPaymentMode(param.getPaymentMode());
                    order.setRemark(shop.getRemark());
                    order.setBuyerUserId(user.getBuyerUserId());
                    //插入订单数据
                    cereShopOrderDAO.insert(order);
                    //新增订单商品数据
                    addOrderProduct(skus,order.getOrderId(),shop.getShopId(),map,carts,attributes);
                    //设置30分钟延时自动取消订单,并且释放库存
                    stringRedisService.set(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId(),param.getShops(),30*60*1000);
                    //新增延时任务记录
                    cereRedisKeyServcice.add(StringEnum.ORDER_AUTOMATIC_CANCEL.getCode()+"-"+order.getOrderId(),TimeUtils.getMinuteAfter(30));
                }
            }
            //批量更新库存数量
            //cereProductSkuService.updateBatch(skus);
            List<CartSku> dealedSkus = new ArrayList<>();
            if (!EmptyUtils.isEmpty(skus)) {
                for (CartSku sku:skus) {
                    try {
                        cereStockService.reduceStock(sku.getActivityType(), sku.getActivityId(), sku.getProductId(), sku.getSkuId(), sku.getNumber(), sku.getIfOversold());
                        dealedSkus.add(sku);
                    } catch (CoBusinessException cbe) {
                        log.error("reduceStock failed: activityType = {}, activityId = {}, productId = {}, skuId = {}, buyNumber = {}, ifOversold = {},",
                                sku.getActivityType(), sku.getActivityId(), sku.getProductId(), sku.getSkuId(), sku.getNumber(), sku.getIfOversold(), cbe);
                        for (CartSku dSku:dealedSkus) {
                            cereStockService.rollbackStock(dSku.getActivityType(), dSku.getActivityId(), dSku.getProductId(), dSku.getSkuId(), dSku.getNumber());
                        }
                    }
                }
            }
            //插入规格属性数据
            if(!EmptyUtils.isEmpty(attributes)){
                cereOrderProductAttributeService.insertBatch(attributes);
            }
        }
        if(!EmptyUtils.isEmpty(carts)){
            //删除购物车商品
            cereShopCartService.deleteSkus(carts,user.getBuyerUserId());
        }
    }

    private void addOrderProduct(List<CartSku> skus, Long orderId, Long shopId,
                                 Map<Long, CartSku> map,List<CartSku> carts,List<OrderProductAttribute> attributes) throws CoBusinessException{
        if(!EmptyUtils.isEmpty(skus)){
            for (CartSku sku:skus) {
                if(sku.getShopId().equals(shopId)){
                    if(IntegerEnum.YES.getCode().equals(sku.getSelected())){
                        //购物车商品,需要删除
                        carts.add(sku);
                    }
                    CereOrderProduct orderProduct=new CereOrderProduct();
                    orderProduct.setOrderId(orderId);
                    orderProduct.setProductId(sku.getProductId());
                    orderProduct.setSkuId(sku.getSkuId());
                    orderProduct.setImage(sku.getImage());
                    orderProduct.setNumber(sku.getNumber());
                    orderProduct.setProductName(sku.getProductName());
                    orderProduct.setProductPrice(sku.getPrice());
                    orderProduct.setSKU(sku.getSKU());
                    orderProduct.setWeight(sku.getWeight());
                    setActivityInfo(orderProduct,sku);
                    //插入订单商品明细数据
                    cereOrderProductService.insert(orderProduct);
                    //更新库存数量
                    int stockNumber=0;
                    /*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                        stockNumber = (int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                    }else {
                        stockNumber=map.get(sku.getSkuId()).getStockNumber();
                    }*/
                    stockNumber=map.get(sku.getSkuId()).getStockNumber();
                    sku.setStockNumber(stockNumber-sku.getNumber());
                    //更新redis商品库存
                    //stringRedisService.set(String.valueOf(sku.getSkuId()),stockNumber-sku.getNumber());
                    //根据规格id查询规格属性数据
                    if(!EmptyUtils.isEmpty(attributes)){
                        attributes.forEach(a -> {
                            if(sku.getSkuId().equals(a.getSkuId())){
                                a.setOrderProductId(orderProduct.getOrderProductId());
                            }
                        });
                    }
                }
            }
        }
    }

    private void setActivityInfo(CereOrderProduct op, CartSku sku) {
        if (sku.getActivityType() != null && sku.getActivityType() > 0) {
            op.setActivityType(sku.getActivityType());
            op.setActivityId(sku.getActivityId());
        } else {
            if (sku.getPlatformSeckillId() != null && sku.getPlatformSeckillId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_PLATFORM_SECKILL.getCode());
                op.setActivityId(sku.getPlatformSeckillId());
            } else if (sku.getPlatformDiscountId() != null && sku.getPlatformDiscountId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_PLATFORM_DISCOUNT.getCode());
                op.setActivityId(sku.getPlatformDiscountId());
            } else if (sku.getShopSeckillId() != null && sku.getShopSeckillId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode());
                op.setActivityId(sku.getShopSeckillId());
            } else if (sku.getShopDiscountId() != null && sku.getShopDiscountId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode());
                op.setActivityId(sku.getShopDiscountId());
            } else if (sku.getShopGroupWorkId() != null && sku.getShopGroupWorkId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_SHOP_GROUP.getCode());
                op.setActivityId(sku.getShopGroupWorkId());
            } else if (sku.getComposeId() != null && sku.getComposeId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_COMPOSE.getCode());
                op.setActivityId(sku.getComposeId());
            } else if (sku.getPriceId() != null && sku.getPriceId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_PRICE.getCode());
                op.setActivityId(sku.getPriceId());
            } else if (sku.getSceneId() != null && sku.getSceneId() > 0) {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_SCENE.getCode());
                op.setActivityId(sku.getSceneId());
            } else {
                op.setActivityType(IntegerEnum.ACTIVITY_TYPE_NORMAL.getCode());
            }
        }
    }

    private void buyNow(Settlement settlement, SettlementParam param, CereBuyerUser user, List<ProductCoupon> coupons) throws CoBusinessException{
        if(!EmptyUtils.isEmpty(param.getShops())){
            List<SettlementShop> shops=new ArrayList<>();
            boolean outerMatchAnyMarketing = IntegerEnum.YES.getCode().equals(param.getIfWork())
                    || param.getShopSeckillId() != null
                    || param.getShopDiscountId() != null;
            Map<Long, BigDecimal> shopDiscountMap = new HashMap<>();

            List<Long> shopIdList = param.getShops().stream().map(ShopProductParam::getShopId).collect(Collectors.toList());
            // 平台秒杀
            List<ShopPlatformSeckill> platformSeckillList = cerePlatformSeckillService.selectPlatformSeckillsByShopIdList(shopIdList);
            Map<Long, List<ShopPlatformSeckill>> productIdPlatformSeckillMap = platformSeckillList.stream().collect(Collectors.groupingBy(ShopPlatformSeckill::getProductId));

            // 平台折扣
            List<ShopPlatformDiscount> platformDiscountList = cerePlatformDiscountService.selectPlatformDiscountByShopIdList(shopIdList);
            Map<Long, List<ShopPlatformDiscount>> productIdPlatformDiscountMap = platformDiscountList.stream().collect(Collectors.groupingBy(ShopPlatformDiscount::getProductId));

            for (ShopProductParam shop:param.getShops()) {
                //新增转化数据
                CereShopConversion cereShopConversion=new CereShopConversion();
                cereShopConversion.setShopId(shop.getShopId());
                cereShopConversion.setCreateTime(TimeUtils.yyMMddHHmmss());
                cereShopConversion.setType(ParamEnum.CONVERSION_CHECK.getCode());
                cereShopConversionService.insert(cereShopConversion);
                boolean innerMatchAnyMarketing = outerMatchAnyMarketing || shop.getPlatformSeckillId() != null
                        || shop.getPlatformDiscountId() != null || shop.getShopSeckillId() != null
                        || shop.getShopDiscountId() != null || shop.getComposeId() != null;
                boolean matchAnyScene = false;

                shopDiscountMap.put(shop.getShopId(), BigDecimal.ZERO);
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    //将商品数量放到map中
                    Map<Long, Integer> map = shop.getSkus().stream()
                            .collect(Collectors.toMap(ProductSku::getSkuId, ProductSku::getNumber));
                    Map<Long, Integer> productIdBuyNumberMap = new HashMap<>();
                    if(!EmptyUtils.isEmpty(map)){
                        //封装商品明细数据
                        SettlementShop settlementShop=cereShopOrderDAO.findSettlementShop(shop.getShopId());
                        //查询规格信息数据
                        List<CartSku> skus=null;
                        if(IntegerEnum.YES.getCode().equals(param.getIfWork())){
                            //拼团单独购买
                            skus=cereProductSkuService.findOriginalSkuBySkus(shop.getSkus());
                        }else {
                            skus=cereProductSkuService.findSkuBySkus(shop.getSkus());
                        }
                        if (!EmptyUtils.isEmpty(skus)) {
                            //设置商品数量 和 店铺id
                            skus.forEach(sku -> {
                                sku.setNumber(map.get(sku.getSkuId()));
                                sku.setShopId(shop.getShopId());
                                Integer buyNum = productIdBuyNumberMap.getOrDefault(sku.getProductId(), 0);
                                productIdBuyNumberMap.put(sku.getProductId(), buyNum + sku.getNumber());
                            });

                            //先做活动校验 和 库存校验
                            for (CartSku sku : skus) {
                                //秒杀活动和限时折扣活动校验
                                checkActivity(param, map.get(sku.getSkuId()), sku, settlement, user, shop);

                                //校验库存
                                int stockNumber=0;
                                /*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                                }else {
                                    stockNumber=cereProductSkuService.findStockNumberBySkuId(sku.getSkuId());
                                }*/
                                stockNumber=cereProductSkuService.findStockNumberBySkuId(sku.getSkuId());
                                if(sku.getNumber()>stockNumber){
                                    //超出库存,提示‘商品已不可售，看看其他的吧’
                                    throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                                }
                            }

                            // 匹配平台秒杀
                            if (productIdPlatformSeckillMap != null && productIdPlatformSeckillMap.size() > 0) {
                                for (CartSku sku:skus) {
                                    List<ShopPlatformSeckill> list = productIdPlatformSeckillMap.get(sku.getProductId());
                                    if (CollectionUtils.isNotEmpty(list)) {
                                        innerMatchAnyMarketing = true;
                                        ShopPlatformSeckill seckill = list.get(0);
                                        sku.setPlatformSeckillId(seckill.getSeckillId());
                                        sku.setPrice(sku.getPrice().subtract(seckill.getSeckillMoney()));
                                        if (sku.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                                            sku.setPrice(BigDecimal.ZERO);
                                        }
                                        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(seckill.getIfLimit())) {
                                            if (productIdBuyNumberMap.get(sku.getProductId()) > seckill.getLimitNumber()) {
                                                throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                            }
                                        }
                                        if (seckill.getNumber() != null && productIdBuyNumberMap.get(sku.getProductId()) > seckill.getNumber()) {
                                            throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                                        }
                                    }
                                }
                                /*CerePlatformSeckill cerePlatformSeckill = cerePlatformSeckillService.findById(shop.getPlatformSeckillId());
                                if (cerePlatformSeckill != null && IntegerEnum.ACTIVITY_START.getCode().equals(cerePlatformSeckill.getState())) {
                                    List<Long> seckillProductIdList = cerePlatformSeckillService.queryProductIdListBySeckillId(cerePlatformSeckill.getSeckillId());
                                    if (CollectionUtils.isNotEmpty(seckillProductIdList)) {
                                        skus.forEach(sku -> {
                                            sku.setPlatformSeckillId(shop.getPlatformSeckillId());
                                            sku.setPrice(sku.getPrice().subtract(cerePlatformSeckill.getSeckillMoney()));
                                            if (sku.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                                                sku.setPrice(BigDecimal.ZERO);
                                            }
                                        });
                                    }
                                }*/
                            }

                            // 匹配平台折扣
                            if (productIdPlatformDiscountMap != null && productIdPlatformDiscountMap.size() > 0) {
                                for (CartSku sku:skus) {
                                    List<ShopPlatformDiscount> list = productIdPlatformDiscountMap.get(sku.getProductId());
                                    if (CollectionUtils.isNotEmpty(list)) {
                                        innerMatchAnyMarketing = true;
                                        ShopPlatformDiscount discount = list.get(0);
                                        sku.setPlatformDiscountId(discount.getDiscountId());
                                        sku.setPrice(sku.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                                        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(discount.getIfLimit())) {
                                            if (productIdBuyNumberMap.get(sku.getProductId()) > discount.getLimitNumber()) {
                                                throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                            }
                                        }
                                        if (discount.getNumber() != null && productIdBuyNumberMap.get(sku.getProductId()) > discount.getNumber()) {
                                            throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                                        }
                                    }
                                }
                                /*CerePlatformDiscount cerePlatformDiscount = cerePlatformDiscountService.findById(shop.getPlatformDiscountId());
                                if (cerePlatformDiscount != null && IntegerEnum.ACTIVITY_START.getCode().equals(cerePlatformDiscount.getState())) {
                                    skus.forEach(sku -> {
                                        sku.setPlatformDiscountId(shop.getPlatformDiscountId());
                                        sku.setPrice(sku.getPrice().multiply(cerePlatformDiscount.getDiscount()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                                    });
                                }*/
                            }

                            // 匹配商家秒杀
                            if (shop.getShopSeckillId() != null) {
                                CereShopSeckill shopSeckill = cereShopSeckillService.findById(shop.getShopSeckillId());
                                if (shopSeckill != null && IntegerEnum.TOOL_HAND.getCode().equals(shopSeckill.getState())) {
                                    for (CartSku sku:skus) {
                                        //商家秒杀针对sku，立即购买如果多个sku存在1个有秒杀，就会传shopSeckillId过来
                                        ActivityData data = cereShopSeckillDetailService.findPriceBySkuId(sku.getSkuId());
                                        if (data != null) {
                                            sku.setShopSeckillId(shop.getShopSeckillId());
                                            sku.setPrice(data.getPrice());

                                            if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(shopSeckill.getIfLimit())) {
                                                if (shopSeckill.getLimitNumber() != null &&
                                                        productIdBuyNumberMap.get(sku.getProductId()) > shopSeckill.getLimitNumber()) {
                                                    throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // 匹配商家折扣
                            if (shop.getShopDiscountId() != null) {
                                CereShopDiscount shopDiscount = cereShopDiscountService.findById(shop.getShopDiscountId());
                                if (shopDiscount != null && IntegerEnum.TOOL_HAND.getCode().equals(shopDiscount.getState())) {
                                    for (CartSku sku:skus) {
                                        ActivityData data = cereShopDiscountDetailService.findPriceBySkuId(sku.getSkuId());
                                        // 商家折扣针对sku，立即购买如果多个sku存在一个you折扣，就会传shopDiscountId过来
                                        if (data != null) {
                                            sku.setShopDiscountId(shop.getShopDiscountId());
                                            sku.setPrice(data.getPrice());

                                            if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(shopDiscount.getIfLimit())) {
                                                if (shopDiscount.getLimitNumber() != null &&
                                                        productIdBuyNumberMap.get(sku.getProductId()) > shopDiscount.getLimitNumber()) {
                                                    throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // 匹配定价捆绑
                            boolean matchCombine = false;
                            if (!innerMatchAnyMarketing) {
                                PriceCombineParam priceCombineParam = getPriceParam(Collections.singletonList(shop.getShopId()), skus.stream().map(CartSku::getProductId).collect(Collectors.toList()));
                                matchCombine = matchCombineExt(priceCombineParam,skus,settlementShop,shopDiscountMap);
                            }

                            // 匹配组合捆绑
                            if (shop.getComposeId() != null) {
                                CereShopCompose compose = cereShopComposeService.selectOnGoingByComposeId(shop.getComposeId());
                                if (compose == null || !settlementShop.getShopId().equals(compose.getShopId())) {
                                    throw new CoBusinessException(CoReturnFormat.INVALID_COMPOSE);
                                }
                                skus.forEach(sku -> sku.setComposeId(shop.getComposeId()));
                                int minNumber = skus.stream().mapToInt(CartSku::getNumber).min().getAsInt();
                                // 每个组合原来需要的价格
                                BigDecimal eachComposeOriginalPrice = skus.stream().map(CartSku::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
                                BigDecimal discountPrice = BigDecimal.ZERO;
                                //直接定价
                                if (compose.getComposeType() == 1) {
                                    discountPrice = eachComposeOriginalPrice.subtract(compose.getPromote()).multiply(new BigDecimal(minNumber));
                                } else if (compose.getComposeType() == 2) {//固定减价
                                    discountPrice = compose.getPromote().multiply(new BigDecimal(minNumber));
                                } else {//折扣
                                    BigDecimal discountPromote = BigDecimal.TEN.subtract(compose.getPromote()).divide(BigDecimal.TEN,2,BigDecimal.ROUND_HALF_UP);
                                    discountPrice = eachComposeOriginalPrice.multiply(new BigDecimal(minNumber)).multiply(discountPromote);
                                }
                                shopDiscountMap.put(shop.getShopId(), shopDiscountMap.get(shop.getShopId()).add(discountPrice));
                            }

                            // 匹配场景营销
                            if (!innerMatchAnyMarketing && !matchCombine) {
                                List<CereShopScene> sceneList = cereShopSceneService.selectOnGoingMarketingByShopId(shop.getShopId());
                                if (CollectionUtils.isNotEmpty(sceneList)) {
                                    for (CereShopScene scene:sceneList) {
                                        Calendar cal = Calendar.getInstance();
                                        boolean matched = matchScene(scene, user, cal);
                                        // 满足场景营销的条件
                                        if (matched) {
                                            CereShopSceneMember sceneMember = cereShopSceneMemberService.selectSceneMemberList(scene.getSceneId(), user.getMemberLevelId());
                                            if (sceneMember != null) {
                                                skus.forEach(sku -> sku.setSceneId(scene.getSceneId()));
                                                //设置场景营销id，订单提交的时候，使用到该参数
                                                if (IntegerEnum.YES.getCode().equals(sceneMember.getIfFreeShipping())) {
                                                    Distribution sceneDistribution = new Distribution();
                                                    sceneDistribution.setDistributionName("场景营销-包邮");
                                                    sceneDistribution.setDistributionPrice(BigDecimal.ZERO);
                                                    sceneDistribution.setLogisticsId(-1L);
                                                    settlementShop.setDistribution(sceneDistribution);
                                                }
                                                //场景营销打折
                                                BigDecimal sceneDiscount = sceneMember.getDiscount();
                                                /*BigDecimal originalPrice = BigDecimal.ZERO;
                                                for (CartSku sku:skus) {
                                                    originalPrice = originalPrice.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                                }*/
                                                skus.forEach(sku -> sku.setPrice(sku.getPrice().multiply(sceneDiscount).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP)));
                                                //shopDiscountMap.put(shop.getShopId(), shopDiscountMap.get(shop.getShopId()).add(originalPrice.multiply(discountPromote)));
                                                matchAnyScene = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            // 匹配会员价
                            if (!innerMatchAnyMarketing && !matchCombine && !matchAnyScene) {
                                for (CartSku sku:skus) {
                                    CereProductMember productMember = cereProductMemberService.selectProductMember(user.getMemberLevelId(), sku.getProductId(), sku.getSkuId());
                                    if (productMember != null) {
                                        sku.setUseMember(true);
                                        if (IntegerEnum.MEMBER_PRODUCT_MODE_PRICE.getCode().equals(productMember.getMode())) {
                                            if (sku.getPrice().compareTo(productMember.getPrice()) > 0) {
                                                sku.setPrice(productMember.getPrice());
                                                /*BigDecimal discountPrice = sku.getPrice().subtract(productMember.getPrice()).multiply(new BigDecimal(sku.getNumber()));
                                                shopDiscountMap.put(shop.getShopId(), shopDiscountMap.get(shop.getShopId()).add(discountPrice));*/
                                            }
                                        } else {
                                            sku.setPrice(sku.getPrice().multiply(productMember.getPrice()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                                            /*BigDecimal discountPromote = BigDecimal.TEN.subtract(productMember.getPrice()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP);
                                            BigDecimal discountPrice = sku.getPrice().multiply(new BigDecimal(sku.getNumber())).multiply(discountPromote);
                                            shopDiscountMap.put(shop.getShopId(), shopDiscountMap.get(shop.getShopId()).add(discountPrice));*/
                                        }
                                    }
                                }
                            }

                            BigDecimal total=new BigDecimal(0);
                            for (CartSku sku : skus) {
                                //设置规格属性值数组
                                sku.setValues(EmptyUtils.getImages(sku.getValue()));
                                //封装商品数据
                                sku.setTotal(new BigDecimal(map.get(sku.getSkuId())).multiply(sku.getPrice()));
                                total=total.add(sku.getTotal());
                                sku.setSelected(IntegerEnum.YES.getCode());
                                //封装优惠券数据
                                setSettlementCoupons(param,shop,user.getBuyerUserId(),sku,settlementShop,settlement
                                        ,settlement.getCoupons(),settlementShop.getShopCoupons());
                            }

                            settlementShop.setNumber(skus.stream().mapToInt(CartSku::getNumber).sum());
                            settlementShop.setSkus(skus);
                            settlementShop.setTotal(total.subtract(shopDiscountMap.get(shop.getShopId())).setScale(2, BigDecimal.ROUND_HALF_UP));

                            // 匹配场景营销时，可能已经有物流方案了
                            if (settlementShop.getDistribution() == null) {
                                //查询店铺物流方案
                                List<CereOrderLogistics> logistics=cereOrderLogisticsService.findLogistics(shop.getShopId());
                                //计算物流费用
                                Distribution distribution=setLogisticPrice(logistics,skus,settlement.getReceive(),map);
                                settlementShop.setDistribution(distribution);
                            }

                            shops.add(settlementShop);
                        }
                    }
                }
            }

            settlement.setShops(shops);
        }
    }

    private void shopCart(Settlement settlement, SettlementParam param, CereBuyerUser user, List<ProductCoupon> coupons) throws CoBusinessException {
        //定义是否提示
        AtomicBoolean flag= new AtomicBoolean(false);
        //封装商品明细数据
        if(!EmptyUtils.isEmpty(param.getShops())){
            List<SettlementShop> invalidShops=new ArrayList<>();
            List<SettlementShop> collect=new ArrayList<>();

            //查找所有的活动相关信息
            //cerePlatformSeckillService.
            List<Long> shopIdList = new ArrayList<>();
            for (ShopProductParam shop : param.getShops()) {
                shopIdList.add(shop.getShopId());
            }

            // 平台秒杀
            List<ShopPlatformSeckill> platformSeckillList = cerePlatformSeckillService.selectPlatformSeckillsByShopIdList(shopIdList);
            Map<Long, List<ShopPlatformSeckill>> productIdPlatformSeckillMap = platformSeckillList.stream().collect(Collectors.groupingBy(ShopPlatformSeckill::getProductId));

            // 平台折扣
            List<ShopPlatformDiscount> platformDiscountList = cerePlatformDiscountService.selectPlatformDiscountByShopIdList(shopIdList);
            Map<Long, List<ShopPlatformDiscount>> productIdPlatformDiscountMap = platformDiscountList.stream().collect(Collectors.groupingBy(ShopPlatformDiscount::getProductId));

            // 商家秒杀
            List<ShopBusinessSeckill> seckillList = cereShopSeckillService.selectByShopIdList(shopIdList);
            Map<Long, List<ShopBusinessSeckill>> skuIdSeckillMap = seckillList.stream().collect(Collectors.groupingBy(ShopBusinessSeckill::getSkuId));

            // 商家折扣
            List<ShopBusinessDiscount> discountList = cereShopDiscountService.selectByShopIdList(shopIdList);
            Map<Long, List<ShopBusinessDiscount>> skuIdDiscountMap = discountList.stream().collect(Collectors.groupingBy(ShopBusinessDiscount::getSkuId));

            // 定价捆绑
            Map<Long, Map<Long, List<CerePriceRule>>> priceMap = cereShopPriceService.selectPriceMap(shopIdList);

            // 组合捆绑
            List<CereShopComposeDTO> composeDTOList = cereShopComposeService.selectByShopIdList(shopIdList);
            Map<Long, List<CereShopComposeDTO>> shopIdComposeMap = composeDTOList.stream().collect(Collectors.groupingBy(CereShopComposeDTO::getShopId));

            //商家的场景营销 优惠价
            Map<Long, DiscountDescDTO> shopIdSceneDiscountMap = new HashMap<>();
            //商家的会员价优惠
            Map<Long, DiscountDescDTO> memberDiscountPriceMap = new HashMap<>();
            //商家匹配场景营销是否包邮
            Map<Long, Boolean> sceneMarketFreeShipingMap = new HashMap<>();

            for (ShopProductParam shop : param.getShops()) {

                //计算某个定价id优惠了多少钱
                Map<Long, DiscountDescDTO> priceDiscountMap = new HashMap<>();
                //计算某个组合捆绑id优惠了多少钱
                Map<Long, DiscountDescDTO> composeDiscountMap = new HashMap<>();

                //查询店铺信息
                SettlementShop settlementShop = cereShopOrderDAO.findSettlementShop(shop.getShopId());
                settlementShop.setShopId(shop.getShopId());
                List<CartSku> invalidSkus=new ArrayList<>();
                int invalidNumber = 0;
                BigDecimal total = new BigDecimal(0);
                //sku后面接的 优惠信息
                Map<Long, List<String>> skuDiscountInfoMap = new HashMap<>();
                settlementShop.setSkuDiscountInfoMap(skuDiscountInfoMap);

                Map<Long,CartSku> map=new HashMap<>();
                if(!EmptyUtils.isEmpty(shop.getSkus())){
                    //查询当前店铺所有购买商品的库存数据
                    List<CartSku> productSkus=cereProductSkuService.findStockNumberBySkus(shop.getSkus());
                    if(!EmptyUtils.isEmpty(productSkus)){
                        productSkus.forEach(sku -> {
                            map.put(sku.getSkuId(),sku);
                        });
                    }
                    //将商品数量放到map中
                    Map<Long, Integer> numberMap = shop.getSkus().stream()
                            .collect(Collectors.toMap(ProductSku::getSkuId, ProductSku::getNumber));

                    Map<Long, Integer> productIdBuyNumberMap = new HashMap<>();

                    Set<Long> usedSkuIdSet = new HashSet<>();

                    if(!EmptyUtils.isEmpty(numberMap)){
                        //查询规格信息数据
                        List<CartSku> skus=cereProductSkuService.findStockNumberBySkus(shop.getSkus());
                        if(!EmptyUtils.isEmpty(skus)){
                            //设置商品数量
                            skus.forEach(sku -> {
                                sku.setNumber(numberMap.get(sku.getSkuId()));
                                sku.setShopId(shop.getShopId());
                                Integer buyNum = productIdBuyNumberMap.getOrDefault(sku.getProductId(), 0);
                                productIdBuyNumberMap.put(sku.getProductId(), buyNum + sku.getNumber());
                            });

                            for (CartSku sku : skus) {
                                //秒杀活动和限时折扣活动校验
                                checkActivity(param, numberMap.get(sku.getSkuId()), sku, settlement, user, shop);
                                //设置规格属性值数组
                                sku.setValues(EmptyUtils.getImages(sku.getValue()));
                            }

                            for (CartSku sku : skus) {
                                List<ShopPlatformSeckill> shopPlatormSeckillList = productIdPlatformSeckillMap.get(sku.getProductId());
                                List<ShopPlatformDiscount> shopPlatformDiscountList = productIdPlatformDiscountMap.get(sku.getProductId());
                                List<ShopBusinessSeckill> shopBusinessSeckillList = skuIdSeckillMap.get(sku.getSkuId());
                                List<ShopBusinessDiscount> shopBusinessDiscountList = skuIdDiscountMap.get(sku.getSkuId());
                                BigDecimal num = new BigDecimal(sku.getNumber());

                                //匹配平台秒杀
                                if (CollectionUtils.isNotEmpty(shopPlatormSeckillList)) {
                                    ShopPlatformSeckill seckill = shopPlatormSeckillList.get(0);

                                    usedSkuIdSet.add(sku.getSkuId());
                                    sku.setPlatformSeckillId(shopPlatormSeckillList.get(0).getSeckillId());
                                    if (sku.getPrice().compareTo(seckill.getSeckillMoney()) >= 0) {
                                        sku.setPrice(sku.getPrice().subtract(seckill.getSeckillMoney()).setScale(2, BigDecimal.ROUND_HALF_UP));

                                        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(seckill.getIfLimit())) {
                                            if (productIdBuyNumberMap.get(sku.getProductId()) > seckill.getLimitNumber()) {
                                                throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                            }
                                        }
                                        if (seckill.getNumber() != null && productIdBuyNumberMap.get(sku.getProductId()) > seckill.getNumber()) {
                                            throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                                        }
                                    }
                                }

                                //匹配平台折扣
                                if (CollectionUtils.isNotEmpty(shopPlatformDiscountList)) {
                                    ShopPlatformDiscount discount = shopPlatformDiscountList.get(0);

                                    usedSkuIdSet.add(sku.getSkuId());
                                    sku.setPlatformDiscountId(shopPlatformDiscountList.get(0).getDiscountId());
                                    sku.setPrice(sku.getPrice().multiply(discount.getDiscount()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));

                                    if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(discount.getIfLimit())) {
                                        if (productIdBuyNumberMap.get(sku.getProductId()) > discount.getLimitNumber()) {
                                            throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                        }
                                    }
                                    if (discount.getNumber() != null && productIdBuyNumberMap.get(sku.getProductId()) > discount.getNumber()) {
                                        throw new CoBusinessException(CoReturnFormat.PRODUCT_STOCK_ERROR);
                                    }
                                }

                                //匹配商家秒杀
                                if (CollectionUtils.isNotEmpty(shopBusinessSeckillList)) {
                                    ShopBusinessSeckill seckill = shopBusinessSeckillList.get(0);

                                    usedSkuIdSet.add(sku.getSkuId());
                                    sku.setShopSeckillId(shopBusinessSeckillList.get(0).getSeckillId());
                                    sku.setPrice(seckill.getSeckillPrice());

                                    if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(seckill.getIfLimit())) {
                                        if (seckill.getLimitNumber() != null && productIdBuyNumberMap.get(sku.getProductId()) > seckill.getLimitNumber()) {
                                            throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                        }
                                    }
                                }

                                //匹配商家折扣
                                if (CollectionUtils.isNotEmpty(shopBusinessDiscountList)) {
                                    ShopBusinessDiscount discount = shopBusinessDiscountList.get(0);

                                    usedSkuIdSet.add(sku.getSkuId());
                                    sku.setShopDiscountId(discount.getDiscountId());
                                    sku.setPrice(discount.getPrice());

                                    if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(discount.getIfLimit())) {
                                        if (discount.getLimitNumber() != null && productIdBuyNumberMap.get(sku.getProductId()) > discount.getLimitNumber()) {
                                            throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR);
                                        }
                                    }
                                }
                            }

                            List<CartSku> skuList = skus.stream().filter(sku -> !usedSkuIdSet.contains(sku.getSkuId())).collect(Collectors.toList());

                            // 匹配定价捆绑
                            matchPriceCombine(shop.getShopId(), skus, skuList, settlementShop, usedSkuIdSet, priceDiscountMap);

                            skuList = skus.stream().filter(sku -> !usedSkuIdSet.contains(sku.getSkuId())).collect(Collectors.toList());

                            // 匹配组合捆绑
                            matchComposeCombine(shop.getShopId(), skuList, usedSkuIdSet, shopIdComposeMap, composeDiscountMap, numberMap);

                            skuList = skus.stream().filter(sku -> !usedSkuIdSet.contains(sku.getSkuId())).collect(Collectors.toList());

                            // 匹配场景营销
                            matchSceneMarket(shop.getShopId(), user, skuList, usedSkuIdSet, shopIdSceneDiscountMap, sceneMarketFreeShipingMap);

                            skuList = skuList.stream().filter(sku -> !usedSkuIdSet.contains(sku.getSkuId())).collect(Collectors.toList());

                            //匹配会员价
                            matchMember(shop.getShopId(), user, skuList, usedSkuIdSet, memberDiscountPriceMap);

                            for (CartSku sku : skus) {
                                //秒杀活动和限时折扣活动校验
                                checkActivity(param,numberMap.get(sku.getSkuId()),sku,settlement,user,shop);
                                //设置规格属性值数组
                                sku.setValues(EmptyUtils.getImages(sku.getValue()));

                                sku.setTotal(new BigDecimal(sku.getNumber()).multiply(sku.getPrice()));
                                sku.setSelected(IntegerEnum.YES.getCode());
                                //定义是否需要查询优惠券标识,默认需要
                                setSettlementCoupons(param,shop,user.getBuyerUserId(),sku,settlementShop,settlement,
                                        settlement.getCoupons(),settlementShop.getShopCoupons());
                                //校验库存
                                int stockNumber=0;
                                /*if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                                    stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                                }else {
                                    stockNumber= map.get(sku.getSkuId()).getStockNumber();
                                }*/
                                stockNumber= map.get(sku.getSkuId()).getStockNumber();
                                if(sku.getNumber()>stockNumber){
                                    //叠加商品件数
                                    invalidNumber+=sku.getNumber();
                                    flag.set(true);
                                    invalidSkus.add(sku);
                                }
                                //total = total.add(sku.getTotal());
                            }
                            //settlementShop.setSkus(skus);
                            settlementShop.setNumber(skus.stream().mapToInt(CartSku::getNumber).sum());

                            List<CartSku> sortSkuList = new ArrayList<>();

                            if (priceDiscountMap.size() > 0) {
                                for (Long id:priceDiscountMap.keySet()) {
                                    DiscountDescDTO value = priceDiscountMap.get(id);

                                    List<CartSku> tmpSkuList = skus.stream().filter(sku -> sku.getPriceId() != null && sku.getPriceId().equals(id)).collect(Collectors.toList());
                                    sortSkuList.addAll(tmpSkuList);

                                    for (CartSku sku:tmpSkuList) {
                                        total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                    }
                                    total = total.subtract(value.getDiscountTotal());
                                    skuDiscountInfoMap.put(tmpSkuList.get(tmpSkuList.size() - 1).getSkuId(), Arrays.asList(String.format("定价捆绑  %.2f元任选%d件 x %d", value.getComposePrice(), value.getComposeNum(), value.getDiscountNum()), "优惠￥" + value.getDiscountTotal()));
                                }
                            }

                            if (composeDiscountMap.size() > 0) {
                                for (Long id:composeDiscountMap.keySet()) {
                                    DiscountDescDTO value = composeDiscountMap.get(id);

                                    List<CartSku> tmpSkuList = skus.stream().filter(sku -> sku.getComposeId() != null && sku.getComposeId().equals(id)).collect(Collectors.toList());
                                    sortSkuList.addAll(tmpSkuList);

                                    for (CartSku sku:tmpSkuList) {
                                        total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                    }
                                    total = total.subtract(value.getDiscountTotal());
                                    skuDiscountInfoMap.put(tmpSkuList.get(tmpSkuList.size() - 1).getSkuId(), Arrays.asList(String.format("组合捆绑  x %d", value.getDiscountNum()), "优惠￥" + value.getDiscountTotal()));
                                }
                            }

                            if (shopIdSceneDiscountMap.get(shop.getShopId()) != null) {
                                DiscountDescDTO value = shopIdSceneDiscountMap.get(shop.getShopId());

                                List<CartSku> tmpSkuList = skus.stream().filter(sku -> sku.getSceneId() != null).collect(Collectors.toList());
                                /*sortSkuList.addAll(tmpSkuList);

                                for (CartSku sku:tmpSkuList) {
                                    total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                }
                                total = total.subtract(value.getDiscountTotal());*/
                                skuDiscountInfoMap.put(tmpSkuList.get(tmpSkuList.size() - 1).getSkuId(), Arrays.asList(String.format("场景营销  %.1f折 x %d", value.getDiscount(), value.getDiscountNum()), "优惠￥" + value.getDiscountTotal()));
                            }

                            if (memberDiscountPriceMap.get(shop.getShopId()) != null) {
                                DiscountDescDTO value = memberDiscountPriceMap.get(shop.getShopId());

                                List<CartSku> tmpSkuList = skus.stream().filter(CartSku::isUseMember).collect(Collectors.toList());
                                sortSkuList.addAll(tmpSkuList);

                                for (CartSku sku:tmpSkuList) {
                                    total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                }
                                total = total.subtract(value.getDiscountTotal());
                                skuDiscountInfoMap.put(tmpSkuList.get(tmpSkuList.size() - 1).getSkuId(), Arrays.asList(String.format("会员价 x %d", value.getDiscountNum()), "优惠￥" + value.getDiscountTotal()));
                            }

                            //剩下的就是普通的商品
                            skus.removeAll(sortSkuList);
                            for (CartSku sku:skus) {
                                total = total.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                            }
                            sortSkuList.addAll(skus);

                            settlementShop.setSkus(sortSkuList);

                            if(flag.get()){
                                SettlementShop settlementShop1=new SettlementShop();
                                settlementShop1.setShopId(shop.getShopId());
                                settlementShop1.setNumber(invalidNumber);
                                settlementShop1.setSkus(invalidSkus);
                                invalidShops.add(settlementShop1);
                            } else {
                                //查询店铺物流方案
                                List<CereOrderLogistics> logistics=cereOrderLogisticsService.findLogistics(shop.getShopId());
                                //计算物流费用 如果场景营销中有包邮，则把包邮的sku过滤掉
                                List<CartSku> logisticsSkuList = sortSkuList;
                                if (sceneMarketFreeShipingMap.get(shop.getShopId()) != null) {
                                    logisticsSkuList = sortSkuList.stream().filter(sku -> sku.getSceneId() == null).collect(Collectors.toList());
                                }
                                Distribution distribution=setLogisticPrice(logistics,logisticsSkuList,settlement.getReceive(),numberMap);
                                settlementShop.setDistribution(distribution);
                            }
                        }
                    }
                }
                settlementShop.setTotal(total.setScale(2, BigDecimal.ROUND_HALF_UP));
                collect.add(settlementShop);
            }
            settlement.setShops(collect);
            settlement.setInvalidShops(invalidShops);
            if(flag.get()){
                //超出库存,提示‘存在无效商品，请重新选择’
                throw new CoBusinessException(CoReturnFormat.HAVE_INVALID_PRODUCT,settlement);
            }
        }
    }

    /**
     * 匹配定价捆绑
     * @param shopId
     * @param skus
     * @param skuList
     * @param settlementShop
     * @param usedSkuIdSet
     * @param priceDiscountMap
     */
    private void matchPriceCombine(Long shopId, List<CartSku> skus, List<CartSku> skuList, SettlementShop settlementShop, Set<Long> usedSkuIdSet, Map<Long, DiscountDescDTO> priceDiscountMap) {
        if (CollectionUtils.isNotEmpty(skuList)) {
            PriceCombineParam priceCombineParam = getPriceParam(Collections.singletonList(shopId), skus.stream().map(CartSku::getProductId).collect(Collectors.toList()));
            matchCombineExtNew(priceCombineParam, skus, skuList, settlementShop, usedSkuIdSet, priceDiscountMap);
        }
    }

    /**
     * 匹配组合捆绑
     * @param shopId
     * @param skuList
     * @param usedSkuIdSet
     * @param shopIdComposeMap
     * @param composeDiscountMap
     * @param numberMap
     */
    private void matchComposeCombine(Long shopId, List<CartSku> skuList, Set<Long> usedSkuIdSet, Map<Long, List<CereShopComposeDTO>> shopIdComposeMap,
                                     Map<Long, DiscountDescDTO> composeDiscountMap, Map<Long, Integer> numberMap) {
        if (CollectionUtils.isNotEmpty(skuList)) {
            List<CereShopComposeDTO> shopComposeList = shopIdComposeMap.get(shopId);
            skuList = skuList.stream().sorted((o1, o2) -> o2.getNumber().compareTo(o1.getNumber())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(shopComposeList)) {
                // composeId - 列表
                Map<Long, List<CereShopComposeDTO>> composeIdListMap = shopComposeList.stream().collect(Collectors.groupingBy(CereShopComposeDTO::getComposeId));
                for (Map.Entry<Long, List<CereShopComposeDTO>> entry:composeIdListMap.entrySet()) {
                    Long composeId = entry.getKey();
                    List<Long> productIdList = entry.getValue().stream().map(CereShopComposeDTO::getProductId).distinct().collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(productIdList)) {
                        List<Long> tmpUsedSkuIdList = new ArrayList<>();
                        for (Long productId:productIdList) {
                            for (CartSku cartSku:skuList) {
                                if (!tmpUsedSkuIdList.contains(cartSku.getSkuId()) && cartSku.getProductId().equals(productId)) {
                                    tmpUsedSkuIdList.add(cartSku.getSkuId());
                                    break;
                                }
                            }
                        }
                        //说明数量匹配上了
                        if (tmpUsedSkuIdList.size() == productIdList.size()) {
                            usedSkuIdSet.addAll(tmpUsedSkuIdList);
                            DiscountDescDTO discountDescDTO = new DiscountDescDTO();

                            Long lastSkuId = tmpUsedSkuIdList.get(tmpUsedSkuIdList.size() -1);
                            //由于数量是由大到小排序，所以最后一个的数量代表了 可匹配次数
                            Integer lastNumber = numberMap.get(lastSkuId);

                            CereShopComposeDTO composeDTO = entry.getValue().get(0);
                            int composeType = composeDTO.getComposeType();

                            BigDecimal oldPrice = BigDecimal.ZERO;
                            for(CartSku sku:skuList) {
                                if (tmpUsedSkuIdList.contains(sku.getSkuId())) {
                                    sku.setComposeId(composeId);
                                    oldPrice = oldPrice.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                                }
                            }
                            discountDescDTO.setDiscountNum(lastNumber);
                            if (composeType == 1) {
                                BigDecimal price = composeDTO.getPromote().multiply(new BigDecimal(lastNumber));
                                discountDescDTO.setDiscountTotal(oldPrice.subtract(price));
                            } else if (composeType == 2){
                                BigDecimal discountPrice = composeDTO.getPromote().multiply(new BigDecimal(lastNumber));
                                discountDescDTO.setDiscountTotal(discountPrice);
                            } else {
                                BigDecimal discountPrice = BigDecimal.ZERO;
                                for(CartSku sku:skuList) {
                                    if (tmpUsedSkuIdList.contains(sku.getSkuId())) {
                                        discountPrice = discountPrice.add(sku.getPrice().multiply(new BigDecimal(lastNumber)).multiply(BigDecimal.TEN.subtract(composeDTO.getPromote())).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                                    }
                                }
                                discountDescDTO.setDiscountTotal(discountPrice);
                            }
                            composeDiscountMap.put(composeId, discountDescDTO);

                            skuList = skuList.stream().filter(tmpSku -> tmpUsedSkuIdList.contains(tmpSku.getSkuId())).collect(Collectors.toList());
                        }
                    }
                }
            }
        }
    }

    /**
     * 匹配场景营销
     * @param shopId
     * @param user
     * @param skuList
     * @param usedSkuIdSet
     * @param shopIdSceneDiscountMap
     * @param sceneMarketFreeShipingMap
     */
    private void matchSceneMarket(Long shopId, CereBuyerUser user, List<CartSku> skuList, Set<Long> usedSkuIdSet, Map<Long, DiscountDescDTO> shopIdSceneDiscountMap, Map<Long, Boolean> sceneMarketFreeShipingMap) {
        BigDecimal sceneDiscount = null;
        //Distribution sceneDistribution = null;
        Long matchedSceneId = null;
        if (CollectionUtils.isNotEmpty(skuList)) {
            List<CereShopScene> sceneList = cereShopSceneService.selectOnGoingMarketingByShopId(shopId);
            if (CollectionUtils.isNotEmpty(sceneList)) {
                for (CereShopScene scene:sceneList) {
                    Calendar cal = Calendar.getInstance();
                    boolean matched = matchScene(scene, user, cal);
                    // 满足场景营销的条件
                    if (matched) {
                        CereShopSceneMember sceneMember = cereShopSceneMemberService.selectSceneMemberList(scene.getSceneId(), user.getMemberLevelId());
                        if (sceneMember != null) {
                            if (IntegerEnum.YES.getCode().equals(sceneMember.getIfFreeShipping())) {
                                //sceneDistribution = new Distribution();
                                //sceneDistribution.setDistributionName("场景营销-包邮");
                                //sceneDistribution.setDistributionPrice(BigDecimal.ZERO);
                                //sceneDistribution.setLogisticsId(-1L);
                                sceneMarketFreeShipingMap.put(shopId, true);
                            }
                            //场景营销打折
                            sceneDiscount = sceneMember.getDiscount();
                            matchedSceneId = scene.getSceneId();
                            break;
                        }
                    }
                }
            }

            if (sceneDiscount != null) {
                BigDecimal sceneTotalPrice = BigDecimal.ZERO;
                DiscountDescDTO discountDescDTO = new DiscountDescDTO();
                discountDescDTO.setDiscount(sceneDiscount);
                for (CartSku sku:skuList) {
                    sceneTotalPrice = sceneTotalPrice.add(sku.getPrice().multiply(new BigDecimal(sku.getNumber())));
                    usedSkuIdSet.add(sku.getSkuId());
                    sku.setSceneId(matchedSceneId);
                    sku.setPrice(sku.getPrice().multiply(sceneDiscount).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                    discountDescDTO.setDiscountNum(discountDescDTO.getDiscountNum() + sku.getNumber());
                }
                BigDecimal sceneDiscountPrice = sceneTotalPrice.multiply(BigDecimal.TEN.subtract(sceneDiscount)).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP);
                discountDescDTO.setDiscountTotal(sceneDiscountPrice);
                shopIdSceneDiscountMap.put(shopId, discountDescDTO);
            }
            /*if (sceneDiscount != null) {
                for (CartSku sku:skuList) {
                    usedSkuIdSet.add(sku.getSkuId());
                    sku.setSceneId(matchedSceneId);
                    sku.setPrice(sku.getPrice().multiply(sceneDiscount).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                }
            }*/
        }
    }

    /**
     * 匹配会员价
     * @param shopId
     * @param user
     * @param skuList
     * @param usedSkuIdList
     * @param memberDiscountPriceMap
     */
    private void matchMember(Long shopId, CereBuyerUser user, List<CartSku> skuList, Set<Long> usedSkuIdList, Map<Long, DiscountDescDTO> memberDiscountPriceMap) {
        if (CollectionUtils.isNotEmpty(skuList)) {
            //BigDecimal total = BigDecimal.ZERO;
            //DiscountDescDTO discountDescDTO = new DiscountDescDTO();
            for (CartSku sku:skuList) {
                CereProductMember productMember = cereProductMemberService.selectProductMember(user.getMemberLevelId(), sku.getProductId(), sku.getSkuId());
                if (productMember != null) {
                    usedSkuIdList.add(sku.getSkuId());
                    sku.setUseMember(true);
                    //BigDecimal num = new BigDecimal(sku.getNumber());
                    if (IntegerEnum.MEMBER_PRODUCT_MODE_PRICE.getCode().equals(productMember.getMode())) {
                        sku.setPrice(productMember.getPrice());
                        //sku的价格必须 > 会员价
                        /*if (sku.getPrice().compareTo(productMember.getPrice()) > 0) {
                            total = total.add(sku.getPrice().subtract(productMember.getPrice()).multiply(num));
                        }*/
                    } else {
                        sku.setPrice(sku.getPrice().multiply(productMember.getPrice()).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));
                        // 打折默认折扣都是正常的 价格 x 数量 x (10-折扣) / 10 就是优惠价
                        /*total = total.add(sku.getPrice().multiply(num).multiply(BigDecimal.TEN.subtract(productMember.getPrice())).divide(BigDecimal.TEN, 2, BigDecimal.ROUND_HALF_UP));*/
                    }
                    //discountDescDTO.setDiscountNum(discountDescDTO.getDiscountNum() + sku.getNumber());
                }
            }
            /*if (!total.equals(BigDecimal.ZERO)) {
                discountDescDTO.setDiscountTotal(total);
                memberDiscountPriceMap.put(shopId, discountDescDTO);
            }*/
        }
    }

    private void setSettlementCoupons(SettlementParam param, ShopProductParam shopProductParam, Long buyerUserId, CartSku sku,
                                      SettlementShop settlementShop, Settlement settlement,
                                      List<ProductCoupon> coupons,List<ProductCoupon> shopCoupons){
        if(EmptyUtils.isEmpty(coupons)){
            coupons=new ArrayList<>();
        }
        if(EmptyUtils.isEmpty(shopCoupons)){
            shopCoupons=new ArrayList<>();
        }
        boolean flag=true;
        if(!EmptyUtils.isLongEmpty(param.getShopDiscountId())||!EmptyUtils.isLongEmpty(param.getShopSeckillId())
                ||!EmptyUtils.isLongEmpty(shopProductParam.getPlatformDiscountId())||!EmptyUtils.isLongEmpty(shopProductParam.getPlatformSeckillId())){
            //营销活动购买
            if(!EmptyUtils.isLongEmpty(param.getShopDiscountId())){
                //限时折扣活动购买,查询限时折扣活动数据
                CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(param.getShopDiscountId());
                if(IntegerEnum.NO.getCode().equals(cereShopDiscount.getIfAdd())){
                    //不允许优惠叠加
                    flag=false;
                }
            }else if(!EmptyUtils.isLongEmpty(param.getShopSeckillId())){
                //秒杀活动购买,查询秒杀活动数据
                CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(param.getShopSeckillId());
                if(IntegerEnum.NO.getCode().equals(cereShopSeckill.getIfAdd())){
                    //允许优惠叠加
                    flag=false;
                }
            }else if(!EmptyUtils.isLongEmpty(shopProductParam.getPlatformDiscountId())){
                //平台限时折扣活动购买
                CerePlatformDiscount cerePlatformDiscount=cerePlatformDiscountService.findById(shopProductParam.getPlatformDiscountId());
                if(IntegerEnum.NO.getCode().equals(cerePlatformDiscount.getIfAdd())){
                    //允许优惠叠加
                    flag=false;
                }
            }else if(!EmptyUtils.isLongEmpty(shopProductParam.getPlatformSeckillId())){
                //平台秒杀活动购买
                CerePlatformSeckill cerePlatformSeckill = cerePlatformSeckillService.findById(shopProductParam.getPlatformSeckillId());
                if(IntegerEnum.NO.getCode().equals(cerePlatformSeckill.getIfAdd())){
                    //允许优惠叠加
                    flag=false;
                }
            }
        }
        if(flag){
            //根据商品id、商品价格查询满足条件的平台优惠券
            List<ProductCoupon> productCoupons=cereBuyerCouponService.findCouponByProduct(sku.getTotal(),
                    buyerUserId,sku.getProductId());
            //拼接活动id、满减金额字符串以便去重
            if(!EmptyUtils.isEmpty(productCoupons)){
                List<ProductCoupon> finalCoupons = coupons;
                productCoupons.forEach(coupon -> {
                    coupon.setDistinct(coupon.getActivityId()+"-"+coupon.getFullMoney());
                    finalCoupons.add(coupon);
                });
            }
            if(!EmptyUtils.isEmpty(coupons)){
                //去除重复活动和对应金额优惠券
                coupons= coupons.stream().collect(
                        Collectors.collectingAndThen(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(data -> data.getDistinct()))), ArrayList::new)
                );
                if(!EmptyUtils.isEmpty(coupons)){
                    //设置优惠券内容
                    coupons.forEach(tool -> {
                        if(IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(tool.getDiscountMode())){
                            tool.setContent("满"+tool.getFullMoney().stripTrailingZeros().toPlainString()+
                                    "减"+tool.getReduceMoney().stripTrailingZeros().toPlainString()+"元");
                        } else {
                            tool.setContent(tool.getReduceMoney().stripTrailingZeros().toPlainString()+"折券");
                        }
                    });
                }
                settlement.setCoupons(coupons);
            }
            //查询店铺优惠券数据
            List<ProductCoupon> couponList=cereBuyerShopCouponService.findCouponByProduct(sku.getTotal(),
                    buyerUserId,sku.getProductId());
            if(!EmptyUtils.isEmpty(couponList)){
                List<ProductCoupon> finalShopCoupons = shopCoupons;
                couponList.forEach(coupon -> {
                    if(IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(coupon.getCouponType())){
                        //如果是折扣券,查询关联的所有商品id
                        coupon.setIds(cereBuyerShopCouponService.findProductIds(coupon.getShopCouponId()));
                        coupon.setContent(coupon.getReduceMoney().stripTrailingZeros().toPlainString()+"折券");
                    }else {
                        coupon.setContent("满"+coupon.getFullMoney().stripTrailingZeros().toPlainString()+
                                "减"+coupon.getReduceMoney().stripTrailingZeros().toPlainString()+"元");
                    }
                    finalShopCoupons.add(coupon);
                });
                //去除重复店铺优惠券
                shopCoupons= shopCoupons.stream().collect(
                        Collectors.collectingAndThen(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(data -> data.getShopCouponId()))), ArrayList::new));
            }
            settlementShop.setShopCoupons(shopCoupons);
        }
    }

    /**
     * 该方法传进来的参数，只有一个店铺，以及该店铺下购买的商品
     * @param priceCombineParam
     * @param cartSkuList
     * @return
     */
    private boolean matchCombineExt(PriceCombineParam priceCombineParam, List<CartSku> cartSkuList, SettlementShop settlementShop, Map<Long, BigDecimal> shopDiscountMap) {
        boolean matchCombine = false;
        Map<Long, CartSku> map=new HashMap<>();
        for (CartSku sku:cartSkuList) {
            map.put(sku.getSkuId(), sku);
        }
        //这个价格代表，享受定价捆绑的sku的价格之和，例如：某个sku买5件，享受的是任选3件定价N元，则该值为sku的原价 * 3
        BigDecimal useToDiscountPrice = BigDecimal.ZERO;
        //享受的捆绑的定价 * 享受的次数 例如: 某个sku买10件，享受的是任选3件定价10元，则可享受3次，总共 3 * 10 = 30
        BigDecimal finalPrice = BigDecimal.ZERO;
        if (priceCombineParam != null && MapUtils.isNotEmpty(priceCombineParam.getPriceMap()) && MapUtils.isNotEmpty(priceCombineParam.getPriceProductIdListMap())) {
            Map<Long,Map<Long,List<CerePriceRule>>> priceRuleMap = priceCombineParam.getPriceMap();
            Map<Long,List<Long>> priceIdProductIdListMap = priceCombineParam.getPriceProductIdListMap();

            List<CartSku> skuList = new ArrayList<>();
            for (Long skuId : map.keySet()) {
                skuList.add(map.get(skuId));
            }
            skuList.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
            Map<Long,List<CartSku>> shopIdSkuMap = skuList.stream().collect(Collectors.groupingBy(CartSku::getShopId));
            for (Long shopId:shopIdSkuMap.keySet()) {
                List<CartSku> shopCartSkuList = shopIdSkuMap.get(shopId);
                Map<Long,List<CerePriceRule>> priceIdRuleListMap = priceRuleMap.get(shopId);
                for (Long priceId:priceIdRuleListMap.keySet()) {
                    List<Long> productIdList = priceIdProductIdListMap.get(priceId);
                    if (CollectionUtils.isEmpty(productIdList)) {
                        continue;
                    }
                    //该活动对应的等级列表，这个列表已经按照任选X件的 X做了降序排列
                    List<CerePriceRule> ruleList = priceIdRuleListMap.get(priceId);
                    for (CerePriceRule priceRule:ruleList) {
                        Integer number = priceRule.getNumber();
                        BigDecimal price = priceRule.getPrice();
                        //先筛选在活动指定商品范围中的sku
                        List<CartSku> validSkuList = new ArrayList<>();
                        Integer validBuyNum = 0;
                        for (CartSku sku:shopCartSkuList) {
                            if (productIdList.contains(sku.getProductId())) {
                                validSkuList.add(sku);
                                validBuyNum += sku.getNumber();
                            }
                        }

                        if (validBuyNum >= number) {
                            //可以优惠multiple次
                            int multiple = validBuyNum / number;
                            int remainNum = number * multiple;
                            Iterator<CartSku> ite = validSkuList.iterator();
                            while(ite.hasNext()) {
                                CartSku sku = ite.next();
                                if (remainNum >= sku.getNumber()) {
                                    useToDiscountPrice = useToDiscountPrice.add(new BigDecimal(sku.getNumber()).multiply(sku.getPrice()));
                                    remainNum = remainNum - sku.getNumber();
                                } else {
                                    useToDiscountPrice = useToDiscountPrice.add(new BigDecimal(remainNum).multiply(sku.getPrice()));
                                    remainNum = 0;
                                }
                                sku.setPriceId(priceId);
                                ite.remove();
                                if (remainNum == 0) {
                                    matchCombine = true;
                                    break;
                                }
                            }
                            finalPrice = new BigDecimal(multiple).multiply(price);
                            break;
                        }
                    }
                }
            }
        }
        BigDecimal total=new BigDecimal(0);
        for (Long skuId : map.keySet()) {
            total = total.add(map.get(skuId).getPrice().multiply(new BigDecimal(map.get(skuId).getNumber())));
        }
        //设置本次定价捆绑优惠了多少钱
        shopDiscountMap.put(settlementShop.getShopId(), shopDiscountMap.get(settlementShop.getShopId()).add(useToDiscountPrice.subtract(finalPrice).setScale(2, BigDecimal.ROUND_HALF_UP)));
        return matchCombine;
    }

    /**
     * 该方法传进来的参数，只有一个店铺，以及该店铺下购买的商品
     * @param priceCombineParam
     * @param cartSkuList
     * @return
     */
    private boolean matchCombineExtNew(PriceCombineParam priceCombineParam, List<CartSku> originalSkus, List<CartSku> cartSkuList, SettlementShop settlementShop, Set<Long> usedIdList, Map<Long, DiscountDescDTO> priceDiscontMap) {
        boolean matchCombine = false;
        Map<Long, CartSku> map=new HashMap<>();
        for (CartSku sku:cartSkuList) {
            map.put(sku.getSkuId(), sku);
        }
        //这个价格代表，享受定价捆绑的sku的价格之和，例如：某个sku买5件，享受的是任选3件定价N元，则该值为sku的原价 * 3
        BigDecimal useToDiscountPrice = BigDecimal.ZERO;
        //享受的捆绑的定价 * 享受的次数 例如: 某个sku买10件，享受的是任选3件定价10元，则可享受3次，总共 3 * 10 = 30
        BigDecimal finalPrice = BigDecimal.ZERO;
        Long usedPriceId = null;
        BigDecimal composePrice = null;
        Integer composeNum = null;
        Integer discountNum = null;
        if (priceCombineParam != null && MapUtils.isNotEmpty(priceCombineParam.getPriceMap()) && MapUtils.isNotEmpty(priceCombineParam.getPriceProductIdListMap())) {
            Map<Long,Map<Long,List<CerePriceRule>>> priceRuleMap = priceCombineParam.getPriceMap();
            Map<Long,List<Long>> priceIdProductIdListMap = priceCombineParam.getPriceProductIdListMap();

            List<CartSku> skuList = new ArrayList<>();
            for (Long skuId : map.keySet()) {
                skuList.add(map.get(skuId));
            }
            skuList.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
            Map<Long,List<CartSku>> shopIdSkuMap = skuList.stream().collect(Collectors.groupingBy(CartSku::getShopId));
            for (Long shopId:shopIdSkuMap.keySet()) {
                List<CartSku> shopCartSkuList = shopIdSkuMap.get(shopId);
                Map<Long,List<CerePriceRule>> priceIdRuleListMap = priceRuleMap.get(shopId);
                for (Long priceId:priceIdRuleListMap.keySet()) {
                    List<Long> productIdList = priceIdProductIdListMap.get(priceId);
                    if (CollectionUtils.isEmpty(productIdList)) {
                        continue;
                    }
                    //该活动对应的等级列表，这个列表已经按照任选X件的 X做了降序排列
                    List<CerePriceRule> ruleList = priceIdRuleListMap.get(priceId);
                    for (CerePriceRule priceRule:ruleList) {
                        Integer number = priceRule.getNumber();
                        BigDecimal price = priceRule.getPrice();
                        //先筛选在活动指定商品范围中的sku
                        List<CartSku> validSkuList = new ArrayList<>();
                        Integer validBuyNum = 0;
                        for (CartSku sku:shopCartSkuList) {
                            if (productIdList.contains(sku.getProductId())) {
                                validSkuList.add(sku);
                                validBuyNum += sku.getNumber();
                            }
                        }

                        if (validBuyNum >= number) {
                            //可以优惠multiple次
                            int multiple = validBuyNum / number;
                            int remainNum = number * multiple;
                            Iterator<CartSku> ite = validSkuList.iterator();
                            while(ite.hasNext()) {
                                CartSku sku = ite.next();
                                if (remainNum >= sku.getNumber()) {
                                    useToDiscountPrice = useToDiscountPrice.add(new BigDecimal(sku.getNumber()).multiply(sku.getPrice()));
                                    remainNum = remainNum - sku.getNumber();
                                } else {
                                    useToDiscountPrice = useToDiscountPrice.add(new BigDecimal(remainNum).multiply(sku.getPrice()));
                                    remainNum = 0;
                                }
                                sku.setPriceId(priceId);
                                usedIdList.add(sku.getSkuId());
                                ite.remove();
                                if (remainNum == 0) {
                                    matchCombine = true;
                                    usedPriceId = priceId;
                                    composePrice = price;
                                    composeNum = number;
                                    discountNum = multiple;
                                    break;
                                }
                            }
                            finalPrice = new BigDecimal(multiple).multiply(price);
                            break;
                        }
                    }
                }
            }
        }
        BigDecimal total=new BigDecimal(0);
        for (Long skuId : map.keySet()) {
            total = total.add(map.get(skuId).getPrice().multiply(new BigDecimal(map.get(skuId).getNumber())));
        }
        //设置本次定价捆绑优惠了多少钱
        if (matchCombine) {
            DiscountDescDTO discountDescDTO = new DiscountDescDTO();
            discountDescDTO.setComposePrice(composePrice);
            discountDescDTO.setComposeNum(composeNum);
            discountDescDTO.setDiscountNum(discountNum);
            discountDescDTO.setDiscountTotal(useToDiscountPrice.subtract(finalPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
            priceDiscontMap.put(usedPriceId, discountDescDTO);
        }
        return matchCombine;
    }

    private boolean matchScene(CereShopScene scene, CereBuyerUser user, Calendar cal) {
        boolean matched = false;
        if (IntegerEnum.SCENE_TYPE_FESTIVAL.getCode().equals(scene.getSceneType())) {
            String startTime = scene.getStartTime();
            String endTime = scene.getEndTime();
            String nowTime = DateUtil.format(cal.getTime(),"yyyy-MM-dd");
            if (startTime.substring(0,10).compareTo(nowTime) <= 0 && nowTime.compareTo(endTime.substring(0,10)) <= 0) {
                matched = true;
            }
        } else if (IntegerEnum.SCENE_TYPE_MEMBER.getCode().equals(scene.getSceneType())) {
            String[] timeArr = scene.getSceneTime().split("-");
            if (IntegerEnum.SCENE_TIME_MONTH.getCode().equals(scene.getSceneTimeType())) {
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                if (Integer.parseInt(timeArr[0]) <= dayOfMonth && dayOfMonth <= Integer.parseInt(timeArr[1])) {
                    matched = true;
                }
            } else if (IntegerEnum.SCENE_TIME_WEEK.getCode().equals(scene.getSceneTimeType())) {
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                List<Integer> timeList = new ArrayList<>();
                for(String timeStr:timeArr) {
                    if (timeStr.equals("1")) {
                        timeList.add(7);
                    } else {
                        timeList.add(Integer.parseInt(timeStr) - 1);
                    }
                }
                if (timeList.contains(dayOfWeek)) {
                    matched = true;
                }
            } else {
                String nowTime = DateUtil.format(cal.getTime(), "HH:mm:ss");
                if (timeArr[0].compareTo(nowTime) <= 0 && nowTime.compareTo(timeArr[1]) <= 0) {
                    matched = true;
                }
            }
        } else {
            String birthday = user.getBirthday();
            if (StringUtils.isBlank(birthday)) {
                return false;
            }
            if (IntegerEnum.SCENE_TIME_BIRTHDAY.getCode().equals(scene.getSceneTimeType())) {
                if (DateUtil.format(cal.getTime(), "yyyy-MM-dd").equals(birthday)) {
                    matched = true;
                }
            } else if (IntegerEnum.SCENE_TIME_BIRTHDAY_WEEK.getCode().equals(scene.getSceneTimeType())) {
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == 1) {
                    dayOfWeek = 8;
                }
                cal.add(Calendar.DATE, -1 * (dayOfWeek - 2));
                // 当前时间对应的周的周一
                String startDateStr = DateUtil.format(cal.getTime(), "yyyy-MM-dd");
                cal.add(Calendar.DATE, 6);
                // 当前时间对应的周的周日
                String endDateStr = DateUtil.format(cal.getTime(), "yyyy-MM-dd");
                if (birthday.compareTo(startDateStr) >= 0 && birthday.compareTo(endDateStr) <= 0) {
                    matched = true;
                }
            } else {
                if (birthday.substring(0,7).equals(DateUtil.format(cal.getTime(), "yyyy-MM"))) {
                    matched = true;
                }
            }
        }
        return matched;
    }

    /**
     * 结算接口校验
     */
    private void checkActivity(SettlementParam param, Integer number,CartSku sku,Settlement settlement,
                               CereBuyerUser user,ShopProductParam shop) throws CoBusinessException{
        List<ProductCoupon> coupons=null;
        Long skuId=sku.getSkuId();
        if(!EmptyUtils.isEmpty(param.getShopSeckillId())||!EmptyUtils.isEmpty(shop.getShopSeckillId())){
            Long shopSeckillId=param.getShopSeckillId();
            if(EmptyUtils.isEmpty(shopSeckillId)){
                shopSeckillId=shop.getShopSeckillId();
            }
            //查询秒杀活动数据
            CereShopSeckill cereShopSeckill=cereShopSeckillService.findById(shopSeckillId);
            if(cereShopSeckill!=null){
                //查询优惠券
                coupons=setCoupons(cereShopSeckill.getIfAdd(),sku,user);
                settlement.setCoupons(coupons);
                //定义加锁key
                String key="秒杀活动商品仅剩件数"+cereShopSeckill.getShopSeckillId();
                //加锁
                RLock redissonLock = redisson.getLock(key);
                redissonLock.lock();
                if(IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cereShopSeckill.getIfLimit())){
                    //如果秒杀活动限购,校验数量
                    if(number>cereShopSeckill.getLimitNumber()){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR,redissonLock);
                    }
                }
                if(IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())){
                    int surplusNumber=0;
                    ProductStockInfo productStockInfo = cereStockService.getActivitySkuStock(IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode(),
                            sku.getSkuId());
                    if (productStockInfo != null) {
                        surplusNumber = productStockInfo.getStockNumber();
                    } else {
                        surplusNumber = cereShopSeckillDetailService.findNumber(cereShopSeckill.getShopSeckillId(),sku.getSkuId());
                    }
                    //如果限量,查询缓存中活动商品仅剩数量
                    /*if(EmptyUtils.isEmpty(stringRedisService.get("秒杀活动商品仅剩件数"+cereShopSeckill.getShopSeckillId()+skuId))){
                        //如果没有,取数据库限量库存数据
                        surplusNumber=cereShopSeckillDetailService.findNumber(cereShopSeckill.getShopSeckillId(),sku.getSkuId());
                    }else {
                        //如果有,取缓存
                        surplusNumber= (int) stringRedisService.get("秒杀活动商品仅剩件数"+cereShopSeckill.getShopSeckillId()+skuId);
                    }*/
                    //判断数量是否大于当前购买数量
                    if(number>surplusNumber){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT,redissonLock);
                    }
                }
                //解锁
                redissonLock.unlock();
            }
        }
        if(!EmptyUtils.isEmpty(param.getShopDiscountId())||!EmptyUtils.isEmpty(shop.getShopDiscountId())){
            Long shopDiscountId=param.getShopDiscountId();
            if(EmptyUtils.isEmpty(shopDiscountId)){
                shopDiscountId=shop.getShopDiscountId();
            }
            //查询限时折扣活动数据
            CereShopDiscount cereShopDiscount=cereShopDiscountService.findById(shopDiscountId);
            if(cereShopDiscount!=null){
                //查询优惠券
                coupons=setCoupons(cereShopDiscount.getIfAdd(),sku,user);
                settlement.setCoupons(coupons);
                //定义加锁key
                String key="限时折扣活动商品仅剩件数"+cereShopDiscount.getShopDiscountId();
                //加锁
                RLock redissonLock = redisson.getLock(key);
                redissonLock.lock();
                if(IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cereShopDiscount.getIfLimit())){
                    //如果限时折扣活动限购,校验数量
                    if(number>cereShopDiscount.getLimitNumber()){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR,redissonLock);
                    }
                }
                if(IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())){
                    int surplusNumber=0;
                    ProductStockInfo productStockInfo = cereStockService.getActivitySkuStock(IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode(),
                            sku.getSkuId());
                    if (productStockInfo != null) {
                        surplusNumber = productStockInfo.getStockNumber();
                    } else {
                        surplusNumber = cereShopDiscountDetailService.findNumber(cereShopDiscount.getShopDiscountId(),sku.getSkuId());
                    }
                    //如果限量,查询缓存中活动商品仅剩数量
                    /*if(EmptyUtils.isEmpty(stringRedisService.get("限时折扣活动商品仅剩件数"+cereShopDiscount.getShopDiscountId()+skuId))){
                        //如果没有,取数据库限量库存数据
                        surplusNumber=cereShopDiscountDetailService.findNumber(cereShopDiscount.getShopDiscountId(),sku.getSkuId());
                    }else {
                        //如果有,取缓存
                        surplusNumber= (int) stringRedisService.get("限时折扣活动商品仅剩件数"+cereShopDiscount.getShopDiscountId()+skuId);
                    }*/
                    //判断数量是否大于当前购买数量
                    if(number>surplusNumber){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT,redissonLock);
                    }
                }
                //解锁
                redissonLock.unlock();
            }
        }
        if(!EmptyUtils.isEmpty(shop.getPlatformSeckillId())){
            Long platformSeckillId=shop.getPlatformSeckillId();
            //查询秒杀活动数据
            CerePlatformSeckill cerePlatformSeckill = cerePlatformSeckillService.findById(platformSeckillId);
            if(cerePlatformSeckill!=null && IntegerEnum.ACTIVITY_START.getCode().equals(cerePlatformSeckill.getState())){
                //查询优惠券
                coupons=setCoupons(cerePlatformSeckill.getIfAdd(),sku,user);
                settlement.setCoupons(coupons);
                //定义加锁key
                String key="平台秒杀活动商品仅剩件数"+cerePlatformSeckill.getSeckillId();
                //加锁
                RLock redissonLock = redisson.getLock(key);
                redissonLock.lock();
                if(IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cerePlatformSeckill.getIfLimit())){
                    //如果秒杀活动限购,校验数量
                    if(number>cerePlatformSeckill.getLimitNumber()){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR,redissonLock);
                    }
                }
                /*if(IntegerEnum.YES.getCode().equals(cerePlatformSeckill.getIfLimit())){
                    int surplusNumber=0;
                    //如果限量,查询缓存中活动商品仅剩数量
                    if(EmptyUtils.isEmpty(stringRedisService.get("平台秒杀活动商品仅剩件数"+cerePlatformSeckill.getSeckillId()+skuId))){
                        //如果没有,取数据库限量库存数据
                        surplusNumber=cereShopSeckillDetailService.findNumber(cerePlatformSeckill.getSeckillId(),sku.getSkuId());
                    }else {
                        //如果有,取缓存
                        surplusNumber= (int) stringRedisService.get("平台秒杀活动商品仅剩件数"+cerePlatformSeckill.getSeckillId()+skuId);
                    }
                    //判断数量是否大于当前购买数量
                    if(number>surplusNumber){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT,redissonLock);
                    }
                }*/
                //解锁
                redissonLock.unlock();
            }
        }
        if(!EmptyUtils.isEmpty(shop.getPlatformDiscountId())){
            Long platformDiscountId=shop.getPlatformDiscountId();
            //查询限时折扣活动数据
            CerePlatformDiscount cerePlatformDiscount=cerePlatformDiscountService.findById(platformDiscountId);
            if(cerePlatformDiscount!=null && IntegerEnum.ACTIVITY_START.getCode().equals(cerePlatformDiscount.getState())){
                //查询优惠券
                coupons=setCoupons(cerePlatformDiscount.getIfAdd(),sku,user);
                settlement.setCoupons(coupons);
                //定义加锁key
                String key="平台限时折扣活动商品仅剩件数"+cerePlatformDiscount.getDiscountId();
                //加锁
                RLock redissonLock = redisson.getLock(key);
                redissonLock.lock();
                if(IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cerePlatformDiscount.getIfLimit())){
                    //如果限时折扣活动限购,校验数量
                    if(number>cerePlatformDiscount.getLimitNumber()){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR,redissonLock);
                    }
                }
                /*if(IntegerEnum.YES.getCode().equals(cerePlatformDiscount.getIfLimit())){
                    int surplusNumber=0;
                    //如果限量,查询缓存中活动商品仅剩数量
                    if(EmptyUtils.isEmpty(stringRedisService.get("平台限时折扣活动商品仅剩件数"+cerePlatformDiscount.getDiscountId()+skuId))){
                        //如果没有,取数据库限量库存数据
                        surplusNumber=cereShopDiscountDetailService.findNumber(cerePlatformDiscount.getDiscountId(),sku.getSkuId());
                    }else {
                        //如果有,取缓存
                        surplusNumber= (int) stringRedisService.get("平台限时折扣活动商品仅剩件数"+cerePlatformDiscount.getDiscountId()+skuId);
                    }
                    //判断数量是否大于当前购买数量
                    if(number>surplusNumber){
                        throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT,redissonLock);
                    }
                }*/
                //解锁
                redissonLock.unlock();
            }
        }
    }

    /**
     * 提交订单校验秒杀活动限量和限购
     */
    private void checkSeckillActivity(CereShopSeckill cereShopSeckill,Integer number,CartSku sku, List<CereShopSeckillDetail> seckillDetails) throws CoBusinessException{
        //定义加锁key
        String key = "秒杀活动商品仅剩件数" + cereShopSeckill.getShopSeckillId();
        //加锁
        RLock redissonLock = redisson.getLock(key);
        redissonLock.lock();
        if (IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cereShopSeckill.getIfLimit())) {
            //如果秒杀活动限购,校验数量
            if (number > cereShopSeckill.getLimitNumber()) {
                throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR, redissonLock);
            }
        }
        if (IntegerEnum.YES.getCode().equals(cereShopSeckill.getIfNumber())) {
            int surplusNumber=0;
            ProductStockInfo productStockInfo = cereStockService.getActivitySkuStock(IntegerEnum.ACTIVITY_TYPE_SHOP_SECKILL.getCode(),
                    sku.getSkuId());
            if (productStockInfo != null) {
                surplusNumber = productStockInfo.getStockNumber();
            } else {
                surplusNumber = cereShopSeckillDetailService.findNumber(cereShopSeckill.getShopSeckillId(),sku.getSkuId());
            }
            //如果限量,查询缓存中活动商品仅剩数量
            /*if (EmptyUtils.isEmpty(stringRedisService.get("秒杀活动商品仅剩件数" + cereShopSeckill.getShopSeckillId() + sku.getSkuId()))) {
                //如果缓存中没有,取数据库中仅剩件数
                surplusNumber=cereShopSeckillDetailService.findNumber(cereShopSeckill.getShopSeckillId(),sku.getSkuId());
            } else {
                //如果缓存中有,就使用缓存中仅剩件数
                surplusNumber = (int) stringRedisService.get("秒杀活动商品仅剩件数" + cereShopSeckill.getShopSeckillId() + sku.getSkuId());
            }*/
            //判断数量是否大于当前购买数量
            if (number > surplusNumber) {
                throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT, redissonLock);
            }
            //设置更新限量库存数据
            CereShopSeckillDetail detail=new CereShopSeckillDetail();
            detail.setShopSeckillId(cereShopSeckill.getShopSeckillId());
            detail.setSkuId(sku.getSkuId());
            detail.setNumber(surplusNumber - number);
            seckillDetails.add(detail);
        }
        //解锁
        redissonLock.unlock();
    }

    /**
     * 提交订单校验限时折扣限量和限购
     */
    private void checkDiscountActivity(CereShopDiscount cereShopDiscount,Integer number,CartSku sku,List<CereShopDiscountDetail> discountDetails) throws CoBusinessException{
        //定义加锁key
        String key="限时折扣活动商品仅剩件数"+cereShopDiscount.getShopDiscountId();
        //加锁
        RLock redissonLock = redisson.getLock(key);
        redissonLock.lock();
        if(IntegerEnum.PRODUCT_IF_LIMIT_YES.getCode().equals(cereShopDiscount.getIfLimit())){
            //如果限时折扣活动限购,校验数量
            if(number>cereShopDiscount.getLimitNumber()){
                throw new CoBusinessException(CoReturnFormat.PRODUCT_LIMIT_ERROR,redissonLock);
            }
        }
        if(IntegerEnum.YES.getCode().equals(cereShopDiscount.getIfNumber())){
            int surplusNumber=0;
            ProductStockInfo productStockInfo = cereStockService.getActivitySkuStock(IntegerEnum.ACTIVITY_TYPE_SHOP_DISCOUNT.getCode(),
                    sku.getSkuId());
            if (productStockInfo != null) {
                surplusNumber = productStockInfo.getStockNumber();
            } else {
                surplusNumber = cereShopDiscountDetailService.findNumber(cereShopDiscount.getShopDiscountId(),sku.getSkuId());
            }
            //如果限量,查询缓存中活动商品仅剩数量
            /*if(EmptyUtils.isEmpty(stringRedisService.get("限时折扣活动商品仅剩件数"+cereShopDiscount.getShopDiscountId()+sku.getSkuId()))){
                //如果没有,取数据库仅剩件数
                surplusNumber=cereShopDiscountDetailService.findNumber(cereShopDiscount.getShopDiscountId(),sku.getSkuId());
            }else {
                //如果有,取缓存
                surplusNumber= (int) stringRedisService.get("限时折扣活动商品仅剩件数"+cereShopDiscount.getShopDiscountId()+sku.getSkuId());
            }*/
            //判断数量是否大于当前购买数量
            if(number>surplusNumber){
                throw new CoBusinessException(CoReturnFormat.PRODUCT_ALREADY_SELL_OUT,redissonLock);
            }
            //设置更新限量库存数据
            CereShopDiscountDetail detail=new CereShopDiscountDetail();
            detail.setShopDiscountId(cereShopDiscount.getShopDiscountId());
            detail.setSkuId(sku.getSkuId());
            detail.setNumber(surplusNumber-number);
            discountDetails.add(detail);
        }
        //解锁
        redissonLock.unlock();
    }

    private List<ProductCoupon> setCoupons(Integer ifAdd, CartSku sku,CereBuyerUser user) {
        if(IntegerEnum.YES.getCode().equals(ifAdd)){
            //如果优惠券允许叠加,首先查询平台优惠券
            List<ProductCoupon> tools=new ArrayList<>();
            //根据商品id、商品价格查询满足条件的优惠券
            List<ProductCoupon> productCoupons=cereBuyerCouponService.findCouponByProduct(sku.getTotal(),
                    user.getBuyerUserId(),sku.getProductId());
            //拼接活动id、满减金额字符串以便去重
            if(!EmptyUtils.isEmpty(productCoupons)){
                productCoupons.forEach(coupon -> {
                    coupon.setDistinct(coupon.getActivityId()+"-"+coupon.getFullMoney());
                    tools.add(coupon);
                });
            }
            //然后查询店铺优惠券
            List<ProductCoupon> shopCoupons=cereBuyerShopCouponService.findCouponByProduct(sku.getTotal(),
                    user.getBuyerUserId(),sku.getProductId());
            if(!EmptyUtils.isEmpty(tools)){
                if(!EmptyUtils.isEmpty(shopCoupons)){
                    List<ProductCoupon> coupons=new ArrayList<>();
                    tools.forEach(tool -> {coupons.add(tool);});
                    shopCoupons.forEach(shopCoupon -> {coupons.add(shopCoupon);});
                    return coupons;
                }else {
                    return tools;
                }
            }else {
                return shopCoupons;
            }
        }
        return null;
    }

    @Override
    public Distribution setLogisticPrice(List<CereOrderLogistics> logistics, List<CartSku> skus, CereBuyerReceive receive,
                                         Map<Long, Integer> map) {
        Distribution tribution=new Distribution();
        tribution.setDistributionPrice(new BigDecimal(0));
        //过滤不需要物流的商品
        List<CartSku> cartSkus=skus.stream().filter(sku -> IntegerEnum.YES.getCode().equals(sku.getIfLogistics()))
                .collect(Collectors.toList());
        if(EmptyUtils.isEmpty(cartSkus)){
            tribution.setDistributionName("全国包邮");
            return tribution;
        }
        List<Distribution> distributions=new ArrayList<>();
        if(!EmptyUtils.isEmpty(logistics)&&receive!=null){
            logistics.forEach(a -> {
                Distribution distribution=new Distribution();
                distribution.setDistributionName("全国包邮");
                distribution.setDistributionPrice(new BigDecimal(0));
                distribution.setLogisticsId(a.getLogisticsId());
                if(ParamEnum.CHARGE_TYPE.getCode().equals(a.getChargeType())){
                    //如果是全国包邮
                    distribution.setDistributionName("全国包邮");
                }else if(ParamEnum.CHARGE_TYPE_NUMBER.getCode().equals(a.getChargeType())){
                    //如果是按件,查询所有计费明细
                    List<CereLogisticsCharge> charges=cereOrderLogisticsService.findCharges(a.getLogisticsId());
                    if(!EmptyUtils.isEmpty(charges)){
                        //遍历计费明细计算物流费用
                        charges.forEach(charge -> {
                            //校验城市匹配
                            boolean flag = matching(charge,receive);
                            if(flag){
                                //匹配上了,计算物流费用
                                if(!EmptyUtils.isEmpty(charge.getWeight())&&!EmptyUtils.isEmpty(charge.getPrice())){
                                    //先遍历商品数据拿到总件数
                                    BigDecimal number=new BigDecimal(0);
                                    for (CartSku cartSku : cartSkus) {
                                        number=number.add(new BigDecimal(map.get(cartSku.getSkuId())));
                                    }
                                    //商品总件数-首件
                                    BigDecimal subtract = number.subtract(charge.getWeight());
                                    //加入首件价格
                                    distribution.setDistributionPrice(distribution.getDistributionPrice().add(charge.getPrice()));
                                    if(subtract.compareTo(new BigDecimal(0))==1){
                                        //如果还有剩余
                                        if(!EmptyUtils.isEmpty(charge.getSecondWeight())&&!EmptyUtils.isEmpty(charge.getSecondPrice())){
                                            //剩余件数/续件数（向上取整）*续件价格
                                            distribution.setDistributionPrice(distribution.getDistributionPrice().
                                                    add(subtract.divide(charge.getSecondWeight(),BigDecimal.ROUND_UP).multiply(charge.getSecondPrice())));
                                        }
                                    }
                                }
                            }
                        });
                    }
                    if(distribution.getDistributionPrice().compareTo(new BigDecimal(0))!=0){
                        distribution.setDistributionName("按件");
                    }
                }else if(ParamEnum.CHARGE_TYPE_WEIGHT.getCode().equals(a.getChargeType())){
                    //如果是按重量,查询所有计费明细
                    List<CereLogisticsCharge> charges=cereOrderLogisticsService.findCharges(a.getLogisticsId());
                    if(!EmptyUtils.isEmpty(charges)){
                        charges.forEach(charge -> {
                            //校验城市匹配
                            boolean flag = matching(charge,receive);
                            if(flag){
                                //匹配上了,计算运费
                                if(!EmptyUtils.isEmpty(charge.getWeight())&&!EmptyUtils.isEmpty(charge.getPrice())){
                                    //先遍历商品数据拿到总重量
                                    BigDecimal weight=new BigDecimal(0);
                                    for (CartSku cartSku : cartSkus) {
                                        weight=weight.add(cartSku.getWeight());
                                    }
                                    //商品总重量-首重
                                    BigDecimal subtract = weight.subtract(charge.getWeight());
                                    //加上首重价格
                                    distribution.setDistributionPrice(distribution.getDistributionPrice().add(charge.getPrice()));
                                    if(subtract.compareTo(new BigDecimal(0))==1){
                                        //如果还有剩余
                                        if(!EmptyUtils.isEmpty(charge.getSecondWeight())&&!EmptyUtils.isEmpty(charge.getSecondPrice())){
                                            //剩余重量/续重（向上取整）*续件价格
                                            distribution.setDistributionPrice(distribution.getDistributionPrice().
                                                    add(subtract.divide(charge.getSecondWeight(),BigDecimal.ROUND_UP).multiply(charge.getSecondPrice())));
                                        }
                                    }
                                }
                            }
                        });
                    }
                    if(distribution.getDistributionPrice().compareTo(new BigDecimal(0))!=0){
                        distribution.setDistributionName("按重量");
                    }
                }
                if(!EmptyUtils.isEmpty(distributions)){
                    //比较2个运费的大小,取大的放入list
                    BigDecimal decimal=distributions.get(0).getDistributionPrice();
                    if(distribution.getDistributionPrice().compareTo(decimal)==1){
                        distributions.clear();
                        distributions.add(distribution);
                    }
                }else {
                    distributions.add(distribution);
                }
            });
        }
        if(!EmptyUtils.isEmpty(distributions)){
            return distributions.get(0);
        }
        return tribution;
    }

    private boolean matching(CereLogisticsCharge charge,CereBuyerReceive receive) {
        List<String> provinces=new ArrayList<>();
        List<String> cities=new ArrayList<>();
        //校验与当前收货地址城市是否匹配
        if(charge.getRegion().contains(";")){
            //包含多个省,截取出所有的省
            String[] split = charge.getRegion().split(";");
            for (String str:split) {
                setProvinceCities(str,provinces,cities);
            }
        }else {
            //只有一个省的数据
            setProvinceCities(charge.getRegion(),provinces,cities);
        }
        //校验当前收货地址中的省份
        boolean flag=checkProvince(receive,provinces);
        if(!flag){
            //校验收货地址中的市
            flag=checkCities(receive,cities);
        }else {
            return flag;
        }
        return false;
    }

    private boolean checkCities(CereBuyerReceive receive, List<String> cities) {
        String[] split = receive.getReceiveAdress().split("-");
        if(!EmptyUtils.isEmpty(split)&&!EmptyUtils.isEmpty(cities)){
            if(cities.contains(split[1])){
                return true;
            }
        }
        return false;
    }

    private boolean checkProvince(CereBuyerReceive receive, List<String> provinces) {
        if(receive.getReceiveAdress().contains("-")){
            String[] split = receive.getReceiveAdress().split("-");
            if(!EmptyUtils.isEmpty(split)&&!EmptyUtils.isEmpty(provinces)){
                if(provinces.contains(split[0])){
                    return true;
                }
            }
        }
        return false;
    }

    private void setProvinceCities(String str,List<String> provinces,List<String> cities) {
        if(str.contains(":")){
            //带市的数据
            String[] split1 = str.split(":");
            provinces.add(split1[0]);
            if(split1[1].contains(",")){
                //包含多个市
                String[] split2 = split1[1].split(",");
                for (String city:split2) {
                    cities.add(city);
                }
            }else {
                //一个市
                cities.add(split1[1]);
            }
        }else {
            //不带市
            provinces.add(str);
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void pay(PayParam param) throws CoBusinessException,Exception {
        String time = TimeUtils.yyMMddHHmmss();
        List<CereShopOrder> list=null;
        if(ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())){
            //父订单支付,查询所有子订单数据
            list=cereShopOrderDAO.findByParentId(param.getOrderId());
        }else {
            //子订单支付
            list=cereShopOrderDAO.findByOrderId(param.getOrderId());
        }
        //支付业务处理
        if(!EmptyUtils.isEmpty(list)){
            List<CereDistributionOrder> distributionOrders=new ArrayList<>();
            List<CereShopConversion> conversions=new ArrayList<>();
            for (CereShopOrder order:list) {
                if(!EmptyUtils.isEmpty(order.getShopGroupWorkId())){
                    //拼单支付业务处理
                    handleGroupWork(order,RandomStringUtil.getRandomCode(15,0),
                            order.getOrderFormid()+"-"+RandomStringUtil.getRandomCode(4,0)+"XCX", time);
                }else if(!EmptyUtils.isEmpty(order.getShopSeckillId())){
                    //秒杀活动支付业务处理
                    handleSeckill(order, RandomStringUtil.getRandomCode(15,0),
                            order.getOrderFormid()+"-"+RandomStringUtil.getRandomCode(4,0)+"XCX",time);
                }else if(!EmptyUtils.isEmpty(order.getShopDiscountId())){
                    //限时折扣活动支付业务处理
                    handleDiscount(order, RandomStringUtil.getRandomCode(15,0),
                            order.getOrderFormid()+"-"+RandomStringUtil.getRandomCode(4,0)+"XCX",time);
                }else {
                    //正常下单业务处理
                    handleOrder(order, RandomStringUtil.getRandomCode(15,0),
                            order.getOrderFormid()+"-"+RandomStringUtil.getRandomCode(4,0)+"XCX",time,distributionOrders,conversions);
                }
            }
            if(!EmptyUtils.isEmpty(distributionOrders)){
                //批量插入分销订单数据
                cereDistributionOrderService.insertBatch(distributionOrders);
            }
            if(!EmptyUtils.isEmpty(conversions)){
                //批量插入转化数据
                cereShopConversionService.insertBatch(conversions);
            }
        }
    }

    @Override
    public PayUrl getUrl(OrderGetByIdParam param, CereBuyerUser user, String ip) throws CoBusinessException, Exception {
        PayUrl payUrl=new PayUrl();
        //根据订单id查询支付金额数据
        CereShopOrder shopOrder=cereShopOrderDAO.findById(param.getOrderId());
        if(shopOrder!=null){
            payUrl.setOrderId(param.getOrderId());
            payUrl.setMoney(shopOrder.getPrice());
            //生成收款码
            String formid=shopOrder.getOrderFormid()+"-"+RandomStringUtil.getRandomCode(3,0)+"XCX";
            String code_url = wxPayService.getOrderCollectionCode(formid, shopOrder.getPrice(),
                    ip, WxPayEnum.PAY_TYPE_NATIVE.getCode());
            if(!EmptyUtils.isEmpty(code_url)){
                //生成收款二维码图片
                Map<EncodeHintType, Object> hints = new HashMap<>();
                // 设置纠错等级
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                // 编码类型
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE, 400, 400, hints);
                MatrixToImageConfig config = new MatrixToImageConfig();
                BufferedImage image = toBufferedImage(bitMatrix, config);
                //上传图片到OSS
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(image,"png", out);
                byte[] bytes = out.toByteArray();
                String url = uploadService.uploadByte(formid+".png", bytes);
                payUrl.setUrl(url);
            }
        }
        return payUrl;
    }

    @Override
    public PayUrl checkPay(PayParam param) throws CoBusinessException {
        PayUrl payUrl=new PayUrl();
        List<CereShopOrder> list=null;
        //查询订单是否支付成功
        if(ParamEnum.ORDER_TYPE_PARENT.getCode().equals(param.getType())){
            //父订单校验
            list=cereShopOrderDAO.checkPayParent(param.getOrderId());
        }else {
            //子订单校验
            list=cereShopOrderDAO.checkPayOrder(param.getOrderId());
        }
        if(!EmptyUtils.isEmpty(list)){
            payUrl.setCode(StringEnum.WX_PAY_SUCCESS.getCode());
        }
        return payUrl;
    }

    @Override
    public List<CartSku> refund(OrderGetByIdParam param, CereBuyerUser user) throws CoBusinessException {
        List<CartSku> skus=null;
        //查询当前订单已经提交过的售后商品规格id数组
        List<Long> ids=cereShopOrderDAO.findAfterSkuIdsByOrderId(param.getOrderId());
        if(EmptyUtils.isEmpty(ids)){
            //未申请过售后或者售后失败等,查询订单所有商品列表返回
            skus=cereProductSkuService.findSkuByOrderId(param.getOrderId());
        }else {
            //有售后商品,查询当前订单除了这些商品以外的其他商品
            skus=cereProductSkuService.findSkuByIds(ids,param.getOrderId());
        }
        return skus;
    }

    @Override
    public void payGift(String orderFormId) {
        log.info("payGift orderFormId = {}", orderFormId);
        List<CereShopOrder> list=null;
        Long orderId = null;
        //查询是否为父订单编号
        CereOrderParent parent=cereShopOrderDAO.findByParentFormid(orderFormId);
        if(parent!=null){
            //查询所有子订单数据
            list=cereShopOrderDAO.findByParentId(parent.getParentId());
            orderId = parent.getParentId();
        }else {
            //子订单支付回调
            list=cereShopOrderDAO.findByFormid(orderFormId);
            orderId = list.get(0).getOrderId();
        }
        CerePlatformPolite polite = cerePlatformPoliteService.selectOnGoingPolite();
        log.info("payGift onGoingPolite = {}", JSON.toJSONString(polite));
        if (polite != null) {
            boolean matchCondition = false;
            //购买一定金额商品
            if (polite.getBuyerMode() == 1) {
                BigDecimal totalPrice = list.stream().map(CereShopOrder::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
                //满足条件
                if (totalPrice.compareTo(polite.getBuyer()) >= 0) {
                    matchCondition = true;
                }
            } else {//购买一定数量商品
                List<CereOrderProduct> opList = cereOrderProductService.findByOrderIds(list.stream().map(CereShopOrder::getOrderId).collect(Collectors.toList()));
                int totalBuyNum = opList.stream().mapToInt(CereOrderProduct::getNumber).sum();
                if (totalBuyNum >= polite.getBuyer().intValue()) {
                    matchCondition = true;
                }
            }
            log.info("payGift matchCondition = {}", matchCondition);
            if (matchCondition) {
                Long buyerUserId = list.get(0).getBuyerUserId();
                if (buyerUserId != null) {
                    CereBuyerUser user = cereBuyerUserService.selectByBuyerUserId(buyerUserId);
                    if (user == null) {
                        return;
                    }
                    String time = TimeUtils.yyMMddHHmmss();
                    if (polite.getGrowth() != null && polite.getGrowth() > 0) {
                        if (user.getMemberLevelId() != null && user.getMemberLevelId() > 0) {
                            user.setGrowth(user.getGrowth() + polite.getGrowth());
                            CerePlatformMemberLevel nextLevel = cerePlatformMemberLevelService.selectNextLevel(user.getMemberLevelId());
                            if (nextLevel != null && user.getGrowth() >= nextLevel.getGrowth()) {
                                user.setMemberLevelId(nextLevel.getMemberLevelId());
                            }
                        } else {
                            CerePlatformMemberLevel level = cerePlatformMemberLevelService.selectFirstLevel();
                            if (level != null) {
                                user.setMemberLevelId(level.getMemberLevelId());
                                user.setGrowth(polite.getGrowth());
                            }
                        }
                        // 由于selectByBuyerUserId 可能更改了wechatName字段，所以这里重新设置为null，这样就不会更新到
                        user.setWechatName(null);
                        try {
                            cereBuyerUserService.update(user, null);

                            CereBuyerPoliteRecord record = new CereBuyerPoliteRecord();
                            record.setBuyerUserId(buyerUserId);
                            record.setOrderId(orderId);
                            record.setPoliteId(polite.getPoliteId());
                            record.setPoliteType(IntegerEnum.POLITE_TYPE_GROWTH.getCode());
                            record.setGrowth(polite.getGrowth());
                            record.setCreateTime(time);
                            record.setUpdateTime(time);
                            cereBuyerPoliteRecordDAO.insertSelective(record);
                        } catch (Exception e) {
                            log.error("payGift update user failed: orderFormId = {}", orderFormId, e);
                        }
                    }

                    List<CerePlatformPoliteActivity> activityList = cerePlatformPoliteActivityService.selectByPoliteId(polite.getPoliteId());
                    if (CollectionUtils.isNotEmpty(activityList)) {
                        for (CerePlatformPoliteActivity activity:activityList) {
                            CouponParam couponParam = new CouponParam();
                            couponParam.setCouponId(activity.getActivityId());
                            try {
                                cereBuyerCouponService.takeCoupon(couponParam, user);

                                CereBuyerPoliteRecord record = new CereBuyerPoliteRecord();
                                record.setBuyerUserId(buyerUserId);
                                record.setOrderId(orderId);
                                record.setPoliteId(polite.getPoliteId());
                                if (IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(activity.getActivityType())) {
                                    record.setPoliteType(IntegerEnum.POLITE_TYPE_REDUCTION.getCode());
                                } else {
                                    record.setPoliteType(IntegerEnum.POLITE_TYPE_DISCOUNT.getCode());
                                }
                                record.setDiscount(activity.getCouponContent());
                                record.setCreateTime(time);
                                record.setUpdateTime(time);
                                cereBuyerPoliteRecordDAO.insertSelective(record);
                            } catch (Exception e) {
                                log.error("payGift takeCoupon failed: couponId = {}", activity.getActivityId(), e);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public OrderPoliteDTO getOrderPolite(Long buyerUserId, Long orderId) {
        List<CereBuyerPoliteRecord> recordList = cereBuyerPoliteRecordDAO.selectPoliteRecord(buyerUserId, orderId);
        OrderPoliteDTO politeDTO = new OrderPoliteDTO();
        if (CollectionUtils.isNotEmpty(recordList)) {
            List<OrderPoliteCouponDTO> politeCouponDTOList = new ArrayList<>();
            for (CereBuyerPoliteRecord record:recordList) {
                if (IntegerEnum.POLITE_TYPE_GROWTH.getCode().equals(record.getPoliteType())) {
                    politeDTO.setGrowth(record.getGrowth());
                } else {
                    OrderPoliteCouponDTO couponDTO = new OrderPoliteCouponDTO();
                    if (IntegerEnum.POLITE_TYPE_REDUCTION.getCode().equals(record.getPoliteType())) {
                        couponDTO.setCouponType(IntegerEnum.COUPON_TYPE_REDUTION.getCode());
                    } else {
                        couponDTO.setCouponType(IntegerEnum.COUPON_TYPE_DISCOUNT.getCode());
                    }
                    couponDTO.setDiscount(record.getDiscount());
                    politeCouponDTOList.add(couponDTO);
                }
            }
            politeDTO.setCouponList(politeCouponDTOList);
            return politeDTO;
        }
        return null;
    }

    /**
     * 查询定价捆绑销售的活动
     * @param shopIdList
     * @param productIdList
     * @return
     */
    private PriceCombineParam getPriceParam(List<Long> shopIdList, List<Long> productIdList) {
        PriceCombineParam param = new PriceCombineParam();
        Map<Long,Map<Long,List<CerePriceRule>>> map = cereShopPriceService.selectPriceMap(shopIdList);
        param.setPriceMap(map);
        List<Long> priceIdList = new ArrayList<>();
        if (map != null) {
            for(Map.Entry<Long,Map<Long,List<CerePriceRule>>> entry:map.entrySet()) {
                Map<Long,List<CerePriceRule>> priceIdRuleListMap = entry.getValue();
                if (priceIdRuleListMap != null) {
                    for(Map.Entry<Long,List<CerePriceRule>> priceIdEntry:priceIdRuleListMap.entrySet()) {
                        priceIdList.add(priceIdEntry.getKey());
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(priceIdList)) {
            Map<Long, List<Long>> priceProductIdListMap = cerePriceProductService.selectProductIdListMap(priceIdList,productIdList);
            param.setPriceProductIdListMap(priceProductIdListMap);
        }

        return param;
    }

}
