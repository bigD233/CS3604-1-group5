package com.example.whitecommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ClassificationService {
    @Autowired
    RestTemplate restTemplate;

    public String classifyText(String text) {
        String res;
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("text", text);
        try {
            res = restTemplate.postForObject("http://localhost:8001/text", map, String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            res = text;
        }
        return res;
    }
}
