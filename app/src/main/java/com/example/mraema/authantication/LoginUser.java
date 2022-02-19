package com.example.mraema.authantication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mraema.R;
import com.example.mraema.user.UserHome;
import com.example.mraema.pharmacy.PharmacyHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUser extends AppCompatActivity {

    private static final String TAG = "loged";
    private EditText et_email, et_password;
    private Button btn_login;
    private TextView tv_register, tv_forget_pass, login_pharmacy;
    private FirebaseAuth auth;
    private boolean normal_user = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        tv_register = findViewById(R.id.tv_register);
        et_email = findViewById(R.id.et_email_p);
        et_password = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_lolgin);
        tv_forget_pass = findViewById(R.id.tv_forgot_pwd);


// login setup with login button for user
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(email)){
                    et_email.setError("Required");
                }
                else if (TextUtils.isEmpty(password)) {
                    et_password.setError("Required");
                }else{
                    signIn(email, password);
                }

            }
        });



        //code for forgot password
        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_email.getText().toString())){
                    Toast.makeText(LoginUser.this, "Enter your password!", Toast.LENGTH_LONG).show();
                }else{
                    resetPassword(et_email.getText().toString());
                }

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginUser.this, RegisterUser.class));
            }
        });
    }




    //setting up firebase setting for user authentication
    private void signIn(String email, String password) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginUser.this, "Login is Successful !", Toast.LENGTH_LONG).show();

                            FirebaseUser user = auth.getInstance().getCurrentUser();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
                            reference.child(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("UserType").getValue() != null){
                                        Log.d(TAG, "onSuccess: "+dataSnapshot.child("UserType").toString());
                                        Intent i = new Intent(LoginUser.this, UserHome.class);
                                        startActivity(i);
                                        finish();
                                    }else if(dataSnapshot.child("UserType").getValue() == null){
                                        Intent i = new Intent(LoginUser.this, PharmacyHome.class);
                                        startActivity(i);
                                        finish();
                                        Log.d(TAG, "onSuccess: logd as a pharmacy");
                                    }

                                }
                            });


                        }else {
                            Toast.makeText(LoginUser.this, "Login is not Successful !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    //method for reset passwords
    private void resetPassword(String email){

        String emailAddress = email;
        auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginUser.this, "reset password email sent!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(LoginUser.this, "reset password email not sent!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}