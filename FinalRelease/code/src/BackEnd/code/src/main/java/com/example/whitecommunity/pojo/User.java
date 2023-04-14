package com.example.whitecommunity.pojo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer", "password" })
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "sign")
    private String sign;

    @Column(name = "create_time")
    @CreatedDate
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getSign() {
        return this.sign;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public String getSex() {
        return this.sex;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
