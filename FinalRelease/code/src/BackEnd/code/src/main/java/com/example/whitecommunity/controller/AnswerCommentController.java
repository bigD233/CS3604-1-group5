package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.AnswerCommentInfo;
import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.service.AnswerCommentService;
import com.example.whitecommunity.service.AnswerService;
import com.example.whitecommunity.service.ClassificationService;
import com.example.whitecommunity.service.UserEvalAnswerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class AnswerCommentController {
    @Autowired
    AnswerCommentService answerCommentService = new AnswerCommentService();
    @Autowired
    UserEvalAnswerCommentService userEvalAnswerCommentService = new UserEvalAnswerCommentService();
    @Autowired
    AnswerService answerService = new AnswerService();
    @Autowired
    ClassificationService classificationService;


    @GetMapping("/api/answer/comments/get")
    @CrossOrigin
    public List<AnswerCommentInfo> details(
            @RequestParam int parentId, @RequestParam boolean levelOne,
            @RequestParam int visitorId
    ) throws Exception {
        // 查询 id 为 parentId 的回答 (若 levelOne 为真) 或评论 (若 levelOne 为假) 的所有评论
        List<AnswerCommentInfo> comments =
                answerCommentService.getByParentIdAndLevelOne(parentId, levelOne);
        for (AnswerCommentInfo answerComment : comments) {
            int love = userEvalAnswerCommentService.getLoveByUserAndCommentId(
                    visitorId, answerComment.getCommentId()
            );
            answerComment.setLiked(love);
        }
        return comments;
    }

    @GetMapping("/api/answer/comments/getOne")
    @CrossOrigin
    public AnswerCommentInfo getOneComment(@RequestParam int commentId) throws Exception {
        return answerCommentService.getById(commentId);
    }

    @PostMapping("api/answer/comments/add")
    @CrossOrigin
    public AnswerCommentInfo addComment(
            @RequestBody AnswerCommentInfo comment
    ) throws Exception {
        comment.setCommentText(classificationService.classifyText(comment.getCommentText()));
        // 增加一条新的评论
        AnswerCommentInfo newComment = answerCommentService.insertComment(comment);

        // 将其所有祖先结点后代数加 1
        while (!comment.isLevelOne()) {
            comment = answerCommentService.getById(comment.getParentId());
            answerCommentService.incCommentDesc(comment);
        }

        return newComment;
    }

    @GetMapping("/api/answer/comments/delete")
    @CrossOrigin
    public void deleteComment(@RequestParam int commentId) throws Exception {
        // 删除评论, 将 deleted 字段置为 true
        answerCommentService.deleteById(commentId);
    }

    /**
     * 对评论(取消) 点赞 / 点踩
     *
     * @param userId    操作的用户 Id
     * @param commentId 被操作的评论 Id
     * @param evalType  0: 点赞; 1: 取消点赞; 2: 点踩: 3: 取消点踩
     */
    @GetMapping("/api/answer/comments/evaluate")
    @CrossOrigin
    public void evaluateComment(
            @RequestParam int userId, @RequestParam int commentId,
            @RequestParam int evalType) {
        // 修改表 "answer_comments" 和 "user_eval_answer_comment"
        switch (evalType) {
            case 0:
                answerCommentService.incLikes(commentId);
                userEvalAnswerCommentService.setUserLoveComment(userId, commentId, 1);
                break;
            case 1:
                answerCommentService.decLikes(commentId);
                userEvalAnswerCommentService.setUserLoveComment(userId, commentId, 0);
                break;
            case 2:
                answerCommentService.incDislikes(commentId);
                userEvalAnswerCommentService.setUserLoveComment(userId, commentId, 2);
                break;
            case 3:
                answerCommentService.decDislikes(commentId);
                userEvalAnswerCommentService.setUserLoveComment(userId, commentId, 0);
                break;
        }
    }

    /**
     * 查询用户所有回答下的直接评论
     *
     * @param userId 用户 Id
     * @return 五元组 ("3", 时间, 问题 Id, 回答 Id, 评论 Id) 的列表
     */
    @GetMapping("/api/answer/comments/commentUser")
    @CrossOrigin
    public List<List<String>> getCommentsToUser(@RequestParam int userId) throws Exception {
        // 查询用户所有回答
        List<AnswerInfo> answers = answerService.getByAnswererId(userId);

        // 对每个回答查询所有评论
        List<List<String>> res = new ArrayList<>();
        for (AnswerInfo answer : answers) {
            int answerId = answer.getAnswerId();
            List<AnswerCommentInfo> commentsToAnswer =
                    answerCommentService.getByParentIdAndLevelOne(
                            answerId, true);
            // 对每个评论添加四元组
            String answerIdToString = Integer.toString(answerId);
            String questionIdToString = Integer.toString(answer.getQuestionId());
            for (AnswerCommentInfo comment : commentsToAnswer) {
                if (comment.getCommenterId() != userId && !comment.isDeleted()) {
                    List<String> quintuple = Arrays.asList(
                            "3",
                            comment.getReleaseTime(),
                            questionIdToString,
                            answerIdToString,
                            Integer.toString(comment.getCommentId())
                    );
                    res.add(quintuple);
                }
            }
        }
        return res;
    }

    /**
     * 查询用户所有对回答的评论下的回复
     *
     * @param userId 用户 Id
     * @return 四元组 ("4", 时间, 评论 Id, 回复 Id) 的列表
     */
    @GetMapping("/api/answer/comments/reply")
    @CrossOrigin
    public List<List<String>> getReply(@RequestParam int userId) throws Exception {
        // 查询用户所有评论
        List<AnswerCommentInfo> comments = answerCommentService.getByCommenterId(userId);

        // 对每条评论查询所有回复
        List<List<String>> res = new ArrayList<>();
        for (AnswerCommentInfo comment : comments) {
            if (!comment.isDeleted()) {
                int commentId = comment.getCommentId();
                List<AnswerCommentInfo> repliesToComment =
                        answerCommentService.getByParentIdAndLevelOne(
                                commentId, false);
                // 对每个评论添加四元组
                String commentIdToString = Integer.toString(commentId);
                for (AnswerCommentInfo reply : repliesToComment) {
                    if (reply.getCommenterId() != userId && !reply.isDeleted()) {
                        List<String> quadruple = Arrays.asList(
                                "4",
                                reply.getReleaseTime(),
                                commentIdToString,
                                Integer.toString(reply.getCommentId())
                        );
                        res.add(quadruple);
                    }
                }
            }
        }
        return res;
    }
}
