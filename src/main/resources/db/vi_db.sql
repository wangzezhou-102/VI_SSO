/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.3.21
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : 192.168.3.21:3306
 Source Schema         : vi

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 17/06/2019 16:00:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_code`;
CREATE TABLE `sys_code`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应sys_code_type类型表',
  `code` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code编码',
  `code_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_code_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_code_type`;
CREATE TABLE `sys_code_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型',
  `code_type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_code_type
-- ----------------------------
INSERT INTO `sys_code_type` VALUES (1, '1001', '布控库');

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `type` tinyint(1) NOT NULL COMMENT '0-失败 1-成功 2-异常',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能名称',
  `remote_addr` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求源IP地址',
  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求uri',
  `method` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数{}，即入参',
  `result` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '返回结果{}',
  `exception` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常信息',
  `user_id` int(10) NOT NULL COMMENT '用户账号ID',
  `operate_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  `timeout` int(11) NULL DEFAULT NULL COMMENT '毫秒级响应时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_operation_log_user_id`(`user_id`) USING BTREE COMMENT '用户索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_org_device
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_device`;
CREATE TABLE `sys_org_device`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `org_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织ID（节点ID）',
  `device_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备与组织关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_org_device
-- ----------------------------
INSERT INTO `sys_org_device` VALUES (1, '001001', '1');
INSERT INTO `sys_org_device` VALUES (2, '001001', '3');
INSERT INTO `sys_org_device` VALUES (3, '001002', '2');
INSERT INTO `sys_org_device` VALUES (4, '001001', '4');
INSERT INTO `sys_org_device` VALUES (5, '001001001', '5');
INSERT INTO `sys_org_device` VALUES (6, '001001001', '6');

-- ----------------------------
-- Table structure for sys_org_device_copy
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_device_copy`;
CREATE TABLE `sys_org_device_copy`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `org_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织ID（节点ID）',
  `device_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备与组织关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_org_device_copy
-- ----------------------------
INSERT INTO `sys_org_device_copy` VALUES (9, '001002', '2');

-- ----------------------------
-- Table structure for sys_org_road
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_road`;
CREATE TABLE `sys_org_road`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织编码',
  `org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织名称',
  `parent_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上级组织编码，若为根节点，则为root',
  `total_roads` smallint(5) UNSIGNED NULL DEFAULT NULL COMMENT '总路数',
  `used_roads` smallint(5) UNSIGNED NULL DEFAULT NULL COMMENT '已使用路数',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '路数使用情况，默认充足 0-不足 1-充足',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_ORG_CODE`(`org_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织分配路数情况表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_org_road
-- ----------------------------
INSERT INTO `sys_org_road` VALUES (1, '3304', '杭州市公安局', 'root', 5000, 3000, 1, '2019-06-17 15:39:04', '2019-06-17 15:40:00');
INSERT INTO `sys_org_road` VALUES (2, '330401', '杭州市上城区分局', '3304', 500, 300, 1, '2019-06-17 15:40:42', '2019-06-17 15:54:18');
INSERT INTO `sys_org_road` VALUES (3, '33040101', '杭州市上城区河坊街派出所', '330401', 250, 200, 1, '2019-06-17 15:41:55', '2019-06-17 15:54:22');
INSERT INTO `sys_org_road` VALUES (4, '33010102', '杭州市上城区紫阳派出所', '330401', 250, 100, 1, '2019-06-17 15:43:15', '2019-06-17 15:54:26');
INSERT INTO `sys_org_road` VALUES (5, '330402', '杭州市下城区分局', '3304', 500, 461, 1, '2019-06-17 15:44:17', '2019-06-17 15:54:32');
INSERT INTO `sys_org_road` VALUES (6, '33040201', '杭州市下城区石桥派出所', '330402', 250, 250, 0, '2019-06-17 15:45:13', '2019-06-17 15:54:41');
INSERT INTO `sys_org_road` VALUES (7, '33040202', '杭州市下城区东新派出所', '330402', 250, 211, 1, '2019-06-17 15:45:18', '2019-06-17 15:54:53');
INSERT INTO `sys_org_road` VALUES (8, '330403', '杭州市西湖区分局', '3304', 1000, 320, 1, '2019-06-17 15:46:43', '2019-06-17 15:46:56');
INSERT INTO `sys_org_road` VALUES (9, '33040301', '杭州市西湖区三墩派出所', '330403', 500, 300, 1, '2019-06-17 15:47:01', '2019-06-17 15:47:30');
INSERT INTO `sys_org_road` VALUES (10, '33040302', '杭州市西湖区西溪派出所', '330403', 500, 20, 1, '2019-06-17 15:47:35', '2019-06-17 15:47:54');
INSERT INTO `sys_org_road` VALUES (11, '330404', '杭州市滨江区分局', '3304', 1000, 850, 1, '2019-06-17 15:48:19', '2019-06-17 15:48:19');
INSERT INTO `sys_org_road` VALUES (12, '33040401', '杭州市滨江区西兴派出所', '330404', 500, 450, 1, '2019-06-17 15:48:55', '2019-06-17 15:48:55');
INSERT INTO `sys_org_road` VALUES (13, '33040402', '杭州市滨江区高新派出所', '330404', 500, 400, 1, '2019-06-17 15:49:02', '2019-06-17 15:49:37');
INSERT INTO `sys_org_road` VALUES (14, '330405', '杭州市萧山区分局', '3304', 1000, 621, 1, '2019-06-17 15:50:09', '2019-06-17 15:50:09');
INSERT INTO `sys_org_road` VALUES (15, '33040501', '杭州市萧山区南阳派出所', '330405', 500, 321, 1, '2019-06-17 15:50:42', '2019-06-17 15:50:42');
INSERT INTO `sys_org_road` VALUES (16, '33040502', '杭州市萧山区党山派出所', '330405', 500, 300, 1, '2019-06-17 15:50:48', '2019-06-17 15:51:16');
INSERT INTO `sys_org_road` VALUES (17, '330406', '杭州市拱墅区分局', '3304', 500, 500, 0, '2019-06-17 15:55:31', '2019-06-17 15:55:31');
INSERT INTO `sys_org_road` VALUES (18, '33040601', '杭州市拱墅区半山派出所', '330406', 250, 250, 0, '2019-06-17 15:56:12', '2019-06-17 15:56:36');
INSERT INTO `sys_org_road` VALUES (19, '33040602', '杭州市拱墅区祥符派出所', '330406', 250, 250, 0, '2019-06-17 15:56:17', '2019-06-17 15:56:39');
INSERT INTO `sys_org_road` VALUES (20, '330407', '杭州市江干区分局', '3304', 500, 125, 1, '2019-06-17 15:57:24', '2019-06-17 15:57:24');
INSERT INTO `sys_org_road` VALUES (21, '33040701', '杭州市江干区下沙派出所', '330407', 125, 125, 0, '2019-06-17 15:58:27', '2019-06-17 15:59:02');
INSERT INTO `sys_org_road` VALUES (22, '33040702', '杭州市江干区九堡派出所', '330407', 125, 0, 1, '2019-06-17 15:58:35', '2019-06-17 15:59:06');
INSERT INTO `sys_org_road` VALUES (23, '33040703', '杭州市江干区白杨派出所', '330407', 125, 0, 1, '2019-06-17 15:59:15', '2019-06-17 15:59:46');
INSERT INTO `sys_org_road` VALUES (24, '33040704', '杭州市江干区翁梅派出所', '330407', 125, 0, 1, '2019-06-17 15:59:52', '2019-06-17 16:00:20');

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `org_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织ID（节点ID）',
  `org_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织名称',
  `pid` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级组织ID，若为根节点，则为空',
  `org_type` tinyint(1) NOT NULL DEFAULT 2 COMMENT '1-基本组织 2-业务组织 默认业务组织',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES (1, '001', '杭州市公安局', 'root', 2);
INSERT INTO `sys_organization` VALUES (2, '001001', '上城区分局', '001', 2);
INSERT INTO `sys_organization` VALUES (3, '001002', '下城区分局', '001', 2);
INSERT INTO `sys_organization` VALUES (5, '001001001', '上城区翠苑派出所', '001001', 2);
INSERT INTO `sys_organization` VALUES (6, '001001002', '上城区星际派出所', '001001', 2);

-- ----------------------------
-- Table structure for vi_area
-- ----------------------------
DROP TABLE IF EXISTS `vi_area`;
CREATE TABLE `vi_area`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域名称',
  `folder_id` int(10) NOT NULL COMMENT '文件夹关联Id',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '区域表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_area
-- ----------------------------
INSERT INTO `vi_area` VALUES (1, '嫌疑人Akun活动范围', 1, '2019-06-15 10:33:21', '2019-06-15 10:34:14', NULL, NULL);
INSERT INTO `vi_area` VALUES (2, '区域一', 6, '2019-06-15 14:04:31', '2019-06-15 14:04:31', NULL, NULL);

-- ----------------------------
-- Table structure for vi_area_device
-- ----------------------------
DROP TABLE IF EXISTS `vi_area_device`;
CREATE TABLE `vi_area_device`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_id` int(10) NOT NULL COMMENT '区域ID',
  `device_id` int(10) NOT NULL COMMENT '设备ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备区域表跟设备表中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_area_device
-- ----------------------------
INSERT INTO `vi_area_device` VALUES (1, 1, 1);
INSERT INTO `vi_area_device` VALUES (2, 1, 2);
INSERT INTO `vi_area_device` VALUES (3, 1, 3);
INSERT INTO `vi_area_device` VALUES (4, 2, 1);
INSERT INTO `vi_area_device` VALUES (5, 2, 2);
INSERT INTO `vi_area_device` VALUES (6, 2, 3);

-- ----------------------------
-- Table structure for vi_basic_member
-- ----------------------------
DROP TABLE IF EXISTS `vi_basic_member`;
CREATE TABLE `vi_basic_member`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `object_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象ID，数据来源库+id，若为自定义数据，则定义为vi_private+id',
  `repo_id` int(10) NOT NULL COMMENT '关联布控库的ID',
  `real_object_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '来源库真实主键ID，涉及到多个主键，需要下划线隔开合并',
  `real_table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '来源表',
  `identity_id` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `identity_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图像内容，base64编码',
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图像路径与content二选一，优先级高',
  `feature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图像特征信息',
  `attribute` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '布控目标属性',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态值 1-默认关注  0-无视',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_VI_MEMBER_OBJECT_ID`(`object_id`) USING BTREE,
  INDEX `INDEX_VI_MEMBER_REAL`(`real_object_id`, `real_table_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控目标基础库' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_device
-- ----------------------------
DROP TABLE IF EXISTS `vi_device`;
CREATE TABLE `vi_device`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名称',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '摄像机类型：1-球机；2-半球；3-固定枪机；4-遥控枪机',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备IP',
  `port` int(11) NULL DEFAULT 80 COMMENT '设备端口',
  `stream_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '码流状态 0:未启动; 1:已启动',
  `play_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频播放地址',
  `status` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 ON-在线 OFF-离线',
  `longitude` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '经度',
  `latitude` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '纬度',
  `civil_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政区划',
  `tq_api` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '天擎平台地址',
  `source` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0-真实设备  1-自定义设备',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_device
-- ----------------------------
INSERT INTO `vi_device` VALUES (7, '1', NULL, '上城区摄像头1', NULL, NULL, 80, 0, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'OFF', '121.89090253495695', '27.76237950917555', NULL, '192.168.1.1', 0, '2019-06-14 19:37:20', '2019-06-14 20:26:03');
INSERT INTO `vi_device` VALUES (8, '2', NULL, '下城区摄像头1', NULL, NULL, 80, 1, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'OFF', '118.13919024834334', '32.4088130215265', NULL, '192.168.1.1', 0, '2019-06-14 19:37:21', '2019-06-14 20:26:25');
INSERT INTO `vi_device` VALUES (9, '3', NULL, '上城区摄像头2', NULL, NULL, 80, 0, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'ON', '122.62649466993892', '30.90603459245156', NULL, '192.168.1.1', 0, '2019-06-14 19:38:06', '2019-06-14 20:26:04');
INSERT INTO `vi_device` VALUES (10, '4', NULL, '上城区摄像头2', NULL, NULL, 80, 1, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'ON', '121.6506892597775', '30.535342828869208', NULL, '192.168.1.1', 0, '2019-06-14 19:38:13', '2019-06-14 20:26:28');
INSERT INTO `vi_device` VALUES (11, '5', NULL, '上城区翠苑派出所摄像头1', NULL, NULL, 80, 1, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'ON', '122.72464667235002', '27.017205182478953', NULL, '192.168.1.1', 0, '2019-06-14 19:38:29', '2019-06-14 20:26:30');
INSERT INTO `vi_device` VALUES (12, '6', NULL, '上城区派出所摄像头1', NULL, NULL, 80, 0, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'ON', '121.91208620268114', '33.508815773305315', NULL, '192.168.1.1', 0, '2019-06-14 19:38:43', '2019-06-14 20:26:13');

-- ----------------------------
-- Table structure for vi_device_copy
-- ----------------------------
DROP TABLE IF EXISTS `vi_device_copy`;
CREATE TABLE `vi_device_copy`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名称',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '摄像机类型：1-球机；2-半球；3-固定枪机；4-遥控枪机',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备IP',
  `port` int(11) NULL DEFAULT 80 COMMENT '设备端口',
  `stream_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '码流状态 0:未启动; 1:已启动',
  `play_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频播放地址',
  `status` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 ON-在线 OFF-离线',
  `longitude` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '经度',
  `latitude` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '纬度',
  `civil_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政区划',
  `tq_api` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '天擎平台地址',
  `source` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0-真实设备  1-自定义设备',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25617 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_device_copy
-- ----------------------------
INSERT INTO `vi_device_copy` VALUES (23055, '33011701001310700592', '33011701', '吴山广场592', 2, NULL, 80, 0, NULL, '', '118.2999056', '29.7166033', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23056, '33011701001310700593', '33011701', '中河路上仓桥路593', 2, NULL, 80, 0, NULL, 'OFF', '126.846436', '28.9476374', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23057, '33011701001310700594', '33011701', '西湖大道建国路594', 2, NULL, 80, 0, NULL, 'OFF', '124.3324637', '30.5883775', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23058, '33011701001310700595', '33011701', '平海路中河路595', 2, NULL, 80, 0, NULL, 'OFF', '126.1230113', '31.0989755', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23059, '33011701001310700596', '33011701', '平海路延安路596', 2, NULL, 80, 0, NULL, 'OFF', '122.6176662', '28.7297454', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23060, '33011701001310700597', '33011701', '解放路浣纱路597', 2, NULL, 80, 0, NULL, 'OFF', '119.7192973', '25.3518006', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23061, '33011701001310700598', '33011701', '解放路建国路598', 2, NULL, 80, 0, NULL, 'OFF', '115.7434888', '25.5697674', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23062, '33011701001310700599', '33011701', '钱江三桥秋涛路599', 2, NULL, 80, 0, NULL, 'OFF', '124.5595527', '26.7934353', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23063, '66011701001310700002', '', 'testDevice', NULL, NULL, 80, 1, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'OFF', '114.432432', '22.43243', '', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23064, '2', '', 'dasda601', NULL, NULL, 80, 1, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', '', '134.1578311', '25.909067', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23065, '33011701001310700602', '33011701', '吴山广场602', 2, NULL, 80, 0, NULL, '', '134.0872631', '27.7717119', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23066, '33011701001310700603', '33011701', '中河路上仓桥路603', 2, NULL, 80, 0, NULL, 'OFF', '132.9628229', '26.131359', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23067, '33011701001310700604', '33011701', '西湖大道建国路604', 2, NULL, 80, 0, NULL, 'OFF', '127.5523256', '32.3416593', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23068, '33011701001310700605', '33011701', '平海路中河路605', 2, NULL, 80, 0, NULL, 'OFF', '123.8731598', '28.3142197', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23069, '33011701001310700606', '33011701', '平海路延安路606', 2, NULL, 80, 0, NULL, 'OFF', '121.7088231', '29.546121', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23070, '33011701001310700607', '33011701', '解放路浣纱路607', 2, NULL, 80, 0, NULL, 'OFF', '121.9246366', '27.7879464', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23071, '33011701001310700608', '33011701', '解放路建国路608', 2, NULL, 80, 0, NULL, 'OFF', '129.4967142', '25.3013692', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23072, '33011701001310700609', '33011701', '钱江三桥秋涛路609', 2, NULL, 80, 0, NULL, 'OFF', '126.7096619', '28.1430071', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23073, '33011701001310700610', '', 'testDevice610', NULL, NULL, 80, 0, NULL, 'OFF', '130.0581676', '29.8109282', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23074, '33011701001310700611', '', 'dasda611', NULL, NULL, 80, 0, NULL, '', '115.1618529', '29.6256198', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23075, '33011701001310700617', '33011701', '吴山广场617', 2, NULL, 80, 0, NULL, '', '130.6347622', '33.695315', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23076, '33011701001310700618', '33011701', '中河路上仓桥路618', 2, NULL, 80, 0, NULL, 'OFF', '132.6882532', '34.5997158', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23077, '33011701001310700619', '33011701', '西湖大道建国路619', 2, NULL, 80, 0, NULL, 'OFF', '116.5369798', '26.9126342', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23078, '33011701001310700620', '33011701', '平海路中河路620', 2, NULL, 80, 0, NULL, 'OFF', '129.62014', '25.7640239', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23079, '33011701001310700621', '33011701', '平海路延安路621', 2, NULL, 80, 0, NULL, 'OFF', '123.4897613', '33.0822172', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23080, '33011701001310700622', '33011701', '解放路浣纱路622', 2, NULL, 80, 0, NULL, 'OFF', '133.5883869', '33.1190149', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23081, '33011701001310700623', '33011701', '解放路建国路623', 2, NULL, 80, 0, NULL, 'OFF', '122.4726515', '31.3484228', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23082, '33011701001310700624', '33011701', '钱江三桥秋涛路624', 2, NULL, 80, 0, NULL, 'OFF', '116.5980974', '32.3850699', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23083, '33011701001310700625', '', 'testDevice625', NULL, NULL, 80, 0, NULL, 'OFF', '120.5725329', '32.8800814', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23084, '33011701001310700626', '', 'dasda626', NULL, NULL, 80, 0, NULL, '', '118.068373', '32.2451978', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23085, '33011701001310700627', '33011701', '吴山广场627', 2, NULL, 80, 0, NULL, '', '133.6242671', '27.5857448', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23086, '33011701001310700628', '33011701', '中河路上仓桥路628', 2, NULL, 80, 0, NULL, 'OFF', '118.916217', '26.1931308', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23087, '33011701001310700629', '33011701', '西湖大道建国路629', 2, NULL, 80, 0, NULL, 'OFF', '118.7082841', '33.2084201', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23088, '33011701001310700630', '33011701', '平海路中河路630', 2, NULL, 80, 0, NULL, 'OFF', '121.7927705', '32.4627085', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23089, '33011701001310700631', '33011701', '平海路延安路631', 2, NULL, 80, 0, NULL, 'OFF', '117.8390005', '27.6882823', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23090, '33011701001310700632', '33011701', '解放路浣纱路632', 2, NULL, 80, 0, NULL, 'OFF', '128.8166917', '26.0532866', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23091, '33011701001310700633', '33011701', '解放路建国路633', 2, NULL, 80, 0, NULL, 'OFF', '115.5664575', '32.2015861', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23092, '33011701001310700634', '33011701', '钱江三桥秋涛路634', 2, NULL, 80, 0, NULL, 'OFF', '116.3822131', '27.848071', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23093, '33011701001310700635', '', 'testDevice635', NULL, NULL, 80, 0, NULL, 'OFF', '120.2116935', '27.6355973', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23094, '33011701001310700636', '', 'dasda636', NULL, NULL, 80, 0, NULL, '', '116.9118288', '29.6337737', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23095, '33011701001310700648', '33011701', '吴山广场648', 2, NULL, 80, 0, NULL, '', '128.9240643', '30.2620767', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23096, '33011701001310700649', '33011701', '中河路上仓桥路649', 2, NULL, 80, 0, NULL, 'OFF', '118.8848355', '27.4090629', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23097, '33011701001310700650', '33011701', '西湖大道建国路650', 2, NULL, 80, 0, NULL, 'OFF', '132.6519851', '31.2590847', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23098, '33011701001310700651', '33011701', '平海路中河路651', 2, NULL, 80, 0, NULL, 'OFF', '131.6054198', '29.0682353', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23099, '33011701001310700652', '33011701', '平海路延安路652', 2, NULL, 80, 0, NULL, 'OFF', '125.0711441', '26.5639224', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23100, '33011701001310700653', '33011701', '解放路浣纱路653', 2, NULL, 80, 0, NULL, 'OFF', '115.5394618', '30.6149064', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23101, '33011701001310700654', '33011701', '解放路建国路654', 2, NULL, 80, 0, NULL, 'OFF', '127.4838774', '28.382765', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23102, '33011701001310700655', '33011701', '钱江三桥秋涛路655', 2, NULL, 80, 0, NULL, 'OFF', '115.8010023', '25.0691063', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23103, '33011701001310700656', '', 'testDevice656', NULL, NULL, 80, 0, NULL, 'OFF', '121.55338', '25.1972367', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23104, '33011701001310700657', '', 'dasda657', NULL, NULL, 80, 0, NULL, '', '125.3638937', '25.778865', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23105, '33011701001310700658', '33011701', '吴山广场658', 2, NULL, 80, 0, NULL, '', '127.1593291', '28.3026151', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23106, '33011701001310700659', '33011701', '中河路上仓桥路659', 2, NULL, 80, 0, NULL, 'OFF', '124.7049652', '29.1764807', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23107, '33011701001310700660', '33011701', '西湖大道建国路660', 2, NULL, 80, 0, NULL, 'OFF', '127.046839', '25.9745588', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23108, '33011701001310700661', '33011701', '平海路中河路661', 2, NULL, 80, 0, NULL, 'OFF', '126.1193', '27.3433522', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23109, '33011701001310700662', '33011701', '平海路延安路662', 2, NULL, 80, 0, NULL, 'OFF', '134.4559837', '33.7930846', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23110, '33011701001310700663', '33011701', '解放路浣纱路663', 2, NULL, 80, 0, NULL, 'OFF', '118.9220193', '31.9353669', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23111, '33011701001310700664', '33011701', '解放路建国路664', 2, NULL, 80, 0, NULL, 'OFF', '116.2421457', '33.297581', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23112, '33011701001310700665', '33011701', '钱江三桥秋涛路665', 2, NULL, 80, 0, NULL, 'OFF', '129.4446712', '25.6818047', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23113, '33011701001310700666', '', 'testDevice666', NULL, NULL, 80, 0, NULL, 'OFF', '123.4969197', '33.5162808', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23114, '33011701001310700667', '', 'dasda667', NULL, NULL, 80, 0, NULL, '', '134.1505857', '25.5359902', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23115, '33011701001310700668', '33011701', '吴山广场668', 2, NULL, 80, 0, NULL, '', '125.2621697', '32.1311089', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23116, '33011701001310700669', '33011701', '中河路上仓桥路669', 2, NULL, 80, 0, NULL, 'OFF', '128.859092', '29.047574', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23117, '33011701001310700670', '33011701', '西湖大道建国路670', 2, NULL, 80, 0, NULL, 'OFF', '133.5089517', '33.8445439', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23118, '33011701001310700671', '33011701', '平海路中河路671', 2, NULL, 80, 0, NULL, 'OFF', '125.9674831', '27.0799976', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23119, '33011701001310700672', '33011701', '平海路延安路672', 2, NULL, 80, 0, NULL, 'OFF', '134.3105611', '28.8663567', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23120, '33011701001310700673', '33011701', '解放路浣纱路673', 2, NULL, 80, 0, NULL, 'OFF', '118.6503566', '28.0917911', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23121, '33011701001310700674', '33011701', '解放路建国路674', 2, NULL, 80, 0, NULL, 'OFF', '115.3201003', '28.8598858', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23122, '33011701001310700675', '33011701', '钱江三桥秋涛路675', 2, NULL, 80, 0, NULL, 'OFF', '125.6494323', '25.024056', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23123, '33011701001310700676', '', 'testDevice676', NULL, NULL, 80, 0, NULL, 'OFF', '127.2868613', '33.5406228', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23124, '33011701001310700677', '', 'dasda677', NULL, NULL, 80, 0, NULL, '', '124.4860102', '27.6309462', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23125, '33011701001310700678', '33011701', '吴山广场678', 2, NULL, 80, 0, NULL, '', '125.5694679', '32.5328631', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23126, '33011701001310700679', '33011701', '中河路上仓桥路679', 2, NULL, 80, 0, NULL, 'OFF', '119.3893092', '34.7714772', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23127, '33011701001310700680', '33011701', '西湖大道建国路680', 2, NULL, 80, 0, NULL, 'OFF', '125.238143', '31.2587971', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23128, '33011701001310700681', '33011701', '平海路中河路681', 2, NULL, 80, 0, NULL, 'OFF', '133.0227878', '26.979554', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23129, '33011701001310700682', '33011701', '平海路延安路682', 2, NULL, 80, 0, NULL, 'OFF', '134.399511', '26.1213791', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23130, '33011701001310700683', '33011701', '解放路浣纱路683', 2, NULL, 80, 0, NULL, 'OFF', '117.929192', '34.6682337', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23131, '33011701001310700684', '33011701', '解放路建国路684', 2, NULL, 80, 0, NULL, 'OFF', '131.4474278', '29.9770317', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23132, '33011701001310700685', '33011701', '钱江三桥秋涛路685', 2, NULL, 80, 0, NULL, 'OFF', '128.4495636', '30.8804575', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23133, '33011701001310700686', '', 'testDevice686', NULL, NULL, 80, 0, NULL, 'OFF', '132.9055353', '29.4711926', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23134, '33011701001310700687', '', 'dasda687', NULL, NULL, 80, 0, NULL, '', '124.1789863', '29.714591', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23135, '33011701001310700711', '33011701', '吴山广场711', 2, NULL, 80, 0, NULL, '', '127.1783262', '25.1593773', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23136, '33011701001310700712', '33011701', '中河路上仓桥路712', 2, NULL, 80, 0, NULL, 'OFF', '128.3546728', '31.653114', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23137, '33011701001310700713', '33011701', '西湖大道建国路713', 2, NULL, 80, 0, NULL, 'OFF', '125.2383859', '27.7874385', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23138, '33011701001310700714', '33011701', '平海路中河路714', 2, NULL, 80, 0, NULL, 'OFF', '126.1279116', '28.9778507', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23139, '33011701001310700715', '33011701', '平海路延安路715', 2, NULL, 80, 0, NULL, 'OFF', '119.9244009', '26.5269382', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23140, '33011701001310700716', '33011701', '解放路浣纱路716', 2, NULL, 80, 0, NULL, 'OFF', '126.2382704', '30.7011394', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23141, '33011701001310700717', '33011701', '解放路建国路717', 2, NULL, 80, 0, NULL, 'OFF', '116.4181499', '28.9248825', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23142, '33011701001310700718', '33011701', '钱江三桥秋涛路718', 2, NULL, 80, 0, NULL, 'OFF', '128.3759388', '27.5209949', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23143, '33011701001310700719', '', 'testDevice719', NULL, NULL, 80, 0, NULL, 'OFF', '117.6252452', '25.830327', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23144, '33011701001310700720', '', 'dasda720', NULL, NULL, 80, 0, NULL, '', '127.99841', '31.5886506', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23145, '33011701001310700721', '33011701', '吴山广场721', 2, NULL, 80, 0, NULL, '', '132.1163153', '25.4522723', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23146, '33011701001310700722', '33011701', '中河路上仓桥路722', 2, NULL, 80, 0, NULL, 'OFF', '121.5863468', '27.49541', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23147, '33011701001310700723', '33011701', '西湖大道建国路723', 2, NULL, 80, 0, NULL, 'OFF', '116.5827889', '26.1202335', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23148, '33011701001310700724', '33011701', '平海路中河路724', 2, NULL, 80, 0, NULL, 'OFF', '123.1549047', '33.1149379', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23149, '33011701001310700725', '33011701', '平海路延安路725', 2, NULL, 80, 0, NULL, 'OFF', '131.0261572', '32.2139892', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23150, '33011701001310700726', '33011701', '解放路浣纱路726', 2, NULL, 80, 0, NULL, 'OFF', '130.6660728', '26.7251326', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23151, '33011701001310700727', '33011701', '解放路建国路727', 2, NULL, 80, 0, NULL, 'OFF', '125.2518928', '31.9836959', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23152, '33011701001310700728', '33011701', '钱江三桥秋涛路728', 2, NULL, 80, 0, NULL, 'OFF', '119.2612463', '34.7430819', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23153, '33011701001310700729', '', 'testDevice729', NULL, NULL, 80, 0, NULL, 'OFF', '125.5505536', '32.7643223', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23154, '33011701001310700730', '', 'dasda730', NULL, NULL, 80, 0, NULL, '', '134.9690297', '34.5923658', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23155, '33011701001310700731', '33011701', '吴山广场731', 2, NULL, 80, 0, NULL, '', '123.1934885', '29.6688624', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23156, '33011701001310700732', '33011701', '中河路上仓桥路732', 2, NULL, 80, 0, NULL, 'OFF', '116.0603539', '29.5672148', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23157, '33011701001310700733', '33011701', '西湖大道建国路733', 2, NULL, 80, 0, NULL, 'OFF', '115.7213044', '33.8294873', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23158, '33011701001310700734', '33011701', '平海路中河路734', 2, NULL, 80, 0, NULL, 'OFF', '115.4254612', '25.4457924', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23159, '33011701001310700735', '33011701', '平海路延安路735', 2, NULL, 80, 0, NULL, 'OFF', '134.9633935', '30.7405002', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23160, '33011701001310700736', '33011701', '解放路浣纱路736', 2, NULL, 80, 0, NULL, 'OFF', '133.5405843', '32.3651244', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23161, '33011701001310700737', '33011701', '解放路建国路737', 2, NULL, 80, 0, NULL, 'OFF', '127.8127416', '34.6041218', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23162, '33011701001310700738', '33011701', '钱江三桥秋涛路738', 2, NULL, 80, 0, NULL, 'OFF', '123.4419557', '30.9252358', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23163, '33011701001310700739', '', 'testDevice739', NULL, NULL, 80, 0, NULL, 'OFF', '118.7715543', '25.8138139', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23164, '33011701001310700740', '', 'dasda740', NULL, NULL, 80, 0, NULL, '', '128.5319049', '31.2933623', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23165, '33011701001310700741', '33011701', '吴山广场741', 2, NULL, 80, 0, NULL, '', '131.3448625', '34.0253704', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23166, '33011701001310700742', '33011701', '中河路上仓桥路742', 2, NULL, 80, 0, NULL, 'OFF', '116.1285983', '31.2467651', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23167, '33011701001310700743', '33011701', '西湖大道建国路743', 2, NULL, 80, 0, NULL, 'OFF', '131.6084046', '29.157715', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23168, '33011701001310700744', '33011701', '平海路中河路744', 2, NULL, 80, 0, NULL, 'OFF', '134.6562288', '27.0482797', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23169, '33011701001310700745', '33011701', '平海路延安路745', 2, NULL, 80, 0, NULL, 'OFF', '123.4559309', '32.7682537', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23170, '33011701001310700746', '33011701', '解放路浣纱路746', 2, NULL, 80, 0, NULL, 'OFF', '118.3109686', '27.6964298', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23171, '33011701001310700747', '33011701', '解放路建国路747', 2, NULL, 80, 0, NULL, 'OFF', '126.187051', '25.1773884', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23172, '33011701001310700748', '33011701', '钱江三桥秋涛路748', 2, NULL, 80, 0, NULL, 'OFF', '121.0023498', '27.797653', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23173, '33011701001310700749', '', 'testDevice749', NULL, NULL, 80, 0, NULL, 'OFF', '131.4505965', '28.4560999', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23174, '33011701001310700750', '', 'dasda750', NULL, NULL, 80, 0, NULL, '', '119.2459339', '33.8875407', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23175, '33011701001310700751', '33011701', '吴山广场751', 2, NULL, 80, 0, NULL, '', '126.8778804', '29.0694044', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23176, '33011701001310700752', '33011701', '中河路上仓桥路752', 2, NULL, 80, 0, NULL, 'OFF', '121.6516011', '28.6844001', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23177, '33011701001310700753', '33011701', '西湖大道建国路753', 2, NULL, 80, 0, NULL, 'OFF', '132.6243649', '31.2137875', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23178, '33011701001310700754', '33011701', '平海路中河路754', 2, NULL, 80, 0, NULL, 'OFF', '123.1670217', '25.0157376', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23179, '33011701001310700755', '33011701', '平海路延安路755', 2, NULL, 80, 0, NULL, 'OFF', '122.9620145', '26.4373259', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23180, '33011701001310700756', '33011701', '解放路浣纱路756', 2, NULL, 80, 0, NULL, 'OFF', '130.3090081', '32.1394168', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23181, '33011701001310700757', '33011701', '解放路建国路757', 2, NULL, 80, 0, NULL, 'OFF', '127.6589974', '26.3851067', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23182, '33011701001310700758', '33011701', '钱江三桥秋涛路758', 2, NULL, 80, 0, NULL, 'OFF', '132.3679632', '30.5072834', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23183, '33011701001310700759', '', 'testDevice759', NULL, NULL, 80, 0, NULL, 'OFF', '123.8628247', '28.3810972', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23184, '33011701001310700760', '', 'dasda760', NULL, NULL, 80, 0, NULL, '', '127.2102344', '25.383636', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23185, '33011701001310700761', '33011701', '吴山广场761', 2, NULL, 80, 0, NULL, '', '129.4626984', '26.7748889', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23186, '33011701001310700762', '33011701', '中河路上仓桥路762', 2, NULL, 80, 0, NULL, 'OFF', '130.6827896', '32.7235368', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23187, '33011701001310700763', '33011701', '西湖大道建国路763', 2, NULL, 80, 0, NULL, 'OFF', '130.0258535', '28.2930175', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23188, '33011701001310700764', '33011701', '平海路中河路764', 2, NULL, 80, 0, NULL, 'OFF', '123.0808993', '28.2944776', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23189, '33011701001310700765', '33011701', '平海路延安路765', 2, NULL, 80, 0, NULL, 'OFF', '130.3269367', '31.5933358', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23190, '33011701001310700766', '33011701', '解放路浣纱路766', 2, NULL, 80, 0, NULL, 'OFF', '127.3919861', '28.0832466', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23191, '33011701001310700767', '33011701', '解放路建国路767', 2, NULL, 80, 0, NULL, 'OFF', '130.9791208', '30.6362259', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23192, '33011701001310700768', '33011701', '钱江三桥秋涛路768', 2, NULL, 80, 0, NULL, 'OFF', '117.7196466', '33.9313901', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23193, '33011701001310700769', '', 'testDevice769', NULL, NULL, 80, 0, NULL, 'OFF', '120.6608713', '32.7482729', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23194, '33011701001310700770', '', 'dasda770', NULL, NULL, 80, 0, NULL, '', '115.1454172', '26.9471947', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23195, '33011701001310700771', '33011701', '吴山广场771', 2, NULL, 80, 0, NULL, '', '118.7444725', '31.4911548', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23196, '33011701001310700772', '33011701', '中河路上仓桥路772', 2, NULL, 80, 0, NULL, 'OFF', '133.2861117', '31.6141905', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23197, '33011701001310700773', '33011701', '西湖大道建国路773', 2, NULL, 80, 0, NULL, 'OFF', '115.1971416', '28.5974882', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23198, '33011701001310700774', '33011701', '平海路中河路774', 2, NULL, 80, 0, NULL, 'OFF', '121.1273736', '33.1448701', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23199, '33011701001310700775', '33011701', '平海路延安路775', 2, NULL, 80, 0, NULL, 'OFF', '125.0454437', '34.931886', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23200, '33011701001310700776', '33011701', '解放路浣纱路776', 2, NULL, 80, 0, NULL, 'OFF', '126.8450982', '30.2248201', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23201, '33011701001310700777', '33011701', '解放路建国路777', 2, NULL, 80, 0, NULL, 'OFF', '124.0891607', '31.3284429', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23202, '33011701001310700778', '33011701', '钱江三桥秋涛路778', 2, NULL, 80, 0, NULL, 'OFF', '124.9105095', '30.9677544', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23203, '33011701001310700779', '', 'testDevice779', NULL, NULL, 80, 0, NULL, 'OFF', '117.2850662', '25.8534436', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23204, '33011701001310700780', '', 'dasda780', NULL, NULL, 80, 0, NULL, '', '116.6938028', '31.3639551', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23205, '33011701001310700781', '33011701', '吴山广场781', 2, NULL, 80, 0, NULL, '', '116.613816', '34.2594448', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23206, '33011701001310700782', '33011701', '中河路上仓桥路782', 2, NULL, 80, 0, NULL, 'OFF', '117.9876724', '32.2053592', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23207, '33011701001310700783', '33011701', '西湖大道建国路783', 2, NULL, 80, 0, NULL, 'OFF', '125.0969146', '33.2484621', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23208, '33011701001310700784', '33011701', '平海路中河路784', 2, NULL, 80, 0, NULL, 'OFF', '116.5215563', '34.6262329', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23209, '33011701001310700785', '33011701', '平海路延安路785', 2, NULL, 80, 0, NULL, 'OFF', '132.3170385', '28.3857787', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23210, '33011701001310700786', '33011701', '解放路浣纱路786', 2, NULL, 80, 0, NULL, 'OFF', '117.0205243', '33.0501952', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23211, '33011701001310700787', '33011701', '解放路建国路787', 2, NULL, 80, 0, NULL, 'OFF', '133.1515066', '25.0936399', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23212, '33011701001310700788', '33011701', '钱江三桥秋涛路788', 2, NULL, 80, 0, NULL, 'OFF', '119.6959608', '31.3176144', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23213, '33011701001310700789', '', 'testDevice789', NULL, NULL, 80, 0, NULL, 'OFF', '124.0252846', '26.3071524', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23214, '33011701001310700790', '', 'dasda790', NULL, NULL, 80, 0, NULL, '', '126.0385412', '32.5829192', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23215, '33011701001310700838', '33011701', '吴山广场838', 2, NULL, 80, 0, NULL, '', '123.1168529', '28.9931392', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23216, '33011701001310700839', '33011701', '中河路上仓桥路839', 2, NULL, 80, 0, NULL, 'OFF', '122.4686415', '32.2169388', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23217, '33011701001310700840', '33011701', '西湖大道建国路840', 2, NULL, 80, 0, NULL, 'OFF', '127.9926496', '29.1052764', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23218, '33011701001310700841', '33011701', '平海路中河路841', 2, NULL, 80, 0, NULL, 'OFF', '117.5573239', '33.8755662', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23219, '33011701001310700842', '33011701', '平海路延安路842', 2, NULL, 80, 0, NULL, 'OFF', '128.8086713', '27.062002', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23220, '33011701001310700843', '33011701', '解放路浣纱路843', 2, NULL, 80, 0, NULL, 'OFF', '116.3713853', '28.6833115', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23221, '33011701001310700844', '33011701', '解放路建国路844', 2, NULL, 80, 0, NULL, 'OFF', '120.4309134', '27.2305521', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23222, '33011701001310700845', '33011701', '钱江三桥秋涛路845', 2, NULL, 80, 0, NULL, 'OFF', '118.0404118', '25.102826', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23223, '33011701001310700846', '', 'testDevice846', NULL, NULL, 80, 0, NULL, 'OFF', '133.9093195', '28.8224742', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23224, '33011701001310700847', '', 'dasda847', NULL, NULL, 80, 0, NULL, '', '120.4253627', '33.8038933', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23225, '33011701001310700848', '33011701', '吴山广场848', 2, NULL, 80, 0, NULL, '', '125.3988556', '27.5520441', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23226, '33011701001310700849', '33011701', '中河路上仓桥路849', 2, NULL, 80, 0, NULL, 'OFF', '130.7181903', '31.3485412', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23227, '33011701001310700850', '33011701', '西湖大道建国路850', 2, NULL, 80, 0, NULL, 'OFF', '122.3943853', '29.0865736', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23228, '33011701001310700851', '33011701', '平海路中河路851', 2, NULL, 80, 0, NULL, 'OFF', '124.8173564', '26.3872451', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23229, '33011701001310700852', '33011701', '平海路延安路852', 2, NULL, 80, 0, NULL, 'OFF', '121.9036267', '29.6765046', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23230, '33011701001310700853', '33011701', '解放路浣纱路853', 2, NULL, 80, 0, NULL, 'OFF', '120.066065', '34.2207884', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23231, '33011701001310700854', '33011701', '解放路建国路854', 2, NULL, 80, 0, NULL, 'OFF', '119.6194456', '27.0744282', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23232, '33011701001310700855', '33011701', '钱江三桥秋涛路855', 2, NULL, 80, 0, NULL, 'OFF', '122.8990337', '27.7097763', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23233, '33011701001310700856', '', 'testDevice856', NULL, NULL, 80, 0, NULL, 'OFF', '120.636832', '32.325597', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23234, '33011701001310700857', '', 'dasda857', NULL, NULL, 80, 0, NULL, '', '119.4870596', '33.4986566', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23235, '33011701001310700858', '33011701', '吴山广场858', 2, NULL, 80, 0, NULL, '', '120.5248027', '25.5164921', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23236, '33011701001310700859', '33011701', '中河路上仓桥路859', 2, NULL, 80, 0, NULL, 'OFF', '129.1628351', '32.086491', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23237, '33011701001310700860', '33011701', '西湖大道建国路860', 2, NULL, 80, 0, NULL, 'OFF', '129.2397681', '28.8829792', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23238, '33011701001310700861', '33011701', '平海路中河路861', 2, NULL, 80, 0, NULL, 'OFF', '123.710336', '33.1554233', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23239, '33011701001310700862', '33011701', '平海路延安路862', 2, NULL, 80, 0, NULL, 'OFF', '115.8323762', '34.1281793', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23240, '33011701001310700863', '33011701', '解放路浣纱路863', 2, NULL, 80, 0, NULL, 'OFF', '133.0308737', '26.1746267', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23241, '33011701001310700864', '33011701', '解放路建国路864', 2, NULL, 80, 0, NULL, 'OFF', '122.6572405', '33.4885959', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23242, '33011701001310700865', '33011701', '钱江三桥秋涛路865', 2, NULL, 80, 0, NULL, 'OFF', '119.1935818', '33.9190997', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23243, '33011701001310700866', '', 'testDevice866', NULL, NULL, 80, 0, NULL, 'OFF', '132.9961885', '34.1297111', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23244, '33011701001310700867', '', 'dasda867', NULL, NULL, 80, 0, NULL, '', '132.4001973', '33.8912569', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23245, '33011701001310700868', '33011701', '吴山广场868', 2, NULL, 80, 0, NULL, '', '128.012422', '32.0671516', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23246, '33011701001310700869', '33011701', '中河路上仓桥路869', 2, NULL, 80, 0, NULL, 'OFF', '127.8615185', '33.6619874', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23247, '33011701001310700870', '33011701', '西湖大道建国路870', 2, NULL, 80, 0, NULL, 'OFF', '120.2703271', '27.1084826', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23248, '33011701001310700871', '33011701', '平海路中河路871', 2, NULL, 80, 0, NULL, 'OFF', '122.7670805', '29.5564512', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23249, '33011701001310700872', '33011701', '平海路延安路872', 2, NULL, 80, 0, NULL, 'OFF', '118.0244221', '31.4568084', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23250, '33011701001310700873', '33011701', '解放路浣纱路873', 2, NULL, 80, 0, NULL, 'OFF', '126.8208695', '33.6146887', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23251, '33011701001310700874', '33011701', '解放路建国路874', 2, NULL, 80, 0, NULL, 'OFF', '125.0310818', '28.7030185', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23252, '33011701001310700875', '33011701', '钱江三桥秋涛路875', 2, NULL, 80, 0, NULL, 'OFF', '129.692801', '27.6710268', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23253, '33011701001310700876', '', 'testDevice876', NULL, NULL, 80, 0, NULL, 'OFF', '118.3707605', '27.2460789', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23254, '33011701001310700877', '', 'dasda877', NULL, NULL, 80, 0, NULL, '', '127.7754', '28.2173142', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23255, '33011701001310700878', '33011701', '吴山广场878', 2, NULL, 80, 0, NULL, '', '128.7647189', '34.3483346', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23256, '33011701001310700879', '33011701', '中河路上仓桥路879', 2, NULL, 80, 0, NULL, 'OFF', '125.4973953', '32.0897306', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23257, '33011701001310700880', '33011701', '西湖大道建国路880', 2, NULL, 80, 0, NULL, 'OFF', '126.1928204', '32.4036498', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23258, '33011701001310700881', '33011701', '平海路中河路881', 2, NULL, 80, 0, NULL, 'OFF', '119.4719169', '30.7490574', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23259, '33011701001310700882', '33011701', '平海路延安路882', 2, NULL, 80, 0, NULL, 'OFF', '123.7811236', '31.5343378', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23260, '33011701001310700883', '33011701', '解放路浣纱路883', 2, NULL, 80, 0, NULL, 'OFF', '125.489868', '30.4245172', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23261, '33011701001310700884', '33011701', '解放路建国路884', 2, NULL, 80, 0, NULL, 'OFF', '121.10597', '32.5195729', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23262, '33011701001310700885', '33011701', '钱江三桥秋涛路885', 2, NULL, 80, 0, NULL, 'OFF', '134.0602463', '26.3243134', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23263, '33011701001310700886', '', 'testDevice886', NULL, NULL, 80, 0, NULL, 'OFF', '131.9833225', '29.0628484', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23264, '33011701001310700887', '', 'dasda887', NULL, NULL, 80, 0, NULL, '', '122.7358739', '31.3413022', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23265, '33011701001310700888', '33011701', '吴山广场888', 2, NULL, 80, 0, NULL, '', '122.7294026', '34.5179661', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23266, '33011701001310700889', '33011701', '中河路上仓桥路889', 2, NULL, 80, 0, NULL, 'OFF', '130.4393921', '33.5659242', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23267, '33011701001310700890', '33011701', '西湖大道建国路890', 2, NULL, 80, 0, NULL, 'OFF', '129.0087532', '29.2757229', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23268, '33011701001310700891', '33011701', '平海路中河路891', 2, NULL, 80, 0, NULL, 'OFF', '118.7255902', '30.6808424', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23269, '33011701001310700892', '33011701', '平海路延安路892', 2, NULL, 80, 0, NULL, 'OFF', '131.6016921', '30.5770437', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23270, '33011701001310700893', '33011701', '解放路浣纱路893', 2, NULL, 80, 0, NULL, 'OFF', '126.8316904', '25.8426913', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23271, '33011701001310700894', '33011701', '解放路建国路894', 2, NULL, 80, 0, NULL, 'OFF', '124.3533762', '32.4823259', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23272, '33011701001310700895', '33011701', '钱江三桥秋涛路895', 2, NULL, 80, 0, NULL, 'OFF', '126.2718107', '29.8835561', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23273, '33011701001310700896', '', 'testDevice896', NULL, NULL, 80, 0, NULL, 'OFF', '123.2989256', '26.9708027', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23274, '33011701001310700897', '', 'dasda897', NULL, NULL, 80, 0, NULL, '', '122.6791965', '30.2033459', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23275, '33011701001310700898', '33011701', '吴山广场898', 2, NULL, 80, 0, NULL, '', '128.499206', '25.1043214', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23276, '33011701001310700899', '33011701', '中河路上仓桥路899', 2, NULL, 80, 0, NULL, 'OFF', '119.4584414', '29.9115696', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23277, '33011701001310700900', '33011701', '西湖大道建国路900', 2, NULL, 80, 0, NULL, 'OFF', '116.7945897', '29.2448843', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23278, '33011701001310700901', '33011701', '平海路中河路901', 2, NULL, 80, 0, NULL, 'OFF', '130.5976248', '31.489713', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23279, '33011701001310700902', '33011701', '平海路延安路902', 2, NULL, 80, 0, NULL, 'OFF', '127.6043556', '34.7139124', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23280, '33011701001310700903', '33011701', '解放路浣纱路903', 2, NULL, 80, 0, NULL, 'OFF', '131.228904', '34.1004235', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23281, '33011701001310700904', '33011701', '解放路建国路904', 2, NULL, 80, 0, NULL, 'OFF', '118.3314539', '31.3603804', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23282, '33011701001310700905', '33011701', '钱江三桥秋涛路905', 2, NULL, 80, 0, NULL, 'OFF', '122.9705581', '29.5006317', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23283, '33011701001310700906', '', 'testDevice906', NULL, NULL, 80, 0, NULL, 'OFF', '124.8584295', '28.4220179', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23284, '33011701001310700907', '', 'dasda907', NULL, NULL, 80, 0, NULL, '', '120.3804738', '28.6081945', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23285, '33011701001310700908', '33011701', '吴山广场908', 2, NULL, 80, 0, NULL, '', '132.3270809', '32.7749192', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23286, '33011701001310700909', '33011701', '中河路上仓桥路909', 2, NULL, 80, 0, NULL, 'OFF', '125.4939839', '33.0500126', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23287, '33011701001310700910', '33011701', '西湖大道建国路910', 2, NULL, 80, 0, NULL, 'OFF', '115.4886773', '31.9253058', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23288, '33011701001310700911', '33011701', '平海路中河路911', 2, NULL, 80, 0, NULL, 'OFF', '125.9614354', '25.4764916', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23289, '33011701001310700912', '33011701', '平海路延安路912', 2, NULL, 80, 0, NULL, 'OFF', '128.3411458', '26.6065408', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23290, '33011701001310700913', '33011701', '解放路浣纱路913', 2, NULL, 80, 0, NULL, 'OFF', '128.8214234', '31.6032296', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23291, '33011701001310700914', '33011701', '解放路建国路914', 2, NULL, 80, 0, NULL, 'OFF', '124.0836803', '33.196526', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23292, '33011701001310700915', '33011701', '钱江三桥秋涛路915', 2, NULL, 80, 0, NULL, 'OFF', '118.9541318', '26.1729416', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23293, '33011701001310700916', '', 'testDevice916', NULL, NULL, 80, 0, NULL, 'OFF', '127.5196189', '26.2751301', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23294, '33011701001310700917', '', 'dasda917', NULL, NULL, 80, 0, NULL, '', '125.7356995', '27.856826', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23295, '33011701001310700918', '33011701', '吴山广场918', 2, NULL, 80, 0, NULL, '', '131.1196416', '25.4587402', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23296, '33011701001310700919', '33011701', '中河路上仓桥路919', 2, NULL, 80, 0, NULL, 'OFF', '123.39111', '28.7232231', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23297, '33011701001310700920', '33011701', '西湖大道建国路920', 2, NULL, 80, 0, NULL, 'OFF', '128.596626', '32.2398954', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23298, '33011701001310700921', '33011701', '平海路中河路921', 2, NULL, 80, 0, NULL, 'OFF', '117.8098007', '30.0298079', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23299, '33011701001310700922', '33011701', '平海路延安路922', 2, NULL, 80, 0, NULL, 'OFF', '128.259126', '28.4293538', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23300, '33011701001310700923', '33011701', '解放路浣纱路923', 2, NULL, 80, 0, NULL, 'OFF', '132.8662286', '27.0573455', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23301, '33011701001310700924', '33011701', '解放路建国路924', 2, NULL, 80, 0, NULL, 'OFF', '124.5537658', '34.9986665', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23302, '33011701001310700925', '33011701', '钱江三桥秋涛路925', 2, NULL, 80, 0, NULL, 'OFF', '129.1701434', '28.8212962', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23303, '33011701001310700926', '', 'testDevice926', NULL, NULL, 80, 0, NULL, 'OFF', '117.1894206', '34.1104817', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23304, '33011701001310700927', '', 'dasda927', NULL, NULL, 80, 0, NULL, '', '123.4366732', '29.0885204', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23305, '33011701001310700928', '33011701', '吴山广场928', 2, NULL, 80, 0, NULL, '', '130.615105', '28.1111571', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23306, '33011701001310700929', '33011701', '中河路上仓桥路929', 2, NULL, 80, 0, NULL, 'OFF', '127.765506', '28.2902245', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23307, '33011701001310700930', '33011701', '西湖大道建国路930', 2, NULL, 80, 0, NULL, 'OFF', '131.9822154', '32.1176515', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23308, '33011701001310700931', '33011701', '平海路中河路931', 2, NULL, 80, 0, NULL, 'OFF', '121.6145596', '30.7175846', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23309, '33011701001310700932', '33011701', '平海路延安路932', 2, NULL, 80, 0, NULL, 'OFF', '117.1261526', '32.2349686', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23310, '33011701001310700933', '33011701', '解放路浣纱路933', 2, NULL, 80, 0, NULL, 'OFF', '125.787085', '34.0220896', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23311, '33011701001310700934', '33011701', '解放路建国路934', 2, NULL, 80, 0, NULL, 'OFF', '122.5569674', '28.4055423', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23312, '33011701001310700935', '33011701', '钱江三桥秋涛路935', 2, NULL, 80, 0, NULL, 'OFF', '120.4235829', '34.9614433', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23313, '33011701001310700936', '', 'testDevice936', NULL, NULL, 80, 0, NULL, 'OFF', '119.4470128', '34.5905896', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23314, '33011701001310700937', '', 'dasda937', NULL, NULL, 80, 0, NULL, '', '120.9643159', '33.0686185', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23315, '33011701001310700938', '33011701', '吴山广场938', 2, NULL, 80, 0, NULL, '', '131.4805417', '26.571324', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23316, '33011701001310700939', '33011701', '中河路上仓桥路939', 2, NULL, 80, 0, NULL, 'OFF', '119.5097615', '28.6507648', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23317, '33011701001310700940', '33011701', '西湖大道建国路940', 2, NULL, 80, 0, NULL, 'OFF', '128.1071829', '28.5398525', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23318, '33011701001310700941', '33011701', '平海路中河路941', 2, NULL, 80, 0, NULL, 'OFF', '127.0066307', '31.7469684', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23319, '33011701001310700942', '33011701', '平海路延安路942', 2, NULL, 80, 0, NULL, 'OFF', '115.7116056', '28.1152847', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23320, '33011701001310700943', '33011701', '解放路浣纱路943', 2, NULL, 80, 0, NULL, 'OFF', '122.5381362', '30.3355184', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23321, '33011701001310700944', '33011701', '解放路建国路944', 2, NULL, 80, 0, NULL, 'OFF', '130.5558651', '32.3317384', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23322, '33011701001310700945', '33011701', '钱江三桥秋涛路945', 2, NULL, 80, 0, NULL, 'OFF', '130.1649173', '25.6521372', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23323, '33011701001310700946', '', 'testDevice946', NULL, NULL, 80, 0, NULL, 'OFF', '124.1569918', '26.2654711', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23324, '33011701001310700947', '', 'dasda947', NULL, NULL, 80, 0, NULL, '', '115.2902077', '29.3709443', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23325, '33011701001310700948', '33011701', '吴山广场948', 2, NULL, 80, 0, NULL, '', '128.9800636', '33.0583082', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23326, '33011701001310700949', '33011701', '中河路上仓桥路949', 2, NULL, 80, 0, NULL, 'OFF', '124.0296956', '32.1787086', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23327, '33011701001310700950', '33011701', '西湖大道建国路950', 2, NULL, 80, 0, NULL, 'OFF', '118.2082877', '26.7186185', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23328, '33011701001310700951', '33011701', '平海路中河路951', 2, NULL, 80, 0, NULL, 'OFF', '123.9523524', '32.0569672', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23329, '33011701001310700952', '33011701', '平海路延安路952', 2, NULL, 80, 0, NULL, 'OFF', '130.1368995', '25.1289808', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23330, '33011701001310700953', '33011701', '解放路浣纱路953', 2, NULL, 80, 0, NULL, 'OFF', '123.8274409', '34.4740026', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23331, '33011701001310700954', '33011701', '解放路建国路954', 2, NULL, 80, 0, NULL, 'OFF', '133.7265067', '31.9830708', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23332, '33011701001310700955', '33011701', '钱江三桥秋涛路955', 2, NULL, 80, 0, NULL, 'OFF', '122.1502114', '31.4933466', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23333, '33011701001310700956', '', 'testDevice956', NULL, NULL, 80, 0, NULL, 'OFF', '134.5715373', '26.5175208', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23334, '33011701001310700957', '', 'dasda957', NULL, NULL, 80, 0, NULL, '', '131.4070531', '33.1075647', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23335, '33011701001310700958', '33011701', '吴山广场958', 2, NULL, 80, 0, NULL, '', '118.3206543', '30.9852612', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23336, '33011701001310700959', '33011701', '中河路上仓桥路959', 2, NULL, 80, 0, NULL, 'OFF', '122.3821128', '30.6036123', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23337, '33011701001310700960', '33011701', '西湖大道建国路960', 2, NULL, 80, 0, NULL, 'OFF', '121.9486017', '25.0622783', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23338, '33011701001310700961', '33011701', '平海路中河路961', 2, NULL, 80, 0, NULL, 'OFF', '127.5966706', '28.500555', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23339, '33011701001310700962', '33011701', '平海路延安路962', 2, NULL, 80, 0, NULL, 'OFF', '117.1375487', '32.3159404', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23340, '33011701001310700963', '33011701', '解放路浣纱路963', 2, NULL, 80, 0, NULL, 'OFF', '127.8977322', '31.0780372', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23341, '33011701001310700964', '33011701', '解放路建国路964', 2, NULL, 80, 0, NULL, 'OFF', '133.0760154', '33.4423651', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23342, '33011701001310700965', '33011701', '钱江三桥秋涛路965', 2, NULL, 80, 0, NULL, 'OFF', '126.6868809', '28.9777141', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23343, '33011701001310700966', '', 'testDevice966', NULL, NULL, 80, 0, NULL, 'OFF', '119.2063591', '29.5614758', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23344, '33011701001310700967', '', 'dasda967', NULL, NULL, 80, 0, NULL, '', '120.9711535', '25.8742367', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23345, '33011701001310700968', '33011701', '吴山广场968', 2, NULL, 80, 0, NULL, '', '132.2366908', '25.6867565', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23346, '33011701001310700969', '33011701', '中河路上仓桥路969', 2, NULL, 80, 0, NULL, 'OFF', '123.2699941', '25.8110729', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23347, '33011701001310700970', '33011701', '西湖大道建国路970', 2, NULL, 80, 0, NULL, 'OFF', '124.6398985', '26.995095', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23348, '33011701001310700971', '33011701', '平海路中河路971', 2, NULL, 80, 0, NULL, 'OFF', '118.3895111', '32.5422568', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23349, '33011701001310700972', '33011701', '平海路延安路972', 2, NULL, 80, 0, NULL, 'OFF', '123.0278604', '26.7259991', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23350, '33011701001310700973', '33011701', '解放路浣纱路973', 2, NULL, 80, 0, NULL, 'OFF', '124.9707694', '31.0032256', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23351, '33011701001310700974', '33011701', '解放路建国路974', 2, NULL, 80, 0, NULL, 'OFF', '120.7702664', '29.838131', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23352, '33011701001310700975', '33011701', '钱江三桥秋涛路975', 2, NULL, 80, 0, NULL, 'OFF', '133.9390242', '31.1809783', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23353, '33011701001310700976', '', 'testDevice976', NULL, NULL, 80, 0, NULL, 'OFF', '132.3843228', '31.3904988', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23354, '33011701001310700977', '', 'dasda977', NULL, NULL, 80, 0, NULL, '', '125.1045417', '28.4095596', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23355, '33011701001310700978', '33011701', '吴山广场978', 2, NULL, 80, 0, NULL, '', '133.3697409', '32.8763017', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23356, '33011701001310700979', '33011701', '中河路上仓桥路979', 2, NULL, 80, 0, NULL, 'OFF', '116.5350799', '34.1528302', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23357, '33011701001310700980', '33011701', '西湖大道建国路980', 2, NULL, 80, 0, NULL, 'OFF', '127.5661773', '27.135246', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23358, '33011701001310700981', '33011701', '平海路中河路981', 2, NULL, 80, 0, NULL, 'OFF', '133.2256477', '28.2177396', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23359, '33011701001310700982', '33011701', '平海路延安路982', 2, NULL, 80, 0, NULL, 'OFF', '128.429707', '34.6829606', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23360, '33011701001310700983', '33011701', '解放路浣纱路983', 2, NULL, 80, 0, NULL, 'OFF', '127.4715925', '33.7615844', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23361, '33011701001310700984', '33011701', '解放路建国路984', 2, NULL, 80, 0, NULL, 'OFF', '117.0688422', '29.7590404', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23362, '33011701001310700985', '33011701', '钱江三桥秋涛路985', 2, NULL, 80, 0, NULL, 'OFF', '127.9294342', '32.5104492', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23363, '33011701001310700986', '', 'testDevice986', NULL, NULL, 80, 0, NULL, 'OFF', '133.4406449', '28.2751251', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23364, '33011701001310700987', '', 'dasda987', NULL, NULL, 80, 0, NULL, '', '128.4149226', '28.8442783', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23365, '33011701001310700988', '33011701', '吴山广场988', 2, NULL, 80, 0, NULL, '', '126.7526788', '34.3960163', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23366, '33011701001310700989', '33011701', '中河路上仓桥路989', 2, NULL, 80, 0, NULL, 'OFF', '133.5186267', '30.4472469', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23367, '33011701001310700990', '33011701', '西湖大道建国路990', 2, NULL, 80, 0, NULL, 'OFF', '132.3350979', '34.0481861', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23368, '33011701001310700991', '33011701', '平海路中河路991', 2, NULL, 80, 0, NULL, 'OFF', '126.1196098', '33.8991899', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23369, '33011701001310700992', '33011701', '平海路延安路992', 2, NULL, 80, 0, NULL, 'OFF', '118.592756', '32.3513916', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23370, '33011701001310700993', '33011701', '解放路浣纱路993', 2, NULL, 80, 0, NULL, 'OFF', '119.6049514', '25.0593888', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23371, '33011701001310700994', '33011701', '解放路建国路994', 2, NULL, 80, 0, NULL, 'OFF', '127.2464895', '33.2427694', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23372, '33011701001310700995', '33011701', '钱江三桥秋涛路995', 2, NULL, 80, 0, NULL, 'OFF', '122.4175941', '26.035681', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23373, '33011701001310700996', '', 'testDevice996', NULL, NULL, 80, 0, NULL, 'OFF', '115.3485024', '25.4500968', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23374, '33011701001310700997', '', 'dasda997', NULL, NULL, 80, 0, NULL, '', '134.4897302', '34.1434417', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23375, '33011701001310701093', '33011701', '吴山广场1093', 2, NULL, 80, 0, NULL, '', '131.4031446', '29.3669181', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23376, '33011701001310701094', '33011701', '中河路上仓桥路1094', 2, NULL, 80, 0, NULL, 'OFF', '118.5465328', '29.4042659', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23377, '33011701001310701095', '33011701', '西湖大道建国路1095', 2, NULL, 80, 0, NULL, 'OFF', '123.5232309', '33.9205755', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23378, '33011701001310701096', '33011701', '平海路中河路1096', 2, NULL, 80, 0, NULL, 'OFF', '126.9765566', '26.3900802', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23379, '33011701001310701097', '33011701', '平海路延安路1097', 2, NULL, 80, 0, NULL, 'OFF', '129.3130912', '25.1886748', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23380, '33011701001310701098', '33011701', '解放路浣纱路1098', 2, NULL, 80, 0, NULL, 'OFF', '130.6357866', '31.7731338', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23381, '33011701001310701099', '33011701', '解放路建国路1099', 2, NULL, 80, 0, NULL, 'OFF', '130.2396601', '28.2996449', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23382, '33011701001310701100', '33011701', '钱江三桥秋涛路1100', 2, NULL, 80, 0, NULL, 'OFF', '124.2909414', '31.1788235', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23383, '33011701001310701101', '', 'testDevice1101', NULL, NULL, 80, 0, NULL, 'OFF', '115.7357272', '25.9951832', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23384, '33011701001310701102', '', 'dasda1102', NULL, NULL, 80, 0, NULL, '', '130.8058123', '31.4394455', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23385, '33011701001310701103', '33011701', '吴山广场1103', 2, NULL, 80, 0, NULL, '', '131.8218808', '34.2116783', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23386, '33011701001310701104', '33011701', '中河路上仓桥路1104', 2, NULL, 80, 0, NULL, 'OFF', '131.6919676', '31.7400552', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23387, '33011701001310701105', '33011701', '西湖大道建国路1105', 2, NULL, 80, 0, NULL, 'OFF', '127.9941962', '31.0652417', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23388, '33011701001310701106', '33011701', '平海路中河路1106', 2, NULL, 80, 0, NULL, 'OFF', '129.8950788', '25.1060429', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23389, '33011701001310701107', '33011701', '平海路延安路1107', 2, NULL, 80, 0, NULL, 'OFF', '130.4928062', '27.3344898', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23390, '33011701001310701108', '33011701', '解放路浣纱路1108', 2, NULL, 80, 0, NULL, 'OFF', '127.778795', '26.3543208', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23391, '33011701001310701109', '33011701', '解放路建国路1109', 2, NULL, 80, 0, NULL, 'OFF', '132.4155569', '34.7681346', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23392, '33011701001310701110', '33011701', '钱江三桥秋涛路1110', 2, NULL, 80, 0, NULL, 'OFF', '123.7414003', '29.7777112', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23393, '33011701001310701111', '', 'testDevice1111', NULL, NULL, 80, 0, NULL, 'OFF', '126.4603316', '29.5841525', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23394, '33011701001310701112', '', 'dasda1112', NULL, NULL, 80, 0, NULL, '', '126.0774574', '33.5876291', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23395, '33011701001310701113', '33011701', '吴山广场1113', 2, NULL, 80, 0, NULL, '', '116.0062928', '34.1856884', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23396, '33011701001310701114', '33011701', '中河路上仓桥路1114', 2, NULL, 80, 0, NULL, 'OFF', '126.7990926', '25.1655551', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23397, '33011701001310701115', '33011701', '西湖大道建国路1115', 2, NULL, 80, 0, NULL, 'OFF', '130.9765853', '28.2707104', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23398, '33011701001310701116', '33011701', '平海路中河路1116', 2, NULL, 80, 0, NULL, 'OFF', '119.4856493', '30.8568873', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23399, '33011701001310701117', '33011701', '平海路延安路1117', 2, NULL, 80, 0, NULL, 'OFF', '129.498491', '34.4723054', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23400, '33011701001310701118', '33011701', '解放路浣纱路1118', 2, NULL, 80, 0, NULL, 'OFF', '134.0355078', '34.7908656', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23401, '33011701001310701119', '33011701', '解放路建国路1119', 2, NULL, 80, 0, NULL, 'OFF', '126.6820668', '25.5374119', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23402, '33011701001310701120', '33011701', '钱江三桥秋涛路1120', 2, NULL, 80, 0, NULL, 'OFF', '116.3038109', '28.314463', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23403, '33011701001310701121', '', 'testDevice1121', NULL, NULL, 80, 0, NULL, 'OFF', '126.472855', '29.9600798', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23404, '33011701001310701122', '', 'dasda1122', NULL, NULL, 80, 0, NULL, '', '128.4528426', '29.8570101', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23405, '33011701001310701123', '33011701', '吴山广场1123', 2, NULL, 80, 0, NULL, '', '127.8456489', '34.4048113', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23406, '33011701001310701124', '33011701', '中河路上仓桥路1124', 2, NULL, 80, 0, NULL, 'OFF', '118.8697173', '27.4530268', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23407, '33011701001310701125', '33011701', '西湖大道建国路1125', 2, NULL, 80, 0, NULL, 'OFF', '115.8116406', '29.0507001', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23408, '33011701001310701126', '33011701', '平海路中河路1126', 2, NULL, 80, 0, NULL, 'OFF', '127.4490514', '27.8944205', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23409, '33011701001310701127', '33011701', '平海路延安路1127', 2, NULL, 80, 0, NULL, 'OFF', '134.8103359', '27.3200027', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23410, '33011701001310701128', '33011701', '解放路浣纱路1128', 2, NULL, 80, 0, NULL, 'OFF', '116.7045259', '27.9167523', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23411, '33011701001310701129', '33011701', '解放路建国路1129', 2, NULL, 80, 0, NULL, 'OFF', '124.0916225', '32.6237535', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23412, '33011701001310701130', '33011701', '钱江三桥秋涛路1130', 2, NULL, 80, 0, NULL, 'OFF', '115.3445353', '34.3685112', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23413, '33011701001310701131', '', 'testDevice1131', NULL, NULL, 80, 0, NULL, 'OFF', '129.4478097', '28.9712956', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23414, '33011701001310701132', '', 'dasda1132', NULL, NULL, 80, 0, NULL, '', '126.205443', '26.750945', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23415, '33011701001310701133', '33011701', '吴山广场1133', 2, NULL, 80, 0, NULL, '', '127.6837867', '31.8408382', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23416, '33011701001310701134', '33011701', '中河路上仓桥路1134', 2, NULL, 80, 0, NULL, 'OFF', '124.8026049', '33.9513564', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23417, '33011701001310701135', '33011701', '西湖大道建国路1135', 2, NULL, 80, 0, NULL, 'OFF', '125.9616653', '29.2342676', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23418, '33011701001310701136', '33011701', '平海路中河路1136', 2, NULL, 80, 0, NULL, 'OFF', '120.4005124', '29.317269', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23419, '33011701001310701137', '33011701', '平海路延安路1137', 2, NULL, 80, 0, NULL, 'OFF', '129.1175665', '33.8835425', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23420, '33011701001310701138', '33011701', '解放路浣纱路1138', 2, NULL, 80, 0, NULL, 'OFF', '129.3862961', '26.465906', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23421, '33011701001310701139', '33011701', '解放路建国路1139', 2, NULL, 80, 0, NULL, 'OFF', '124.5787817', '25.6789029', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23422, '33011701001310701140', '33011701', '钱江三桥秋涛路1140', 2, NULL, 80, 0, NULL, 'OFF', '119.7350208', '33.9967965', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23423, '33011701001310701141', '', 'testDevice1141', NULL, NULL, 80, 0, NULL, 'OFF', '129.9387594', '27.9472743', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23424, '33011701001310701142', '', 'dasda1142', NULL, NULL, 80, 0, NULL, '', '115.488735', '32.7459821', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23425, '33011701001310701143', '33011701', '吴山广场1143', 2, NULL, 80, 0, NULL, '', '132.6273978', '34.8880881', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23426, '33011701001310701144', '33011701', '中河路上仓桥路1144', 2, NULL, 80, 0, NULL, 'OFF', '121.6707844', '31.2024943', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23427, '33011701001310701145', '33011701', '西湖大道建国路1145', 2, NULL, 80, 0, NULL, 'OFF', '115.4717294', '26.3482075', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23428, '33011701001310701146', '33011701', '平海路中河路1146', 2, NULL, 80, 0, NULL, 'OFF', '117.3462944', '33.1335552', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23429, '33011701001310701147', '33011701', '平海路延安路1147', 2, NULL, 80, 0, NULL, 'OFF', '125.3162843', '31.6231536', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23430, '33011701001310701148', '33011701', '解放路浣纱路1148', 2, NULL, 80, 0, NULL, 'OFF', '119.5425388', '33.7151029', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23431, '33011701001310701149', '33011701', '解放路建国路1149', 2, NULL, 80, 0, NULL, 'OFF', '126.7638416', '28.7060538', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23432, '33011701001310701150', '33011701', '钱江三桥秋涛路1150', 2, NULL, 80, 0, NULL, 'OFF', '120.1915924', '27.3849605', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23433, '33011701001310701151', '', 'testDevice1151', NULL, NULL, 80, 0, NULL, 'OFF', '125.6664379', '25.8066416', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23434, '33011701001310701152', '', 'dasda1152', NULL, NULL, 80, 0, NULL, '', '132.7574129', '31.8783266', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23435, '33011701001310701153', '33011701', '吴山广场1153', 2, NULL, 80, 0, NULL, '', '131.7877515', '26.9717084', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23436, '33011701001310701154', '33011701', '中河路上仓桥路1154', 2, NULL, 80, 0, NULL, 'OFF', '125.6665195', '34.2235627', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23437, '33011701001310701155', '33011701', '西湖大道建国路1155', 2, NULL, 80, 0, NULL, 'OFF', '117.9693434', '25.2026885', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23438, '33011701001310701156', '33011701', '平海路中河路1156', 2, NULL, 80, 0, NULL, 'OFF', '117.8471594', '28.342755', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23439, '33011701001310701157', '33011701', '平海路延安路1157', 2, NULL, 80, 0, NULL, 'OFF', '120.3277671', '31.1057095', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23440, '33011701001310701158', '33011701', '解放路浣纱路1158', 2, NULL, 80, 0, NULL, 'OFF', '133.097358', '25.5002829', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23441, '33011701001310701159', '33011701', '解放路建国路1159', 2, NULL, 80, 0, NULL, 'OFF', '129.5034891', '29.1842862', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23442, '33011701001310701160', '33011701', '钱江三桥秋涛路1160', 2, NULL, 80, 0, NULL, 'OFF', '133.2253724', '34.4205826', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23443, '33011701001310701161', '', 'testDevice1161', NULL, NULL, 80, 0, NULL, 'OFF', '122.6163953', '29.5500547', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23444, '33011701001310701162', '', 'dasda1162', NULL, NULL, 80, 0, NULL, '', '118.4058598', '29.4885263', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23445, '33011701001310701163', '33011701', '吴山广场1163', 2, NULL, 80, 0, NULL, '', '129.1801138', '33.7924674', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23446, '33011701001310701164', '33011701', '中河路上仓桥路1164', 2, NULL, 80, 0, NULL, 'OFF', '115.6829902', '25.4967584', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23447, '33011701001310701165', '33011701', '西湖大道建国路1165', 2, NULL, 80, 0, NULL, 'OFF', '115.8746101', '31.10639', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23448, '33011701001310701166', '33011701', '平海路中河路1166', 2, NULL, 80, 0, NULL, 'OFF', '117.3240807', '34.0416753', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23449, '33011701001310701167', '33011701', '平海路延安路1167', 2, NULL, 80, 0, NULL, 'OFF', '123.9965735', '31.8892068', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23450, '33011701001310701168', '33011701', '解放路浣纱路1168', 2, NULL, 80, 0, NULL, 'OFF', '133.0106264', '32.3210084', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23451, '33011701001310701169', '33011701', '解放路建国路1169', 2, NULL, 80, 0, NULL, 'OFF', '118.0634119', '30.9374218', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23452, '33011701001310701170', '33011701', '钱江三桥秋涛路1170', 2, NULL, 80, 0, NULL, 'OFF', '116.2851808', '32.7240843', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23453, '33011701001310701171', '', 'testDevice1171', NULL, NULL, 80, 0, NULL, 'OFF', '132.235669', '25.8081563', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23454, '33011701001310701172', '', 'dasda1172', NULL, NULL, 80, 0, NULL, '', '117.3228034', '25.868529', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23455, '33011701001310701173', '33011701', '吴山广场1173', 2, NULL, 80, 0, NULL, '', '134.9070106', '26.9181765', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23456, '33011701001310701174', '33011701', '中河路上仓桥路1174', 2, NULL, 80, 0, NULL, 'OFF', '127.5666433', '31.9852959', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23457, '33011701001310701175', '33011701', '西湖大道建国路1175', 2, NULL, 80, 0, NULL, 'OFF', '118.1121853', '34.1719502', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23458, '33011701001310701176', '33011701', '平海路中河路1176', 2, NULL, 80, 0, NULL, 'OFF', '132.8609973', '29.9038634', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23459, '33011701001310701177', '33011701', '平海路延安路1177', 2, NULL, 80, 0, NULL, 'OFF', '134.9684313', '32.0034668', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23460, '33011701001310701178', '33011701', '解放路浣纱路1178', 2, NULL, 80, 0, NULL, 'OFF', '121.2591653', '25.3057442', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23461, '33011701001310701179', '33011701', '解放路建国路1179', 2, NULL, 80, 0, NULL, 'OFF', '126.3905329', '25.5183208', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23462, '33011701001310701180', '33011701', '钱江三桥秋涛路1180', 2, NULL, 80, 0, NULL, 'OFF', '133.1751695', '26.6743716', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23463, '33011701001310701181', '', 'testDevice1181', NULL, NULL, 80, 0, NULL, 'OFF', '131.7042493', '31.816896', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23464, '33011701001310701182', '', 'dasda1182', NULL, NULL, 80, 0, NULL, '', '123.9957388', '34.0613653', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23465, '33011701001310701183', '33011701', '吴山广场1183', 2, NULL, 80, 0, NULL, '', '129.8659466', '29.8561391', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23466, '33011701001310701184', '33011701', '中河路上仓桥路1184', 2, NULL, 80, 0, NULL, 'OFF', '122.3425172', '32.0965996', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23467, '33011701001310701185', '33011701', '西湖大道建国路1185', 2, NULL, 80, 0, NULL, 'OFF', '127.1147468', '25.9145812', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23468, '33011701001310701186', '33011701', '平海路中河路1186', 2, NULL, 80, 0, NULL, 'OFF', '133.5461831', '28.2831074', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23469, '33011701001310701187', '33011701', '平海路延安路1187', 2, NULL, 80, 0, NULL, 'OFF', '131.3866755', '28.6717938', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23470, '33011701001310701188', '33011701', '解放路浣纱路1188', 2, NULL, 80, 0, NULL, 'OFF', '121.2948288', '33.5096472', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23471, '33011701001310701189', '33011701', '解放路建国路1189', 2, NULL, 80, 0, NULL, 'OFF', '117.314118', '26.5328547', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23472, '33011701001310701190', '33011701', '钱江三桥秋涛路1190', 2, NULL, 80, 0, NULL, 'OFF', '127.6861044', '27.1353322', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23473, '33011701001310701191', '', 'testDevice1191', NULL, NULL, 80, 0, NULL, 'OFF', '131.4881684', '31.0780972', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23474, '33011701001310701192', '', 'dasda1192', NULL, NULL, 80, 0, NULL, '', '119.3825296', '28.9844896', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23475, '33011701001310701193', '33011701', '吴山广场1193', 2, NULL, 80, 0, NULL, '', '127.4481432', '26.6881567', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23476, '33011701001310701194', '33011701', '中河路上仓桥路1194', 2, NULL, 80, 0, NULL, 'OFF', '124.093128', '31.487315', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23477, '33011701001310701195', '33011701', '西湖大道建国路1195', 2, NULL, 80, 0, NULL, 'OFF', '123.1212108', '32.3721051', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23478, '33011701001310701196', '33011701', '平海路中河路1196', 2, NULL, 80, 0, NULL, 'OFF', '128.3266709', '32.3985809', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23479, '33011701001310701197', '33011701', '平海路延安路1197', 2, NULL, 80, 0, NULL, 'OFF', '117.2697224', '29.8765895', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23480, '33011701001310701198', '33011701', '解放路浣纱路1198', 2, NULL, 80, 0, NULL, 'OFF', '126.3686001', '27.187205', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23481, '33011701001310701199', '33011701', '解放路建国路1199', 2, NULL, 80, 0, NULL, 'OFF', '125.0338339', '31.3062569', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23482, '33011701001310701200', '33011701', '钱江三桥秋涛路1200', 2, NULL, 80, 0, NULL, 'OFF', '131.0633698', '29.9696699', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23483, '33011701001310701201', '', 'testDevice1201', NULL, NULL, 80, 0, NULL, 'OFF', '125.2153479', '30.929579', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23484, '33011701001310701202', '', 'dasda1202', NULL, NULL, 80, 0, NULL, '', '117.8866306', '29.7388855', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23485, '33011701001310701203', '33011701', '吴山广场1203', 2, NULL, 80, 0, NULL, '', '118.7871099', '30.9056909', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23486, '33011701001310701204', '33011701', '中河路上仓桥路1204', 2, NULL, 80, 0, NULL, 'OFF', '125.2756584', '30.3117982', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23487, '33011701001310701205', '33011701', '西湖大道建国路1205', 2, NULL, 80, 0, NULL, 'OFF', '115.0169631', '33.8419188', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23488, '33011701001310701206', '33011701', '平海路中河路1206', 2, NULL, 80, 0, NULL, 'OFF', '124.2578406', '33.2741994', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23489, '33011701001310701207', '33011701', '平海路延安路1207', 2, NULL, 80, 0, NULL, 'OFF', '121.2383143', '29.8452412', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23490, '33011701001310701208', '33011701', '解放路浣纱路1208', 2, NULL, 80, 0, NULL, 'OFF', '118.4180506', '34.4036078', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23491, '33011701001310701209', '33011701', '解放路建国路1209', 2, NULL, 80, 0, NULL, 'OFF', '133.3753107', '27.4823159', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23492, '33011701001310701210', '33011701', '钱江三桥秋涛路1210', 2, NULL, 80, 0, NULL, 'OFF', '116.622402', '29.2007565', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23493, '33011701001310701211', '', 'testDevice1211', NULL, NULL, 80, 0, NULL, 'OFF', '127.9860788', '28.556835', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23494, '33011701001310701212', '', 'dasda1212', NULL, NULL, 80, 0, NULL, '', '115.0631887', '30.1819059', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23495, '33011701001310701213', '33011701', '吴山广场1213', 2, NULL, 80, 0, NULL, '', '116.3577075', '30.2390246', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23496, '33011701001310701214', '33011701', '中河路上仓桥路1214', 2, NULL, 80, 0, NULL, 'OFF', '121.598972', '25.6494059', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23497, '33011701001310701215', '33011701', '西湖大道建国路1215', 2, NULL, 80, 0, NULL, 'OFF', '123.9217381', '32.5299559', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23498, '33011701001310701216', '33011701', '平海路中河路1216', 2, NULL, 80, 0, NULL, 'OFF', '119.811775', '30.7015619', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23499, '33011701001310701217', '33011701', '平海路延安路1217', 2, NULL, 80, 0, NULL, 'OFF', '132.2936616', '30.9179423', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23500, '33011701001310701218', '33011701', '解放路浣纱路1218', 2, NULL, 80, 0, NULL, 'OFF', '127.0329834', '27.485026', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23501, '33011701001310701219', '33011701', '解放路建国路1219', 2, NULL, 80, 0, NULL, 'OFF', '123.2839331', '29.6713035', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23502, '33011701001310701220', '33011701', '钱江三桥秋涛路1220', 2, NULL, 80, 0, NULL, 'OFF', '120.3207156', '30.9014398', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23503, '33011701001310701221', '', 'testDevice1221', NULL, NULL, 80, 0, NULL, 'OFF', '116.7517796', '30.4932888', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23504, '33011701001310701222', '', 'dasda1222', NULL, NULL, 80, 0, NULL, '', '127.7967519', '34.7621247', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23505, '33011701001310701223', '33011701', '吴山广场1223', 2, NULL, 80, 0, NULL, '', '133.728421', '27.3307575', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23506, '33011701001310701224', '33011701', '中河路上仓桥路1224', 2, NULL, 80, 0, NULL, 'OFF', '130.2518498', '27.3674139', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23507, '33011701001310701225', '33011701', '西湖大道建国路1225', 2, NULL, 80, 0, NULL, 'OFF', '115.073987', '29.8447972', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23508, '33011701001310701226', '33011701', '平海路中河路1226', 2, NULL, 80, 0, NULL, 'OFF', '129.614386', '32.1217448', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23509, '33011701001310701227', '33011701', '平海路延安路1227', 2, NULL, 80, 0, NULL, 'OFF', '127.8499696', '26.0743326', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23510, '33011701001310701228', '33011701', '解放路浣纱路1228', 2, NULL, 80, 0, NULL, 'OFF', '115.4066908', '29.006429', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23511, '33011701001310701229', '33011701', '解放路建国路1229', 2, NULL, 80, 0, NULL, 'OFF', '118.4835457', '31.8091476', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23512, '33011701001310701230', '33011701', '钱江三桥秋涛路1230', 2, NULL, 80, 0, NULL, 'OFF', '131.1976568', '27.026451', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23513, '33011701001310701231', '', 'testDevice1231', NULL, NULL, 80, 0, NULL, 'OFF', '125.5376473', '34.7048126', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23514, '33011701001310701232', '', 'dasda1232', NULL, NULL, 80, 0, NULL, '', '119.0952668', '27.4447105', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23515, '33011701001310701233', '33011701', '吴山广场1233', 2, NULL, 80, 0, NULL, '', '123.8633928', '28.1091148', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23516, '33011701001310701234', '33011701', '中河路上仓桥路1234', 2, NULL, 80, 0, NULL, 'OFF', '127.0311642', '33.2114429', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23517, '33011701001310701235', '33011701', '西湖大道建国路1235', 2, NULL, 80, 0, NULL, 'OFF', '128.5656433', '26.7298706', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23518, '33011701001310701236', '33011701', '平海路中河路1236', 2, NULL, 80, 0, NULL, 'OFF', '126.7347247', '29.0150243', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23519, '33011701001310701237', '33011701', '平海路延安路1237', 2, NULL, 80, 0, NULL, 'OFF', '132.9766938', '29.8855099', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23520, '33011701001310701238', '33011701', '解放路浣纱路1238', 2, NULL, 80, 0, NULL, 'OFF', '129.6792958', '27.3824771', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23521, '33011701001310701239', '33011701', '解放路建国路1239', 2, NULL, 80, 0, NULL, 'OFF', '134.4663981', '32.2558561', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23522, '33011701001310701240', '33011701', '钱江三桥秋涛路1240', 2, NULL, 80, 0, NULL, 'OFF', '128.2941036', '34.1318497', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23523, '33011701001310701241', '', 'testDevice1241', NULL, NULL, 80, 0, NULL, 'OFF', '123.0713246', '28.8916802', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23524, '33011701001310701242', '', 'dasda1242', NULL, NULL, 80, 0, NULL, '', '115.4743126', '27.0628524', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23525, '33011701001310701243', '33011701', '吴山广场1243', 2, NULL, 80, 0, NULL, '', '133.15759', '33.6392218', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23526, '33011701001310701244', '33011701', '中河路上仓桥路1244', 2, NULL, 80, 0, NULL, 'OFF', '124.3650129', '32.0075519', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23527, '33011701001310701245', '33011701', '西湖大道建国路1245', 2, NULL, 80, 0, NULL, 'OFF', '127.3522951', '34.1200947', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23528, '33011701001310701246', '33011701', '平海路中河路1246', 2, NULL, 80, 0, NULL, 'OFF', '128.6664375', '29.5778178', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23529, '33011701001310701247', '33011701', '平海路延安路1247', 2, NULL, 80, 0, NULL, 'OFF', '126.2753027', '30.5288055', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23530, '33011701001310701248', '33011701', '解放路浣纱路1248', 2, NULL, 80, 0, NULL, 'OFF', '130.3772017', '28.9105742', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23531, '33011701001310701249', '33011701', '解放路建国路1249', 2, NULL, 80, 0, NULL, 'OFF', '118.060101', '27.9664547', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23532, '33011701001310701250', '33011701', '钱江三桥秋涛路1250', 2, NULL, 80, 0, NULL, 'OFF', '124.1689005', '28.1005515', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23533, '33011701001310701251', '', 'testDevice1251', NULL, NULL, 80, 0, NULL, 'OFF', '131.6642003', '31.6033935', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23534, '33011701001310701252', '', 'dasda1252', NULL, NULL, 80, 0, NULL, '', '130.8143004', '28.7153135', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23535, '33011701001310701253', '33011701', '吴山广场1253', 2, NULL, 80, 0, NULL, '', '124.0789016', '33.7663874', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23536, '33011701001310701254', '33011701', '中河路上仓桥路1254', 2, NULL, 80, 0, NULL, 'OFF', '132.9516076', '27.6859967', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23537, '33011701001310701255', '33011701', '西湖大道建国路1255', 2, NULL, 80, 0, NULL, 'OFF', '117.5213336', '32.1308217', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23538, '33011701001310701256', '33011701', '平海路中河路1256', 2, NULL, 80, 0, NULL, 'OFF', '133.7518458', '32.5961186', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23539, '33011701001310701257', '33011701', '平海路延安路1257', 2, NULL, 80, 0, NULL, 'OFF', '121.1952289', '31.5881283', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23540, '33011701001310701258', '33011701', '解放路浣纱路1258', 2, NULL, 80, 0, NULL, 'OFF', '129.7206075', '25.1522861', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23541, '33011701001310701259', '33011701', '解放路建国路1259', 2, NULL, 80, 0, NULL, 'OFF', '130.0173516', '25.9970459', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23542, '33011701001310701260', '33011701', '钱江三桥秋涛路1260', 2, NULL, 80, 0, NULL, 'OFF', '125.9249361', '29.5283713', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23543, '33011701001310701261', '', 'testDevice1261', NULL, NULL, 80, 0, NULL, 'OFF', '124.5726262', '34.6507193', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23544, '33011701001310701262', '', 'dasda1262', NULL, NULL, 80, 0, NULL, '', '130.0883234', '29.6684827', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23545, '33011701001310701263', '33011701', '吴山广场1263', 2, NULL, 80, 0, NULL, '', '121.7237392', '29.390256', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23546, '33011701001310701264', '33011701', '中河路上仓桥路1264', 2, NULL, 80, 0, NULL, 'OFF', '123.3537263', '32.9458323', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23547, '33011701001310701265', '33011701', '西湖大道建国路1265', 2, NULL, 80, 0, NULL, 'OFF', '116.5974143', '31.5583939', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23548, '33011701001310701266', '33011701', '平海路中河路1266', 2, NULL, 80, 0, NULL, 'OFF', '117.9258934', '33.9544727', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23549, '33011701001310701267', '33011701', '平海路延安路1267', 2, NULL, 80, 0, NULL, 'OFF', '124.8372248', '30.0971821', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23550, '33011701001310701268', '33011701', '解放路浣纱路1268', 2, NULL, 80, 0, NULL, 'OFF', '115.4084443', '33.6224926', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23551, '33011701001310701269', '33011701', '解放路建国路1269', 2, NULL, 80, 0, NULL, 'OFF', '127.5305477', '32.8209169', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23552, '33011701001310701270', '33011701', '钱江三桥秋涛路1270', 2, NULL, 80, 0, NULL, 'OFF', '116.4274063', '28.2371074', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23553, '33011701001310701271', '', 'testDevice1271', NULL, NULL, 80, 0, NULL, 'OFF', '124.5453889', '27.7227863', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23554, '33011701001310701272', '', 'dasda1272', NULL, NULL, 80, 0, NULL, '', '118.4447262', '28.9026096', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23555, '33011701001310701273', '33011701', '吴山广场1273', 2, NULL, 80, 0, NULL, '', '123.5874649', '26.3446895', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23556, '33011701001310701274', '33011701', '中河路上仓桥路1274', 2, NULL, 80, 0, NULL, 'OFF', '127.6031467', '30.015619', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23557, '33011701001310701275', '33011701', '西湖大道建国路1275', 2, NULL, 80, 0, NULL, 'OFF', '132.2533391', '26.0440267', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23558, '33011701001310701276', '33011701', '平海路中河路1276', 2, NULL, 80, 0, NULL, 'OFF', '123.4572562', '25.1732768', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23559, '33011701001310701277', '33011701', '平海路延安路1277', 2, NULL, 80, 0, NULL, 'OFF', '125.5262642', '32.7343044', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23560, '33011701001310701278', '33011701', '解放路浣纱路1278', 2, NULL, 80, 0, NULL, 'OFF', '122.259553', '33.1516918', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23561, '33011701001310701279', '33011701', '解放路建国路1279', 2, NULL, 80, 0, NULL, 'OFF', '119.7189729', '32.5555461', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23562, '33011701001310701280', '33011701', '钱江三桥秋涛路1280', 2, NULL, 80, 0, NULL, 'OFF', '116.8162061', '28.3226552', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23563, '33011701001310701281', '', 'testDevice1281', NULL, NULL, 80, 0, NULL, 'OFF', '129.9241123', '28.9466384', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23564, '33011701001310701282', '', 'dasda1282', NULL, NULL, 80, 0, NULL, '', '124.171944', '34.7652263', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23565, '33011701001310701283', '33011701', '吴山广场1283', 2, NULL, 80, 0, NULL, '', '116.0873837', '31.9862169', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23566, '33011701001310701284', '33011701', '中河路上仓桥路1284', 2, NULL, 80, 0, NULL, 'OFF', '132.9210872', '30.635406', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23567, '33011701001310701285', '33011701', '西湖大道建国路1285', 2, NULL, 80, 0, NULL, 'OFF', '121.3432854', '32.2183794', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23568, '33011701001310701286', '33011701', '平海路中河路1286', 2, NULL, 80, 0, NULL, 'OFF', '132.9531663', '34.1856793', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23569, '33011701001310701287', '33011701', '平海路延安路1287', 2, NULL, 80, 0, NULL, 'OFF', '125.7359755', '29.2732588', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23570, '33011701001310701288', '33011701', '解放路浣纱路1288', 2, NULL, 80, 0, NULL, 'OFF', '134.8203795', '28.8092564', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23571, '33011701001310701289', '33011701', '解放路建国路1289', 2, NULL, 80, 0, NULL, 'OFF', '121.8939716', '31.2265059', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23572, '33011701001310701290', '33011701', '钱江三桥秋涛路1290', 2, NULL, 80, 0, NULL, 'OFF', '130.0087202', '34.7047605', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23573, '33011701001310701291', '', 'testDevice1291', NULL, NULL, 80, 0, NULL, 'OFF', '129.3616866', '34.844285', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23574, '33011701001310701292', '', 'dasda1292', NULL, NULL, 80, 0, NULL, '', '121.7822731', '25.1071439', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23575, '33011701001310701293', '33011701', '吴山广场1293', 2, NULL, 80, 0, NULL, '', '125.8263065', '26.0028647', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23576, '33011701001310701294', '33011701', '中河路上仓桥路1294', 2, NULL, 80, 0, NULL, 'OFF', '128.7847135', '29.6928921', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23577, '33011701001310701295', '33011701', '西湖大道建国路1295', 2, NULL, 80, 0, NULL, 'OFF', '131.4446489', '25.4558666', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23578, '33011701001310701296', '33011701', '平海路中河路1296', 2, NULL, 80, 0, NULL, 'OFF', '115.8691045', '33.2006572', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23579, '33011701001310701297', '33011701', '平海路延安路1297', 2, NULL, 80, 0, NULL, 'OFF', '130.0115763', '34.6356864', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23580, '33011701001310701298', '33011701', '解放路浣纱路1298', 2, NULL, 80, 0, NULL, 'OFF', '127.4505687', '28.5764608', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23581, '33011701001310701299', '33011701', '解放路建国路1299', 2, NULL, 80, 0, NULL, 'OFF', '132.2181152', '33.975245', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23582, '33011701001310701300', '33011701', '钱江三桥秋涛路1300', 2, NULL, 80, 0, NULL, 'OFF', '123.7388703', '29.1468429', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23583, '33011701001310701301', '', 'testDevice1301', NULL, NULL, 80, 0, NULL, 'OFF', '127.0400069', '28.8084799', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23584, '33011701001310701302', '', 'dasda1302', NULL, NULL, 80, 0, NULL, '', '128.983424', '31.6018713', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23585, '33011701001310701303', '33011701', '吴山广场1303', 2, NULL, 80, 0, NULL, '', '128.7970999', '26.5839168', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23586, '33011701001310701304', '33011701', '中河路上仓桥路1304', 2, NULL, 80, 0, NULL, 'OFF', '122.0352281', '33.1139705', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23587, '33011701001310701305', '33011701', '西湖大道建国路1305', 2, NULL, 80, 0, NULL, 'OFF', '128.7848413', '30.8181026', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23588, '33011701001310701306', '33011701', '平海路中河路1306', 2, NULL, 80, 0, NULL, 'OFF', '122.8185231', '29.7486016', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23589, '33011701001310701307', '33011701', '平海路延安路1307', 2, NULL, 80, 0, NULL, 'OFF', '132.7380922', '31.2887007', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23590, '33011701001310701308', '33011701', '解放路浣纱路1308', 2, NULL, 80, 0, NULL, 'OFF', '120.2348923', '32.1976989', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23591, '33011701001310701309', '33011701', '解放路建国路1309', 2, NULL, 80, 0, NULL, 'OFF', '127.9601856', '32.1223925', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23592, '33011701001310701310', '33011701', '钱江三桥秋涛路1310', 2, NULL, 80, 0, NULL, 'OFF', '124.0962517', '29.0188663', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23593, '33011701001310701311', '', 'testDevice1311', NULL, NULL, 80, 0, NULL, 'OFF', '121.6007024', '33.7271545', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23594, '33011701001310701312', '', 'dasda1312', NULL, NULL, 80, 0, NULL, '', '120.7147575', '26.5791737', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23595, '33011701001310701313', '33011701', '吴山广场1313', 2, NULL, 80, 0, NULL, '', '123.7716807', '26.7144054', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23596, '33011701001310701314', '33011701', '中河路上仓桥路1314', 2, NULL, 80, 0, NULL, 'OFF', '121.7141318', '28.834506', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23597, '33011701001310701315', '33011701', '西湖大道建国路1315', 2, NULL, 80, 0, NULL, 'OFF', '122.2556174', '29.0293141', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23598, '33011701001310701316', '33011701', '平海路中河路1316', 2, NULL, 80, 0, NULL, 'OFF', '131.1356922', '33.6430529', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23599, '33011701001310701317', '33011701', '平海路延安路1317', 2, NULL, 80, 0, NULL, 'OFF', '133.9116095', '26.1273224', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23600, '33011701001310701318', '33011701', '解放路浣纱路1318', 2, NULL, 80, 0, NULL, 'OFF', '121.1509713', '34.7074535', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23601, '33011701001310701319', '33011701', '解放路建国路1319', 2, NULL, 80, 0, NULL, 'OFF', '129.0200288', '30.1553007', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23602, '33011701001310701320', '33011701', '钱江三桥秋涛路1320', 2, NULL, 80, 0, NULL, 'OFF', '126.6472308', '31.6541432', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23603, '33011701001310701321', '', 'testDevice1321', NULL, NULL, 80, 0, NULL, 'OFF', '131.1760682', '32.8048141', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23604, '33011701001310701322', '', 'dasda1322', NULL, NULL, 80, 0, NULL, '', '120.9386493', '34.0616415', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23605, '33011701001310701323', '33011701', '吴山广场1323', 2, NULL, 80, 0, NULL, '', '116.1650426', '26.8937654', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23606, '33011701001310701324', '33011701', '中河路上仓桥路1324', 2, NULL, 80, 0, NULL, 'OFF', '123.0092654', '27.2839029', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23607, '33011701001310701325', '33011701', '西湖大道建国路1325', 2, NULL, 80, 0, NULL, 'OFF', '131.5512', '30.7382186', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23608, '33011701001310701326', '33011701', '平海路中河路1326', 2, NULL, 80, 0, NULL, 'OFF', '133.7282043', '26.8393846', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23609, '33011701001310701327', '33011701', '平海路延安路1327', 2, NULL, 80, 0, NULL, 'OFF', '118.9874221', '26.9822675', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23610, '33011701001310701328', '33011701', '解放路浣纱路1328', 2, NULL, 80, 0, NULL, 'OFF', '118.7524984', '29.393184', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23611, '33011701001310701329', '33011701', '解放路建国路1329', 2, NULL, 80, 0, NULL, 'OFF', '121.8002264', '31.019118', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23612, '33011701001310701330', '33011701', '钱江三桥秋涛路1330', 2, NULL, 80, 0, NULL, 'OFF', '117.7436373', '31.9160381', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23613, '33011701001310701331', '', 'testDevice1331', NULL, NULL, 80, 0, NULL, 'OFF', '128.317508', '31.5228367', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23614, '33011701001310701332', '', 'dasda1332', NULL, NULL, 80, 0, NULL, '', '133.3566287', '26.8660698', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23615, '33011701001310701333', '33011701', '吴山广场1333', 2, NULL, 80, 0, NULL, '', '126.8306199', '34.7618392', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23616, '33011701001310701334', '33011701', '中河路上仓桥路1334', 2, NULL, 80, 0, NULL, 'OFF', '119.0832141', '28.2109867', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23617, '33011701001310701335', '33011701', '西湖大道建国路1335', 2, NULL, 80, 0, NULL, 'OFF', '119.9242116', '31.7694162', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23618, '33011701001310701336', '33011701', '平海路中河路1336', 2, NULL, 80, 0, NULL, 'OFF', '127.3714164', '29.2141211', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23619, '33011701001310701337', '33011701', '平海路延安路1337', 2, NULL, 80, 0, NULL, 'OFF', '122.0844475', '25.7623574', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23620, '33011701001310701338', '33011701', '解放路浣纱路1338', 2, NULL, 80, 0, NULL, 'OFF', '133.3079891', '26.1694238', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23621, '33011701001310701339', '33011701', '解放路建国路1339', 2, NULL, 80, 0, NULL, 'OFF', '125.2866038', '28.5600471', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23622, '33011701001310701340', '33011701', '钱江三桥秋涛路1340', 2, NULL, 80, 0, NULL, 'OFF', '131.509052', '29.2919646', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23623, '33011701001310701341', '', 'testDevice1341', NULL, NULL, 80, 0, NULL, 'OFF', '126.6854494', '25.7796819', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23624, '33011701001310701342', '', 'dasda1342', NULL, NULL, 80, 0, NULL, '', '123.9000914', '26.0225161', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23625, '33011701001310701343', '33011701', '吴山广场1343', 2, NULL, 80, 0, NULL, '', '124.4441095', '27.773535', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23626, '33011701001310701344', '33011701', '中河路上仓桥路1344', 2, NULL, 80, 0, NULL, 'OFF', '115.520274', '25.8001271', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23627, '33011701001310701345', '33011701', '西湖大道建国路1345', 2, NULL, 80, 0, NULL, 'OFF', '129.2690419', '30.6800308', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23628, '33011701001310701346', '33011701', '平海路中河路1346', 2, NULL, 80, 0, NULL, 'OFF', '124.7843884', '30.9997728', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23629, '33011701001310701347', '33011701', '平海路延安路1347', 2, NULL, 80, 0, NULL, 'OFF', '121.1148167', '27.9587722', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23630, '33011701001310701348', '33011701', '解放路浣纱路1348', 2, NULL, 80, 0, NULL, 'OFF', '116.2209191', '31.7945429', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23631, '33011701001310701349', '33011701', '解放路建国路1349', 2, NULL, 80, 0, NULL, 'OFF', '122.7601459', '30.0963981', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23632, '33011701001310701350', '33011701', '钱江三桥秋涛路1350', 2, NULL, 80, 0, NULL, 'OFF', '130.137973', '30.0983621', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23633, '33011701001310701351', '', 'testDevice1351', NULL, NULL, 80, 0, NULL, 'OFF', '127.4094277', '25.2026165', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23634, '33011701001310701352', '', 'dasda1352', NULL, NULL, 80, 0, NULL, '', '131.6332202', '30.7179967', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23635, '33011701001310701353', '33011701', '吴山广场1353', 2, NULL, 80, 0, NULL, '', '120.9378184', '32.9821343', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23636, '33011701001310701354', '33011701', '中河路上仓桥路1354', 2, NULL, 80, 0, NULL, 'OFF', '134.7894322', '27.7566816', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23637, '33011701001310701355', '33011701', '西湖大道建国路1355', 2, NULL, 80, 0, NULL, 'OFF', '116.1337063', '34.8370056', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23638, '33011701001310701356', '33011701', '平海路中河路1356', 2, NULL, 80, 0, NULL, 'OFF', '121.3002356', '25.9149836', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23639, '33011701001310701357', '33011701', '平海路延安路1357', 2, NULL, 80, 0, NULL, 'OFF', '123.1000596', '30.0639012', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23640, '33011701001310701358', '33011701', '解放路浣纱路1358', 2, NULL, 80, 0, NULL, 'OFF', '116.5995919', '27.5745556', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23641, '33011701001310701359', '33011701', '解放路建国路1359', 2, NULL, 80, 0, NULL, 'OFF', '118.6977811', '32.6810749', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23642, '33011701001310701360', '33011701', '钱江三桥秋涛路1360', 2, NULL, 80, 0, NULL, 'OFF', '128.6901306', '25.6817077', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23643, '33011701001310701361', '', 'testDevice1361', NULL, NULL, 80, 0, NULL, 'OFF', '132.3573104', '25.3653144', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23644, '33011701001310701362', '', 'dasda1362', NULL, NULL, 80, 0, NULL, '', '120.7161608', '34.7814491', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23645, '33011701001310701363', '33011701', '吴山广场1363', 2, NULL, 80, 0, NULL, '', '131.5088732', '32.8113026', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23646, '33011701001310701364', '33011701', '中河路上仓桥路1364', 2, NULL, 80, 0, NULL, 'OFF', '120.3958844', '34.7121659', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23647, '33011701001310701365', '33011701', '西湖大道建国路1365', 2, NULL, 80, 0, NULL, 'OFF', '132.4528029', '30.126922', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23648, '33011701001310701366', '33011701', '平海路中河路1366', 2, NULL, 80, 0, NULL, 'OFF', '126.076362', '31.4981125', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23649, '33011701001310701367', '33011701', '平海路延安路1367', 2, NULL, 80, 0, NULL, 'OFF', '118.023402', '32.109797', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23650, '33011701001310701368', '33011701', '解放路浣纱路1368', 2, NULL, 80, 0, NULL, 'OFF', '116.8879246', '31.0546479', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23651, '33011701001310701369', '33011701', '解放路建国路1369', 2, NULL, 80, 0, NULL, 'OFF', '115.3694177', '33.9438487', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23652, '33011701001310701370', '33011701', '钱江三桥秋涛路1370', 2, NULL, 80, 0, NULL, 'OFF', '131.1833153', '31.5553002', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23653, '33011701001310701371', '', 'testDevice1371', NULL, NULL, 80, 0, NULL, 'OFF', '134.8083241', '30.9449553', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23654, '33011701001310701372', '', 'dasda1372', NULL, NULL, 80, 0, NULL, '', '125.491675', '25.0588762', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23655, '33011701001310701373', '33011701', '吴山广场1373', 2, NULL, 80, 0, NULL, '', '128.0334034', '27.4595152', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23656, '33011701001310701374', '33011701', '中河路上仓桥路1374', 2, NULL, 80, 0, NULL, 'OFF', '128.6919926', '27.1209479', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23657, '33011701001310701375', '33011701', '西湖大道建国路1375', 2, NULL, 80, 0, NULL, 'OFF', '124.3597534', '28.2261942', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23658, '33011701001310701376', '33011701', '平海路中河路1376', 2, NULL, 80, 0, NULL, 'OFF', '120.7227897', '34.7681274', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23659, '33011701001310701377', '33011701', '平海路延安路1377', 2, NULL, 80, 0, NULL, 'OFF', '115.5346889', '34.1620548', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23660, '33011701001310701378', '33011701', '解放路浣纱路1378', 2, NULL, 80, 0, NULL, 'OFF', '120.5050761', '31.5058922', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23661, '33011701001310701379', '33011701', '解放路建国路1379', 2, NULL, 80, 0, NULL, 'OFF', '120.9213145', '30.0432968', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23662, '33011701001310701380', '33011701', '钱江三桥秋涛路1380', 2, NULL, 80, 0, NULL, 'OFF', '128.0913446', '30.6988078', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23663, '33011701001310701381', '', 'testDevice1381', NULL, NULL, 80, 0, NULL, 'OFF', '122.6927801', '28.3641488', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23664, '33011701001310701382', '', 'dasda1382', NULL, NULL, 80, 0, NULL, '', '134.1898673', '34.724321', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23665, '33011701001310701383', '33011701', '吴山广场1383', 2, NULL, 80, 0, NULL, '', '127.8709968', '33.5291588', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23666, '33011701001310701384', '33011701', '中河路上仓桥路1384', 2, NULL, 80, 0, NULL, 'OFF', '121.7853828', '28.4728312', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23667, '33011701001310701385', '33011701', '西湖大道建国路1385', 2, NULL, 80, 0, NULL, 'OFF', '130.3139243', '26.77668', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23668, '33011701001310701386', '33011701', '平海路中河路1386', 2, NULL, 80, 0, NULL, 'OFF', '131.2134736', '33.4649069', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23669, '33011701001310701387', '33011701', '平海路延安路1387', 2, NULL, 80, 0, NULL, 'OFF', '130.1255957', '31.9944947', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23670, '33011701001310701388', '33011701', '解放路浣纱路1388', 2, NULL, 80, 0, NULL, 'OFF', '121.9875582', '34.5777533', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23671, '33011701001310701389', '33011701', '解放路建国路1389', 2, NULL, 80, 0, NULL, 'OFF', '124.5610045', '31.9052824', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23672, '33011701001310701390', '33011701', '钱江三桥秋涛路1390', 2, NULL, 80, 0, NULL, 'OFF', '121.8423487', '30.7931524', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23673, '33011701001310701391', '', 'testDevice1391', NULL, NULL, 80, 0, NULL, 'OFF', '120.5287308', '33.2499152', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23674, '33011701001310701392', '', 'dasda1392', NULL, NULL, 80, 0, NULL, '', '122.1166081', '28.8701189', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23675, '33011701001310701393', '33011701', '吴山广场1393', 2, NULL, 80, 0, NULL, '', '133.9968491', '29.6008494', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23676, '33011701001310701394', '33011701', '中河路上仓桥路1394', 2, NULL, 80, 0, NULL, 'OFF', '128.6344215', '26.3938906', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23677, '33011701001310701395', '33011701', '西湖大道建国路1395', 2, NULL, 80, 0, NULL, 'OFF', '126.1815609', '28.1669053', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23678, '33011701001310701396', '33011701', '平海路中河路1396', 2, NULL, 80, 0, NULL, 'OFF', '130.0045405', '26.6528547', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23679, '33011701001310701397', '33011701', '平海路延安路1397', 2, NULL, 80, 0, NULL, 'OFF', '116.4780203', '33.7635582', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23680, '33011701001310701398', '33011701', '解放路浣纱路1398', 2, NULL, 80, 0, NULL, 'OFF', '117.3764808', '33.859227', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23681, '33011701001310701399', '33011701', '解放路建国路1399', 2, NULL, 80, 0, NULL, 'OFF', '122.4483437', '33.0054608', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23682, '33011701001310701400', '33011701', '钱江三桥秋涛路1400', 2, NULL, 80, 0, NULL, 'OFF', '125.1122765', '28.4496233', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23683, '33011701001310701401', '', 'testDevice1401', NULL, NULL, 80, 0, NULL, 'OFF', '123.2163523', '28.2317344', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23684, '33011701001310701402', '', 'dasda1402', NULL, NULL, 80, 0, NULL, '', '125.7449325', '30.8098025', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23685, '33011701001310701403', '33011701', '吴山广场1403', 2, NULL, 80, 0, NULL, '', '124.0756063', '34.3538097', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23686, '33011701001310701404', '33011701', '中河路上仓桥路1404', 2, NULL, 80, 0, NULL, 'OFF', '128.1432347', '34.3396412', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23687, '33011701001310701405', '33011701', '西湖大道建国路1405', 2, NULL, 80, 0, NULL, 'OFF', '133.4893553', '33.6367774', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23688, '33011701001310701406', '33011701', '平海路中河路1406', 2, NULL, 80, 0, NULL, 'OFF', '128.017073', '30.1649634', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23689, '33011701001310701407', '33011701', '平海路延安路1407', 2, NULL, 80, 0, NULL, 'OFF', '124.6172997', '34.9144854', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23690, '33011701001310701408', '33011701', '解放路浣纱路1408', 2, NULL, 80, 0, NULL, 'OFF', '124.0352803', '29.0775372', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23691, '33011701001310701409', '33011701', '解放路建国路1409', 2, NULL, 80, 0, NULL, 'OFF', '131.3245027', '25.6442298', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23692, '33011701001310701410', '33011701', '钱江三桥秋涛路1410', 2, NULL, 80, 0, NULL, 'OFF', '129.5166733', '25.9885377', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23693, '33011701001310701411', '', 'testDevice1411', NULL, NULL, 80, 0, NULL, 'OFF', '118.609859', '28.0099995', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23694, '33011701001310701412', '', 'dasda1412', NULL, NULL, 80, 0, NULL, '', '129.4992756', '27.0843846', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23695, '33011701001310701604', '33011701', '吴山广场1604', 2, NULL, 80, 0, NULL, '', '116.6668019', '26.391925', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23696, '33011701001310701605', '33011701', '中河路上仓桥路1605', 2, NULL, 80, 0, NULL, 'OFF', '119.8361831', '25.7064712', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23697, '33011701001310701606', '33011701', '西湖大道建国路1606', 2, NULL, 80, 0, NULL, 'OFF', '134.1805106', '34.3565816', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23698, '33011701001310701607', '33011701', '平海路中河路1607', 2, NULL, 80, 0, NULL, 'OFF', '116.3940044', '29.6634946', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23699, '33011701001310701608', '33011701', '平海路延安路1608', 2, NULL, 80, 0, NULL, 'OFF', '124.4284907', '30.2477287', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23700, '33011701001310701609', '33011701', '解放路浣纱路1609', 2, NULL, 80, 0, NULL, 'OFF', '117.9604408', '27.2481597', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23701, '33011701001310701610', '33011701', '解放路建国路1610', 2, NULL, 80, 0, NULL, 'OFF', '121.5167325', '30.4976129', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23702, '33011701001310701611', '33011701', '钱江三桥秋涛路1611', 2, NULL, 80, 0, NULL, 'OFF', '118.7023408', '25.7435856', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23703, '33011701001310701612', '', 'testDevice1612', NULL, NULL, 80, 0, NULL, 'OFF', '133.9615071', '32.2250898', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23704, '33011701001310701613', '', 'dasda1613', NULL, NULL, 80, 0, NULL, '', '118.7005137', '28.8946923', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23705, '33011701001310701614', '33011701', '吴山广场1614', 2, NULL, 80, 0, NULL, '', '116.6180479', '32.7981925', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23706, '33011701001310701615', '33011701', '中河路上仓桥路1615', 2, NULL, 80, 0, NULL, 'OFF', '131.9886989', '32.306886', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23707, '33011701001310701616', '33011701', '西湖大道建国路1616', 2, NULL, 80, 0, NULL, 'OFF', '115.0893513', '28.1398526', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23708, '33011701001310701617', '33011701', '平海路中河路1617', 2, NULL, 80, 0, NULL, 'OFF', '124.4806607', '28.7786054', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23709, '33011701001310701618', '33011701', '平海路延安路1618', 2, NULL, 80, 0, NULL, 'OFF', '122.1352502', '34.4734694', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23710, '33011701001310701619', '33011701', '解放路浣纱路1619', 2, NULL, 80, 0, NULL, 'OFF', '122.2342696', '31.0315313', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23711, '33011701001310701620', '33011701', '解放路建国路1620', 2, NULL, 80, 0, NULL, 'OFF', '129.7655979', '26.7372485', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23712, '33011701001310701621', '33011701', '钱江三桥秋涛路1621', 2, NULL, 80, 0, NULL, 'OFF', '127.1251815', '25.5916488', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23713, '33011701001310701622', '', 'testDevice1622', NULL, NULL, 80, 0, NULL, 'OFF', '131.3291144', '32.7464989', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23714, '33011701001310701623', '', 'dasda1623', NULL, NULL, 80, 0, NULL, '', '120.2700281', '31.9575484', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23715, '33011701001310701624', '33011701', '吴山广场1624', 2, NULL, 80, 0, NULL, '', '132.3627979', '26.5482454', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23716, '33011701001310701625', '33011701', '中河路上仓桥路1625', 2, NULL, 80, 0, NULL, 'OFF', '126.0039057', '31.8685824', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23717, '33011701001310701626', '33011701', '西湖大道建国路1626', 2, NULL, 80, 0, NULL, 'OFF', '117.9311354', '34.6981761', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23718, '33011701001310701627', '33011701', '平海路中河路1627', 2, NULL, 80, 0, NULL, 'OFF', '116.6439605', '32.8851335', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23719, '33011701001310701628', '33011701', '平海路延安路1628', 2, NULL, 80, 0, NULL, 'OFF', '134.4263968', '25.3311396', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23720, '33011701001310701629', '33011701', '解放路浣纱路1629', 2, NULL, 80, 0, NULL, 'OFF', '127.2001032', '33.0002979', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23721, '33011701001310701630', '33011701', '解放路建国路1630', 2, NULL, 80, 0, NULL, 'OFF', '117.7213261', '34.0080707', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23722, '33011701001310701631', '33011701', '钱江三桥秋涛路1631', 2, NULL, 80, 0, NULL, 'OFF', '132.0063217', '26.0394603', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23723, '33011701001310701632', '', 'testDevice1632', NULL, NULL, 80, 0, NULL, 'OFF', '131.8676306', '33.1730896', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23724, '33011701001310701633', '', 'dasda1633', NULL, NULL, 80, 0, NULL, '', '128.3191885', '32.7470673', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23725, '33011701001310701634', '33011701', '吴山广场1634', 2, NULL, 80, 0, NULL, '', '130.9930514', '29.2160682', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23726, '33011701001310701635', '33011701', '中河路上仓桥路1635', 2, NULL, 80, 0, NULL, 'OFF', '115.0076921', '32.8391393', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23727, '33011701001310701636', '33011701', '西湖大道建国路1636', 2, NULL, 80, 0, NULL, 'OFF', '127.0593069', '31.547492', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23728, '33011701001310701637', '33011701', '平海路中河路1637', 2, NULL, 80, 0, NULL, 'OFF', '115.2734588', '34.2200427', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23729, '33011701001310701638', '33011701', '平海路延安路1638', 2, NULL, 80, 0, NULL, 'OFF', '120.1893738', '31.4577378', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23730, '33011701001310701639', '33011701', '解放路浣纱路1639', 2, NULL, 80, 0, NULL, 'OFF', '120.1264934', '29.628561', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23731, '33011701001310701640', '33011701', '解放路建国路1640', 2, NULL, 80, 0, NULL, 'OFF', '125.064346', '28.7695922', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23732, '33011701001310701641', '33011701', '钱江三桥秋涛路1641', 2, NULL, 80, 0, NULL, 'OFF', '129.9422507', '29.9622781', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23733, '33011701001310701642', '', 'testDevice1642', NULL, NULL, 80, 0, NULL, 'OFF', '119.5182159', '28.5026144', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23734, '33011701001310701643', '', 'dasda1643', NULL, NULL, 80, 0, NULL, '', '132.764328', '27.6262379', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23735, '33011701001310701644', '33011701', '吴山广场1644', 2, NULL, 80, 0, NULL, '', '130.266993', '27.6233468', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23736, '33011701001310701645', '33011701', '中河路上仓桥路1645', 2, NULL, 80, 0, NULL, 'OFF', '118.0419816', '30.2380205', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23737, '33011701001310701646', '33011701', '西湖大道建国路1646', 2, NULL, 80, 0, NULL, 'OFF', '124.4089297', '33.3200626', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23738, '33011701001310701647', '33011701', '平海路中河路1647', 2, NULL, 80, 0, NULL, 'OFF', '132.9187041', '30.8862514', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23739, '33011701001310701648', '33011701', '平海路延安路1648', 2, NULL, 80, 0, NULL, 'OFF', '116.3667319', '29.4710699', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23740, '33011701001310701649', '33011701', '解放路浣纱路1649', 2, NULL, 80, 0, NULL, 'OFF', '128.0775481', '29.6965954', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23741, '33011701001310701650', '33011701', '解放路建国路1650', 2, NULL, 80, 0, NULL, 'OFF', '116.2875454', '25.0697678', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23742, '33011701001310701651', '33011701', '钱江三桥秋涛路1651', 2, NULL, 80, 0, NULL, 'OFF', '122.2050833', '31.2590531', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23743, '33011701001310701652', '', 'testDevice1652', NULL, NULL, 80, 0, NULL, 'OFF', '127.162781', '26.0859624', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23744, '33011701001310701653', '', 'dasda1653', NULL, NULL, 80, 0, NULL, '', '134.1986555', '31.6526528', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23745, '33011701001310701654', '33011701', '吴山广场1654', 2, NULL, 80, 0, NULL, '', '134.5049354', '25.0053771', '330102', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23746, '33011701001310701655', '33011701', '中河路上仓桥路1655', 2, NULL, 80, 0, NULL, 'OFF', '134.9287109', '25.0689276', '330105', '192.168.1.2', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23747, '33011701001310701656', '33011701', '西湖大道建国路1656', 2, NULL, 80, 0, NULL, 'OFF', '116.1287491', '25.3285069', '330104', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23748, '33011701001310701657', '33011701', '平海路中河路1657', 2, NULL, 80, 0, NULL, 'OFF', '120.8576133', '26.4357519', '330106', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23749, '33011701001310701658', '33011701', '平海路延安路1658', 2, NULL, 80, 0, NULL, 'OFF', '120.9018197', '31.1932391', '330108', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23750, '33011701001310701659', '33011701', '解放路浣纱路1659', 2, NULL, 80, 0, NULL, 'OFF', '126.9362591', '31.6589402', '330109', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23751, '33011701001310701660', '33011701', '解放路建国路1660', 2, NULL, 80, 0, NULL, 'OFF', '116.9758373', '29.714984', '330110', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23752, '33011701001310701661', '33011701', '钱江三桥秋涛路1661', 2, NULL, 80, 0, NULL, 'OFF', '129.0704097', '28.5980998', '330111', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23753, '33011701001310701662', '', 'testDevice1662', NULL, NULL, 80, 0, NULL, 'OFF', '119.4245372', '28.8455474', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23754, '33011701001310701663', '', 'dasda1663', NULL, NULL, 80, 0, NULL, '', '134.9114576', '33.4334378', '', '192.168.1.1', 0, '2019-06-17 10:30:00', '2019-06-17 10:30:00');
INSERT INTO `vi_device_copy` VALUES (23755, '33011701001310701664', '33011701', '吴山广场1664', 2, NULL, 80, 0, NULL, '', '121.283677', '25.6305472', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23756, '33011701001310701665', '33011701', '中河路上仓桥路1665', 2, NULL, 80, 0, NULL, 'OFF', '126.684013', '32.8524226', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23757, '33011701001310701666', '33011701', '西湖大道建国路1666', 2, NULL, 80, 0, NULL, 'OFF', '134.5690343', '32.370472', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23758, '33011701001310701667', '33011701', '平海路中河路1667', 2, NULL, 80, 0, NULL, 'OFF', '117.7931333', '28.2950926', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23759, '33011701001310701668', '33011701', '平海路延安路1668', 2, NULL, 80, 0, NULL, 'OFF', '130.2585642', '29.3640472', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23760, '33011701001310701669', '33011701', '解放路浣纱路1669', 2, NULL, 80, 0, NULL, 'OFF', '122.9134215', '26.9349587', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23761, '33011701001310701670', '33011701', '解放路建国路1670', 2, NULL, 80, 0, NULL, 'OFF', '128.7914158', '31.5826519', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23762, '33011701001310701671', '33011701', '钱江三桥秋涛路1671', 2, NULL, 80, 0, NULL, 'OFF', '120.2168149', '32.108384', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23763, '33011701001310701672', '', 'testDevice1672', NULL, NULL, 80, 0, NULL, 'OFF', '119.7098279', '30.7939644', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23764, '33011701001310701673', '', 'dasda1673', NULL, NULL, 80, 0, NULL, '', '122.8986952', '32.6446703', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23765, '33011701001310701674', '33011701', '吴山广场1674', 2, NULL, 80, 0, NULL, '', '120.363993', '25.8414586', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23766, '33011701001310701675', '33011701', '中河路上仓桥路1675', 2, NULL, 80, 0, NULL, 'OFF', '118.12388', '26.2732826', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23767, '33011701001310701676', '33011701', '西湖大道建国路1676', 2, NULL, 80, 0, NULL, 'OFF', '134.5274217', '28.8420374', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23768, '33011701001310701677', '33011701', '平海路中河路1677', 2, NULL, 80, 0, NULL, 'OFF', '123.265469', '30.3903395', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23769, '33011701001310701678', '33011701', '平海路延安路1678', 2, NULL, 80, 0, NULL, 'OFF', '117.7450806', '30.4255856', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23770, '33011701001310701679', '33011701', '解放路浣纱路1679', 2, NULL, 80, 0, NULL, 'OFF', '123.9289966', '25.9569098', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23771, '33011701001310701680', '33011701', '解放路建国路1680', 2, NULL, 80, 0, NULL, 'OFF', '131.4097416', '33.5077927', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23772, '33011701001310701681', '33011701', '钱江三桥秋涛路1681', 2, NULL, 80, 0, NULL, 'OFF', '130.261719', '34.6682343', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23773, '33011701001310701682', '', 'testDevice1682', NULL, NULL, 80, 0, NULL, 'OFF', '122.0793709', '27.8177937', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23774, '33011701001310701683', '', 'dasda1683', NULL, NULL, 80, 0, NULL, '', '124.6116982', '30.084266', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23775, '33011701001310701684', '33011701', '吴山广场1684', 2, NULL, 80, 0, NULL, '', '121.8203789', '31.9679492', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23776, '33011701001310701685', '33011701', '中河路上仓桥路1685', 2, NULL, 80, 0, NULL, 'OFF', '120.2668002', '34.5869481', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23777, '33011701001310701686', '33011701', '西湖大道建国路1686', 2, NULL, 80, 0, NULL, 'OFF', '120.8728652', '32.0308934', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23778, '33011701001310701687', '33011701', '平海路中河路1687', 2, NULL, 80, 0, NULL, 'OFF', '128.563926', '31.393623', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23779, '33011701001310701688', '33011701', '平海路延安路1688', 2, NULL, 80, 0, NULL, 'OFF', '125.2010349', '25.8754349', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23780, '33011701001310701689', '33011701', '解放路浣纱路1689', 2, NULL, 80, 0, NULL, 'OFF', '125.3133973', '30.1963061', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23781, '33011701001310701690', '33011701', '解放路建国路1690', 2, NULL, 80, 0, NULL, 'OFF', '115.9638822', '28.3552258', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23782, '33011701001310701691', '33011701', '钱江三桥秋涛路1691', 2, NULL, 80, 0, NULL, 'OFF', '128.87922', '26.1872114', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23783, '33011701001310701692', '', 'testDevice1692', NULL, NULL, 80, 0, NULL, 'OFF', '121.5044539', '30.8703795', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23784, '33011701001310701693', '', 'dasda1693', NULL, NULL, 80, 0, NULL, '', '125.8846099', '30.7902638', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23785, '33011701001310701694', '33011701', '吴山广场1694', 2, NULL, 80, 0, NULL, '', '129.9096886', '26.3401809', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23786, '33011701001310701695', '33011701', '中河路上仓桥路1695', 2, NULL, 80, 0, NULL, 'OFF', '116.8946137', '34.3301132', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23787, '33011701001310701696', '33011701', '西湖大道建国路1696', 2, NULL, 80, 0, NULL, 'OFF', '119.7440036', '27.6300237', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23788, '33011701001310701697', '33011701', '平海路中河路1697', 2, NULL, 80, 0, NULL, 'OFF', '133.0361773', '30.1597791', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23789, '33011701001310701698', '33011701', '平海路延安路1698', 2, NULL, 80, 0, NULL, 'OFF', '130.9488763', '32.9088247', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23790, '33011701001310701699', '33011701', '解放路浣纱路1699', 2, NULL, 80, 0, NULL, 'OFF', '120.6358501', '29.0647864', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23791, '33011701001310701700', '33011701', '解放路建国路1700', 2, NULL, 80, 0, NULL, 'OFF', '115.3326222', '31.5974585', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23792, '33011701001310701701', '33011701', '钱江三桥秋涛路1701', 2, NULL, 80, 0, NULL, 'OFF', '119.7555613', '25.7929334', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23793, '33011701001310701702', '', 'testDevice1702', NULL, NULL, 80, 0, NULL, 'OFF', '117.7799406', '29.1722919', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23794, '33011701001310701703', '', 'dasda1703', NULL, NULL, 80, 0, NULL, '', '134.6330197', '33.4826597', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23795, '33011701001310701704', '33011701', '吴山广场1704', 2, NULL, 80, 0, NULL, '', '124.8252772', '34.896423', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23796, '33011701001310701705', '33011701', '中河路上仓桥路1705', 2, NULL, 80, 0, NULL, 'OFF', '125.2273277', '29.0341363', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23797, '33011701001310701706', '33011701', '西湖大道建国路1706', 2, NULL, 80, 0, NULL, 'OFF', '116.6608075', '25.4814129', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23798, '33011701001310701707', '33011701', '平海路中河路1707', 2, NULL, 80, 0, NULL, 'OFF', '132.6220549', '25.3046558', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23799, '33011701001310701708', '33011701', '平海路延安路1708', 2, NULL, 80, 0, NULL, 'OFF', '118.1278528', '25.0790406', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23800, '33011701001310701709', '33011701', '解放路浣纱路1709', 2, NULL, 80, 0, NULL, 'OFF', '117.7730997', '34.4812357', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23801, '33011701001310701710', '33011701', '解放路建国路1710', 2, NULL, 80, 0, NULL, 'OFF', '119.4819409', '32.1690573', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23802, '33011701001310701711', '33011701', '钱江三桥秋涛路1711', 2, NULL, 80, 0, NULL, 'OFF', '129.0904057', '32.4015794', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23803, '33011701001310701712', '', 'testDevice1712', NULL, NULL, 80, 0, NULL, 'OFF', '132.0062067', '30.5007257', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23804, '33011701001310701713', '', 'dasda1713', NULL, NULL, 80, 0, NULL, '', '117.7598171', '30.2988904', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23805, '33011701001310701714', '33011701', '吴山广场1714', 2, NULL, 80, 0, NULL, '', '117.7804658', '34.9922754', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23806, '33011701001310701715', '33011701', '中河路上仓桥路1715', 2, NULL, 80, 0, NULL, 'OFF', '120.6228784', '29.0647059', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23807, '33011701001310701716', '33011701', '西湖大道建国路1716', 2, NULL, 80, 0, NULL, 'OFF', '134.7729952', '25.3467036', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23808, '33011701001310701717', '33011701', '平海路中河路1717', 2, NULL, 80, 0, NULL, 'OFF', '116.9963414', '34.5394009', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23809, '33011701001310701718', '33011701', '平海路延安路1718', 2, NULL, 80, 0, NULL, 'OFF', '125.6627219', '31.6568936', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23810, '33011701001310701719', '33011701', '解放路浣纱路1719', 2, NULL, 80, 0, NULL, 'OFF', '122.324586', '29.6662659', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23811, '33011701001310701720', '33011701', '解放路建国路1720', 2, NULL, 80, 0, NULL, 'OFF', '119.6347647', '28.3606491', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23812, '33011701001310701721', '33011701', '钱江三桥秋涛路1721', 2, NULL, 80, 0, NULL, 'OFF', '116.2000664', '27.804448', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23813, '33011701001310701722', '', 'testDevice1722', NULL, NULL, 80, 0, NULL, 'OFF', '127.0960386', '28.9402931', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23814, '33011701001310701723', '', 'dasda1723', NULL, NULL, 80, 0, NULL, '', '131.8799941', '26.2881217', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23815, '33011701001310701724', '33011701', '吴山广场1724', 2, NULL, 80, 0, NULL, '', '123.1118556', '29.6197294', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23816, '33011701001310701725', '33011701', '中河路上仓桥路1725', 2, NULL, 80, 0, NULL, 'OFF', '124.9192963', '34.2342823', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23817, '33011701001310701726', '33011701', '西湖大道建国路1726', 2, NULL, 80, 0, NULL, 'OFF', '120.2609152', '27.3122236', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23818, '33011701001310701727', '33011701', '平海路中河路1727', 2, NULL, 80, 0, NULL, 'OFF', '131.5466878', '28.8582716', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23819, '33011701001310701728', '33011701', '平海路延安路1728', 2, NULL, 80, 0, NULL, 'OFF', '121.9506938', '27.3546874', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23820, '33011701001310701729', '33011701', '解放路浣纱路1729', 2, NULL, 80, 0, NULL, 'OFF', '120.1134064', '25.1986224', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23821, '33011701001310701730', '33011701', '解放路建国路1730', 2, NULL, 80, 0, NULL, 'OFF', '119.7149511', '28.92905', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23822, '33011701001310701731', '33011701', '钱江三桥秋涛路1731', 2, NULL, 80, 0, NULL, 'OFF', '123.2345368', '34.0493833', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23823, '33011701001310701732', '', 'testDevice1732', NULL, NULL, 80, 0, NULL, 'OFF', '122.0278313', '28.4597668', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23824, '33011701001310701733', '', 'dasda1733', NULL, NULL, 80, 0, NULL, '', '125.4355468', '25.1506843', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23825, '33011701001310701734', '33011701', '吴山广场1734', 2, NULL, 80, 0, NULL, '', '126.0942405', '25.3741216', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23826, '33011701001310701735', '33011701', '中河路上仓桥路1735', 2, NULL, 80, 0, NULL, 'OFF', '119.164563', '26.4185553', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23827, '33011701001310701736', '33011701', '西湖大道建国路1736', 2, NULL, 80, 0, NULL, 'OFF', '122.5400942', '30.9704121', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23828, '33011701001310701737', '33011701', '平海路中河路1737', 2, NULL, 80, 0, NULL, 'OFF', '120.2067823', '30.5963948', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23829, '33011701001310701738', '33011701', '平海路延安路1738', 2, NULL, 80, 0, NULL, 'OFF', '118.4136296', '25.0707379', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23830, '33011701001310701739', '33011701', '解放路浣纱路1739', 2, NULL, 80, 0, NULL, 'OFF', '116.4478016', '28.5645053', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23831, '33011701001310701740', '33011701', '解放路建国路1740', 2, NULL, 80, 0, NULL, 'OFF', '131.99812', '32.6103134', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23832, '33011701001310701741', '33011701', '钱江三桥秋涛路1741', 2, NULL, 80, 0, NULL, 'OFF', '115.6471959', '32.3580514', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23833, '33011701001310701742', '', 'testDevice1742', NULL, NULL, 80, 0, NULL, 'OFF', '127.2416202', '28.9593169', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23834, '33011701001310701743', '', 'dasda1743', NULL, NULL, 80, 0, NULL, '', '134.2665136', '32.7224307', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23835, '33011701001310701744', '33011701', '吴山广场1744', 2, NULL, 80, 0, NULL, '', '134.6077081', '31.7342032', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23836, '33011701001310701745', '33011701', '中河路上仓桥路1745', 2, NULL, 80, 0, NULL, 'OFF', '115.2390003', '25.5037241', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23837, '33011701001310701746', '33011701', '西湖大道建国路1746', 2, NULL, 80, 0, NULL, 'OFF', '117.3718777', '27.3160114', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23838, '33011701001310701747', '33011701', '平海路中河路1747', 2, NULL, 80, 0, NULL, 'OFF', '126.1423883', '25.068885', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23839, '33011701001310701748', '33011701', '平海路延安路1748', 2, NULL, 80, 0, NULL, 'OFF', '123.596309', '28.3963911', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23840, '33011701001310701749', '33011701', '解放路浣纱路1749', 2, NULL, 80, 0, NULL, 'OFF', '124.5543807', '31.7753006', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23841, '33011701001310701750', '33011701', '解放路建国路1750', 2, NULL, 80, 0, NULL, 'OFF', '116.9829773', '28.6873299', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23842, '33011701001310701751', '33011701', '钱江三桥秋涛路1751', 2, NULL, 80, 0, NULL, 'OFF', '116.2517451', '33.1107483', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23843, '33011701001310701752', '', 'testDevice1752', NULL, NULL, 80, 0, NULL, 'OFF', '115.3097939', '34.4917519', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23844, '33011701001310701753', '', 'dasda1753', NULL, NULL, 80, 0, NULL, '', '132.7937349', '28.1265152', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23845, '33011701001310701754', '33011701', '吴山广场1754', 2, NULL, 80, 0, NULL, '', '123.0392935', '32.1573204', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23846, '33011701001310701755', '33011701', '中河路上仓桥路1755', 2, NULL, 80, 0, NULL, 'OFF', '121.8152633', '31.4070568', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23847, '33011701001310701756', '33011701', '西湖大道建国路1756', 2, NULL, 80, 0, NULL, 'OFF', '124.9584367', '25.5633231', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23848, '33011701001310701757', '33011701', '平海路中河路1757', 2, NULL, 80, 0, NULL, 'OFF', '124.346394', '28.5954455', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23849, '33011701001310701758', '33011701', '平海路延安路1758', 2, NULL, 80, 0, NULL, 'OFF', '131.8566608', '31.2872582', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23850, '33011701001310701759', '33011701', '解放路浣纱路1759', 2, NULL, 80, 0, NULL, 'OFF', '131.2441227', '25.6499552', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23851, '33011701001310701760', '33011701', '解放路建国路1760', 2, NULL, 80, 0, NULL, 'OFF', '125.6506315', '29.3880014', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23852, '33011701001310701761', '33011701', '钱江三桥秋涛路1761', 2, NULL, 80, 0, NULL, 'OFF', '119.5207901', '34.9901417', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23853, '33011701001310701762', '', 'testDevice1762', NULL, NULL, 80, 0, NULL, 'OFF', '125.6520564', '31.7867048', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23854, '33011701001310701763', '', 'dasda1763', NULL, NULL, 80, 0, NULL, '', '134.6979125', '28.9630989', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23855, '33011701001310701764', '33011701', '吴山广场1764', 2, NULL, 80, 0, NULL, '', '121.5333941', '34.4553808', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23856, '33011701001310701765', '33011701', '中河路上仓桥路1765', 2, NULL, 80, 0, NULL, 'OFF', '128.5732333', '30.3876073', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23857, '33011701001310701766', '33011701', '西湖大道建国路1766', 2, NULL, 80, 0, NULL, 'OFF', '123.2659847', '33.5718947', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23858, '33011701001310701767', '33011701', '平海路中河路1767', 2, NULL, 80, 0, NULL, 'OFF', '115.6102245', '31.6966516', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23859, '33011701001310701768', '33011701', '平海路延安路1768', 2, NULL, 80, 0, NULL, 'OFF', '133.2531691', '32.7675742', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23860, '33011701001310701769', '33011701', '解放路浣纱路1769', 2, NULL, 80, 0, NULL, 'OFF', '124.4351723', '33.7479165', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23861, '33011701001310701770', '33011701', '解放路建国路1770', 2, NULL, 80, 0, NULL, 'OFF', '127.416355', '25.4368603', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23862, '33011701001310701771', '33011701', '钱江三桥秋涛路1771', 2, NULL, 80, 0, NULL, 'OFF', '128.7762587', '30.9405524', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23863, '33011701001310701772', '', 'testDevice1772', NULL, NULL, 80, 0, NULL, 'OFF', '126.6322291', '33.3921811', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23864, '33011701001310701773', '', 'dasda1773', NULL, NULL, 80, 0, NULL, '', '131.8323702', '29.1392488', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23865, '33011701001310701774', '33011701', '吴山广场1774', 2, NULL, 80, 0, NULL, '', '124.2651641', '30.5197011', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23866, '33011701001310701775', '33011701', '中河路上仓桥路1775', 2, NULL, 80, 0, NULL, 'OFF', '130.8287105', '30.1807595', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23867, '33011701001310701776', '33011701', '西湖大道建国路1776', 2, NULL, 80, 0, NULL, 'OFF', '126.348061', '34.3446943', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23868, '33011701001310701777', '33011701', '平海路中河路1777', 2, NULL, 80, 0, NULL, 'OFF', '124.254174', '26.1811934', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23869, '33011701001310701778', '33011701', '平海路延安路1778', 2, NULL, 80, 0, NULL, 'OFF', '127.2266876', '32.8718844', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23870, '33011701001310701779', '33011701', '解放路浣纱路1779', 2, NULL, 80, 0, NULL, 'OFF', '128.3709167', '30.815842', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23871, '33011701001310701780', '33011701', '解放路建国路1780', 2, NULL, 80, 0, NULL, 'OFF', '125.1745215', '30.463557', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23872, '33011701001310701781', '33011701', '钱江三桥秋涛路1781', 2, NULL, 80, 0, NULL, 'OFF', '125.7598577', '34.8702593', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23873, '33011701001310701782', '', 'testDevice1782', NULL, NULL, 80, 0, NULL, 'OFF', '118.2757246', '27.9606259', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23874, '33011701001310701783', '', 'dasda1783', NULL, NULL, 80, 0, NULL, '', '119.0990507', '30.1923518', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23875, '33011701001310701784', '33011701', '吴山广场1784', 2, NULL, 80, 0, NULL, '', '125.6680804', '32.0798818', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23876, '33011701001310701785', '33011701', '中河路上仓桥路1785', 2, NULL, 80, 0, NULL, 'OFF', '116.0432506', '34.8223537', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23877, '33011701001310701786', '33011701', '西湖大道建国路1786', 2, NULL, 80, 0, NULL, 'OFF', '128.2120122', '32.8721235', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23878, '33011701001310701787', '33011701', '平海路中河路1787', 2, NULL, 80, 0, NULL, 'OFF', '117.9303098', '34.8935567', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23879, '33011701001310701788', '33011701', '平海路延安路1788', 2, NULL, 80, 0, NULL, 'OFF', '130.0155133', '30.8514134', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23880, '33011701001310701789', '33011701', '解放路浣纱路1789', 2, NULL, 80, 0, NULL, 'OFF', '121.2866375', '34.576397', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23881, '33011701001310701790', '33011701', '解放路建国路1790', 2, NULL, 80, 0, NULL, 'OFF', '121.386648', '25.3277451', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23882, '33011701001310701791', '33011701', '钱江三桥秋涛路1791', 2, NULL, 80, 0, NULL, 'OFF', '128.0733285', '27.909535', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23883, '33011701001310701792', '', 'testDevice1792', NULL, NULL, 80, 0, NULL, 'OFF', '121.206699', '28.5644397', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23884, '33011701001310701793', '', 'dasda1793', NULL, NULL, 80, 0, NULL, '', '126.81351', '34.0935942', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23885, '33011701001310701794', '33011701', '吴山广场1794', 2, NULL, 80, 0, NULL, '', '115.4474537', '29.7746519', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23886, '33011701001310701795', '33011701', '中河路上仓桥路1795', 2, NULL, 80, 0, NULL, 'OFF', '121.7967393', '31.5924772', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23887, '33011701001310701796', '33011701', '西湖大道建国路1796', 2, NULL, 80, 0, NULL, 'OFF', '127.6413358', '33.6384305', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23888, '33011701001310701797', '33011701', '平海路中河路1797', 2, NULL, 80, 0, NULL, 'OFF', '117.816462', '28.4147215', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23889, '33011701001310701798', '33011701', '平海路延安路1798', 2, NULL, 80, 0, NULL, 'OFF', '131.158303', '26.1583161', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23890, '33011701001310701799', '33011701', '解放路浣纱路1799', 2, NULL, 80, 0, NULL, 'OFF', '127.3421295', '30.5474164', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23891, '33011701001310701800', '33011701', '解放路建国路1800', 2, NULL, 80, 0, NULL, 'OFF', '128.2357392', '29.2621342', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23892, '33011701001310701801', '33011701', '钱江三桥秋涛路1801', 2, NULL, 80, 0, NULL, 'OFF', '124.1523081', '29.6684219', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23893, '33011701001310701802', '', 'testDevice1802', NULL, NULL, 80, 0, NULL, 'OFF', '121.0543234', '25.5557074', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23894, '33011701001310701803', '', 'dasda1803', NULL, NULL, 80, 0, NULL, '', '117.8146932', '33.7732715', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23895, '33011701001310701804', '33011701', '吴山广场1804', 2, NULL, 80, 0, NULL, '', '130.9104967', '27.1992357', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23896, '33011701001310701805', '33011701', '中河路上仓桥路1805', 2, NULL, 80, 0, NULL, 'OFF', '126.1084044', '29.6763641', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23897, '33011701001310701806', '33011701', '西湖大道建国路1806', 2, NULL, 80, 0, NULL, 'OFF', '122.8105324', '31.7841139', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23898, '33011701001310701807', '33011701', '平海路中河路1807', 2, NULL, 80, 0, NULL, 'OFF', '120.7274495', '34.8914775', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23899, '33011701001310701808', '33011701', '平海路延安路1808', 2, NULL, 80, 0, NULL, 'OFF', '120.205651', '34.1050461', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23900, '33011701001310701809', '33011701', '解放路浣纱路1809', 2, NULL, 80, 0, NULL, 'OFF', '123.8459069', '30.8507981', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23901, '33011701001310701810', '33011701', '解放路建国路1810', 2, NULL, 80, 0, NULL, 'OFF', '123.6125823', '26.9388526', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23902, '33011701001310701811', '33011701', '钱江三桥秋涛路1811', 2, NULL, 80, 0, NULL, 'OFF', '131.5251914', '27.1418691', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23903, '33011701001310701812', '', 'testDevice1812', NULL, NULL, 80, 0, NULL, 'OFF', '131.7882105', '29.8927878', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23904, '33011701001310701813', '', 'dasda1813', NULL, NULL, 80, 0, NULL, '', '129.3654792', '33.0383319', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23905, '33011701001310701814', '33011701', '吴山广场1814', 2, NULL, 80, 0, NULL, '', '116.4627649', '30.5132966', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23906, '33011701001310701815', '33011701', '中河路上仓桥路1815', 2, NULL, 80, 0, NULL, 'OFF', '119.2173876', '28.4514876', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23907, '33011701001310701816', '33011701', '西湖大道建国路1816', 2, NULL, 80, 0, NULL, 'OFF', '131.698644', '25.7175485', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23908, '33011701001310701817', '33011701', '平海路中河路1817', 2, NULL, 80, 0, NULL, 'OFF', '125.8410579', '28.2332802', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23909, '33011701001310701818', '33011701', '平海路延安路1818', 2, NULL, 80, 0, NULL, 'OFF', '119.1093579', '29.0137556', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23910, '33011701001310701819', '33011701', '解放路浣纱路1819', 2, NULL, 80, 0, NULL, 'OFF', '123.0236164', '25.3689378', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23911, '33011701001310701820', '33011701', '解放路建国路1820', 2, NULL, 80, 0, NULL, 'OFF', '122.790009', '34.8034224', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23912, '33011701001310701821', '33011701', '钱江三桥秋涛路1821', 2, NULL, 80, 0, NULL, 'OFF', '129.8791963', '32.9102988', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23913, '33011701001310701822', '', 'testDevice1822', NULL, NULL, 80, 0, NULL, 'OFF', '126.0259552', '25.1412274', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23914, '33011701001310701823', '', 'dasda1823', NULL, NULL, 80, 0, NULL, '', '125.4921876', '31.9752408', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23915, '33011701001310701824', '33011701', '吴山广场1824', 2, NULL, 80, 0, NULL, '', '134.3830732', '29.4525221', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23916, '33011701001310701825', '33011701', '中河路上仓桥路1825', 2, NULL, 80, 0, NULL, 'OFF', '120.4388039', '26.3368883', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23917, '33011701001310701826', '33011701', '西湖大道建国路1826', 2, NULL, 80, 0, NULL, 'OFF', '124.0448005', '28.3268757', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23918, '33011701001310701827', '33011701', '平海路中河路1827', 2, NULL, 80, 0, NULL, 'OFF', '123.9075914', '27.623714', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23919, '33011701001310701828', '33011701', '平海路延安路1828', 2, NULL, 80, 0, NULL, 'OFF', '132.4035559', '28.137943', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23920, '33011701001310701829', '33011701', '解放路浣纱路1829', 2, NULL, 80, 0, NULL, 'OFF', '115.295006', '32.8185735', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23921, '33011701001310701830', '33011701', '解放路建国路1830', 2, NULL, 80, 0, NULL, 'OFF', '124.2643628', '34.6790387', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23922, '33011701001310701831', '33011701', '钱江三桥秋涛路1831', 2, NULL, 80, 0, NULL, 'OFF', '120.4367966', '29.9394733', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23923, '33011701001310701832', '', 'testDevice1832', NULL, NULL, 80, 0, NULL, 'OFF', '134.3908954', '30.6602508', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23924, '33011701001310701833', '', 'dasda1833', NULL, NULL, 80, 0, NULL, '', '115.6440878', '28.4828344', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23925, '33011701001310701834', '33011701', '吴山广场1834', 2, NULL, 80, 0, NULL, '', '120.0477532', '25.43342', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23926, '33011701001310701835', '33011701', '中河路上仓桥路1835', 2, NULL, 80, 0, NULL, 'OFF', '118.3065032', '26.7185969', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23927, '33011701001310701836', '33011701', '西湖大道建国路1836', 2, NULL, 80, 0, NULL, 'OFF', '116.3892573', '32.2927247', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23928, '33011701001310701837', '33011701', '平海路中河路1837', 2, NULL, 80, 0, NULL, 'OFF', '132.0267774', '26.3078332', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23929, '33011701001310701838', '33011701', '平海路延安路1838', 2, NULL, 80, 0, NULL, 'OFF', '115.9661155', '29.6609923', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23930, '33011701001310701839', '33011701', '解放路浣纱路1839', 2, NULL, 80, 0, NULL, 'OFF', '128.7502461', '34.3814623', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23931, '33011701001310701840', '33011701', '解放路建国路1840', 2, NULL, 80, 0, NULL, 'OFF', '120.8528848', '27.9243349', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23932, '33011701001310701841', '33011701', '钱江三桥秋涛路1841', 2, NULL, 80, 0, NULL, 'OFF', '123.013686', '31.4772879', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23933, '33011701001310701842', '', 'testDevice1842', NULL, NULL, 80, 0, NULL, 'OFF', '117.5097764', '28.6134352', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23934, '33011701001310701843', '', 'dasda1843', NULL, NULL, 80, 0, NULL, '', '123.5078247', '33.6353127', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23935, '33011701001310701844', '33011701', '吴山广场1844', 2, NULL, 80, 0, NULL, '', '130.0097948', '27.336258', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23936, '33011701001310701845', '33011701', '中河路上仓桥路1845', 2, NULL, 80, 0, NULL, 'OFF', '124.5255006', '30.7753522', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23937, '33011701001310701846', '33011701', '西湖大道建国路1846', 2, NULL, 80, 0, NULL, 'OFF', '117.598119', '26.8679876', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23938, '33011701001310701847', '33011701', '平海路中河路1847', 2, NULL, 80, 0, NULL, 'OFF', '119.414094', '27.0138813', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23939, '33011701001310701848', '33011701', '平海路延安路1848', 2, NULL, 80, 0, NULL, 'OFF', '129.2761134', '29.4654443', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23940, '33011701001310701849', '33011701', '解放路浣纱路1849', 2, NULL, 80, 0, NULL, 'OFF', '133.1382858', '31.2855778', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23941, '33011701001310701850', '33011701', '解放路建国路1850', 2, NULL, 80, 0, NULL, 'OFF', '122.8630893', '33.0315564', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23942, '33011701001310701851', '33011701', '钱江三桥秋涛路1851', 2, NULL, 80, 0, NULL, 'OFF', '119.9005897', '26.3010491', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23943, '33011701001310701852', '', 'testDevice1852', NULL, NULL, 80, 0, NULL, 'OFF', '115.9136812', '27.4105764', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23944, '33011701001310701853', '', 'dasda1853', NULL, NULL, 80, 0, NULL, '', '124.8666375', '33.149735', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23945, '33011701001310701854', '33011701', '吴山广场1854', 2, NULL, 80, 0, NULL, '', '121.5921445', '28.5169463', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23946, '33011701001310701855', '33011701', '中河路上仓桥路1855', 2, NULL, 80, 0, NULL, 'OFF', '118.3608106', '28.1355267', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23947, '33011701001310701856', '33011701', '西湖大道建国路1856', 2, NULL, 80, 0, NULL, 'OFF', '132.0276201', '30.1267947', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23948, '33011701001310701857', '33011701', '平海路中河路1857', 2, NULL, 80, 0, NULL, 'OFF', '130.0556696', '31.227394', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23949, '33011701001310701858', '33011701', '平海路延安路1858', 2, NULL, 80, 0, NULL, 'OFF', '119.195488', '30.756586', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23950, '33011701001310701859', '33011701', '解放路浣纱路1859', 2, NULL, 80, 0, NULL, 'OFF', '130.8104318', '25.1007486', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23951, '33011701001310701860', '33011701', '解放路建国路1860', 2, NULL, 80, 0, NULL, 'OFF', '121.4656956', '28.233985', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23952, '33011701001310701861', '33011701', '钱江三桥秋涛路1861', 2, NULL, 80, 0, NULL, 'OFF', '119.8971833', '30.8676795', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23953, '33011701001310701862', '', 'testDevice1862', NULL, NULL, 80, 0, NULL, 'OFF', '120.0888302', '34.6364429', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23954, '33011701001310701863', '', 'dasda1863', NULL, NULL, 80, 0, NULL, '', '125.7526018', '25.5791762', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23955, '33011701001310701864', '33011701', '吴山广场1864', 2, NULL, 80, 0, NULL, '', '133.496519', '28.9865526', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23956, '33011701001310701865', '33011701', '中河路上仓桥路1865', 2, NULL, 80, 0, NULL, 'OFF', '115.2247903', '33.1952349', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23957, '33011701001310701866', '33011701', '西湖大道建国路1866', 2, NULL, 80, 0, NULL, 'OFF', '120.6343949', '34.0165171', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23958, '33011701001310701867', '33011701', '平海路中河路1867', 2, NULL, 80, 0, NULL, 'OFF', '122.4976042', '25.4968808', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23959, '33011701001310701868', '33011701', '平海路延安路1868', 2, NULL, 80, 0, NULL, 'OFF', '115.584837', '30.4348532', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23960, '33011701001310701869', '33011701', '解放路浣纱路1869', 2, NULL, 80, 0, NULL, 'OFF', '115.431373', '30.683624', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23961, '33011701001310701870', '33011701', '解放路建国路1870', 2, NULL, 80, 0, NULL, 'OFF', '115.4023546', '27.1135605', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23962, '33011701001310701871', '33011701', '钱江三桥秋涛路1871', 2, NULL, 80, 0, NULL, 'OFF', '115.7176545', '28.5169307', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23963, '33011701001310701872', '', 'testDevice1872', NULL, NULL, 80, 0, NULL, 'OFF', '117.3812096', '26.2439726', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23964, '33011701001310701873', '', 'dasda1873', NULL, NULL, 80, 0, NULL, '', '124.753085', '30.6690712', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23965, '33011701001310701874', '33011701', '吴山广场1874', 2, NULL, 80, 0, NULL, '', '116.6217968', '29.6134385', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23966, '33011701001310701875', '33011701', '中河路上仓桥路1875', 2, NULL, 80, 0, NULL, 'OFF', '133.8497295', '31.0599793', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23967, '33011701001310701876', '33011701', '西湖大道建国路1876', 2, NULL, 80, 0, NULL, 'OFF', '124.3832577', '31.4595812', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23968, '33011701001310701877', '33011701', '平海路中河路1877', 2, NULL, 80, 0, NULL, 'OFF', '125.3671008', '29.1179684', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23969, '33011701001310701878', '33011701', '平海路延安路1878', 2, NULL, 80, 0, NULL, 'OFF', '118.6857315', '26.2110989', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23970, '33011701001310701879', '33011701', '解放路浣纱路1879', 2, NULL, 80, 0, NULL, 'OFF', '122.3273555', '28.7015894', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23971, '33011701001310701880', '33011701', '解放路建国路1880', 2, NULL, 80, 0, NULL, 'OFF', '120.5795838', '29.8746508', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23972, '33011701001310701881', '33011701', '钱江三桥秋涛路1881', 2, NULL, 80, 0, NULL, 'OFF', '120.9158529', '28.2684861', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23973, '33011701001310701882', '', 'testDevice1882', NULL, NULL, 80, 0, NULL, 'OFF', '127.8405138', '26.7184784', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23974, '33011701001310701883', '', 'dasda1883', NULL, NULL, 80, 0, NULL, '', '121.4550111', '33.7869338', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23975, '33011701001310701884', '33011701', '吴山广场1884', 2, NULL, 80, 0, NULL, '', '128.7535147', '33.7792344', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23976, '33011701001310701885', '33011701', '中河路上仓桥路1885', 2, NULL, 80, 0, NULL, 'OFF', '124.4025406', '32.5353709', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23977, '33011701001310701886', '33011701', '西湖大道建国路1886', 2, NULL, 80, 0, NULL, 'OFF', '120.7521594', '26.3391517', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23978, '33011701001310701887', '33011701', '平海路中河路1887', 2, NULL, 80, 0, NULL, 'OFF', '115.5531761', '29.0896462', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23979, '33011701001310701888', '33011701', '平海路延安路1888', 2, NULL, 80, 0, NULL, 'OFF', '120.5094029', '31.4307759', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23980, '33011701001310701889', '33011701', '解放路浣纱路1889', 2, NULL, 80, 0, NULL, 'OFF', '120.8874869', '34.8849412', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23981, '33011701001310701890', '33011701', '解放路建国路1890', 2, NULL, 80, 0, NULL, 'OFF', '127.9092262', '25.1323787', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23982, '33011701001310701891', '33011701', '钱江三桥秋涛路1891', 2, NULL, 80, 0, NULL, 'OFF', '121.8836709', '26.0070703', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23983, '33011701001310701892', '', 'testDevice1892', NULL, NULL, 80, 0, NULL, 'OFF', '130.6906764', '29.6382154', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23984, '33011701001310701893', '', 'dasda1893', NULL, NULL, 80, 0, NULL, '', '132.8023701', '25.1698667', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23985, '33011701001310701894', '33011701', '吴山广场1894', 2, NULL, 80, 0, NULL, '', '116.9398219', '31.9346877', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23986, '33011701001310701895', '33011701', '中河路上仓桥路1895', 2, NULL, 80, 0, NULL, 'OFF', '131.2919999', '29.1638385', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23987, '33011701001310701896', '33011701', '西湖大道建国路1896', 2, NULL, 80, 0, NULL, 'OFF', '130.6405345', '25.0151297', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23988, '33011701001310701897', '33011701', '平海路中河路1897', 2, NULL, 80, 0, NULL, 'OFF', '124.3266734', '32.5841334', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23989, '33011701001310701898', '33011701', '平海路延安路1898', 2, NULL, 80, 0, NULL, 'OFF', '134.7117642', '32.8752783', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23990, '33011701001310701899', '33011701', '解放路浣纱路1899', 2, NULL, 80, 0, NULL, 'OFF', '125.5788014', '31.6239914', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23991, '33011701001310701900', '33011701', '解放路建国路1900', 2, NULL, 80, 0, NULL, 'OFF', '128.7587148', '34.4941227', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23992, '33011701001310701901', '33011701', '钱江三桥秋涛路1901', 2, NULL, 80, 0, NULL, 'OFF', '132.0571705', '32.5986394', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23993, '33011701001310701902', '', 'testDevice1902', NULL, NULL, 80, 0, NULL, 'OFF', '119.0097086', '34.5108292', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23994, '33011701001310701903', '', 'dasda1903', NULL, NULL, 80, 0, NULL, '', '123.8770323', '29.7582283', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23995, '33011701001310701904', '33011701', '吴山广场1904', 2, NULL, 80, 0, NULL, '', '127.3560361', '30.2586542', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23996, '33011701001310701905', '33011701', '中河路上仓桥路1905', 2, NULL, 80, 0, NULL, 'OFF', '130.1490845', '27.0185864', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23997, '33011701001310701906', '33011701', '西湖大道建国路1906', 2, NULL, 80, 0, NULL, 'OFF', '133.6773148', '29.3169698', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23998, '33011701001310701907', '33011701', '平海路中河路1907', 2, NULL, 80, 0, NULL, 'OFF', '122.9393211', '30.5290899', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (23999, '33011701001310701908', '33011701', '平海路延安路1908', 2, NULL, 80, 0, NULL, 'OFF', '118.6646617', '29.6945406', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24000, '33011701001310701909', '33011701', '解放路浣纱路1909', 2, NULL, 80, 0, NULL, 'OFF', '129.5053458', '31.8854334', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24001, '33011701001310701910', '33011701', '解放路建国路1910', 2, NULL, 80, 0, NULL, 'OFF', '116.5327446', '25.3435456', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24002, '33011701001310701911', '33011701', '钱江三桥秋涛路1911', 2, NULL, 80, 0, NULL, 'OFF', '119.1476861', '26.0614281', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24003, '33011701001310701912', '', 'testDevice1912', NULL, NULL, 80, 0, NULL, 'OFF', '131.1401972', '29.2765039', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24004, '33011701001310701913', '', 'dasda1913', NULL, NULL, 80, 0, NULL, '', '123.2579285', '33.1982355', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24005, '33011701001310701914', '33011701', '吴山广场1914', 2, NULL, 80, 0, NULL, '', '127.8690514', '33.1616661', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24006, '33011701001310701915', '33011701', '中河路上仓桥路1915', 2, NULL, 80, 0, NULL, 'OFF', '134.571472', '31.2136244', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24007, '33011701001310701916', '33011701', '西湖大道建国路1916', 2, NULL, 80, 0, NULL, 'OFF', '134.2502067', '31.5831242', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24008, '33011701001310701917', '33011701', '平海路中河路1917', 2, NULL, 80, 0, NULL, 'OFF', '132.5366179', '29.2747478', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24009, '33011701001310701918', '33011701', '平海路延安路1918', 2, NULL, 80, 0, NULL, 'OFF', '124.9324699', '26.6243669', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24010, '33011701001310701919', '33011701', '解放路浣纱路1919', 2, NULL, 80, 0, NULL, 'OFF', '132.0524964', '30.2975916', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24011, '33011701001310701920', '33011701', '解放路建国路1920', 2, NULL, 80, 0, NULL, 'OFF', '130.465073', '26.6148573', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24012, '33011701001310701921', '33011701', '钱江三桥秋涛路1921', 2, NULL, 80, 0, NULL, 'OFF', '121.1678763', '27.1815119', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24013, '33011701001310701922', '', 'testDevice1922', NULL, NULL, 80, 0, NULL, 'OFF', '119.4441631', '31.0629882', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24014, '33011701001310701923', '', 'dasda1923', NULL, NULL, 80, 0, NULL, '', '118.7171873', '28.7704054', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24015, '33011701001310701924', '33011701', '吴山广场1924', 2, NULL, 80, 0, NULL, '', '120.2534479', '25.663063', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24016, '33011701001310701925', '33011701', '中河路上仓桥路1925', 2, NULL, 80, 0, NULL, 'OFF', '130.1156783', '27.0040989', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24017, '33011701001310701926', '33011701', '西湖大道建国路1926', 2, NULL, 80, 0, NULL, 'OFF', '134.8180483', '33.0313058', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24018, '33011701001310701927', '33011701', '平海路中河路1927', 2, NULL, 80, 0, NULL, 'OFF', '128.743207', '29.1442327', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24019, '33011701001310701928', '33011701', '平海路延安路1928', 2, NULL, 80, 0, NULL, 'OFF', '124.261891', '31.6272464', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24020, '33011701001310701929', '33011701', '解放路浣纱路1929', 2, NULL, 80, 0, NULL, 'OFF', '120.0798343', '25.7035343', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24021, '33011701001310701930', '33011701', '解放路建国路1930', 2, NULL, 80, 0, NULL, 'OFF', '132.6134994', '28.6359325', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24022, '33011701001310701931', '33011701', '钱江三桥秋涛路1931', 2, NULL, 80, 0, NULL, 'OFF', '127.8279947', '31.06906', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24023, '33011701001310701932', '', 'testDevice1932', NULL, NULL, 80, 0, NULL, 'OFF', '126.299476', '34.4375028', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24024, '33011701001310701933', '', 'dasda1933', NULL, NULL, 80, 0, NULL, '', '133.0133963', '33.9803341', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24025, '33011701001310701934', '33011701', '吴山广场1934', 2, NULL, 80, 0, NULL, '', '131.168554', '31.5891626', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24026, '33011701001310701935', '33011701', '中河路上仓桥路1935', 2, NULL, 80, 0, NULL, 'OFF', '121.802582', '31.0048109', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24027, '33011701001310701936', '33011701', '西湖大道建国路1936', 2, NULL, 80, 0, NULL, 'OFF', '120.5072484', '25.2565669', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24028, '33011701001310701937', '33011701', '平海路中河路1937', 2, NULL, 80, 0, NULL, 'OFF', '122.1284969', '28.2684023', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24029, '33011701001310701938', '33011701', '平海路延安路1938', 2, NULL, 80, 0, NULL, 'OFF', '134.1207396', '30.572311', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24030, '33011701001310701939', '33011701', '解放路浣纱路1939', 2, NULL, 80, 0, NULL, 'OFF', '129.2182081', '33.0563484', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24031, '33011701001310701940', '33011701', '解放路建国路1940', 2, NULL, 80, 0, NULL, 'OFF', '128.7288224', '28.5648093', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24032, '33011701001310701941', '33011701', '钱江三桥秋涛路1941', 2, NULL, 80, 0, NULL, 'OFF', '120.9894883', '28.6550016', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24033, '33011701001310701942', '', 'testDevice1942', NULL, NULL, 80, 0, NULL, 'OFF', '123.7609747', '32.5805806', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24034, '33011701001310701943', '', 'dasda1943', NULL, NULL, 80, 0, NULL, '', '120.8364092', '31.9378985', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24035, '33011701001310701944', '33011701', '吴山广场1944', 2, NULL, 80, 0, NULL, '', '117.8991227', '26.9477509', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24036, '33011701001310701945', '33011701', '中河路上仓桥路1945', 2, NULL, 80, 0, NULL, 'OFF', '131.9863865', '33.9250594', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24037, '33011701001310701946', '33011701', '西湖大道建国路1946', 2, NULL, 80, 0, NULL, 'OFF', '131.2345649', '33.7820446', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24038, '33011701001310701947', '33011701', '平海路中河路1947', 2, NULL, 80, 0, NULL, 'OFF', '125.2136655', '32.1350449', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24039, '33011701001310701948', '33011701', '平海路延安路1948', 2, NULL, 80, 0, NULL, 'OFF', '117.3646337', '34.3290911', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24040, '33011701001310701949', '33011701', '解放路浣纱路1949', 2, NULL, 80, 0, NULL, 'OFF', '116.1821726', '30.240321', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24041, '33011701001310701950', '33011701', '解放路建国路1950', 2, NULL, 80, 0, NULL, 'OFF', '133.8169624', '33.2143319', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24042, '33011701001310701951', '33011701', '钱江三桥秋涛路1951', 2, NULL, 80, 0, NULL, 'OFF', '125.5382946', '30.3506969', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24043, '33011701001310701952', '', 'testDevice1952', NULL, NULL, 80, 0, NULL, 'OFF', '131.2405868', '27.110489', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24044, '33011701001310701953', '', 'dasda1953', NULL, NULL, 80, 0, NULL, '', '124.5880505', '29.5003547', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24045, '33011701001310701954', '33011701', '吴山广场1954', 2, NULL, 80, 0, NULL, '', '134.2184929', '31.1703069', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24046, '33011701001310701955', '33011701', '中河路上仓桥路1955', 2, NULL, 80, 0, NULL, 'OFF', '122.3283136', '32.3504706', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24047, '33011701001310701956', '33011701', '西湖大道建国路1956', 2, NULL, 80, 0, NULL, 'OFF', '133.9860897', '33.2414327', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24048, '33011701001310701957', '33011701', '平海路中河路1957', 2, NULL, 80, 0, NULL, 'OFF', '127.9455085', '34.1557518', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24049, '33011701001310701958', '33011701', '平海路延安路1958', 2, NULL, 80, 0, NULL, 'OFF', '122.769274', '26.0544615', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24050, '33011701001310701959', '33011701', '解放路浣纱路1959', 2, NULL, 80, 0, NULL, 'OFF', '115.0098451', '32.8050522', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24051, '33011701001310701960', '33011701', '解放路建国路1960', 2, NULL, 80, 0, NULL, 'OFF', '131.741404', '30.861877', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24052, '33011701001310701961', '33011701', '钱江三桥秋涛路1961', 2, NULL, 80, 0, NULL, 'OFF', '118.6774853', '30.8942286', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24053, '33011701001310701962', '', 'testDevice1962', NULL, NULL, 80, 0, NULL, 'OFF', '123.1632151', '26.8855123', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24054, '33011701001310701963', '', 'dasda1963', NULL, NULL, 80, 0, NULL, '', '124.7836201', '26.7448759', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24055, '33011701001310701964', '33011701', '吴山广场1964', 2, NULL, 80, 0, NULL, '', '119.4284559', '28.0678429', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24056, '33011701001310701965', '33011701', '中河路上仓桥路1965', 2, NULL, 80, 0, NULL, 'OFF', '127.79142', '25.1045872', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24057, '33011701001310701966', '33011701', '西湖大道建国路1966', 2, NULL, 80, 0, NULL, 'OFF', '125.6717326', '26.3194077', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24058, '33011701001310701967', '33011701', '平海路中河路1967', 2, NULL, 80, 0, NULL, 'OFF', '129.9844038', '31.283277', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24059, '33011701001310701968', '33011701', '平海路延安路1968', 2, NULL, 80, 0, NULL, 'OFF', '117.9068217', '32.4581622', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24060, '33011701001310701969', '33011701', '解放路浣纱路1969', 2, NULL, 80, 0, NULL, 'OFF', '124.5808976', '33.4409803', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24061, '33011701001310701970', '33011701', '解放路建国路1970', 2, NULL, 80, 0, NULL, 'OFF', '134.1840235', '34.8304151', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24062, '33011701001310701971', '33011701', '钱江三桥秋涛路1971', 2, NULL, 80, 0, NULL, 'OFF', '122.1774254', '28.8291351', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24063, '33011701001310701972', '', 'testDevice1972', NULL, NULL, 80, 0, NULL, 'OFF', '133.3350571', '34.6544304', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24064, '33011701001310701973', '', 'dasda1973', NULL, NULL, 80, 0, NULL, '', '125.1430098', '31.7847472', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24065, '33011701001310701974', '33011701', '吴山广场1974', 2, NULL, 80, 0, NULL, '', '130.7098784', '29.960445', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24066, '33011701001310701975', '33011701', '中河路上仓桥路1975', 2, NULL, 80, 0, NULL, 'OFF', '123.120363', '29.4479839', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24067, '33011701001310701976', '33011701', '西湖大道建国路1976', 2, NULL, 80, 0, NULL, 'OFF', '128.4721806', '32.3585847', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24068, '33011701001310701977', '33011701', '平海路中河路1977', 2, NULL, 80, 0, NULL, 'OFF', '117.9998146', '28.4489723', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24069, '33011701001310701978', '33011701', '平海路延安路1978', 2, NULL, 80, 0, NULL, 'OFF', '129.5825318', '30.1691075', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24070, '33011701001310701979', '33011701', '解放路浣纱路1979', 2, NULL, 80, 0, NULL, 'OFF', '118.9132159', '30.4986209', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24071, '33011701001310701980', '33011701', '解放路建国路1980', 2, NULL, 80, 0, NULL, 'OFF', '130.8184847', '26.9857825', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24072, '33011701001310701981', '33011701', '钱江三桥秋涛路1981', 2, NULL, 80, 0, NULL, 'OFF', '122.3527763', '28.4330502', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24073, '33011701001310701982', '', 'testDevice1982', NULL, NULL, 80, 0, NULL, 'OFF', '124.3084281', '26.2079036', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24074, '33011701001310701983', '', 'dasda1983', NULL, NULL, 80, 0, NULL, '', '119.4838123', '30.7403679', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24075, '33011701001310701984', '33011701', '吴山广场1984', 2, NULL, 80, 0, NULL, '', '129.4937777', '30.0781287', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24076, '33011701001310701985', '33011701', '中河路上仓桥路1985', 2, NULL, 80, 0, NULL, 'OFF', '134.0174522', '33.1695404', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24077, '33011701001310701986', '33011701', '西湖大道建国路1986', 2, NULL, 80, 0, NULL, 'OFF', '126.6059286', '30.613316', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24078, '33011701001310701987', '33011701', '平海路中河路1987', 2, NULL, 80, 0, NULL, 'OFF', '115.9772868', '28.5579592', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24079, '33011701001310701988', '33011701', '平海路延安路1988', 2, NULL, 80, 0, NULL, 'OFF', '125.0686491', '25.9498485', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24080, '33011701001310701989', '33011701', '解放路浣纱路1989', 2, NULL, 80, 0, NULL, 'OFF', '122.4113858', '29.0753649', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24081, '33011701001310701990', '33011701', '解放路建国路1990', 2, NULL, 80, 0, NULL, 'OFF', '121.850982', '32.5272794', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24082, '33011701001310701991', '33011701', '钱江三桥秋涛路1991', 2, NULL, 80, 0, NULL, 'OFF', '127.0207534', '30.4103028', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24083, '33011701001310701992', '', 'testDevice1992', NULL, NULL, 80, 0, NULL, 'OFF', '134.5508216', '29.4696758', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24084, '33011701001310701993', '', 'dasda1993', NULL, NULL, 80, 0, NULL, '', '116.6918485', '31.1174711', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24085, '33011701001310701994', '33011701', '吴山广场1994', 2, NULL, 80, 0, NULL, '', '124.806778', '32.1783284', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24086, '33011701001310701995', '33011701', '中河路上仓桥路1995', 2, NULL, 80, 0, NULL, 'OFF', '118.9583454', '32.539229', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24087, '33011701001310701996', '33011701', '西湖大道建国路1996', 2, NULL, 80, 0, NULL, 'OFF', '125.3713936', '31.16116', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24088, '33011701001310701997', '33011701', '平海路中河路1997', 2, NULL, 80, 0, NULL, 'OFF', '134.9819323', '33.1881133', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24089, '33011701001310701998', '33011701', '平海路延安路1998', 2, NULL, 80, 0, NULL, 'OFF', '123.7954815', '27.4570867', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24090, '33011701001310701999', '33011701', '解放路浣纱路1999', 2, NULL, 80, 0, NULL, 'OFF', '119.031611', '32.7210938', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24091, '33011701001310702000', '33011701', '解放路建国路2000', 2, NULL, 80, 0, NULL, 'OFF', '128.771611', '26.2342094', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24092, '33011701001310702001', '33011701', '钱江三桥秋涛路2001', 2, NULL, 80, 0, NULL, 'OFF', '131.7632228', '28.0077658', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24093, '33011701001310702002', '', 'testDevice2002', NULL, NULL, 80, 0, NULL, 'OFF', '117.5012817', '26.3362013', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24094, '33011701001310702003', '', 'dasda2003', NULL, NULL, 80, 0, NULL, '', '117.2167407', '32.6577094', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24095, '33011701001310702004', '33011701', '吴山广场2004', 2, NULL, 80, 0, NULL, '', '118.5798588', '29.2799431', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24096, '33011701001310702005', '33011701', '中河路上仓桥路2005', 2, NULL, 80, 0, NULL, 'OFF', '126.2490727', '33.426588', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24097, '33011701001310702006', '33011701', '西湖大道建国路2006', 2, NULL, 80, 0, NULL, 'OFF', '120.5057875', '34.2931106', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24098, '33011701001310702007', '33011701', '平海路中河路2007', 2, NULL, 80, 0, NULL, 'OFF', '128.78172', '26.1857897', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24099, '33011701001310702008', '33011701', '平海路延安路2008', 2, NULL, 80, 0, NULL, 'OFF', '127.3912383', '33.0496168', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24100, '33011701001310702009', '33011701', '解放路浣纱路2009', 2, NULL, 80, 0, NULL, 'OFF', '115.611032', '31.6907154', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24101, '33011701001310702010', '33011701', '解放路建国路2010', 2, NULL, 80, 0, NULL, 'OFF', '120.8814459', '34.3047269', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24102, '33011701001310702011', '33011701', '钱江三桥秋涛路2011', 2, NULL, 80, 0, NULL, 'OFF', '122.5741342', '31.4514884', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24103, '33011701001310702012', '', 'testDevice2012', NULL, NULL, 80, 0, NULL, 'OFF', '115.2263336', '29.3432619', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24104, '33011701001310702013', '', 'dasda2013', NULL, NULL, 80, 0, NULL, '', '133.4092662', '27.3618444', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24105, '33011701001310702014', '33011701', '吴山广场2014', 2, NULL, 80, 0, NULL, '', '126.3673307', '33.7794366', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24106, '33011701001310702015', '33011701', '中河路上仓桥路2015', 2, NULL, 80, 0, NULL, 'OFF', '116.6088554', '31.8116502', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24107, '33011701001310702016', '33011701', '西湖大道建国路2016', 2, NULL, 80, 0, NULL, 'OFF', '128.9422855', '32.7199414', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24108, '33011701001310702017', '33011701', '平海路中河路2017', 2, NULL, 80, 0, NULL, 'OFF', '119.884862', '33.1647568', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24109, '33011701001310702018', '33011701', '平海路延安路2018', 2, NULL, 80, 0, NULL, 'OFF', '117.5974543', '32.6639602', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24110, '33011701001310702019', '33011701', '解放路浣纱路2019', 2, NULL, 80, 0, NULL, 'OFF', '133.3326861', '28.8255308', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24111, '33011701001310702020', '33011701', '解放路建国路2020', 2, NULL, 80, 0, NULL, 'OFF', '118.8710681', '31.1357737', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24112, '33011701001310702021', '33011701', '钱江三桥秋涛路2021', 2, NULL, 80, 0, NULL, 'OFF', '119.3572828', '34.2022764', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24113, '33011701001310702022', '', 'testDevice2022', NULL, NULL, 80, 0, NULL, 'OFF', '125.1732102', '32.6040614', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24114, '33011701001310702023', '', 'dasda2023', NULL, NULL, 80, 0, NULL, '', '132.7942033', '25.4134778', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24115, '33011701001310702024', '33011701', '吴山广场2024', 2, NULL, 80, 0, NULL, '', '133.4513865', '34.2552052', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24116, '33011701001310702025', '33011701', '中河路上仓桥路2025', 2, NULL, 80, 0, NULL, 'OFF', '133.8743232', '30.0355928', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24117, '33011701001310702026', '33011701', '西湖大道建国路2026', 2, NULL, 80, 0, NULL, 'OFF', '134.017457', '32.4123488', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24118, '33011701001310702027', '33011701', '平海路中河路2027', 2, NULL, 80, 0, NULL, 'OFF', '133.464316', '26.9549659', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24119, '33011701001310702028', '33011701', '平海路延安路2028', 2, NULL, 80, 0, NULL, 'OFF', '130.2692097', '32.5377832', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24120, '33011701001310702029', '33011701', '解放路浣纱路2029', 2, NULL, 80, 0, NULL, 'OFF', '115.953101', '26.8240188', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24121, '33011701001310702030', '33011701', '解放路建国路2030', 2, NULL, 80, 0, NULL, 'OFF', '133.9578767', '31.5067448', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24122, '33011701001310702031', '33011701', '钱江三桥秋涛路2031', 2, NULL, 80, 0, NULL, 'OFF', '126.9300812', '32.0616676', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24123, '33011701001310702032', '', 'testDevice2032', NULL, NULL, 80, 0, NULL, 'OFF', '117.7767763', '30.7881041', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24124, '33011701001310702033', '', 'dasda2033', NULL, NULL, 80, 0, NULL, '', '133.0936384', '32.7555179', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24125, '33011701001310702034', '33011701', '吴山广场2034', 2, NULL, 80, 0, NULL, '', '117.1378638', '26.4132778', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24126, '33011701001310702035', '33011701', '中河路上仓桥路2035', 2, NULL, 80, 0, NULL, 'OFF', '131.4084046', '28.7998353', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24127, '33011701001310702036', '33011701', '西湖大道建国路2036', 2, NULL, 80, 0, NULL, 'OFF', '130.6284322', '29.7593436', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24128, '33011701001310702037', '33011701', '平海路中河路2037', 2, NULL, 80, 0, NULL, 'OFF', '123.9169479', '27.3972122', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24129, '33011701001310702038', '33011701', '平海路延安路2038', 2, NULL, 80, 0, NULL, 'OFF', '132.6994434', '32.7080308', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24130, '33011701001310702039', '33011701', '解放路浣纱路2039', 2, NULL, 80, 0, NULL, 'OFF', '116.7463741', '26.3485176', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24131, '33011701001310702040', '33011701', '解放路建国路2040', 2, NULL, 80, 0, NULL, 'OFF', '130.6335407', '28.6184961', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24132, '33011701001310702041', '33011701', '钱江三桥秋涛路2041', 2, NULL, 80, 0, NULL, 'OFF', '127.9285818', '29.0469277', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24133, '33011701001310702042', '', 'testDevice2042', NULL, NULL, 80, 0, NULL, 'OFF', '132.7422874', '34.3791506', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24134, '33011701001310702043', '', 'dasda2043', NULL, NULL, 80, 0, NULL, '', '124.9256922', '29.7549704', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24135, '33011701001310702044', '33011701', '吴山广场2044', 2, NULL, 80, 0, NULL, '', '131.4015994', '30.6374002', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24136, '33011701001310702045', '33011701', '中河路上仓桥路2045', 2, NULL, 80, 0, NULL, 'OFF', '127.2309213', '28.9220904', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24137, '33011701001310702046', '33011701', '西湖大道建国路2046', 2, NULL, 80, 0, NULL, 'OFF', '126.9498086', '27.6982516', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24138, '33011701001310702047', '33011701', '平海路中河路2047', 2, NULL, 80, 0, NULL, 'OFF', '118.0562796', '26.7249871', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24139, '33011701001310702048', '33011701', '平海路延安路2048', 2, NULL, 80, 0, NULL, 'OFF', '134.4319731', '25.5301812', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24140, '33011701001310702049', '33011701', '解放路浣纱路2049', 2, NULL, 80, 0, NULL, 'OFF', '122.9910272', '32.4759447', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24141, '33011701001310702050', '33011701', '解放路建国路2050', 2, NULL, 80, 0, NULL, 'OFF', '116.6592174', '30.7891802', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24142, '33011701001310702051', '33011701', '钱江三桥秋涛路2051', 2, NULL, 80, 0, NULL, 'OFF', '119.3230061', '31.5180675', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24143, '33011701001310702052', '', 'testDevice2052', NULL, NULL, 80, 0, NULL, 'OFF', '131.637379', '30.222797', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24144, '33011701001310702053', '', 'dasda2053', NULL, NULL, 80, 0, NULL, '', '125.2178773', '31.559783', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24145, '33011701001310702054', '33011701', '吴山广场2054', 2, NULL, 80, 0, NULL, '', '116.17725', '32.1305241', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24146, '33011701001310702055', '33011701', '中河路上仓桥路2055', 2, NULL, 80, 0, NULL, 'OFF', '130.2326186', '30.9732717', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24147, '33011701001310702056', '33011701', '西湖大道建国路2056', 2, NULL, 80, 0, NULL, 'OFF', '127.6313436', '33.4747866', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24148, '33011701001310702057', '33011701', '平海路中河路2057', 2, NULL, 80, 0, NULL, 'OFF', '132.458863', '29.4541183', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24149, '33011701001310702058', '33011701', '平海路延安路2058', 2, NULL, 80, 0, NULL, 'OFF', '124.4002846', '31.8462319', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24150, '33011701001310702059', '33011701', '解放路浣纱路2059', 2, NULL, 80, 0, NULL, 'OFF', '129.6248345', '25.8688051', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24151, '33011701001310702060', '33011701', '解放路建国路2060', 2, NULL, 80, 0, NULL, 'OFF', '119.9233196', '28.8053302', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24152, '33011701001310702061', '33011701', '钱江三桥秋涛路2061', 2, NULL, 80, 0, NULL, 'OFF', '115.7420952', '31.4202358', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24153, '33011701001310702062', '', 'testDevice2062', NULL, NULL, 80, 0, NULL, 'OFF', '123.9405176', '25.6851889', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24154, '33011701001310702063', '', 'dasda2063', NULL, NULL, 80, 0, NULL, '', '117.4763029', '29.1652375', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24155, '33011701001310702064', '33011701', '吴山广场2064', 2, NULL, 80, 0, NULL, '', '120.5599624', '33.770621', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24156, '33011701001310702065', '33011701', '中河路上仓桥路2065', 2, NULL, 80, 0, NULL, 'OFF', '115.3709039', '26.3573929', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24157, '33011701001310702066', '33011701', '西湖大道建国路2066', 2, NULL, 80, 0, NULL, 'OFF', '120.1746331', '25.4751017', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24158, '33011701001310702067', '33011701', '平海路中河路2067', 2, NULL, 80, 0, NULL, 'OFF', '119.7604542', '33.3033303', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24159, '33011701001310702068', '33011701', '平海路延安路2068', 2, NULL, 80, 0, NULL, 'OFF', '123.2783722', '25.0913466', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24160, '33011701001310702069', '33011701', '解放路浣纱路2069', 2, NULL, 80, 0, NULL, 'OFF', '122.1104991', '30.5467425', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24161, '33011701001310702070', '33011701', '解放路建国路2070', 2, NULL, 80, 0, NULL, 'OFF', '125.7173794', '32.4596728', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24162, '33011701001310702071', '33011701', '钱江三桥秋涛路2071', 2, NULL, 80, 0, NULL, 'OFF', '127.2554006', '25.6581367', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24163, '33011701001310702072', '', 'testDevice2072', NULL, NULL, 80, 0, NULL, 'OFF', '124.1248653', '25.9116657', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24164, '33011701001310702073', '', 'dasda2073', NULL, NULL, 80, 0, NULL, '', '123.8581252', '27.5839186', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24165, '33011701001310702074', '33011701', '吴山广场2074', 2, NULL, 80, 0, NULL, '', '131.9160309', '25.1845964', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24166, '33011701001310702075', '33011701', '中河路上仓桥路2075', 2, NULL, 80, 0, NULL, 'OFF', '133.0057793', '28.1712263', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24167, '33011701001310702076', '33011701', '西湖大道建国路2076', 2, NULL, 80, 0, NULL, 'OFF', '134.2808045', '30.3023425', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24168, '33011701001310702077', '33011701', '平海路中河路2077', 2, NULL, 80, 0, NULL, 'OFF', '117.3866851', '31.9980342', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24169, '33011701001310702078', '33011701', '平海路延安路2078', 2, NULL, 80, 0, NULL, 'OFF', '129.0910127', '34.0831437', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24170, '33011701001310702079', '33011701', '解放路浣纱路2079', 2, NULL, 80, 0, NULL, 'OFF', '118.2950086', '29.4216163', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24171, '33011701001310702080', '33011701', '解放路建国路2080', 2, NULL, 80, 0, NULL, 'OFF', '129.2020058', '29.8586506', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24172, '33011701001310702081', '33011701', '钱江三桥秋涛路2081', 2, NULL, 80, 0, NULL, 'OFF', '116.1250038', '26.0284045', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24173, '33011701001310702082', '', 'testDevice2082', NULL, NULL, 80, 0, NULL, 'OFF', '118.0190022', '25.5660708', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24174, '33011701001310702083', '', 'dasda2083', NULL, NULL, 80, 0, NULL, '', '126.7200002', '34.7451409', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24175, '33011701001310702084', '33011701', '吴山广场2084', 2, NULL, 80, 0, NULL, '', '124.5429948', '32.0274923', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24176, '33011701001310702085', '33011701', '中河路上仓桥路2085', 2, NULL, 80, 0, NULL, 'OFF', '127.5549743', '30.9020391', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24177, '33011701001310702086', '33011701', '西湖大道建国路2086', 2, NULL, 80, 0, NULL, 'OFF', '129.1458878', '33.427719', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24178, '33011701001310702087', '33011701', '平海路中河路2087', 2, NULL, 80, 0, NULL, 'OFF', '128.0645166', '29.4324778', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24179, '33011701001310702088', '33011701', '平海路延安路2088', 2, NULL, 80, 0, NULL, 'OFF', '117.8849202', '31.8792325', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24180, '33011701001310702089', '33011701', '解放路浣纱路2089', 2, NULL, 80, 0, NULL, 'OFF', '130.231052', '26.0987294', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24181, '33011701001310702090', '33011701', '解放路建国路2090', 2, NULL, 80, 0, NULL, 'OFF', '122.5004999', '29.8559497', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24182, '33011701001310702091', '33011701', '钱江三桥秋涛路2091', 2, NULL, 80, 0, NULL, 'OFF', '126.809344', '25.9835607', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24183, '33011701001310702092', '', 'testDevice2092', NULL, NULL, 80, 0, NULL, 'OFF', '131.5452212', '25.3499546', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24184, '33011701001310702093', '', 'dasda2093', NULL, NULL, 80, 0, NULL, '', '122.2980743', '33.7990915', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24185, '33011701001310702094', '33011701', '吴山广场2094', 2, NULL, 80, 0, NULL, '', '121.8547088', '27.9455937', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24186, '33011701001310702095', '33011701', '中河路上仓桥路2095', 2, NULL, 80, 0, NULL, 'OFF', '127.3793215', '33.3306944', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24187, '33011701001310702096', '33011701', '西湖大道建国路2096', 2, NULL, 80, 0, NULL, 'OFF', '116.3324818', '27.8166911', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24188, '33011701001310702097', '33011701', '平海路中河路2097', 2, NULL, 80, 0, NULL, 'OFF', '124.5244451', '34.0913726', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24189, '33011701001310702098', '33011701', '平海路延安路2098', 2, NULL, 80, 0, NULL, 'OFF', '118.6247808', '32.0067902', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24190, '33011701001310702099', '33011701', '解放路浣纱路2099', 2, NULL, 80, 0, NULL, 'OFF', '124.5505692', '32.7598332', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24191, '33011701001310702100', '33011701', '解放路建国路2100', 2, NULL, 80, 0, NULL, 'OFF', '131.8785041', '32.7787959', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24192, '33011701001310702101', '33011701', '钱江三桥秋涛路2101', 2, NULL, 80, 0, NULL, 'OFF', '130.7408135', '30.6144801', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24193, '33011701001310702102', '', 'testDevice2102', NULL, NULL, 80, 0, NULL, 'OFF', '123.068556', '29.736013', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24194, '33011701001310702103', '', 'dasda2103', NULL, NULL, 80, 0, NULL, '', '128.1203403', '31.836625', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24195, '33011701001310702104', '33011701', '吴山广场2104', 2, NULL, 80, 0, NULL, '', '116.3960337', '34.9750863', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24196, '33011701001310702105', '33011701', '中河路上仓桥路2105', 2, NULL, 80, 0, NULL, 'OFF', '122.6191485', '34.3655571', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24197, '33011701001310702106', '33011701', '西湖大道建国路2106', 2, NULL, 80, 0, NULL, 'OFF', '128.9076419', '31.9025267', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24198, '33011701001310702107', '33011701', '平海路中河路2107', 2, NULL, 80, 0, NULL, 'OFF', '121.6807648', '31.4159623', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24199, '33011701001310702108', '33011701', '平海路延安路2108', 2, NULL, 80, 0, NULL, 'OFF', '126.6808987', '26.372232', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24200, '33011701001310702109', '33011701', '解放路浣纱路2109', 2, NULL, 80, 0, NULL, 'OFF', '133.3621996', '32.6132734', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24201, '33011701001310702110', '33011701', '解放路建国路2110', 2, NULL, 80, 0, NULL, 'OFF', '131.7683027', '28.9496711', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24202, '33011701001310702111', '33011701', '钱江三桥秋涛路2111', 2, NULL, 80, 0, NULL, 'OFF', '123.7549152', '31.9085357', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24203, '33011701001310702112', '', 'testDevice2112', NULL, NULL, 80, 0, NULL, 'OFF', '128.4696684', '27.6936654', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24204, '33011701001310702113', '', 'dasda2113', NULL, NULL, 80, 0, NULL, '', '116.083597', '27.7427205', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24205, '33011701001310702114', '33011701', '吴山广场2114', 2, NULL, 80, 0, NULL, '', '120.0089807', '30.6326065', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24206, '33011701001310702115', '33011701', '中河路上仓桥路2115', 2, NULL, 80, 0, NULL, 'OFF', '116.7941129', '34.9348711', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24207, '33011701001310702116', '33011701', '西湖大道建国路2116', 2, NULL, 80, 0, NULL, 'OFF', '128.9436229', '27.7765365', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24208, '33011701001310702117', '33011701', '平海路中河路2117', 2, NULL, 80, 0, NULL, 'OFF', '119.3357766', '29.0780694', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24209, '33011701001310702118', '33011701', '平海路延安路2118', 2, NULL, 80, 0, NULL, 'OFF', '134.8480151', '27.0607379', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24210, '33011701001310702119', '33011701', '解放路浣纱路2119', 2, NULL, 80, 0, NULL, 'OFF', '121.232746', '33.0694815', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24211, '33011701001310702120', '33011701', '解放路建国路2120', 2, NULL, 80, 0, NULL, 'OFF', '126.6196855', '29.1651941', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24212, '33011701001310702121', '33011701', '钱江三桥秋涛路2121', 2, NULL, 80, 0, NULL, 'OFF', '134.4001899', '31.6175265', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24213, '33011701001310702122', '', 'testDevice2122', NULL, NULL, 80, 0, NULL, 'OFF', '117.1418935', '25.5920503', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24214, '33011701001310702123', '', 'dasda2123', NULL, NULL, 80, 0, NULL, '', '127.5088987', '28.1076725', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24215, '33011701001310702124', '33011701', '吴山广场2124', 2, NULL, 80, 0, NULL, '', '131.1188135', '28.7622118', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24216, '33011701001310702125', '33011701', '中河路上仓桥路2125', 2, NULL, 80, 0, NULL, 'OFF', '118.0673719', '34.488042', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24217, '33011701001310702126', '33011701', '西湖大道建国路2126', 2, NULL, 80, 0, NULL, 'OFF', '121.9804197', '31.1535748', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24218, '33011701001310702127', '33011701', '平海路中河路2127', 2, NULL, 80, 0, NULL, 'OFF', '120.6999835', '27.3037482', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24219, '33011701001310702128', '33011701', '平海路延安路2128', 2, NULL, 80, 0, NULL, 'OFF', '122.5586591', '28.0580168', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24220, '33011701001310702129', '33011701', '解放路浣纱路2129', 2, NULL, 80, 0, NULL, 'OFF', '115.6933453', '33.3788399', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24221, '33011701001310702130', '33011701', '解放路建国路2130', 2, NULL, 80, 0, NULL, 'OFF', '115.7907502', '27.7201492', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24222, '33011701001310702131', '33011701', '钱江三桥秋涛路2131', 2, NULL, 80, 0, NULL, 'OFF', '116.8737155', '33.4642266', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24223, '33011701001310702132', '', 'testDevice2132', NULL, NULL, 80, 0, NULL, 'OFF', '121.9963274', '29.1606856', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24224, '33011701001310702133', '', 'dasda2133', NULL, NULL, 80, 0, NULL, '', '124.3604912', '30.4107486', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24225, '33011701001310702134', '33011701', '吴山广场2134', 2, NULL, 80, 0, NULL, '', '120.8134746', '29.5716865', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24226, '33011701001310702135', '33011701', '中河路上仓桥路2135', 2, NULL, 80, 0, NULL, 'OFF', '115.9858998', '31.6261871', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24227, '33011701001310702136', '33011701', '西湖大道建国路2136', 2, NULL, 80, 0, NULL, 'OFF', '122.4890757', '34.4158762', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24228, '33011701001310702137', '33011701', '平海路中河路2137', 2, NULL, 80, 0, NULL, 'OFF', '129.4876798', '32.20082', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24229, '33011701001310702138', '33011701', '平海路延安路2138', 2, NULL, 80, 0, NULL, 'OFF', '124.9711725', '32.7564718', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24230, '33011701001310702139', '33011701', '解放路浣纱路2139', 2, NULL, 80, 0, NULL, 'OFF', '121.3928236', '32.1798995', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24231, '33011701001310702140', '33011701', '解放路建国路2140', 2, NULL, 80, 0, NULL, 'OFF', '117.0506012', '27.6300821', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24232, '33011701001310702141', '33011701', '钱江三桥秋涛路2141', 2, NULL, 80, 0, NULL, 'OFF', '126.0745358', '26.6107124', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24233, '33011701001310702142', '', 'testDevice2142', NULL, NULL, 80, 0, NULL, 'OFF', '124.220876', '25.1633159', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24234, '33011701001310702143', '', 'dasda2143', NULL, NULL, 80, 0, NULL, '', '127.8807731', '30.9844427', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24235, '33011701001310702144', '33011701', '吴山广场2144', 2, NULL, 80, 0, NULL, '', '131.741238', '34.432266', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24236, '33011701001310702145', '33011701', '中河路上仓桥路2145', 2, NULL, 80, 0, NULL, 'OFF', '120.0638715', '34.2080023', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24237, '33011701001310702146', '33011701', '西湖大道建国路2146', 2, NULL, 80, 0, NULL, 'OFF', '130.0956441', '32.7432137', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24238, '33011701001310702147', '33011701', '平海路中河路2147', 2, NULL, 80, 0, NULL, 'OFF', '115.2866066', '26.0920619', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24239, '33011701001310702148', '33011701', '平海路延安路2148', 2, NULL, 80, 0, NULL, 'OFF', '131.1461014', '27.2306688', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24240, '33011701001310702149', '33011701', '解放路浣纱路2149', 2, NULL, 80, 0, NULL, 'OFF', '134.8706879', '32.8771586', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24241, '33011701001310702150', '33011701', '解放路建国路2150', 2, NULL, 80, 0, NULL, 'OFF', '125.9151357', '27.6937871', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24242, '33011701001310702151', '33011701', '钱江三桥秋涛路2151', 2, NULL, 80, 0, NULL, 'OFF', '129.9636155', '34.8374597', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24243, '33011701001310702152', '', 'testDevice2152', NULL, NULL, 80, 0, NULL, 'OFF', '117.0726709', '26.1059378', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24244, '33011701001310702153', '', 'dasda2153', NULL, NULL, 80, 0, NULL, '', '120.4725087', '31.01731', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24245, '33011701001310702154', '33011701', '吴山广场2154', 2, NULL, 80, 0, NULL, '', '116.1445315', '31.7687368', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24246, '33011701001310702155', '33011701', '中河路上仓桥路2155', 2, NULL, 80, 0, NULL, 'OFF', '124.3051321', '30.7917545', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24247, '33011701001310702156', '33011701', '西湖大道建国路2156', 2, NULL, 80, 0, NULL, 'OFF', '118.0920666', '33.6525622', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24248, '33011701001310702157', '33011701', '平海路中河路2157', 2, NULL, 80, 0, NULL, 'OFF', '122.5449371', '30.8875478', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24249, '33011701001310702158', '33011701', '平海路延安路2158', 2, NULL, 80, 0, NULL, 'OFF', '123.4484866', '28.4800528', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24250, '33011701001310702159', '33011701', '解放路浣纱路2159', 2, NULL, 80, 0, NULL, 'OFF', '134.6076222', '34.7376207', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24251, '33011701001310702160', '33011701', '解放路建国路2160', 2, NULL, 80, 0, NULL, 'OFF', '127.6926517', '33.2479456', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24252, '33011701001310702161', '33011701', '钱江三桥秋涛路2161', 2, NULL, 80, 0, NULL, 'OFF', '119.6403926', '27.0268661', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24253, '33011701001310702162', '', 'testDevice2162', NULL, NULL, 80, 0, NULL, 'OFF', '120.1240085', '30.3904939', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24254, '33011701001310702163', '', 'dasda2163', NULL, NULL, 80, 0, NULL, '', '126.6988655', '25.8718717', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24255, '33011701001310702164', '33011701', '吴山广场2164', 2, NULL, 80, 0, NULL, '', '118.1223026', '33.1878773', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24256, '33011701001310702165', '33011701', '中河路上仓桥路2165', 2, NULL, 80, 0, NULL, 'OFF', '115.5149171', '33.3237713', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24257, '33011701001310702166', '33011701', '西湖大道建国路2166', 2, NULL, 80, 0, NULL, 'OFF', '128.2076782', '32.0552252', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24258, '33011701001310702167', '33011701', '平海路中河路2167', 2, NULL, 80, 0, NULL, 'OFF', '119.4936406', '25.3048124', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24259, '33011701001310702168', '33011701', '平海路延安路2168', 2, NULL, 80, 0, NULL, 'OFF', '117.8451687', '25.3583868', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24260, '33011701001310702169', '33011701', '解放路浣纱路2169', 2, NULL, 80, 0, NULL, 'OFF', '115.7449226', '25.8774972', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24261, '33011701001310702170', '33011701', '解放路建国路2170', 2, NULL, 80, 0, NULL, 'OFF', '130.1891072', '28.3123257', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24262, '33011701001310702171', '33011701', '钱江三桥秋涛路2171', 2, NULL, 80, 0, NULL, 'OFF', '128.7107689', '28.9291372', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24263, '33011701001310702172', '', 'testDevice2172', NULL, NULL, 80, 0, NULL, 'OFF', '117.9865235', '34.708709', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24264, '33011701001310702173', '', 'dasda2173', NULL, NULL, 80, 0, NULL, '', '128.8003114', '31.7561341', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24265, '33011701001310702174', '33011701', '吴山广场2174', 2, NULL, 80, 0, NULL, '', '115.0419872', '29.6545436', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24266, '33011701001310702175', '33011701', '中河路上仓桥路2175', 2, NULL, 80, 0, NULL, 'OFF', '133.8090025', '28.0043158', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24267, '33011701001310702176', '33011701', '西湖大道建国路2176', 2, NULL, 80, 0, NULL, 'OFF', '128.9190514', '26.0579489', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24268, '33011701001310702177', '33011701', '平海路中河路2177', 2, NULL, 80, 0, NULL, 'OFF', '128.16825', '31.2767971', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24269, '33011701001310702178', '33011701', '平海路延安路2178', 2, NULL, 80, 0, NULL, 'OFF', '119.0840967', '33.2101392', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24270, '33011701001310702179', '33011701', '解放路浣纱路2179', 2, NULL, 80, 0, NULL, 'OFF', '115.9157338', '27.2203049', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24271, '33011701001310702180', '33011701', '解放路建国路2180', 2, NULL, 80, 0, NULL, 'OFF', '127.3263798', '31.4711072', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24272, '33011701001310702181', '33011701', '钱江三桥秋涛路2181', 2, NULL, 80, 0, NULL, 'OFF', '133.8846981', '30.6946219', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24273, '33011701001310702182', '', 'testDevice2182', NULL, NULL, 80, 0, NULL, 'OFF', '132.4443518', '34.0597879', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24274, '33011701001310702183', '', 'dasda2183', NULL, NULL, 80, 0, NULL, '', '125.5676652', '33.2150741', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24275, '33011701001310702184', '33011701', '吴山广场2184', 2, NULL, 80, 0, NULL, '', '115.5052711', '28.8960074', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24276, '33011701001310702185', '33011701', '中河路上仓桥路2185', 2, NULL, 80, 0, NULL, 'OFF', '125.8233607', '29.8348148', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24277, '33011701001310702186', '33011701', '西湖大道建国路2186', 2, NULL, 80, 0, NULL, 'OFF', '127.6009906', '27.4860521', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24278, '33011701001310702187', '33011701', '平海路中河路2187', 2, NULL, 80, 0, NULL, 'OFF', '125.5348715', '32.9258163', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24279, '33011701001310702188', '33011701', '平海路延安路2188', 2, NULL, 80, 0, NULL, 'OFF', '129.8713863', '27.1709258', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24280, '33011701001310702189', '33011701', '解放路浣纱路2189', 2, NULL, 80, 0, NULL, 'OFF', '117.7523176', '32.0771802', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24281, '33011701001310702190', '33011701', '解放路建国路2190', 2, NULL, 80, 0, NULL, 'OFF', '124.1474299', '33.8731242', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24282, '33011701001310702191', '33011701', '钱江三桥秋涛路2191', 2, NULL, 80, 0, NULL, 'OFF', '132.4801971', '28.1340804', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24283, '33011701001310702192', '', 'testDevice2192', NULL, NULL, 80, 0, NULL, 'OFF', '134.9586964', '34.0510296', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24284, '33011701001310702193', '', 'dasda2193', NULL, NULL, 80, 0, NULL, '', '122.3528915', '30.8529074', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24285, '33011701001310702194', '33011701', '吴山广场2194', 2, NULL, 80, 0, NULL, '', '131.8883687', '27.1114484', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24286, '33011701001310702195', '33011701', '中河路上仓桥路2195', 2, NULL, 80, 0, NULL, 'OFF', '117.3831699', '27.9985201', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24287, '33011701001310702196', '33011701', '西湖大道建国路2196', 2, NULL, 80, 0, NULL, 'OFF', '116.2507438', '33.6582556', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24288, '33011701001310702197', '33011701', '平海路中河路2197', 2, NULL, 80, 0, NULL, 'OFF', '134.1042101', '29.2957179', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24289, '33011701001310702198', '33011701', '平海路延安路2198', 2, NULL, 80, 0, NULL, 'OFF', '126.7688196', '30.5038232', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24290, '33011701001310702199', '33011701', '解放路浣纱路2199', 2, NULL, 80, 0, NULL, 'OFF', '116.5314684', '29.6319626', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24291, '33011701001310702200', '33011701', '解放路建国路2200', 2, NULL, 80, 0, NULL, 'OFF', '127.350884', '31.6483436', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24292, '33011701001310702201', '33011701', '钱江三桥秋涛路2201', 2, NULL, 80, 0, NULL, 'OFF', '132.1600151', '34.3458305', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24293, '33011701001310702202', '', 'testDevice2202', NULL, NULL, 80, 0, NULL, 'OFF', '123.7474242', '31.7841221', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24294, '33011701001310702203', '', 'dasda2203', NULL, NULL, 80, 0, NULL, '', '127.2570761', '30.8831194', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24295, '33011701001310702204', '33011701', '吴山广场2204', 2, NULL, 80, 0, NULL, '', '130.0431088', '34.0632308', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24296, '33011701001310702205', '33011701', '中河路上仓桥路2205', 2, NULL, 80, 0, NULL, 'OFF', '133.4443164', '32.6667963', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24297, '33011701001310702206', '33011701', '西湖大道建国路2206', 2, NULL, 80, 0, NULL, 'OFF', '122.0922563', '26.1442895', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24298, '33011701001310702207', '33011701', '平海路中河路2207', 2, NULL, 80, 0, NULL, 'OFF', '115.1283327', '27.7210588', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24299, '33011701001310702208', '33011701', '平海路延安路2208', 2, NULL, 80, 0, NULL, 'OFF', '134.3648951', '25.1724259', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24300, '33011701001310702209', '33011701', '解放路浣纱路2209', 2, NULL, 80, 0, NULL, 'OFF', '131.439478', '27.6989531', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24301, '33011701001310702210', '33011701', '解放路建国路2210', 2, NULL, 80, 0, NULL, 'OFF', '119.1027055', '27.9774884', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24302, '33011701001310702211', '33011701', '钱江三桥秋涛路2211', 2, NULL, 80, 0, NULL, 'OFF', '126.195094', '31.790583', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24303, '33011701001310702212', '', 'testDevice2212', NULL, NULL, 80, 0, NULL, 'OFF', '118.6673543', '30.0204502', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24304, '33011701001310702213', '', 'dasda2213', NULL, NULL, 80, 0, NULL, '', '119.75149', '29.7305021', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24305, '33011701001310702214', '33011701', '吴山广场2214', 2, NULL, 80, 0, NULL, '', '127.7553879', '33.5911602', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24306, '33011701001310702215', '33011701', '中河路上仓桥路2215', 2, NULL, 80, 0, NULL, 'OFF', '124.52247', '33.7642951', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24307, '33011701001310702216', '33011701', '西湖大道建国路2216', 2, NULL, 80, 0, NULL, 'OFF', '124.3461869', '33.0479951', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24308, '33011701001310702217', '33011701', '平海路中河路2217', 2, NULL, 80, 0, NULL, 'OFF', '133.1635252', '28.9470906', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24309, '33011701001310702218', '33011701', '平海路延安路2218', 2, NULL, 80, 0, NULL, 'OFF', '117.7790659', '30.591468', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24310, '33011701001310702219', '33011701', '解放路浣纱路2219', 2, NULL, 80, 0, NULL, 'OFF', '134.4047543', '31.1160685', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24311, '33011701001310702220', '33011701', '解放路建国路2220', 2, NULL, 80, 0, NULL, 'OFF', '123.6865746', '28.8059387', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24312, '33011701001310702221', '33011701', '钱江三桥秋涛路2221', 2, NULL, 80, 0, NULL, 'OFF', '120.2186105', '25.6814885', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24313, '33011701001310702222', '', 'testDevice2222', NULL, NULL, 80, 0, NULL, 'OFF', '115.0333294', '26.9896265', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24314, '33011701001310702223', '', 'dasda2223', NULL, NULL, 80, 0, NULL, '', '119.5108161', '32.9036673', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24315, '33011701001310702224', '33011701', '吴山广场2224', 2, NULL, 80, 0, NULL, '', '117.4540929', '28.5494575', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24316, '33011701001310702225', '33011701', '中河路上仓桥路2225', 2, NULL, 80, 0, NULL, 'OFF', '133.738017', '29.0362859', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24317, '33011701001310702226', '33011701', '西湖大道建国路2226', 2, NULL, 80, 0, NULL, 'OFF', '121.3278068', '34.5330571', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24318, '33011701001310702227', '33011701', '平海路中河路2227', 2, NULL, 80, 0, NULL, 'OFF', '130.4249834', '30.5564283', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24319, '33011701001310702228', '33011701', '平海路延安路2228', 2, NULL, 80, 0, NULL, 'OFF', '133.1414975', '34.1829706', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24320, '33011701001310702229', '33011701', '解放路浣纱路2229', 2, NULL, 80, 0, NULL, 'OFF', '119.4325377', '34.2455683', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24321, '33011701001310702230', '33011701', '解放路建国路2230', 2, NULL, 80, 0, NULL, 'OFF', '122.7381966', '33.67893', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24322, '33011701001310702231', '33011701', '钱江三桥秋涛路2231', 2, NULL, 80, 0, NULL, 'OFF', '120.3933705', '30.6579453', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24323, '33011701001310702232', '', 'testDevice2232', NULL, NULL, 80, 0, NULL, 'OFF', '118.7522633', '27.2529367', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24324, '33011701001310702233', '', 'dasda2233', NULL, NULL, 80, 0, NULL, '', '117.5812058', '29.290848', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24325, '33011701001310702234', '33011701', '吴山广场2234', 2, NULL, 80, 0, NULL, '', '116.6492395', '29.6954301', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24326, '33011701001310702235', '33011701', '中河路上仓桥路2235', 2, NULL, 80, 0, NULL, 'OFF', '115.5025806', '25.6046067', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24327, '33011701001310702236', '33011701', '西湖大道建国路2236', 2, NULL, 80, 0, NULL, 'OFF', '132.5651853', '33.9367437', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24328, '33011701001310702237', '33011701', '平海路中河路2237', 2, NULL, 80, 0, NULL, 'OFF', '121.3181853', '27.8698988', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24329, '33011701001310702238', '33011701', '平海路延安路2238', 2, NULL, 80, 0, NULL, 'OFF', '133.895371', '32.539263', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24330, '33011701001310702239', '33011701', '解放路浣纱路2239', 2, NULL, 80, 0, NULL, 'OFF', '130.5223', '34.0866192', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24331, '33011701001310702240', '33011701', '解放路建国路2240', 2, NULL, 80, 0, NULL, 'OFF', '115.9253875', '27.815307', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24332, '33011701001310702241', '33011701', '钱江三桥秋涛路2241', 2, NULL, 80, 0, NULL, 'OFF', '133.0600382', '31.8166777', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24333, '33011701001310702242', '', 'testDevice2242', NULL, NULL, 80, 0, NULL, 'OFF', '122.5240291', '30.637468', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24334, '33011701001310702243', '', 'dasda2243', NULL, NULL, 80, 0, NULL, '', '118.4400313', '32.7373073', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24335, '33011701001310702627', '33011701', '吴山广场2627', 2, NULL, 80, 0, NULL, '', '129.62807', '26.7741329', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24336, '33011701001310702628', '33011701', '中河路上仓桥路2628', 2, NULL, 80, 0, NULL, 'OFF', '117.8202568', '30.6587426', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24337, '33011701001310702629', '33011701', '西湖大道建国路2629', 2, NULL, 80, 0, NULL, 'OFF', '125.2170745', '27.9713148', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24338, '33011701001310702630', '33011701', '平海路中河路2630', 2, NULL, 80, 0, NULL, 'OFF', '117.6246029', '32.8803465', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24339, '33011701001310702631', '33011701', '平海路延安路2631', 2, NULL, 80, 0, NULL, 'OFF', '117.4717915', '25.4877885', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24340, '33011701001310702632', '33011701', '解放路浣纱路2632', 2, NULL, 80, 0, NULL, 'OFF', '119.4851494', '33.7979032', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24341, '33011701001310702633', '33011701', '解放路建国路2633', 2, NULL, 80, 0, NULL, 'OFF', '130.010373', '27.526151', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24342, '33011701001310702634', '33011701', '钱江三桥秋涛路2634', 2, NULL, 80, 0, NULL, 'OFF', '116.5964175', '31.2370457', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24343, '33011701001310702635', '', 'testDevice2635', NULL, NULL, 80, 0, NULL, 'OFF', '117.950969', '28.6067756', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24344, '33011701001310702636', '', 'dasda2636', NULL, NULL, 80, 0, NULL, '', '124.9655931', '34.3227411', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24345, '33011701001310702637', '33011701', '吴山广场2637', 2, NULL, 80, 0, NULL, '', '115.975059', '30.7933793', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24346, '33011701001310702638', '33011701', '中河路上仓桥路2638', 2, NULL, 80, 0, NULL, 'OFF', '129.9785165', '25.9986735', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24347, '33011701001310702639', '33011701', '西湖大道建国路2639', 2, NULL, 80, 0, NULL, 'OFF', '126.967406', '32.6132299', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24348, '33011701001310702640', '33011701', '平海路中河路2640', 2, NULL, 80, 0, NULL, 'OFF', '129.9014813', '30.0701293', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24349, '33011701001310702641', '33011701', '平海路延安路2641', 2, NULL, 80, 0, NULL, 'OFF', '133.6051889', '27.5109569', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24350, '33011701001310702642', '33011701', '解放路浣纱路2642', 2, NULL, 80, 0, NULL, 'OFF', '123.3215012', '32.344397', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24351, '33011701001310702643', '33011701', '解放路建国路2643', 2, NULL, 80, 0, NULL, 'OFF', '120.7919399', '34.1891146', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24352, '33011701001310702644', '33011701', '钱江三桥秋涛路2644', 2, NULL, 80, 0, NULL, 'OFF', '118.9951968', '28.9123824', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24353, '33011701001310702645', '', 'testDevice2645', NULL, NULL, 80, 0, NULL, 'OFF', '117.6001647', '26.9945685', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24354, '33011701001310702646', '', 'dasda2646', NULL, NULL, 80, 0, NULL, '', '116.0152337', '33.2356954', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24355, '33011701001310702647', '33011701', '吴山广场2647', 2, NULL, 80, 0, NULL, '', '132.2756752', '30.1947719', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24356, '33011701001310702648', '33011701', '中河路上仓桥路2648', 2, NULL, 80, 0, NULL, 'OFF', '118.3326754', '26.2667735', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24357, '33011701001310702649', '33011701', '西湖大道建国路2649', 2, NULL, 80, 0, NULL, 'OFF', '119.836352', '25.7495524', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24358, '33011701001310702650', '33011701', '平海路中河路2650', 2, NULL, 80, 0, NULL, 'OFF', '129.1837345', '34.9474417', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24359, '33011701001310702651', '33011701', '平海路延安路2651', 2, NULL, 80, 0, NULL, 'OFF', '131.4096172', '32.4885516', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24360, '33011701001310702652', '33011701', '解放路浣纱路2652', 2, NULL, 80, 0, NULL, 'OFF', '134.4968832', '32.6004332', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24361, '33011701001310702653', '33011701', '解放路建国路2653', 2, NULL, 80, 0, NULL, 'OFF', '123.2555647', '30.5365115', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24362, '33011701001310702654', '33011701', '钱江三桥秋涛路2654', 2, NULL, 80, 0, NULL, 'OFF', '117.7871746', '29.8812584', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24363, '33011701001310702655', '', 'testDevice2655', NULL, NULL, 80, 0, NULL, 'OFF', '124.1691796', '32.7967577', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24364, '33011701001310702656', '', 'dasda2656', NULL, NULL, 80, 0, NULL, '', '132.4843746', '29.3400136', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24365, '33011701001310702657', '33011701', '吴山广场2657', 2, NULL, 80, 0, NULL, '', '134.914335', '33.3097952', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24366, '33011701001310702658', '33011701', '中河路上仓桥路2658', 2, NULL, 80, 0, NULL, 'OFF', '122.1185519', '33.5289356', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24367, '33011701001310702659', '33011701', '西湖大道建国路2659', 2, NULL, 80, 0, NULL, 'OFF', '130.849755', '32.7152926', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24368, '33011701001310702660', '33011701', '平海路中河路2660', 2, NULL, 80, 0, NULL, 'OFF', '132.8931201', '27.9896567', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24369, '33011701001310702661', '33011701', '平海路延安路2661', 2, NULL, 80, 0, NULL, 'OFF', '116.9163362', '26.802406', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24370, '33011701001310702662', '33011701', '解放路浣纱路2662', 2, NULL, 80, 0, NULL, 'OFF', '130.9023211', '25.0430603', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24371, '33011701001310702663', '33011701', '解放路建国路2663', 2, NULL, 80, 0, NULL, 'OFF', '128.7625975', '29.8080836', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24372, '33011701001310702664', '33011701', '钱江三桥秋涛路2664', 2, NULL, 80, 0, NULL, 'OFF', '116.1060247', '28.9112374', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24373, '33011701001310702665', '', 'testDevice2665', NULL, NULL, 80, 0, NULL, 'OFF', '119.2423316', '30.1319367', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24374, '33011701001310702666', '', 'dasda2666', NULL, NULL, 80, 0, NULL, '', '132.8935845', '28.9259715', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24375, '33011701001310702667', '33011701', '吴山广场2667', 2, NULL, 80, 0, NULL, '', '131.7409284', '29.2340476', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24376, '33011701001310702668', '33011701', '中河路上仓桥路2668', 2, NULL, 80, 0, NULL, 'OFF', '125.023889', '34.3923239', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24377, '33011701001310702669', '33011701', '西湖大道建国路2669', 2, NULL, 80, 0, NULL, 'OFF', '134.8966606', '29.2594771', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24378, '33011701001310702670', '33011701', '平海路中河路2670', 2, NULL, 80, 0, NULL, 'OFF', '124.4116366', '28.1204142', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24379, '33011701001310702671', '33011701', '平海路延安路2671', 2, NULL, 80, 0, NULL, 'OFF', '122.3682018', '27.8236398', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24380, '33011701001310702672', '33011701', '解放路浣纱路2672', 2, NULL, 80, 0, NULL, 'OFF', '123.6060997', '29.7569566', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24381, '33011701001310702673', '33011701', '解放路建国路2673', 2, NULL, 80, 0, NULL, 'OFF', '115.9258936', '30.3138641', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24382, '33011701001310702674', '33011701', '钱江三桥秋涛路2674', 2, NULL, 80, 0, NULL, 'OFF', '133.8111697', '27.2984511', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24383, '33011701001310702675', '', 'testDevice2675', NULL, NULL, 80, 0, NULL, 'OFF', '126.2781683', '30.5506636', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24384, '33011701001310702676', '', 'dasda2676', NULL, NULL, 80, 0, NULL, '', '134.9573331', '25.8579647', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24385, '33011701001310702677', '33011701', '吴山广场2677', 2, NULL, 80, 0, NULL, '', '120.9521612', '32.637833', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24386, '33011701001310702678', '33011701', '中河路上仓桥路2678', 2, NULL, 80, 0, NULL, 'OFF', '124.8888073', '30.6152711', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24387, '33011701001310702679', '33011701', '西湖大道建国路2679', 2, NULL, 80, 0, NULL, 'OFF', '126.5875534', '30.1628571', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24388, '33011701001310702680', '33011701', '平海路中河路2680', 2, NULL, 80, 0, NULL, 'OFF', '123.2713459', '33.9684725', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24389, '33011701001310702681', '33011701', '平海路延安路2681', 2, NULL, 80, 0, NULL, 'OFF', '121.5940698', '34.3537913', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24390, '33011701001310702682', '33011701', '解放路浣纱路2682', 2, NULL, 80, 0, NULL, 'OFF', '123.156312', '34.8635393', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24391, '33011701001310702683', '33011701', '解放路建国路2683', 2, NULL, 80, 0, NULL, 'OFF', '115.9993511', '26.2563229', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24392, '33011701001310702684', '33011701', '钱江三桥秋涛路2684', 2, NULL, 80, 0, NULL, 'OFF', '115.5278202', '31.6909968', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24393, '33011701001310702685', '', 'testDevice2685', NULL, NULL, 80, 0, NULL, 'OFF', '134.6410483', '34.6860157', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24394, '33011701001310702686', '', 'dasda2686', NULL, NULL, 80, 0, NULL, '', '131.6217814', '33.3570885', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24395, '33011701001310702687', '33011701', '吴山广场2687', 2, NULL, 80, 0, NULL, '', '119.1857628', '27.7273956', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24396, '33011701001310702688', '33011701', '中河路上仓桥路2688', 2, NULL, 80, 0, NULL, 'OFF', '126.0634703', '33.5657127', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24397, '33011701001310702689', '33011701', '西湖大道建国路2689', 2, NULL, 80, 0, NULL, 'OFF', '117.7600639', '29.6463771', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24398, '33011701001310702690', '33011701', '平海路中河路2690', 2, NULL, 80, 0, NULL, 'OFF', '115.6099092', '32.5347479', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24399, '33011701001310702691', '33011701', '平海路延安路2691', 2, NULL, 80, 0, NULL, 'OFF', '129.7693549', '28.7346085', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24400, '33011701001310702692', '33011701', '解放路浣纱路2692', 2, NULL, 80, 0, NULL, 'OFF', '127.0170474', '31.0687989', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24401, '33011701001310702693', '33011701', '解放路建国路2693', 2, NULL, 80, 0, NULL, 'OFF', '130.7771731', '34.1401694', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24402, '33011701001310702694', '33011701', '钱江三桥秋涛路2694', 2, NULL, 80, 0, NULL, 'OFF', '117.834724', '32.4944507', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24403, '33011701001310702695', '', 'testDevice2695', NULL, NULL, 80, 0, NULL, 'OFF', '121.8421011', '25.0517454', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24404, '33011701001310702696', '', 'dasda2696', NULL, NULL, 80, 0, NULL, '', '120.706334', '32.7753753', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24405, '33011701001310702697', '33011701', '吴山广场2697', 2, NULL, 80, 0, NULL, '', '123.0053674', '33.7216405', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24406, '33011701001310702698', '33011701', '中河路上仓桥路2698', 2, NULL, 80, 0, NULL, 'OFF', '117.9078356', '25.2820768', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24407, '33011701001310702699', '33011701', '西湖大道建国路2699', 2, NULL, 80, 0, NULL, 'OFF', '125.5230765', '30.2454629', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24408, '33011701001310702700', '33011701', '平海路中河路2700', 2, NULL, 80, 0, NULL, 'OFF', '118.8918764', '30.3810843', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24409, '33011701001310702701', '33011701', '平海路延安路2701', 2, NULL, 80, 0, NULL, 'OFF', '122.8901531', '26.1690331', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24410, '33011701001310702702', '33011701', '解放路浣纱路2702', 2, NULL, 80, 0, NULL, 'OFF', '122.7751369', '34.7019131', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24411, '33011701001310702703', '33011701', '解放路建国路2703', 2, NULL, 80, 0, NULL, 'OFF', '130.2052258', '30.0024665', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24412, '33011701001310702704', '33011701', '钱江三桥秋涛路2704', 2, NULL, 80, 0, NULL, 'OFF', '127.7007187', '30.9065937', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24413, '33011701001310702705', '', 'testDevice2705', NULL, NULL, 80, 0, NULL, 'OFF', '132.8879169', '29.5255689', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24414, '33011701001310702706', '', 'dasda2706', NULL, NULL, 80, 0, NULL, '', '126.337429', '29.9080641', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24415, '33011701001310702707', '33011701', '吴山广场2707', 2, NULL, 80, 0, NULL, '', '118.0233948', '25.9636138', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24416, '33011701001310702708', '33011701', '中河路上仓桥路2708', 2, NULL, 80, 0, NULL, 'OFF', '116.1046878', '25.0938772', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24417, '33011701001310702709', '33011701', '西湖大道建国路2709', 2, NULL, 80, 0, NULL, 'OFF', '131.4532549', '32.5785447', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24418, '33011701001310702710', '33011701', '平海路中河路2710', 2, NULL, 80, 0, NULL, 'OFF', '133.952212', '32.6110921', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24419, '33011701001310702711', '33011701', '平海路延安路2711', 2, NULL, 80, 0, NULL, 'OFF', '120.4012958', '30.319827', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24420, '33011701001310702712', '33011701', '解放路浣纱路2712', 2, NULL, 80, 0, NULL, 'OFF', '125.1498437', '28.765859', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24421, '33011701001310702713', '33011701', '解放路建国路2713', 2, NULL, 80, 0, NULL, 'OFF', '129.5453318', '27.8698143', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24422, '33011701001310702714', '33011701', '钱江三桥秋涛路2714', 2, NULL, 80, 0, NULL, 'OFF', '117.2771283', '28.0514949', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24423, '33011701001310702715', '', 'testDevice2715', NULL, NULL, 80, 0, NULL, 'OFF', '122.7496466', '31.6480318', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24424, '33011701001310702716', '', 'dasda2716', NULL, NULL, 80, 0, NULL, '', '126.9168488', '29.0856746', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24425, '33011701001310702717', '33011701', '吴山广场2717', 2, NULL, 80, 0, NULL, '', '131.335305', '25.4842777', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24426, '33011701001310702718', '33011701', '中河路上仓桥路2718', 2, NULL, 80, 0, NULL, 'OFF', '120.9259789', '25.1643654', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24427, '33011701001310702719', '33011701', '西湖大道建国路2719', 2, NULL, 80, 0, NULL, 'OFF', '115.6239803', '34.3689939', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24428, '33011701001310702720', '33011701', '平海路中河路2720', 2, NULL, 80, 0, NULL, 'OFF', '120.3419654', '31.3518735', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24429, '33011701001310702721', '33011701', '平海路延安路2721', 2, NULL, 80, 0, NULL, 'OFF', '119.8378869', '28.6523864', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24430, '33011701001310702722', '33011701', '解放路浣纱路2722', 2, NULL, 80, 0, NULL, 'OFF', '123.1635389', '34.2063119', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24431, '33011701001310702723', '33011701', '解放路建国路2723', 2, NULL, 80, 0, NULL, 'OFF', '121.3040342', '30.0744005', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24432, '33011701001310702724', '33011701', '钱江三桥秋涛路2724', 2, NULL, 80, 0, NULL, 'OFF', '122.0295549', '32.7530669', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24433, '33011701001310702725', '', 'testDevice2725', NULL, NULL, 80, 0, NULL, 'OFF', '131.2356725', '28.5421335', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24434, '33011701001310702726', '', 'dasda2726', NULL, NULL, 80, 0, NULL, '', '115.0896983', '29.451467', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24435, '33011701001310702727', '33011701', '吴山广场2727', 2, NULL, 80, 0, NULL, '', '126.7414747', '26.6309349', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24436, '33011701001310702728', '33011701', '中河路上仓桥路2728', 2, NULL, 80, 0, NULL, 'OFF', '133.4382791', '29.8002739', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24437, '33011701001310702729', '33011701', '西湖大道建国路2729', 2, NULL, 80, 0, NULL, 'OFF', '131.9669722', '34.1085649', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24438, '33011701001310702730', '33011701', '平海路中河路2730', 2, NULL, 80, 0, NULL, 'OFF', '124.5200243', '26.1420034', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24439, '33011701001310702731', '33011701', '平海路延安路2731', 2, NULL, 80, 0, NULL, 'OFF', '131.6992056', '33.3843224', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24440, '33011701001310702732', '33011701', '解放路浣纱路2732', 2, NULL, 80, 0, NULL, 'OFF', '129.9359557', '33.4956022', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24441, '33011701001310702733', '33011701', '解放路建国路2733', 2, NULL, 80, 0, NULL, 'OFF', '119.5821623', '32.3250441', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24442, '33011701001310702734', '33011701', '钱江三桥秋涛路2734', 2, NULL, 80, 0, NULL, 'OFF', '133.1029448', '26.138414', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24443, '33011701001310702735', '', 'testDevice2735', NULL, NULL, 80, 0, NULL, 'OFF', '131.768238', '28.7169382', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24444, '33011701001310702736', '', 'dasda2736', NULL, NULL, 80, 0, NULL, '', '124.532356', '30.1694491', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24445, '33011701001310702737', '33011701', '吴山广场2737', 2, NULL, 80, 0, NULL, '', '132.3570665', '29.6964314', '330102', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24446, '33011701001310702738', '33011701', '中河路上仓桥路2738', 2, NULL, 80, 0, NULL, 'OFF', '133.1882652', '32.9738098', '330105', '192.168.1.2', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24447, '33011701001310702739', '33011701', '西湖大道建国路2739', 2, NULL, 80, 0, NULL, 'OFF', '133.8701273', '30.7797551', '330104', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24448, '33011701001310702740', '33011701', '平海路中河路2740', 2, NULL, 80, 0, NULL, 'OFF', '134.7858412', '29.9773466', '330106', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24449, '33011701001310702741', '33011701', '平海路延安路2741', 2, NULL, 80, 0, NULL, 'OFF', '117.3188249', '32.547468', '330108', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24450, '33011701001310702742', '33011701', '解放路浣纱路2742', 2, NULL, 80, 0, NULL, 'OFF', '127.2366017', '27.8053005', '330109', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24451, '33011701001310702743', '33011701', '解放路建国路2743', 2, NULL, 80, 0, NULL, 'OFF', '129.2265341', '26.3840986', '330110', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24452, '33011701001310702744', '33011701', '钱江三桥秋涛路2744', 2, NULL, 80, 0, NULL, 'OFF', '129.4228662', '33.504592', '330111', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24453, '33011701001310702745', '', 'testDevice2745', NULL, NULL, 80, 0, NULL, 'OFF', '124.4347293', '33.3706643', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24454, '33011701001310702746', '', 'dasda2746', NULL, NULL, 80, 0, NULL, '', '118.9050486', '31.3395458', '', '192.168.1.1', 0, '2019-06-17 10:30:01', '2019-06-17 10:30:01');
INSERT INTO `vi_device_copy` VALUES (24455, '33011701001310702747', '33011701', '吴山广场2747', 2, NULL, 80, 0, NULL, '', '126.2210555', '31.5857364', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24456, '33011701001310702748', '33011701', '中河路上仓桥路2748', 2, NULL, 80, 0, NULL, 'OFF', '119.3901326', '28.9100449', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24457, '33011701001310702749', '33011701', '西湖大道建国路2749', 2, NULL, 80, 0, NULL, 'OFF', '123.2874971', '34.7930158', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24458, '33011701001310702750', '33011701', '平海路中河路2750', 2, NULL, 80, 0, NULL, 'OFF', '123.2670881', '32.2349446', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24459, '33011701001310702751', '33011701', '平海路延安路2751', 2, NULL, 80, 0, NULL, 'OFF', '131.4729497', '31.7956758', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24460, '33011701001310702752', '33011701', '解放路浣纱路2752', 2, NULL, 80, 0, NULL, 'OFF', '132.563485', '27.2735456', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24461, '33011701001310702753', '33011701', '解放路建国路2753', 2, NULL, 80, 0, NULL, 'OFF', '133.3985766', '25.9807011', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24462, '33011701001310702754', '33011701', '钱江三桥秋涛路2754', 2, NULL, 80, 0, NULL, 'OFF', '134.3024287', '33.0828687', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24463, '33011701001310702755', '', 'testDevice2755', NULL, NULL, 80, 0, NULL, 'OFF', '116.3164142', '32.4722408', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24464, '33011701001310702756', '', 'dasda2756', NULL, NULL, 80, 0, NULL, '', '123.6747855', '28.1125979', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24465, '33011701001310702757', '33011701', '吴山广场2757', 2, NULL, 80, 0, NULL, '', '134.4246855', '28.1462674', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24466, '33011701001310702758', '33011701', '中河路上仓桥路2758', 2, NULL, 80, 0, NULL, 'OFF', '126.0990718', '31.3935438', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24467, '33011701001310702759', '33011701', '西湖大道建国路2759', 2, NULL, 80, 0, NULL, 'OFF', '132.221303', '27.5289172', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24468, '33011701001310702760', '33011701', '平海路中河路2760', 2, NULL, 80, 0, NULL, 'OFF', '127.8093001', '28.4639549', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24469, '33011701001310702761', '33011701', '平海路延安路2761', 2, NULL, 80, 0, NULL, 'OFF', '127.382592', '34.7330229', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24470, '33011701001310702762', '33011701', '解放路浣纱路2762', 2, NULL, 80, 0, NULL, 'OFF', '118.4850606', '33.2732504', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24471, '33011701001310702763', '33011701', '解放路建国路2763', 2, NULL, 80, 0, NULL, 'OFF', '115.2775274', '27.1671836', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24472, '33011701001310702764', '33011701', '钱江三桥秋涛路2764', 2, NULL, 80, 0, NULL, 'OFF', '125.932456', '31.0161671', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24473, '33011701001310702765', '', 'testDevice2765', NULL, NULL, 80, 0, NULL, 'OFF', '128.8296983', '28.5792849', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24474, '33011701001310702766', '', 'dasda2766', NULL, NULL, 80, 0, NULL, '', '131.351124', '34.8479237', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24475, '33011701001310702767', '33011701', '吴山广场2767', 2, NULL, 80, 0, NULL, '', '115.266526', '33.501764', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24476, '33011701001310702768', '33011701', '中河路上仓桥路2768', 2, NULL, 80, 0, NULL, 'OFF', '127.2792585', '27.9650493', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24477, '33011701001310702769', '33011701', '西湖大道建国路2769', 2, NULL, 80, 0, NULL, 'OFF', '115.5967152', '34.3199548', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24478, '33011701001310702770', '33011701', '平海路中河路2770', 2, NULL, 80, 0, NULL, 'OFF', '121.1458009', '32.7046263', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24479, '33011701001310702771', '33011701', '平海路延安路2771', 2, NULL, 80, 0, NULL, 'OFF', '123.9388594', '25.5632675', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24480, '33011701001310702772', '33011701', '解放路浣纱路2772', 2, NULL, 80, 0, NULL, 'OFF', '121.2568953', '34.7024587', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24481, '33011701001310702773', '33011701', '解放路建国路2773', 2, NULL, 80, 0, NULL, 'OFF', '119.4678986', '31.8224915', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24482, '33011701001310702774', '33011701', '钱江三桥秋涛路2774', 2, NULL, 80, 0, NULL, 'OFF', '118.568808', '30.0050815', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24483, '33011701001310702775', '', 'testDevice2775', NULL, NULL, 80, 0, NULL, 'OFF', '119.4403448', '29.5579334', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24484, '33011701001310702776', '', 'dasda2776', NULL, NULL, 80, 0, NULL, '', '126.4953006', '32.7744228', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24485, '33011701001310702777', '33011701', '吴山广场2777', 2, NULL, 80, 0, NULL, '', '119.1554692', '30.1983142', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24486, '33011701001310702778', '33011701', '中河路上仓桥路2778', 2, NULL, 80, 0, NULL, 'OFF', '121.291445', '27.668303', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24487, '33011701001310702779', '33011701', '西湖大道建国路2779', 2, NULL, 80, 0, NULL, 'OFF', '133.9908178', '32.7465727', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24488, '33011701001310702780', '33011701', '平海路中河路2780', 2, NULL, 80, 0, NULL, 'OFF', '131.0797546', '25.7279549', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24489, '33011701001310702781', '33011701', '平海路延安路2781', 2, NULL, 80, 0, NULL, 'OFF', '118.4263201', '25.4000566', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24490, '33011701001310702782', '33011701', '解放路浣纱路2782', 2, NULL, 80, 0, NULL, 'OFF', '123.8923375', '34.8164184', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24491, '33011701001310702783', '33011701', '解放路建国路2783', 2, NULL, 80, 0, NULL, 'OFF', '129.1827276', '32.8819226', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24492, '33011701001310702784', '33011701', '钱江三桥秋涛路2784', 2, NULL, 80, 0, NULL, 'OFF', '119.2366263', '34.9603582', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24493, '33011701001310702785', '', 'testDevice2785', NULL, NULL, 80, 0, NULL, 'OFF', '133.6349492', '31.1560235', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24494, '33011701001310702786', '', 'dasda2786', NULL, NULL, 80, 0, NULL, '', '115.4648676', '25.8990434', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24495, '33011701001310702787', '33011701', '吴山广场2787', 2, NULL, 80, 0, NULL, '', '121.419491', '31.0271465', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24496, '33011701001310702788', '33011701', '中河路上仓桥路2788', 2, NULL, 80, 0, NULL, 'OFF', '125.702853', '32.4386027', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24497, '33011701001310702789', '33011701', '西湖大道建国路2789', 2, NULL, 80, 0, NULL, 'OFF', '129.2557927', '34.1115744', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24498, '33011701001310702790', '33011701', '平海路中河路2790', 2, NULL, 80, 0, NULL, 'OFF', '134.1704048', '28.2420641', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24499, '33011701001310702791', '33011701', '平海路延安路2791', 2, NULL, 80, 0, NULL, 'OFF', '128.0846468', '33.8755976', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24500, '33011701001310702792', '33011701', '解放路浣纱路2792', 2, NULL, 80, 0, NULL, 'OFF', '122.9120202', '29.6517959', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24501, '33011701001310702793', '33011701', '解放路建国路2793', 2, NULL, 80, 0, NULL, 'OFF', '115.3061613', '31.6321872', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24502, '33011701001310702794', '33011701', '钱江三桥秋涛路2794', 2, NULL, 80, 0, NULL, 'OFF', '132.7947466', '34.2055486', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24503, '33011701001310702795', '', 'testDevice2795', NULL, NULL, 80, 0, NULL, 'OFF', '123.0552496', '31.1311818', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24504, '33011701001310702796', '', 'dasda2796', NULL, NULL, 80, 0, NULL, '', '121.8920088', '28.0392636', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24505, '33011701001310702797', '33011701', '吴山广场2797', 2, NULL, 80, 0, NULL, '', '125.294296', '31.8027728', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24506, '33011701001310702798', '33011701', '中河路上仓桥路2798', 2, NULL, 80, 0, NULL, 'OFF', '125.7954541', '29.8960734', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24507, '33011701001310702799', '33011701', '西湖大道建国路2799', 2, NULL, 80, 0, NULL, 'OFF', '118.0943832', '29.0720491', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24508, '33011701001310702800', '33011701', '平海路中河路2800', 2, NULL, 80, 0, NULL, 'OFF', '118.0855541', '30.6720253', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24509, '33011701001310702801', '33011701', '平海路延安路2801', 2, NULL, 80, 0, NULL, 'OFF', '121.1446216', '31.1439798', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24510, '33011701001310702802', '33011701', '解放路浣纱路2802', 2, NULL, 80, 0, NULL, 'OFF', '116.4664463', '28.7038235', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24511, '33011701001310702803', '33011701', '解放路建国路2803', 2, NULL, 80, 0, NULL, 'OFF', '123.8983675', '25.0871781', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24512, '33011701001310702804', '33011701', '钱江三桥秋涛路2804', 2, NULL, 80, 0, NULL, 'OFF', '115.092499', '34.3244205', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24513, '33011701001310702805', '', 'testDevice2805', NULL, NULL, 80, 0, NULL, 'OFF', '128.7673932', '31.3605686', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24514, '33011701001310702806', '', 'dasda2806', NULL, NULL, 80, 0, NULL, '', '123.5594698', '28.8295819', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24515, '33011701001310702807', '33011701', '吴山广场2807', 2, NULL, 80, 0, NULL, '', '116.4951698', '25.0662037', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24516, '33011701001310702808', '33011701', '中河路上仓桥路2808', 2, NULL, 80, 0, NULL, 'OFF', '116.7974405', '33.8422731', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24517, '33011701001310702809', '33011701', '西湖大道建国路2809', 2, NULL, 80, 0, NULL, 'OFF', '119.5016935', '29.0127548', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24518, '33011701001310702810', '33011701', '平海路中河路2810', 2, NULL, 80, 0, NULL, 'OFF', '132.1161466', '28.536955', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24519, '33011701001310702811', '33011701', '平海路延安路2811', 2, NULL, 80, 0, NULL, 'OFF', '127.0756529', '30.646511', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24520, '33011701001310702812', '33011701', '解放路浣纱路2812', 2, NULL, 80, 0, NULL, 'OFF', '124.0298257', '32.6216904', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24521, '33011701001310702813', '33011701', '解放路建国路2813', 2, NULL, 80, 0, NULL, 'OFF', '123.9221701', '26.1689191', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24522, '33011701001310702814', '33011701', '钱江三桥秋涛路2814', 2, NULL, 80, 0, NULL, 'OFF', '132.521374', '27.9795249', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24523, '33011701001310702815', '', 'testDevice2815', NULL, NULL, 80, 0, NULL, 'OFF', '115.8403605', '26.3908672', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24524, '33011701001310702816', '', 'dasda2816', NULL, NULL, 80, 0, NULL, '', '126.637681', '33.0157615', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24525, '33011701001310702817', '33011701', '吴山广场2817', 2, NULL, 80, 0, NULL, '', '130.6673241', '30.9062065', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24526, '33011701001310702818', '33011701', '中河路上仓桥路2818', 2, NULL, 80, 0, NULL, 'OFF', '118.4235781', '30.4837483', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24527, '33011701001310702819', '33011701', '西湖大道建国路2819', 2, NULL, 80, 0, NULL, 'OFF', '125.115919', '34.7001225', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24528, '33011701001310702820', '33011701', '平海路中河路2820', 2, NULL, 80, 0, NULL, 'OFF', '115.3088614', '27.0493675', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24529, '33011701001310702821', '33011701', '平海路延安路2821', 2, NULL, 80, 0, NULL, 'OFF', '126.1965506', '26.1464706', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24530, '33011701001310702822', '33011701', '解放路浣纱路2822', 2, NULL, 80, 0, NULL, 'OFF', '130.0561691', '34.5842509', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24531, '33011701001310702823', '33011701', '解放路建国路2823', 2, NULL, 80, 0, NULL, 'OFF', '116.6911946', '29.4818429', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24532, '33011701001310702824', '33011701', '钱江三桥秋涛路2824', 2, NULL, 80, 0, NULL, 'OFF', '118.2874661', '28.6564621', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24533, '33011701001310702825', '', 'testDevice2825', NULL, NULL, 80, 0, NULL, 'OFF', '126.3637474', '29.8367821', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24534, '33011701001310702826', '', 'dasda2826', NULL, NULL, 80, 0, NULL, '', '121.9563393', '28.2145247', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24535, '33011701001310702827', '33011701', '吴山广场2827', 2, NULL, 80, 0, NULL, '', '115.6904548', '26.5622772', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24536, '33011701001310702828', '33011701', '中河路上仓桥路2828', 2, NULL, 80, 0, NULL, 'OFF', '117.583257', '33.1678125', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24537, '33011701001310702829', '33011701', '西湖大道建国路2829', 2, NULL, 80, 0, NULL, 'OFF', '125.844921', '31.1522312', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24538, '33011701001310702830', '33011701', '平海路中河路2830', 2, NULL, 80, 0, NULL, 'OFF', '121.4748346', '31.2577189', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24539, '33011701001310702831', '33011701', '平海路延安路2831', 2, NULL, 80, 0, NULL, 'OFF', '134.8394109', '27.831901', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24540, '33011701001310702832', '33011701', '解放路浣纱路2832', 2, NULL, 80, 0, NULL, 'OFF', '134.772551', '30.3863485', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24541, '33011701001310702833', '33011701', '解放路建国路2833', 2, NULL, 80, 0, NULL, 'OFF', '134.3445232', '33.4360401', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24542, '33011701001310702834', '33011701', '钱江三桥秋涛路2834', 2, NULL, 80, 0, NULL, 'OFF', '132.4049635', '31.0211552', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24543, '33011701001310702835', '', 'testDevice2835', NULL, NULL, 80, 0, NULL, 'OFF', '123.9912484', '29.7976558', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24544, '33011701001310702836', '', 'dasda2836', NULL, NULL, 80, 0, NULL, '', '127.741352', '30.924814', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24545, '33011701001310702837', '33011701', '吴山广场2837', 2, NULL, 80, 0, NULL, '', '131.7330155', '30.2311027', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24546, '33011701001310702838', '33011701', '中河路上仓桥路2838', 2, NULL, 80, 0, NULL, 'OFF', '120.4410223', '33.381072', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24547, '33011701001310702839', '33011701', '西湖大道建国路2839', 2, NULL, 80, 0, NULL, 'OFF', '132.0060654', '31.2120523', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24548, '33011701001310702840', '33011701', '平海路中河路2840', 2, NULL, 80, 0, NULL, 'OFF', '123.7072609', '30.9170456', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24549, '33011701001310702841', '33011701', '平海路延安路2841', 2, NULL, 80, 0, NULL, 'OFF', '127.5181086', '25.9490716', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24550, '33011701001310702842', '33011701', '解放路浣纱路2842', 2, NULL, 80, 0, NULL, 'OFF', '131.4687612', '31.9942215', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24551, '33011701001310702843', '33011701', '解放路建国路2843', 2, NULL, 80, 0, NULL, 'OFF', '119.7894808', '27.1238931', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24552, '33011701001310702844', '33011701', '钱江三桥秋涛路2844', 2, NULL, 80, 0, NULL, 'OFF', '129.5411208', '34.6368012', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24553, '33011701001310702845', '', 'testDevice2845', NULL, NULL, 80, 0, NULL, 'OFF', '133.3371623', '26.8123269', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24554, '33011701001310702846', '', 'dasda2846', NULL, NULL, 80, 0, NULL, '', '123.0624496', '25.1512311', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24555, '33011701001310702847', '33011701', '吴山广场2847', 2, NULL, 80, 0, NULL, '', '120.3007618', '30.3191752', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24556, '33011701001310702848', '33011701', '中河路上仓桥路2848', 2, NULL, 80, 0, NULL, 'OFF', '117.3164607', '31.1421829', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24557, '33011701001310702849', '33011701', '西湖大道建国路2849', 2, NULL, 80, 0, NULL, 'OFF', '130.6800187', '29.7533892', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24558, '33011701001310702850', '33011701', '平海路中河路2850', 2, NULL, 80, 0, NULL, 'OFF', '126.4507119', '30.3403979', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24559, '33011701001310702851', '33011701', '平海路延安路2851', 2, NULL, 80, 0, NULL, 'OFF', '125.2135042', '27.4418219', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24560, '33011701001310702852', '33011701', '解放路浣纱路2852', 2, NULL, 80, 0, NULL, 'OFF', '131.7153858', '31.1879161', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24561, '33011701001310702853', '33011701', '解放路建国路2853', 2, NULL, 80, 0, NULL, 'OFF', '127.9364171', '28.614115', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24562, '33011701001310702854', '33011701', '钱江三桥秋涛路2854', 2, NULL, 80, 0, NULL, 'OFF', '129.5359285', '34.5068272', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24563, '33011701001310702855', '', 'testDevice2855', NULL, NULL, 80, 0, NULL, 'OFF', '128.8703921', '31.6917912', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24564, '33011701001310702856', '', 'dasda2856', NULL, NULL, 80, 0, NULL, '', '120.7441754', '29.9384747', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24565, '33011701001310702857', '33011701', '吴山广场2857', 2, NULL, 80, 0, NULL, '', '122.1097015', '29.6170003', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24566, '33011701001310702858', '33011701', '中河路上仓桥路2858', 2, NULL, 80, 0, NULL, 'OFF', '133.315982', '33.2695775', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24567, '33011701001310702859', '33011701', '西湖大道建国路2859', 2, NULL, 80, 0, NULL, 'OFF', '125.2508058', '32.4968869', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24568, '33011701001310702860', '33011701', '平海路中河路2860', 2, NULL, 80, 0, NULL, 'OFF', '131.3060839', '27.6757024', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24569, '33011701001310702861', '33011701', '平海路延安路2861', 2, NULL, 80, 0, NULL, 'OFF', '125.7780026', '25.8878516', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24570, '33011701001310702862', '33011701', '解放路浣纱路2862', 2, NULL, 80, 0, NULL, 'OFF', '119.9717618', '31.4121512', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24571, '33011701001310702863', '33011701', '解放路建国路2863', 2, NULL, 80, 0, NULL, 'OFF', '127.5248017', '34.3972016', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24572, '33011701001310702864', '33011701', '钱江三桥秋涛路2864', 2, NULL, 80, 0, NULL, 'OFF', '122.7087241', '32.7495548', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24573, '33011701001310702865', '', 'testDevice2865', NULL, NULL, 80, 0, NULL, 'OFF', '115.9692157', '25.5561694', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24574, '33011701001310702866', '', 'dasda2866', NULL, NULL, 80, 0, NULL, '', '116.7199067', '34.5321829', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24575, '33011701001310702867', '33011701', '吴山广场2867', 2, NULL, 80, 0, NULL, '', '120.6918872', '30.9924068', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24576, '33011701001310702868', '33011701', '中河路上仓桥路2868', 2, NULL, 80, 0, NULL, 'OFF', '118.2997164', '26.3654854', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24577, '33011701001310702869', '33011701', '西湖大道建国路2869', 2, NULL, 80, 0, NULL, 'OFF', '134.4229211', '33.8502069', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24578, '33011701001310702870', '33011701', '平海路中河路2870', 2, NULL, 80, 0, NULL, 'OFF', '122.2154571', '25.1545785', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24579, '33011701001310702871', '33011701', '平海路延安路2871', 2, NULL, 80, 0, NULL, 'OFF', '132.8085227', '29.2222721', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24580, '33011701001310702872', '33011701', '解放路浣纱路2872', 2, NULL, 80, 0, NULL, 'OFF', '122.3962427', '25.6476255', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24581, '33011701001310702873', '33011701', '解放路建国路2873', 2, NULL, 80, 0, NULL, 'OFF', '118.555646', '25.5713114', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24582, '33011701001310702874', '33011701', '钱江三桥秋涛路2874', 2, NULL, 80, 0, NULL, 'OFF', '130.5895026', '25.9136808', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24583, '33011701001310702875', '', 'testDevice2875', NULL, NULL, 80, 0, NULL, 'OFF', '122.2805755', '27.8544703', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24584, '33011701001310702876', '', 'dasda2876', NULL, NULL, 80, 0, NULL, '', '124.6343704', '26.5313094', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24585, '33011701001310702877', '33011701', '吴山广场2877', 2, NULL, 80, 0, NULL, '', '121.3301262', '34.0931365', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24586, '33011701001310702878', '33011701', '中河路上仓桥路2878', 2, NULL, 80, 0, NULL, 'OFF', '117.7475203', '25.8717543', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24587, '33011701001310702879', '33011701', '西湖大道建国路2879', 2, NULL, 80, 0, NULL, 'OFF', '129.7472236', '32.0793625', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24588, '33011701001310702880', '33011701', '平海路中河路2880', 2, NULL, 80, 0, NULL, 'OFF', '120.4935579', '27.7815499', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24589, '33011701001310702881', '33011701', '平海路延安路2881', 2, NULL, 80, 0, NULL, 'OFF', '118.226119', '27.6696624', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24590, '33011701001310702882', '33011701', '解放路浣纱路2882', 2, NULL, 80, 0, NULL, 'OFF', '134.6499221', '30.0036624', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24591, '33011701001310702883', '33011701', '解放路建国路2883', 2, NULL, 80, 0, NULL, 'OFF', '123.5712542', '32.009325', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24592, '33011701001310702884', '33011701', '钱江三桥秋涛路2884', 2, NULL, 80, 0, NULL, 'OFF', '118.906505', '25.0356383', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24593, '33011701001310702885', '', 'testDevice2885', NULL, NULL, 80, 0, NULL, 'OFF', '128.8187632', '34.1502166', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24594, '33011701001310702886', '', 'dasda2886', NULL, NULL, 80, 0, NULL, '', '132.3743018', '30.6441687', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24595, '33011701001310702887', '33011701', '吴山广场2887', 2, NULL, 80, 0, NULL, '', '120.4152198', '25.7701938', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24596, '33011701001310702888', '33011701', '中河路上仓桥路2888', 2, NULL, 80, 0, NULL, 'OFF', '129.9531944', '31.9184631', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24597, '33011701001310702889', '33011701', '西湖大道建国路2889', 2, NULL, 80, 0, NULL, 'OFF', '133.5203132', '27.2817346', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24598, '33011701001310702890', '33011701', '平海路中河路2890', 2, NULL, 80, 0, NULL, 'OFF', '122.7419834', '25.6532841', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24599, '33011701001310702891', '33011701', '平海路延安路2891', 2, NULL, 80, 0, NULL, 'OFF', '118.1489782', '31.421217', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24600, '33011701001310702892', '33011701', '解放路浣纱路2892', 2, NULL, 80, 0, NULL, 'OFF', '127.5189412', '25.1462329', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24601, '33011701001310702893', '33011701', '解放路建国路2893', 2, NULL, 80, 0, NULL, 'OFF', '128.1477721', '26.4675137', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24602, '33011701001310702894', '33011701', '钱江三桥秋涛路2894', 2, NULL, 80, 0, NULL, 'OFF', '123.1820376', '31.8988703', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24603, '33011701001310702895', '', 'testDevice2895', NULL, NULL, 80, 0, NULL, 'OFF', '116.466872', '25.0918105', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24604, '33011701001310702896', '', 'dasda2896', NULL, NULL, 80, 0, NULL, '', '117.7882482', '34.7624419', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24605, '33011701001310702897', '33011701', '吴山广场2897', 2, NULL, 80, 0, NULL, '', '124.5406253', '33.5367782', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24606, '33011701001310702898', '33011701', '中河路上仓桥路2898', 2, NULL, 80, 0, NULL, 'OFF', '134.3383825', '28.3965659', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24607, '33011701001310702899', '33011701', '西湖大道建国路2899', 2, NULL, 80, 0, NULL, 'OFF', '123.0700375', '26.3724953', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24608, '33011701001310702900', '33011701', '平海路中河路2900', 2, NULL, 80, 0, NULL, 'OFF', '117.3350403', '31.6727789', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24609, '33011701001310702901', '33011701', '平海路延安路2901', 2, NULL, 80, 0, NULL, 'OFF', '122.4650899', '34.2464089', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24610, '33011701001310702902', '33011701', '解放路浣纱路2902', 2, NULL, 80, 0, NULL, 'OFF', '125.3203289', '31.2137083', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24611, '33011701001310702903', '33011701', '解放路建国路2903', 2, NULL, 80, 0, NULL, 'OFF', '124.2063758', '28.3293152', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24612, '33011701001310702904', '33011701', '钱江三桥秋涛路2904', 2, NULL, 80, 0, NULL, 'OFF', '130.0708926', '33.0054513', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24613, '33011701001310702905', '', 'testDevice2905', NULL, NULL, 80, 0, NULL, 'OFF', '122.7353363', '25.0393112', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24614, '33011701001310702906', '', 'dasda2906', NULL, NULL, 80, 0, NULL, '', '128.4640045', '31.1802023', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24615, '33011701001310702907', '33011701', '吴山广场2907', 2, NULL, 80, 0, NULL, '', '119.114014', '25.7830782', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24616, '33011701001310702908', '33011701', '中河路上仓桥路2908', 2, NULL, 80, 0, NULL, 'OFF', '115.1780573', '30.3747844', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24617, '33011701001310702909', '33011701', '西湖大道建国路2909', 2, NULL, 80, 0, NULL, 'OFF', '123.548245', '29.5246876', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24618, '33011701001310702910', '33011701', '平海路中河路2910', 2, NULL, 80, 0, NULL, 'OFF', '117.2070537', '31.4990852', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24619, '33011701001310702911', '33011701', '平海路延安路2911', 2, NULL, 80, 0, NULL, 'OFF', '120.3905341', '33.9213634', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24620, '33011701001310702912', '33011701', '解放路浣纱路2912', 2, NULL, 80, 0, NULL, 'OFF', '115.3315102', '30.1095616', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24621, '33011701001310702913', '33011701', '解放路建国路2913', 2, NULL, 80, 0, NULL, 'OFF', '120.485949', '33.7837182', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24622, '33011701001310702914', '33011701', '钱江三桥秋涛路2914', 2, NULL, 80, 0, NULL, 'OFF', '121.4352152', '33.5899064', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24623, '33011701001310702915', '', 'testDevice2915', NULL, NULL, 80, 0, NULL, 'OFF', '130.7182297', '31.5983778', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24624, '33011701001310702916', '', 'dasda2916', NULL, NULL, 80, 0, NULL, '', '134.2855036', '32.2221702', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24625, '33011701001310702917', '33011701', '吴山广场2917', 2, NULL, 80, 0, NULL, '', '124.2728295', '31.3157177', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24626, '33011701001310702918', '33011701', '中河路上仓桥路2918', 2, NULL, 80, 0, NULL, 'OFF', '123.5076373', '34.9120781', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24627, '33011701001310702919', '33011701', '西湖大道建国路2919', 2, NULL, 80, 0, NULL, 'OFF', '129.7196985', '25.6132379', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24628, '33011701001310702920', '33011701', '平海路中河路2920', 2, NULL, 80, 0, NULL, 'OFF', '123.0755812', '28.3299556', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24629, '33011701001310702921', '33011701', '平海路延安路2921', 2, NULL, 80, 0, NULL, 'OFF', '131.2188111', '29.8100647', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24630, '33011701001310702922', '33011701', '解放路浣纱路2922', 2, NULL, 80, 0, NULL, 'OFF', '131.8673125', '29.0604567', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24631, '33011701001310702923', '33011701', '解放路建国路2923', 2, NULL, 80, 0, NULL, 'OFF', '130.6801298', '30.8720898', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24632, '33011701001310702924', '33011701', '钱江三桥秋涛路2924', 2, NULL, 80, 0, NULL, 'OFF', '122.7987122', '32.179079', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24633, '33011701001310702925', '', 'testDevice2925', NULL, NULL, 80, 0, NULL, 'OFF', '126.953172', '33.2791263', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24634, '33011701001310702926', '', 'dasda2926', NULL, NULL, 80, 0, NULL, '', '131.3697243', '34.8583945', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24635, '33011701001310702927', '33011701', '吴山广场2927', 2, NULL, 80, 0, NULL, '', '120.9891061', '29.4545938', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24636, '33011701001310702928', '33011701', '中河路上仓桥路2928', 2, NULL, 80, 0, NULL, 'OFF', '115.8363582', '27.6977861', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24637, '33011701001310702929', '33011701', '西湖大道建国路2929', 2, NULL, 80, 0, NULL, 'OFF', '121.2144734', '25.1251494', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24638, '33011701001310702930', '33011701', '平海路中河路2930', 2, NULL, 80, 0, NULL, 'OFF', '123.5632928', '27.532389', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24639, '33011701001310702931', '33011701', '平海路延安路2931', 2, NULL, 80, 0, NULL, 'OFF', '119.1730444', '27.2864969', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24640, '33011701001310702932', '33011701', '解放路浣纱路2932', 2, NULL, 80, 0, NULL, 'OFF', '130.1753444', '28.8353179', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24641, '33011701001310702933', '33011701', '解放路建国路2933', 2, NULL, 80, 0, NULL, 'OFF', '118.3575892', '27.3170992', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24642, '33011701001310702934', '33011701', '钱江三桥秋涛路2934', 2, NULL, 80, 0, NULL, 'OFF', '126.2619134', '25.0795427', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24643, '33011701001310702935', '', 'testDevice2935', NULL, NULL, 80, 0, NULL, 'OFF', '121.2368001', '28.446416', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24644, '33011701001310702936', '', 'dasda2936', NULL, NULL, 80, 0, NULL, '', '132.3982608', '31.9934523', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24645, '33011701001310702937', '33011701', '吴山广场2937', 2, NULL, 80, 0, NULL, '', '123.2809044', '29.6280139', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24646, '33011701001310702938', '33011701', '中河路上仓桥路2938', 2, NULL, 80, 0, NULL, 'OFF', '124.20974', '27.1597129', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24647, '33011701001310702939', '33011701', '西湖大道建国路2939', 2, NULL, 80, 0, NULL, 'OFF', '116.2059877', '31.9145229', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24648, '33011701001310702940', '33011701', '平海路中河路2940', 2, NULL, 80, 0, NULL, 'OFF', '133.4007188', '33.0934763', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24649, '33011701001310702941', '33011701', '平海路延安路2941', 2, NULL, 80, 0, NULL, 'OFF', '123.3856317', '34.723813', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24650, '33011701001310702942', '33011701', '解放路浣纱路2942', 2, NULL, 80, 0, NULL, 'OFF', '121.7260027', '29.3386364', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24651, '33011701001310702943', '33011701', '解放路建国路2943', 2, NULL, 80, 0, NULL, 'OFF', '123.4731192', '27.5217432', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24652, '33011701001310702944', '33011701', '钱江三桥秋涛路2944', 2, NULL, 80, 0, NULL, 'OFF', '117.1875882', '34.5928073', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24653, '33011701001310702945', '', 'testDevice2945', NULL, NULL, 80, 0, NULL, 'OFF', '120.5185843', '25.3988072', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24654, '33011701001310702946', '', 'dasda2946', NULL, NULL, 80, 0, NULL, '', '116.0301574', '28.2156145', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24655, '33011701001310702947', '33011701', '吴山广场2947', 2, NULL, 80, 0, NULL, '', '123.5950346', '29.8816511', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24656, '33011701001310702948', '33011701', '中河路上仓桥路2948', 2, NULL, 80, 0, NULL, 'OFF', '134.8847016', '29.7614121', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24657, '33011701001310702949', '33011701', '西湖大道建国路2949', 2, NULL, 80, 0, NULL, 'OFF', '128.6384046', '34.1621077', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24658, '33011701001310702950', '33011701', '平海路中河路2950', 2, NULL, 80, 0, NULL, 'OFF', '123.5379191', '26.5263025', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24659, '33011701001310702951', '33011701', '平海路延安路2951', 2, NULL, 80, 0, NULL, 'OFF', '116.7743821', '25.1451897', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24660, '33011701001310702952', '33011701', '解放路浣纱路2952', 2, NULL, 80, 0, NULL, 'OFF', '118.2581539', '31.1470413', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24661, '33011701001310702953', '33011701', '解放路建国路2953', 2, NULL, 80, 0, NULL, 'OFF', '125.9676238', '25.2996377', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24662, '33011701001310702954', '33011701', '钱江三桥秋涛路2954', 2, NULL, 80, 0, NULL, 'OFF', '120.0636581', '28.0570648', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24663, '33011701001310702955', '', 'testDevice2955', NULL, NULL, 80, 0, NULL, 'OFF', '127.4154196', '29.3864111', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24664, '33011701001310702956', '', 'dasda2956', NULL, NULL, 80, 0, NULL, '', '121.8861242', '27.7608615', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24665, '33011701001310702957', '33011701', '吴山广场2957', 2, NULL, 80, 0, NULL, '', '132.1843631', '25.6450743', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24666, '33011701001310702958', '33011701', '中河路上仓桥路2958', 2, NULL, 80, 0, NULL, 'OFF', '120.2634433', '29.9427876', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24667, '33011701001310702959', '33011701', '西湖大道建国路2959', 2, NULL, 80, 0, NULL, 'OFF', '129.7641278', '27.7787154', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24668, '33011701001310702960', '33011701', '平海路中河路2960', 2, NULL, 80, 0, NULL, 'OFF', '133.0303096', '34.0652143', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24669, '33011701001310702961', '33011701', '平海路延安路2961', 2, NULL, 80, 0, NULL, 'OFF', '120.8591653', '31.9899257', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24670, '33011701001310702962', '33011701', '解放路浣纱路2962', 2, NULL, 80, 0, NULL, 'OFF', '130.2048984', '32.7539858', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24671, '33011701001310702963', '33011701', '解放路建国路2963', 2, NULL, 80, 0, NULL, 'OFF', '133.4469969', '32.8001524', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24672, '33011701001310702964', '33011701', '钱江三桥秋涛路2964', 2, NULL, 80, 0, NULL, 'OFF', '121.6202897', '30.7388047', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24673, '33011701001310702965', '', 'testDevice2965', NULL, NULL, 80, 0, NULL, 'OFF', '132.7604583', '30.2935668', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24674, '33011701001310702966', '', 'dasda2966', NULL, NULL, 80, 0, NULL, '', '123.9414232', '34.25142', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24675, '33011701001310702967', '33011701', '吴山广场2967', 2, NULL, 80, 0, NULL, '', '126.4257418', '25.3764001', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24676, '33011701001310702968', '33011701', '中河路上仓桥路2968', 2, NULL, 80, 0, NULL, 'OFF', '125.3044399', '29.1277409', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24677, '33011701001310702969', '33011701', '西湖大道建国路2969', 2, NULL, 80, 0, NULL, 'OFF', '132.2449746', '34.5095044', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24678, '33011701001310702970', '33011701', '平海路中河路2970', 2, NULL, 80, 0, NULL, 'OFF', '130.3115541', '30.1642996', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24679, '33011701001310702971', '33011701', '平海路延安路2971', 2, NULL, 80, 0, NULL, 'OFF', '119.8228474', '32.2929851', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24680, '33011701001310702972', '33011701', '解放路浣纱路2972', 2, NULL, 80, 0, NULL, 'OFF', '133.1795754', '25.9720271', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24681, '33011701001310702973', '33011701', '解放路建国路2973', 2, NULL, 80, 0, NULL, 'OFF', '131.4293352', '27.9811805', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24682, '33011701001310702974', '33011701', '钱江三桥秋涛路2974', 2, NULL, 80, 0, NULL, 'OFF', '122.6079503', '26.9898214', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24683, '33011701001310702975', '', 'testDevice2975', NULL, NULL, 80, 0, NULL, 'OFF', '123.7517465', '26.0055659', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24684, '33011701001310702976', '', 'dasda2976', NULL, NULL, 80, 0, NULL, '', '115.9348825', '34.0583656', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24685, '33011701001310702977', '33011701', '吴山广场2977', 2, NULL, 80, 0, NULL, '', '133.4191733', '27.2751306', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24686, '33011701001310702978', '33011701', '中河路上仓桥路2978', 2, NULL, 80, 0, NULL, 'OFF', '124.2912198', '29.2005566', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24687, '33011701001310702979', '33011701', '西湖大道建国路2979', 2, NULL, 80, 0, NULL, 'OFF', '126.1985798', '29.1773914', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24688, '33011701001310702980', '33011701', '平海路中河路2980', 2, NULL, 80, 0, NULL, 'OFF', '123.1192402', '33.2852875', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24689, '33011701001310702981', '33011701', '平海路延安路2981', 2, NULL, 80, 0, NULL, 'OFF', '122.000462', '33.8942637', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24690, '33011701001310702982', '33011701', '解放路浣纱路2982', 2, NULL, 80, 0, NULL, 'OFF', '125.6445902', '34.6154561', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24691, '33011701001310702983', '33011701', '解放路建国路2983', 2, NULL, 80, 0, NULL, 'OFF', '127.2215658', '26.3944897', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24692, '33011701001310702984', '33011701', '钱江三桥秋涛路2984', 2, NULL, 80, 0, NULL, 'OFF', '124.1740588', '33.1260804', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24693, '33011701001310702985', '', 'testDevice2985', NULL, NULL, 80, 0, NULL, 'OFF', '124.2055971', '31.4469332', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24694, '33011701001310702986', '', 'dasda2986', NULL, NULL, 80, 0, NULL, '', '133.5058098', '32.8564254', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24695, '33011701001310702987', '33011701', '吴山广场2987', 2, NULL, 80, 0, NULL, '', '119.9122582', '34.9413276', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24696, '33011701001310702988', '33011701', '中河路上仓桥路2988', 2, NULL, 80, 0, NULL, 'OFF', '124.0438623', '31.1373623', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24697, '33011701001310702989', '33011701', '西湖大道建国路2989', 2, NULL, 80, 0, NULL, 'OFF', '125.4825376', '25.8628288', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24698, '33011701001310702990', '33011701', '平海路中河路2990', 2, NULL, 80, 0, NULL, 'OFF', '120.2811018', '30.9020575', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24699, '33011701001310702991', '33011701', '平海路延安路2991', 2, NULL, 80, 0, NULL, 'OFF', '129.9578966', '31.9218012', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24700, '33011701001310702992', '33011701', '解放路浣纱路2992', 2, NULL, 80, 0, NULL, 'OFF', '133.9461782', '31.9028338', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24701, '33011701001310702993', '33011701', '解放路建国路2993', 2, NULL, 80, 0, NULL, 'OFF', '124.8572019', '28.7487658', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24702, '33011701001310702994', '33011701', '钱江三桥秋涛路2994', 2, NULL, 80, 0, NULL, 'OFF', '127.4474754', '33.0353281', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24703, '33011701001310702995', '', 'testDevice2995', NULL, NULL, 80, 0, NULL, 'OFF', '127.6657721', '33.9303432', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24704, '33011701001310702996', '', 'dasda2996', NULL, NULL, 80, 0, NULL, '', '120.9864349', '25.5457321', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24705, '33011701001310702997', '33011701', '吴山广场2997', 2, NULL, 80, 0, NULL, '', '126.9348588', '30.9376314', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24706, '33011701001310702998', '33011701', '中河路上仓桥路2998', 2, NULL, 80, 0, NULL, 'OFF', '116.7149897', '33.0509608', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24707, '33011701001310702999', '33011701', '西湖大道建国路2999', 2, NULL, 80, 0, NULL, 'OFF', '127.7703729', '27.44191', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24708, '33011701001310703000', '33011701', '平海路中河路3000', 2, NULL, 80, 0, NULL, 'OFF', '133.706896', '33.0566678', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24709, '33011701001310703001', '33011701', '平海路延安路3001', 2, NULL, 80, 0, NULL, 'OFF', '130.2233618', '27.9576096', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24710, '33011701001310703002', '33011701', '解放路浣纱路3002', 2, NULL, 80, 0, NULL, 'OFF', '134.9961219', '25.6180447', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24711, '33011701001310703003', '33011701', '解放路建国路3003', 2, NULL, 80, 0, NULL, 'OFF', '129.3105245', '29.2173949', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24712, '33011701001310703004', '33011701', '钱江三桥秋涛路3004', 2, NULL, 80, 0, NULL, 'OFF', '126.5642574', '34.232841', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24713, '33011701001310703005', '', 'testDevice3005', NULL, NULL, 80, 0, NULL, 'OFF', '129.889714', '28.5120207', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24714, '33011701001310703006', '', 'dasda3006', NULL, NULL, 80, 0, NULL, '', '134.7557985', '34.8615805', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24715, '33011701001310703007', '33011701', '吴山广场3007', 2, NULL, 80, 0, NULL, '', '129.109851', '33.7718408', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24716, '33011701001310703008', '33011701', '中河路上仓桥路3008', 2, NULL, 80, 0, NULL, 'OFF', '126.2818601', '29.274463', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24717, '33011701001310703009', '33011701', '西湖大道建国路3009', 2, NULL, 80, 0, NULL, 'OFF', '129.0797484', '30.0567928', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24718, '33011701001310703010', '33011701', '平海路中河路3010', 2, NULL, 80, 0, NULL, 'OFF', '131.5531621', '27.4605755', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24719, '33011701001310703011', '33011701', '平海路延安路3011', 2, NULL, 80, 0, NULL, 'OFF', '115.526566', '32.1324993', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24720, '33011701001310703012', '33011701', '解放路浣纱路3012', 2, NULL, 80, 0, NULL, 'OFF', '127.9733443', '33.2807701', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24721, '33011701001310703013', '33011701', '解放路建国路3013', 2, NULL, 80, 0, NULL, 'OFF', '118.2870241', '25.0063529', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24722, '33011701001310703014', '33011701', '钱江三桥秋涛路3014', 2, NULL, 80, 0, NULL, 'OFF', '132.5150883', '30.1894548', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24723, '33011701001310703015', '', 'testDevice3015', NULL, NULL, 80, 0, NULL, 'OFF', '132.7143696', '30.9282154', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24724, '33011701001310703016', '', 'dasda3016', NULL, NULL, 80, 0, NULL, '', '131.0265838', '29.072713', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24725, '33011701001310703017', '33011701', '吴山广场3017', 2, NULL, 80, 0, NULL, '', '121.9898108', '27.5789189', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24726, '33011701001310703018', '33011701', '中河路上仓桥路3018', 2, NULL, 80, 0, NULL, 'OFF', '121.8693033', '25.6764558', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24727, '33011701001310703019', '33011701', '西湖大道建国路3019', 2, NULL, 80, 0, NULL, 'OFF', '128.3770846', '30.6455227', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24728, '33011701001310703020', '33011701', '平海路中河路3020', 2, NULL, 80, 0, NULL, 'OFF', '121.2775136', '31.1982465', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24729, '33011701001310703021', '33011701', '平海路延安路3021', 2, NULL, 80, 0, NULL, 'OFF', '126.2563149', '29.0546647', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24730, '33011701001310703022', '33011701', '解放路浣纱路3022', 2, NULL, 80, 0, NULL, 'OFF', '132.4490342', '26.6785841', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24731, '33011701001310703023', '33011701', '解放路建国路3023', 2, NULL, 80, 0, NULL, 'OFF', '128.4762269', '31.2289267', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24732, '33011701001310703024', '33011701', '钱江三桥秋涛路3024', 2, NULL, 80, 0, NULL, 'OFF', '130.0340325', '31.1088818', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24733, '33011701001310703025', '', 'testDevice3025', NULL, NULL, 80, 0, NULL, 'OFF', '129.7414825', '26.8576289', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24734, '33011701001310703026', '', 'dasda3026', NULL, NULL, 80, 0, NULL, '', '123.6053158', '25.9614994', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24735, '33011701001310703027', '33011701', '吴山广场3027', 2, NULL, 80, 0, NULL, '', '133.8021319', '34.2346105', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24736, '33011701001310703028', '33011701', '中河路上仓桥路3028', 2, NULL, 80, 0, NULL, 'OFF', '123.1947128', '28.2885547', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24737, '33011701001310703029', '33011701', '西湖大道建国路3029', 2, NULL, 80, 0, NULL, 'OFF', '119.567169', '33.7389425', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24738, '33011701001310703030', '33011701', '平海路中河路3030', 2, NULL, 80, 0, NULL, 'OFF', '133.2517073', '28.8290484', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24739, '33011701001310703031', '33011701', '平海路延安路3031', 2, NULL, 80, 0, NULL, 'OFF', '132.5570302', '27.9284151', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24740, '33011701001310703032', '33011701', '解放路浣纱路3032', 2, NULL, 80, 0, NULL, 'OFF', '128.0300296', '28.1549303', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24741, '33011701001310703033', '33011701', '解放路建国路3033', 2, NULL, 80, 0, NULL, 'OFF', '127.479058', '31.9894068', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24742, '33011701001310703034', '33011701', '钱江三桥秋涛路3034', 2, NULL, 80, 0, NULL, 'OFF', '118.3052019', '30.4822435', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24743, '33011701001310703035', '', 'testDevice3035', NULL, NULL, 80, 0, NULL, 'OFF', '134.0888359', '31.4429971', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24744, '33011701001310703036', '', 'dasda3036', NULL, NULL, 80, 0, NULL, '', '120.5285746', '30.7682555', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24745, '33011701001310703037', '33011701', '吴山广场3037', 2, NULL, 80, 0, NULL, '', '125.3763659', '34.5122863', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24746, '33011701001310703038', '33011701', '中河路上仓桥路3038', 2, NULL, 80, 0, NULL, 'OFF', '130.2961063', '25.2566653', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24747, '33011701001310703039', '33011701', '西湖大道建国路3039', 2, NULL, 80, 0, NULL, 'OFF', '120.3514344', '27.7464679', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24748, '33011701001310703040', '33011701', '平海路中河路3040', 2, NULL, 80, 0, NULL, 'OFF', '115.8688537', '27.962344', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24749, '33011701001310703041', '33011701', '平海路延安路3041', 2, NULL, 80, 0, NULL, 'OFF', '123.2899659', '31.5723168', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24750, '33011701001310703042', '33011701', '解放路浣纱路3042', 2, NULL, 80, 0, NULL, 'OFF', '133.8432689', '28.9745521', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24751, '33011701001310703043', '33011701', '解放路建国路3043', 2, NULL, 80, 0, NULL, 'OFF', '124.3464474', '25.1558107', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24752, '33011701001310703044', '33011701', '钱江三桥秋涛路3044', 2, NULL, 80, 0, NULL, 'OFF', '125.2024311', '33.8553972', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24753, '33011701001310703045', '', 'testDevice3045', NULL, NULL, 80, 0, NULL, 'OFF', '117.9728139', '28.8095541', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24754, '33011701001310703046', '', 'dasda3046', NULL, NULL, 80, 0, NULL, '', '119.2567767', '27.4815793', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24755, '33011701001310703047', '33011701', '吴山广场3047', 2, NULL, 80, 0, NULL, '', '127.3654425', '25.9792345', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24756, '33011701001310703048', '33011701', '中河路上仓桥路3048', 2, NULL, 80, 0, NULL, 'OFF', '124.0568829', '32.4514349', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24757, '33011701001310703049', '33011701', '西湖大道建国路3049', 2, NULL, 80, 0, NULL, 'OFF', '123.1880879', '29.3194714', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24758, '33011701001310703050', '33011701', '平海路中河路3050', 2, NULL, 80, 0, NULL, 'OFF', '128.7697911', '34.2430524', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24759, '33011701001310703051', '33011701', '平海路延安路3051', 2, NULL, 80, 0, NULL, 'OFF', '119.2846924', '28.2568481', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24760, '33011701001310703052', '33011701', '解放路浣纱路3052', 2, NULL, 80, 0, NULL, 'OFF', '115.1140893', '33.5550838', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24761, '33011701001310703053', '33011701', '解放路建国路3053', 2, NULL, 80, 0, NULL, 'OFF', '122.7163699', '28.0048749', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24762, '33011701001310703054', '33011701', '钱江三桥秋涛路3054', 2, NULL, 80, 0, NULL, 'OFF', '133.2395824', '34.3591233', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24763, '33011701001310703055', '', 'testDevice3055', NULL, NULL, 80, 0, NULL, 'OFF', '123.0488029', '32.7809922', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24764, '33011701001310703056', '', 'dasda3056', NULL, NULL, 80, 0, NULL, '', '120.5252679', '25.8275913', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24765, '33011701001310703057', '33011701', '吴山广场3057', 2, NULL, 80, 0, NULL, '', '118.4799316', '25.7949804', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24766, '33011701001310703058', '33011701', '中河路上仓桥路3058', 2, NULL, 80, 0, NULL, 'OFF', '115.8238548', '26.4921283', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24767, '33011701001310703059', '33011701', '西湖大道建国路3059', 2, NULL, 80, 0, NULL, 'OFF', '128.6794797', '30.0757007', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24768, '33011701001310703060', '33011701', '平海路中河路3060', 2, NULL, 80, 0, NULL, 'OFF', '120.9258346', '25.9021187', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24769, '33011701001310703061', '33011701', '平海路延安路3061', 2, NULL, 80, 0, NULL, 'OFF', '123.5907345', '34.2834917', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24770, '33011701001310703062', '33011701', '解放路浣纱路3062', 2, NULL, 80, 0, NULL, 'OFF', '120.1761696', '28.711103', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24771, '33011701001310703063', '33011701', '解放路建国路3063', 2, NULL, 80, 0, NULL, 'OFF', '115.1086448', '25.70504', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24772, '33011701001310703064', '33011701', '钱江三桥秋涛路3064', 2, NULL, 80, 0, NULL, 'OFF', '120.014716', '27.3918912', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24773, '33011701001310703065', '', 'testDevice3065', NULL, NULL, 80, 0, NULL, 'OFF', '119.7476462', '34.8443366', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24774, '33011701001310703066', '', 'dasda3066', NULL, NULL, 80, 0, NULL, '', '123.6940835', '27.0460097', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24775, '33011701001310703067', '33011701', '吴山广场3067', 2, NULL, 80, 0, NULL, '', '124.2274796', '25.6970388', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24776, '33011701001310703068', '33011701', '中河路上仓桥路3068', 2, NULL, 80, 0, NULL, 'OFF', '115.0551479', '32.3471652', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24777, '33011701001310703069', '33011701', '西湖大道建国路3069', 2, NULL, 80, 0, NULL, 'OFF', '127.5933015', '29.64471', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24778, '33011701001310703070', '33011701', '平海路中河路3070', 2, NULL, 80, 0, NULL, 'OFF', '117.8010644', '26.1820546', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24779, '33011701001310703071', '33011701', '平海路延安路3071', 2, NULL, 80, 0, NULL, 'OFF', '131.2254182', '26.9761435', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24780, '33011701001310703072', '33011701', '解放路浣纱路3072', 2, NULL, 80, 0, NULL, 'OFF', '127.7238984', '31.334554', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24781, '33011701001310703073', '33011701', '解放路建国路3073', 2, NULL, 80, 0, NULL, 'OFF', '129.9432378', '30.7443398', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24782, '33011701001310703074', '33011701', '钱江三桥秋涛路3074', 2, NULL, 80, 0, NULL, 'OFF', '131.5444946', '34.7180374', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24783, '33011701001310703075', '', 'testDevice3075', NULL, NULL, 80, 0, NULL, 'OFF', '132.89276', '26.3571678', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24784, '33011701001310703076', '', 'dasda3076', NULL, NULL, 80, 0, NULL, '', '134.8303168', '32.6317271', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24785, '33011701001310703077', '33011701', '吴山广场3077', 2, NULL, 80, 0, NULL, '', '120.4733046', '29.0871325', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24786, '33011701001310703078', '33011701', '中河路上仓桥路3078', 2, NULL, 80, 0, NULL, 'OFF', '122.8755732', '32.5404816', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24787, '33011701001310703079', '33011701', '西湖大道建国路3079', 2, NULL, 80, 0, NULL, 'OFF', '117.9579529', '30.4410109', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24788, '33011701001310703080', '33011701', '平海路中河路3080', 2, NULL, 80, 0, NULL, 'OFF', '126.1630457', '29.5836099', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24789, '33011701001310703081', '33011701', '平海路延安路3081', 2, NULL, 80, 0, NULL, 'OFF', '121.9413701', '31.5950171', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24790, '33011701001310703082', '33011701', '解放路浣纱路3082', 2, NULL, 80, 0, NULL, 'OFF', '116.2177142', '34.224256', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24791, '33011701001310703083', '33011701', '解放路建国路3083', 2, NULL, 80, 0, NULL, 'OFF', '120.2644613', '31.3362293', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24792, '33011701001310703084', '33011701', '钱江三桥秋涛路3084', 2, NULL, 80, 0, NULL, 'OFF', '117.6691643', '29.0083785', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24793, '33011701001310703085', '', 'testDevice3085', NULL, NULL, 80, 0, NULL, 'OFF', '132.5524383', '26.033205', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24794, '33011701001310703086', '', 'dasda3086', NULL, NULL, 80, 0, NULL, '', '134.7546991', '28.1408897', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24795, '33011701001310703087', '33011701', '吴山广场3087', 2, NULL, 80, 0, NULL, '', '121.1161813', '27.604834', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24796, '33011701001310703088', '33011701', '中河路上仓桥路3088', 2, NULL, 80, 0, NULL, 'OFF', '126.3168098', '28.6015013', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24797, '33011701001310703089', '33011701', '西湖大道建国路3089', 2, NULL, 80, 0, NULL, 'OFF', '133.2355059', '25.1930047', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24798, '33011701001310703090', '33011701', '平海路中河路3090', 2, NULL, 80, 0, NULL, 'OFF', '132.2271005', '25.1605198', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24799, '33011701001310703091', '33011701', '平海路延安路3091', 2, NULL, 80, 0, NULL, 'OFF', '126.4289854', '25.2235854', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24800, '33011701001310703092', '33011701', '解放路浣纱路3092', 2, NULL, 80, 0, NULL, 'OFF', '120.4636263', '25.6363678', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24801, '33011701001310703093', '33011701', '解放路建国路3093', 2, NULL, 80, 0, NULL, 'OFF', '128.0311757', '27.511083', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24802, '33011701001310703094', '33011701', '钱江三桥秋涛路3094', 2, NULL, 80, 0, NULL, 'OFF', '123.7650001', '25.6463118', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24803, '33011701001310703095', '', 'testDevice3095', NULL, NULL, 80, 0, NULL, 'OFF', '119.7314741', '30.6983104', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24804, '33011701001310703096', '', 'dasda3096', NULL, NULL, 80, 0, NULL, '', '132.362371', '31.5526171', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24805, '33011701001310703097', '33011701', '吴山广场3097', 2, NULL, 80, 0, NULL, '', '127.6174332', '30.6681545', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24806, '33011701001310703098', '33011701', '中河路上仓桥路3098', 2, NULL, 80, 0, NULL, 'OFF', '126.0000535', '33.6829216', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24807, '33011701001310703099', '33011701', '西湖大道建国路3099', 2, NULL, 80, 0, NULL, 'OFF', '132.1479687', '31.4101449', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24808, '33011701001310703100', '33011701', '平海路中河路3100', 2, NULL, 80, 0, NULL, 'OFF', '127.7396835', '31.0019599', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24809, '33011701001310703101', '33011701', '平海路延安路3101', 2, NULL, 80, 0, NULL, 'OFF', '127.254512', '25.7793652', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24810, '33011701001310703102', '33011701', '解放路浣纱路3102', 2, NULL, 80, 0, NULL, 'OFF', '118.0535102', '30.8909467', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24811, '33011701001310703103', '33011701', '解放路建国路3103', 2, NULL, 80, 0, NULL, 'OFF', '133.5040158', '32.1166383', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24812, '33011701001310703104', '33011701', '钱江三桥秋涛路3104', 2, NULL, 80, 0, NULL, 'OFF', '118.3595488', '32.9103514', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24813, '33011701001310703105', '', 'testDevice3105', NULL, NULL, 80, 0, NULL, 'OFF', '116.2856972', '33.2018425', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24814, '33011701001310703106', '', 'dasda3106', NULL, NULL, 80, 0, NULL, '', '131.3498403', '32.2781587', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24815, '33011701001310703107', '33011701', '吴山广场3107', 2, NULL, 80, 0, NULL, '', '132.8921104', '26.7852661', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24816, '33011701001310703108', '33011701', '中河路上仓桥路3108', 2, NULL, 80, 0, NULL, 'OFF', '115.4110316', '32.0918549', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24817, '33011701001310703109', '33011701', '西湖大道建国路3109', 2, NULL, 80, 0, NULL, 'OFF', '123.3788276', '25.1034767', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24818, '33011701001310703110', '33011701', '平海路中河路3110', 2, NULL, 80, 0, NULL, 'OFF', '115.6610438', '34.2418187', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24819, '33011701001310703111', '33011701', '平海路延安路3111', 2, NULL, 80, 0, NULL, 'OFF', '133.168737', '30.898664', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24820, '33011701001310703112', '33011701', '解放路浣纱路3112', 2, NULL, 80, 0, NULL, 'OFF', '123.860554', '26.7678641', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24821, '33011701001310703113', '33011701', '解放路建国路3113', 2, NULL, 80, 0, NULL, 'OFF', '124.7965595', '26.1433288', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24822, '33011701001310703114', '33011701', '钱江三桥秋涛路3114', 2, NULL, 80, 0, NULL, 'OFF', '117.4011362', '25.4130519', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24823, '33011701001310703115', '', 'testDevice3115', NULL, NULL, 80, 0, NULL, 'OFF', '117.616003', '33.6352735', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24824, '33011701001310703116', '', 'dasda3116', NULL, NULL, 80, 0, NULL, '', '120.876607', '26.9372121', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24825, '33011701001310703117', '33011701', '吴山广场3117', 2, NULL, 80, 0, NULL, '', '116.5350266', '28.7802401', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24826, '33011701001310703118', '33011701', '中河路上仓桥路3118', 2, NULL, 80, 0, NULL, 'OFF', '125.0453128', '28.0895648', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24827, '33011701001310703119', '33011701', '西湖大道建国路3119', 2, NULL, 80, 0, NULL, 'OFF', '120.6214849', '29.107104', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24828, '33011701001310703120', '33011701', '平海路中河路3120', 2, NULL, 80, 0, NULL, 'OFF', '132.9714866', '26.2668259', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24829, '33011701001310703121', '33011701', '平海路延安路3121', 2, NULL, 80, 0, NULL, 'OFF', '127.9929788', '29.0128179', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24830, '33011701001310703122', '33011701', '解放路浣纱路3122', 2, NULL, 80, 0, NULL, 'OFF', '126.050435', '31.2636121', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24831, '33011701001310703123', '33011701', '解放路建国路3123', 2, NULL, 80, 0, NULL, 'OFF', '131.273239', '34.2796071', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24832, '33011701001310703124', '33011701', '钱江三桥秋涛路3124', 2, NULL, 80, 0, NULL, 'OFF', '123.2148906', '32.6071993', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24833, '33011701001310703125', '', 'testDevice3125', NULL, NULL, 80, 0, NULL, 'OFF', '127.2547367', '25.1971758', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24834, '33011701001310703126', '', 'dasda3126', NULL, NULL, 80, 0, NULL, '', '131.6290121', '33.1642812', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24835, '33011701001310703127', '33011701', '吴山广场3127', 2, NULL, 80, 0, NULL, '', '121.3808513', '25.2298788', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24836, '33011701001310703128', '33011701', '中河路上仓桥路3128', 2, NULL, 80, 0, NULL, 'OFF', '117.0172207', '31.6565507', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24837, '33011701001310703129', '33011701', '西湖大道建国路3129', 2, NULL, 80, 0, NULL, 'OFF', '125.9435502', '27.5931175', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24838, '33011701001310703130', '33011701', '平海路中河路3130', 2, NULL, 80, 0, NULL, 'OFF', '123.6660894', '27.9959356', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24839, '33011701001310703131', '33011701', '平海路延安路3131', 2, NULL, 80, 0, NULL, 'OFF', '125.4997973', '32.2003259', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24840, '33011701001310703132', '33011701', '解放路浣纱路3132', 2, NULL, 80, 0, NULL, 'OFF', '121.5007188', '32.0138232', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24841, '33011701001310703133', '33011701', '解放路建国路3133', 2, NULL, 80, 0, NULL, 'OFF', '116.0042027', '28.4681385', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24842, '33011701001310703134', '33011701', '钱江三桥秋涛路3134', 2, NULL, 80, 0, NULL, 'OFF', '120.5188578', '31.299223', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24843, '33011701001310703135', '', 'testDevice3135', NULL, NULL, 80, 0, NULL, 'OFF', '119.5816815', '26.0917001', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24844, '33011701001310703136', '', 'dasda3136', NULL, NULL, 80, 0, NULL, '', '121.3518346', '31.5608317', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24845, '33011701001310703137', '33011701', '吴山广场3137', 2, NULL, 80, 0, NULL, '', '133.0141291', '34.5290585', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24846, '33011701001310703138', '33011701', '中河路上仓桥路3138', 2, NULL, 80, 0, NULL, 'OFF', '126.0151423', '32.9627979', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24847, '33011701001310703139', '33011701', '西湖大道建国路3139', 2, NULL, 80, 0, NULL, 'OFF', '116.0333248', '26.2268141', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24848, '33011701001310703140', '33011701', '平海路中河路3140', 2, NULL, 80, 0, NULL, 'OFF', '127.1211978', '27.2456772', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24849, '33011701001310703141', '33011701', '平海路延安路3141', 2, NULL, 80, 0, NULL, 'OFF', '132.5060151', '32.5479442', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24850, '33011701001310703142', '33011701', '解放路浣纱路3142', 2, NULL, 80, 0, NULL, 'OFF', '126.1664827', '26.0026895', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24851, '33011701001310703143', '33011701', '解放路建国路3143', 2, NULL, 80, 0, NULL, 'OFF', '118.3143691', '27.369615', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24852, '33011701001310703144', '33011701', '钱江三桥秋涛路3144', 2, NULL, 80, 0, NULL, 'OFF', '118.0723977', '33.8400071', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24853, '33011701001310703145', '', 'testDevice3145', NULL, NULL, 80, 0, NULL, 'OFF', '120.4188819', '32.0911909', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24854, '33011701001310703146', '', 'dasda3146', NULL, NULL, 80, 0, NULL, '', '132.877217', '33.9359333', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24855, '33011701001310703147', '33011701', '吴山广场3147', 2, NULL, 80, 0, NULL, '', '128.1294399', '28.4060943', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24856, '33011701001310703148', '33011701', '中河路上仓桥路3148', 2, NULL, 80, 0, NULL, 'OFF', '127.0155491', '25.2226717', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24857, '33011701001310703149', '33011701', '西湖大道建国路3149', 2, NULL, 80, 0, NULL, 'OFF', '115.6894264', '25.8950761', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24858, '33011701001310703150', '33011701', '平海路中河路3150', 2, NULL, 80, 0, NULL, 'OFF', '122.4004853', '28.8073655', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24859, '33011701001310703151', '33011701', '平海路延安路3151', 2, NULL, 80, 0, NULL, 'OFF', '129.9341478', '31.3515997', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24860, '33011701001310703152', '33011701', '解放路浣纱路3152', 2, NULL, 80, 0, NULL, 'OFF', '127.4692838', '25.3359024', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24861, '33011701001310703153', '33011701', '解放路建国路3153', 2, NULL, 80, 0, NULL, 'OFF', '132.5439762', '27.6247132', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24862, '33011701001310703154', '33011701', '钱江三桥秋涛路3154', 2, NULL, 80, 0, NULL, 'OFF', '125.3120304', '27.1158592', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24863, '33011701001310703155', '', 'testDevice3155', NULL, NULL, 80, 0, NULL, 'OFF', '133.9282237', '27.7051565', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24864, '33011701001310703156', '', 'dasda3156', NULL, NULL, 80, 0, NULL, '', '118.7050282', '32.1782051', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24865, '33011701001310703157', '33011701', '吴山广场3157', 2, NULL, 80, 0, NULL, '', '116.7404704', '32.7755563', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24866, '33011701001310703158', '33011701', '中河路上仓桥路3158', 2, NULL, 80, 0, NULL, 'OFF', '132.5872682', '32.3431664', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24867, '33011701001310703159', '33011701', '西湖大道建国路3159', 2, NULL, 80, 0, NULL, 'OFF', '117.7149302', '28.3891638', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24868, '33011701001310703160', '33011701', '平海路中河路3160', 2, NULL, 80, 0, NULL, 'OFF', '115.8128471', '29.9163197', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24869, '33011701001310703161', '33011701', '平海路延安路3161', 2, NULL, 80, 0, NULL, 'OFF', '130.9194457', '29.4141077', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24870, '33011701001310703162', '33011701', '解放路浣纱路3162', 2, NULL, 80, 0, NULL, 'OFF', '132.1586877', '32.3215798', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24871, '33011701001310703163', '33011701', '解放路建国路3163', 2, NULL, 80, 0, NULL, 'OFF', '133.0351021', '28.365576', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24872, '33011701001310703164', '33011701', '钱江三桥秋涛路3164', 2, NULL, 80, 0, NULL, 'OFF', '133.699448', '29.8631409', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24873, '33011701001310703165', '', 'testDevice3165', NULL, NULL, 80, 0, NULL, 'OFF', '134.3919343', '29.2189769', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24874, '33011701001310703166', '', 'dasda3166', NULL, NULL, 80, 0, NULL, '', '115.8613279', '31.5054623', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24875, '33011701001310703167', '33011701', '吴山广场3167', 2, NULL, 80, 0, NULL, '', '121.1308374', '34.8703809', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24876, '33011701001310703168', '33011701', '中河路上仓桥路3168', 2, NULL, 80, 0, NULL, 'OFF', '123.0702039', '34.8355178', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24877, '33011701001310703169', '33011701', '西湖大道建国路3169', 2, NULL, 80, 0, NULL, 'OFF', '116.9585077', '34.5664468', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24878, '33011701001310703170', '33011701', '平海路中河路3170', 2, NULL, 80, 0, NULL, 'OFF', '120.5819276', '33.3256807', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24879, '33011701001310703171', '33011701', '平海路延安路3171', 2, NULL, 80, 0, NULL, 'OFF', '117.0341155', '27.9290637', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24880, '33011701001310703172', '33011701', '解放路浣纱路3172', 2, NULL, 80, 0, NULL, 'OFF', '128.4247953', '34.6682764', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24881, '33011701001310703173', '33011701', '解放路建国路3173', 2, NULL, 80, 0, NULL, 'OFF', '116.0216305', '34.5541913', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24882, '33011701001310703174', '33011701', '钱江三桥秋涛路3174', 2, NULL, 80, 0, NULL, 'OFF', '119.8337675', '33.7661275', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24883, '33011701001310703175', '', 'testDevice3175', NULL, NULL, 80, 0, NULL, 'OFF', '116.1039464', '30.1680638', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24884, '33011701001310703176', '', 'dasda3176', NULL, NULL, 80, 0, NULL, '', '126.01843', '34.5419369', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24885, '33011701001310703177', '33011701', '吴山广场3177', 2, NULL, 80, 0, NULL, '', '126.7803116', '27.2054933', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24886, '33011701001310703178', '33011701', '中河路上仓桥路3178', 2, NULL, 80, 0, NULL, 'OFF', '120.8462684', '27.4016564', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24887, '33011701001310703179', '33011701', '西湖大道建国路3179', 2, NULL, 80, 0, NULL, 'OFF', '128.8904077', '30.3918023', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24888, '33011701001310703180', '33011701', '平海路中河路3180', 2, NULL, 80, 0, NULL, 'OFF', '126.9132342', '34.7540428', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24889, '33011701001310703181', '33011701', '平海路延安路3181', 2, NULL, 80, 0, NULL, 'OFF', '132.8949485', '27.5948072', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24890, '33011701001310703182', '33011701', '解放路浣纱路3182', 2, NULL, 80, 0, NULL, 'OFF', '128.7350404', '28.7119079', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24891, '33011701001310703183', '33011701', '解放路建国路3183', 2, NULL, 80, 0, NULL, 'OFF', '129.990357', '25.7751182', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24892, '33011701001310703184', '33011701', '钱江三桥秋涛路3184', 2, NULL, 80, 0, NULL, 'OFF', '128.7466646', '27.7398677', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24893, '33011701001310703185', '', 'testDevice3185', NULL, NULL, 80, 0, NULL, 'OFF', '118.7622524', '26.3739842', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24894, '33011701001310703186', '', 'dasda3186', NULL, NULL, 80, 0, NULL, '', '132.5712688', '33.6503184', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24895, '33011701001310703187', '33011701', '吴山广场3187', 2, NULL, 80, 0, NULL, '', '131.5695875', '34.1296395', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24896, '33011701001310703188', '33011701', '中河路上仓桥路3188', 2, NULL, 80, 0, NULL, 'OFF', '125.1341318', '34.6972426', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24897, '33011701001310703189', '33011701', '西湖大道建国路3189', 2, NULL, 80, 0, NULL, 'OFF', '115.9618972', '26.0972947', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24898, '33011701001310703190', '33011701', '平海路中河路3190', 2, NULL, 80, 0, NULL, 'OFF', '129.4070913', '31.3947461', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24899, '33011701001310703191', '33011701', '平海路延安路3191', 2, NULL, 80, 0, NULL, 'OFF', '124.1497654', '33.6818465', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24900, '33011701001310703192', '33011701', '解放路浣纱路3192', 2, NULL, 80, 0, NULL, 'OFF', '117.5275537', '29.2249948', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24901, '33011701001310703193', '33011701', '解放路建国路3193', 2, NULL, 80, 0, NULL, 'OFF', '120.1884729', '30.0794346', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24902, '33011701001310703194', '33011701', '钱江三桥秋涛路3194', 2, NULL, 80, 0, NULL, 'OFF', '133.3597041', '27.7221891', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24903, '33011701001310703195', '', 'testDevice3195', NULL, NULL, 80, 0, NULL, 'OFF', '131.2331026', '33.372642', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24904, '33011701001310703196', '', 'dasda3196', NULL, NULL, 80, 0, NULL, '', '121.086401', '28.6966429', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24905, '33011701001310703197', '33011701', '吴山广场3197', 2, NULL, 80, 0, NULL, '', '116.7326979', '28.3652887', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24906, '33011701001310703198', '33011701', '中河路上仓桥路3198', 2, NULL, 80, 0, NULL, 'OFF', '125.4042871', '30.736515', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24907, '33011701001310703199', '33011701', '西湖大道建国路3199', 2, NULL, 80, 0, NULL, 'OFF', '121.8233425', '33.5867095', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24908, '33011701001310703200', '33011701', '平海路中河路3200', 2, NULL, 80, 0, NULL, 'OFF', '117.9038519', '30.7240027', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24909, '33011701001310703201', '33011701', '平海路延安路3201', 2, NULL, 80, 0, NULL, 'OFF', '129.0492324', '27.8598853', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24910, '33011701001310703202', '33011701', '解放路浣纱路3202', 2, NULL, 80, 0, NULL, 'OFF', '116.534607', '32.1274187', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24911, '33011701001310703203', '33011701', '解放路建国路3203', 2, NULL, 80, 0, NULL, 'OFF', '120.5253383', '32.0574379', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24912, '33011701001310703204', '33011701', '钱江三桥秋涛路3204', 2, NULL, 80, 0, NULL, 'OFF', '118.022871', '28.9049337', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24913, '33011701001310703205', '', 'testDevice3205', NULL, NULL, 80, 0, NULL, 'OFF', '133.538341', '33.3523549', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24914, '33011701001310703206', '', 'dasda3206', NULL, NULL, 80, 0, NULL, '', '118.6230926', '25.046974', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24915, '33011701001310703207', '33011701', '吴山广场3207', 2, NULL, 80, 0, NULL, '', '117.5004405', '30.1778053', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24916, '33011701001310703208', '33011701', '中河路上仓桥路3208', 2, NULL, 80, 0, NULL, 'OFF', '116.6329253', '30.748105', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24917, '33011701001310703209', '33011701', '西湖大道建国路3209', 2, NULL, 80, 0, NULL, 'OFF', '115.6633059', '28.2071095', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24918, '33011701001310703210', '33011701', '平海路中河路3210', 2, NULL, 80, 0, NULL, 'OFF', '133.4177539', '33.7912327', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24919, '33011701001310703211', '33011701', '平海路延安路3211', 2, NULL, 80, 0, NULL, 'OFF', '125.0988523', '29.3348352', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24920, '33011701001310703212', '33011701', '解放路浣纱路3212', 2, NULL, 80, 0, NULL, 'OFF', '130.2410006', '30.3004784', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24921, '33011701001310703213', '33011701', '解放路建国路3213', 2, NULL, 80, 0, NULL, 'OFF', '120.9084469', '28.4978865', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24922, '33011701001310703214', '33011701', '钱江三桥秋涛路3214', 2, NULL, 80, 0, NULL, 'OFF', '118.8192331', '26.5879978', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24923, '33011701001310703215', '', 'testDevice3215', NULL, NULL, 80, 0, NULL, 'OFF', '116.3708257', '32.4463295', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24924, '33011701001310703216', '', 'dasda3216', NULL, NULL, 80, 0, NULL, '', '130.3964295', '27.4676547', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24925, '33011701001310703217', '33011701', '吴山广场3217', 2, NULL, 80, 0, NULL, '', '127.869671', '34.9992852', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24926, '33011701001310703218', '33011701', '中河路上仓桥路3218', 2, NULL, 80, 0, NULL, 'OFF', '133.159067', '27.593462', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24927, '33011701001310703219', '33011701', '西湖大道建国路3219', 2, NULL, 80, 0, NULL, 'OFF', '127.1863229', '27.969455', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24928, '33011701001310703220', '33011701', '平海路中河路3220', 2, NULL, 80, 0, NULL, 'OFF', '121.454414', '32.066889', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24929, '33011701001310703221', '33011701', '平海路延安路3221', 2, NULL, 80, 0, NULL, 'OFF', '130.7131019', '31.4260806', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24930, '33011701001310703222', '33011701', '解放路浣纱路3222', 2, NULL, 80, 0, NULL, 'OFF', '134.202268', '25.9297361', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24931, '33011701001310703223', '33011701', '解放路建国路3223', 2, NULL, 80, 0, NULL, 'OFF', '123.8720349', '30.3704392', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24932, '33011701001310703224', '33011701', '钱江三桥秋涛路3224', 2, NULL, 80, 0, NULL, 'OFF', '121.7533712', '29.062988', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24933, '33011701001310703225', '', 'testDevice3225', NULL, NULL, 80, 0, NULL, 'OFF', '122.1507517', '29.2036227', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24934, '33011701001310703226', '', 'dasda3226', NULL, NULL, 80, 0, NULL, '', '130.4936458', '33.8291499', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24935, '33011701001310703227', '33011701', '吴山广场3227', 2, NULL, 80, 0, NULL, '', '131.0159744', '26.5348816', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24936, '33011701001310703228', '33011701', '中河路上仓桥路3228', 2, NULL, 80, 0, NULL, 'OFF', '128.5989352', '26.1869586', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24937, '33011701001310703229', '33011701', '西湖大道建国路3229', 2, NULL, 80, 0, NULL, 'OFF', '134.9467534', '26.3301483', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24938, '33011701001310703230', '33011701', '平海路中河路3230', 2, NULL, 80, 0, NULL, 'OFF', '133.936962', '28.0898662', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24939, '33011701001310703231', '33011701', '平海路延安路3231', 2, NULL, 80, 0, NULL, 'OFF', '129.8445503', '26.4588866', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24940, '33011701001310703232', '33011701', '解放路浣纱路3232', 2, NULL, 80, 0, NULL, 'OFF', '132.4118661', '33.0248344', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24941, '33011701001310703233', '33011701', '解放路建国路3233', 2, NULL, 80, 0, NULL, 'OFF', '117.5256804', '30.7475125', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24942, '33011701001310703234', '33011701', '钱江三桥秋涛路3234', 2, NULL, 80, 0, NULL, 'OFF', '115.3928043', '29.6630595', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24943, '33011701001310703235', '', 'testDevice3235', NULL, NULL, 80, 0, NULL, 'OFF', '129.386981', '31.0727606', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24944, '33011701001310703236', '', 'dasda3236', NULL, NULL, 80, 0, NULL, '', '125.7564927', '31.3746245', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24945, '33011701001310703237', '33011701', '吴山广场3237', 2, NULL, 80, 0, NULL, '', '125.621521', '28.6548413', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24946, '33011701001310703238', '33011701', '中河路上仓桥路3238', 2, NULL, 80, 0, NULL, 'OFF', '115.8381275', '34.1503334', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24947, '33011701001310703239', '33011701', '西湖大道建国路3239', 2, NULL, 80, 0, NULL, 'OFF', '127.326075', '29.7871431', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24948, '33011701001310703240', '33011701', '平海路中河路3240', 2, NULL, 80, 0, NULL, 'OFF', '134.1159934', '31.4847156', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24949, '33011701001310703241', '33011701', '平海路延安路3241', 2, NULL, 80, 0, NULL, 'OFF', '133.6017424', '33.0621493', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24950, '33011701001310703242', '33011701', '解放路浣纱路3242', 2, NULL, 80, 0, NULL, 'OFF', '130.6607325', '25.8565998', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24951, '33011701001310703243', '33011701', '解放路建国路3243', 2, NULL, 80, 0, NULL, 'OFF', '117.4984359', '25.0965516', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24952, '33011701001310703244', '33011701', '钱江三桥秋涛路3244', 2, NULL, 80, 0, NULL, 'OFF', '120.5099825', '32.9129588', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24953, '33011701001310703245', '', 'testDevice3245', NULL, NULL, 80, 0, NULL, 'OFF', '115.0546057', '34.2751397', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24954, '33011701001310703246', '', 'dasda3246', NULL, NULL, 80, 0, NULL, '', '118.7430814', '27.6368222', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24955, '33011701001310703247', '33011701', '吴山广场3247', 2, NULL, 80, 0, NULL, '', '133.5515905', '30.3586922', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24956, '33011701001310703248', '33011701', '中河路上仓桥路3248', 2, NULL, 80, 0, NULL, 'OFF', '116.5287088', '33.8829949', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24957, '33011701001310703249', '33011701', '西湖大道建国路3249', 2, NULL, 80, 0, NULL, 'OFF', '126.9887732', '33.3388981', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24958, '33011701001310703250', '33011701', '平海路中河路3250', 2, NULL, 80, 0, NULL, 'OFF', '130.3577401', '30.0455062', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24959, '33011701001310703251', '33011701', '平海路延安路3251', 2, NULL, 80, 0, NULL, 'OFF', '115.8223816', '25.2108368', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24960, '33011701001310703252', '33011701', '解放路浣纱路3252', 2, NULL, 80, 0, NULL, 'OFF', '133.0386885', '30.9176656', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24961, '33011701001310703253', '33011701', '解放路建国路3253', 2, NULL, 80, 0, NULL, 'OFF', '122.7262981', '33.9558183', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24962, '33011701001310703254', '33011701', '钱江三桥秋涛路3254', 2, NULL, 80, 0, NULL, 'OFF', '119.5154258', '32.0260946', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24963, '33011701001310703255', '', 'testDevice3255', NULL, NULL, 80, 0, NULL, 'OFF', '134.3982353', '33.2630188', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24964, '33011701001310703256', '', 'dasda3256', NULL, NULL, 80, 0, NULL, '', '118.4448995', '25.2368101', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24965, '33011701001310703257', '33011701', '吴山广场3257', 2, NULL, 80, 0, NULL, '', '134.0297925', '31.3949948', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24966, '33011701001310703258', '33011701', '中河路上仓桥路3258', 2, NULL, 80, 0, NULL, 'OFF', '119.8142643', '26.2645438', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24967, '33011701001310703259', '33011701', '西湖大道建国路3259', 2, NULL, 80, 0, NULL, 'OFF', '121.9819447', '32.1377349', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24968, '33011701001310703260', '33011701', '平海路中河路3260', 2, NULL, 80, 0, NULL, 'OFF', '115.4669314', '26.8950435', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24969, '33011701001310703261', '33011701', '平海路延安路3261', 2, NULL, 80, 0, NULL, 'OFF', '116.3888233', '33.0620133', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24970, '33011701001310703262', '33011701', '解放路浣纱路3262', 2, NULL, 80, 0, NULL, 'OFF', '120.5433229', '29.624936', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24971, '33011701001310703263', '33011701', '解放路建国路3263', 2, NULL, 80, 0, NULL, 'OFF', '118.5501453', '33.9386403', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24972, '33011701001310703264', '33011701', '钱江三桥秋涛路3264', 2, NULL, 80, 0, NULL, 'OFF', '116.1207584', '25.8183939', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24973, '33011701001310703265', '', 'testDevice3265', NULL, NULL, 80, 0, NULL, 'OFF', '129.9533566', '32.2760492', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24974, '33011701001310703266', '', 'dasda3266', NULL, NULL, 80, 0, NULL, '', '126.4045085', '28.9250642', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24975, '33011701001310703267', '33011701', '吴山广场3267', 2, NULL, 80, 0, NULL, '', '127.1624734', '32.7971739', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24976, '33011701001310703268', '33011701', '中河路上仓桥路3268', 2, NULL, 80, 0, NULL, 'OFF', '121.5988419', '32.2106774', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24977, '33011701001310703269', '33011701', '西湖大道建国路3269', 2, NULL, 80, 0, NULL, 'OFF', '131.50679', '27.6618654', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24978, '33011701001310703270', '33011701', '平海路中河路3270', 2, NULL, 80, 0, NULL, 'OFF', '117.7374248', '26.6772953', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24979, '33011701001310703271', '33011701', '平海路延安路3271', 2, NULL, 80, 0, NULL, 'OFF', '119.1667548', '25.4008804', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24980, '33011701001310703272', '33011701', '解放路浣纱路3272', 2, NULL, 80, 0, NULL, 'OFF', '127.6215002', '31.9725165', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24981, '33011701001310703273', '33011701', '解放路建国路3273', 2, NULL, 80, 0, NULL, 'OFF', '125.607237', '28.6599416', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24982, '33011701001310703274', '33011701', '钱江三桥秋涛路3274', 2, NULL, 80, 0, NULL, 'OFF', '130.1716853', '32.382159', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24983, '33011701001310703275', '', 'testDevice3275', NULL, NULL, 80, 0, NULL, 'OFF', '119.0367161', '30.9309703', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24984, '33011701001310703276', '', 'dasda3276', NULL, NULL, 80, 0, NULL, '', '129.6685252', '32.5083748', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24985, '33011701001310703277', '33011701', '吴山广场3277', 2, NULL, 80, 0, NULL, '', '116.2324783', '34.7489633', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24986, '33011701001310703278', '33011701', '中河路上仓桥路3278', 2, NULL, 80, 0, NULL, 'OFF', '117.1568166', '31.2196925', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24987, '33011701001310703279', '33011701', '西湖大道建国路3279', 2, NULL, 80, 0, NULL, 'OFF', '122.0866486', '26.851573', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24988, '33011701001310703280', '33011701', '平海路中河路3280', 2, NULL, 80, 0, NULL, 'OFF', '123.9627939', '25.5987878', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24989, '33011701001310703281', '33011701', '平海路延安路3281', 2, NULL, 80, 0, NULL, 'OFF', '118.5540243', '32.4392204', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24990, '33011701001310703282', '33011701', '解放路浣纱路3282', 2, NULL, 80, 0, NULL, 'OFF', '125.8817403', '30.3997389', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24991, '33011701001310703283', '33011701', '解放路建国路3283', 2, NULL, 80, 0, NULL, 'OFF', '118.7466292', '29.6810335', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24992, '33011701001310703284', '33011701', '钱江三桥秋涛路3284', 2, NULL, 80, 0, NULL, 'OFF', '121.0879257', '32.205951', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24993, '33011701001310703285', '', 'testDevice3285', NULL, NULL, 80, 0, NULL, 'OFF', '134.1997414', '26.9866549', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24994, '33011701001310703286', '', 'dasda3286', NULL, NULL, 80, 0, NULL, '', '132.7349307', '33.315422', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24995, '33011701001310703287', '33011701', '吴山广场3287', 2, NULL, 80, 0, NULL, '', '126.0754297', '30.6171453', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24996, '33011701001310703288', '33011701', '中河路上仓桥路3288', 2, NULL, 80, 0, NULL, 'OFF', '117.1723571', '28.1394609', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24997, '33011701001310703289', '33011701', '西湖大道建国路3289', 2, NULL, 80, 0, NULL, 'OFF', '132.6354972', '33.8458688', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24998, '33011701001310703290', '33011701', '平海路中河路3290', 2, NULL, 80, 0, NULL, 'OFF', '116.6604153', '29.8109617', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (24999, '33011701001310703291', '33011701', '平海路延安路3291', 2, NULL, 80, 0, NULL, 'OFF', '130.3955855', '32.5172025', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25000, '33011701001310703292', '33011701', '解放路浣纱路3292', 2, NULL, 80, 0, NULL, 'OFF', '126.9966821', '28.1531278', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25001, '33011701001310703293', '33011701', '解放路建国路3293', 2, NULL, 80, 0, NULL, 'OFF', '128.7966546', '28.2140318', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25002, '33011701001310703294', '33011701', '钱江三桥秋涛路3294', 2, NULL, 80, 0, NULL, 'OFF', '127.9932275', '31.6107757', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25003, '33011701001310703295', '', 'testDevice3295', NULL, NULL, 80, 0, NULL, 'OFF', '118.5761743', '28.4117835', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25004, '33011701001310703296', '', 'dasda3296', NULL, NULL, 80, 0, NULL, '', '133.9011895', '32.2265907', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25005, '33011701001310703297', '33011701', '吴山广场3297', 2, NULL, 80, 0, NULL, '', '118.7774253', '30.8976033', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25006, '33011701001310703298', '33011701', '中河路上仓桥路3298', 2, NULL, 80, 0, NULL, 'OFF', '117.1835586', '32.8082446', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25007, '33011701001310703299', '33011701', '西湖大道建国路3299', 2, NULL, 80, 0, NULL, 'OFF', '134.5855175', '26.3484133', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25008, '33011701001310703300', '33011701', '平海路中河路3300', 2, NULL, 80, 0, NULL, 'OFF', '126.3769124', '28.3173332', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25009, '33011701001310703301', '33011701', '平海路延安路3301', 2, NULL, 80, 0, NULL, 'OFF', '133.1280103', '27.5414265', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25010, '33011701001310703302', '33011701', '解放路浣纱路3302', 2, NULL, 80, 0, NULL, 'OFF', '131.5093149', '27.7551333', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25011, '33011701001310703303', '33011701', '解放路建国路3303', 2, NULL, 80, 0, NULL, 'OFF', '123.162544', '31.151387', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25012, '33011701001310703304', '33011701', '钱江三桥秋涛路3304', 2, NULL, 80, 0, NULL, 'OFF', '126.284776', '27.4915356', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25013, '33011701001310703305', '', 'testDevice3305', NULL, NULL, 80, 0, NULL, 'OFF', '126.9362488', '29.0035171', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25014, '33011701001310703306', '', 'dasda3306', NULL, NULL, 80, 0, NULL, '', '120.8269166', '27.5429792', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25015, '33011701001310703307', '33011701', '吴山广场3307', 2, NULL, 80, 0, NULL, '', '128.3258369', '25.7043448', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25016, '33011701001310703308', '33011701', '中河路上仓桥路3308', 2, NULL, 80, 0, NULL, 'OFF', '124.1484356', '30.8927867', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25017, '33011701001310703309', '33011701', '西湖大道建国路3309', 2, NULL, 80, 0, NULL, 'OFF', '120.7646679', '32.3508996', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25018, '33011701001310703310', '33011701', '平海路中河路3310', 2, NULL, 80, 0, NULL, 'OFF', '116.3780333', '34.0761383', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25019, '33011701001310703311', '33011701', '平海路延安路3311', 2, NULL, 80, 0, NULL, 'OFF', '124.5961636', '28.3279927', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25020, '33011701001310703312', '33011701', '解放路浣纱路3312', 2, NULL, 80, 0, NULL, 'OFF', '118.8467186', '34.4115491', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25021, '33011701001310703313', '33011701', '解放路建国路3313', 2, NULL, 80, 0, NULL, 'OFF', '125.4451027', '32.0737676', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25022, '33011701001310703314', '33011701', '钱江三桥秋涛路3314', 2, NULL, 80, 0, NULL, 'OFF', '115.6853584', '32.134191', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25023, '33011701001310703315', '', 'testDevice3315', NULL, NULL, 80, 0, NULL, 'OFF', '127.0914845', '29.4496526', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25024, '33011701001310703316', '', 'dasda3316', NULL, NULL, 80, 0, NULL, '', '133.401348', '25.8456904', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25025, '33011701001310703317', '33011701', '吴山广场3317', 2, NULL, 80, 0, NULL, '', '130.7322869', '25.8794943', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25026, '33011701001310703318', '33011701', '中河路上仓桥路3318', 2, NULL, 80, 0, NULL, 'OFF', '118.4573911', '26.8604006', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25027, '33011701001310703319', '33011701', '西湖大道建国路3319', 2, NULL, 80, 0, NULL, 'OFF', '125.0900954', '31.6635206', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25028, '33011701001310703320', '33011701', '平海路中河路3320', 2, NULL, 80, 0, NULL, 'OFF', '115.0783044', '32.7364015', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25029, '33011701001310703321', '33011701', '平海路延安路3321', 2, NULL, 80, 0, NULL, 'OFF', '125.1212365', '33.691446', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25030, '33011701001310703322', '33011701', '解放路浣纱路3322', 2, NULL, 80, 0, NULL, 'OFF', '125.37127', '25.2480257', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25031, '33011701001310703323', '33011701', '解放路建国路3323', 2, NULL, 80, 0, NULL, 'OFF', '116.4926409', '30.1657909', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25032, '33011701001310703324', '33011701', '钱江三桥秋涛路3324', 2, NULL, 80, 0, NULL, 'OFF', '131.349395', '30.0848779', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25033, '33011701001310703325', '', 'testDevice3325', NULL, NULL, 80, 0, NULL, 'OFF', '132.2690532', '34.9270169', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25034, '33011701001310703326', '', 'dasda3326', NULL, NULL, 80, 0, NULL, '', '132.2970813', '29.3804511', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25035, '33011701001310703327', '33011701', '吴山广场3327', 2, NULL, 80, 0, NULL, '', '129.6782478', '27.1212051', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25036, '33011701001310703328', '33011701', '中河路上仓桥路3328', 2, NULL, 80, 0, NULL, 'OFF', '116.4999956', '32.4646725', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25037, '33011701001310703329', '33011701', '西湖大道建国路3329', 2, NULL, 80, 0, NULL, 'OFF', '118.4652351', '25.9597477', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25038, '33011701001310703330', '33011701', '平海路中河路3330', 2, NULL, 80, 0, NULL, 'OFF', '127.8261894', '27.404721', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25039, '33011701001310703331', '33011701', '平海路延安路3331', 2, NULL, 80, 0, NULL, 'OFF', '128.7352424', '34.1443624', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25040, '33011701001310703332', '33011701', '解放路浣纱路3332', 2, NULL, 80, 0, NULL, 'OFF', '125.1976445', '33.5076492', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25041, '33011701001310703333', '33011701', '解放路建国路3333', 2, NULL, 80, 0, NULL, 'OFF', '124.782496', '30.105159', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25042, '33011701001310703334', '33011701', '钱江三桥秋涛路3334', 2, NULL, 80, 0, NULL, 'OFF', '133.319547', '25.0028479', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25043, '33011701001310703335', '', 'testDevice3335', NULL, NULL, 80, 0, NULL, 'OFF', '117.2502474', '29.6987629', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25044, '33011701001310703336', '', 'dasda3336', NULL, NULL, 80, 0, NULL, '', '131.2925967', '28.4852709', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25045, '33011701001310703337', '33011701', '吴山广场3337', 2, NULL, 80, 0, NULL, '', '129.7122421', '28.3300661', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25046, '33011701001310703338', '33011701', '中河路上仓桥路3338', 2, NULL, 80, 0, NULL, 'OFF', '119.6834209', '31.194518', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25047, '33011701001310703339', '33011701', '西湖大道建国路3339', 2, NULL, 80, 0, NULL, 'OFF', '134.280379', '25.982392', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25048, '33011701001310703340', '33011701', '平海路中河路3340', 2, NULL, 80, 0, NULL, 'OFF', '117.3516328', '31.3284066', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25049, '33011701001310703341', '33011701', '平海路延安路3341', 2, NULL, 80, 0, NULL, 'OFF', '128.9170277', '33.694857', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25050, '33011701001310703342', '33011701', '解放路浣纱路3342', 2, NULL, 80, 0, NULL, 'OFF', '117.5302404', '29.4890656', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25051, '33011701001310703343', '33011701', '解放路建国路3343', 2, NULL, 80, 0, NULL, 'OFF', '125.9001199', '31.3607575', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25052, '33011701001310703344', '33011701', '钱江三桥秋涛路3344', 2, NULL, 80, 0, NULL, 'OFF', '121.9098786', '33.3365908', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25053, '33011701001310703345', '', 'testDevice3345', NULL, NULL, 80, 0, NULL, 'OFF', '116.8490339', '27.6006818', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25054, '33011701001310703346', '', 'dasda3346', NULL, NULL, 80, 0, NULL, '', '123.5155342', '32.993637', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25055, '33011701001310703347', '33011701', '吴山广场3347', 2, NULL, 80, 0, NULL, '', '132.0305701', '27.1661399', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25056, '33011701001310703348', '33011701', '中河路上仓桥路3348', 2, NULL, 80, 0, NULL, 'OFF', '134.6062484', '31.8497888', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25057, '33011701001310703349', '33011701', '西湖大道建国路3349', 2, NULL, 80, 0, NULL, 'OFF', '121.9395323', '32.7505245', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25058, '33011701001310703350', '33011701', '平海路中河路3350', 2, NULL, 80, 0, NULL, 'OFF', '130.878917', '33.2032563', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25059, '33011701001310703351', '33011701', '平海路延安路3351', 2, NULL, 80, 0, NULL, 'OFF', '133.5759885', '32.7647086', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25060, '33011701001310703352', '33011701', '解放路浣纱路3352', 2, NULL, 80, 0, NULL, 'OFF', '120.2431923', '29.2137742', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25061, '33011701001310703353', '33011701', '解放路建国路3353', 2, NULL, 80, 0, NULL, 'OFF', '125.4879964', '32.7747455', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25062, '33011701001310703354', '33011701', '钱江三桥秋涛路3354', 2, NULL, 80, 0, NULL, 'OFF', '131.7104059', '31.2324052', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25063, '33011701001310703355', '', 'testDevice3355', NULL, NULL, 80, 0, NULL, 'OFF', '127.0880411', '32.8377898', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25064, '33011701001310703356', '', 'dasda3356', NULL, NULL, 80, 0, NULL, '', '125.3089881', '25.4917337', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25065, '33011701001310703357', '33011701', '吴山广场3357', 2, NULL, 80, 0, NULL, '', '130.2808178', '33.9452996', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25066, '33011701001310703358', '33011701', '中河路上仓桥路3358', 2, NULL, 80, 0, NULL, 'OFF', '120.4771253', '28.2512973', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25067, '33011701001310703359', '33011701', '西湖大道建国路3359', 2, NULL, 80, 0, NULL, 'OFF', '116.5431738', '34.4205877', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25068, '33011701001310703360', '33011701', '平海路中河路3360', 2, NULL, 80, 0, NULL, 'OFF', '126.2844939', '32.3490469', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25069, '33011701001310703361', '33011701', '平海路延安路3361', 2, NULL, 80, 0, NULL, 'OFF', '126.7929486', '33.4834716', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25070, '33011701001310703362', '33011701', '解放路浣纱路3362', 2, NULL, 80, 0, NULL, 'OFF', '120.1112619', '25.3702178', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25071, '33011701001310703363', '33011701', '解放路建国路3363', 2, NULL, 80, 0, NULL, 'OFF', '125.1774645', '31.4006744', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25072, '33011701001310703364', '33011701', '钱江三桥秋涛路3364', 2, NULL, 80, 0, NULL, 'OFF', '130.5535373', '25.8927188', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25073, '33011701001310703365', '', 'testDevice3365', NULL, NULL, 80, 0, NULL, 'OFF', '122.2352938', '30.2615712', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25074, '33011701001310703366', '', 'dasda3366', NULL, NULL, 80, 0, NULL, '', '124.5158575', '28.6296999', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25075, '33011701001310703367', '33011701', '吴山广场3367', 2, NULL, 80, 0, NULL, '', '120.8734069', '27.3637864', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25076, '33011701001310703368', '33011701', '中河路上仓桥路3368', 2, NULL, 80, 0, NULL, 'OFF', '115.8194623', '25.9298326', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25077, '33011701001310703369', '33011701', '西湖大道建国路3369', 2, NULL, 80, 0, NULL, 'OFF', '121.4770916', '32.5578041', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25078, '33011701001310703370', '33011701', '平海路中河路3370', 2, NULL, 80, 0, NULL, 'OFF', '124.9270718', '29.9995228', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25079, '33011701001310703371', '33011701', '平海路延安路3371', 2, NULL, 80, 0, NULL, 'OFF', '125.2040849', '27.3242021', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25080, '33011701001310703372', '33011701', '解放路浣纱路3372', 2, NULL, 80, 0, NULL, 'OFF', '116.2392098', '31.6224422', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25081, '33011701001310703373', '33011701', '解放路建国路3373', 2, NULL, 80, 0, NULL, 'OFF', '130.5837946', '31.1396052', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25082, '33011701001310703374', '33011701', '钱江三桥秋涛路3374', 2, NULL, 80, 0, NULL, 'OFF', '129.2013442', '25.8306998', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25083, '33011701001310703375', '', 'testDevice3375', NULL, NULL, 80, 0, NULL, 'OFF', '119.2553381', '30.7346838', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25084, '33011701001310703376', '', 'dasda3376', NULL, NULL, 80, 0, NULL, '', '133.6726584', '31.1813198', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25085, '33011701001310703377', '33011701', '吴山广场3377', 2, NULL, 80, 0, NULL, '', '115.5972784', '28.7025477', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25086, '33011701001310703378', '33011701', '中河路上仓桥路3378', 2, NULL, 80, 0, NULL, 'OFF', '121.9684174', '34.9687795', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25087, '33011701001310703379', '33011701', '西湖大道建国路3379', 2, NULL, 80, 0, NULL, 'OFF', '128.0502525', '33.7362548', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25088, '33011701001310703380', '33011701', '平海路中河路3380', 2, NULL, 80, 0, NULL, 'OFF', '119.3460109', '28.7749358', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25089, '33011701001310703381', '33011701', '平海路延安路3381', 2, NULL, 80, 0, NULL, 'OFF', '117.5792978', '27.6659148', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25090, '33011701001310703382', '33011701', '解放路浣纱路3382', 2, NULL, 80, 0, NULL, 'OFF', '134.8584567', '27.004767', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25091, '33011701001310703383', '33011701', '解放路建国路3383', 2, NULL, 80, 0, NULL, 'OFF', '126.5543906', '27.0260909', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25092, '33011701001310703384', '33011701', '钱江三桥秋涛路3384', 2, NULL, 80, 0, NULL, 'OFF', '133.1965836', '29.116154', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25093, '33011701001310703385', '', 'testDevice3385', NULL, NULL, 80, 0, NULL, 'OFF', '131.3197468', '29.5024975', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25094, '33011701001310703386', '', 'dasda3386', NULL, NULL, 80, 0, NULL, '', '122.0089838', '25.1640258', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25095, '33011701001310703387', '33011701', '吴山广场3387', 2, NULL, 80, 0, NULL, '', '121.0856794', '32.3126368', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25096, '33011701001310703388', '33011701', '中河路上仓桥路3388', 2, NULL, 80, 0, NULL, 'OFF', '124.401446', '31.071107', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25097, '33011701001310703389', '33011701', '西湖大道建国路3389', 2, NULL, 80, 0, NULL, 'OFF', '123.7501927', '33.417625', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25098, '33011701001310703390', '33011701', '平海路中河路3390', 2, NULL, 80, 0, NULL, 'OFF', '130.546626', '28.8748041', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25099, '33011701001310703391', '33011701', '平海路延安路3391', 2, NULL, 80, 0, NULL, 'OFF', '126.4825523', '29.1211457', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25100, '33011701001310703392', '33011701', '解放路浣纱路3392', 2, NULL, 80, 0, NULL, 'OFF', '125.7728843', '33.9813167', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25101, '33011701001310703393', '33011701', '解放路建国路3393', 2, NULL, 80, 0, NULL, 'OFF', '134.4167653', '27.5431468', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25102, '33011701001310703394', '33011701', '钱江三桥秋涛路3394', 2, NULL, 80, 0, NULL, 'OFF', '119.7651742', '30.7717839', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25103, '33011701001310703395', '', 'testDevice3395', NULL, NULL, 80, 0, NULL, 'OFF', '120.5755755', '26.2294796', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25104, '33011701001310703396', '', 'dasda3396', NULL, NULL, 80, 0, NULL, '', '128.5823558', '33.8320464', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25105, '33011701001310703397', '33011701', '吴山广场3397', 2, NULL, 80, 0, NULL, '', '126.1850529', '25.4717937', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25106, '33011701001310703398', '33011701', '中河路上仓桥路3398', 2, NULL, 80, 0, NULL, 'OFF', '130.1781979', '30.8628297', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25107, '33011701001310703399', '33011701', '西湖大道建国路3399', 2, NULL, 80, 0, NULL, 'OFF', '117.3358313', '32.8987677', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25108, '33011701001310703400', '33011701', '平海路中河路3400', 2, NULL, 80, 0, NULL, 'OFF', '121.1445635', '26.9053498', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25109, '33011701001310703401', '33011701', '平海路延安路3401', 2, NULL, 80, 0, NULL, 'OFF', '118.7153243', '30.8304459', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25110, '33011701001310703402', '33011701', '解放路浣纱路3402', 2, NULL, 80, 0, NULL, 'OFF', '115.1429314', '28.4361807', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25111, '33011701001310703403', '33011701', '解放路建国路3403', 2, NULL, 80, 0, NULL, 'OFF', '124.5686849', '34.6895662', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25112, '33011701001310703404', '33011701', '钱江三桥秋涛路3404', 2, NULL, 80, 0, NULL, 'OFF', '122.4146307', '33.1392891', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25113, '33011701001310703405', '', 'testDevice3405', NULL, NULL, 80, 0, NULL, 'OFF', '123.3670997', '26.6277471', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25114, '33011701001310703406', '', 'dasda3406', NULL, NULL, 80, 0, NULL, '', '134.5916067', '28.7208687', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25115, '33011701001310703407', '33011701', '吴山广场3407', 2, NULL, 80, 0, NULL, '', '127.8567352', '28.7211022', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25116, '33011701001310703408', '33011701', '中河路上仓桥路3408', 2, NULL, 80, 0, NULL, 'OFF', '120.5088564', '32.4429054', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25117, '33011701001310703409', '33011701', '西湖大道建国路3409', 2, NULL, 80, 0, NULL, 'OFF', '123.9740769', '31.0512207', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25118, '33011701001310703410', '33011701', '平海路中河路3410', 2, NULL, 80, 0, NULL, 'OFF', '123.3438158', '32.9273874', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25119, '33011701001310703411', '33011701', '平海路延安路3411', 2, NULL, 80, 0, NULL, 'OFF', '129.7968492', '26.4832755', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25120, '33011701001310703412', '33011701', '解放路浣纱路3412', 2, NULL, 80, 0, NULL, 'OFF', '123.9527991', '28.6342157', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25121, '33011701001310703413', '33011701', '解放路建国路3413', 2, NULL, 80, 0, NULL, 'OFF', '115.3734487', '28.7212522', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25122, '33011701001310703414', '33011701', '钱江三桥秋涛路3414', 2, NULL, 80, 0, NULL, 'OFF', '130.0088469', '32.703614', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25123, '33011701001310703415', '', 'testDevice3415', NULL, NULL, 80, 0, NULL, 'OFF', '128.9238889', '32.3543139', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25124, '33011701001310703416', '', 'dasda3416', NULL, NULL, 80, 0, NULL, '', '119.5929044', '28.6607278', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25125, '33011701001310703417', '33011701', '吴山广场3417', 2, NULL, 80, 0, NULL, '', '116.1928561', '31.2406977', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25126, '33011701001310703418', '33011701', '中河路上仓桥路3418', 2, NULL, 80, 0, NULL, 'OFF', '127.1855676', '25.2213053', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25127, '33011701001310703419', '33011701', '西湖大道建国路3419', 2, NULL, 80, 0, NULL, 'OFF', '132.3492705', '27.3844337', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25128, '33011701001310703420', '33011701', '平海路中河路3420', 2, NULL, 80, 0, NULL, 'OFF', '125.1896502', '26.2582531', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25129, '33011701001310703421', '33011701', '平海路延安路3421', 2, NULL, 80, 0, NULL, 'OFF', '133.9004402', '34.1379646', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25130, '33011701001310703422', '33011701', '解放路浣纱路3422', 2, NULL, 80, 0, NULL, 'OFF', '118.9332512', '26.9150638', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25131, '33011701001310703423', '33011701', '解放路建国路3423', 2, NULL, 80, 0, NULL, 'OFF', '117.9649357', '27.1614257', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25132, '33011701001310703424', '33011701', '钱江三桥秋涛路3424', 2, NULL, 80, 0, NULL, 'OFF', '118.0249258', '30.0619372', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25133, '33011701001310703425', '', 'testDevice3425', NULL, NULL, 80, 0, NULL, 'OFF', '121.2298223', '33.8254093', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25134, '33011701001310703426', '', 'dasda3426', NULL, NULL, 80, 0, NULL, '', '117.0743348', '33.9412354', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25135, '33011701001310703427', '33011701', '吴山广场3427', 2, NULL, 80, 0, NULL, '', '126.6822077', '33.2299492', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25136, '33011701001310703428', '33011701', '中河路上仓桥路3428', 2, NULL, 80, 0, NULL, 'OFF', '127.1880349', '29.3260402', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25137, '33011701001310703429', '33011701', '西湖大道建国路3429', 2, NULL, 80, 0, NULL, 'OFF', '120.8935519', '31.9403538', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25138, '33011701001310703430', '33011701', '平海路中河路3430', 2, NULL, 80, 0, NULL, 'OFF', '127.9036555', '26.7236485', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25139, '33011701001310703431', '33011701', '平海路延安路3431', 2, NULL, 80, 0, NULL, 'OFF', '121.8376223', '32.7971813', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25140, '33011701001310703432', '33011701', '解放路浣纱路3432', 2, NULL, 80, 0, NULL, 'OFF', '130.4771455', '28.8149616', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25141, '33011701001310703433', '33011701', '解放路建国路3433', 2, NULL, 80, 0, NULL, 'OFF', '131.8728614', '30.6832641', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25142, '33011701001310703434', '33011701', '钱江三桥秋涛路3434', 2, NULL, 80, 0, NULL, 'OFF', '132.932871', '31.9714361', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25143, '33011701001310703435', '', 'testDevice3435', NULL, NULL, 80, 0, NULL, 'OFF', '134.0457714', '32.8073885', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25144, '33011701001310703436', '', 'dasda3436', NULL, NULL, 80, 0, NULL, '', '116.4302446', '33.1226344', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25145, '33011701001310703437', '33011701', '吴山广场3437', 2, NULL, 80, 0, NULL, '', '125.0139093', '32.1910069', '330102', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25146, '33011701001310703438', '33011701', '中河路上仓桥路3438', 2, NULL, 80, 0, NULL, 'OFF', '120.7788133', '26.5871318', '330105', '192.168.1.2', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25147, '33011701001310703439', '33011701', '西湖大道建国路3439', 2, NULL, 80, 0, NULL, 'OFF', '133.8523393', '31.3626385', '330104', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25148, '33011701001310703440', '33011701', '平海路中河路3440', 2, NULL, 80, 0, NULL, 'OFF', '131.9252574', '32.0517976', '330106', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25149, '33011701001310703441', '33011701', '平海路延安路3441', 2, NULL, 80, 0, NULL, 'OFF', '123.0692697', '31.1710726', '330108', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25150, '33011701001310703442', '33011701', '解放路浣纱路3442', 2, NULL, 80, 0, NULL, 'OFF', '124.5705768', '34.6999705', '330109', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25151, '33011701001310703443', '33011701', '解放路建国路3443', 2, NULL, 80, 0, NULL, 'OFF', '118.6450754', '34.9866349', '330110', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25152, '33011701001310703444', '33011701', '钱江三桥秋涛路3444', 2, NULL, 80, 0, NULL, 'OFF', '124.5136472', '25.8332632', '330111', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25153, '33011701001310703445', '', 'testDevice3445', NULL, NULL, 80, 0, NULL, 'OFF', '131.6330105', '29.2064117', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25154, '33011701001310703446', '', 'dasda3446', NULL, NULL, 80, 0, NULL, '', '129.6241117', '33.5322693', '', '192.168.1.1', 0, '2019-06-17 10:30:02', '2019-06-17 10:30:02');
INSERT INTO `vi_device_copy` VALUES (25155, '33011701001310703447', '33011701', '吴山广场3447', 2, NULL, 80, 0, NULL, '', '118.2215274', '25.0421118', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25156, '33011701001310703448', '33011701', '中河路上仓桥路3448', 2, NULL, 80, 0, NULL, 'OFF', '127.2353026', '29.6137514', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25157, '33011701001310703449', '33011701', '西湖大道建国路3449', 2, NULL, 80, 0, NULL, 'OFF', '126.5119316', '27.9424218', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25158, '33011701001310703450', '33011701', '平海路中河路3450', 2, NULL, 80, 0, NULL, 'OFF', '115.8537505', '25.8708552', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25159, '33011701001310703451', '33011701', '平海路延安路3451', 2, NULL, 80, 0, NULL, 'OFF', '124.7329584', '30.5270108', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25160, '33011701001310703452', '33011701', '解放路浣纱路3452', 2, NULL, 80, 0, NULL, 'OFF', '121.1035413', '30.0224889', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25161, '33011701001310703453', '33011701', '解放路建国路3453', 2, NULL, 80, 0, NULL, 'OFF', '116.3188319', '33.5314122', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25162, '33011701001310703454', '33011701', '钱江三桥秋涛路3454', 2, NULL, 80, 0, NULL, 'OFF', '123.283536', '32.5895945', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25163, '33011701001310703455', '', 'testDevice3455', NULL, NULL, 80, 0, NULL, 'OFF', '132.4611852', '27.3537363', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25164, '33011701001310703456', '', 'dasda3456', NULL, NULL, 80, 0, NULL, '', '117.4553183', '33.9998982', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25165, '33011701001310703457', '33011701', '吴山广场3457', 2, NULL, 80, 0, NULL, '', '134.8930366', '32.9382826', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25166, '33011701001310703458', '33011701', '中河路上仓桥路3458', 2, NULL, 80, 0, NULL, 'OFF', '127.0992288', '27.6917187', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25167, '33011701001310703459', '33011701', '西湖大道建国路3459', 2, NULL, 80, 0, NULL, 'OFF', '115.8170348', '34.643746', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25168, '33011701001310703460', '33011701', '平海路中河路3460', 2, NULL, 80, 0, NULL, 'OFF', '122.7874883', '25.1435743', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25169, '33011701001310703461', '33011701', '平海路延安路3461', 2, NULL, 80, 0, NULL, 'OFF', '131.4863376', '26.7866337', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25170, '33011701001310703462', '33011701', '解放路浣纱路3462', 2, NULL, 80, 0, NULL, 'OFF', '134.0692239', '33.502446', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25171, '33011701001310703463', '33011701', '解放路建国路3463', 2, NULL, 80, 0, NULL, 'OFF', '120.8871071', '32.1523292', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25172, '33011701001310703464', '33011701', '钱江三桥秋涛路3464', 2, NULL, 80, 0, NULL, 'OFF', '127.2278644', '25.2543085', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25173, '33011701001310703465', '', 'testDevice3465', NULL, NULL, 80, 0, NULL, 'OFF', '118.4780012', '34.8145549', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25174, '33011701001310703466', '', 'dasda3466', NULL, NULL, 80, 0, NULL, '', '115.7064137', '33.3098493', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25175, '33011701001310703467', '33011701', '吴山广场3467', 2, NULL, 80, 0, NULL, '', '128.0980656', '27.1055823', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25176, '33011701001310703468', '33011701', '中河路上仓桥路3468', 2, NULL, 80, 0, NULL, 'OFF', '118.3710873', '30.5983639', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25177, '33011701001310703469', '33011701', '西湖大道建国路3469', 2, NULL, 80, 0, NULL, 'OFF', '132.5612404', '26.6750729', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25178, '33011701001310703470', '33011701', '平海路中河路3470', 2, NULL, 80, 0, NULL, 'OFF', '132.6929408', '26.5802732', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25179, '33011701001310703471', '33011701', '平海路延安路3471', 2, NULL, 80, 0, NULL, 'OFF', '130.7809835', '27.8761477', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25180, '33011701001310703472', '33011701', '解放路浣纱路3472', 2, NULL, 80, 0, NULL, 'OFF', '120.8260958', '34.6399191', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25181, '33011701001310703473', '33011701', '解放路建国路3473', 2, NULL, 80, 0, NULL, 'OFF', '116.787529', '34.5711525', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25182, '33011701001310703474', '33011701', '钱江三桥秋涛路3474', 2, NULL, 80, 0, NULL, 'OFF', '126.459358', '33.9360058', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25183, '33011701001310703475', '', 'testDevice3475', NULL, NULL, 80, 0, NULL, 'OFF', '126.9342038', '30.9665718', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25184, '33011701001310703476', '', 'dasda3476', NULL, NULL, 80, 0, NULL, '', '120.2929456', '28.024842', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25185, '33011701001310703477', '33011701', '吴山广场3477', 2, NULL, 80, 0, NULL, '', '125.6621171', '32.2244946', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25186, '33011701001310703478', '33011701', '中河路上仓桥路3478', 2, NULL, 80, 0, NULL, 'OFF', '132.4317492', '32.0479474', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25187, '33011701001310703479', '33011701', '西湖大道建国路3479', 2, NULL, 80, 0, NULL, 'OFF', '130.1723953', '28.5662533', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25188, '33011701001310703480', '33011701', '平海路中河路3480', 2, NULL, 80, 0, NULL, 'OFF', '118.5667298', '31.6874249', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25189, '33011701001310703481', '33011701', '平海路延安路3481', 2, NULL, 80, 0, NULL, 'OFF', '127.3164637', '27.738365', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25190, '33011701001310703482', '33011701', '解放路浣纱路3482', 2, NULL, 80, 0, NULL, 'OFF', '125.8821295', '28.6295503', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25191, '33011701001310703483', '33011701', '解放路建国路3483', 2, NULL, 80, 0, NULL, 'OFF', '132.4612572', '34.9326571', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25192, '33011701001310703484', '33011701', '钱江三桥秋涛路3484', 2, NULL, 80, 0, NULL, 'OFF', '129.6598982', '33.7746347', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25193, '33011701001310703485', '', 'testDevice3485', NULL, NULL, 80, 0, NULL, 'OFF', '115.91572', '29.0752024', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25194, '33011701001310703486', '', 'dasda3486', NULL, NULL, 80, 0, NULL, '', '115.5989059', '29.0521084', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25195, '33011701001310703487', '33011701', '吴山广场3487', 2, NULL, 80, 0, NULL, '', '115.2473703', '33.034935', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25196, '33011701001310703488', '33011701', '中河路上仓桥路3488', 2, NULL, 80, 0, NULL, 'OFF', '134.4401345', '33.0183502', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25197, '33011701001310703489', '33011701', '西湖大道建国路3489', 2, NULL, 80, 0, NULL, 'OFF', '131.458562', '30.9869463', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25198, '33011701001310703490', '33011701', '平海路中河路3490', 2, NULL, 80, 0, NULL, 'OFF', '118.9724072', '30.8796812', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25199, '33011701001310703491', '33011701', '平海路延安路3491', 2, NULL, 80, 0, NULL, 'OFF', '125.4863507', '26.4375674', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25200, '33011701001310703492', '33011701', '解放路浣纱路3492', 2, NULL, 80, 0, NULL, 'OFF', '115.5145325', '34.5487939', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25201, '33011701001310703493', '33011701', '解放路建国路3493', 2, NULL, 80, 0, NULL, 'OFF', '126.1136111', '28.4312673', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25202, '33011701001310703494', '33011701', '钱江三桥秋涛路3494', 2, NULL, 80, 0, NULL, 'OFF', '129.0244586', '33.5099553', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25203, '33011701001310703495', '', 'testDevice3495', NULL, NULL, 80, 0, NULL, 'OFF', '131.7814602', '27.2559748', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25204, '33011701001310703496', '', 'dasda3496', NULL, NULL, 80, 0, NULL, '', '116.8339261', '30.7500084', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25205, '33011701001310703497', '33011701', '吴山广场3497', 2, NULL, 80, 0, NULL, '', '133.8252502', '26.9821179', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25206, '33011701001310703498', '33011701', '中河路上仓桥路3498', 2, NULL, 80, 0, NULL, 'OFF', '123.6244735', '27.6605646', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25207, '33011701001310703499', '33011701', '西湖大道建国路3499', 2, NULL, 80, 0, NULL, 'OFF', '121.6466173', '32.3564697', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25208, '33011701001310703500', '33011701', '平海路中河路3500', 2, NULL, 80, 0, NULL, 'OFF', '122.3596667', '33.8006548', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25209, '33011701001310703501', '33011701', '平海路延安路3501', 2, NULL, 80, 0, NULL, 'OFF', '131.8584824', '26.9338652', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25210, '33011701001310703502', '33011701', '解放路浣纱路3502', 2, NULL, 80, 0, NULL, 'OFF', '117.2134124', '28.267362', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25211, '33011701001310703503', '33011701', '解放路建国路3503', 2, NULL, 80, 0, NULL, 'OFF', '115.4916153', '25.5352147', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25212, '33011701001310703504', '33011701', '钱江三桥秋涛路3504', 2, NULL, 80, 0, NULL, 'OFF', '130.81784', '27.873988', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25213, '33011701001310703505', '', 'testDevice3505', NULL, NULL, 80, 0, NULL, 'OFF', '132.6143549', '27.764296', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25214, '33011701001310703506', '', 'dasda3506', NULL, NULL, 80, 0, NULL, '', '115.6182549', '30.1995163', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25215, '33011701001310703507', '33011701', '吴山广场3507', 2, NULL, 80, 0, NULL, '', '125.2482105', '32.7046936', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25216, '33011701001310703508', '33011701', '中河路上仓桥路3508', 2, NULL, 80, 0, NULL, 'OFF', '124.3862882', '27.9249198', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25217, '33011701001310703509', '33011701', '西湖大道建国路3509', 2, NULL, 80, 0, NULL, 'OFF', '131.1868103', '26.5105182', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25218, '33011701001310703510', '33011701', '平海路中河路3510', 2, NULL, 80, 0, NULL, 'OFF', '127.7751876', '33.7778322', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25219, '33011701001310703511', '33011701', '平海路延安路3511', 2, NULL, 80, 0, NULL, 'OFF', '130.3155075', '34.3576065', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25220, '33011701001310703512', '33011701', '解放路浣纱路3512', 2, NULL, 80, 0, NULL, 'OFF', '133.2519752', '25.4545365', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25221, '33011701001310703513', '33011701', '解放路建国路3513', 2, NULL, 80, 0, NULL, 'OFF', '120.3133544', '29.1998629', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25222, '33011701001310703514', '33011701', '钱江三桥秋涛路3514', 2, NULL, 80, 0, NULL, 'OFF', '126.8108469', '34.6357056', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25223, '33011701001310703515', '', 'testDevice3515', NULL, NULL, 80, 0, NULL, 'OFF', '118.114172', '30.5789397', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25224, '33011701001310703516', '', 'dasda3516', NULL, NULL, 80, 0, NULL, '', '115.1383199', '33.987582', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25225, '33011701001310703517', '33011701', '吴山广场3517', 2, NULL, 80, 0, NULL, '', '126.3490839', '33.2010912', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25226, '33011701001310703518', '33011701', '中河路上仓桥路3518', 2, NULL, 80, 0, NULL, 'OFF', '131.3304606', '29.0427104', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25227, '33011701001310703519', '33011701', '西湖大道建国路3519', 2, NULL, 80, 0, NULL, 'OFF', '122.6050521', '30.6102787', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25228, '33011701001310703520', '33011701', '平海路中河路3520', 2, NULL, 80, 0, NULL, 'OFF', '124.033879', '30.9232627', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25229, '33011701001310703521', '33011701', '平海路延安路3521', 2, NULL, 80, 0, NULL, 'OFF', '117.3542393', '27.7854778', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25230, '33011701001310703522', '33011701', '解放路浣纱路3522', 2, NULL, 80, 0, NULL, 'OFF', '119.6695603', '31.1576011', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25231, '33011701001310703523', '33011701', '解放路建国路3523', 2, NULL, 80, 0, NULL, 'OFF', '131.2850841', '27.4315722', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25232, '33011701001310703524', '33011701', '钱江三桥秋涛路3524', 2, NULL, 80, 0, NULL, 'OFF', '122.4167403', '28.6850582', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25233, '33011701001310703525', '', 'testDevice3525', NULL, NULL, 80, 0, NULL, 'OFF', '123.2284497', '26.1305748', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25234, '33011701001310703526', '', 'dasda3526', NULL, NULL, 80, 0, NULL, '', '133.8920284', '29.5976995', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25235, '33011701001310703527', '33011701', '吴山广场3527', 2, NULL, 80, 0, NULL, '', '124.7747935', '34.5967737', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25236, '33011701001310703528', '33011701', '中河路上仓桥路3528', 2, NULL, 80, 0, NULL, 'OFF', '127.197883', '29.19077', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25237, '33011701001310703529', '33011701', '西湖大道建国路3529', 2, NULL, 80, 0, NULL, 'OFF', '126.6650349', '27.1635295', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25238, '33011701001310703530', '33011701', '平海路中河路3530', 2, NULL, 80, 0, NULL, 'OFF', '116.7315262', '33.2453378', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25239, '33011701001310703531', '33011701', '平海路延安路3531', 2, NULL, 80, 0, NULL, 'OFF', '128.6625269', '29.7361006', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25240, '33011701001310703532', '33011701', '解放路浣纱路3532', 2, NULL, 80, 0, NULL, 'OFF', '118.1180566', '33.9444898', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25241, '33011701001310703533', '33011701', '解放路建国路3533', 2, NULL, 80, 0, NULL, 'OFF', '129.6027029', '25.5141477', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25242, '33011701001310703534', '33011701', '钱江三桥秋涛路3534', 2, NULL, 80, 0, NULL, 'OFF', '118.6593452', '30.7372696', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25243, '33011701001310703535', '', 'testDevice3535', NULL, NULL, 80, 0, NULL, 'OFF', '129.4886178', '32.1439049', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25244, '33011701001310703536', '', 'dasda3536', NULL, NULL, 80, 0, NULL, '', '116.4650541', '33.5077162', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25245, '33011701001310703537', '33011701', '吴山广场3537', 2, NULL, 80, 0, NULL, '', '118.8594177', '26.1068667', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25246, '33011701001310703538', '33011701', '中河路上仓桥路3538', 2, NULL, 80, 0, NULL, 'OFF', '129.901927', '25.0111849', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25247, '33011701001310703539', '33011701', '西湖大道建国路3539', 2, NULL, 80, 0, NULL, 'OFF', '117.9313825', '31.7353249', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25248, '33011701001310703540', '33011701', '平海路中河路3540', 2, NULL, 80, 0, NULL, 'OFF', '124.9511319', '28.6430699', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25249, '33011701001310703541', '33011701', '平海路延安路3541', 2, NULL, 80, 0, NULL, 'OFF', '115.9615128', '33.0093753', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25250, '33011701001310703542', '33011701', '解放路浣纱路3542', 2, NULL, 80, 0, NULL, 'OFF', '129.9541691', '34.1176672', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25251, '33011701001310703543', '33011701', '解放路建国路3543', 2, NULL, 80, 0, NULL, 'OFF', '126.8863074', '26.5602101', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25252, '33011701001310703544', '33011701', '钱江三桥秋涛路3544', 2, NULL, 80, 0, NULL, 'OFF', '129.5690305', '25.4480494', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25253, '33011701001310703545', '', 'testDevice3545', NULL, NULL, 80, 0, NULL, 'OFF', '132.1862309', '32.5596168', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25254, '33011701001310703546', '', 'dasda3546', NULL, NULL, 80, 0, NULL, '', '117.2240636', '31.4539363', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25255, '33011701001310703547', '33011701', '吴山广场3547', 2, NULL, 80, 0, NULL, '', '134.5616259', '34.5908313', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25256, '33011701001310703548', '33011701', '中河路上仓桥路3548', 2, NULL, 80, 0, NULL, 'OFF', '126.1359393', '33.5923481', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25257, '33011701001310703549', '33011701', '西湖大道建国路3549', 2, NULL, 80, 0, NULL, 'OFF', '131.9948192', '29.1892466', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25258, '33011701001310703550', '33011701', '平海路中河路3550', 2, NULL, 80, 0, NULL, 'OFF', '126.5662791', '30.1691891', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25259, '33011701001310703551', '33011701', '平海路延安路3551', 2, NULL, 80, 0, NULL, 'OFF', '121.8469383', '28.278206', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25260, '33011701001310703552', '33011701', '解放路浣纱路3552', 2, NULL, 80, 0, NULL, 'OFF', '134.5358546', '25.8834632', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25261, '33011701001310703553', '33011701', '解放路建国路3553', 2, NULL, 80, 0, NULL, 'OFF', '132.1384591', '29.5826984', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25262, '33011701001310703554', '33011701', '钱江三桥秋涛路3554', 2, NULL, 80, 0, NULL, 'OFF', '122.0847321', '25.2631026', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25263, '33011701001310703555', '', 'testDevice3555', NULL, NULL, 80, 0, NULL, 'OFF', '119.0082839', '32.567418', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25264, '33011701001310703556', '', 'dasda3556', NULL, NULL, 80, 0, NULL, '', '133.7872236', '32.0477826', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25265, '33011701001310703557', '33011701', '吴山广场3557', 2, NULL, 80, 0, NULL, '', '116.9112671', '27.5366595', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25266, '33011701001310703558', '33011701', '中河路上仓桥路3558', 2, NULL, 80, 0, NULL, 'OFF', '128.1946654', '26.5399498', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25267, '33011701001310703559', '33011701', '西湖大道建国路3559', 2, NULL, 80, 0, NULL, 'OFF', '115.2395264', '25.089771', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25268, '33011701001310703560', '33011701', '平海路中河路3560', 2, NULL, 80, 0, NULL, 'OFF', '116.6136362', '30.8290057', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25269, '33011701001310703561', '33011701', '平海路延安路3561', 2, NULL, 80, 0, NULL, 'OFF', '122.3496027', '33.875716', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25270, '33011701001310703562', '33011701', '解放路浣纱路3562', 2, NULL, 80, 0, NULL, 'OFF', '126.9071052', '31.8915632', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25271, '33011701001310703563', '33011701', '解放路建国路3563', 2, NULL, 80, 0, NULL, 'OFF', '132.4867186', '32.8306681', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25272, '33011701001310703564', '33011701', '钱江三桥秋涛路3564', 2, NULL, 80, 0, NULL, 'OFF', '126.7122781', '33.4786514', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25273, '33011701001310703565', '', 'testDevice3565', NULL, NULL, 80, 0, NULL, 'OFF', '121.1012352', '33.9012529', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25274, '33011701001310703566', '', 'dasda3566', NULL, NULL, 80, 0, NULL, '', '130.3693423', '34.0703107', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25275, '33011701001310703567', '33011701', '吴山广场3567', 2, NULL, 80, 0, NULL, '', '133.5430067', '33.6477949', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25276, '33011701001310703568', '33011701', '中河路上仓桥路3568', 2, NULL, 80, 0, NULL, 'OFF', '121.6070072', '31.0280427', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25277, '33011701001310703569', '33011701', '西湖大道建国路3569', 2, NULL, 80, 0, NULL, 'OFF', '132.4060165', '29.1968294', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25278, '33011701001310703570', '33011701', '平海路中河路3570', 2, NULL, 80, 0, NULL, 'OFF', '122.2090614', '27.9000189', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25279, '33011701001310703571', '33011701', '平海路延安路3571', 2, NULL, 80, 0, NULL, 'OFF', '118.8272581', '26.9096067', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25280, '33011701001310703572', '33011701', '解放路浣纱路3572', 2, NULL, 80, 0, NULL, 'OFF', '132.5091068', '25.8479772', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25281, '33011701001310703573', '33011701', '解放路建国路3573', 2, NULL, 80, 0, NULL, 'OFF', '131.0637603', '33.5110662', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25282, '33011701001310703574', '33011701', '钱江三桥秋涛路3574', 2, NULL, 80, 0, NULL, 'OFF', '122.7914818', '25.0113996', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25283, '33011701001310703575', '', 'testDevice3575', NULL, NULL, 80, 0, NULL, 'OFF', '125.7661286', '29.5237996', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25284, '33011701001310703576', '', 'dasda3576', NULL, NULL, 80, 0, NULL, '', '125.4561983', '27.5847997', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25285, '33011701001310703577', '33011701', '吴山广场3577', 2, NULL, 80, 0, NULL, '', '134.9826061', '34.3525998', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25286, '33011701001310703578', '33011701', '中河路上仓桥路3578', 2, NULL, 80, 0, NULL, 'OFF', '123.5444365', '34.0086002', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25287, '33011701001310703579', '33011701', '西湖大道建国路3579', 2, NULL, 80, 0, NULL, 'OFF', '117.7743648', '31.9852021', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25288, '33011701001310703580', '33011701', '平海路中河路3580', 2, NULL, 80, 0, NULL, 'OFF', '123.238515', '32.9002103', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25289, '33011701001310703581', '33011701', '平海路延安路3581', 2, NULL, 80, 0, NULL, 'OFF', '127.8694812', '33.5454454', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25290, '33011701001310703582', '33011701', '解放路浣纱路3582', 2, NULL, 80, 0, NULL, 'OFF', '134.6318616', '34.0265963', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25291, '33011701001310703583', '33011701', '解放路建国路3583', 2, NULL, 80, 0, NULL, 'OFF', '134.5508652', '34.4966459', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25292, '33011701001310703584', '33011701', '钱江三桥秋涛路3584', 2, NULL, 80, 0, NULL, 'OFF', '133.8587417', '25.4034407', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25293, '33011701001310703585', '', 'testDevice3585', NULL, NULL, 80, 0, NULL, 'OFF', '130.6411136', '28.5272662', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25294, '33011701001310703586', '', 'dasda3586', NULL, NULL, 80, 0, NULL, '', '116.6293433', '31.426009', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25295, '33011701001310703587', '33011701', '吴山广场3587', 2, NULL, 80, 0, NULL, '', '116.2233762', '26.548247', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25296, '33011701001310703588', '33011701', '中河路上仓桥路3588', 2, NULL, 80, 0, NULL, 'OFF', '116.2288521', '33.4632083', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25297, '33011701001310703589', '33011701', '西湖大道建国路3589', 2, NULL, 80, 0, NULL, 'OFF', '117.4741322', '32.6713008', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25298, '33011701001310703590', '33011701', '平海路中河路3590', 2, NULL, 80, 0, NULL, 'OFF', '123.6841055', '27.9668794', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25299, '33011701001310703591', '33011701', '平海路延安路3591', 2, NULL, 80, 0, NULL, 'OFF', '130.9981315', '26.8204949', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25300, '33011701001310703592', '33011701', '解放路浣纱路3592', 2, NULL, 80, 0, NULL, 'OFF', '128.9383416', '25.2018367', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25301, '33011701001310703593', '33011701', '解放路建国路3593', 2, NULL, 80, 0, NULL, 'OFF', '116.697314', '30.5476992', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25302, '33011701001310703594', '33011701', '钱江三桥秋涛路3594', 2, NULL, 80, 0, NULL, 'OFF', '121.671546', '32.1329861', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25303, '33011701001310703595', '', 'testDevice3595', NULL, NULL, 80, 0, NULL, 'OFF', '123.2657886', '34.0218333', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25304, '33011701001310703596', '', 'dasda3596', NULL, NULL, 80, 0, NULL, '', '116.3143056', '28.7102084', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25305, '33011701001310703597', '33011701', '吴山广场3597', 2, NULL, 80, 0, NULL, '', '116.7741627', '26.4855423', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25306, '33011701001310703598', '33011701', '中河路上仓桥路3598', 2, NULL, 80, 0, NULL, 'OFF', '119.9278973', '31.2970869', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25307, '33011701001310703599', '33011701', '西湖大道建国路3599', 2, NULL, 80, 0, NULL, 'OFF', '134.3169989', '32.0288078', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25308, '33011701001310703600', '33011701', '平海路中河路3600', 2, NULL, 80, 0, NULL, 'OFF', '116.8013032', '31.2527787', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25309, '33011701001310703601', '33011701', '平海路延安路3601', 2, NULL, 80, 0, NULL, 'OFF', '126.05552', '25.1774702', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25310, '33011701001310703602', '33011701', '解放路浣纱路3602', 2, NULL, 80, 0, NULL, 'OFF', '124.873691', '27.1290152', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25311, '33011701001310703603', '33011701', '解放路建国路3603', 2, NULL, 80, 0, NULL, 'OFF', '131.2018957', '25.1126656', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25312, '33011701001310703604', '33011701', '钱江三桥秋涛路3604', 2, NULL, 80, 0, NULL, 'OFF', '126.3884062', '29.1762827', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25313, '33011701001310703605', '', 'testDevice3605', NULL, NULL, 80, 0, NULL, 'OFF', '123.3363444', '25.543417', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25314, '33011701001310703606', '', 'dasda3606', NULL, NULL, 80, 0, NULL, '', '122.5165042', '25.1882374', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25315, '33011701001310703607', '33011701', '吴山广场3607', 2, NULL, 80, 0, NULL, '', '127.5734884', '34.310936', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25316, '33011701001310703608', '33011701', '中河路上仓桥路3608', 2, NULL, 80, 0, NULL, 'OFF', '115.3179298', '30.9899684', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25317, '33011701001310703609', '33011701', '西湖大道建国路3609', 2, NULL, 80, 0, NULL, 'OFF', '118.8691845', '27.0170341', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25318, '33011701001310703610', '33011701', '平海路中河路3610', 2, NULL, 80, 0, NULL, 'OFF', '133.3921337', '27.1152655', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25319, '33011701001310703611', '33011701', '平海路延安路3611', 2, NULL, 80, 0, NULL, 'OFF', '115.3531158', '29.5252255', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25320, '33011701001310703612', '33011701', '解放路浣纱路3612', 2, NULL, 80, 0, NULL, 'OFF', '121.5891782', '31.2803316', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25321, '33011701001310703613', '33011701', '解放路建国路3613', 2, NULL, 80, 0, NULL, 'OFF', '126.8865445', '32.8259815', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25322, '33011701001310703614', '33011701', '钱江三桥秋涛路3614', 2, NULL, 80, 0, NULL, 'OFF', '134.6651882', '25.2889133', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25323, '33011701001310703615', '', 'testDevice3615', NULL, NULL, 80, 0, NULL, 'OFF', '117.6663085', '32.9666223', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25324, '33011701001310703616', '', 'dasda3616', NULL, NULL, 80, 0, NULL, '', '129.3359782', '33.9663716', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25325, '33011701001310703617', '33011701', '吴山广场3617', 2, NULL, 80, 0, NULL, '', '118.6809664', '25.9319917', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25326, '33011701001310703618', '33011701', '中河路上仓桥路3618', 2, NULL, 80, 0, NULL, 'OFF', '130.3968977', '32.7608437', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25327, '33011701001310703619', '33011701', '西湖大道建国路3619', 2, NULL, 80, 0, NULL, 'OFF', '120.9415899', '31.008244', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25328, '33011701001310703620', '33011701', '平海路中河路3620', 2, NULL, 80, 0, NULL, 'OFF', '118.5172573', '31.758689', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25329, '33011701001310703621', '33011701', '平海路延安路3621', 2, NULL, 80, 0, NULL, 'OFF', '134.7615173', '30.7687132', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25330, '33011701001310703622', '33011701', '解放路浣纱路3622', 2, NULL, 80, 0, NULL, 'OFF', '123.2558154', '33.5674996', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25331, '33011701001310703623', '33011701', '解放路建国路3623', 2, NULL, 80, 0, NULL, 'OFF', '116.9945256', '30.5313584', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25332, '33011701001310703624', '33011701', '钱江三桥秋涛路3624', 2, NULL, 80, 0, NULL, 'OFF', '120.2051823', '26.9542938', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25333, '33011701001310703625', '', 'testDevice3625', NULL, NULL, 80, 0, NULL, 'OFF', '115.0423356', '28.177394', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25334, '33011701001310703626', '', 'dasda3626', NULL, NULL, 80, 0, NULL, '', '119.5961315', '25.0240888', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25335, '33011701001310703627', '33011701', '吴山广场3627', 2, NULL, 80, 0, NULL, '', '117.8536512', '25.5882622', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25336, '33011701001310703628', '33011701', '中河路上仓桥路3628', 2, NULL, 80, 0, NULL, 'OFF', '115.4798623', '27.8690451', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25337, '33011701001310703629', '33011701', '西湖大道建国路3629', 2, NULL, 80, 0, NULL, 'OFF', '128.8383585', '27.5804391', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25338, '33011701001310703630', '33011701', '平海路中河路3630', 2, NULL, 80, 0, NULL, 'OFF', '122.752206', '29.2950605', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25339, '33011701001310703631', '33011701', '平海路延安路3631', 2, NULL, 80, 0, NULL, 'OFF', '132.2459553', '28.7339857', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25340, '33011701001310703632', '33011701', '解放路浣纱路3632', 2, NULL, 80, 0, NULL, 'OFF', '117.9731591', '30.7847472', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25341, '33011701001310703633', '33011701', '解放路建国路3633', 2, NULL, 80, 0, NULL, 'OFF', '118.1279304', '32.7217791', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25342, '33011701001310703634', '33011701', '钱江三桥秋涛路3634', 2, NULL, 80, 0, NULL, 'OFF', '121.7201751', '26.2546545', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25343, '33011701001310703635', '', 'testDevice3635', NULL, NULL, 80, 0, NULL, 'OFF', '119.2170851', '28.1079353', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25344, '33011701001310703636', '', 'dasda3636', NULL, NULL, 80, 0, NULL, '', '115.9249008', '26.7757134', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25345, '33011701001310703637', '33011701', '吴山广场3637', 2, NULL, 80, 0, NULL, '', '126.9732492', '34.5547614', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25346, '33011701001310703638', '33011701', '中河路上仓桥路3638', 2, NULL, 80, 0, NULL, 'OFF', '132.0915443', '27.4466671', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25347, '33011701001310703639', '33011701', '西湖大道建国路3639', 2, NULL, 80, 0, NULL, 'OFF', '124.5379746', '28.5690515', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25348, '33011701001310703640', '33011701', '平海路中河路3640', 2, NULL, 80, 0, NULL, 'OFF', '131.4152406', '25.5052564', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25349, '33011701001310703641', '33011701', '平海路延安路3641', 2, NULL, 80, 0, NULL, 'OFF', '128.4622797', '26.8191281', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25350, '33011701001310703642', '33011701', '解放路浣纱路3642', 2, NULL, 80, 0, NULL, 'OFF', '133.0656775', '32.5798715', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25351, '33011701001310703643', '33011701', '解放路建国路3643', 2, NULL, 80, 0, NULL, 'OFF', '124.9415491', '27.4419734', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25352, '33011701001310703644', '33011701', '钱江三桥秋涛路3644', 2, NULL, 80, 0, NULL, 'OFF', '130.5107137', '34.4702529', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25353, '33011701001310703645', '', 'testDevice3645', NULL, NULL, 80, 0, NULL, 'OFF', '122.7289217', '25.0253447', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25354, '33011701001310703646', '', 'dasda3646', NULL, NULL, 80, 0, NULL, '', '127.1124679', '26.7159649', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25355, '33011701001310703647', '33011701', '吴山广场3647', 2, NULL, 80, 0, NULL, '', '132.3755749', '33.5037906', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25356, '33011701001310703648', '33011701', '中河路上仓桥路3648', 2, NULL, 80, 0, NULL, 'OFF', '125.5404716', '32.3710587', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25357, '33011701001310703649', '33011701', '西湖大道建国路3649', 2, NULL, 80, 0, NULL, 'OFF', '115.5756338', '26.3439222', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25358, '33011701001310703650', '33011701', '平海路中河路3650', 2, NULL, 80, 0, NULL, 'OFF', '126.256755', '29.6064352', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25359, '33011701001310703651', '33011701', '平海路延安路3651', 2, NULL, 80, 0, NULL, 'OFF', '129.5568741', '34.0004095', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25360, '33011701001310703652', '33011701', '解放路浣纱路3652', 2, NULL, 80, 0, NULL, 'OFF', '134.0141063', '26.1827422', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25361, '33011701001310703653', '33011701', '解放路建国路3653', 2, NULL, 80, 0, NULL, 'OFF', '126.3999098', '33.9124829', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25362, '33011701001310703654', '33011701', '钱江三桥秋涛路3654', 2, NULL, 80, 0, NULL, 'OFF', '134.9572305', '26.0141882', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25363, '33011701001310703655', '', 'testDevice3655', NULL, NULL, 80, 0, NULL, 'OFF', '120.5864239', '33.3334925', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25364, '33011701001310703656', '', 'dasda3656', NULL, NULL, 80, 0, NULL, '', '123.0604285', '33.6248981', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25365, '33011701001310703657', '33011701', '吴山广场3657', 2, NULL, 80, 0, NULL, '', '118.5428716', '33.1240134', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25366, '33011701001310703658', '33011701', '中河路上仓桥路3658', 2, NULL, 80, 0, NULL, 'OFF', '128.5330732', '29.7453728', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25367, '33011701001310703659', '33011701', '西湖大道建国路3659', 2, NULL, 80, 0, NULL, 'OFF', '132.0367517', '34.3548244', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25368, '33011701001310703660', '33011701', '平海路中河路3660', 2, NULL, 80, 0, NULL, 'OFF', '119.5845396', '27.5380039', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25369, '33011701001310703661', '33011701', '平海路延安路3661', 2, NULL, 80, 0, NULL, 'OFF', '126.8124436', '29.6255466', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25370, '33011701001310703662', '33011701', '解放路浣纱路3662', 2, NULL, 80, 0, NULL, 'OFF', '120.3085996', '30.5137216', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25371, '33011701001310703663', '33011701', '解放路建国路3663', 2, NULL, 80, 0, NULL, 'OFF', '126.1056679', '28.6919686', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25372, '33011701001310703664', '33011701', '钱江三桥秋涛路3664', 2, NULL, 80, 0, NULL, 'OFF', '134.6025414', '26.9186782', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25373, '33011701001310703665', '', 'testDevice3665', NULL, NULL, 80, 0, NULL, 'OFF', '119.6957039', '33.5174859', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25374, '33011701001310703666', '', 'dasda3666', NULL, NULL, 80, 0, NULL, '', '119.6708957', '31.8313949', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25375, '33011701001310703667', '33011701', '吴山广场3667', 2, NULL, 80, 0, NULL, '', '124.2673675', '33.6045174', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25376, '33011701001310703668', '33011701', '中河路上仓桥路3668', 2, NULL, 80, 0, NULL, 'OFF', '127.3241511', '27.5284023', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25377, '33011701001310703669', '33011701', '西湖大道建国路3669', 2, NULL, 80, 0, NULL, 'OFF', '128.8186538', '31.8284596', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25378, '33011701001310703670', '33011701', '平海路中河路3670', 2, NULL, 80, 0, NULL, 'OFF', '127.1208161', '31.5570916', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25379, '33011701001310703671', '33011701', '平海路延安路3671', 2, NULL, 80, 0, NULL, 'OFF', '134.1481196', '27.3000795', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25380, '33011701001310703672', '33011701', '解放路浣纱路3672', 2, NULL, 80, 0, NULL, 'OFF', '134.3781505', '26.8291231', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25381, '33011701001310703673', '33011701', '解放路建国路3673', 2, NULL, 80, 0, NULL, 'OFF', '134.4463942', '27.245377', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25382, '33011701001310703674', '33011701', '钱江三桥秋涛路3674', 2, NULL, 80, 0, NULL, 'OFF', '134.09752', '30.7395163', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25383, '33011701001310703675', '', 'testDevice3675', NULL, NULL, 80, 0, NULL, 'OFF', '132.1484181', '26.9614509', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25384, '33011701001310703676', '', 'dasda3676', NULL, NULL, 80, 0, NULL, '', '123.4495312', '27.5887057', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25385, '33011701001310703677', '33011701', '吴山广场3677', 2, NULL, 80, 0, NULL, '', '125.8024025', '32.0591762', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25386, '33011701001310703678', '33011701', '中河路上仓桥路3678', 2, NULL, 80, 0, NULL, 'OFF', '123.6634191', '32.5297642', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25387, '33011701001310703679', '33011701', '西湖大道建国路3679', 2, NULL, 80, 0, NULL, 'OFF', '125.909889', '31.4712927', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25388, '33011701001310703680', '33011701', '平海路中河路3680', 2, NULL, 80, 0, NULL, 'OFF', '123.559188', '34.7671714', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25389, '33011701001310703681', '33011701', '平海路延安路3681', 2, NULL, 80, 0, NULL, 'OFF', '125.0662737', '34.4219789', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25390, '33011701001310703682', '33011701', '解放路浣纱路3682', 2, NULL, 80, 0, NULL, 'OFF', '119.6538052', '32.8083807', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25391, '33011701001310703683', '33011701', '解放路建国路3683', 2, NULL, 80, 0, NULL, 'OFF', '128.0702053', '25.7759671', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25392, '33011701001310703684', '33011701', '钱江三桥秋涛路3684', 2, NULL, 80, 0, NULL, 'OFF', '126.3896117', '25.4546935', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25393, '33011701001310703685', '', 'testDevice3685', NULL, NULL, 80, 0, NULL, 'OFF', '132.7374431', '34.9455668', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25394, '33011701001310703686', '', 'dasda3686', NULL, NULL, 80, 0, NULL, '', '129.5183812', '33.3637537', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25395, '33011701001310703687', '33011701', '吴山广场3687', 2, NULL, 80, 0, NULL, '', '134.3795772', '26.9820684', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25396, '33011701001310703688', '33011701', '中河路上仓桥路3688', 2, NULL, 80, 0, NULL, 'OFF', '128.3427428', '29.819081', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25397, '33011701001310703689', '33011701', '西湖大道建国路3689', 2, NULL, 80, 0, NULL, 'OFF', '123.5749833', '33.1492004', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25398, '33011701001310703690', '33011701', '平海路中河路3690', 2, NULL, 80, 0, NULL, 'OFF', '117.8466885', '31.2887592', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25399, '33011701001310703691', '33011701', '平海路延安路3691', 2, NULL, 80, 0, NULL, 'OFF', '123.5084931', '31.9961949', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25400, '33011701001310703692', '33011701', '解放路浣纱路3692', 2, NULL, 80, 0, NULL, 'OFF', '129.0024009', '31.1146975', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25401, '33011701001310703693', '33011701', '解放路建国路3693', 2, NULL, 80, 0, NULL, 'OFF', '119.4865259', '34.5849028', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25402, '33011701001310703694', '33011701', '钱江三桥秋涛路3694', 2, NULL, 80, 0, NULL, 'OFF', '115.4254274', '34.580422', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25403, '33011701001310703695', '', 'testDevice3695', NULL, NULL, 80, 0, NULL, 'OFF', '123.6675597', '34.1474017', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25404, '33011701001310703696', '', 'dasda3696', NULL, NULL, 80, 0, NULL, '', '117.061517', '31.9957431', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25405, '33011701001310703697', '33011701', '吴山广场3697', 2, NULL, 80, 0, NULL, '', '119.3049067', '32.5365106', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25406, '33011701001310703698', '33011701', '中河路上仓桥路3698', 2, NULL, 80, 0, NULL, 'OFF', '130.3399829', '31.695324', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25407, '33011701001310703699', '33011701', '西湖大道建国路3699', 2, NULL, 80, 0, NULL, 'OFF', '118.785195', '25.8670883', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25408, '33011701001310703700', '33011701', '平海路中河路3700', 2, NULL, 80, 0, NULL, 'OFF', '127.9060269', '29.24947', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25409, '33011701001310703701', '33011701', '平海路延安路3701', 2, NULL, 80, 0, NULL, 'OFF', '128.1745501', '33.6460855', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25410, '33011701001310703702', '33011701', '解放路浣纱路3702', 2, NULL, 80, 0, NULL, 'OFF', '122.1546705', '25.4820176', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25411, '33011701001310703703', '33011701', '解放路建国路3703', 2, NULL, 80, 0, NULL, 'OFF', '131.2497029', '31.4718319', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25412, '33011701001310703704', '33011701', '钱江三桥秋涛路3704', 2, NULL, 80, 0, NULL, 'OFF', '134.7845036', '25.9131072', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25413, '33011701001310703705', '', 'testDevice3705', NULL, NULL, 80, 0, NULL, 'OFF', '125.17341', '30.1500404', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25414, '33011701001310703706', '', 'dasda3706', NULL, NULL, 80, 0, NULL, '', '126.5135398', '28.0108809', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25415, '33011701001310703707', '33011701', '吴山广场3707', 2, NULL, 80, 0, NULL, '', '122.0474696', '34.6042834', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25416, '33011701001310703708', '33011701', '中河路上仓桥路3708', 2, NULL, 80, 0, NULL, 'OFF', '115.6967291', '33.9887749', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25417, '33011701001310703709', '33011701', '西湖大道建国路3709', 2, NULL, 80, 0, NULL, 'OFF', '117.3412375', '31.1310243', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25418, '33011701001310703710', '33011701', '平海路中河路3710', 2, NULL, 80, 0, NULL, 'OFF', '124.6160006', '28.6887971', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25419, '33011701001310703711', '33011701', '平海路延安路3711', 2, NULL, 80, 0, NULL, 'OFF', '116.0562914', '25.0509128', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25420, '33011701001310703712', '33011701', '解放路浣纱路3712', 2, NULL, 80, 0, NULL, 'OFF', '131.4334556', '34.1881732', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25421, '33011701001310703713', '33011701', '解放路建国路3713', 2, NULL, 80, 0, NULL, 'OFF', '133.9984046', '30.7881279', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25422, '33011701001310703714', '33011701', '钱江三桥秋涛路3714', 2, NULL, 80, 0, NULL, 'OFF', '120.6916566', '26.3761202', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25423, '33011701001310703715', '', 'testDevice3715', NULL, NULL, 80, 0, NULL, 'OFF', '126.4630699', '34.5162175', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25424, '33011701001310703716', '', 'dasda3716', NULL, NULL, 80, 0, NULL, '', '115.2403804', '28.4527271', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25425, '33011701001310703717', '33011701', '吴山广场3717', 2, NULL, 80, 0, NULL, '', '121.812693', '33.7149835', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25426, '33011701001310703718', '33011701', '中河路上仓桥路3718', 2, NULL, 80, 0, NULL, 'OFF', '128.3423242', '28.2167367', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25427, '33011701001310703719', '33011701', '西湖大道建国路3719', 2, NULL, 80, 0, NULL, 'OFF', '121.2735428', '34.9387329', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25428, '33011701001310703720', '33011701', '平海路中河路3720', 2, NULL, 80, 0, NULL, 'OFF', '126.340742', '25.0434551', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25429, '33011701001310703721', '33011701', '平海路延安路3721', 2, NULL, 80, 0, NULL, 'OFF', '132.8830822', '25.4010768', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25430, '33011701001310703722', '33011701', '解放路浣纱路3722', 2, NULL, 80, 0, NULL, 'OFF', '130.3931857', '26.8750191', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25431, '33011701001310703723', '33011701', '解放路建国路3723', 2, NULL, 80, 0, NULL, 'OFF', '118.3166823', '33.1718654', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25432, '33011701001310703724', '33011701', '钱江三桥秋涛路3724', 2, NULL, 80, 0, NULL, 'OFF', '125.4038551', '30.23427', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25433, '33011701001310703725', '', 'testDevice3725', NULL, NULL, 80, 0, NULL, 'OFF', '117.0692293', '26.655754', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25434, '33011701001310703726', '', 'dasda3726', NULL, NULL, 80, 0, NULL, '', '134.1345818', '27.5759604', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25435, '33011701001310703727', '33011701', '吴山广场3727', 2, NULL, 80, 0, NULL, '', '124.4652215', '32.9125401', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25436, '33011701001310703728', '33011701', '中河路上仓桥路3728', 2, NULL, 80, 0, NULL, 'OFF', '124.9223629', '26.8348198', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25437, '33011701001310703729', '33011701', '西湖大道建国路3729', 2, NULL, 80, 0, NULL, 'OFF', '116.2161505', '30.4364791', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25438, '33011701001310703730', '33011701', '平海路中河路3730', 2, NULL, 80, 0, NULL, 'OFF', '131.3136646', '26.6779362', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25439, '33011701001310703731', '33011701', '平海路延安路3731', 2, NULL, 80, 0, NULL, 'OFF', '132.9198722', '27.0802439', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25440, '33011701001310703732', '33011701', '解放路浣纱路3732', 2, NULL, 80, 0, NULL, 'OFF', '115.6583678', '30.3674114', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25441, '33011701001310703733', '33011701', '解放路建国路3733', 2, NULL, 80, 0, NULL, 'OFF', '124.5322231', '25.5963255', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25442, '33011701001310703734', '33011701', '钱江三桥秋涛路3734', 2, NULL, 80, 0, NULL, 'OFF', '120.6860126', '31.8793937', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25443, '33011701001310703735', '', 'testDevice3735', NULL, NULL, 80, 0, NULL, 'OFF', '134.8333942', '27.6079924', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25444, '33011701001310703736', '', 'dasda3736', NULL, NULL, 80, 0, NULL, '', '117.1089338', '27.4017812', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25445, '33011701001310703737', '33011701', '吴山广场3737', 2, NULL, 80, 0, NULL, '', '126.0444871', '29.1849292', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25446, '33011701001310703738', '33011701', '中河路上仓桥路3738', 2, NULL, 80, 0, NULL, 'OFF', '123.8956346', '28.7193024', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25447, '33011701001310703739', '33011701', '西湖大道建国路3739', 2, NULL, 80, 0, NULL, 'OFF', '126.3447123', '31.0417249', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25448, '33011701001310703740', '33011701', '平海路中河路3740', 2, NULL, 80, 0, NULL, 'OFF', '125.0366582', '34.0507175', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25449, '33011701001310703741', '33011701', '平海路延安路3741', 2, NULL, 80, 0, NULL, 'OFF', '131.1491548', '32.1284133', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25450, '33011701001310703742', '33011701', '解放路浣纱路3742', 2, NULL, 80, 0, NULL, 'OFF', '125.6358', '33.4899143', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25451, '33011701001310703743', '33011701', '解放路建国路3743', 2, NULL, 80, 0, NULL, 'OFF', '119.7315362', '26.0643319', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25452, '33011701001310703744', '33011701', '钱江三桥秋涛路3744', 2, NULL, 80, 0, NULL, 'OFF', '126.7502815', '34.851917', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25453, '33011701001310703745', '', 'testDevice3745', NULL, NULL, 80, 0, NULL, 'OFF', '119.5567995', '31.0665896', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25454, '33011701001310703746', '', 'dasda3746', NULL, NULL, 80, 0, NULL, '', '122.5331535', '25.7771971', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25455, '33011701001310703747', '33011701', '吴山广场3747', 2, NULL, 80, 0, NULL, '', '118.9953699', '30.6862169', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25456, '33011701001310703748', '33011701', '中河路上仓桥路3748', 2, NULL, 80, 0, NULL, 'OFF', '132.3773894', '31.0994939', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25457, '33011701001310703749', '33011701', '西湖大道建国路3749', 2, NULL, 80, 0, NULL, 'OFF', '129.9008378', '28.4388187', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25458, '33011701001310703750', '33011701', '平海路中河路3750', 2, NULL, 80, 0, NULL, 'OFF', '117.3720215', '33.8956124', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25459, '33011701001310703751', '33011701', '平海路延安路3751', 2, NULL, 80, 0, NULL, 'OFF', '122.1575945', '29.1616063', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25460, '33011701001310703752', '33011701', '解放路浣纱路3752', 2, NULL, 80, 0, NULL, 'OFF', '123.671909', '29.1211943', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25461, '33011701001310703753', '33011701', '解放路建国路3753', 2, NULL, 80, 0, NULL, 'OFF', '116.886762', '33.121153', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25462, '33011701001310703754', '33011701', '钱江三桥秋涛路3754', 2, NULL, 80, 0, NULL, 'OFF', '118.4180835', '33.2421826', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25463, '33011701001310703755', '', 'testDevice3755', NULL, NULL, 80, 0, NULL, 'OFF', '126.4301321', '31.847454', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25464, '33011701001310703756', '', 'dasda3756', NULL, NULL, 80, 0, NULL, '', '121.8964105', '34.5107228', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25465, '33011701001310703757', '33011701', '吴山广场3757', 2, NULL, 80, 0, NULL, '', '115.191657', '32.011252', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25466, '33011701001310703758', '33011701', '中河路上仓桥路3758', 2, NULL, 80, 0, NULL, 'OFF', '115.2690541', '31.5240921', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25467, '33011701001310703759', '33011701', '西湖大道建国路3759', 2, NULL, 80, 0, NULL, 'OFF', '115.7703', '26.5867046', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25468, '33011701001310703760', '33011701', '平海路中河路3760', 2, NULL, 80, 0, NULL, 'OFF', '118.0443384', '33.3612472', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25469, '33011701001310703761', '33011701', '平海路延安路3761', 2, NULL, 80, 0, NULL, 'OFF', '127.9107927', '32.0461227', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25470, '33011701001310703762', '33011701', '解放路浣纱路3762', 2, NULL, 80, 0, NULL, 'OFF', '130.420949', '25.1468718', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25471, '33011701001310703763', '33011701', '解放路建国路3763', 2, NULL, 80, 0, NULL, 'OFF', '133.3723676', '34.5959915', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25472, '33011701001310703764', '33011701', '钱江三桥秋涛路3764', 2, NULL, 80, 0, NULL, 'OFF', '120.5989915', '32.5393422', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25473, '33011701001310703765', '', 'testDevice3765', NULL, NULL, 80, 0, NULL, 'OFF', '127.8778552', '33.9087369', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25474, '33011701001310703766', '', 'dasda3766', NULL, NULL, 80, 0, NULL, '', '122.5923023', '26.9256584', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25475, '33011701001310703767', '33011701', '吴山广场3767', 2, NULL, 80, 0, NULL, '', '134.3279463', '27.9020813', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25476, '33011701001310703768', '33011701', '中河路上仓桥路3768', 2, NULL, 80, 0, NULL, 'OFF', '128.8628253', '33.7334317', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25477, '33011701001310703769', '33011701', '西湖大道建国路3769', 2, NULL, 80, 0, NULL, 'OFF', '126.3302883', '29.9609151', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25478, '33011701001310703770', '33011701', '平海路中河路3770', 2, NULL, 80, 0, NULL, 'OFF', '130.0629663', '33.6042806', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25479, '33011701001310703771', '33011701', '平海路延安路3771', 2, NULL, 80, 0, NULL, 'OFF', '116.3239673', '33.138658', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25480, '33011701001310703772', '33011701', '解放路浣纱路3772', 2, NULL, 80, 0, NULL, 'OFF', '116.4309381', '29.8804487', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25481, '33011701001310703773', '33011701', '解放路建国路3773', 2, NULL, 80, 0, NULL, 'OFF', '118.1827891', '34.9862697', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25482, '33011701001310703774', '33011701', '钱江三桥秋涛路3774', 2, NULL, 80, 0, NULL, 'OFF', '126.6211319', '30.2900028', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25483, '33011701001310703775', '', 'testDevice3775', NULL, NULL, 80, 0, NULL, 'OFF', '123.5572929', '31.4912053', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25484, '33011701001310703776', '', 'dasda3776', NULL, NULL, 80, 0, NULL, '', '122.9230694', '31.5860182', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25485, '33011701001310703777', '33011701', '吴山广场3777', 2, NULL, 80, 0, NULL, '', '128.943469', '28.4564756', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25486, '33011701001310703778', '33011701', '中河路上仓桥路3778', 2, NULL, 80, 0, NULL, 'OFF', '120.9481375', '32.5243238', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25487, '33011701001310703779', '33011701', '西湖大道建国路3779', 2, NULL, 80, 0, NULL, 'OFF', '122.9102809', '32.2521922', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25488, '33011701001310703780', '33011701', '平海路中河路3780', 2, NULL, 80, 0, NULL, 'OFF', '116.7069927', '28.6879902', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25489, '33011701001310703781', '33011701', '平海路延安路3781', 2, NULL, 80, 0, NULL, 'OFF', '119.8041214', '31.6833747', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25490, '33011701001310703782', '33011701', '解放路浣纱路3782', 2, NULL, 80, 0, NULL, 'OFF', '133.8996296', '27.352903', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25491, '33011701001310703783', '33011701', '解放路建国路3783', 2, NULL, 80, 0, NULL, 'OFF', '115.0857845', '26.7143913', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25492, '33011701001310703784', '33011701', '钱江三桥秋涛路3784', 2, NULL, 80, 0, NULL, 'OFF', '118.7300343', '26.5132477', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25493, '33011701001310703785', '', 'testDevice3785', NULL, NULL, 80, 0, NULL, 'OFF', '133.3928184', '27.4230649', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25494, '33011701001310703786', '', 'dasda3786', NULL, NULL, 80, 0, NULL, '', '115.77399', '32.5755819', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25495, '33011701001310703787', '33011701', '吴山广场3787', 2, NULL, 80, 0, NULL, '', '123.6914952', '25.6087149', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25496, '33011701001310703788', '33011701', '中河路上仓桥路3788', 2, NULL, 80, 0, NULL, 'OFF', '116.1355068', '25.3168292', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25497, '33011701001310703789', '33011701', '西湖大道建国路3789', 2, NULL, 80, 0, NULL, 'OFF', '134.6030491', '34.7580014', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25498, '33011701001310703790', '33011701', '平海路中河路3790', 2, NULL, 80, 0, NULL, 'OFF', '129.6087255', '32.83952', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25499, '33011701001310703791', '33011701', '平海路延安路3791', 2, NULL, 80, 0, NULL, 'OFF', '129.234481', '34.9235958', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25500, '33011701001310703792', '33011701', '解放路浣纱路3792', 2, NULL, 80, 0, NULL, 'OFF', '122.346229', '31.0994195', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25501, '33011701001310703793', '33011701', '解放路建国路3793', 2, NULL, 80, 0, NULL, 'OFF', '129.0277028', '25.7263104', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25502, '33011701001310703794', '33011701', '钱江三桥秋涛路3794', 2, NULL, 80, 0, NULL, 'OFF', '123.0998275', '30.3332938', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25503, '33011701001310703795', '', 'testDevice3795', NULL, NULL, 80, 0, NULL, 'OFF', '133.4160298', '29.487538', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25504, '33011701001310703796', '', 'dasda3796', NULL, NULL, 80, 0, NULL, '', '122.7806669', '31.4378089', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25505, '33011701001310703797', '33011701', '吴山广场3797', 2, NULL, 80, 0, NULL, '', '118.655246', '33.726431', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25506, '33011701001310703798', '33011701', '中河路上仓桥路3798', 2, NULL, 80, 0, NULL, 'OFF', '129.9342297', '29.3187283', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25507, '33011701001310703799', '33011701', '西湖大道建国路3799', 2, NULL, 80, 0, NULL, 'OFF', '118.7054112', '30.4143492', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25508, '33011701001310703800', '33011701', '平海路中河路3800', 2, NULL, 80, 0, NULL, 'OFF', '128.7243674', '29.1155611', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25509, '33011701001310703801', '33011701', '平海路延安路3801', 2, NULL, 80, 0, NULL, 'OFF', '132.5056039', '29.3347582', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25510, '33011701001310703802', '33011701', '解放路浣纱路3802', 2, NULL, 80, 0, NULL, 'OFF', '121.3549179', '34.327108', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25511, '33011701001310703803', '33011701', '解放路建国路3803', 2, NULL, 80, 0, NULL, 'OFF', '134.2577787', '28.6312659', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25512, '33011701001310703804', '33011701', '钱江三桥秋涛路3804', 2, NULL, 80, 0, NULL, 'OFF', '132.2241403', '25.1750057', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25513, '33011701001310703805', '', 'testDevice3805', NULL, NULL, 80, 0, NULL, 'OFF', '123.3473661', '34.981231', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25514, '33011701001310703806', '', 'dasda3806', NULL, NULL, 80, 0, NULL, '', '125.0644103', '34.3811384', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25515, '33011701001310703807', '33011701', '吴山广场3807', 2, NULL, 80, 0, NULL, '', '120.2799539', '31.9619994', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25516, '33011701001310703808', '33011701', '中河路上仓桥路3808', 2, NULL, 80, 0, NULL, 'OFF', '131.2065389', '31.6665822', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25517, '33011701001310703809', '33011701', '西湖大道建国路3809', 2, NULL, 80, 0, NULL, 'OFF', '120.1928335', '27.4469128', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25518, '33011701001310703810', '33011701', '平海路中河路3810', 2, NULL, 80, 0, NULL, 'OFF', '132.3445516', '27.2348177', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25519, '33011701001310703811', '33011701', '平海路延安路3811', 2, NULL, 80, 0, NULL, 'OFF', '126.1442579', '28.8333506', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25520, '33011701001310703812', '33011701', '解放路浣纱路3812', 2, NULL, 80, 0, NULL, 'OFF', '118.6876353', '27.4623003', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25521, '33011701001310703813', '33011701', '解放路建国路3813', 2, NULL, 80, 0, NULL, 'OFF', '120.0054033', '25.81145', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25522, '33011701001310703814', '33011701', '钱江三桥秋涛路3814', 2, NULL, 80, 0, NULL, 'OFF', '128.9641112', '31.6703493', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25523, '33011701001310703815', '', 'testDevice3815', NULL, NULL, 80, 0, NULL, 'OFF', '129.804347', '25.9173969', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25524, '33011701001310703816', '', 'dasda3816', NULL, NULL, 80, 0, NULL, '', '127.1294018', '29.5759369', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25525, '33011701001310703817', '33011701', '吴山广场3817', 2, NULL, 80, 0, NULL, '', '131.2339687', '25.1274942', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25526, '33011701001310703818', '33011701', '中河路上仓桥路3818', 2, NULL, 80, 0, NULL, 'OFF', '119.7816389', '31.9096607', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25527, '33011701001310703819', '33011701', '西湖大道建国路3819', 2, NULL, 80, 0, NULL, 'OFF', '130.2062889', '29.1658211', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25528, '33011701001310703820', '33011701', '平海路中河路3820', 2, NULL, 80, 0, NULL, 'OFF', '116.6865285', '25.1001237', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25529, '33011701001310703821', '33011701', '平海路延安路3821', 2, NULL, 80, 0, NULL, 'OFF', '117.8137762', '33.0031554', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25530, '33011701001310703822', '33011701', '解放路浣纱路3822', 2, NULL, 80, 0, NULL, 'OFF', '124.009296', '34.7154065', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25531, '33011701001310703823', '33011701', '解放路建国路3823', 2, NULL, 80, 0, NULL, 'OFF', '131.6051523', '29.5675664', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25532, '33011701001310703824', '33011701', '钱江三桥秋涛路3824', 2, NULL, 80, 0, NULL, 'OFF', '130.9978739', '28.6916128', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25533, '33011701001310703825', '', 'testDevice3825', NULL, NULL, 80, 0, NULL, 'OFF', '125.1739134', '29.7553651', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25534, '33011701001310703826', '', 'dasda3826', NULL, NULL, 80, 0, NULL, '', '117.875946', '27.7019875', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25535, '33011701001310703827', '33011701', '吴山广场3827', 2, NULL, 80, 0, NULL, '', '118.8579903', '34.2438423', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25536, '33011701001310703828', '33011701', '中河路上仓桥路3828', 2, NULL, 80, 0, NULL, 'OFF', '125.6621141', '33.1132495', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25537, '33011701001310703829', '33011701', '西湖大道建国路3829', 2, NULL, 80, 0, NULL, 'OFF', '116.7366003', '27.834721', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25538, '33011701001310703830', '33011701', '平海路中河路3830', 2, NULL, 80, 0, NULL, 'OFF', '131.69666', '34.8338566', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25539, '33011701001310703831', '33011701', '平海路延安路3831', 2, NULL, 80, 0, NULL, 'OFF', '133.2734994', '25.6651204', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25540, '33011701001310703832', '33011701', '解放路浣纱路3832', 2, NULL, 80, 0, NULL, 'OFF', '116.2775176', '28.8240325', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25541, '33011701001310703833', '33011701', '解放路建国路3833', 2, NULL, 80, 0, NULL, 'OFF', '126.5670904', '32.1248017', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25542, '33011701001310703834', '33011701', '钱江三桥秋涛路3834', 2, NULL, 80, 0, NULL, 'OFF', '129.0028997', '29.1519114', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25543, '33011701001310703835', '', 'testDevice3835', NULL, NULL, 80, 0, NULL, 'OFF', '130.3132279', '34.385152', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25544, '33011701001310703836', '', 'dasda3836', NULL, NULL, 80, 0, NULL, '', '129.5574412', '29.4700261', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25545, '33011701001310703837', '33011701', '吴山广场3837', 2, NULL, 80, 0, NULL, '', '121.8475228', '29.194675', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25546, '33011701001310703838', '33011701', '中河路上仓桥路3838', 2, NULL, 80, 0, NULL, 'OFF', '125.5652911', '32.5632971', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25547, '33011701001310703839', '33011701', '西湖大道建国路3839', 2, NULL, 80, 0, NULL, 'OFF', '127.2838876', '30.2324607', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25548, '33011701001310703840', '33011701', '平海路中河路3840', 2, NULL, 80, 0, NULL, 'OFF', '124.7235653', '28.4724124', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25549, '33011701001310703841', '33011701', '平海路延安路3841', 2, NULL, 80, 0, NULL, 'OFF', '126.7661642', '26.6646805', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25550, '33011701001310703842', '33011701', '解放路浣纱路3842', 2, NULL, 80, 0, NULL, 'OFF', '124.660126', '32.9061653', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25551, '33011701001310703843', '33011701', '解放路建国路3843', 2, NULL, 80, 0, NULL, 'OFF', '128.0021379', '29.5367854', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25552, '33011701001310703844', '33011701', '钱江三桥秋涛路3844', 2, NULL, 80, 0, NULL, 'OFF', '131.0303121', '33.9654316', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25553, '33011701001310703845', '', 'testDevice3845', NULL, NULL, 80, 0, NULL, 'OFF', '116.1451473', '26.2168019', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25554, '33011701001310703846', '', 'dasda3846', NULL, NULL, 80, 0, NULL, '', '132.6348008', '34.1877152', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25555, '33011701001310703847', '33011701', '吴山广场3847', 2, NULL, 80, 0, NULL, '', '119.7385627', '27.2881706', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25556, '33011701001310703848', '33011701', '中河路上仓桥路3848', 2, NULL, 80, 0, NULL, 'OFF', '125.7884118', '28.8777076', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25557, '33011701001310703849', '33011701', '西湖大道建国路3849', 2, NULL, 80, 0, NULL, 'OFF', '134.7263715', '27.5240267', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25558, '33011701001310703850', '33011701', '平海路中河路3850', 2, NULL, 80, 0, NULL, 'OFF', '121.2666228', '25.9870109', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25559, '33011701001310703851', '33011701', '平海路延安路3851', 2, NULL, 80, 0, NULL, 'OFF', '127.1540001', '32.3629749', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25560, '33011701001310703852', '33011701', '解放路浣纱路3852', 2, NULL, 80, 0, NULL, 'OFF', '116.9701327', '28.8538418', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25561, '33011701001310703853', '33011701', '解放路建国路3853', 2, NULL, 80, 0, NULL, 'OFF', '128.3886637', '32.1802846', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25562, '33011701001310703854', '33011701', '钱江三桥秋涛路3854', 2, NULL, 80, 0, NULL, 'OFF', '116.0329209', '29.3398979', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25563, '33011701001310703855', '', 'testDevice3855', NULL, NULL, 80, 0, NULL, 'OFF', '119.9986141', '25.1586359', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25564, '33011701001310703856', '', 'dasda3856', NULL, NULL, 80, 0, NULL, '', '116.8943085', '32.7734861', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25565, '33011701001310703857', '33011701', '吴山广场3857', 2, NULL, 80, 0, NULL, '', '129.4757006', '33.3915231', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25566, '33011701001310703858', '33011701', '中河路上仓桥路3858', 2, NULL, 80, 0, NULL, 'OFF', '121.6955783', '33.6371574', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25567, '33011701001310703859', '33011701', '西湖大道建国路3859', 2, NULL, 80, 0, NULL, 'OFF', '125.0507902', '33.011218', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25568, '33011701001310703860', '33011701', '平海路中河路3860', 2, NULL, 80, 0, NULL, 'OFF', '125.1672166', '29.1446182', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25569, '33011701001310703861', '33011701', '平海路延安路3861', 2, NULL, 80, 0, NULL, 'OFF', '115.6837129', '31.6894373', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25570, '33011701001310703862', '33011701', '解放路浣纱路3862', 2, NULL, 80, 0, NULL, 'OFF', '127.9169157', '26.0133322', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25571, '33011701001310703863', '33011701', '解放路建国路3863', 2, NULL, 80, 0, NULL, 'OFF', '117.5334401', '29.9983494', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25572, '33011701001310703864', '33011701', '钱江三桥秋涛路3864', 2, NULL, 80, 0, NULL, 'OFF', '128.9164539', '26.9517509', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25573, '33011701001310703865', '', 'testDevice3865', NULL, NULL, 80, 0, NULL, 'OFF', '116.9819501', '29.7637064', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25574, '33011701001310703866', '', 'dasda3866', NULL, NULL, 80, 0, NULL, '', '123.1603891', '32.9632798', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25575, '33011701001310703867', '33011701', '吴山广场3867', 2, NULL, 80, 0, NULL, '', '129.856096', '30.5252802', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25576, '33011701001310703868', '33011701', '中河路上仓桥路3868', 2, NULL, 80, 0, NULL, 'OFF', '124.7993134', '28.7365618', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25577, '33011701001310703869', '33011701', '西湖大道建国路3869', 2, NULL, 80, 0, NULL, 'OFF', '119.4282794', '27.1069689', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25578, '33011701001310703870', '33011701', '平海路中河路3870', 2, NULL, 80, 0, NULL, 'OFF', '127.7434575', '34.3251591', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25579, '33011701001310703871', '33011701', '平海路延安路3871', 2, NULL, 80, 0, NULL, 'OFF', '125.4324501', '25.3048891', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25580, '33011701001310703872', '33011701', '解放路浣纱路3872', 2, NULL, 80, 0, NULL, 'OFF', '128.9318783', '28.5489686', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25581, '33011701001310703873', '33011701', '解放路建国路3873', 2, NULL, 80, 0, NULL, 'OFF', '133.3620419', '31.8301758', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25582, '33011701001310703874', '33011701', '钱江三桥秋涛路3874', 2, NULL, 80, 0, NULL, 'OFF', '125.0145754', '28.5039737', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25583, '33011701001310703875', '', 'testDevice3875', NULL, NULL, 80, 0, NULL, 'OFF', '129.9867518', '32.0293416', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25584, '33011701001310703876', '', 'dasda3876', NULL, NULL, 80, 0, NULL, '', '119.8900333', '29.634787', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25585, '33011701001310703877', '33011701', '吴山广场3877', 2, NULL, 80, 0, NULL, '', '134.489912', '27.0859103', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25586, '33011701001310703878', '33011701', '中河路上仓桥路3878', 2, NULL, 80, 0, NULL, 'OFF', '117.7794606', '31.5251911', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25587, '33011701001310703879', '33011701', '西湖大道建国路3879', 2, NULL, 80, 0, NULL, 'OFF', '130.4275675', '31.3682248', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25588, '33011701001310703880', '33011701', '平海路中河路3880', 2, NULL, 80, 0, NULL, 'OFF', '123.7994563', '27.2655512', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25589, '33011701001310703881', '33011701', '平海路延安路3881', 2, NULL, 80, 0, NULL, 'OFF', '132.7145795', '27.2230816', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25590, '33011701001310703882', '33011701', '解放路浣纱路3882', 2, NULL, 80, 0, NULL, 'OFF', '117.1745292', '29.3187548', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25591, '33011701001310703883', '33011701', '解放路建国路3883', 2, NULL, 80, 0, NULL, 'OFF', '132.7289083', '29.9245297', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25592, '33011701001310703884', '33011701', '钱江三桥秋涛路3884', 2, NULL, 80, 0, NULL, 'OFF', '117.1209546', '26.6663842', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25593, '33011701001310703885', '', 'testDevice3885', NULL, NULL, 80, 0, NULL, 'OFF', '132.4180487', '28.5583324', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25594, '33011701001310703886', '', 'dasda3886', NULL, NULL, 80, 0, NULL, '', '115.7273802', '27.7925094', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25595, '33011701001310703887', '33011701', '吴山广场3887', 2, NULL, 80, 0, NULL, '', '126.3827557', '28.2875504', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25596, '33011701001310703888', '33011701', '中河路上仓桥路3888', 2, NULL, 80, 0, NULL, 'OFF', '129.7316384', '33.0602238', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25597, '33011701001310703889', '33011701', '西湖大道建国路3889', 2, NULL, 80, 0, NULL, 'OFF', '134.5099256', '25.4384685', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25598, '33011701001310703890', '33011701', '平海路中河路3890', 2, NULL, 80, 0, NULL, 'OFF', '128.3547135', '33.0116711', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25599, '33011701001310703891', '33011701', '平海路延安路3891', 2, NULL, 80, 0, NULL, 'OFF', '123.2437913', '33.7429504', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25600, '33011701001310703892', '33011701', '解放路浣纱路3892', 2, NULL, 80, 0, NULL, 'OFF', '116.1548166', '34.6797388', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25601, '33011701001310703893', '33011701', '解放路建国路3893', 2, NULL, 80, 0, NULL, 'OFF', '116.0427095', '27.1698433', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25602, '33011701001310703894', '33011701', '钱江三桥秋涛路3894', 2, NULL, 80, 0, NULL, 'OFF', '116.7490985', '26.8100003', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25603, '33011701001310703895', '', 'testDevice3895', NULL, NULL, 80, 0, NULL, 'OFF', '120.6173647', '27.5404718', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25604, '33011701001310703896', '', 'dasda3896', NULL, NULL, 80, 0, NULL, '', '117.8395285', '32.2723583', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25605, '33011701001310703897', '33011701', '吴山广场3897', 2, NULL, 80, 0, NULL, '', '132.3455489', '33.7403766', '330102', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25606, '33011701001310703898', '33011701', '中河路上仓桥路3898', 2, NULL, 80, 0, NULL, 'OFF', '133.2091598', '26.8848084', '330105', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25607, '33011701001310703899', '33011701', '西湖大道建国路3899', 2, NULL, 80, 0, NULL, 'OFF', '134.0091529', '28.2029125', '330104', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25608, '33011701001310703900', '33011701', '平海路中河路3900', 2, NULL, 80, 0, NULL, 'OFF', '115.4182855', '25.3601376', '330106', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25609, '33011701001310703901', '33011701', '平海路延安路3901', 2, NULL, 80, 0, NULL, 'OFF', '120.0639695', '27.1919509', '330108', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25610, '33011701001310703902', '33011701', '解放路浣纱路3902', 2, NULL, 80, 0, NULL, 'OFF', '119.0649916', '34.8793418', '330109', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25611, '33011701001310703903', '33011701', '解放路建国路3903', 2, NULL, 80, 0, NULL, 'OFF', '120.1330499', '27.8208569', '330110', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25612, '33011701001310703904', '33011701', '钱江三桥秋涛路3904', 2, NULL, 80, 0, NULL, 'OFF', '128.4702757', '29.4662592', '330111', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25613, '33011701001310703905', '', 'testDevice3905', NULL, NULL, 80, 0, NULL, 'OFF', '126.9522291', '28.8687258', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25614, '33011701001310703906', '', 'dasda3906', NULL, NULL, 80, 0, NULL, '', '134.350319', '30.9448516', '', '192.168.1.1', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25615, '66011701001310700002', '', 'testDevice', NULL, NULL, 80, 1, 'rtmp://192.168.1.1:1950/66011701001310700002/livestream', 'OFF', '114.432432', '22.43243', '', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');
INSERT INTO `vi_device_copy` VALUES (25616, '66011701001310700003', '', 'dasda', NULL, NULL, 80, 0, NULL, 'ON', '121.4235864', '27.75585', '', '192.168.1.2', 0, '2019-06-17 10:30:03', '2019-06-17 10:30:03');

-- ----------------------------
-- Table structure for vi_folder
-- ----------------------------
DROP TABLE IF EXISTS `vi_folder`;
CREATE TABLE `vi_folder`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `folder_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹名称',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '文件夹状态： 0待办结 1已结案',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件夹表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_folder
-- ----------------------------
INSERT INTO `vi_folder` VALUES (5, '案情一', 0, '2019-06-10 10:52:09', '2019-06-15 09:49:40', NULL, NULL);
INSERT INTO `vi_folder` VALUES (6, 'akunplus', 0, '2019-06-10 10:52:55', '2019-06-15 09:50:00', NULL, NULL);
INSERT INTO `vi_folder` VALUES (7, '案情三', 0, '2019-06-10 10:55:05', '2019-06-15 09:50:24', NULL, NULL);
INSERT INTO `vi_folder` VALUES (8, '620案情', 1, '2019-06-15 09:49:16', '2019-06-15 09:49:49', NULL, NULL);

-- ----------------------------
-- Table structure for vi_gazhk_dfk_wffzry
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_dfk_wffzry`;
CREATE TABLE `vi_gazhk_dfk_wffzry`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `jg_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省份',
  `jg_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省市',
  `r_ajbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件编号',
  `r_rybh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `r_xlh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序列号',
  `r_ajlb` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别',
  `r_ryly` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员来源',
  `r_zhrq` datetime(0) NULL DEFAULT NULL COMMENT '抓获日期',
  `r_zhdw` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抓获单位',
  `r_lxr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `r_lxdh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `r_zyaq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主要案情',
  `r_cldw` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理单位',
  `r_clrq` datetime(0) NULL DEFAULT NULL COMMENT '处理日期',
  `r_cllx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理类型',
  `r_cljg` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理结果',
  `r_cfrq` datetime(0) NULL DEFAULT NULL COMMENT '处罚日期',
  `r_xxzk` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现行状况',
  `r_zxd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行地行政区划',
  `r_zxdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行地详址',
  `r_sfrq` datetime(0) NULL DEFAULT NULL COMMENT '释放日期',
  `r_sfly` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '释放理由',
  `r_rylx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员流向',
  `r_bz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `r_jbqkjsxbx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本情况及现实表现',
  `r_glcs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理措施',
  `r_jgxzcy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监改小组成员',
  `r_bgxj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变更续记',
  `r_zxyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注销原因',
  `r_zxrq` datetime(0) NULL DEFAULT NULL COMMENT '注销日期',
  `r_zxr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注销人',
  `r_tbdw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填表单位',
  `r_tbr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填表人',
  `r_tbsj` datetime(0) NULL DEFAULT NULL COMMENT '填表时间',
  `lrdw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入单位',
  `lrr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入人',
  `lrsj` datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
  `csbz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传输标志',
  `scbz` int(10) NULL DEFAULT NULL COMMENT '删除标记',
  `xgsj` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `csspdw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呈送审批单位',
  `csscspdw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呈送删除审批单位',
  `scyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除原因',
  `sbsj` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  `r_ywxtfl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务系统分类',
  `r_cfjsrq` datetime(0) NULL DEFAULT NULL COMMENT '处罚结束日期',
  `r_ypxq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原判刑期',
  `r_ajlb1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别1',
  `r_ajlb2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别2',
  `r_zrdd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `r_zhddxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `r_zhfs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `scbz_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标记',
  `r_cldw_code6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理单位',
  `r_rybh_jgcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯代码 ',
  `r_rybh_mzcode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `r_rybh_xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `r_rybh_xb` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `r_rybh_sfzh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `r_rybh_mz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `r_rybh_jg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `r_ajlb_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别',
  `r_ryly_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员来源',
  `r_zhdw_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抓获单位',
  `r_cldw_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理单位',
  `r_cllx_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理类型',
  `r_xxzk_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现行状况',
  `r_zxd_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行地',
  `r_sfly_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '释放理由',
  `r_rylx_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员流向',
  `r_tbdw_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '填表单位',
  `lrdw_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入单位',
  `csspdw_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呈送审批单位',
  `csscspdw_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '呈送删除审批单位',
  `r_ywxtfl_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务系统分类',
  `r_ajlb1_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别1',
  `r_ajlb2_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别2',
  `pk_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联合外键',
  PRIMARY KEY (`pk_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '打防控人员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_dzzwb_cgxzcf
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_dzzwb_cgxzcf`;
CREATE TABLE `vi_gazhk_dzzwb_cgxzcf`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `unid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UNID',
  `areacode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政区划',
  `xzcf_reginname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政处罚区划',
  `xzcf_orgname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政处罚机关',
  `xzcf_orgcode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政处罚机关代码',
  `xzcfws_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政处罚文书号',
  `xzcfws_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '行政处罚文书',
  `bxzcf_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被处罚类型',
  `bxzcf_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被处罚人',
  `apply_cardtype` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `bxzcf_cardnumber` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被处罚人证件号码',
  `bxzcf_legalman` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被处罚法人',
  `bxzcf_legalmancardnumber` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被处罚法人证件号',
  `xzcf_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `xzcf_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `xzcf_zy` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `xzcf_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处罚日期',
  `xzcf_gklx` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `xzcf_bgkyj` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `xzcf_state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处罚状态',
  `xzcf_cancel` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `xzcf_dataversion` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `in_todb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库时间',
  `iidd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  PRIMARY KEY (`iidd`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '城管行政处罚' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_dzzwb_fy_grsxxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_dzzwb_fy_grsxxx`;
CREATE TABLE `vi_gazhk_dzzwb_fy_grsxxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `sqzxbd` int(10) NULL DEFAULT NULL COMMENT '申请执行标的',
  `ah` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案号',
  `ay` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '案由',
  `zxfymc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行法院名称',
  `ajzt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件状态',
  `dcsj` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导出时间',
  `lsh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流水号',
  PRIMARY KEY (`lsh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '个人失信人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_eles_case_retainers
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_eles_case_retainers`;
CREATE TABLE `vi_gazhk_eles_case_retainers`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `retname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名拼音',
  `spell` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名拼音',
  `alias` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `previousname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '曾用名',
  `gender` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生年月',
  `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
  `residenceaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `workunit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `idno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `educationalcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `nationalitycode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `nativeplacecode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `nationcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `occupation` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `polity` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `retid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留置人员ID',
  `retno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留置人员编号',
  `secretlevel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留置人员密级',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `lrrq` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入日期',
  `starttime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `endtime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `hynl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `hyfr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `xzxw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rdzx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `yzjb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `telnumber` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `crimerecord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '犯罪记录',
  `idtype` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `df_cznr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `df_czsj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `df_scbz` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `df_scsj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `gender_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `educationalcode_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `nativeplacecode_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `nativeplacecode_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `nativeplacecode_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `nationcode_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `occupation_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `polity_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  PRIMARY KEY (`retid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '执法办案平台-留置盘问人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_eles_ry_bzcjrybbb
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_eles_ry_bzcjrybbb`;
CREATE TABLE `vi_gazhk_eles_ry_bzcjrybbb`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录号',
  `suspectid` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `suspectno` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `docid` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文书号',
  `docno` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '具体文书主键ID',
  `caseid` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件ID',
  `caseno` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件编号',
  `xybbbz` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '1=需要报备；0=不需要报备',
  `tblno` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备人员档号',
  `orderno` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备人员序号.同一人同一档案时多记录序号',
  `idno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公民身份号码',
  `chnname` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员中文姓名',
  `enname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名拼音',
  `sex` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码',
  `csex` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别名称',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `birthplace` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地代码国家/地区代码',
  `cbirthplace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地详址',
  `regiareacode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划',
  `cregiareacode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `policestation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口所在地派出所',
  `cpolicestation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口所在地派出所中文',
  `passtype` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件种类代码',
  `cpasstype` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件种类名称',
  `passno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `apprdate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签发日期',
  `apprunit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签发地，审批签发机关代码',
  `capprunit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签发地名称',
  `serveunit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `unitphone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址',
  `homephone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭电话',
  `recordno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案号',
  `begindate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备起始日期',
  `enddate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备终止日期',
  `forbleavtype` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通报备案人员类别代码',
  `cforbleavtype` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备类别名称',
  `forbresn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备原因',
  `lawitem` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法律依据',
  `dealrequest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理要求',
  `forbunittype` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备单位类别代码',
  `cforbunittype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备单位类别名称',
  `forbunitlevel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备单位级别',
  `cforbunitlevel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备单位级别名称',
  `forbunitno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备单位组织机构代码',
  `cforbunitno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报备单位名称',
  `linkman` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `levelflag` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息级别。0=公开；1=受控',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `checkflag` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人口核查标志。0=未核对；1=核对一致；2=核对不一致；3=未查中。',
  `checkdate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人口核查时间',
  `revcheckflag` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '倒查标志。0=未倒查；1=已倒查。',
  `alarmmode` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警方式。0=前台不提示；1=前台提示。2=前台详细提示。',
  `applname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人员',
  `appldate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作时间',
  `operflag` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库操作标志。布控表：I=增加；U=修改；D=撤控；撤控表：D=撤控；R=复控',
  `batchno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库批号。批量入库时填写。',
  `chiename` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '复核人',
  `chiedate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '复核时间',
  `chieflag` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '复核标识。0=未复核；1=已复核。',
  `applareacode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作单位行政区划代码。',
  `capplareacode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作单位名称',
  `quashunit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控单位行政区划代码。',
  `cquashunit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控单位名称',
  `quashresn` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控原因代码',
  `cquashresn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控原因',
  `quashview` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控描述',
  `quashdate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控日期',
  `quashname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控操作人',
  `update_bz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `update_ryid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `update_sj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `update_dw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `quash_bz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `nationcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `residenceaddr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `educationalcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `syncflag` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同步标志（0：未同步，1：同步中，2：操作标识不正确，3：异常，9：同步成功）',
  `syncdate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同步时间',
  `syncdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同步描述',
  `xybbbz_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需要报备标志',
  `levelflag_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息级别',
  `checkflag_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人口核查标志',
  `revcheckflag_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '倒查标志',
  `alarmmode_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警方式',
  `chieflag_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '复核标识',
  `regiareacode_sf` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划',
  `regiareacode_ss` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '不准出境报备人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_eles_ry_tbb
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_eles_ry_tbb`;
CREATE TABLE `vi_gazhk_eles_ry_tbb`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录号',
  `docid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文书号',
  `docno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '具体文书主键ID',
  `caseid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件ID',
  `caseno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件编号',
  `transactunit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案单位',
  `casetype` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类型',
  `zyaq` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '主要案情',
  `clqkjflyj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理情况及法律依据',
  `suspectid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `suspectno` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `chnname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中文姓名',
  `enname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外文姓名',
  `sex` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `csex` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别名称',
  `birthday` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `gj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `cgj` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍名称',
  `lxdh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `passtype` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护照种类代码',
  `cpasstype` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护照种类名称',
  `passno` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护照证件号码',
  `hzyxqz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '护照有效期至',
  `qzzjzlzt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签证证件种类(字头)',
  `tljlyxqz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '停留(居留)有效期至',
  `rjka` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入境口岸',
  `rjsj` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入境时间',
  `zhdz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在华地址',
  `jddw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接待单位',
  `applname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人员',
  `appldate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作时间',
  `applareacode` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作单位行政区划代码。',
  `capplareacode` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作单位名称',
  `quashunit` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控单位行政区划代码。',
  `cquashunit` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控单位名称',
  `quashresn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控原因代码',
  `cquashresn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控原因',
  `quashview` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '撤控描述',
  `quashdate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控日期',
  `quashname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '撤控操作人',
  `jddwdh` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接待单位电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '涉外案件人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_hz_zjk1_baqryxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_hz_zjk1_baqryxx`;
CREATE TABLE `vi_gazhk_hz_zjk1_baqryxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `isdel_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `cbdw_bh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '承办单位',
  `lxdh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `hj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍',
  `hjxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍详址',
  `gyzt` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关押状态(01:在所02:送押03:已关押)',
  `zhdd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抓获地点',
  `signname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公章名称（用于打印公章）',
  `rsyy_bh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因(案由编号)',
  `jslxdh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家属联系电话',
  `jsgx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家属与嫌疑人的关系',
  `gj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `tbsj` datetime(0) NULL DEFAULT NULL COMMENT '同步时间',
  `zpid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片ID',
  `zzxzqh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址行政区划',
  `sfsfbm` int(10) NULL DEFAULT NULL COMMENT '是否身份不明',
  `gzdw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `bm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `ch` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绰号',
  `cym` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '曾用名',
  `whcd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `zzmm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `dafs_text` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到案方案（字典值）',
  `sftb` int(10) NULL DEFAULT NULL COMMENT '是否同步至涉案人员',
  `baqsyqkbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案区使用情况编号',
  `ythcjzt` int(10) NULL DEFAULT NULL COMMENT '一体化采集状态(1为已采集，0为未采集)',
  `xsys` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示颜色',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `isdel` int(10) NULL DEFAULT NULL COMMENT '数据是否逻辑删除',
  `dataversion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据版本号(yyyymmddhhmiss)',
  `lrr_sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入人',
  `lrsj` datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
  `xgr_sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `xgsj` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `rybh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员姓名（外国人姓名）',
  `zjlx` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `xb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `mz` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `csrq` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `nl` int(10) NULL DEFAULT NULL COMMENT '年龄',
  `zz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
  `dafs` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到案方式(字典)',
  `ryly` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员来源（字典）',
  `rsyy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因(案由字典)',
  `r_rssj` datetime(0) NULL DEFAULT NULL COMMENT '入所时间',
  `rylx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员类型（字典）',
  `baqm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案区位置(字典)',
  `mj_sfzh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案民警身份证号码',
  `badwjc` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案单位简称',
  `r_sfyzjb` int(10) NULL DEFAULT NULL COMMENT '是否疾病',
  `r_yzjb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重疾病',
  `r_sfssjc` int(10) NULL DEFAULT NULL COMMENT '是否伤势检查',
  `r_ssms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '伤势描述',
  `r_sjtstbtz` int(10) NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `r_sfzs` int(10) NULL DEFAULT NULL COMMENT '是否有无伤情',
  `r_zsss` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '自述伤势成因',
  `c_ryqx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所去向',
  `c_cssj` datetime(0) NULL DEFAULT NULL COMMENT '出所时间',
  `c_csyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所原因',
  `sfywp` int(10) NULL DEFAULT NULL COMMENT '是否有物品（随身物品）',
  `sftsqt` int(10) NULL DEFAULT NULL COMMENT '是否特殊群体',
  `tsqt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊群体',
  `ryzt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员状态',
  `c_sfyzjb` int(10) NULL DEFAULT NULL COMMENT '是否严重疾病',
  `c_yzjb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重疾病',
  `c_sfssjc` int(10) NULL DEFAULT NULL COMMENT '是否伤势检查',
  `c_ssms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '伤势描述',
  `c_sjtstbtz` int(10) NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `c_sfzs` int(10) NULL DEFAULT NULL COMMENT '是否自述成因',
  `c_zsss` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自述伤势成因 ',
  `mz_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `c_sfyzjb_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否严重疾病',
  `c_sjtstbtz_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `gyzt_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关押状态',
  `whcd_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `rylx_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员类型',
  `dafs_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到案方式',
  `tsqt_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊群体',
  `sfzbm` int(10) NULL DEFAULT NULL COMMENT '是否自报名',
  `r_sfyzjb_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否疾病',
  `r_sfssjc_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否伤势检查',
  `r_sjtstbtz_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `sftsqt_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊群体',
  `sftb_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否同步至涉案人员',
  `c_sfssjc_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否伤势检查',
  `sfzbm_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自报名',
  `r_sfzs_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有无伤情',
  `sfywp_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有物品',
  `zzmm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `xb_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `ythcjzt_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一体化采集状态',
  `cbdw_bh6` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '承办区县',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省厅执法-办案区人员登记' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_hz_zjk1_xyrxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_hz_zjk1_xyrxx`;
CREATE TABLE `vi_gazhk_hz_zjk1_xyrxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `isdel_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `sfythcj_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否一体化采集',
  `xb_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `sfbmsf_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否保密身份',
  `is_aj_analyze_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已分析',
  `tsqt_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊群体',
  `sfzbm_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自报名',
  `zzmm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `scspzt_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批状态',
  `whcd_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `isdel` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据是否逻辑删除',
  `dataversion` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据版本号(yyyymmddhhmiss)',
  `lrr_sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入人',
  `lrsj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入时间',
  `xgr_sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `xgsj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `rybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `xm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员姓名（外国人姓名）',
  `cym` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '曾用名',
  `bh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `sfzh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `qtzjlx1` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他证件类型1',
  `qtzjhm1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他证件号码1',
  `qtzjlx2` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他证件类型2',
  `qtzjhm2` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他证件号码2',
  `qtzjlx3` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他证件类型3',
  `qtzjhm3` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他证件号码3',
  `xb` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `mz` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `csrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `nl` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄(下限)',
  `lxfs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `qtlxfs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更多联系方式',
  `hyzk` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `zzmm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `whcd` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `sg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身高',
  `tx` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体型',
  `zy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `zylb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业类别',
  `hjd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地',
  `hjdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `xzd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地',
  `xzdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地详址',
  `xzdxz_x` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地坐标X',
  `xzdxz_y` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地坐标Y',
  `grxg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个人习惯',
  `sftsqt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊群体',
  `tsqt` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊群体（字典）',
  `zw` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指纹',
  `dna` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'DNA',
  `sflar` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否另案人',
  `larsfsfmq` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '另案人身份是否明确',
  `xx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '血型',
  `zagj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作案工具',
  `zatd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作案特点',
  `jtzk` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '家庭状况',
  `shgx` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '社会关系(简历)',
  `wfss` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '违法事实',
  `gj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `gzdw` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `bz` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `sfzbm` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自报名',
  `dlr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理人',
  `dlrdh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理人电话',
  `sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `sfythcj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否一体化采集',
  `ay_bh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案由编号',
  `ay_mc` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案由名称',
  `tbsj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同步时间',
  `sfga` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '另案人员是否归案',
  `gasj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '归案时间',
  `sfbmsf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否保密身份',
  `nlsx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄(上限)',
  `zpid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片ID',
  `crj_zjlx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出入境证件类型',
  `crj_zjhm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出入境证件号码',
  `ch` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绰号',
  `qkqk` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '前科情况',
  `sftb` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否同步至办案区人员',
  `scspzt` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '01:正常;02:审核中;03:已审批',
  `baqrybh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案区人员编号',
  `is_aj_analyze` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否已分析',
  `is_escaped` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `latest_ws` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `sftsqt_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊群体',
  `sflar_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否另案人',
  `sfga_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '另案人是否归案',
  `sftb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否同步至办案区人员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省厅执法-嫌疑人登记' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_hz_zjk1_ydcjry
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_hz_zjk1_ydcjry`;
CREATE TABLE `vi_gazhk_hz_zjk1_ydcjry`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `sfsfbm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否身份不明',
  `c_sfzs_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自述成因',
  `sfzbm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自报名',
  `gyzt_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关押状态',
  `zzmm_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `sftb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否同步至涉案人员',
  `ythcjzt_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一体化采集状态',
  `whcd_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `r_sfyzjb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否疾病',
  `sfywp_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有物品',
  `sftsqt_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊群体',
  `c_sfyzjb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否严重疾病',
  `c_sfssjc_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否伤势检查',
  `c_sjtstbtz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `r_sjtstbtz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `xb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `isdel` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据是否逻辑删除',
  `dataversion` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据版本号(yyyymmddhhmiss)',
  `lrr_sfzh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入人',
  `lrsj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入时间',
  `xgr_sfzh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `xgsj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `rybh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `xm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员姓名（外国人姓名）',
  `zjlx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `xb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `mz` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `csrq` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `nl` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄',
  `zz` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
  `dafs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到案方式(字典)',
  `ryly` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员来源（字典）',
  `rsyy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因(案由字典)',
  `r_rssj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所时间',
  `rylx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员类型（字典）',
  `baqm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案区位置(字典)',
  `mj_sfzh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案民警身份证号码',
  `badwjc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案单位简称',
  `r_sfyzjb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否疾病',
  `r_yzjb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重疾病',
  `r_sfssjc` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否伤势检查',
  `r_ssms` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '伤势描述',
  `r_sfzs` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有无伤情',
  `r_sjtstbtz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `r_zsss` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自述伤势成因',
  `c_ryqx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所去向',
  `c_cssj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所时间',
  `c_csyy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所原因',
  `sfywp` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有物品（随身物品）',
  `sftsqt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊群体',
  `tsqt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊群体',
  `ryzt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员状态',
  `c_sfyzjb` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否严重疾病',
  `c_yzjb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '严重疾病',
  `c_sfssjc` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否伤势检查',
  `c_ssms` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '伤势描述',
  `c_sjtstbtz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否特殊体表特征',
  `c_sfzs` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自述成因',
  `c_zsss` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自述伤势成因 ',
  `sfzbm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否自报名',
  `cbdw_bh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '承办单位',
  `lxdh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `hj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍',
  `hjxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍详址',
  `gyzt` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关押状态(01:在所02:送押03:已关押)',
  `zhdd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抓获地点',
  `signname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公章名称（用于打印公章）',
  `rsyy_bh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因(案由编号)',
  `jslxdh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家属联系电话',
  `jsgx` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家属与嫌疑人的关系',
  `gj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `tbsj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '同步时间',
  `zpid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片ID',
  `zzxzqh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址行政区划',
  `sfsfbm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否身份不明',
  `gzdw` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `bm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `ch` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绰号',
  `cym` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '曾用名',
  `whcd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `zzmm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `dafs_text` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到案方案（字典值）',
  `sftb` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否同步至涉案人员',
  `baqsyqkbh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案区使用情况编号',
  `ythcjzt` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一体化采集状态(1为已采集，0为未采集)',
  `cjsj` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集时间',
  `cjdd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集地点',
  `mz_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `r_sfssjc_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否伤势检查',
  `r_sfzs_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否有无伤情',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省厅执法-盘问人员登记' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_jgxt_jdsryxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_jgxt_jdsryxx`;
CREATE TABLE `vi_gazhk_jgxt_jdsryxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `xb` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `jg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `whcd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `zy` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `tssf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `dpzl1` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毒品种类',
  `xds` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '吸毒史',
  `csyy` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '出所原因',
  `hjszd_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地省份',
  `hyzk` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `gj` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `hjszd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `jg_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省份',
  `xdfs1` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '吸毒方式',
  `jdff` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '戒毒方法',
  `xzzqh_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址省份',
  `xzzqh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址省市',
  `jbxxbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本信息编号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb_format` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `csrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `zjlx_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `zjhm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `hyzk_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `mz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `gj_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `jg_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `whcd_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `zy_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `sf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `tssf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `hjszd_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `hjdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `xzzqh_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `xzzxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址详址',
  `xdfs1_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '吸毒方式',
  `dpzl1_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毒品种类',
  `xds_format` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '吸毒史',
  `jdff_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '戒毒方法',
  `badw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案单位',
  `bar` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案人',
  `jyaq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简要案情',
  `rybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `rsyy_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因',
  `rsrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所日期',
  `csyy_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所原因',
  `outrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所日期',
  `csqx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所去向',
  `rswsh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所法律文书',
  `jsmc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所名称',
  `zybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '在押编号',
  `rsyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因',
  `zjlx` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `mz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `xzzqh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `hjszd_ss` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地省市',
  `jg_ss` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省市',
  `jsbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所编号',
  `jsh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监室号',
  PRIMARY KEY (`zybh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '戒毒所人员信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_jgxt_jlsryxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_jgxt_jlsryxx`;
CREATE TABLE `vi_gazhk_jgxt_jlsryxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `xb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `zjlx` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `hyzk` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `jg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `hjszd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `zy` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `tssf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `xzzqh` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `wfxz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '违法性质',
  `rsyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因',
  `jg_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省份',
  `jg_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省市',
  `hjszd_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍省份',
  `hjszd_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍省市',
  `xzzqh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住省市',
  `zybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '在押编号',
  `gj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `whcd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `csyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所原因',
  `jbxxbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本信息标号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb_format` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `csrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `zjlx_format` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `zjhm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `mz_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `hyzk_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `gj_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `jg_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `whcd_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `hjszd_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `hjdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `zy_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `sf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `tssf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `xzzqh_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `xzzxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址详址',
  `jsmc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所名称',
  `wfxz_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '违法性质',
  `rsrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所日期',
  `rsyy_format` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '入所原因',
  `csyy_format` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '出所原因',
  `outrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所日期',
  `csqx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所去向',
  `jlts` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拘留天数',
  `jlksrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拘留开始日期',
  `jljsrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拘留结束日期',
  `sjdw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送押单位',
  `sjr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送押人',
  `flwsh` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所法律文书',
  `jyaq` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简要案情',
  `xzzqh_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住省份',
  `mz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `jsbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所编号',
  `jsh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监室号',
  PRIMARY KEY (`zybh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '拘留所人员信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_jgxt_kssryxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_jgxt_kssryxx`;
CREATE TABLE `vi_gazhk_jgxt_kssryxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `xb` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `gj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `xzzqh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址省市',
  `zybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '在押编号',
  `mz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `jg` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `hjszd` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `tssf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `xzzqh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `ssjd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诉讼阶段',
  `cljg` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理结果',
  `ajlb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别',
  `zjlx` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `xzzqh_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址省份',
  `hyzk` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `zy` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `rsyy` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因',
  `jbxxbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本信息编号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `csrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `xb_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `zjlx_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `zjhm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `mz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `hyzk_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `gj_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `whcd_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `jg_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `hjszd_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `hjdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `zy_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `sf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `tssf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `xzzqh_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `xzzxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址详址',
  `jsmc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所名称',
  `ssjd_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '诉讼阶段',
  `cljg_format` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理结果',
  `ajlb_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别',
  `rsyy_format` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '入所原因',
  `rsrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所日期',
  `csyy_format` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '出所原因',
  `outrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所日期',
  `csqx` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所去向',
  `dbrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '逮捕日期',
  `sydw` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送押单位',
  `syr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送押人',
  `rsflwsh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所法律文书',
  `jyaq` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '简要案情',
  `whcd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `jg_ss` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省市',
  `hjszd_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍省份',
  `csyy` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '出所原因',
  `jg_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省份',
  `hjszd_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍省市',
  `jsh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监室号',
  `jsbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所编号',
  PRIMARY KEY (`zybh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '看守所人员信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_jgxt_sjsryxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_jgxt_sjsryxx`;
CREATE TABLE `vi_gazhk_jgxt_sjsryxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `hjszd_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍省市',
  `jg` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `hjszd_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍省份',
  `zybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '在押编号',
  `mz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `hjszd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `rsyy` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因',
  `jg_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯身份',
  `xzzqh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址省市',
  `xb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `zjlx` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `whcd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `tssf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `xzzqh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `jg_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯省市',
  `gj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `hyzk` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `zy` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `csyy` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '出所原因',
  `xzzqh_sf` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址省份',
  `jbxxbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本信息编号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `csrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `zjlx_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `zjhm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `mz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `hyzk_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
  `gj_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍',
  `jg_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `whcd_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文化程度',
  `hjszd_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
  `hjdxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `zy_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `sf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份',
  `tssf_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊身份',
  `xzzqh_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址区划',
  `xzzxz` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住址详址',
  `jsmc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所名称',
  `rsyy_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所原因',
  `rsrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所日期',
  `csyy_format` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所原因',
  `outrq` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所日期',
  `csqx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出所去向',
  `cbdw` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案单位',
  `cbr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办案人',
  `rswsh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入所法律文书',
  `jyaq` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简要案情',
  `jsh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监室号',
  `jsbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '监所编号',
  PRIMARY KEY (`zybh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收教所人员信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_jwbzd_wzxsxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_jwbzd_wzxsxx`;
CREATE TABLE `vi_gazhk_jwbzd_wzxsxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `xing` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓',
  `ming` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名',
  `xingming` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `mz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `zzmm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `zjxy` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宗教信仰',
  `csny` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生年月',
  `yjqx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原籍区县',
  `yjxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原籍详址',
  `zhqx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在杭区县',
  `zhxz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在杭详址',
  `sflb` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份类别',
  `sspcs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属派出所',
  `sslb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `lx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '脸型',
  `zt` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `bedroomcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房间号',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updatedate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `string1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `string2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `string3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `string4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `datetime1` datetime(0) NULL DEFAULT NULL COMMENT 'null',
  `mz_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `zzmm_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `yjqx_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原籍区县',
  `zhqx_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在杭区县',
  `yjqx_sf` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原籍省份',
  `yjqx_ss` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原籍省市',
  `zhqx_sf` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在杭区县省份',
  `zhqx_ss` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在杭区县省市',
  `sspcs_code6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属派出所',
  `sspcs_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属派出所',
  `datetime2` datetime(0) NULL DEFAULT NULL COMMENT 'null',
  `xb_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '维族学生系统-维族学生' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_lgy_lgxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_lgy_lgxx`;
CREATE TABLE `vi_gazhk_lgy_lgxx`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `lglx_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆治安星级',
  `lgzaxj_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆治安星级',
  `fsbz_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送标识',
  `ssxq_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属辖区',
  `xgrip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人IP',
  `lgblzd8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段8',
  `dt2id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `lgdm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '旅馆代码',
  `lgmc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆名称',
  `lgcym` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆曾用名',
  `fddbr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '法定代表人',
  `fzr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '负责人',
  `zafzr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '治安负责人',
  `dz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `lxdh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `babdh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保安部电话',
  `lgzt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆状态',
  `ztgbrq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态改变日期',
  `fjs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房间数',
  `cws` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '床位数',
  `xj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '星级',
  `dj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级',
  `lglx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆类型',
  `ssxq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属辖区',
  `lgzaxj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆治安星级',
  `pdsj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评定时间',
  `xxsbd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息申报点',
  `lgfj1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆房价1',
  `lgfj2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆房价2',
  `lgfj3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆房价3',
  `whdqrq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维护到期日期',
  `zhycsbsj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次申报时间',
  `lgbh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆编号',
  `lgmcszm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆名称首字母',
  `lgmcjym` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆名称校验码',
  `lgblzd1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段1',
  `lgblzd2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段2',
  `lgblzd3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段3',
  `lgblzd4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段4',
  `bz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `glm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过滤码',
  `cjr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `cjrip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人IP',
  `cjsj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `xgr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `xgsj` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
  `fsbz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送标志',
  `clbz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理标志',
  `lgblzd5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段5',
  `lgblzd6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段6',
  `lgblzd7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆保留字段7',
  `nbrbbsjb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内宾日报报送级别',
  `wbrbbsjb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外宾日报报送级别',
  `wbdjba` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外宾登记备案',
  `wbdjbarq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外宾登记备案日期',
  `tbbz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `dt1id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `dt1x` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `dt1y` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `dt2x` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `dt2y` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `lgzt_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旅馆状态',
  `xj_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '星级',
  `dj_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等级',
  `pcs_code6` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属辖区',
  `lx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `ly` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `zhunquedu` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '准确度',
  `lylx` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经纬度',
  PRIMARY KEY (`lgdm`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '旅馆法人代表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_qbpt_swgzry
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_qbpt_swgzry`;
CREATE TABLE `vi_gazhk_qbpt_swgzry`  (
  `object_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sfzh` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `xb` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `hjdz` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地址',
  `pcs` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '派出所',
  `xq` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '辖区',
  `gkjb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管控级别',
  `gklb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管控类别',
  `lxdh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `rylb` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员类别',
  `id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '涉稳关注人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_qbpt_xjqkry
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_qbpt_xjqkry`;
CREATE TABLE `vi_gazhk_qbpt_xjqkry`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `csrq` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `sfzh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `hjd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地',
  `hjdxz` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `iidd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  PRIMARY KEY (`iidd`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邪教组织人员信息管理系统-邪教前科人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_sjzyzh_qgpqry
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_sjzyzh_qgpqry`;
CREATE TABLE `vi_gazhk_sjzyzh_qgpqry`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `xh` int(22) NOT NULL COMMENT '序号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `sfzh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公民身份号码',
  `jg` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  PRIMARY KEY (`xh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '全国扒窃人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_zazd_ddhcyry
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_zazd_ddhcyry`;
CREATE TABLE `vi_gazhk_zazd_ddhcyry`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键ID',
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `xh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '序号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sfz` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `xb` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `cs` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `dh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `dz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `zw` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务',
  `zt` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `bm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门',
  `djrq` datetime(0) NULL DEFAULT NULL COMMENT '登记日期',
  `sc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属单位',
  `gabs` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公安报送状态',
  `ddhbh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '典当行代码',
  `mc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '典当行名称',
  `ddhdz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '典当行地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '典当行从业人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_zazd_ylcscyryxx
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_zazd_ylcscyryxx`;
CREATE TABLE `vi_gazhk_zazd_ylcscyryxx`  (
  `object_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外部ID',
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `userid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户编号',
  `siteid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场所编号',
  `controlcode` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `userdata` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `firstname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
  `nationality` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `idnum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `height` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身高',
  `weight` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '体重',
  `bloodtype` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '血型',
  `lastname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `telphone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `registeredaddrcode` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划',
  `registeredaddrstreet` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `homeaddrcode` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地代码',
  `homeaddrstreet` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地详址',
  `departmentid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  `dutytype` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务',
  `otherduty` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他职务',
  `dnaserialnum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Dna序列',
  `character` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性格特征',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email地址',
  `groupmemberflag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否党员',
  `partymemberflag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否团员',
  `faith` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宗教信仰',
  `edulevel` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历',
  `graduateschool` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业院校',
  `graduatetime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业时间',
  `speciality` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `foreignlanguage` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外语种类',
  `foreignablity` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外语能力',
  `computerablity` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计算机水平',
  `otherablity` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他才能',
  `worktime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始工作时间',
  `resume` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简历',
  `contractnum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同编号',
  `hiretime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '雇佣日期',
  `basepay` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基本工资',
  `effectivetime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同生效时间',
  `expiretime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同到期时间',
  `warrantor` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '担保人',
  `warrantorrelation` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '和担保人关系',
  `warrantortelphone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '担保人电话',
  `insurenum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保险编号',
  `insuretime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投保日期',
  `insuretype` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投保类别',
  `endinsuretime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同终止日期',
  `personneldocnum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案编号',
  `applicationdocnum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hirestatus` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '雇佣状态',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `hodeiccardflag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `iccardnum` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `iccardid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `operatorcode` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `operatorname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员姓名',
  `lastarchivetime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'lastarchivetime',
  `collecttime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `uploadflag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `recordtime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录时间',
  `photoid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片ID',
  `signaturephotoid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimagel1_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指纹照片1ID',
  `fingeimagel2_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指纹照片2ID',
  `fingeimagel3_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指纹照片3ID',
  `fingeimagel4_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimagel5_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimager1_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimager2_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimager3_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimager4_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingeimager5_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingel1_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingel2_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingel3_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingel4_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `fingel5_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `finger1_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `finger2_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `finger3_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `finger4_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `finger5_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `flag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `deleteflag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `secrecy` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `create_user` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_datetime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `rev1` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev2` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev3` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev4` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev5` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev6` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev7` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `rev8` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `xiecha_flag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `check_person` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `check_unit` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `check_date` datetime(0) NULL DEFAULT NULL COMMENT 'null',
  `effect_flag` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `card_type` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'null',
  `sex_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `siteid_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位名称',
  `nationality_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `registeredaddrcode_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地',
  `registeredaddrcode_sf` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地省份',
  `registeredaddrcode_ss` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地省市',
  `homeaddrcode_format` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地',
  `homeaddrcode_sf` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地省份',
  `homeaddrcode_ss` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地省市',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '娱乐场所从业人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_gazhk_ztry_qg_ztry_jbxx_ii
-- ----------------------------
DROP TABLE IF EXISTS `vi_gazhk_ztry_qg_ztry_jbxx_ii`;
CREATE TABLE `vi_gazhk_ztry_qg_ztry_jbxx_ii`  (
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应该表的主键ID',
  `mz_dm_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `hjd_qh_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地',
  `ryid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人员标识',
  `rybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员编号',
  `jrybh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '旧人员编号',
  `xm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `xb_dm` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别代码',
  `csrq_sx` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `csrq_xx` datetime(0) NULL DEFAULT NULL COMMENT '出生日期下限',
  `sfzh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `mz_dm` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族代码',
  `sg` int(10) NULL DEFAULT NULL COMMENT '身高',
  `ky_dm` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '口音代码',
  `zy_dm` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业代码',
  `hjd_qh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划',
  `hjd_xz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地详址',
  `xzd_qh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地区划',
  `xzd_xz` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地详址',
  `jg_qh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯区划',
  `jg_xz` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯详址',
  `zwbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指纹编号',
  `dna` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'DNA',
  `ryfjxx` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '人员附加信息',
  `ajbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件编号',
  `ajlb_dm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别',
  `ztlx_dm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在逃类型代码',
  `jj` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖金',
  `jyaq` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简要案情',
  `tprq` datetime(0) NULL DEFAULT NULL COMMENT '逃跑日期',
  `tpfx_qh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '逃跑方向',
  `lasj` datetime(0) NULL DEFAULT NULL COMMENT '立案时间',
  `la_dwqh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立案单位区划',
  `la_dwdm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立案单位代码',
  `la_dwxc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立案单位详称',
  `la_dwxt_dm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立案单位系统代码',
  `zb_dwqh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办单位区划',
  `zb_dwdm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办单位代码',
  `zb_dwxc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办单位详称',
  `zb_lxr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办联系人',
  `zb_lxfs` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办联系方式',
  `dj_tbr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记表填表人',
  `dj_rq` datetime(0) NULL DEFAULT NULL COMMENT '登记日期',
  `dj_spr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记审批人',
  `dbjb_dm` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '督捕级别代码',
  `tjlbh` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通缉令编号',
  `blyy` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补录原因',
  `blbs` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '补录标识',
  `sp` varchar(320) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批标识',
  `spyy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批未通过原因',
  `lr_dwdm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入单位',
  `lrr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入人',
  `lrsj` datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
  `xg_dwdm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改单位',
  `xgr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `xgsj` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `sc_dwdm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除单位代码',
  `scr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除人',
  `scsj` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `sc_dwxc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除单位详称',
  `zkbz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转库备注',
  `rbksj_dj` datetime(0) NULL DEFAULT NULL COMMENT '登记入部库时间',
  `rbksj_cx` datetime(0) NULL DEFAULT NULL COMMENT '撤销入部库时间',
  `rbksj_sc` datetime(0) NULL DEFAULT NULL COMMENT '删除入部库时间',
  `sy_dwdm` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '责任单位代码',
  `sbsj` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  `xb_dm_format` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `hjd_qh_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划HJD_QH',
  `hjd_qh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户籍地区划',
  `jg_qh_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯区划',
  `jg_qh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯区划',
  `xzd_qh_sf` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地区划',
  `xzd_qh_ss` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地区划',
  `ky_dm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '口音',
  `zy_dm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `xzd_qh_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '现住地',
  `jg_qh_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  `ajlb_dm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件类别',
  `ztlx_dm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在逃类型',
  `tpfx_qh_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '逃跑方向',
  `la_dwdm_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立案单位',
  `zb_dwdm_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办单位',
  `dbjb_dm_format` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '督捕级别',
  `lr_dwdm_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录入单位',
  `xg_dwdm_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改单位',
  `sc_dwdm_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除单位',
  `sy_dwdm_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '责任单位',
  `ztry_kcbj` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库存标记',
  `ztry_kcbj_format` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库存标记',
  `sc_dwxt_dm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除单位业务系统',
  `sc_spr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除审批人',
  `iidd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IIDD',
  PRIMARY KEY (`ryid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在逃人员系统-全国在逃人员' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_member_ignore
-- ----------------------------
DROP TABLE IF EXISTS `vi_member_ignore`;
CREATE TABLE `vi_member_ignore`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `object_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联布控目标object_id',
  `real_object_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '来源库真实主键ID，涉及到多个主键，需要下划线隔开合并',
  `real_table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所在表',
  `repo_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控库ID',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0-基础库 1-自定义库，目前暂时默认基础库',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`, `object_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控目标无视表（基础库）' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_offline_folder
-- ----------------------------
DROP TABLE IF EXISTS `vi_offline_folder`;
CREATE TABLE `vi_offline_folder`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `org_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构ID',
  `classify_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹名',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '离线视频文件夹' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_offline_folder
-- ----------------------------
INSERT INTO `vi_offline_folder` VALUES (27, NULL, '1', '2019-06-06 18:01:07', '2019-06-06 18:01:07', NULL, NULL);
INSERT INTO `vi_offline_folder` VALUES (28, NULL, '2', '2019-06-06 18:01:14', '2019-06-06 18:01:14', NULL, NULL);

-- ----------------------------
-- Table structure for vi_offline_point
-- ----------------------------
DROP TABLE IF EXISTS `vi_offline_point`;
CREATE TABLE `vi_offline_point`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `camera_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID，关联点位',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点位名',
  `classify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联离线视频文件夹ID',
  `org_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织结构ID',
  `location_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经纬度',
  `camera_type` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '点位类型，虚拟-2 默认虚拟',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '离线点位表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_offline_point
-- ----------------------------
INSERT INTO `vi_offline_point` VALUES (1, '', '234', '27', '', NULL, '2', '2019-06-06 18:01:48', '2019-06-06 18:01:48', NULL, NULL);
INSERT INTO `vi_offline_point` VALUES (2, '', '23434', '27', '', NULL, '2', '2019-06-06 18:01:54', '2019-06-06 18:01:54', NULL, NULL);
INSERT INTO `vi_offline_point` VALUES (3, '', '345456', '28', '', NULL, '2', '2019-06-06 18:02:00', '2019-06-06 18:02:00', NULL, NULL);

-- ----------------------------
-- Table structure for vi_offline_video
-- ----------------------------
DROP TABLE IF EXISTS `vi_offline_video`;
CREATE TABLE `vi_offline_video`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '离线视频名称',
  `camera_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联点位',
  `start_time` datetime(0) NOT NULL COMMENT '开始时间',
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'OSSbucket',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '离线视频表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_offline_video
-- ----------------------------
INSERT INTO `vi_offline_video` VALUES (1, '2342', '1', '2019-06-06 18:02:06', '234', '2019-06-06 18:02:15', '2019-06-06 18:02:15', NULL, NULL);
INSERT INTO `vi_offline_video` VALUES (2, '345456', '3', '2019-06-06 18:02:35', '3456', '2019-06-06 18:02:41', '2019-06-06 18:23:40', NULL, NULL);
INSERT INTO `vi_offline_video` VALUES (3, '345345', '4', '0000-00-00 00:00:00', '345', '2019-06-06 18:24:22', '2019-06-06 18:24:22', NULL, NULL);

-- ----------------------------
-- Table structure for vi_picture
-- ----------------------------
DROP TABLE IF EXISTS `vi_picture`;
CREATE TABLE `vi_picture`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `picture_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '照片在es索引内的唯一ID',
  `orig_image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原图的OSS路径',
  `crop_image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扣图的OSS路径',
  `ori_image_signed_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原图带签名的OSS路径',
  `crop_image_signed_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扣图带签名的OSS路径',
  `picture_time` bigint(16) UNSIGNED NOT NULL COMMENT '图片的时间戳',
  `device_id` int(10) UNSIGNED NOT NULL COMMENT '图片对应的设备id',
  `folder_id` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '图片所属的文件夹id',
  `pic_type` tinyint(1) NOT NULL COMMENT '区别轨迹和收藏 0：不是轨迹 1：是轨迹',
  `score` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '相似程度',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '图片表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_picture
-- ----------------------------
INSERT INTO `vi_picture` VALUES (2, '8848', '222', '222', '222', '234', 123456, 6, 5, 0, '99', '2019-06-15 10:11:25', '2019-06-15 10:13:24', NULL, NULL);
INSERT INTO `vi_picture` VALUES (3, '8847', '222', '222', '222', '234', 123456, 5, 6, 0, '99', '2019-06-15 10:11:52', '2019-06-15 10:13:26', NULL, NULL);
INSERT INTO `vi_picture` VALUES (4, '8846', '222', '222', '222', '234', 123456, 4, 6, 0, '99', '2019-06-15 10:12:07', '2019-06-15 10:13:27', NULL, NULL);
INSERT INTO `vi_picture` VALUES (5, '324234', '222', '222', '222', '234', 123456, 3, 7, 0, '99', '2019-06-15 10:12:12', '2019-06-15 10:13:29', NULL, NULL);
INSERT INTO `vi_picture` VALUES (6, '3424', '222', '222', '222', '234', 123456, 2, 8, 0, '99', '2019-06-15 10:12:17', '2019-06-15 10:13:32', NULL, NULL);
INSERT INTO `vi_picture` VALUES (9, '8888', '111', '111', '111', '111', 123456, 7, NULL, 1, '199', '2019-06-15 14:01:41', '2019-06-15 14:01:41', NULL, NULL);
INSERT INTO `vi_picture` VALUES (10, '88889', '666', '666', '666', '666', 123456, 7, NULL, 1, '199', '2019-06-15 14:01:41', '2019-06-15 14:01:41', NULL, NULL);

-- ----------------------------
-- Table structure for vi_private_member
-- ----------------------------
DROP TABLE IF EXISTS `vi_private_member`;
CREATE TABLE `vi_private_member`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `object_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '自定义object_id，规则：vi_private_id',
  `identity_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `identity_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证id',
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片地址',
  `repo_id` int(10) NOT NULL COMMENT '关联布控库ID',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述信息',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_VI_PRIVATE_MEMBER`(`object_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '自定义目标成员库' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_private_member
-- ----------------------------
INSERT INTO `vi_private_member` VALUES (1, 'vi_private_17640', '张三', '330782195602361454', 'http://s11.sinaimg.cn/middle/001oA5HFzy6YRMyyOiC9a&690', 2, '测试', '2019-06-06 15:19:37', '2019-06-06 15:19:37');
INSERT INTO `vi_private_member` VALUES (3, 'vi_private_3', '王五', '330782195602361454', 'http://s11.sinaimg.cn/middle/001oA5HFzy6YRMyyOiC9a&690', 2, '测试', '2019-06-06 15:22:03', '2019-06-06 15:22:03');

-- ----------------------------
-- Table structure for vi_psurvey_alaram
-- ----------------------------
DROP TABLE IF EXISTS `vi_psurvey_alaram`;
CREATE TABLE `vi_psurvey_alaram`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `orig_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原图oss地址',
  `crop_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸抠图',
  `person_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人体抠图oss地址',
  `entry_time` datetime(0) NULL DEFAULT NULL COMMENT '进入时间',
  `leave_time` datetime(0) NULL DEFAULT NULL COMMENT '离开时间',
  `feature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特征向量',
  `camera_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '摄像头ID',
  `obj_top` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸在原图的位置(上)，单位像素',
  `obj_bottom` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸在原图的位置(下)，单位像素',
  `obj_left` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸在原图的位置(左)，单位像素',
  `obj_right` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸在原图的位置(右)，单位像素',
  `obj_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象唯一ID',
  `obj_uuid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象唯一ID，此id用于关联人体',
  `timestamp` bigint(16) NULL DEFAULT NULL COMMENT '入库时间戳，抓拍时间',
  `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控任务ID，传递给阿里的taskId',
  `status` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '0-无意义 1-关注  2-忽略 默认无意义',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控报警表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_psurvey_alarm_detail
-- ----------------------------
DROP TABLE IF EXISTS `vi_psurvey_alarm_detail`;
CREATE TABLE `vi_psurvey_alarm_detail`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `alarm_id` int(10) NOT NULL COMMENT '关联alarm表ID',
  `task_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控任务ID',
  `bkid` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布控库ID',
  `alarm_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控报警类型code',
  `alarm_type_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布控报警类型名称',
  `feature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特征向量',
  `oss_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'oss地址',
  `similarity` decimal(3, 2) NOT NULL COMMENT '相似度',
  `object_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象姓名',
  `picid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '天擎入库时间',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布控状态，1-已布控 0-撤控',
  `survey_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否被布控库忽略，1-被布控库忽略 0-不忽略',
  `alarm_status` tinyint(1) UNSIGNED ZEROFILL NOT NULL COMMENT '处理状态 0-无意义 1-关注 2-忽略',
  `ai_status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'AI处理状态 0-未处理 1-已处理',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `PK_PDETAIL_ALARM_ID`(`alarm_id`) USING BTREE COMMENT '关联报警ID'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '人员报警布控图比对表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_repo
-- ----------------------------
DROP TABLE IF EXISTS `vi_repo`;
CREATE TABLE `vi_repo`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `bkdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '布控库描述信息',
  `bktype` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控类型 1-人员 2-车辆 3-事件 4-物品',
  `bkname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控库名称',
  `police_station_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分局ID',
  `table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名,基础库的话，vi+源表名 ，自定义库的话，全部为vi_private_member',
  `type` tinyint(1) NOT NULL COMMENT '0-基础库 1-自定义库',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控库(基础库+自定义库)' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_survey_task
-- ----------------------------
DROP TABLE IF EXISTS `vi_survey_task`;
CREATE TABLE `vi_survey_task`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控任务ID，格式:t+id，用于传递给阿里',
  `survey_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控名称',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间（yyyy-MM-dd HH:mm:ss）',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间（yyyy-MM-dd HH:mm:ss）',
  `survey_type` tinyint(2) UNSIGNED NOT NULL COMMENT '布控类型 1-人员 2-车辆 3-事件 4-物品',
  `survey_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '布控状态（对接阿里，默认失败） 0-发送失败 1-发送成功',
  `area_type` tinyint(1) NOT NULL COMMENT '区域或框选择 1-区域选择 2-不规则圈选 3-不规则框选',
  `topic` varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT 'output主题信息，topic+task_id',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启 0-关闭 1-开启',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_SURVEY_TASK_TASK_ID`(`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控任务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_survey_task
-- ----------------------------
INSERT INTO `vi_survey_task` VALUES (1, 't1', '布控黄色衣服啊', '2019-06-23 17:51:31', '2019-08-05 11:51:31', 1, 0, 1, 'topict1', 0, '2019-06-06 13:47:12', '2019-06-06 13:47:12');
INSERT INTO `vi_survey_task` VALUES (2, 't2', '布控棕色衣服啊', '2019-05-23 12:51:31', '2019-07-05 19:51:31', 1, 0, 1, 'topict2', 0, '2019-06-06 13:48:39', '2019-06-06 13:59:49');

-- ----------------------------
-- Table structure for vi_task_device
-- ----------------------------
DROP TABLE IF EXISTS `vi_task_device`;
CREATE TABLE `vi_task_device`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `task_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控任务ID',
  `device_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  `status` tinyint(1) NOT NULL DEFAULT 2 COMMENT '0-失败 1-成功 2-无意义，未操作',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控任务与设备关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_task_repo
-- ----------------------------
DROP TABLE IF EXISTS `vi_task_repo`;
CREATE TABLE `vi_task_repo`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `task_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '布控任务ID，序号',
  `repo_id` int(10) NOT NULL COMMENT '布控库ID，序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '布控任务与库关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for vi_track
-- ----------------------------
DROP TABLE IF EXISTS `vi_track`;
CREATE TABLE `vi_track`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `track_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '轨迹名称',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `folder_id` int(10) NOT NULL COMMENT '该轨迹所属的文件夹id',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  `create_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建者',
  `modified_user` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '轨迹表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_track
-- ----------------------------
INSERT INTO `vi_track` VALUES (1, '第一轨迹', '2019-06-15 10:15:17', 5, '2019-06-15 10:31:47', NULL, NULL);
INSERT INTO `vi_track` VALUES (2, 'Akun轨迹', '2019-06-15 10:20:09', 5, '2019-06-15 10:32:18', NULL, NULL);
INSERT INTO `vi_track` VALUES (3, '第三轨迹', '2019-06-15 10:24:12', 5, '2019-06-15 10:24:12', NULL, NULL);
INSERT INTO `vi_track` VALUES (4, '第四轨迹', '2019-06-15 10:24:12', 5, '2019-06-15 10:32:02', NULL, NULL);
INSERT INTO `vi_track` VALUES (5, '第五轨迹', '2019-06-15 10:24:35', 5, '2019-06-15 10:32:05', NULL, NULL);
INSERT INTO `vi_track` VALUES (6, '第六轨迹', '2019-06-15 10:29:47', 5, '2019-06-15 10:32:11', NULL, NULL);
INSERT INTO `vi_track` VALUES (8, '犯罪嫌疑人轨迹', '2019-06-15 14:01:41', 6, '2019-06-15 14:01:41', NULL, NULL);

-- ----------------------------
-- Table structure for vi_track_picture
-- ----------------------------
DROP TABLE IF EXISTS `vi_track_picture`;
CREATE TABLE `vi_track_picture`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `track_id` int(20) UNSIGNED NOT NULL COMMENT '轨迹id',
  `picture_id` int(20) UNSIGNED NOT NULL COMMENT '图片id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '轨迹图片关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vi_track_picture
-- ----------------------------
INSERT INTO `vi_track_picture` VALUES (3, 8, 9);
INSERT INTO `vi_track_picture` VALUES (4, 8, 10);

SET FOREIGN_KEY_CHECKS = 1;
