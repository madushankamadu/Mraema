package com.example.mraema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity {

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
        login_pharmacy =findViewById(R.id.tv_pharmacy_change);
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

        //changing interface from one to another

        login_pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (normal_user){
                    btn_login.setText("ඔසුසලක් ලෙස ලියාපදිංචි වන්න.");
                    login_pharmacy.setText("පරිෂීලකයෙක් ලෙස පිවිසෙන්න.");
                    normal_user =false;
                }else if(!normal_user){
                    btn_login.setText("MRAEMA වෙත පිවිසෙන්න");
                    login_pharmacy.setText("ඔසුසලක් ලෙස පිවිසෙන්න");
                    normal_user =true;
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
                startActivity(new Intent(LoginUser.this,RegisterUser.class));
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
                            if (normal_user){
                               // Intent i = new Intent(LoginUser.this, HomePage.class);
                                //startActivity(i);
                                //finish();

                            }
                            if (!normal_user){
                              //  Intent i = new Intent(LoginUser.this, HomePage.class);
                                //startActivity(i);
                                //finish();
                            }

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