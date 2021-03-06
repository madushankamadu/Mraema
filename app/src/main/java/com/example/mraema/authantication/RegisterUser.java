package com.example.mraema.authantication;

import static com.example.mraema.MainActivity.sinhala;
import static com.example.mraema.authantication.MapFragment.pharmacyLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mraema.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterUser extends AppCompatActivity {

    private static final String TAG = "naduncheck";
    private EditText user_email, user_name, user_contact, user_id, user_pass, user_conf_pass, pharmacy_name, pharmacy_reg_code, pharmacy_district, pharmacy_email, pharmacy_number;
    private RadioButton rd_type;
    private Button pharmacy_request, user_register, choose_location;
    private TextView change_to_user, change_to_pharmacy, topicInRegister,pharmacyType;
    private FirebaseAuth auth;
    private RadioGroup radio_group;
    private RadioButton retail, whol;
    private RelativeLayout pharmacy_type;
    private FrameLayout frame_layout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Fragment fragment = new MapFragment();





        user_email = findViewById(R.id.emailU);
        user_name = findViewById(R.id.nameU);
        user_contact = findViewById(R.id.contactU);
        user_id = findViewById(R.id.idU);
        user_pass = findViewById(R.id.passU);
        user_conf_pass = findViewById(R.id.confpassU);

        pharmacy_name =  findViewById(R.id.et_nameP);
        pharmacy_reg_code = findViewById(R.id.et_code);
        pharmacy_district = findViewById(R.id.et_district);
        pharmacy_email = findViewById(R.id.et_email_p);
        pharmacy_number = findViewById(R.id.et_contactP);

        change_to_user = findViewById(R.id.change_to_user);
        change_to_pharmacy = findViewById(R.id.change_to_pharmacy);

        pharmacy_request = findViewById(R.id.btn_reg);
        user_register = findViewById(R.id.btn_regU);
        choose_location = findViewById(R.id.location_set);
        frame_layout = findViewById(R.id.frame_layout);


        radio_group = findViewById(R.id.rdgroup);
        pharmacyType = findViewById(R.id.tv_type);
        retail = findViewById(R.id.radio_ret);
        whol = findViewById(R.id.radio_whal);
        pharmacy_type = findViewById(R.id.redio_layout);
        topicInRegister = findViewById(R.id.topic_in_register);





// changing interface to pharmacy
        change_to_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pharmacy_name.setVisibility(View.VISIBLE);
                pharmacy_email.setVisibility(View.VISIBLE);
                pharmacy_district.setVisibility(View.VISIBLE);
                pharmacy_number.setVisibility(View.VISIBLE);
                pharmacy_type.setVisibility(View.VISIBLE);
                pharmacy_reg_code.setVisibility(View.VISIBLE);
                pharmacy_request.setVisibility(View.VISIBLE);
                change_to_pharmacy.setVisibility(View.VISIBLE);
                choose_location.setVisibility(View.VISIBLE);
                frame_layout.setVisibility(View.VISIBLE);

                user_email.setVisibility(View.GONE);
                user_name.setVisibility(View.GONE);
                user_contact.setVisibility(View.GONE);
                user_id.setVisibility(View.GONE);
                user_pass.setVisibility(View.GONE);
                user_conf_pass.setVisibility(View.GONE);
                user_register.setVisibility(View.GONE);
                change_to_user.setVisibility(View.GONE);

            }
        });
// changing interface to user
        change_to_pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pharmacy_name.setVisibility(View.GONE);
                pharmacy_email.setVisibility(View.GONE);
                pharmacy_district.setVisibility(View.GONE);
                pharmacy_number.setVisibility(View.GONE);
                pharmacy_type.setVisibility(View.GONE);
                pharmacy_reg_code.setVisibility(View.GONE);
                pharmacy_request.setVisibility(View.GONE);
                change_to_pharmacy.setVisibility(View.GONE);
                choose_location.setVisibility(View.GONE);
                frame_layout.setVisibility(View.GONE);

                user_email.setVisibility(View.VISIBLE);
                user_name.setVisibility(View.VISIBLE);
                user_contact.setVisibility(View.VISIBLE);
                user_id.setVisibility(View.VISIBLE);
                user_pass.setVisibility(View.VISIBLE);
                user_conf_pass.setVisibility(View.VISIBLE);
                user_register.setVisibility(View.VISIBLE);
                change_to_user.setVisibility(View.VISIBLE);
            }
        });
