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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;


public class RandomNumberActivity extends AppCompatActivity {

    public static final String HIGHEST_NUMBER = "HighestNumberPrefsFile";
    private BluetoothDevice mBluetoothDevice;
    private ArrayList<Integer> numbers;
    private int numShoppers;
    private byte flagZero = 0x00;
    private byte flagOne = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_number);

        Intent intent = getIntent();
        numShoppers = intent.getIntExtra("NUMBER_OF_SHOPPERS", 0);

        numbers = new ArrayList();

        for (int i = 1; i< numShoppers+1; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
    }

    public void pickNumber(View view) {
        TextView currentNumberView = (TextView) findViewById(R.id.currentNumber);
        TextView numbersLeftView = (TextView) findViewById(R.id.numbersLeft);
        if (numbers.size() > 0) {
            int currentNumber = numbers.get(0);
            textPrintBT(String.valueOf(currentNumber));
            currentNumberView.setText(String.valueOf(currentNumber));
            numbers.remove(0);
            numbersLeftView.setText(String.valueOf(numbers.size()));

            SharedPreferences settings = getSharedPreferences(HIGHEST_NUMBER, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGHEST_NUMBER", numShoppers);
            editor.apply();
        }
        else {
            currentNumberView.setText("N/A");
        }
    }

    public void addNumber(View view) {
        numbers.add(numShoppers + 1);
        numShoppers += 1;
        Collections.shuffle(numbers);

        TextView numbersLeftView = (TextView) findViewById(R.id.numbersLeft);
        numbersLeftView.setText(String.valueOf(numbers.size()));

        SharedPreferences settings = getSharedPreferences(HIGHEST_NUMBER, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("HIGHEST_NUMBER", numShoppers);
        editor.apply();
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

        // print date at top of receipt
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        String dateText = simpleDateFormat.format(new Date());
        hsBluetoothPrintDriver.BT_Write("\n\n" + dateText);

        for (int i = 0; i < 6; i++) {  // add blank lines at bottom of receipt
            hsBluetoothPrintDriver.CR();
        }


    }
}
