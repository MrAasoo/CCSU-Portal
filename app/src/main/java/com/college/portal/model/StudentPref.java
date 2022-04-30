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

    @SuppressLint("stdDepartmentId")
    @Expose
    private String stdDepartmentId;

    @SuppressLint("stdBranchName")
    @Expose
    private String stdBranchName;

    @SuppressLint("stdBranchId")
    @Expose
    private String stdBranchId;

    public StudentPref() {
    }

    public StudentPref(String stdId,
                       String stdPassword,
                       String stdName,
                       String stdImage,
                       String stdDepartment,
                       String stdDepartmentId,
                       String stdBranchName,
                       String stdBranchId) {
        this.stdId = stdId;
        this.stdPassword = stdPassword;
        this.stdName = stdName;
        this.stdImage = stdImage;
        this.stdDepartment = stdDepartment;
        this.stdDepartmentId = stdDepartmentId;
        this.stdBranchName = stdBranchName;
        this.stdBranchId = stdBranchId;
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

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdImage() {
        return stdImage;
    }

    public void setStdImage(String stdImage) {
        this.stdImage = stdImage;
    }

    public String getStdDepartment() {
        return stdDepartment;
    }

    public void setStdDepartment(String stdDepartment) {
        this.stdDepartment = stdDepartment;
    }

    public String getStdDepartmentId() {
        return stdDepartmentId;
    }

    public void setStdDepartmentId(String stdDepartmentId) {
        this.stdDepartmentId = stdDepartmentId;
    }

    public String getStdBranchName() {
        return stdBranchName;
    }

    public void setStdBranchName(String stdBranchName) {
        this.stdBranchName = stdBranchName;
    }

    public String getStdBranchId() {
        return stdBranchId;
    }

    public void setStdBranchId(String stdBranchId) {
        this.stdBranchId = stdBranchId;
    }
}
