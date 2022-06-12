package com.college.portal.modules.studentzone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("assi_id")
    @Expose
    private Integer assiId;

    @SerializedName("assi_title")
    @Expose
    private String assiTitle;

    @SerializedName("assi_date")
    @Expose
    private String assiDate;

    @SerializedName("assi_details")
    @Expose
    private String assiDetails;

    @SerializedName("faculty_name")
    @Expose
    private String facultyName;

    @SerializedName("assi_due_date")
    @Expose
    private String assiDueDate;

    public Assignment() {
    }

    public Assignment(Integer assiId,
                      String assiTitle,
                      String assiDate,
                      String assiDetails,
                      String facultyName,
                      String assiDueDate) {
        this.assiId = assiId;
        this.assiTitle = assiTitle;
        this.assiDate = assiDate;
        this.assiDetails = assiDetails;
        this.facultyName = facultyName;
        this.assiDueDate = assiDueDate;
    }

    public Integer getAssiId() {
        return assiId;
    }

    public void setAssiId(Integer assiId) {
        this.assiId = assiId;
    }

    public String getAssiTitle() {
        return assiTitle;
    }

    public void setAssiTitle(String assiTitle) {
        this.assiTitle = assiTitle;
    }

    public String getAssiDate() {
        return assiDate;
    }

    public void setAssiDate(String assiDate) {
        this.assiDate = assiDate;
    }

    public String getAssiDetails() {
        return assiDetails;
    }

    public void setAssiDetails(String assiDetails) {
        this.assiDetails = assiDetails;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getAssiDueDate() {
        return assiDueDate;
    }

    public void setAssiDueDate(String assiDueDate) {
        this.assiDueDate = assiDueDate;
    }
}