package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tags")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TagInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int tagId;
    @Column(name = "tag_name")
    private String tagName;

//    @ManyToMany(mappedBy = "tags")
//    private List<QuestionInfo> questions;

    public TagInfo(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
//        this.questions = questions;
    }

    public TagInfo() {
    }

    public int getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }


    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public void setName(String name) {
        this.tagName = name;
    }
}
