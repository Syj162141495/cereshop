/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;
 
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
* BCrypt Java实现
* BCrypt也是一种哈希算法，相比MD5更安全，速度慢
* */
public class UseBCrypt {
    public static void main(String args[]){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(BCrypt.hashpw("123456".toString(), BCrypt.gensalt(13)));
        String encodedPassword = "$2a$13$Fxs7FqJGU5fVwje1YcJrlumF2IW6pviMMybC/aLLLAesU.vfwj2eC";
        boolean bMatch = bCryptPasswordEncoder.matches("123456", encodedPassword);
        System.out.println(bMatch); // true
    }
}
