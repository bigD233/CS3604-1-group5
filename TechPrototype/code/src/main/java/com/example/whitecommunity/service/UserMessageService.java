package com.example.whitecommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.whitecommunity.dao.UserMessageDAO;
import com.example.whitecommunity.pojo.UserMessage;

@Service
public class UserMessageService {
    @Autowired
    UserMessageDAO userMessageDAO;

    public void add(int from, int to, String message) {
        UserMessage userMessage = new UserMessage();
        userMessage.setFrom(from);
        userMessage.setTo(to);
        userMessage.setMessage(message);
        userMessageDAO.save(userMessage);
    }

    public Page<UserMessage> get(int id1, int id2, int pageSize, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return userMessageDAO.getMessageByIds(id1, id2, pageRequest);
    }
}
