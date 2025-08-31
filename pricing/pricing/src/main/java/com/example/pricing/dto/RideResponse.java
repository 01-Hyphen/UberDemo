package com.example.pricing.dto;

public class RideResponse {

    private double distanceKm;
    private String  durationMinutes;
    private double price;

    public RideResponse(double distanceKm, String durationMinutes, double price) {
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.price = price;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public String  getDurationMinutes() {
        return durationMinutes;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public void setDurationMinutes(String durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}