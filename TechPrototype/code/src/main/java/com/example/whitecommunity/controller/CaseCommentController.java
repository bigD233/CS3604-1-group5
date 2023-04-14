package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.CaseCommentInfo;
import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.service.CaseCommentService;
import com.example.whitecommunity.service.ClassificationService;
import com.example.whitecommunity.service.TagService;
import com.example.whitecommunity.service.UserEvalCaseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CaseCommentController {
    @Autowired
    CaseCommentService caseCommentService = new CaseCommentService();
    @Autowired
    UserEvalCaseCommentService userEvalCaseCommentService = new UserEvalCaseCommentService();

    @Autowired
    ClassificationService classificationService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TagService tagService;

    @GetMapping("/api/case/comments/get")
    @CrossOrigin
    public List<CaseCommentInfo> details(
            @RequestParam int parentId, @RequestParam boolean levelOne,
            @RequestParam int visitorId
    ) throws Exception {
        // 查询 id 为 parentId 的回答 (若 levelOne 为真) 或评论 (若 levelOne 为假) 的所有评论
        List<CaseCommentInfo> comments =
                caseCommentService.getByParentIdAndLevelOne(parentId, levelOne);
        for (CaseCommentInfo caseComment : comments) {
            int love = userEvalCaseCommentService.getLoveByUserAndCommentId(
                    visitorId, caseComment.getCommentId()
            );
            caseComment.setLiked(love);
        }
        return comments;
    }

    @PostMapping("api/case/comments/add")
    @CrossOrigin
    public CaseCommentInfo addComment(
            @RequestBody CaseCommentInfo comment
    ) throws Exception {
        // 增加一条新的评论
        comment.setCommentText(classificationService.classifyText(comment.getCommentText()));
        CaseCommentInfo newComment = caseCommentService.insertComment(comment);

        // 将其所有祖先结点后代数加 1
        while (!comment.isLevelOne()) {
            comment = caseCommentService.getById(comment.getParentId());
            caseCommentService.incCommentDesc(comment);
        }

        return newComment;
    }

    @GetMapping("/api/case/comments/delete")
    @CrossOrigin
    public void deleteComment(@RequestParam int commentId) throws Exception {
        // 删除评论, 将 deleted 字段置为 true
        caseCommentService.deleteById(commentId);
    }

    /**
     * 对评论(取消) 点赞 / 点踩
     *
     * @param userId    操作的用户 Id
     * @param commentId 被操作的评论 Id
     * @param evalType  0: 点赞; 1: 取消点赞; 2: 点踩: 3: 取消点踩
     */
    @GetMapping("/api/case/comments/evaluate")
    @CrossOrigin
    public void evaluateComment(
            @RequestParam int userId, @RequestParam int commentId,
            @RequestParam int evalType) {
        // 修改表 "case_comments" 和 "user_eval_case_comment"
        switch (evalType) {
            case 0:
                caseCommentService.incLikes(commentId);
                userEvalCaseCommentService.setUserLoveComment(userId, commentId, 1);
                break;
            case 1:
                caseCommentService.decLikes(commentId);
                userEvalCaseCommentService.setUserLoveComment(userId, commentId, 0);
                break;
            case 2:
                caseCommentService.incDislikes(commentId);
                userEvalCaseCommentService.setUserLoveComment(userId, commentId, 2);
                break;
            case 3:
                caseCommentService.decDislikes(commentId);
                userEvalCaseCommentService.setUserLoveComment(userId, commentId, 0);
                break;
        }
    }


    @GetMapping("/api/case/recommendtags")
    @CrossOrigin
    public List<String> getRecommendTags(@RequestParam int userId) throws Exception{
        List<Integer> res = restTemplate.getForObject("http://localhost:4333/user_to_tag?user_id="+userId, List.class);

        List<String> tagsList=new ArrayList<>();
        for (Integer re : res){
            TagInfo myTag=tagService.getById(Integer.valueOf(re));
            tagsList.add(myTag.getTagName());
//            System.out.println(tagService.getById(re).getTagName());

        }
        return tagsList;
    }
}
