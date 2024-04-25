/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.after.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.after.CereOrderAfterDAO;
import com.shop.cereshop.admin.dao.after.CerePlatformAfterDAO;
import com.shop.cereshop.admin.dao.order.CereShopOrderDAO;
import com.shop.cereshop.admin.page.after.*;
import com.shop.cereshop.admin.param.after.AfterGetAllParam;
import com.shop.cereshop.admin.param.after.AfterGetDileveryParam;
import com.shop.cereshop.admin.param.after.AfterhandleParam;
import com.shop.cereshop.admin.pay.PayFactory;
import com.shop.cereshop.admin.redis.service.api.StringRedisService;
import com.shop.cereshop.admin.service.after.CereOrderAfterService;
import com.shop.cereshop.admin.service.dict.CerePlatformDictService;
import com.shop.cereshop.admin.service.express.KuaiDi100Service;
import com.shop.cereshop.admin.service.express.KuaiDiNiaoService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.order.CereOrderReconciliationService;
import com.shop.cereshop.admin.service.order.CereShopOrderService;
import com.shop.cereshop.admin.service.pay.CerePayLogService;
import com.shop.cereshop.admin.service.product.CereProductSkuService;
import com.shop.cereshop.commons.constant.*;
import com.shop.cereshop.commons.domain.after.CereOrderAfter;
import com.shop.cereshop.commons.domain.after.CerePlatformAfter;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.express.ShippingTrace;
import com.shop.cereshop.commons.domain.order.CereOrderProduct;
import com.shop.cereshop.commons.domain.order.CereOrderReconciliation;
import com.shop.cereshop.commons.domain.order.CereShopOrder;
import com.shop.cereshop.commons.domain.pay.CerePayLog;
import com.shop.cereshop.commons.domain.product.CereProductSku;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CereOrderAfterServiceImpl implements CereOrderAfterService {

    @Autowired
    private CereOrderAfterDAO cereOrderAfterDAO;

    @Autowired
    private CerePlatformAfterDAO cerePlatformAfterDAO;

    @Autowired
    private CereShopOrderService cereShopOrderService;

    @Autowired
    private KuaiDiNiaoService kuaiDiNiaoService;

    @Autowired
    private KuaiDi100Service kuaiDi100Service;

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    private CereProductSkuService cereProductSkuService;

    @Autowired
    private CerePayLogService cerePayLogService;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private CereOrderReconciliationService cereOrderReconciliationService;

    @Autowired
    private CereShopOrderDAO cereShopOrderDAO;

    @Override
    public Page getAll(AfterGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<After> list=cereOrderAfterDAO.getAll(param);
        PageInfo<After> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public AfterDetail getById(Long afterId) throws CoBusinessException,Exception {
        //查询售后数据
        AfterDetail detail=cereOrderAfterDAO.getById(afterId);
        if(detail!=null){
            //设置合同有效日期
            detail.setEffectiveTime(TimeUtils.rollYear(detail.getEffectiveDate(), detail.getEffectiveYear()));
            //查询商品明细数据
            detail.setProducts(cereOrderAfterDAO.findProducts(afterId));
            //查询协商历史
            List<AfterHistory> histories=cereOrderAfterDAO.findHistories(afterId);
            if(!EmptyUtils.isEmpty(histories)){
                histories.forEach(afterHistory -> {
                    //封装图片数组
                    afterHistory.setImages(EmptyUtils.getImages(afterHistory.getImage()));
                    if(afterHistory.getTitle().contains("买家发起了")){
                        afterHistory.setReason(detail.getExplain());
                    }else {
                        afterHistory.setReason(detail.getReason());
                    }
                });
            }
            detail.setAfterHistory(histories);
        }
        return detail;
    }

    @Override
    public AfterBuyer getBuyer(Long buyerUserId) throws CoBusinessException {
        AfterBuyer buyer = cereOrderAfterDAO.getBuyer(buyerUserId);
        if(buyer!=null){
            //查询售后成功的售后单数量
            Integer number=cereOrderAfterDAO.findSuccessAfter(buyerUserId);
            if(number>0){
                //计算售后成功率=成功的售后数量/总的售后单数
                buyer.setRate(new BigDecimal(number/buyer.getAfters()).
                        setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            }
        }
        return buyer;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void handle(AfterhandleParam param, CerePlatformUser user) throws CoBusinessException,Exception {
        String time=TimeUtils.yyMMddHHmmss();
        CerePlatformAfter cerePlatformAfter=new CerePlatformAfter();
        cerePlatformAfter.setOrderId(param.getOrderId());
        cerePlatformAfter.setState(param.getState());
        cerePlatformAfter.setRemark(param.getRemark());
        //更新平台介入处理数据
        cerePlatformAfter.setHandleTime(time);
        cerePlatformAfterDAO.update(cerePlatformAfter);
        //根据售后单id查询售后单数据
        CereOrderAfter cereOrderAfter=cereOrderAfterDAO.selectByPrimaryKey(param.getAfterId());
        if(cereOrderAfter!=null){
            CereShopOrder cereShopOrder=new CereShopOrder();
            cereShopOrder.setOrderId(cerePlatformAfter.getOrderId());
            cereOrderAfter.setUpdateTime(time);
            if(IntegerEnum.AFTER_ADMIN_SUCCESS.getCode().equals(cerePlatformAfter.getState())){
                //同意售后
                if(IntegerEnum.AFTER_REFUND.getCode().equals(cereOrderAfter.getAfterType())){
                    //如果是仅退款,将自动从商家账户退款给买家,修改售后单状态为退款中
                    cereOrderAfter.setAfterState(IntegerEnum.AFTER_REFUND_STAY.getCode());
                    //根据售后单id查询订单支付信息和订单总金额
                    AfterRefund refund=cereOrderAfterDAO.findOrderPay(cereOrderAfter.getAfterId());
                    //调用退款接口
                    if(refund!=null){
                        //测试假退款处理
//                        handleWxLog(refund.getOrderFormid(),refund.getTransactionId(),refund.getOutTradeNo(),param.getAfterId());
                        //更新支付流水售后单id字段
                        CerePayLog payLog=cerePayLogService.findByOrderFormid(refund.getOrderFormid());
                        payLog.setId(refund.getId());
                        if(!EmptyUtils.isEmpty(refund.getAfter())){
                            //拼接售后单id
                            payLog.setAfter(refund.getAfter()+","+param.getAfterId());
                        }else {
                            //第一次售后退款
                            payLog.setAfter(String.valueOf(param.getAfterId()));
                        }
                        Map<String, String> resultMap = PayFactory.getPayService(payLog.getPaymentMode()).refund(refund.getTransactionId(),
                                refund.getOutRefundNo(), refund.getOrderPrice(), refund.getPrice());
                        if(!EmptyUtils.isEmpty(resultMap)){
                            if(resultMap.get("return_msg").equals(WxPayEnum.REFUND_SUCCESS.getCode())&&
                                    resultMap.get("return_code").equals(WxPayEnum.REFUND_OK.getCode())){
                                //退款成功 支付宝的退款是立即退款的，需要同步处理
                                if (IntegerEnum.ORDER_PAY_ALI.getCode().equals(payLog.getPaymentMode())) {
                                    CereShopOrder order = cereShopOrderDAO.selectByPrimaryKey(cereOrderAfter.getOrderId());
                                    handleRefundLog(order, refund.getTransactionId(), refund.getOutRefundNo(), refund.getOutTradeNo());
                                }
                            }else if(resultMap.get("return_code").equals(WxPayEnum.BUSINESS_BALANCE_NOTENOUGH.getCode())){
                                //退款失败,商户余额不足
                                throw new CoBusinessException(CoReturnFormat.BUSINESS_BALANCE_NOT);
                            }
                        }
                    }
                }else {
                    //如果是退货退款,修改售后单状态为审核通过
                    cereOrderAfter.setAfterState(IntegerEnum.AFTER_STAY_YES.getCode());
                }
            }else {
                //拒绝售后,修改售后单状态为已关闭
                cereOrderAfter.setAfterState(IntegerEnum.AFTER_CANCEL.getCode());
            }
            cereOrderAfterDAO.updateByPrimaryKeySelective(cereOrderAfter);
            cereShopOrder.setUpdateTime(time);
            cereShopOrderService.updateAfterState(cereShopOrder);
        }
        String describe="";
        if(IntegerEnum.AFTER_ADMIN_SUCCESS.getCode().equals(cerePlatformAfter.getState())){
            describe="同意售后";
        }else {
            describe="拒绝售后";
        }
        //新增日志
        cerePlatformLogService.addLog(user,"售后管理","平台端操作",describe,String.valueOf(param.getAfterId()),time);
    }

    public void handleWxLog(String orderFormid, String transaction_id, String orderNo,Long afterId) throws CoBusinessException {
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
            if(cereOrderAfter!=null){
                //修改售后单状态为退款成功
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
                        int stockNumber=cereProductSkuService.findStockNumber(sku.getSkuId());
                        if(!EmptyUtils.isEmpty(stringRedisService.get(String.valueOf(sku.getSkuId())))){
                            stockNumber=(int) stringRedisService.get(String.valueOf(sku.getSkuId()));
                        }
                        //更新redis商品库存
                        stringRedisService.set(String.valueOf(sku.getSkuId()),stockNumber+sku.getStockNumber());
                    });
                    cereShopOrderService.updateBatchStock(skus);
                }
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
    public List<AfterDilevery> getDilevery(AfterGetDileveryParam param) throws CoBusinessException,Exception {
        List<AfterDilevery> list=new ArrayList<>();
        //查询物流查询策略
        Integer express = cereOrderAfterDAO.findExpress();
        String code="";
        if(IntegerEnum.EXPRESS_100.getCode().equals(express)){
            //查询快递公司编码
            code=cerePlatformDictService.findByCompany(param.getExpress(), LongEnum.EXPRESS_100.getCode());
            //通过快递100查询物流轨迹
            List<ShippingTrace> traces = kuaiDi100Service.findTraces(code, param.getDeliverFormid());
            if(!EmptyUtils.isEmpty(traces)){
                list = traces.stream()
                        .map(a -> {
                            AfterDilevery dilevery = new AfterDilevery();
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
    public void insert(CereOrderAfter cereOrderAfter) throws CoBusinessException {
        cereOrderAfterDAO.insert(cereOrderAfter);
    }

    /**
     * 处理支付宝退款成功结果
     */
    private void handleRefundLog(CereShopOrder order, String transactionId, String outRefundNo, String outTradeNo) throws CoBusinessException {
        String time = TimeUtils.yyMMddHHmmss();
        //查询订单数据
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
            //CerePayLog cerePayLog=cereShopOrderDAO.findPayLog(orderFormId);
            //插入退款流水
            CerePayLog payLog=new CerePayLog();
            payLog.setCreateTime(time);
            payLog.setOrderFormid(order.getOrderFormid());
            payLog.setOutTradeNo(outTradeNo);
            payLog.setTransactionId(transactionId);
            payLog.setOutRefundNo(outRefundNo);
            payLog.setPaymentMode(order.getPaymentMode());
            payLog.setShopId(order.getShopId());
            payLog.setState(StringEnum.PAY_LOG_REFUND.getCode());
            payLog.setTotalFee(order.getPrice());
            payLog.setUserId(String.valueOf(order.getBuyerUserId()));
            payLog.setRemark(order.getCustomerName()+"取消订单退款"+order.getPrice()+"元,退款单号为："+outRefundNo
                    +",退款时间为"+time);
            cerePayLogService.insert(payLog);
        }
    }
}
