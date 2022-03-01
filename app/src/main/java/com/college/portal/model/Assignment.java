package com.college.portal.model;

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

    @SerializedName("assi_type")
    @Expose
    private String assiType;

    @SerializedName("assi_dep")
    @Expose
    private String assiDep;

    @SerializedName("assi_faculty")
    @Expose
    private String assiFaculty;

    public Assignment() {
    }

    public Assignment(Integer assiId,
                      String assiTitle,
                      String assiDate,
                      String assiType,
                      String assiDep,
                      String assiFaculty) {
        this.assiId = assiId;
        this.assiTitle = assiTitle;
        this.assiDate = assiDate;
        this.assiType = assiType;
        this.assiDep = assiDep;
        this.assiFaculty = assiFaculty;
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

    public String getAssiType() {
        return assiType;
    }

    public void setAssiType(String assiType) {
        this.assiType = assiType;
    }

    public String getAssiDep() {
        return assiDep;
    }

    public void setAssiDep(String assiDep) {
        this.assiDep = assiDep;
    }

    public String getAssiFaculty() {
        return assiFaculty;
    }

    public void setAssiFaculty(String assiFaculty) {
        this.assiFaculty = assiFaculty;
    }
}
