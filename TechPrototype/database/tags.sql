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

 Date: 13/01/2023 11:49:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `tag_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (1, '心理健康');
INSERT INTO `tags` VALUES (2, 'COVID-19');
INSERT INTO `tags` VALUES (4, '躁郁症');
INSERT INTO `tags` VALUES (12, '哮喘');
INSERT INTO `tags` VALUES (13, '癫痫');
INSERT INTO `tags` VALUES (14, '医疗美容');
INSERT INTO `tags` VALUES (15, '荨麻疹');
INSERT INTO `tags` VALUES (16, '慢性');
INSERT INTO `tags` VALUES (17, '牙科');
INSERT INTO `tags` VALUES (18, '外伤');
INSERT INTO `tags` VALUES (19, '放放4法3');
INSERT INTO `tags` VALUES (20, '性癖');
INSERT INTO `tags` VALUES (21, 'cscsc');
INSERT INTO `tags` VALUES (22, '眼科');
INSERT INTO `tags` VALUES (23, '痛风');
INSERT INTO `tags` VALUES (24, 'asdf');
INSERT INTO `tags` VALUES (25, 'qewr');
INSERT INTO `tags` VALUES (26, 'zxcv');
INSERT INTO `tags` VALUES (27, '脑部');
INSERT INTO `tags` VALUES (28, '外科');
INSERT INTO `tags` VALUES (29, 'dsaf');
INSERT INTO `tags` VALUES (30, 'adsf');
INSERT INTO `tags` VALUES (31, 'zcxv');
INSERT INTO `tags` VALUES (32, 'wqer');
INSERT INTO `tags` VALUES (33, '新冠');
INSERT INTO `tags` VALUES (34, '发热');
INSERT INTO `tags` VALUES (35, '后遗症');
INSERT INTO `tags` VALUES (36, '肺部');
INSERT INTO `tags` VALUES (37, '社交');
INSERT INTO `tags` VALUES (38, '感冒');
INSERT INTO `tags` VALUES (39, '绝症');
INSERT INTO `tags` VALUES (40, '胃部');
INSERT INTO `tags` VALUES (41, '精神分裂');
INSERT INTO `tags` VALUES (42, '车祸');
INSERT INTO `tags` VALUES (43, '整容');
INSERT INTO `tags` VALUES (44, '毁容');
INSERT INTO `tags` VALUES (45, '传染性疾病');
INSERT INTO `tags` VALUES (46, '难产');
INSERT INTO `tags` VALUES (47, '早产');
INSERT INTO `tags` VALUES (48, '先天性障碍');
INSERT INTO `tags` VALUES (49, '猝死');
INSERT INTO `tags` VALUES (50, '骨折');
INSERT INTO `tags` VALUES (51, '抑郁');
INSERT INTO `tags` VALUES (52, '阿尔兹海默症');
INSERT INTO `tags` VALUES (53, '预防');
INSERT INTO `tags` VALUES (54, '痔疮');
INSERT INTO `tags` VALUES (55, '唐氏综合症');
INSERT INTO `tags` VALUES (56, '前列腺炎');

SET FOREIGN_KEY_CHECKS = 1;
