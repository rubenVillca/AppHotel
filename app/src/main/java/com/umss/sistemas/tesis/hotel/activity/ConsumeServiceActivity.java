package com.umss.sistemas.tesis.hotel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ConsumeServiceActivity extends ActivityParent {
    private Spinner unitSpinnerConsume;
    private Spinner timeDurationSpinnerConsume;
    private Spinner timeStartSpinnerConsume;
    private EditText detailEditTextConsume;
    private TextView costTotalTextViewConsume;
    private ServiceModel serviceModel;
    private ServicePriceDetailModel servicePriceDetailModelMin;
    private int idCheck;
    private int idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_service);
        initVar();

        super.showToolBar(serviceModel.getName(), true);
        container = findViewById(R.id.scrollConsumeService);

        initContent();
        updateCost();
    }

    private void initVar() {
        serviceModel = (ServiceModel) getIntent().getExtras().getSerializable("serviceModel");
        helperSQLiteObtain = new HelperSQLiteObtain(this);
        idCheck = helperSQLiteObtain.getCheckModel(0, 1, 2).get(0).getId();
        idPerson = helperSQLiteObtain.getLoginModel().getIdPerson();
    }

    private void initContent() {
        String[] valueUnit = new String[10];
        for (int i = 0; i < valueUnit.length; i++) {
            valueUnit[i] = String.valueOf(i + 1);
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

        String[] valueDuration = {"30", "60", "90", "120", "150", "180"};
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
                valueTime[i] = String.valueOf(((5 + i) / 2) + ":00");
            else
                valueTime[i] = String.valueOf(((5 + i) / 2) + ":30");
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
        servicePriceDetailModelMin = null;
        for (ServicePriceDetailModel servicePriceDetailModel : serviceModel.getServicePrice()) {
            if (servicePriceDetailModelMin == null) {
                servicePriceDetailModelMin = servicePriceDetailModel;
            }
            if ((unit % servicePriceDetailModel.getServicePriceUnit()) == 0) {
                if ((servicePriceDetailModel.getServicePricePrice() / servicePriceDetailModel.getServicePriceUnit()) <
                        (servicePriceDetailModelMin.getServicePricePrice() / servicePriceDetailModelMin.getServicePriceUnit()))
                    servicePriceDetailModelMin = servicePriceDetailModel;
            }
        }
        double unitTime = 1.0 * (Integer.parseInt(timeDurationSpinnerConsume.getSelectedItem().toString())) /
                (servicePriceDetailModelMin.getServicePriceDay() * 24 * 60 + servicePriceDetailModelMin.getServicePriceHour() * 60);
        if (servicePriceDetailModelMin != null)
            costTotalTextViewConsume.setText(String.valueOf(servicePriceDetailModelMin.getServicePricePrice() * (1.0 * unit / servicePriceDetailModelMin.getServicePriceUnit()) * unitTime));
    }

    public void sendConsumeService(View view) {
        helperSQLiteInsert = new HelperSQLiteInsert(this);

        showProgress(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson", idPerson);
        params.put("idCheck", idCheck);
        params.put("idCost", servicePriceDetailModelMin.getServicePriceId());
        params.put("pointObtain", servicePriceDetailModelMin.getServicePricePointObtain());
        params.put("pointRequired", servicePriceDetailModelMin.getServicePricePointRequired());
        params.put("idService", serviceModel.getId());
        params.put("unit", unitSpinnerConsume.getSelectedItem().toString());
        params.put("duration", timeDurationSpinnerConsume.getSelectedItem().toString());
        params.put("timeStart", timeStartSpinnerConsume.getSelectedItem().toString());
        params.put("detail", detailEditTextConsume.getText().toString());
        params.put("costTotal", costTotalTextViewConsume.getText());

        client.post(Conexion.CONSUME_SERVICE, params, new AsyncHttpResponseHandler() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    /*try {
                        JSONObject obj = new JSONObject(new String(responseBody));*/
                    ArrayList<ConsumeServiceModel> listConsumeService = new ArrayList<>();

                    ConsumeServiceModel consumeServiceModel = new ConsumeServiceModel();
                    consumeServiceModel.setReserveModelArrayList(new ArrayList<ReserveModel>());
                    consumeServiceModel.setOccupationModelArrayList(new ArrayList<OccupationModel>());
                    consumeServiceModel.setTypeMoney(servicePriceDetailModelMin.getServicePriceNameMoney());
                    consumeServiceModel.setState(true);
                    consumeServiceModel.setPay(0);
                    consumeServiceModel.setMemberModelArrayList(new ArrayList<MemberModel>());
                    consumeServiceModel.setIdKeyCheck(idCheck);
                    consumeServiceModel.setIdKeyService(serviceModel.getId());
                    consumeServiceModel.setDateInConsum(Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH) + "-" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    consumeServiceModel.setTimeInConsum(timeStartSpinnerConsume.getSelectedItem().toString());
                    consumeServiceModel.setDateOutConsum(consumeServiceModel.getDateInConsum());
                    try {
                        Date hora1 = null;
                        hora1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(consumeServiceModel.getDateInConsum() + " " + consumeServiceModel.getTimeInConsum());
                        long lantes = hora1.getTime();
                        long diferencia = (lantes + Integer.parseInt(timeDurationSpinnerConsume.getSelectedItem().toString()) * 60);
                        consumeServiceModel.setTimeOutConsum(new SimpleDateFormat("HH:mm:ss").format(new Date(diferencia)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    consumeServiceModel.setPrice(servicePriceDetailModelMin.getServicePricePrice() * Integer.parseInt(unitSpinnerConsume.getSelectedItem().toString()));
                    consumeServiceModel.setArticleModel(new ArrayList<ArticleModel>());
                    consumeServiceModel.setNameService(serviceModel.getName());
                    consumeServiceModel.setPointObtain(servicePriceDetailModelMin.getServicePricePointObtain());
                    consumeServiceModel.setPointRequired(servicePriceDetailModelMin.getServicePricePointRequired());
                    consumeServiceModel.setnDay(servicePriceDetailModelMin.getServicePriceDay());
                    consumeServiceModel.setnHour(servicePriceDetailModelMin.getServicePriceHour());
                    consumeServiceModel.setUnit(Integer.parseInt(unitSpinnerConsume.getSelectedItem().toString()));

                    listConsumeService.add(consumeServiceModel);
                    helperSQLiteInsert.insertConsumeSQLite(listConsumeService);
                    showMessaje("Consumo registrado");
                    goServicesActivity();
                    /*} catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }*/
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

    private void goServicesActivity() {
        Intent intent = new Intent(this, ServicesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
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
}
