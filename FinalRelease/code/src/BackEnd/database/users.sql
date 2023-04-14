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

 Date: 13/01/2023 11:50:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `height` int(11) NULL DEFAULT NULL,
  `weight` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (9, 'Alice', '$2a$10$sURrIj0PZw/jiR/UMpCm2.t0pNdujspEDxXZvX5QxNCm.W9n.rOIy', 'http://localhost:8443/api/image/2022121020465995019b0a738df7d9c049edcb3755b574.jpg', '我该走哪条路？', '2022-12-10 20:41:08', '2022-12-13 02:36:19', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (10, 'Bob', '$2a$10$LWru8aVoGWuTurraQbK32eErr2PUBitv5j4j8xEBGbHU2zcfWr5lC', 'http://localhost:8443/api/image/2022121020585136bef6358feb2bcef94ee83788547595.jpg', '精神分裂患者', '2022-12-10 20:56:00', '2022-12-13 02:36:55', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (11, '徒此', '$2a$10$8tahAtVsAvJ5KTzP8ux6aOXcATH11sdGSHhlrgXiXb/HpGHJxEXB6', 'http://localhost:8443/api/image/20221210214410e8629d8da472a0de30547a766951addc.png', 'Flesh is weak.', '2022-12-10 21:25:21', '2022-12-13 02:35:47', '2002-09-20', '男', 164, 55);
INSERT INTO `users` VALUES (12, 'Carol', '$2a$10$HXq7QW4.MHyjX6Rk7WhLpOxzTzlgSESy6Pjv2C0mkXu5m.sQ4EqrC', 'http://localhost:8443/api/image/20221211230310fc7ef861a3c5da6d60dc934db03a682c.jpg', '蚌埠住的大学生一枚吖~', '2022-12-11 23:03:01', '2022-12-13 02:37:34', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (13, 'Dave', '$2a$10$GIAUoVNTAQImSWpKNRXs9.daTCa7CNy5CY5qmVx0EY/dqHU7trd6e', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '哮喘患者', '2022-12-13 02:32:23', '2022-12-13 03:12:01', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (14, 'Eve', '$2a$10$Id6yQFI5UmgpOnQMFD/Qu.Civc86EvbU5dCSEgnxtRNfWU4sh1lN6', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '糖尿病患者', '2022-12-13 02:32:38', '2022-12-13 02:32:38', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (15, 'Issac', '$2a$10$gMQi5pID3pcbGas/0Y1RXeCz.62Wo1D43gIFwPHmsAMFyw3kC51F2', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '唐氏儿母亲', '2022-12-13 02:33:27', '2022-12-13 02:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (16, 'Ivan', '$2a$10$KH/gM5uVCq6o0KrX8GvEv.O6sQkbh5MDnKh5tH.xWk2BLkyfQAYY2', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '肺结核患者', '2022-12-13 02:33:46', '2022-12-13 02:33:46', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (17, 'Justin', '$2a$10$WTfxIJA/yE1K2HMmEoh7O.VDWrBD8dJVLGMWSFDgRTimjcSQc3yke', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '乙肝患者', '2022-12-13 02:34:10', '2022-12-13 02:34:10', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (18, 'Mallory', '$2a$10$ihd9mANor.Nyp6CKGgXP9erjlCmYhXOZ7eJ5IcdJ/qA10k2jmdDAa', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '老年痴呆症患者儿子', '2022-12-13 02:34:33', '2022-12-13 02:34:33', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (19, 'Oscar', '$2a$10$pPkWW4YskxmY7BDDzq98h.Eqi74YzB3PDHQi96VHOVaZPdo/tUFU.', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '痔疮患者', '2022-12-13 02:34:48', '2022-12-13 02:34:48', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (20, 'Pat', '$2a$10$D/zsomYJaOVTMPnLoGR30.8I3R32qAbYoRyGqYs03Vqp/0KkN.gAu', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '癫痫患者', '2022-12-13 02:34:59', '2022-12-13 02:34:59', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (21, 'Steve', '$2a$10$RJPpUCQ43YMjKkCK1kGyL.LULXGEXQgHLH2gJuCFOFwcWtAIGQ8SK', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '痛风患者', '2022-12-13 02:35:17', '2022-12-13 02:35:17', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (22, '云淡风轻', '$2a$10$SMcULKUqtCuNzXsPHzcY8.lqg2Kydok56ZdTICWKe7xQbbbq/ly/.', 'http://localhost:8443/api/image/20221213024100bd208462736ee78720e79f058e2c86f0.jpg', '平平淡淡才是真', '2022-12-13 02:38:29', '2022-12-13 02:41:01', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (23, '高山流水', '$2a$10$yjKS0uNTCfoOR94EXxtYuOFJVZQcM/hmcYjLRwbA7azyQEdowaTKO', 'http://localhost:8443/api/image/20221213024736f16b1051f19ddf1fff01ed769343bcef.png', '来如风雨，去似微尘', '2022-12-13 02:41:25', '2022-12-13 02:48:07', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (24, '平安是福', '$2a$10$QwOrTyxst/cFforaXs0HP.rIU1/7tatumn1ad04RrLv12.VX2jRJy', 'http://localhost:8443/api/image/202212130251054524e3a8291bd5ce9fbf5e6143791bee.png', '但愿一切顺利！', '2022-12-13 02:48:33', '2022-12-13 02:51:05', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (25, '灰风', '$2a$10$88HUEwO/Qge3GfY66e5K8umRpuRAbaFAOwJQqfNUUBlrgc7r9rBQO', 'http://localhost:8443/api/image/20221213025629be9bb1bebef83633241c773023943f70.png', '我的梦中并没有羔羊', '2022-12-13 02:51:32', '2022-12-13 02:58:10', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (26, '小帅', '$2a$10$bywd6PXobbcTFG/rje5tA.b12.r1oO8OTCSXwgydUYEcnDhqlM6Ru', 'http://localhost:8443/api/image/20221213033706459e5b9fda216180c232361653ae8179.png', '注意看，我是小帅', '2022-12-13 03:15:15', '2022-12-13 03:37:06', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (27, '小美', '$2a$10$pmeBjG6RBB4yEYbR4.5tdOTpEqmZz.YbJ9XBQKdYrNss0l8wW2fda', 'http://localhost:8443/api/image/202212130339213753eb13d1e9b2857853cda758f33132.png', '注意看，我是小美', '2022-12-13 03:15:40', '2022-12-13 03:39:22', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (28, '大壮', '$2a$10$uYaQ.YhTwFiEa9Dr6NwOeORcS3Zk5HM4sKpASGohAglwIC69AlP4u', 'http://localhost:8443/api/image/2022121303390161babc54519eb435af7f0ba9fa951c16.png', '注意看，我是大壮', '2022-12-13 03:16:00', '2022-12-13 03:39:01', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (29, 'Trump', '$2a$10$iG3xLsLEF4BP1LLGzfS00uz9FA/22J1KaZZ3eiVSvf5f2scwmSvbK', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', 'AGAM!', '2022-12-13 03:18:22', '2022-12-13 03:18:22', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (30, 'Jefferson', '$2a$10$DH1QgwF.g9S614cFazFVpOKVqSxFXCZJ/1YRQ.gSYuuRDUexS9i/6', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '右侧锁骨骨折患者', '2022-12-13 03:18:56', '2022-12-13 03:18:56', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (31, 'Taylor', '$2a$10$MiOMDVMWvmo2EJHymPUId.zHYMdyh5rOklQ7WtYBSbPDgikWYPVZO', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '前列腺炎患者', '2022-12-13 03:19:29', '2022-12-13 03:19:29', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (32, 'Buchanan', '$2a$10$k6TQvbeBdDq/CWzgp7uqs.rlJmprXN8I9DldpVySh7A4YyF56OBXS', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '过敏性皮肤炎患者', '2022-12-13 03:19:48', '2022-12-13 03:19:48', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (33, 'Grant', '$2a$10$qD/T2x5tIpk1mc7ZEt8uMOsxEgaMsFmUQE/yxNFQYENeVBHQYkDCW', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '抑郁症患者', '2022-12-13 03:21:09', '2022-12-13 03:21:09', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (34, 'Cleveland', '$2a$10$FAw0vvjbOaZnqgprYm04DOCVdG77HSnIe8V9V9BWFWsANE9Y3iqga', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '小儿麻痹症患者', '2022-12-13 03:21:38', '2022-12-13 03:21:38', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (35, 'Mckinley', '$2a$10$NrOBK.s4WWf3WQ19iy4HceQSueLPuiDrik.eKCbQAknp7JhoqDrVy', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '难产，早产患者', '2022-12-13 03:21:57', '2022-12-13 03:21:57', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (36, 'Bush', '$2a$10$te2STGLTf/BMFSEGWgkviOm4RYBZQVJKJ9kuR1RjDuNq0AzJy0GiS', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '车祸毁容患者', '2022-12-13 03:23:12', '2022-12-13 03:23:12', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (37, 'Adams', '$2a$10$VUeZLPMvJHXyzXDfaWg0CuoZNF6G036B/nyF4M0nXGWdkY03Q452i', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', 'COVID-19患者', '2022-12-13 03:23:55', '2022-12-13 03:23:55', NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (38, 'Madison', '$2a$10$zdd6BoWxHJOFTVRVSddpherErJlhpVQptmfhgOXOPicZeGn5H1baa', 'http://localhost:8443/api/image/2022121303120145aedbc1732d85aace42fdd9d78788f9.jpg', '手足口病患者', '2022-12-13 03:24:19', '2022-12-13 03:24:19', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
