ALTER TABLE `cere_platform_canvas`
ADD COLUMN `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '类型 1-系统画布 2-自定义页面 3-商家店铺装修' AFTER `json`,
ADD COLUMN `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称' AFTER `type`,
ADD COLUMN `shop_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '店铺id，当type=3的时候，值为具体的店铺id，其它情况为0' AFTER `name`,
ADD COLUMN `custom_id` bigint(20) NULL DEFAULT 0 COMMENT '自定义页面id，当type=2时，值为自定义页面对应的id，其它情况为0' AFTER `shop_id`,
ADD COLUMN `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
ADD COLUMN `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间';

-- 新增自定义页面表
CREATE TABLE `cere_platform_canvas_custom` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
   `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
   `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自定义页面表';
