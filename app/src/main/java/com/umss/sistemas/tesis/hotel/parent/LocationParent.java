package com.umss.sistemas.tesis.hotel.parent;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.util.DirectionFinderListener;
import com.umss.sistemas.tesis.hotel.util.Route;

import java.util.ArrayList;
import java.util.List;

public class LocationParent extends ActivityParent implements DirectionFinderListener {
    protected GoogleMap mMap;

    protected Location myLocationGPS;//origen
    protected Marker myMarkerGPS;//origen
    protected Marker markerHotel;
    protected LatLng myCoordinatorGPS;//origen
    protected CameraUpdate cameraUpdate;

    protected static final int zoomMap = 16;
    protected static final int timeUpdate = 50;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    /**
     * actualizar cada 10 segundos la ubicacion del usuario
     *
     * @param location:nueva ubicacion recibida
     */
    protected void updateLocation(Location location) {
        if (myMarkerGPS != null)
            myMarkerGPS.remove();
        myLocationGPS = location;
        if (location != null) {
            LatLng locationGPS = new LatLng(myLocationGPS.getLatitude(), myLocationGPS.getLongitude());
            myMarkerGPS = mMap.addMarker(new MarkerOptions().position(locationGPS).title("Mi Posicion").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Espere un momento.",
                "Buscando ruta..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();


        for (Route ruta : routes) {
            ((TextView) findViewById(R.id.tvDuration)).setText(ruta.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(ruta.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title("Lugar de partida")
                    .snippet(ruta.startAddress)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(ruta.startLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(8);

            for (int i = 0; i < ruta.points.size(); i++)
                polylineOptions.add(ruta.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

            cameraUpdate = CameraUpdateFactory.newLatLngZoom(ruta.startLocation, zoomMap);
            mMap.animateCamera(cameraUpdate);
        }
    }
}
