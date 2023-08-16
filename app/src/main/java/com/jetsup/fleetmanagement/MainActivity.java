package com.jetsup.fleetmanagement;

import static com.jetsup.fleetmanagement.util.GlobalsConstants.M_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jetsup.fleetmanagement.helper.MDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: Create an SQLLite database to store data offline to minimize query limit
        try (MDatabaseHelper databaseHelper = new MDatabaseHelper(MainActivity.this)) {
            databaseHelper.getWritableDatabase();
            if (databaseHelper.addFirstData("KCH268D", -0.720341, 37.144993, "2020-10-10 10:10:10")) {
                Log.e(M_TAG, "DATABASE: Creation failed");
                Toast.makeText(this, "Could not create add the data item to the database.", Toast.LENGTH_LONG).show();
            }
        }
        Button btnChangeView = findViewById(R.id.btnChangeView);
        btnChangeView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapViewActivity.class)));
    }
}
