package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.UserEvalCase;
import com.example.whitecommunity.result.Result;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.service.CaseService;
import com.example.whitecommunity.service.ClassificationService;
import com.example.whitecommunity.service.TagCaseService;
import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.service.TagService;
import com.example.whitecommunity.service.UserEvalCaseService;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.example.whitecommunity.result.Result;
import com.example.whitecommunity.utils.JwtUtils;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;

@RestController
public class caseController {
    @Autowired
    CaseService caseService;

    @Autowired
    TagService tagService;

    @Autowired
    TagCaseService tagCaseService;

    @Autowired
    UserEvalCaseService userEvalCaseService;

    @Autowired
    ClassificationService classificationService;

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/api/case/editopen/{caseId}")
    @CrossOrigin
    public CaseInfo editOpen(@PathVariable(value = "caseId") int caseId) throws Exception {
        CaseInfo myCase = caseService.getById(caseId);
        myCase.setOpen(1 - myCase.getOpen());
        caseService.addOrUpdate(myCase);
        return myCase;
    }

    @GetMapping("/api/case/{caseId}/{visitorId}")
    @CrossOrigin
    public CaseInfo details(
            @PathVariable(value = "caseId") int caseId,
            @PathVariable(value = "visitorId") int visitorId
    ) throws Exception {
        CaseInfo myCase = caseService.getById(caseId);
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (myCase.getOpen() == 0 && JwtUtils.getId(jwt) != myCase.getUserId()) {
            CaseInfo nullCase = new CaseInfo();
            nullCase.setName(myCase.getName());
            nullCase.setUserId(myCase.getUserId());
            nullCase.setCaseId(myCase.getCaseId());
            nullCase.setOpen(myCase.getOpen());
            nullCase.setReleaseTime(myCase.getReleaseTime());
            return nullCase;

        }
        List<TagInfo> tags = tagCaseService.getByCaseId(caseId);
        myCase.setTags(tags);
        boolean loved = userEvalCaseService.getLoveByUserAndCaseId(visitorId, caseId);
        myCase.setLiked(loved);
        boolean watched = userEvalCaseService.getWatchByUserAndCaseId(visitorId, caseId);
        myCase.setWatched(watched);
        return myCase;
    }

    @GetMapping("/api/caseForPublish/{caseId}")
    @CrossOrigin
    public CaseInfo detailsForPublish(@PathVariable(value = "caseId") int caseId) throws Exception {
        CaseInfo myCase = caseService.getById(caseId);
        Subject subject = SecurityUtils.getSubject();
        String jwt = (String) subject.getPrincipal();

        if (JwtUtils.getId(jwt) != myCase.getUserId()) {
            CaseInfo nullCase = new CaseInfo();
            nullCase.setName(myCase.getName());
            nullCase.setUserId(myCase.getUserId());
            nullCase.setCaseId(myCase.getCaseId());
            nullCase.setReleaseTime(myCase.getReleaseTime());
            nullCase.setNotes("unSuccess");
            return nullCase;
        }
        List<TagInfo> tags = tagCaseService.getByCaseId(caseId);
        myCase.setTags(tags);
        return myCase;
    }


    @PostMapping("/api/case/add")
    @CrossOrigin
    public CaseInfo addOrUpdate(@RequestBody CaseInfo myCase, @RequestParam(value = "tags") String tags) throws Exception {
        myCase.setName(classificationService.classifyText(myCase.getName()));
        myCase.setIntro(classificationService.classifyText(myCase.getIntro()));
        myCase.setHistory(classificationService.classifyText(myCase.getHistory()));
        myCase.setDiagnosis(classificationService.classifyText(myCase.getDiagnosis()));
        myCase.setTreatment(classificationService.classifyText(myCase.getTreatment()));
        myCase.setPrevention(classificationService.classifyText(myCase.getPrevention()));
        myCase.setNotes(classificationService.classifyText(myCase.getNotes()));

        System.out.println(myCase.getCaseId());
        CaseInfo newCase = caseService.addOrUpdate(myCase);


        // 删除旧标签与该案例的联系
        tagCaseService.deleteTagCaseRelation(newCase.getCaseId());

        for (String tag : tags.split(";")) {
            if (!Objects.equals(tag, ""))
                tagCaseService.insertTagCase(tag, myCase.getCaseId());
        }
        return newCase;
    }


