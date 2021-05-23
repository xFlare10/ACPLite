package com.example.acplite.entidades;

public class Evento {
    private int eventID;
    private String eventName;
    private String eventAddress;
    private String eventDate;

    public Evento(int eventID, String eventName, String eventAddress, String eventDate) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventAddress = eventAddress;
        this.eventDate = eventDate;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
