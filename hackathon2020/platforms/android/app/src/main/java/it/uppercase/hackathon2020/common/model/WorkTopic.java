package it.uppercase.hackathon2020.common.model;

import java.util.HashMap;

public class WorkTopic {
    private String id;
    private String roomId;
    private String description;
    private String title;
    private HashMap<String, String> member;

    public WorkTopic() {
    }

    public WorkTopic(String id, String roomId, String description, String title, HashMap<String, String> member) {
        this.id = id;
        this.roomId = roomId;
        this.description = description;
        this.title = title;
        this.member = member;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<String, String>  getMember() {
        return member;
    }

    public void setMember(HashMap<String, String>  member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WorkTopic{" +
                "id='" + id + '\'' +
                ", roomId='" + roomId + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", member=" + member +
                '}';
    }
}
