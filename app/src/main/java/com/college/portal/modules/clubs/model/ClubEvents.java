package com.college.portal.modules.clubs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubEvents {

    @SerializedName("event_id")
    @Expose
    private String eventId;

    @SerializedName("club_id")
    @Expose
    private String clubId;

    @SerializedName("event_type")
    @Expose
    private String eventType;

    @SerializedName("event_topic")
    @Expose
    private String eventTopic;

    @SerializedName("event_motive")
    @Expose
    private String eventMotive;

    @SerializedName("event_post_date")
    @Expose
    private String eventPostDate;

    @SerializedName("event_date")
    @Expose
    private String eventDate;

    @SerializedName("event_image_path")
    @Expose
    private String eventImagePath;

    public ClubEvents() {
    }

    public ClubEvents(String eventId,
                      String clubId,
                      String eventType,
                      String eventTopic,
                      String eventMotive,
                      String eventPostDate,
                      String eventDate,
                      String eventImagePath) {
        this.eventId = eventId;
        this.clubId = clubId;
        this.eventType = eventType;
        this.eventTopic = eventTopic;
        this.eventMotive = eventMotive;
        this.eventPostDate = eventPostDate;
        this.eventDate = eventDate;
        this.eventImagePath = eventImagePath;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public void setEventTopic(String eventTopic) {
        this.eventTopic = eventTopic;
    }

    public String getEventMotive() {
        return eventMotive;
    }

    public void setEventMotive(String eventMotive) {
        this.eventMotive = eventMotive;
    }

    public String getEventPostDate() {
        return eventPostDate;
    }

    public void setEventPostDate(String eventPostDate) {
        this.eventPostDate = eventPostDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventImagePath() {
        return eventImagePath;
    }

    public void setEventImagePath(String eventImagePath) {
        this.eventImagePath = eventImagePath;
    }
}
