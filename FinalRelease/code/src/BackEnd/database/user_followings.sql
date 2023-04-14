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

 Date: 13/01/2023 11:50:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_followings
-- ----------------------------
DROP TABLE IF EXISTS `user_followings`;
CREATE TABLE `user_followings`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `followee` int(10) NULL DEFAULT NULL,
  `follower` int(10) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `valid` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_followings
-- ----------------------------
INSERT INTO `user_followings` VALUES (2, 9, 11, '2022-12-12 11:03:44', '2022-12-26 13:20:57', 1);
INSERT INTO `user_followings` VALUES (4, 9, 12, '2022-12-12 23:42:47', '2022-12-12 23:53:04', 1);
INSERT INTO `user_followings` VALUES (5, 10, 12, '2022-12-12 23:45:40', '2022-12-12 23:47:40', 0);
INSERT INTO `user_followings` VALUES (6, 11, 12, '2022-12-13 01:21:54', '2022-12-13 01:25:21', 0);
INSERT INTO `user_followings` VALUES (7, 31, 13, '2022-12-13 03:47:04', '2022-12-13 03:47:04', 1);
INSERT INTO `user_followings` VALUES (8, 26, 37, '2022-12-13 03:53:39', '2022-12-13 03:53:39', 1);
INSERT INTO `user_followings` VALUES (9, 25, 9, '2022-12-13 08:56:43', '2022-12-13 17:15:49', 0);
INSERT INTO `user_followings` VALUES (10, 10, 9, '2022-12-13 09:14:25', '2022-12-13 17:30:36', 0);
INSERT INTO `user_followings` VALUES (11, 34, 13, '2022-12-13 12:20:26', '2022-12-13 12:20:26', 1);
INSERT INTO `user_followings` VALUES (12, 25, 11, '2022-12-13 13:48:11', '2022-12-13 13:48:11', 1);
INSERT INTO `user_followings` VALUES (13, 12, 11, '2022-12-13 15:23:16', '2022-12-13 15:23:18', 0);
INSERT INTO `user_followings` VALUES (14, 9, 10, '2022-12-13 17:05:11', '2022-12-13 17:05:15', 0);
INSERT INTO `user_followings` VALUES (15, 26, 10, '2022-12-13 17:08:41', '2022-12-13 17:08:41', 1);
INSERT INTO `user_followings` VALUES (16, 11, 9, '2022-12-13 17:16:01', '2022-12-13 17:16:03', 0);
INSERT INTO `user_followings` VALUES (17, 9, 25, '2022-12-27 18:14:04', '2022-12-27 18:14:18', 0);
INSERT INTO `user_followings` VALUES (18, 11, 25, '2022-12-27 18:14:07', '2022-12-27 18:14:07', 1);
INSERT INTO `user_followings` VALUES (19, 12, 10, '2023-01-13 08:39:03', '2023-01-13 08:39:08', 0);
INSERT INTO `user_followings` VALUES (20, 10, 29, '2023-01-13 09:37:16', '2023-01-13 09:37:16', 1);
INSERT INTO `user_followings` VALUES (21, 10, 35, '2023-01-13 09:37:45', '2023-01-13 09:37:45', 1);

SET FOREIGN_KEY_CHECKS = 1;
