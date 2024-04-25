/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum StringEnum {

    SESSION_USER_ID("登录验证ID","userId"),
    DEFAULT_HEAD_IMAGE("默认头像","https://cereshop.oss-cn-shenzhen.aliyuncs.com/test/2021-03-09/52711fdcf6244df8bc5f1d3b1f6cf0d3%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210309104412.png"),
    NOTICE_TITLE_SYSTEM("消息标题-系统通知","系统通知"),
    MESSAGE_SUBMIT_SUCCESS("短信发送接口返回成功码","0"),
    MESSAGE_TEMPLATE("秒信云验证码短信内容","您好,您的验证码为：{0},请妥善保管,5分钟后失效"),
    MENU("菜单权限类型","menu"),
    BUTTON("按钮权限类型","button"),
    CATALOG("菜单目录权限类型","catalog"),
    MESSAGE_SIGN("秒信云签名","cereshop商城"),
    MESSAGE_SUBJECT_CODE("短信主体","验证码"),
    TEMPLATE_CODE("模板编码","SMS_176943773"),
    MESSAGE_RESULT_CODE("短信发送结果编码","OK"),
    MESSAGE_RESULT_MSG("秒信云短信发送结果信息","发送成功"),
    SEVEN_DAY_CHANGE_TOKEN("更换token","7天后自动更换token"),
    SEVEN_DAY_CHANGE_BUSINESS_TOKEN("更换商家token","7天后自动更换商家token"),
    SHOP_VALIDITY_DAT("关系绑定天数","关系绑定有效天数"),
    SHOP_SIGN_IN("报名进行中","修改活动状态为报名进行中"),
    SHOP_ACTIVITY_STAY("活动待开始","修改活动状态为活动待开始"),
    SHOP_ACTIVITY_IN("活动进行中","修改活动状态为活动进行中"),
    SHOP_ACTIVITY_END("活动已结束","修改活动状态为活动已结束"),
    PLATFORM_SECKILL_SIGN_IN("平台秒杀活动报名进行中","修改平台秒杀活动状态为报名进行中"),
    PLATFORM_SECKILL_ACTIVITY_STAY("平台秒杀活动待开始","修改平台秒杀活动状态为活动待开始"),
    PLATFORM_SECKILL_ACTIVITY_IN("平台秒杀活动进行中","修改平台秒杀活动状态为活动进行中"),
    PLATFORM_SECKILL_ACTIVITY_END("平台秒杀活动已结束","修改平台秒杀活动状态为活动已结束"),
    PLATFORM_DISCOUNT_SIGN_IN("平台限时折扣报名进行中","修改平台限时折扣活动状态为报名进行中"),
    PLATFORM_DISCOUNT_ACTIVITY_STAY("平台限时折扣活动待开始","修改平台限时折扣活动状态为活动待开始"),
    PLATFORM_DISCOUNT_ACTIVITY_IN("平台限时折扣活动进行中","修改平台限时折扣活动状态为活动进行中"),
    PLATFORM_DISCOUNT_ACTIVITY_END("平台限时折扣活动已结束","修改平台限时折扣活动状态为活动已结束"),
    SHOP_TOOL_END("优惠券已结束","修改营销工具状态为已结束"),
    SHOP_TOOL_IN("优惠券进行中","修改营销工具状态为进行中"),
    COUPON_OVERDUE("平台优惠券已过期","修改平台优惠券状态为已过期"),
    SHOP_COUPON_OVERDUE("店铺优惠券已过期","修改店铺优惠券状态为已过期"),
    SHOP_COUPON_IN("店铺优惠券状态为进行中","修改店铺优惠券状态为进行中"),
    SHOP_COUPON_END("店铺优惠券状态为已结束","修改店铺优惠券状态为已结束"),
    SHOP_GROUP_WORK_IN("店铺拼团活动为进行中","修改店铺店铺拼团活动状态为进行中"),
    SHOP_GROUP_WORK_END("店铺拼团活动为已结束","修改店铺店铺拼团活动状态为已结束"),
    COLLAGE_ERROR("拼团失败","拼团失败"),
    SHOP_SECKILL_IN("店铺秒杀活动为进行中","修改店铺秒杀活动状态为进行中"),
    SHOP_SECKILL_END("店铺秒杀活动为已结束","修改店铺秒杀活动状态为已结束"),
    SHOP_DISCOUNT_IN("店铺限时折活动为进行中","修改店铺限时折活动状态为进行中"),
    SHOP_DISCOUNT_END("店铺限时折活动为已结束","修改店铺限时折活动状态为已结束"),
    POLITE_START("平台支付有礼活动进行中","修改平台支付有礼活动状态为进行中"),
    POLITE_END("平台支付有礼活动已结束","修改平台支付有礼活动状态为已结束"),
    SCENE_START("场景营销活动进行中","场景营销活动活动状态为进行中"),
    SCENE_END("场景营销活动已停止","场景营销活动活动状态为已停止"),
    COMPOSE_START("组合捆绑活动进行中","组合捆绑活动活动状态为进行中"),
    COMPOSE_END("组合捆绑活动已停止","组合捆绑活动活动状态为已停止"),
    PRICE_START("定价捆绑活动进行中","定价捆绑活动活动状态为进行中"),
    PRICE_END("定价捆绑活动已结束","定价捆绑活动活动状态为已结束"),
    YES("是","是"),
    NO("否","否"),
    UP("上架","上架"),
    NOTICE_TITLE_ORDER_CANCEL("消息标题为订单已关闭","消息标题为订单已关闭"),
    NOTICE_TITLE_ORDER_PAY_SUCCESS("消息标题为支付成功","消息标题为支付成功"),
    NOTICE_TITLE_ORDER_DELIVERY("消息标题为商家已放货","消息标题为商家已放货"),
    NOTICE_TITLE_ORDER_FINISH("消息标题为订单已完成","消息标题为订单已完成"),
    WX_RETURN_CODE("微信支付返回状态码","return_code"),
    WX_PAY_SUCCESS("微信支付成功标识","SUCCESS"),
    ALI_PAY_TRADE_STATUS("支付宝返回状态码","trade_status"),
    //以下两者的区别参考支付宝支付回调异步通知说明 - https://opendocs.alipay.com/open/204/105301
    ALI_PAY_SUCCESS("支付宝成功标识","TRADE_SUCCESS"),
    ALI_PAY_FINISHED("支付宝成功标识2","TRADE_FINISHED"),
    PAY_LOG_PAY("支付日志状态-支付","支付"),
    PAY_LOG_REFUND("支付日志状态-退款","退款"),
    PAY_LOG_REFLECT("支付日志状态-提现","提现"),
    ORDER_AUTOMATIC_CANCEL("订单延时任务-自动关闭","30分钟后订单自动关闭"),
    ORDER_CONFIRM_DILEVERY("订单延时任务-自动确认收货","15天后自动确认收货"),
    EXTENSION_TITLE_SHOP("推广设置标题-推广店铺","推广店铺"),
    EXTENSION_TITLE_NEXT("推广设置标题-邀请下级","邀请下级"),
    THREE_DAY_REFUND_BOND("报名失败3天后退保证金","报名失败3天后退保证金"),
    AFTER_CANCEL("申请售后3天后未处理自动关闭","申请售后3天后未处理自动关闭"),
    NOTICE_OPERATE("手动计划运营计划定时任务执行发送消息通知","手动计划运营计划定时任务执行发送消息通知"),
    NOTICE_OPERATE_AUTOMATIC("自动计划运营计划定时任务执行发送消息通知","自动计划运营计划定时任务执行发送消息通知"),
    OPERATE_STATE_STAY("修改运营计划状态-进行中","修改运营计划状态为进行中"),
    OPERATE_STATE_STOP("修改运营计划状态-已结束","修改运营计划状态为已结束"),
    ACTIVITY_END_FIFTEAN_REFUND_BOND("活动结束15天后自动退款至商家微信","活动结束15天后自动退款至商家微信"),
    SIGN_NOT_PAY_DELETE("报名后1分钟未支付删除报名数据","报名后1分钟未支付删除报名数据");

    String name;
    String code;

    private static Map<String, StringEnum> valueMap = new HashMap<>();

    static {
        for(StringEnum gender : StringEnum.values()) {
            valueMap.put(gender.name, gender);
        }
    }

    StringEnum(String name, String code) {
        this.code = code;
        this.name=name;
    }

    public static String getByName(String name) {
        StringEnum result = valueMap.get(name);
        if(result == null) {
            throw new IllegalArgumentException("No element matches " + name);
        }
        return result.code;
    }
}
