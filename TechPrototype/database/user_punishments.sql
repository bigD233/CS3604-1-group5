SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_punishments
-- ----------------------------
DROP TABLE IF EXISTS `user_punishments`;
CREATE TABLE `user_punishments`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `valid` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
