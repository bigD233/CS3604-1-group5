package com.example.whitecommunity.dao;


import com.example.whitecommunity.pojo.CaseInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class CaseDAOTest {
    @Autowired
    private CaseDAO caseDAO;

    @Test
    void findByIdTest(){
        CaseInfo myCase=caseDAO.findById(20039);
        System.out.println(myCase);
        assertNotNull(myCase);
    }

    @Test
    void findByNameLikeTest(){
        String name="科";
        List<CaseInfo> myCaseList=caseDAO.findByNameLike(name);
        for(CaseInfo myCase:myCaseList){
            System.out.println(myCase);
            assertNotNull(myCase);
        }
    }

    @Test
    void findByNameTest(){
        String name="荨麻疹";
        CaseInfo myCase=caseDAO.findByName(name);
        System.out.println(myCase);
        assertNotNull(myCase);
    }

    @Test
    void updateInfoTest(){
        CaseInfo myCase=caseDAO.findById(20077);

        System.out.println(myCase.getName());

        int num=(int)(Math.random()*10);
        if(myCase.getName().endsWith(String.valueOf(num))){
            num=num+1;
        }
        caseDAO.updateInfo("测试一下"+num,"希望能成功",1,100,20077);
        CaseInfo updateCase=caseDAO.findById(20077);
        System.out.println(updateCase.getName());

        assertNotEquals(myCase.getName(),updateCase.getName());
    }

    @Test
    void updateContentTest(){
        CaseInfo myCase=caseDAO.findById(20077);

        System.out.println(myCase.getIntro());

        int num=(int)(Math.random()*10);
        if(myCase.getIntro().endsWith(String.valueOf(num))){
            num=num+1;
        }

        caseDAO.updateContent("单元测试真的好难"+num,"心好累","还没复习","想玩","随便测测吧",1,"2022-12-13 09:47:24",20077);
        CaseInfo updateCase=caseDAO.findById(20077);
        System.out.println(updateCase.getIntro());

        assertNotEquals(myCase.getIntro(),updateCase.getIntro());

    }

    @Test
    void findByUserIdTest(){
        List<CaseInfo> myCaseList=caseDAO.findByUserId(9);
        for(CaseInfo myCase:myCaseList){
            System.out.println(myCase);
            assertNotNull(myCase);
        }
    }
}
