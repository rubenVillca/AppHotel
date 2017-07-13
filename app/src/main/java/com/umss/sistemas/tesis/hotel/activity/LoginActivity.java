package com.umss.sistemas.tesis.hotel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        container = findViewById(R.id.containerLogin);
        helperSQLiteInsert = new HelperSQLiteInsert(this);
    }

    /**
     * ir a activity createAccountActivity para crear usuario
     *
     * @param view:vista login
     */
    public void goCreateAccountActivity(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * cambiar activity a containerActivity->homeFragment
     *
     * @param view:activity login
     */
    public void goContainerActivity(View view) {
        //ocultar teclado
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        boolean cancel = isValidLogin();
        if (!cancel) {
            iniciarSession();
        }
    }

    /**
     * conectar con webServer e iniciar session
     */
    private void iniciarSession() {
        showProgress(true);

        final String emailText = ((EditText) findViewById(R.id.userName)).getText().toString();
        final String passText = ((EditText) findViewById(R.id.password)).getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("email", emailText);
        params.put("pass", passText);
        params.put("android", "android");

        client.post(Conexion.getUrlServer(Conexion.LOGIN), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    int idPerson;
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        idPerson = obj.getInt("idPerson");
                    } catch (JSONException e) {
                        goLoginActivity();
                        idPerson = 0;
                        showMessaje("Error de conexion");
                    }

                    switch (idPerson) {
                        case 0:
                            showMessaje("Nombre de usuario incorrecto");
                            goLoginActivity();
                            break;
                        case -1:
                            showMessaje("Contrasenia incorrectos");
                            goLoginActivity();
                            break;
                        case -2:
                            showMessaje("Cuenta no disponible");
                            goLoginActivity();
                            break;
                        default:
                            helperSQLiteInsert.syncUpLogin(idPerson, passText, 1);
                            showProgress(false);
                            goHomeFragment(idPerson);
                            showMessaje("Ha iniciado Sesion");
                            break;
                    }
                } else {
                    goLoginActivity();
                    showMessaje("Servidor no disponible");
                }
                showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goLoginActivity();
                showProgress(false);
                showMessaje("Servidor no esta disponible");
            }
        });
    }

    /**
     * cambiar de activityLogin a activityContainer->fragmentHome
     *
     * @param idPerson:identificador de idPerson
     */
    private void goHomeFragment(int idPerson) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("idPerson", idPerson);
        startActivity(intent);
    }

    private void goLoginActivity(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    /**
     * @return true: si el login es valido
     */
    public boolean isValidLogin() {
        EditText login = (EditText) findViewById(R.id.userName);
        EditText pass = (EditText) findViewById(R.id.password);
        login.setError(null);
        pass.setError(null);

        String loginText = login.getText().toString().trim();
        String passText = pass.getText().toString().trim();

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

        if (!isEmailValid(loginText) && loginText.length() > 0) {
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
        }
        return cancel;
    }

    /**
     * @param email:login que ingresa el usuario
     * @return true: si es email valid
     */
    private boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    /**
     * verifica si la contrasena ingresada es valida
     *
     * @param password:contrasena ingresada por el usuario
     * @return true: si la contrasena es valida
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onBackPressed() {}
}
