package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.TagCaseRelation;
import com.example.whitecommunity.pojo.TagQuestionRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagCaseDAO extends JpaRepository<TagCaseRelation, Integer> {
    List<TagCaseRelation> findByCaseId(int caseId);
}
