package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.AnswerCommentDAO;
import com.example.whitecommunity.pojo.AnswerCommentInfo;
import com.example.whitecommunity.pojo.AnswerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerCommentService {
    @Autowired
    AnswerCommentDAO answerCommentDAO;

    public AnswerCommentInfo getById(int id) {
        return answerCommentDAO.findById(id).get();
    }

    public List<AnswerCommentInfo> getByCommenterId(int commenterId) {
        return answerCommentDAO.findByCommenterId(commenterId);
    }

    public List<AnswerCommentInfo> getByParentIdAndLevelOne(int id, boolean levelOne) {
        return answerCommentDAO.findByParentIdAndLevelOne(id, levelOne);
    }

    public AnswerCommentInfo insertComment(AnswerCommentInfo comment) {
        return answerCommentDAO.save(comment);
    }

    public void incCommentDesc(AnswerCommentInfo comment) {
        int desc = comment.getDescendants();
        int id = comment.getCommentId();
        answerCommentDAO.updateCommentDesc(desc+1, id);
    }

    public void deleteById(int id) {
        answerCommentDAO.deleteComment(id);
    }

    public void incLikes(int commentId) {
        AnswerCommentInfo comment = getById(commentId);
        answerCommentDAO.updateLikes(comment.getLikes()+1, commentId);
    }

    public void decLikes(int commentId) {
        AnswerCommentInfo comment = getById(commentId);
        answerCommentDAO.updateLikes(comment.getLikes()-1, commentId);
    }

    public void incDislikes(int commentId) {
        AnswerCommentInfo comment = getById(commentId);
        answerCommentDAO.updateDislikes(comment.getDislikes()+1, commentId);
    }

    public void decDislikes(int commentId) {
        AnswerCommentInfo comment = getById(commentId);
        answerCommentDAO.updateDislikes(comment.getDislikes()-1, commentId);
    }
}
