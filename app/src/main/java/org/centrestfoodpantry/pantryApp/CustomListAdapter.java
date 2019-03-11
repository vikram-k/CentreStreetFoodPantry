package org.centrestfoodpantry.pantryApp;

        import android.app.Activity;
        import android.content.SharedPreferences;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;


public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final ArrayList<Integer> quantities;
    public static final String FOOD_COUNTS_PREFS = "FoodCountsFile";


    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid, ArrayList<Integer> quantities) {
        super(context, R.layout.mylist, itemname);
        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.quantities = quantities;
    }

    private class ViewHolder {
        Button plus;
        Button minus;
        TextView code;
        ImageView img;
        TextView item;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }


    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        //if (view == null) {
            LayoutInflater vi = context.getLayoutInflater();
            view = vi.inflate(R.layout.mylist, null);

            holder = new ViewHolder();
            holder.code = (TextView) view.findViewById(R.id.textView2);
            holder.plus = (Button) view.findViewById(R.id.buttonPlus);
            holder.minus = (Button) view.findViewById(R.id.buttonMinus);
            holder.item = (TextView) view.findViewById(R.id.item);
            holder.img = (ImageView) view.findViewById(R.id.icon);
        view.setTag(holder);

        holder.plus.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                TextView quantity = (TextView) ((View) v.getParent()).findViewById(R.id.textView2);
                quantities.set(position, quantities.get(position) + 1);
                quantity.setText(String.valueOf(quantities.get(position)));

            }
        });

        holder.minus.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                int currentQuantity = quantities.get(position);
                if (currentQuantity != 0) {
                    quantities.set(position, currentQuantity - 1);
                    TextView quantity = (TextView) ((View) v.getParent()).findViewById(R.id.textView2);
                    quantity.setText(String.valueOf(currentQuantity - 1));
                }

            }
        });



        holder.item.setText(itemname[position].substring(3));
        holder.item.setTextSize(36);
        holder.img.setImageResource(imgid[position]);
        holder.code.setText(quantities.get(position).toString());

    Button plus = (Button)view.findViewById(R.id.buttonPlus);
    plus.setTag(itemname[position]);
    Button minus = (Button)view.findViewById(R.id.buttonMinus);
    minus.setTag(itemname[position]);

        return view;



    };

}