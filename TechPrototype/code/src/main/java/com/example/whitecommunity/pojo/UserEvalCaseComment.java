package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "user_eval_case_comment")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserEvalCaseComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "case_comment_id")
    private int commentId;
    @Column(name = "love")
    private int love;

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

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public UserEvalCaseComment(int userId, int commentId, int love) {
        this.userId = userId;
        this.commentId = commentId;
        this.love = love;
    }

    public UserEvalCaseComment() {
    }

}
