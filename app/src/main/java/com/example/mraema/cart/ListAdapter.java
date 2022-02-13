package com.example.mraema.cart;

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

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends ArrayAdapter<Items> {
    private static final String TAG = "checkCart";
    private int resourceLayout;
    private Context mContext;

//    public ListAdapter(Context context, ArrayList<Items> userArrayList) {
//        super(context, R.layout.cart_list_item,userArrayList );
//
//        this.mContext = context;
//
//    }

    public ListAdapter(Context context, int resource, List<Items> itemArrayList) {
        super(context,resource, itemArrayList);
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
            v = vi.inflate(R.layout.cart_list_item, null);
        }

        Items p = getItem(position);

        if (p != null) {
            TextView name = v.findViewById(R.id.pillName);
            TextView price = v.findViewById(R.id.price);
            TextView pharmacy = v.findViewById(R.id.pharmacy);

            if (name != null) {
                name.setText(p.getItemName());
                Log.d(TAG, "getView1: "+p.getItemName());
            }

            if (price != null) {
                price.setText(p.getCount());
                Log.d(TAG, "getView2: "+p.getCount());
            }

            if (pharmacy != null) {
                pharmacy.setText(p.getPharmacyName());
                Log.d(TAG, "getView3: "+p.getPharmacyName());
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


