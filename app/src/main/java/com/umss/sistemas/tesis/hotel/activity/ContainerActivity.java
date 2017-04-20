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
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.util.Activities;
import com.umss.sistemas.tesis.hotel.util.Fragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ContainerActivity extends Activities {

    private int idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        obtainDataBundle();
        setActionBottomBar();
    }

    private void obtainDataBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            idPerson = bundle.getInt("idPerson");
        else {
            List<PersonModel> listPerson = getContentPerson();
            if (!listPerson.isEmpty()) {
                PersonModel personModel = listPerson.get(0);
                idPerson = personModel.getIdPerson();
            }
        }
    }

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
                        goProfile();
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

    private void goProfile() {
        sync = new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("idPerson", idPerson);
        params.put("android", "android");

        client.post(Conexion.getUrlServer(2), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    PersonModel personModel = getPersonModel(responseBody);
                    setPersonModel(personModel);
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

    /**
     * ingresar personModel a la base de datos SQLite, si hay reemplazarlos
     * @param personModel: objeto a ingresar a la base dedd atos sqlite
     */
    private void setPersonModel(PersonModel personModel) {
        ContentValues newRegister = new ContentValues();

        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_ID, personModel.getIdPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_EMAIL, personModel.getEmailPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NAME, personModel.getNamePerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NAME_LAST, personModel.getNameLastPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_CITY, personModel.getCityPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_COUNTRY, personModel.getCountryPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_POINT, personModel.getPointPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_SEX, personModel.getSexPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_ADDRESS, personModel.getAddressPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_IMG_PERSON, personModel.getImgPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_DATE_BORN, personModel.getDateBornPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_DATE_REGISTER, personModel.getDateRegisterPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_TYPE_DOCUMENT, personModel.getTypeDocument());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NUMBER_DOCUMENT, personModel.getNumberDocument());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NUMBER_PHONE, personModel.getNumberPhone());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_STATE, 1);

        db.execSQL("DELETE FROM " + DataBaseSQLiteHelper.TABLE_PERSON);
        db.insert(DataBaseSQLiteHelper.TABLE_PERSON, null, newRegister);
    }

    /**
     * obtener el person model a partir del json recivido del webserver
     * @param responseBody: json recivido
     * @return PersonModel: modelo de la person cnvertido en objeto
     */
    private PersonModel getPersonModel(byte[] responseBody) {
        PersonModel personModel = new PersonModel();
        try {
            JSONObject obj = new JSONObject(new String(responseBody));
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
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
        }
        return personModel;
    }

    private void goFragment(Fragments fragment) {
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
    public void onBackPressed() {
    }
}
