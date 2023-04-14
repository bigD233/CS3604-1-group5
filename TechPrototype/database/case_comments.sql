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

 Date: 10/01/2023 22:15:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for case_comments
-- ----------------------------
DROP TABLE IF EXISTS `case_comments`;
CREATE TABLE `case_comments`  (
  `comment_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_text` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` int(11) NOT NULL,
  `level_one` bit(1) NOT NULL,
  `commenter_id` int(11) NOT NULL,
  `comment_release_time` datetime NOT NULL,
  `descendants` int(11) NOT NULL,
  `likes` int(11) NOT NULL,
  `dislikes` int(11) NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of case_comments
-- ----------------------------
INSERT INTO `case_comments` VALUES (6, 'adsf', 20042, b'1', 11, '2023-01-10 21:51:58', 1, 0, 1, b'0');
INSERT INTO `case_comments` VALUES (7, 'wqer', 6, b'0', 11, '2023-01-10 21:53:48', 0, 1, 0, b'0');
INSERT INTO `case_comments` VALUES (8, 'a', 20042, b'1', 11, '2023-01-10 21:53:55', 2, 1, 0, b'1');
INSERT INTO `case_comments` VALUES (9, 'ewqr', 8, b'0', 11, '2023-01-10 21:53:58', 1, 0, 0, b'0');
INSERT INTO `case_comments` VALUES (10, 'aadsf', 9, b'0', 11, '2023-01-10 21:54:04', 0, 0, 0, b'0');

SET FOREIGN_KEY_CHECKS = 1;
