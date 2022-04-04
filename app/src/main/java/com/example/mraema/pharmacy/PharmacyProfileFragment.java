package com.example.mraema.pharmacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mraema.R;
import com.example.mraema.authantication.LoginUser;
import com.example.mraema.user.UserHome;
import com.google.firebase.auth.FirebaseAuth;


public class PharmacyProfileFragment extends Fragment {

    private Button logoutBtn;



    public PharmacyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pharmacy_profile, container, false);

        logoutBtn = view.findViewById(R.id.logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginUser.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Inflate the layout for this fragment
        return view;


    }
}