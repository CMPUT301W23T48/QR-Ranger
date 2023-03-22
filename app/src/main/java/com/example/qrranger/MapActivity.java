package com.example.qrranger;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import android.Manifest;

import com.example.qrranger.databinding.ActivityMainBinding;
import com.example.qrranger.databinding.ActivityMapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.List;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    List<Address> listGeoCoder;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        gMap = googleMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Gets the SupportMapFragment and notifies the user when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //Error chekcing
        try{
            listGeoCoder = new Geocoder(this).getFromLocationName("China",100);
            } catch (Exception e) {
            e.printStackTrace();
        }

        double longitude = listGeoCoder.get(0).getLongitude();
        double latitude = listGeoCoder.get(0).getLatitude();

        //Ask for permission
        Log.i("GOOGLE_MAP_TAG","Address Longitude: " + String.valueOf(longitude) + "\n" + "Address Latitude: " + String.valueOf(latitude));
    }

}

