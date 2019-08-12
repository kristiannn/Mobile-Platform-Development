
//
// Name                 Kristiyan Nenov
// Student ID           S1512113
// Programme of Study   Computer Games(Software Development)


package com.example.mpd_kristiyannenov_s1512113;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    float locLat = 0;
    float locLong = 0;
    String titleMaps = "";
    String location = "";
    float magnitude = 0;
    float depth = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        Intent intent = getIntent();
        titleMaps = intent.getStringExtra("description");
        location = intent.getStringExtra("location");
        magnitude = intent.getFloatExtra("magnitude", 0);
        depth = intent.getFloatExtra("depth", 0);
        locLat = intent.getFloatExtra("locLat", 0);
        locLong = intent.getFloatExtra("locLong", 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng earthquake = new LatLng(locLat, locLong);
        Marker earthquakeMarker = mMap.addMarker(new MarkerOptions().position(earthquake).title(titleMaps));
        earthquakeMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(earthquake));
    }
}
