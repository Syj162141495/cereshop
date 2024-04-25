/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;

import java.io.Serializable;

/**
 * 小程序获取token返回数据
 * @author 0200759
 *
 */
public class AppletTokenResult implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2537928824643088525L;
    
    /**
     * 小程序token
     */
    private String access_token;
    
    /**
     * token有效时间
     */
    private int expires_in;
    
    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
