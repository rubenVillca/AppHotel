package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.util.DatePickerFragment;
import com.umss.sistemas.tesis.hotel.util.TimePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class ReserveSearchActivity extends ActivityParent implements View.OnClickListener {

    private TextView dayInTextViewReserve;
    private TextView dateInTextViewReserve;
    private TextView timeInTextViewReserve;
    private TextView typeTimeInTextViewReserve;

    private TextView dayOutTextViewReserve;
    private TextView dateOutTextViewReserve;
    private TextView timeOutTextViewReserve;
    private TextView typeTimeOutTextViewReserve;

    private TextView nAdult;
    private TextView nBoy;

    private CheckModel checkModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_search);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_reserve), true);
        container = findViewById(R.id.reserveSearchScrollView);
        buildContent();
        initContentArrow();
        initContentDate();
    }

    private void buildContent() {
        Bundle bundle = getIntent().getExtras();
        checkModel = (CheckModel) bundle.getSerializable("checkModel");
    }

    private void initContentArrow() {
        nAdult = (TextView) findViewById(R.id.nPersonAdult);
        ImageView btnLeftAdult = (ImageView) findViewById(R.id.btnLeftAdult);
        btnLeftAdult.setOnClickListener(this);

        ImageView btnRightAdult = (ImageView) findViewById(R.id.btnRightAdult);
        btnRightAdult.setOnClickListener(this);

        nBoy = (TextView) findViewById(R.id.nPersonBoy);
        ImageView btnLeftBoy = (ImageView) findViewById(R.id.btnLeftBoy);
        btnLeftBoy.setOnClickListener(this);

        ImageView btnRightBoy = (ImageView) findViewById(R.id.btnRightBoy);
        btnRightBoy.setOnClickListener(this);
    }

    private void initContentDate() {
        dayInTextViewReserve = (TextView) findViewById(R.id.dayInReserve);
        dateInTextViewReserve = (TextView) findViewById(R.id.dateInReserve);
        timeInTextViewReserve = (TextView) findViewById(R.id.hourInReserve);
        typeTimeInTextViewReserve = (TextView) findViewById(R.id.typeHourInReserve);

        dayOutTextViewReserve = (TextView) findViewById(R.id.dayOutReserve);
        dateOutTextViewReserve = (TextView) findViewById(R.id.dateOutReserve);
        timeOutTextViewReserve = (TextView) findViewById(R.id.hourOutReserve);
        typeTimeOutTextViewReserve = (TextView) findViewById(R.id.typeHourOutReserve);

        Calendar calendar = Calendar.getInstance();

        typeTimeOutTextViewReserve.setEnabled(false);

        if (checkModel.getId() > 0) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contentLayoutReserveSearch);
            linearLayout.setVisibility(View.INVISIBLE);
            timeInTextViewReserve.setVisibility(View.INVISIBLE);
            dayInTextViewReserve.setVisibility(View.INVISIBLE);
            dateInTextViewReserve.setVisibility(View.INVISIBLE);
            timeInTextViewReserve.setVisibility(View.INVISIBLE);
            typeTimeInTextViewReserve.setVisibility(View.INVISIBLE);
            dayOutTextViewReserve.setVisibility(View.INVISIBLE);
            dateOutTextViewReserve.setVisibility(View.INVISIBLE);
            timeOutTextViewReserve.setVisibility(View.INVISIBLE);
            typeTimeOutTextViewReserve.setVisibility(View.INVISIBLE);
        }
        if (checkModel.getId() <= 0) {
            //date in
            String[] strDays = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
            String dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
            //dateIn
            dayInTextViewReserve.setText(dia);
            dateInTextViewReserve.setText(dateFormat.format(calendar.getTime()));
            //dateOut
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
            dayOutTextViewReserve.setText(dia);
            dateOutTextViewReserve.setText(dateFormat.format(calendar.getTime()));

            //timeIn
            timeInTextViewReserve.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)));
            typeTimeInTextViewReserve.setText((calendar.get(Calendar.AM_PM)) == 1 ? "PM" : "AM");
            //timeOut
            timeOutTextViewReserve.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)));
            typeTimeOutTextViewReserve.setText((calendar.get(Calendar.AM_PM)) == 1 ? "PM" : "AM");
        }

        Button continueReserve = (Button) findViewById(R.id.btnContinueReserve);
        continueReserve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int numberPersonAdult = Integer.parseInt(nAdult.getText().toString());
        int numberPersonBoy = Integer.parseInt(nBoy.getText().toString());

        switch (v.getId()) {
            case R.id.btnLeftAdult:
                numberPersonAdult = (numberPersonAdult - 1) > 0 ? --numberPersonAdult : numberPersonAdult;
                nAdult.setText(String.valueOf(numberPersonAdult));
                break;
            case R.id.btnRightAdult:
                numberPersonAdult = (numberPersonAdult + 1) < 100 ? ++numberPersonAdult : numberPersonAdult;
                nAdult.setText(String.valueOf(numberPersonAdult));
                break;
            case R.id.btnLeftBoy:
                numberPersonBoy = (numberPersonBoy - 1) >= 0 ? --numberPersonBoy : numberPersonBoy;
                nBoy.setText(String.valueOf(numberPersonBoy));
                break;
            case R.id.btnRightBoy:
                numberPersonBoy = (numberPersonBoy + 1) < 100 ? ++numberPersonBoy : numberPersonBoy;
                nBoy.setText(String.valueOf(numberPersonBoy));
                break;
            case R.id.btnContinueReserve:
                goReserveResult();
                break;
        }

    }

    /**
     * obtener lista de tipos de habitaciones disponibles del servidor
     */
    private void goReserveResult() {
        showProgress(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");

        params.put("nAdult", nAdult.getText().toString());
        params.put("nBoy", nBoy.getText().toString());
        params.put("dateIn", dateInTextViewReserve.getText().toString());
        params.put("timeIn", timeInTextViewReserve.getText().toString());
        params.put("dateOut", dateOutTextViewReserve.getText().toString());
        params.put("timeOut", timeOutTextViewReserve.getText().toString());

        helperSQLiteInsert = new HelperSQLiteInsert(this);

        client.post(Conexion.getUrlServer(Conexion.RESERVE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        ArrayList<ReserveSearchModel> reserveSearchModels = helperSQLiteInsert.getRoomAvailableModel(obj);
                        goReserveResultActivity(reserveSearchModels);
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

    /**
     * cambiar de activity a roomAvailableActivity enviando variables
     *
     * @param reserveSearchModels:lista de tipos de habitaciones disponibles
     */
    private void goReserveResultActivity(ArrayList<ReserveSearchModel> reserveSearchModels) {
        Intent intent = new Intent(this, ReserveResultActivity.class);

        intent.putExtra("checkModel", checkModel);
        intent.putExtra("nAdult", Integer.parseInt(nAdult.getText().toString()));
        intent.putExtra("nBoy", Integer.parseInt(nBoy.getText().toString()));
        intent.putExtra("dateIn", dateInTextViewReserve.getText().toString());
        intent.putExtra("timeIn", timeInTextViewReserve.getText().toString());
        intent.putExtra("dateOut", dateOutTextViewReserve.getText().toString());
        intent.putExtra("timeOut", timeOutTextViewReserve.getText().toString());

        int size = reserveSearchModels.size();
        intent.putExtra("roomAvailableSize", size);
        for (int i = 0; i < size; i++) {
            intent.putExtra("room-" + i, reserveSearchModels.get(i));
        }

        startActivity(intent);
    }

    public void showDateInPickerDialog(View v) {
        DatePickerFragment datePickerFragmentIn = new DatePickerFragment();
        datePickerFragmentIn.setTextView(dateInTextViewReserve, dayInTextViewReserve);
        datePickerFragmentIn.show(getFragmentManager(), "datePicker");
    }

    public void showTimeInPickerDialog(View view) {
        TimePickerFragment timePickerFragmentIn = new TimePickerFragment();
        timePickerFragmentIn.setTextViewTime(timeInTextViewReserve);
        timePickerFragmentIn.setTextViewTypeTime(typeTimeInTextViewReserve);
        timePickerFragmentIn.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDateOutPickerDialog(View v) {
        DatePickerFragment datePickerFragmentOut = new DatePickerFragment();
        datePickerFragmentOut.setTextView(dateOutTextViewReserve, dayOutTextViewReserve);
        datePickerFragmentOut.show(getFragmentManager(), "datePicker");
    }

    public void showTimeOutPickerDialog(View view) {
        TimePickerFragment timePickerFragmentOut = new TimePickerFragment();
        timePickerFragmentOut.setTextViewTime(timeOutTextViewReserve);
        timePickerFragmentOut.setTextViewTypeTime(typeTimeOutTextViewReserve);
        timePickerFragmentOut.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (checkModel.getId()==-1) {
                Intent intent = new Intent(this, ReserveCheckActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, ContainerActivity.class);
                startActivity(intent);
            }
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (checkModel.getId()==-1||checkModel.getId()>0) {
                Intent intent = new Intent(this, ReserveCheckActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, ContainerActivity.class);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
