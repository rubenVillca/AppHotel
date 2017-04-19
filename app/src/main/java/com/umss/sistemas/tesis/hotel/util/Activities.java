package com.umss.sistemas.tesis.hotel.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.DataBaseSQLiteHelper;
import com.umss.sistemas.tesis.hotel.model.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class Activities extends AppCompatActivity {
    private DataBaseSQLiteHelper sync;
    private SQLiteDatabase db;
    protected View progressView;
    protected View container;

    protected void showToolBar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
    }

    protected void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        progressView.setVisibility(show ? View.GONE : View.VISIBLE);
        progressView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        container.setVisibility(show ? View.VISIBLE : View.GONE);
        container.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    protected void showMesaje(String m) {
        Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
    }

    protected List<PersonModel> getContentPerson() {
        List<PersonModel> listPerson = new ArrayList<>();
        sync = new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_PERSON, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
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

                listPerson.add(personModel);

                cursor.moveToNext();
            }
        }
        return listPerson;
    }
}
