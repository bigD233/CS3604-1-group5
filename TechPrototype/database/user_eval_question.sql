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

 Date: 13/01/2023 11:50:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_eval_question
-- ----------------------------
DROP TABLE IF EXISTS `user_eval_question`;
CREATE TABLE `user_eval_question`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `love` bit(1) NULL DEFAULT NULL,
  `watch` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_eval_question
-- ----------------------------
INSERT INTO `user_eval_question` VALUES (2, 11, 1, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (3, 11, 40, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (4, 19, 53, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (5, 10, 53, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (6, 35, 54, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (7, 15, 55, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (8, 19, 55, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (9, 18, 54, b'0', b'0');
INSERT INTO `user_eval_question` VALUES (10, 15, 56, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (11, 35, 56, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (12, 15, 57, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (13, 36, 58, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (14, 15, 59, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (15, 18, 60, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (16, 31, 61, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (17, 36, 62, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (18, 18, 63, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (19, 27, 64, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (20, 27, 65, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (21, 27, 66, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (22, 15, 67, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (23, 15, 68, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (24, 19, 69, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (25, 27, 70, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (26, 15, 71, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (27, 35, 72, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (28, 19, 74, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (29, 15, 76, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (30, 31, 77, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (31, 19, 78, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (32, 10, 79, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (33, 10, 80, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (34, 10, 81, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (35, 10, 82, b'1', b'0');
INSERT INTO `user_eval_question` VALUES (36, 27, 82, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (37, 27, 83, b'0', b'1');
INSERT INTO `user_eval_question` VALUES (38, 19, 84, b'1', b'1');
INSERT INTO `user_eval_question` VALUES (39, 36, 86, b'1', b'1');

SET FOREIGN_KEY_CHECKS = 1;
