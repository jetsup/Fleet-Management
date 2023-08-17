package com.jetsup.fleetmanagement.model;

import androidx.annotation.NonNull;

public class VehicleModel {
    private final int vehicleID, driverID;
    private final String numberPlate;

    public VehicleModel(int vehicleID, String numberPlate, int driverID) {
        this.vehicleID = vehicleID;
        this.numberPlate = numberPlate;
        this.driverID = driverID;
    }

    @NonNull
    @Override
    public String toString() {
        return "VEHICLE{" +
                "vehicleID=" + vehicleID +
                ", driverID=" + driverID +
                ", numberPlate='" + numberPlate + '\'' +
                '}';
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public int getDriverID() {
        return driverID;
    }
}
