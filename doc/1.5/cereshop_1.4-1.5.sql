-- 修改商品表字段上架状态
alter table cere_shop_product modify column shelve_state tinyint(1) DEFAULT '1' COMMENT '商品状态 0-已下架 1-已上架 2-待审核 3-审核失败';
-- 修改商品图片表图片长度
alter table cere_product_image modify column `product_image` varchar(1000) DEFAULT NULL COMMENT '图片地址';
-- 商品表新增虚拟销量字段
ALTER TABLE `cere_shop_product`
    ADD COLUMN `fictitious_number` int(11) DEFAULT NULL COMMENT '虚拟销量';
-- 商品表新增驳回原因字段
ALTER TABLE `cere_shop_product`
    ADD COLUMN `reject` varchar(255) DEFAULT NULL COMMENT '驳回原因';
-- 报名商品明细信息表新增商品限量剩余字段
ALTER TABLE `cere_sign_product`
    ADD COLUMN `number` int(11) DEFAULT NULL COMMENT '商品限量（剩余）';
-- 报名商品明细信息表新增商品限量总数字段
ALTER TABLE `cere_sign_product`
    ADD COLUMN `total` int(11) DEFAULT NULL COMMENT '限量总数';
-- 订单表新增平台秒杀活动id字段
ALTER TABLE `cere_shop_order`
    ADD COLUMN `seckill_id` bigint(11) DEFAULT NULL COMMENT '平台秒杀活动id';
-- 订单表新增平台限时折扣活动id字段
ALTER TABLE `cere_shop_order`
    ADD COLUMN `discount_id` bigint(11) DEFAULT NULL COMMENT '平台限时折扣活动id';
-- 订单表新增平台支付有礼活动id字段
ALTER TABLE `cere_shop_order`
    ADD COLUMN `polite_id` bigint(11) DEFAULT NULL COMMENT '平台支付有礼活动id';
-- 订单表新增商家场景营销活动id字段
ALTER TABLE `cere_shop_order`
    ADD COLUMN `scene_id` bigint(11) DEFAULT NULL COMMENT '商家场景营销活动id';
-- 商家报名活动申请信息表新增报名活动类型字段
ALTER TABLE `cere_activity_sign`
    ADD COLUMN `sign_type` tinyint(1) DEFAULT NULL COMMENT '报名活动类型  1-平台优惠券 2-平台秒杀 3-平台限时折扣';
-- 客户信息表添加会员等级id字段
ALTER TABLE `cere_buyer_user`
    ADD COLUMN `member_level_id` bigint(20) DEFAULT NULL COMMENT '会员等级id' AFTER `token`;
-- 客户信息表添加会员成长值字段
ALTER TABLE `cere_buyer_user`
	ADD COLUMN `growth` int(11) NULL DEFAULT 0 COMMENT '成长值' AFTER `member_level_id`;

