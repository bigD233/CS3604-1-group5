package com.example.whitecommunity.controller;

import com.alibaba.fastjson.JSON;
import com.example.whitecommunity.pojo.AnswerInfo;
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
                false, 0, 0, 0, 1, false);
    }

    @Test
    void getOneAnswer() throws Exception {
        this.mockMvc.perform(get("/api/answer/one?answerId=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JSON.toJSONString(example)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void getAllAnswersOfQuestion() throws Exception {
        List<AnswerInfo> expected = new ArrayList<>();
        expected.add(example);
        this.mockMvc.perform(get("/api/answer/all?questionId=3"))
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
}