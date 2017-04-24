package com.umss.sistemas.tesis.hotel.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.AboutModel;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AboutModel aboutModel;
    private static int zoomMap=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        addMarker();
        addLocation();
    }

    private void addLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            addMyLocation(location);
        }
    }

    private void addMarker() {
        HelperSQLite helperSQLite = new HelperSQLite(this);
        aboutModel = helperSQLite.getAboutModel();
        // Add a marker in Sydney and move the camera
        LatLng hotelMarker = new LatLng(Float.parseFloat(aboutModel.getAddressGPSX()), Float.parseFloat(aboutModel.getAddressGPSY()));
        mMap.addMarker(new MarkerOptions().position(hotelMarker).title(aboutModel.getNameHotel()).snippet(aboutModel.getAddress()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hotelMarker));
    }

    private void updateLocation(Location location) {
        addMarker();
        addMyLocation(location);
    }

    private void addMyLocation(Location location) {
        if (location != null) {
            LatLng locationActual = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(locationActual).title("Ubicacion actual").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationActual));

            CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(locationActual, zoomMap);
            mMap.animateCamera(miUbicacion);
        }else {
            CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(
                    new LatLng(Float.parseFloat(aboutModel.getAddressGPSX()),Float.parseFloat(aboutModel.getAddressGPSY())), zoomMap);
            mMap.animateCamera(miUbicacion);
        }


    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //updateLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(LocationActivity.this, "GPS Deshabilitado: " + s, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(LocationActivity.this, "GPS Habilitado: " + s, Toast.LENGTH_LONG).show();

        }
    };

}
