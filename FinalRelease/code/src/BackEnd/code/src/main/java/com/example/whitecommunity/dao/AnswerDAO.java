package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.AnswerCommentInfo;
import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AnswerDAO extends JpaRepository<AnswerInfo, Integer> {
    List<AnswerInfo> findByQuestionId(int id);

    List<AnswerInfo> findByAnswererId(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE answers SET likes = :likes WHERE answer_id = :id", nativeQuery = true)
    void updateLikes(int likes, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE answers SET dislikes = :dislikes WHERE answer_id = :id", nativeQuery = true)
    void updateDislikes(int dislikes, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE answers SET saves = :saves WHERE answer_id = :id", nativeQuery = true)
    void updateSaves(int saves, int id);

    @Query(value = "SELECT answers.answer_id FROM answers " +
            "ORDER BY answers.answer_release_time,answers.answer_id DESC " +
            "LIMIT :pageNum, :pageSize", nativeQuery = true)
    List<Integer> getCaseByTimeOrder(int pageSize, int pageNum);
}
