package com.example.inventory;

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
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {
    TextView loginHere;
    EditText fullName, email, password, confirmPassword;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginHere = findViewById(R.id.r_login_here);
        fullName = findViewById(R.id.r_full_name);
        email = findViewById(R.id.r_email);
        password = findViewById(R.id.r_password);
        confirmPassword = findViewById(R.id.confirm_password);
        register = findViewById(R.id.r_register);
        mAuth = FirebaseAuth.getInstance();


        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }

        });
        

    }

    private void performAuth() {
        String sFullName = fullName.getText().toString();
        String sEmail = email.getText().toString();
        String sPassword = password.getText().toString();
        String sConfirmPassword = confirmPassword.getText().toString();

        if(TextUtils.isEmpty(sFullName)){
            fullName.setError("Full Name is required");

        } else if(TextUtils.isEmpty(sEmail)){
            email.setError("Email is required");

        } else if(!sEmail.matches(emailPattern)){
            email.setError("Enter correct email");

        } else if (sPassword.isEmpty() || sPassword.length() < 6 ){
            password.setError("Enter proper password");

        }else if ( !sPassword.equals(sConfirmPassword)){
            confirmPassword.setError("Password not matched");


        }else{

            mAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                    .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                      Toast.makeText(RegisterActivity.this, "Sucessfully Registered", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                      finish();

                      }else{
                           Toast.makeText(RegisterActivity.this, "Not Registered "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                          }



        }});


        }

    }
}