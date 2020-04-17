package com.example.mobileappproject;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LocationsActivity extends AppCompatActivity {
    ImageButton back;
    //Button park;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        back = findViewById(R.id.backArrowGoogleButton);
        //park = findViewById(R.id.parkButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationsActivity.this, CarsActivity.class));
            }
        });
    }
}
