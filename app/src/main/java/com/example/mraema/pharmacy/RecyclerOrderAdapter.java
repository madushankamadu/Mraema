package com.example.mraema.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mraema.R;



import java.util.List;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.MyViewHolder> {
    private List<Orders> ordersList;

    public RecyclerOrderAdapter(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView orderedPillName, orderedPillPrice, orderedPillQuantity, dateOfOdered;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderedPillName = itemView.findViewById(R.id.medi_name);


        }
    }




    @Override
    public RecyclerOrderAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View pilView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item, parent,false);

        return new RecyclerOrderAdapter.MyViewHolder(pilView);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Orders orders = ordersList.get(position);

        holder.orderedPillName.setText(orders.getUserName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("customer",orders.getUserName());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ViewOrderFragment viewOrderFragment = new ViewOrderFragment();
                viewOrderFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_pharmacy, viewOrderFragment).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}
