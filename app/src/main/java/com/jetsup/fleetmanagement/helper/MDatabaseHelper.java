package com.jetsup.fleetmanagement.helper;

import static com.jetsup.fleetmanagement.util.GlobalsConstants.DATABASE_NAME;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.DRIVER_TB_DRIVER_EMAIL;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.DRIVER_TB_DRIVER_ID;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.DRIVER_TB_DRIVER_NAME;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.DRIVER_TB_DRIVER_PHONE;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.DRIVER_TB_FROM_DATE;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.DRIVER_TB_NAME;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.FLEET_TB_NAME;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.FLEET_TB_NUMBER_PLATE;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.FLEET_TB_VEHICLE_ID;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.LATITUDE;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.LOG_TB_LOGGED_TIME;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.LOG_TB_LOG_ID;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.LOG_TB_NAME;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.LOG_TB_PARENT_VEHICLE_ID;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.LONGITUDE;
import static com.jetsup.fleetmanagement.util.GlobalsConstants.M_TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jetsup.fleetmanagement.DatabaseLogs;
import com.jetsup.fleetmanagement.model.DriverModel;
import com.jetsup.fleetmanagement.model.LogModel;
import com.jetsup.fleetmanagement.model.VehicleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MDatabaseHelper extends SQLiteOpenHelper {

    Map<Integer, DriverModel> driverModelMap;
    Map<Integer, VehicleModel> vehicleIdMap;
    Map<Integer, LogModel> logModelMap;

    public MDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // "CREATE TABLE fleet (vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT, number_plate TEXT UNIQUE, driver_id INTEGER, FOREIGN KEY(driver_id) REFERENCES drivers(driver_id))"
        // vehicle_id, number_plate, driver_id
        String createTableQuery = "CREATE TABLE " + FLEET_TB_NAME +
                " (" + FLEET_TB_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FLEET_TB_NUMBER_PLATE + " TEXT UNIQUE, " +
                DRIVER_TB_DRIVER_ID + " INTEGER, " +
                "FOREIGN KEY(" + DRIVER_TB_DRIVER_ID + ") REFERENCES " + DRIVER_TB_NAME + "(" + DRIVER_TB_DRIVER_ID + ") ON DELETE SET NULL)";
        db.execSQL(createTableQuery);
        Log.i(M_TAG, "Fleet table created");
        // CREATE TABLE drivers (driver_id INTEGER PRIMARY KEY AUTOINCREMENT, driver_name TEXT,
        // driver_phone TEXT UNIQUE, driver_email TEXT UNIQUE, from_date DATE DEFAULT CURRENT_DATE
        // driver_id, driver_name, driver_phone, driver_email, from_date
        String createDriversTableQuery = "CREATE TABLE " + DRIVER_TB_NAME +
                " (" + DRIVER_TB_DRIVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DRIVER_TB_DRIVER_NAME + " TEXT NOT NULL, " +
                DRIVER_TB_DRIVER_PHONE + " TEXT NOT NULL UNIQUE, " + DRIVER_TB_DRIVER_EMAIL + " TEXT NOT NULL UNIQUE, " +
                DRIVER_TB_FROM_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createDriversTableQuery);
        Log.i(M_TAG, "Drivers table created");
        //  "CREATE TABLE logs (log_id INTEGER PRIMARY KEY AUTOINCREMENT, parent_vehicle_id INTEGER UNIQUE, driver_id INTEGER UNIQUE,
        //  latitude TEXT, longitude TEXT, logged_time TEXT, FOREIGN KEY(parent_vehicle_id) REFERENCES FLEET_TABLE(vehicle_id))"
        // log_id, parent_vehicle_id, driver_id, latitude, longitude, logged_time
        String createDataTableQuery = "CREATE TABLE " + LOG_TB_NAME +
                " (" + LOG_TB_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOG_TB_PARENT_VEHICLE_ID + " INTEGER UNIQUE, " +
                DRIVER_TB_DRIVER_ID + " INTEGER UNIQUE, " + LATITUDE + " TEXT, " + LONGITUDE + " TEXT, " +
                LOG_TB_LOGGED_TIME + " TEXT, " +
                "FOREIGN KEY(" + LOG_TB_PARENT_VEHICLE_ID + ") REFERENCES " + FLEET_TB_NAME + "(" + FLEET_TB_VEHICLE_ID + ") ON DELETE SET NULL," +
                "FOREIGN KEY(" + DRIVER_TB_DRIVER_ID + ") REFERENCES " + DRIVER_TB_NAME + "(" + DRIVER_TB_DRIVER_ID + ") ON DELETE SET NULL)";
        db.execSQL(createDataTableQuery);
        Log.i(M_TAG, "Logs table created");

        // TODO: Fetch the data below from Firebase
        String firstInsertQuery = "INSERT INTO " + DRIVER_TB_NAME + " (" + DRIVER_TB_DRIVER_NAME + ", " +
                DRIVER_TB_DRIVER_PHONE + ", " + DRIVER_TB_DRIVER_EMAIL + ", " + DRIVER_TB_FROM_DATE + ") " +
                "VALUES ('Admin', '0700000000', 'admin@fleet.com', '08-17-2023 200020')";
        db.execSQL(firstInsertQuery);
        firstInsertQuery = "INSERT INTO " + FLEET_TB_NAME + " (" + FLEET_TB_NUMBER_PLATE + ", " + DRIVER_TB_DRIVER_ID + ") " +
                "VALUES ('KAA 123A', 1)";
        db.execSQL(firstInsertQuery);
        firstInsertQuery = "INSERT INTO " + LOG_TB_NAME + " (" + LOG_TB_PARENT_VEHICLE_ID + ", " + DRIVER_TB_DRIVER_ID + ", " +
                LATITUDE + ", " + LONGITUDE + ", " + LOG_TB_LOGGED_TIME + ") " +
                "VALUES (1, 1, '-0.786801', '37.045632', '08-17-2023 200020')";
        db.execSQL(firstInsertQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<LogModel> fetchAllData() {
        ArrayList<LogModel> loggedData = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // SELECT * FROM fleet
        // vehicle_id, number_plate, driver_id
        Cursor cursor = db.rawQuery("SELECT * FROM " + FLEET_TB_NAME, null);
        vehicleIdMap = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                vehicleIdMap.put(cursor.getInt(0)/*vehicle_id*/,
                        new VehicleModel(cursor.getInt(0), cursor.getString(1),
                                cursor.getInt(2)));
            } while (cursor.moveToNext());
        }
        DatabaseLogs.vehicleIdMap = vehicleIdMap;
        Log.i(M_TAG, "Vehicle ID Map: " + vehicleIdMap.toString());
        cursor.close();

        // SELECT * FROM drivers
        // driver_id, driver_name, driver_phone, driver_email, from_date
        driverModelMap = new HashMap<>(); // DriverID and NumberPlate
        cursor = db.rawQuery("SELECT * FROM " + DRIVER_TB_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                driverModelMap.put(cursor.getInt(0),
                        new DriverModel(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        DatabaseLogs.driverModelMap = driverModelMap;
        Log.i(M_TAG, "Driver Model Map: " + driverModelMap.toString());
        cursor.close();

        // SELECT * FROM logs
        // log_id, parent_vehicle_id, driver_id, latitude, longitude, logged_time
        logModelMap = new HashMap<>();
        cursor = db.rawQuery("SELECT * FROM " + LOG_TB_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                logModelMap.put(cursor.getInt(0),
                        new LogModel(cursor.getInt(0), cursor.getInt(1),
                                cursor.getInt(2), cursor.getDouble(3),
                                cursor.getDouble(4), cursor.getString(5)));
                loggedData.add(new LogModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                        cursor.getDouble(3), cursor.getDouble(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        DatabaseLogs.logModelMap = logModelMap;
        Log.i(M_TAG, "Log Model Map: " + logModelMap.toString());
        cursor.close();
        Log.i(M_TAG, "Logged Data: " + loggedData);
        return loggedData;
    }
}
