package com.college.portal.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;

public class InfoResponse {

    @SuppressLint("error")
    @Expose
    private Boolean error;

    @SuppressLint("code")
    @Expose
    private Integer code;

    @SuppressLint("info")
    @Expose
    private StudentInfo info;


    public InfoResponse(Boolean error, Integer code, StudentInfo info) {
        this.error = error;
        this.code = code;
        this.info = info;
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

    public StudentInfo getInfo() {
        return info;
    }

    public void setInfo(StudentInfo info) {
        this.info = info;
    }

}