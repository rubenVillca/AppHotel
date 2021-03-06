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
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class OfferConsumeActivity extends ActivityParent {
    private OfferModel offerModel;
    private Spinner unitSpinnerConsume;
    private Spinner timeDurationSpinnerConsume;
    private Spinner timeStartSpinnerConsume;
    private EditText detailEditTextConsume;
    private TextView costTotalTextViewConsume;
    private ServicePriceDetailModel servicePriceDetailModelMin;
    private int idCheck;
    private int idPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_consume);
        container=findViewById(R.id.scrollConsumeOffer);
        super.showToolBar(getResources().getString(R.string.toolbar_tittle_offer_consume),true);
        initValues();

        initVar();
        initContent();
        updateCost();
    }

    private void initValues() {
        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            offerModel=(OfferModel) bundle.getSerializable("offerModel");
        }
    }

    private void initVar() {
        serviceHelper = new ServiceHelper(this);
        idCheck = serviceHelper.getCheckModel(0, 1, 2).get(0).getId();
        idPerson= serviceHelper.getLoginModel().getIdPerson();
    }

    private void initContent() {
        String[] valueUnit = new String[10];
        for (int i = 0; i< valueUnit.length; i++){
            valueUnit[i]=String.valueOf(i+1);
        }

        unitSpinnerConsume = findViewById(R.id.unitSpinnerConsumeOffer);
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
        timeDurationSpinnerConsume = findViewById(R.id.timeDurationSpinnerConsumeOffer);
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
        timeStartSpinnerConsume = findViewById(R.id.timeStartSpinnerConsumeOffer);
        timeStartSpinnerConsume.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueTime));

        detailEditTextConsume = findViewById(R.id.detailEditTextConsumeOffer);
        costTotalTextViewConsume = findViewById(R.id.costTotalTextViewConsumeOffer);
    }

    private void updateCost() {
        int unit = Integer.parseInt(unitSpinnerConsume.getSelectedItem().toString());
        servicePriceDetailModelMin =null;
        for (ServicePriceDetailModel servicePriceDetailModel : offerModel.getServicePriceDetailModel()) {
            if (servicePriceDetailModelMin == null) {
                servicePriceDetailModelMin = servicePriceDetailModel;
            }
            if ((unit% servicePriceDetailModel.getServicePriceUnit()) == 0) {
                if ((servicePriceDetailModel.getServicePricePrice() / servicePriceDetailModel.getServicePriceUnit()) <
                        (servicePriceDetailModelMin.getServicePricePrice() / servicePriceDetailModelMin.getServicePriceUnit()))
                    servicePriceDetailModelMin = servicePriceDetailModel;
            }
        }
        double unitTime= 1.0*(Integer.parseInt(timeDurationSpinnerConsume.getSelectedItem().toString()))/
                (servicePriceDetailModelMin.getServicePriceDay()*24*60+ servicePriceDetailModelMin.getServicePriceHour()*60);
        if (servicePriceDetailModelMin != null)
            costTotalTextViewConsume.setText(String.valueOf(servicePriceDetailModelMin.getServicePricePrice() * (1.0*unit/ servicePriceDetailModelMin.getServicePriceUnit())*unitTime));
    }

    public void sendConsumeOffer(View view) {
        serviceHelper =new ServiceHelper(this);

        showProgressUnit(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("idPerson",idPerson);
        params.put("idCheck", idCheck);
        params.put("idCost", servicePriceDetailModelMin.getServicePriceId());
        params.put("pointObtain", servicePriceDetailModelMin.getServicePricePointObtain());
        params.put("pointRequired", servicePriceDetailModelMin.getServicePricePointRequired());
        params.put("idService", offerModel.getIdKeyService());
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
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        ArrayList<ConsumeServiceModel> listConsumeService=new ArrayList<>();

                        ConsumeServiceModel consumeServiceModel =new ConsumeServiceModel();
                        consumeServiceModel.setReserveModelArrayList(new ArrayList<ReserveModel>());
                        consumeServiceModel.setOccupationModelArrayList(new ArrayList<OccupationModel>());
                        consumeServiceModel.setTypeMoney(servicePriceDetailModelMin.getServicePriceNameMoney());
                        consumeServiceModel.setState(true);
                        consumeServiceModel.setPay(0);
                        consumeServiceModel.setMemberModelArrayList(new ArrayList<MemberModel>());
                        consumeServiceModel.setIdKeyCheck(idCheck);
                        consumeServiceModel.setIdKeyService(offerModel.getIdKeyService());
                        consumeServiceModel.setDateInConsum(Calendar.getInstance().get(Calendar.YEAR)+"-"+Calendar.getInstance().get(Calendar.MONTH)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        consumeServiceModel.setTimeInConsum(timeStartSpinnerConsume.getSelectedItem().toString());
                        consumeServiceModel.setDateOutConsum(consumeServiceModel.getDateInConsum());
                        try {
                            Date hora1 = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).parse(consumeServiceModel.getDateInConsum()+ " "+ consumeServiceModel.getTimeInConsum());
                            long lantes = hora1.getTime();
                            long diferencia = (lantes + Integer.parseInt(timeDurationSpinnerConsume.getSelectedItem().toString())*60);
                            consumeServiceModel.setTimeOutConsum(new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date(diferencia)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        consumeServiceModel.setPrice(servicePriceDetailModelMin.getServicePricePrice()*Integer.parseInt(unitSpinnerConsume.getSelectedItem().toString()));
                        consumeServiceModel.setArticleModel(new ArrayList<ArticleModel>());
                        consumeServiceModel.setNameService(offerModel.getName());
                        consumeServiceModel.setPointObtain(servicePriceDetailModelMin.getServicePricePointObtain());
                        consumeServiceModel.setPointRequired(servicePriceDetailModelMin.getServicePricePointRequired());
                        consumeServiceModel.setnDay(servicePriceDetailModelMin.getServicePriceDay());
                        consumeServiceModel.setnHour(servicePriceDetailModelMin.getServicePriceHour());
                        consumeServiceModel.setUnit(Integer.parseInt(unitSpinnerConsume.getSelectedItem().toString()));

                        listConsumeService.add(consumeServiceModel);
                        serviceHelper.insertConsumeSQLite(listConsumeService);
                        goConsumeActivity();
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                showProgressUnit(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                showProgressUnit(false);
            }
        });
    }

    private void goConsumeActivity() {
        Intent intent = new Intent(this, ConsumeActivity.class);
        startActivity(intent);
    }

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
