/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.param.member.MembershipSaveParam;
import com.shop.cereshop.admin.service.member.CerePlatformMembershipService;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 会员权益测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformMemberShipTest {

    @Autowired
    private CerePlatformMembershipService cerePlatformMembershipService;

    @Test
    public void save(){
        try {
            CerePlatformUser user=new CerePlatformUser();
            user.setPlatformUserId(2l);
            MembershipSaveParam param=new MembershipSaveParam();
            param.setMemberName("普通权益");
            param.setMemberIcon("123");
            param.setMemberReason("普通权益说明");
            cerePlatformMembershipService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
