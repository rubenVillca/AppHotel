package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

public class ServiceProfile extends ServiceParent {
    public ServiceProfile(SQLiteDatabase db) {
        super(db);
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
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBSQLite.TABLE_LOGIN, null);
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

    /**
     * sincornizar sqlite en la tabla loginModel para poder iniciar session
     *
     * @param idPerson: identificador de la cuenta
     * @param passText: contrasena de la cuenta
     * @param state:    estado de la cuenta
     */
    public void syncUpLogin(int idPerson, String passText, int state) {
        LoginModel loginModel = new LoginModel(idPerson, passText, state);
        insertLoginSQLite(loginModel);
    }

    /**
     * ingresar loginModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param loginModel: objeto a ingresar a la base de datos sqlite
     */
    private void insertLoginSQLite(LoginModel loginModel) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_LOGIN);

        ContentValues newRegister = new ContentValues();

        newRegister.put(DBSQLite.KEY_LOGIN_ID_PERSON, loginModel.getIdPerson());
        newRegister.put(DBSQLite.KEY_LOGIN_PASSWORD, loginModel.getPassword());
        newRegister.put(DBSQLite.KEY_LOGIN_STATE, loginModel.getState());

        if (db.insert(DBSQLite.TABLE_LOGIN, null, newRegister) == -1)
            System.out.println("Ocurrio un error al inserar la consulta en LoginModel");
    }

    /**
     * cerrar session del usuario, cambiar el estado de la tabla logout
     */
    public void logoutAction() {
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_LOGIN);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_PERSON);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_ABOUT);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_SERVICE);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_PRICE_SERVICE);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_SITE_TOUR);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_SITE_TOUR_IMAGE);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_OFFER);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_FOOD);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_FOOD_MENU);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_FOOD_PRICE);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_CHECK);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_CONSUM);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_ARTICLE);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_CARD);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_MEMBER);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_MESSAGE);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_ACTIVITY);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_FREQUENTLY);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_CONSUME_FOOD);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_OCCUPATION);
        db.execSQL("DELETE FROM "+ DBSQLite.TABLE_RESERVE);
    }
}
