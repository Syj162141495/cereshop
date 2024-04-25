/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.business.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lightClouds917
 * Date 2018/1/16
 * Description:配置文件配置项
 */
@Configuration
public class ConstantProperties implements InitializingBean {

    /**
     * 阿里云区域id
     */
    @Value("${aliyun.regionid}")
    private String aliyun_region_id;

    /**
     * 阿里云短信端点地址
     */
    @Value("${aliyun.signname}")
    private String aliyun_signname;

    /**
     * 阿里云产品名称
     */
    @Value("${aliyun.name}")
    private String aliyun_name;

    /**
     * 阿里云产品域名
     */
    @Value("${aliyun.domain}")
    private String aliyun_domain;

    /**
     * 阿里云产品keyid
     */
    @Value("${aliyun.keyid}")
    private String aliyun_keyid;

    /**
     * 阿里云产品密钥
     */
    @Value("${aliyun.keysecret}")
    private String aliyun_keysecret;

    /**
     * 秒信云账号app_id
     */
    @Value("${miaoxinyun.account}")
    private String miaoxin_account;

    /**
     * 秒信云密钥
     */
    @Value("${miaoxinyun.secret}")
    private String miaoxin_secret;

    /**
     * 秒信云域名
     */
    @Value("${miaoxinyun.domain}")
    private String miaoxin_domain;

    /**
     * 阿里不同的服务器，地址不同
     */
    @Value("${alioss.file.endpoint}")
    private String alioss_file_endpoint;

    /**
     * OSS控制台获取
     */
    @Value("${alioss.file.keyid}")
    private String alioss_file_keyid;

    /**
     * OSS控制台获取
     */
    @Value("${alioss.file.keysecret}")
    private String alioss_file_keysecret;

    /**
     * 文件上传路径
     */
    @Value("${alioss.file.upload}")
    private String alioss_file_fileHost;

    /**
     * 这个自己创建bucket时的命名，控制台创建也行，代码创建也行
     */
    @Value("${alioss.file.bucketname}")
    private String alioss_file_bucketname;

    public static String ALIYUN_REGION_ID;
    public static String ALIYUN_SIGNNAME;
    public static String ALIYUN_NAME;
    public static String ALIYUN_ACCESS_KEY_ID;
    public static String ALIYUN_ACCESS_KEY_SECRET;
    public static String ALIYUN_DOMIAN;
    public static String MIAOXIN_ACCOUNT;
    public static String MIAOXIN_SECRET;
    public static String MIAOXIN_DOMAIN;
    public static String ALIOSS_END_POINT;
    public static String ALIOSS_ACCESS_KEY_ID;
    public static String ALIOSS_ACCESS_KEY_SECRET;
    public static String ALIOSS_BUCKET_NAME;
    public static String ALIOSS_FILE_HOST;



    @Override
    public void afterPropertiesSet() throws Exception {
        ALIYUN_SIGNNAME=aliyun_signname;
        ALIYUN_REGION_ID=aliyun_region_id;
        ALIYUN_NAME=aliyun_name;
        ALIYUN_DOMIAN=aliyun_domain;
        ALIYUN_ACCESS_KEY_ID=aliyun_keyid;
        ALIYUN_ACCESS_KEY_SECRET=aliyun_keysecret;
        MIAOXIN_ACCOUNT=miaoxin_account;
        MIAOXIN_SECRET=miaoxin_secret;
        MIAOXIN_DOMAIN=miaoxin_domain;
        ALIOSS_END_POINT = alioss_file_endpoint;
        ALIOSS_ACCESS_KEY_ID = alioss_file_keyid;
        ALIOSS_ACCESS_KEY_SECRET = alioss_file_keysecret;
        ALIOSS_BUCKET_NAME = alioss_file_bucketname;
        ALIOSS_FILE_HOST = alioss_file_fileHost;
    }
}
