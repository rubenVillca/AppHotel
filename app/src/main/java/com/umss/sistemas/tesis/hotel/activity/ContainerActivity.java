package com.umss.sistemas.tesis.hotel.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.fragments.FrequentlyFragment;
import com.umss.sistemas.tesis.hotel.fragments.HomeFragment;
import com.umss.sistemas.tesis.hotel.fragments.MessageSendFragment;
import com.umss.sistemas.tesis.hotel.fragments.ProfileFragment;
import com.umss.sistemas.tesis.hotel.fragments.SearchFragment;
import com.umss.sistemas.tesis.hotel.helper.PersonSqliteHelper;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.util.Activities;
import com.umss.sistemas.tesis.hotel.util.Fragments;

import java.util.ArrayList;
import java.util.List;

public class ContainerActivity extends Activities {

    private int idPerson;
    private PersonSqliteHelper sync;
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
        sync=new PersonSqliteHelper(this,PersonSqliteHelper.DATABASE_NAME,null,PersonSqliteHelper.DATABASE_VERSION);
        db=sync.getWritableDatabase();
        if (db!=null){
            ContentValues newRegister=new ContentValues();
            newRegister.put(PersonSqliteHelper.KEY_ID,1);
            newRegister.put(PersonSqliteHelper.KEY_NAME,"juan");
            newRegister.put(PersonSqliteHelper.KEY_NAME_LAST,"Perez");
            newRegister.put(PersonSqliteHelper.KEY_CITY,"cbba");
            newRegister.put(PersonSqliteHelper.KEY_COUNTRY,"Bolivia");
            newRegister.put(PersonSqliteHelper.KEY_ADDRESS,"Av. siempre viva");
            newRegister.put(PersonSqliteHelper.KEY_DATE_BORN,"2000-01-01");
            newRegister.put(PersonSqliteHelper.KEY_DATE_REGISTER,"2015-01-01");
            newRegister.put(PersonSqliteHelper.KEY_EMAIL,"juan@gmail.com");
            newRegister.put(PersonSqliteHelper.KEY_POINT,100);
            newRegister.put(PersonSqliteHelper.KEY_NAME,"juan");
            newRegister.put(PersonSqliteHelper.KEY_NAME,"juan");
            newRegister.put(PersonSqliteHelper.KEY_NAME,"juan");
            newRegister.put(PersonSqliteHelper.KEY_NAME,"juan");
            newRegister.put(PersonSqliteHelper.KEY_SEX,0);
            newRegister.put(PersonSqliteHelper.KEY_STATE,1);
            newRegister.put(PersonSqliteHelper.KEY_IMG_PERSON,"droide/imagen.jpg");

            db.insert(PersonSqliteHelper.TABLE_PERSON,null,newRegister);
        }
    }


    @Override
    public void onBackPressed() {}

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public List<PersonModel> getAllTablePerson(){
        Cursor cursor=db.rawQuery("select * from "+PersonSqliteHelper.TABLE_PERSON,null);
        List<PersonModel> listPerson=new ArrayList<>();
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                PersonModel personModel=new PersonModel();
                personModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(PersonSqliteHelper.KEY_ID)));
                personModel.setNamePerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_NAME)));
                personModel.setNameLastPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_NAME_LAST)));
                personModel.setCityPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_CITY)));
                personModel.setAddressPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_ADDRESS)));
                personModel.setDateBornPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_DATE_BORN)));
                personModel.setDateRegisterPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_DATE_REGISTER)));
                personModel.setEmailPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_EMAIL)));
                personModel.setPointPerson(cursor.getInt(cursor.getColumnIndex(PersonSqliteHelper.KEY_POINT)));
                personModel.setCountryPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_COUNTRY)));
                personModel.setSexPerson((byte) cursor.getInt(cursor.getColumnIndex(PersonSqliteHelper.KEY_SEX)));
                personModel.setImgPerson(cursor.getString(cursor.getColumnIndex(PersonSqliteHelper.KEY_IMG_PERSON)));

                listPerson.add(personModel);

                cursor.moveToNext();
            }
        }
        return listPerson;
    }
}
