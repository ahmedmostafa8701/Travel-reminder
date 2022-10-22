package com.example.travelreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().getReferenceFromUrl("https://travelreminder-b45ee-default-rtdb.firebaseio.com/users").child("gsff").child("ddfgfdg").setValue("ahmed");
    }
}