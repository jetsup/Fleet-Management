package com.jetsup.fleetmanagement.helper;

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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jetsup.fleetmanagement.model.VehicleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    public String DRIVER_TB_NAME = "drivers";
    public String DRIVER_TB_DRIVER_ID = "driver_id";
    public String DRIVER_TB_DRIVER_NAME = "driver_name";
    public String DRIVER_TB_DRIVER_PHONE = "driver_phone";
    public String DRIVER_TB_DRIVER_EMAIL = "driver_email";
    public String DRIVER_TB_VEHICLE_ID = "driver_vehicle_id";

    public MDatabaseHelper(@Nullable Context context) {
        super(context, "FleetManagement.db", null, 1);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // "CREATE TABLE fleet (vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT, number_plate TEXT UNIQUE)"
        String createTableQuery = "CREATE TABLE " + FLEET_TB_NAME +
                " (" + FLEET_TB_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FLEET_TB_NUMBER_PLATE + " TEXT UNIQUE)";
        db.execSQL(createTableQuery);
        // Create Drivers' table
        // Might include worker ID
        String createDriversTableQuery = "CREATE TABLE " + DRIVER_TB_NAME + " (" + DRIVER_TB_DRIVER_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + DRIVER_TB_DRIVER_NAME + " TEXT, " +
                DRIVER_TB_DRIVER_PHONE + " TEXT UNIQUE, " + DRIVER_TB_DRIVER_EMAIL + " TEXT UNIQUE, " + DRIVER_TB_VEHICLE_ID + " INTEGER, " +
                "FOREIGN KEY(" + DRIVER_TB_VEHICLE_ID + ") REFERENCES " + FLEET_TB_NAME + "(" + FLEET_TB_VEHICLE_ID + "))";
        db.execSQL(createDriversTableQuery);
        //  "CREATE TABLE logs (log_id INTEGER PRIMARY KEY AUTOINCREMENT, parent_vehicle_id INTEGER,
        //  latitude TEXT, longitude TEXT, logged_time TEXT, FOREIGN KEY(parent_vehicle_id) REFERENCES FLEET_TABLE(vehicle_id))"
        String createDataTableQuery = "CREATE TABLE " + LOG_TB_NAME +
                " (" + LOG_TB_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOG_TB_PARENT_VEHICLE_ID +
                " INTEGER, " + LATITUDE + " REAL, " + LONGITUDE + " REAL, " + LOG_TB_LOGGED_TIME + " TEXT," +
                " FOREIGN KEY(" + LOG_TB_PARENT_VEHICLE_ID + ") REFERENCES " + FLEET_TB_NAME + "(vehicle_id))";
        db.execSQL(createDataTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Add data the very first data to the database
     *
     * @param carPlate   the car plate number
     * @param latitude   the latitude position of organisation
     * @param longitude  the longitude position of organisation
     * @param loggedTime the time the data was logged
     * @return true if data is added successfully, false otherwise
     */
    public boolean addFirstData(String carPlate, double latitude, double longitude, String loggedTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FLEET_TB_NUMBER_PLATE, carPlate);
        Log.d(M_TAG, "addData: Adding " + carPlate + " to " + FLEET_TB_NAME);
        long result = db.insert(FLEET_TB_NAME, null, contentValues);
        if (result == -1) {
            Log.d(M_TAG, "addData: Failed to add " + carPlate + " to " + FLEET_TB_NAME);
            return false;
        }
        contentValues.clear();
        contentValues.put(LOG_TB_PARENT_VEHICLE_ID, carPlate);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);
        contentValues.put(LOG_TB_LOGGED_TIME, loggedTime);
        Log.d(M_TAG, "addData: Adding " + carPlate + " to " + LOG_TB_NAME);
        result = db.insert(LOG_TB_NAME, null, contentValues);
        if (result == -1) {
            Log.d(M_TAG, "addData: Failed to add " + carPlate + " to " + LOG_TB_NAME);
            return false;
        }
        //if data as inserted incorrectly it will return -1
        return true;
    }

    public List<VehicleModel> fetchAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Query the FLEE_TABLE_NAME table for all data so as to get the ID of the vehicle
        // SELECT * FROM fleet
        Cursor cursor = db.rawQuery("SELECT * FROM " + FLEET_TB_NAME, null);
        Map<Integer, String> vehicleIdMap = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                vehicleIdMap.put(cursor.getInt(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        // SELECT * FROM logs
        // id(int) | vehicle_id(int) | latitude(double) | longitude(double) | logged_time(text)
        cursor = db.rawQuery("SELECT * FROM " + LOG_TB_NAME, null);
        List<VehicleModel> dataList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new VehicleModel("John Doe", vehicleIdMap.get(cursor.getInt(1)),
                        cursor.getDouble(2), cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }
}
