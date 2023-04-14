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

 Date: 12/01/2023 20:21:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cases_tags
-- ----------------------------
DROP TABLE IF EXISTS `cases_tags`;
CREATE TABLE `cases_tags`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cases_tags
-- ----------------------------
INSERT INTO `cases_tags` VALUES (30, 20039, 15);
INSERT INTO `cases_tags` VALUES (31, 20039, 16);
INSERT INTO `cases_tags` VALUES (32, 20042, 17);
INSERT INTO `cases_tags` VALUES (33, 20042, 18);
INSERT INTO `cases_tags` VALUES (34, 20068, 19);
INSERT INTO `cases_tags` VALUES (37, 20069, 20);
INSERT INTO `cases_tags` VALUES (39, 20043, 22);
INSERT INTO `cases_tags` VALUES (40, 20043, 18);
INSERT INTO `cases_tags` VALUES (41, 20077, 33);
INSERT INTO `cases_tags` VALUES (42, 20077, 2);
INSERT INTO `cases_tags` VALUES (43, 20077, 34);
INSERT INTO `cases_tags` VALUES (44, 20078, 33);
INSERT INTO `cases_tags` VALUES (45, 20078, 35);
INSERT INTO `cases_tags` VALUES (46, 20079, 33);
INSERT INTO `cases_tags` VALUES (47, 20079, 2);
INSERT INTO `cases_tags` VALUES (48, 20079, 36);
INSERT INTO `cases_tags` VALUES (49, 20080, 33);
INSERT INTO `cases_tags` VALUES (50, 20080, 37);
INSERT INTO `cases_tags` VALUES (51, 20081, 33);
INSERT INTO `cases_tags` VALUES (52, 20081, 2);
INSERT INTO `cases_tags` VALUES (53, 20082, 33);
INSERT INTO `cases_tags` VALUES (54, 20082, 38);
INSERT INTO `cases_tags` VALUES (55, 20083, 33);
INSERT INTO `cases_tags` VALUES (56, 20083, 36);
INSERT INTO `cases_tags` VALUES (57, 20083, 39);
INSERT INTO `cases_tags` VALUES (58, 20083, 35);
INSERT INTO `cases_tags` VALUES (59, 20084, 33);
INSERT INTO `cases_tags` VALUES (60, 20084, 38);
INSERT INTO `cases_tags` VALUES (61, 20085, 33);
INSERT INTO `cases_tags` VALUES (62, 20086, 33);
INSERT INTO `cases_tags` VALUES (63, 20086, 2);
INSERT INTO `cases_tags` VALUES (64, 20087, 33);
INSERT INTO `cases_tags` VALUES (65, 20087, 2);
INSERT INTO `cases_tags` VALUES (66, 20088, 33);
INSERT INTO `cases_tags` VALUES (67, 20088, 35);
INSERT INTO `cases_tags` VALUES (68, 20089, 2);
INSERT INTO `cases_tags` VALUES (69, 20089, 33);
INSERT INTO `cases_tags` VALUES (70, 20090, 2);
INSERT INTO `cases_tags` VALUES (71, 20090, 33);
INSERT INTO `cases_tags` VALUES (72, 20091, 33);
INSERT INTO `cases_tags` VALUES (73, 20091, 2);
INSERT INTO `cases_tags` VALUES (74, 20092, 33);
INSERT INTO `cases_tags` VALUES (75, 20092, 2);
INSERT INTO `cases_tags` VALUES (76, 20093, 40);

SET FOREIGN_KEY_CHECKS = 1;
