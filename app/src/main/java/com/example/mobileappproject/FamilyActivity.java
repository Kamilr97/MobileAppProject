package com.example.mobileappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

public class FamilyActivity extends AppCompatActivity {
    ImageButton back;
    Button logout;
    Button joinFamily;
    Button createFamily;
    TextView familyName;
    ListView listUsers;

    DatabaseReference mDatabase;

    List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        FirebaseApp.initializeApp(this);

        back = findViewById(R.id.backArrowFamilyButton);
        joinFamily = findViewById(R.id.joinFamilyButton);
        createFamily = findViewById(R.id.createFamilyButton);
        familyName = findViewById(R.id.familyName);
        listUsers = findViewById(R.id.listUsers);

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list );
        listUsers.setAdapter(adapter);

        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String family = dataSnapshot.child("Users").child(currentUser).child("family").getValue().toString();
                    if (!family.equalsIgnoreCase("0")){
                        familyName.setText(family);
                    }

                for(DataSnapshot ds : dataSnapshot.child("Families").child(family).child("users").getChildren()) {
                    String userKey = ds.getKey();
                    String user = dataSnapshot.child("Users").child(userKey).child("name").getValue().toString();
                    adapter.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        joinFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamilyActivity.this, JoinFamilyActivity.class));
            }
        });

        createFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamilyActivity.this, CreateFamilyActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
