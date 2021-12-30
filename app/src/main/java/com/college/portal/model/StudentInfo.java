package com.college.portal.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentInfo {

    @SuppressLint("stdId")
    @Expose
    private String stdId;

    @SuppressLint("stdPassword")
    @Expose
    private String stdPassword;

    @SuppressLint("stdName")
    @Expose
    private String stdName;

    @SuppressLint("stdDepartment")
    @Expose
    private String stdDepartment;

    @SerializedName("stdSem")
    @Expose
    private String stdSem;

    @SerializedName("stdAcademic")
    @Expose
    private String stdAcademic;

    @SerializedName("stdFather")
    @Expose
    private String stdFather;

    @SerializedName("stdMother")
    @Expose
    private String stdMother;

    @SerializedName("stdDob")
    @Expose
    private String stdDob;

    @SerializedName("stdContact")
    @Expose
    private String stdContact;

    @SerializedName("stdEmail")
    @Expose
    private String stdEmail;

    @SerializedName("stdCity")
    @Expose
    private String stdCity;

    @SerializedName("stdDist")
    @Expose
    private String stdDist;

    @SerializedName("stdPin")
    @Expose
    private String stdPin;

    @SerializedName("stdImage")
    @Expose
    private String stdImage;

    public StudentInfo(String stdId,
                       String stdPassword,
                       String stdName,
                       String stdImage,
                       String stdDepartment,
                       String stdSem,
                       String stdAcademic,
                       String stdFather,
                       String stdMother,
                       String stdDob,
                       String stdContact,
                       String stdEmail,
                       String stdCity,
                       String stdDist,
                       String stdPin) {
        this.stdId = stdId;
        this.stdPassword = stdPassword;
        this.stdName = stdName;
        this.stdImage = stdImage;
        this.stdDepartment = stdDepartment;
        this.stdSem = stdSem;
        this.stdAcademic = stdAcademic;
        this.stdFather = stdFather;
        this.stdMother = stdMother;
        this.stdDob = stdDob;
        this.stdContact = stdContact;
        this.stdEmail = stdEmail;
        this.stdCity = stdCity;
        this.stdDist = stdDist;
        this.stdPin = stdPin;
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

    public String getStdSem() {
        return stdSem;
    }

    public void setStdSem(String stdSem) {
        this.stdSem = stdSem;
    }

    public String getStdAcademic() {
        return stdAcademic;
    }

    public void setStdAcademic(String stdAcademic) {
        this.stdAcademic = stdAcademic;
    }

    public String getStdFather() {
        return stdFather;
    }

    public void setStdFather(String stdFather) {
        this.stdFather = stdFather;
    }

    public String getStdMother() {
        return stdMother;
    }

    public void setStdMother(String stdMother) {
        this.stdMother = stdMother;
    }

    public String getStdDob() {
        return stdDob;
    }

    public void setStdDob(String stdDob) {
        this.stdDob = stdDob;
    }

    public String getStdContact() {
        return stdContact;
    }

    public void setStdContact(String stdContact) {
        this.stdContact = stdContact;
    }

    public String getStdEmail() {
        return stdEmail;
    }

    public void setStdEmail(String stdEmail) {
        this.stdEmail = stdEmail;
    }

    public String getStdCity() {
        return stdCity;
    }

    public void setStdCity(String stdCity) {
        this.stdCity = stdCity;
    }

    public String getStdDist() {
        return stdDist;
    }

    public void setStdDist(String stdDist) {
        this.stdDist = stdDist;
    }

    public String getStdPin() {
        return stdPin;
    }

    public void setStdPin(String stdPin) {
        this.stdPin = stdPin;
    }
}

