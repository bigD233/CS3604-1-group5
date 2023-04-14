package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.whitecommunity.pojo.QuestionInfo;

import com.alibaba.fastjson.JSON;
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


class searchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<CaseInfo> case1;
    private List<CaseInfo> case2;
    private List<QuestionInfo> que1;
    private User user1;
    private List<User> user2;
    private List<TagInfo> tags1;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new searchController()).build();

        this.case1 = new ArrayList<>();
        this.case1.add(new CaseInfo(20060,"偏执性精神障碍","精神疾病",
                "无患病历史","系统性妄想","药物+精神关照","保持好心情",
                "注意无",1,12,"2022-12-13 09:47:24",1,1,2));
        this.case1.add(new CaseInfo(20067,"中耳炎","炎症",
                "无患病历史","耳朵发炎","药物","耳朵远离水",
                "注意无",1,13,"2022-12-10 19:07:22",1,5,8));

        this.case2 = new ArrayList<>();
        this.case2.add(new CaseInfo(20060,"偏执性精神障碍","精神疾病",
                "无患病历史","系统性妄想","药物+精神关照","保持好心情",
                "注意无",1,12,"2022-12-13 09:47:24",1,1,2));


        this.que1 = new ArrayList<>();
        this.que1.add(new QuestionInfo(1, "被封控在UTJS，如何缓解焦虑？",
                "<p>封控持续几个月了，内心充满了迷茫，对未来感到悲观，怎么办？</p>",
                9, "2022-06-01 08:05:57", null,
                true, false, 0, 0, false));
        this.que1.add(new QuestionInfo(48, "如何定义心理健康？",
                "", 9, "2022-12-26 20:52:02", null,
                false, false, 0, 0, false));
        this.que1.add(new QuestionInfo(47, "喉咙干痒，头晕头痛，乏力畏寒，是感染新冠了吗？",
                "<p>我不能倒下我还有软件工程大作业呜呜呜</p>",
                9, "2022-12-23 20:49:35", null,
                false, false, 0, 0, false));

        this.user1 = new User(9,"Alice","http://localhost:8443/api/image/1.jpg",
                "nothing is true",null,null,null,"man",175,140);

        this.user2 = new ArrayList<>();
        this.user2.add(new User(9,"Alice","http://localhost:8443/api/image/1.jpg",
                "nothing is true",null,null,null,"man",175,140));
        this.user2.add(new User(11,"Alice.T.Hob","http://localhost:8443/api/image/2.jpg",
                "existing is true",null,null,null,"woman",170,110));


        this.tags1 = new ArrayList<>();
        this.tags1.add(new TagInfo(1, "心理健康"));
        this.tags1.add(new TagInfo(2, "COVID-19"));
    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void caseSearch() throws Exception {
        this.mockMvc.perform(get("/api/caseSearch/伤"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(case1)));
    }

    @Test
    void queSearch() throws Exception  {
        this.mockMvc.perform(get("/api/queSearch/焦虑"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(que1)));
    }

    @Test
    void userSearch() throws Exception  {
        this.mockMvc.perform(get("/api/userSearch/9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(user1)));
    }

    @Test
    void userSearchByName() throws Exception  {
        this.mockMvc.perform(get("/api/userSearchByName/Alice"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(user2)));
    }

    @Test
    void tagSearch() throws Exception {
        this.mockMvc.perform(get("/api/tagSearch/COVID"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(tags1)));
    }

    @Test
    void semanticSearch() throws Exception {
        this.mockMvc.perform(get("/api/semanticSearch/血"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(case2)));
    }
}