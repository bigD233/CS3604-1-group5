package com.example.whitecommunity.service;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserFollowingServiceTest {
    private UserFollowingService userFollowingService = new UserFollowingService();

    @Test
    public void existsTest() {
        boolean b = userFollowingService.exists(1, 2);
        assertThat(b, is(true));
    }

    @Test
    public void addTest1() {
        userFollowingService.add(1, 2);
    }

    @Test
    public void addTest2() {
        userFollowingService.add(1, 3);
    }

    @Test
    public void addTest3() {
        userFollowingService.add(1, 4);
    }

    @Test
    public void removeTest1() {
        userFollowingService.remove(1, 2);
    }

    @Test
    public void removeTest2() {
        userFollowingService.remove(1, 3);
    }

    @Test
    public void removeTest3() {
        userFollowingService.remove(1, 4);
    }

    @Test
    public void countFolloweesTest() {
        int res = userFollowingService.countFollowees(1);
        assertThat(res, is(1));
    }

    @Test
    public void countFollowersTest() {
        int res = userFollowingService.countFollowers(1);
        assertThat(res, is(0));
    }
}
