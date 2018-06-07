package com.example.ludov.projetoscilloscope;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;


public class BlueetoothConnectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private BluetoothAdapter mBluetoothAdapter;
    private ListView pairedDevicesListView, discoveredDevicesListView;
    private ProgressBar mProgressBar;
    private Button searching;
    private IntentFilter filterDiscovered;
    private IntentFilter filterEndDiscovering;
    private ArrayAdapter<String> adapterDiscoveredDevice;


    @Override
    public void onClick(View view) {
        this.mBluetoothAdapter.startDiscovery();
        mProgressBar.setVisibility(View.VISIBLE);


        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    adapterDiscoveredDevice.add(device.getName() + "\n" + device.getAddress());

                }
                if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
                {
                    mBluetoothAdapter.cancelDiscovery();
                    mProgressBar.setVisibility(View.GONE);
                    unregisterReceiver(this);
                }
            }
        };

        registerReceiver(mReceiver,filterDiscovered);
        registerReceiver(mReceiver,filterEndDiscovering);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        setResult(RESULT_OK,getIntent().putExtra("deviceName", ((TextView)view).getText() ));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blueetooth_connect);
        Toolbar mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);


        //instanciations
            //liste des objets appairés
        pairedDevicesListView = (ListView) findViewById(R.id.pairedDevicesView);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> setPairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        pairedDevicesListView.setAdapter(stringArrayAdapter);

            //Pour la découverte d'appareils
        discoveredDevicesListView = (ListView) findViewById(R.id.listView2);
        filterDiscovered = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filterEndDiscovering = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        adapterDiscoveredDevice = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        discoveredDevicesListView.setAdapter(adapterDiscoveredDevice);



        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        searching = (Button) findViewById(R.id.button);



        searching.setOnClickListener(this);
        pairedDevicesListView.setOnItemClickListener(this);
        discoveredDevicesListView.setOnItemClickListener(this);






        //affichage des appareils appairés

        for (BluetoothDevice device : setPairedDevices)
        {
            stringArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        }





    }
    



}
