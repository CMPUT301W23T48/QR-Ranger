package com.example.qrranger;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    //Initialize Variables
    public MapView mapView;
    private GoogleMap gMap;
    public Bundle publicSavedInstanceState;

    private boolean isPermissionGranted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //MapView Variable
        mapView = (MapView) findViewById(R.id.mapView);

        //Check if Location permission is granted
        checkPermission();
        publicSavedInstanceState = savedInstanceState;
        mapView.onCreate(savedInstanceState);

    }

    /*
    Important for getting the map function working
    Uses Google maps to get location markers
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        //Add a marker and move the camera
        //Zoom in on Players current location
        LatLng home = new LatLng(53.62619228224998, -113.43941918391658);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));
        gMap.addMarker(new MarkerOptions().position(home).title("This is my home"));

        /*
        Checks if the Permission is granted
         */


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            gMap.setMyLocationEnabled(true);
        }


    }

    public void initMap(){
        mapView.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    //Check user Permission to access the map
    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                Toast.makeText(MapActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                initMap();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package", getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    //Update Location


    //Using the Search fragment
    public void onClick(View view){
        //Implement search function
    }


    //Search Location Function
    public void searchLocation(){

    }

    //Get all QR code longitude and latitude location


}