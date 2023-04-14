package com.example.whitecommunity.controller;

import com.example.whitecommunity.pojo.TagInfo;
import com.example.whitecommunity.result.Result;
import com.example.whitecommunity.pojo.CaseInfo;
import com.example.whitecommunity.pojo.QuestionInfo;
import com.example.whitecommunity.pojo.User;
import com.example.whitecommunity.service.CaseService;
import com.example.whitecommunity.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;

@RestController
public class searchController {
    @Autowired
    SearchService searchService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CaseService caseService;


    @GetMapping("/api/caseSearch/{searchInput}")
    @CrossOrigin
    public List<CaseInfo> caseSearch (@PathVariable(value="searchInput") String searchInput) throws Exception {
//        searchInput =searchInput;
        System.out.print("!  ");
        searchInput='%'+searchInput+'%';
        List<CaseInfo> cases = searchService.getAllByCaseInput(searchInput);
        return cases;

    }


    @GetMapping("/api/queSearch/{searchInput}")
    @CrossOrigin
    public List<QuestionInfo> queSearch (@PathVariable(value="searchInput") String searchInput) throws Exception {
//        searchInput =searchInput;
        System.out.print("?  ");
        searchInput='%'+searchInput+'%';
        List<QuestionInfo> queInfos = searchService.getAllByQueInput(searchInput);
        return queInfos;

    }


    @GetMapping("/api/userSearch/{userID}")
    @CrossOrigin
    public User userSearch (@PathVariable(value="userID") int userID) throws Exception {
        System.out.print(".  ");
        User theUser = searchService.getByUserID(userID);
        return theUser;

    }

    @GetMapping("/api/userSearchByName/{userName}")
    @CrossOrigin
    public List<User> userSearchByName (@PathVariable(value="userName") String userName) throws Exception {
        System.out.print("~  ");
        userName='%'+userName+'%';
        List<User> theUser = searchService.getByUserName(userName);
        return theUser;

    }

    @GetMapping("/api/tagSearch/{searchInput}")
    @CrossOrigin
    public List<TagInfo> tagSearch (@PathVariable(value="searchInput") String searchInput) throws Exception {
//        searchInput =searchInput;
        System.out.print("!  ");
        searchInput='%'+searchInput+'%';
        List<TagInfo> tags = searchService.getAllByTagInput(searchInput);
        return tags;

    }

    @GetMapping("/api/semanticSearch/{searchInput}")
    @CrossOrigin
    public List<CaseInfo> semanticSearch (@PathVariable(value="searchInput") String searchInput) throws Exception {
//        searchInput =searchInput;
        List<Integer> res = restTemplate.getForObject("http://localhost:5000?query="+searchInput, List.class);
        List<CaseInfo> CaseList=new ArrayList<>();
        for (Integer caseId:res){
            CaseInfo myCase=caseService.getById(caseId);
//            System.out.println(caseService.getById(20071).getName());
            CaseList.add(myCase);

        }
        return CaseList;

    }

}
