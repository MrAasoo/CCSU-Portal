package com.college.portal.modules.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUs {

    @SerializedName("contact_name")
    @Expose
    private String contactName;

    @SerializedName("contact_designation")
    @Expose
    private String contactDesignation;

    @SerializedName("contact_number")
    @Expose
    private String contactNumber;

    @SerializedName("contact_link")
    @Expose
    private String contactLink;

    @SerializedName("contact_email")
    @Expose
    private String contactEmail;

    public ContactUs() {
    }

    public ContactUs(String contactName, String contactDesignation, String contactNumber, String contactLink, String contactEmail) {
        this.contactName = contactName;
        this.contactDesignation = contactDesignation;
        this.contactNumber = contactNumber;
        this.contactLink = contactLink;
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactDesignation() {
        return contactDesignation;
    }

    public void setContactDesignation(String contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactLink() {
        return contactLink;
    }

    public void setContactLink(String contactLink) {
        this.contactLink = contactLink;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}