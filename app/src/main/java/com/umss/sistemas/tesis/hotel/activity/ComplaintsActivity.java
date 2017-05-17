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

public class ComplaintsActivity extends ActivityParent implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        showToolBar(getResources().getString(R.string.toolbar_tittle_complaints), true);

        Button button = (Button) findViewById(R.id.btnComplaintsSend);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnComplaintsSend) {
            TextView edit = (TextView) findViewById(R.id.complaintsEditText);
            String text = edit.getText().toString();

            sendComplaints(text);
        }
    }

    /**
     * enviar mensaje de quejas al webserver
     *
     * @param text:quejas
     */
    private void sendComplaints(String text) {
        helperSQLiteObtain=new HelperSQLiteObtain(this);
        int idPerson=helperSQLiteObtain.getLoginModel().getIdPerson();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("message",text);
        params.put("android","android");
        params.put("idPerson",idPerson);

        client.post(Conexion.getUrlServer(Conexion.COMPLAINTS), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    int idMessage=0;
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        idMessage=obj.getInt("isSave");
                    } catch (JSONException e) {
                        showMesaje("Error de consulta");
                        //showProgress(false);
                    }

                    if (idMessage>0) {
                        Intent intent = new Intent(ComplaintsActivity.this, ContainerActivity.class);
                        startActivity(intent);
                        showMesaje("Mensaje Enviado");
                    } else {
                        showMesaje("No se envio su mensaje intente nuevamente");
                    }
                    //showProgress(false);
                }else{
                    showMesaje("Servidor no disponible");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //showProgress(false);
                showMesaje("No se ha podido establecer conecion con el servidor");
            }
        });
    }
}