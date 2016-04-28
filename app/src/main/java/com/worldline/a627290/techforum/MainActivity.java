package com.worldline.a627290.techforum;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.worldline.a627290.techforum.enablewifi.AlarmManagerBroadcastReceiver;
import com.worldline.a627290.techforum.wifiTest.WifiReceiver;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    AlarmManagerBroadcastReceiver alarm;
    TextView text;
    WifiManager wifiManager;
    WifiInfo info;
    BluetoothAdapter mBluetoothAdapter;

    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Configure Alarm to be up in a date
         * */
        alarm = new AlarmManagerBroadcastReceiver();
        alarm.SetAlarm(this);
        //****************************************//
        /**
         * Detect if wifi is available
         */
        text = (TextView) this.findViewById(R.id.scanresult);

        wifiManager = (WifiManager) this.getSystemService(this.WIFI_SERVICE);

        info = wifiManager.getConnectionInfo();

        final List<ScanResult> results = wifiManager.getScanResults();

        if (results != null) {
            StringBuffer buf = new StringBuffer();

            for (int i = 0; i < results.size(); i++) {
                String ssid = results.get(i).SSID;
                if (ssid.equals("TechForum")) {
                    buf.append(ssid + "\n");
                    AlertDialog alertDialog=new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Welecome !!");
                    alertDialog.setMessage("Welcome to the TechForum");
                    alertDialog.show();
                }
            }


        }
        //**************************************//
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

}
