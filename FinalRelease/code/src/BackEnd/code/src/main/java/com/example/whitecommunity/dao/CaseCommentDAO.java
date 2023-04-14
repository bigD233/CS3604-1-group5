package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.CaseCommentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CaseCommentDAO extends JpaRepository<CaseCommentInfo, Integer> {
    List<CaseCommentInfo> findByParentIdAndLevelOne(int id, boolean levelOne);

    @Modifying
    @Transactional
    @Query(value = "UPDATE case_comments SET descendants = :desc WHERE comment_id = :id", nativeQuery = true)
    void updateCommentDesc(int desc, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE case_comments SET deleted = 1 WHERE comment_id = :id", nativeQuery = true)
    void deleteComment(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE case_comments SET likes = :likes WHERE comment_id = :id", nativeQuery = true)
    void updateLikes(int likes, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE case_comments SET dislikes = :dislikes WHERE comment_id = :id", nativeQuery = true)
    void updateDislikes(int dislikes, int id);
}
