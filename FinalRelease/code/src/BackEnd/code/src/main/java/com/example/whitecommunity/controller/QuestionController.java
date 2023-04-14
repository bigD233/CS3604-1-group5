package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.*;
import com.example.whitecommunity.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService = new QuestionService();

    @Autowired
    TagQuestionService tagQuestionService = new TagQuestionService();

    @Autowired
    AnswerService answerService = new AnswerService();

    @Autowired
    UserEvalQuestionService userEvalQuestionService = new UserEvalQuestionService();

    @Autowired
    UserService userService = new UserService();

        @Autowired
        ClassificationService classificationService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 返回具有指定编号的问题
     *
     * @param questionId 所查询问题的编号
     */
    @GetMapping("/api/question/{questionId}/{visitorId}")
    @CrossOrigin
    public QuestionInfo getQuestion(
            @PathVariable(value = "questionId") int questionId,
            @PathVariable(value = "visitorId") int visitorId
    ) throws Exception {
        QuestionInfo question = questionService.getById(questionId);
        List<TagInfo> tags = tagQuestionService.getByQuestionId(questionId);
        question.setTags(tags);
        boolean loved = userEvalQuestionService.getLoveByUserAndQuestionId(visitorId, questionId);
        question.setLiked(loved);
        boolean watched = userEvalQuestionService.getWatchByUserAndQuestionId(visitorId, questionId);
        question.setWatched(watched);
        return question;
    }

    /**
     * 返回具有指定编号的用户的所有问题
     *
     * @param questionerId 所查询用户的编号
     */
    @GetMapping("/api/question/user")
    @CrossOrigin
    public List<QuestionInfo> getQuestionsOfUser(@RequestParam int questionerId) throws Exception {
        List<QuestionInfo> questions = questionService.getByQuestionerId(questionerId);

        for (QuestionInfo question : questions) {
            List<TagInfo> tags = tagQuestionService.getByQuestionId(question.getQuestionId());
            question.setTags(tags);
        }

        return questions;
    }

    /**
     * 将用户发布的问题存入数据库，并作为结果返回
     *
     * @param question 提交的问题（无标签）
     * @param tags     提交的问题标签
     */
    @PostMapping("api/question/add")
    @CrossOrigin
    public QuestionInfo addQuestion(
            @RequestBody QuestionInfo question,
            @RequestParam(value = "tags") String tags
    ) throws Exception {
        question.setCaption(classificationService.classifyText(question.getCaption()));
        question.setContent(classificationService.classifyText(question.getContent()));
        QuestionInfo newQuestion = questionService.insertQuestion(question);

        for (String tag : tags.split(";")) {
            if (!Objects.equals(tag, ""))
                tagQuestionService.insertTagQuestion(tag, newQuestion.getQuestionId());
        }

        return newQuestion;
    }

    /**
     * 用用户编辑的问题更新数据库，并将其作为结果返回
     *
     * @param question 编辑后提交的问题（无标签）
     * @param tags     编辑后提交的问题标签
     */
    @PostMapping("api/question/edit")
    @CrossOrigin
    public QuestionInfo editQuestion(
            @RequestBody QuestionInfo question,
            @RequestParam(value = "tags") String tags
    ) throws Exception {
        int id = question.getQuestionId();
        QuestionInfo newQuestion = questionService.getById(id);

        // 更新标题, 内容, 编辑时间, 文本类型
        newQuestion.setCaption(classificationService.classifyText(question.getCaption()));
        newQuestion.setContent(classificationService.classifyText(question.getContent()));
        newQuestion.setEditTime(question.getEditTime());
        newQuestion.setMarkdown(question.isMarkdown());

        // 删除旧标签与该问题的联系
        tagQuestionService.deleteTagQuestionRelation(id);

        // 添加新标签与该问题的联系
        for (String tag : tags.split(";")) {
            if (!Objects.equals(tag, ""))
                tagQuestionService.insertTagQuestion(tag, id);
        }

        return questionService.updateQuestion(newQuestion);
    }

    /**
     * 删除具有指定编号的问题
     *
     * @param questionId 需要删除的问题的编号
     */
    @GetMapping("/api/question/delete")
    @CrossOrigin
    public void deleteQuestion(@RequestParam int questionId) throws Exception {
        // 删除该问题与其所有标签的联系
        tagQuestionService.deleteTagQuestionRelation(questionId);

        // 删除该问题的所有回答
        answerService.deleteByQuestionId(questionId);

        // 删除问题的表中的记录
        questionService.deleteById(questionId);
    }

    /**
     * 查询具有指定若干标签之一的问题
     *
     * @param tagNames 查询的各标签，各标签之间用分号隔开
     */
    @GetMapping("/api/question/getByTags")
    @CrossOrigin
    public List<QuestionInfo> getByTagNames(@RequestParam String tagNames) throws Exception {
        return tagQuestionService.getByTagNames(tagNames);
    }

    /**
     * 翻转问题的开放与关闭
     *
     * @param closed      更新后的 开放/关闭
     * @param question_id 待更新问题的编号
     */
    @GetMapping("/api/question/closed")
    @CrossOrigin
    public void toggleClosed(@RequestParam boolean closed, @RequestParam int question_id) {
        questionService.toggleClosed(closed, question_id);
    }

    @GetMapping("/api/question/getClosed")
    @CrossOrigin
    public int getClosed(@RequestParam int questionId) {
        return questionService.getClosed(questionId);
    }


    /**
     * 对问题 (取消) 点赞
     *
     * @param userId     操作的用户 Id
     * @param questionId 被操作的问题 Id
     * @param evalType   0: 点赞; 1: 取消点赞
     */
    @GetMapping("/api/question/like")
    @CrossOrigin
    public void likeQuestion(
            @RequestParam int userId, @RequestParam int questionId,
            @RequestParam int evalType) {
        // 修改表 "questions" 和 "user_eval_question"
        switch (evalType) {
            case 0:
                questionService.incLikes(questionId);
                userEvalQuestionService.setUserLovesQuestion(userId, questionId, true);
                break;
            case 1:
                questionService.decLikes(questionId);
                userEvalQuestionService.setUserLovesQuestion(userId, questionId, false);
                break;
        }
    }


    /**
     * (取消) 收藏回答
     *
     * @param userId     操作的用户 Id
     * @param questionId 被操作的问题 Id
     * @param evalType   0: 收藏; 1: 取消收藏
     */
    @GetMapping("/api/question/watch")
    @CrossOrigin
    public void watchQuestion(
            @RequestParam int userId, @RequestParam int questionId,
            @RequestParam int evalType) {
        // 修改表 "questions" 和 "user_eval_question"
        switch (evalType) {
            case 0:
                questionService.incWatchers(questionId);
                userEvalQuestionService.setUserWatchesQuestion(userId, questionId, true);
                break;
            case 1:
                questionService.decWatchers(questionId);
                userEvalQuestionService.setUserWatchesQuestion(userId, questionId, false);
                break;
        }
    }

    /**
     * 查询所有关注的问题
     *
     * @param userId 操作的用户 Id
     * @return 用户关注的问题的编号列表
     */
    @GetMapping("/api/question/watched")
    @CrossOrigin
    public List<Integer> getQuestionsWatchedByUser(@RequestParam int userId) throws Exception {
        List<UserEvalQuestion> entries = userEvalQuestionService.getSavedQuestions(userId);
        List<Integer> questionIds = new ArrayList<>();
        for (UserEvalQuestion entry : entries) {
            questionIds.add(entry.getQuestionId());
        }
        return questionIds;
    }

    /**
     * 查询所有所有关注的用户发布的问题
     *
     * @param userId 用户 Id
     * @return 四元组 ("5", 时间, 被关注者 Id, 问题 Id) 的列表
     */
    @GetMapping("/api/question/followeeQuestions")
    @CrossOrigin
    public List<List<String>> getQuestionsOfFollowee(@RequestParam int userId) throws Exception {
        // 查询所有关注的用户
        List<Integer> followeeIds = userService.getFolloweesNoPage(userId);

        // 对于每个用户，查询其所有问题
        List<List<String>> res = new ArrayList<>();
        for (Integer followeeId : followeeIds) {
            List<QuestionInfo> questions = questionService.getByQuestionerId(followeeId);
            String followeeIdString = Integer.toString(followeeId);
            // 对于每个问题, 添加四元组
            for (QuestionInfo question : questions) {
                List<String> quadruple = Arrays.asList(
                        "5",
                        question.getReleaseTime(),
                        followeeIdString,
                        Integer.toString(question.getQuestionId())
                );
                res.add(quadruple);
            }
        }
        return res;
    }


    @GetMapping("/api/question/recommend")
    @CrossOrigin
    public List<Integer> getRecommendQuestion(@RequestParam int userId){
        List<Integer> res = restTemplate.getForObject("http://localhost:4333/user_to_ques?user_id="+userId, List.class);
        return res;
    }
}
