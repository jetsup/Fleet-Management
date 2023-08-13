package com.jetsup.fleetmanagement;

import static com.jetsup.fleetmanagement.utils.GlobalsConstants.M_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapViewActivity extends AppCompatActivity implements ValueEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

//        myRef.setValue("Value"); // write to a database
        myRef.addValueEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String value = snapshot.getValue(String.class);
        Log.d(M_TAG, "Firebase Received Data:");
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.e(M_TAG, "Failed to read Value.",error.toException() );
    }
}
