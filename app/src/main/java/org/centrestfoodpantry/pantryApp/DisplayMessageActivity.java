package org.centrestfoodpantry.pantryApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.rtdriver.driver.HsBluetoothPrintDriver;

public class DisplayMessageActivity extends AppCompatActivity {

    // constants

    private byte flagZero = 0x00;
    private byte flagOne = 0x01;

    String outputPrint ;
    String shopperName, familySize;
    HashMap foodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        Bundle orderInfo = intent.getBundleExtra("ORDER_INFO");
        shopperName = orderInfo.getString("SHOPPER_NAME");
        familySize = orderInfo.getString("FAMILY_SIZE");
        foodItems = (HashMap) orderInfo.getSerializable("FOOD_ITEMS");

        outputPrint = "Name: " + shopperName + "\n" + "Family Size: " + familySize + "\n";
        for (Object item : foodItems.keySet()) {
            int quantity = (int) foodItems.get(item);
            if (quantity != 0) {
                outputPrint += "\n" + item.toString().substring(3);
                if (quantity > 1) {
                    outputPrint += " (" + quantity + ")";
                }
            }
        }

        TextView textView = new TextView(this);
        textView.setTextSize(28);
        textView.setText(outputPrint);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-hh-mm");
        String dateText = simpleDateFormat.format(new Date());
    }


    /** Called when the user clicks the New Order button */
    public void newOrder(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void textPrintBT(String contentToPrint) {
        HsBluetoothPrintDriver hsBluetoothPrintDriver = HsBluetoothPrintDriver.getInstance();
        hsBluetoothPrintDriver.Begin();
        //hsBluetoothPrintDriver.SetDefaultSetting();
        hsBluetoothPrintDriver.SetPrintRotate(flagOne);
        hsBluetoothPrintDriver.SetAlignMode(flagZero);
        hsBluetoothPrintDriver.SetCharacterPrintMode(flagZero);
        hsBluetoothPrintDriver.SetUnderline(flagZero);
        hsBluetoothPrintDriver.SetBold(flagOne);
        hsBluetoothPrintDriver.BT_Write("Name: " + shopperName + "\n" + "Family Size: " + familySize + "\n");
        hsBluetoothPrintDriver.SetBold(flagZero);
        //byte flagEnlargeSize = 0100;
        //hsBluetoothPrintDriver.SetFontEnlarge(flagEnlargeSize);

        for (Object item : foodItems.keySet()) {
            int quantity = (int) foodItems.get(item);
            if (quantity != 0) {
                hsBluetoothPrintDriver.BT_Write("\n" + item.toString().substring(3));
                if (quantity > 1) {
                    hsBluetoothPrintDriver.BT_Write(" (" + quantity + ")");
                }
            }
        }

        hsBluetoothPrintDriver.CR();
        hsBluetoothPrintDriver.LF();
        hsBluetoothPrintDriver.CR();
        hsBluetoothPrintDriver.LF();
        hsBluetoothPrintDriver.CR();
        hsBluetoothPrintDriver.LF();
        hsBluetoothPrintDriver.CR();

    }

    /** Called when the user clicks the Print button */
    public void printScreen(View view) {
        textPrintBT(outputPrint);
    }

}

