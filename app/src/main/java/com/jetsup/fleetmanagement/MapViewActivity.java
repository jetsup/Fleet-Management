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
import com.jetsup.fleetmanagement.model.LogModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapViewActivity extends AppCompatActivity implements ValueEventListener, Marker.OnMarkerClickListener {
    MapView mapView;
    MapController mapController;
    ArrayList<GeoPoint> geoPoints;
    ArrayList<Marker> markers;
    private int previousLogsCount = 0;
    private MarkerInfoWindow infoWindow;
    private GeoPoint openedInfoWindowPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_map_view);
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        // TODO: Display the loading dialog while initializing the database
        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setZoom(1);
        geoPoints = new ArrayList<>();
        markers = new ArrayList<>();
        // TODO: Get the current time to be used to determine when to fetch the data from te database
    }

    @Override
    protected void onResume() {
        super.onResume();
        // FIXME: The marker number are increasing on every resume
        // TODO: Check if the SQLite database has changed and get the latest data
        Map<Integer, LogModel> logs = DatabaseLogs.logModelMap;
        Set<Integer> keys = logs.keySet();
        Log.i(M_TAG, "[" + logs.size() + " DB LOGS MAPS] " + logs);
        Log.w(M_TAG, "Log Data: " + logs.get(1));
        if (previousLogsCount != logs.size()) {
            previousLogsCount = logs.size();
            for (int key : keys) {
                geoPoints.add(new GeoPoint(Objects.requireNonNull(logs.get(key)).getLatitude(),
                        Objects.requireNonNull(logs.get(key)).getLongitude()));
                previousLogsCount++;
            }
        }
        // Testing data
        geoPoints.add(new GeoPoint(-0.720341, 37.144993));
        geoPoints.add(new GeoPoint(23.8103, 90.4125)); // Dhaka
        geoPoints.add(new GeoPoint(40.7128, -74.0060)); // New York City
        geoPoints.add(new GeoPoint(51.5074, 0.1278)); // London
        geoPoints.add(new GeoPoint(35.6895, 139.6917)); // Tokyo
        previousLogsCount += 5;
        for (GeoPoint point : geoPoints) {
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener(MapViewActivity.this);
            mapView.getOverlays().add(marker);
            markers.add(marker);
        }
//        mapController.setCenter(geoPoints.get(0));
        mapView.invalidate();
        Log.i(M_TAG, "[MAP] Plotted " + geoPoints.size() + " points on the map.");
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

    @Override
    public boolean onMarkerClick(Marker marker, MapView mapView) {
        // Show driver name, vehicle plate number and last updated time
        if (infoWindow == null) {
            infoWindow = new MarkerInfoWindow(R.layout.marker_custom_bubble, mapView);
        } else if (infoWindow.isOpen() && marker.getPosition() == openedInfoWindowPosition) {
            infoWindow.close();
        } else {
            if (infoWindow.isOpen()) {
                infoWindow.close();
            }
            infoWindow = new MarkerInfoWindow(R.layout.marker_custom_bubble, mapView);
            infoWindow.open(marker, marker.getPosition(), 0, -marker.getIcon().getIntrinsicHeight());
            openedInfoWindowPosition = marker.getPosition();
        }
//        View layout = infoWindow.getView();
        return true;
    }

}
