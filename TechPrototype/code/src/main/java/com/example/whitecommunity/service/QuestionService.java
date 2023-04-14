package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.QuestionDAO;
import com.example.whitecommunity.pojo.AnswerCommentInfo;
import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.pojo.TagInfo;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

//    public boolean Exist(String username){
//        QuestionInfo Question1 = questionDAO.findByName(username);
//        return Question1 != null;
//    }

    public QuestionInfo getById(int id) {
        return questionDAO.findById(id).get();
    }

    public List<QuestionInfo> getByQuestionerId(int questionerId) {
        return questionDAO.findByQuestionerId(questionerId);
    }

    public QuestionInfo insertQuestion(QuestionInfo question) {
        return questionDAO.save(question);
    }

    public QuestionInfo updateQuestion(QuestionInfo question) {
        return questionDAO.save(question);
    }

    public void deleteById(int questionId) {
        questionDAO.deleteById(questionId);
    }

    public void toggleClosed(boolean closed, int question_id) {
        questionDAO.updateClosed(closed, question_id);
    }

    public void incLikes(int questionId) {
        QuestionInfo question = getById(questionId);
        questionDAO.updateLikes(question.getLikes() + 1, questionId);
    }

    public void decLikes(int questionId) {
        QuestionInfo question = getById(questionId);
        questionDAO.updateLikes(question.getLikes() - 1, questionId);
    }

    public void incWatchers(int questionId) {
        QuestionInfo question = getById(questionId);
        questionDAO.updateWatchers(question.getWatchers() + 1, questionId);
    }

    public void decWatchers(int questionId) {
        QuestionInfo question = getById(questionId);
        questionDAO.updateWatchers(question.getWatchers() - 1, questionId);
    }

    public int getClosed(int questionId) {
        if (questionDAO.findById(questionId).isPresent()) {
            if (questionDAO.getClosed(questionId))
                return 1;
            return 0;
        }
        else
            return -1;
    }
}
