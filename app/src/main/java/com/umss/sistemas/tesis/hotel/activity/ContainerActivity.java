package com.umss.sistemas.tesis.hotel.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import com.umss.sistemas.tesis.hotel.helper.DataBaseSQLiteHelper;
import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.util.Activities;
import com.umss.sistemas.tesis.hotel.util.Fragments;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContainerActivity extends Activities {

    private int idPerson;
    private DataBaseSQLiteHelper sync;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        obtainDataBundle();
        setActionBottomBar();
    }

    private void obtainDataBundle() {
        Bundle bundle = getIntent().getExtras();
        idPerson= bundle.getInt("idPerson");
    }

    private void setActionBottomBar() {
        BottomBar bottomBar=(BottomBar)findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.tabHome);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tabHome:
                        showFragment(new HomeFragment());
                        break;
                    case R.id.tabProfile:
                        //showProgress(true);
                        updateDataBaseProfile();
                        //showProgress(false);
                        break;
                    case R.id.tabSearch:
                        showFragment(new SearchFragment());
                        break;
                    case R.id.tabMessajeSend:
                        showFragment(new MessageSendFragment());
                        break;
                    case R.id.tabFrequently:
                        updateDataBaseFrequently();
                        break;
                }
            }
        });
    }

    private void updateDataBaseFrequently() {
        sync=new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME,null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db=sync.getWritableDatabase();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idPerson", idPerson);
        params.put("android", "android");

        client.post(Conexion.getUrlServer(3), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200) {
                    try {
                        JSONObject obj=new JSONObject(new String(responseBody));
                        AboutModel aboutModel=new AboutModel();
                        aboutModel.setId(1);
                        aboutModel.setPhoneHotel(obj.getInt("phoneHotel"));

                        aboutModel.setNameHotel(obj.getString("nameHotel"));
                        aboutModel.setMision(obj.getString("mision"));
                        aboutModel.setVision(obj.getString("vision"));
                        aboutModel.setAddress(obj.getString("address"));
                        aboutModel.setScope(obj.getString("scope"));
                        aboutModel.setHistory(obj.getString("history"));
                        aboutModel.setFundation(obj.getString("fundation"));
                        aboutModel.setWatchWord(obj.getString("watchWord"));
                        aboutModel.setObjetive(obj.getString("objetive"));
                        aboutModel.setEmail(obj.getString("email"));
                        aboutModel.setDescription(obj.getString("description"));
                        aboutModel.setLogoHotel(obj.getString("logoHotel"));
                        aboutModel.setAddressGPSX(obj.getString("addressGPSX"));
                        aboutModel.setAddressGPSY(obj.getString("addressGPSY"));
                        aboutModel.setAddressImage(obj.getString("addressImage"));

                        ContentValues newRegister=new ContentValues();

                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ID,aboutModel.getId());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_PHONEHOTEL,aboutModel.getPhoneHotel());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_NAMEHOTEL,aboutModel.getNameHotel());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_MISION,aboutModel.getMision());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_VISION,aboutModel.getVision());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESS,aboutModel.getAddress());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_SCOPE,aboutModel.getScope());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_HISTORY,aboutModel.getHistory());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_FUNDATION,aboutModel.getFundation());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_WATCHWORD,aboutModel.getWatchWord());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_OBJETIVE,aboutModel.getObjetive());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_EMAIL,aboutModel.getEmail());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_DESCRIPTION,aboutModel.getDescription());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_LOGOHOTEL,aboutModel.getLogoHotel());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSX,aboutModel.getAddressGPSX());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSY,aboutModel.getAddressGPSY());
                        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE,aboutModel.getAddressImage());

                        db.execSQL("DELETE FROM "+DataBaseSQLiteHelper.TABLE_ABOUT);
                        db.insert(DataBaseSQLiteHelper.TABLE_PERSON,null,newRegister);

                        showMesaje("Perfil Actualizado exitosamente");
                    } catch (JSONException e) {
                        System.out.println("Datos no legibles");
                    }

                } else {
                    System.out.println("Servidor no disponible");
                }
                showFragment(new FrequentlyFragment());
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showFragment(new ProfileFragment());
                //showProgress(false);
                System.out.println("Servidor no esta disponible");
            }
        });
    }

    private void updateDataBaseProfile() {
        sync=new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME,null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db=sync.getWritableDatabase();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idPerson", idPerson);
        params.put("android", "android");

        client.post(Conexion.getUrlServer(2), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200) {
                    try {
                        JSONObject obj=new JSONObject(new String(responseBody));
                        PersonModel personModel=new PersonModel();
                        personModel.setIdPerson(idPerson);
                        personModel.setEmailPerson(obj.getString("email"));
                        personModel.setNamePerson(obj.getString("namePerson"));
                        personModel.setNameLastPerson(obj.getString("nameLastPerson"));
                        personModel.setSexPerson((byte) obj.getInt("sex"));
                        personModel.setCityPerson(obj.getString("city"));
                        personModel.setCountryPerson(obj.getString("country"));
                        personModel.setPointPerson(obj.getInt("point"));
                        personModel.setDateBornPerson(obj.getString("dateBorn"));
                        personModel.setDateRegisterPerson(obj.getString("dateRegister"));
                        personModel.setAddressPerson(obj.getString("address"));
                        personModel.setNumberPhone(obj.getInt("numberPhone"));
                        personModel.setNumberDocument(obj.getInt("numberDocument"));
                        personModel.setTypeDocument(obj.getString("typeDocument"));
                        personModel.setImgPerson(obj.getString("imageProfile"));

                        ContentValues newRegister=new ContentValues();

                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_ID,personModel.getIdPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_EMAIL,personModel.getEmailPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NAME,personModel.getNamePerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NAME_LAST,personModel.getNameLastPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_CITY,personModel.getCityPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_COUNTRY,personModel.getCountryPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_POINT,personModel.getPointPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_SEX,personModel.getSexPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_ADDRESS,personModel.getAddressPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_IMG_PERSON,personModel.getImgPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_DATE_BORN,personModel.getDateBornPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_DATE_REGISTER,personModel.getDateRegisterPerson());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_TYPE_DOCUMENT,personModel.getTypeDocument());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NUMBER_DOCUMENT,personModel.getNumberDocument());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NUMBER_PHONE,personModel.getNumberPhone());
                        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_STATE,1);

                        db.execSQL("DELETE FROM "+DataBaseSQLiteHelper.TABLE_PERSON);
                        db.insert(DataBaseSQLiteHelper.TABLE_PERSON,null,newRegister);

                        showMesaje("Perfil Actualizado exitosamente");
                    } catch (JSONException e) {
                        System.out.println("Datos no legibles");
                    }

                } else {
                    System.out.println("Servidor no disponible");
                }
                showFragment(new ProfileFragment());
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showFragment(new ProfileFragment());
                //showProgress(false);
                System.out.println("Servidor no esta disponible");
            }
        });
    }


    private void showFragment(Fragments fragment) {
        if (fragment!=null) {
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

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
