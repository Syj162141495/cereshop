/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin;

import com.shop.cereshop.admin.param.member.MemberLevelSaveParam;
import com.shop.cereshop.admin.service.member.CerePlatformMemberLevelService;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员等级测试用例
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlatformMemberLevelTest {

    @Autowired
    private CerePlatformMemberLevelService cerePlatformMemberLevelService;

    @Test
    public void save(){
        try {
            CerePlatformUser user=new CerePlatformUser();
            user.setPlatformUserId(2l);
            MemberLevelSaveParam param=new MemberLevelSaveParam();
            param.setMemberLevelName("超级会员");
            param.setMemberLevelIcon("21313");
            param.setMemberLevelReason("超级会员说明");
            param.setGrowth(10000);
            List<String> ids=new ArrayList<>();
            ids.add("5");
            param.setIds(ids);
            param.setMemberLevelBackground("2313131");
            cerePlatformMemberLevelService.save(param,user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
