package com.umss.sistemas.tesis.hotel.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.model.RoomAvailableModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.util.DatePickerFragment;
import com.umss.sistemas.tesis.hotel.util.TimePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.msebera.android.httpclient.Header;

public class ReserveActivity extends ActivityParent implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView dayInTextViewReserve;
    private TextView dateInTextViewReserve;
    private TextView timeInTextViewReserve;
    //private int timeIn=0;
    private TextView typeTimeInTextViewReserve;

    private TextView dayOutTextViewReserve;
    private TextView dateOutTextViewReserve;
    private TextView timeOutTextViewReserve;
    //private int timeOut=0;
    private TextView typeTimeOutTextViewReserve;

    private DatePickerFragment datePickerFragmentIn;
    private DatePickerFragment datePickerFragmentOut;
    private TimePickerFragment timePickerFragmentIn;
    private TimePickerFragment timePickerFragmentOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_reserve), true);

        initContent();
    }

    private void initContent() {
        ImageView btnLeft = (ImageView) findViewById(R.id.btnLeftAdult);
        btnLeft.setOnClickListener(this);

        ImageView btnRight = (ImageView) findViewById(R.id.btnRightAdult);
        btnRight.setOnClickListener(this);

        dayInTextViewReserve = (TextView) findViewById(R.id.dayInReserve);
        dateInTextViewReserve = (TextView) findViewById(R.id.dateInReserve);
        timeInTextViewReserve = (TextView) findViewById(R.id.hourInReserve);
        typeTimeInTextViewReserve = (TextView) findViewById(R.id.typeHourInReserve);

        dayOutTextViewReserve = (TextView) findViewById(R.id.dayOutReserve);
        dateOutTextViewReserve = (TextView) findViewById(R.id.dateOutReserve);
        timeOutTextViewReserve = (TextView) findViewById(R.id.hourOutReserve);
        typeTimeOutTextViewReserve = (TextView) findViewById(R.id.typeHourOutReserve);

        Button continueReserve = (Button) findViewById(R.id.btnContinueReserve);
        continueReserve.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();

        setDateIn(calendar);
        setTimeIn(calendar);
        setDateOut(calendar);
        setTimeOut(calendar);
    }

    @Override
    public void onClick(View v) {
        TextView nAdult = (TextView) findViewById(R.id.nPersonAdult);
        int numberPersonAdult = Integer.parseInt(nAdult.getText().toString());

        TextView nBoy = (TextView) findViewById(R.id.nPersonBoy);
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
                numberPersonBoy = (numberPersonBoy - 1) > 0 ? --numberPersonBoy : numberPersonBoy;
                nBoy.setText(String.valueOf(numberPersonBoy));
                break;
            case R.id.btnRightBoy:
                numberPersonBoy = (numberPersonBoy + 1) < 100 ? ++numberPersonBoy : numberPersonBoy;
                nBoy.setText(String.valueOf(numberPersonBoy));
                break;
            case R.id.btnContinueReserve:
                goRoomAvailable(nAdult, nBoy);
                break;
        }

    }

    private void goRoomAvailable(TextView nAdult, TextView nBoy) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("android", "android");
        params.put("nAdult", nAdult);
        params.put("nBoy", nBoy);
        params.put("dateIn", dateInTextViewReserve.getText());
        params.put("timeIn", timeInTextViewReserve.getText());
        params.put("dateOut", dateOutTextViewReserve.getText());
        params.put("timeOut", timeOutTextViewReserve.getText());

        helperSQLiteInsert = new HelperSQLiteInsert(this);

        client.post(Conexion.getUrlServer(Conexion.RESERVE), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        ArrayList<RoomAvailableModel> roomAvailableModels=helperSQLiteInsert.getRoomAvailableModel(obj);
                        goActivityRoomAvailable(roomAvailableModels);
                    } catch (JSONException e) {
                        System.out.println("Datos recibidos incorrectos");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                //showProgress(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Servidor no disponible");
                //showProgress(false);
            }
        });
    }

    private void goActivityRoomAvailable(ArrayList<RoomAvailableModel> roomAvailableModels) {
        Intent intent=new Intent(this,RoomAvailableActivity.class);

        int size=roomAvailableModels.size();
        intent.putExtra("roomAvailableSize",size);

        for (int i=0;i<size;i++){
            intent.putExtra("room-"+i,roomAvailableModels.get(i));
        }

        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        datePickerFragmentIn = new DatePickerFragment();
        datePickerFragmentIn.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        timePickerFragmentIn = new TimePickerFragment();
        timePickerFragmentIn.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDateOutPickerDialog(View v) {
        datePickerFragmentOut = new DatePickerFragment();
        datePickerFragmentOut.show(getFragmentManager(), "datePicker");
    }

    public void showTimeOutPickerDialog(View view) {
        timePickerFragmentOut = new TimePickerFragment();
        timePickerFragmentOut.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        int a = view.getId();
        int b = datePickerFragmentIn.getId();
        //int c = timePickerFragmentIn.getId();
        //int d = datePickerFragmentOut.getId();
        //int e = timePickerFragmentOut.getId();
        if (view.getId() == datePickerFragmentIn.getId()) {
            setDateIn(cal);
        }
        if (view.getId() == datePickerFragmentOut.getId()) {
            setDateOut(cal);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar cal = new GregorianCalendar(0, 0, 0, hourOfDay, minute);
        if (view.getId() == R.id.layoutTimeInReserve)
            setTimeIn(cal);
        if (view.getId() == R.id.layoutTimeOutReserve)
            setTimeOut(cal);
    }

    /**
     * To set date on TextView
     *
     * @param calendar:calendario
     */
    private void setTimeIn(Calendar calendar) {
        byte hour = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        boolean type = false;
        if (hour > 12) {
            hour -= 12;
            type = true;
        }

        timeInTextViewReserve.setText(String.valueOf(hour + ":" + calendar.get(Calendar.MINUTE)));
        typeTimeInTextViewReserve.setText(type ? "PM" : "AM");
    }

    /**
     * To set date on TextView
     *
     * @param calendar:calendario
     */
    private void setTimeOut(Calendar calendar) {
        byte hour = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        boolean type = false;
        if (hour > 12) {
            hour -= 12;
            type = true;
        }

        timeOutTextViewReserve.setText(String.valueOf(hour + ":" + calendar.get(Calendar.MINUTE)));
        typeTimeOutTextViewReserve.setText(type ? "PM" : "AM");
    }

    /**
     * To set date on TextView
     *
     * @param calendar:calendario
     */
    private void setDateIn(final Calendar calendar) {
        String[] strDays = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        dayInTextViewReserve.setText(dia);
        dateInTextViewReserve.setText(dateFormat.format(calendar.getTime()));
    }

    /**
     * To set date on TextView
     *
     * @param calendar:calendario
     */
    private void setDateOut(final Calendar calendar) {
        calendar.add(Calendar.DAY_OF_MONTH,1);
        String[] strDays = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        dayOutTextViewReserve.setText(dia);
        dateOutTextViewReserve.setText(dateFormat.format(calendar.getTime()));
    }
}
