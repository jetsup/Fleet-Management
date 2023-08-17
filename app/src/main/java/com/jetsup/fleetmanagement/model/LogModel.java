package com.jetsup.fleetmanagement.model;

import androidx.annotation.NonNull;

public class LogModel {
    private final int logID, vehicleID, driverID;
    private final double latitude, longitude;
    private final String loggedTime;

    public LogModel(int logID, int vehicleID, int driverID, double latitude, double longitude, String loggedTime) {
        this.logID = logID;
        this.vehicleID = vehicleID;
        this.driverID = driverID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.loggedTime = loggedTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "LOG{" +
                "logID=" + logID +
                ", vehicleID=" + vehicleID +
                ", driverID=" + driverID +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", loggedTime='" + loggedTime + '\'' +
                '}';
    }

    public int getLogID() {
        return logID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public int getDriverID() {
        return driverID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLoggedTime() {
        return loggedTime;
    }
}
