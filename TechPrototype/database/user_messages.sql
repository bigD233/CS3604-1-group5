SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_messages
-- ----------------------------
DROP TABLE IF EXISTS `user_messages`;
CREATE TABLE `user_messages`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `message_from` int NOT NULL,
  `message_to` int NOT NULL,
  `create_time` datetime NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
