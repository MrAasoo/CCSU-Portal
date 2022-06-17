package com.college.portal.modules.classroom.module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscussionRoom {

    @SerializedName("room_id")
    @Expose
    private String roomId;

    @SerializedName("room_name")
    @Expose
    private String roomName;


    public DiscussionRoom() {
    }

    public DiscussionRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
