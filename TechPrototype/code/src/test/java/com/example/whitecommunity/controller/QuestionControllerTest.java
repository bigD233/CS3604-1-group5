package com.example.whitecommunity.controller;

import com.alibaba.fastjson.JSON;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.pojo.TagInfo;
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
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private QuestionInfo example1;
    private List<TagInfo> tags1;
    private QuestionInfo example2;
    private List<TagInfo> tags2;
    private QuestionInfo example3;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new QuestionController()).build();

        // 构造用例对象
        this.example1 = new QuestionInfo(1, "被封控在UTJS，如何缓解焦虑？",
                "<p>封控持续几个月了，内心充满了迷茫，对未来感到悲观，怎么办？</p>",
                9, "2022-06-01 08:05:57", null,
                true, false, 0, 0, false);
        this.tags1 = new ArrayList<>();
        this.tags1.add(new TagInfo(1, "心理健康"));
        this.tags1.add(new TagInfo(2, "COVID-19"));

        this.example2 = new QuestionInfo(48, "如何定义心理健康？",
                "", 9, "2022-12-26 20:52:02", null,
                false, false, 0, 0, false);
        this.tags2 = new ArrayList<>();
        this.tags2.add(new TagInfo(1, "心理健康"));

        this.example3 = new QuestionInfo(47, "喉咙干痒，头晕头痛，乏力畏寒，是感染新冠了吗？",
                "<p>我不能倒下我还有软件工程大作业呜呜呜</p>",
                9, "2022-12-23 20:49:35", null,
                false, false, 0, 0, false);
    }

    @Test
    void getQuestion() throws Exception {
        example1.setTags(tags1);
        this.mockMvc.perform(get("/api/question/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example1)));
    }

    @Test
    void getQuestionsOfUser() throws Exception {
        example1.setTags(tags1);
        example2.setTags(tags2);
        List<QuestionInfo> expected = new ArrayList<>();
        expected.add(example1);
        expected.add(example2);
        this.mockMvc.perform(get("/api/question/user?questionerId=9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void addQuestion() throws Exception {
        this.mockMvc.perform(post("/api/question/add?tags=心理健康;COVID-19")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example1)));
    }

    @Test
    void editQuestion() throws Exception {
        this.mockMvc.perform(post("/api/question/edit?tags=心理健康")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example2)));
    }

    @Test
    void deleteQuestion() throws Exception {
        this.mockMvc.perform(get("/api/question/delete?questionId=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getByTagNames() throws Exception {
        List<QuestionInfo> expected = new ArrayList<>();
        expected.add(example1);
        expected.add(example2);
        expected.add(example3);
        this.mockMvc.perform(get("/api/question/getByTags?tagNames=心理健康;COVID-19"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }
}