package com.example.androidmapsh.model;

public class Bookmark {
    private final String name;
    private final float latitude;
    private final float longitude;

    public Bookmark(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
