package com.example.mobileappproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class JoinFamilyActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    ImageButton back;
    Button joinFamily;

    TextView selectedFamily;
    AutoCompleteTextView searchBox;

    List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinfamily);

        FirebaseApp.initializeApp(this);

        back = findViewById(R.id.backArrowFamilyButton);
        joinFamily = findViewById(R.id.joinChosenFamily);
        searchBox = findViewById(R.id.searchBox);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list);
        searchBox.setThreshold(1);
        searchBox.setAdapter(adapter);
        searchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("Families").getChildren()) {
                    list.add(ds.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        joinFamily.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userInput = searchBox.getText().toString();
                if(!list.contains(userInput)){
                    Toast.makeText(JoinFamilyActivity.this, "Invalid Family selected", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(JoinFamilyActivity.this, "Joined family successfully", Toast.LENGTH_LONG).show();
                    mDatabase.child("Users").child(currentuser).child("family").setValue(userInput);
                    mDatabase.child("Families").child(userInput).child("users").child(currentuser).setValue(true);
                    finish();
                }
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
