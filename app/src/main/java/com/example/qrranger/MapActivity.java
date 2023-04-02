package com.example.qrranger;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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

import java.util.List;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    //Initialize Variables
    private MapView mapView;
    private GoogleMap gMap;
    public boolean isPermissionGranted;
    List<Address> listGeoCoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        checkPermission();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        //Error checking
        try {
            listGeoCoder = new Geocoder(this).getFromLocationName("6320 164 Ave NW", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double longitude = listGeoCoder.get(0).getLongitude();
        double latitude = listGeoCoder.get(0).getLatitude();

        //
        Log.i("GOOGLE_MAP_TAG", "Address Longitude: " + String.valueOf(longitude) + "\n" + "Address Latitude: " + String.valueOf(latitude));


    }

    /*
    Important for getting the map function working
    Uses Google maps to get location markers
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        //Add a marker and move the camera
        LatLng home = new LatLng(53.62619228224998, -113.43941918391658);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        gMap.addMarker(new MarkerOptions().position(home).title("This is my home"));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            gMap.setMyLocationEnabled(true);
        }

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

    //Check and request location permission functions
    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                Toast.makeText(MapActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_AUTO_ROTATE_SETTINGS);
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



    //Using the Search fragment
    public void onClick(View view){
        //Implement search function
    }


    //Search Location Function
    public void searchLocation(){

    }


}
