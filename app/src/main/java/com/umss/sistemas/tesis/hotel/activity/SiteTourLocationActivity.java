package com.umss.sistemas.tesis.hotel.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.LocationParent;
import com.umss.sistemas.tesis.hotel.util.DirectionFinder;

import java.io.UnsupportedEncodingException;

public class SiteTourLocationActivity extends LocationParent implements OnMapReadyCallback{
    private SiteTourModel siteTourModel;//destino

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showToolBar("Mapa Lugar Turistico", true);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play serviceHelper is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play serviceHelper and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle=getIntent().getExtras();
        int idSiteTour= 0;
        if (bundle != null) {
            idSiteTour = bundle.getInt("idSiteTour");
        }
        serviceHelper = new ServiceHelper(this);
        siteTourModel = serviceHelper.getSiteTourModel(idSiteTour).get(0);

        addMarker();
        verifyActiveGPS();
        trazarRuta();

        mMap.animateCamera(cameraUpdate);
    }

    private void trazarRuta() {
        if (myCoordinatorGPS != null && markerHotel != null && myMarkerGPS != null) {
            String origin = myCoordinatorGPS.latitude + "," + myCoordinatorGPS.longitude;
            String destination = siteTourModel.getGpsLatitudeSite() + "," + siteTourModel.getGpsLongitudeSite();
            try {
                new DirectionFinder(this, origin, destination).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * insertar en el mapa la ubucacion del hotel
     */
    private void addMarker() {
        LatLng destinoCoordinator = new LatLng(siteTourModel.getGpsLatitudeSite(), siteTourModel.getGpsLongitudeSite());

        markerHotel = mMap.addMarker(new MarkerOptions()
                .position(destinoCoordinator)
                .title(siteTourModel.getNameSite())
                .snippet(siteTourModel.getAddressSite()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(destinoCoordinator));

        cameraUpdate = CameraUpdateFactory.newLatLngZoom(destinoCoordinator, zoomMap);
    }

    /**
     * comprobar que el gps este activo
     */
    private void verifyActiveGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeUpdate, 0, locListener);
                myLocationGPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (myLocationGPS==null){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeUpdate, 0, locListener);
                    myLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }else {
                System.out.println("La aplicacion no puede acceder al GPS del dispositivo");
            }
            mMap.setMyLocationEnabled(true);
        }

        addMyLocationGPS();
    }

    /**
     * insertar mi ubicacion en el mapa
     */
    private void addMyLocationGPS() {
        if (myLocationGPS != null) {
            myCoordinatorGPS = new LatLng(myLocationGPS.getLatitude(), myLocationGPS.getLongitude());
            myMarkerGPS = mMap.addMarker(new MarkerOptions().position(myCoordinatorGPS).title("Ubicacion Actual").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            cameraUpdate = CameraUpdateFactory.newLatLngZoom(myCoordinatorGPS, zoomMap);
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
            //Toast.makeText(SiteTourLocationActivity.this, s + " Habilitado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            //Toast.makeText(SiteTourLocationActivity.this, s + " Deshabilitado", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
