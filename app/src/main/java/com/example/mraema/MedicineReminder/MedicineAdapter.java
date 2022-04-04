package com.example.mraema.MedicineReminder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mraema.MedicineReminder.db.MedicineAlarm;
import com.example.mraema.R;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private static final String TAG = "checkQuery";
    private List<MedicineAlarm> medicineAlarmList;



    @Override
    public MedicineAdapter.MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medicine,parent,false);
        return new MedicineViewHolder(view);
    }

    public MedicineAdapter(List<MedicineAlarm> medicineAlarmList) {
        this.medicineAlarmList = medicineAlarmList;
    }

    public void replaceData(List<MedicineAlarm> medicineAlarmList) {
        this.medicineAlarmList = medicineAlarmList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.MedicineViewHolder holder, int position) {
        final MedicineAlarm medicineAlarm = medicineAlarmList.get(position);
        if (medicineAlarm == null) {
            return;
        }
        holder.tvMedTime.setText(medicineAlarm.getStringTime());
        holder.tvMedicineName.setText(medicineAlarm.getPilName());
        Log.d(TAG, "onBindViewHolder: "+ medicineAlarm.getPilName());
        holder.tvDoseDetails.setText(medicineAlarm.getDose());
        holder.ivAlarmDelete.setVisibility(View.VISIBLE);
        holder.ivAlarmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return (medicineAlarmList != null && !medicineAlarmList.isEmpty()) ? medicineAlarmList.size() : 0;
    }

    static class MedicineViewHolder extends RecyclerView.ViewHolder{
        TextView tvMedTime,tvMedicineName,tvDoseDetails;
        ImageView ivMedicineAction,ivAlarmDelete;



        public MedicineViewHolder(View itemView) {
            super(itemView);
            tvMedTime = itemView.findViewById(R.id.tv_med_time);
            tvMedicineName = itemView.findViewById(R.id.tv_medicine_name);
            tvDoseDetails = itemView.findViewById(R.id.tv_dose_details);
            ivMedicineAction = itemView.findViewById(R.id.iv_medicine_action);
            ivAlarmDelete = itemView.findViewById(R.id.iv_alarm_delete);
        }

    }

}
