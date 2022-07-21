/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 127.0.0.1:3306
 Source Schema         : db_kanban

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 31/05/2022 16:44:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for board
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å¡ç‰‡id',
  `kind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ç§ç±»',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æ ‡é¢˜',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ç®€è¿°',
  `stage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'é˜¶æ®µ',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è¯¦è¿°',
  `attachments` json NULL COMMENT 'é™„ä»¶',
  `members` json NULL COMMENT 'æˆå‘˜',
  `project_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'é¡¹ç›®id',
  `team_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å›¢é˜Ÿid',
  `start_time` datetime(0) NOT NULL COMMENT 'å¼€å§‹æ—¶é—´',
  `end_time` datetime(0) NOT NULL COMMENT 'ç»“æŸæ—¶é—´',
  `update_time` datetime(0) NOT NULL COMMENT 'æ›´æ–°æ—¶é—´',
  `create_time` datetime(0) NOT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of board
-- ----------------------------
INSERT INTO `board` VALUES ('047a14fff3b60855668c35822a1fa3ad', 'Design', 'UI 设计', '开发系统的操作界面', 'PROCESS IN', '', NULL, '[{\"id\": \"8cfb79b6d0a15a3d7ec632cb82b76e2f\", \"avatar\": null, \"gender\": \"male\", \"status\": \"NORMAL\", \"teamId\": \"3288355bd0d182a05464cac5f8e04e79\", \"account\": \"1898061\", \"delFlag\": 0, \"nickName\": \"李四\", \"password\": \"$2a$10$HR5UlbmJ/AwyrqDTywU3FOMM/2Nxn4JdRe3ZrbieiJ15dJIgb1rVa\", \"signature\": null}, {\"id\": \"a3d791c307fae65afb755418578a0354\", \"avatar\": null, \"gender\": \"male\", \"status\": \"NORMAL\", \"teamId\": \"3288355bd0d182a05464cac5f8e04e79\", \"account\": \"1801140\", \"delFlag\": 0, \"nickName\": \"张海凌\", \"password\": \"$2a$10$TBmEpjTGh4h/7rHWbLeoje6/upsLfFQwJHJVUX1AUDuo3rNa7Ced.\", \"signature\": null}]', 'b7c0d0fc28c2e4db080c0ea3c2e0b879', '3288355bd0d182a05464cac5f8e04e79', '2022-05-03 00:00:00', '2022-05-12 00:00:00', '2022-05-25 11:57:24', '2022-05-25 11:57:24');

-- ----------------------------
-- Table structure for board_comments
-- ----------------------------
DROP TABLE IF EXISTS `board_comments`;
CREATE TABLE `board_comments`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è¯„è®ºid',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'çˆ¶çº§id',
  `board_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'å¡ç‰‡id',
  `user_info` json NOT NULL COMMENT 'ç”¨æˆ·ä¿¡æ¯',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'è¯„è®ºå†…å®¹',
  `create_time` datetime(0) NOT NULL COMMENT 'è¯„è®ºæ—¶é—´',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of board_comments
-- ----------------------------

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æ–‡ä»¶id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æ–‡ä»¶åç§°',
  `board_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å¡ç‰‡id',
  `team_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å›¢é˜Ÿid',
  `create_time` datetime(0) NOT NULL COMMENT 'ä¸Šä¼ æ—¶é—´',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_info
-- ----------------------------

-- ----------------------------
-- Table structure for kanban_stage
-- ----------------------------
DROP TABLE IF EXISTS `kanban_stage`;
CREATE TABLE `kanban_stage`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '阶段id',
  `stage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '阶段名称',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kanban_stage
