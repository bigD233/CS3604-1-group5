package com.example.whitecommunity.controller;

import com.alibaba.fastjson.JSON;
import com.example.whitecommunity.pojo.AnswerInfo;
import com.example.whitecommunity.pojo.UserEvalAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(AnswerController.class)
class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private AnswerInfo example;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AnswerController()).build();
        this.example = new AnswerInfo(3, "<p>这种事以后多着呢</p>", 10,
                "2022-06-02 21:00:01", "2022-06-09 00:00:00",
                false, 0, 0, 0, 1, false, 0, false, false);
    }

    @Test
    void getOneAnswer() throws Exception {
        this.mockMvc.perform(get("/api/answer/one?answerId=3&visitorId=9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.toJSONString(example)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void getAllAnswersOfQuestion() throws Exception {
        List<AnswerInfo> expected = new ArrayList<>();
        expected.add(example);
        this.mockMvc.perform(get("/api/answer/all?questionId=3&visitorId=9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void getAnswersOfUser() throws Exception {
        List<AnswerInfo> expected = new ArrayList<>();
        expected.add(example);
        this.mockMvc.perform(get("/api/answer/user?answererId=9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void addAnswer() throws Exception {
        this.mockMvc.perform(post("/api/answer/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.toJSONString(example)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    void editAnswer() throws Exception {
        this.mockMvc.perform(post("/api/answer/edit")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.toJSONString(example)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void deleteAnswers() throws Exception {
        this.mockMvc.perform(get("/api/answer/delete?answerId=3"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void likeAnswer() throws Exception {
        this.mockMvc.perform(get("/api/answer/like?userId=9&articleId=1&evalType=0"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/answer/like?userId=9&articleId=1&evalType=1"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/answer/like?userId=9&articleId=1&evalType=2"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/answer/like?userId=9&articleId=1&evalType=3"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void saveAnswer() throws Exception {
        this.mockMvc.perform(get("/api/answer/save?userId=9&articleId=1&evalType=0"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/answer/save?userId=9&articleId=1&evalType=1"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void reportAnswer() throws Exception {
        this.mockMvc.perform(get("/api/answer/report?userId=9&articleId=1&reason=123"))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getReportedAnswer() throws Exception {
        List<UserEvalAnswer> expected = new ArrayList<>();
        UserEvalAnswer obj = new UserEvalAnswer(25, 4, 0, false, true, "foo");
        expected.add(obj);
        this.mockMvc.perform(get("/api/answer/reported"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void getAnswersSavedByUser() throws Exception {
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        this.mockMvc.perform(get("/api/answer/saved?userId=11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void getAnswersToUser() throws Exception {
        List<List<String>> expected = new ArrayList<>();
        List<String> q = new ArrayList<>();
        q.add("1"); q.add("2022-06-02 21:00:01"); q.add("1"); q.add("3");
        expected.add(q);
        this.mockMvc.perform(get("/api/answer/answerUser?userId=11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void getAnswersToFollowed() throws Exception {
        List<List<String>> expected = new ArrayList<>();
        List<String> q = new ArrayList<>();
        q.add("2"); q.add("2022-06-02 21:00:01"); q.add("1"); q.add("3");
        expected.add(q);
        this.mockMvc.perform(get("/api/answer/answerToWatched?userId=11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void getAnswersOfFollowee() throws Exception {
        List<List<String>> expected = new ArrayList<>();
        List<String> q = new ArrayList<>();
        q.add("6"); q.add("2022-06-02 21:00:01"); q.add("1"); q.add("1"); q.add("3");
        expected.add(q);
        this.mockMvc.perform(get("/api/answer/followeeAnswers?userId=11"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }
}