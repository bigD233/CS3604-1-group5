package com.example.whitecommunity.service;

import static org.junit.Assert.assertThat;

import java.util.List;

import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.whitecommunity.pojo.UserMessage;

@SpringBootTest
public class UserMessageServiceTest {
    private UserMessageService userMessageService = new UserMessageService();

    @Test
    public void addTest() {
        userMessageService.add(1, 2, "hello");
    }

    @Test
    public void getTest() {
        Page<UserMessage> page = userMessageService.get(1, 2, 20, 1);
        List<UserMessage> list = page.getContent();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getMessage(), is("hello"));
    }
}
