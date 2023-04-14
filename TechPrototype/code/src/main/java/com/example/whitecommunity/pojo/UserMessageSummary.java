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
@Table(name = "user_message_summaries")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class UserMessageSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message_from")
    private int from;

    @Column(name = "message_to")
    private int to;

    @Column(name = "update_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    @Column(name = "unread")
    private int unread;

    @Column(name = "message")
    private String message;

    public int getId() {
        return this.id;
    }

    public int getFrom() {
        return this.from;
    }

    public int getTo() {
        return this.to;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public int getUnread() {
        return this.unread;
    }

    public String getMessage() {
        return this.message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
