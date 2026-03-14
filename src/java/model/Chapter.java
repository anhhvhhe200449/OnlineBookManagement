package model;

import java.sql.Timestamp;

public class Chapter {

    private int chapterID;
    private int bookID;
    private int chapterNumber;
    private String ChapTitle;
    private String content;
    private Timestamp createdAt;

    public Chapter() {
    }

    public Chapter(int chapterID, int bookID, int chapterNumber, String ChapTitle, String content, Timestamp createdAt) {
        this.chapterID = chapterID;
        this.bookID = bookID;
        this.chapterNumber = chapterNumber;
        this.ChapTitle = ChapTitle;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapTitle() {
        return ChapTitle;
    }

    public void setChapTitle(String ChapTitle) {
        this.ChapTitle = ChapTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
