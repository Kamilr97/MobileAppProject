package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CarsActivity extends AppCompatActivity {
    ImageButton back;
    Button logout;
    //Button addCar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        back = findViewById(R.id.backArrowCarsButton);
        logout = findViewById(R.id.logoutCarsButton);
        //addCar = findViewById(R.id.addCarButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarsActivity.this, HomeActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarsActivity.this, MainActivity.class));
            }
        });
    }
}
