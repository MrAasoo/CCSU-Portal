package com.college.portal.modules.classroom.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeBoard {

    @SerializedName("notice_id")
    @Expose
    private String noticeId;

    @SerializedName("notice_title")
    @Expose
    private String noticeTitle;

    @SerializedName("notice_message")
    @Expose
    private String noticeMessage;

    @SerializedName("notice_date")
    @Expose
    private String noticeDate;

    @SerializedName("file_path")
    @Expose
    private String filePath;

    public NoticeBoard() {
    }

    public NoticeBoard(String noticeId,
                       String noticeTitle,
                       String noticeMessage,
                       String noticeDate,
                       String filePath) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeMessage = noticeMessage;
        this.noticeDate = noticeDate;
        this.filePath = filePath;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeMessage() {
        return noticeMessage;
    }

    public void setNoticeMessage(String noticeMessage) {
        this.noticeMessage = noticeMessage;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
