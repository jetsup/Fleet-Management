package com.jetsup.fleetmanagement;

import static com.jetsup.fleetmanagement.util.GlobalsConstants.M_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jetsup.fleetmanagement.helper.MDatabaseHelper;
import com.jetsup.fleetmanagement.model.LogModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static MDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: Create an SQLLite database to store data offline to minimize query limit
        databaseHelper = new MDatabaseHelper(MainActivity.this);
        ArrayList<LogModel> allData = databaseHelper.fetchAllData();
        Log.i(M_TAG, "[DB DATA] " + allData.toString());
        Log.i(M_TAG, "[DB LOGS MAIN] " + DatabaseLogs.logModelMap);
        Button btnOpenMapView = findViewById(R.id.btnOpenMapView);
        btnOpenMapView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapViewActivity.class)));
    }
}
