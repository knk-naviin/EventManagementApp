package com.kce.bean;


public class EventType extends Event {
    private String type;

    public EventType() {
    }

    public EventType(String name, String venue, String date, String time, int capacity, String type) {
        super(name, venue, date, time, capacity);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

