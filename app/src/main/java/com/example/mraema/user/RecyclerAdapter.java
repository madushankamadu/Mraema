package com.example.mraema.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mraema.R;
import com.example.mraema.selectMedicine.OnePharmacyFragment;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Apharmacy> distanceList;


    public RecyclerAdapter(List<Apharmacy> distanceList) {
        this.distanceList = distanceList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView distancetopharmacy,pharmacyName,pharmacyTown;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            distancetopharmacy = itemView.findViewById(R.id.distanceToMe);
            pharmacyName = itemView.findViewById(R.id.label);
            pharmacyTown = itemView.findViewById(R.id.pharmacyTown);



        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);

        return new RecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Apharmacy apharmacy = distanceList.get(position);


        DecimalFormat df = new DecimalFormat("###.##");
        holder.distancetopharmacy.setText((df.format(apharmacy.distance)+"Km from you"));
        holder.pharmacyName.setText(apharmacy.name);
        holder.pharmacyTown.setText(apharmacy.town);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", apharmacy.id);
                bundle.putString("name",apharmacy.name);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                OnePharmacyFragment onePharmacyFragment = new OnePharmacyFragment();
                onePharmacyFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_interface, onePharmacyFragment).addToBackStack(null).commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return distanceList.size();
    }
}