    @PostMapping("/api/case/modify/info")
    @CrossOrigin
    public CaseInfo modifyInfo(@RequestBody CaseInfo myCase) throws Exception {
        myCase.setName(classificationService.classifyText(myCase.getName()));
        myCase.setNotes(classificationService.classifyText(myCase.getNotes()));

        System.out.println(myCase.getCaseId());
        caseService.updateInfo(myCase);
        return myCase;
    }

    @PostMapping("/api/case/modify/content")
    @CrossOrigin
    public CaseInfo modifyContent(@RequestBody CaseInfo myCase) throws Exception {
        myCase.setIntro(classificationService.classifyText(myCase.getIntro()));
        myCase.setHistory(classificationService.classifyText(myCase.getHistory()));
        myCase.setDiagnosis(classificationService.classifyText(myCase.getDiagnosis()));
        myCase.setTreatment(classificationService.classifyText(myCase.getTreatment()));
        myCase.setPrevention(classificationService.classifyText(myCase.getPrevention()));

        System.out.println(myCase.getCaseId());
        caseService.updateContent(myCase);
        return myCase;
    }


    @GetMapping("/api/case/user")
    @CrossOrigin
    public List<CaseInfo> getCasesOfUser(@RequestParam int userId) throws Exception {
        return caseService.findByUserId(userId);
    }

    /**
     * 查询所有收藏的案例
     *
     * @param userId 操作的用户 Id
     * @return 用户收藏的案例的编号列表
     */
    @GetMapping("/api/case/saved")
    @CrossOrigin
    public List<Integer> getCasesSavedByUser(@RequestParam int userId) throws Exception {
        List<UserEvalCase> entries = userEvalCaseService.getSavedCases(userId);
        List<Integer> caseIds = new ArrayList<>();
        for (UserEvalCase entry : entries) {
            caseIds.add(entry.getCaseId());
        }
        return caseIds;
    }


    @GetMapping("/api/case/delete")
    @CrossOrigin
    public void deleteCase(@RequestParam int caseId) throws Exception {
        // 删除该案例与其所有标签的联系
        tagCaseService.deleteTagCaseRelation(caseId);


        // 删除案例的表中的记录
        caseService.deleteById(caseId);
    }

    /**
     * 对案例 (取消) 点赞
     *
     * @param userId    操作的用户 Id
     * @param articleId 被操作的案例 Id
     * @param evalType  0: 点赞; 1: 取消点赞
     */
    @GetMapping("/api/case/like")
    @CrossOrigin
    public void likeCase(
            @RequestParam int userId, @RequestParam int articleId,
            @RequestParam int evalType) {
        // 修改表 "cases" 和 "user_eval_case"
        switch (evalType) {
            case 0:
                caseService.incLikes(articleId);
                userEvalCaseService.setUserLovesCase(userId, articleId, true);
                break;
            case 1:
                caseService.decLikes(articleId);
                userEvalCaseService.setUserLovesCase(userId, articleId, false);
                break;
        }
    }


    /**
     * (取消) 收藏回答
     *
     * @param userId    操作的用户 Id
     * @param articleId 被操作的案例 Id
     * @param evalType  0: 收藏; 1: 取消收藏
     */
    @GetMapping("/api/case/save")
    @CrossOrigin
    public void watchCase(
            @RequestParam int userId, @RequestParam int articleId,
            @RequestParam int evalType) {
        // 修改表 "cases" 和 "user_eval_case"
        switch (evalType) {
            case 0:
                caseService.incWatchers(articleId);
                userEvalCaseService.setUserWatchesCase(userId, articleId, true);
                break;
            case 1:
                caseService.decWatchers(articleId);
                userEvalCaseService.setUserWatchesCase(userId, articleId, false);
                break;
        }
    }

    /**
     * 主页按时间拉取case（带分页）
     *
     * @param pageNum  分页数
     * @param pageSize 分页大小
     */

    @GetMapping("/api/case/timeorder")
    @CrossOrigin
    public List<Integer> getByTimeOrder(@RequestParam int pageSize, @RequestParam int pageNum) {
        return caseService.getByTimeOrder(pageSize,pageNum);
    }

    @GetMapping("/api/case/recommend")
    @CrossOrigin
    public List<Integer> getRecommendCase(@RequestParam int userId) throws Exception{
        List<Integer> res = restTemplate.getForObject("http://localhost:4333/user_to_case?user_id=" + userId,List.class);

        List<Integer> caseIdList=new ArrayList<>();
        for (Integer  re : res){
            caseIdList.add(Integer.valueOf(re));
        }
        return caseIdList;
    }


}
