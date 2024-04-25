/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.controller;

import com.shop.cereshop.business.annotation.NoRepeatSubmit;
import com.shop.cereshop.business.annotation.NoRepeatWebLog;
import com.shop.cereshop.business.page.activity.ActivityData;
import com.shop.cereshop.business.page.activity.ActivitySign;
import com.shop.cereshop.business.page.activity.ActivitySignDetail;
import com.shop.cereshop.business.page.activity.BondState;
import com.shop.cereshop.business.page.order.Product;
import com.shop.cereshop.business.param.activity.*;
import com.shop.cereshop.business.pay.weixin.skd.HttpKit;
import com.shop.cereshop.business.pay.weixin.skd.PaymentKit;
import com.shop.cereshop.business.redis.service.api.StringRedisService;
import com.shop.cereshop.business.service.activity.CereActivitySignService;
import com.shop.cereshop.business.utils.ContextUtil;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.constant.StringEnum;
import com.shop.cereshop.commons.domain.activity.CereActivitySign;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.shop.CereShopGroup;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.AppletPayUtil;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.GsonUtil;
import com.shop.cereshop.commons.utils.PayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 营销活动
 */
@RestController
@RequestMapping("activity")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "ActivitySignController")
@Api(value = "营销活动模块", tags = "营销活动模块")
public class ActivitySignController {

    @Autowired
    private CereActivitySignService cereActivitySignService;

    @Autowired
    private StringRedisService stringRedisService;

    /**
     * 商户秘钥
     */
    @Value("${weixin.key}")
    private String key;

    /**
     * 营销活动管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "营销活动管理查询")
    public Result<Page<ActivitySign>> getAll(@RequestBody ActivitiSignGetAllParam param,HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        param.setShopId(user.getShopId());
        Page page=cereActivitySignService.getAll(param);
        return new Result(page);
    }

    /**
     * 报名
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "报名")
    @NoRepeatWebLog
    public Result save(@RequestBody ActivitySignSaveParam param, HttpServletRequest request) throws CoBusinessException,Exception{
        //获取当前登录账户
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        String ip= AppletPayUtil.getClientIp(request);
        param.setShopId(ContextUtil.getShopId());
        BondState bondState=cereActivitySignService.save(param,user,ip);
        return new Result(bondState,user.getUsername(),"报名", GsonUtil.objectToGson(param));
    }

    /**
     * 保证金支付状态查询
     * @param param
     * @return
     */
    @RequestMapping(value = "checkPay",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "保证金支付状态查询")
    public Result checkPay(@RequestBody ActivitySignSaveParam param) throws CoBusinessException,Exception{
        param.setShopId(ContextUtil.getShopId());
        BondState code=cereActivitySignService.checkPay(param);
        return new Result(code);
    }

    /**
     * 保证金微信扫码支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value={"pay/rolBack"}, method = RequestMethod.POST)
    public void wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("进入保证金支付回调");
        String xmlMsg = HttpKit.readData(request);
        log.info("微信小程序通知信息"+xmlMsg);
        Map<String, String> resultMap = PaymentKit.xmlToMap(xmlMsg);
        if(!EmptyUtils.isEmpty(resultMap)){
            if(resultMap.get(StringEnum.WX_RETURN_CODE.getCode()).equals(StringEnum.WX_PAY_SUCCESS.getCode())){
                String orderNo = resultMap.get("out_trade_no");
                log.info("回调out_trade_no="+orderNo);
                log.info("保证金支付回调成功");
                //截取订单编号
                String[] split = orderNo.split("-");
                if(!EmptyUtils.isEmpty(split)){
                    cereActivitySignService.handleBondPayLog(split,resultMap.get("transaction_id"),orderNo,IntegerEnum.BOND_PAY_WEIXIN.getCode());
                }
            }
        }else {
            log.info("map为null");
        }
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response.getWriter().write(result);
    }

    /**
     * 保证金微信扫码支付回调
     * @param request
     * @throws Exception
     */
    @RequestMapping(value={"alipay/rolBack"}, method = RequestMethod.POST)
    public String alipayProPayNotify(HttpServletRequest request) throws Exception {
        log.info("进入支付宝保证金支付回调");
        Map<String, String> params = PayUtil.getAlipayResultParams(request);
        if(!EmptyUtils.isEmpty(params) && PayUtil.signAlipayVerified(params)){
            String tradeStatus = params.get(StringEnum.ALI_PAY_TRADE_STATUS.getCode());
            if(StringEnum.ALI_PAY_SUCCESS.getCode().equals(tradeStatus) || StringEnum.ALI_PAY_FINISHED.getCode().equals(tradeStatus)){
                String orderNo = params.get("out_trade_no");
                log.info("支付宝保证金支付回调out_trade_no="+orderNo);
                //截取订单编号
                String[] split = orderNo.split("-");
                if(!EmptyUtils.isEmpty(split)){
                    cereActivitySignService.handleBondPayLog(split,params.get("trade_no"),orderNo,IntegerEnum.BOND_PAY_ALIPAY.getCode());
                }
                return "success";
            } else {
                log.error("交易状态异常");
            }
        } else {
            log.error("map为null 或 签名不通过");
        }
        return "failure";
    }

