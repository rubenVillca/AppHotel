package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.MessageModel;

import java.util.ArrayList;

public class ServiceMessage {
    private SQLiteDatabase db;

    public ServiceMessage(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * obtener de la base deatos SQLite la lista de mensajes de un usuario
     *
     * @param idMessage: id de mensaje
     * @return ArrayList<MessageModel>: idMensaje==0?lista de todos los mensjes: lista del mensaje con id requerido
     */
    public ArrayList<MessageModel> getMessageModel(int idMessage) {
        ArrayList<MessageModel> listMessageModel = new ArrayList<>();
        Cursor cursor;
        if (idMessage == 0) {
            cursor = db.rawQuery("select *" + " from " + DBSQLite.TABLE_MESSAGE, null);
        } else {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_MESSAGE
                    + " where " + DBSQLite.KEY_MESSAGE_ID + "=" + idMessage, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                MessageModel messageModel = obtainMessageModelCursor(cursor);

                listMessageModel.add(messageModel);

                cursor.moveToNext();
            }
        }
        return listMessageModel;
    }

    private MessageModel obtainMessageModelCursor(Cursor cursor) {
        MessageModel messageModel = new MessageModel();

        messageModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_ID)));
        messageModel.setTittle(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_TITTLE)));
        messageModel.setContent(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_CONTENT)));
        messageModel.setDateRecived(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_DATE_RECIVED)));
        messageModel.setTimeRecived(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_TIME_RECIVED)));
        messageModel.setRead(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_ISREAD)) > 0);
        messageModel.setEmailSender(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_EMAIL_SENDER)));
        messageModel.setNameSender(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_NAME_SENDER)));
        messageModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_ISACTIVE)) > 0);
        messageModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_TYPE)));

        return messageModel;
    }

}
