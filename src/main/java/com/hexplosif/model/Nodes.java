package com.hexplosif.model;

public class Nodes {
    private Long id;
    private double latitude;
    private double longitude;

    public Nodes() {
        this.id = 0L;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public void setNodesAttributes(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getID() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}