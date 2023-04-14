package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.TagInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagDAO extends JpaRepository<TagInfo, Integer> {
    TagInfo findByTagName(String tagName);

    List<TagInfo> findByTagNameLike(String name);

}
