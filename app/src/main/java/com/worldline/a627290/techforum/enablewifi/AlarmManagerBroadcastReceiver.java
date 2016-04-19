package com.worldline.a627290.techforum.enablewifi;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by A627290 on 19/04/2016.
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver{

    final public static String ONE_TIME = "onetime";
    Calendar cal;

    public AlarmManagerBroadcastReceiver(){
        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();


        cal.set(2016, 03, 19, 16, 44);
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        if(System.currentTimeMillis()>cal.getTimeInMillis()){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            System.out.print("hello");
            CancelAlarm(context);
        }
    }
    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds.
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), cal.getTimeInMillis() - System.currentTimeMillis(), pi);

    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


}

