package com.example.mraema.pharmacy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mraema.R;
import com.example.mraema.cart.Items;

import java.util.List;


public class PilListAdapter extends ArrayAdapter<Pils> {
    private static final String TAG = "checkCart";
    private int resourceLayout;
    private Context mContext;

//    public ListAdapter(Context context, ArrayList<Items> userArrayList) {
//        super(context, R.layout.cart_list_item,userArrayList );
//
//        this.mContext = context;
//
//    }

    public PilListAdapter(Context context, int resource, List<Pils> medicines) {
        super(context,resource, medicines);
        this.resourceLayout = resource;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.ordered_pill_item, null);
        }

        Pils p = getItem(position);

        if (p != null) {
            TextView name = v.findViewById(R.id.medicine_name);
            TextView price = v.findViewById(R.id.medicine_price);
            TextView count = v.findViewById(R.id.medicine_count);

            if (name != null) {
                name.setText(p.getPillName());
                Log.d(TAG, "getView1: "+p.getPillName());
            }

            if (price != null) {
                count.setText("Item Count: "+p.getPilCount());
                Log.d(TAG, "getView2: "+p.getPilCount());
            }

            if (count != null) {
                price.setText("Total Price: Rs "+p.getPilPrice());
                Log.d(TAG, "getView3: "+p.getPilPrice());
            }
        }

        return v;

//         Items item = getItem(position);
//
//        if (convertView == null ){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_list_item,parent,false);
//        }
//
//        TextView name = convertView.findViewById(R.id.pillName);
//        TextView price = convertView.findViewById(R.id.price);
//        TextView pharmacy = convertView.findViewById(R.id.pharmacy);
//
//        name.setText(item.getItemName());
//        price.setText(item.getCount());
//        pharmacy.setText(item.getPharmacyName());
//
//
//        return super.getView(position, convertView, parent);
    }
}


