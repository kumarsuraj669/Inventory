package com.example.inventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText email;
    Button forgetpassword;
    TextView loginHere, createAccountHere;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email = findViewById(R.id.forget_password_email);
        forgetpassword = findViewById(R.id.forget_password_button);
        loginHere = findViewById(R.id.login_here);
        createAccountHere = findViewById(R.id.create_account_here);

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                finish();
            }
        });
        createAccountHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,RegisterActivity.class));
                finish();
            }
        });



        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();

                if(emailAddress.isEmpty()){
                    email.setError("Email is Required");
                }else if (!emailAddress.matches(emailPattern)){
                    email.setError("Enter proper Email");

                }else{
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Check Email", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                                    finish();

                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
            }
        });
    }
}