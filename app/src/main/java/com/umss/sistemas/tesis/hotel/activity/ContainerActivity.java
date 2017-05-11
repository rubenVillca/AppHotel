package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.fragments.FrequentlyFragment;
import com.umss.sistemas.tesis.hotel.fragments.HomeFragment;
import com.umss.sistemas.tesis.hotel.fragments.MessageSendFragment;
import com.umss.sistemas.tesis.hotel.fragments.ProfileFragment;
import com.umss.sistemas.tesis.hotel.fragments.SearchFragment;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.parent.FragmentParent;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContainerActivity extends ActivityParent {

    private int idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        helperSQLiteObtain =new HelperSQLiteObtain(this);
        obtainDataBundle();
        setActionBottomBar();
    }

    private void obtainDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            idPerson = bundle.getInt("idPerson");
        else {
            LoginModel loginModel = helperSQLiteObtain.getLoginModel();
            idPerson = loginModel.getIdPerson();
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
                        //showProgress(true);
                        goProfileFragment();
                        //showProgress(false);
                        break;
                    case R.id.tabSearch:
                        goFragment(new SearchFragment());
                        break;
                    case R.id.tabMessajeSend:
                        goFragment(new MessageSendFragment());
                        break;
                    case R.id.tabFrequently:
                        goFragment(new FrequentlyFragment());
                        break;
                }
            }
        });
    }

    private void goProfileFragment() {
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
                goFragment(new ProfileFragment());
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goFragment(new ProfileFragment());
                //showProgress(false);
                System.out.println("Servidor no esta disponible");
            }
        });
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

    @Override
    public void onBackPressed() {}
}
