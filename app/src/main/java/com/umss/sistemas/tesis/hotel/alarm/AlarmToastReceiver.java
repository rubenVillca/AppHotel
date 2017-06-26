package com.umss.sistemas.tesis.hotel.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ruben on 24/06/2017
 */

public class AlarmToastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Esta es mi alarma",Toast.LENGTH_SHORT).show();
    }
}
