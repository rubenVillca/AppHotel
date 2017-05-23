package com.umss.sistemas.tesis.hotel.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.util.DatePickerFragment;
import com.umss.sistemas.tesis.hotel.util.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReserveActivity extends ActivityParent implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView dayInTextViewReserve;
    private TextView dateInTextViewReserve;
    private TextView hourInTextViewReserve;
    private TextView typeHourInTextViewReserve;

    private TextView dayOutTextViewReserve;
    private TextView dateOutTextViewReserve;
    private TextView hourOutTextViewReserve;
    private TextView typeHourOutTextViewReserve;

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
        ImageView btnLeft = (ImageView) findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(this);

        ImageView btnRight = (ImageView) findViewById(R.id.btnRight);
        btnRight.setOnClickListener(this);

        dayInTextViewReserve = (TextView) findViewById(R.id.dayInReserve);
        dateInTextViewReserve = (TextView) findViewById(R.id.dateInReserve);
        hourInTextViewReserve = (TextView) findViewById(R.id.hourInReserve);
        typeHourInTextViewReserve = (TextView) findViewById(R.id.typeHourInReserve);

        dayOutTextViewReserve = (TextView) findViewById(R.id.dayOutReserve);
        dateOutTextViewReserve = (TextView) findViewById(R.id.dateOutReserve);
        hourOutTextViewReserve = (TextView) findViewById(R.id.hourOutReserve);
        typeHourOutTextViewReserve = (TextView) findViewById(R.id.typeHourOutReserve);

        Calendar calendar = Calendar.getInstance();

        setDateIn(calendar);
        setTimeIn(calendar);
        setDateOut(calendar);
        setTimeOut(calendar);
    }

    @Override
    public void onClick(View v) {
        TextView nPerson = (TextView) findViewById(R.id.nPerson);
        int numberPerson = Integer.parseInt(nPerson.getText().toString());

        if (v.getId() == R.id.btnLeft) {
            numberPerson = (numberPerson - 1) > 0 ? --numberPerson : numberPerson;
            nPerson.setText(String.valueOf(numberPerson));
        }
        if (v.getId() == R.id.btnRight) {
            numberPerson = (numberPerson + 1) < 100 ? ++numberPerson : numberPerson;
            nPerson.setText(String.valueOf(numberPerson));
        }
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

        hourInTextViewReserve.setText(String.valueOf(hour + ":" + calendar.get(Calendar.MINUTE)));
        typeHourInTextViewReserve.setText(type ? "PM" : "AM");
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

        hourOutTextViewReserve.setText(String.valueOf(hour + ":" + calendar.get(Calendar.MINUTE)));
        typeHourOutTextViewReserve.setText(type ? "PM" : "AM");
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
        String[] strDays = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        dayOutTextViewReserve.setText(dia);
        dateOutTextViewReserve.setText(dateFormat.format(calendar.getTime()));
    }
}