-- 订单商品新增活动类型和活动id字段
ALTER TABLE `cere_order_product`
ADD COLUMN `activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品活动类型 参考IntegerEnum.ACTIVITY_TYPE_XXX',

ALTER TABLE `cere_order_product`
ADD COLUMN `activity_id` bigint(20) NULL COMMENT '活动id';

-- 替换原有平台活动信息表
DROP TABLE IF EXISTS `cere_platform_activity`;
CREATE TABLE `cere_platform_activity`
(
    `activity_id`         bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动id',
    `activity_name`       varchar(60)    DEFAULT NULL COMMENT '优惠券活动名称',
    `activity_introduce`  varchar(255)   DEFAULT NULL COMMENT '备注',
    `sign_start_time`     varchar(20)    DEFAULT NULL COMMENT '报名开始时间',
    `sign_end_time`       varchar(20)    DEFAULT NULL COMMENT '报名结束时间',
    `activity_start_time` varchar(20)    DEFAULT NULL COMMENT '活动开始时间',
    `activity_end_time`   varchar(20)    DEFAULT NULL COMMENT '活动结束时间',
    `if_bond`             tinyint(1) DEFAULT '1' COMMENT '是否需要保证金  1-是 0-否',
    `bond_money`          decimal(15, 2) DEFAULT NULL COMMENT '保证金金额',
    `threshold`           decimal(15, 2) NOT NULL COMMENT '使用门槛满多少元，无门槛为0',
    `discount_mode`       tinyint(1) DEFAULT '1' COMMENT '优惠券类型 1-满减 2-折扣',
    `coupon_content`      decimal(15, 2) NOT NULL COMMENT '优惠内容减多少元/打多少折',
    `number`              int(11) DEFAULT NULL COMMENT '发放数量',
    `stock_number`        int(11) DEFAULT NULL COMMENT '库存数量',
    `receive_type`        tinyint(1) NOT NULL COMMENT '每人限领次数  1-无限次 2-限制几次',
    `frequency`           int(11) DEFAULT NULL COMMENT '限制次数',
    `image`               varchar(255)   DEFAULT NULL COMMENT '图片地址',
    `state`               tinyint(1) DEFAULT '0' COMMENT '活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束',
    `create_time`         varchar(20)    DEFAULT NULL COMMENT '创建时间',
    `update_time`         varchar(20)    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='平台优惠券活动信息表';

-- 新增平台秒杀活动信息表
DROP TABLE IF EXISTS `cere_platform_seckill`;
CREATE TABLE `cere_platform_seckill`
(
    `seckill_id`      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台秒杀活动id',
    `seckill_name`    varchar(60) NOT NULL COMMENT '活动名称',
    `remark`          varchar(255)   DEFAULT NULL COMMENT '备注',
    `sign_start_time` varchar(20)    DEFAULT NULL COMMENT '报名开始时间',
    `sign_end_time`   varchar(20)    DEFAULT NULL COMMENT '报名结束时间',
    `start_time`      varchar(20)    DEFAULT NULL COMMENT '活动开始时间',
    `end_time`        varchar(20)    DEFAULT NULL COMMENT '活动结束时间',
    `if_limit`        tinyint(1) NOT NULL COMMENT '商品限购 1-不限购 2-限购',
    `limit_number`    int(11) DEFAULT NULL COMMENT '限购几件/人',
    `if_add`          tinyint(1) NOT NULL COMMENT '优惠券是否叠加 1-是 0-否',
    `state`           tinyint(1) DEFAULT '0' COMMENT '活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束',
    `if_bond`         tinyint(1) DEFAULT '1' COMMENT '是否需要保证金  1-是 0-否',
    `bond_money`      decimal(15, 2) DEFAULT NULL COMMENT '保证金金额',
    `seckill_money`   decimal(15, 2) DEFAULT NULL COMMENT '直降多少元',
    `create_time`     varchar(20)    DEFAULT NULL COMMENT '创建时间',
    `update_time`     varchar(20)    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='平台秒杀活动信息表';

-- 新增平台限时折扣信息表
DROP TABLE IF EXISTS `cere_platform_discount`;
CREATE TABLE `cere_platform_discount`
(
    `discount_id`     bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台限时折扣活动id',
    `discount_name`   varchar(60) NOT NULL COMMENT '活动名称',
    `remark`          varchar(255)   DEFAULT NULL COMMENT '备注',
    `sign_start_time` varchar(20)    DEFAULT NULL COMMENT '报名开始时间',
    `sign_end_time`   varchar(20)    DEFAULT NULL COMMENT '报名结束时间',
    `start_time`      varchar(20) NOT NULL COMMENT '活动开始时间',
    `end_time`        varchar(20) NOT NULL COMMENT '活动结束时间',
    `if_limit`        tinyint(1) NOT NULL COMMENT '商品限购 1-不限购 2-限购',
    `limit_number`    int(11) DEFAULT NULL COMMENT '限购几件/人',
    `if_add`          tinyint(1) NOT NULL COMMENT '优惠券是否叠加 1-是 0-否',
    `state`           tinyint(1) DEFAULT '0' COMMENT '活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束',
    `if_bond`         tinyint(1) DEFAULT '1' COMMENT '是否需要保证金  1-是 0-否',
    `bond_money`      decimal(15, 2) DEFAULT NULL COMMENT '保证金金额',
    `discount`        decimal(10,2) DEFAULT NULL COMMENT '打几折',
    `create_time`     varchar(20)    DEFAULT NULL COMMENT '创建时间',
    `update_time`     varchar(20)    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`discount_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='平台限时折扣活动信息表';

