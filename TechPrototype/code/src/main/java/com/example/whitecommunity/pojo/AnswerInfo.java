package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "answers")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AnswerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;
    @Column(name = "answer_text")
    private String answerText;
    @Column(name = "answerer_id")
    private int answererId;
    @Column(name = "answer_release_time")
    private String releaseTime;
    @Column(name = "answer_edit_time")
    private String editTime;
    @Column(name = "is_solution")
    private boolean isSolution;
    @Column(name = "saves")
    private int saves;
    @Column(name = "likes")
    private int likes;
    @Column(name = "dislikes")
    private int dislikes;
    @Column(name = "question_id")
    private int questionId;
    @Column(name = "markdown")
    private boolean markdown;
    @Transient
    private int liked;  // 当前用户点赞/点踩信息，0 为无操作，1 为点赞，2 为点踩
    @Transient
    private boolean saved;  // 是否被当前用户收藏
    @Transient
    private boolean reported;  // 是否被当前用户举报

    public AnswerInfo(int answerId, String answerText, int answererId,
                      String releaseTime, String editTime,
                      boolean isSolution, int saves, int likes, int dislikes,
                      int questionId, boolean markdown) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.answererId = answererId;
        this.releaseTime = releaseTime;
        this.editTime = editTime;
        this.isSolution = isSolution;
        this.saves = saves;
        this.likes = likes;
        this.dislikes = dislikes;
        this.questionId = questionId;
        this.markdown = markdown;
    }

    public AnswerInfo() {
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public int getAnswererId() {
        return answererId;
    }

    public String getReleaseTime() {
        return releaseTime;
    }
    public String getEditTime() {
        if (editTime == null)
            return "";
        return editTime;
    }

    public int getSaves() {
        return saves;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean isSolution() {
        return isSolution;
    }

    public int getLiked() {
        return liked;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setSolution(boolean solution) {
        isSolution = solution;
    }

    public boolean isMarkdown() {
        return markdown;
    }

    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public void setAnswerText(String text) {
        this.answerText = text;
    }

    public void setAnswererId(int answererId) {
        this.answererId = answererId;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public void setIsSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }
}
