package com.umss.sistemas.tesis.hotel.SyncSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.umss.sistemas.tesis.hotel.helper.PersonSqliteHelper;
import com.umss.sistemas.tesis.hotel.model.PersonModel;

import java.util.ArrayList;
import java.util.List;

public class SyncSQLiteHotel {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Hotel";
    private SQLiteDatabase db;
    private Context context;
    private PersonSqliteHelper personSqliteTable;

    public SyncSQLiteHotel(Context context) {
        this.context=context;
        personSqliteTable=new PersonSqliteHelper(context,DATABASE_NAME,null,DATABASE_VERSION);

        db=personSqliteTable.getWritableDatabase();
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
    public void close() {
        db.close();
    }
}