//set up user register button
        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user_email.getText().toString();
                String name = user_name.getText().toString();
                String ph_number = user_contact.getText().toString();
                String idNumber = user_id.getText().toString();
                String password = user_pass.getText().toString();
                String conformPassword = user_conf_pass.getText().toString();


                if (email.isEmpty()){
                    user_email.setError("Required");
                    return;
                }
                else if (name.isEmpty()){
                    user_email.setError("Required");
                    return;
                }
                else if (ph_number.isEmpty()){
                    user_contact.setError("Required");
                    return;
                }
                else if (idNumber.isEmpty()){
                    user_id.setError("Required");
                    return;
                }
                else if (password.isEmpty()){
                    user_pass.setError("Required");
                    return;
                }
                else if (conformPassword.isEmpty() || !password.equals(conformPassword)){
                    user_conf_pass.setError("Required");
                    return;
                }else {
                    createuAccount(email, password);
                }
            }
        });

        pharmacy_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph_name = pharmacy_name.getText().toString();
                String ph_email = pharmacy_email.getText().toString();
                String ph_district = pharmacy_district.getText().toString();
                String ph_contact = pharmacy_number.getText().toString();
                String ph_code = pharmacy_reg_code.getText().toString();
                int selected = radio_group.getCheckedRadioButtonId();
                rd_type = findViewById(selected);
                String type = rd_type.getText().toString();








                if (ph_name.isEmpty()){
                    pharmacy_name.setError("Required");
                    return;
                }

                else if (ph_email.isEmpty()){
                    pharmacy_email.setError("Required");
                    return;
                }

                else if (ph_district.isEmpty()){
                    pharmacy_district.setError("Required");
                    return;
                }

                if (ph_contact.isEmpty()){
                    pharmacy_number.setError("Required");
                    return;
                }

                else if (type.isEmpty()){
                    rd_type.setError("Required");
                    return;
                }

                else if (ph_code.isEmpty()){
                    pharmacy_reg_code.setError("Required");
                    return;
                }else if(pharmacyLocation == null) {
                    choose_location.setError("required");
                    return;
                }else{



                        String subject = "" + ph_name + " wants to connect with MRAEMA as a pharmacy";
                        String message = "name of the pharmacy - " + ph_name + "\n" + "Register number - " + ph_code + "\n" + "District - " + ph_district + "\n" + "Pharmacy type - " + type + "\n" + "Email Address - " + ph_email + "\n" + "Contact Number" + ph_contact + "\n"+ "location- longitude"+pharmacyLocation.longitude+ "location- latitude"+pharmacyLocation.latitude;

                        Log.d(TAG, "onClick: "+subject+"\n"+message);

                        JavaMailApi javaMailApi = new JavaMailApi(RegisterUser.this, "mraemareciever@gmail.com", subject, message);
                        javaMailApi.execute();

                    }
                }

        });

        choose_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
            }
        });

        setLanguage();

    }// oncreate

    //  create an account for user
    private void createuAccount(String email, String password) {

        auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    updateuDb(user, email);
                }
            }
        });
    }


// UPDATING THE DATABASE FOR USER ACCOUNT
    private void updateuDb(FirebaseUser user, String email) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("email",email);
        map.put("name", user_name.getText().toString());
        map.put("ph_number", user_contact.getText().toString());
        map.put("idNumber", user_id.getText().toString());
        map.put("UserType","User");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
        reference.child(user.getUid())
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterUser.this, "Registration Successful, please login to continue", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterUser.this, LoginUser.class));
                            finish();
                        }else {
                            Toast.makeText(RegisterUser.this, "Register failed!" + task.getException() +"", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void setLanguage(){
        if (sinhala == false){
            pharmacy_name.setHint("Name");
            pharmacy_email.setHint("Email");
            pharmacy_district.setHint("District");
            pharmacy_number.setHint("Phone Number");
            pharmacy_reg_code.setHint("Reg Code");
            pharmacy_request.setText("Send Request");
            change_to_pharmacy.setText("Register as pharmacy");
            choose_location.setText("Choose Location");
            topicInRegister.setText("Register for MRAEMA");
            user_email.setHint("Email");
            user_name.setHint("Name");
            user_contact.setHint("Phone Number");
            user_id.setHint("NIC Number");
            user_pass.setHint("Password");
            user_conf_pass.setHint("Confirm Password");
            user_register.setText("Register");
            change_to_user.setText("Register as user");
            pharmacyType.setText("Pharmacy Type");
            retail.setText("Retail");
            whol.setText("Wholesale");
        }else if (sinhala == true){
            pharmacy_name.setHint("?????????????????? ??????");
            pharmacy_email.setHint("??????????????? ??????????????????");
            pharmacy_district.setHint("??????????????????\u200D??????????????????");
            pharmacy_number.setHint("?????????????????? ??????");
            pharmacy_reg_code.setHint("?????????????????????????????? ????????????");
            pharmacy_request.setText("?????????????????????????????? ????????????.");
            change_to_pharmacy.setText("????????????????????????????????? ????????? ?????????????????????????????? ????????????.");
            choose_location.setText("?????????????????? ??????????????????.");
            topicInRegister.setText("MRAEMA ?????? ?????????????????????????????? ????????????.");
            pharmacyType.setText("?????????????????? ???????????????");
            retail.setText("????????????   ");
            whol.setText("??????????????????");
            user_email.setHint("??????????????? ??????????????????");
            user_name.setHint("????????????????????? ??????");
            user_contact.setHint("?????????????????? ??????");
            user_id.setHint("ID ????????????");
            user_pass.setHint("??????????????????");
            user_conf_pass.setHint("?????????????????? ???????????? ??????????????????");
            user_register.setText("?????????????????????????????? ????????????.");
            change_to_user.setText("????????????????????? ????????? ?????????????????????????????? ????????????.");
        }
    }

}