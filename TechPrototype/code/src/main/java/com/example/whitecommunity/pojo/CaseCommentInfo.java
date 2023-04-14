package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "case_comments")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class CaseCommentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;
    @Column(name = "comment_text")
    private String commentText;
    @Column(name = "parent_id")
    private int parentId;
    @Column(name = "commenter_id")
    private int commenterId;
    @Column(name = "comment_release_time")
    private String releaseTime;
    @Column(name = "level_one")
    private boolean levelOne;
    @Column(name = "descendants")
    private int descendants;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;
    @Column(name = "deleted")
    private boolean deleted;

    @Transient
    private int liked;  // 当前用户点赞/点踩信息，0 为无操作，1 为点赞，2 为点踩

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(int commenterId) {
        this.commenterId = commenterId;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public boolean isLevelOne() {
        return levelOne;
    }

    public void setLevelOne(boolean levelOne) {
        this.levelOne = levelOne;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public CaseCommentInfo(int commentId, String commentText, int parentId,
                           int commenterId, String releaseTime, boolean levelOne,
                           int descendants, int likes, int dislikes,
                           boolean deleted, int liked) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.parentId = parentId;
        this.commenterId = commenterId;
        this.releaseTime = releaseTime;
        this.levelOne = levelOne;
        this.descendants = descendants;
        this.likes = likes;
        this.dislikes = dislikes;
        this.deleted = deleted;
        this.liked = liked;
    }

    public CaseCommentInfo() {
    }

}
