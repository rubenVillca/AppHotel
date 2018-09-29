package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.LoginModel;

public class ServiceProfile {
    private SQLiteDatabase db;

    public ServiceProfile(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * verificar q la cuenta del usuario este activa en la base de datos SQLite
     *
     * @return true: si la cuenta del usuario esta activa
     */
    public boolean isAccountActive() {
        LoginModel loginModel = getLoginModel();
        return loginModel.getState() > 0;
    }

    /**
     * Lee de la base de datos de sqlite los datos del login
     *
     * @return LoginModel: estado de la cuenta
     */
    public LoginModel getLoginModel() {
        LoginModel loginModel = new LoginModel();
        Cursor cursor = db.rawQuery("select * from " + DBSQLite.TABLE_LOGIN, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                loginModel = obtainLoginModelCursor(cursor);
                cursor.moveToNext();
            }
        }

        return loginModel;
    }

    private LoginModel obtainLoginModelCursor(Cursor cursor) {
        LoginModel loginModel = new LoginModel();

        loginModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_LOGIN_ID_PERSON)));
        loginModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_LOGIN_STATE)));
        loginModel.setPassword(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_LOGIN_PASSWORD)));

        return loginModel;
    }
}
