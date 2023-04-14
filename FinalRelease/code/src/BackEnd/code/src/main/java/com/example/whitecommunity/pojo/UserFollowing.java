package com.example.whitecommunity.pojo;

import java.sql.Timestamp;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_followings")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class UserFollowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "followee")
    private int followee;

    @Column(name = "follower")
    private int follower;

    @Column(name = "create_time")
    @CreatedDate
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    @Column(name = "valid")
    private boolean valid;

    public int getId() {
        return this.id;
    }

    public int getFollowee() {
        return this.followee;
    }

    public int getFollower() {
        return this.follower;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public boolean getValid() {
        return this.valid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFollowee(int followee) {
        this.followee = followee;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
