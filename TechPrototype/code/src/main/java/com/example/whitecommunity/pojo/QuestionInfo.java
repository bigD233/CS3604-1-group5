package com.example.whitecommunity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "questions")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class QuestionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;
    @Column(name = "question_caption")
    private String caption;
    @Column(name = "question_content")
    private String content;
    @Column(name = "questioner_id")
    private int questionerId;
    @Column(name = "question_release_time")
    private String releaseTime;
    @Column(name = "question_edit_time")
    private String editTime;
    @Column(name = "solved")
    private boolean solved;
    @Column(name = "closed")
    private boolean closed;
    @Column(name = "likes")
    private int likes;
    @Column(name = "watchers")
    private int watchers;
    @Column(name = "markdown")
    private boolean markdown;
//    @OneToMany
//    @JoinColumn(name = "question_id")
//    private List<AnswerInfo> answers;

    //    @ManyToMany(targetEntity = TagInfo.class, cascade = CascadeType.ALL)
//    @JoinTable(name = "questions_tags",
//            // 当前对象在中间表中的外键
//            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "question_id"),
//            // 对方对象在中间表的外键
//            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
//    private List<TagInfo> tags;
    @Transient
    private List<TagInfo> tags;
    @Transient
    private boolean liked;
    @Transient
    private boolean watched;


    public QuestionInfo(int questionId, String caption, String content,
                        int questionerId, String releaseTime, String editTime,
                        boolean solved, boolean closed, int likes, int watchers,
                        boolean markdown, List<TagInfo> tags
    ) {
        this.questionId = questionId;
        this.caption = caption;
        this.content = content;
        this.questionerId = questionerId;
        this.releaseTime = releaseTime;
        this.editTime = editTime;
        this.solved = solved;
        this.closed = closed;
        this.likes = likes;
        this.watchers = watchers;
        this.markdown = markdown;
        this.tags = tags;
//        this.answers = answers;
    }

    public QuestionInfo(int questionId, String caption, String content,
                        int questionerId, String releaseTime, String editTime,
                        boolean solved, boolean closed, int likes, int watchers,
                        boolean markdown
    ) {
        this.questionId = questionId;
        this.caption = caption;
        this.content = content;
        this.questionerId = questionerId;
        this.releaseTime = releaseTime;
        this.editTime = editTime;
        this.solved = solved;
        this.closed = closed;
        this.likes = likes;
        this.watchers = watchers;
        this.markdown = markdown;
    }

    public QuestionInfo() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getCaption() {
        return caption;
    }

    public String getContent() {
        return content;
    }

    public int getQuestionerId() {
        return questionerId;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getEditTime() {
        if (editTime == null)
            return "";
        return editTime;
    }

    public List<TagInfo> getTags() {
        return tags;
    }

    public int getLikes() {
        return likes;
    }

    public int getWatchers() {
        return watchers;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isSolved() {
        return solved;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isMarkdown() {
        return markdown;
    }


    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuestionerId(int questionerId) {
        this.questionerId = questionerId;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }


    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setTags(List<TagInfo> tags) {
        this.tags = tags;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
    }


//    public void setAnswers(List<AnswerInfo> answers) {
//        this.answers = answers;
//    }
}
