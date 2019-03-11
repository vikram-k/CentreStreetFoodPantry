package org.centrestfoodpantry.pantryApp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rtdriver.driver.Contants;
import com.rtdriver.driver.HsBluetoothPrintDriver;


public class MainActivity extends AppCompatActivity {

    private BluetoothDevice mBluetoothDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the New Order button */
    public void newOrder(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Set Inventory button */
    public void setInventory(View view) {
        Intent intent = new Intent(this, SetInventoryActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Connect Bluetooth button */
    public void connectBT(View view) {


        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            
        String macAddress = "00:0E:0E:0B:B4:2B";

        mBluetoothDevice = adapter.getRemoteDevice(macAddress);
        HsBluetoothPrintDriver hsBluetoothPrintDriver = HsBluetoothPrintDriver.getInstance();
        hsBluetoothPrintDriver.start();
        hsBluetoothPrintDriver.connect(mBluetoothDevice);
    }

    /** Called when the user clicks the Set Inventory button */
    public void numberLottery(View view) {
        Intent intent = new Intent(this, NumberLotteryActivity.class);
        startActivity(intent);
    }

}
