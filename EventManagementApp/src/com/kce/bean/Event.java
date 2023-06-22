package com.kce.bean;

public class Event {
    private int id;
    private String name;
    private String venue;
    private String date;
    private String time;
    private int capacity;

    public Event() {
    }

    public Event(String name, String venue, String date, String time, int capacity) {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
