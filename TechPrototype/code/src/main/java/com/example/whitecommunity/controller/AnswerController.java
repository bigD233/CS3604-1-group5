package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.pojo.UserEvalAnswer;
import com.example.whitecommunity.pojo.UserEvalQuestion;
import com.example.whitecommunity.service.*;
import com.sun.org.apache.bcel.internal.generic.INEG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService = new AnswerService();
    @Autowired
    UserEvalAnswerService userEvalAnswerService = new UserEvalAnswerService();
    @Autowired
    QuestionService questionService = new QuestionService();
    @Autowired
    UserEvalQuestionService userEvalQuestionService = new UserEvalQuestionService();
    @Autowired
    UserService userService = new UserService();
    @Autowired
    ClassificationService classificationService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/api/answer/one")
    @CrossOrigin
    public AnswerInfo getOneAnswer(
            @RequestParam int answerId, @RequestParam int visitorId
    ) throws Exception {
        AnswerInfo ans = answerService.getById(answerId);
        int loved = userEvalAnswerService.getLoveByUserAndAnswerId(
                visitorId, answerId
        );
        ans.setLiked(loved);
        boolean saved = userEvalAnswerService.getSaveByUserAndAnswerId(
                visitorId, answerId
        );
        ans.setSaved(saved);
        boolean reported = userEvalAnswerService.getReportByUserAndAnswerId(
                visitorId, answerId
        );
        ans.setReported(reported);
        return ans;
    }

    @GetMapping("/api/answer/all")
    @CrossOrigin
    public List<AnswerInfo> getAllAnswersOfQuestion(
            @RequestParam int questionId, @RequestParam int visitorId
    ) throws Exception {
        List<AnswerInfo> answers = answerService.getAllAnswersByQuestionId(questionId);
        for (AnswerInfo ans : answers) {
            int answerId = ans.getAnswerId();
            int loved = userEvalAnswerService.getLoveByUserAndAnswerId(
                    visitorId, answerId
            );
            ans.setLiked(loved);
            boolean saved = userEvalAnswerService.getSaveByUserAndAnswerId(
                    visitorId, answerId
            );
            ans.setSaved(saved);
            boolean reported = userEvalAnswerService.getReportByUserAndAnswerId(
                    visitorId, answerId
            );
            ans.setReported(reported);
        }
        return answers;
    }

    @GetMapping("/api/answer/user")
    @CrossOrigin
    public List<AnswerInfo> getAnswersOfUser(@RequestParam int answererId) throws Exception {
        return answerService.getByAnswererId(answererId);
    }

    @PostMapping("api/answer/add")
    @CrossOrigin
    public AnswerInfo addAnswer(
            @RequestBody AnswerInfo answer
    ) throws Exception {
        answer.setAnswerText(classificationService.classifyText(answer.getAnswerText()));
        return answerService.insertAnswer(answer);
    }

    @PostMapping("api/answer/edit")
    @CrossOrigin
    public AnswerInfo editAnswer(@RequestBody AnswerInfo answer) throws Exception {
        int id = answer.getAnswerId();
        AnswerInfo newAnswer = answerService.getById(id);

        // 更新内容, 编辑时间, 文本类型
        newAnswer.setAnswerText(classificationService.classifyText(answer.getAnswerText()));
        newAnswer.setEditTime(answer.getEditTime());
        newAnswer.setMarkdown(answer.isMarkdown());

        return answerService.updateAnswer(newAnswer);
    }

    @GetMapping("/api/answer/delete")
    @CrossOrigin
    public void deleteAnswers(@RequestParam int answerId) throws Exception {
        answerService.deleteById(answerId);
    }

    /**
     * 对回答 (取消) 点赞 / 点踩
     *
     * @param userId    操作的用户 Id
     * @param articleId 被操作的回答 Id
     * @param evalType  0: 点赞; 1: 取消点赞; 2: 点踩: 3: 取消点踩
     */
    @GetMapping("/api/answer/like")
    @CrossOrigin
    public void likeAnswer(
            @RequestParam int userId, @RequestParam int articleId,
            @RequestParam int evalType) {
        // 修改表 "answers" 和 "user_eval_answer"
        switch (evalType) {
            case 0:
                answerService.incLikes(articleId);
                userEvalAnswerService.setUserLovesAnswer(userId, articleId, 1);
                break;
            case 1:
                answerService.decLikes(articleId);
                userEvalAnswerService.setUserLovesAnswer(userId, articleId, 0);
                break;
            case 2:
                answerService.incDislikes(articleId);
                userEvalAnswerService.setUserLovesAnswer(userId, articleId, 2);
                break;
            case 3:
                answerService.decDislikes(articleId);
                userEvalAnswerService.setUserLovesAnswer(userId, articleId, 0);
                break;
        }
    }


    /**
     * (取消) 收藏回答
     *
     * @param userId    操作的用户 Id
     * @param articleId 被操作的回答 Id
     * @param evalType  0: 收藏; 1: 取消收藏
     */
    @GetMapping("/api/answer/save")
    @CrossOrigin
    public void saveAnswer(
            @RequestParam int userId, @RequestParam int articleId,
            @RequestParam int evalType) {
        // 修改表 "answers" 和 "user_eval_answer"
        switch (evalType) {
            case 0:
                answerService.incSaves(articleId);
                userEvalAnswerService.setUserSavesAnswer(userId, articleId, true);
                break;
            case 1:
                answerService.decSaves(articleId);
                userEvalAnswerService.setUserSavesAnswer(userId, articleId, false);
                break;
        }
    }

    /**
     * 举报回答
     *
     * @param userId    举报的用户 Id
     * @param articleId 被举报的回答 Id
     */
    @GetMapping("/api/answer/report")
    @CrossOrigin
    public void reportAnswer(
            @RequestParam int userId, @RequestParam int articleId,
            @RequestParam String reason
    ) {
        // 修改表 "user_eval_answer"
        userEvalAnswerService.setUserReportsAnswer(userId, articleId, reason);
    }

    /**
     * 查询所有收藏的回答
     *
     * @param userId 操作的用户 Id
     * @return 用户收藏的回答的编号列表
     */
    @GetMapping("/api/answer/saved")
    @CrossOrigin
    public List<Integer> getAnswersSavedByUser(@RequestParam int userId) throws Exception {
        List<UserEvalAnswer> entries = userEvalAnswerService.getSavedAnswers(userId);
        List<Integer> answerIds = new ArrayList<>();
        for (UserEvalAnswer entry : entries) {
            answerIds.add(entry.getAnswerId());
        }
        return answerIds;
    }

    /**
     * 查询用户所有提问下的回答
     *
     * @param userId 用户 Id
     * @return 四元组 ("1", 时间, 回答者 Id, 回答 Id) 的列表
     */
    @GetMapping("/api/answer/answerUser")
    @CrossOrigin
    public List<List<String>> getAnswersToUser(@RequestParam int userId) throws Exception {
        // 查询用户所有提问
        List<QuestionInfo> questions = questionService.getByQuestionerId(userId);

        // 对每个提问查询所有回答
        List<List<String>> res = new ArrayList<>();
        for (QuestionInfo question : questions) {
            int questionId = question.getQuestionId();
            List<AnswerInfo> answersToQuestion =
                    answerService.getAllAnswersByQuestionId(questionId);
            // 对每个回答添加四元组
            String questionIdString = Integer.toString(questionId);
            for (AnswerInfo answer : answersToQuestion) {
                if (answer.getAnswererId() != userId) {
                    List<String> quadruple = Arrays.asList(
                            "1",
                            answer.getReleaseTime(),
                            questionIdString,
                            Integer.toString(answer.getAnswerId())
                    );
                    res.add(quadruple);
                }
            }
        }
        return res;
    }

    /**
     * 查询用户所有关注的提问下的回答
     *
     * @param userId 用户 Id
     * @return 四元组 ("2", 时间, 问题 Id, 回答 Id) 的列表
     */
    @GetMapping("/api/answer/answerToWatched")
    @CrossOrigin
    public List<List<String>> getAnswersToFollowed(@RequestParam int userId) throws Exception {
        // 查询用户所有关注的提问
        List<UserEvalQuestion> entries = userEvalQuestionService.getSavedQuestions(userId);
        List<QuestionInfo> questions = new ArrayList<>();
        for (UserEvalQuestion entry : entries) {
            int questionId = entry.getQuestionId();
            questions.add(questionService.getById(questionId));
        }

        // 对每个提问查询所有回答
        List<List<String>> res = new ArrayList<>();
        for (QuestionInfo question : questions) {
            int questionId = question.getQuestionId();
            List<AnswerInfo> answersToQuestion =
                    answerService.getAllAnswersByQuestionId(questionId);
            // 对每个回答添加四元组
            String questionIdString = Integer.toString(questionId);
            for (AnswerInfo answer : answersToQuestion) {
                if (answer.getAnswererId() != userId) {
                    List<String> quadruple = Arrays.asList(
                            "2",
                            answer.getReleaseTime(),
                            questionIdString,
                            Integer.toString(answer.getAnswerId())
                    );
                    res.add(quadruple);
                }
            }
        }
        return res;
    }

    /**
     * 查询所有关注的用户发布的回答
     *
     * @param userId 用户 Id
     * @return 五元组 ("6", 时间, 被关注者 Id, 问题 Id, 回答 Id) 的列表
     */
    @GetMapping("/api/answer/followeeAnswers")
    @CrossOrigin
    public List<List<String>> getAnswersOfFollowee(@RequestParam int userId) throws Exception {
        // 查询所有关注的用户
        List<Integer> followeeIds = userService.getFolloweesNoPage(userId);

        // 对于每个用户，查询其所有回答
        List<List<String>> res = new ArrayList<>();
        for (Integer followeeId : followeeIds) {
            List<AnswerInfo> answers = answerService.getByAnswererId(followeeId);
            String followeeIdString = Integer.toString(followeeId);
            // 对于每个回答, 添加五元组
            for (AnswerInfo answer : answers) {
                List<String> quintuple = Arrays.asList(
                        "6",
                        answer.getReleaseTime(),
                        followeeIdString,
                        Integer.toString(answer.getQuestionId()),
                        Integer.toString(answer.getAnswerId())
                );
                res.add(quintuple);
            }
        }
        return res;
    }

    /**
     * 查询所有被举报的回答
     */
    @GetMapping("/api/answer/reported")
    @CrossOrigin
    public List<UserEvalAnswer> getReportedAnswer() throws Exception {
        return userEvalAnswerService.getReportedAnswers();
    }

    /**
     * 主页按时间拉取answer（带分页）
     *
     * @param pageNum  分页数
     * @param pageSize 分页大小
     */
    @GetMapping("/api/answer/timeorder")
    @CrossOrigin
    public List<Integer> getByTimeOrder(@RequestParam int pageSize, @RequestParam int pageNum) {
        return answerService.getByTimeOrder(pageSize,pageNum);
    }


    @GetMapping("/api/answer/recommend")
    @CrossOrigin
    public List<Integer> getRecommendAnswer(@RequestParam int userId){
        return restTemplate.getForObject("http://localhost:4333/user_to_answer?user_id="+userId, List.class);
    }
}
