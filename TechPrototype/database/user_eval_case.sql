/*
 Navicat Premium Data Transfer

 Source Server         : 0216
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : cloudcommunity

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 12/01/2023 20:22:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_eval_case
-- ----------------------------
DROP TABLE IF EXISTS `user_eval_case`;
CREATE TABLE `user_eval_case`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `case_id` int(11) NOT NULL,
  `love` bit(1) NULL DEFAULT NULL,
  `watch` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_eval_case
-- ----------------------------
INSERT INTO `user_eval_case` VALUES (6, 11, 20042, b'1', b'0');
INSERT INTO `user_eval_case` VALUES (7, 11, 20039, b'1', b'0');
INSERT INTO `user_eval_case` VALUES (8, 11, 20045, b'1', b'1');
INSERT INTO `user_eval_case` VALUES (9, 27, 20087, b'0', b'0');

SET FOREIGN_KEY_CHECKS = 1;
