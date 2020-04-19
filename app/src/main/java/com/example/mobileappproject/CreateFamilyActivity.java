package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CreateFamilyActivity extends AppCompatActivity {

    ImageButton back;
    Button logout;

    EditText familyName;
    Button createFamily;

    Iterable<DataSnapshot> familyList;

    List<String> list = new ArrayList<>();

    DatabaseReference mDatabase, familyDatabase;

    boolean familyExists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createfamily);

        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        familyName = findViewById(R.id.enterFamilyName);
        createFamily = findViewById(R.id.createFamilyButton);

        back = findViewById(R.id.backArrowFamilyButton);
        logout = findViewById(R.id.logoutFamilyButton);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Families");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              familyList = dataSnapshot.getChildren();
                for(DataSnapshot ds : familyList){
                        list.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        createFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(familyName.getText().toString())) {
                    Toast.makeText(CreateFamilyActivity.this, "Please type in a family name", Toast.LENGTH_LONG).show();
                } else {
                    familyExists = false;
                    String currentName = familyName.getText().toString();
                    for (String temp : list){
                        if (currentName.equalsIgnoreCase(temp)){
                            familyExists = true;
                            break;
                        }
                    }
                    familyDatabase = FirebaseDatabase.getInstance().getReference();
                    String createdName = familyName.getText().toString();
                    if (!familyExists) {
                        familyDatabase.child("Families").child(createdName).child("cars").child("0").setValue("true");
                        familyDatabase.child("Families").child(createdName).child("users").child(currentUser).setValue("true");
                        Toast.makeText(CreateFamilyActivity.this, "Family Created Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateFamilyActivity.this, FamilyActivity.class));
                    } else {
                        Toast.makeText(CreateFamilyActivity.this, "A family with the same name already exists. Please change name or try joining a family.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateFamilyActivity.this, FamilyActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateFamilyActivity.this, MainActivity.class));
            }
        });

    }
}
