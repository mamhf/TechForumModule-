package com.worldline.a627290.techforum.wifiTest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.worldline.a627290.techforum.MainActivity;

import java.util.List;

/**
 * Created by A627290 on 18/04/2016.
 */
public class WifiReceiver extends BroadcastReceiver {
    private WifiManager wifiManager;
    private MainActivity activity;
    List<ScanResult> wifiList;
    public WifiReceiver(){}
    public WifiReceiver(WifiManager wifiManager, MainActivity activity){
        this.wifiManager = wifiManager;
        this.activity=activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        StringBuilder sb = new StringBuilder();
        wifiList = wifiManager.getScanResults();
        sb.append("\n        Number Of Wifi connections :"+wifiList.size()+"\n\n");

        for(int i = 0; i < wifiList.size(); i++){

            sb.append(new Integer(i+1).toString() + ". ");
            sb.append((wifiList.get(i)).toString());
            sb.append("\n\n");
        }

        System.out.print(sb.toString());
    }
    /** Detect you are connected to a specific network. */



}