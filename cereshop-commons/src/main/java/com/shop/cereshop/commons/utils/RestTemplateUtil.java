/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private static final Logger logger = LoggerFactory.getLogger(com.shop.cereshop.commons.utils.RestTemplateUtil.class);

    /**
     * 1）url: 请求地址；
     * 2）method: 请求类型(如：POST,PUT,DELETE,GET)；
     * 3）requestEntity: 请求实体，封装请求头，请求内容
     * 4）responseType: 响应类型，根据服务接口的返回类型决定
     * 5）uriVariables: url中参数变量值
     */
    public static <T> ResponseEntity sendRequest(String url, HttpMethod method, T requestEntity,
                                                 Class responseType, Object[] uriVariables, String w3Account) {
        RestTemplate restTemplate=new RestTemplate();
        // 设置请求头：ContentType、Token、Cookie、User-Agent
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json");
        headers.setContentType(type);
//        headers.set("userName",w3Account);   //这是请求头里的一些参数配置
        headers.set("clientType","ISCENARIO");
        headers.set("requestId","c021ec9f-11dd-47f7-bb2b-01bae5388779");

        HttpEntity<T> entity = new HttpEntity<T>(requestEntity, headers);
        // 响应默认返回类型为String
        responseType = (responseType == null ? String.class : responseType);
        ResponseEntity response = null;
        try {
            if(null == uriVariables) {
                response = restTemplate.exchange(url, method, entity, responseType);
            } else {
                response = restTemplate.exchange(url, method, entity, responseType, uriVariables);
            }
        }
        catch (RestClientException e) {
            logger.error("Send request to the url:{} error.", url);
        }
        return response;
    }

    /**
     * 1）url: 请求地址；
     * 2）method: 请求类型(如：POST,PUT,DELETE,GET)；
     * 3）requestEntity: 请求实体，封装请求头，请求内容
     * 4）responseType: 响应类型，根据服务接口的返回类型决定
     * 5）uriVariables: url中参数变量值
     */
    public static <T> ResponseEntity sendExpressRequest(String url, HttpMethod method, T requestEntity,
                                                 Class responseType, Object[] uriVariables, String w3Account) {
        RestTemplate restTemplate=new RestTemplate();
        // 设置请求头：ContentType、Token、Cookie、User-Agent
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
        headers.setContentType(type);
//        headers.set("userName",w3Account);   //这是请求头里的一些参数配置
        headers.set("clientType","ISCENARIO");
        headers.set("requestId","c021ec9f-11dd-47f7-bb2b-01bae5388779");

        HttpEntity<T> entity = new HttpEntity<T>(requestEntity, headers);
        // 响应默认返回类型为String
        responseType = (responseType == null ? String.class : responseType);
        ResponseEntity response = null;
        try {
            if(null == uriVariables) {
                response = restTemplate.exchange(url, method, entity, responseType);
            } else {
                response = restTemplate.exchange(url, method, entity, responseType, uriVariables);
            }
        }
        catch (RestClientException e) {
            logger.error("Send request to the url:{} error.", url);
        }
        return response;
    }
}
