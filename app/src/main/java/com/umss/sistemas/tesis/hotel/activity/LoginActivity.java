package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.util.Activities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        container=findViewById(R.id.containerLogin);
        progressView=findViewById(R.id.progress_bar);
    }

    public void goCreateAccount(View view){
        Intent intent =new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
    public void proccessLogin(View view){
        EditText login=(EditText)findViewById(R.id.userName);
        EditText pass=(EditText)findViewById(R.id.password);
        login.setError(null);
        pass.setError(null);

        String loginText=login.getText().toString().trim();
        String passText=pass.getText().toString().trim();


        boolean cancel = false;
        View focusView = null;

        if (!isPasswordValid(passText)) {
            pass.setError(getString(R.string.error_invalid_password));
            focusView = pass;
            cancel = true;
        }
        if (TextUtils.isEmpty(passText)) {
            pass.setError(getString(R.string.error_field_required));
            focusView = pass;
            cancel = true;
        }

        if (!isEmailValid(loginText)&&loginText.length()>0) {
            login.setError(getString(R.string.error_invalid_email));
            focusView = login;
            cancel = true;
        }

        if (TextUtils.isEmpty(loginText)) {
            login.setError(getString(R.string.error_field_required));
            focusView = login;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            iniciarSession();
            showProgress(false);
        }
    }

    private void iniciarSession() {
        final String emailText = ((EditText) findViewById(R.id.userName)).getText().toString();
        final String passText = ((EditText) findViewById(R.id.password)).getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", emailText);
        params.put("pass", passText);
        params.put("android", "android");

        client.post(Conexion.getUrlServer(0), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200) {
                    String idUser="";
                    try {
                        JSONObject obj=new JSONObject(new String(responseBody));
                        idUser=obj.getString("idPerson");
                    } catch (JSONException e) {
                        showMesaje("Error de conexion");
                    }
                    int idPerson;
                    try {
                        idPerson=Integer.parseInt(idUser);
                    } catch (NumberFormatException n) {
                        idPerson=0;
                    }
                    switch (idPerson) {
                        case  0:showMesaje("Nombre de usuario incorrecto");break;
                        case -1:showMesaje("Contrasenia incorrectos");break;
                        case -2:showMesaje("Cuenta no disponible");break;
                        default:
                            showMesaje("Ha iniciado Sesion");
                            changeToHome(idPerson);
                            break;
                    }
                } else {
                    showMesaje("Servidor no disponible");
                }
                showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                showMesaje("Servidor no esta disponible");
            }
        });
    }

    private void changeToHome(int idPerson) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("idPerson",idPerson);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
