package com.college.portal.modules.student_zone.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("subject_code")
    @Expose
    private String subjectCode;

    @SerializedName("subject_name")
    @Expose
    private String subjectName;

    @SerializedName("faculty_name")
    @Expose
    private String facultyName;

    public Subject() {
    }

    public Subject(String subjectCode,
                   String subjectName,
                   String facultyName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.facultyName = facultyName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
