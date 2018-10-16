package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.util.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReserveSearchActivity extends ActivityParent implements View.OnClickListener {

    private Switch isMember;
    private TextView dayInTextViewReserve;
    private TextView dateInTextViewReserve;
    private Spinner timeInSpinnerViewReserve;
    private TextView typeTimeInTextViewReserve;

    private TextView dayOutTextViewReserve;
    private TextView dateOutTextViewReserve;
    private Spinner timeOutSpinnerViewReserve;
    private TextView typeTimeOutTextViewReserve;

    private TextView nAdult;
    private TextView nBoy;
    private DatePickerFragment datePickerFragmentIn;
    private Calendar calendarDateIn;
    private Calendar calendarDateOut;
    private DatePickerFragment datePickerFragmentOut;
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
        initValueDate();
    }

    private void initValueDate() {
        datePickerFragmentIn = new DatePickerFragment();
        datePickerFragmentIn.setTextView(dateInTextViewReserve, dayInTextViewReserve,calendarDateIn);

        datePickerFragmentOut = new DatePickerFragment();
        datePickerFragmentOut.setTextView(dateOutTextViewReserve, dayOutTextViewReserve,calendarDateOut);
    }

    private void buildContent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            checkModel = (CheckModel) bundle.getSerializable("checkModel");
        }
    }

    private void initContentArrow() {
        nAdult = findViewById(R.id.nPersonAdult);
        ImageView btnLeftAdult = findViewById(R.id.btnLeftAdult);
        btnLeftAdult.setOnClickListener(this);

        ImageView btnRightAdult = findViewById(R.id.btnRightAdult);
        btnRightAdult.setOnClickListener(this);

        nBoy = findViewById(R.id.nPersonBoy);
        ImageView btnLeftBoy = findViewById(R.id.btnLeftBoy);
        btnLeftBoy.setOnClickListener(this);

        ImageView btnRightBoy = findViewById(R.id.btnRightBoy);
        btnRightBoy.setOnClickListener(this);
    }

    private void initContentDate() {
        isMember = findViewById(R.id.switchButton);
        isMember.setChecked(checkModel.getId()<1);

        dayInTextViewReserve = findViewById(R.id.dayInReserve);
        dateInTextViewReserve = findViewById(R.id.dateInReserve);
        timeInSpinnerViewReserve = findViewById(R.id.hourInSpinnerReserveSearch);
        typeTimeInTextViewReserve = findViewById(R.id.typeHourInReserve);

        dayOutTextViewReserve = findViewById(R.id.dayOutReserve);
        dateOutTextViewReserve = findViewById(R.id.dateOutReserve);
        timeOutSpinnerViewReserve = findViewById(R.id.hourOutSpinnerReserveSearch);
        typeTimeOutTextViewReserve = findViewById(R.id.typeHourOutReserve);

        String valuesHour[] = new String[24];
        for (int i = 0; i < valuesHour.length; i++) {
            if (i < 10)
                valuesHour[i] = String.valueOf("0" + i + ":00");
            else
                valuesHour[i] = String.valueOf(i + ":00");
        }
        timeInSpinnerViewReserve.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valuesHour));
        timeOutSpinnerViewReserve.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valuesHour));

        timeInSpinnerViewReserve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeTimeInTextViewReserve.setText(position > 12 ? "PM" : "AM");
                timeOutSpinnerViewReserve.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        timeOutSpinnerViewReserve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeTimeOutTextViewReserve.setText(position > 12 ? "PM" : "AM");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (checkModel.getId() > 0) {
            chargeDateReserve();
        }
        if (checkModel.getId() <= 0) {
            calendarDateIn=setDateReserve(1, 6, dayInTextViewReserve, dateInTextViewReserve, timeInSpinnerViewReserve, typeTimeInTextViewReserve);
            calendarDateOut=setDateReserve(2, 6, dayOutTextViewReserve, dateOutTextViewReserve, timeOutSpinnerViewReserve, typeTimeOutTextViewReserve);
        }

        Button continueReserve = findViewById(R.id.btnContinueReserve);
        continueReserve.setOnClickListener(this);
    }

    private void chargeDateReserve() {
        LinearLayout linearLayout = findViewById(R.id.contentLayoutReserveSearch);
        linearLayout.setVisibility(View.GONE);

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat formateador = new SimpleDateFormat("MMM dd, yyyy",Locale.getDefault());
        Date dateInParse = null;
        Date dateOutParse =null;
        try {
            dateInParse = parseador.parse(checkModel.getDateIn());
            dateInTextViewReserve.setText(formateador.format(dateInParse));

            dateOutParse = parseador.parse(checkModel.getDateEnd());
            dateOutTextViewReserve.setText(formateador.format(dateOutParse));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendarDateIn=Calendar.getInstance();
        calendarDateOut=Calendar.getInstance();
        if (dateInParse != null&&dateOutParse!=null) {
            calendarDateIn.setTimeInMillis(dateInParse.getTime());
            calendarDateOut.setTimeInMillis(dateOutParse.getTime());
        }else{
            System.out.println("Error al convertir fecha de reservas");
        }

        timeInSpinnerViewReserve.setSelection(Integer.parseInt(checkModel.getTimeIn().split(":")[0]));
        timeOutSpinnerViewReserve.setSelection(Integer.parseInt(checkModel.getTimeEnd().split(":")[0]));
    }

    private Calendar setDateReserve(int dayLast, int hour, TextView dayTextView, TextView dateTextView, Spinner spinnerTime, TextView typeTimeReserve) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, dayLast);//sumar dias
        calendar.set(Calendar.HOUR_OF_DAY, hour);

        String[] strDays = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];//obtener dia

        dayTextView.setText(dia);
        dateTextView.setText(dateFormat.format(calendar.getTime()));

        spinnerTime.setSelection(hour);
        typeTimeReserve.setText(hour > 12 ? "PM" : "AM");

        return calendar;
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
        showProgressUnit(true);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("android", "android");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        params.put("nAdult", nAdult.getText().toString());
        params.put("nBoy", nBoy.getText().toString());
        params.put("dateIn", format.format(datePickerFragmentIn.getTime()));
        params.put("timeIn", String.valueOf(timeInSpinnerViewReserve.getSelectedItem().toString()));
        params.put("dateOut", format.format(datePickerFragmentOut.getTime()));
        params.put("timeOut", String.valueOf(timeOutSpinnerViewReserve.getSelectedItem().toString()));

        serviceHelper = new ServiceHelper(this);

        client.post(Conexion.RESERVE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(new String(responseBody));
                        ArrayList<ReserveSearchModel> reserveSearchModels = serviceHelper.getRoomAvailableModel(obj);
                        if (!reserveSearchModels.isEmpty())
                            goReserveResultActivity(reserveSearchModels);
                        else
                            showMessaje("No se encontraron habitaciones disponibles");
                    } catch (JSONException e) {
                        showMessaje("Error con el servidor");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Modo Offline");
                }
                showProgressUnit(false);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                showMessaje("Servidor no disponible");
                showProgressUnit(false);
            }
        });
    }

    /**
     * cambiar de activity a roomAvailableActivity enviando variables
     *
     * @param reserveSearchModels:lista de tipos de habitaciones disponibles
     */
    private void goReserveResultActivity(ArrayList<ReserveSearchModel> reserveSearchModels) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        Intent intent = new Intent(this, ReserveResultActivity.class);

        intent.putExtra("checkModel", checkModel);
        intent.putExtra("isMember",isMember.isChecked());
        intent.putExtra("nAdult", Integer.parseInt(nAdult.getText().toString()));
        intent.putExtra("nBoy", Integer.parseInt(nBoy.getText().toString()));
        intent.putExtra("dateIn", format.format(datePickerFragmentIn.getTime()));
        intent.putExtra("timeIn", String.valueOf(timeInSpinnerViewReserve.getSelectedItem().toString()));
        intent.putExtra("dateOut", format.format(datePickerFragmentOut.getTime()));
        intent.putExtra("timeOut", String.valueOf(timeOutSpinnerViewReserve.getSelectedItem().toString()));

        int size = reserveSearchModels.size();
        intent.putExtra("roomAvailableSize", size);
        for (int i = 0; i < size; i++) {
            intent.putExtra("room-" + i, reserveSearchModels.get(i));
        }

        startActivity(intent);
    }

    public void showDateInPickerDialog(View v) {
        datePickerFragmentIn.show(getFragmentManager(), "datePicker");
    }

    public void showDateOutPickerDialog(View v) {
        datePickerFragmentOut.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (checkModel.getId() == -1) {
                Intent intent = new Intent(this, ReserveCheckActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ContainerActivity.class);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (checkModel.getId() == -1 || checkModel.getId() > 0) {
                Intent intent = new Intent(this, ReserveCheckActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ContainerActivity.class);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
