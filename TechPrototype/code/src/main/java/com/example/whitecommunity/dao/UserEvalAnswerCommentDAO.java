package com.example.whitecommunity.dao;

import com.example.whitecommunity.pojo.UserEvalAnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserEvalAnswerCommentDAO extends JpaRepository<UserEvalAnswerComment, Integer> {
    UserEvalAnswerComment findByUserIdAndCommentId(int userId, int answer_comment_id);
}
