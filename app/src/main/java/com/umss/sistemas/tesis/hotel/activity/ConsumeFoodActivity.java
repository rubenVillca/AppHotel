package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ConsumeFoodActivity extends ActivityParent {

    private TextView nameFoodTextView;
    private TextView typeFoodTextView;
    private Spinner moneyFoodSpinner;
    private TextView priceTotalFoodTextView;
    private Spinner spinnerSiteFood;
    private Spinner unitSpinnerFood;
    private ImageView imageView;
    private HashMap<String, Integer> typeMoneyHashMap;

    private CheckModel checkModel;
    private FoodModel foodModel;
    private FoodPriceModel foodPriceModelMin;
    private int idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_food);
        container=findViewById(R.id.layoutFoodCoordinator);
        initContent();
        getBundle();
        super.showToolBar(foodModel.getName(), true);
        updateContent();
        updateTotalCost();
    }

    private void getBundle() {
        foodModel = (FoodModel) getIntent().getExtras().getSerializable("foodModel");
        typeMoneyHashMap = new HashMap<>();
        helperSQLiteObtain = new HelperSQLiteObtain(this);

        idPerson=helperSQLiteObtain.getLoginModel().getIdPerson();
        ArrayList<CheckModel> checkModelArrayList = helperSQLiteObtain.getCheckModel(0, 1, 2);
        if (!checkModelArrayList.isEmpty())
            checkModel = checkModelArrayList.get(0);
        else
            checkModel = new CheckModel();

        ArrayList<FoodPriceModel> foodPriceModels = foodModel.getListFoodPriceModel();

        for (FoodPriceModel foodPriceModel : foodPriceModels) {
            typeMoneyHashMap.put(foodPriceModel.getTypeMoney(), foodPriceModel.getIdKeyTypeMoneyFood());
        }

    }

    private void initContent() {
        nameFoodTextView = (TextView) findViewById(R.id.nameFoodTextView);
        typeFoodTextView = (TextView) findViewById(R.id.typeFoodTextView);
        moneyFoodSpinner = (Spinner) findViewById(R.id.moneyFoodSpinner);
        priceTotalFoodTextView = (TextView) findViewById(R.id.priceTotalFoodTextView);
        spinnerSiteFood = (Spinner) findViewById(R.id.spinnerSiteFood);
        unitSpinnerFood = (Spinner) findViewById(R.id.unitSpinnerFood);
        imageView = (ImageView) findViewById(R.id.imageHeaderCollapsing);
    }

    private void updateContent() {
        Picasso.with(this).load(Conexion.urlServer + foodModel.getImage()).into(imageView);
        nameFoodTextView.setText(foodModel.getName());
        typeFoodTextView.setText(foodModel.getType());

        moneyFoodSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeMoneyHashMap.keySet().toArray()));
        moneyFoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotalCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<OccupationModel> occupationModels=new ArrayList<>();
        for(ConsumeModel consumeModel: checkModel.getConsumeModelArrayList()){
            occupationModels.addAll(consumeModel.getOccupationModelArrayList());
        }

        String sites[] = new String[occupationModels.size()+20];
        for(int i=0;i<occupationModels.size();i++){
            sites[i] = String.valueOf("Habitación "+occupationModels.get(0).getNameRoom());
        }
        for (int i = occupationModels.size(); i < sites.length; i++) {
            sites[i] = String.valueOf("Mesa " + (i + 1));
        }
        spinnerSiteFood.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sites));

        String[] units = new String[10];
        for (int i = 0; i < units.length; i++) {
            units[i] = String.valueOf(i + 1);
        }
        unitSpinnerFood.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units));
        unitSpinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotalCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateTotalCost() {
        int unit = Integer.parseInt(unitSpinnerFood.getSelectedItem().toString());
        foodPriceModelMin = null;
        for (FoodPriceModel foodPriceModel : foodModel.getListFoodPriceModel()) {
            if (foodPriceModelMin == null) {
                foodPriceModelMin = foodPriceModel;
            }
            if ((unit%foodPriceModel.getUnit()) == 0) {
                if ((foodPriceModel.getPrice() / foodPriceModel.getUnit()) < (foodPriceModelMin.getPrice() / foodPriceModelMin.getUnit()))
                    foodPriceModelMin = foodPriceModel;
            }
        }
        if (foodPriceModelMin != null)
            priceTotalFoodTextView.setText(String.valueOf(foodPriceModelMin.getPrice() * (1.0*unit/foodPriceModelMin.getUnit())));
    }

    public void goMenuFood(View view) {
        showProgress(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");

        params.put("idFood", foodModel.getId());
        params.put("idPerson", idPerson);
        params.put("idCheck", checkModel.getId());
        params.put("idTypeMoney",foodPriceModelMin.getIdKeyTypeMoneyFood());
        params.put("price",foodPriceModelMin.getPrice());
        params.put("unit",foodPriceModelMin.getUnit());
        params.put("pointObtain",foodPriceModelMin.getPointObtain());
        params.put("pointRequired",foodPriceModelMin.getPointRequired());
        params.put("unitTotal",unitSpinnerFood.getSelectedItem().toString());
        params.put("priceTotal",priceTotalFoodTextView.getText());
        params.put("site",spinnerSiteFood.getSelectedItem().toString());

        helperSQLiteInsert = new HelperSQLiteInsert(this);

        client.post(Conexion.getUrlServer(Conexion.FOOD_MENU_INSERT), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        goMenuFoodActivity();
                        showMessaje("Pedido enviado");
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

    private void goMenuFoodActivity() {
        Intent intent=new Intent(this,ConsumeActivity.class);
        startActivity(intent);
    }
}