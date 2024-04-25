/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : cereshop

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2021-03-22 11:23:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cere_activity_sign
-- ----------------------------
DROP TABLE IF EXISTS `cere_activity_sign`;
CREATE TABLE `cere_activity_sign` (
  `sign_id` bigint NOT NULL AUTO_INCREMENT COMMENT '报名id',
  `sign_code` varchar(20) DEFAULT NULL COMMENT '交易流水号',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `activity_id` bigint NOT NULL COMMENT '关联活动id',
  `bond_money` decimal(15,2) DEFAULT NULL COMMENT '保证金',
  `payment_mode` tinyint(1) DEFAULT '1' COMMENT '支付方式  1-微信支付 2-支付宝支付',
  `qr_image` varchar(255) DEFAULT NULL COMMENT '收款二维码',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态 0-待审核 1-报名成功 2-报名失败',
  `bond_state` tinyint(1) DEFAULT NULL COMMENT '保证金状态 0-未支付 1-冻结中 2-已退回',
  `payment_time` varchar(20) DEFAULT NULL COMMENT '缴纳时间',
  `return_time` varchar(20) DEFAULT NULL COMMENT '退保时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sign_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='商家报名活动申请信息表';

-- ----------------------------
-- Table structure for cere_after_dilever
-- ----------------------------
DROP TABLE IF EXISTS `cere_after_dilever`;
CREATE TABLE `cere_after_dilever` (
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `after_id` bigint DEFAULT NULL COMMENT '关联售后单id',
  `express` bigint NOT NULL COMMENT '快递公司（取数据字典）',
  `deliver_formid` varchar(20) NOT NULL COMMENT '快递单号',
  `reason` varchar(255) DEFAULT NULL COMMENT '说明',
  `image` varchar(500) DEFAULT NULL COMMENT '凭证图片，多个以逗号隔开',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='买家退货信息表';

-- ----------------------------
-- Table structure for cere_after_product
-- ----------------------------
DROP TABLE IF EXISTS `cere_after_product`;
CREATE TABLE `cere_after_product` (
  `after_product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '售后单商品明细id',
  `after_id` bigint NOT NULL COMMENT '关联售后单id',
  `product_id` bigint NOT NULL COMMENT '关联商品id',
  `sku_id` bigint NOT NULL COMMENT '关联规格id',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `number` int NOT NULL COMMENT '购买数量',
  `product_price` decimal(15,2) NOT NULL COMMENT '商品售价',
  `image` varchar(255) NOT NULL COMMENT '图片地址',
  `SKU` varchar(50) DEFAULT NULL COMMENT 'SKU',
  `weight` decimal(15,2) DEFAULT NULL COMMENT '重量',
  PRIMARY KEY (`after_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COMMENT='售后单商品信息表';

-- ----------------------------
-- Table structure for cere_after_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `cere_after_product_attribute`;
CREATE TABLE `cere_after_product_attribute` (
  `after_product_id` bigint NOT NULL COMMENT '关联售后商品明细id',
  `sku_name` varchar(10) DEFAULT NULL COMMENT '规格名',
  `sku_value` varchar(10) DEFAULT NULL COMMENT '规格值',
  `name_code` varchar(20) DEFAULT NULL COMMENT '规格名级别',
  `value_code` varchar(20) DEFAULT NULL COMMENT '规格值级别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='售后商品属性信息表';

-- ----------------------------
-- Table structure for cere_business_shop
-- ----------------------------
DROP TABLE IF EXISTS `cere_business_shop`;
CREATE TABLE `cere_business_shop` (
  `business_user_id` bigint NOT NULL COMMENT '商家用户id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `if_binding` tinyint(1) NOT NULL COMMENT '是否绑定当前店铺 1-是 0-否'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家用户绑定店铺信息表';

-- ----------------------------
-- Table structure for cere_business_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cere_business_user_role`;
CREATE TABLE `cere_business_user_role` (
  `business_user_id` bigint NOT NULL COMMENT '商家用户id',
  `role_id` bigint NOT NULL COMMENT '关联角色表id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家用户关联角色表';

-- ----------------------------
-- Table structure for cere_buyer_bank
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_bank`;
CREATE TABLE `cere_buyer_bank` (
  `bank_id` bigint NOT NULL AUTO_INCREMENT COMMENT '银行卡id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `bank_name` varchar(50) NOT NULL COMMENT '银行名称',
  `bank_card` varchar(50) NOT NULL COMMENT '卡号',
  PRIMARY KEY (`bank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='客户绑定银行卡信息表';

-- ----------------------------
-- Table structure for cere_buyer_collect
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_collect`;
CREATE TABLE `cere_buyer_collect` (
  `collect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `collect_type` tinyint(1) NOT NULL COMMENT '收藏类型 1-商品 2-店铺',
  `product_id` bigint DEFAULT NULL COMMENT '商品id',
  `shop_id` bigint DEFAULT NULL COMMENT '店铺id',
  `state` tinyint(1) NOT NULL COMMENT '是否收藏 1-是 0-否',
  `selected` tinyint(1) DEFAULT NULL COMMENT '是否选中 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 COMMENT='客户收藏信息表';

-- ----------------------------
-- Table structure for cere_buyer_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_comment_like`;
CREATE TABLE `cere_buyer_comment_like` (
  `comment_id` bigint NOT NULL COMMENT '评论id',
  `buyer_user_id` bigint NOT NULL COMMENT '点赞客户id',
  `if_like` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否点赞 1-是 0-否'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户评论点赞信息表';

-- ----------------------------
-- Table structure for cere_buyer_coupon
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_coupon`;
CREATE TABLE `cere_buyer_coupon` (
  `coupon_id` bigint NOT NULL COMMENT '优惠券id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `activity_id` bigint NOT NULL COMMENT '平台活动id',
  `activity_name` varchar(20) NOT NULL COMMENT '活动名称',
  `start_time` varchar(20) DEFAULT NULL COMMENT '活动开始时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '活动结束时间',
  `discount_mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '优惠方式 1-满减 2-优惠券',
  `discount_programme` tinyint(1) NOT NULL DEFAULT '1' COMMENT '优惠方案  1-叠加优惠 2-阶梯优惠',
  `state` tinyint(1) NOT NULL COMMENT '状态 0-已领取 1-已使用 2-已过期',
  `full_money` decimal(15,2) DEFAULT NULL COMMENT '满多少元',
  `reduce_money` decimal(15,2) DEFAULT NULL COMMENT '减多少元',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户关联优惠券信息实体类';

-- ----------------------------
-- Table structure for cere_buyer_footprint
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_footprint`;
CREATE TABLE `cere_buyer_footprint` (
  `footprint_id` bigint NOT NULL AUTO_INCREMENT COMMENT '足迹id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `sku_id` bigint NOT NULL COMMENT '规格id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `selected` tinyint(1) DEFAULT NULL COMMENT '是否选中 1-是 0-否',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`footprint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6009 DEFAULT CHARSET=utf8 COMMENT='商品足迹信息表';

-- ----------------------------
-- Table structure for cere_buyer_label
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_label`;
CREATE TABLE `cere_buyer_label` (
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `buyer_label_id` bigint NOT NULL COMMENT '关联平台标签id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户关联标签信息表';

-- ----------------------------
-- Table structure for cere_buyer_notice
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_notice`;
CREATE TABLE `cere_buyer_notice` (
  `notice_id` bigint NOT NULL COMMENT '消息id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户已读公告、站内信消息关系表';

-- ----------------------------
-- Table structure for cere_buyer_receive
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_receive`;
CREATE TABLE `cere_buyer_receive` (
  `receive_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收货地址id',
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `receive_name` varchar(20) NOT NULL COMMENT '收货人姓名',
  `receive_phone` varchar(20) NOT NULL COMMENT '收货人电话',
  `receive_adress` varchar(50) NOT NULL COMMENT '收货地址',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `label` varchar(5) NOT NULL COMMENT '地址标签 家、公司、学校',
  `if_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`receive_id`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8 COMMENT='客户收货地址信息表';

-- ----------------------------
-- Table structure for cere_buyer_search
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_search`;
CREATE TABLE `cere_buyer_search` (
  `search_id` bigint NOT NULL AUTO_INCREMENT COMMENT '搜索id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `search` varchar(20) DEFAULT NULL COMMENT '搜索内容',
  PRIMARY KEY (`search_id`)
) ENGINE=InnoDB AUTO_INCREMENT=350 DEFAULT CHARSET=utf8 COMMENT='客户历史搜索信息表';

-- ----------------------------
-- Table structure for cere_buyer_seckill_visit
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_seckill_visit`;
CREATE TABLE `cere_buyer_seckill_visit` (
  `shop_seckill_id` bigint NOT NULL COMMENT '秒杀活动id',
  `buyer_user_id` bigint NOT NULL COMMENT '浏览客户id',
  `visit_time` varchar(20) NOT NULL COMMENT '浏览时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户浏览秒杀记录表';

-- ----------------------------
-- Table structure for cere_buyer_shop_coupon
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_shop_coupon`;
CREATE TABLE `cere_buyer_shop_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shop_operate_id` bigint DEFAULT NULL COMMENT '运营计划id',
  `shop_coupon_id` bigint NOT NULL COMMENT '商家优惠券id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `coupon_name` varchar(20) NOT NULL COMMENT '优惠券名称',
  `start_time` varchar(20) DEFAULT NULL COMMENT '用券开始时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '用券结束时间',
  `coupon_type` tinyint(1) NOT NULL COMMENT '优惠券类型 1-满减券 2-折扣券',
  `state` tinyint(1) NOT NULL COMMENT '状态 0-已领取 1-已使用 2-已过期',
  `full_money` decimal(15,2) DEFAULT NULL COMMENT '满多少元',
  `reduce_money` decimal(15,2) DEFAULT NULL COMMENT '减多少元/打几折',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8 COMMENT='客户关联商家优惠券信息表';

-- ----------------------------
-- Table structure for cere_buyer_shop_label
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_shop_label`;
CREATE TABLE `cere_buyer_shop_label` (
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `label_id` bigint NOT NULL COMMENT '关联商家标签id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户关联商家标签信息表';

-- ----------------------------
-- Table structure for cere_buyer_user
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_user`;
CREATE TABLE `cere_buyer_user` (
  `buyer_user_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `wechat_open_id` varchar(50) DEFAULT NULL COMMENT '微信openID',
  `wechat_union_id` varchar(50) DEFAULT NULL COMMENT '微信unionId',
  `wechat_name` varchar(20) DEFAULT NULL COMMENT '微信昵称',
  `wechat_number` varchar(20) DEFAULT NULL COMMENT '微信号',
  `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `head_image` varchar(255) DEFAULT NULL COMMENT '头像图片',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用   1-是 0-否',
  `if_black` tinyint(1) DEFAULT '0' COMMENT '是否加入黑名单 1-是 0-否',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `token` varchar(128) DEFAULT NULL COMMENT '请求token',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`buyer_user_id`),
  UNIQUE KEY `index` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=996 DEFAULT CHARSET=utf8 COMMENT='客户信息表';

-- ----------------------------
-- Table structure for cere_buyer_withdrawal
-- ----------------------------
DROP TABLE IF EXISTS `cere_buyer_withdrawal`;
CREATE TABLE `cere_buyer_withdrawal` (
  `withdrawal_id` bigint NOT NULL AUTO_INCREMENT COMMENT '提现申请id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `bank_name` varchar(50) NOT NULL COMMENT '银行名称',
  `bank_card` varchar(50) NOT NULL COMMENT '银行卡号',
  `withdrawal_money` decimal(15,2) NOT NULL COMMENT '提现金额',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '处理状态  0-审核中 1-通过 2-拒绝',
  `apply_time` varchar(20) DEFAULT NULL COMMENT '申请时间',
  `handle_time` varchar(20) DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`withdrawal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='客户提现信息表';

-- ----------------------------
-- Table structure for cere_cart_attribute
-- ----------------------------
DROP TABLE IF EXISTS `cere_cart_attribute`;
CREATE TABLE `cere_cart_attribute` (
  `cart_id` bigint NOT NULL COMMENT '关联购物车id',
  `sku_name` varchar(10) DEFAULT NULL COMMENT '规格名',
  `sku_value` varchar(10) DEFAULT NULL COMMENT '规格值',
  `name_code` varchar(20) DEFAULT NULL COMMENT '规格名级别',
  `value_code` varchar(20) DEFAULT NULL COMMENT '规格值级别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车商品属性表';

-- ----------------------------
-- Table structure for cere_city_manage
-- ----------------------------
DROP TABLE IF EXISTS `cere_city_manage`;
CREATE TABLE `cere_city_manage` (
  `city_id` bigint NOT NULL AUTO_INCREMENT COMMENT '城市id',
  `city_pid` bigint NOT NULL COMMENT '上级节点id',
  `city_name` varchar(20) DEFAULT NULL COMMENT '城市名称',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=820001 DEFAULT CHARSET=utf8 COMMENT='城市管理信息表';

-- ----------------------------
-- Table structure for cere_collage_order
-- ----------------------------
DROP TABLE IF EXISTS `cere_collage_order`;
CREATE TABLE `cere_collage_order` (
  `collage_id` bigint NOT NULL AUTO_INCREMENT COMMENT '拼团单id',
  `shop_group_work_id` bigint NOT NULL COMMENT '拼团活动id',
  `state` tinyint(1) NOT NULL COMMENT '拼单状态，0-待成团，1-拼团成功，2-拼团失败',
  `collage_name` varchar(255) DEFAULT NULL COMMENT '拼团名称',
  `buyer_user_id` bigint DEFAULT NULL COMMENT '发起人用户ID',
  `surplus_number` int DEFAULT NULL COMMENT '剩余人数',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`collage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 COMMENT='拼单信息表';

-- ----------------------------
-- Table structure for cere_collage_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_collage_order_detail`;
CREATE TABLE `cere_collage_order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `collage_id` bigint NOT NULL COMMENT '拼团单id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `state` tinyint(1) DEFAULT NULL COMMENT '状态   1-正常 0-失效订单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8 COMMENT='拼单关联订单信息表';

-- ----------------------------
-- Table structure for cere_comment_word
-- ----------------------------
DROP TABLE IF EXISTS `cere_comment_word`;
CREATE TABLE `cere_comment_word` (
  `comment_id` bigint NOT NULL COMMENT '评论id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `word_id` bigint NOT NULL COMMENT '关键词id',
  `key_word` varchar(20) NOT NULL COMMENT '关键词'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关键词关联评论信息表';

-- ----------------------------
-- Table structure for cere_distribution_order
-- ----------------------------
DROP TABLE IF EXISTS `cere_distribution_order`;
CREATE TABLE `cere_distribution_order` (
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `order_formid` varchar(20) NOT NULL COMMENT '订单编号',
  `distributor_id` bigint NOT NULL COMMENT '关联分销员id',
  `distributor_name` varchar(20) NOT NULL COMMENT '分销员名称',
  `distributor_phone` varchar(20) DEFAULT NULL COMMENT '分销员手机号',
  `price` decimal(15,2) NOT NULL COMMENT '订单实付金额',
  `commission` decimal(15,2) NOT NULL COMMENT '佣金',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否结算 1-是 0-否',
  `transaction_time` varchar(20) NOT NULL COMMENT '交易时间',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '佣金类型 1-直接佣金 2-间接佣金'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分销员订单信息表';

-- ----------------------------
-- Table structure for cere_distributor_buyer
-- ----------------------------
DROP TABLE IF EXISTS `cere_distributor_buyer`;
CREATE TABLE `cere_distributor_buyer` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `distributor_id` bigint NOT NULL COMMENT '关联分销员id',
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `if_bind` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否绑定 1-是 0-否',
  `update_time` varchar(20) DEFAULT NULL COMMENT '关系更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分销员绑定客户信息表';

-- ----------------------------
-- Table structure for cere_label_attribute
-- ----------------------------
DROP TABLE IF EXISTS `cere_label_attribute`;
CREATE TABLE `cere_label_attribute` (
  `label_id` bigint NOT NULL COMMENT '关联标签id',
  `image` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `link` varchar(500) DEFAULT NULL COMMENT '链接'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺标签属性表';

-- ----------------------------
-- Table structure for cere_label_source
-- ----------------------------
DROP TABLE IF EXISTS `cere_label_source`;
CREATE TABLE `cere_label_source` (
  `label_id` bigint NOT NULL COMMENT '店铺标签id',
  `label_type` tinyint(1) DEFAULT NULL COMMENT '素材类型 1-图片 2-视频',
  `image` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `link` varchar(255) DEFAULT NULL COMMENT '链接'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺素材信息表';

-- ----------------------------
-- Table structure for cere_logistics_charge
-- ----------------------------
DROP TABLE IF EXISTS `cere_logistics_charge`;
CREATE TABLE `cere_logistics_charge` (
  `logistics_id` bigint NOT NULL COMMENT '关联物流方案id',
  `region` varchar(50) NOT NULL COMMENT '配送区域 省-市',
  `weight` decimal(5,2) DEFAULT NULL COMMENT '首重 多少KG/件',
  `price` decimal(15,2) DEFAULT NULL COMMENT '首重价格',
  `second_weight` decimal(5,2) DEFAULT NULL COMMENT '续重',
  `second_price` decimal(15,2) DEFAULT NULL COMMENT '续重价格'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流计费明细信息表';

-- ----------------------------
-- Table structure for cere_message_log
-- ----------------------------
DROP TABLE IF EXISTS `cere_message_log`;
CREATE TABLE `cere_message_log` (
  `message_id` bigint NOT NULL COMMENT '关联短信id',
  `template` varchar(20) NOT NULL COMMENT '短信模板编号',
  `phone` varchar(20) NOT NULL COMMENT '接收者',
  `subject` varchar(50) NOT NULL COMMENT '主体',
  `message` varchar(255) NOT NULL COMMENT '发送内容',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '执行状态 1-执行成功 2-执行失败',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送记录信息表';

-- ----------------------------
-- Table structure for cere_notice
-- ----------------------------
DROP TABLE IF EXISTS `cere_notice`;
CREATE TABLE `cere_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `notice_type` tinyint(1) NOT NULL COMMENT '消息类型 1-系统消息（系统自动发送） 2-公告（后台录入） 3-站内信',
  `jump` tinyint(1) DEFAULT NULL COMMENT '跳转类型 1-商品详情 2-订单详情',
  `receive` tinyint(1) DEFAULT '1' COMMENT '接收用户类型 1-全部用户 2-商家 3-普通用户',
  `if_read` tinyint(1) DEFAULT NULL COMMENT '是否读取 1-是 0-否',
  `buyer_user_id` bigint DEFAULT NULL COMMENT '客户id',
  `shop_id` bigint DEFAULT NULL COMMENT '店铺id',
  `notice_title` varchar(20) NOT NULL COMMENT '消息标题',
  `notice_content` longtext NOT NULL COMMENT '消息内容',
  `only` bigint DEFAULT NULL COMMENT '跳转详情唯一标识',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=454 DEFAULT CHARSET=utf8 COMMENT='系统消息表';

-- ----------------------------
-- Table structure for cere_order_after
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_after`;
CREATE TABLE `cere_order_after` (
  `after_id` bigint NOT NULL AUTO_INCREMENT COMMENT '售后单id',
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `after_formid` varchar(20) NOT NULL COMMENT '售后单号',
  `after_state` tinyint NOT NULL DEFAULT '1' COMMENT '售后状态 1-审核中 2-退款中 3-退货中 4-退款成功 5-退款失败 6-审核不通过 7-评审中 8-退货完成，拒绝退款 9-已关闭 10-审核通过',
  `goods_state` tinyint(1) DEFAULT NULL COMMENT '（货物状态）是否收到货 1-是 0-否',
  `after_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '售后类型  1-仅退款  2-退货退款',
  `price` decimal(15,2) NOT NULL COMMENT '退款金额',
  `explain` varchar(255) DEFAULT NULL COMMENT '买家说明',
  `return_reason` varchar(255) DEFAULT NULL COMMENT '售后原因',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `reason` varchar(255) DEFAULT NULL COMMENT '商家处理留言',
  `image` varchar(1000) DEFAULT NULL COMMENT '图片地址（多个以逗号隔开）',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`after_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='售后单信息表';

-- ----------------------------
-- Table structure for cere_order_dilever
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_dilever`;
CREATE TABLE `cere_order_dilever` (
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `express` bigint NOT NULL COMMENT '快递公司（取数据字典）',
  `deliver_formid` varchar(20) NOT NULL COMMENT '快递单号',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单发货信息表';

-- ----------------------------
-- Table structure for cere_order_logistics
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_logistics`;
CREATE TABLE `cere_order_logistics` (
  `logistics_id` bigint NOT NULL AUTO_INCREMENT COMMENT '物流方案id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `logistics_name` varchar(30) NOT NULL COMMENT '方案名称',
  `charge_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '计费方式 1-按件数 2-按重量 3-全国包邮',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`logistics_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='物流方案设置信息表';

-- ----------------------------
-- Table structure for cere_order_parent
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_parent`;
CREATE TABLE `cere_order_parent` (
  `parent_id` bigint NOT NULL AUTO_INCREMENT COMMENT '父订单id',
  `parent_formid` varchar(20) NOT NULL COMMENT '父订单编号',
  `order_price` decimal(15,2) NOT NULL COMMENT '所有子订单总价之和',
  `logistics_price` decimal(15,2) DEFAULT NULL COMMENT '所有子订单物流费用之和',
  `price` decimal(15,2) NOT NULL COMMENT '所有子订单支付金额之和',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=944 DEFAULT CHARSET=utf8 COMMENT='父订单信息表';

-- ----------------------------
-- Table structure for cere_order_product
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_product`;
CREATE TABLE `cere_order_product` (
  `order_product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单商品明细id',
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `product_id` bigint NOT NULL COMMENT '关联商品id',
  `sku_id` bigint NOT NULL COMMENT '关联规格id',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `number` int NOT NULL COMMENT '购买数量',
  `product_price` decimal(15,2) NOT NULL COMMENT '商品售价',
  `image` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `SKU` varchar(50) DEFAULT NULL COMMENT 'SKU',
  `weight` decimal(15,2) DEFAULT NULL COMMENT '重量',
  PRIMARY KEY (`order_product_id`),
  KEY `index` (`sku_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=990 DEFAULT CHARSET=utf8 COMMENT='订单商品信息表';

-- ----------------------------
-- Table structure for cere_order_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_product_attribute`;
CREATE TABLE `cere_order_product_attribute` (
  `order_product_id` bigint NOT NULL COMMENT '关联订单商品明细id',
  `sku_name` varchar(10) DEFAULT NULL COMMENT '规格名',
  `sku_value` varchar(10) DEFAULT NULL COMMENT '规格值',
  `name_code` varchar(20) DEFAULT NULL COMMENT '规格名级别',
  `value_code` varchar(20) DEFAULT NULL COMMENT '规格值级别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品属性表';

-- ----------------------------
-- Table structure for cere_order_reconciliation
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_reconciliation`;
CREATE TABLE `cere_order_reconciliation` (
  `reconciliation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '对账单id',
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `money` decimal(15,2) NOT NULL COMMENT '金额',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态  1-冻结 2-解冻',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '收支状态 1-收入(付款) 2-支出（退款）',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`reconciliation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=573 DEFAULT CHARSET=utf8 COMMENT='订单对账单信息表';

-- ----------------------------
-- Table structure for cere_order_return
-- ----------------------------
DROP TABLE IF EXISTS `cere_order_return`;
CREATE TABLE `cere_order_return` (
  `return_id` bigint NOT NULL AUTO_INCREMENT COMMENT '退货单id',
  `return_formid` varchar(20) NOT NULL COMMENT '退货单号',
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退货单信息表';

-- ----------------------------
-- Table structure for cere_pay_log
-- ----------------------------
DROP TABLE IF EXISTS `cere_pay_log`;
CREATE TABLE `cere_pay_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `order_formid` varchar(32) DEFAULT NULL COMMENT '关联订单编号',
  `out_trade_no` varchar(32) NOT NULL COMMENT '订单支付编号',
  `transaction_id` varchar(32) NOT NULL COMMENT '支付生成的订单号',
  `out_refund_no` varchar(64) NOT NULL COMMENT '商户系统内部的退款单号,商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。退货流水号',
  `total_fee` decimal(15,2) DEFAULT NULL COMMENT '订单总金额',
  `refund_fee` decimal(15,2) DEFAULT NULL COMMENT '退款总金额，订单总金额，单位为分',
  `user_id` varchar(20) DEFAULT NULL COMMENT '支付或退款用户ID',
  `state` varchar(10) NOT NULL COMMENT '支付、退款、提现',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `payment_mode` tinyint(1) DEFAULT '1' COMMENT '支付方式 1-微信 2-支付宝',
  `after` varchar(50) DEFAULT NULL COMMENT '售后单id,多个以逗号隔开（多次退款使用）',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2353 DEFAULT CHARSET=utf8 COMMENT='支付日志信息表';

-- ----------------------------
-- Table structure for cere_platform_activity
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_activity`;
CREATE TABLE `cere_platform_activity` (
  `activity_id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动id',
  `activity_name` varchar(20) DEFAULT NULL,
  `activity_introduce` varchar(255) DEFAULT NULL COMMENT '活动介绍',
  `sign_start_time` varchar(20) DEFAULT NULL COMMENT '报名开始时间',
  `sign_end_time` varchar(20) DEFAULT NULL COMMENT '报名结束时间',
  `activity_start_time` varchar(20) DEFAULT NULL COMMENT '活动开始时间',
  `activity_end_time` varchar(20) DEFAULT NULL COMMENT '活动结束时间',
  `if_bond` tinyint(1) DEFAULT '1' COMMENT '是否需要保证金  1-是 0-否',
  `bond_money` decimal(15,2) DEFAULT NULL COMMENT '保证金金额',
  `activity_label` varchar(20) DEFAULT NULL COMMENT '活动标签',
  `discount_mode` tinyint(1) DEFAULT '1' COMMENT '优惠方式 1-满减 2-优惠券',
  `discount_programme` tinyint(1) DEFAULT '1' COMMENT '优惠方案  1-叠加优惠 2-阶梯优惠',
  `image` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `state` tinyint(1) DEFAULT '0' COMMENT '活动状态  0-报名未开始 1-报名进行中 2-活动待开始 3-活动进行中 4-活动已结束',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='平台营销活动信息表';

-- ----------------------------
-- Table structure for cere_platform_activity_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_activity_detail`;
CREATE TABLE `cere_platform_activity_detail` (
  `coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠券id',
  `activity_id` bigint NOT NULL COMMENT '关联营销活动id',
  `full_money` decimal(15,2) NOT NULL COMMENT '满多少元',
  `reduce_money` decimal(15,2) NOT NULL COMMENT '减多少元',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='营销活动优惠方案明细信息表';

-- ----------------------------
-- Table structure for cere_platform_after
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_after`;
CREATE TABLE `cere_platform_after` (
  `order_id` bigint NOT NULL COMMENT '关联订单id',
  `reason` varchar(255) NOT NULL COMMENT '问题描述',
  `image` varchar(1000) NOT NULL COMMENT '举证图片',
  `remark` varchar(255) DEFAULT NULL COMMENT '处理备注',
  `state` tinyint(1) DEFAULT '0' COMMENT '处理状态 0-未处理 1-同意 2-拒绝',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `handle_time` varchar(20) DEFAULT NULL COMMENT '平台处理时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台介入售后信息表';

-- ----------------------------
-- Table structure for cere_platform_business
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_business`;
CREATE TABLE `cere_platform_business` (
  `business_user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商家用户id',
  `username` varchar(20) NOT NULL COMMENT '商家登录账号',
  `password` varchar(128) NOT NULL COMMENT '商家登录密码',
  `name` varchar(20) DEFAULT NULL COMMENT '商家昵称',
  `sex` varchar(2) DEFAULT NULL COMMENT '商家性别',
  `email` varchar(255) DEFAULT NULL COMMENT '商家邮箱',
  `token` varchar(128) NOT NULL COMMENT '商家用户token',
  `state` tinyint(1) DEFAULT NULL COMMENT '是否启用 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`business_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COMMENT='平台商家用户表';

-- ----------------------------
-- Table structure for cere_platform_canvas
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_canvas`;
CREATE TABLE `cere_platform_canvas` (
  `canvas_id` bigint NOT NULL AUTO_INCREMENT COMMENT '画布id',
  `terminal` tinyint(1) NOT NULL COMMENT '终端 1-小程序 2-H5 3-APP 4-PC',
  `json` text COMMENT '画布json数据',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '类型 1-系统画布 2-自定义页面 3-商家店铺装修',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `shop_id` bigint NOT NULL DEFAULT '0' COMMENT '店铺id，当type=3的时候，值为具体的店铺id，其它情况为0',
  `custom_id` bigint DEFAULT '0' COMMENT '自定义页面id，当type=2时，值为自定义页面对应的id，其它情况为0',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`canvas_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='画布信息表';

-- ----------------------------
-- Table structure for cere_platform_canvas_custom
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_canvas_custom`;
CREATE TABLE `cere_platform_canvas_custom` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='自定义页面信息表';

-- ----------------------------
-- Table structure for cere_platform_dict
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_dict`;
CREATE TABLE `cere_platform_dict` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_pid` bigint NOT NULL COMMENT '父节点id',
  `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
  `dict_describe` mediumtext COMMENT '字典描述',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1900 DEFAULT CHARSET=utf8 COMMENT='数据字典信息表';

-- ----------------------------
-- Table structure for cere_platform_express
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_express`;
CREATE TABLE `cere_platform_express` (
  `platform_user_id` bigint DEFAULT NULL COMMENT '操作人用户id   初始化为空',
  `express_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '对接第三方快递类型 1-快递100 2-快递鸟',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流查询策略表';

-- ----------------------------
-- Table structure for cere_platform_label
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_label`;
CREATE TABLE `cere_platform_label` (
  `buyer_label_id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户标签id',
  `label_name` varchar(32) NOT NULL COMMENT '标签名称',
  `label_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '标签类型 1-手动标签 2-自动标签',
  `meet_conditions` tinyint(1) NOT NULL DEFAULT '1' COMMENT '满足条件 1-满足任意一个被选中条件即可  2-必须满足所有被选中条件',
  `last_consumption_time` tinyint(1) DEFAULT '1' COMMENT '是否选中最后消费时间 1-是 0-否',
  `consumption_frequency` tinyint(1) DEFAULT '1' COMMENT '是否选中累计消费次数  1-是 0-否',
  `consumption_money` tinyint(1) DEFAULT '1' COMMENT '是否选中累计交易金额 1-是 0-否',
  `consumption_day` int DEFAULT NULL COMMENT '最近几天（天）',
  `consumption_start_time` varchar(20) DEFAULT NULL COMMENT '最后消费开始时间',
  `consumption_end_time` varchar(20) DEFAULT NULL COMMENT '最后消费结束时间',
  `frequency_start` int DEFAULT NULL COMMENT '起始次数',
  `frequency_end` int DEFAULT NULL COMMENT '截止次数',
  `money_start` decimal(15,2) DEFAULT NULL COMMENT '起始金额',
  `money_end` decimal(15,2) DEFAULT NULL COMMENT '截止金额',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`buyer_label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='平台标签信息表';

-- ----------------------------
-- Table structure for cere_platform_log
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_log`;
CREATE TABLE `cere_platform_log` (
  `log_id` bigint NOT NULL AUTO_INCREMENT,
  `platform_user_id` bigint NOT NULL COMMENT '关联用户id',
  `module` varchar(20) NOT NULL COMMENT '模块名称',
  `operation` varchar(20) NOT NULL COMMENT '操作对应工程',
  `operation_describtion` varchar(100) NOT NULL COMMENT '操作功能描述',
  `only` varchar(20) DEFAULT NULL COMMENT '操作功能对应唯一标识id',
  `create_time` varchar(20) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29746 DEFAULT CHARSET=utf8 COMMENT='平台系统日志表';

-- ----------------------------
-- Table structure for cere_platform_permission
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_permission`;
CREATE TABLE `cere_platform_permission` (
  `permission_id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permission_pid` bigint NOT NULL COMMENT '父节点id',
  `permission_name` varchar(255) NOT NULL COMMENT '名称',
  `permission_uri` varchar(100) DEFAULT NULL COMMENT '路由URI',
  `permission` varchar(100) DEFAULT NULL COMMENT '组件',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标地址',
  `describe` varchar(50) DEFAULT NULL COMMENT '文字描述',
  `resource_type` enum('menu','button','catalog') NOT NULL COMMENT '权限类型',
  `sort` int DEFAULT NULL COMMENT '排序号',
  `project` tinyint(1) DEFAULT NULL COMMENT '权限所属项目  1-平台端 2-商家端',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=908 DEFAULT CHARSET=utf8 COMMENT='权限菜单信息表';

-- ----------------------------
-- Table structure for cere_platform_role
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_role`;
CREATE TABLE `cere_platform_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_describe` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `project` tinyint(1) DEFAULT NULL COMMENT '角色所属项目  1-平台端 2-商家端',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for cere_platform_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_role_permission`;
CREATE TABLE `cere_platform_role_permission` (
  `role_id` bigint NOT NULL COMMENT '关联角色id',
  `permission_id` bigint NOT NULL COMMENT '关联权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台账户角色关联权限实体类';

-- ----------------------------
-- Table structure for cere_platform_sensitive
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_sensitive`;
CREATE TABLE `cere_platform_sensitive` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sensitive_word` varchar(500) DEFAULT NULL COMMENT '敏感词库',
  `state` tinyint(1) DEFAULT '0' COMMENT '是否开启 1-是 0-否',
  `handle_measures` tinyint(1) DEFAULT NULL COMMENT '处理措施  1-禁止发布 2-需审核',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='敏感词库信息表';

-- ----------------------------
-- Table structure for cere_platform_shop
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_shop`;
CREATE TABLE `cere_platform_shop` (
  `shop_id` bigint NOT NULL AUTO_INCREMENT,
  `shop_code` varchar(10) NOT NULL COMMENT '商家编码',
  `shop_name` varchar(30) NOT NULL COMMENT '店铺名称',
  `shop_brief` varchar(50) DEFAULT NULL COMMENT '简介',
  `shop_phone` varchar(20) NOT NULL COMMENT '店铺账号',
  `shop_password` varchar(128) NOT NULL COMMENT '店铺密码',
  `charge_person_name` varchar(50) DEFAULT NULL COMMENT '店铺负责人',
  `charge_person_phone` varchar(50) DEFAULT NULL COMMENT '负责人电话',
  `shop_adress` varchar(500) DEFAULT NULL COMMENT '店铺地址',
  `shop_adress_province` varchar(500) DEFAULT NULL COMMENT '店铺地址-省',
  `shop_adress_city` varchar(500) DEFAULT NULL COMMENT '店铺地址-市',
  `shop_adress_detail` varchar(500) DEFAULT NULL COMMENT '店铺地址-详细地址',
  `shop_logo` varchar(255) DEFAULT NULL COMMENT '店铺logo',
  `effective_date` varchar(20) DEFAULT NULL COMMENT '生效日期  即时生效-设置当前时间 有值-指定日期生效',
  `effective_year` int DEFAULT NULL COMMENT '生效时限（年）',
  `contract_state` tinyint(1) DEFAULT '1' COMMENT '合同状态 1-有效 0-无效',
  `authentication_state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '认证状态 1-未认证 2-审核中 3-审核通过 4-审核失败 5-签约成功（使用中）',
  `check_state` tinyint(1) NOT NULL COMMENT '入驻处理状态  0-未处理 1-通过 2-拒绝',
  `state` tinyint(1) NOT NULL COMMENT '是否启用 1-是 0-否',
  `authen_type` tinyint(1) DEFAULT NULL COMMENT '主体类型 1-个人 2-个体工商户 3-企业 4-其他组织',
  `wx_image` varchar(255) DEFAULT NULL COMMENT '微信收款码',
  `ailpay_image` varchar(255) DEFAULT NULL COMMENT '支付宝收款码',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='商家信息表';

-- ----------------------------
-- Table structure for cere_platform_user
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_user`;
CREATE TABLE `cere_platform_user` (
  `platform_user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '账户id',
  `username` varchar(20) NOT NULL COMMENT '平台登录账号',
  `name` varchar(20) NOT NULL COMMENT '昵称',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) DEFAULT NULL COMMENT '账户手机号',
  `password` varchar(128) DEFAULT NULL COMMENT '账户登录密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `token` varchar(128) NOT NULL COMMENT '账户token',
  `state` tinyint(1) NOT NULL COMMENT '是否启用   1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`platform_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='平台账户信息表';

-- ----------------------------
-- Table structure for cere_platform_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_user_role`;
CREATE TABLE `cere_platform_user_role` (
  `platform_user_id` bigint NOT NULL COMMENT '平台账户编号',
  `role_id` bigint NOT NULL COMMENT '关联角色表id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台账户关联角色信息表';

-- ----------------------------
-- Table structure for cere_platform_word
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_word`;
CREATE TABLE `cere_platform_word` (
  `word_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关键词id',
  `key_word` varchar(20) NOT NULL COMMENT '关键词内容',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='关键词信息表';

-- ----------------------------
-- Table structure for cere_platfrom_message
-- ----------------------------
DROP TABLE IF EXISTS `cere_platfrom_message`;
CREATE TABLE `cere_platfrom_message` (
  `message_id` bigint NOT NULL AUTO_INCREMENT COMMENT '短信id',
  `message_project` varchar(20) NOT NULL COMMENT '第三方短信名称',
  `message_template` varchar(20) NOT NULL COMMENT '短信模板名称',
  `template_code` varchar(50) NOT NULL COMMENT '模板编码',
  `template_sign` varchar(50) NOT NULL COMMENT '模板签名',
  `template_describe` varchar(255) DEFAULT NULL COMMENT '模板描述',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信账号配置信息表';

-- ----------------------------
-- Table structure for cere_product_answer
-- ----------------------------
DROP TABLE IF EXISTS `cere_product_answer`;
CREATE TABLE `cere_product_answer` (
  `answer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '回答id',
  `problem_id` bigint NOT NULL COMMENT '提问id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `answer` varchar(255) DEFAULT NULL COMMENT '回答',
  `if_anonymous` tinyint(1) NOT NULL COMMENT '是否匿名 1-是 0-否',
  `selected` tinyint(1) DEFAULT NULL COMMENT '是否选中 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '回答时间',
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='商品提问回答信息表';

-- ----------------------------
-- Table structure for cere_product_classify
-- ----------------------------
DROP TABLE IF EXISTS `cere_product_classify`;
CREATE TABLE `cere_product_classify` (
  `classify_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `classify_pid` bigint NOT NULL COMMENT '分类父id',
  `classify_hierarchy` varchar(255) NOT NULL COMMENT '分类层级结构名称',
  `classify_level` tinyint NOT NULL COMMENT '分类级别',
  `classify_level_hierarchy` varchar(100) DEFAULT NULL COMMENT '分类层级结构',
  `classify_name` varchar(20) NOT NULL COMMENT '分类名称',
  `classify_image` varchar(255) DEFAULT NULL COMMENT '分类图片地址',
  `sort` int DEFAULT NULL COMMENT '排序号',
  `link` bigint DEFAULT NULL COMMENT '链接(取数据字典)',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`classify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=802 DEFAULT CHARSET=utf8 COMMENT='商品分类信息表';

-- ----------------------------
-- Table structure for cere_product_image
-- ----------------------------
DROP TABLE IF EXISTS `cere_product_image`;
CREATE TABLE `cere_product_image` (
  `product_id` bigint NOT NULL COMMENT '关联商品id',
  `product_image` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `sort` int DEFAULT NULL COMMENT '排序值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片信息表';

-- ----------------------------
-- Table structure for cere_product_problem
-- ----------------------------
DROP TABLE IF EXISTS `cere_product_problem`;
CREATE TABLE `cere_product_problem` (
  `problem_id` bigint NOT NULL AUTO_INCREMENT COMMENT '提问id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `problem` varchar(255) DEFAULT NULL COMMENT '问题',
  `buyer_user_id` bigint NOT NULL COMMENT '客户id',
  `if_anonymous` tinyint(1) NOT NULL COMMENT '是否匿名 1-是 0-否',
  `selected` tinyint(1) DEFAULT NULL COMMENT '是否选中 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '提问时间',
  PRIMARY KEY (`problem_id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='商品提问信息表';

-- ----------------------------
-- Table structure for cere_product_sku
-- ----------------------------
DROP TABLE IF EXISTS `cere_product_sku`;
CREATE TABLE `cere_product_sku` (
  `sku_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品规格id',
  `product_id` bigint NOT NULL COMMENT '关联商品id',
  `SKU` varchar(50) DEFAULT NULL COMMENT 'SKU',
  `price` decimal(15,2) NOT NULL COMMENT '售价',
  `original_price` decimal(15,2) NOT NULL COMMENT '原价',
  `stock_number` int DEFAULT NULL COMMENT '库存数量',
  `total` int DEFAULT NULL COMMENT '库存总数',
  `weight` decimal(15,2) DEFAULT NULL COMMENT '重量',
  `sku_image` varchar(255) DEFAULT NULL COMMENT '规格图片地址',
  `style` tinyint(1) NOT NULL DEFAULT '1' COMMENT '款式  0-单款式 1-多款式',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sku_id`),
  UNIQUE KEY `index` (`sku_id`,`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=486 DEFAULT CHARSET=utf8 COMMENT='商品规格信息表';

-- ----------------------------
-- Table structure for cere_redis_key
-- ----------------------------
DROP TABLE IF EXISTS `cere_redis_key`;
CREATE TABLE `cere_redis_key` (
  `redis_key` varchar(50) DEFAULT NULL COMMENT 'redis的延时任务key',
  `end_time` varchar(20) DEFAULT NULL COMMENT '结束时间（到这个时间就需要业务处理）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='redis的过期键信息表';

-- ----------------------------
-- Table structure for cere_shop_bank
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_bank`;
CREATE TABLE `cere_shop_bank` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `card_name` varchar(10) NOT NULL COMMENT '持卡人姓名',
  `card_number` varchar(20) NOT NULL COMMENT '银行卡号',
  `bank` bigint NOT NULL COMMENT '所属银行（取数据字典）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺收款账户信息表';

-- ----------------------------
-- Table structure for cere_shop_banner
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_banner`;
CREATE TABLE `cere_shop_banner` (
  `banner_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'banner配置id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `banner_style` tinyint(1) NOT NULL COMMENT '样式   1-留边  2-填充',
  `banner_image` varchar(1000) DEFAULT NULL COMMENT 'banner图片地址（多张图片以逗号隔开）',
  `banner_url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `state` tinyint(1) NOT NULL COMMENT '是否启用 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='banner配置信息表';

-- ----------------------------
-- Table structure for cere_shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_cart`;
CREATE TABLE `cere_shop_cart` (
  `cart_id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `product_id` bigint NOT NULL COMMENT '关联商品id',
  `sku_id` bigint NOT NULL COMMENT '关联规格id',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `number` int NOT NULL COMMENT '购买数量',
  `product_price` decimal(15,2) NOT NULL COMMENT '商品售价',
  `image` varchar(255) NOT NULL COMMENT '图片地址',
  `SKU` varchar(50) DEFAULT NULL COMMENT 'SKU',
  `weight` decimal(15,2) DEFAULT NULL COMMENT '重量',
  `selected` tinyint(1) DEFAULT NULL COMMENT '是否选中 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=utf8 COMMENT='购物车信息表';

-- ----------------------------
-- Table structure for cere_shop_check
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_check`;
CREATE TABLE `cere_shop_check` (
  `check_id` bigint NOT NULL AUTO_INCREMENT COMMENT '处理id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `check_handle` tinyint(1) NOT NULL COMMENT '入住处理 1-同意入驻 0-拒绝入驻',
  `reason` varchar(255) DEFAULT NULL COMMENT '处理原因',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='店铺入驻处理信息表';

-- ----------------------------
-- Table structure for cere_shop_comment
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_comment`;
CREATE TABLE `cere_shop_comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `shop_name` varchar(20) DEFAULT NULL COMMENT '商家名称',
  `shop_code` varchar(20) DEFAULT NULL COMMENT '商家编码',
  `product_id` bigint DEFAULT NULL COMMENT '关联商品id',
  `sku_id` bigint DEFAULT NULL COMMENT '规格id',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `buyer_user_id` bigint DEFAULT NULL COMMENT '关联客户id',
  `image` varchar(1000) DEFAULT NULL COMMENT '图片地址（多个以逗号隔开）',
  `add_image` varchar(1000) DEFAULT NULL COMMENT '追加图片',
  `comment` varchar(255) DEFAULT NULL COMMENT '评论',
  `add_comment` varchar(255) DEFAULT NULL COMMENT '追加评论',
  `state` tinyint(1) DEFAULT '0' COMMENT '是否隐藏 1-是 0-否',
  `if_sensitive` tinyint(1) DEFAULT '0' COMMENT '是否敏感词审核  1-是 0-否',
  `add_time` varchar(20) DEFAULT NULL COMMENT '追评时间',
  `star` tinyint(1) DEFAULT NULL COMMENT '商品满意度(宝贝描述) 1-一星 2-二星 3-三星 4-四星 5-五星',
  `des` tinyint(1) DEFAULT NULL COMMENT '描述相符 1-一星 2-二星 3-三星 4-四星 5-五星',
  `delivery` tinyint(1) DEFAULT NULL COMMENT '物流服务 1-一星 2-二星 3-三星 4-四星 5-五星',
  `attitude` tinyint(1) DEFAULT NULL COMMENT '服务态度 1-一星 2-二星 3-三星 4-四星 5-五星',
  `impression` varchar(255) DEFAULT NULL COMMENT '商品印象',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='客户评论信息表';

-- ----------------------------
-- Table structure for cere_shop_conversion
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_conversion`;
CREATE TABLE `cere_shop_conversion` (
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `type` tinyint(1) DEFAULT NULL COMMENT '转化类型 1-访问人数 2-加购人数 3-结账人数 4-调用支付 5-支付成功',
  `create_time` varchar(20) DEFAULT NULL COMMENT '转化时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺转化信息表';

-- ----------------------------
-- Table structure for cere_shop_coupon
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_coupon`;
CREATE TABLE `cere_shop_coupon` (
  `shop_coupon_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺优惠券id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `coupon_name` varchar(20) NOT NULL COMMENT '优惠券名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `coupon_type` tinyint(1) NOT NULL COMMENT '优惠券类型 1-满减券 2-折扣券',
  `apply_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '适用商品 1-全部商品 2-指定商品可用 3-指定商品不可用',
  `threshold` decimal(15,2) NOT NULL COMMENT '使用门槛满多少元，无门槛为0',
  `coupon_content` decimal(15,2) NOT NULL COMMENT '优惠内容减多少元/打多少折',
  `time_type` tinyint(1) NOT NULL COMMENT '用券时间 1-固定时间 2-领券当日起几天内可用',
  `effective_start` varchar(20) DEFAULT NULL COMMENT '用券开始时间',
  `effective_end` varchar(20) DEFAULT NULL COMMENT '用券结束时间',
  `effective_day` int DEFAULT NULL COMMENT '领券当日几天内（天数）',
  `number` int DEFAULT NULL COMMENT '发放数量',
  `stock_number` int DEFAULT NULL COMMENT '库存数量',
  `receive_type` tinyint(1) NOT NULL COMMENT '每人限领次数  1-无限次 2-限制几次',
  `frequency` int DEFAULT NULL COMMENT '限制次数',
  `state` tinyint(1) DEFAULT NULL COMMENT '优惠券状态  0-未开始 1-进行中 2-已结束',
  `if_add` tinyint(1) DEFAULT NULL COMMENT '是否叠加平台优惠 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='店铺优惠券信息表';

-- ----------------------------
-- Table structure for cere_shop_coupon_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_coupon_detail`;
CREATE TABLE `cere_shop_coupon_detail` (
  `shop_coupon_id` bigint NOT NULL COMMENT '店铺优惠券id',
  `product_id` bigint NOT NULL COMMENT '商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺优惠券商品明细信息表';

-- ----------------------------
-- Table structure for cere_shop_crowd
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_crowd`;
CREATE TABLE `cere_shop_crowd` (
  `shop_crowd_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺人群id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `crowd_name` varchar(10) NOT NULL COMMENT '人群名称',
  `shop_buy_yes` int DEFAULT NULL COMMENT '店铺有购买最近几天购买过本店商品的客户（以支付成功为准，不剔除退款）',
  `shop_buy_no` int DEFAULT NULL COMMENT '店铺无购买最近几天没有购买过本店商品的客户（以支付成功为准，不剔除退款）',
  `shop_order_yes` int DEFAULT NULL COMMENT '店铺有下单最近几天在店铺有下单行为的客户（包含未付款客户）',
  `shop_order_no` int DEFAULT NULL COMMENT '店铺无下单最近几天（在店铺没有下单行为的客户）',
  `shop_add_yes` int DEFAULT NULL COMMENT '店铺有加购最近几天（加购过本店商品的客户）',
  `shop_add_no` int DEFAULT NULL COMMENT '店铺无加购最近几天（没有加购过本店商品的客户）',
  `shop_visit_yes` int DEFAULT NULL COMMENT '店铺有访问最近几天（访问过本店的客户）',
  `shop_visit_no` int DEFAULT NULL COMMENT '店铺无访问最近几天（没有访问过本店的客户）',
  `effective_buy` tinyint(1) DEFAULT NULL COMMENT '有效购买次数比较类型  1-大于 2-等于 3-小于',
  `effective_buy_count` int DEFAULT NULL COMMENT '有效购买次数，客户累计在店铺交易成功的订单数量（剔除退款的订单）',
  `effective_price` tinyint(1) DEFAULT NULL COMMENT '有效购买金额比较类型  1-大于 2-等于 3-小于',
  `effective_price_count` decimal(15,2) DEFAULT NULL COMMENT '有效购买金额，客户累计在店铺交易成功的金额数量（剔除退款金额）',
  `users` int DEFAULT NULL COMMENT '客户数量',
  `label_id` varchar(20) DEFAULT NULL COMMENT '商家标签id，多个以逗号隔开',
  `ids` varchar(500) DEFAULT NULL COMMENT '客户id，多个以逗号隔开',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_crowd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='客户分群信息表';

-- ----------------------------
-- Table structure for cere_shop_discount
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_discount`;
CREATE TABLE `cere_shop_discount` (
  `shop_discount_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺限时折扣活动id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `discount_name` varchar(20) NOT NULL COMMENT '活动名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `start_time` varchar(20) NOT NULL COMMENT '活动开始时间',
  `end_time` varchar(20) NOT NULL COMMENT '活动结束时间',
  `if_limit` tinyint(1) NOT NULL COMMENT '商品限购 1-不限购 2-限购',
  `limit_number` int DEFAULT NULL COMMENT '限购几件/人',
  `if_number` tinyint(1) NOT NULL COMMENT '是否限量 1-是 0-否',
  `number` int DEFAULT NULL COMMENT '限制数量',
  `if_enable` tinyint(1) NOT NULL COMMENT '活动预热   1-停用  2-启用',
  `enable_time` int DEFAULT NULL COMMENT '预热几小时前',
  `if_add` tinyint(1) NOT NULL COMMENT '优惠券是否叠加 1-是 0-否',
  `state` tinyint(1) DEFAULT NULL COMMENT '限时折扣活动状态 0-未开始 1-进行中 2-已结束',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_discount_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='店铺限时折扣活动信息表';

-- ----------------------------
-- Table structure for cere_shop_discount_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_discount_detail`;
CREATE TABLE `cere_shop_discount_detail` (
  `shop_discount_id` bigint NOT NULL COMMENT '店铺限时折扣活动id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `sku_id` bigint NOT NULL COMMENT '规格id',
  `discount` decimal(15,2) NOT NULL COMMENT '折扣',
  `price` decimal(15,2) NOT NULL COMMENT '活动价格',
  `number` int DEFAULT NULL COMMENT '限量数量（剩余）',
  `total` int DEFAULT NULL COMMENT '限制总数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺限时折扣活动商品明细信息表';

-- ----------------------------
-- Table structure for cere_shop_distribution_level
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_distribution_level`;
CREATE TABLE `cere_shop_distribution_level` (
  `distributor_level_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分销员等级id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `level_name` varchar(20) NOT NULL COMMENT '等级名称',
  `if_self` tinyint(1) DEFAULT '0' COMMENT '是否开启自购分佣  1-是 0-否',
  `if_money` tinyint(1) DEFAULT '0' COMMENT '是否勾选累计分销金额 1-是 0-否',
  `if_invitation` tinyint(1) DEFAULT '0' COMMENT '是否勾选邀请下级满  1-是 0-否',
  `if_customer` tinyint(1) DEFAULT '0' COMMENT '是否勾选客户  1-是 0-否',
  `condition_money` decimal(15,2) DEFAULT NULL COMMENT '累计分销金额',
  `condition_invitation` int DEFAULT NULL COMMENT '邀请下级满人数',
  `condition_customer` int DEFAULT NULL COMMENT '客户满人数',
  `direct_proportion` int DEFAULT NULL COMMENT '直接分佣比例（存整数最大99）',
  `indirect_proportion` int DEFAULT NULL COMMENT '间接分佣比例（存整数最大99）',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`distributor_level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='店铺分销员等级信息表';

-- ----------------------------
-- Table structure for cere_shop_distributor
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_distributor`;
CREATE TABLE `cere_shop_distributor` (
  `distributor_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分销员id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `distributor_name` varchar(20) NOT NULL COMMENT '分销员昵称',
  `distributor_phone` varchar(20) NOT NULL COMMENT '分销员手机号',
  `distributor_level_id` bigint DEFAULT NULL COMMENT '关联分销员等级id',
  `invitees` bigint DEFAULT NULL COMMENT '邀请人id',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '审核状态 0-待处理 1-审核通过 2-审核不通过',
  `if_Liquidation` tinyint(1) DEFAULT '0' COMMENT '是否清退 1-是 0-否',
  `join_time` varchar(20) DEFAULT NULL COMMENT '审核通过时间',
  `invitation_code` varchar(10) DEFAULT NULL COMMENT '邀请码',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`distributor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='分销员信息表';

-- ----------------------------
-- Table structure for cere_shop_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_enterprise`;
CREATE TABLE `cere_shop_enterprise` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `enterprise_name` varchar(110) NOT NULL COMMENT '企业名称',
  `enterprise_code` varchar(18) NOT NULL COMMENT '统一社会信用代码',
  `enterprise_region` varchar(50) NOT NULL COMMENT '地址  省-市-区',
  `enterprise_adress` varchar(256) DEFAULT NULL COMMENT '详细地址',
  `enterprise_start_time` varchar(20) NOT NULL COMMENT '营业期限开始时间',
  `enterprise_end_time` varchar(20) NOT NULL COMMENT '营业期限结束时间',
  `enterprise_license` varchar(1000) NOT NULL COMMENT '营业执照图片地址（多个以逗号隔开）',
  `enterprise_operator` varchar(30) NOT NULL COMMENT '法人姓名',
  `enterprise_card_type` bigint NOT NULL COMMENT '证件类型 （取数据字典）',
  `enterprise_id_card` varchar(18) NOT NULL COMMENT '法人身份证号码',
  `enterprise_card_start_time` varchar(20) NOT NULL COMMENT '证件有效开始时间',
  `enterprise_card_end_time` varchar(20) NOT NULL COMMENT '证件有效结束时间',
  `enterprise_card_positive` varchar(255) NOT NULL COMMENT '法人身份证正面照',
  `enterprise_card_side` varchar(255) NOT NULL COMMENT '法人身份证反面照',
  `administrators_phone` varchar(20) DEFAULT '' COMMENT '管理员手机号',
  `administrators_email` varchar(50) DEFAULT '' COMMENT '管理员邮箱',
  `account_open_bank` bigint DEFAULT '0' COMMENT '开户银行（取数据字典）',
  `account_bank_name` varchar(50) DEFAULT '' COMMENT '开户银行名称',
  `account_bank_region` varchar(50) DEFAULT '' COMMENT '开户银行地区   省-市',
  `account_bank_card` varchar(50) DEFAULT '' COMMENT '银行账户',
  `shop_abbreviation` varchar(30) DEFAULT '' COMMENT '商户简称',
  `service_phone` varchar(16) DEFAULT '' COMMENT '客服电话',
  `service_providing` bigint DEFAULT '0' COMMENT '提供服务（取数据字典）',
  `shop_index_image` varchar(255) DEFAULT '' COMMENT '店铺首页截图',
  `shop_backstage_image` varchar(255) DEFAULT '' COMMENT '店铺管理后台截图',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺企业认证信息表';

-- ----------------------------
-- Table structure for cere_shop_extension
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_extension`;
CREATE TABLE `cere_shop_extension` (
  `extension_id` bigint NOT NULL AUTO_INCREMENT COMMENT '推广设置id',
  `title` varchar(20) NOT NULL COMMENT '推广标题',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `if_logo` tinyint(1) NOT NULL DEFAULT '1' COMMENT '店铺logo是否展示在二维码 1-是 0-否',
  `if_head` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否展示头像昵称 1-是 0-否',
  `extension_reason` varchar(50) DEFAULT NULL COMMENT '推广语',
  `image` varchar(255) DEFAULT NULL COMMENT '背景图片地址 875x1275像素',
  `poster` varchar(255) DEFAULT NULL COMMENT '海报图片地址',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`extension_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='店铺推广设置信息表';

-- ----------------------------
-- Table structure for cere_shop_group
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_group`;
CREATE TABLE `cere_shop_group` (
  `shop_group_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品分组id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `group_name` varchar(20) NOT NULL COMMENT '分组名称',
  `group_describe` varchar(200) DEFAULT NULL COMMENT '分组描述',
  `group_image` varchar(255) DEFAULT NULL COMMENT '分组封面图片地址',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='店铺商品分组信息表';

-- ----------------------------
-- Table structure for cere_shop_group_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_group_detail`;
CREATE TABLE `cere_shop_group_detail` (
  `shop_group_id` bigint NOT NULL COMMENT '店铺分组id',
  `product_id` bigint NOT NULL COMMENT '商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分组关联商品信息表';

-- ----------------------------
-- Table structure for cere_shop_group_work
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_group_work`;
CREATE TABLE `cere_shop_group_work` (
  `shop_group_work_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺拼团活动id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `group_name` varchar(20) NOT NULL COMMENT '活动名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `start_time` varchar(20) NOT NULL COMMENT '活动开始时间',
  `end_time` varchar(20) NOT NULL COMMENT '活动结束时间',
  `person` int NOT NULL COMMENT '成团人数',
  `effective_time` int NOT NULL COMMENT '成团有效时间几（小时）',
  `if_limit` tinyint(1) NOT NULL COMMENT '商品限购 1-不限购 2-限购',
  `limit_number` int DEFAULT NULL COMMENT '限购几件/人',
  `if_enable` tinyint(1) NOT NULL COMMENT '活动预热   1-停用  2-启用',
  `enable_time` int DEFAULT NULL COMMENT '预热几小时前',
  `if_add` tinyint(1) NOT NULL COMMENT '优惠券是否叠加 1-是 0-否',
  `state` tinyint(1) DEFAULT NULL COMMENT '拼团活动状态 0-未开始 1-进行中 2-已结束',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_group_work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='店铺拼团活动信息表';

-- ----------------------------
-- Table structure for cere_shop_group_work_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_group_work_detail`;
CREATE TABLE `cere_shop_group_work_detail` (
  `shop_group_work_id` bigint NOT NULL COMMENT '店铺拼团活动id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `sku_id` bigint NOT NULL COMMENT '规格id',
  `price` decimal(15,2) NOT NULL COMMENT '拼团价格'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺拼团活动商品明细信息表';

-- ----------------------------
-- Table structure for cere_shop_individual_businesses
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_individual_businesses`;
CREATE TABLE `cere_shop_individual_businesses` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `subject_name` varchar(110) NOT NULL COMMENT '商户名称',
  `subject_code` varchar(18) NOT NULL COMMENT '统一社会信用代码',
  `subject_region` varchar(50) NOT NULL COMMENT '地址  省-市-区',
  `subject_adress` varchar(256) DEFAULT NULL COMMENT '详细地址',
  `subject_start_time` varchar(20) NOT NULL COMMENT '营业期限开始时间',
  `subject_end_time` varchar(20) NOT NULL COMMENT '营业期限结束时间',
  `subject_license` varchar(1000) NOT NULL COMMENT '营业执照图片地址（多个以逗号隔开）',
  `subject_operator` varchar(30) NOT NULL COMMENT '经营者姓名',
  `subject_card_type` bigint NOT NULL COMMENT '证件类型 （取数据字典）',
  `subject_id_card` varchar(18) NOT NULL COMMENT '经营者身份证号码',
  `subject_card_start_time` varchar(20) NOT NULL COMMENT '证件有效开始时间',
  `subject_card_end_time` varchar(20) NOT NULL COMMENT '证件有效结束时间',
  `subject_card_positive` varchar(255) NOT NULL COMMENT '身份证正面照',
  `subject_card_side` varchar(255) NOT NULL COMMENT '身份证反面照',
  `administrators_phone` varchar(20) DEFAULT '' COMMENT '管理员手机号',
  `administrators_email` varchar(50) DEFAULT '' COMMENT '管理员邮箱',
  `account_type` tinyint(1) DEFAULT '1' COMMENT '账户类型 1-对公 2-对私',
  `account_open_bank` bigint DEFAULT '0' COMMENT '开户银行（取数据字典）',
  `account_bank_region` varchar(50) DEFAULT '' COMMENT '开户银行地区   省-市',
  `account_bank_card` varchar(50) DEFAULT '' COMMENT '银行账户',
  `shop_abbreviation` varchar(30) DEFAULT '' COMMENT '商户简称',
  `service_phone` varchar(16) DEFAULT '' COMMENT '客服电话',
  `service_providing` bigint DEFAULT '0' COMMENT '提供服务（取数据字典）',
  `shop_index_image` varchar(255) DEFAULT '' COMMENT '店铺首页截图',
  `shop_backstage_image` varchar(255) DEFAULT '' COMMENT '店铺管理后台截图',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺个体工商户认证信息表';

-- ----------------------------
-- Table structure for cere_shop_label
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_label`;
CREATE TABLE `cere_shop_label` (
  `label_id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `label_name` varchar(10) NOT NULL COMMENT '标签名称',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='店铺标签信息表';

-- ----------------------------
-- Table structure for cere_shop_operate
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_operate`;
CREATE TABLE `cere_shop_operate` (
  `shop_operate_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺运营计划id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `operate_name` varchar(20) NOT NULL COMMENT '计划名称',
  `shop_crowd_id` bigint NOT NULL COMMENT '店铺人群id',
  `plan_mode` tinyint(1) NOT NULL COMMENT '计划方式  1-自动长期计划 2-手动定时计划',
  `state` tinyint(1) DEFAULT NULL COMMENT '状态 0-未开始 1-进行中 2-已结束',
  `plan_start` varchar(20) DEFAULT NULL COMMENT '长期计划开始时间',
  `plan_end` varchar(20) DEFAULT NULL COMMENT '长期计划结束时间',
  `manual_time` varchar(20) DEFAULT NULL COMMENT '手动定时执行时间，如果为空说明是立即执行',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_operate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='店铺运营计划信息表';

-- ----------------------------
-- Table structure for cere_shop_operate_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_operate_detail`;
CREATE TABLE `cere_shop_operate_detail` (
  `shop_operate_id` bigint NOT NULL,
  `shop_coupon_id` bigint NOT NULL COMMENT '店铺优惠券id',
  `number` int NOT NULL COMMENT '赠券数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺运营计划优惠券明细信息表';

-- ----------------------------
-- Table structure for cere_shop_order
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_order`;
CREATE TABLE `cere_shop_order` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `parent_id` bigint NOT NULL COMMENT '关联父订单id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `order_formid` varchar(20) NOT NULL COMMENT '订单ID',
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `coupon_id` bigint(11) DEFAULT NULL COMMENT '平台优惠券id',
  `id` bigint DEFAULT NULL COMMENT '客户关联商家优惠券数据主键id',
  `shop_seckill_id` bigint DEFAULT NULL COMMENT '店铺秒杀活动id',
  `shop_group_work_id` bigint DEFAULT NULL COMMENT '店铺拼团活动id',
  `shop_discount_id` bigint DEFAULT NULL COMMENT '店铺限时折扣活动id',
  `shop_operate_id` bigint DEFAULT NULL COMMENT '运营计划id',
  `order_price` decimal(15,2) NOT NULL COMMENT '订单总价',
  `logistics_price` decimal(15,2) DEFAULT NULL COMMENT '物流费用',
  `discount_price` decimal(15,2) DEFAULT NULL COMMENT '优惠金额',
  `price` decimal(15,2) NOT NULL COMMENT '支付金额',
  `state` tinyint(1) NOT NULL COMMENT '订单状态 1-待付款 2-待发货 3-待收货 4-已完成 5-已取消 6-待成团',
  `payment_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否支付 1-是 0-否',
  `payment_mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付方式 1-微信支付',
  `payment_time` varchar(20) DEFAULT NULL COMMENT '支付时间',
  `customer_name` varchar(20) DEFAULT NULL COMMENT '下单人姓名',
  `customer_phone` varchar(20) DEFAULT NULL COMMENT '下单人手机号',
  `receive_name` varchar(20) DEFAULT NULL COMMENT '收货人姓名',
  `receive_phone` varchar(20) DEFAULT NULL COMMENT '收货人手机号',
  `receive_adress` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `receive_time` varchar(20) DEFAULT NULL COMMENT '成交时间',
  `postal_code` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '订单备注',
  `after_state` tinyint(1) DEFAULT NULL COMMENT '无用字段',
  `logistics_id` bigint DEFAULT NULL COMMENT '物流方案id',
  `distributor_id` bigint DEFAULT NULL COMMENT '关联分销员id',
  `direct_distributor_money` decimal(15,2) DEFAULT NULL COMMENT '直接分销佣金',
  `indirect_distributor_money` decimal(15,2) DEFAULT NULL COMMENT '间接分销佣金',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900 DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- ----------------------------
-- Table structure for cere_shop_other_organizations
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_other_organizations`;
CREATE TABLE `cere_shop_other_organizations` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `other_name` varchar(110) NOT NULL COMMENT '组织名称',
  `other_code` varchar(18) NOT NULL COMMENT '组织机构代码',
  `other_region` varchar(50) NOT NULL COMMENT '地址  省-市-区',
  `other_adress` varchar(256) DEFAULT NULL COMMENT '详细地址',
  `other_start_time` varchar(20) NOT NULL COMMENT '营业期限开始时间',
  `other_end_time` varchar(20) NOT NULL COMMENT '营业期限结束时间',
  `other_license` varchar(1000) NOT NULL COMMENT '机构证件或其他证明材料图片地址（多个以逗号隔开）',
  `other_operator` varchar(30) NOT NULL COMMENT '负责人姓名',
  `other_card_type` bigint NOT NULL COMMENT '证件类型 （取数据字典）',
  `other_id_card` varchar(18) NOT NULL COMMENT '负责人身份证号码',
  `other_card_start_time` varchar(20) NOT NULL COMMENT '证件有效开始时间',
  `other_card_end_time` varchar(20) NOT NULL COMMENT '证件有效结束时间',
  `other_card_positive` varchar(255) NOT NULL COMMENT '身份证正面照',
  `other_card_side` varchar(255) NOT NULL COMMENT '身份证反面照',
  `administrators_phone` varchar(20) DEFAULT '' COMMENT '管理员手机号',
  `administrators_email` varchar(50) DEFAULT '' COMMENT '管理员邮箱',
  `account_open_bank` bigint DEFAULT '0' COMMENT '开户银行（取数据字典）',
  `account_bank_region` varchar(50) DEFAULT '' COMMENT '开户银行地区   省-市',
  `account_bank_card` varchar(50) DEFAULT '' COMMENT '银行账户',
  `shop_abbreviation` varchar(30) DEFAULT '' COMMENT '商户简称',
  `service_phone` varchar(16) DEFAULT '' COMMENT '客服电话',
  `service_providing` bigint DEFAULT '0' COMMENT '提供服务（取数据字典）',
  `shop_index_image` varchar(255) DEFAULT '' COMMENT '店铺首页截图',
  `shop_backstage_image` varchar(255) DEFAULT '' COMMENT '店铺管理后台截图',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺其他组织认证信息表';

-- ----------------------------
-- Table structure for cere_shop_personal
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_personal`;
CREATE TABLE `cere_shop_personal` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `personal_name` varchar(30) NOT NULL COMMENT '个人姓名',
  `personal_card_type` bigint NOT NULL COMMENT '证件类型 （取数据字典）',
  `personal_id_card` varchar(18) NOT NULL COMMENT '法人身份证号码',
  `personal_card_start_time` varchar(20) NOT NULL COMMENT '证件有效开始时间',
  `personal_card_end_time` varchar(20) NOT NULL COMMENT '证件有效结束时间',
  `personal_card_positive` varchar(255) NOT NULL COMMENT '法人身份证正面照',
  `personal_card_side` varchar(255) NOT NULL COMMENT '法人身份证反面照',
  `personal_card_hand` varchar(255) NOT NULL COMMENT '手持证件照',
  `administrators_phone` varchar(20) DEFAULT '' COMMENT '管理员手机号',
  `administrators_email` varchar(50) DEFAULT '' COMMENT '管理员邮箱',
  `account_open_bank` bigint DEFAULT '0' COMMENT '开户银行（取数据字典）',
  `account_bank_region` varchar(50) DEFAULT '' COMMENT '开户银行地区   省-市',
  `account_bank_card` varchar(50) DEFAULT '' COMMENT '银行账户',
  `shop_abbreviation` varchar(30) DEFAULT '' COMMENT '商户简称',
  `service_phone` varchar(16) NOT NULL DEFAULT '' COMMENT '客服电话',
  `service_providing` bigint DEFAULT '0' COMMENT '提供服务（取数据字典）',
  `shop_index_image` varchar(255) DEFAULT '' COMMENT '店铺首页截图',
  `shop_backstage_image` varchar(255) DEFAULT '' COMMENT '店铺管理后台截图',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺个人认证信息表';

-- ----------------------------
-- Table structure for cere_shop_product
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_product`;
CREATE TABLE `cere_shop_product` (
  `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `product_brief` varchar(50) DEFAULT NULL COMMENT '卖点简介',
  `product_text` longtext COMMENT '商品描述（富文本）',
  `shop_group_id` bigint DEFAULT NULL COMMENT '关联商品分组id',
  `classify_id` bigint NOT NULL COMMENT '关联分类id',
  `supplier_id` bigint DEFAULT NULL COMMENT '关联供应商id',
  `supplier_name` varchar(20) DEFAULT NULL COMMENT '供应商名称',
  `if_logistics` tinyint(1) DEFAULT '1' COMMENT '是否需要物流 1-是 0-否',
  `shelve_state` tinyint(1) DEFAULT '1' COMMENT '是否上架 1-上架 0-不上架',
  `if_oversold` tinyint(1) DEFAULT '1' COMMENT '是否允许超卖 1-是 0-否',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `index` (`product_id`,`shop_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8 COMMENT='店铺商品信息表';

-- ----------------------------
-- Table structure for cere_shop_recruit
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_recruit`;
CREATE TABLE `cere_shop_recruit` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `purchase_everything` tinyint(1) DEFAULT '0' COMMENT '是否勾选购买任意商品  1-是 0-否',
  `order_frequency` tinyint(1) DEFAULT '0' COMMENT '是否勾选至少下单满多少  1-是 0-否',
  `consumption_money` tinyint(1) DEFAULT '0' COMMENT '是否勾选消费金额满多少 1-是 0-否',
  `frequency` int DEFAULT NULL COMMENT '下单次数',
  `money` decimal(15,2) DEFAULT NULL COMMENT '消费金额',
  `if_examine` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否需要审核 1-是 0-否',
  `url` varchar(255) DEFAULT NULL COMMENT '招募页链接'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺招募信息表';

-- ----------------------------
-- Table structure for cere_shop_relationship
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_relationship`;
CREATE TABLE `cere_shop_relationship` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `if_invitation` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否允许邀请下级  1-是 0-否',
  `bind_validity` tinyint(1) NOT NULL DEFAULT '1' COMMENT '关系绑定有效期  1-永久有效 2-几天有效',
  `validity_day` int DEFAULT NULL COMMENT '关系绑定有效天数',
  `if_robbing` tinyint(1) NOT NULL DEFAULT '2' COMMENT '抢客条件 1-随时可抢客 2-不允许抢客 3-保护期几天内不允许抢',
  `robbing_day` int DEFAULT NULL COMMENT '保护期天数',
  `if_distribution_relationship` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否允许分销员之间建立客户关系 1-是 0-否'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺关系设置表';

-- ----------------------------
-- Table structure for cere_shop_return
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_return`;
CREATE TABLE `cere_shop_return` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `return_adress` varchar(255) DEFAULT NULL COMMENT '退货地址',
  `return_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `return_phone` varchar(50) DEFAULT NULL COMMENT '联系电话'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺退货地址信息表';

-- ----------------------------
-- Table structure for cere_shop_seckill
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_seckill`;
CREATE TABLE `cere_shop_seckill` (
  `shop_seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺秒杀活动id',
  `shop_id` bigint NOT NULL COMMENT '店铺id',
  `seckill_name` varchar(20) NOT NULL COMMENT '活动名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `effective_start` varchar(20) DEFAULT NULL COMMENT '开始时间',
  `effective_end` varchar(20) DEFAULT NULL COMMENT '结束时间',
  `if_limit` tinyint(1) NOT NULL COMMENT '商品限购 1-不限购 2-限购',
  `limit_number` int DEFAULT NULL COMMENT '限购几件/人',
  `if_number` tinyint(1) NOT NULL COMMENT '是否限量 1-是 0-否',
  `number` int DEFAULT NULL COMMENT '限制数量',
  `if_enable` tinyint(1) NOT NULL COMMENT '活动预热   1-停用  2-启用',
  `enable_time` int DEFAULT NULL COMMENT '预热几小时前',
  `if_add` tinyint(1) NOT NULL COMMENT '优惠券是否叠加 1-是 0-否',
  `state` tinyint(1) DEFAULT NULL COMMENT '活动状态 0-未开始 1-进行中 2-已结束',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`shop_seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='店铺秒杀活动信息表';

-- ----------------------------
-- Table structure for cere_shop_seckill_detail
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_seckill_detail`;
CREATE TABLE `cere_shop_seckill_detail` (
  `shop_seckill_id` bigint NOT NULL COMMENT '店铺秒杀活动id',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `sku_id` bigint NOT NULL COMMENT '规格id',
  `down_price` decimal(15,2) NOT NULL COMMENT '直降多少元',
  `seckill_price` decimal(15,2) NOT NULL COMMENT '秒杀价格',
  `number` int DEFAULT NULL COMMENT '限量数量(剩余)',
  `total` int DEFAULT NULL COMMENT '限制总数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺秒杀活动商品明细信息表';

-- ----------------------------
-- Table structure for cere_shop_user_label
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_user_label`;
CREATE TABLE `cere_shop_user_label` (
  `label_id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺标签id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `label_name` varchar(10) NOT NULL COMMENT '标签名称',
  `remark` varchar(255) NOT NULL COMMENT '备注信息',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='店铺用户标签信息表';

-- ----------------------------
-- Table structure for cere_shop_visit
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_visit`;
CREATE TABLE `cere_shop_visit` (
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `buyer_user_id` bigint NOT NULL COMMENT '关联客户id',
  `visit_time` varchar(20) NOT NULL COMMENT '访问时间',
  `terminal` tinyint(1) DEFAULT NULL COMMENT '访问终端  1-APP 2-小程序 3-PC端',
  `system` tinyint(1) DEFAULT NULL COMMENT '操作系统1-Android 2-IOS 3-浏览器',
  `city` varchar(50) DEFAULT NULL COMMENT '所在地区'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺访问信息表';

-- ----------------------------
-- Table structure for cere_shop_withdrawal
-- ----------------------------
DROP TABLE IF EXISTS `cere_shop_withdrawal`;
CREATE TABLE `cere_shop_withdrawal` (
  `withdrawal_id` bigint NOT NULL AUTO_INCREMENT COMMENT '提现申请id',
  `shop_id` bigint NOT NULL COMMENT '关联店铺id',
  `shop_name` varchar(20) NOT NULL COMMENT '店铺名称',
  `shop_code` varchar(20) NOT NULL COMMENT '店铺编码',
  `bank_name` varchar(50) NOT NULL COMMENT '银行名称',
  `bank_card` varchar(50) NOT NULL COMMENT '银行卡号',
  `collection_name` varchar(20) NOT NULL COMMENT '收款人姓名',
  `withdrawal_money` decimal(15,2) NOT NULL COMMENT '提现金额',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '处理状态  0-待处理 1-已处理',
  `apply_time` varchar(20) DEFAULT NULL COMMENT '申请时间',
  `handle_time` varchar(20) DEFAULT NULL COMMENT '处理时间',
  `handling_fee` decimal(15,0) DEFAULT '0' COMMENT '提现手续费',
  PRIMARY KEY (`withdrawal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='商家提现申请信息表';

-- ----------------------------
-- Table structure for cere_sign_product
-- ----------------------------
DROP TABLE IF EXISTS `cere_sign_product`;
CREATE TABLE `cere_sign_product` (
  `sign_id` bigint NOT NULL COMMENT '关联报名id',
  `product_id` bigint NOT NULL COMMENT '关联商品id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报名商品明细信息表';

-- ----------------------------
-- Table structure for cere_sku_name
-- ----------------------------
DROP TABLE IF EXISTS `cere_sku_name`;
CREATE TABLE `cere_sku_name` (
  `sku_id` bigint NOT NULL COMMENT '规格id',
  `need` tinyint(1) DEFAULT NULL COMMENT '是否需要配图 1-是 0-否',
  `sku_name` varchar(10) DEFAULT NULL COMMENT '规格名',
  `sku_value` varchar(10) NOT NULL COMMENT '规格值',
  `image` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `name_code` varchar(20) DEFAULT NULL COMMENT '规格名级别',
  `value_code` varchar(20) DEFAULT NULL COMMENT '规格值级别',
   KEY `index` (`sku_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格属性信息表';

-- 修改评论表编码为utf8mb4
alter table cere_shop_comment character set utf8mb4 COLLATE utf8mb4_general_ci;
-- 修改评论表评论字段编码为utf8mb4
ALTER TABLE cere_shop_comment MODIFY `comment` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
-- 修改评论表评追加论字段编码为utf8mb4
ALTER TABLE cere_shop_comment MODIFY `add_comment` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 修改商品回答表编码为utf8mb4
alter table cere_product_answer character set utf8mb4 COLLATE utf8mb4_general_ci;
-- 修改商品回答表回答内容字段编码为utf8mb4
ALTER TABLE cere_product_answer MODIFY `answer` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 修改商品提问表编码为utf8mb4
alter table cere_product_problem character set utf8mb4 COLLATE utf8mb4_general_ci;
-- 修改商品提问表提问内容字段编码为utf8mb4
ALTER TABLE cere_product_problem MODIFY `problem` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 修改商家入驻信息表身份证相关图片地址字段长度
alter table cere_shop_personal modify column personal_card_positive varchar(1000) COMMENT '身份证正面照';
alter table cere_shop_personal modify column personal_card_side varchar(1000) COMMENT '身份证反面照';
alter table cere_shop_personal modify column personal_card_hand varchar(1000) COMMENT '手持身份证照';
alter table cere_shop_individual_businesses modify column subject_card_positive varchar(1000) COMMENT '身份证正面照';
alter table cere_shop_individual_businesses modify column subject_card_side varchar(1000) COMMENT '身份证反面照';
alter table cere_shop_other_organizations modify column other_card_positive varchar(1000) COMMENT '身份证正面照';
alter table cere_shop_other_organizations modify column other_card_side varchar(1000) COMMENT '身份证反面照';
alter table cere_shop_enterprise modify column enterprise_card_positive varchar(1000) COMMENT '身份证正面照';
alter table cere_shop_enterprise modify column enterprise_card_side varchar(1000) COMMENT '身份证反面照';

-- 修改角色表和菜单表project字段类型
alter table cere_platform_role modify column project BIGINT(11);
alter table cere_platform_permission modify column project BIGINT(11);

-- 修改规格属性表规格名字段长度为20
alter table cere_sku_name modify column sku_name varchar(20) COMMENT '规格名';

-- 修改店铺报名平台活动表交易流水号字段长度为50
alter table cere_activity_sign modify column sign_code varchar(50) COMMENT '交易流水号';

-- 修改客户历史搜索信息表搜索内容字段长度为40
alter table cere_buyer_search modify column search varchar(40) COMMENT '搜索内容';

-- 新增请求记录表
-- ----------------------------
-- Table structure for cere_platform_web_log
-- ----------------------------
DROP TABLE IF EXISTS `cere_platform_web_log`;
CREATE TABLE `cere_platform_web_log` (
  `web_log_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '请求记录id',
  `name` varchar(50) DEFAULT NULL COMMENT '请求用户名',
  `url` varchar(50) DEFAULT NULL COMMENT '请求接口地址',
  `params` varchar(1000) DEFAULT NULL COMMENT '请求接口参数',
  `type` tinyint(1) DEFAULT NULL COMMENT '请求服务类型    1-平台端 2-商家端 3-C端',
  `ip` varchar(120) DEFAULT NULL COMMENT 'IP地址',
  `ip_source` varchar(50) DEFAULT NULL COMMENT 'IP来源',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `browser_name` varchar(20) DEFAULT NULL COMMENT '浏览器',
  `time` int(10) DEFAULT NULL COMMENT '耗时',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`web_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='接口请求记录表';

-- 修改请求记录表请求参数字段类型为mediumtext
alter table cere_platform_web_log modify column params MEDIUMTEXT COMMENT '请求接口参数';

-- 修改客户分群信息表客户id字段类型为mediumtext
alter table cere_shop_crowd modify column ids MEDIUMTEXT COMMENT '客户id，多个以逗号隔开';

-- 用户表新增支付宝用户id字段
ALTER TABLE `cere_buyer_user`
ADD COLUMN `ali_user_id` varchar(50) CHARACTER SET utf8mb4 NULL DEFAULT NULL COMMENT '支付宝小程序用户id' AFTER `wechat_number`;

-- 商品表新增是否支持花呗分期的字段
ALTER TABLE `cere_shop_product`
ADD COLUMN `if_huabei` tinyint(1) NULL DEFAULT 0 COMMENT '是否支持花呗分期' AFTER `if_oversold`;

-- 新增等级编号字段
ALTER TABLE `cere_shop_distribution_level`
ADD COLUMN `level_num` int(11) NOT NULL DEFAULT 1 COMMENT '等级编号，编号越大等级越高' AFTER `level_name`;

ALTER TABLE `cere_activity_sign`
MODIFY COLUMN `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '审核状态 0-待审核 1-报名成功 2-报名失败 3-报名进行中(未支付)' AFTER `qr_image`;

CREATE TABLE `cere_business_buyer_user` (
    `shop_id` bigint(20) NOT NULL COMMENT '店铺id',
    `buyer_user_id` bigint(20) NOT NULL COMMENT '用户id',
    `create_time` varchar(20) NOT NULL COMMENT '创建时间(首次成为客户时间)',
    `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间(上次消费时间)',
    PRIMARY KEY (`shop_id`,`buyer_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家客户关联表';

ALTER TABLE `cere_platform_business` ADD COLUMN `avatar` varchar(256) NULL COMMENT '头像' AFTER `sex`;




