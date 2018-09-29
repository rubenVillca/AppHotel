package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContactActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        container = findViewById(R.id.message_form);
        services = new Services(this);
        showToolBar(getResources().getString(R.string.toolbar_tittle_contact), true);
        setSpinner();
        showButtonMessage();
    }

    /**
     * cargar lista de tipos de usuarios en el spinner
     */
    private void setSpinner() {
        Spinner spinner = findViewById(R.id.contactSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rol_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void showButtonMessage() {
        Button button = findViewById(R.id.btnContactSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.contactEditText);
                String messsage = editText.getText().toString();

                EditText titleMessage = findViewById(R.id.contactTitle);
                String title = titleMessage.getText().toString();

                Spinner spin = findViewById(R.id.contactSpinner);
                String receiver = spin.getSelectedItem().toString();
                receiver = receiver.equals("Administrador") ? "admin" : "recepcion";

                if (!title.isEmpty() && !messsage.isEmpty())
                    sendMessage(title, messsage, receiver);
                else {
                    editText.setError("Ingrese un titulo y un contenido");
                    titleMessage.setError("Ingrese un titulo y un contenido");
                }
            }
        });
    }

    /**
     * enviar mensaje de sugerencia al webserver
     *
     * @param title     :titulo del mensaje
     * @param messsage  :mensaje a enviar
     * @param receiver: usuaraio/s a los q se esta enviando el mensaje
     */
    private void sendMessage(String title, String messsage, String receiver) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        services = new Services(this);
        int idPerson = services.getLoginModel().getIdPerson();

        params.put("message", messsage);
        params.put("tittle", title);
        params.put("receiver", receiver);
        params.put("idPerson", idPerson);
        params.put("android", "android");

        client.post(Conexion.CONTACT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    int idMessage = 0;
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        idMessage = obj.getInt("isSave");
                    } catch (JSONException e) {
                        showMessage("Error de consulta");
                        //showProgress(false);
                    }

                    if (idMessage > 0) {
                        Intent intent = new Intent(ContactActivity.this, ContainerActivity.class);
                        startActivity(intent);
                        showMessage("Mensaje Enviado");
                    }
                    if (idMessage == 0) {
                        showMessage("No se envio su mensaje intente nuevamente");
                    }
                    if (idMessage < 0) {
                        showMessage("Error al insertar a la BD del webserver");
                    }
                    //showProgress(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //showProgress(false);
                showMessage("No se ha podido establecer conecion con el servidor");
            }
        });
    }

    private void showMessage(String message) {
        super.showMessaje(message);
    }
}
