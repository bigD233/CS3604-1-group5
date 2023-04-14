package com.example.whitecommunity.service;

import com.example.whitecommunity.dao.TagDAO;
import com.example.whitecommunity.pojo.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    TagDAO tagDAO;

    public TagInfo getById(int id) {
        return tagDAO.findById(id).get();
    }

    public TagInfo getByName(String name) {
        return tagDAO.findByTagName(name);
    }


    public TagInfo insertTag(String tagName) {
        TagInfo tag = tagDAO.findByTagName(tagName);
        if (tag != null)
            return tag;

        TagInfo newTag = new TagInfo();
        newTag.setName(tagName);

        return tagDAO.save(newTag);
    }
}
