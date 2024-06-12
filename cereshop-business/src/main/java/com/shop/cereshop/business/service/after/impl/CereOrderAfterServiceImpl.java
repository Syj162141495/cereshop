/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.service.after.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.business.dao.after.CereOrderAfterDAO;
import com.shop.cereshop.business.page.after.After;
import com.shop.cereshop.business.page.after.AfterHistory;
import com.shop.cereshop.business.page.after.Refund;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.page.order.ShopOrder;
import com.shop.cereshop.business.page.pay.PayLog;
import com.shop.cereshop.business.param.after.AfterGetAllParam;
import com.shop.cereshop.business.param.after.AfterIdParam;
import com.shop.cereshop.business.pay.PayFactory;
import com.shop.cereshop.business.pay.PayService;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.after.CereOrderAfterService;
import com.shop.cereshop.business.service.buyer.CereBuyerUserService;
import com.shop.cereshop.business.service.log.CerePlatformLogService;
import com.shop.cereshop.business.service.order.CereOrderReconciliationService;
import com.shop.cereshop.business.service.order.CereShopOrderService;
import com.shop.cereshop.business.service.pay.CerePayLogService;
import com.shop.cereshop.business.service.product.CereProductSkuService;
import com.shop.cereshop.business.service.redis.CereRedisKeyServcice;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.constant.WxPayEnum;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.order.CereOrderReconciliation;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.SpringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CereOrderAfterServiceImpl implements CereOrderAfterService {

    @Autowired
    private CereOrderAfterDAO cereOrderAfterDAO;

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereRedisKeyServcice cereRedisKeyServcice;

    @Autowired
    private CereProductSkuService cereProductSkuService;

    @Autowired
    private CerePayLogService cerePayLogService;

    @Autowired
    private CereOrderReconciliationService cereOrderReconciliationService;

    @Autowired
    private CereBuyerUserService cereBuyerUserService;

    @Override
    public Page getAll(AfterGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<After> list=cereOrderAfterDAO.getAll(param);
        PageInfo<After> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public After getById(Long afterId) throws CoBusinessException {
        After after=cereOrderAfterDAO.getById(afterId);
        if(after!=null){
            if(!EmptyUtils.isEmpty(after.getImage())){
                after.setImages(EmptyUtils.getImages(after.getImage()));
            }
            //查询商品信息
            List<Product> products=cereOrderAfterDAO.findProducts(afterId);
            if(!EmptyUtils.isEmpty(products)){
                //封装规格属性数据
                products.forEach(product -> product.setSkuDetails(cereOrderAfterDAO.findSkuAttribute(product.getAfterProductId())));
            }
            //查询协商历史
            List<AfterHistory> histories=cereOrderAfterDAO.findHistories(afterId);
            if(!EmptyUtils.isEmpty(histories)){
                histories.forEach(afterHistory -> {
                    //封装图片数组
                    afterHistory.setImages(EmptyUtils.getImages(afterHistory.getImage()));
                    if(afterHistory.getTitle().contains("买家发起了")){
                        afterHistory.setReason(after.getExplain());
                    }else {
                        afterHistory.setReason(after.getReason());
                    }
                });
            }
            after.setAfterHistory(histories);
            after.setProducts(products);
        }
        return after;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundSuccess(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //同意退款申请,修改售后单状态为退款中
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //根据售后单id查询订单支付信息和订单总金额
        Refund refund=cereOrderAfterDAO.findOrderPay(param.getAfterId());
        //调用退款接口
        if(refund!=null){
            //更新支付流水after字段
            CerePayLog cerePayLog=new CerePayLog();
            cerePayLog.setId(refund.getId());
            if(!EmptyUtils.isEmpty(refund.getAfter())){
                //不是第一次退款,重新设置退款单号
                if(refund.getOutTradeNo().contains("XCX")){
                    //小程序支付
                    refund.setOutRefundNo("XCXTK"+ TimeUtils.todayTime()+refund.getOrderFormid());
                }else if(refund.getOutTradeNo().contains("APP")){
                    //APP支付
                    refund.setOutRefundNo("APPTK"+ TimeUtils.todayTime()+refund.getOrderFormid());
                }
                cerePayLog.setAfter(refund.getAfter()+","+param.getAfterId());
            }else {
                cerePayLog.setAfter(String.valueOf(param.getAfterId()));
            }
            cerePayLogService.update(cerePayLog);
            CereShopOrder order = cereShopOrderService.findById(param.getOrderId());
//            handleWxLogTest(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo(),param.getAfterId());
            PayService payService = PayFactory.getPayService(order.getPaymentMode());
            Map<String, String> resultMap = payService.refund(refund.getOutTradeNo(), refund.getTransactionId(), refund.getOutRefundNo(), refund.getOrderPrice(), refund.getPrice(), param.getAfterId());
            if(!EmptyUtils.isEmpty(resultMap)){
                if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                        resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                    //退款成功 支付宝的退款是立即退款的，需要同步处理
                    if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                            IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(order.getPaymentMode())) {
                        //内部调用不会执行事务，所以通过springUtil获取service对象
                        CereOrderAfterService afterService = SpringUtil.getBean(CereOrderAfterService.class);
                        afterService.handleRefundSuc(refund.getOutRefundNo(), refund.getTransactionId(), refund.getOutTradeNo(), order.getPaymentMode());
                    }
                }else if(resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())){
                    //退款失败,商户余额不足
                    throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                }
            }
        }
        //删除自动确认收货延时任务
        stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        //清空自动关闭售后延时任务和任务记录
        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
        cereRedisKeyServcice.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","仅退款同意退款申请",param.getAfterId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundRefuse(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //拒绝退款申请,修改售后单状态为审核不通过
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_NO.getCode());
        cereOrderAfter.setReason(param.getReason());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","仅退款拒绝退款申请",param.getAfterId(),time);
        //重新开启自动确认收货延时任务
        String end =cereRedisKeyServcice.findByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        if(!EmptyUtils.isEmpty(end)){
            stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),TimeUtils.getCountDownByTime(time,end));
        }
        //清空自动关闭售后延时任务和任务记录
        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
        cereRedisKeyServcice.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void success(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //同意退货退款申请,修改售后单状态为审核通过
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_YES.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","退货退款同意申请",param.getAfterId(),time);
        //删除自动确认收货延时任务
        stringRedisService.delete(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        cereRedisKeyServcice.deleteByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        //清空自动关闭售后延时任务和任务记录
        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
        cereRedisKeyServcice.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refuse(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        String time= TimeUtils.yyMMddHHmmss();
        //拒绝退货退款申请,修改售后单状态为审核不通过
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_NO.getCode());
        cereOrderAfter.setReason(param.getReason());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","退货退款拒绝申请,拒绝原因："+
                param.getReason(),param.getAfterId(),time);
        //重新开启自动确认收货延时任务
        String end =cereRedisKeyServcice.findByKey(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId());
        if(!EmptyUtils.isEmpty(end)){
            stringRedisService.set(StringEnum.ORDER_CONFIRM_DILEVERY.getCode()+"-"+param.getOrderId(),TimeUtils.getCountDownByTime(time,end));
        }
        //清空自动关闭售后延时任务和任务记录
        stringRedisService.delete(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
        cereRedisKeyServcice.deleteByKey(StringEnum.AFTER_CANCEL.getCode()+"-"+param.getAfterId());
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void confirmAndRefund(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException,Exception {
        //资金将退回买家的支付账户，售后单状态变为售后成功，买家端变为已退款
        String time= TimeUtils.yyMMddHHmmss();
        //确认收货且退款,修改售后单状态为退款中
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_STAY.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        //根据售后单id查询订单支付信息和订单总金额
        Refund refund=cereOrderAfterDAO.findOrderPay(param.getAfterId());
        //调用退款接口
        if(refund!=null){
            //测试使用=退款
//            handleWxLogTest(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo(),param.getAfterId());
            //更新支付流水售后单id字段
            CerePayLog payLog=new CerePayLog();
            payLog.setId(refund.getId());
            if(!EmptyUtils.isEmpty(refund.getAfter())){
                //不是第一次退款,重新设置退款单号
                if(refund.getOutTradeNo().contains("XCX")){
                    //小程序支付
                    refund.setOutRefundNo("XCXTK"+ TimeUtils.todayTime()+refund.getOrderFormid());
                }else if(refund.getOutTradeNo().contains("APP")){
                    //APP支付
                    refund.setOutRefundNo("APPTK"+ TimeUtils.todayTime()+refund.getOrderFormid());
                }
                //拼接售后单id
                payLog.setAfter(refund.getAfter()+","+param.getAfterId());
            }else {
                //第一次售后退款
                payLog.setAfter(String.valueOf(param.getAfterId()));
            }
            cerePayLogService.update(payLog);
            CereShopOrder order = cereShopOrderService.findById(param.getOrderId());
            PayService payService = PayFactory.getPayService(order.getPaymentMode());
            Map<String, String> resultMap = payService.refund(refund.getOutTradeNo(), refund.getTransactionId(), refund.getOutRefundNo(), refund.getOrderPrice(), refund.getPrice(), param.getAfterId());
            if(!EmptyUtils.isEmpty(resultMap)){
                if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                        resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                    //退款成功 支付宝的退款是立即退款的，需要同步处理
                    if (Arrays.asList(IntegerEnum.ORDER_PAY_ALI.getCode(),
                            IntegerEnum.ORDER_PAY_HUABEI.getCode()).contains(order.getPaymentMode())) {
                        //内部调用不会执行事务，所以通过springUtil获取service对象
                        CereOrderAfterService afterService = SpringUtil.getBean(CereOrderAfterService.class);
                        afterService.handleRefundSuc(refund.getOutRefundNo(), refund.getTransactionId(), refund.getOutTradeNo(), order.getPaymentMode());
                    }
                }else if(resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())){
                    //退款失败,商户余额不足
                    throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                }
            }
        }
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","退货退款确认收货且退款",param.getAfterId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void damaging(AfterIdParam param, CerePlatformBusiness user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        //货物有损拒绝退款,修改售后单状态改为退货完成,拒绝退款
        CereOrderAfter cereOrderAfter=new CereOrderAfter();
        cereOrderAfter.setAfterId(param.getAfterId());
        cereOrderAfter.setAfterState(IntegerEnum.AFTER_RETURN_SUCCESS_NOT_REFUND.getCode());
        cereOrderAfter.setUpdateTime(time);
        cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        if(EmptyUtils.isEmpty(param.getReason())){
            param.setReason("无");
        }
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","商户端操作","退货退款货物有损拒绝退款,拒绝原因："+
                param.getReason(),param.getAfterId(),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handleRefundSuc(String refundNo, String transaction_id, String orderNo, Integer paymentMode) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        //根据退款单号查询支付流水
        PayLog payLog=cerePayLogService.findByOutRefundNo(refundNo);
        if(payLog!=null){
            Long afterId=null;
            //截取最新售后单id
            if(!EmptyUtils.isEmpty(payLog.getAfter())){
                if(payLog.getAfter().contains(",")){
                    String[] split = payLog.getAfter().split(",");
                    afterId=Long.parseLong(split[split.length-1]);
                }else {
                    afterId=Long.parseLong(payLog.getAfter());
                }
            }
            //查询售后单数据
            CereOrderAfter cereOrderAfter=cereOrderAfterDAO.findById(afterId);
            if(cereOrderAfter!=null){
                //修改售后单状态为退款成功
                cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_SUCCESS.getCode());
                cereOrderAfter.setAfterId(afterId);
                cereOrderAfter.setUpdateTime(time);
                cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
                //查询订单是否有其他未退款商品(退款成功的商品才算退款商品)
                List<CereOrderProduct> list=cereOrderAfterDAO.findOtherProductsByAfterId(payLog.getOrderId(),afterId);
                CereShopOrder cereShopOrder=new CereShopOrder();
                cereShopOrder.setOrderId(payLog.getOrderId());
                cereShopOrder.setUpdateTime(time);
                if(EmptyUtils.isEmpty(list)){
                    //如果没有其他未退款商品,订单整体商品售后成功，订单状态流转为已取消
                    cereShopOrder.setState(IntegerEnum.ORDER_STOP.getCode());
                }
                cereShopOrderService.updateState(cereShopOrder);
                //查询售后单商品数据,更新对应商品库存
                List<CereProductSku> skus=cereOrderAfterDAO.findAfterSkus(afterId);
                if(!EmptyUtils.isEmpty(skus)){
                    skus.forEach(sku -> {
                        int stockNumber=cereProductSkuService.findStockNumber(sku.getSkuId());
                        if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                            stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                        }
                        //更新redis商品库存
                        stringRedisService.set(String.valueOf(sku.getSkuId()),stockNumber+sku.getStockNumber());
                    });
                    cereShopOrderService.updateBatchStock(skus);
                }
                //新增对账单数据
                CereOrderReconciliation reconciliation=new CereOrderReconciliation();
                reconciliation.setOrderId(cereOrderAfter.getOrderId());
                reconciliation.setMoney(cereOrderAfter.getPrice());
                reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
                reconciliation.setType(IntegerEnum.RELATIONSHIP_ONCOME.getCode());
                reconciliation.setCreateTime(time);
                cereOrderReconciliationService.insert(reconciliation);
                //新增退款流水
                CerePayLog cerePayLog=new CerePayLog();
                cerePayLog.setCreateTime(time);
                cerePayLog.setOrderFormid(orderNo);
                cerePayLog.setOutTradeNo(orderNo);
                cerePayLog.setOutRefundNo(refundNo);
                cerePayLog.setTransactionId(transaction_id);
                cerePayLog.setRefundFee(cereOrderAfter.getPrice());
                cerePayLog.setPaymentMode(paymentMode);
                cerePayLog.setShopId(payLog.getShopId());
                cerePayLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
                cerePayLog.setTotalFee(payLog.getTotalFee());
                cerePayLog.setUserId(payLog.getUserId());
                cerePayLog.setRemark(payLog.getOrderFormid()+"订单退款"+cereOrderAfter.getPrice()+"元");
                cerePayLogService.insert(cerePayLog);
                //扣减成长值
                // 需要在payLog中增加一个queryType属性
                // ShopOrder shopOrder = cereShopOrderService.getById(payLog.getOrderId());
//                if (shopOrder != null) {
//                    cereBuyerUserService.updateGrowth(shopOrder.getBuyerUserId(), payLog.getTotalFee().setScale(0, BigDecimal.ROUND_UP).negate().intValue());
//                }
            }
        }
    }

    public void handleWxLogTest(String orderFormid, String transaction_id, String orderNo,Long afterId) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        //根据订单id查询支付流水
        PayLog payLog=cerePayLogService.findByOrderFormid(orderFormid);
        if(payLog!=null){
            //截取最新售后单id
            if(!EmptyUtils.isEmpty(payLog.getAfter())){
                if(payLog.getAfter().contains(",")){
                    String[] split = payLog.getAfter().split(",");
                    afterId=Long.parseLong(split[split.length-1]);
                }else {
                    afterId=Long.parseLong(payLog.getAfter());
                }
            }else {
                //更新订单支付流水
                CerePayLog cerePayLog=new CerePayLog();
                cerePayLog.setId(payLog.getId());
                cerePayLog.setAfter(String.valueOf(afterId));
                cerePayLogService.update(cerePayLog);
            }
            //查询售后订单数据
            CereOrderAfter cereOrderAfter=cereOrderAfterDAO.findById(afterId);
            //修改售后单状态为退款成功
            if(cereOrderAfter!=null){
                cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_SUCCESS.getCode());
                cereOrderAfter.setAfterId(afterId);
                cereOrderAfter.setUpdateTime(time);
                cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
                //查询订单是否有其他未退款商品
                List<CereOrderProduct> list=cereOrderAfterDAO.findOtherProductsByAfterId(payLog.getOrderId(),afterId);
                CereShopOrder cereShopOrder=new CereShopOrder();
                cereShopOrder.setOrderId(payLog.getOrderId());
                cereShopOrder.setUpdateTime(time);
                if(EmptyUtils.isEmpty(list)){
                    //如果没有其他未退款商品,订单整体商品售后成功，订单状态流转为已取消
                    cereShopOrder.setState(IntegerEnum.ORDER_STOP.getCode());
                }
                cereShopOrderService.updateState(cereShopOrder);
                //查询售后单商品数据,更新对应商品库存
                List<CereProductSku> skus=cereOrderAfterDAO.findAfterSkus(afterId);
                if(!EmptyUtils.isEmpty(skus)){
                    skus.forEach(sku -> {
                        int stockNumber = cereProductSkuService.findStockNumber(sku.getSkuId());
                        if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                            stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                        }
                        //更新redis商品库存
                        stringRedisService.set(String.valueOf(sku.getSkuId()),stockNumber+sku.getStockNumber());
                    });
                    cereShopOrderService.updateBatchStock(skus);
                }
                //新增对账单数据
                CereOrderReconciliation reconciliation=new CereOrderReconciliation();
                reconciliation.setOrderId(cereOrderAfter.getOrderId());
                reconciliation.setMoney(cereOrderAfter.getPrice());
                reconciliation.setState(IntegerEnum.RELATIONSHIP_SOLVE_FROZEN.getCode());
                reconciliation.setType(IntegerEnum.RELATIONSHIP_ONCOME.getCode());
                reconciliation.setCreateTime(time);
                cereOrderReconciliationService.insert(reconciliation);
                //新增退款流水
                CerePayLog cerePayLog=new CerePayLog();
                cerePayLog.setCreateTime(time);
                cerePayLog.setOrderFormid(orderNo);
                cerePayLog.setOutTradeNo(orderNo);
                cerePayLog.setOutRefundNo(payLog.getOutRefundNo());
                cerePayLog.setTransactionId(transaction_id);
                cerePayLog.setRefundFee(cereOrderAfter.getPrice());
                cerePayLog.setPaymentMode(IntegerEnum.ORDER_PAY_WX.getCode());
                cerePayLog.setShopId(payLog.getShopId());
                cerePayLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
                cerePayLog.setTotalFee(payLog.getTotalFee());
                cerePayLog.setUserId(payLog.getUserId());
                cerePayLog.setRemark(payLog.getOrderFormid()+"订单退款"+cereOrderAfter.getPrice()+"元");
                cerePayLogService.insert(cerePayLog);
            }
        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void refundError(String outRefundNo) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        //根据订单id查询支付流水
        PayLog payLog=cerePayLogService.findByOutRefundNo(outRefundNo);
        if(payLog!=null){
            String afterId="";
            //截取最新售后单id
            if(payLog.getAfter().contains(",")){
                String[] split = payLog.getAfter().split(",");
                afterId=split[split.length-1];
            }else {
                afterId=payLog.getAfter();
            }
            //修改售后单状态为退款失败
            CereOrderAfter cereOrderAfter=new CereOrderAfter();
            cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_ERRPR.getCode());
            cereOrderAfter.setAfterId(Long.parseLong(afterId));
            cereOrderAfter.setUpdateTime(time);
            cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
        }
    }

}
