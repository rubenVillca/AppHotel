package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.services.ServiceCheck;
import com.umss.sistemas.tesis.hotel.util.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ReserveMemberActivity extends ActivityParent {
    private MemberModel memberModel;

    private EditText reserveMemberName;
    private EditText reserveMemberLastName;
    private EditText reserveMemberDateBorn;
    private EditText reserveMemberPhone;
    private EditText reserveMemberDocument;
    private EditText reserveMemberEmail;
    private EditText reserveMemberAddress;
    private RadioButton reserveMemberRadioHombre;
    private RadioButton reserveMemberRadioMujer;
    private EditText reserveMemberCity;
    private EditText reserveMemberCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_member);

        super.showToolBar("Huéspedes", true);

        container=findViewById(R.id.scrollReserveMember);
        updateBundle();
        initContent();
        updateContent();
    }

    private void initContent() {
        reserveMemberName = findViewById(R.id.reserveMemberName);
        reserveMemberLastName = findViewById(R.id.reserveMemberLastName);
        reserveMemberDateBorn = findViewById(R.id.reserveMemberDateBorn);
        reserveMemberPhone = findViewById(R.id.reserveMemberPhone);
        reserveMemberDocument = findViewById(R.id.reserveMemberDocument);
        reserveMemberEmail = findViewById(R.id.reserveMemberEmail);
        reserveMemberAddress = findViewById(R.id.reserveMemberAddress);
        reserveMemberRadioHombre = findViewById(R.id.reserveMemberRadioHombre);
        reserveMemberRadioMujer = findViewById(R.id.reserveMemberRadioMujer);
        reserveMemberCity = findViewById(R.id.reserveMemberCity);
        reserveMemberCountry = findViewById(R.id.reserveMemberCountry);
    }

    private void updateBundle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            memberModel = (MemberModel) Objects.requireNonNull(getIntent().getExtras()).getSerializable("member");
        }else {
            memberModel=new MemberModel();
            int idConsume=getIntent().getExtras().getInt("idConsume");
            memberModel.setIdKeyConsum(idConsume);
        }
    }

    private void updateContent() {
        if (!memberModel.getNamePerson().isEmpty()) {
            reserveMemberName.setText(memberModel.getNamePerson());
            reserveMemberLastName.setText(memberModel.getNameLastPerson());
            reserveMemberDateBorn.setText(memberModel.getDateBornPerson());
            reserveMemberPhone.setText(String.valueOf(memberModel.getNumberPhone()));
            reserveMemberDocument.setText(String.valueOf(memberModel.getNumberDocument()));
            reserveMemberEmail.setText(memberModel.getEmailPerson());
            reserveMemberAddress.setText(memberModel.getAddressPerson());
            reserveMemberRadioHombre.setChecked(memberModel.getSexPerson() == 1);
            reserveMemberRadioMujer.setChecked(memberModel.getSexPerson() == 0);
            reserveMemberCity.setText(memberModel.getCityPerson());
            reserveMemberCountry.setText(memberModel.getCountryPerson());
        }
    }

    public void goReserveVerifyActivity(View view) {
        Intent intent = new Intent(this, ReserveVerifyActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * enviar datos modificados de huesped para actualizarlo al webserver
     * @param view:boton de aceptar
     */
    public void goReserveCheck(View view) {
        showProgressUnit(true);
        serviceHelper = new ServiceHelper(this);

        SimpleDateFormat parseador = new SimpleDateFormat("MMM dd, yyyy",Locale.getDefault());
        SimpleDateFormat formateador = new SimpleDateFormat("yy-MM-dd",Locale.getDefault());

        Date dateInParse = new Date();
        try {
            dateInParse = parseador.parse(memberModel.getDateBornPerson());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateBorn=formateador.format(dateInParse);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        int sexPerson=-2;
        if (reserveMemberRadioHombre.isChecked())
            sexPerson=1;
        if (reserveMemberRadioMujer.isChecked())
            sexPerson=0;


        params.put("android", "android");
        params.put("idKeyConsum", memberModel.getIdKeyConsum());
        params.put("idPerson", memberModel.getIdPerson());
        params.put("sexPerson", sexPerson);
        params.put("pointPerson",memberModel.getPointPerson());
        params.put("numberDocument", reserveMemberDocument.getText().toString());
        params.put("numberPhone", reserveMemberPhone.getText().toString());
        params.put("emailPerson", reserveMemberEmail.getText().toString());
        params.put("namePerson",reserveMemberName.getText().toString());
        params.put("nameLastPerson", reserveMemberLastName.getText().toString());
        params.put("cityPerson", reserveMemberCity.getText().toString());
        params.put("countryPerson", reserveMemberCountry.getText().toString());
        params.put("addressPerson", reserveMemberAddress.getText().toString());
        params.put("imgPerson", memberModel.getImgPerson());
        params.put("dateBornPerson", dateBorn);
        params.put("dateRegisterPerson", memberModel.getDateRegisterPerson());
        params.put("typeDocument", memberModel.getTypeDocument());
        showProgress(true);
        client.post(Conexion.MEMBER_SAVE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        syncUpCheck();
                        showMessaje("Insertado");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showMessaje("Error de conexion");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showMessaje("no conectado");
                showProgressUnit(false);
            }
        });
    }

    private void syncUpCheck() {
        int idPerson = serviceHelper.getLoginModel().getIdPerson();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson", idPerson);

        client.post(Conexion.CHECK, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        serviceHelper.syncUpCheck(obj);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                goReserveCheckActivity();
                showProgressUnit(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                goReserveCheckActivity();
                showProgressUnit(false);
            }
        });
    }

    /**
     * Ir a la actividad reserveCheckActivity
     */
    private void goReserveCheckActivity() {
        Intent intent=new Intent(this,ReserveCheckActivity.class);
        startActivity(intent);
    }

    public void showDateBorn(View view) {
        Calendar calendar=Calendar.getInstance();
        DatePickerFragment datePickerFragmentOut = new DatePickerFragment();
        datePickerFragmentOut.setTextView(reserveMemberDateBorn,null,calendar);
        datePickerFragmentOut.show(getFragmentManager(), "datePicker");
    }
}
