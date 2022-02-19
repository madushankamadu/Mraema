package com.example.mraema.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mraema.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private static final String TAG = "checkingprofile";
    private TextView userNameTv,emailTv,idNumberTv,contactNumberTv;
    private String userName, email, idNumber, contactNumber;
    private List<String> details;
    private ImageView buyHistory;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userNameTv = view.findViewById(R.id.user_name_tv);
        emailTv = view.findViewById(R.id.email_tv);
        idNumberTv = view.findViewById(R.id.id_number_tv);
        contactNumberTv = view.findViewById(R.id.contact_tv);
        buyHistory = view.findViewById(R.id.history_btn);

        details = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        if(user != null){
            email = user.getEmail();
            emailTv.setText(email);

          //  Log.d(TAG, "onCreateView: "+reference.getKey());
            ProfileModel pm = new ProfileModel();

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     userName = snapshot.child("name").getValue().toString();
                     email = snapshot.child("email").getValue().toString();
                     idNumber = snapshot.child("idNumber").getValue().toString();
                     contactNumber = snapshot.child("ph_number").getValue().toString();

                    userNameTv.setText(userName);
                    emailTv.setText("email: "+email);
                    idNumberTv.setText("NIC Number: "+idNumber);
                    contactNumberTv.setText("Contact No: "+contactNumber);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        buyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}