-- ----------------------------
INSERT INTO `kanban_stage` VALUES ('c37244a7ca38c892c48cc4351b995466', 'PROCESS IN', '2022-05-25 11:57:49');
INSERT INTO `kanban_stage` VALUES ('d7d513872990c4ade8a10c9ad0ab8dc9', 'TO DO', '2022-05-25 11:56:12');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'èœå•id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'èœå•åç§°',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'Project');
INSERT INTO `menu` VALUES ('2', 'DashBoard');
INSERT INTO `menu` VALUES ('3', 'Notification');
INSERT INTO `menu` VALUES ('4', 'Team');
INSERT INTO `menu` VALUES ('5', 'Settings');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'é€šçŸ¥id',
  `message` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'é€šçŸ¥å†…å®¹',
  `origin` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å‘é€è€…id',
  `destination` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æŽ¥æ”¶è€…id',
  `create_time` datetime(0) NOT NULL COMMENT 'å‘é€æ—¶é—´',
  `update_time` datetime(0) NOT NULL COMMENT 'ä¿®æ”¹æ—¶é—´',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'é¡¹ç›®id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'é¡¹ç›®åç§°',
  `team_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å›¢é˜Ÿid',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'æè¿°',
  `wip` int(0) NOT NULL COMMENT 'åœ¨åˆ¶å“é™åˆ¶',
  `is_check` tinyint(0) NOT NULL COMMENT 'æ˜¯å¦é€‰ä¸­',
  `members` json NULL COMMENT 'é¡¹ç›®æˆå‘˜',
  `create_time` datetime(0) NOT NULL COMMENT 'åˆ›å»ºæ—¶é—´',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('b7c0d0fc28c2e4db080c0ea3c2e0b879', '运维系统', '3288355bd0d182a05464cac5f8e04e79', '为了降低公司维护成本，开发此系统', 4, 1, '[{\"id\": \"8cfb79b6d0a15a3d7ec632cb82b76e2f\", \"avatar\": null, \"gender\": \"male\", \"status\": \"NORMAL\", \"teamId\": \"3288355bd0d182a05464cac5f8e04e79\", \"account\": \"1898061\", \"delFlag\": 0, \"nickName\": \"李四\", \"password\": \"$2a$10$HR5UlbmJ/AwyrqDTywU3FOMM/2Nxn4JdRe3ZrbieiJ15dJIgb1rVa\", \"signature\": null}, {\"id\": \"a3d791c307fae65afb755418578a0354\", \"avatar\": null, \"gender\": \"male\", \"status\": \"NORMAL\", \"teamId\": \"3288355bd0d182a05464cac5f8e04e79\", \"account\": \"1801140\", \"delFlag\": 0, \"nickName\": \"张海凌\", \"password\": \"$2a$10$TBmEpjTGh4h/7rHWbLeoje6/upsLfFQwJHJVUX1AUDuo3rNa7Ced.\", \"signature\": null}]', '2022-05-25 11:27:57');

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions`  (
  `permission_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æƒé™id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æƒé™åç§°',
  `flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æƒé™æ ‡è¯†',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions` VALUES ('2fa97f02e8759749da347e1e0b8c9e7f', '文件管理', 'sys:file:manage');
INSERT INTO `sys_permissions` VALUES ('360e17bdc4a1ac5e00f726181c452c39', '发送通知', 'sys:notice:add');
INSERT INTO `sys_permissions` VALUES ('5c8c0f2ef2d4153e4f9120dae30439f2', '添加阶段', 'sys:stage:add');
INSERT INTO `sys_permissions` VALUES ('beabac884cc0a55af901e4c1562b3821', '文件列表', 'sys:file:list');
INSERT INTO `sys_permissions` VALUES ('c509d6a44decf6da61024e55946989d9', '操作项目', 'sys:project:edit');
INSERT INTO `sys_permissions` VALUES ('c8aa53b447160f16c464479f160d49c8', '操作看板', 'sys:card:edit');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è§’è‰²id',
  `flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è§’è‰²æ ‡è¯†',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è§’è‰²åç§°',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('34b74bbffa5bdb0540885c9d1bcfaf89', 'Dev', '开发者');
INSERT INTO `sys_role` VALUES ('e6bec36c34352c103ab7588aee331939', 'PM', '项目经理');

-- ----------------------------
-- Table structure for sys_role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permissions`;
CREATE TABLE `sys_role_permissions`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è§’è‰²æƒé™è¡¨',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è§’è‰²id',
  `permission_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æƒé™id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permissions
-- ----------------------------
INSERT INTO `sys_role_permissions` VALUES ('2337837f1037a4a3ddd32ead210384b6', 'e6bec36c34352c103ab7588aee331939', '5c8c0f2ef2d4153e4f9120dae30439f2');
INSERT INTO `sys_role_permissions` VALUES ('37e1115bfb7654f5797b7967f72a3eca', '34b74bbffa5bdb0540885c9d1bcfaf89', '2fa97f02e8759749da347e1e0b8c9e7f');
INSERT INTO `sys_role_permissions` VALUES ('53c0a7dff59acc09c23871044d6b9e25', 'e6bec36c34352c103ab7588aee331939', '2fa97f02e8759749da347e1e0b8c9e7f');
INSERT INTO `sys_role_permissions` VALUES ('5ead27e58d7d3919c2f8d46e5c1ec2ae', 'e6bec36c34352c103ab7588aee331939', 'c8aa53b447160f16c464479f160d49c8');
INSERT INTO `sys_role_permissions` VALUES ('7c936eb2a1ac638e61a84c3d759962dc', 'e6bec36c34352c103ab7588aee331939', '360e17bdc4a1ac5e00f726181c452c39');
INSERT INTO `sys_role_permissions` VALUES ('7e570b26f9d997abda7265d6300e5153', 'e6bec36c34352c103ab7588aee331939', 'beabac884cc0a55af901e4c1562b3821');
INSERT INTO `sys_role_permissions` VALUES ('b1c39fc204e6c23b186d287f88c4c918', '34b74bbffa5bdb0540885c9d1bcfaf89', 'c8aa53b447160f16c464479f160d49c8');
INSERT INTO `sys_role_permissions` VALUES ('d22f5bb3dbf8e383cf434be6b53abfc0', 'e6bec36c34352c103ab7588aee331939', 'c509d6a44decf6da61024e55946989d9');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ç”¨æˆ·id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ç”¨æˆ·è´¦å·',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ç”¨æˆ·å¯†ç ',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æ˜µç§°',
  `signature` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ä¸ªæ€§ç­¾å',
  `team_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å›¢é˜Ÿid',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'çŠ¶æ€',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'æ€§åˆ«',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'å¤´åƒåœ°å€',
  `del_flag` tinyint(0) NOT NULL COMMENT 'åˆ é™¤æ ‡è®°',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('8cfb79b6d0a15a3d7ec632cb82b76e2f', '1898061', '$2a$10$HR5UlbmJ/AwyrqDTywU3FOMM/2Nxn4JdRe3ZrbieiJ15dJIgb1rVa', '李四', NULL, '3288355bd0d182a05464cac5f8e04e79', 'NORMAL', 'male', NULL, 0);
INSERT INTO `sys_user` VALUES ('a3d791c307fae65afb755418578a0354', '1801140', '$2a$10$TBmEpjTGh4h/7rHWbLeoje6/upsLfFQwJHJVUX1AUDuo3rNa7Ced.', '张海凌', NULL, '3288355bd0d182a05464cac5f8e04e79', 'NORMAL', 'male', NULL, 0);
INSERT INTO `sys_user` VALUES ('d819d91e9f0f1b5af8cb07fa607d2232', '859975072', '$2a$10$r66O50IHSGcdtF7BkQhzAOT6YHUC6QaPBt8IJSin8qNqN2RPxRxRK', '张恒博', NULL, '3288355bd0d182a05464cac5f8e04e79', 'NORMAL', 'male', NULL, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ç”¨æˆ·id',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'è§’è‰²id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('04a37ee61ba5add66eeced6d2e178d42', '8cfb79b6d0a15a3d7ec632cb82b76e2f', '34b74bbffa5bdb0540885c9d1bcfaf89');
INSERT INTO `sys_user_role` VALUES ('6a178de069a83167af7f5bd03747cf07', 'c752d8bc46a84848639a993a32d352cb', 'e6bec36c34352c103ab7588aee331939');
INSERT INTO `sys_user_role` VALUES ('c77d34f9ee7d8fcaade7b6179c9fc978', 'de86ba7471aff9498b3d82449d8bc5fd', '34b74bbffa5bdb0540885c9d1bcfaf89');
INSERT INTO `sys_user_role` VALUES ('cb06555e48b1b1e5be735f6f8023f930', 'd819d91e9f0f1b5af8cb07fa607d2232', 'e6bec36c34352c103ab7588aee331939');

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'å›¢é˜Ÿid',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'å›¢é˜Ÿåç§°',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'å›¢é˜Ÿè´¦å·',
  `members` json NULL COMMENT 'å›¢é˜Ÿæˆå‘˜',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('3288355bd0d182a05464cac5f8e04e79', '团队1', 'teamAcc', NULL);

SET FOREIGN_KEY_CHECKS = 1;
