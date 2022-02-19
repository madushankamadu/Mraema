package com.example.mraema.selectMedicine;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mraema.R;
import com.example.mraema.cart.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class OnePharmacyFragment extends Fragment {

    private static final String TAG = "listedData";
    private String id, pharmacyName;
    private ListView pilset;
    List<Items> pils;
    private Button directOrderButton, addToCart;
    private ImageView increaseBtn, decreaseBtn;
    private TextView counter, popupTopic;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private int count = 1;
    private int total, unitPrice;
    private FirebaseUser user;

    public OnePharmacyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_one_pharmacy, container, false);
        // Inflate the layout for this fragment
        id = getArguments().getString("id");
        pharmacyName = getArguments().getString("name");
        Log.d(TAG, "onCreateView: run");

        pilset = view.findViewById(R.id.medies);


        directOrderButton = view.findViewById(R.id.direct_oder);
        directOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                DirectOderFragment directOderFragment = new DirectOderFragment();
                directOderFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_interface, directOderFragment).addToBackStack(null).commit();

            }
        });

        pilset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView text = view.findViewById(R.id.pName);

                String output = text.getText().toString();
                Log.d(TAG, "onItemClick: "+output);
                ItemPopup(output);





            }
        });
        return view;


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getMedicineList() {
        pils = new ArrayList<>();
        pils.clear();


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("medicines").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                Map<String, Object> medies = value.getData();
                for (Map.Entry<String, Object> entry : medies.entrySet()) {
                    Items items = new Items();
                    Log.d(TAG, "onEvent: " + entry.getKey() + "  " + entry.getValue());
                    items.setItemName((String)entry.getKey());
                    items.setUnitPrice(entry.getValue().toString());
                    //pils.add(unitName+" - " + unitPrice + "per item");

                    pils.add(items);

                }
                PillAdapter pv = new PillAdapter(getActivity(), R.layout.pil_item, pils);

                pilset.setAdapter(pv);
                pv.notifyDataSetChanged();





            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        pils.clear();
    }


    public void ItemPopup(String unitName) {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.popup_item, null);


        increaseBtn = view.findViewById(R.id.increaseBtn);
        counter = view.findViewById(R.id.quantity);
        decreaseBtn = view.findViewById(R.id.decreaseBtn);
        addToCart = view.findViewById(R.id.addToCart);
        popupTopic = view.findViewById(R.id.medicineName);


        user = FirebaseAuth.getInstance().getCurrentUser();


        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                Log.d(TAG, "onClick: clicked");
                counter.setText("" + count);
                total = unitPrice*count;

            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked");
                if (count >0){
                    count--;
                    counter.setText("" + count);
                    total = unitPrice*count;
                }


            }
        });
        popupTopic.setText(unitName);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dbid = String.valueOf(UUID.randomUUID());

                Map<String, Object> map = new HashMap<>();
                map.put("pharmacyName", pharmacyName);
                map.put("nedicineName",unitName);
                map.put("pharmacyId",id);
                map.put("units",count);
                map.put("totalPricce",total);




                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
                reference.child(user.getUid()).child("cart").child(unitName)
                        .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            count = 0;
                            total = 0;
                            map.clear();
                            Toast.makeText(getActivity(), "The item successfully added..!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.dismiss();
            }
        });


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();


    }

    private void addThingsToCart() {

        Map<String, Object> addCart= new HashMap();



    }


    @Override
    public void onResume() {
        super.onResume();

        getMedicineList();

        }
    }
