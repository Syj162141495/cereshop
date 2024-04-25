/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.activity.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.shop.cereshop.business.alioss.service.UploadService;
import com.shop.cereshop.business.dao.activity.CereActivitySignDAO;
import com.shop.cereshop.business.dao.activity.CereSignProductDAO;
import com.shop.cereshop.business.page.activity.*;
import com.shop.cereshop.business.page.finance.BondCount;
import com.shop.cereshop.business.page.finance.ShopBond;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.activity.*;
import com.shop.cereshop.business.param.finance.BondParam;
import com.shop.cereshop.business.pay.PayFactory;
import com.shop.cereshop.business.pay.weixin.service.WxPayService;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.activity.CereActivitySignService;
import com.shop.cereshop.business.service.activity.CereSignProductService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.pay.CerePayLogService;
import com.shop.cereshop.business.service.platformtool.CerePlatformDiscountService;
import com.shop.cereshop.business.service.platformtool.CerePlatformSeckillService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.business.service.tool.*;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.constant.WxPayEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.activity.CerePlatformActivity;
import com.shop.cereshop.commons.domain.activity.CereSignProduct;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformDiscount;
import com.shop.cereshop.commons.domain.platformtool.CerePlatformSeckill;
import com.shop.cereshop.commons.domain.shop.CereShopGroup;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage;

@Service
public class CereActivitySignServiceImpl implements CereActivitySignService {

    @Autowired
    private CereActivitySignDAO cereActivitySignDAO;

    @Autowired
    private CereSignProductService cereSignProductService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CerePayLogService cerePayLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CereSignProductDAO cereSignProductDAO;

    @Value("${bond_payment_mode}")
    private Integer bondPaymentMode;

    @Autowired
    private CereShopGroupWorkService cereShopGroupWorkService;

    @Autowired
    private CereShopSeckillService cereShopSeckillService;

    @Autowired
    private CereShopDiscountService cereShopDiscountService;

    @Autowired
    private CerePlatformSeckillService cerePlatformSeckillService;

    @Autowired
    private CerePlatformDiscountService cerePlatformDiscountService;

