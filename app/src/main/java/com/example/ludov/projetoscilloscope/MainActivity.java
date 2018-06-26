package com.example.ludov.projetoscilloscope;

import android.Manifest;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {

    //codes d'érreur
    Intent intent;
    static private final int NO_BLUETOOTH = 100;
    final static private int REQUEST_CODE = 3;
    private int userResponse;
    private String permissions[] = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_FINE_LOCATION};

    private BluetoothDevice deviceToConnect;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            final String adress = data.getStringExtra("deviceAdress");
            //Toast.makeText(this, data.getStringExtra("deviceAdress") , Toast.LENGTH_SHORT).show();

            deviceToConnect = mBluetoothAdapter.getRemoteDevice(adress);
            Toast.makeText(this, deviceToConnect.getName(), Toast.LENGTH_SHORT).show();
            BluetoothManager connection = new BluetoothManager(deviceToConnect);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(bluetoothRights() == NO_BLUETOOTH)
            Toast.makeText(this, "pas De bluetooth", Toast.LENGTH_SHORT).show();
        else if(bluetoothRights() == PERMISSION_GRANTED)
        {
            intent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
            startActivityForResult(new Intent(MainActivity.this,BlueetoothConnectActivity.class),REQUEST_CODE);

            Toast.makeText(this, "bluetooth activé", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "blueetooth refusé", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
    private int bluetoothRights()
    {


        if (BluetoothAdapter.getDefaultAdapter() == null){
            return NO_BLUETOOTH;
        }


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();




        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        {
            //demande d'autorisations si besoins
            for (String permission:permissions)
            {
                if(checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
                    requestPermissions(new String[]{permission}, userResponse);

            }
            for (String permission:permissions)
            {
                if(checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
                    return PERMISSION_DENIED;

            }


        }
        else
        {

            return PERMISSION_GRANTED;
        }


        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        for (int grantResult:grantResults
             ) {
            if(grantResult == PERMISSION_DENIED){
                break;
            }
            if(mBluetoothAdapter.disable()) {
                intent = new Intent(mBluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}



