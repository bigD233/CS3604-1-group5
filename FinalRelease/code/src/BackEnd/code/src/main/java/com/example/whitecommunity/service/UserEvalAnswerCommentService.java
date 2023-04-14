package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.UserEvalAnswerCommentDAO;
import com.example.whitecommunity.pojo.UserEvalAnswerComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEvalAnswerCommentService {
    @Autowired
    UserEvalAnswerCommentDAO userEvalAnswerCommentDAO;

    public int getLoveByUserAndCommentId(int userId, int commentId) {
        UserEvalAnswerComment res =
                userEvalAnswerCommentDAO.findByUserIdAndCommentId(userId, commentId);
        if (res == null)
            return 0;
        return res.getLove();
    }

    public void setUserLoveComment(int userId, int commentId, int newLove) {
        UserEvalAnswerComment entry =
                userEvalAnswerCommentDAO.findByUserIdAndCommentId(userId, commentId);
        if (entry == null) {
            entry = new UserEvalAnswerComment(userId, commentId, newLove);
        }
        else {
            entry.setLove(newLove);
        }
        userEvalAnswerCommentDAO.save(entry);
    }
}
