package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "questions_tags")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TagQuestionRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "question_id")
    private int questionId;
    @Column(name = "tag_id")
    private int tagId;
//    @Transient
//    private List<TagInfo> tags;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

//    public List<TagInfo> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<TagInfo> tags) {
//        this.tags = tags;
//    }

    public TagQuestionRelation(int questionId, int tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
//        this.tags = tags;
    }

    public TagQuestionRelation() {
    }

}
