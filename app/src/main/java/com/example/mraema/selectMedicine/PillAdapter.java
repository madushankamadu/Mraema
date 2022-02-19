package com.example.mraema.selectMedicine;

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

public class PillAdapter extends ArrayAdapter<Items> {
    private static final String TAG = "pillsAdded";
    private int resourceLayout;
    private Context mContext;

    public PillAdapter(@NonNull Context context, int resource, @NonNull List<Items> objects) {
        super(context, resource, objects);
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
            v = vi.inflate(R.layout.pil_item, null);
        }

        Items p = getItem(position);

        if (p != null) {
            TextView name = v.findViewById(R.id.pName);
            TextView price = v.findViewById(R.id.pPrice);

            if (name != null) {
                name.setText(p.getItemName());
                Log.d(TAG, "getView1: "+p.getItemName());
            }

            if (price != null) {
                price.setText(p.getUnitPrice());
                Log.d(TAG, "getView2: "+p.getUnitPrice());
            }

        }

        return v;

    }
}
