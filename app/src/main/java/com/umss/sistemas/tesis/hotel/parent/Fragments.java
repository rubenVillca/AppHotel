package com.umss.sistemas.tesis.hotel.parent;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.AboutActivity;
import com.umss.sistemas.tesis.hotel.activity.CalendarActivity;
import com.umss.sistemas.tesis.hotel.activity.LocationActivity;
import com.umss.sistemas.tesis.hotel.activity.MenuFoodActivity;
import com.umss.sistemas.tesis.hotel.activity.MessagesActivity;
import com.umss.sistemas.tesis.hotel.activity.OffersActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveActivity;
import com.umss.sistemas.tesis.hotel.activity.ServicesActivity;
import com.umss.sistemas.tesis.hotel.activity.SitesTourActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Fragments extends Fragment implements View.OnClickListener {
    protected HelperSQLite helperSQLite;
    protected String mCurrentPhotoPath;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    private AsyncHttpClient client;
    private RequestParams params;

    /**
     * barra superior de la activity en la q esta el boton de atras y el nombre de la misma
     *
     * @param tittle:   nombre de la actividad
     * @param upButton: estado del boton true se se ve
     * @param view:     Activity en la q se muestra el boton
     */
    @SuppressWarnings("ConstantConditions")
    protected void showToolBar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    /**
     * boton flotante para acceder a los mensajes recibidos
     *
     * @param view: actividad en la estar el boton de mensaje
     */
    protected void showFloatingButtonMessage(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        helperSQLite = new HelperSQLite(getContext());
        client = new AsyncHttpClient();
        params = new RequestParams();

        Intent intent = null;
        switch (v.getId()) {
            case R.id.fab:
                intent = new Intent(getActivity(), MessagesActivity.class);
                break;
            case R.id.imageOffer:
                intent = new Intent(getActivity(), OffersActivity.class);
                break;
            case R.id.imageSiteTour:
                goSiteTour();
                break;
            case R.id.imageService:
                goService();
                break;
            case R.id.imageAboutHotel:
                goAbout();
                break;
            case R.id.imageReserve:
                intent = new Intent(getActivity(), ReserveActivity.class);
                break;
            case R.id.imageLocationMap:
                goLocation();
                break;
            case R.id.imageActivity:
                intent = new Intent(getActivity(), CalendarActivity.class);
                break;
            case R.id.imageServiceFood:
                intent = new Intent(getActivity(), MenuFoodActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * Conectar con el webServer y sincronizar la tabla about
     */
    private void goAbout() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.INFO), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLite.syncUpAbout(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goAboutActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goAboutActivity();
                //showProgress(false);
                System.out.println("Servidor no disponible");
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla service
     */
    private void goService() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.SERVICE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLite.syncUpService(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goServiceActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goServiceActivity();
                //showProgress(false);
                System.out.println("Servidor no disponible");
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla location
     */
    private void goLocation() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.INFO), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLite.syncUpAbout(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goLocationActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goLocationActivity();
                //showProgress(false);
                System.out.println("Servidor no disponible");
            }
        });


    }

    /**
     * Conectar con el webServer y sincronizar la tabla siteTour y siteToutImage
     */
    private void goSiteTour() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.SITES), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLite.syncUpSiteTour(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goSiteTourActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goSiteTourActivity();
                //showProgress(false);
                System.out.println("Servidor no disponible");
            }
        });

    }

    /**
     * Cambiar de activity a AboutActivity
     */
    private void goAboutActivity() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a serviceActivity
     */
    private void goServiceActivity() {
        Intent intent = new Intent(getActivity(), ServicesActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a locationActivity
     */
    private void goLocationActivity() {
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a siteTourActivity
     */
    private void goSiteTourActivity() {
        Intent intent = new Intent(getActivity(), SitesTourActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        if (helperSQLite != null)
            helperSQLite.destroy();
        super.onDestroy();
    }
}
