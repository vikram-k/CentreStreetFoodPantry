package org.centrestfoodpantry.pantryApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.rtdriver.driver.HsBluetoothPrintDriver;

import static com.rtdriver.driver.Contants.TYPE_80;

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

        /*Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        hsBluetoothPrintDriver.printImage(bMap, TYPE_80);
        */


        hsBluetoothPrintDriver.SetBold(flagOne);
        hsBluetoothPrintDriver.BT_Write("Name: " + shopperName + "\n" + "Family Size: " + familySize + "\n");
        hsBluetoothPrintDriver.SetBold(flagZero);

        for (Object item : foodItems.keySet()) {
            int quantity = (int) foodItems.get(item);
            if (quantity != 0) {
                hsBluetoothPrintDriver.BT_Write("\n" + item.toString().substring(3));
                if (quantity > 1) {
                    hsBluetoothPrintDriver.BT_Write(" (" + quantity + ")");
                }
            }
        }

        // print date at top of receipt
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        String dateText = simpleDateFormat.format(new Date());
        //hsBluetoothPrintDriver.BT_Write("\n\n" + dateText);

        for (int i = 0; i < 6; i++) {  // add blank lines at bottom of receipt
            hsBluetoothPrintDriver.CR();
        }


    }

    /** Called when the user clicks the Print button */
    public void printScreen(View view) {
        textPrintBT(outputPrint);
    }

    /** Called when the user clicks the Main Menu button */
    public void mainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

