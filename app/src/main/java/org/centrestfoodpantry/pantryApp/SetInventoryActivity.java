package org.centrestfoodpantry.pantryApp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;

public class SetInventoryActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    String[] itemname ={
            "Milk",
            "Yogurt",
            "Eggs",
            "Broccoli",
            "Hummus",
            "Mozzarella Cheese",
            "American Cheese",
            "Cheddar Cheese",
            "Whole Chicken",
            "Chicken Thighs",
            "Chicken Drumsticks",
            "Chicken Breasts",
            "Fish Fillet",
            "Cheese Ravioli"
    };

    Integer[] imgid={
            R.drawable.milk,
            R.drawable.yogurt, R.drawable.eggs, R.drawable.broccoli, R.drawable.hummus,
            R.drawable.mozzarella_cheese, R.drawable.american_cheese,
            R.drawable.cheddar_cheese,
            R.drawable.wholechicken, R.drawable.chicken_thighs,
            R.drawable.chicken_drumsticks, R.drawable.chickenbreast, R.drawable.fish_fillet,
            R.drawable.broccoli

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inventory);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashMap<String, Integer> foodItems = (HashMap<String, Integer>) settings.getAll(); // restore saved foods

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_set_inventory);

        for (int i = 0; i < itemname.length; i++) { // create checkboxes for all foods
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(itemname[i]);
            checkBox.setTag(imgid[i]);
            checkBox.setTextSize(24);

            if (foodItems.containsKey(itemname)) { // if CheckBox previously checked as per SharedPref, check the box
                checkBox.setChecked(true);
            }
            checkBoxes.add(checkBox);
            layout.addView(checkBox);
        }
       }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashMap<String, Integer> foodItems = (HashMap<String, Integer>) settings.getAll();

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_set_inventory);

        for (CheckBox checkBox : checkBoxes) {
            if (foodItems.containsKey(checkBox.getText())) {
                checkBox.setChecked(true);
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();

        for (CheckBox checkBox : checkBoxes) { // Store checked foods in SharedPref
            if (checkBox.isChecked() == true) {
                editor.putInt(checkBox.getText().toString(), (int) checkBox.getTag());
            }
        }

        // Commit the edits!
        editor.commit();
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();

        for (CheckBox checkBox : checkBoxes) { // Store checked foods in SharedPref
            if (checkBox.isChecked() == true) {
                editor.putInt(checkBox.getText().toString(), (int) checkBox.getTag());
            }
        }

        // Commit the edits!
        editor.commit();
    }
}



