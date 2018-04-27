package com.example.ludov.projetoscilloscope;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;


public class BlueetoothConnectActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private ListView pairedDevicesListView, discoveredDevicesListView;
    private ProgressBar mProgressBar;
    private Button searching;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blueetooth_connect);
        Toolbar mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        //instanciation
        pairedDevicesListView = (ListView) findViewById(R.id.pairedDevicesView);
        discoveredDevicesListView = (ListView) findViewById(R.id.listView2);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        searching = (Button) findViewById(R.id.button);

        //lancement du layout


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> setPairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        pairedDevicesListView.setAdapter(stringArrayAdapter);


        for (BluetoothDevice device : setPairedDevices)
        {
            stringArrayAdapter.add(device.getName());
        }


    }
    



}
