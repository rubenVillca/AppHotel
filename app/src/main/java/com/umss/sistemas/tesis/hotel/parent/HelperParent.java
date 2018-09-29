package com.umss.sistemas.tesis.hotel.parent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;

/**
 * Created by ruben on 10/05/2017
 */

public abstract class HelperParent {
    protected SQLiteDatabase db;

    public HelperParent(Context context) {
        DBSQLite sync = new DBSQLite(context, DBSQLite.DATABASE_NAME, null, DBSQLite.DATABASE_VERSION);
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
