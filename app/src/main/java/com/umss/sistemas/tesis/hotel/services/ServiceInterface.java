package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;

import org.json.JSONObject;

import java.util.ArrayList;

public interface ServiceInterface<T> {
    T getModelCursor(Cursor cursor);

    ArrayList<T> getModels(int id);

    ArrayList<T> getModelJSON(JSONObject object);

    void syncUp(JSONObject object);

    void insertSQLite(ArrayList<T> modelList);
}
