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

 Date: 13/01/2023 11:48:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions`  (
  `question_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_caption` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `question_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `questioner_id` int(11) NOT NULL,
  `question_release_time` datetime NOT NULL,
  `question_edit_time` datetime NULL DEFAULT NULL,
  `solved` bit(1) NOT NULL,
  `closed` bit(1) NOT NULL,
  `likes` int(11) NOT NULL,
  `watchers` int(11) NOT NULL,
  `markdown` bit(1) NOT NULL,
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `fk_questioner`(`questioner_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of questions
-- ----------------------------
INSERT INTO `questions` VALUES (1, '被封控在UTJS，如何缓解焦虑？', '<p>封控持续几个月了，内心充满了迷茫，对未来感到悲观，怎么办？</p>', 9, '2022-06-01 08:05:57', NULL, b'1', b'1', 1, 1, b'0');
INSERT INTO `questions` VALUES (39, '哮喘和癫痫的病状区别是什么？', '<p>花了很多钱都查不出是什么病，就是发作时和哮喘、癫痫病状差不多，哮喘和癫痫的病状区别是什么？</p>\n<p><img src=\"http://localhost:8443/api/image/20221213131333202f11ac1620ac42a4f47cfeb24cc9bb.png\" alt=\"\" width=\"607\" height=\"275\"></p>', 13, '2022-12-13 00:00:00', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (40, '吃哮喘药可以打瘦脸针吗', '<p>我一直有哮喘的毛病，最近在吃哮喘药，想咨询一下，吃哮喘药可以打瘦脸针吗？</p>', 26, '2022-12-13 03:49:01', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (41, '什么瘦脸针比较好', '<p>我脸部特别肥胖，就到医院做了检查，医生说可以注射瘦脸针来治疗，那请问什么瘦脸针比较好？</p>', 28, '2022-12-13 00:00:00', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (42, '癫痫初期有什么症状?', '', 20, '2022-12-13 04:03:13', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (47, '喉咙干痒，头晕头痛，乏力畏寒，是感染新冠了吗？', '<p>我不能倒下我还有软件工程大作业呜呜呜</p>', 11, '2022-12-23 19:58:35', NULL, b'0', b'1', 0, 0, b'0');
INSERT INTO `questions` VALUES (48, '如何定义心理健康？', '', 9, '2022-12-26 20:52:02', '2022-12-27 00:00:00', b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (50, '大家得了新冠后大概多久症状完全消失的？', '<p>我感染将近两个星期了，自认为症状属于较轻的，大概感染一星期后主要症状就消失了，但现在嗓子仍会有干痒或疼痛感，不知道正不正常。</p>', 11, '2023-01-04 20:31:20', '2023-01-04 21:40:32', b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (51, '仿生人会得电子新冠吗？', '', 25, '2023-01-11 10:55:04', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (53, '新冠有什么后遗症吗？', '<p>我是最近刚刚患上COVID-19，看了网上的许多资料，有人说这个病没什么大不了，也有人说这个病非常可怕，后遗症很严重，在这里想问一下大家到底有没有后遗症，以及是什么，谢谢。</p>', 29, '2023-01-13 09:52:29', NULL, b'0', b'0', 2, 2, b'0');
INSERT INTO `questions` VALUES (54, '宝子门们，新冠有什么症状呀', '<p>今天早上醒来发现自己有点头疼，嗓子也有点不舒服，COVID-19最近很流行，害怕自己得了新冠就想问一下大家。</p>', 25, '2023-01-13 10:02:26', NULL, b'0', b'0', 1, 2, b'0');
INSERT INTO `questions` VALUES (55, '大家对精神分裂有什么推荐的药物吗？', '<p>我是一名精神分裂患者，我每天都吃药，但是感觉自己的那些药效果愈来愈差，想问一下和我一样的人有没有什么推荐的药物。</p>', 10, '2023-01-13 10:04:53', NULL, b'0', b'0', 1, 2, b'0');
INSERT INTO `questions` VALUES (56, '车祸毁容了该怎么办？整容的话有推荐的医院吗？', '<p>最近我遭遇了车祸，现在听医生说自己的脸毁容，我也没什么勇气敢看，认为自己只能整容了，大家有什么推荐的医院吗？（ps：我在的这家医院不管这个）</p>', 25, '2023-01-13 10:10:33', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (57, '新冠到底是什么，它的传染性真有这么强吗？', '<p>最近出现了一个新冠病毒COVID-19，别人都说这个病毒可怕的很，所以这个病毒真有传闻的那么厉害吗》传染性真有这么强吗？</p>', 35, '2023-01-13 10:12:38', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (58, '我妻子难产了，医生说二保一，我该怎么吧？', '<p>我用的我妻子的号登录的这个网站，我妻子难产了，情况很严重，医生说只能二保一，家人们，该怎么办，在线等，很着急。</p>', 35, '2023-01-13 10:15:01', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (59, '早产的小孩有什么缺陷吗？', '<p>家人们，我有来了，多亏了医生和大家，我妻子和孩子都保住了，但是我孩子还是个早产儿，想问一下大家早产儿有什么缺陷吗？有的话，立马扔了，我家可不能出一个这样的人，在线等，很着急。</p>', 35, '2023-01-13 10:17:09', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (60, '患上了COVID-19，痊愈后一直咳嗽属于正常现象吗？', '<p>家人们，我不久前患了新冠，到现在以及痊愈一个月啦，但是有时候还是会咳嗽，还很剧烈，这是正常现象吗？</p>', 27, '2023-01-13 10:19:14', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (61, '患上新冠，会被其他人嘲笑吗？', '<p>COVID-19患者在痊愈后听说会被社会上的人鄙视，这是真的吗？我好害怕自己得了这种病，先问一下有没有患者告诉我一下具体情况。</p>', 27, '2023-01-13 10:22:38', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (62, '听说得新冠后不要剧烈运动可能会猝死？', '<p>宝子们，最近真是不幸，我患上了新冠，刚刚痊愈了，想去运动一下，但是听说新冠后剧烈运动会猝死，这是什么情况？</p>', 27, '2023-01-13 10:25:41', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (63, '新冠该怎么预防？', '<p>最近有一个新冠病毒COVID-19非常流行，我害怕自己会患上新冠，在这里想问一下大家有什么预防措施吗？谢谢大家啦。</p>', 30, '2023-01-13 10:26:51', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (64, '锁骨骨折有后遗症吗？', '<p>我在和同学玩的时候不小心把右锁骨给撞断了，现在在医院里，医生说需要做手术。请问做完手术之后对锁骨会有什么影响吗？</p>', 30, '2023-01-13 10:28:33', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (65, '锁骨骨折该怎么办？', '<p>我同学的锁骨断了，他现在正在操场上坐着，很难受，我应该怎么办才能把它安全的送到门口，我已经和医疗人员打了120，但是我现在想要帮他缓解一下痛苦，在线等，很着急</p>', 30, '2023-01-13 10:29:46', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (66, '新冠好可怕呀，我要抑郁啦！来人给点缓解的办法。', '<p>我是一名精神分裂病患者，最近有一个病毒COVID-19很流行，我害怕自己得上了，现在非常的抑郁，请大家能不能帮我支招，缓解一下抑郁？</p>', 10, '2023-01-13 10:32:38', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (67, '精神分裂有希望完全治疗好吗？', '<p>我是一名精神分裂症患者，我想要向大家问一下。这个病能不能完全治愈？我向心理医生询问之后，心理医生只会安慰我，没事，相信自己。我想问一些和我一样的患者，到底能不能完全治愈，有没有希望？</p>', 10, '2023-01-13 10:33:57', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (68, '老年痴呆患者应该怎么照顾呀？', '<p>我父亲患了阿尔茨海默症，也就是常说的老年痴呆。我想问一下，对于这种病人我应该怎么照顾？我想要让他能在晚年过一个好的晚年，除了吃药以外，到现在他也没有丝毫好转的迹象，也不记得我是谁，心里真的非常着急，很难受</p>', 18, '2023-01-13 10:36:16', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (69, '老年痴呆患者还能记起来吗？', '<p>我父亲是阿尔兹海默症患者，也就是患了老年痴呆，他最近对我还有亲人都一点也不熟悉，我想问一下，有没有什么办法能让他记起来我，我真的很非常想他，但他不记得我的名字，也不知道我是谁看见，我也不理我。</p>', 18, '2023-01-13 10:38:41', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (70, '老年痴呆患者该如何预防新冠？', '<p>我父亲得了阿尔兹海默症，最近有一个新冠病毒COVID-19也十分流行，我害怕新冠病毒会要了我父亲的命，想要问一下大家有什么预防措施吗？就是对于老年痴呆这类患者。</p>', 18, '2023-01-13 10:40:20', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (71, '关于老年痴呆的一点疑问？', '<p>我父亲得了阿尔兹海默症，我也害怕自己得了它，所以现在想在这里问一下大家，这个病有没有什么预防措施？我害怕我的儿子，我的亲人也会被我忘记。</p>', 18, '2023-01-13 10:41:59', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (72, '得了新冠，但是没有药该怎么办？', '<p>我患上新冠，但是因为最近已经放开了，许多人也换上了新冠，然后药房里的药也被抢完了，退烧药什么的都已经没有了，而我现在还在发烧38.5&deg;C，很难受，我去医院排队也没排上号。请问我现在该怎么办？有什么可以治疗的措施吗？</p>', 19, '2023-01-13 10:44:04', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (73, '得了痔疮该怎么办？', '<p>说一件比较启齿的事，我现在才12岁，但是已经患上了痔疮。这让我感觉到非常的难受，我想问一下我应该怎么办，有没有办法去缓解一下？我现在还很年轻，我真的很害怕这件事</p>', 19, '2023-01-13 10:45:01', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (74, '如何预防痔疮？', '<p>首先要多谢大家的解答，在上个问题中，我说我得了痔疮，然后我害怕我的其他朋友也得了痔疮，我想问一下对于这个病应该怎么预防才比较好？</p>', 19, '2023-01-13 10:46:20', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (75, '得新冠了，但是一直拉肚子，痔疮又犯了，我还有活路吗？', '<p>真是祸不单行啊，我不仅得了痔疮，至少还患上了新冠COVID-19。唉，因为新冠我最近一直在拉肚子，然后因为拉肚子时间过长，然后痔疮又犯了，现在躺在床上非常的难受，大家能有什么建议吗？</p>', 19, '2023-01-13 10:48:23', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (76, '我患上了新冠，但是无症状，这个是有影响吗？', '<p>首先很难受我患上了新冠COVID-19，但是好事是我是一个无症状患者。我听别人说无症状有时候是好的，有的是坏的，对于我这种情况，我也不知道是好是坏，所以才想问一下无症状患者和那些正常的患者到底有什么区别？</p>', 15, '2023-01-13 10:50:46', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (77, '唐氏综合症是什么？', '<p>我儿子得了唐氏综合症，这个病我之前没有了解过，但是从医生那里得知，好像不是一个比较简单的病。请问一下大家，这个病到底是什么？我的儿子还有活下去的希望吗？</p>', 15, '2023-01-13 10:52:21', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (78, '唐氏综合症能避免吗？', '<p>我儿子得了唐氏综合症，但是我还想要一个小孩儿，然后我想问一下，这个唐氏综合症有没有办法预防，医生说他是一个遗传性疾病，必须通过检测才能知道，但是为什么我的第一个孩子没有检测出来，在这里想问一下大家</p>', 15, '2023-01-13 10:53:56', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (79, '戴口罩真的能预防新冠吗？', '<p>最近新冠病毒COVID-19非常流行，许多人都已经中招了，然后专家说戴口罩保持社交距离能预防新冠。但是我看许多人戴了口罩还是没用。我想问一下，戴口罩真的有用吗？或者说作用到底有多大？</p>', 31, '2023-01-13 10:55:33', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (80, '新冠需要备一些药物吗？', '<p>最近新冠病毒非常流行，许多人都已经中招了，我看网上说这个病毒不过是个大号感冒罢了，但是也有人说这个病毒非常的凶猛，我想问一下，我需不需要备一些药物，或者说要备些什么药物？</p>', 31, '2023-01-13 10:57:06', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (81, '前列腺炎能治好吗？', '<p>可能是因为久坐或者什么原因我得了前列腺炎，我在这里想问一下大家，勤演员能治好吗。我看他好像比较严重，然后也不好意思问医生，也不敢和被人说。</p>', 31, '2023-01-13 10:58:19', NULL, b'0', b'0', 1, 0, b'0');
INSERT INTO `questions` VALUES (82, '前列腺炎该如何预防？', '<p>首先谢谢大家的建议，前列腺炎确实可以治好，但是也可能会再犯，所以在这里我想问一下大家，如果为了想要防止前列腺炎，我应该做些什么才能预防？</p>', 31, '2023-01-13 10:59:43', NULL, b'0', b'0', 2, 1, b'0');
INSERT INTO `questions` VALUES (83, '新冠会损伤神经细胞吗？', '<p>我看网上许多人说新冠病毒会侵入你的身体，然后破坏你的神经细胞，这是真的吗？如果是的话，那这可不是小感冒了，我应该要好好的对待它，进行一下预防。</p>', 36, '2023-01-13 11:01:15', NULL, b'0', b'0', 0, 1, b'0');
INSERT INTO `questions` VALUES (84, '新冠的后遗症真有这么严重吗？', '<p>我在网上看了看，有许多人说得新冠后有味觉和嗅觉丧失等后遗症，也有人说自己的身体已经彻底垮了，等什么都会，一定有说小的，有多大的。我想问一下，新冠后遗症到底包括哪些？</p>', 36, '2023-01-13 11:02:39', NULL, b'0', b'0', 1, 1, b'0');
INSERT INTO `questions` VALUES (85, '患上新冠后，我得各种后遗症的可能性？', '<p>首先在这里谢谢大家在上个问题中的回答，在这里我想问一下新冠病毒COVID-19后遗症的比例，得了之后会患那些后遗症的具体可能性大概是多少？一般人是不是患上新冠后并没有很大的后遗症？</p>', 36, '2023-01-13 11:04:25', NULL, b'0', b'0', 0, 0, b'0');
INSERT INTO `questions` VALUES (86, '整容有用吗？', '<p>我想问一下整容到底有没有用，之前因为车祸导致自己的毁容了，然后去医院整了一下容，但是整容之后也没有变多好看啊。我现在想问一下，如果我再去整容能不能变得更好看一些，反正已经整过容了。</p>', 36, '2023-01-13 11:05:48', NULL, b'0', b'0', 1, 1, b'0');

SET FOREIGN_KEY_CHECKS = 1;