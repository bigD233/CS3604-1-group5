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

 Date: 13/01/2023 11:49:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_eval_answer
-- ----------------------------
DROP TABLE IF EXISTS `user_eval_answer`;
CREATE TABLE `user_eval_answer`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `answer_id` int(11) NOT NULL,
  `love` int(2) NULL DEFAULT NULL COMMENT '0 为无点赞或点踩，1为点赞，2为点踩',
  `save` bit(1) NULL DEFAULT NULL,
  `report` bit(1) NULL DEFAULT NULL,
  `report_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_eval_answer
-- ----------------------------
INSERT INTO `user_eval_answer` VALUES (6, 11, 1, 1, b'0', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (7, 11, 2, 1, b'0', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (8, 11, 21, 1, b'1', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (10, 11, 3, 2, b'0', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (11, 11, 15, 1, b'0', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (12, 11, 5, 0, b'0', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (13, 11, 18, 2, b'0', b'1', '无意义内容');
INSERT INTO `user_eval_answer` VALUES (14, 11, 19, 1, b'0', b'0', NULL);
INSERT INTO `user_eval_answer` VALUES (15, 11, 22, 2, b'0', b'1', '破防了');
INSERT INTO `user_eval_answer` VALUES (16, 10, 24, 2, b'1', b'0', '');
INSERT INTO `user_eval_answer` VALUES (17, 25, 24, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (18, 25, 25, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (19, 18, 25, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (20, 18, 24, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (21, 35, 27, 2, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (22, 18, 28, 2, b'0', b'1', '恶意攻击');
INSERT INTO `user_eval_answer` VALUES (23, 27, 29, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (24, 19, 29, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (25, 15, 31, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (26, 35, 32, 2, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (27, 18, 32, 0, b'0', b'1', '恶意攻击');
INSERT INTO `user_eval_answer` VALUES (28, 15, 33, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (29, 31, 33, 0, b'1', b'0', '');
INSERT INTO `user_eval_answer` VALUES (30, 36, 34, 1, b'1', b'0', '');
INSERT INTO `user_eval_answer` VALUES (31, 15, 35, 1, b'1', b'0', '');
INSERT INTO `user_eval_answer` VALUES (32, 15, 36, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (33, 19, 36, 1, b'1', b'0', '');
INSERT INTO `user_eval_answer` VALUES (34, 18, 37, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (35, 18, 27, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (36, 31, 39, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (37, 18, 41, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (38, 18, 42, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (39, 27, 43, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (40, 15, 45, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (41, 15, 46, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (42, 19, 47, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (43, 27, 48, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (44, 15, 49, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (45, 19, 51, 2, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (46, 15, 52, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (47, 31, 53, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (48, 10, 55, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (49, 36, 60, 1, b'0', b'0', '');
INSERT INTO `user_eval_answer` VALUES (50, 35, 50, 1, b'0', b'1', '迷信言论');

SET FOREIGN_KEY_CHECKS = 1;
