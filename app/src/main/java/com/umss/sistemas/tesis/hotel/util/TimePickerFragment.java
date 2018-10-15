package com.umss.sistemas.tesis.hotel.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by ruben on 22/05/2017
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TextView textViewTime;
    private TextView textViewTypeTime;

    public void setTextViewTime(TextView textViewTime) {
        this.textViewTime = textViewTime;
    }

    public void setTextViewTypeTime(TextView textViewTypeTime) {
        this.textViewTypeTime = textViewTypeTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",Locale.getDefault());
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(formatter.parse(String.valueOf(textViewTime.getText())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = new GregorianCalendar(0, 0, 0, hourOfDay, minute);
        textViewTime.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)));
        textViewTypeTime.setText((calendar.get(Calendar.AM_PM)) == 1 ? "PM" : "AM");
    }
}
