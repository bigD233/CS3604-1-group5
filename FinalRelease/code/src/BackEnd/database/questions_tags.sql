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

 Date: 13/01/2023 11:49:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for questions_tags
-- ----------------------------
DROP TABLE IF EXISTS `questions_tags`;
CREATE TABLE `questions_tags`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_tag`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of questions_tags
-- ----------------------------
INSERT INTO `questions_tags` VALUES (22, 40, 12);
INSERT INTO `questions_tags` VALUES (23, 40, 14);
INSERT INTO `questions_tags` VALUES (26, 41, 14);
INSERT INTO `questions_tags` VALUES (27, 42, 13);
INSERT INTO `questions_tags` VALUES (32, 39, 12);
INSERT INTO `questions_tags` VALUES (33, 39, 13);
INSERT INTO `questions_tags` VALUES (47, 47, 2);
INSERT INTO `questions_tags` VALUES (51, 1, 1);
INSERT INTO `questions_tags` VALUES (52, 1, 2);
INSERT INTO `questions_tags` VALUES (53, 48, 1);
INSERT INTO `questions_tags` VALUES (59, 50, 2);
INSERT INTO `questions_tags` VALUES (60, 51, 2);
INSERT INTO `questions_tags` VALUES (61, 53, 2);
INSERT INTO `questions_tags` VALUES (62, 53, 35);
INSERT INTO `questions_tags` VALUES (63, 54, 2);
INSERT INTO `questions_tags` VALUES (64, 55, 41);
INSERT INTO `questions_tags` VALUES (65, 56, 42);
INSERT INTO `questions_tags` VALUES (66, 56, 43);
INSERT INTO `questions_tags` VALUES (67, 56, 44);
INSERT INTO `questions_tags` VALUES (68, 57, 2);
INSERT INTO `questions_tags` VALUES (69, 57, 45);
INSERT INTO `questions_tags` VALUES (70, 58, 46);
INSERT INTO `questions_tags` VALUES (71, 59, 47);
INSERT INTO `questions_tags` VALUES (72, 59, 48);
INSERT INTO `questions_tags` VALUES (73, 60, 2);
INSERT INTO `questions_tags` VALUES (74, 61, 2);
INSERT INTO `questions_tags` VALUES (75, 62, 2);
INSERT INTO `questions_tags` VALUES (76, 62, 49);
INSERT INTO `questions_tags` VALUES (77, 63, 2);
INSERT INTO `questions_tags` VALUES (78, 64, 50);
INSERT INTO `questions_tags` VALUES (79, 65, 50);
INSERT INTO `questions_tags` VALUES (80, 66, 2);
INSERT INTO `questions_tags` VALUES (81, 66, 51);
INSERT INTO `questions_tags` VALUES (82, 67, 41);
INSERT INTO `questions_tags` VALUES (83, 68, 52);
INSERT INTO `questions_tags` VALUES (84, 69, 52);
INSERT INTO `questions_tags` VALUES (85, 70, 2);
INSERT INTO `questions_tags` VALUES (86, 70, 52);
INSERT INTO `questions_tags` VALUES (87, 71, 52);
INSERT INTO `questions_tags` VALUES (88, 71, 53);
INSERT INTO `questions_tags` VALUES (89, 72, 2);
INSERT INTO `questions_tags` VALUES (90, 73, 54);
INSERT INTO `questions_tags` VALUES (91, 74, 54);
INSERT INTO `questions_tags` VALUES (92, 74, 53);
INSERT INTO `questions_tags` VALUES (93, 75, 2);
INSERT INTO `questions_tags` VALUES (94, 75, 54);
INSERT INTO `questions_tags` VALUES (95, 76, 2);
INSERT INTO `questions_tags` VALUES (96, 77, 55);
INSERT INTO `questions_tags` VALUES (97, 78, 55);
INSERT INTO `questions_tags` VALUES (98, 79, 2);
INSERT INTO `questions_tags` VALUES (99, 80, 2);
INSERT INTO `questions_tags` VALUES (100, 80, 53);
INSERT INTO `questions_tags` VALUES (101, 81, 56);
INSERT INTO `questions_tags` VALUES (102, 82, 56);
INSERT INTO `questions_tags` VALUES (103, 82, 53);
INSERT INTO `questions_tags` VALUES (104, 83, 2);
INSERT INTO `questions_tags` VALUES (105, 84, 2);
INSERT INTO `questions_tags` VALUES (106, 84, 35);
INSERT INTO `questions_tags` VALUES (107, 85, 2);
INSERT INTO `questions_tags` VALUES (108, 85, 35);
INSERT INTO `questions_tags` VALUES (109, 86, 43);

SET FOREIGN_KEY_CHECKS = 1;
