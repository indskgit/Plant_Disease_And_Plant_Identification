package com.saurabh.homepage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail , mPass, username, phone;
    private Button signUpBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mEmail = findViewById(R.id.reditTextEmail);
        mPass = findViewById(R.id.reditTextPassword);
        username = findViewById(R.id.reditTextName);
        phone = findViewById(R.id.reditTextMobile);
        signUpBtn = findViewById(R.id.cirRegisterButton);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createUser();
                newuser();
            }
        });
        changeStatusBarColor();

    }

    public void changeStatusBarColor() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, com.saurabh.homepage.LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

//    private void createUser() {
//        String email = mEmail.getText().toString().trim();
//        String pass = mPass.getText().toString();
//        String name = username.getText().toString();
//        String mobile = phone.getText().toString();
//
//        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            if (!pass.isEmpty()) {
//                mAuth.createUserWithEmailAndPassword(email, pass)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                Toast.makeText(RegisterActivity.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                                finish();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(RegisterActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            } else {
//                mPass.setError("Empty Fields Are not Allowed");
//            }
//        } else if (email.isEmpty()) {
//            mEmail.setError("Empty Fields Are not Allowed");
//        } else {
//            mEmail.setError("Please Enter Correct Email");
//        }
//    }

    public void newuser() {

        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString();
        String name = username.getText().toString();
        String mobile = phone.getText().toString();

        if(name.isEmpty()){
            username.setError("Enter the full name");
            username.requestFocus();
            return;
        }

        if(mobile.isEmpty()) {
            phone.setError("Enter the mobile number");
            phone.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            mEmail.setError("Empty Fields Are not Allowed");
            mEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Please enter the Email Correctely!!");
            mEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()) {
            mPass.setError("Empty Fields Are not Allowed");
            mPass.requestFocus();
            return;
        }

        if(pass.length() < 6) {
            mPass.setError("Enter password of atleast 6 character");
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name, mobile, email);

                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, com.saurabh.homepage.LoginActivity.class));
                                        finish();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}