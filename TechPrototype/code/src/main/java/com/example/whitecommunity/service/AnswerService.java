package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.AnswerDAO;
import com.example.whitecommunity.pojo.AnswerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    AnswerDAO answerDAO;


    public AnswerInfo getById(int id) {
        return answerDAO.findById(id).get();
    }

    public List<AnswerInfo> getAllAnswersByQuestionId(int id) {
        return answerDAO.findByQuestionId(id);
    }

    public List<AnswerInfo> getByAnswererId(int answererId) {
        return answerDAO.findByAnswererId(answererId);
    }

    public AnswerInfo insertAnswer(AnswerInfo answer) {
        return answerDAO.save(answer);
    }

    public AnswerInfo updateAnswer(AnswerInfo answer) {
        return answerDAO.save(answer);
    }

    public void deleteById(int answerId) {
        answerDAO.deleteById(answerId);
    }

    public void deleteByQuestionId(int questionId) {
        List<AnswerInfo> answers = getAllAnswersByQuestionId(questionId);
        answerDAO.deleteAll(answers);
    }

    public void incLikes(int answerId) {
        AnswerInfo ans = getById(answerId);
        answerDAO.updateLikes(ans.getLikes() + 1, answerId);
    }

    public void decLikes(int answerId) {
        AnswerInfo ans = getById(answerId);
        answerDAO.updateLikes(ans.getLikes() - 1, answerId);
    }

    public void incDislikes(int answerId) {
        AnswerInfo ans = getById(answerId);
        answerDAO.updateDislikes(ans.getDislikes() + 1, answerId);
    }

    public void decDislikes(int answerId) {
        AnswerInfo ans = getById(answerId);
        answerDAO.updateDislikes(ans.getDislikes() - 1, answerId);
    }

    public void incSaves(int answerId) {
        AnswerInfo ans = getById(answerId);
        answerDAO.updateSaves(ans.getSaves() + 1, answerId);
    }

    public void decSaves(int answerId) {
        AnswerInfo ans = getById(answerId);
        answerDAO.updateSaves(ans.getSaves() - 1, answerId);
    }

    public List<Integer> getByTimeOrder(int pageSize,int pageNum){
        return answerDAO.getCaseByTimeOrder(pageSize,pageNum);
    }
}
