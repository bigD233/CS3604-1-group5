package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.CaseCommentDAO;
import com.example.whitecommunity.pojo.CaseCommentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseCommentService {
    @Autowired
    CaseCommentDAO caseCommentDAO;

    public CaseCommentInfo getById(int id) {
        return caseCommentDAO.findById(id).get();
    }

    public List<CaseCommentInfo> getByParentIdAndLevelOne(int id, boolean levelOne) {
        return caseCommentDAO.findByParentIdAndLevelOne(id, levelOne);
    }

    public CaseCommentInfo insertComment(CaseCommentInfo comment) {
        return caseCommentDAO.save(comment);
    }

    public void incCommentDesc(CaseCommentInfo comment) {
        int desc = comment.getDescendants();
        int id = comment.getCommentId();
        caseCommentDAO.updateCommentDesc(desc + 1, id);
    }

    public void deleteById(int id) {
        caseCommentDAO.deleteComment(id);
    }

    public void incLikes(int commentId) {
        CaseCommentInfo comment = getById(commentId);
        caseCommentDAO.updateLikes(comment.getLikes() + 1, commentId);
    }

    public void decLikes(int commentId) {
        CaseCommentInfo comment = getById(commentId);
        caseCommentDAO.updateLikes(comment.getLikes() - 1, commentId);
    }

    public void incDislikes(int commentId) {
        CaseCommentInfo comment = getById(commentId);
        caseCommentDAO.updateDislikes(comment.getDislikes() + 1, commentId);
    }

    public void decDislikes(int commentId) {
        CaseCommentInfo comment = getById(commentId);
        caseCommentDAO.updateDislikes(comment.getDislikes() - 1, commentId);
    }
}
