package com.jetsup.fleetmanagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jetsup.fleetmanagement.adapter.DetailedRecyclerAdapter;

public class DetailedActivity extends AppCompatActivity {
    RecyclerView detailedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        detailedRecyclerView = findViewById(R.id.detailedRecyclerView);
        DetailedRecyclerAdapter detailedRecyclerAdapter = new DetailedRecyclerAdapter();
        detailedRecyclerView.setAdapter(detailedRecyclerAdapter);
    }
}
