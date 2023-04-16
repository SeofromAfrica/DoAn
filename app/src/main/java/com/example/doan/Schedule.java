package com.example.doan;

public class Schedule {

    private long id;
    private String title;
    private String time;

    public Schedule() {
    }

    public Schedule(long id, String title, String time) {
        this.id=id;
        this.title = title;
        this.time = time;
    }
    public Schedule(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
