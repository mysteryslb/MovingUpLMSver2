package com.example.asus.movinguplms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

public class SHSSchoolMapsActivity extends FragmentActivity
        implements  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitide, longitude;
    private int ProximityRadius = 100000;
    private GoogleApiClient googleApiClient;
    private DatabaseReference MapReference;
    private EditText searchmaptext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shsschool_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d("Maps", "Place selected: " + place.getName());
                addMarker(place);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public boolean checkUserLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitide = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null)
        {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You're here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(15));

        if (googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.allschools_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool1).title(SchoolMapsLocations.title1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool2).title(SchoolMapsLocations.title2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool3).title(SchoolMapsLocations.title3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool4).title(SchoolMapsLocations.title4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool6).title(SchoolMapsLocations.title6).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool7).title(SchoolMapsLocations.title7).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool8).title(SchoolMapsLocations.title8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool9).title(SchoolMapsLocations.title9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool10).title(SchoolMapsLocations.title10).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool11).title(SchoolMapsLocations.title11).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool12).title(SchoolMapsLocations.title12).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool13).title(SchoolMapsLocations.title13).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool16).title(SchoolMapsLocations.title16).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool17).title(SchoolMapsLocations.title17).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool18).title(SchoolMapsLocations.title18).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool19).title(SchoolMapsLocations.title19).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool20).title(SchoolMapsLocations.title20).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool21).title(SchoolMapsLocations.title21).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool22).title(SchoolMapsLocations.title22).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool23).title(SchoolMapsLocations.title23).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool24).title(SchoolMapsLocations.title24).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool26).title(SchoolMapsLocations.title26).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool27).title(SchoolMapsLocations.title27).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool28).title(SchoolMapsLocations.title28).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool29).title(SchoolMapsLocations.title29).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool30).title(SchoolMapsLocations.title30).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool31).title(SchoolMapsLocations.title31).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool33).title(SchoolMapsLocations.title33).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                break;


            case R.id.publicschools_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool6).title(SchoolMapsLocations.title6).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool13).title(SchoolMapsLocations.title13).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool16).title(SchoolMapsLocations.title16).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool22).title(SchoolMapsLocations.title22).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool31).title(SchoolMapsLocations.title31).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool33).title(SchoolMapsLocations.title33).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                break;

            case R.id.privateschools_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool1).title(SchoolMapsLocations.title1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool2).title(SchoolMapsLocations.title2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool3).title(SchoolMapsLocations.title3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool4).title(SchoolMapsLocations.title4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool7).title(SchoolMapsLocations.title7).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool8).title(SchoolMapsLocations.title8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool9).title(SchoolMapsLocations.title9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool10).title(SchoolMapsLocations.title10).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool11).title(SchoolMapsLocations.title11).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool12).title(SchoolMapsLocations.title12).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool17).title(SchoolMapsLocations.title17).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool18).title(SchoolMapsLocations.title18).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool19).title(SchoolMapsLocations.title19).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool20).title(SchoolMapsLocations.title20).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool21).title(SchoolMapsLocations.title21).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool23).title(SchoolMapsLocations.title23).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool24).title(SchoolMapsLocations.title24).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool26).title(SchoolMapsLocations.title26).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool27).title(SchoolMapsLocations.title27).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool28).title(SchoolMapsLocations.title28).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool29).title(SchoolMapsLocations.title29).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool30).title(SchoolMapsLocations.title30).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                break;

            case R.id.academic_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool1).title(SchoolMapsLocations.title1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool3).title(SchoolMapsLocations.title3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool4).title(SchoolMapsLocations.title4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool6).title(SchoolMapsLocations.title6).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool7).title(SchoolMapsLocations.title7).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool8).title(SchoolMapsLocations.title8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool9).title(SchoolMapsLocations.title9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool10).title(SchoolMapsLocations.title10).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool11).title(SchoolMapsLocations.title11).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool12).title(SchoolMapsLocations.title12).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool13).title(SchoolMapsLocations.title13).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool16).title(SchoolMapsLocations.title16).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool17).title(SchoolMapsLocations.title17).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool18).title(SchoolMapsLocations.title18).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool19).title(SchoolMapsLocations.title19).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool20).title(SchoolMapsLocations.title20).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool21).title(SchoolMapsLocations.title21).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool22).title(SchoolMapsLocations.title22).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool23).title(SchoolMapsLocations.title23).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool24).title(SchoolMapsLocations.title24).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool26).title(SchoolMapsLocations.title26).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool27).title(SchoolMapsLocations.title27).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool28).title(SchoolMapsLocations.title28).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool29).title(SchoolMapsLocations.title29).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool30).title(SchoolMapsLocations.title30).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool31).title(SchoolMapsLocations.title31).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool33).title(SchoolMapsLocations.title33).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                break;

            case R.id.tvl_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool1).title(SchoolMapsLocations.title1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool2).title(SchoolMapsLocations.title2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool4).title(SchoolMapsLocations.title4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool8).title(SchoolMapsLocations.title8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool9).title(SchoolMapsLocations.title9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool12).title(SchoolMapsLocations.title12).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool21).title(SchoolMapsLocations.title21).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool28).title(SchoolMapsLocations.title28).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool29).title(SchoolMapsLocations.title29).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                break;

            case R.id.sports_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool4).title(SchoolMapsLocations.title4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool21).title(SchoolMapsLocations.title21).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                break;

            case R.id.arts_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool9).title(SchoolMapsLocations.title9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool28).title(SchoolMapsLocations.title28).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                break;

            case R.id.search_nearby:
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool1).title(SchoolMapsLocations.title1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool2).title(SchoolMapsLocations.title2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool3).title(SchoolMapsLocations.title3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool4).title(SchoolMapsLocations.title4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool5).title(SchoolMapsLocations.title5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool6).title(SchoolMapsLocations.title6).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool7).title(SchoolMapsLocations.title7).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool8).title(SchoolMapsLocations.title8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool9).title(SchoolMapsLocations.title9).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool10).title(SchoolMapsLocations.title10).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool11).title(SchoolMapsLocations.title11).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool12).title(SchoolMapsLocations.title12).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool13).title(SchoolMapsLocations.title13).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool14).title(SchoolMapsLocations.title14).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool15).title(SchoolMapsLocations.title15).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool16).title(SchoolMapsLocations.title16).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool17).title(SchoolMapsLocations.title17).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool18).title(SchoolMapsLocations.title18).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool19).title(SchoolMapsLocations.title19).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool20).title(SchoolMapsLocations.title20).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool21).title(SchoolMapsLocations.title21).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool22).title(SchoolMapsLocations.title22).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool23).title(SchoolMapsLocations.title23).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool24).title(SchoolMapsLocations.title24).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool25).title(SchoolMapsLocations.title25).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool26).title(SchoolMapsLocations.title26).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool27).title(SchoolMapsLocations.title27).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool28).title(SchoolMapsLocations.title28).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool29).title(SchoolMapsLocations.title29).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool30).title(SchoolMapsLocations.title30).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool31).title(SchoolMapsLocations.title31).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool32).title(SchoolMapsLocations.title32).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                mMap.addMarker(new MarkerOptions().position(SchoolMapsLocations.locschool33).title(SchoolMapsLocations.title33).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                break;

        }
    }

    public void addMarker(Place p){
        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(p.getLatLng());
        markerOptions.title(p.getName()+"");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

    }
}
