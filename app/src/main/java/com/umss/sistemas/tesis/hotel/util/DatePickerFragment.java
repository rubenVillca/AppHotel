package com.umss.sistemas.tesis.hotel.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by ruben on 22/05/2017
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView textViewDate;
    private TextView textViewDay;
    private Calendar calendar;

    public void setTextView(TextView textViewDate,TextView textViewDay,Calendar calendar) {
        this.textViewDate = textViewDate;
        this.textViewDay=textViewDay;
        this.calendar=calendar;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy",Locale.getDefault());
        try {
            calendar.setTime(formatter.parse(String.valueOf(textViewDate.getText())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), this, year, month, day);
        DatePicker dp = datePickerDialog.getDatePicker();
        dp.setMinDate(calendar.getTimeInMillis());

        return  datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        String[] strDays = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String dia = strDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
        if (textViewDay!=null)
            textViewDay.setText(dia);
        textViewDate.setText(dateFormat.format(calendar.getTime()));
    }

    public long getTime() {
        return calendar.getTimeInMillis();
    }
}
