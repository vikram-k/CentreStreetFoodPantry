package org.centrestfoodpantry.pantryApp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        String macAddress = "00:0E:0E:0C:12:69";

        mBluetoothDevice = adapter.getRemoteDevice(macAddress);
        connectBluetooth(mBluetoothDevice);
    }

    private void connectBluetooth(BluetoothDevice bluetoothDevice) {
        HsBluetoothPrintDriver hsBluetoothPrintDriver = HsBluetoothPrintDriver.getInstance();
        hsBluetoothPrintDriver.start();
        hsBluetoothPrintDriver.connect(bluetoothDevice);
    }

}
