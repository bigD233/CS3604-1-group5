package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.UserEvalCase;
import com.example.whitecommunity.pojo.UserEvalQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEvalQuestionDAO extends JpaRepository<UserEvalQuestion, Integer> {
    UserEvalQuestion findByUserIdAndQuestionId(int userId, int questionId);

    List<UserEvalQuestion> findByUserIdAndWatch(int userId, boolean watch);

}
