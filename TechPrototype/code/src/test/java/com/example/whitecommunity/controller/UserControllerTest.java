package com.example.whitecommunity.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;
    private int correctUserId = 1;
    private String correctUserName = "Alice";
    private String correctPassword = "3e3592d80990ff4db04858a23752d7817fd59ea82b2ba8c9d30ba0c134fbd3d8";
    private int wrongUserId = 1024;
    private String wrongUsername = "ADMIN";
    private String wrongPassword = "";
    private int newUserId = 2;
    private String newUserName = "AliceBob";
    private String newPassword = "38be58e5a685565f0eff0e53b38e0d4ad0176a14b6aa8735880788de4f9c96f8";
    private String succResult = "{ code: 0 }";
    private String errorResult = "{ code: 1 }";

    private String succResultBuilder(String data) {
        return "{ code: 0, data: " + data + "}";
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void loginTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .param("username", correctUserName)
                .param("password", correctPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void loginTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                .param("username", wrongUsername)
                .param("password", wrongPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void addTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add")
                .param("username", newUserName)
                .param("password", newPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void addTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add")
                .param("username", correctUserName)
                .param("password", correctPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void getTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get")
                .param("id", String.valueOf(correctUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "{ id: " + correctUserId + ", username: " + correctUserName + "}"), false));
    }

    @Test
    public void getTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get")
                .param("id", String.valueOf(wrongUserId))
                .param("password", wrongPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void updateSettingTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/update/setting")
                .param("id", String.valueOf(correctUserId))
                .param("sign", "sign")
                .param("avatar", "avatar"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void updateSettingTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/update/setting")
                .param("id", String.valueOf(wrongUserId))
                .param("sign", "sign")
                .param("avatar", "avatar"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void updatePasswordTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/update/password")
                .param("id", String.valueOf(correctUserId))
                .param("oldPassword", correctPassword)
                .param("newPassword", newPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void updatePasswordTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/update/password")
                .param("id", String.valueOf(correctUserId))
                .param("oldPassword", wrongPassword)
                .param("newPassword", newPassword))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void updateInfoTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/update/info")
                .param("id", String.valueOf(correctUserId))
                .param("birthday", "1")
                .param("sex", "男")
                .param("height", "180")
                .param("weight", "80"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void updateInfoTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/update/info")
                .param("id", String.valueOf(wrongUserId))
                .param("birthday", "-1")
                .param("sex", "男")
                .param("height", "180")
                .param("weight", "80"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void addFollowingTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add/following")
                .param("followee", String.valueOf(correctUserId))
                .param("follower", String.valueOf(newUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void addFollowingTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/add/following")
                .param("followee", String.valueOf(correctUserId))
                .param("follower", String.valueOf(correctUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void removeFollowingTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/remove/following")
                .param("followee", String.valueOf(correctUserId))
                .param("follower", String.valueOf(newUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void getFollowingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get/following")
                .param("followee", String.valueOf(correctUserId))
                .param("follower", String.valueOf(newUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder("true"), false));
    }

    @Test
    public void getFollowerTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get/follower")
                .param("followee", String.valueOf(correctUserId))
                .param("pageSize", "20")
                .param("pageNum", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "{list: [{id: 2}], total: 1}"), false));
    }

    @Test
    public void getFollowerTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get/follower")
                .param("followee", String.valueOf(wrongUserId))
                .param("pageSize", "20")
                .param("pageNum", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void getFolloweeTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get/followee")
                .param("follower", String.valueOf(correctUserId))
                .param("pageSize", "20")
                .param("pageNum", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "{list: [{id: 2}], total: 1}"), false));
    }

    @Test
    public void getFolloweeTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/get/followee")
                .param("follower", String.valueOf(wrongUserId))
                .param("pageSize", "20")
                .param("pageNum", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void newMessageTestSucc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/message/add")
                .param("from", String.valueOf(correctUserId))
                .param("to", String.valueOf(newUserId))
                .param("message", "hello, world"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void newMessageTestFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/message/add")
                .param("from", String.valueOf(correctUserId))
                .param("to", String.valueOf(correctUserId))
                .param("message", "hello, world"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(errorResult, false));
    }

    @Test
    public void readMessageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/message/read")
                .param("from", String.valueOf(correctUserId))
                .param("to", String.valueOf(newUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void getMessageSummaryTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/summary")
                .param("id", String.valueOf(correctUserId))
                .param("pageSize", "20")
                .param("pageNum", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "{content: [{from: 2, to: 1, message: 'hello, world'}], totalElements: 1}"), false));
    }

    @Test
    public void getMessageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/get")
                .param("id1", String.valueOf(correctUserId))
                .param("id2", String.valueOf(newUserId))
                .param("pageSize", "20")
                .param("pageNum", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "{content: [{from: 1, to: 2, message: 'hello, world'}], totalElements: 1}"), false));
    }

    @Test
    public void getRoleTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/role"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void getPunishmentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/punishment/get")
                .param("userId", String.valueOf(correctUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "{startTime: '2020-01-01 00:00:00', endTime: '2030-01-01 00:00:00'}"), false));
    }

    @Test
    public void getAllPunishmentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/punishment/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResultBuilder(
                        "[{startTime: '2020-01-01 00:00:00', endTime: '2030-01-01 00:00:00'}]"), false));
    }

    @Test
    public void setPunishmentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/punishment/set")
                .param("userId", String.valueOf(correctUserId))
                .param("days", "30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }

    @Test
    public void stopPunishmentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/punishment/stop")
                .param("userId", String.valueOf(correctUserId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(succResult, false));
    }
}
