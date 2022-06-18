package com.college.portal.modules.classroom.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscussionRoom {

    @SerializedName("room_key")
    @Expose
    private String roomKey;

    @SerializedName("room_name")
    @Expose
    private String roomName;


    public DiscussionRoom() {
    }

    public DiscussionRoom(String roomKey, String roomName) {
        this.roomKey = roomKey;
        this.roomName = roomName;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
