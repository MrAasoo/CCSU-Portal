package com.college.portal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("n_id")
    @Expose
    private Integer nId;
    @SerializedName("n_date")
    @Expose
    private String nDate;
    @SerializedName("n_title")
    @Expose
    private String nTitle;
    @SerializedName("n_message")
    @Expose
    private String nMessage;

    public News() {
    }

    public News(Integer nId, String nDate, String nTitle, String nMessage) {
        this.nId = nId;
        this.nDate = nDate;
        this.nTitle = nTitle;
        this.nMessage = nMessage;
    }

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnMessage() {
        return nMessage;
    }

    public void setnMessage(String nMessage) {
        this.nMessage = nMessage;
    }
}
