package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.fragments.ContactFragment;
import com.umss.sistemas.tesis.hotel.fragments.FrequentlyFragment;
import com.umss.sistemas.tesis.hotel.fragments.HomeFragment;
import com.umss.sistemas.tesis.hotel.fragments.ProfileFragment;
import com.umss.sistemas.tesis.hotel.fragments.SearchFragment;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContainerActivity extends ActivityParent {
    private int idPerson;
    private int progressSync;
    private ProgressBar progressBarAdvanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        container = findViewById(R.id.container);
        progressBarAdvanced = (ProgressBar) findViewById(R.id.progressBarAdvanced);
        helperSQLiteObtain = new HelperSQLiteObtain(this);

        setActionBottomBar();
        obtainDataBundle();
    }

    private void obtainDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idPerson = bundle.getInt("idPerson");
            syncSQLite();
        } else {
            idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();
        }
    }

    /**
     * carga el menu principal de la app en la parte inferior
     */
    private void setActionBottomBar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.tabHome);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tabHome:
                        goFragment(new HomeFragment());
                        break;
                    case R.id.tabProfile:
                        goProfileFragment();
                        break;
                    case R.id.tabSearch:
                        goFragment(new SearchFragment());
                        break;
                    case R.id.tabMessajeSend:
                        goFragment(new ContactFragment());
                        break;
                    case R.id.tabFrequently:
                        goFrequentlyFragment();
                        break;
                }
            }
        });
    }

    private void goProfileFragment() {
        goFragment(new ProfileFragment());
    }

    private void goFrequentlyFragment() {
        goFragment(new FrequentlyFragment());
    }

    private void goFragment(FragmentParent fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override//boton de atras del teclado
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        helperSQLiteInsert = new HelperSQLiteInsert(this);

        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_complaints:
                intent = new Intent(this, ComplaintsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_suggestion:
                intent = new Intent(this, SuggestionActivity.class);
                startActivity(intent);
                break;
            case R.id.action_frequently:
                break;
            case R.id.action_logout:
                helperSQLiteInsert.logoutAction();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_sync:
                syncSQLite();
                break;
        }
        return true;
    }

    private void updateProgressSync() {
        progressSync += 1;
        switch (progressSync) {
            case 1:
                syncFrequently();
                break;
            case 2:
                syncSiteTour();
                break;
            case 3:
                syncLocation();
                break;
            case 4:
                syncConsume();
                break;
            case 5:
                syncHistory();
                break;
            case 6:
                syncCalendar();
                break;
            case 7:
                syncMessages();
                break;
            case 8:
                syncServiceFood();
                break;
            case 9:
                syncOffer();
                break;
            case 10:
                syncAbout();
                break;
            case 11:
                syncService();
                break;
            case 12:
                syncCheck();
                break;
        }

        progressBarAdvanced.setProgress((int) (1.0 * 100 * progressSync / 13));
        if (progressSync == 13) {
            showProgress(false);
            progressBarAdvanced.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * sincronizar la base de datos SQLite
     */
    private void syncSQLite() {
        progressSync = 0;
        progressBarAdvanced.setProgress(progressSync);
        progressBarAdvanced.setVisibility(View.VISIBLE);
        showProgress(true);

        helperSQLiteInsert = new HelperSQLiteInsert(this);
        syncProfile();
    }

    private void syncFrequently() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.FREQUENTLY), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpFrequently(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Servidor no disponible");
                }
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                updateProgressSync();
                System.out.println("Servidor no esta disponible");
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla profile
     */
    private void syncProfile() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idPerson", idPerson);
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.PROFILE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        helperSQLiteInsert.syncUpPerson(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Servidor no disponible");
                }
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no esta disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla siteTour y siteToutImage
     */
    private void syncSiteTour() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });

    }

    /**
     * Conectar con el webServer y sincronizar la tabla location
     */
    private void syncLocation() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void syncConsume() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.CHECK), params, new AsyncHttpResponseHandler() {
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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void syncHistory() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.CHECK), params, new AsyncHttpResponseHandler() {
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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Calendar
     */
    private void syncCalendar() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Messages
     */
    private void syncMessages() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tablaMenuFood
     */
    private void syncServiceFood() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla offer
     */
    private void syncOffer() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla about
     */
    private void syncAbout() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla service
     */
    private void syncService() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void syncCheck() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.getUrlServer(Conexion.CHECK), params, new AsyncHttpResponseHandler() {
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
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                updateProgressSync();
            }
        });
    }
}
