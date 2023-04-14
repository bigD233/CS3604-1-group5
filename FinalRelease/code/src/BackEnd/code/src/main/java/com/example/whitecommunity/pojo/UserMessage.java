package com.example.whitecommunity.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_messages")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message_from")
    private int from;

    @Column(name = "message_to")
    private int to;

    @Column(name = "create_time")
    @CreatedDate
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

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

    public Timestamp getCreateTime() {
        return this.createTime;
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

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
