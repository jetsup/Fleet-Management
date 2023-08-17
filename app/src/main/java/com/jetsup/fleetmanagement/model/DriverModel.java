package com.jetsup.fleetmanagement.model;

import androidx.annotation.NonNull;

public class DriverModel {
    private final int driverId;
    private final String driverName, driverEmail, driverPhone, fromDate;

    public DriverModel(int driverId, String driverName, String driverEmail, String driverPhone, String fromDate) {
        this.driverId = driverId;
        this.fromDate = fromDate;
        this.driverName = driverName;
        this.driverEmail = driverEmail;
        this.driverPhone = driverPhone;
    }

    @NonNull
    @Override
    public String toString() {
        return "DRIVER{" +
                "driverId=" + driverId +
                ", driverName='" + driverName + '\'' +
                ", driverEmail='" + driverEmail + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", fromDate='" + fromDate + '\'' +
                '}';
    }

    public int getDriverId() {
        return driverId;
    }

    public String getFromDate() {
        return fromDate;
    }


    public String getDriverName() {
        return driverName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public String getDriverPhone() {
        return driverPhone;
    }
}
