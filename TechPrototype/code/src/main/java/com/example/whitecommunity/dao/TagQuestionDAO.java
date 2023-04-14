package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.TagQuestionRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagQuestionDAO extends JpaRepository<TagQuestionRelation, Integer> {
    List<TagQuestionRelation> findByQuestionId(int questionId);
    List<TagQuestionRelation> findByTagId(int tagId);
}
