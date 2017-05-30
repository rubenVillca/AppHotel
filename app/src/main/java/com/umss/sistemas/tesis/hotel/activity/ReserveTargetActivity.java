package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.Calendar;

public class ReserveTargetActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_target);

        super.showToolBar("Tarjeta de credito", true);

        builtBundle();
        init();
    }

    private void builtBundle() {

    }
    private void init(){
        Spinner spinnerMonth=(Spinner)findViewById(R.id.spinnerMonthTargetReserve);
        String valoresMonth[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        spinnerMonth.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresMonth));

        String valoresYear[] = new String[10];
        for (int i = 0; i <valoresYear.length; i++) {
            valoresYear[i] = String.valueOf(i + Calendar.getInstance().get(Calendar.YEAR));
        }
        Spinner spinnerYear=(Spinner)findViewById(R.id.spinnerYearTargetReserve);
        spinnerYear.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,valoresYear));

        String valoresType[]={"Visa","MasterCard","PayPal"};
        Spinner spinnerType=(Spinner)findViewById(R.id.spinnerTypeTargetReserve);
        spinnerType.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,valoresType));

        EditText editTextNumberTarget=(EditText)findViewById(R.id.reserveNumberTargetReserve);
        EditText editTextCCV=(EditText)findViewById(R.id.reserveCCVTargetReserve);
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
