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

 Date: 13/01/2023 11:49:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_eval_answer_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_eval_answer_comment`;
CREATE TABLE `user_eval_answer_comment`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `answer_comment_id` int(11) NOT NULL,
  `love` int(2) NULL DEFAULT NULL COMMENT '0 为无点赞或点踩，1为点赞，2为点踩',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_eval_answer_comment
-- ----------------------------
INSERT INTO `user_eval_answer_comment` VALUES (5, 11, 1, 0);
INSERT INTO `user_eval_answer_comment` VALUES (6, 11, 4, 2);
INSERT INTO `user_eval_answer_comment` VALUES (7, 11, 10, 1);
INSERT INTO `user_eval_answer_comment` VALUES (8, 11, 8, 1);
INSERT INTO `user_eval_answer_comment` VALUES (9, 11, 56, 1);
INSERT INTO `user_eval_answer_comment` VALUES (11, 11, 5, 2);
INSERT INTO `user_eval_answer_comment` VALUES (12, 11, 6, 2);
INSERT INTO `user_eval_answer_comment` VALUES (13, 11, 33, 1);
INSERT INTO `user_eval_answer_comment` VALUES (14, 11, 62, 1);
INSERT INTO `user_eval_answer_comment` VALUES (15, 11, 57, 1);
INSERT INTO `user_eval_answer_comment` VALUES (16, 11, 59, 1);
INSERT INTO `user_eval_answer_comment` VALUES (17, 11, 58, 0);
INSERT INTO `user_eval_answer_comment` VALUES (18, 10, 65, 1);
INSERT INTO `user_eval_answer_comment` VALUES (19, 36, 71, 1);
INSERT INTO `user_eval_answer_comment` VALUES (20, 31, 70, 1);
INSERT INTO `user_eval_answer_comment` VALUES (21, 19, 72, 2);
INSERT INTO `user_eval_answer_comment` VALUES (22, 18, 73, 1);
INSERT INTO `user_eval_answer_comment` VALUES (23, 18, 75, 1);
INSERT INTO `user_eval_answer_comment` VALUES (24, 15, 76, 1);

SET FOREIGN_KEY_CHECKS = 1;
