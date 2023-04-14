package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.UserEvalAnswer;
import com.example.whitecommunity.pojo.UserEvalQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserEvalAnswerDAO extends JpaRepository<UserEvalAnswer, Integer> {
    UserEvalAnswer findByUserIdAndAnswerId(int userId, int answerId);
    List<UserEvalAnswer> findByUserIdAndSave(int userId, boolean save);
    List<UserEvalAnswer> findByReport(boolean report);
}
