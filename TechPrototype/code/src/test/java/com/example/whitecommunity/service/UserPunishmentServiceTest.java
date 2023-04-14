package com.example.whitecommunity.service;

import static org.junit.Assert.assertThat;

import java.util.List;

import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.whitecommunity.pojo.UserPunishment;

@SpringBootTest
public class UserPunishmentServiceTest {
    private UserPunishmentService userPunishmentService = new UserPunishmentService();

    @Test
    public void getTest() {
        UserPunishment userPunishment = userPunishmentService.get(1);
        assertThat(userPunishment.getUserId(), is(1));
    }

    @Test
    public void getAllTest() {
        List<UserPunishment> list = userPunishmentService.getAll();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getUserId(), is(1));
    }

    @Test
    public void stopTest1() {
        userPunishmentService.stop(1);
    }

    @Test
    public void stopTest2() {
        userPunishmentService.stop(2);
    }

    @Test
    public void punishTest1() {
        userPunishmentService.punish(1, 0);
    }

    @Test
    public void punishTest2() {
        userPunishmentService.punish(1, 30);
    }

    @Test
    public void punishTest3() {
        userPunishmentService.punish(2, 30);
    }

    @Test
    public void punishTest4() {
        userPunishmentService.punish(3, 30);
    }
}
