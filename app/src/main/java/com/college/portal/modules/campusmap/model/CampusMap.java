package com.college.portal.modules.campusmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampusMap {

    @SerializedName("sr_no")
    @Expose
    private Integer srNo;

    @SerializedName("location_name")
    @Expose
    private String locationName;

    @SerializedName("location_latitude")
    @Expose
    private String locationLatitude;

    @SerializedName("location_longitude")
    @Expose
    private String locationLongitude;


    public CampusMap() {

    }

    public CampusMap(Integer srNo, String locationName, String locationLatitude, String locationLongitude) {
        this.srNo = srNo;
        this.locationName = locationName;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
