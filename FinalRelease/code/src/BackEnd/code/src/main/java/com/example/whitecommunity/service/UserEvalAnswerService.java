package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.UserEvalAnswerDAO;
import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.UserEvalAnswer;
import com.example.whitecommunity.pojo.UserEvalCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEvalAnswerService {
    @Autowired
    UserEvalAnswerDAO userEvalAnswerDAO;

    public int getLoveByUserAndAnswerId(int userId, int answerId) {
        UserEvalAnswer res =
                userEvalAnswerDAO.findByUserIdAndAnswerId(userId, answerId);
        if (res == null) {
            return 0;
        }
        return res.getLove();
    }

    public void setUserLovesAnswer(int userId, int answerId, int newLove) {
        UserEvalAnswer entry =
                userEvalAnswerDAO.findByUserIdAndAnswerId(userId, answerId);
        if (entry == null) {
            entry = new UserEvalAnswer(userId, answerId, newLove, false, false, "");
        } else {
            entry.setLove(newLove);
        }
        userEvalAnswerDAO.save(entry);
    }

    public boolean getSaveByUserAndAnswerId(int userId, int answerId) {
        UserEvalAnswer res =
                userEvalAnswerDAO.findByUserIdAndAnswerId(userId, answerId);
        if (res == null) {
            return false;
        }
        return res.isSave();
    }

    public void setUserSavesAnswer(int userId, int answerId, boolean newSave) {
        UserEvalAnswer entry =
                userEvalAnswerDAO.findByUserIdAndAnswerId(userId, answerId);
        if (entry == null) {
            entry = new UserEvalAnswer(userId, answerId, 0, true, false, "");
        } else {
            entry.setSave(newSave);
        }
        userEvalAnswerDAO.save(entry);
    }

    public boolean getReportByUserAndAnswerId(int userId, int answerId) {
        UserEvalAnswer res =
                userEvalAnswerDAO.findByUserIdAndAnswerId(userId, answerId);
        if (res == null) {
            return false;
        }
        return res.isReport();
    }

    public void setUserReportsAnswer(int userId, int answerId, String reason) {
        UserEvalAnswer entry =
                userEvalAnswerDAO.findByUserIdAndAnswerId(userId, answerId);
        if (entry == null) {
            entry = new UserEvalAnswer(userId, answerId, 0, false, true, reason);
        } else {
            entry.setReport(true);
            entry.setReason(reason);
        }
        userEvalAnswerDAO.save(entry);
    }

    public List<UserEvalAnswer> getSavedAnswers(int userId) {
        return userEvalAnswerDAO.findByUserIdAndSave(userId, true);
    }

    public List<UserEvalAnswer> getReportedAnswers() {
        return userEvalAnswerDAO.findByReport(true);
    }
}
