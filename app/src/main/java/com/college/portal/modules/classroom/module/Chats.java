package com.college.portal.modules.classroom.module;

public class Chats {

    private String std_id;
    private String std_name;
    private String message;
    private String date;
    private String time;

    public Chats() {
    }

    public Chats(String std_id, String std_name, String message, String date, String time) {
        this.std_id = std_id;
        this.std_name = std_name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
