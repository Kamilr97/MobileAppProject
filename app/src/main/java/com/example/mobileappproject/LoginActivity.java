package com.example.mobileappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    ImageButton back;
    Button login;

    EditText email;
    EditText password;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        login = findViewById(R.id.loginButton2);
        back = findViewById(R.id.backArrowLoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Log In Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "E-mail or Password is incorrect", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

}
