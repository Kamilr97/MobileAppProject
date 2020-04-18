package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarsActivity extends AppCompatActivity {
    ImageButton back;
    Button logout;
    //Button addCar;
    ListView carsList;

    DatabaseReference mDatabase;

    List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_cars);

        back = findViewById(R.id.backArrowCarsButton);
        logout = findViewById(R.id.logoutCarsButton);
        //addCar = findViewById(R.id.addCarButton);
        carsList = findViewById(R.id.carsList);

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list );

        carsList.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Cars");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                  String name = ds.child("name").getValue().toString();
                    adapter.add(name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
