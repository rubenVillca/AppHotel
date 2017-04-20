package com.umss.sistemas.tesis.hotel.util;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.AboutActivity;
import com.umss.sistemas.tesis.hotel.activity.CalendarActivity;
import com.umss.sistemas.tesis.hotel.activity.MenuFoodActivity;
import com.umss.sistemas.tesis.hotel.activity.MessagesActivity;
import com.umss.sistemas.tesis.hotel.activity.OffersActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveActivity;
import com.umss.sistemas.tesis.hotel.activity.ServicesActivity;
import com.umss.sistemas.tesis.hotel.activity.SitesTourActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DataBaseSQLiteHelper;
import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Fragments extends Fragment implements View.OnClickListener {
    protected DataBaseSQLiteHelper sync;
    protected SQLiteDatabase db;
    protected String mCurrentPhotoPath;
    protected static final int REQUEST_IMAGE_CAPTURE = 1;

    protected void showToolBar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fab:
                intent = new Intent(getActivity(), MessagesActivity.class);
                break;
            case R.id.imageOffer:
                intent = new Intent(getActivity(), OffersActivity.class);
                break;
            case R.id.imageSiteTour:
                intent = new Intent(getActivity(), SitesTourActivity.class);
                break;
            case R.id.imageService:
                intent = new Intent(getActivity(), ServicesActivity.class);
                break;
            case R.id.imageAboutHotel:
                goAbout();
                break;
            case R.id.imageReserve:
                intent = new Intent(getActivity(), ReserveActivity.class);
                break;
            case R.id.imageLocationMap:
                Toast.makeText(getContext(), "Localizanos", Toast.LENGTH_LONG).show();
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

    private void goAbout() {
        sync = new DataBaseSQLiteHelper(getContext(), DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");

        client.post(Conexion.getUrlServer(3), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    AboutModel aboutModel = getAboutModel(responseBody);
                    setAboutModel(aboutModel);

                } else {
                    System.out.println("Servidor no disponible");
                }
                goActivityAbout();
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goActivityAbout();
                //showProgress(false);
                System.out.println("Servidor no disponible");
            }
        });
    }

    private AboutModel getAboutModel(byte[] responseBody) {
        AboutModel aboutModel = new AboutModel();
        try {
            JSONObject obj = new JSONObject(new String(responseBody));
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
            aboutModel.setType(obj.getString("typeHotel"));
            aboutModel.setSiteWeb(obj.getString("siteWeb"));
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
        }
            return aboutModel;
        }

    private void setAboutModel(AboutModel aboutModel) {
        ContentValues newRegister = new ContentValues();

        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ID, aboutModel.getId());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_PHONEHOTEL, aboutModel.getPhoneHotel());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_NAMEHOTEL, aboutModel.getNameHotel());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_MISION, aboutModel.getMision());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_VISION, aboutModel.getVision());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESS, aboutModel.getAddress());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_SCOPE, aboutModel.getScope());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_HISTORY, aboutModel.getHistory());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_FUNDATION, aboutModel.getFundation());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_WATCHWORD, aboutModel.getWatchWord());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_OBJETIVE, aboutModel.getObjetive());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_EMAIL, aboutModel.getEmail());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_DESCRIPTION, aboutModel.getDescription());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_LOGOHOTEL, aboutModel.getLogoHotel());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSX, aboutModel.getAddressGPSX());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSY, aboutModel.getAddressGPSY());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE, aboutModel.getAddressImage());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_TYPEHOTEL, aboutModel.getType());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL, aboutModel.getSiteWeb());

        db.execSQL("DELETE FROM " + DataBaseSQLiteHelper.TABLE_ABOUT);
        db.insert(DataBaseSQLiteHelper.TABLE_ABOUT, null, newRegister);
    }

    private void goActivityAbout() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    /**
     * Lee de la base de datos de sqlite los datos de los perfiles
     *
     * @return List<PersonModel>: lista de perfiles
     */
    protected List<PersonModel> getContentPerson() {
        List<PersonModel> listPerson = new ArrayList<>();
        sync = new DataBaseSQLiteHelper(getContext(), DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_PERSON, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                PersonModel personModel = getPersonModel(cursor);

                listPerson.add(personModel);

                cursor.moveToNext();
            }
        }
        return listPerson;
    }

    @NonNull
    private PersonModel getPersonModel(Cursor cursor) {
        PersonModel personModel = new PersonModel();
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
        return personModel;
    }

    protected void showFloatingButtonMessage(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }
}
