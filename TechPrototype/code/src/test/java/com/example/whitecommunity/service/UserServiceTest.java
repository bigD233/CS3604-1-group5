package com.example.whitecommunity.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.whitecommunity.pojo.User;

@SpringBootTest
public class UserServiceTest {
    private int correctUserId = 1;
    private String correctUserName = "Alice";
    private String correctPassword = "3e3592d80990ff4db04858a23752d7817fd59ea82b2ba8c9d30ba0c134fbd3d8";
    private int wrongUserId = 1024;
    private String wrongUsername = "ADMIN";
    private String wrongPassword = "";
    private String newUserName = "AliceBob";
    private String newPassword = "38be58e5a685565f0eff0e53b38e0d4ad0176a14b6aa8735880788de4f9c96f8";

    private UserService userService = new UserService();

    @Test
    public void addTest1() {
        User user = userService.add(correctUserName, correctPassword);
        assertNull(user);
    }

    @Test
    public void addTest2() {
        User user = userService.add(newUserName, newPassword);
        assertThat(user.getUsername(), is(newUserName));
    }

    @Test
    public void loginTest1() {
        User user = userService.login(correctUserName, correctPassword);
        assertThat(user.getId(), is(1));
        assertThat(user.getUsername(), is(correctUserName));
    }

    @Test
    public void loginTest2() {
        User user = userService.login(wrongUsername, wrongPassword);
        assertNull(user);
    }

    @Test
    public void updatePasswordTest1() {
        User user = userService.updatePassword(correctUserId, correctPassword, newPassword);
        assertThat(user.getId(), is(correctUserId));
        assertThat(user.getUsername(), is(correctUserName));
    }

    @Test
    public void updatePasswordTest2() {
        User user = userService.updatePassword(correctUserId, wrongPassword, newPassword);
        assertNull(user);
    }

    @Test
    public void updateSettingTest1() {
        User user = userService.updateSetting(correctUserId, "hello", "avatar");
        assertThat(user.getId(), is(correctUserId));
        assertThat(user.getSign(), is("hello"));
        assertThat(user.getAvatar(), is("avatar"));
    }

    @Test
    public void updateSettingTest2() {
        User user = userService.updateSetting(wrongUserId, "hello", "avatar");
        assertNull(user);
    }

    @Test
    public void updateInfoTest1() {
        Date birthday = new Date(946684800000l);
        User user = userService.updateInfo(correctUserId, birthday, "男", 180, 80);
        assertThat(user.getId(), is(correctUserId));
        assertThat(user.getBirthday().toString(), is("2000-01-01"));
        assertThat(user.getSex(), is("男"));
        assertThat(user.getHeight(), is(180));
        assertThat(user.getWeight(), is(80));
    }

    @Test
    public void updateInfo2() {
        User user = userService.updateInfo(wrongUserId, new Date(), "", 0, 0);
        assertNull(user);
    }

    @Test
    public void getTest1() {
        User user = userService.get(correctUserId);
        assertThat(user.getId(), is(correctUserId));
    }

    @Test
    public void getTest2() {
        User user = userService.get(wrongUserId);
        assertNull(user);
    }

    @Test
    public void getFollowersTest1() {
        List<User> list = userService.getFollowers(correctUserId, 20, 1);
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getId(), is(2));
    }

    @Test
    public void getFollowersTest2() {
        List<User> list = userService.getFollowers(correctUserId, -1, 1);
        assertNull(list);
    }

    @Test
    public void getFolloweesTest1() {
        List<User> list = userService.getFollowees(correctUserId, 20, 1);
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getId(), is(2));
    }

    @Test
    public void getFolloweesTest2() {
        List<User> list = userService.getFollowees(correctUserId, -1, 1);
        assertNull(list);
    }

    @Test
    public void getFolloweesNoPageTest() {
        List<Integer> list = userService.getFolloweesNoPage(correctUserId);
        assertThat(list.size(), is(1));
        assertThat(list.get(0), is(2));
    }
}
