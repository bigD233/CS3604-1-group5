package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "user_eval_answer")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserEvalAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "answer_id")
    private int answerId;
    @Column(name = "love")
    private int love;
    @Column(name = "save")
    private boolean save;
    @Column(name = "report")
    private boolean report;
    @Column(name = "report_reason")
    private String reason;

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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public UserEvalAnswer(int userId, int answerId, int love, boolean save, boolean report, String reason) {
        this.userId = userId;
        this.answerId = answerId;
        this.love = love;
        this.save = save;
        this.report = report;
        this.reason = reason;
    }

    public UserEvalAnswer() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
