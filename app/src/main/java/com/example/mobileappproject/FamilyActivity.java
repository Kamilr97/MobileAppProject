package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FamilyActivity extends AppCompatActivity {
    ImageButton back;
    Button logout;
    //Button joinFamily;
    //Button createFamily;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        back = findViewById(R.id.backArrowFamilyButton);
        logout = findViewById(R.id.logoutFamilyButton);
        //joinFamily = findViewById(R.id.joinFamilyButton);
        //createFamily = findViewById(R.id.createFamilyButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamilyActivity.this, HomeActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamilyActivity.this, MainActivity.class));
            }
        });
    }
}
