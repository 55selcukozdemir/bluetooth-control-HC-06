package com.example.drone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Comunication extends AppCompatActivity {

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;

    BluetoothSocket btSocket = null;
    BluetoothDevice remoteDevice;
    BluetoothServerSocket mmServer;

    SeekBar seekBarThrottle, seekBarYaw, seekBarRoll, seekBarPitch;
    TextView txtThrottle, txtYaw, txtRoll, txtPitch, txtTotal;
    String Throttle, Yaw, Roll, Pitch;

    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunication);
        Intent i = getIntent();
        address = i.getStringExtra(MainActivity.EXTRA_ADRESS);
        new BTbaglan().execute();


        seekBarThrottle = (SeekBar) findViewById(R.id.seekBarThrottle);
        seekBarRoll = (SeekBar) findViewById(R.id.seekBarRoll);
        seekBarYaw = (SeekBar) findViewById(R.id.seekBarYaw);
        seekBarPitch = (SeekBar) findViewById(R.id.seekBarPitch);

        txtPitch = (TextView) findViewById(R.id.txt_pitch);
        txtRoll= (TextView) findViewById(R.id.txt_roll);
        txtThrottle = (TextView) findViewById(R.id.txt_throttle);
        txtYaw = (TextView) findViewById(R.id.txt_yaw);
        txtTotal = (TextView) findViewById(R.id.txt_total);

        Throttle = "30";
        Yaw = "30";
        Roll = "30";
        Pitch = "30";

        seekBarThrottle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(btSocket != null){
                    try {
                        Throttle = (progress + 500) +"";
                        btSocket.getOutputStream().write((Throttle + " ").getBytes());
                        txtThrottle.setText("Throttle: "+Throttle);
                        txtTotal.setText(Throttle +"|" + Yaw + "|" + Roll + "|" + Pitch + "a");
                    }
                    catch (Exception e)
                    {

                    }
                }
            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarYaw.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(btSocket != null){
                    try {
                        Yaw = (progress + 1500) +"";
                        btSocket.getOutputStream().write((Yaw + " ").getBytes());
                        txtYaw.setText("Yaw: "+Yaw);
                        txtTotal.setText(Throttle +"|" + Yaw + "|" + Roll + "|" + Pitch + "a");
                    }
                    catch (Exception e)
                    {

                    }
                }
            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarRoll.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(btSocket != null){
                    try {
                        Roll = (2500+progress) +"";
                        btSocket.getOutputStream().write((Roll + " ").getBytes());
                        txtRoll.setText("Roll: "+Roll);
                        txtTotal.setText(Throttle +"|" + Yaw + "|" + Roll + "|" + Pitch + "a");
                    }
                    catch (Exception e)
                    {

                    }
                }
            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(btSocket != null){
                    try {
                        Pitch = (3500+progress) +"";
                        btSocket.getOutputStream().write((Pitch + " ").getBytes());
                        txtPitch.setText("Pitch: "+Pitch);
                        txtTotal.setText(Throttle +"|" + Yaw + "|" + Roll + "|" + Pitch + "a");
                    }
                    catch (Exception e)
                    {

                    }
                }
            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }


    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {

            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }

    private class BTbaglan extends AsyncTask<Void, Void, Void> {

        private  boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Comunication.this, "Bağlanılıyor...", "Lütfen Bekleyin");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (btSocket == null || !isBtConnected) {
                if (ActivityCompat.checkSelfPermission(Comunication.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    try {
                        myBluetooth = BluetoothAdapter.getDefaultAdapter();
                        BluetoothDevice device = myBluetooth.getRemoteDevice(address);
                        btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        btSocket.connect();
                    }
                    catch (IOException e){
                        ConnectSuccess = false;
                    }
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(!ConnectSuccess){
                Toast.makeText(getApplicationContext(), "Bağlantı hatası", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Bağlantı Başarılı", Toast.LENGTH_SHORT).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}