    @Override
    public Page getAll(ActivitiSignGetAllParam param) throws CoBusinessException,Exception {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ActivitySign> list=new ArrayList<>();
        if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(param.getSignType())){
            //平台优惠券
            list=cereActivitySignDAO.getAll(param);
        }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(param.getSignType())){
            //平台秒杀
            list=cereActivitySignDAO.getSeckillAll(param);
        }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(param.getSignType())){
            //平台限时折扣
            list=cereActivitySignDAO.getDiscountAll(param);
        }
        if(!EmptyUtils.isEmpty(list)){
            for (ActivitySign activitySign:list) {
                //设置操作标识
                if(!EmptyUtils.isEmpty(activitySign.getSignState())){
                    //有报名数据
                    if(IntegerEnum.SIGN_ACTIVITY_ING.getCode().equals(activitySign.getSignState())) {
                        //报名进行中 如果活动也是报名中，继续报名
                        if(IntegerEnum.ACTIVITY_SIGN_ON.getCode().equals(activitySign.getState())){
                            activitySign.setOperation(IntegerEnum.SIGN_CONTINUE.getCode());
                        }
                    } else if(IntegerEnum.SIGN_ACTIVITY_STAY.getCode().equals(activitySign.getSignState())
                    ||IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode().equals(activitySign.getSignState())){
                        //报名状态为待审核或者报名成功,显示报名详情
                        activitySign.setOperation(IntegerEnum.SIGN_ALREADY.getCode());
                    }else if(IntegerEnum.SIGN_ACTIVITY_ERROR.getCode().equals(activitySign.getSignState())){
                        //报名状态为报名失败
                        if(IntegerEnum.ACTIVITY_SIGN_ON.getCode().equals(activitySign.getState())){
                            //如果活动状态为报名进行中,显示重新报名
                            activitySign.setOperation(IntegerEnum.SIGN_AGAIN.getCode());
                        }else {
                            //其他活动状态,显示报名详情
                            activitySign.setOperation(IntegerEnum.SIGN_ALREADY.getCode());
                        }
                    }
                }else {
                    //没有报名数据
                    if(IntegerEnum.ACTIVITY_SIGN_ON.getCode().equals(activitySign.getState())){
                        //如果活动状态为报名进行中,显示立即报名（高亮）
                        activitySign.setOperation(IntegerEnum.SIGN_STAY.getCode());
                    }else {
                        //其他活动状态,显示立即报名（置灰）
                        activitySign.setOperation(IntegerEnum.SIGN_STAY_NOT.getCode());
                    }
                }
                activitySign.setTime(TimeUtils.getCountDownByTime(TimeUtils.yyMMddHHmmss(),activitySign.getActivityEndTime())/1000);
            }
        }
        PageInfo<ActivitySign> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public BondState save(ActivitySignSaveParam param, CerePlatformBusiness user,String ip) throws CoBusinessException,Exception {
        BondState bondState=new BondState();
        List<Long> productIs=null;
        if(!EmptyUtils.isEmpty(param.getProducts())){
            productIs = param.getProducts().stream().map(SignProductParam::getProductId).collect(Collectors.toList());
        }
        //查询活动是否需要保证金
        Integer ifBond = null;
        if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(param.getSignType())){
            //平台优惠券
            CerePlatformActivity activity = cereActivitySignDAO.findIfBond(param.getActivityId());
            ifBond=activity.getIfBond();
        }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(param.getSignType())){
            //平台秒杀
            CerePlatformSeckill seckill = cereActivitySignDAO.findSeckillIfBond(param.getActivityId());
            ifBond=seckill.getIfBond();
            if(!EmptyUtils.isEmpty(productIs)){
                //查询商品中最低售价
                BigDecimal min=cerePlatformSeckillService.findMinProductPrice(productIs);
                if(seckill.getSeckillMoney().compareTo(min)  > 0){
                    throw new CoBusinessException(CoReturnFormat.SECKILL_PRICE_ERROR);
                }
                //查询当前时间范围内这些商品中是否存在商家限时折扣活动中
                List<Long> ids=cereShopSeckillService.checkOtherDiscount(productIs,seckill.getStartTime(),
                        seckill.getEndTime(),param.getShopId());
                if(!EmptyUtils.isEmpty(ids)){
                    throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
                }
                //查询当前时间范围内这些商品中是否存在商家拼团活动中
                ids=cereShopSeckillService.checkOtherWork(productIs,seckill.getStartTime(),
                        seckill.getEndTime(),param.getShopId());
                if(!EmptyUtils.isEmpty(ids)){
                    throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
                }
                //查询当前时间范围内这些商品中是否存在商家秒杀活动中
                ids=cereShopDiscountService.checkOtherSeckill(productIs,seckill.getStartTime(),
                        seckill.getEndTime(),param.getShopId());
                if(!EmptyUtils.isEmpty(ids)){
                    throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
                }
                //查询当前时间范围内这些商品中是否存在平台限时折扣活动中
                ids=cerePlatformDiscountService.checkPlatformDiscount(productIs,seckill.getStartTime(),
                        seckill.getEndTime(),param.getShopId());
                if(!EmptyUtils.isEmpty(ids)){
                    throw new CoBusinessException(CoReturnFormat.PLATFORM_DISCOUNT_PRODUCT_REPEAT);
                }
            }
        }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(param.getSignType())){
            //平台优限时折扣
            CerePlatformDiscount discount =cereActivitySignDAO.findDiscountIfBond(param.getActivityId());
            ifBond=discount.getIfBond();
            if(!EmptyUtils.isEmpty(productIs)) {
                //查询当前时间范围内这些商品中是否存在商家限时折扣活动中
                List<Long> ids = cereShopSeckillService.checkOtherDiscount(productIs, discount.getStartTime(),
                        discount.getEndTime(), param.getShopId());
                if (!EmptyUtils.isEmpty(ids)) {
                    throw new CoBusinessException(CoReturnFormat.SHOP_DISCOUNT_PRODUCT_REPEAT);
                }
                //查询当前时间范围内这些商品中是否存在商家拼团活动中
                ids = cereShopSeckillService.checkOtherWork(productIs, discount.getStartTime(),
                        discount.getEndTime(), param.getShopId());
                if (!EmptyUtils.isEmpty(ids)) {
                    throw new CoBusinessException(CoReturnFormat.GROUP_WORK_PRODUCT_REPEAT);
                }
                //查询当前时间范围内这些商品中是否存在商家秒杀活动中
                ids = cereShopDiscountService.checkOtherSeckill(productIs, discount.getStartTime(),
                        discount.getEndTime(), param.getShopId());
                if (!EmptyUtils.isEmpty(ids)) {
                    throw new CoBusinessException(CoReturnFormat.SHOP_SECKILL_PRODUCT_REPEAT);
                }
                //查询当前时间范围内这些商品中是否存在平台秒杀活动中
                ids = cerePlatformSeckillService.checkPlatformSeckill(productIs, discount.getStartTime(),
                        discount.getEndTime(), param.getShopId());
                if (!EmptyUtils.isEmpty(ids)) {
                    throw new CoBusinessException(CoReturnFormat.PLATFORM_SECKILL_PRODUCT_REPEAT);
                }
            }
        }

        String time=TimeUtils.yyMMddHHmmss();
        //新增报名数据
        CereActivitySign cereActivitySign=new CereActivitySign();
        cereActivitySign.setActivityId(param.getActivityId());
        cereActivitySign.setShopId(param.getShopId());
        cereActivitySign.setBondMoney(param.getBondMoney());
        cereActivitySign.setBondState(IntegerEnum.BOND_NOT_PAY.getCode());
        cereActivitySign.setCreateTime(time);
        cereActivitySign.setSignType(param.getSignType());
        //如果不需要保证金直接修改状态为报名成功
        if(IntegerEnum.NO.getCode().equals(ifBond)){
            cereActivitySign.setState(IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode());
        }else {
            //如果需要就是待审核
            cereActivitySign.setState(IntegerEnum.SIGN_ACTIVITY_STAY.getCode());
        }
        cereActivitySignDAO.insert(cereActivitySign);
        //新增活动商品数据
        if(!EmptyUtils.isEmpty(param.getProducts())){
            long nullNumberCount = param.getProducts().stream().filter(product->product.getNumber() == null).count();
            if (nullNumberCount > 0) {
                throw new CoBusinessException(CoReturnFormat.ACTIVITY_STOCK_NOT_NULL);
            }
            List<CereSignProduct> cereSignProducts = param.getProducts().stream()
                    .map(product -> {
                        CereSignProduct cereSignProduct = new CereSignProduct();
                        cereSignProduct.setSignId(cereActivitySign.getSignId());
                        cereSignProduct.setProductId(product.getProductId());
                        cereSignProduct.setTotal(product.getNumber());
                        cereSignProduct.setNumber(product.getNumber());
                        return cereSignProduct;
                    })
                    .collect(Collectors.toList());
            //批量插入活动商品数据
            cereSignProductService.insertBatch(cereSignProducts);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"营销活动管理","商户端操作","报名营销活动",param.getActivityId(),time);

        if(IntegerEnum.YES.getCode().equals(ifBond)){
            //需要保证金
            //新增延时任务2分钟如果还没支付成功,就删除业务数据
            stringRedisService.set(StringEnum.SIGN_NOT_PAY_DELETE.getCode()+"-"+cereActivitySign.getSignId(),1, 60 * 1000);
            //新增延时记录
            cereRedisKeyServcice.add(StringEnum.SIGN_NOT_PAY_DELETE.getCode()+"-"+cereActivitySign.getSignId(),TimeUtils.getMinuteAfter(1));
            //生成收款码返回（BZJ-12-12）
            String formid="";
            if(IntegerEnum.ACTIVITY_COUPON.getCode().equals(param.getSignType())){
                //平台优惠券
                formid="YHQBZJ-"+cereActivitySign.getSignId();
            }else if(IntegerEnum.ACTIVITY_SECKILL.getCode().equals(param.getSignType())){
                //平台秒杀
                formid="MSBZJ-"+cereActivitySign.getSignId();
            }else if(IntegerEnum.ACTIVITY_DISCOUNT.getCode().equals(param.getSignType())){
                //平台限时折扣
                formid="XSZKBZJ-"+cereActivitySign.getSignId();
            }

            String code_url = PayFactory.getPayService(bondPaymentMode).getCollectionCode(formid, param.getBondMoney(), ip, WxPayEnum.PAY_TYPE_NATIVE.getCode());
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
                bondState.setUrl(url);
            }
        }
        return bondState;
    }

    @Override
    public void deleteById(Long signId) throws CoBusinessException {
        cereActivitySignDAO.deleteByPrimaryKey(signId);
    }

    @Override
    public void deleteDetailsBySignId(Long signId) throws CoBusinessException {
        cereSignProductService.deleteBySignId(signId);
    }

    @Override
    public void deleteLogBySignId(Long signId) throws CoBusinessException {
        //查询活动id
        CereActivitySign cereActivitySign = cereActivitySignDAO.selectByPrimaryKey(signId);
        if(cereActivitySign!=null){
            //根据商家店铺id查询商家用户id
            Long userId=cereActivitySignDAO.findShopUserId(cereActivitySign.getShopId());
            cerePlatformLogService.deleteSignActivityByOnly(cereActivitySign.getActivityId(),userId);
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleBondPayLog(String[] split, String transaction_id, String orderNo, Integer paymentMode) throws Exception{
        //修改店铺报名状态
        String time =TimeUtils.yyMMddHHmmss();
        //根据店铺id和活动id查询报名数据
        CereActivitySign cereActivitySign=cereActivitySignDAO.findBySignId(Long.parseLong(split[1]));
        if(cereActivitySign!=null){
            //更新报名状态为报名成功,保证金状态为冻结中
            cereActivitySign.setState(IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode());
            cereActivitySign.setBondState(IntegerEnum.BOND_PAY.getCode());
            cereActivitySign.setPaymentTime(time);
            cereActivitySign.setUpdateTime(time);
            cereActivitySign.setPaymentMode(paymentMode);
            cereActivitySign.setSignCode(transaction_id);
            cereActivitySignDAO.updateByPrimaryKeySelective(cereActivitySign);
            //新增支付流水
            CerePayLog payLog=new CerePayLog();
            payLog.setCreateTime(time);
            payLog.setOrderFormid(orderNo);
            payLog.setOutTradeNo(orderNo);
            //设置退款单号
            payLog.setOutRefundNo("XCXTK"+ TimeUtils.todayTime()+ RandomStringUtil.getRandomCode(10,0));
            payLog.setTransactionId(transaction_id);
            payLog.setPaymentMode(paymentMode);
            payLog.setShopId(cereActivitySign.getShopId());
            payLog.setState(StringEnum.PAY_LOG_PAY.getCode());
            payLog.setTotalFee(cereActivitySign.getBondMoney());
            payLog.setUserId(String.valueOf(cereActivitySign.getShopId()));
            payLog.setRemark("店铺id为:"+cereActivitySign.getShopId()+"的店使用微信支付了"+cereActivitySign.getBondMoney()+"元保证金,支付订单号为："+orderNo
                    +",支付时间为"+time);
            cerePayLogService.insert(payLog);
            //删除延时任务
            stringRedisService.delete(StringEnum.SIGN_NOT_PAY_DELETE.getCode()+"-"+cereActivitySign.getSignId());
            //新增延时记录
            cereRedisKeyServcice.deleteByKey(StringEnum.SIGN_NOT_PAY_DELETE.getCode()+"-"+cereActivitySign.getSignId());
        }
    }

    @Override
    public BondState checkPay(ActivitySignSaveParam param) throws CoBusinessException {
        //根据店铺id和活动id查询店铺报名保证金支付状态
        CereActivitySign cereActivitySign=cereActivitySignDAO.findByShopIdAndActivityId(param.getShopId(),param.getActivityId());
        if(cereActivitySign!=null){
            if(IntegerEnum.BOND_PAY.getCode().equals(cereActivitySign.getBondState())){
                //冻结中=已支付
                BondState bondState=new BondState();
                bondState.setCode(StringEnum.WX_PAY_SUCCESS.getCode());
                if(IntegerEnum.BOND_PAY_WEIXIN.getCode().equals(cereActivitySign.getPaymentMode())){
                    bondState.setType("微信");
                }else if(IntegerEnum.BOND_PAY_ALIPAY.getCode().equals(cereActivitySign.getPaymentMode())){
                    bondState.setType("支付宝");
                }
                bondState.setPrice(cereActivitySign.getBondMoney());
                bondState.setSignCode(cereActivitySign.getSignCode());
                return bondState;
            }
        }
        return null;
    }

    @Override
    public CereActivitySign findBySignId(Long signId) {
        return cereActivitySignDAO.findBySignId(signId);
    }

    @Override
    public Integer findActitivyState(Long activityId) {
        return cereActivitySignDAO.findActivityState(activityId);
    }

    @Override
    public BondCount getAllBond(BondParam param) throws CoBusinessException {
        BondCount count=new BondCount();
        //查询保证金总额
        count.setTotal(cereActivitySignDAO.findBondTotal(param.getShopId()));
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopBond> list=cereActivitySignDAO.getAllBond(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(shopBond -> {
                //拼接BZJ-报名id查询交易流水号
                shopBond.setTransactionId(cereActivitySignDAO.findTransactionId("BZJ-"+shopBond.getSignId()+"-"));
                //根据保证金状态设置显示缴纳时间/退保时间
                if(IntegerEnum.BOND_REFUND.getCode().equals(shopBond.getBondState())){
                    shopBond.setTime(shopBond.getReturnTime());
                }else {
                    shopBond.setTime(shopBond.getPaymentTime());
                }
            });
        }
        PageInfo<ShopBond> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        count.setPage(page);
        return count;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundBond(String[] split, String transaction_id, String orderNo) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        //保证金退款业务处理
        //根据店铺id和活动id查询报名数据
        CereActivitySign cereActivitySign=cereActivitySignDAO.findBySignId(Long.parseLong(split[1]));
        if(cereActivitySign!=null){
            //修改保证金状态为已退回
            cereActivitySign.setUpdateTime(time);
            cereActivitySign.setReturnTime(time);
            cereActivitySign.setBondState(IntegerEnum.BOND_REFUND.getCode());
            cereActivitySignDAO.updateByPrimaryKeySelective(cereActivitySign);
            //查询支付流水数据
            CerePayLog payLog=cerePayLogService.findByOrderFormid("BZJ-"+cereActivitySign.getSignId());
            if(payLog!=null){
                //新增退款流水
                CerePayLog cerePayLog=new CerePayLog();
                cerePayLog.setCreateTime(time);
                cerePayLog.setOrderFormid(orderNo);
                cerePayLog.setOutTradeNo(orderNo);
                cerePayLog.setOutRefundNo(payLog.getOutRefundNo());
                cerePayLog.setTransactionId(transaction_id);
                cerePayLog.setRefundFee(payLog.getRefundFee());
                cerePayLog.setPaymentMode(IntegerEnum.ORDER_PAY_WX.getCode());
                cerePayLog.setShopId(payLog.getShopId());
                cerePayLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
                cerePayLog.setTotalFee(payLog.getTotalFee());
                cerePayLog.setUserId(payLog.getUserId());
                cerePayLog.setRemark(payLog.getOrderFormid()+"保证金退款"+payLog.getRefundFee()+"元");
                cerePayLogService.insert(cerePayLog);
                //清除延时任务和延时记录
                //查询当前活动是否为进行中
                Integer state=findActitivyState(cereActivitySign.getActivityId());
                if(IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode().equals(cereActivitySign.getState())){
                    //如果是报名成功说明退款是走的活动结束延时任务
                    stringRedisService.delete(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+cereActivitySign.getSignType());
                    cereRedisKeyServcice.deleteByKey(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+cereActivitySign.getSignType());
                }else {
                    //报名失败退款
                    if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
                        //如果是活动进行中说明当前退款是走的活动刚开始延时任务退款
                        stringRedisService.delete(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+cereActivitySign.getSignType());
                        cereRedisKeyServcice.deleteByKey(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+cereActivitySign.getSignType());
                    }else {
                        //报名失败
                        stringRedisService.delete(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+split[1]+"-"+cereActivitySign.getSignType());
                        cereRedisKeyServcice.deleteByKey(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+split[1]+"-"+cereActivitySign.getSignType());
                    }
                }
            }
        }
    }

    @Override
    public List<Product> getById(Long signId) throws CoBusinessException {
        return cereActivitySignDAO.getById(signId);
    }

    @Override
    public Page getProducts(ActivitySignGetProductParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Product> products = cereActivitySignDAO.getProducts(param);
        PageInfo<Product> pageInfo=new PageInfo<>(products);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public List<CereShopGroup> getGroups(Long shopId) throws CoBusinessException{
        return cereActivitySignDAO.getGroups(shopId);
    }

    @Override
    public ActivitySignDetail getActivitySignDetail(Long activityId, CerePlatformBusiness user) {
        CereActivitySign cereActivitySign=cereActivitySignDAO.findByShopIdAndActivityId(user.getShopId(), activityId);
        if (cereActivitySign != null) {
            List<Long> productIdList = cereSignProductDAO.selectProductListBySignId(cereActivitySign.getSignId());
            ActivitySignDetail detail = new ActivitySignDetail();
            BeanUtils.copyProperties(cereActivitySign, detail);
            detail.setProductIdList(productIdList);
            return detail;
        }
        return null;
    }

    @Override
    public ActivityData getDatas(ActivityGetDataParam param) throws CoBusinessException {
        ActivityData data=new ActivityData();
        //设置成交笔数
        data.setOrders(cereActivitySignDAO.findOrders(param));
        //设置成交人数
        data.setUsers(cereActivitySignDAO.findUsers(param));
        //设置成交金额
        data.setTotal(cereActivitySignDAO.findTotal(param));
        //查询商品明细
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ActivityProductData> list=cereActivitySignDAO.findActivityProducts(param);
        if(!EmptyUtils.isEmpty(list)){
            list.forEach(product -> {
                //设置商品活动销量
                product.setValume(cereActivitySignDAO.findValume(product.getProductId(),param.getActivityId(),param.getSignType()));
                //设置活动成交金额
                product.setTotal(cereActivitySignDAO.findProductTotal(product.getProductId(),param.getActivityId(),param.getSignType()));
                if(IntegerEnum.COUPON_TYPE_DISCOUNT.getCode().equals(product.getDiscountMode())){
                    //设置折扣
                    BigDecimal discount=product.getCouponContent().divide(BigDecimal.TEN,2,BigDecimal.ROUND_HALF_UP);
                    //设置折扣价区间
                    product.setSectionPrice("￥"+ product.getMinPrice().multiply(discount).setScale(2,BigDecimal.ROUND_HALF_UP)+"~￥"+
                            product.getMaxPrice().multiply(discount).setScale(2,BigDecimal.ROUND_HALF_UP));
                }else if(IntegerEnum.COUPON_TYPE_REDUTION.getCode().equals(product.getDiscountMode())) {
                    //设置满减价区间
                    product.setSectionPrice("￥"+ product.getMinPrice().subtract(product.getCouponContent())+"~￥"+
                            product.getMaxPrice().multiply(product.getCouponContent()));
                }
            });
        }
        PageInfo<ActivityProductData> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        data.setDatas(page);
        return data;
    }
}
