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
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        progressBarAdvanced = (ProgressBar) findViewById(R.id.progressBarAdvanced);
        helperSQLiteObtain = new HelperSQLiteObtain(this);

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
            idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();
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
            case R.id.action_logout:
                helperSQLiteInsert.logoutAction();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_sync:
                syncSQLite();
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
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContainerActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
        });
    }


    static class Adapter extends FragmentPagerAdapter {
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

        public void addFragment(Fragment fragment, String title) {
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
                syncConsume();
                break;
            case 4:
                syncHistory();
                break;
            case 5:
                syncCalendar();
                break;
            case 6:
                syncMessages();
                break;
            case 7:
                syncServiceFood();
                break;
            case 8:
                syncOffer();
                break;
            case 9:
                syncAbout();
                break;
            case 10:
                syncService();
                break;
            case 11:
                syncCheck();
                break;
        }

        progressBarAdvanced.setProgress((int) (1.0 * 100 * progressSync / 12));
        if (progressSync == 12) {
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

        client.post(Conexion.getUrlServer(Conexion.FREQUENTLY), params,
                new AsyncHttpResponseHandler() {
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
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
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
