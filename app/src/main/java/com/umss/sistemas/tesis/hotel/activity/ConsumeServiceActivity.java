package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ConsumeServiceActivity extends ActivityParent {
    private Spinner unitSpinnerConsume;
    private Spinner timeDurationSpinnerConsume;
    private Spinner timeStartSpinnerConsume;
    private EditText detailEditTextConsume;
    private TextView costTotalTextViewConsume;
    private ServiceModel serviceModel;
    private ServicePriceModel servicePriceModelMin;
    private int idCheck;
    private int idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_service);
        super.showToolBar("Soliciar servicio", true);
        container=findViewById(R.id.scrollConsumeService);

        initVar();
        initContent();
        updateCost();
    }

    private void initVar() {
        serviceModel = (ServiceModel) getIntent().getExtras().getSerializable("serviceModel");
        helperSQLiteObtain = new HelperSQLiteObtain(this);
        idCheck = helperSQLiteObtain.getCheckModel(0, 1, 2).get(0).getId();
        idPerson=helperSQLiteObtain.getLoginModel().getIdPerson();
    }

    private void initContent() {
        String[] valueUnit = new String[10];
        for (int i = 0; i< valueUnit.length; i++){
            valueUnit[i]=String.valueOf(i+1);
        }

        unitSpinnerConsume = (Spinner) findViewById(R.id.unitSpinnerConsume);
        unitSpinnerConsume.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueUnit));
        unitSpinnerConsume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] valueDuration = {"30","60","90","120","150","180"};
        timeDurationSpinnerConsume = (Spinner) findViewById(R.id.timeDurationSpinnerConsumeService);
        timeDurationSpinnerConsume.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueDuration));
        timeDurationSpinnerConsume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] valueTime = new String[36];
        for (int i = 0; i < valueTime.length; i++) {
            if (i % 2 == 0)
                valueTime[i] = String.valueOf(((5+i) / 2) + ":00");
            else
                valueTime[i] = String.valueOf(((5+i) / 2) + ":30");
        }
        timeStartSpinnerConsume = (Spinner) findViewById(R.id.timeStartSpinnerConsumeService);
        timeStartSpinnerConsume.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueTime));

        detailEditTextConsume = (EditText) findViewById(R.id.detailEditTextConsume);
        costTotalTextViewConsume = (TextView) findViewById(R.id.costTotalTextViewConsume);
    }

    /**
     * actualizar el costo al cambiar un item de los spinner
     * y actualizar el costo minimo
     */
    private void updateCost() {
        int unit = Integer.parseInt(unitSpinnerConsume.getSelectedItem().toString());
        servicePriceModelMin =null;
        for (ServicePriceModel servicePriceModel : serviceModel.getServicePrice()) {
            if (servicePriceModelMin == null) {
                servicePriceModelMin = servicePriceModel;
            }
            if ((unit%servicePriceModel.getServicePriceUnit()) == 0) {
                if ((servicePriceModel.getServicePricePrice() / servicePriceModel.getServicePriceUnit()) < (servicePriceModelMin.getServicePricePrice() / servicePriceModelMin.getServicePriceUnit()))
                    servicePriceModelMin = servicePriceModel;
            }
        }
        if (servicePriceModelMin != null)
            costTotalTextViewConsume.setText(String.valueOf(servicePriceModelMin.getServicePricePrice() * (1.0*unit/servicePriceModelMin.getServicePriceUnit())));
    }

    public void sendConsumeService(View view) {
        showProgress(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson",idPerson);
        params.put("idCheck", idCheck);
        params.put("idCost",servicePriceModelMin.getServicePriceId());
        params.put("pointObtain",servicePriceModelMin.getServicePricePointObtain());
        params.put("pointRequired",servicePriceModelMin.getServicePricePointRequired());
        params.put("idService", serviceModel.getId());
        params.put("unit", unitSpinnerConsume.getSelectedItem().toString());
        params.put("duration", timeDurationSpinnerConsume.getSelectedItem().toString());
        params.put("timeStart", timeStartSpinnerConsume.getSelectedItem().toString());
        params.put("detail", detailEditTextConsume.getText().toString());
        params.put("costTotal", costTotalTextViewConsume.getText());

        client.post(Conexion.getUrlServer(Conexion.CONSUME_SERVICE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));

                        goHomeActivity();
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                showProgress(false);
            }
        });
    }

    private void goHomeActivity() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }
}
