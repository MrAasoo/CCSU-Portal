package com.college.portal.modules.clubs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Club {

    @SerializedName("club_id")
    @Expose
    private String clubId;

    @SerializedName("club_name")
    @Expose
    private String clubName;

    @SerializedName("club_motive")
    @Expose
    private String clubMotive;

    @SerializedName("club_logo")
    @Expose
    private String clubLogo;

    @SerializedName("club_background_image")
    @Expose
    private String clubBackgroundImage;

    @SerializedName("club_start_date")
    @Expose
    private String clubStartDate;

    @SerializedName("join_date")
    @Expose
    private String joinDate;

    @SerializedName("sr_no")
    @Expose
    private Integer srNo;

    @SerializedName("member_status")
    @Expose
    private Integer memberStatus;

    @SerializedName("member_type")
    @Expose
    private Integer memberType;

    @SerializedName("total_members")
    @Expose
    private Integer totalMembers;


    public Club() {
    }

    public Club(String clubId,
                String clubName,
                String clubMotive,
                String clubLogo,
                String clubBackgroundImage,
                String clubStartDate,
                String joinDate,
                Integer srNo,
                Integer memberStatus,
                Integer memberType,
                Integer totalMembers) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubMotive = clubMotive;
        this.clubLogo = clubLogo;
        this.clubBackgroundImage = clubBackgroundImage;
        this.clubStartDate = clubStartDate;
        this.joinDate = joinDate;
        this.srNo = srNo;
        this.memberStatus = memberStatus;
        this.memberType = memberType;
        this.totalMembers = totalMembers;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubMotive() {
        return clubMotive;
    }

    public void setClubMotive(String clubMotive) {
        this.clubMotive = clubMotive;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getClubBackgroundImage() {
        return clubBackgroundImage;
    }

    public void setClubBackgroundImage(String clubBackgroundImage) {
        this.clubBackgroundImage = clubBackgroundImage;
    }

    public String getClubStartDate() {
        return clubStartDate;
    }

    public void setClubStartDate(String clubStartDate) {
        this.clubStartDate = clubStartDate;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(Integer totalMembers) {
        this.totalMembers = totalMembers;
    }
}
