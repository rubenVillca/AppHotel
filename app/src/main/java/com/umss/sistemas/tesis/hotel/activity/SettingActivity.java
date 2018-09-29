package com.umss.sistemas.tesis.hotel.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.alarm.AlarmNotificationReceiver;
import com.umss.sistemas.tesis.hotel.alarm.AlarmToastReceiver;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SettingActivity extends ActivityParent {

    private Spinner spinnerAlarm;
    private LinearLayout linearLayout;
    private Switch switchActiveReserve;
    private boolean isReserve;
    private CheckModel checkModel;
    private TextView hourAlarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        showToolBar(getResources().getString(R.string.toolbar_tittle_settings), true);

        initValues();
    }

    /**
     * obtener valores los atributos del activity setting
     */
    private void initValues() {
        isReserve=false;
        String valoresType[] = new String[24];
        for (int i=0; i<valoresType.length;i++){
            valoresType[i]=String.valueOf(i+1);
        }
        spinnerAlarm = findViewById(R.id.timeSpinnerAlarm);
        spinnerAlarm.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valoresType));
        /*spinnerAlarm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAlarm(true, true);
            }
        });*/

        linearLayout= findViewById(R.id.linearLayoutAlarm);
        switchActiveReserve= findViewById(R.id.switchActiveAlarm);

        serviceHelper =new ServiceHelper(this);
        ArrayList<CheckModel> checkModels= serviceHelper.getCheckModel(0,1,1);
        if (checkModels.size()>0) {
            isReserve = true;
            checkModel=checkModels.get(0);
        }
        hourAlarmTextView = findViewById(R.id.hourAlarmActived);
        startAlarm(true,true);
    }


    private void startAlarm(boolean isNotification, boolean isRepeat) {
        if (isReserve) {
            hourAlarmTextView.setText(checkModel.getDateIn()+" "+checkModel.getTimeIn());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=new Date();
            try {
                date=simpleDateFormat.parse(checkModel.getDateIn()+" "+checkModel.getTimeIn());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int time = Integer.parseInt(spinnerAlarm.getSelectedItem().toString());

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent;
            PendingIntent pendingIntent;

            if (!isNotification) {
                myIntent = new Intent(this, AlarmToastReceiver.class);
            } else {
                myIntent = new Intent(this, AlarmNotificationReceiver.class);
            }
            pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

            if (manager!=null) {
                if (!isRepeat)
                    manager.set(AlarmManager.RTC_WAKEUP, date.getTime() + time * 1000, pendingIntent);
                else
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime() + time * 1000, 60 * 1000, pendingIntent);
            }
        }else{
            showMessaje("No tiene reservas activas");
        }
    }

    public void updateStateAlarm(View view) {
        boolean visibility=switchActiveReserve.isChecked();
        if (visibility) {
            if (isReserve) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                showMessaje("No tiene reservas activas");
            }
        }else {
            linearLayout.setVisibility(View.GONE);
        }
    }
}
