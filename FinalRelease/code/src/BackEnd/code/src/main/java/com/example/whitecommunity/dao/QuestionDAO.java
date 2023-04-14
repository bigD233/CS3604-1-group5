package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionDAO extends JpaRepository<QuestionInfo, Integer> {
    //    QuestionInfo findByName(String name);
//    QuestionInfo getByName(String name);
    List<QuestionInfo> findByQuestionerId(int id);

    List<QuestionInfo> findByCaptionLikeOrContentLike(String caption, String content);

    @Modifying
    @Transactional
    @Query(value = "UPDATE questions SET closed = :closed WHERE question_id = :id", nativeQuery = true)
    void updateClosed(boolean closed, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE questions SET likes = :likes WHERE question_id = :id", nativeQuery = true)
    void updateLikes(int likes, int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE questions SET watchers = :watchers WHERE question_id = :id", nativeQuery = true)
    void updateWatchers(int watchers, int id);

    @Query(value = "SELECT closed FROM questions WHERE question_id = :id", nativeQuery = true)
    boolean getClosed(int id);
}
