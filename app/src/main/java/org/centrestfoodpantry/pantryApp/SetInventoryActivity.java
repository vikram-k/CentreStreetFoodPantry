package org.centrestfoodpantry.pantryApp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SetInventoryActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<String> foodNames = new ArrayList<>();
    ArrayList<Integer> foodImages = new ArrayList<>();

    String[] initialFoodNames = {
            "Milk",
            "Soy Milk",
            "Yogurt",
            "Eggs",
            "Broccoli",
            "Hummus",
            "Tofu",

            "Mozzarella Cheese",
            "American Cheese",
            "Cheddar Cheese",

            "Ground Turkey",
            "Whole Chicken",
            "Chicken Thighs",
            "Chicken Drumsticks",
            "Chicken Breasts",
            "Fish Fillet",
            "Cheese Ravioli",
            "Ground Beef",
            "Turkey Burgers"
    };

    Integer[] initialFoodImages = {
            R.drawable.milk, R.drawable.soy_milk,
            R.drawable.yogurt, R.drawable.eggs, R.drawable.broccoli, R.drawable.hummus, R.drawable.tofu,

            R.drawable.mozzarella_cheese, R.drawable.american_cheese, R.drawable.cheddar_cheese,

            R.drawable.groundturkey,
            R.drawable.wholechicken, R.drawable.chicken_thighs,
            R.drawable.chicken_drumsticks, R.drawable.chickenbreast, R.drawable.fish_fillet,
            R.drawable.cheese_ravioli, R.drawable.groundbeef, R.drawable.turkey_burgers

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_inventory);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashMap<String, Integer> foodItems = (HashMap<String, Integer>) settings.getAll(); // restore saved foods
        String foodArray [];
        foodArray = Arrays.copyOf(foodItems.keySet().toArray(), foodItems.keySet().toArray().length, String[].class);
        for (int i = 0; i < foodArray.length; i++) {
            foodArray[i] = foodArray[i].substring(3);
        }

        Set<String> foodSet = new HashSet<String>(Arrays.asList(foodArray));

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_set_inventory);

        for (int i = 0; i < initialFoodNames.length; i++) {
            foodNames.add(initialFoodNames[i]);
            foodImages.add(initialFoodImages[i]);
        }

        for (int i = 0; i < initialFoodNames.length; i++) { // create checkboxes for all foods
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(initialFoodNames[i]);
            checkBox.setTag(initialFoodImages[i]);
            checkBox.setTextSize(24);

            if (foodSet.contains(initialFoodNames[i])) { // if CheckBox previously checked as per SharedPref, check the box
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

        String foodArray [];
        foodArray = Arrays.copyOf(foodItems.keySet().toArray(), foodItems.keySet().toArray().length, String[].class);
        for (int i = 0; i < foodArray.length; i++) {
            foodArray[i] = foodArray[i].substring(3);
        }

        Set<String> foodSet = new HashSet<String>(Arrays.asList(foodArray));

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_set_inventory);

        for (CheckBox checkBox : checkBoxes) {
            if (foodSet.contains(checkBox.getText())) {
                checkBox.setChecked(true);
            }
        }
    }

    @Override
    protected void onPause() {
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
        int num = 0;
        for (CheckBox checkBox : checkBoxes) { // Store checked foods in SharedPref
            if (checkBox.isChecked() == true) {
                editor.putInt(String.format("%03d", num) + checkBox.getText().toString(), (int) checkBox.getTag());
                num += 1;
            }
        }

        // Commit the edits!
        editor.commit();
    }

    /**
     * Called when the user clicks the Submit button
     **/
    public void addFood(View view) {
        EditText newFoodText = (EditText) findViewById(R.id.new_food);
        String newFood = newFoodText.getText().toString();
        foodNames.add(newFood);
        foodImages.add(R.drawable.broccoli);

        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(newFood);
        checkBox.setTag(R.drawable.broccoli);
        checkBox.setTextSize(24);

        checkBoxes.add(checkBox);
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_set_inventory);
        layout.addView(checkBox);
    }
}