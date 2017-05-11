package com.umss.sistemas.tesis.hotel.parent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLiteHelper;

/**
 * Created by ruben on 10/05/2017
 */

public class HelperParent {
    protected SQLiteDatabase db;

    public HelperParent(Context context) {
        DBSQLiteHelper sync = new DBSQLiteHelper(context, DBSQLiteHelper.DATABASE_NAME, null, DBSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();
    }

    /**
     * cerrar conexion con  SQLite
     */
    public void destroy() {
        if (db != null)
            db.close();
    }
}
