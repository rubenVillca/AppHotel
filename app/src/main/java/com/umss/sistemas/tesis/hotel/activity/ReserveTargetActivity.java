package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.PriceServiceModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.Calendar;

public class ReserveTargetActivity extends ActivityParent implements View.OnClickListener {
    private PriceServiceModel priceServiceModel;
    private int nRoom;
    private int nAdult;
    private int nBoy;
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

        super.showToolBar("Tarjeta de credito", true);

        builtBundle();
        init();
    }

    private void builtBundle() {
        priceServiceModel = (PriceServiceModel) getIntent().getExtras().getSerializable("priceServiceModel");
        nRoom = getIntent().getExtras().getInt("nRoom");
        nAdult = getIntent().getExtras().getInt("nAdult");
        nBoy = getIntent().getExtras().getInt("nBoy");
        dateIn = getIntent().getExtras().getString("dateIn");
        timeIn = getIntent().getExtras().getString("timeIn");
        dateOut = getIntent().getExtras().getString("dateOut");
        timeOut = getIntent().getExtras().getString("timeOut");
    }

    private void init() {
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonthTargetReserve);
        String valoresMonth[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spinnerMonth.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresMonth));

        String valoresYear[] = new String[10];
        for (int i = 0; i < valoresYear.length; i++) {
            valoresYear[i] = String.valueOf(i + Calendar.getInstance().get(Calendar.YEAR));
        }
        spinnerYear = (Spinner) findViewById(R.id.spinnerYearTargetReserve);
        spinnerYear.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresYear));

        String valoresType[] = {"Visa", "MasterCard", "PayPal"};
        spinnerType = (Spinner) findViewById(R.id.spinnerTypeTargetReserve);
        spinnerType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresType));

        editTextNumberTarget = (EditText) findViewById(R.id.reserveNumberTargetReserve);
        editTextCCV = (EditText) findViewById(R.id.reserveCCVTargetReserve);

        Button button=(Button)findViewById(R.id.btnContinueReserveTarget);
        button.setOnClickListener(this);
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
        if (v.getId()==R.id.btnContinueReserveTarget){
            Intent intent=new Intent(this,ReserveMemberActivity.class);

            intent.putExtra("priceServiceModel", priceServiceModel);
            intent.putExtra("nRoom",nRoom);
            intent.putExtra("nAdult", nAdult);
            intent.putExtra("nBoy", nBoy);
            intent.putExtra("dateIn", dateIn);
            intent.putExtra("timeIn", timeIn);
            intent.putExtra("dateOut", dateOut);
            intent.putExtra("timeOut", timeOut);
            intent.putExtra("numberTarget",editTextNumberTarget.getText().toString());
            intent.putExtra("ccvTarget",editTextCCV.getText().toString());
            intent.putExtra("yearTarget",spinnerYear.getSelectedItem().toString());
            intent.putExtra("monthTarget",spinnerMonth.getSelectedItem().toString());
            intent.putExtra("typeTarget",spinnerType.getSelectedItem().toString());

            startActivity(intent);
        }
    }
}
