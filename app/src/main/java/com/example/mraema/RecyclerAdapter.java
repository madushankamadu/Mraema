package com.example.mraema;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Distances> distanceList;


    public RecyclerAdapter(List<Distances> distanceList) {
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

        Distances distances = distanceList.get(position);

        DecimalFormat df = new DecimalFormat("###.##");
        holder.distancetopharmacy.setText((df.format(distances.distance)+"Km from you"));
        holder.pharmacyName.setText(distances.name);
        holder.pharmacyTown.setText(distances.town);


    }

    @Override
    public int getItemCount() {
        return distanceList.size();
    }
}
