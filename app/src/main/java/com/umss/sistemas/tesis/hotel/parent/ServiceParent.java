package com.umss.sistemas.tesis.hotel.parent;

import android.database.sqlite.SQLiteDatabase;

public abstract class ServiceParent {
    protected SQLiteDatabase db;

    public ServiceParent(SQLiteDatabase db) {
        this.db = db;
    }
}
