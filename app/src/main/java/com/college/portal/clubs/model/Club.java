package com.college.portal.clubs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Club {

    @SerializedName("club_id")
    @Expose
    private Integer clubId;

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

    @SerializedName("member_status")
    @Expose
    private Integer membeStatus;

    @SerializedName("member_type")
    @Expose
    private Integer memberType;


    public Club() {
    }

    public Club(Integer clubId,
                String clubName,
                String clubMotive,
                String clubLogo,
                String clubBackgroundImage,
                String clubStartDate,
                String joinDate,
                Integer membeStatus,
                Integer memberType) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubMotive = clubMotive;
        this.clubLogo = clubLogo;
        this.clubBackgroundImage = clubBackgroundImage;
        this.clubStartDate = clubStartDate;
        this.joinDate = joinDate;
        this.membeStatus = membeStatus;
        this.memberType = memberType;
    }


    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
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

    public Integer getMembeStatus() {
        return membeStatus;
    }

    public void setMembeStatus(Integer membeStatus) {
        this.membeStatus = membeStatus;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }
}
