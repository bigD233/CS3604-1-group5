package com.example.whitecommunity.pojo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_punishments")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "password" })
public class UserPunishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "start_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;

    @Column(name = "end_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endTime;

    @Column(name = "valid")
    private int valid;

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
