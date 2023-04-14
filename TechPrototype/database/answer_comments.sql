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

 Date: 13/01/2023 11:48:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer_comments
-- ----------------------------
DROP TABLE IF EXISTS `answer_comments`;
CREATE TABLE `answer_comments`  (
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
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of answer_comments
-- ----------------------------
INSERT INTO `answer_comments` VALUES (1, '答主说得很棒', 1, b'1', 26, '2022-06-02 23:24:24', 4, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (2, '谢谢答主！', 1, b'1', 23, '2022-06-03 01:24:33', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (3, '确实！', 1, b'0', 27, '2022-06-03 00:25:38', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (4, '确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实', 1, b'0', 14, '2022-06-03 11:26:44', 2, 0, 1, b'0');
INSERT INTO `answer_comments` VALUES (5, '确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实', 4, b'0', 15, '2022-06-03 12:27:49', 1, 0, 1, b'0');
INSERT INTO `answer_comments` VALUES (6, '确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实确实', 5, b'0', 17, '2022-06-04 23:28:26', 0, 0, 1, b'0');
INSERT INTO `answer_comments` VALUES (7, '正确的', 2, b'1', 20, '2022-06-03 23:29:34', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (8, '正定的', 2, b'1', 19, '2022-06-04 04:30:15', 3, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (9, '可微的', 2, b'1', 21, '2022-06-04 09:31:14', 1, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (10, '非奇异的', 8, b'0', 12, '2022-06-05 20:32:08', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (11, '几乎处处的', 9, b'0', 15, '2022-06-05 23:32:45', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (12, '预言家刀了', 3, b'1', 28, '2022-09-13 18:44:24', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (18, '厄密的', 8, b'0', 11, '2022-09-15 12:14:55', 1, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (33, '对易的', 18, b'0', 16, '2022-11-23 16:07:18', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (34, 'S3赛季前来报到', 3, b'1', 14, '2022-12-06 16:39:07', 2, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (43, 'sb', 34, b'0', 29, '2022-12-11 22:15:28', 1, 0, 0, b'1');
INSERT INTO `answer_comments` VALUES (56, '谢谢医生！', 15, b'1', 13, '2022-12-13 03:47:09', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (57, '为什么这个破网站没有流汗黄豆', 18, b'1', 12, '2022-12-13 04:10:07', 1, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (58, '好骂', 57, b'0', 11, '2022-12-13 04:11:36', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (59, '这个人素质太差！已举报！', 18, b'1', 23, '2022-12-13 04:12:53', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (60, '我知道我很急，但我真的很急', 22, b'1', 11, '2023-01-11 10:51:24', 1, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (61, 'ssw', 23, b'1', 25, '2023-01-11 11:18:59', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (62, '真别急，后面有的是你急', 60, b'0', 25, '2023-01-11 11:32:04', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (63, '你好狠的心www', 21, b'1', 11, '2023-01-11 17:40:39', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (64, '？？？他说了啥', 43, b'0', 11, '2023-01-11 22:27:20', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (65, '说得好！', 24, b'1', 10, '2023-01-13 11:07:07', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (66, '专业！', 25, b'1', 18, '2023-01-13 11:09:07', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (67, '复制粘贴罢了', 27, b'1', 30, '2023-01-13 11:10:50', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (68, '好好好，巴不得别人得是吧？', 28, b'1', 18, '2023-01-13 11:12:03', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (69, 'cy', 29, b'1', 19, '2023-01-13 11:13:11', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (70, '确实可怕呀，唉。', 33, b'1', 31, '2023-01-13 11:18:52', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (71, '支持，咱们的社区可不能被这种人污染了', 35, b'1', 36, '2023-01-13 11:21:08', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (72, '发生什么事情了？前排吃瓜', 36, b'1', 19, '2023-01-13 11:22:30', 0, 0, 1, b'0');
INSERT INTO `answer_comments` VALUES (73, '抱一抱你，宝', 39, b'1', 31, '2023-01-13 11:26:00', 1, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (74, '谢谢你啦', 73, b'0', 18, '2023-01-13 11:26:21', 0, 0, 0, b'0');
INSERT INTO `answer_comments` VALUES (75, '建议再摔另一个（bushi）', 42, b'1', 18, '2023-01-13 11:30:35', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (76, '哥，您真说到点子上了', 46, b'1', 15, '2023-01-13 11:34:54', 0, 1, 0, b'0');
INSERT INTO `answer_comments` VALUES (77, '感觉你没说啥', 51, b'1', 15, '2023-01-13 11:39:00', 0, 0, 0, b'0');

SET FOREIGN_KEY_CHECKS = 1;
