package com.example.mraema.MedicineReminder;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mraema.MedicineReminder.db.MedicineAlarm;
import com.example.mraema.MedicineReminder.db.MedicineDBHelper;
import com.example.mraema.R;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class MedicineFragment extends Fragment {


    private static final String TAG = "debugfragmennt";
    private RecyclerView listMedicine;
    private MedicineAdapter medicineAdapter;
    private List<MedicineAlarm> alarmList = new ArrayList<>();

    public MedicineFragment() {
        // Required empty public constructor
    }

    public static MedicineFragment newInstance() {
        Bundle args = new Bundle();
        MedicineFragment fragment = new MedicineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);
        // Inflate the layout for this fragment
        listMedicine = view.findViewById(R.id.medicine_list);

            setAdapter();


        return view;
    }

    private void setAdapter()  {

        MedicineDBHelper medicineDBHelper = new MedicineDBHelper(getActivity());
        alarmList = medicineDBHelper.getAlarms();

        for (MedicineAlarm al: alarmList){
            Log.d(TAG, "onCreateView: check adapter " + al.getPilName()+" "+ al.getDoseQuantity()+" "+ al.getDose()+" ");
        }



        medicineAdapter = new MedicineAdapter(alarmList);
        listMedicine.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMedicine.setAdapter(medicineAdapter);

           // loading.setVisibility(View.GONE);



    }
}