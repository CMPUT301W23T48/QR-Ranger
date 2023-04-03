package com.example.qrranger;


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * A fragment displaying a map view for the user to see and interact with QR codes.
 * The map shows the user's current location, allows searching for specific locations,
 * and displays markers for each QR code fetched from the Firestore database.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{


    //Initialize Variables
    public MapView mapView;
    private GoogleMap gMap;

    private boolean isPermissionGranted;

    /**
     * Called when the fragment is created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_view, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Checks if the user gives permission to track the device
        deviceLocationCheckPermission();

        // Initialize the search button and EditText
        Button findButton = view.findViewById(R.id.MapFindButton);
        EditText searchEditText = view.findViewById(R.id.MapSearch);

        // Set a click listener for the search button
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationName = searchEditText.getText().toString();
                searchLocation(locationName);
            }
        });

        // Load the QR codes and add markers for each of them
        loadQRCodes();

        return view;
    }


    /**
     * Called when the map is ready for use.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Enable MyLocation layer if the permission is granted
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);

            // Get the user's current location using LocationManager and LocationListener
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Move the camera to the user's current location
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));

                    // Stop location updates after the camera has moved to the user's current location
                    locationManager.removeUpdates(this);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            // Fall back to a default location if the permission is not granted
            LatLng defaultLocation = new LatLng(53.62619228224998, -113.43941918391658);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
        }
    }

    /**
     * Initializes the map and sets the OnMapReadyCallback.
     */
    public void initMap(){
        mapView.getMapAsync(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    /**
     * Checks if the user has granted the required permission for device location access.
     */
    private void deviceLocationCheckPermission(){
        Dexter.withContext(this.getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                //Initialize the map on permissionGranted
                initMap();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package", getContext().getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    /**
     * Searches for a location based on the input string and moves the camera to that location.
     *
     * @param locationName The name of the location to search for.
     */
    public void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng searchedLocation = new LatLng(address.getLatitude(), address.getLongitude());
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchedLocation, 15));
            } else {
                Toast.makeText(getContext(), "No location found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads QR codes from the Firestore database and adds markers for each QR code on the map.
     */
    private void loadQRCodes() {
        Database db = Database.getInstance();
        CollectionReference qrc = db.getCollection("qr_codes");
        qrc.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.contains("geolocation")) {
                            // Get the geolocation field and create a LatLng object
                            GeoPoint geoPoint = document.getGeoPoint("geolocation");
                            LatLng qrCodeLocation = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());

                            // Get the name and points information
                            String qrCodeName = document.getString("name");
                            String qrCodePoints = document.get("points").toString(); // Assuming points are stored as a String

                            // Build the marker title
                            String markerTitle = qrCodeName + " - " + qrCodePoints + " points";

                            // Add a marker for the QR code
                            gMap.addMarker(new MarkerOptions()
                                    .position(qrCodeLocation)
                                    .title(markerTitle));
                        }
                    }
                } else {
                    Log.e("MapFragment", "Error getting QR codes: ", task.getException());
                }
            }
        });
    }

}