package org.centrestfoodpantry.pantryApp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class HelloGridView extends AppCompatActivity {

    String foodItems = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_grid_view);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(HelloGridView.this, "" + v.getTag(), Toast.LENGTH_SHORT).show();
                foodItems += "\n" + v.getTag().toString();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public java.lang.String getName(int id) {
            return getResources().getResourceName(id);
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setTag(imageView.getResources().getResourceEntryName(mThumbIds[position]));
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.milk, R.drawable.eggs,
                R.drawable.yogurt, R.drawable.mozzarella_cheese,
                R.drawable.cheddar_cheese, R.drawable.american_cheese,
                R.drawable.tofu

        };
    }



    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String shopperName = editText.getText().toString();
        String outputPrint = "Name: " + shopperName + "\n" + foodItems;
                intent.putExtra("EXTRA_MESSAGE", outputPrint);
        startActivity(intent);

    }

}
