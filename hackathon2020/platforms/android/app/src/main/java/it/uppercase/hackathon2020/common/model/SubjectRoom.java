package it.uppercase.hackathon2020.common.model;

import java.io.Serializable;

public class SubjectRoom implements Serializable {
    private String id;
    private String live;
    private String name;
    private String drive;

    public SubjectRoom() {
    }

    public SubjectRoom(String id, String live, String name, String drive) {
        this.id = id;
        this.live = live;
        this.name = name;
        this.drive = drive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    @Override
    public String toString() {
        return "SubjectRoom{" +
                "id='" + id + '\'' +
                ", live='" + live + '\'' +
                ", name='" + name + '\'' +
                ", drive='" + drive + '\'' +
                '}';
    }
}
