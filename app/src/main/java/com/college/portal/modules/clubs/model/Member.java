package com.college.portal.modules.clubs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("sr_no")
    @Expose
    private String srNo;

    @SerializedName("member_status")
    @Expose
    private String memberStatus;

    @SerializedName("member_type")
    @Expose
    private String memberType;

    @SerializedName("join_date")
    @Expose
    private String joinDate;

    @SerializedName("std_name")
    @Expose
    private String stdName;

    @SerializedName("std_id")
    @Expose
    private String stdId;

    @SerializedName("std_image")
    @Expose
    private String stdImage;

    @SerializedName("branch_name")
    @Expose
    private String branchName;

    public Member() {
    }

    public Member(String srNo,
                  String memberStatus,
                  String memberType,
                  String joinDate,
                  String stdName,
                  String stdId,
                  String stdImage,
                  String branchName) {
        this.srNo = srNo;
        this.memberStatus = memberStatus;
        this.memberType = memberType;
        this.joinDate = joinDate;
        this.stdName = stdName;
        this.stdId = stdId;
        this.stdImage = stdImage;
        this.branchName = branchName;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public String getStdImage() {
        return stdImage;
    }

    public void setStdImage(String stdImage) {
        this.stdImage = stdImage;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
