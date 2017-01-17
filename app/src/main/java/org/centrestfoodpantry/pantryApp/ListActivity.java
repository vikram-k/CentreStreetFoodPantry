package org.centrestfoodpantry.pantryApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    String shopperName = "";
    String familySize = "";
    HashMap foodItems = new HashMap();
    ArrayList<Integer> quantities = new ArrayList<Integer>();
    CustomListAdapter adapter;

        ListView list;
        String[] itemname ={
                "Milk",
                "Eggs",
                "Yogurt",
                "Mozzarella Cheese",
                "Cheddar Cheese",
                "American Cheese",
                "Tofu",
                "Broccoli",
                "Hummus",
                "Ground Turkey",
                "Whole Chicken",
                "Chicken Breast",
                "Hot Dogs"
        };

        Integer[] imgid={
                R.drawable.milk, R.drawable.eggs,
                R.drawable.yogurt, R.drawable.mozzarella_cheese,
                R.drawable.cheddar_cheese, R.drawable.american_cheese,
                R.drawable.tofu, R.drawable.broccoli, R.drawable.hummus,
                R.drawable.groundturkey, R.drawable.wholechicken,
                R.drawable.chickenbreast, R.drawable.hotdogs

        };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);

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

    /*
    public void onPlusClicked(View view) {
        // Is the view now checked?
        String foodItem = ((Button) view).getTag().toString();
        TextView quantity = (TextView) ((View) view.getParent()).findViewById(R.id.textView2);
        int currentQuantity = (int) foodItems.get(foodItem);
        foodItems.put(foodItem, currentQuantity+1);
        quantity.setText(String.valueOf(currentQuantity + 1));

        //updateView();
    }

    public void onMinusClicked(View view) {
        // Is the view now checked?
        String foodItem = ((Button) view).getTag().toString();

        int currentQuantity = (int) foodItems.get(foodItem);
        if (currentQuantity > 0) {
            foodItems.put(foodItem, currentQuantity - 1);
            TextView quantity = (TextView) ((View) view.getParent()).findViewById(R.id.textView2);
            quantity.setText(String.valueOf(currentQuantity - 1));
            //updateView();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        String foodItem = ((CheckBox) view).getTag().toString();
        if (checked == true)
            foodItems.add(foodItem);
        else
            foodItems.remove(foodItem);
    }
    */
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

