package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.UserEvalQuestionDAO;
import com.example.whitecommunity.pojo.UserEvalCase;
import com.example.whitecommunity.pojo.UserEvalQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEvalQuestionService {
    @Autowired
    UserEvalQuestionDAO userEvalQuestionDAO;

    public boolean getLoveByUserAndQuestionId(int userId, int questionId) {
        UserEvalQuestion res =
                userEvalQuestionDAO.findByUserIdAndQuestionId(userId, questionId);
        if (res == null) {
            return false;
        }
        return res.isLove();
    }

    public void setUserLovesQuestion(int userId, int questionId, boolean newLove) {
        UserEvalQuestion entry =
                userEvalQuestionDAO.findByUserIdAndQuestionId(userId, questionId);
        if (entry == null) {
            entry = new UserEvalQuestion(userId, questionId, newLove, false);
        } else {
            entry.setLove(newLove);
        }
        userEvalQuestionDAO.save(entry);
    }

    public boolean getWatchByUserAndQuestionId(int userId, int questionId) {
        UserEvalQuestion res =
                userEvalQuestionDAO.findByUserIdAndQuestionId(userId, questionId);
        if (res == null) {
            return false;
        }
        return res.isWatch();
    }

    public void setUserWatchesQuestion(int userId, int questionId, boolean newWatch) {
        UserEvalQuestion entry =
                userEvalQuestionDAO.findByUserIdAndQuestionId(userId, questionId);
        if (entry == null) {
            entry = new UserEvalQuestion(userId, questionId, false, true);
        } else {
            entry.setWatch(newWatch);
        }
        userEvalQuestionDAO.save(entry);
    }

    public List<UserEvalQuestion> getSavedQuestions(int userId) {
        return userEvalQuestionDAO.findByUserIdAndWatch(userId, true);
    }
}
