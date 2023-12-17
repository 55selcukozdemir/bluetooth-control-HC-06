package com.example.drone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter myBluetooth;
    private Set<BluetoothDevice> pairedDevices;
    Button toggle_button, pari_button;
    ListView pairedList;

    public static String EXTRA_ADRESS = "device_address";
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        toggle_button = (Button) findViewById(R.id.button_toggle);
        pari_button = (Button) findViewById(R.id.button_pair);
        pairedList = (ListView) findViewById(R.id.device_list);

        toggle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBluetooth();
            }
        });

        pari_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listDevice();
            }
        });
    }

    private void listDevice() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            pairedDevices = myBluetooth.getBondedDevices();

            ArrayList list = new ArrayList();

            if(pairedDevices.size() > 0){
                for(BluetoothDevice bt : pairedDevices){
                    list.add(bt.getName()+"\n" + bt.getAddress());
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Eşleşmiş cihaz yok", Toast.LENGTH_SHORT).show();

            }

            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

            pairedList.setAdapter(adapter);
            pairedList.setOnItemClickListener(selectDevice);
        }
    }

    public AdapterView.OnItemClickListener selectDevice  = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length()-17);

            Intent intent = new Intent(MainActivity.this, Comunication.class);

            intent.putExtra(EXTRA_ADRESS, address);
            startActivity(intent);
        }
    };

    private void toggleBluetooth() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (myBluetooth == null) {
                Toast.makeText(getApplicationContext(), "Bluetooth cihazı yok", Toast.LENGTH_LONG).show();
            }

            if (!myBluetooth.isEnabled()) {
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBTIntent);
            }

            if (myBluetooth.isEnabled()) {
                myBluetooth.disable();
            }
        }
    }
}