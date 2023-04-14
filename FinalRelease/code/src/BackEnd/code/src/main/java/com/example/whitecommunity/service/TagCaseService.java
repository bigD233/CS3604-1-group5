package com.example.whitecommunity.service;


import com.example.whitecommunity.dao.TagCaseDAO;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.pojo.TagCaseRelation;
import com.example.whitecommunity.pojo.TagQuestionRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagCaseService {
    @Autowired
    TagCaseDAO tagCaseDAO;
    @Autowired
    TagService tagService;

    public List<TagInfo> getByCaseId(int caseId) {
        // 返回指定 id 的问题的所有标签
        List<TagCaseRelation> relations = tagCaseDAO.findByCaseId(caseId);
        List<TagInfo> tags = new ArrayList<>();
        for (TagCaseRelation rel: relations) {
            tags.add(tagService.getById(rel.getTagId()));
        }
        return tags;
    }

    public void insertTagCase(String tagName, int caseId) {
        // 添加名为 tagName 的标签 (如果需要) 和 该标签与指定 id 的问题的联系

        // 添加标签
        TagInfo tag = tagService.insertTag(tagName);
        // 添加联系
        TagCaseRelation rel = new TagCaseRelation(caseId, tag.getTagId());
        tagCaseDAO.save(rel);
    }

    public void deleteTagCaseRelation(int caseId) {
        // 删除指定 id 的问题与其所有标签的联系
        List<TagCaseRelation> oldRelations = tagCaseDAO.findByCaseId(caseId);
        tagCaseDAO.deleteAll(oldRelations);
    }
}
