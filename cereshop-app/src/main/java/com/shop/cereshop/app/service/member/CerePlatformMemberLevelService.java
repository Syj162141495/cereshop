/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.service.member;

import com.shop.cereshop.commons.domain.member.CerePlatformMemberLevel;

public interface CerePlatformMemberLevelService {

    /**
     * 查询会员等级
     * @param memberLevelId
     * @return
     */
    CerePlatformMemberLevel selectByMemberLevelId(Long memberLevelId);

    /**
     * 查询下一个等级
     * @param memberLevelId
     * @return
     */
    CerePlatformMemberLevel selectNextLevel(Long memberLevelId);

    /**
     * 查询最小等级
     * @return
     */
    CerePlatformMemberLevel selectFirstLevel();
}
