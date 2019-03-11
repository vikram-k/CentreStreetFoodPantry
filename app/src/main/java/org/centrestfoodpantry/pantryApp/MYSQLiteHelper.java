package org.centrestfoodpantry.pantryApp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FOODS = "foods";
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_IMAGE = "image";

    private static final String DATABASE_NAME = "foods.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FOODS + "(" + COLUMN_FOOD
            + " varchar(255), " + COLUMN_IMAGE
            + " int);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        String[] foodItems ={
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
                "Cheese Ravioli",
        };

        Integer[] imgid={
                R.drawable.milk,
                R.drawable.yogurt, R.drawable.eggs, R.drawable.broccoli, R.drawable.hummus,
                R.drawable.mozzarella_cheese, R.drawable.american_cheese,
                R.drawable.cheddar_cheese,
                R.drawable.whole_chicken, R.drawable.chicken_thighs,
                R.drawable.chicken_drumsticks, R.drawable.chicken_breast, R.drawable.fish_fillet,
                R.drawable.broccoli


        };


        for (int i = 0; i < foodItems.length; i++) {
            database.execSQL("INSERT INTO " + TABLE_FOODS + " (" + COLUMN_FOOD + ", " + COLUMN_IMAGE + ")");
            database.execSQL("VALUES " + " (" + foodItems[i] + "," + imgid[i] + ");");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        onCreate(db);
    }

    //---opens the database---
    public SQLiteDatabase open() throws SQLException {
        return this.getWritableDatabase();
    }

} 