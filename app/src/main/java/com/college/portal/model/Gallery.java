package com.college.portal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("sr_no")
    @Expose
    private Integer srNo;

    @SerializedName("media_description")
    @Expose
    private String mediaDescription;

    @SerializedName("media_added")
    @Expose
    private String mediaAdded;

    @SerializedName("media_path")
    @Expose
    private String mediaPath;

    public Gallery() {
    }

    public Gallery(Integer srNo,
                   String mediaDescription,
                   String mediaAdded,
                   String mediaPath) {
        this.srNo = srNo;
        this.mediaDescription = mediaDescription;
        this.mediaAdded = mediaAdded;
        this.mediaPath = mediaPath;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public String getMediaAdded() {
        return mediaAdded;
    }

    public void setMediaAdded(String mediaAdded) {
        this.mediaAdded = mediaAdded;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }
}
