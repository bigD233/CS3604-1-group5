package com.example.whitecommunity.controller;

import com.alibaba.fastjson.JSON;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.service.CaseService;
import com.example.whitecommunity.service.TagCaseService;
import com.example.whitecommunity.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(caseController.class)

class caseControllerTest {

    @Autowired
    private MockMvc mockMvc;




    private CaseInfo example;
    private CaseInfo example1;
    private CaseInfo example2;
    private int id_1;
    private int id_2;

    private List<TagInfo> tags1;
    private List<TagInfo> tags2;



    @BeforeEach
    void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new caseController()).build();

        this.id_1=20081;

        this.id_2=id_1+1;

        this.example=new CaseInfo(id_1,"就要测试","没啥好介绍的",
                "没啥历史","崩溃","一天啥也没干","赶紧写啊",
                "再摸鱼打死你啊",1,100,"2022-12-13 09:47:24",1);
        this.example1=new CaseInfo(id_1,"就不要测试","好介绍的",
                "全是历史","一点也不崩溃","干","不写了",
                "再摸鱼亲死你啊",0,100,"2022-12-13 10:47:24",0);

        this.example2=new CaseInfo(id_1,"就要测试","没啥好介绍的",
                "没啥历史","崩溃","一天啥也没干","赶紧写啊",
                "再摸鱼打死你啊",0,100,"2022-12-13 09:47:24",1);

        this.tags1 = new ArrayList<>();
        this.tags1.add(new TagInfo(1, "心理健康"));
        this.tags1.add(new TagInfo(2, "COVID-19"));

        this.tags2 = new ArrayList<>();
        this.tags2.add(new TagInfo(1, "心理健康"));
    }


    @Test
    void editOpen() throws Exception {
        this.mockMvc.perform(get("/api/case/editopen/20081"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example2)));

    }

    @Test
    void details() throws Exception {
//        Mockito.when(caseService.getById(20039)).thenReturn(example);
//        Mockito.when(tagCaseService.getByCaseId(20039)).thenReturn(tags1);
        example.setTags(tags1);
        this.mockMvc.perform(get("/api/case/20081"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example)));
    }

    @Test
    void detailsForPublish() throws Exception {
        example.setTags(tags1);
        this.mockMvc.perform(get("/api/caseForPublish/20081"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example)));
    }

    @Test
    void addOrUpdate() throws Exception {
        this.mockMvc.perform(post("/api/case/add?tags=心理健康;COVID-19")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example)));
    }

    @Test
    void modifyInfo() throws Exception {
        this.mockMvc.perform(post("/api/case/modify/info")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example)));
    }

    @Test
    void modifyContent() throws Exception {
        this.mockMvc.perform(post("/api/case/modify/content")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(example)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(example)));
    }

    @Test
    void getCasesOfUser() throws Exception {


        List<CaseInfo> expected = new ArrayList<>();
        expected.add(example);

        this.mockMvc.perform(get("/api/case/user?userId=100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JSON.toJSONString(expected)));
    }

    @Test
    void deleteQuestion() throws Exception {
        this.mockMvc.perform(get("/api/case/delete?caseId=20081"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}