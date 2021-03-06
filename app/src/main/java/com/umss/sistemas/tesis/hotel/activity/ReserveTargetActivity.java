package com.umss.sistemas.tesis.hotel.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.ServicePriceConsumeModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ReserveTargetActivity extends ActivityParent implements View.OnClickListener {
    private ServicePriceConsumeModel servicePriceConsumeModel;
    private boolean isMember;
    private int idCheck;
    private int nRoom;
    private int nAdult;
    private int nBoy;
    private int idTypeRoom;
    private double priceEstimated;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner spinnerType;
    private EditText editTextNumberTarget;
    private EditText editTextCCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_target);
        container = findViewById(R.id.layoutReserveTargetActivity);
        super.showToolBar("Tarjeta de credito", true);

        builtBundle();
        verifyCheck();
        init();
    }

    private void verifyCheck() {
        if (idCheck>0){
            showProgressUnit(true);
            goReserveSave();
        }
    }

    private void builtBundle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isMember =Objects.requireNonNull(getIntent().getExtras()).getBoolean("isMember");
            idCheck =getIntent().getExtras().getInt("idCheck");
            servicePriceConsumeModel = (ServicePriceConsumeModel) getIntent().getExtras().getSerializable("servicePriceConsumeModel");
            priceEstimated=Double.parseDouble(getIntent().getExtras().getString("priceEstimated"));
            idTypeRoom = Integer.parseInt(getIntent().getExtras().getString("idTypeRoom"));
            nRoom = Integer.parseInt(getIntent().getExtras().getString("nRoom"));
            nAdult = Integer.parseInt(getIntent().getExtras().getString("nAdult"));
            nBoy = Integer.parseInt(getIntent().getExtras().getString("nBoy"));
            dateIn = getIntent().getExtras().getString("dateIn");
            timeIn = getIntent().getExtras().getString("timeIn");
            dateOut = getIntent().getExtras().getString("dateOut");
            timeOut = getIntent().getExtras().getString("timeOut");
        }
    }

    private void init() {
        spinnerMonth = findViewById(R.id.spinnerMonthTargetReserve);
        String valoresMonth[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMonth.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresMonth));

        String valoresYear[] = new String[10];
        for (int i = 0; i < valoresYear.length; i++) {
            valoresYear[i] = String.valueOf(i + Calendar.getInstance().get(Calendar.YEAR));
        }
        spinnerYear = findViewById(R.id.spinnerYearTargetReserve);
        spinnerYear.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresYear));

        String valoresType[] = {"Visa", "MasterCard", "PayPal"};
        spinnerType = findViewById(R.id.spinnerTypeTargetReserve);
        spinnerType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresType));

        editTextNumberTarget = findViewById(R.id.reserveNumberTargetReserve);
        editTextCCV = findViewById(R.id.reserveCCVTargetReserve);

        Button button = findViewById(R.id.btnContinueReserveTarget);
        button.setOnClickListener(this);

        Button buttonCancel = findViewById(R.id.btnCancelReserveTarget);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        switch (v.getId()) {
            case R.id.btnContinueReserveTarget:
                alertBuilder.setMessage("¿Guardar reserva?");
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                goReserveSave();
                            }
                        });

                alertBuilder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertSave = alertBuilder.create();
                alertSave.show();
                break;
            case R.id.btnCancelReserveTarget:
                alertBuilder.setMessage("¿Cancelar reserva?");
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                goReserveVerify();
                                dialog.cancel();
                            }
                        });

                alertBuilder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertSave2 = alertBuilder.create();
                alertSave2.show();
                break;
        }
    }

    /**
     * enviar dtos de la reserva al servidor y guardar la reserva
     */
    private void goReserveSave() {
        showProgressUnit(true);
        serviceHelper = new ServiceHelper(this);
        int idPerson = serviceHelper.getLoginModel().getIdPerson();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idCheck", idCheck);
        params.put("isMember", isMember?idPerson:0);
        params.put("idPerson", idPerson);
        params.put("idCost", servicePriceConsumeModel.getIdKeyCost());
        params.put("priceEstimated",priceEstimated);
        params.put("nameTypeMoney", servicePriceConsumeModel.getNameTypeMoney());
        params.put("idTypeRoom", idTypeRoom);
        params.put("nRoom", nRoom);
        params.put("nAdult", nAdult);
        params.put("nBoy", nBoy);
        params.put("dateIn", String.valueOf(dateIn));
        params.put("timeIn", String.valueOf(timeIn));
        params.put("dateOut", String.valueOf(dateOut));
        params.put("timeOut", String.valueOf(timeOut));
        if (idCheck<=0) {
            params.put("numberTarget", editTextNumberTarget.getText().toString());
            params.put("ccvTarget", editTextCCV.getText().toString());
            params.put("yearTarget", spinnerYear.getSelectedItem().toString());
            params.put("monthTarget", String.valueOf(spinnerMonth.getSelectedItemPosition() + 1));
            params.put("typeTarget", String.valueOf(spinnerType.getSelectedItemPosition() + 1));
        }

        client.post(Conexion.RESERVE_SAVE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                int isReserve = 0;
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        isReserve = obj.getInt("isReserve");
                        goReserveVerify();
                    } catch (JSONException e) {
                        isReserve = -1;
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                if (isReserve > 0)
                    showMessaje("Reserva realizada correctamente");
                else
                    showMessaje("No se guardo la reserva");
                showProgressUnit(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showMessaje("No se pudo guardar los datos");
                showProgressUnit(false);
            }
        });
    }

    /**
     * Conectar con el webServer y sincronizar la tabla Check
     */
    private void goReserveVerify() {
        showProgressUnit(true);
        serviceHelper = new ServiceHelper(this);
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
                goReserveVerifyActivity();
                showProgressUnit(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                goReserveVerifyActivity();
                showProgressUnit(false);
            }
        });
    }

    /**
     * cambiar de activity a ReserveSearchActivity
     */
    private void goReserveVerifyActivity() {
        Intent intent = new Intent(this, ReserveVerifyActivity.class);
        startActivity(intent);
        finish();
    }
}
