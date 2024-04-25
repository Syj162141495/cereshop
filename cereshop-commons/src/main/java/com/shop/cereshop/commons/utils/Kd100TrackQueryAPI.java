/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.shop.cereshop.commons.domain.express.*;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class Kd100TrackQueryAPI {

    private static final String key="LYLQhYAP6315";

    private static final String customer="97B658F5054A285AA81426BA6EAB3F1A";

    private static final String queryUrl = "https://poll.kuaidi100.com/poll/query.do";

    public static ShippingDTO getOrderTracesByJson(String expCode, String expNo){

        QueryTrackReq queryTrackReq = new QueryTrackReq();
        QueryTrackParam queryTrackParam = new QueryTrackParam();
        queryTrackParam.setCom(expCode);
        queryTrackParam.setNum(expNo);
        queryTrackReq.setParam(queryTrackParam);
        queryTrackReq.setCustomer(customer);
        queryTrackReq.setSign(DigestUtils.md5Hex(new Gson().toJson(queryTrackParam)+key+customer).toUpperCase());


        HttpResult httpResult = HttpUtils.doPost(queryUrl, queryTrackReq);

        if (httpResult.getStatus() == 200 && StringUtils.isNotBlank(httpResult.getBody())){
            QueryTrackResp queryTrackResp = JSON.parseObject(httpResult.getBody(), QueryTrackResp.class);
            ShippingDTO shippingDTO = new ShippingDTO();
            shippingDTO.setShipperCode(expCode);
            shippingDTO.setLogisticCode(expNo);
            if (queryTrackResp != null && queryTrackResp.getData() != null) {
                List<ShippingTrace> shippingTraceList = new ArrayList();
                for(QueryTrackData queryTrackData:queryTrackResp.getData()){
                    ShippingTrace trace = new ShippingTrace();
                    trace.setAcceptTime(queryTrackData.getTime());
                    trace.setAcceptStation(queryTrackData.getContext());
                    shippingTraceList.add(trace);
                }
                shippingDTO.setTraces(shippingTraceList);
                return shippingDTO;
            }
        }

        return new ShippingDTO();
    }

    public static void main(String[] args) {
        ShippingDTO orderTracesByJson = getOrderTracesByJson("zhongtong","78159217396609");
        System.out.println("");
    }
}
