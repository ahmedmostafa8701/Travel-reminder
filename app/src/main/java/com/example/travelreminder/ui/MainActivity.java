package com.example.travelreminder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.travelreminder.Auth.SignUp;
import com.example.travelreminder.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validateUser();
    }
    void validateUser(){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, SignUp.class));
        }
        else{

        }
        finish();
    }
}