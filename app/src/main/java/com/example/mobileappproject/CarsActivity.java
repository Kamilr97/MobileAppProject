package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarsActivity extends AppCompatActivity {
    ImageButton back;
    Button addCar;
    ListView carsList;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    List<String> list = new ArrayList<>();
    HashMap<String, String> cars;
    ArrayAdapter<String> adapter;
    String username;
    String family;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_cars);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String UserId = user.getUid();
        username = "";
        family = "";

        back = findViewById(R.id.backArrowCarsButton);
        addCar = findViewById(R.id.addCarButton);
        carsList = findViewById(R.id.carsList);

        list = new ArrayList<String>();
        cars = new HashMap<String, String>();

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list );

        carsList.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        carsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = carsList.getItemAtPosition(position);
                String str = o.toString();
                if(str.equals("Please Join a Family") || str.equals("Please Add a Car")){
                    Toast.makeText(CarsActivity.this, str, Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(CarsActivity.this, LocationsActivity.class);
                    i.putExtra("carID", cars.get(str));
                    i.putExtra("user", username);
                    startActivity(i);
                }
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //gets name of family
                family = dataSnapshot.child("Users").child(UserId).child("family").getValue().toString();
                username = dataSnapshot.child("Users").child(UserId).child("name").getValue().toString();

                //iterates through cars held in Families
                adapter.clear();
                for(DataSnapshot ds : dataSnapshot.child("Families").child(family).child("cars").getChildren()) {
                    //gets name of car from Cars
                    String name = dataSnapshot.child("Cars").child(ds.getKey()).child("name").getValue().toString();
                    cars.put(name, ds.getKey().toString());
                    adapter.add(name);
                }
                if(cars.containsValue("1") && cars.size() > 1 ){
                    mDatabase.child("Families").child(family).child("cars").child("1").removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.contains("Please Join a Family")){
                    Toast.makeText(CarsActivity.this, "Please Join a Family", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(CarsActivity.this, AddCarActivity.class);
                    i.putExtra("family", family);
                    startActivity(i);
                }
            }
        });
    }
}
