package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.UserEvalCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEvalCaseDAO extends JpaRepository<UserEvalCase, Integer> {
    UserEvalCase findByUserIdAndCaseId(int userId, int caseId);

    List<UserEvalCase> findByUserIdAndWatch(int userId, boolean watch);
}
