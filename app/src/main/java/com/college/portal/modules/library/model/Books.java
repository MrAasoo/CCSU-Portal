package com.college.portal.modules.library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Books {

    @SerializedName("srno")
    @Expose
    private String srNo;

    @SerializedName("book_title")
    @Expose
    private String bookTitle;

    @SerializedName("book_author")
    @Expose
    private String bookAuthor;

    @SerializedName("publisher")
    @Expose
    private String publisher;

    @SerializedName("publish_date")
    @Expose
    private String publishDate;

    @SerializedName("book_type")
    @Expose
    private String bookType;

    @SerializedName("book_path")
    @Expose
    private String bookPath;

    public Books() {
    }

    public Books(String srNo, String bookTitle, String bookAuthor, String publisher, String publishDate, String bookType, String bookPath) {
        this.srNo = srNo;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.bookType = bookType;
        this.bookPath = bookPath;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }
}
