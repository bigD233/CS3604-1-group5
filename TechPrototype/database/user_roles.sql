SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (1, 9, 1);

SET FOREIGN_KEY_CHECKS = 1;