    /**
     * 保证金退款回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value={"refund/rolBack"}, method = RequestMethod.POST)
    public void wxProRefundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("进入保证金退款回调");
        String xmlMsg = HttpKit.readData(request);
        log.info("微信小程序通知信息"+xmlMsg);
        Map<String, String> resultMap = PaymentKit.xmlToMap(xmlMsg);
        if(!EmptyUtils.isEmpty(resultMap)){
            //获取微信返回数据进行解密
            String req_info = resultMap.get("req_info");
            String str= EmptyUtils.decryption(req_info,key);
            Map<String, String> map = PaymentKit.xmlToMap(str);
            String orderNo = map.get("out_trade_no");
            log.info("回调out_trade_no="+orderNo);
            //截取订单编号
            String[] split = orderNo.split("-");
            if(resultMap.get(StringEnum.WX_RETURN_CODE.getCode()).equals(StringEnum.WX_PAY_SUCCESS.getCode())){
                log.info("保证金退款回调成功");
                if(!EmptyUtils.isEmpty(split)){
                    cereActivitySignService.refundBond(split,map.get("transaction_id"),orderNo);
                }
            }else {
                //查询报名数据
                CereActivitySign sign=cereActivitySignService.findBySignId(Long.parseLong(split[1]));
                if(sign!=null){
                    //查询当前活动是否为进行中
                    Integer state=cereActivitySignService.findActitivyState(sign.getActivityId());
                    if(IntegerEnum.SIGN_ACTIVITY_SUCCESS.getCode().equals(sign.getState())){
                        //如果是报名成功说明退款是走的活动结束延时任务,新增延时任务再一次处理自动退款
                        stringRedisService.set(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+sign.getSignType(),5000);
                    }else {
                        //报名失败退款
                        if(IntegerEnum.ACTIVITY_START.getCode().equals(state)){
                            //如果是活动进行中说明当前退款是走的活动刚开始延时任务退款,失败时再次新增延时任务处理退款
                            stringRedisService.set(StringEnum.ACTIVITY_END_FIFTEAN_REFUND_BOND.getCode()+"-"+split[1]+"-"+sign.getSignType(),5000);
                        }else {
                            //报名失败,新增延时任务再一次处理自动退款
                            stringRedisService.set(StringEnum.THREE_DAY_REFUND_BOND.getCode()+"-"+split[1]+"-"+sign.getSignType(),5000);
                        }
                    }
                }
            }
        }else {
            log.info("map为null");
        }
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response.getWriter().write(result);
    }

    /**
     * 报名详情查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "报名详情查询")
    public Result<List<Product>> getById(@RequestBody ActivitySignGetByIdParam param) throws CoBusinessException{
        List<Product> list=cereActivitySignService.getById(param.getSignId());
        return new Result(list);
    }

    /**
     * 选择商品查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getProducts",method = RequestMethod.POST)
    @ApiOperation(value = "选择商品查询")
    public Result<Page<Product>> getProducts(@RequestBody ActivitySignGetProductParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        Page page=cereActivitySignService.getProducts(param);
        return new Result(page);
    }

    /**
     * 活动数据查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getDatas",method = RequestMethod.POST)
    @ApiOperation(value = "活动数据查询")
    public Result<ActivityData> getDatas(@RequestBody ActivityGetDataParam param) throws CoBusinessException{
        ActivityData data=cereActivitySignService.getDatas(param);
        return new Result(data);
    }

    /**
     * 选择商品分组查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getGroups",method = RequestMethod.POST)
    @ApiOperation(value = "选择商品分组查询")
    public Result<List<CereShopGroup>> getGroups(@RequestBody ActivitySignGetGroupParam param) throws CoBusinessException{
        param.setShopId(ContextUtil.getShopId());
        List<CereShopGroup> list=cereActivitySignService.getGroups(param.getShopId());
        return new Result(list);
    }

    /**
     * 查询活动报名详情
     * @param param
     * @return
     */
    @RequestMapping(value = "getActivitySignDetail",method = RequestMethod.POST)
    @ApiOperation(value = "查询活动报名详情")
    public Result<ActivitySignDetail> getActivitySignDetail(@RequestBody ActivitySignGetByIdParam param, HttpServletRequest request) throws CoBusinessException{
        CerePlatformBusiness user = (CerePlatformBusiness) request.getAttribute("user");
        ActivitySignDetail detail=cereActivitySignService.getActivitySignDetail(param.getActivityId(), user);
        return new Result(detail);
    }


}
