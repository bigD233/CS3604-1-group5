package com.example.whitecommunity.service;

import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.dao.TagDAO;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.dao.CaseDAO;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.dao.QuestionDAO;
import com.example.whitecommunity.pojo.User;
import com.example.whitecommunity.dao.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    CaseDAO caseDAO;

    public List<CaseInfo> getAllByCaseInput(String searchInput){
        String name = searchInput;
        return caseDAO.findByNameLike(name);
    }

    @Autowired
    TagDAO tagDAO;

    public List<TagInfo>  getAllByTagInput(String searchTag){
        String name = searchTag;
        return tagDAO.findByTagNameLike(name);
    }


    @Autowired
    QuestionDAO questionDAO;
    public List<QuestionInfo> getAllByQueInput(String searchInput){
        String caption = searchInput;
        String content = searchInput;
        return questionDAO.findByCaptionLikeOrContentLike(caption,content);
    }

    @Autowired
    UserDAO userDAO;
    public User getByUserID(int userID){
        return userDAO.findById(userID);
    }

    public List<User> getByUserName(String userName){
        return userDAO.findByUsernameLike(userName);
    }
}
