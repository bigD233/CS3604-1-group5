package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;




    @Entity
    @Table(name = "cases_tags")
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    public class TagCaseRelation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;
        @Column(name = "case_id")
        private int caseId;
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

        public int getCaseId() {
            return caseId;
        }

        public void setCaseId(int caseId) {
            this.caseId = caseId;
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

        public TagCaseRelation(int caseId, int tagId) {
            this.caseId = caseId;
            this.tagId = tagId;
//        this.tags = tags;
        }

        public TagCaseRelation() {
        }

    }

