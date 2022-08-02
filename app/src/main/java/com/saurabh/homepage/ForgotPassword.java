package com.saurabh.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEdiText;
    private Button resetPasswordButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEdiText = findViewById(R.id.email);
        resetPasswordButton = findViewById(R.id.ResetButton);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, com.saurabh.homepage.LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void resetPassword() {
        String email = emailEdiText.getText().toString().trim();

        if(email.isEmpty()){
            emailEdiText.setError("Email is required");
            emailEdiText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEdiText.setError("Please provide valid email");
            emailEdiText.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(ForgotPassword.this, "Try again! Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}