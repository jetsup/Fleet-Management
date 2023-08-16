package com.jetsup.fleetmanagement.model;

public class VehicleModel {
    private String numberPlate, driverName;
    private double longitude, latitude;

    public VehicleModel(String driverName, String numberPlate, double latitude, double longitude) {
        this.numberPlate = numberPlate;
        this.driverName = driverName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
