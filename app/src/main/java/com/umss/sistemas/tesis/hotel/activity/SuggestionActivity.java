package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SuggestionActivity extends ActivityParent implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        showToolBar(getResources().getString(R.string.toolbar_tittle_suggestion), true);
        container=findViewById(R.id.layoutSuggestionActivity);
        Button button = findViewById(R.id.btnSuggestionSend);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSuggestionSend) {
            TextView edit = findViewById(R.id.suggestionEditText);
            String text = edit.getText().toString();

            sendSuggestion(text);
        }
    }

    /**
     * enviar mensaje de sugerencia al webserver
     *
     * @param text:sugerencia
     */
    private void sendSuggestion(String text) {
        showProgress(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        helperSQLiteObtain=new HelperSQLiteObtain(this);
        int idPerson=helperSQLiteObtain.getLoginModel().getIdPerson();

        params.put("message",text);
        params.put("android","android");
        params.put("idPerson",idPerson);

        client.post(Conexion.SUGGESTION, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    int idMessage=0;
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        idMessage=obj.getInt("isSave");
                    } catch (JSONException e) {
                        showMessaje("Error de consulta");
                    }

                    if (idMessage>0) {
                        Intent intent = new Intent(SuggestionActivity.this, ContainerActivity.class);
                        startActivity(intent);
                        showMessaje("Mensaje Enviado");
                    }
                    if (idMessage==0) {
                        showMessaje("No se envio su mensaje intente nuevamente");
                    }
                    if (idMessage<0)
                        showMessaje("Error al insertar a la BD del webserver");

                }
                showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                showMessaje("No se ha podido establecer conecion con el servidor");
            }
        });
    }
}
