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

 Date: 13/01/2023 11:48:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answers
-- ----------------------------
DROP TABLE IF EXISTS `answers`;
CREATE TABLE `answers`  (
  `answer_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `answer_text` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `answerer_id` int(11) NOT NULL,
  `answer_release_time` datetime NOT NULL,
  `answer_edit_time` datetime NULL DEFAULT NULL,
  `is_solution` bit(1) NOT NULL,
  `saves` int(11) NOT NULL,
  `likes` int(11) NOT NULL,
  `dislikes` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `markdown` bit(1) NOT NULL,
  PRIMARY KEY (`answer_id`) USING BTREE,
  INDEX `fk_qa`(`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of answers
-- ----------------------------
INSERT INTO `answers` VALUES (1, '<p>缓解焦虑最有效的方法，首先是找一个你信赖的，可以倾听的人，告诉她你需要什么，比如你只需要她安静的倾听，或者你需要她的拥抱，如果她也能做到，可以按照你需要的来陪伴你，这样是最快可以缓解焦虑的。或者仅仅是与情绪稳定的人呆在一起就是有帮助的，也就是说当我们心慌或者焦虑的时候，我们可以寻求别人尤其是身边人的支持，这叫做&ldquo;有恃无恐&rdquo;。</p>\n<p>第二种方法，是全身放松法，首先你把注意力放在自己的额头上，感觉到额头，然后放松它，这里多停留一会，让自己的前额包括眉毛充分的放松下来，然后按照顺序依次向下移动比如到脸颊，感觉到它之后放松它，这样一直放松到脚底。多做几次一般都可以放松下来。</p>\n<p>另外我还常常采用一种方法，这种方法被称为&ldquo;和你的感受呆一会&rdquo;。</p>\n<p>这种方法的操作，就是全身心的去体验焦虑，首先尽量的让自己放松，然后去感觉焦虑，这样说也可能你会觉得抽象，那么可以把注意力放在焦虑给自己带来的身体感受上，比如身体的紧张，胸口觉得很硬等等，你放松的同时把注意力全部放在这种身体感受上，然后尝试放下自己的要去改变这种情绪的念头，单纯的和这种感觉在一起，那么这种焦虑也会很快的得到缓解。</p>\n<p>当然这样的方法可能需要训练，如果在初期，你觉得难以做到呢，可以使用深呼吸，因为呼吸是连接副交感神经的，所以你用深呼吸的方式来调节自己的神经系统，首先让自己做深呼吸，随着焦虑的减弱，然后逐渐的变成自然呼吸，把注意力放在呼吸带来的身体感受上，比如自己的腹部的起伏或者鼻孔的风感也是可以的，我自己通常放在鼻孔气息尽出的气流的感觉上，如果你觉得没有感觉，就放在腹部的起伏上也是可以的。</p>\n<p>专注的做一件事情也可以缓解焦虑，比如可以专注的去搞卫生，洗衣服（手洗），最好是做自己感兴趣的事情，也可以是运动，或者念佛号，默念一句话，念你喜欢的人的名字，或者&ldquo;把一切化为零&rdquo;，如果你对佛经感兴趣，读几遍心经也可以让自己安静下来。默念的时候，同时在自己的心里听到声音，效果会更好。</p>', 10, '2022-06-02 21:00:01', NULL, b'1', 0, 1, 0, 1, b'0');
INSERT INTO `answers` VALUES (2, '<p>没关系 精神稳定一分钟也很厉害了没精神关系稳定一分钟也很厉害了没dhdhb精神定稳一分钟也厉很害了没quan没关系精稳1分</p>', 12, '2022-06-04 00:00:00', NULL, b'0', 0, 1, 0, 1, b'0');
INSERT INTO `answers` VALUES (3, '<p>这种事以后多着呢</p>', 11, '2022-06-08 08:07:01', '2023-01-10 16:12:37', b'0', 0, 0, 1, 1, b'0');
INSERT INTO `answers` VALUES (5, '# 感谢大家的回答\n## 回答大家的感谢\n### 大家感谢的回答\n> 回家大谢的感答\n```\n#include <iostream>\nusing namespace std;\nint main() {\n    cout << \"Thank you for answering!\" << endl;\n    return 0;\n}\n```\n![DESC](http://localhost:8443/api/image/20221213085619202f11ac1620ac42a4f47cfeb24cc9bb.png)', 9, '2022-12-13 00:00:00', NULL, b'0', 0, 0, 0, 1, b'1');
INSERT INTO `answers` VALUES (15, '哮喘和癫痫的病状区别如下：\n+ 哮喘属于呼吸系统的疾病，表现为发作性的喘息、气急、胸闷或咳嗽等症状。\n+ 癫痫属于神经内科的疾病，表现为四肢抽搐、口角流沫、两目上视等症状，病人会在突然之间失去意识。\n\n不典型的癫痫一般是很容易误诊的，因此可以在发病的时候查一下脑电图，通过头颅CT或者核磁共振等来排除癫痫的诊断。', 31, '2022-12-13 03:45:40', '2022-12-13 00:00:00', b'0', 0, 1, 0, 39, b'1');
INSERT INTO `answers` VALUES (16, '<p>吃哮喘药一般是不可以打瘦脸针的，以免对病情产生不利的影响。</p>\n<p>哮喘的发病可能与过敏有关，可以通过吃哮喘药能够使病情得到缓解，但是在吃药期间如果注射瘦脸针，很有可能会使病情加重，也有可能会影响到瘦脸针效果。瘦脸针的肉毒素成分，能够快速的阻断肌肉神经的神经递质传导，会使肌肉慢慢萎缩，达到瘦脸目的。</p>\n<p>如果还有问题可以进一步咨询。</p>', 37, '2022-12-13 00:00:00', NULL, b'0', 0, 0, 0, 40, b'0');
INSERT INTO `answers` VALUES (17, '<p>瘦脸针瘦脸咬合肌是比较好，能快速达到瘦脸效果。</p>\n<p>瘦脸针的主要成分是肉毒素，是通过咬合肌注射来进行治疗，在注射药物之后能快速阻断肌肉吸收营养，会随着时间变化出现痒和肌萎缩来达到瘦脸的效果，可能需要连续注射两针到30左右。在注射瘦脸针之前还需要到医院再次线扫描，可以根据咬合肌大小来调整瘦脸针的用药量，能快速促进药效吸收和发挥。</p>\n<p>注射瘦脸针之后还需要改善饮食，最好不要吃太硬的食物，比如坚果或者是牛肉。</p>', 36, '2022-12-13 03:59:38', NULL, b'0', 0, 0, 0, 41, b'0');
INSERT INTO `answers` VALUES (18, '<p>最近癫痫患者很多，那么癫痫是什么呢，癫痫是什么意思呢出自哪里？接下来就和小编一起来了解一下吧。</p>\n<p>癫痫是一种疾病，症状比较复杂。很多患者想知道癫痫的症状有哪些，那么它的症状是什么呢？其实现在官方还没有具体公布，具体还得等进一步消息哦。</p>\n<p>好了，这就是小编给大家分享的癫痫的症状。希望大家看完这篇由小编精心整理的内容后，能对相关知识有所了解，解决你的困惑。</p>', 29, '2022-12-13 04:08:42', NULL, b'0', 0, 0, 1, 42, b'0');
INSERT INTO `answers` VALUES (19, '癫痫这种疾病发生以后，给患者也带来很大的伤害，而且这种疾病的发病范围也是很广的，可以发生在任何年龄段，所以大家有必要了解这种疾病的早期症状，有一下这些：\n\n+ 严重者的癫痫病人的临床表现为突然神志丧失，呼吸暂停、四肢抽动，双手握拳，两眼上翻或黑眼球偏向一侧，面色青紫，口吐白沫，常伴有舌咬伤和尿失禁等。\n\n+ 成年癫痫病人的临床表现是指大发作前数秒钟内病人出现的错觉、幻觉、自动症、局部肌阵挛或其它特殊感觉等。有些精神运动性发作也可出现类似大发作的前驱症状。\n\n+ 躯体感觉性，如肢体麻木、刺痛等;肌肉或肌群的颤动、发声或咀嚼、头眼转向一侧等，心悸、气短、呼吸窘迫、出汗、脸红、胃肠不适等，这也是一种癫痫病人的临床表现。\n\n+ 癫痫病人的临床表现是指在大发作前的数日或数小时，病人出现的全身不适、易激惹、烦躁不安、情绪忧郁、心境不佳、常挑剔或抱怨他人的症状，这是癫痫病人的临床表现症状的一种。\n\n+ 情绪先兆，这种癫痫先兆包括焦虑、不安、压抑、惊恐等，恐惧是最常见的一种，错觉、幻觉、看见了或感到了实际上不存在的东西和场景等。作为痫病人的临床表现的一种，患者应该注意。\n\n希望大家了解到癫痫这种疾病的初期症状以后，能够及早发现癫痫这种疾病，并且要及时到正规的医院接受治疗。', 31, '2022-12-13 04:15:31', NULL, b'0', 0, 1, 0, 42, b'1');
INSERT INTO `answers` VALUES (20, '<p>根据我的经验，应该是不能的</p>', 22, '2022-12-13 12:40:56', NULL, b'0', 0, 0, 0, 40, b'0');
INSERT INTO `answers` VALUES (21, '<p>以普遍理性而言，你应该是寄了</p>', 25, '2022-12-23 23:56:32', NULL, b'0', 1, 1, 0, 47, b'0');
INSERT INTO `answers` VALUES (22, '<p>别急</p>', 29, '2023-01-11 10:50:52', NULL, b'0', 0, 0, 1, 50, b'0');
INSERT INTO `answers` VALUES (23, '<p>来我家我帮你治</p>', 11, '2023-01-11 11:18:32', NULL, b'0', 0, 0, 0, 51, b'0');
INSERT INTO `answers` VALUES (24, '<p>为什么会味觉失灵和全身疼?一个攻击神经细胞的病毒，会没有后遗症吗，细胞免疫是要杀掉感染细胞的。<br>无非是其它神经细胞代偿了，感觉不明显，实在代偿不了才表现出来而已，所以重复感染的长新冠概率越来越高。<br>你觉得经历了一个会攻击神经细胞的病毒，会没有长期损伤吗? 你的神经细胞有多少可以再生？</p>', 19, '2023-01-13 09:57:37', NULL, b'0', 1, 2, 1, 53, b'0');
INSERT INTO `answers` VALUES (25, '<p>新冠后遗症有不少，包括：乏力或疲倦、思维障碍或不能集中精力、呼吸急促或困难、头痛、头晕、心跳加速、胸口疼痛、咳嗽、关节或肌肉疼痛、抑郁或焦虑、发热、嗅觉或味觉丧失，等等。这些症状可以持续数周、数月甚至更长时间。</p>', 10, '2023-01-13 11:08:01', NULL, b'0', 0, 2, 0, 53, b'0');
INSERT INTO `answers` VALUES (26, '<p>肯定有，但是没人说罢了。</p>', 18, '2023-01-13 11:09:25', NULL, b'0', 0, 0, 0, 53, b'0');
INSERT INTO `answers` VALUES (27, '<p>新型冠状病毒感染患者以咽干、咽喉疼痛、咳嗽、发热为主要表现，少数患者在感染新型冠状病毒后可无明显临床症状。部分患者还可伴有肌肉酸痛、嗅觉味觉减退、鼻塞、流涕等表现。</p>', 35, '2023-01-13 11:10:22', NULL, b'0', 0, 1, 1, 54, b'0');
INSERT INTO `answers` VALUES (28, '<p>？？？？还有人没得过吗？</p>', 30, '2023-01-13 11:11:15', NULL, b'0', 0, 0, 1, 54, b'0');
INSERT INTO `answers` VALUES (29, '<p>一样，我也有这个问题，在这里插个眼。</p>', 27, '2023-01-13 11:12:49', NULL, b'0', 0, 2, 0, 55, b'0');
INSERT INTO `answers` VALUES (30, '<div class=\"list-row_25EWt\">\n<div class=\"text-content_2Ev0u\">\n<div class=\"c-font-middle c-color-text list-content_2o_3q\" aria-label=\"第一代抗精神病药物：&ldquo;第一代抗精神病药物&rdquo;为&ldquo;典型&rdquo;或&ldquo;传统&rdquo;抗精神病药物。常见的有：氯丙嗪、氟哌啶醇、奋乃静、五氟利多。第一代老药通常价格较低，而且可能会导致催乳素水平升高，男性和女性患者的性欲、情...\">\n<div class=\"text_2NOr6\">第一代抗精神病药物：&ldquo;第一代抗精神病药物&rdquo;为&ldquo;典型&rdquo;或&ldquo;传统&rdquo;抗精神病药物。常见的有：氯丙嗪、氟哌啶醇、奋乃静、五氟利多。第一代老药通常价格较低，而且可能会导致催乳素水平升高，男性和女性患者的性欲。</div>\n</div>\n</div>\n</div>\n<div class=\"list-row_25EWt\">\n<div class=\"text-content_2Ev0u\">\n<div class=\"c-font-middle c-color-text list-content_2o_3q\" aria-label=\"第二代抗精神病药物：较新的被称为&ldquo;第二代&rdquo;或&ldquo;非典型&rdquo;抗精神病药物。常见的有：阿立哌唑、氯氮平、伊潘立酮、鲁拉西酮、奥氮平、帕利哌酮、喹硫平、利培酮、齐拉西酮、氨磺必利、布南色林。\">\n<div class=\"text_2NOr6\">第二代抗精神病药物：较新的被称为&ldquo;第二代&rdquo;或&ldquo;非典型&rdquo;抗精神病药物。常见的有：阿立哌唑、氯氮平、伊潘立酮、鲁拉西酮、奥氮平、帕利哌酮、喹硫平、利培酮、齐拉西酮、氨磺必利、布南色林。</div>\n</div>\n</div>\n</div>', 15, '2023-01-13 11:14:17', NULL, b'0', 0, 0, 0, 55, b'0');
INSERT INTO `answers` VALUES (31, '<p>建议去大城市里，最好是上海或者北京这些城市的大医院才有保障，小一点的医院真的不行呀！</p>', 15, '2023-01-13 11:15:39', NULL, b'0', 0, 1, 0, 56, b'0');
INSERT INTO `answers` VALUES (32, '<p>你毁容说不定是整容呢！</p>', 35, '2023-01-13 11:16:35', NULL, b'0', 0, 0, 1, 56, b'0');
INSERT INTO `answers` VALUES (33, '<p>新冠真的传染性很强，我上次只是和一个人说了会话就得了，重要的是当时我还带着医用防护口罩，不是那种一般的口罩。</p>', 15, '2023-01-13 11:18:23', NULL, b'0', 1, 1, 0, 57, b'0');
INSERT INTO `answers` VALUES (34, '<p>你看看说的是人话吗？不要闲着没事来钓鱼好吧。</p>', 36, '2023-01-13 11:19:41', NULL, b'0', 1, 1, 0, 58, b'0');
INSERT INTO `answers` VALUES (35, '<p>举报了，不用谢。</p>', 15, '2023-01-13 11:20:32', NULL, b'0', 1, 1, 0, 58, b'0');
INSERT INTO `answers` VALUES (36, '<p>怎么又是你，闲着没事钓鱼是吧？</p>', 15, '2023-01-13 11:21:47', NULL, b'0', 1, 2, 0, 59, b'0');
INSERT INTO `answers` VALUES (37, '<p>你好，是正常现象，但是如果比较严重的话，建议去医院做个CT，看一看肺部有没有毛病</p>', 19, '2023-01-13 11:23:28', NULL, b'0', 0, 1, 0, 60, b'0');
INSERT INTO `answers` VALUES (38, '<p>这边建议你去检查一下，毕竟一个月不短了。</p>', 18, '2023-01-13 11:24:11', NULL, b'0', 0, 0, 0, 60, b'0');
INSERT INTO `answers` VALUES (39, '<p>唉，来点自己的心酸经历，自从得了新冠后，我的同事都不太待见我，上司也总是给我穿小鞋，我打算过一段时间就去辞职，找一个新工作去。</p>', 18, '2023-01-13 11:25:36', NULL, b'0', 0, 1, 0, 61, b'0');
INSERT INTO `answers` VALUES (40, '<p>你好，是的，这边建议过一段时间再开始，而且一定要慢慢恢复。</p>', 36, '2023-01-13 11:27:25', NULL, b'0', 0, 0, 0, 62, b'0');
INSERT INTO `answers` VALUES (41, '<p>距离！距离！距离！口罩！口罩！口罩！</p>', 18, '2023-01-13 11:28:13', NULL, b'0', 0, 1, 0, 63, b'0');
INSERT INTO `answers` VALUES (42, '<p>锁骨骨折只是一个比较常见的骨折，基本没啥后遗症，顶多是骨头代偿性增生，摸着和另一侧不太一样宽罢了。</p>', 27, '2023-01-13 11:30:06', NULL, b'0', 0, 1, 0, 64, b'0');
INSERT INTO `answers` VALUES (43, '<p>额，非专业人员建议不要做任何帮助，你只用打120帮忙就行了。</p>', 27, '2023-01-13 11:31:33', NULL, b'0', 0, 1, 0, 65, b'0');
INSERT INTO `answers` VALUES (44, '<p>怎么说呢，这个病说大不大，说小不小，一般来说都没啥事，毕竟平常感冒发烧也有人没扛过去的。</p>', 27, '2023-01-13 11:32:38', NULL, b'0', 0, 0, 0, 66, b'0');
INSERT INTO `answers` VALUES (45, '<p>只能说保持一个好心情比什么都重要，完全治疗的可能性比较低。</p>', 15, '2023-01-13 11:33:21', NULL, b'0', 0, 1, 0, 67, b'0');
INSERT INTO `answers` VALUES (46, '<p>你只需要把他当作一个孩子来对待就好了，他没有病，只是变年轻了。</p>', 31, '2023-01-13 11:34:27', NULL, b'0', 0, 1, 0, 68, b'0');
INSERT INTO `answers` VALUES (47, '<p>能，但是希望不大，多数是间歇性的。</p>', 19, '2023-01-13 11:35:27', NULL, b'0', 0, 1, 0, 69, b'0');
INSERT INTO `answers` VALUES (48, '<p>当成孩子正常对待就行。</p>', 27, '2023-01-13 11:36:24', NULL, b'0', 0, 1, 0, 70, b'0');
INSERT INTO `answers` VALUES (49, '<p>这个怎么说呢，感觉没啥好办法。。。</p>', 15, '2023-01-13 11:36:56', NULL, b'0', 0, 1, 0, 71, b'0');
INSERT INTO `answers` VALUES (50, '<p>俗话说，十男九痔，问题真不大，去医院就行。</p>', 35, '2023-01-13 11:38:12', NULL, b'0', 0, 1, 0, 73, b'0');
INSERT INTO `answers` VALUES (51, '<p>保持一个健康的生活习惯就行。</p>', 19, '2023-01-13 11:38:39', NULL, b'0', 0, 0, 1, 74, b'0');
INSERT INTO `answers` VALUES (52, '<p>无症状患者大多没啥问题，不过也有可能是机体没触发免疫机制，建议去医院检查一下。</p>', 15, '2023-01-13 11:39:56', NULL, b'0', 0, 1, 0, 76, b'0');
INSERT INTO `answers` VALUES (53, '<p><em>唐氏综合征即21-三体综合征，又称先天愚型或Down综合征，是由染色体异常（多了一条21号染色体）而导致的疾病</em>。60%患儿在胎内早期即流产，存活者有明显的智能落后、特殊面容、生长发育障碍和多发畸形。</p>', 31, '2023-01-13 11:40:42', NULL, b'0', 0, 1, 0, 77, b'0');
INSERT INTO `answers` VALUES (54, '<p>只能出生前检测，不过一般比较容易检测。</p>', 19, '2023-01-13 11:41:23', NULL, b'0', 0, 0, 0, 78, b'0');
INSERT INTO `answers` VALUES (55, '<p>能，但是光靠它也不行。</p>', 10, '2023-01-13 11:41:54', NULL, b'0', 0, 1, 0, 79, b'0');
INSERT INTO `answers` VALUES (56, '<p>及时治疗，不一直拖着的话，不算大问题</p>', 10, '2023-01-13 11:42:47', NULL, b'0', 0, 0, 0, 81, b'0');
INSERT INTO `answers` VALUES (57, '<div class=\"list-row_25EWt\">\n<div class=\"text-content_2Ev0u\">\n<div class=\"c-font-middle c-color-text list-content_2o_3q\" aria-label=\"要坚持治疗，治疗期间不要随便换药或更换治疗方法。\">\n<div class=\"text_2NOr6\">1.</div>\n<div class=\"text_2NOr6\">要坚持治疗，治疗期间不要随便换药或更换治疗方法。</div>\n</div>\n</div>\n</div>\n<div class=\"list-row_25EWt\"><span class=\"list-num_3O7jC\">2.</span>\n<div class=\"text-content_2Ev0u\">\n<div class=\"c-font-middle c-color-text list-content_2o_3q\" aria-label=\"规律性生活。\">\n<div class=\"text_2NOr6\">规律性生活。</div>\n</div>\n</div>\n</div>\n<div class=\"list-row_25EWt\"><span class=\"list-num_3O7jC\">3.</span>\n<div class=\"text-content_2Ev0u\">\n<div class=\"c-font-middle c-color-text list-content_2o_3q\" aria-label=\"正确认识前列腺炎，保持良好的心...\">\n<div class=\"text_2NOr6\">正确认识前列腺炎，保持良好的心情</div>\n</div>\n</div>\n</div>\n<div class=\"list-row_25EWt\"><span class=\"list-num_3O7jC\">4.</span>\n<div class=\"text-content_2Ev0u\">\n<div class=\"c-font-middle c-color-text list-content_2o_3q\" aria-label=\"要多喝水、勤排尿。\">\n<div class=\"text_2NOr6\">要多喝水、勤排尿。</div>\n</div>\n</div>\n</div>', 27, '2023-01-13 11:43:39', NULL, b'0', 0, 0, 0, 82, b'0');
INSERT INTO `answers` VALUES (58, '<p>确实会，最近的许多毒株变种确实可以损害脑细胞。</p>', 27, '2023-01-13 11:44:42', NULL, b'0', 0, 0, 0, 83, b'0');
INSERT INTO `answers` VALUES (59, '<p>这个怎么说呢，和自己的身体素质啥的都有关系，不过做好防护，打好预防针，问题不大。</p>', 19, '2023-01-13 11:45:36', NULL, b'0', 0, 0, 0, 85, b'0');
INSERT INTO `answers` VALUES (60, '<p>整容有用，但是你还是慎重一点比较好。</p>', 36, '2023-01-13 11:47:07', NULL, b'0', 0, 1, 0, 86, b'0');

SET FOREIGN_KEY_CHECKS = 1;
