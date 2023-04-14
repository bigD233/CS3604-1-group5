/*
 Navicat Premium Data Transfer

 Source Server         : cloudcommunity
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : cloudcommunity

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 10/01/2023 22:15:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_eval_case_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_eval_case_comment`;
CREATE TABLE `user_eval_case_comment`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `case_comment_id` int(11) NOT NULL,
  `love` int(2) NULL DEFAULT NULL COMMENT '0 为无点赞或点踩，1为点赞，2为点踩',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_eval_case_comment
-- ----------------------------
INSERT INTO `user_eval_case_comment` VALUES (14, 11, 6, 2);
INSERT INTO `user_eval_case_comment` VALUES (15, 11, 7, 1);
INSERT INTO `user_eval_case_comment` VALUES (16, 11, 8, 1);
INSERT INTO `user_eval_case_comment` VALUES (17, 11, 9, 0);

SET FOREIGN_KEY_CHECKS = 1;
