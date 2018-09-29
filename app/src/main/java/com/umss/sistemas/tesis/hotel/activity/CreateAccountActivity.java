package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceInsert;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class CreateAccountActivity extends ActivityParent implements View.OnClickListener {

    private Button btnRegistrar;
    private EditText emailInput;
    private EditText passText, passText2, nameText, appText, phoneText;
    private RadioButton sexRadioH, sexRadioM;
    private String email;
    private String pass;
    private String pass2;
    private String name;
    private String nameApp;
    private String phone;
    private boolean sexH;
    private boolean sexM;
    private View focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_create_account), true);
        container=findViewById(R.id.layoutCreateAccountActivity);
        getDataView();
    }

    private void getDataView() {
        emailInput = (EditText) findViewById(R.id.email);
        passText = (EditText) findViewById(R.id.passwordCreateAccout);
        passText2 = (EditText) findViewById(R.id.repeatPassword);
        nameText = (EditText) findViewById(R.id.personName);
        appText = (EditText) findViewById(R.id.personLastName);
        phoneText = (EditText) findViewById(R.id.numberPhone);
        sexRadioH = (RadioButton) findViewById(R.id.radioButtonH);
        sexRadioM = (RadioButton) findViewById(R.id.radioButtonM);
        btnRegistrar = (Button) findViewById(R.id.btnCreateAcount);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        focusView = null;
        if (v.getId() == btnRegistrar.getId()) {
            registerUser();
        }
    }

    private void registerUser() {
        email = emailInput.getText().toString();
        pass = passText.getText().toString();
        pass2 = passText2.getText().toString();
        name = nameText.getText().toString();
        nameApp = appText.getText().toString();
        phone = phoneText.getText().toString();
        sexH = sexRadioH.isChecked();
        sexM = sexRadioM.isChecked();
        boolean cancel = isEmptyInput();
        if (!cancel)
            cancel = isValidInput();

        if (!cancel) {
            registerPost();
        }
    }

    private void registerPost() {
        showProgress(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("email", email);
        params.put("pass", pass);
        params.put("name", name);
        params.put("nameAp", nameApp);
        params.put("phone", phone);
        params.put("sex", sexH);

        client.post(Conexion.CREATE_ACCOUNT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    int idPerson;
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        idPerson = obj.getInt("idPerson");
                    } catch (JSONException e) {
                        idPerson = 0;
                        showMessaje("Error en el servidor");
                    }
                    if (idPerson>0)
                        iniciarSession(idPerson);
                    else
                        goCreateAccount();
                } else {
                    showMessaje("Error en tiempo de respuesta de lado del servidor");
                }
                showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                System.out.println("Servidor no disponible intente nuevamente");
            }
        });
    }

    private void iniciarSession(int idPerson) {
        ServiceInsert =new ServiceInsert(this);

        if (idPerson > 0) {
            showMessaje("Usuario registrado exitosamente");
            ServiceInsert.syncUpLogin(idPerson, pass, 1);
            goHomeContainer(idPerson);
        }
        if (idPerson == -1) {
            showMessaje("Nombre de usuario no disponible");
        }
        if (idPerson == -2) {
            appText.setError(getString(R.string.error_field_email_repeat));
            focusView = appText;
            showMessaje("Error en la consulta");
        }
    }

    private void goHomeContainer(int idPerson) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("idPerson", idPerson);
        startActivity(intent);
    }

    private void goCreateAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    private boolean isEmptyInput() {
        boolean cancel = false;
        if (TextUtils.isEmpty(phone)) {
            phoneText.setError(getString(R.string.error_field_required));
            focusView = phoneText;
            cancel = true;
        }
        if (TextUtils.isEmpty(nameApp)) {
            appText.setError(getString(R.string.error_field_required));
            focusView = appText;
            cancel = true;
        }
        if (TextUtils.isEmpty(name)) {
            nameText.setError(getString(R.string.error_field_required));
            focusView = nameText;
            cancel = true;
        }
        if (TextUtils.isEmpty(pass2)) {
            passText2.setError(getString(R.string.error_field_required));
            focusView = passText2;
            cancel = true;
        }
        if (TextUtils.isEmpty(pass)) {
            passText.setError(getString(R.string.error_field_required));
            focusView = passText;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            emailInput.setError(getString(R.string.error_field_required));
            focusView = emailInput;
            cancel = true;
        }
        if (!(sexH || sexM)) {
            sexRadioH.setError("Seleccione un genero");
            cancel = true;
        }
        return cancel;
    }

    private boolean isValidInput() {
        boolean cancel = false;
        if (!isNumeric(phone)) {
            phoneText.setError(getString(R.string.error_field_phone));
            focusView = phoneText;
            cancel = true;
        }
        if (!isText(nameApp)) {
            appText.setError(getString(R.string.error_field_nameApp));
            focusView = appText;
            cancel = true;
        }
        if (!isText(name)) {
            nameText.setError(getString(R.string.error_field_name));
            focusView = nameText;
            cancel = true;
        }
        if (!pass.equals(pass2)) {
            passText2.setError(getString(R.string.error_field_pass2));
            focusView = passText2;
            cancel = true;
        }
        if (!isEmail(email)) {
            emailInput.setError(getString(R.string.error_field_email));
            focusView = emailInput;
            cancel = true;
        }

        return cancel;
    }

    private boolean isEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isText(String text) {
        boolean res = false;
        if (!text.trim().equals("")) res = true;
        return res;
    }

    private boolean isNumeric(String n) {
        boolean res = false;
        if (n.length() > 0) {
            try {
                Integer.parseInt(n.trim());
                res = true;
            } catch (NumberFormatException nfe) {
                res = false;
            }
        }
        return res;
    }
}
