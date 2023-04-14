package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.TagQuestionDAO;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.pojo.TagQuestionRelation;
import com.example.whitecommunity.service.TagService;
import com.example.whitecommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagQuestionService {
    @Autowired
    TagQuestionDAO tagQuestionDAO;
    @Autowired
    TagService tagService;
    @Autowired
    QuestionService questionService;

    // 返回指定 id 的问题的所有标签
    public List<TagInfo> getByQuestionId(int questionId) {
        List<TagQuestionRelation> relations = tagQuestionDAO.findByQuestionId(questionId);
        List<TagInfo> tags = new ArrayList<>();
        for (TagQuestionRelation rel : relations) {
            tags.add(tagService.getById(rel.getTagId()));
        }
        return tags;
    }

    // 返回具有指定各标签的所有问题
    public List<QuestionInfo> getByTagNames(String tagNames) {
        // 记录已经查询的的问题
        List<QuestionInfo> questions = new ArrayList<>();
        // 记录已经查询到的问题编号
        HashSet<Integer> questionIds = new HashSet<>();

        // 对每个标签进行查询
        for (String tagName : tagNames.split(";")) {
            TagInfo tag = tagService.getByName(tagName);
            if (tag != null) {
                List<TagQuestionRelation> relations = tagQuestionDAO.findByTagId(tag.getTagId());
                if (relations != null) {
                    for (TagQuestionRelation rel : relations) {
                        int questionId = rel.getQuestionId();
                        // 如果该问题尚未被查到，则记录下来，否则丢弃
                        if (!questionIds.contains(questionId)) {
                            questionIds.add(questionId);
                            questions.add(questionService.getById(questionId));
                        }
                    }
                }
            }
        }
        return questions;
    }

    // 添加名为 tagName 的标签 (如果需要) 和 该标签与指定 id 的问题的联系
    public void insertTagQuestion(String tagName, int questionId) {
        // 添加标签
        TagInfo tag = tagService.insertTag(tagName);
        // 添加联系
        TagQuestionRelation rel = new TagQuestionRelation(questionId, tag.getTagId());
        tagQuestionDAO.save(rel);
    }

    // 删除指定 id 的问题与其所有标签的联系
    public void deleteTagQuestionRelation(int questionId) {
        List<TagQuestionRelation> oldRelations = tagQuestionDAO.findByQuestionId(questionId);
        tagQuestionDAO.deleteAll(oldRelations);
    }

//    public TagQuestionRelation insertTagQuestion(TagInfo tag) {
//        return tagDAO.save(tag);
//    }
}
