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

    @SerializedName("n_subtitle")
    @Expose
    private String nSubtitle;

    @SerializedName("n_detail")
    @Expose
    private String nDetail;

    @SerializedName("n_image")
    @Expose
    private String nImage;

    public News() {
    }

    public News(Integer nId,
                String nDate,
                String nTitle,
                String nSubtitle,
                String nDetail,
                String nImage) {
        this.nId = nId;
        this.nDate = nDate;
        this.nTitle = nTitle;
        this.nSubtitle = nSubtitle;
        this.nDetail = nDetail;
        this.nImage = nImage;
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

    public String getnSubtitle() {
        return nSubtitle;
    }

    public void setnSubtitle(String nSubtitle) {
        this.nSubtitle = nSubtitle;
    }

    public String getnDetail() {
        return nDetail;
    }

    public void setnDetail(String nDetail) {
        this.nDetail = nDetail;
    }

    public String getnImage() {
        return nImage;
    }

    public void setnImage(String nImage) {
        this.nImage = nImage;
    }
}
