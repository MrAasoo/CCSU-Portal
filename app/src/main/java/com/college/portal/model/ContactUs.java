package com.college.portal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUs {

    @SerializedName("col_name")
    @Expose
    private String colName;
    @SerializedName("col_contact")
    @Expose
    private String colContact;
    @SerializedName("col_link")
    @Expose
    private String colLink;
    @SerializedName("col_email")
    @Expose
    private String colEmail;

    public ContactUs() {
    }

    public ContactUs(String colName, String colContact, String colLink, String colEmail) {
        this.colName = colName;
        this.colContact = colContact;
        this.colLink = colLink;
        this.colEmail = colEmail;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColContact() {
        return colContact;
    }

    public void setColContact(String colContact) {
        this.colContact = colContact;
    }

    public String getColLink() {
        return colLink;
    }

    public void setColLink(String colLink) {
        this.colLink = colLink;
    }

    public String getColEmail() {
        return colEmail;
    }

    public void setColEmail(String colEmail) {
        this.colEmail = colEmail;
    }
}
