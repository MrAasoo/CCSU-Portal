package com.college.portal.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;

public class StudentPref {

    @SuppressLint("stdId")
    @Expose
    private String stdId;

    @SuppressLint("stdPassword")
    @Expose
    private String stdPassword;

    @SuppressLint("stdName")
    @Expose
    private String stdName;

    @SuppressLint("stdImage")
    @Expose
    private String stdImage;

    @SuppressLint("stdDepartment")
    @Expose
    private String stdDepartment;

    public StudentPref(String stdId,
                       String stdPassword,
                       String stdName,
                       String stdDepartment,
                       String stdImage){
        this.stdId = stdId;
        this.stdPassword = stdPassword;
        this.stdName = stdName;
        this.stdDepartment = stdDepartment;
        this.stdImage = stdImage;

    }


    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdDepartment() {
        return stdDepartment;
    }

    public void setStdDepartment(String stdDepartment) {
        this.stdDepartment = stdDepartment;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public String getStdPassword() {
        return stdPassword;
    }

    public void setStdPassword(String stdPassword) {
        this.stdPassword = stdPassword;
    }

    public String getStdImage() {
        return stdImage;
    }

    public void setStdImage(String stdImage) {
        this.stdImage = stdImage;
    }
}
