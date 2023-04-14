package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.UserEvalCaseDAO;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.UserEvalCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEvalCaseService {
    @Autowired
    UserEvalCaseDAO userEvalCaseDAO;

    public boolean getLoveByUserAndCaseId(int userId, int caseId) {
        UserEvalCase res =
                userEvalCaseDAO.findByUserIdAndCaseId(userId, caseId);
        if (res == null) {
            return false;
        }
        return res.isLove();
    }

    public void setUserLovesCase(int userId, int caseId, boolean newLove) {
        UserEvalCase entry =
                userEvalCaseDAO.findByUserIdAndCaseId(userId, caseId);
        if (entry == null) {
            entry = new UserEvalCase(userId, caseId, newLove, false);
        } else {
            entry.setLove(newLove);
        }
        userEvalCaseDAO.save(entry);
    }

    public boolean getWatchByUserAndCaseId(int userId, int caseId) {
        UserEvalCase res =
                userEvalCaseDAO.findByUserIdAndCaseId(userId, caseId);
        if (res == null) {
            return false;
        }
        return res.isWatch();
    }

    public void setUserWatchesCase(int userId, int caseId, boolean newWatch) {
        UserEvalCase entry =
                userEvalCaseDAO.findByUserIdAndCaseId(userId, caseId);
        if (entry == null) {
            entry = new UserEvalCase(userId, caseId, false, true);
        } else {
            entry.setWatch(newWatch);
        }
        userEvalCaseDAO.save(entry);
    }

    public List<UserEvalCase> getSavedCases(int userId) {
        return userEvalCaseDAO.findByUserIdAndWatch(userId, true);
    }
}
