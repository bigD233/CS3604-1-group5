package com.example.whitecommunity.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.whitecommunity.dao.UserMessageSummaryDAO;
import com.example.whitecommunity.pojo.UserMessageSummary;

@Service
public class UserMessageSummaryService {
    @Autowired
    UserMessageSummaryDAO userMessageSummaryDAO;

    private UserMessageSummary getOrCreate(int from, int to) {
        UserMessageSummary userMessageSummary = userMessageSummaryDAO.findByFromAndTo(from, to);
        if (userMessageSummary == null) {
            userMessageSummary = new UserMessageSummary();
            userMessageSummary.setFrom(from);
            userMessageSummary.setTo(to);
            userMessageSummary.setUnread(0);
        }

        return userMessageSummary;
    }

    public void increaseUnread(int from, int to, String message) {
        message = message.substring(0, Integer.min(message.length(), 20));

        UserMessageSummary userMessageSummary = getOrCreate(from, to);
        UserMessageSummary userMessageSummaryReverse = getOrCreate(to, from);

        Date now = new Date();
        userMessageSummary.setUnread(userMessageSummary.getUnread() + 1);
        userMessageSummary.setUpdateTime(Timestamp.from(now.toInstant()));
        userMessageSummary.setMessage(message);
        userMessageSummaryReverse.setUpdateTime(Timestamp.from(now.toInstant()));
        userMessageSummaryReverse.setMessage(message);
        userMessageSummaryDAO.save(userMessageSummary);
        userMessageSummaryDAO.save(userMessageSummaryReverse);
    }

    public void clearUnread(int from, int to) {
        UserMessageSummary userMessageSummary = userMessageSummaryDAO.findByFromAndTo(from, to);
        if (userMessageSummary == null) {
            return;
        }
        userMessageSummary.setUnread(0);
        userMessageSummaryDAO.save(userMessageSummary);
    }

    public Page<UserMessageSummary> findByTo(int to, int pageSize, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);
        return userMessageSummaryDAO.findByToOrderByUpdateTimeDesc(to, pageRequest);
    }
}
