package com.jetsup.fleetmanagement;

import static com.jetsup.fleetmanagement.util.GlobalsConstants.M_TAG;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

public class MapViewActivity extends AppCompatActivity implements ValueEventListener {
    MapView mapView;
    MapController mapController;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map_view);
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        // TODO: Display the loading dialog while initializing the database
        initializeDatabase();
        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(true);
        mapController= (MapController) mapView.getController();
        mapController.setZoom(13);
        GeoPoint geoPoint= new GeoPoint(-0.720341,37.144993);
        mapController.setCenter(geoPoint);
        // TODO: Get the current time to be used to determine when to fetch the data from te database
    }

    private void initializeDatabase() {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String value = snapshot.getValue(String.class);
        Log.d(M_TAG, "Firebase Received Data: " + value);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.e(M_TAG, "Failed to read Value.", error.toException());
    }
}
