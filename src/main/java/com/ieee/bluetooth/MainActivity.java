package com.ieee.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_Discover_BT = 1;



    TextView mStatusBlueTv, mPairedTv ;
    ImageView mBlueIv;
    Button mOnBtn , mOffBtn , mDiscoverButton , mPairedBtn ;
    BluetoothAdapter mBlueAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusBlueTv   = findViewById(R.id.statusbluetoothTv);
        mPairedTv       = findViewById(R.id.pairedTv);
        mBlueIv         = findViewById(R.id.statusbluetoothIv);
        mOnBtn          = findViewById(R.id.onBtn);
        mOffBtn         = findViewById(R.id.offBtn);
        mDiscoverButton = findViewById(R.id.DiscoverableBtn);
        mPairedBtn      =  findViewById(R.id.pairedBtn);

        // adapter
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        // check if button is avaible or not
        if (mBlueAdapter == null ) {
            mStatusBlueTv.setText("Bluetooth is not available");
        }
        else {
            mStatusBlueTv.setText("Bluetooth is available");

        // set image according to bluetooth status (on/off)
        if (mBlueAdapter.isEnabled()) {
            mBlueIv.setImageResource(R.drawable.ic_action_on);}
        else {
            mBlueIv.setImageResource(R.drawable.ic_action_off);}

        // on Btn click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()){
                    showToast("Turning on Bluetooth");
                    // intent on Bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent , REQUEST_ENABLE_BT);
                }
                else {
                    showToast("Bluetooth already on ");
                }
            }
        });
        //discover bluetooth btn
            mDiscoverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mBlueAdapter.isDiscovering()){
                        showToast("Making your device discoverable");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        startActivityForResult(intent , REQUEST_Discover_BT);
                    }


                }
            });
        // off btn click
            mOffBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mBlueAdapter.isEnabled()){
                        mBlueAdapter.disable();
                        showToast("Turning Bluetooth off");
                        mBlueIv.setImageResource(R.drawable.ic_action_off);
                    }
                    else {
                        showToast("Bluetooth is already off");
                    }
                    }

            });
        // get paired devices btn click
            mPairedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mBlueAdapter.isEnabled()){
                        mPairedTv.setText("Paired devices");
                        Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                        for (BluetoothDevice device : devices){
                            mPairedTv.append("\nDevice" + device.getName()+ "," + device);
                        }
                    }
                    else{
                        // Bluettoth is off so can't paired devices
                        showToast("Turn on Bluetooth to get paired devicesw");
                    }
                }
            });


        }}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK){
                    // Bluetooth is on
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on ");

                }
                else {
                    //user denied to turn bluetooth on
                    showToast("couldn't on Bluetooth");

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // toast messages function
        private void showToast (String msg ) {
            Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();


    }


        }

        //*Using BluetoothAdapter Class we will do the following operations

         //1- check if Bluettoth is availble or not
         // 2-Turn on/off Bluettoth
         // 3-Make Bluetooth Discoverable
         // 4- Display Bounded/Paired devices
         // NB : The GetBoundedDevices() method of BluettothAdapter class provides a set containing list of all paired devices.
         // Permissions required : BLUETOOTH , BLUETOOTH_ADMIN





