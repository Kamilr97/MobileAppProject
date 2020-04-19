package com.example.mobileappproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class JoinFamilyActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    Button logout;
    Button joinFamily;

    TextView selectedFamily;
    ListView familyList;

    List<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinfamily);

        FirebaseApp.initializeApp(this);


        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Families");

    }
}
