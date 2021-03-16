/*
 Navicat Premium Data Transfer

 Source Server         : LuckyMoney
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : ars

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 16/03/2021 17:42:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expect_score` int(11) NULL DEFAULT NULL,
  `chinese` int(11) NULL DEFAULT NULL,
  `math` int(11) NULL DEFAULT NULL,
  `english` int(11) NULL DEFAULT NULL,
  `physics` int(11) NULL DEFAULT NULL,
  `chemistry` int(11) NULL DEFAULT NULL,
  `biology` int(11) NULL DEFAULT NULL,
  `politics` int(11) NULL DEFAULT NULL,
  `history` int(11) NULL DEFAULT NULL,
  `geography` int(11) NULL DEFAULT NULL,
  `technology` int(255) NULL DEFAULT NULL,
  `comprehensive_science` int(255) NULL DEFAULT NULL,
  `comprehensive_liberal_arts` int(255) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
