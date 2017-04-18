package com.umss.sistemas.tesis.hotel.activity;

import android.content.ContentValues;
import android.database.Cursor;
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
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.util.Activities;
import com.umss.sistemas.tesis.hotel.util.Fragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

        syncronizeDataBase();
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
                Fragments fragment = null;
                switch (tabId){
                    case R.id.tabHome:
                        fragment=new HomeFragment();
                        break;
                    case R.id.tabProfile:
                        fragment=new ProfileFragment();
                        break;
                    case R.id.tabSearch:
                        fragment=new SearchFragment();
                        break;
                    case R.id.tabMessajeSend:
                        fragment=new MessageSendFragment();
                        break;
                    case R.id.tabFrequently:
                        fragment=new FrequentlyFragment();
                        break;
                }
                if (fragment!=null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    private void syncronizeDataBase() {
        sync=new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME,null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db=sync.getWritableDatabase();
        if (db!=null){
            insertPersonSQLite();
        }
    }

    private void insertPersonSQLite() {
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
                        personModel.setPointPerson(obj.getInt("pointPerson"));
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
                    } catch (JSONException e) {
                        showMesaje("Error de conexion");
                    }

                } else {
                    showMesaje("Servidor no disponible");
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showMesaje("Servidor no esta disponible");
            }
        });
    }


    @Override
    public void onBackPressed() {}

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public List<PersonModel> getAllTablePerson(){
        Cursor cursor=db.rawQuery("select * from "+ DataBaseSQLiteHelper.TABLE_PERSON,null);
        List<PersonModel> listPerson=new ArrayList<>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                PersonModel personModel=new PersonModel();
                personModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_ID)));
                personModel.setNamePerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_NAME)));
                personModel.setNameLastPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_NAME_LAST)));
                personModel.setCityPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_CITY)));
                personModel.setAddressPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_ADDRESS)));
                personModel.setDateBornPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_DATE_BORN)));
                personModel.setDateRegisterPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_DATE_REGISTER)));
                personModel.setEmailPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_EMAIL)));
                personModel.setPointPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_POINT)));
                personModel.setCountryPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_COUNTRY)));
                personModel.setSexPerson((byte) cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_SEX)));
                personModel.setImgPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_IMG_PERSON)));

                listPerson.add(personModel);

                cursor.moveToNext();
            }
        }
        return listPerson;
    }
}
