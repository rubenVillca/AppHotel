package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceArticle extends ServiceParent {
    public ServiceArticle(SQLiteDatabase db) {
        super(db);
    }

    /**
     * obtener de la base datos SQLite la lista de articulos entregados al ingresar
     *
     * @param idConsum: id de consumo
     * @return ArrayList<ArticleModel>: lista de articulos entregados al momento del registro
     */
    public ArrayList<ArticleModel> getArticleModel(int idConsum) {
        ArrayList<ArticleModel> listArticleModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_ARTICLE
                + " where " + DBSQLite.KEY_ARTICLE_ID_KEY_CONSUM + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ArticleModel articleModel = obtainArticleModelCursor(cursor);

                listArticleModel.add(articleModel);

                cursor.moveToNext();
            }
        }
        return listArticleModel;
    }

    private ArticleModel obtainArticleModelCursor(Cursor cursor) {
        ArticleModel articleModel = new ArticleModel();

        articleModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_ID)));
        articleModel.setIdKeyConsum(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_ID_KEY_CONSUM)));
        articleModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_NAME)));
        articleModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_DESCRIPTION)));
        articleModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_IS_ACTIVE)) > 0);

        return articleModel;
    }

    /**
     * guardar los ariculos q recibio el huesped base de datos SQLIte
     *
     * @param articleModels:lista de articulos q el cliente recibio al ingrear al hotel  formato JAVa
     */
    public void insertArticleSQLite(ArrayList<ArticleModel> articleModels) {
        for (ArticleModel articleModel : articleModels) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_ARTICLE_ID, articleModel.getId());
            contentValues.put(DBSQLite.KEY_ARTICLE_ID_KEY_CONSUM, articleModel.getIdKeyConsum());
            contentValues.put(DBSQLite.KEY_ARTICLE_NAME, articleModel.getName());
            contentValues.put(DBSQLite.KEY_ARTICLE_DESCRIPTION, articleModel.getDescription());
            contentValues.put(DBSQLite.KEY_ARTICLE_IS_ACTIVE, articleModel.isActive()?1:0);

            if (db.insert(DBSQLite.TABLE_ARTICLE, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    public ArrayList<ArticleModel> getArticleModelJSON(JSONObject obj) {
        ArrayList<ArticleModel> articleArray = new ArrayList<>();

        try {
            JSONArray articleJSONArray = obj.getJSONArray("articles");

            for (int j = 0; j < articleJSONArray.length(); j++) {
                ArticleModel articleModel = new ArticleModel();

                JSONObject articleObject = articleJSONArray.getJSONObject(j);

                articleModel.setId(articleObject.getInt("ID_ARTICLE"));
                articleModel.setIdKeyConsum(articleObject.getInt("ID_CONSUME_SERVICE"));
                articleModel.setName(articleObject.getString("NAME_ARTICLE"));
                articleModel.setDescription(articleObject.getString("DESCRIPTION_ARTICLE"));
                articleModel.setActive(articleObject.getInt("VALUE_STATE_ARTICLE") > 0);

                articleArray.add(articleModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return articleArray;
    }
}
