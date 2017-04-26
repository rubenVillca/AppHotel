package com.umss.sistemas.tesis.hotel.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.AboutModel;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AboutModel aboutModel;

    private Location myLocationGPS;
    private CameraUpdate cameraUpdate;
    private Marker myMarkerGPS;

    private static final int zoomMap=12;
    private static final int timeUpdate=50;

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
        verifyActiveGPS();
    }

    /**
     * insertar en el mapa la ubucacion del hotel
     */
    private void addMarker() {
        HelperSQLite helperSQLite = new HelperSQLite(this);
        aboutModel = helperSQLite.getAboutModel();

        LatLng hotelCoordinator = new LatLng(Float.parseFloat(aboutModel.getAddressGPSX()), Float.parseFloat(aboutModel.getAddressGPSY()));

        mMap.addMarker(new MarkerOptions()
                .position(hotelCoordinator)
                .title(aboutModel.getNameHotel())
                .snippet(aboutModel.getAddress()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(hotelCoordinator));

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(hotelCoordinator, zoomMap);
    }

    /**
     * comprobar que el gps este activo
     */
    private void verifyActiveGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdate, 0, locListener);
            myLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        addMyLocation();
    }

    /**
     * insertar mi ubicacion en el mapa
     */
    private void addMyLocation() {
        LatLng coordenadas;
        if (myLocationGPS != null) {
            coordenadas=new LatLng(myLocationGPS.getLatitude(), myLocationGPS.getLongitude());
            myMarkerGPS = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi Posicion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenadas, zoomMap);

        }

        mMap.animateCamera(cameraUpdate);
    }

    /**
     * actualizar cada 10 segundos la ubicacion del usuario
     * @param location:nueva ubicacion recibida
     */
    private void updateLocation(Location location) {
        if (myMarkerGPS != null)
            myMarkerGPS.remove();
        myLocationGPS=location;
        if (location!=null) {
            LatLng locationGPS = new LatLng(myLocationGPS.getLatitude(), myLocationGPS.getLongitude());
            myMarkerGPS = mMap.addMarker(new MarkerOptions().position(locationGPS).title("Mi Posicion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(LocationActivity.this, s+" Habilitado", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(LocationActivity.this, s+" Deshabilitado", Toast.LENGTH_LONG).show();

        }
    };

}
