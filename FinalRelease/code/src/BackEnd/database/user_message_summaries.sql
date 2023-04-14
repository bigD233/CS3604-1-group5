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

 Date: 13/12/2022 09:19:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_message_summaries
-- ----------------------------
DROP TABLE IF EXISTS `user_message_summaries`;
CREATE TABLE `user_message_summaries`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `message_from` int(11) NOT NULL,
  `message_to` int(11) NOT NULL,
  `update_time` datetime NOT NULL,
  `unread` int(11) NOT NULL,
  `message` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_message_summaries
-- ----------------------------
INSERT INTO `user_message_summaries` VALUES (3, 11, 9, '2022-12-12 16:09:48', 0, 'hello');
INSERT INTO `user_message_summaries` VALUES (4, 9, 11, '2022-12-12 16:09:48', 0, 'hello');
INSERT INTO `user_message_summaries` VALUES (5, 9, 10, '2022-12-13 08:57:48', 1, '????');
INSERT INTO `user_message_summaries` VALUES (6, 10, 9, '2022-12-13 08:57:48', 0, '????');

SET FOREIGN_KEY_CHECKS = 1;
