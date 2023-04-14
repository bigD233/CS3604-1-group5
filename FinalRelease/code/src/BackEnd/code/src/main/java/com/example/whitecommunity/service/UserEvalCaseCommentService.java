package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.UserEvalCaseCommentDAO;
import com.example.whitecommunity.pojo.UserEvalCaseComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEvalCaseCommentService {
    @Autowired
    UserEvalCaseCommentDAO userEvalCaseCommentDAO;

    public int getLoveByUserAndCommentId(int userId, int commentId) {
        UserEvalCaseComment res =
                userEvalCaseCommentDAO.findByUserIdAndCommentId(userId, commentId);
        if (res == null)
            return 0;
        return res.getLove();
    }

    public void setUserLoveComment(int userId, int commentId, int newLove) {
        UserEvalCaseComment entry =
                userEvalCaseCommentDAO.findByUserIdAndCommentId(userId, commentId);
        if (entry == null) {
            entry = new UserEvalCaseComment(userId, commentId, newLove);
        }
        else {
            entry.setLove(newLove);
        }
        userEvalCaseCommentDAO.save(entry);
    }
}
