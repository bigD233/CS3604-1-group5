package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.whitecommunity.pojo.TagInfo;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "caserepository")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class CaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private int caseId;

    @Column(name = "name")
    private String name;
    @Column(name = "intro")
    private String intro;
    @Column(name = "history")
    private String history;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "treatment")
    private String treatment;
    @Column(name = "prevention")
    private String prevention;

    @Column(name = "notes")
    private String notes;


    @Column(name = "open")
    private int open;


    @Column(name = "user_id")
    private int userId;


    @Column(name = "release_time")
    private String releaseTime;


    @Column(name = "text_type")
    private int textType;

    @Column(name = "likes")
    private int likes;
    @Column(name = "watchers")
    private int watchers;

    @Transient
    private List<TagInfo> tags;

    @Transient
    private boolean liked;
    @Transient
    private boolean watched;
//    public CaseInfo(int id, String name,String intro,String history,String diagnosis,String treatment,String prevention,String notes,String tags, String publisher,int open){
//        this.caseId=id;
//        this.name=name;
//        this.intro=intro;
//        this.history=history;
//        this.diagnosis=diagnosis;
//        this.treatment=treatment;
//        this.prevention=prevention;
//        this.notes=notes;
//        this.tags=tags;
//        this.publisher=publisher;
//        this.doctor=doctor;
//        this.open=open;
//    }

    public CaseInfo() {

    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<TagInfo> getTags() {
        return tags;
    }

    public void setTags(List<TagInfo> tags) {
        this.tags = tags;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int useId) {
        this.userId = useId;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }


    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
