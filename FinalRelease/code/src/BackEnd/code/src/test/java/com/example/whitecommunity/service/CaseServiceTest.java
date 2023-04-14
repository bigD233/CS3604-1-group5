package com.example.whitecommunity.service;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import com.example.whitecommunity.pojo.CaseInfo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest


class CaseServiceTest {
    @Autowired
    CaseService caseService;

    private CaseInfo example;
    private CaseInfo example1;
    private CaseInfo example2;
    private int id_1;
    private int id_2;



    @BeforeEach
    void init(){
        this.id_1=20081;

        this.id_2=id_1+1;

        this.example=new CaseInfo(id_1,"就要测试","没啥好介绍的",
                "没啥历史","崩溃","一天啥也没干","赶紧写啊",
                "再摸鱼打死你啊",1,100,"2022-12-13 09:47:24",1);
        this.example1=new CaseInfo(id_1,"就不要测试","好介绍的",
                "全是历史","一点也不崩溃","干","不写了",
                "再摸鱼亲死你啊",0,100,"2022-12-13 10:47:24",0);

        this.example2=new CaseInfo(id_2,"就不要测试","好介绍的",
                "全是历史","一点也不崩溃","干","不写了",
                "再摸鱼亲死你啊",0,100,"2022-12-13 10:47:24",0);
    }


    @Test
    void addOrUpdateTest(){
        CaseInfo myCase=caseService.addOrUpdate(example);
        System.out.println(myCase);
        this.id_1=myCase.getCaseId();
        Assert.assertThat(myCase.getCaseId(),is(id_1));
    }

    @Test
    void getByIdTest(){
        CaseInfo myCase=caseService.getById(id_1);

        Assert.assertThat(myCase.getName(),is("就要测试"));
    }

    @Test
    void findByUserId(){
        List<CaseInfo> myCaseList=caseService.findByUserId(100);
        for (CaseInfo myCase:myCaseList){
            Assert.assertNotNull(myCase);
        }
    }

    @Test
    void updateInfoTest(){


        System.out.println(example.getName());
        caseService.updateInfo(example1);

        CaseInfo myCase=caseService.getById(id_1);
//        System.out.println(myCase.getName());

        Assert.assertThat(myCase.getName(),is("就不要测试"));

    }

    @Test
    void updateContentTest(){
        System.out.println(example.getDiagnosis());
        caseService.updateContent(example1);

        CaseInfo myCase=caseService.getById(id_1);
        System.out.println(myCase.getDiagnosis());

        Assert.assertThat(myCase.getDiagnosis(),is("一点也不崩溃"));
    }

    @Test
    void deleteByIdTest(){
        CaseInfo newCase=caseService.addOrUpdate(example2);
        int caseId=newCase.getCaseId();
        caseService.deleteById(caseId);
        CaseInfo myCase=caseService.getById(caseId);
        Assert.assertNull(myCase);
    }

}