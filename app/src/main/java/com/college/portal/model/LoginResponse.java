package com.college.portal.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;

public class LoginResponse {

    @SuppressLint("error")
    @Expose
    private Boolean error;

    @SuppressLint("code")
    @Expose
    private Integer code;

    @SuppressLint("message")
    @Expose
    private String message;

    @SuppressLint("info")
    @Expose
    private StudentPref info;


    public LoginResponse(Boolean error, Integer code, String message, StudentPref info) {
        this.error = error;
        this.message = message;
        this.code = code;
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public StudentPref getInfo() {
        return info;
    }

    public void setInfo(StudentPref info) {
        this.info = info;
    }
}
