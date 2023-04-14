package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.UserEvalCaseComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEvalCaseCommentDAO extends JpaRepository<UserEvalCaseComment, Integer> {
    UserEvalCaseComment findByUserIdAndCommentId(int userId, int case_comment_id);
}
