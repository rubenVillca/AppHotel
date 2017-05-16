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
import com.umss.sistemas.tesis.hotel.activity.ConsumeActivity;
import com.umss.sistemas.tesis.hotel.activity.HistoryActivity;
import com.umss.sistemas.tesis.hotel.activity.LocationActivity;
import com.umss.sistemas.tesis.hotel.activity.MenuFoodActivity;
import com.umss.sistemas.tesis.hotel.activity.MessagesActivity;
import com.umss.sistemas.tesis.hotel.activity.OffersActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveActivity;
import com.umss.sistemas.tesis.hotel.activity.ServicesActivity;
import com.umss.sistemas.tesis.hotel.activity.SitesTourActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FragmentParent extends Fragment implements View.OnClickListener {

    protected HelperSQLiteInsert helperSQLiteInsert;
    protected HelperSQLiteObtain helperSQLiteObtain;
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
    public void onDestroy() {
        if (helperSQLiteInsert != null)
            helperSQLiteInsert.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        helperSQLiteInsert = new HelperSQLiteInsert(getContext());
        helperSQLiteObtain = new HelperSQLiteObtain(getContext());

        client = new AsyncHttpClient();
        params = new RequestParams();

        switch (v.getId()) {
            case R.id.fab:
                goMessages();
                break;
            case R.id.imageOffer:
                goOffer();
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
                goReserve();
                break;
            case R.id.imageLocationMap:
                goLocation();
                break;
            case R.id.imageActivity:
                goCalendar();
                break;
            case R.id.imageServiceFood:
                goServiceFood();
                break;
            case R.id.imageHistory:
                goHistory();
                break;
            case R.id.imageConsum:
                goConsume();
                break;
        }
    }

    //****************************************GO****************************************************

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void goReserve() {
        int idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.RESERVE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpCheck(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goReserveActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goReserveActivity();
                //showProgress(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void goConsume() {
        int idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.RESERVE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpCheck(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goConsumeActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goConsumeActivity();
                //showProgress(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void goHistory() {
        int idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.RESERVE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpCheck(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goHistoryActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goHistoryActivity();
                //showProgress(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Calendar
     */
    private void goCalendar() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.CALENDAR), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpCalendar(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goCalendarActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goCalendarActivity();
                //showProgress(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Messages
     */
    private void goMessages() {
        int idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.MESSAGES), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpMessages(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goMessageActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goMessageActivity();
                //showProgress(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tablaMenuFood
     */
    private void goServiceFood() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.FOOD_MENU), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpFoodMenu(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goServiceFoodActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goServiceFoodActivity();
                //showProgress(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla offer
     */
    private void goOffer() {
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.OFFER), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpOffer(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goOfferActivity();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goOfferActivity();
                //showProgress(false);
            }
        });
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
                        helperSQLiteInsert.syncUpAbout(obj);
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
                        helperSQLiteInsert.syncUpService(obj);
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
                        helperSQLiteInsert.syncUpAbout(obj);
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
                        helperSQLiteInsert.syncUpSiteTour(obj);
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

    //*************************************GO_ACTIVITY**********************************************

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

    /**
     * cambiar de activity a OffersActivity
     */
    private void goOfferActivity() {
        Intent intent = new Intent(getActivity(), OffersActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a messageActivity
     */
    private void goMessageActivity() {
        Intent intent = new Intent(getActivity(), MessagesActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a MenuFoodActivity
     */
    private void goServiceFoodActivity() {
        Intent intent = new Intent(getActivity(), MenuFoodActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a CalendarActivity
     */
    private void goCalendarActivity() {
        Intent intent = new Intent(getActivity(), CalendarActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a HistoryActivity
     */
    private void goHistoryActivity() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a ConsumeActivity
     */
    private void goConsumeActivity() {
        Intent intent = new Intent(getActivity(), ConsumeActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar de activity a ReserveActivity
     */
    private void goReserveActivity() {
        Intent intent = new Intent(getActivity(), ReserveActivity.class);
        startActivity(intent);
    }

}
