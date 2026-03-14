package model;

import java.sql.Timestamp;

public class Book {

    private int bookID;
    private String title;
    private String description;
    private String coverImage;
    private int categoryID;
    private String author;
    private int views;
    private String status;
    private Timestamp createdAt;

    public Book() {
    }

    public Book(int bookID, String title, String description, String coverImage, int categoryID, String author, int views, String status, Timestamp createdAt) {
        this.bookID = bookID;
        this.title = title;
        this.description = description;
        this.coverImage = coverImage;
        this.categoryID = categoryID;
        this.author = author;
        this.views = views;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