-- 新增平台支付有礼活动信息表
DROP TABLE IF EXISTS `cere_platform_polite`;
CREATE TABLE `cere_platform_polite`
(
    `polite_id`   bigint(11) NOT NULL AUTO_INCREMENT COMMENT '支付有礼活动id',
    `polite_name` varchar(60) NOT NULL COMMENT '活动名称',
    `remark`      varchar(255)   DEFAULT NULL COMMENT '备注',
    `start_time`  varchar(20) NOT NULL COMMENT '活动开始时间',
    `end_time`    varchar(20) NOT NULL COMMENT '活动结束时间',
    `buyer_mode`  tinyint(1) NOT NULL COMMENT '消费方式   1-购买一定金额商品 2-购买一定数量商品',
    `buyer`       decimal(15, 2) DEFAULT NULL COMMENT '满多少元/件,参与活动',
    `growth`      int(10) DEFAULT NULL COMMENT '会员成长值',
    `state`       tinyint(1) unsigned  NOT NULL DEFAULT '0' COMMENT '状态 0-未开始 1-进行中 2-已结束',
    `create_time` varchar(20)    DEFAULT NULL COMMENT '创建时间',
    `update_time` varchar(20)    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`polite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台支付有礼活动信息表';

-- 平台支付有礼活动优惠券信息表
DROP TABLE IF EXISTS `cere_platform_polite_activity`;
CREATE TABLE `cere_platform_polite_activity`
(
    `polite_id`      bigint(11) NOT NULL COMMENT '平台支付有礼活动id',
    `activity_id`    bigint(11) NOT NULL COMMENT '平台优惠券活动id',
    `activity_name`  varchar(60)    NOT NULL COMMENT '优惠券名称',
    `activity_type`  tinyint(1) NOT NULL COMMENT '优惠券类型 1-满减 2-折扣',
    `threshold`      decimal(15, 2) NOT NULL COMMENT '使用门槛满多少元，无门槛为0',
    `coupon_content` decimal(15, 2) DEFAULT NULL COMMENT '优惠内容减多少元/打多少折'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台支付有礼活动优惠券信息表';

CREATE TABLE `cere_buyer_polite_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `buyer_user_id` bigint(20) NOT NULL COMMENT '用户id',
    `order_id` bigint(20) NOT NULL COMMENT '关联订单id',
    `polite_id` bigint(20) NOT NULL COMMENT '关联的支付有礼活动id',
    `polite_type` tinyint(4) NOT NULL COMMENT '奖励类型 1-成长值，2-满减券，3-折扣券',
    `growth` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '奖励的成长值',
    `discount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '奖励的优惠券金额，或折扣券的折扣额度',
    `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
    `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_buyer_user_id` (`buyer_user_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户支付有礼记录';

-- 新增商品会员价格信息表
DROP TABLE IF EXISTS `cere_product_member`;
CREATE TABLE `cere_product_member`
(
    `product_id`      bigint(11) NOT NULL COMMENT '商品id',
    `sku_id`          bigint(11) NOT NULL COMMENT '规格id',
    `member_level_id` bigint(11) NOT NULL COMMENT '会员等级id',
    `mode`            tinyint(1) NOT NULL COMMENT '优惠方式   1-折扣 2-指定价格',
    `price`           decimal(15, 2) NOT NULL COMMENT '多少元/几折',
    `total`           decimal(15,2) DEFAULT NULL COMMENT '最终价格'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品会员价格信息表';

-- 新增场景营销信息表
DROP TABLE IF EXISTS `cere_shop_scene`;
CREATE TABLE `cere_shop_scene` (
  `scene_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '场景营销id',
  `shop_id` bigint(11) NOT NULL COMMENT '店铺id',
  `scene_type` tinyint(1) NOT NULL COMMENT '营销类型 1-节日营销 2-会员日营销 3-生日营销',
  `scene_name` varchar(60) NOT NULL COMMENT '营销名称',
  `start_time` varchar(20) NOT NULL COMMENT '开始时间',
  `end_time` varchar(20) NOT NULL COMMENT '结束时间',
  `scene_rule` tinyint(1) NOT NULL COMMENT '营销规则 1-所有等级会员，同一规则 2-不同等级会员,不同规则',
  `scene_time_type` tinyint(1) DEFAULT NULL COMMENT '营销时间类型 1-每月 2-每周 3-每日 4-生日当天 5-生日当周 6-生日当月',
  `scene_time` varchar(255) DEFAULT NULL COMMENT '营销事件 每月示例1-2表示每月一日到二日；每周示例1(周一)-2（周二）-3-4-5-6-7表示全选，0代表未选；每日示例2021-01-01 10:00:00至2021-01-01 12:00:00',
  `state` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '状态 0-未开始 1-进行中 2-已停止',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`scene_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='场景营销信息表';


-- 新增商家场景营销规则信息表
DROP TABLE IF EXISTS `cere_shop_scene_member`;
CREATE TABLE `cere_shop_scene_member` (
  `scene_id` bigint(11) NOT NULL COMMENT '场景营销id',
  `member_level_id` bigint(11) NOT NULL COMMENT '会员等级id',
  `if_free_shipping` tinyint(1) unsigned  DEFAULT '0' COMMENT '是否包邮 1-是 0-否',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家场景营销规则信息表';

-- 新增商家场景营销规则会员等级优惠券信息表
DROP TABLE IF EXISTS `cere_shop_scene_member_coupon`;
CREATE TABLE `cere_shop_scene_member_coupon` (
  `scene_id` bigint(11) NOT NULL COMMENT '场景营销id',
  `member_level_id` bigint(11) NOT NULL COMMENT '会员等级id',
  `coupon_id` bigint(11) NOT NULL COMMENT '商家优惠券id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家场景营销规则会员等级优惠券信息表';

-- 新增商家组合捆绑信息表
DROP TABLE IF EXISTS `cere_shop_compose`;
CREATE TABLE `cere_shop_compose`
(
    `compose_id`   bigint(11) NOT NULL AUTO_INCREMENT COMMENT '组合捆绑id',
    `shop_id`      bigint(11) NOT NULL COMMENT '店铺id',
    `compose_name` varchar(60)    NOT NULL COMMENT '组合名称',
    `start_time`   varchar(20)    NOT NULL COMMENT '活动开始时间',
    `end_time`     varchar(20)    NOT NULL COMMENT '活动结束时间',
    `compose_type` tinyint(1) NOT NULL COMMENT '促销类型 1-直接定价 2-固定减价 3-折扣',
    `promote`      decimal(15, 2) NOT NULL COMMENT '促销值(元/折)',
    `price`        decimal(15, 2) DEFAULT NULL COMMENT '最终价格',
    `state`        tinyint(1) NOT NULL COMMENT '状态 0-未开始 1-进行中 2-已结束 3-已停用',
    `create_time`  varchar(20)    DEFAULT NULL COMMENT '创建时间',
    `update_time`  varchar(20)    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`compose_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家组合捆绑信息表';

-- 新增商家组合捆绑商品信息表
DROP TABLE IF EXISTS `cere_compose_product`;
CREATE TABLE `cere_compose_product`
(
    `compose_id` bigint(11) NOT NULL COMMENT '组合捆绑id',
    `product_id` bigint(11) NOT NULL COMMENT '商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家组合捆绑商品信息表';

-- 新增商家定价捆绑信息表
DROP TABLE IF EXISTS `cere_shop_price`;
CREATE TABLE `cere_shop_price`
(
    `price_id`     bigint(11) NOT NULL AUTO_INCREMENT COMMENT '定价捆绑id',
    `shop_id`      bigint(11) NOT NULL COMMENT '店铺id',
    `compose_name` varchar(60) NOT NULL COMMENT '活动名称',
    `start_time`   varchar(20) NOT NULL COMMENT '活动开始时间',
    `end_time`     varchar(20) NOT NULL COMMENT '活动结束时间',
    `state`        tinyint(1) NOT NULL COMMENT '状态 0-未开始 1-进行中 2-已结束 3-已停用',
    `create_time`  varchar(20) DEFAULT NULL COMMENT '创建时间',
    `update_time`  varchar(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`price_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家定价捆绑信息表';

-- 新增商家定价捆绑商品信息表
DROP TABLE IF EXISTS `cere_price_product`;
CREATE TABLE `cere_price_product`
(
    `price_id`   bigint(11) NOT NULL COMMENT '定价捆绑id',
    `product_id` bigint(11) NOT NULL COMMENT '商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家定价捆绑商品信息表';

-- 新增商家定价优惠规则信息表
DROP TABLE IF EXISTS `cere_price_rule`;
CREATE TABLE `cere_price_rule`
(
    `price_id` bigint(11) NOT NULL COMMENT '定价捆绑id',
    `number`   int(11) NOT NULL COMMENT '任选几件',
    `price`    decimal(15, 2) NOT NULL COMMENT '捆绑价'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家定价捆绑优惠规则信息表';

-- 新增平台会员等级信息表
DROP TABLE IF EXISTS `cere_platform_member_level`;
CREATE TABLE `cere_platform_member_level` (
  `member_level_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '会员等级id',
  `member_level_name` varchar(20) NOT NULL COMMENT '等级名称',
  `member_level_icon` varchar(255) NOT NULL COMMENT '等级图标地址',
  `member_level_background` varchar(255) NOT NULL COMMENT '等级背景图地址',
  `growth` int(11) unsigned  NOT NULL DEFAULT '00000000000' COMMENT '成长值',
  `member_ids` varchar(255) NOT NULL COMMENT '会员权益id(多个以逗号隔开)',
  `member_level_reason` varchar(255) DEFAULT NULL COMMENT '会员等级说明',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台会员等级信息表';

-- 新增平台会员权益信息表
DROP TABLE IF EXISTS `cere_platform_membership`;
CREATE TABLE `cere_platform_membership` (
  `member_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '会员权益id',
  `member_name` varchar(20) NOT NULL COMMENT '权益名称',
  `member_icon` varchar(255) NOT NULL COMMENT '权益图标地址',
  `member_reason` varchar(255) DEFAULT NULL COMMENT '权益说明',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员权益信息表';

-- 新增客户浏览平台秒杀记录表
CREATE TABLE `cere_buyer_platform_seckill_visit` (
   `seckill_id` bigint(20) NOT NULL COMMENT '平台秒杀活动id',
   `buyer_user_id` bigint(20) NOT NULL COMMENT '浏览客户id',
   `visit_time` varchar(20) NOT NULL COMMENT '浏览时间',
   `shop_id` bigint(11) NOT NULL COMMENT '店铺id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户浏览平台秒杀记录表';

-- 新增客户浏览平台秒杀记录表
CREATE TABLE `cere_buyer_platform_discount_visit` (
    `discount_id` bigint(20) NOT NULL COMMENT '平台限时折扣活动id',
    `buyer_user_id` bigint(20) NOT NULL COMMENT '浏览客户id',
    `visit_time` varchar(20) NOT NULL COMMENT '浏览时间',
    `shop_id` bigint(11) NOT NULL COMMENT '店铺id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户浏览平台限时折扣记录表';

-- 新增会员签到表
CREATE TABLE `cere_member_signin_record` (
    `signin_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到id',
    `buyer_user_id` bigint(20) NOT NULL COMMENT '客户id',
    `term_id` int(11) NOT NULL DEFAULT '1' COMMENT '轮询id,第一天签到则为1，第2天签到则为2，到7之后，从1重新开始',
    `growth` int(11) NOT NULL DEFAULT '10' COMMENT '奖励的成长值',
    `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
    `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`signin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='会员签到表';

CREATE TABLE `cere_buyer_discount_visit` (
    `shop_discount_id` bigint(20) NOT NULL COMMENT '限时折扣活动id',
    `buyer_user_id` bigint(20) NOT NULL COMMENT '浏览客户id',
    `visit_time` varchar(20) NOT NULL COMMENT '浏览时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户浏览限时折扣记录表';
