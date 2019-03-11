package org.centrestfoodpantry.pantryApp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rtdriver.driver.HsBluetoothPrintDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NumberLotteryActivity extends AppCompatActivity {

    private byte flagZero = 0x00;
    private byte flagOne = 0x01;
    public static final String HIGHEST_NUMBER = "HighestNumberPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_lottery);
    }

    /** Called when the user clicks the Submit button */
    public void toRandomNumberActivity(View view) {
        EditText sNumShoppers = (EditText) findViewById(R.id.number_shoppers);
        if (sNumShoppers.length() > 0) {
            int numShoppers = Integer.valueOf(sNumShoppers.getText().toString());

            Intent intent = new Intent(this, RandomNumberActivity.class);
            intent.putExtra("NUMBER_OF_SHOPPERS", numShoppers);
            startActivity(intent);
        }
    }

    /** Called when the user clicks the Submit button */
    public void forcePrintNumber(View view) {
        EditText sNumberToPrint = (EditText) findViewById(R.id.print_number);
        if (sNumberToPrint.length() > 0) {
            int numToPrint = Integer.valueOf(sNumberToPrint.getText().toString());
            textPrintBT(String.valueOf(numToPrint));
/*
        SharedPreferences settings = getSharedPreferences(HIGHEST_NUMBER, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HIGHEST_NUMBER", numToPrint);
        editor.apply();
*/
            TextView currentNumberView = (TextView) findViewById(R.id.currentNumber2);
            currentNumberView.setText(String.valueOf(numToPrint));
        }
    }

    public void printNextNumber(View view) {
        SharedPreferences settings = getSharedPreferences(HIGHEST_NUMBER, 0);
        int numToPrint = settings.getInt("HIGHEST_NUMBER", 0);
        numToPrint += 1;

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HIGHEST_NUMBER", numToPrint);
        editor.apply();

        TextView currentNumberView = (TextView) findViewById(R.id.currentNumber2);
        currentNumberView.setText(String.valueOf(numToPrint));
        textPrintBT(String.valueOf(numToPrint));
    }

    public void textPrintBT(String contentToPrint) {
        HsBluetoothPrintDriver hsBluetoothPrintDriver = HsBluetoothPrintDriver.getInstance();
        hsBluetoothPrintDriver.Begin();
        //hsBluetoothPrintDriver.SetDefaultSetting();
        hsBluetoothPrintDriver.SetPrintRotate(flagOne);
        hsBluetoothPrintDriver.SetAlignMode(flagZero);
        hsBluetoothPrintDriver.SetCharacterPrintMode(flagZero);
        hsBluetoothPrintDriver.SetUnderline(flagZero);
        hsBluetoothPrintDriver.SetBold(flagZero);

        byte enlargeByte = (byte) 0210;
        hsBluetoothPrintDriver.SetFontEnlarge(enlargeByte);
        hsBluetoothPrintDriver.BT_Write(contentToPrint);
        hsBluetoothPrintDriver.SetFontEnlarge(flagZero);

        // print date at bottom of receipt
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        String dateText = simpleDateFormat.format(new Date());
        hsBluetoothPrintDriver.BT_Write("\n\n" + dateText);

        for (int i = 0; i < 6; i++) {  // add blank lines at bottom of receipt
            hsBluetoothPrintDriver.CR();
        }


    }
}
