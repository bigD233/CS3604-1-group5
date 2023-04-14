package com.example.whitecommunity.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.whitecommunity.pojo.UserMessageSummary;

@SpringBootTest
public class UserMessageSummaryServiceTest {
    private UserMessageSummaryService userMessageSummaryService = new UserMessageSummaryService();

    @Test
    public void increaseUnreadTest() {
        userMessageSummaryService.increaseUnread(1, 2, "hello");
    }

    @Test
    public void clearUnreadTest1() {
        userMessageSummaryService.clearUnread(1, 2);
    }

    @Test
    public void clearUnreadTest2() {
        userMessageSummaryService.clearUnread(2, 1);
    }

    @Test
    public void findByTo() {
        Page<UserMessageSummary> page = userMessageSummaryService.findByTo(1, 20, 1);
        assertNotNull(page);
    }
}
