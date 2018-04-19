package com.example.ludov.projetoscilloscope;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {

    //codes d'érreur
    static private final int NO_BLUETOOTH = 100;

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

        if(bluetoothRights() == PERMISSION_GRANTED)
            Toast.makeText(this, "bluetooth", Toast.LENGTH_SHORT).show();
        if(bluetoothRights() == PERMISSION_DENIED)
            Toast.makeText(this, "pas blueetooth", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
    private int bluetoothRights()
    {

        if (BluetoothAdapter.getDefaultAdapter() == null){
            return NO_BLUETOOTH;
        }

        if(Build.VERSION.SDK_INT > 23)
        {
            if(ContextCompat.checkSelfPermission(this,BLUETOOTH_SERVICE) == PERMISSION_GRANTED )
            {
                return PERMISSION_GRANTED;
            }
            else
            {
                return PERMISSION_DENIED;
            }

        }
        return 0;
    }

}


