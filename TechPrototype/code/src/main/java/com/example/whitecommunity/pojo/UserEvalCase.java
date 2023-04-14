package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "user_eval_case")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserEvalCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "case_id")
    private int caseId;
    @Column(name = "love")
    private boolean love;
    @Column(name = "watch")
    private boolean watch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public boolean isLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public UserEvalCase(int userId, int caseId, boolean love, boolean watch) {
        this.userId = userId;
        this.caseId = caseId;
        this.love = love;
        this.watch = watch;
    }

    public UserEvalCase() {
    }

}
