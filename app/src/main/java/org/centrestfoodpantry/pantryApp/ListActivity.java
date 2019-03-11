package org.centrestfoodpantry.pantryApp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.rtdriver.driver.HsBluetoothPrintDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

        String shopperName = "";
        String familySize = "";
        HashMap foodItems = new HashMap();
        ArrayList<Integer> quantities = new ArrayList<>();
        CustomListAdapter adapter;

        ListView list;
        public static final String PREFS_NAME = "MyPrefsFile";

        String[] itemname;
        Integer[] imgid = new Integer[100];

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

            // Get names and images for available foods from inventory SharedPreferences
            HashMap<String, Integer> foodItems = (HashMap) settings.getAll();
            itemname = foodItems.keySet().toArray(new String[foodItems.keySet().size()]);
            Arrays.sort(itemname);

            Object[] imgIdObjects = foodItems.values().toArray();
            Arrays.sort(imgIdObjects);

            for (int i = 0; i < foodItems.size(); i++) {
                imgid[i] = (Integer) foodItems.get(itemname[i]);
            }

            for (String s: itemname) {
                int currentQuantity = 0;
                foodItems.put(s, currentQuantity);
            }

            //ArrayList<Integer> quantities = new ArrayList<Integer>();
            for (int i =0; i < foodItems.size(); i++) {
                quantities.add((int) foodItems.get(itemname[i]));
            }

            adapter=new CustomListAdapter(this, itemname, imgid, quantities);
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);

        }

        @Override
        public void onPause() {
            super.onPause();
            quantities = adapter.getQuantities();
            EditText sName = (EditText) findViewById(R.id.edit_name);
            EditText fSize = (EditText) findViewById(R.id.edit_size);
            shopperName = sName.getText().toString();
            familySize = fSize.getText().toString();
        }

        @Override
        public void onResume() {
            super.onResume();  // Always call the superclass method first
            setContentView(R.layout.activity_list);

            EditText sName = (EditText) findViewById(R.id.edit_name);
            EditText fSize = (EditText) findViewById(R.id.edit_size);
            sName.setText(shopperName);
            fSize.setText(familySize);

            adapter=new CustomListAdapter(this, itemname, imgid, quantities);
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);
        }

    /** Called when the user clicks the Submit button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText sName = (EditText) findViewById(R.id.edit_name);
        EditText fSize = (EditText) findViewById(R.id.edit_size);
        String shopperName = sName.getText().toString();
        String familySize = fSize.getText().toString();

        quantities = adapter.getQuantities();
        for (int i = 0; i < itemname.length; i++) {
            foodItems.put(itemname[i], quantities.get(i));
        }
        Bundle orderInfo = new Bundle();
        orderInfo.putString("SHOPPER_NAME",shopperName);
        orderInfo.putString("FAMILY_SIZE", familySize);
        orderInfo.putSerializable("FOOD_ITEMS", foodItems);
        intent.putExtra("ORDER_INFO", orderInfo);

        startActivity(intent);
    }

}

