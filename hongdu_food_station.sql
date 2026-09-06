-- ============================================================
-- 红都食驿（Hongdu Food Station）建库脚本
-- 基于苍穹外卖(sky_take_out)业务架构重构，数据库：hongdu_food_station
-- 说明：
--   1. 表结构以 com.hongdu 实体类与 mapper/*.xml 为准
--   2. 业务闭环：员工管理 -> 分类/菜品/套餐 -> 店铺营业 ->
--      用户登录 -> 浏览 -> 购物车 -> 下单 -> 支付 -> 订单管理 -> 数据统计
--   3. 图片地址为占位符，部署时请替换为实际 OSS/CDN 地址
--   4. 管理员账号：admin / 123456（密码以 MD5 存储）
-- ============================================================

CREATE DATABASE IF NOT EXISTS `hongdu_food_station` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `hongdu_food_station`;

-- ------------------------------------------------------------
-- 1. 分类表（菜品分类 / 套餐分类）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int DEFAULT NULL COMMENT '类型 1 菜品分类 2 套餐分类',
  `name` varchar(32) NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` int DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿菜品及套餐分类';

INSERT INTO `category` VALUES (1,1,'红都招牌菜',1,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (2,1,'忆苦思甜菜',2,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (3,1,'客家风味',3,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (4,1,'主食简餐',4,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (5,1,'汤羹',5,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (6,1,'饮品',6,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (7,2,'人气套餐',10,1,NOW(),NOW(),1,1);
INSERT INTO `category` VALUES (8,2,'商务套餐',11,1,NOW(),NOW(),1,1);

-- ------------------------------------------------------------
-- 2. 菜品表（product）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '菜品名称',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  `status` int DEFAULT '1' COMMENT '0 停售 1 起售',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_product_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿菜品';

-- 红都招牌菜
INSERT INTO `product` VALUES (1,'红军焖鸭',1,58.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/hongjun-menya.png','瑞金传统名菜，选用本地麻鸭红焖入味，肉质酥烂',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (2,'瑞金牛肉汤',1,32.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/niuroutang.png','瑞金街头名小吃，牛肉鲜嫩，汤味醇厚',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (3,'客家酿豆腐',1,28.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/niangdoufu.png','客家经典名菜，豆腐外焦里嫩，肉馅鲜香',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (4,'苏区红烧肉',1,48.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/hongshaorou.png','肥而不腻，入口即化，重温苏区记忆',1,NOW(),NOW(),1,1);
-- 忆苦思甜菜
INSERT INTO `product` VALUES (5,'红米饭',2,3.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/hongmifan.png','井冈红米，忆苦思甜，粒粒饱满',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (6,'南瓜汤',2,8.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/nanguatang.png','红军时期的主食记忆，清甜可口',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (7,'红军菜',2,18.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/hongjuncaicai.png','时令野菜，清淡爽口，健康绿色',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (8,'蒸红薯',2,5.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/hongshu.png','粗粮细作，香甜软糯',1,NOW(),NOW(),1,1);
-- 客家风味
INSERT INTO `product` VALUES (9,'芋子包',3,16.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/yuzibao.png','客家名小吃，外皮软糯，馅料鲜美',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (10,'艾米果',3,12.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/aimiguo.png','清明时节粿品，艾香四溢',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (11,'黄元米果',3,15.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/huangyuanmiguo.png','瑞金年节必备，软糯弹牙',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (12,'瑞金烫皮',3,10.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/tangpi.png','瑞金特色米制品，薄如纸，爽滑筋道',1,NOW(),NOW(),1,1);
-- 主食简餐
INSERT INTO `product` VALUES (13,'米饭',4,2.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/mifan.png','精选本地香米',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (14,'馒头',4,1.50,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/mantou.png','老面发酵，麦香十足',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (15,'瑞金拌粉',4,8.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/banfen.png','瑞金米粉，爽滑鲜辣，早餐首选',1,NOW(),NOW(),1,1);
-- 汤羹
INSERT INTO `product` VALUES (16,'瓦罐汤',5,12.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/waguantang.png','文火慢煨，原汁原味',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (17,'紫菜蛋花汤',5,6.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/danhuatang.png','家常味道，鲜美爽口',1,NOW(),NOW(),1,1);
INSERT INTO `product` VALUES (18,'鱼头豆腐汤',5,22.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/yutoudoufutang.png','汤白味鲜，营养滋补',1,NOW(),NOW(),1,1);
-- 饮品
INSERT INTO `product` VALUES (19,'酸梅汤',6,6.00,'https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/suanmeitang.png','生津止渴，消暑解腻',1,NOW(),NOW(),1,1);

-- ------------------------------------------------------------
-- 3. 菜品口味表（product_spec）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `product_spec`;
CREATE TABLE `product_spec` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` bigint NOT NULL COMMENT '菜品id',
  `name` varchar(32) DEFAULT NULL COMMENT '口味名称',
  `value` varchar(255) DEFAULT NULL COMMENT '口味数据list',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品口味关系表';

INSERT INTO `product_spec` VALUES (1,2,'辣度','["不辣","微辣","中辣","重辣"]');
INSERT INTO `product_spec` VALUES (2,2,'忌口','["不要葱","不要香菜","不要辣"]');
INSERT INTO `product_spec` VALUES (3,4,'口味','["原味","微辣"]');
INSERT INTO `product_spec` VALUES (4,7,'忌口','["不要蒜","不要辣椒"]');
INSERT INTO `product_spec` VALUES (5,15,'辣度','["不辣","微辣","中辣"]');
INSERT INTO `product_spec` VALUES (6,15,'忌口','["不要葱","不要香菜"]');
INSERT INTO `product_spec` VALUES (7,19,'温度','["常温","加冰","去冰"]');
INSERT INTO `product_spec` VALUES (8,19,'甜度','["无糖","少糖","标准糖","多糖"]');

-- ------------------------------------------------------------
-- 4. 套餐表（combo）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `combo`;
CREATE TABLE `combo` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '套餐分类id',
  `name` varchar(32) NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int DEFAULT '1' COMMENT '售卖状态 0:停售 1:起售',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_combo_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿套餐';

INSERT INTO `combo` VALUES (1,7,'忆苦思甜套餐',26.00,1,'红米饭+南瓜汤+红军菜+芋子包，重温峥嵘岁月','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/combo/yikushitian.png',NOW(),NOW(),1,1);
INSERT INTO `combo` VALUES (2,7,'红都招牌套餐',98.00,1,'红军焖鸭+瑞金牛肉汤+客家酿豆腐+苏区红烧肉+米饭','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/combo/hongduzhaopai.png',NOW(),NOW(),1,1);
INSERT INTO `combo` VALUES (3,8,'客家风味套餐',56.00,1,'芋子包+艾米果+黄元米果+瓦罐汤+米饭','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/combo/kejiafengwei.png',NOW(),NOW(),1,1);
INSERT INTO `combo` VALUES (4,8,'单人简餐',15.00,1,'米饭+红军菜+紫菜蛋花汤，一人食好选择','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/combo/danrenjiancan.png',NOW(),NOW(),1,1);

-- ------------------------------------------------------------
-- 5. 套餐菜品关系表（combo_item）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `combo_item`;
CREATE TABLE `combo_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `combo_id` bigint DEFAULT NULL COMMENT '套餐id',
  `product_id` bigint DEFAULT NULL COMMENT '菜品id',
  `name` varchar(32) DEFAULT NULL COMMENT '菜品名称（冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品单价（冗余字段）',
  `copies` int DEFAULT NULL COMMENT '菜品份数',
  PRIMARY KEY (`id`),
  KEY `idx_combo_id` (`combo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='套餐菜品关系';

INSERT INTO `combo_item` VALUES (1,1,5,'红米饭',3.00,2);
INSERT INTO `combo_item` VALUES (2,1,6,'南瓜汤',8.00,2);
INSERT INTO `combo_item` VALUES (3,1,7,'红军菜',18.00,1);
INSERT INTO `combo_item` VALUES (4,1,9,'芋子包',16.00,2);
INSERT INTO `combo_item` VALUES (5,2,1,'红军焖鸭',58.00,1);
INSERT INTO `combo_item` VALUES (6,2,2,'瑞金牛肉汤',32.00,1);
INSERT INTO `combo_item` VALUES (7,2,3,'客家酿豆腐',28.00,1);
INSERT INTO `combo_item` VALUES (8,2,4,'苏区红烧肉',48.00,1);
INSERT INTO `combo_item` VALUES (9,2,13,'米饭',2.00,2);
INSERT INTO `combo_item` VALUES (10,3,9,'芋子包',16.00,2);
INSERT INTO `combo_item` VALUES (11,3,10,'艾米果',12.00,2);
INSERT INTO `combo_item` VALUES (12,3,11,'黄元米果',15.00,2);

-- ------------------------------------------------------------
-- 6. 员工表（staff）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码(MD5)',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `sex` varchar(2) NOT NULL COMMENT '性别',
  `id_number` varchar(18) NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿员工信息';

-- 默认管理员：admin / 123456（e10adc3949ba59abbe56e057f20f883e 为 123456 的 MD5）
INSERT INTO `staff` VALUES (1,'管理员','admin','e10adc3949ba59abbe56e057f20f883e','13812312312','1','110101199001010047',1,NOW(),NOW(),1,1);

-- ------------------------------------------------------------
-- 7. 用户表（user）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) DEFAULT NULL COMMENT '微信用户唯一标识',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿用户信息';

INSERT INTO `user` VALUES (1,'test_openid_001','张红军','13800138001','1',NULL,NULL,NOW() - INTERVAL 3 DAY);
INSERT INTO `user` VALUES (2,'test_openid_002','李瑞金','13800138002','0',NULL,NULL,NOW() - INTERVAL 1 DAY);

-- ------------------------------------------------------------
-- 8. 自提点表（pickup_point，原地址簿）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `pickup_point`;
CREATE TABLE `pickup_point` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `province_code` varchar(12) DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿自提点/收货地址';

INSERT INTO `pickup_point` VALUES (1,1,'张红军','1','13800138001','360000','江西省','360700','赣州市','360781','瑞金市','红都大道188号红都食驿', '公司',1);
INSERT INTO `pickup_point` VALUES (2,1,'张红军','1','13800138001','360000','江西省','360700','赣州市','360781','瑞金市','象湖镇绵江路102号', '家',0);

-- ------------------------------------------------------------
-- 9. 购物车表（shopping_cart）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '商品名称',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `product_id` bigint DEFAULT NULL COMMENT '菜品id',
  `combo_id` bigint DEFAULT NULL COMMENT '套餐id',
  `product_spec` varchar(50) DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿购物车';

-- ------------------------------------------------------------
-- 10. 订单表（orders）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `pickup_point_id` bigint NOT NULL COMMENT '自提点/地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime DEFAULT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint NOT NULL DEFAULT '0' COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `consignee` varchar(32) DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime DEFAULT NULL COMMENT '订单取消时间',
  `estimated_delivery_time` datetime DEFAULT NULL COMMENT '预计送达时间',
  `delivery_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配送状态 1立即送出 0选择具体时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '送达时间',
  `pack_amount` int DEFAULT NULL COMMENT '打包费',
  `tableware_number` int DEFAULT NULL COMMENT '餐具数量',
  `tableware_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '餐具数量状态 1按餐量提供 0选择具体数量',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿订单表';

-- 示例订单（便于工作台/报表演示，时间相对当前动态生成）
INSERT INTO `orders` VALUES (1,CONCAT('HD',DATE_FORMAT(NOW() - INTERVAL 1 DAY,'%Y%m%d'),'0001'),5,1,1,NOW() - INTERVAL 1 DAY,NOW() - INTERVAL 1 DAY + INTERVAL 10 MINUTE,1,1,98.00,'不要辣','13800138001','江西省瑞金市红都大道188号红都食驿','张红军','张红军',NULL,NULL,NULL,NOW() - INTERVAL 1 DAY + INTERVAL 30 MINUTE,1,NOW() - INTERVAL 1 DAY + INTERVAL 45 MINUTE,0,2,1);
INSERT INTO `orders` VALUES (2,CONCAT('HD',DATE_FORMAT(NOW(),'%Y%m%d'),'0001'),6,2,2,NOW() - INTERVAL 5 HOUR,NOW() - INTERVAL 5 HOUR + INTERVAL 5 MINUTE,1,1,26.00,'','13800138002','江西省瑞金市象湖镇绵江路102号','李瑞金','李瑞金','用户取消','',NOW() - INTERVAL 4 HOUR,NULL,1,NULL,0,1,1);

-- ------------------------------------------------------------
-- 11. 订单明细表（order_detail）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '名字',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `product_id` bigint DEFAULT NULL COMMENT '菜品id',
  `combo_id` bigint DEFAULT NULL COMMENT '套餐id',
  `product_spec` varchar(50) DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='红都食驿订单明细表';

INSERT INTO `order_detail` VALUES (1,'红都招牌套餐','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/combo/hongduzhaopai.png',1,NULL,2,'',1,98.00);
INSERT INTO `order_detail` VALUES (2,'米饭','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/mifan.png',1,13,NULL,'',1,2.00);
INSERT INTO `order_detail` VALUES (3,'忆苦思甜套餐','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/combo/yikushitian.png',2,NULL,1,'',1,26.00);
INSERT INTO `order_detail` VALUES (4,'南瓜汤','https://hongdu-food-station.oss-cn-beijing.aliyuncs.com/product/nanguatang.png',2,6,NULL,'',1,8.00);
