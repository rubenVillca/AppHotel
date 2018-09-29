package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.fragments.AboutFragment;
import com.umss.sistemas.tesis.hotel.fragments.ConsumeFragment;
import com.umss.sistemas.tesis.hotel.fragments.HomeFragment;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ContainerActivity extends ActivityParent {
    private int idPerson;
    private int progressSync;
    private ProgressBar progressBarAdvanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        container = findViewById(R.id.layoutContainerActivity);

        //cabecera de la app
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*getSupportActionBar().setHomeButtonEnabled(true);//icono en el toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(true); //optional*/


        // Setting ViewPager for each Tabs
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //pestanas de la app
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        progressBarAdvanced = findViewById(R.id.progressBarAdvanced);
        serviceHelper = new ServiceHelper(this);

        obtainDataBundle();
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Lista");
        adapter.addFragment(new ConsumeFragment(), "Consumo");
        adapter.addFragment(new AboutFragment(), "Acerca de ..");
        viewPager.setAdapter(adapter);
    }

    private void obtainDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idPerson = bundle.getInt("idPerson");
            syncSQLite();
        } else {
            idPerson = serviceHelper.getLoginModel().getIdPerson();
        }
        showProgress(false);
    }

    @Override//boton de atras del teclado
    public void onBackPressed() {
        //moveTaskToBack(true);//minimizar app
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_sync:
                syncSQLite();
                break;
            case R.id.action_settings:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_suggestion:
                intent = new Intent(this, SuggestionActivity.class);
                startActivity(intent);
                break;
            case R.id.action_complaints:
                intent = new Intent(this, ComplaintsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                serviceHelper.logoutAction();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     * boton flotante para acceder a los mensajes recibidos
     *
     * @param view: actividad en la estar el boton de mensaje
     */
    public void goMessage(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContainerActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
        });
    }


    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
                syncCalendar();
                break;
            case 4:
                syncMessages();
                break;
            case 5:
                syncServiceFood();
                break;
            case 6:
                syncOffer();
                break;
            case 7:
                syncAbout();
                break;
            case 8:
                syncService();
                break;
            case 9:
                syncCheck();
                break;
        }

        progressBarAdvanced.setProgress((int) (1.0 * 100 * progressSync / 10));
        if (progressSync == 10) {
            showProgress(false);
            progressBarAdvanced.setVisibility(View.GONE);
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

        serviceHelper = new ServiceHelper(this);
        syncProfile();
    }

    /**
     * Conectar con el webServer y sincronizar la tabla profile
     */
    private void syncProfile() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idPerson", idPerson);
        params.put("android", "android");

        client.post(Conexion.PROFILE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpPerson(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Servidor no disponible, informacion de perfil de usuario no actualizada");
                }
                updateProgressSync();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no esta disponible informacion de perfil dee usuario no actualizda");
                updateProgressSync();
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar las preguntas frecuentes
     */
    private void syncFrequently() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");

        client.post(Conexion.FREQUENTLY, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200) {
                            try {
                                JSONObject obj = new JSONObject(new String(responseBody));
                                serviceHelper.syncUpFrequently(obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Servidor no disponible, preguntas frecuentes no actualizadas");
                        }
                        updateProgressSync();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        updateProgressSync();
                        System.out.println("Servidor no esta disponible");
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

        client.post(Conexion.SITES, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpSiteTour(obj);
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
                System.out.println("Servidor no disponible, lugares turistics no actualizadas");
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

        client.post(Conexion.CALENDAR, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpCalendar(obj);
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
                System.out.println("Servidor no disponible, actividades no actualizadas");
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

        client.post(Conexion.MESSAGES, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpMessages(obj);
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
                System.out.println("Servidor no disponible, bandeja de mensajes no actualizada");
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

        client.post(Conexion.FOOD_MENU, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpFoodMenu(obj);
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
                System.out.println("Servidor no disponible, menu de alimentos no actualizada");
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

        client.post(Conexion.OFFER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpOffer(obj);
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
                System.out.println("Servidor no disponible, offertas no actualizadas");
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

        client.post(Conexion.INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpAbout(obj);
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
                System.out.println("Servidor no disponible, informacion de hotel no actualizada");
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

        client.post(Conexion.SERVICE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpService(obj);
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
                System.out.println("Servidor no disponible, informacion de servicio no actualizada");
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

        client.post(Conexion.CHECK, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpCheck(obj);
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
                System.out.println("Servidor no disponible, informacion de check no disponible");
                updateProgressSync();
            }
        });
    }
}
