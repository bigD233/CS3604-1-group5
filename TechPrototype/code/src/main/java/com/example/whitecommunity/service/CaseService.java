package com.example.whitecommunity.service;

import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.dao.CaseDAO;

import com.example.whitecommunity.pojo.CaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {
    @Autowired
    CaseDAO caseDAO;

    public boolean isExist(String username) {
        CaseInfo case1 = caseDAO.findByName(username);
        return null != case1;
    }

    public CaseInfo addOrUpdate(CaseInfo newCase) {

        return caseDAO.save(newCase);
    }



    public void deleteById(int caseId) {
        caseDAO.deleteById(caseId);
    }

    public List<CaseInfo> findByUserId(int userId) {
        List<CaseInfo> cases = caseDAO.findByUserId(userId);
        return cases;
    }

    public CaseInfo getById(int id) {
        return caseDAO.findById(id);
    }

    public void updateInfo(CaseInfo newCase) {
        int id = newCase.getCaseId();
        String name = newCase.getName();
        String notes = newCase.getNotes();
        int userId = newCase.getUserId();
        int open = newCase.getOpen();
        caseDAO.updateInfo(name, notes, open, userId, id);
    }

    public void updateContent(CaseInfo newCase) {
        int id = newCase.getCaseId();
        String intro = newCase.getIntro();
        String diagnosis = newCase.getDiagnosis();
        String history = newCase.getHistory();
        String treatment = newCase.getTreatment();
        String prevention = newCase.getPrevention();
        String releaseTime = newCase.getReleaseTime();
        int textType = newCase.getTextType();
        caseDAO.updateContent(intro, history, diagnosis, treatment, prevention, textType, releaseTime, id);
    }

    public void incLikes(int caseId) {
        CaseInfo case1 = getById(caseId);
        caseDAO.updateLikes(case1.getLikes() + 1, caseId);
    }

    public void decLikes(int caseId) {
        CaseInfo case1 = getById(caseId);
        caseDAO.updateLikes(case1.getLikes() - 1, caseId);
    }

    public void incWatchers(int caseId) {
        CaseInfo case1 = getById(caseId);
        caseDAO.updateWatchers(case1.getWatchers() + 1, caseId);
    }

    public void decWatchers(int caseId) {
        CaseInfo case1 = getById(caseId);
        caseDAO.updateWatchers(case1.getWatchers() - 1, caseId);
    }

    public List<Integer> getByTimeOrder(int pageSize,int pageNum){
        return caseDAO.getCaseByTimeOrder(pageSize,pageNum);
    }
}
